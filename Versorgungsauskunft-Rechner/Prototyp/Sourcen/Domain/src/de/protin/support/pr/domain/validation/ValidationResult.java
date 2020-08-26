package de.protin.support.pr.domain.validation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ValidationResult {
	private Set<ValidationItem> items;
	
	public ValidationResult() {
		this.items = new HashSet<ValidationItem>();
	}

	public boolean isSuccess() {
		if(this.items.isEmpty()) {
			return true;
		}
		else {
			return false;
		}
	}

	public void addItem(ValidationItem item) {
		this.items.add(item);
	}

	public Set<ValidationItem> getItems() {
		return items;
	}

	public void setItems(Set<ValidationItem> items) {
		this.items = items;
	}
	
	public void reset() {
		this.items.clear();
	}

	
	@Override
	public String toString() {
		
		if(isSuccess()) {
		 return new String("Succesful validated");
		}
		else {
			StringBuffer sb = new StringBuffer();
			sb.append("Validation failed: \n");
			for (Iterator<ValidationItem> iterator = items.iterator(); iterator.hasNext();) {
				ValidationItem validationItem = iterator.next();
				sb.append(validationItem.getMessage() + "\n");
			}
			return sb.toString();
		}
		
	}
}