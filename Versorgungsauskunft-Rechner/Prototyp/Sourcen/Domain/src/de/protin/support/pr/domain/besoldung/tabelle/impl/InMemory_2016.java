package de.protin.support.pr.domain.besoldung.tabelle.impl;

import java.util.HashMap;
import java.util.TreeMap;

import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.tabelle.AbstractBesoldungstabelle;
import de.protin.support.pr.domain.utils.DateUtil;
import de.protin.support.pr.domain.utils.PrintUtils;

public class InMemory_2016 extends AbstractBesoldungstabelle {

	
	
	public InMemory_2016() {
		setTitel("Besoldungstabelle Bund");
		setSubTitel("Besoldungstabelle für Beamtinnen und Beamte des Bundes (Gültig vom 01.03.2016 - 31.01.2017)");
		setValidFrom(DateUtil.getDate(new String("01.03.2016")));
		setValidTo(DateUtil.getDate(new String("31.01.2017")));
		
		initABesoldung();
		initBBesoldung();
		initFamZuschlag();
		initAmtszulage();
		initKindererziehungszuschlag(DateUtil.getDate(new String("01.03.2016")));
	}
	

	
	private void initBBesoldung() {
		besoldungsordnungB = new TreeMap<Integer, Float>();
		besoldungsordnungB.put(1, 6409.37f);
		besoldungsordnungB.put(2, 7445.54f);
		besoldungsordnungB.put(3, 7883.9f);
		besoldungsordnungB.put(4, 8342.64f);
		besoldungsordnungB.put(5, 8869.05f);
		besoldungsordnungB.put(6, 9369.31f);
		besoldungsordnungB.put(7, 9851.72f);
		besoldungsordnungB.put(8, 10356.71f);
		besoldungsordnungB.put(9, 10982.92f);
		besoldungsordnungB.put(10, 12928.08f);
		besoldungsordnungB.put(11, 13430.70f);
	}



	private void initABesoldung() {
		besoldungsordnungA = new HashMap<Integer, float[]>();
		besoldungsordnungA.put(2, new float[] {2018.16f, 2063.12f, 2109.29f, 2143.89f, 2179.66f, 2215.42f, 2251.17f, 2286.93f});
		besoldungsordnungA.put(3, new float[] {2095.45f, 2142.74f, 2190.02f, 2228.10f, 2266.17f, 2304.22f, 2342.31f, 2380.36f});
		besoldungsordnungA.put(4, new float[] {2139.30f, 2195.80f, 2252.32f, 2297.31f, 2342.31f, 2387.29f, 2432.27f, 2473.81f});
		besoldungsordnungA.put(5 ,new float[] {2155.42f, 2225.78f, 2282.30f, 2337.69f, 2393.07f, 2449.60f, 2504.95f, 2559.17f});
		besoldungsordnungA.put(6, new float[] {2201.56f, 2283.49f, 2366.52f, 2429.97f, 2495.72f, 2559.17f, 2629.54f, 2690.68f});
		besoldungsordnungA.put(7, new float[] {2311.16f, 2383.84f, 2479.61f, 2577.63f, 2673.38f, 2770.28f, 2842.96f, 2915.62f});
		besoldungsordnungA.put(8, new float[] {2444.97f, 2532.65f, 2656.07f, 2780.67f, 2905.24f, 2991.75f, 3079.43f, 3165.95f});
		besoldungsordnungA.put(9, new float[] {2638.76f, 2725.29f, 2861.42f, 2999.84f, 3135.94f, 3228.46f, 3324.72f, 3418.58f});
		besoldungsordnungA.put(10, new float[] {2824.48f, 2943.30f, 3115.20f, 3287.86f, 3463.72f, 3586.13f, 3708.49f, 3830.91f});
		besoldungsordnungA.put(11, new float[] {3228.46f, 3410.26f, 3590.87f, 3772.67f, 3897.43f, 4022.20f, 4146.97f, 4271.75f});
		besoldungsordnungA.put(12, new float[] {3461.37f, 3676.43f, 3892.69f, 4107.75f, 4257.48f, 4404.81f, 4553.35f, 4704.26f});
		besoldungsordnungA.put(13, new float[] {4059.04f, 4261.05f, 4461.85f, 4663.85f, 4802.88f, 4943.10f, 5082.10f, 5218.75f});
		besoldungsordnungA.put(14, new float[] {4174.30f, 4434.51f, 4695.94f, 4956.15f, 5135.56f, 5316.20f, 5495.61f, 5676.24f});
		besoldungsordnungA.put(15, new float[] {5102.31f, 5337.60f, 5517.00f, 5696.43f, 5875.87f, 6054.10f, 6232.33f, 6409.37f});
		besoldungsordnungA.put(16, new float[] {5628.70f, 5902.01f, 6108.75f, 6315.51f, 6521.08f, 6729.03f, 6935.78f, 7140.16f});
	}
	
	
	private void initAmtszulage() {
		amtszulage = new TreeMap<String, Amtszulage[]>();
		amtszulage.put("A 2", new Amtszulage[] {new Amtszulage("A 2", 39.49f, "1"), new Amtszulage("A 2", 72.85f, "2")});
		amtszulage.put("A 3", new Amtszulage[] {new Amtszulage("A 3", 39.49f, "2"), new Amtszulage("A 3", 72.85f, "4"), new Amtszulage("A 3", 36.78f, "5")});
		amtszulage.put("A 4", new Amtszulage[] {new Amtszulage("A 4", 39.49f, "1"), new Amtszulage("A 4", 72.85f, "2"), new Amtszulage("A 4", 7.94f, "4")});
		amtszulage.put("A 5", new Amtszulage[] {new Amtszulage("A 5", 39.49f, "1"),  new Amtszulage("A 5", 72.85f, "3")});
		amtszulage.put("A 6", new Amtszulage[] {new Amtszulage("A 6", 39.49f, "2")});
		amtszulage.put("A 7", new Amtszulage[] {new Amtszulage("A 7", 49.05f, "5")});
		amtszulage.put("A 8", new Amtszulage[] {new Amtszulage("A 8", 63.19f, "1")});
		amtszulage.put("A 9", new Amtszulage[] {new Amtszulage("A 9", 294.00f, "1")});
		amtszulage.put("A 13", new Amtszulage[] {new Amtszulage("A 13", 298.79f, "1"), new Amtszulage("A 13", 136.57f, "7")});
		amtszulage.put("A 14", new Amtszulage[] {new Amtszulage("A 14", 204.85f, "5")});
		amtszulage.put("A 15" ,new Amtszulage[] {new Amtszulage("A 15", 273.10f, "3"), new Amtszulage("A 15", 204.85f, "8")});
		amtszulage.put("A 16", new Amtszulage[] {new Amtszulage("A 16", 229.09f, "10")});
		amtszulage.put("B 10", new Amtszulage[] {new Amtszulage("B 10", 473.38f, "1")});
	}
	
	
	private void initFamZuschlag() {
		this.famZuschlagStufe1 = 135.98f;
		this.famZuschlagStufe2 = 252.22f;
		this.famZuschlagZweitesKind = 116.24f;
		this.famZuschlagWeitereKinder = 362.18f;    
		
	}


}
