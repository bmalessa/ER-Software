package de.protin.support.pr.domain.besoldung.tabelle.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.tabelle.AbstractBesoldungstabelle;
import de.protin.support.pr.domain.utils.DateUtil;

public class InMemory_Actual extends AbstractBesoldungstabelle {

	
	
	public InMemory_Actual() {
		setTitel("Besoldungstabelle Bund");
		setSubTitel("Aktuelle Besoldungstabelle für Beamtinnen und Beamte des Bundes (Gültig ab 01.03.2020)");
		setValidFrom(DateUtil.getDate(new String("01.03.2020")));
		setValidTo(null);
		
		initABesoldung();
		initBBesoldung();
		initFamZuschlag();
		initAmtszulage();
		initKindererziehungszuschlag(new Date());
	}
	

	
	private void initBBesoldung() {
		besoldungsordnungB = new TreeMap<Integer, Float>();
		besoldungsordnungB.put(1, 7038.72f);
		besoldungsordnungB.put(2, 8176.63f);
		besoldungsordnungB.put(3, 8658.13f);
		besoldungsordnungB.put(4, 9161.83f);
		besoldungsordnungB.put(5, 9739.93f);
		besoldungsordnungB.put(6, 10289.32f);
		besoldungsordnungB.put(7, 10819.10f);
		besoldungsordnungB.put(8, 11373.67f);
		besoldungsordnungB.put(9, 12061.37f);
		besoldungsordnungB.put(10, 14197.53f);
		besoldungsordnungB.put(11, 14749.49f);
	}



	private void initABesoldung() {
		
		besoldungsordnungA = new HashMap<Integer, float[]>();
		besoldungsordnungA.put(3, new float[] {2301.21f, 2354.13f, 2406.07f, 2447.88f, 2488.68f, 2530.48f, 2572.30f, 2615.10f});
		besoldungsordnungA.put(4, new float[] {2349.36f, 2411.41f, 2473.48f, 2522.89f, 2572.30f, 2621.71f, 2671.10f, 2716.73f});
		besoldungsordnungA.put(5, new float[] {2367.07f, 2444.34f, 2506.40f, 2567.24f, 2628.06f, 2690.14f, 2750.92f, 2810.47f});
		besoldungsordnungA.put(6 ,new float[] {2417.74f, 2507.71f, 2598.89f, 2668.57f, 2740.79f, 2810.47f, 2887.74f, 2954.88f});
		besoldungsordnungA.put(7, new float[] {2538.10f, 2617.92f, 2723.09f, 2830.73f, 2935.88f, 3042.30f, 3122.12f, 3201.92f});
		besoldungsordnungA.put(8, new float[] {2685.05f, 2781.34f, 2916.87f, 3053.72f, 3190.51f, 3285.53f, 3381.81f, 3476.83f});
		besoldungsordnungA.put(9, new float[] {2897.87f, 2992.89f, 3142.39f, 3294.40f, 3443.86f, 3545.48f, 3651.19f, 3754.27f});
		besoldungsordnungA.put(10, new float[] {3101.83f, 3232.31f, 3421.09f, 3610.70f, 3803.84f, 3938.26f, 4072.64f, 4207.09f});
		besoldungsordnungA.put(11, new float[] {3545.48f, 3745.12f, 3943.47f, 4143.12f, 4280.13f, 4417.15f, 4554.17f, 4691.22f});
		besoldungsordnungA.put(12 ,new float[] {3801.25f, 4037.44f, 4274.93f, 4511.11f, 4675.53f, 4837.33f, 5000.45f, 5166.19f});
		besoldungsordnungA.put(13, new float[] {4457.62f, 4679.45f, 4899.96f, 5121.81f, 5274.49f, 5428.48f, 5581.13f, 5731.19f});
		besoldungsordnungA.put(14, new float[] {4584.18f, 4869.95f, 5157.05f, 5442.81f, 5639.84f, 5838.22f, 6035.24f, 6233.61f});
		besoldungsordnungA.put(15, new float[] {5603.31f, 5861.70f, 6058.73f, 6255.79f, 6452.84f, 6648.57f, 6844.31f, 7038.72f});
		besoldungsordnungA.put(16, new float[] {6181.40f, 6481.55f, 6708.59f, 6935.65f, 7161.40f, 7389.78f, 7616.82f, 7841.28f});
	}
	
	
	private void initAmtszulage() {
		//Stand 01.07.2020
		amtszulage = new TreeMap<String, Amtszulage[]>();
		amtszulage.put("A 3", new Amtszulage[] {new Amtszulage("A 3", 43.37f, "1"), new Amtszulage("A 3", 80.00f, "2"), new Amtszulage("A 3", 40.39f, "3")});
		amtszulage.put("A 4", new Amtszulage[] {new Amtszulage("A 4", 43.37f, "1"), new Amtszulage("A 4", 80.00f, "2"), new Amtszulage("A 4", 8.72f, "4")});
		amtszulage.put("A 5", new Amtszulage[] {new Amtszulage("A 5", 43.37f, "1"),  new Amtszulage("A 5", 80.00f, "3")});
		amtszulage.put("A 6", new Amtszulage[] {new Amtszulage("A 6", 43.37f, "2"), new Amtszulage("A 6", 43.37f, "5")});
		amtszulage.put("A 7", new Amtszulage[] {new Amtszulage("A 7", 53.86f, "5")});
		amtszulage.put("A 8", new Amtszulage[] {new Amtszulage("A 8", 69.39f, "1")});
		amtszulage.put("A 9", new Amtszulage[] {new Amtszulage("A 9", 322.88f, "1")});
		amtszulage.put("A 13", new Amtszulage[] {new Amtszulage("A 13", 328.12f, "1"), new Amtszulage("A 13", 149.98f, "7")});
		amtszulage.put("A 14", new Amtszulage[] {new Amtszulage("A 14", 224.96f, "5")});
		amtszulage.put("A 15" ,new Amtszulage[] {new Amtszulage("A 15", 299.93f, "3"), new Amtszulage("A 15", 224.96f, "8")});
		amtszulage.put("A 16", new Amtszulage[] {new Amtszulage("A 16", 251.58f, "6")});
		amtszulage.put("B 10", new Amtszulage[] {new Amtszulage("B 10", 519.86f, "1")});
	}
	
	
	private void initFamZuschlag() {
		this.famZuschlagStufe1 = 149.36f;
		this.famZuschlagStufe2 = 277.02f;
		this.famZuschlagZweitesKind = 127.66f;
		this.famZuschlagWeitereKinder = 397.74f;    
		
	}


	
}
