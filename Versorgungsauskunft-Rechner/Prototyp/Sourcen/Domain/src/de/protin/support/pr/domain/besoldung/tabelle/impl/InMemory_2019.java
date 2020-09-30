package de.protin.support.pr.domain.besoldung.tabelle.impl;

import java.util.HashMap;
import java.util.TreeMap;

import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.tabelle.AbstractBesoldungstabelle;
import de.protin.support.pr.domain.utils.DateUtil;

public class InMemory_2019 extends AbstractBesoldungstabelle {

	
	
	public InMemory_2019() {
		setTitel("Besoldungstabelle Bund");
		setSubTitel("Besoldungstabelle für Beamtinnen und Beamte des Bundes (Gültig vom 01.04.2019 - 29.02.2020)");
		setValidFrom(DateUtil.getDate(new String("01.04.2019")));
		setValidTo(DateUtil.getDate(new String("29.02.2020")));
		
		initABesoldung();
		initBBesoldung();
		initFamZuschlag();
		initAmtszulage();
		initKindererziehungszuschlag(DateUtil.getDate(new String("01.04.2019")));
	}
	

	
	
	private void initBBesoldung() {
		besoldungsordnungB = new TreeMap<Integer, Float>();
		besoldungsordnungB.put(1, 6964.89f);
		besoldungsordnungB.put(2, 8090.87f); 
		besoldungsordnungB.put(3, 8567.32f); 
		besoldungsordnungB.put(4, 9065.73f); 
		besoldungsordnungB.put(5, 9637.77f); 
		besoldungsordnungB.put(6, 10181.40f);
		besoldungsordnungB.put(7, 10705.62f); 
		besoldungsordnungB.put(8, 11254.37f);
		besoldungsordnungB.put(9, 11934.86f);
		besoldungsordnungB.put(10,14048.61f); 
		besoldungsordnungB.put(11, 14594.79f);
	}



	private void initABesoldung() {
		besoldungsordnungA = new HashMap<Integer, float[]>();
		besoldungsordnungA.put(2, new float[] {2193.09f, 2241.94f, 2292.11f, 2329.71f, 2368.58f, 2407.44f, 2446.28f, 2485.15f});
		besoldungsordnungA.put(3, new float[] {2277.07f, 2328.45f, 2379.84f, 2421.22f, 2462.58f, 2503.94f, 2545.32f, 2586.68f});
		besoldungsordnungA.put(4, new float[] {2324.72f, 2386.12f, 2447.54f, 2496.43f, 2545.32f, 2594.21f, 2643.08f, 2688.23f});
		besoldungsordnungA.put(5, new float[] {2342.24f, 2418.70f, 2480.11f, 2540.31f, 2600.49f, 2661.92f, 2722.07f, 2780.99f});
		besoldungsordnungA.put(6, new float[] {2392.38f, 2481.41f, 2571.63f, 2640.58f, 2712.04f, 2780.99f, 2857.45f, 2923.89f});
		besoldungsordnungA.put(7, new float[] {2511.48f, 2590.46f, 2694.53f, 2801.04f, 2905.09f, 3010.39f, 3089.37f, 3168.34f});
		besoldungsordnungA.put(8, new float[] {2656.89f, 2752.17f, 2886.28f, 3021.69f, 3157.05f, 3251.07f, 3346.34f, 3440.36f});
		besoldungsordnungA.put(9, new float[] {2867.47f, 2961.50f, 3109.43f, 3259.85f, 3407.74f, 3508.29f, 3612.89f, 3714.89f});
		besoldungsordnungA.put(10, new float[] {3069.30f, 3198.41f, 3385.21f, 3572.83f, 3763.94f, 3896.95f, 4029.92f, 4162.96f});
		besoldungsordnungA.put(11, new float[] {3508.29f, 3705.84f, 3902.11f, 4099.66f, 4235.24f, 4370.82f, 4506.40f, 4642.01f});
		besoldungsordnungA.put(12, new float[] {3761.38f, 3995.09f, 4230.09f, 4463.79f, 4626.49f, 4786.59f, 4948.00f, 5112.00f});
		besoldungsordnungA.put(13, new float[] {4410.86f, 4630.37f, 4848.57f, 5068.09f, 5219.17f, 5371.54f, 5522.59f, 5671.08f});
		besoldungsordnungA.put(14, new float[] {4536.10f, 4818.87f, 5102.96f, 5385.72f, 5580.68f, 5776.98f, 5971.94f, 6168.23f});
		besoldungsordnungA.put(15, new float[] {5544.54f, 5800.22f, 5995.18f, 6190.17f, 6385.16f, 6578.83f, 6772.52f, 6964.89f});
		besoldungsordnungA.put(16, new float[] {6116.56f, 6413.57f, 6638.22f, 6862.90f, 7086.29f, 7312.27f, 7536.93f, 7759.03f});
	}
	
	
	private void initAmtszulage() {
		amtszulage = new TreeMap<String, Amtszulage[]>();
		amtszulage.put("A 2", new Amtszulage[] {new Amtszulage("A 2", 42.92f, "1"), new Amtszulage("A 2", 79.16f, "2")});
		amtszulage.put("A 3", new Amtszulage[] {new Amtszulage("A 3", 42.92f, "2"), new Amtszulage("A 3", 79.16f, "4"), new Amtszulage("A 3", 39.97f, "5")});
		amtszulage.put("A 4", new Amtszulage[] {new Amtszulage("A 4", 42.92f, "1"), new Amtszulage("A 4", 79.16f, "2"), new Amtszulage("A 4", 8.63f, "4")});
		amtszulage.put("A 5", new Amtszulage[] {new Amtszulage("A 5", 42.92f, "1"),  new Amtszulage("A 5", 79.16f, "3")});
		amtszulage.put("A 6", new Amtszulage[] {new Amtszulage("A 6", 42.92f, "2")});
		amtszulage.put("A 7", new Amtszulage[] {new Amtszulage("A 7", 53.30f, "5")});
		amtszulage.put("A 8", new Amtszulage[] {new Amtszulage("A 8", 68.66f, "1")});
		amtszulage.put("A 9", new Amtszulage[] {new Amtszulage("A 9", 319.49f, "1")});
		amtszulage.put("A 13", new Amtszulage[] {new Amtszulage("A 13", 324.68f, "1"), new Amtszulage("A 13", 148.41f, "7")});
		amtszulage.put("A 14", new Amtszulage[] {new Amtszulage("A 14", 222.60f, "5")});
		amtszulage.put("A 15" ,new Amtszulage[] {new Amtszulage("A 15", 296.78f, "3"), new Amtszulage("A 15", 222.60f, "8")});
		amtszulage.put("A 16", new Amtszulage[] {new Amtszulage("A 16", 248.94f, "10")});
		amtszulage.put("B 10", new Amtszulage[] {new Amtszulage("B 10", 514.41f, "1")});
	}
	
	
	private void initFamZuschlag() {
		this.famZuschlagStufe1 = 147.78f;
		this.famZuschlagStufe2 = 274.10f;
		this.famZuschlagZweitesKind = 126.32f;
		this.famZuschlagWeitereKinder = 393.57f;    
		
	}
	

}
