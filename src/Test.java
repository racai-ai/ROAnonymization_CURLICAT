import java.util.ArrayList;
import roanon.AnonymizationModel;
import roanon.CSVAssoc;
import roanon.ExternalNER;
import roanon.NERList;

public class Test {

	private static final String text="Se promulgă Legea privind respingerea Ordonanței de urgență a Guvernului nr. 75/2018 pentru modificarea și completarea unor acte normative în domeniul protecției mediului și al regimului străinilor și se dispune publicarea acestei legi în Monitorul Oficial al României , Partea I.\r\n" + 
			"ORDIN nr. 823 din 27 mai 2021 privind numirea reprezentanților părții române în cadrul organismelor create în temeiul tratatelor bilaterale privind gospodărirea apelor de frontieră\r\n" + 
			"EMITENT\r\n" + 
			"MINISTERUL MEDIULUI , APELOR ȘI PĂDURILOR\r\n" + 
			"Publicat în  MONITORUL OFICIAL nr. 573 din 7 iunie 2021\r\n" + 
			"\r\n" + 
			"Având în vedere Referatul de aprobare nr. DMRA/192.653/5.04.2021 al Direcției managementul resurselor de apă ,\r\n" + 
			"luând în considerare prevederile Acordului dintre Guvernul României și Guvernul Republicii Serbia privind cooperarea în domeniul gospodăririi durabile a apelor transfrontaliere , semnat la București la 5 iunie 2019, aprobat prin Hotărârea Guvernului nr. 725/2020, prevederile Acordului dintre Guvernul României și Guvernul Ucrainei privind cooperarea în domeniul gospodăririi apelor de frontieră, semnat la Galați la 30 septembrie 1997, ratificat prin Legea nr. 16/1999 , prevederile Acordului dintre Guvernul României și Guvernul Republicii Ungare privind colaborarea pentru protecția și utilizarea durabilă a apelor de frontieră, semnat la Budapesta la 15 septembrie 2003 , aprobat prin Hotărârea Guvernului nr. 577/2004 , prevederile Acordului dintre Guvernul României și Guvernul Republicii Moldova privind cooperarea pentru protecția și utilizarea durabilă a apelor Prutului și Dunării, semnat la Chișinău la 28 iunie 2010 , aprobat prin Hotărârea Guvernului nr. 1.092/2010 , în temeiul art. 2 alin. (1) din Hotărârea Guvernului nr. 1.079/2010 pentru reprezentarea în cadrul organismelor create în temeiul tratatelor bilaterale privind gospodărirea apelor de frontieră , al art. 57 alin. (1), (4) și (5) din Ordonanța de urgență a Guvernului nr. 57/2019 privind Codul administrativ, cu modificările și completările ulterioare, precum și al art. 13 alin. (4) din Hotărârea Guvernului nr. 43/2020 privind organizarea și funcționarea Ministerului Mediului , Apelor și Pădurilor ,\r\n" + 
			"ministrul mediului, apelor și pădurilor emite prezentul ordin .";
	
