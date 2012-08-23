 import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;

public class MoviesJoinerReducerSide extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
      
       //Variables to aid the join process
       private String useridRD,useridUD,restValRD,restValUD,moviedata,movieID;
       /*Map to store Delivery Codes and Messages
       Key being the status code and value being the status message*/
       private static Map<String,String> MovieNameMap= new HashMap<String,String>();
       private List l = new ArrayList();
       public void configure(JobConf job)
       {
              //To load the Delivery Codes and Messages into a hash map
              loadDeliveryStatusCodes();
             
       }


       public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException
    {
        while (values.hasNext())
        {
             String currValue = values.next().toString();
             String valueSplitted[] = currValue.split("~");
             /*identifying the record source that corresponds to a cell number
             and parses the values accordingly*/
             if(valueSplitted[0].equals("RD"))
             {
            	 restValRD=valueSplitted[1].trim();
            	 useridRD=key.toString();
            	 movieID=restValRD.split("::")[0];
            	// l.add(useridUD+"*"+restValUD);
             }
             if(valueSplitted[0].equals("UD"))
             {
            	 restValUD=valueSplitted[1].trim();
            	
             }
             
             //else if(valueSplitted[0].equals("DR"))
             //{
              //getting the delivery code and using the same to obtain the Message
             moviedata = MovieNameMap.get(movieID);
             //}
        
        
        //pump final output to file
        if(useridRD!=null && moviedata!=null)
        {
               output.collect(new Text(useridRD), new Text("::"+restValUD+"::"+restValRD+"::"+moviedata));
        }
        else if(useridRD==null)
             output.collect(new Text("useridRD"), new Text("moviedata"));
       else if(moviedata==null)
              output.collect(new Text(useridRD), new Text("moviedata"));
       }
    } 
      
      
       //To load the Delivery Codes and Messages into a hash map
    private void loadDeliveryStatusCodes()
    {
       String strRead;
       try {
              //read file from Distributed Cache
                     BufferedReader reader = new BufferedReader(new FileReader("movies.dat"));
                     while ((strRead=reader.readLine() ) != null)
                     {
                           String splitarray[] = strRead.split("::");
                           //parse record and load into HahMap
                           MovieNameMap.put(splitarray[0].trim(), splitarray[1].trim()+"::"+splitarray[2].trim());
                          
                     }
              }
              catch (FileNotFoundException e) {
              e.printStackTrace();
              }catch( IOException e ) {
                       e.printStackTrace();
                }
             
       }
}