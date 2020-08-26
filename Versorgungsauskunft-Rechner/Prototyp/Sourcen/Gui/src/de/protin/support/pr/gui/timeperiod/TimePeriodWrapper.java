package de.protin.support.pr.gui.timeperiod;
/**
 * (c) Copyright Mirasol Op'nWorks Inc. 2002, 2003. 
 * http://www.opnworks.com
 * Created on Apr 2, 2003 by lgauthier@opnworks.com
 * 
 */

import java.util.Date;
import java.util.Set;

import de.protin.support.pr.domain.Comment;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.utils.TimePeriodHelper;

/**
 * 
 * @author 
 *
 * 
 */
public class TimePeriodWrapper implements ITimePeriod {

	private ITimePeriod timePeriod;
	protected int lfdNummer;
	protected String label;
	
	
	public TimePeriodWrapper() {
		this.timePeriod = new TimePeriodTemplate( new Date(), null, 1.0f);
	}
	
	public TimePeriodWrapper(ITimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}
	
	public TimePeriodWrapper(int type, Date startDate, Date endDate, float factor) {
		this.timePeriod = TimePeriodHelper.createTimePeriod(type, startDate, endDate, factor);
	}
	
	
	

	public void addComment(Comment comment) {
		timePeriod.addComment(comment);
	}
	
	public boolean removeComment(Comment comment) {
		return timePeriod.removeComment(comment);
	}
	
	public void resetComments() {
		timePeriod.resetComments();
	}


	public int getType() {
		return timePeriod.getType();
	}


	public void setType(int type) {
		this.timePeriod.setType(type);
	}
	

	public String getLabel() {
		TimePeriodHelper.getLabel(timePeriod.getType());
		return TimePeriodHelper.getLabel(timePeriod.getType());
	}


	public Date getStartDate() {
		return timePeriod.getStartDate();
	}


	public void setStartDate(Date startDate) {
		this.timePeriod.setStartDate(startDate);
	}


	public Date getEndDate() {
		return timePeriod.getEndDate();
	}


	public void setEndDate(Date endDate) {
		this.timePeriod.setEndDate(endDate);
	}


	public float getFactor() {
		return timePeriod.getFactor();
	}


	public void setFactor(float factor) {
		this.timePeriod.setFactor(factor);
	}


	public Set<Comment> getComments() {
		return timePeriod.getComments();
	}


	public void setComments(Set<Comment> comments) {
		this.timePeriod.setComments(comments);
	}

	
	public int getAnzahlTage() {
		return this.timePeriod.getAnzahlTage();
	}


	public int getRuhegehaltsfaehigeTage() {
		return this.timePeriod.getRuhegehaltsfaehigeTage();
	}


	public void setRuhegehaltsfaehigeTage(int ruhegehaltsfaehigeTage) {
		this.timePeriod.setRuhegehaltsfaehigeTage(ruhegehaltsfaehigeTage);
	}


	public int getLfdNummer() {
		return lfdNummer;
	}

	
	public void setLfdNummer(int lfdNummer) {
		this.lfdNummer = lfdNummer;
	}

	
	public void changeType(String valueString) {
		int newType = TimePeriodHelper.getTimePeriodTypeForLabel(valueString);
		ITimePeriod newTimePeriod = TimePeriodHelper.createTimePeriod(newType, timePeriod.getStartDate(), timePeriod.getEndDate(), timePeriod.getFactor());
		newTimePeriod.setComments(this.timePeriod.getComments());
		this.timePeriod = newTimePeriod;
		this.label = valueString;
	}

	@Override
	public String getZeitart() {
		return this.timePeriod.getZeitart();
	}
	
	

	

}
