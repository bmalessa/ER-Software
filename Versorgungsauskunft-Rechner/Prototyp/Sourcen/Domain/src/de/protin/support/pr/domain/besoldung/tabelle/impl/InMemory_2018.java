package de.protin.support.pr.domain.besoldung.tabelle.impl;

import java.util.HashMap;
import java.util.TreeMap;

import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.tabelle.AbstractBesoldungstabelle;
import de.protin.support.pr.domain.utils.DateUtil;

public class InMemory_2018 extends AbstractBesoldungstabelle {

	
	
	public InMemory_2018() {
		setTitel("Besoldungstabelle Bund");
		setSubTitel("Besoldungstabelle für Beamtinnen und Beamte des Bundes (Gültig vom 01.03.2018 - 31.03.2019)");
		setValidFrom(DateUtil.getDate(new String("01.03.2018")));
		setValidTo(DateUtil.getDate(new String("31.03.2019")));
		
		initABesoldung();
		initBBesoldung();
		initFamZuschlag();
		initAmtszulage();
		initKindererziehungszuschlag(DateUtil.getDate(new String("01.03.2018")));
	}
	

	
	private void initBBesoldung() {
		besoldungsordnungB = new TreeMap<Integer, Float>();
		besoldungsordnungB.put(1, 6756.13f);
		besoldungsordnungB.put(2, 7848.36f); 
		besoldungsordnungB.put(3, 8310.52f); 
		besoldungsordnungB.put(4, 8794.00f); 
		besoldungsordnungB.put(5, 9348.89f); 
		besoldungsordnungB.put(6, 9876.22f);
		besoldungsordnungB.put(7, 10384.73f); 
		besoldungsordnungB.put(8, 10917.03f);
		besoldungsordnungB.put(9, 11577.13f);
		besoldungsordnungB.put(10, 13627.52f); 
		besoldungsordnungB.put(11, 14157.33f);
	}



	private void initABesoldung() {
		besoldungsordnungA = new HashMap<Integer, float[]>();
		besoldungsordnungA.put(2, new float[] {2127.35f, 2174.74f, 2223.41f, 2259.88f, 2297.58f, 2335.28f, 2372.96f, 2410.66f});
		besoldungsordnungA.put(3, new float[] {2208.82f, 2258.66f, 2308.51f, 2348.65f, 2388.77f, 2428.89f, 2469.03f, 2509.15f});
		besoldungsordnungA.put(4, new float[] {2255.04f, 2314.60f, 2374.18f, 2421.60f, 2469.03f, 2516.45f, 2563.86f, 2607.65f});
		besoldungsordnungA.put(5, new float[] {2272.03f, 2346.20f, 2405.77f, 2464.17f, 2522.54f, 2582.13f, 2640.48f, 2697.63f});
		besoldungsordnungA.put(6, new float[] {2320.67f, 2407.03f, 2494.55f, 2561.43f, 2630.75f, 2697.63f, 2771.80f, 2836.25f});
		besoldungsordnungA.put(7, new float[] {2436.20f, 2512.81f, 2613.76f, 2717.08f, 2818.01f, 2920.16f, 2996.77f, 3073.37f});
		besoldungsordnungA.put(8, new float[] {2577.25f, 2669.68f, 2799.77f, 2931.12f, 3062.42f, 3153.62f, 3246.04f, 3337.24f});
		besoldungsordnungA.put(9, new float[] {2781.52f, 2872.73f, 3016.23f, 3162.14f, 3305.60f, 3403.13f, 3504.60f, 3603.54f});
		besoldungsordnungA.put(10, new float[] {2977.30f, 3102.54f, 3283.74f, 3465.74f, 3651.12f, 3780.14f, 3909.13f, 4038.18f});
		besoldungsordnungA.put(11, new float[] {3403.13f, 3594.76f, 3785.15f, 3976.78f, 4108.29f, 4239.81f, 4371.33f, 4502.87f});
		besoldungsordnungA.put(12, new float[] {3648.64f, 3875.34f, 4103.30f, 4329.99f, 4487.82f, 4643.12f, 4799.69f, 4958.77f});
		besoldungsordnungA.put(13, new float[] {4278.65f, 4491.58f, 4703.24f, 4916.18f, 5062.73f, 5210.53f, 5357.06f, 5501.10f});
		besoldungsordnungA.put(14, new float[] {4400.14f, 4674.43f, 4950.00f, 5224.29f, 5413.41f, 5603.82f, 5792.94f, 5983.34f});
		besoldungsordnungA.put(15, new float[] {5378.35f, 5626.37f, 5815.48f, 6004.63f, 6193.77f, 6381.64f, 6569.52f, 6756.13f});
		besoldungsordnungA.put(16, new float[] {5933.22f, 6221.33f, 6439.25f, 6657.19f, 6873.89f, 7093.09f, 7311.02f, 7526.46f});
	}
	
	
	private void initAmtszulage() {
		amtszulage = new TreeMap<String, Amtszulage[]>();
		amtszulage.put("A 2", new Amtszulage[] {new Amtszulage("A 2", 41.63f, "1"), new Amtszulage("A 2", 76.79f, "2")});
		amtszulage.put("A 3", new Amtszulage[] {new Amtszulage("A 3", 41.63f, "2"), new Amtszulage("A 3", 76.79f, "4"), new Amtszulage("A 3", 38.77f, "5")});
		amtszulage.put("A 4", new Amtszulage[] {new Amtszulage("A 4", 41.63f, "1"), new Amtszulage("A 4", 76.79f, "2"), new Amtszulage("A 4", 8.37f, "4")});
		amtszulage.put("A 5", new Amtszulage[] {new Amtszulage("A 5", 41.63f, "1"),  new Amtszulage("A 5", 76.79f, "3")});
		amtszulage.put("A 6", new Amtszulage[] {new Amtszulage("A 6", 41.63f, "2")});
		amtszulage.put("A 7", new Amtszulage[] {new Amtszulage("A 7", 51.70f, "5")});
		amtszulage.put("A 8", new Amtszulage[] {new Amtszulage("A 8", 66.60f, "1")});
		amtszulage.put("A 9", new Amtszulage[] {new Amtszulage("A 9", 309.91f, "1")});
		amtszulage.put("A 13", new Amtszulage[] {new Amtszulage("A 13", 314.95f, "1"), new Amtszulage("A 13", 143.96f, "7")});
		amtszulage.put("A 14", new Amtszulage[] {new Amtszulage("A 14", 215.93f, "5")});
		amtszulage.put("A 15" ,new Amtszulage[] {new Amtszulage("A 15", 287.88f, "3"), new Amtszulage("A 15", 215.93f, "8")});
		amtszulage.put("A 16", new Amtszulage[] {new Amtszulage("A 16", 241.48f, "10")});
		amtszulage.put("B 10", new Amtszulage[] {new Amtszulage("B 10", 498.99f, "1")});
	}
	
	
	private void initFamZuschlag() {
		this.famZuschlagStufe1 = 143.34f;
		this.famZuschlagStufe2 = 265.87f;
		this.famZuschlagZweitesKind = 122.53f;
		this.famZuschlagWeitereKinder = 381.77f;    
		
	}


}
