package com.tcs.im;


import java.io.BufferedReader;
//import com.google.gson.Gson;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
/*import net.*;*/
//import com.google.gson.*;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

//import org.apache.commons.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
//@author Siddharth tiwari
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.JSONValue;
public class FbGrabber
{
	public void breakFile() throws IOException
	{
		boolean validate = true;
		
		    	// Open the file that is the first 
			  // command line parameter
			
			  FileInputStream fstream2 = new FileInputStream("C:/Documents and Settings/460615/Desktop/out_id.txt");
			 
			// Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream2);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  List<String> list_id = new ArrayList<String>();
			//Read File Line By Line
			 // URL likes = new URL("https://graph.facebook.com/"+((JSONObject)jsonArray.get(i)).get("id")+"/likes?access_token="+accessToken);
			  String[] temp = null;
			  while ((strLine = br.readLine()) != null) 
			  {
			  list_id.add(strLine);}
			  in.close();
			  PrintWriter out = new PrintWriter (new FileWriter("C:/Documents and Settings/460615/Desktop/feeds_out.txt",true));
			 chalega:
				
				 for (String l : list_id){
					 
				 try{
					 System.err.println(l);
					 temp=l.split(":");
				  URL likes = new URL("https://graph.facebook.com/"+"214208065504"+"/feed?access_token=AAABcAajL3akBAB2LVyQQOZCgJDW5VW5yH6g8ZCEh9o2Fz5QqFqNfrZC1tdyEoEv8Ie8KOpoSq197CGI0LedgHbd0n66AJ4ZD");
				  URLConnection yc2 = likes.openConnection();
				BufferedReader in2 = new BufferedReader(new InputStreamReader(yc2.getInputStream()));
				String inputLine2;
				String s2 = "";
				
				again:
				while ((inputLine2 = in2.readLine()) != null )
					s2 = s2+inputLine2;
				in2.close();
				System.out.println(s2);
				JSONObject json2=new JSONObject();
					JSONArray jsonArray2 = new JSONArray();
					jsonArray2=null;
					 jsonArray2 = (JSONArray)json2.fromString(s2).get("data");
				//System.out.println((JSONArray)json2.fromString(s2).get("data"));
					System.err.print("this is my length: "+jsonArray2.length()+"\n");
				
				for (int ii=0;ii<jsonArray2.length();ii++){
				//System.out.println(ii);
				//System.out.println(((JSONObject)jsonArray2.get(ii)).get("category"));
				try{
					String stemp=""+l+":"+((JSONObject)jsonArray2.get(ii)).get("message")+"\n";
				
					System.out.println(stemp);
					out.write(stemp);}
				catch (Exception e){
				}
				}
					
				//------------//
				  // Print the content on the console
				  //String[] temp = strLine.split("\\{");
				 // for (int i=0;i<temp.length;i++)
				 // {
			//  System.out.println (temp[i]);
			  //out.println(temp[i]);
			  }
			 //}
			//Close the input stream
			  
			  catch (Exception e){//Catch exception if any
					 validate = false;
						  System.err.println("Error: " + e.getMessage());
						  continue chalega;
						  }
			   // }
				 } out.close();
			 

		
	}
/*public void breakFile()
{
	try {
	    	// Open the file that is the first 
		  // command line parameter
		  FileInputStream fstream2 = new FileInputStream("c:/users/siddharth/desktop/out.txt");
		  PrintWriter out = new PrintWriter (new FileWriter("c:/users/siddharth/desktop/java_out.txt"));
		// Get the object of DataInputStream
		  DataInputStream in = new DataInputStream(fstream2);
		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
		  String strLine;
		//Read File Line By Line
		  while ((strLine = br.readLine()) != null) 
		  {
		  // Print the content on the console
			  String[] temp = strLine.split("\\{");
			  for (int i=0;i<temp.length;i++)
			  {
		  System.out.println (temp[i]);
		  out.println(temp[i]);
		  }
		  }
		//Close the input stream
		  in.close();
		    }
		  catch (Exception e){//Catch exception if any
		  System.err.println("Error: " + e.getMessage());
}
	
}
}
class data {
  private String name;
  private Long id;
  //private Boolean children;
  //private List<Data> groups;

  public String getName() { return name; }
  public Long getId() { return id; }
  //public Boolean getChildren() { return children; }
  //public List<Data> getGroups() { return groups; }

  public void setName(String name) { this.name = name; }
  public void setId(Long id) { this.id = id; }
 // public void setChildren(Boolean children) { this.children = children; }
 // public void setGroups(List<Data> groups) { this.groups = groups; }

  public String toString() {
      return String.format("name:%s,id:%d", name, id);
  }
}*/

//public class JsonParsing {
/**

* @param args

* @throws IOException

*/

public static void main(String[] args) throws IOException {
	System.setProperty("http.proxySet", "true");

System.setProperty("https.proxyHost", "proxy.tcs.com");
System.setProperty("https.proxyPort","8080");

System.setProperty("proxy.authentication.username", "%69%6E%64%69%61%5C%34%36%30%36%31%35");
System.setProperty("proxy.authentication.password", "%4B%4B%54%2F%70%75%73%70%2F%2A%39%34");
String username = System.getProperty("proxy.authentication.username");
String password = System.getProperty("proxy.authentication.password");

//String encoding = new sun.misc.BASE64Encoder().encode (userPassword.getBytes());  
//connection.setRequestProperty ("Authorization", "Basic " + encoding);  
//connection.setRequestProperty(userPassword, encoding);  

String commentID = "interlace.your.heart";
FbGrabber tk = new FbGrabber();
String accessToken = "AAABcAajL3akBAB2LVyQQOZCgJDW5VW5yH6g8ZCEh9o2Fz5QqFqNfrZC1tdyEoEv8Ie8KOpoSq197CGI0LedgHbd0n66AJ4ZD";
//TODO Auto-generated method stub

URL yahoo = new URL(

"https://graph.facebook.com/me/friends?access_token="+accessToken);
URLConnection yc = yahoo.openConnection();
String login = "%69%6E%64%69%61%5C%34%36%30%36%31%35:%4B%4B%54%2F%70%75%73%70%2F%2A%39%34";
String encoding =new Base64().encodeToString((login.getBytes()));
yc.setRequestProperty("Authorization", login);
//yc.setRequestProperty ("Authorization", "Basic " + encoding);  
//yc.setRequestProperty(userPassword, encoding); 
BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
String inputLine;
String s = "";
while ((inputLine = in.readLine()) != null)

//System.out.println(inputLine);

s = s+inputLine;

in.close();
//data data2 = new Gson().fromJson(s,data.class);
//System.out.println(data2);
//System.out.println(s);
/*JSONObject json = JSONObject.fromString(s);*/
//Gson gson=new Gson();
//try{
	  // Create file 
	 System.out.println(s);
	  FileWriter fstream = new FileWriter("C:/Documents and Settings/460615/Desktop/out_id.txt");
	  BufferedWriter out = new BufferedWriter(fstream);

JSONObject json=new JSONObject();
//json=gson.fromJson(gson.toJson(s), JsonParsing.class);
//json.getClass().getS
JSONArray jsonArray = (JSONArray) json.fromString(s).get("data");
System.err.print("this is my length initial loop : "+jsonArray.length()+"\n");
for (int i=0;i<jsonArray.length();i++){
	//System.err.println(i);
	//System.out.println(((JSONObject)jsonArray.get(i)).get("name"));
	System.out.println(((JSONObject)jsonArray.get(i)).get("id"));
	String temp = ""+jsonArray.get(i);
	out.write(""+((JSONObject)jsonArray.get(i)).get("id")+":"+((JSONObject)jsonArray.get(i)).get("name")+"\n");
	//System.out.println(temp);
	//URL likes = new URL("https://graph.facebook.com/"+((JSONObject)jsonArray.get(i)).get("id")+"/likes?access_token="+accessToken);
		//	URLConnection yc2 = likes.openConnection();
		//	BufferedReader in2 = new BufferedReader(new InputStreamReader(yc2.getInputStream()));
		//	String inputLine2;
		//				String s2 = "";
		//	while ((inputLine2 = in2.readLine()) != null)
			//	System.out.println(inputLine2.length());
			//	s2 = s2+inputLine2;
						//System.out.println(s2);
			//	in2.close();
			//	JSONObject json2=new JSONObject();
			//	JSONArray jsonArray2 = new JSONArray();
			//	jsonArray2=null;
			//	 jsonArray2 = (JSONArray)json2.fromString(s2).get("data");
			//System.out.println((JSONArray)json2.fromString(s2).get("data"));
			//	System.err.print("this is my length: "+jsonArray2.length()+"\n");
				//for (int ii=0;ii<jsonArray2.length();ii++){
					//System.out.println(ii);
					//System.out.println(((JSONObject)jsonArray2.get(ii)).get("category"));
					//String stemp=""+((JSONObject)jsonArray.get(i)).get("id")+":"+((JSONObject)jsonArray2.get(ii)).get("category")+"\n";
					 
					//out.write(stemp);
					  
						 
				//} }out.close();//}//catch (Exception e){//Catch exception if any
					  //System.err.println("Error: " + e.getMessage()+e.getCause()+e.getStackTrace()+e.fillInStackTrace());
					  //}	  //Close the output stream
						 
						  // tk.breakFile();
						 
				}out.close();
				FbGrabber parse = new FbGrabber();
parse.breakFile();


//try{
	  // Create file 
//	 System.out.println("printing ");
//	  FileWriter fstream = new FileWriter("c:/Users/siddharth/Desktop/out.txt");
//	  BufferedWriter out = new BufferedWriter(fstream);
//	  out.write(s);
	  //Close the output stream
//	  out.close();
//	   tk.breakFile();
//	  }catch (Exception e){//Catch exception if any
//	  System.err.println("Error: " + e.getMessage());
//	  }
	

//Object obj = JSONValue.parse(s);
//Object obj2 = obj+"n";

//JSONArray array=(JSONArray)obj;

//System.out.println(obj);

//System.out.println("printed thi:"+array.get(1));

//JSONObject obj2 = (JSONObject) obj;
//JSONObject comments = (JSONObject) obj2.get("comments");

//JSONArray commentsArray = (JSONArray) comments.get("data");

//System.out.println(commentsArray.size());

//System.out.println(obj2.get("comments"));

//for (int i = 0; i<<commentsArray.size(); i++) {

//JSONObject commentobj = (JSONObject) commentsArray.get(i);

//System.out.println(commentobj);

//String commentText = (String) commentobj.get("message");

//System.out.println(commentText);

}}


//}
