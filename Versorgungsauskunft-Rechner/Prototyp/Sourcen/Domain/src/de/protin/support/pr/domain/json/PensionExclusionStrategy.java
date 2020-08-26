package de.protin.support.pr.domain.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class PensionExclusionStrategy implements ExclusionStrategy{
	
	
	    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
	        if("besoldungstabelle".equals(fieldAttributes.getName())){
	            return true;
	        }
	        else if ("deductionParts".equals(fieldAttributes.getName())) {
	        	return true;
	        }
	        else if ("additionParts".equals(fieldAttributes.getName())) {
	        	return true;
	        }
	        else if ("sumYears".equals(fieldAttributes.getName())) {
	        	return true;
	        }
	        else if ("restDays".equals(fieldAttributes.getName())) {
	        	return true;
	        }
	        
	        //Attribute f.d. Kindererziehungszuschlag
	        else if ("kezService".equals(fieldAttributes.getName()) ||
	        		"date_19911231".equals(fieldAttributes.getName()) ||
	        		"date_19920101".equals(fieldAttributes.getName()) ||
	        		"mtlEntgeltPunkt".equals(fieldAttributes.getName()) ||
	        		"rentenwertID".equals(fieldAttributes.getName()) ||
	        		"pension".equals(fieldAttributes.getName())) {
	        	return true;
        	}	
	        
	        return false;
	    }
	
	    public boolean shouldSkipClass(Class aClass) {
	    	//System.out.println("shouldSkipClass: " + aClass.getName());
	        return false;
	    }
	
}

