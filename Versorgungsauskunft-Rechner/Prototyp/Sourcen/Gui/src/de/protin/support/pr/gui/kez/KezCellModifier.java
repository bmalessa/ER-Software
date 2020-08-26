package de.protin.support.pr.gui.kez;
import java.util.Date;
import java.util.Set;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

import de.protin.support.pr.domain.besoldung.zuschlag.Para_50_Kindererziehungszeit;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.PrintUtils;
import de.protin.support.pr.gui.KindererziehungszeitenPart;

/**
 * This class implements an ICellModifier
 * An ICellModifier is called when the user modifes a cell in the 
 * tableViewer
 */

public class KezCellModifier implements ICellModifier {
	
	private KindererziehungszeitenPart tableViewer;
	
	/**
	 * Constructor 
	 * @param ZeitenPart an instance of a ZeitenPart 
	 */
	public KezCellModifier(KindererziehungszeitenPart tableViewer, Set<KezPeriodWrapper> kindererziehungszuschlagList) {
		super();
		this.tableViewer = tableViewer;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		return true;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {

		// Find the index of the column
		int columnIndex = tableViewer.getColumnNames().indexOf(property);

		Object result = null;
		KezPeriodWrapper wrapper = (KezPeriodWrapper) element;
		
		switch (columnIndex) {
			case 0 : 
				result = wrapper.getKindererziehungszeit().getChildName();
				break;
			case 1 :
				result = PrintUtils.formatValue(wrapper.getKindererziehungszeit().getBirthDate());
				break;
			case 2 :
				result = PrintUtils.formatValue(wrapper.getKindererziehungszeit().getStartDate());
				break;
			case 3 :
				result = PrintUtils.formatValue(wrapper.getKindererziehungszeit().getEndDate());
				break;
			case 4 :
				result = Integer.toString(wrapper.getKindererziehungszeit().getLegalMonths());
				break;
			case 5 : 
				result = Integer.toString(wrapper.getKindererziehungszeit().getZulageMonths());
				break;
			case 6 : 	
				String stringValue = wrapper.getLabel();
				String[] choices = tableViewer.getChoices(property);
				int i = choices.length - 1;
				while (!stringValue.equals(choices[i]) && i > 0)
					--i;
				result = new Integer(i);
				break;
			default :
				result = "";
		}
		return result;	
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#modify(java.lang.Object, java.lang.String, java.lang.Object)
	 */
	public void modify(Object element, String property, Object value) {	

		int columnIndex	= tableViewer.getColumnNames().indexOf(property);
		TableItem item = (TableItem) element;
		KezPeriodWrapper wrapper = (KezPeriodWrapper)item.getData();
						
				
		String valueString;
		Date valueDate;
		int valueInt;
		switch (columnIndex) {
			case 0 : 
				wrapper.getKindererziehungszeit().setChildName((String)value);
				break;
			case 1 : // Start Datum
				valueDate = DateUtil.getDate((String)value);
				wrapper.getKindererziehungszeit().setBirthDate(valueDate);
				break;		
			case 2 : // Start Datum
				valueDate = DateUtil.getDate((String)value);
				wrapper.getKindererziehungszeit().setStartDate(valueDate);
				break;	
			case 3 : // Start Datum
				valueDate = DateUtil.getDate((String)value);
				wrapper.getKindererziehungszeit().setEndDate(valueDate);
				break;	
			case 4 : // Factor
				valueInt = Integer.parseInt(((String)value));
				wrapper.getKindererziehungszeit().setLegalMonths(valueInt);
				break;
			case 5 : // RGF/Tage
				valueInt = Integer.parseInt(((String)value));
				if (wrapper.getKindererziehungszeit().getZulageMonths() != valueInt) {
					wrapper.getKindererziehungszeit().setZulageMonths(valueInt);
				}
				break;
			case 6 : // Voller Anspruch
				valueString = tableViewer.getChoices(property)[((Integer) value).intValue()].trim();
				wrapper.setLabel(valueString);
				if (Para_50_Kindererziehungszeit.VOLLER_KEZ_ANSPRUCH.equalsIgnoreCase(valueString)) {
					wrapper.getKindererziehungszeit().setVollerAnspruch(true);
					wrapper.getKindererziehungszeit().setAnspruch(Para_50_Kindererziehungszeit.VOLLER_KEZ_ANSPRUCH);
				}
				else if (Para_50_Kindererziehungszeit.TEIL_KEZ_ANSPRUCH.equalsIgnoreCase(valueString)) {
					wrapper.getKindererziehungszeit().setVollerAnspruch(false);
					wrapper.getKindererziehungszeit().setAnspruch(Para_50_Kindererziehungszeit.TEIL_KEZ_ANSPRUCH);
				}
				else if (Para_50_Kindererziehungszeit.KEIN_KEZ_ANSPRUCH.equalsIgnoreCase(valueString)) {
					wrapper.getKindererziehungszeit().setVollerAnspruch(false);
					wrapper.getKindererziehungszeit().setAnspruch(Para_50_Kindererziehungszeit.KEIN_KEZ_ANSPRUCH);
					wrapper.getKindererziehungszeit().setLegalMonths(0);
					wrapper.getKindererziehungszeit().setZulageMonths(0);
				}
				break;
				
			default :
			}
		
		tableViewer.refreshViewPart();
	}
}
