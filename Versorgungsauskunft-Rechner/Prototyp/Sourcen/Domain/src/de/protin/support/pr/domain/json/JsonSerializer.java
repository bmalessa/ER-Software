package de.protin.support.pr.domain.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.protin.support.pr.domain.IPension;

public class JsonSerializer {

	/**
	 * 
	 * @param pension
	 * @return
	 */
	public static String serializeToJson(IPension pension) {
		GsonBuilder builder = new GsonBuilder();
		builder = builder
				.setExclusionStrategies(new PensionExclusionStrategy())
				.serializeNulls()
				.setPrettyPrinting();
		
		Gson gson = builder.create();
		
		String jsonString = gson.toJson(pension);
		return jsonString;
	}

}
