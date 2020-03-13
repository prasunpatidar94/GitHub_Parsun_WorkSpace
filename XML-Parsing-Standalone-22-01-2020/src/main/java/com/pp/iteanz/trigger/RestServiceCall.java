package com.pp.iteanz.trigger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.stereotype.Component;

@Component
public class RestServiceCall {

	public Boolean caller(String input, URL url, long reqno, String subprocess, String appid) {
		
		boolean result=false;
		
//		String input = "{\"APPID\":\"" + appid + "\",\"SUBPROC\":\"" + subprocess + "\",\"REQID\":"
//				+ reqno + "}";
//
//		
//		String input = "{\r\n" + 
//				"	\"ADRID\":\""+result+"\",\"BEGDA\":\""+result+"\",\r\n" + 
//				"	\"ENDDA\":\""+result+"\",\"SUNAME\":\""+result+"\",\r\n" + 
//				"	\"ENAME\":\"0\",\"EMAIL\":\"0\",\r\n" + 
//				"	\"MOBILE\":\"0\",\"BPTYPE\":\"0\",\r\n" + 
//				"	\"CRNAM\":\"0\",\"CRDAT\":\"0\",\r\n" + 
//				"	\"CDNAM\":\"0\",\"CDDAT\":\"0\",\r\n" + 
//				"	\"CDTIM\":\"0\",\"ROLE\":\"0\",\r\n" + 
//				"	\"ALEVL\":\"0\",\"DEGFLG\":\"0\",\r\n" + 
//				"	\"ADFLG\":\"0\",\"BPVALE\":\"0\",\r\n" + 
//				"	\"DEPT\":\"0\",\"ID\":\"0\",\r\n" + 
//				"	\"SAPID\":\"\",\"PSWRD\":\"\",\r\n" + 
//				"		\"CRTIM\":\"0\"\r\n" + 
//				"}";


		try {

			// URL url = new URL("http://localhost:8080/RESTfulExample/json/product/post");
//			URL url = new URL("http://localhost:8080/pendingPRList");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

//			long reqno = main.getPrno();
//			String subprocess = "PRSUB";
//			String appid = "PR";

			System.out.println("input is ::  " + input);
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());

			os.flush();
			System.out.println(HttpURLConnection.HTTP_CREATED);

			if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			System.out.println(conn.getResponseMessage());

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {

				System.out.println(output);
			}
			 result = true;
			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}

		return result;
	}

}
