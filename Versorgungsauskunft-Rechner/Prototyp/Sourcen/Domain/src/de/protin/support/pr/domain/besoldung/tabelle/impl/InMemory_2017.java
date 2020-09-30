package de.protin.support.pr.domain.besoldung.tabelle.impl;

import java.util.HashMap;
import java.util.TreeMap;

import de.protin.support.pr.domain.besoldung.Amtszulage;
import de.protin.support.pr.domain.besoldung.tabelle.AbstractBesoldungstabelle;
import de.protin.support.pr.domain.utils.DateUtil;

public class InMemory_2017 extends AbstractBesoldungstabelle {

	
	
	public InMemory_2017() {
		setTitel("Besoldungstabelle Bund");
		setSubTitel("Besoldungstabelle für Beamtinnen und Beamte des Bundes (Gültig vom 01.02.2017 - 28.02.2018)");
		setValidFrom(DateUtil.getDate(new String("01.02.2017")));
		setValidTo(DateUtil.getDate(new String("28.02.2018")));
		
		initABesoldung();
		initBBesoldung();
		initFamZuschlag();
		initAmtszulage();
		initKindererziehungszuschlag(DateUtil.getDate(new String("01.02.2017")));
	}
	

	
	private void initBBesoldung() {
		besoldungsordnungB = new TreeMap<Integer, Float>();
		besoldungsordnungB.put(1, 6559.99f);
		besoldungsordnungB.put(2, 7620.51f);
		besoldungsordnungB.put(3, 8069.25f);
		besoldungsordnungB.put(4, 8538.69f);
		besoldungsordnungB.put(5, 9077.47f);
		besoldungsordnungB.put(6, 9589.49f);
		besoldungsordnungB.put(7, 10083.24f);
		besoldungsordnungB.put(8, 10600.09f);
		besoldungsordnungB.put(9, 11241.02f);
		besoldungsordnungB.put(10, 13231.89f);
		besoldungsordnungB.put(11, 13746.32f);
	}



	private void initABesoldung() {
		besoldungsordnungA = new HashMap<Integer, float[]>();
		besoldungsordnungA.put(2, new float[] {2065.59f, 2111.60f, 2158.86f, 2194.27f, 2230.88f, 2267.48f, 2304.07f, 2340.67f});
		besoldungsordnungA.put(3, new float[] {2144.69f, 2193.09f, 2241.49f, 2280.46f, 2319.42f, 2358.37f, 2397.35f, 2436.30f});
		besoldungsordnungA.put(4, new float[] {2189.57f, 2247.40f, 2305.25f, 2351.30f, 2397.35f, 2443.39f, 2489.43f, 2531.94f});
		besoldungsordnungA.put(5, new float[] {2206.07f, 2278.09f, 2335.93f, 2392.63f, 2449.31f, 2507.17f, 2563.82f, 2619.31f});
		besoldungsordnungA.put(6, new float[] {2253.30f, 2337.15f, 2422.13f, 2487.07f, 2554.37f, 2619.31f, 2691.33f, 2753.91f});
		besoldungsordnungA.put(7, new float[] {2365.47f, 2439.86f, 2537.88f, 2638.20f, 2736.20f, 2835.38f, 2909.77f, 2984.14f});
		besoldungsordnungA.put(8, new float[] {2502.43f, 2592.17f, 2718.49f, 2846.02f, 2973.51f, 3062.06f, 3151.80f, 3240.35f});
		besoldungsordnungA.put(9, new float[] {2700.77f, 2789.33f, 2928.66f, 3070.34f, 3209.63f, 3304.33f, 3402.85f, 3498.92f});
		besoldungsordnungA.put(10, new float[] {2890.86f, 3012.47f, 3188.41f, 3365.12f, 3545.12f, 3670.40f, 3795.64f, 3920.94f});
		besoldungsordnungA.put(11, new float[] {3304.33f, 3490.40f, 3675.26f, 3861.33f, 3989.02f, 4116.72f, 4244.42f, 4372.14f});
		besoldungsordnungA.put(12, new float[] {3542.71f, 3762.83f, 3984.17f, 4204.28f, 4357.53f, 4508.32f, 4660.35f, 4814.81f});
		besoldungsordnungA.put(13, new float[] {4154.43f, 4361.18f, 4566.70f, 4773.45f, 4915.75f, 5059.26f, 5201.53f, 5341.39f});
		besoldungsordnungA.put(14, new float[] {4272.40f, 4538.72f, 4806.29f, 5072.62f, 5256.25f, 5441.13f, 5624.76f, 5809.63f});
		besoldungsordnungA.put(15, new float[] {5222.21f, 5463.03f, 5646.65f, 5830.30f, 6013.95f, 6196.37f, 6378.79f, 6559.99f});
		besoldungsordnungA.put(16, new float[] {5760.97f, 6040.71f, 6252.31f, 6463.92f, 6674.33f, 6887.16f, 7098.77f, 7307.95f});
	}
	
	
	private void initAmtszulage() {
		amtszulage = new TreeMap<String, Amtszulage[]>();
		amtszulage.put("A 2", new Amtszulage[] {new Amtszulage("A 2", 40.42f, "1"), new Amtszulage("A 2", 74.56f, "2")});
		amtszulage.put("A 3", new Amtszulage[] {new Amtszulage("A 3", 40.42f, "1"), new Amtszulage("A 3", 74.56f, "4"), new Amtszulage("A 3", 37.64f, "5")});
		amtszulage.put("A 4", new Amtszulage[] {new Amtszulage("A 4", 40.42f, "1"), new Amtszulage("A 4", 74.56f, "2"), new Amtszulage("A 4", 8.13f, "4")});
		amtszulage.put("A 5", new Amtszulage[] {new Amtszulage("A 5", 40.42f, "1"),  new Amtszulage("A 5", 74.56f, "3")});
		amtszulage.put("A 6", new Amtszulage[] {new Amtszulage("A 6", 40.42f, "2")});
		amtszulage.put("A 7", new Amtszulage[] {new Amtszulage("A 7", 50.20f, "5")});
		amtszulage.put("A 8", new Amtszulage[] {new Amtszulage("A 8", 64.67f, "1")});
		amtszulage.put("A 9", new Amtszulage[] {new Amtszulage("A 9", 300.91f, "1")});
		amtszulage.put("A 13", new Amtszulage[] {new Amtszulage("A 13", 305.81f, "1"), new Amtszulage("A 13", 139.78f, "7")});
		amtszulage.put("A 14", new Amtszulage[] {new Amtszulage("A 14", 209.66f, "5")});
		amtszulage.put("A 15" ,new Amtszulage[] {new Amtszulage("A 15", 279.52f, "3"), new Amtszulage("A 15", 209.66f, "8")});
		amtszulage.put("A 16", new Amtszulage[] {new Amtszulage("A 16", 234.47f, "10")});
		amtszulage.put("B 10", new Amtszulage[] {new Amtszulage("B 10", 484.50f, "1")});
	}
	
	
	private void initFamZuschlag() {
		this.famZuschlagStufe1 = 139.18f;
		this.famZuschlagStufe2 = 258.15f;
		this.famZuschlagZweitesKind = 118.97f;
		this.famZuschlagWeitereKinder = 370.69f;    
		
	}


}
