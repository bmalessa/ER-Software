package de.protin.support.pr.gui.timeperiod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.eclipse.swt.widgets.Text;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.TimePeriodHelper;
import de.protin.support.pr.gui.PensionGUI;

/**
 * Class that plays the role of the domain model.
 * In real life, this class would access a persistent store of some kind.
 * 
 */

public class TimePeriodList {

	private List<ITimePeriod> timePeriodList = new ArrayList<ITimePeriod>();
	private Set<ITimePeriodListViewer> changeListeners = new LinkedHashSet<ITimePeriodListViewer>();

	// Combo box choices
	static final String[] TIMEPERIOD_ARRAY = TimePeriodHelper.getTimePeriodTypeLabels();
	
	/**
	 * Constructor
	 */
	public TimePeriodList() {
		super();
		this.initData();
	}
	
	/**
	 * Initialize the table data.
	 * Create some time periods and add them them to the list of time periods
	 * 
	 */
	private void initData() {

	};

	/**
	 * Return the array of time period types   
	 */
	public String[] getTimePeriodLabels() {
		return TIMEPERIOD_ARRAY;
	}
	
	/**
	 * Return the collection of time periods
	 */
	public List<ITimePeriod> getTimePeriods() {
		return timePeriodList;
	}
	
	/**
	 * Add a new time period to the collection of time period
	 */
	public void addTimePeriod() {
		TimePeriodWrapper lastTimePeriod = null;
		if(timePeriodList.size() > 0) {
			lastTimePeriod = (TimePeriodWrapper)timePeriodList.get(timePeriodList.size()-1);
		}
		
		TimePeriodWrapper newTimePeriod = new TimePeriodWrapper();
		
		newTimePeriod.setLfdNummer(timePeriodList.size()+1);
		timePeriodList.add(timePeriodList.size(), newTimePeriod);
		Iterator<ITimePeriodListViewer> iterator = changeListeners.iterator();
		while (iterator.hasNext()) {
			iterator.next().addTimePeriod(newTimePeriod);
		}
		
		//das vorletzte Item enthält das letzte Ende-Datum
		Date nextStartDate = calculateStartDatum(newTimePeriod, lastTimePeriod);
		newTimePeriod.setStartDate(nextStartDate);
		
		sortTimePeriodList(timePeriodList);
	}





	/**
	 * @param time period
	 */
	public void removeTimePeriod(ITimePeriod timePeriod) {
		timePeriodList.remove(timePeriod);
		sortTimePeriodList(timePeriodList);
		Iterator<ITimePeriodListViewer> iterator = changeListeners.iterator();
		while (iterator.hasNext()) {
			iterator.next().removeTimePeriod(timePeriod);
		}
	}

	/**
	 * @param time period
	 */
	public void timePeriodChanged(ITimePeriod task) {
		Iterator<ITimePeriodListViewer> iterator = changeListeners.iterator();
		while (iterator.hasNext())
			iterator.next().updateTimePeriod(task);
	}

	/**
	 * @param viewer
	 */
	public void removeChangeListener(ITimePeriodListViewer viewer) {
		changeListeners.remove(viewer);
	}

	/**
	 * @param viewer
	 */
	public void addChangeListener(ITimePeriodListViewer viewer) {
		changeListeners.add(viewer);
	}

	
	private void sortTimePeriodList(List<ITimePeriod> timePeriodList) {
		 Collections.sort(timePeriodList, (x, y) -> x.getStartDate().compareTo(y.getStartDate()));
         for (int i = 0; i < timePeriodList.size(); i++) {
	    	TimePeriodWrapper timePeriod =(TimePeriodWrapper)timePeriodList.get(i);
		   	timePeriod.setLfdNummer(i + 1);
		 }
	}
	
	
	private Date calculateStartDatum(TimePeriodWrapper newTimePeriod, ITimePeriod lastTimePeriod) {
		if(lastTimePeriod == null) {
			PensionGUI gui = PensionGUI.getInstance();
			String substantiationDate = gui.getGrunddaten().getTfSubstantiationDate().getText();
			if(substantiationDate != null && substantiationDate.length() == 10) {
				Date nextStartDate = DateUtil.getDate(substantiationDate);
				if(nextStartDate == null) {
					return new Date();
				}
				else {
					return nextStartDate;
				}
			}
			else {
				return new Date();
			}
		}
		Date lastEndDate = lastTimePeriod.getEndDate();
		if(lastEndDate == null) {
			return new Date();
		}
		else {
			return DateUtils.addDays(lastEndDate, 1);
		}
	}
	
}
