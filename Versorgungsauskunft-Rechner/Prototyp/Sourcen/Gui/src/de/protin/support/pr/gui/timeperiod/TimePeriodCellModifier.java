package de.protin.support.pr.gui.timeperiod;
import java.text.ParseException;
import java.util.Date;

import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.swt.widgets.TableItem;

import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.PrintUtils;
import de.protin.support.pr.gui.ZeitenPart;

/**
 * This class implements an ICellModifier
 * An ICellModifier is called when the user modifes a cell in the 
 * tableViewer
 */

public class TimePeriodCellModifier implements ICellModifier {
	
	private ZeitenPart tableViewer;
	
	/**
	 * Constructor 
	 * @param ZeitenPart an instance of a ZeitenPart 
	 */
	public TimePeriodCellModifier(ZeitenPart tableViewerExample) {
		super();
		this.tableViewer = tableViewerExample;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#canModify(java.lang.Object, java.lang.String)
	 */
	public boolean canModify(Object element, String property) {
		// Find the index of the column
		int columnIndex = tableViewer.getColumnNames().indexOf(property);
		
		if(columnIndex == 1 || columnIndex == 2|| columnIndex == 3|| columnIndex == 4) {
			return true;
		}
		
		//rgf Tage nur dann editierbar, wenn der Faktor = 1.0 ist.
		if(columnIndex == 6) {
			TimePeriodWrapper timePeriod = (TimePeriodWrapper) element;
			if (timePeriod.getFactor() == 1.0) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * @see org.eclipse.jface.viewers.ICellModifier#getValue(java.lang.Object, java.lang.String)
	 */
	public Object getValue(Object element, String property) {

		// Find the index of the column
		int columnIndex = tableViewer.getColumnNames().indexOf(property);

		Object result = null;
		TimePeriodWrapper timePeriod = (TimePeriodWrapper) element;

		switch (columnIndex) {
			case 1 : // TimePeriod Label 
				String stringValue = timePeriod.getLabel();
				String[] choices = tableViewer.getChoices(property);
				int i = choices.length - 1;
				while (!stringValue.equals(choices[i]) && i > 0)
					--i;
				result = new Integer(i);
				break;
			case 2 :
				result = PrintUtils.formatValue(timePeriod.getStartDate());
				break;
			case 3 :
				result = PrintUtils.formatValue(timePeriod.getEndDate());
				break;
			case 4 :
				result = Float.toString(timePeriod.getFactor());
				break;
			case 6 : 
				result = Integer.toString(timePeriod.getRuhegehaltsfaehigeTage());
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

		// Find the index of the column 
		int columnIndex	= tableViewer.getColumnNames().indexOf(property);
			
		TableItem item = (TableItem) element;
		TimePeriodWrapper timePeriod = (TimePeriodWrapper) item.getData();
		String valueString;
		Date valueDate;
		switch (columnIndex) {
			case 1 : 
				valueString = tableViewer.getChoices(property)[((Integer) value).intValue()].trim();
				if (!timePeriod.getLabel().equals(valueString)) {
					timePeriod.changeType(valueString);
				}
				break;
			case 2 : // Start Datum
				if(((String)value).length() == 10) {
					try {
						valueDate = DateUtil.parseDateString((String)value);
						timePeriod.setStartDate(valueDate);
					}
					catch (ParseException ex) {
						//e.printStackTrace();
					}
				}
				break;	
			case 3 : // Start Datum
				if(((String)value).length() == 10) {
					try {
						valueDate = DateUtil.parseDateString((String)value);
						timePeriod.setEndDate(valueDate);
					}
					catch (ParseException ex) {
						//e.printStackTrace();
					}
				}
				break;	
			case 4 : // Factor
				
				//falls jemand ein Komma anstatt eines Decimal-Punkt eingegeben hat, wird dies hier berichtigt, damit keine NumberFormatException auftritt 
				String sFactor = (String)value;
				if(sFactor.contains(",")) {
					sFactor = sFactor.replace(",", ".");
					value = sFactor;
				}
				
				
				try {
					float valueFloat = Float.parseFloat(((String)value));
					if (timePeriod.getFactor() != valueFloat) {
						timePeriod.setFactor(valueFloat);
					}
				} catch (NumberFormatException e) {
					//e.printStackTrace();
				}
				break;
			case 6 : // RGF/Tage
				try {
				int valueInt = Integer.parseInt(((String)value));
					if (timePeriod.getRuhegehaltsfaehigeTage() != valueInt) {
						timePeriod.setRuhegehaltsfaehigeTage(valueInt);
					}
				} catch (NumberFormatException e) {
					//e.printStackTrace();
				}
				break;
			default :
		}
		
		tableViewer.getTimePeriodList().timePeriodChanged(timePeriod);
	}
}
