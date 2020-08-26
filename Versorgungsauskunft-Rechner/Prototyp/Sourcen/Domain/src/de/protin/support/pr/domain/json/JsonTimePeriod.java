package de.protin.support.pr.domain.json;

import de.protin.support.pr.domain.ITimePeriod;
import de.protin.support.pr.domain.pension.AntragsRuhestand;
import de.protin.support.pr.domain.pension.Dienstunfaehigkeit;
import de.protin.support.pr.domain.pension.EngagierterRuhestand;
import de.protin.support.pr.domain.pension.RegelaltersgrenzeRuhestand;
import de.protin.support.pr.domain.pension.Vorruhestand;
import de.protin.support.pr.domain.timeperiod.AufbauhilfeOst;
import de.protin.support.pr.domain.timeperiod.BaseTimePeriod;
import de.protin.support.pr.domain.timeperiod.Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst;
import de.protin.support.pr.domain.timeperiod.Para_11_SonstigeZeiten;
import de.protin.support.pr.domain.timeperiod.Para_12_FoerderlichePraktischeAusbildung;
import de.protin.support.pr.domain.timeperiod.Para_12_FoerderlichePraktischeHauptberuflicheTaetigkeit;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebeneFachschulzeiten;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebeneHochschulstudienzeiten;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebenePraktischeAusbildung;
import de.protin.support.pr.domain.timeperiod.Para_12_VorgeschriebenePraktischeHauptberuflicheTaetigkeit;
import de.protin.support.pr.domain.timeperiod.Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima;
import de.protin.support.pr.domain.timeperiod.Para_13_Zurechnungszeit_DDU;
import de.protin.support.pr.domain.timeperiod.Para_6_AltersteilzeitBlockmodel;
import de.protin.support.pr.domain.timeperiod.Para_6_AltersteilzeitTeilzeitmodel;
import de.protin.support.pr.domain.timeperiod.Para_6_AusbildungBeamterAufWiderruf;
import de.protin.support.pr.domain.timeperiod.Para_6_Beamterdienstzeit;
import de.protin.support.pr.domain.timeperiod.Para_8_9_WehrdienstOderZivildienst;
import de.protin.support.pr.domain.timeperiod.UOB_Ruhegehaltsfaehig;

public class JsonTimePeriod extends BaseTimePeriod {

	public static final String ZEITART = "JSON Wrapper";
	
	public JsonTimePeriod() {
		super(999, null, null, 1.0f);
	}
	
	public String getZeitart() {
		return ZEITART;
	}
	
	public ITimePeriod getDomainObject() {
		ITimePeriod result = null;

		switch(this.type) {
	    	case ITimePeriod.BEAMTEN_DIENSTZEIT_RUHEGEHALTSFAEHIG_PARA_6:
	    		result = new Para_6_Beamterdienstzeit(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.AUSBILDUNG_BEAMTER_WIDERRUF_PARA_6:
	    		result = new Para_6_AusbildungBeamterAufWiderruf(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.BUNDESWEHR_ODER_ZIVILDIENST_PARA_8_9:
	    		result = new Para_8_9_WehrdienstOderZivildienst(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.ANGESTELLTENVERHAELTNIS_IM_OEFFENTLICHEN_DIENST_PARA_10:
	    		result = new Para_10_VordienstzeitenAlsArbeitnehmerImOeffentlichenDienst(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.UOB_RUHEGEHALTSFAEHIG:
	    		result = new UOB_Ruhegehaltsfaehig(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.ALTERSTEILZEIT_BLOCKMODELL:
	    		result = new Para_6_AltersteilzeitBlockmodel(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.ALTERSTEILZEIT_TEILZEITMODELL:
	    		result = new Para_6_AltersteilzeitTeilzeitmodel(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.AUFBAUHILFE_NEUE_BUNDESLAENDER:
	    		result = new AufbauhilfeOst(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.VERWENDUNG_IN_LAENDERN_MIT_GESUNDHEITSSCHAEDLICHEM_KLIMA_PARA_13:
	    		result = new Para_13_VerwendungInLaendernMitGesundheitsschaedlichenKlima(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.SONSTIGE_ZEITEN_PARA_11:
	    		result = new Para_11_SonstigeZeiten(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.VORGESCHR_FACHSCHULZEITEN_PARA_12_1_1:
	    		result = new Para_12_VorgeschriebeneFachschulzeiten(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.VORGESCHR_HOCHSCHULSTUDIEN_ZEITEN_PARA_12_1_1:
	    		result = new Para_12_VorgeschriebeneHochschulstudienzeiten(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.VORGESCHR_PRAKTISCHE_AUSBILDUNG_PARA_12_1_1:
	    		result = new Para_12_VorgeschriebenePraktischeAusbildung(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.VORGESCHR_PRAKTISCHE_HAUPTBERUFLICHE_TAETIGKEIT_PARA_12_1_2:
	    		result = new Para_12_VorgeschriebenePraktischeHauptberuflicheTaetigkeit(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.FOERDERLICHE_PRAKTISCHE_AUSBILDUNG_PARA_12_2:
	    		result = new Para_12_FoerderlichePraktischeAusbildung(this.startDate, this.endDate, this.factor);
	    		break;
	    	case ITimePeriod.FOERDERLICHE_PRAKTISCHE_HAUPTBERUFLICHE_TAETIGKEIT_PARA_12_2:
	    		result = new Para_12_FoerderlichePraktischeHauptberuflicheTaetigkeit(this.startDate, this.endDate, this.factor);
	    		break;
	    	
	    	default:
	    		break;
	    }
		
		result.setRuhegehaltsfaehigeTage(this.getRuhegehaltsfaehigeTage());
		result.setComments(this.getComments());
		return result;
	}

}