import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RadioActiveDecay {

	/*public static void main(String args[]) throws IOException, ParseException {
		URL url = new URL("https://www.fourmilab.ch/cgi-bin/Hotbits.api?nbytes=5&fmt=json&npass=1&lpass=8&pwtype=3&apikey=");
		String parser = getDataFromServer(url);
		System.out.println(parseURl(parser, "serverVersion"));
		System.out.println(parseURl(parser, "generationTime"));

		System.out.println(getRandom(parser));
		System.out.println(parseURl(parser, "quotaRequestsRemaining"));
		System.out.println(parseURl(parser, "quotaBytesRemaining"));

	}*/
	public String parseURl(String fullString, String location) {
		int d = fullString.indexOf(location);
		String eee = fullString.substring(d);
		String fff = eee.substring(eee.indexOf(":")+2, eee.indexOf(","));
		return fff;
	}

	public JSONArray getRandom(String fullString) throws ParseException {
		JSONParser parse = new JSONParser();
		JSONObject jobj = (JSONObject) parse.parse(fullString);
		JSONArray arr = (JSONArray) jobj.get("data");
		return arr;
	}

	public String getDataFromServer(String TotalNum, String APIkey) throws IOException {
		URL url = new URL("https://www.fourmilab.ch/cgi-bin/Hotbits.api?nbytes="+TotalNum+"&fmt=json&npass=1&lpass=8&pwtype=3&apikey="+APIkey);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		String parser = "";
		con.setRequestMethod("GET");
		con.connect();
		if (con.getResponseCode() != 200) {
			throw new RuntimeException("Error getting data from server");
		}else {
			long tStart = System.currentTimeMillis();
			Scanner scn = new Scanner(url.openStream());
			while(scn.hasNext()) {
				long tEnd = System.currentTimeMillis();
				if ((tEnd - tStart)/1000.0 >= (60)) {
					scn.close();
					throw new RuntimeException("Operation timed out! Check your internet connection");
				}
				parser+=scn.nextLine();
			}
			scn.close();
		}
		return parser;
	}
}
