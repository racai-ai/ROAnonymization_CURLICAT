import java.util.ArrayList;
import roanon.AnonymizationModel;
import roanon.CSVAssoc;
import roanon.ExternalNER;
import roanon.NERList;

public class Test2 {

	private static final String text="Nu mai puțin de 10 gloanțe au fost trase miercuri asupra mașinii în care se afla consilierul președintelui Ucrainei Volodimir Zelenski, într-o tentativă de asasinat, șoferul autoturismului fiind rănit, potrivit poliției.";
	
	private static final String textNER="{\"result\":[],\"status\":\"OK\"}";
	
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