	private static final String textNER="{\"result\":[{\"end\":72,\"id\":\"T1\",\"start\":62,\"text\":\"Guvernului\",\"type\":\"ORG\"},{\"end\":268,\"id\":\"T2\",\"start\":239,\"text\":\"Monitorul Oficial al Rom\\u00e2niei\",\"type\":\"ORG\"},{\"end\":311,\"id\":\"T3\",\"start\":300,\"text\":\"27 mai 2021\",\"type\":\"TIME\"},{\"end\":514,\"id\":\"T4\",\"start\":473,\"text\":\"MINISTERUL MEDIULUI , APELOR \\u0218I P\\u0102DURILOR\",\"type\":\"ORG\"},{\"end\":546,\"id\":\"T5\",\"start\":529,\"text\":\"MONITORUL OFICIAL\",\"type\":\"ORG\"},{\"end\":571,\"id\":\"T6\",\"start\":559,\"text\":\"7 iunie 2021\",\"type\":\"TIME\"},{\"end\":639,\"id\":\"T7\",\"start\":617,\"text\":\"DMRA/192.653/5.04.2021\",\"type\":\"TIME\"},{\"end\":745,\"id\":\"T8\",\"start\":737,\"text\":\"Guvernul\",\"type\":\"ORG\"},{\"end\":754,\"id\":\"T9\",\"start\":746,\"text\":\"Rom\\u00e2niei\",\"type\":\"LOC\"},{\"end\":766,\"id\":\"T10\",\"start\":758,\"text\":\"Guvernul\",\"type\":\"ORG\"},{\"end\":784,\"id\":\"T11\",\"start\":767,\"text\":\"Republicii Serbia\",\"type\":\"LOC\"},{\"end\":885,\"id\":\"T12\",\"start\":876,\"text\":\"Bucure\\u0219ti\",\"type\":\"LOC\"},{\"end\":901,\"id\":\"T13\",\"start\":889,\"text\":\"5 iunie 2019\",\"type\":\"TIME\"},{\"end\":936,\"id\":\"T14\",\"start\":926,\"text\":\"Guvernului\",\"type\":\"ORG\"},{\"end\":988,\"id\":\"T15\",\"start\":980,\"text\":\"Guvernul\",\"type\":\"ORG\"},{\"end\":997,\"id\":\"T16\",\"start\":989,\"text\":\"Rom\\u00e2niei\",\"type\":\"LOC\"},{\"end\":1009,\"id\":\"T17\",\"start\":1001,\"text\":\"Guvernul\",\"type\":\"ORG\"},{\"end\":1100,\"id\":\"T18\",\"start\":1094,\"text\":\"Gala\\u021bi\",\"type\":\"LOC\"},{\"end\":1122,\"id\":\"T19\",\"start\":1104,\"text\":\"30 septembrie 1997\",\"type\":\"TIME\"},{\"end\":1196,\"id\":\"T20\",\"start\":1188,\"text\":\"Guvernul\",\"type\":\"ORG\"},{\"end\":1205,\"id\":\"T21\",\"start\":1197,\"text\":\"Rom\\u00e2niei\",\"type\":\"LOC\"},{\"end\":1217,\"id\":\"T22\",\"start\":1209,\"text\":\"Guvernul\",\"type\":\"ORG\"},{\"end\":1235,\"id\":\"T23\",\"start\":1218,\"text\":\"Republicii Ungare\",\"type\":\"LOC\"},{\"end\":1338,\"id\":\"T24\",\"start\":1329,\"text\":\"Budapesta\",\"type\":\"LOC\"},{\"end\":1360,\"id\":\"T25\",\"start\":1342,\"text\":\"15 septembrie 2003\",\"type\":\"TIME\"},{\"end\":1396,\"id\":\"T26\",\"start\":1386,\"text\":\"Guvernului\",\"type\":\"ORG\"},{\"end\":1449,\"id\":\"T27\",\"start\":1441,\"text\":\"Guvernul\",\"type\":\"ORG\"},{\"end\":1458,\"id\":\"T28\",\"start\":1450,\"text\":\"Rom\\u00e2niei\",\"type\":\"LOC\"},{\"end\":1470,\"id\":\"T29\",\"start\":1462,\"text\":\"Guvernul\",\"type\":\"ORG\"},{\"end\":1489,\"id\":\"T30\",\"start\":1471,\"text\":\"Republicii Moldova\",\"type\":\"LOC\"},{\"end\":1577,\"id\":\"T31\",\"start\":1570,\"text\":\"Dun\\u0103rii\",\"type\":\"LOC\"},{\"end\":1597,\"id\":\"T32\",\"start\":1589,\"text\":\"Chi\\u0219in\\u0103u\",\"type\":\"LOC\"},{\"end\":1614,\"id\":\"T33\",\"start\":1601,\"text\":\"28 iunie 2010\",\"type\":\"TIME\"},{\"end\":1650,\"id\":\"T34\",\"start\":1640,\"text\":\"Guvernului\",\"type\":\"ORG\"},{\"end\":1720,\"id\":\"T35\",\"start\":1710,\"text\":\"Guvernului\",\"type\":\"ORG\"},{\"end\":1933,\"id\":\"T36\",\"start\":1923,\"text\":\"Guvernului\",\"type\":\"ORG\"},{\"end\":2074,\"id\":\"T37\",\"start\":2064,\"text\":\"Guvernului\",\"type\":\"ORG\"},{\"end\":2166,\"id\":\"T38\",\"start\":2123,\"text\":\"Ministerului Mediului , Apelor \\u0219i P\\u0103durilor\",\"type\":\"ORG\"}],\"status\":\"OK\"}\r\n" + 
			"";
	
	public static void main(String[] args) {
		String dbpath=".";
		ExternalNER.testNER=textNER;
		
        System.out.println("Loading resources from ["+dbpath+"]");
        AnonymizationModel model=new AnonymizationModel("",true);
        model.ne=NERList.loadGazetteer(dbpath+"/ne.1.gazetteer.gz");
        model.wordForm=CSVAssoc.loadResource(dbpath+"/n1.csv.gz", '\t');
        
        //model.ne=new HashMap<>();
        //model.wordForm=new HashMap<>();

        System.out.println("TEXT:");
        System.out.println(text);
        
        ArrayList<String> mappings=new ArrayList<>();
        String ret=model.anonymize(text, mappings);
        
        System.out.println("\n\nREZULTAT:");
        System.out.println(ret);
        for(String s:mappings) {
        	System.out.println(s);
        }
	}

}
