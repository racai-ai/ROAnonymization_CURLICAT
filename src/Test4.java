import java.util.ArrayList;
import roanon.AnonymizationModel;
import roanon.CSVAssoc;
import roanon.ExternalNER;
import roanon.NERList;

public class Test4 {

	private static final String text="Washingtonul a criticat luni condiţiile în care s-au desfăşurat alegerile legislative din Rusia, câştigate de partidul preşedintelui Vladimir Putin, Statele Unite considerând că cetăţenii ruşi au fost „împiedicaţi să-şi exercite drepturile civice”, scrie Agerpres. Și Comisia Europeană consideră că acest scrutin s-a desfăşurat într-o „atmosferă de intimidare”.\r\n" + 
			"\r\n" + 
			"„Alegerile legislative din 17-19 septembrie în Federaţia Rusă s-au desfăşurat în condiţii care nu au fost propice procedurilor libere şi echitabile”, a transmis într-un comunicat purtătorul de cuvânt al diplomaţiei americane, Ned Price.";
	
	private static final String textNER="{\"result\":[{\"end\":95,\"id\":\"T1\",\"start\":90,\"text\":\"Rusia\",\"type\":\"TIME\"},{\"end\":285,\"id\":\"T2\",\"start\":268,\"text\":\"Comisia European\\u0103\",\"type\":\"ORG\"},{\"end\":408,\"id\":\"T3\",\"start\":392,\"text\":\"17-19 septembrie\",\"type\":\"TIME\"}],\"status\":\"OK\"}\r\n" + 
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
