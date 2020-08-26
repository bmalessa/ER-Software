package de.protin.support.pr.gui.timeperiod;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;



public class TimePeriodComparator extends ViewerComparator {
    private int propertyIndex;
    private static final int DESCENDING = 1;
    private int direction = DESCENDING;

    public TimePeriodComparator() {
        this.propertyIndex = 0;
        direction = DESCENDING;
    }

    public int getDirection() {
        return direction == 1 ? SWT.DOWN : SWT.UP;
    }

    public void setColumn(int column) {
        if (column == this.propertyIndex) {
            // Same column as last sort; toggle the direction
            direction = 1 - direction;
        } else {
            // New column; do an ascending sort
            this.propertyIndex = column;
            direction = DESCENDING;
        }
    }

    @Override
    public int compare(Viewer viewer, Object e1, Object e2) {
        TimePeriodWrapper timePeriod1 = (TimePeriodWrapper) e1;
		TimePeriodWrapper timePeriod2 = (TimePeriodWrapper) e2;

        int rc = 0;
        switch (propertyIndex) {
        case 1:
            rc = compareTimePeriodLabels(timePeriod1, timePeriod2);
            break;
        case 2:
            rc = compareDateFrom(timePeriod1, timePeriod2);
            break;
        case 3:
            rc = compareDateTo(timePeriod1, timePeriod2);
            break;
        case 5:
            rc = compareAmountOfDays(timePeriod1, timePeriod2);
            break;
        default:
            rc = 0;
        }
        // If descending order, flip the direction
        if (direction == DESCENDING) {
            rc = -rc;
        }
        return rc;
    }
	
	
	private int compareDateFrom(TimePeriodWrapper timePeriod1, TimePeriodWrapper timePeriod2) {
		int result = timePeriod1.getStartDate().compareTo(timePeriod2.getStartDate());
		result = result < 0 ? -1 : (result > 0) ? 1 : 0;  
		return result;
	}
	
	private int compareDateTo(TimePeriodWrapper timePeriod1, TimePeriodWrapper timePeriod2) {
		int result = timePeriod1.getEndDate().compareTo(timePeriod2.getEndDate());
		result = result < 0 ? -1 : (result > 0) ? 1 : 0;  
		return result;
	}


	protected int compareTimePeriodLabels(TimePeriodWrapper timePeriod1, TimePeriodWrapper timePeriod2) {
		int result = timePeriod1.getLabel().compareTo(timePeriod2.getLabel());
		result = result < 0 ? -1 : (result > 0) ? 1 : 0;  
		return result;
	}


	protected int compareAmountOfDays(TimePeriodWrapper timePeriod1, TimePeriodWrapper timePeriod2) {
		int result = timePeriod1.getAnzahlTage() - timePeriod2.getAnzahlTage();
		result = result < 0 ? -1 : (result > 0) ? 1 : 0;  
		return result;
	}

}