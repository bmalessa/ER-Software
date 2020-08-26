package de.protin.support.pr.domain.timeperiod;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import de.protin.support.pr.domain.Comment;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.utils.TimePeriodCalculator;

public abstract class BaseTimePeriod implements ITimePeriod {

	
		protected int type;
		protected Date startDate;
		protected Date endDate;
		protected float factor; //Vollzeit = 1, Halbtags = 0,5
		protected int ruhegehaltsfaehigeTage;
		
		protected Set<Comment> comments;
		
		
		public BaseTimePeriod(int type, Date startDate, Date endDate, float factor) {
			this.type = type;
			this.startDate = startDate;
			this.endDate = endDate;
			this.factor = factor; 
			this.comments = new HashSet<Comment>();
			this.ruhegehaltsfaehigeTage = -1;
		}
		

		public abstract String getZeitart(); 
		
		
		public void addComment(Comment comment) {
			comments.add(comment);
		}
		
		public boolean removeComment(Comment comment) {
			return comments.remove(comment);
		}
		
		public void resetComments() {
			comments.clear();
		}


		public int getType() {
			return type;
		}


		public void setType(int type) {
			this.type = type;
		}


		public Date getStartDate() {
			return startDate;
		}


		public void setStartDate(Date startDate) {
			//bei Änderung am Zeitraum der periode werden die ruhegehaltsfähge Tage 
			//auf 0 zurückgesetzt
			setRuhegehaltsfaehigeTage(0);
			this.startDate = startDate;
		}


		public Date getEndDate() {
			return endDate;
		}


		public void setEndDate(Date endDate) {
			//bei Änderung am Zeitraum der periode werden die ruhegehaltsfähge Tage 
			//auf 0 zurückgesetzt
			if(this.endDate != null) {
				setRuhegehaltsfaehigeTage(0);
			}
			this.endDate = endDate;
			
		}


		public float getFactor() {
			return factor;
		}


		public void setFactor(float factor) {
			this.factor = factor;
		}


		public Set<Comment> getComments() {
			return comments;
		}


		public void setComments(Set<Comment> comments) {
			this.comments = comments;
		}


		@Override
		public int getAnzahlTage() {
			if(startDate != null && endDate != null && startDate.before(endDate) ) {
				long daysForTimePeriod = TimePeriodCalculator.calculateDaysForTimePeriod(startDate, endDate);
				return (int)daysForTimePeriod;
			}
			return -1;
		}


		public int getRuhegehaltsfaehigeTage() {
			
			if(factor == 1.0 && ruhegehaltsfaehigeTage > -1) {
				return ruhegehaltsfaehigeTage;
			}
			
			if(ruhegehaltsfaehigeTage < 0) {
				ruhegehaltsfaehigeTage = getAnzahlTage();
			}
			
			ruhegehaltsfaehigeTage = (int) (getAnzahlTage() * this.factor);
			return ruhegehaltsfaehigeTage;
		}


		
		public void setRuhegehaltsfaehigeTage(int ruhegehaltsfaehigeTage) {
			this.ruhegehaltsfaehigeTage = ruhegehaltsfaehigeTage;
		}


		

		
}
