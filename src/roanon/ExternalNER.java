package roanon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;

public class ExternalNER {
	
	public static String testNER=null;

	public static JSONArray callNER(String url, String text) throws IOException {
		String charset = "UTF-8";
		String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.

		String jsonRet="";
		if(testNER!=null)jsonRet=testNER;
		else {
			URLConnection connection = new URL(url).openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
	
			try (
			    OutputStream output = connection.getOutputStream();
			    PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
			) {
			    // Send text file.
			    writer.append("--" + boundary).append(CRLF);
			    writer.append("Content-Disposition: form-data; name=\"text\"; filename=\"text.txt\"").append(CRLF);
			    writer.append("Content-Type: text/plain").append(CRLF); // Text file itself must be saved in this charset!
			    writer.append(CRLF).flush();
			    writer.append(text);
			    //Files.copy(textFile.toPath(), output);
			    output.flush(); // Important before continuing with writer!
			    writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
	
			    // End of multipart/form-data.
			    writer.append("--" + boundary + "--").append(CRLF).flush();
			}
	
			// Request is lazily fired whenever you need to obtain information about response.
			int responseCode = ((HttpURLConnection) connection).getResponseCode();
			System.out.println(responseCode); // Should be 200
			
			InputStream in=connection.getInputStream();
			BufferedReader inr=new BufferedReader(new InputStreamReader(in,charset));
			String line;
			StringBuffer ret=new StringBuffer();
			while((line=inr.readLine())!=null) {
				ret.append(line);
			}
			
			jsonRet=ret.toString();
		}
		
		JSONObject retObj=new JSONObject(jsonRet);
		return retObj.getJSONArray("result");
		
	}
	
}
