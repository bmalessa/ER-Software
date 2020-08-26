package de.protin.support.pr.domain.json;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import de.protin.support.pr.domain.IBesoldungstabelle;
import de.protin.support.pr.domain.IPension;
import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.Person;
import de.protin.support.pr.domain.besoldung.tabelle.BesoldungstabelleFactory;
import de.protin.support.pr.domain.besoldung.zuschlag.KindererziehungszeitenZuschlag;
import de.protin.support.pr.domain.pension.AntragsRuhestand;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.pension.EngagierterRuhestand;
import de.protin.support.pr.domain.pension.RegelaltersgrenzeRuhestand;
import de.protin.support.pr.domain.pension.Vorruhestand;
import de.protin.support.pr.domain.utils.DateUtil;

public class JsonDeserializer {

	private static Gson gson;
	private static Date date_19911231 = DateUtil.getDate("31.12.1991");
	
	/**
	 * 
	 * @param jsonString
	 * @return
	 */
	public static IPension deserializeFromJson(String jsonString) {
		GsonBuilder builder = new GsonBuilder();
		builder = builder
				.setExclusionStrategies(new PensionExclusionStrategy())
				.serializeNulls()
				.setPrettyPrinting();
		
		gson = builder.create();
		
		
		IPension pension=  null;
		
		JsonObject jsonObject = null;
		JsonElement jsonTree = JsonParser.parseString(jsonString);
		if(jsonTree.isJsonObject()) {
			jsonObject = jsonTree.getAsJsonObject();
		}
			
		Person person = getPersonFromJson(jsonObject);
		String anzwRecht = jsonObject.get("anzwRecht").getAsString();	
			
	    JsonElement pensionTyp = jsonObject.get("typ");
	    int typ = pensionTyp.getAsInt();
		    
	    switch(typ) {
	    	case 1:
	    		pension = new RegelaltersgrenzeRuhestand(person, anzwRecht);
	    		break;
	    	case 2:
	    		pension = new AntragsRuhestand(person, anzwRecht);
	    		break;
	    	case 3:
	    		pension = new EngagierterRuhestand(person, anzwRecht);
	    		break;
	    	case 4:
	    		pension = new Vorruhestand(person, anzwRecht);
	    		break;
	    	case 5:
	    		pension = new Dienstunfaehigkeit(person, anzwRecht);
	    		break;
	    	default:
	    		break;
	    }
	
	    
	    pension.setVergleichsberechnungBeamtVG_PARA_85(jsonObject.get("vergleichsberechnungBeamtVG_PARA_85").getAsBoolean());
	    pension.setTimePeriods(getTimePeriodsFromJson(jsonObject));
	    pension.setKindererziehungsZuschlag(getKindererziehungszuschlagFromJson(jsonObject, anzwRecht, person.getDateOfRetirement()));
	    
	    return pension;
	    
	}
	
	


	/**
	 * 
	 * @param jsonObject
	 * @return
	 */
	private static Person getPersonFromJson(JsonObject jsonObject) {
		
		Person person = null;
		
		JsonObject personObject = null;
		JsonElement personElement = jsonObject.get("person");
		if(personElement.isJsonObject()) {
			personObject = personElement.getAsJsonObject();
			
			String personString = personObject.toString();
			person = gson.fromJson(personString, Person.class);
			
		}
		
		return person;
	}


	
	/**
	 * 
	 * @param jsonObject
	 * @return
	 */
	private static Set<ITimePeriod> getTimePeriodsFromJson(JsonObject jsonObject) {
		Set<ITimePeriod> timePeriods = new LinkedHashSet<ITimePeriod>();
		
		
		JsonElement jsonElement = jsonObject.get("timePeriods");
		JsonArray jsonArray = null;
		if(jsonElement.isJsonArray()) {
			jsonArray = jsonElement.getAsJsonArray();
		}
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement tpElement = jsonArray.get(i);
			if(tpElement.isJsonObject()) {
				JsonObject tpJsonObject = tpElement.getAsJsonObject();
				tpJsonObject = tpJsonObject.get("timePeriod").getAsJsonObject();
			
				JsonTimePeriod jsonTP = gson.fromJson(tpJsonObject.toString(), JsonTimePeriod.class);
				timePeriods.add(jsonTP.getDomainObject());
			}
		}
	
		
		return timePeriods;
	}
	
	
	
	private static KindererziehungszeitenZuschlag getKindererziehungszuschlagFromJson(JsonObject jsonObject, String anzuwRecht, Date dateOfRetirement) {
		
		IBesoldungstabelle besoldungstabelle = BesoldungstabelleFactory.getInstance().getBesoldungstabelle(anzuwRecht, dateOfRetirement);
		KindererziehungszeitenZuschlag kez = new KindererziehungszeitenZuschlag(besoldungstabelle.getAktuellenRentenwertWestForKindererziehungszuschlag());
		
		JsonElement jsonElement = jsonObject.get("kindererziehungsZuschlag");
		if(jsonElement instanceof JsonNull) {
			return null;
		}
		
		
		JsonArray jsonArray = null;
		
		JsonElement jsonElementKezVor1992 = jsonElement.getAsJsonObject().get("kindererziehungszeitenVor1992");
		if(jsonElementKezVor1992.isJsonArray()) {
			jsonArray = jsonElementKezVor1992.getAsJsonArray();
		}
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement tpElement = jsonArray.get(i);
			if(tpElement.isJsonObject()) {
				JsonObject tpJsonObject = tpElement.getAsJsonObject();
				JsonKindererziehungszeit jsonKez = gson.fromJson(tpJsonObject.toString(), JsonKindererziehungszeit.class);
				kez.addKindererziehungszeit(jsonKez.getDomainObject(), KindererziehungszeitenZuschlag.KEZ_VOR_1992);
			}
		}
		
		JsonElement jsonElementNach1991 = jsonElement.getAsJsonObject().get("kindererziehungszeitenNach1991");
		jsonArray = null;
		if(jsonElementNach1991.isJsonArray()) {
			jsonArray = jsonElementNach1991.getAsJsonArray();
		}
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonElement tpElement = jsonArray.get(i);
			if(tpElement.isJsonObject()) {
				JsonObject tpJsonObject = tpElement.getAsJsonObject();
				JsonKindererziehungszeit jsonKez = gson.fromJson(tpJsonObject.toString(), JsonKindererziehungszeit.class);
				kez.addKindererziehungszeit(jsonKez.getDomainObject(), KindererziehungszeitenZuschlag.KEZ_NACH_1991);
			}
		}
		
		
		
		return kez;
	}

}
