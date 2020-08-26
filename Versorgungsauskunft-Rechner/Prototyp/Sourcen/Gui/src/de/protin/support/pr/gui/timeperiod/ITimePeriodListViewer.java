package de.protin.support.pr.gui.timeperiod;

import de.protin.support.pr.domain.ITimePeriod;

public interface ITimePeriodListViewer {
	
	/**
	 * Update the view to reflect the fact that a time period was added 
	 * to the time period list
	 * 
	 * @param timePeriod
	 */
	public void addTimePeriod(ITimePeriod timePeriod);
	
	/**
	 * Update the view to reflect the fact that a time period was removed 
	 * from the time period
	 * 
	 * @param timePeriod
	 */
	public void removeTimePeriod(ITimePeriod timePeriod);
	
	/**
	 * Update the view to reflect the fact that one of the time periods
	 * was modified 
	 * 
	 * @param timePeriod
	 */
	public void updateTimePeriod(ITimePeriod timePeriod);
}