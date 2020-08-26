package de.protin.support.pr.gui.kez;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.protin.support.pr.domain.utils.PrintUtils;


/**
 * Label provider for the tableviewer in KindererziehungszeitenPart
 * 
 * @see org.eclipse.jface.viewers.LabelProvider 
 */
public class KezLabelProvider extends LabelProvider	implements ITableLabelProvider {




	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		String result = "";
		KezPeriodWrapper wrapper = (KezPeriodWrapper) element;
		switch (columnIndex) {
			case 0:  
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
				result = wrapper.getLabel();
				break;
			default :
				break; 	
		}
		return result;
	}

	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
	 */
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}

}