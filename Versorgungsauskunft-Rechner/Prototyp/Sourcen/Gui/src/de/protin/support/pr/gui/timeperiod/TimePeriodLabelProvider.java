package de.protin.support.pr.gui.timeperiod;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.protin.support.pr.domain.utils.PrintUtils;


/**
 * Label provider for the ZeitenPart
 * 
 * @see org.eclipse.jface.viewers.LabelProvider 
 */
public class TimePeriodLabelProvider extends LabelProvider	implements ITableLabelProvider {




	/**
	 * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
	 */
	public String getColumnText(Object element, int columnIndex) {
		String result = "";
		TimePeriodWrapper timePeriod = (TimePeriodWrapper) element;
		switch (columnIndex) {
			case 0:  // COMPLETED_COLUMN
				result = Integer.toString(timePeriod.getLfdNummer());
				break;
			case 1 :
				result = timePeriod.getLabel();
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
			case 5 :
				result = Integer.toString(timePeriod.getAnzahlTage());
				break;
			case 6 :
				result = Integer.toString(timePeriod.getRuhegehaltsfaehigeTage());
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