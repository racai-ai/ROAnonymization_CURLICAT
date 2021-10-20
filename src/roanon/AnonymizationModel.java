package roanon;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class AnonymizationModel {
	public HashMap<String,String> ne;
	public HashMap<String,Integer> wordForm;
	
	
	//private static final String URL_NER="http://127.0.0.1:5104/api/v1.0/ner";
	private String URL_NER;
	private boolean useUnk;
	
	
	// aici mai sunt nume http://www.diacronia.ro/ro/indexing/details/A24318/pdf
	// Derivarea numelor feminine in limba romana
	// Anca Bercaru
	
	
	// http://www.diacronia.ro/ro/indexing/details/V433/pdf
	// Articol hotarat referential vs articol hotarat expletiv. Situatia numelor proprii 
	// Alexandra Cornilescu, Alexandru Nicolae
	private String[][] suffixes=new String[][] {
		new String[] {"ului",""},
		new String[] {"ei","a"},
		new String[] {"ii","a"},
		new String[] {"ul",""}
	};
	
	private String endtok=" -.!?,;:\"'/\\\n\t\r";
	
	private String[] allowedNER=new String[] {
			"PER","LOC","ORG"
	};

	private HashMap<String,Boolean> endTokHash;
	private HashMap<String,Boolean> allowedNERHash;
	
	public AnonymizationModel(String nerUrl, boolean useUnk) {
		this.URL_NER=nerUrl;
		this.useUnk=useUnk;
		
		endTokHash=new HashMap<>(endtok.length());
		
		for(int i=0;i<endtok.length();i++) {
			endTokHash.put(""+endtok.charAt(i), Boolean.TRUE);
		}
		
		allowedNERHash=new HashMap<>(allowedNER.length);
		for(int i=0;i<allowedNER.length;i++)
			allowedNERHash.put(allowedNER[i],Boolean.TRUE);
	}
	
	private String getMapping(String cString,String type,HashMap<String,Integer> typeIds, HashMap<String,String> currentMappings) {
		return getMapping(cString,type,typeIds,currentMappings,"","");
	}
	
	private String getMapping(String cString,String type,HashMap<String,Integer> typeIds, HashMap<String,String> currentMappings, String suff, String cword) {
		int num=1;
		if(typeIds.containsKey(type)) {
			num=typeIds.get(type);
			num++;
		}
		typeIds.put(type, num);

		if(cword.length()>0) {
			String id="_#"+type+"#"+num+"_";
			currentMappings.put(cword, id);
		}
		
		String id="_#"+type+"#"+num+"_"+suff;
		currentMappings.put(cString, id);
		return id;
	}
	
	
	
	private String anonymizeFirstTokens(LinkedList<Token> tokens, HashMap<String,Integer> typeIds, HashMap<String,String> currentMappings) {
		StringBuilder ret=new StringBuilder(1000);
		Token firstTok=tokens.getFirst();
		ret.append(firstTok.getSep());
		
		if(!firstTok.getTok().toLowerCase().equals(firstTok.getTok())) {
		
			StringBuilder current=new StringBuilder(1000);
			for(int sz=tokens.size();sz>0;sz--) {
				current.setLength(0);
				
				for(int i=0;i<sz;i++) {
					if(current.length()>0)current.append(" ");
					current.append(tokens.get(i).getTok());
				}
				
				if(current.toString().contains("_#"))continue;
				
				String cString=current.toString().toLowerCase();
				if(currentMappings.containsKey(cString)) {
					ret.append(currentMappings.get(cString));
					for(int j=0;j<sz;j++)tokens.removeFirst();
					return ret.toString();
				}else if(ne.containsKey(cString)) {
					String type=ne.get(cString);
					String id=getMapping(cString,type,typeIds,currentMappings);
					ret.append(id);
					
					for(int j=0;j<sz;j++)tokens.removeFirst();
					
					return ret.toString();
				}
				
			}
			
			
			String fword=firstTok.getTok().toLowerCase();
			if(!fword.contains("_#")) {
				for(String[] suff:suffixes) {
					if(fword.endsWith(suff[0])) {
						String cword=fword;
						cword=fword.substring(0,fword.length()-suff[0].length());
						cword+=suff[1];
	
						if(currentMappings.containsKey(cword)) {
							String id=currentMappings.get(cword);
							id+=suff[0];
							currentMappings.put(fword, id);
							ret.append(id);
						}else if(ne.containsKey(cword)) {
							String type=ne.get(cword);
							String id=getMapping(fword,type,typeIds,currentMappings,suff[0],cword);
							ret.append(id);
							
							tokens.removeFirst();
							
							return ret.toString();
						}					
						
					}
				}
				
				if(useUnk) {
					if(!wordForm.containsKey(firstTok.getTok().toLowerCase())) {
						String id=getMapping(firstTok.getTok(),"UNK",typeIds,currentMappings);
						ret.append(id);
						tokens.removeFirst();
						return ret.toString();
					}
				}
			}			
		
		}
		
		ret.append(firstTok.getTok());
		tokens.removeFirst();
		return ret.toString();
	}
	
	public String anonymize(String text,List<String> mappings) {
		if(text==null) return "";

		HashMap<String,Integer> typeIds=new HashMap<>(10);
		HashMap<String,String> currentMappings=new HashMap<>(100);		

		if(URL_NER!=null) {
			JSONArray arr=null;
			try{arr=ExternalNER.callNER(URL_NER, text);}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			if(arr!=null) {
				for(int i=0;i<arr.length();i++) {
					JSONObject ob=arr.getJSONObject(i);
					int start=ob.getInt("start");
					int end=ob.getInt("end");
					String type=ob.getString("type");
					String cString=ob.getString("text").toLowerCase();
					if(!allowedNERHash.containsKey(type))continue;
					
					int len=end-start;
					String t1=text.substring(0,start);
					String t2=text.substring(end);
	
					String id="";
					if(currentMappings.containsKey(cString)) {
						id=currentMappings.get(cString);
					}else {
						for(String[] suff:suffixes) {
							if(cString.endsWith(suff[0])) {
								String cword=cString.substring(0,cString.length()-suff[0].length());
								cword+=suff[1];
	
								if(currentMappings.containsKey(cword)) {
									id=currentMappings.get(cword);
									currentMappings.put(cString, id);
									break;
								}else {
									id=getMapping(cString,type,typeIds,currentMappings,suff[0],cword);
									break;
								}
								
							}
						}
						
						if(id.length()==0) {
							id=getMapping(cString,type,typeIds,currentMappings);
						}
					}
					
					text=t1+id+t2;
	
					// update the remaining positions
					len-=id.length();
					for(int j=i+1;j<arr.length();j++) {
						JSONObject ob1=arr.getJSONObject(j);
						int start1=ob1.getInt("start");
						int end1=ob1.getInt("end");
						if(start1>start) {
							ob1.put("start", start1-len);
							ob1.put("end", end1-len);
						}
						arr.put(j,ob1);
					}
					
				}
			}
			
			//System.out.println("\n\nDUPA NER:");		
			//System.out.println(text);
		}		
		
		int last=0;
		StringBuilder ret=new StringBuilder(text.length());
		StringBuilder sep=new StringBuilder(100);
		LinkedList<Token> tokens=new LinkedList<>();
		
		for(int i=0;i<text.length();i++) {
			String s=""+text.charAt(i);
			if(endTokHash.containsKey(s)) {
				if(i-last>0) {
					String tok=text.substring(last,i);
					tokens.add(new Token(sep.toString(),tok));
					sep.setLength(0);
					
					if(tokens.size()>10) {
						ret.append(anonymizeFirstTokens(tokens,typeIds,currentMappings));
					}
				}
				sep.append(s);
				last=i+1;
			}
		}
		
		while(!tokens.isEmpty()) {
			ret.append(anonymizeFirstTokens(tokens,typeIds,currentMappings));
		}
		
		if(sep.length()>0)ret.append(sep.toString());
		
		for(Map.Entry<String, String> entry:currentMappings.entrySet()) {
			mappings.add(entry.getKey()+"\t"+entry.getValue());
		}
		
		return ret.toString();
	}
	
}
