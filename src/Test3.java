import java.util.ArrayList;
import roanon.AnonymizationModel;
import roanon.CSVAssoc;
import roanon.ExternalNER;
import roanon.NERList;

public class Test3 {

	private static final String text="În scenă în schimb au intrat strategic Marea Britanie și Statele Unite, cu un puternic interes în zona Pacificului și care oferă Australiei acces la tehnologia submarinelor nucleare.";
	
	private static final String textNER="{\"result\":[],\"status\":\"OK\"}";
	
	public static void main(String[] args) {
		String dbpath=".";
		ExternalNER.testNER=textNER;
		
        System.out.println("Loading resources from ["+dbpath+"]");
        AnonymizationModel model=new AnonymizationModel("",true);
        model.ne=NERList.loadGazetteer(dbpath+"/ne.1.gazetteer.gz");
        model.wordForm=CSVAssoc.loadResource(dbpath+"/n1.csv.gz", '\t');
        
        System.out.println(model.ne.containsKey("marea britanie"));
        System.out.println(model.ne.containsKey("Marea Britanie"));
        
        /*for(String s:model.ne.keySet()) {
        	System.out.println(s);
        }*/
        
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
