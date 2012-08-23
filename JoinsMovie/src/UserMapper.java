import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;


public class UserMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text>
{
	//variables to process Consumer Details
    private String userid,restVal,fileTag="UD~";
    
    /* map method that process ConsumerDetails.txt and frames the initial key value pairs
    Key(Text) – mobile number
    Value(Text) – An identifier to indicate the source of input(using ‘CD’ for the customer details file) + Customer Name
  */
 public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException
 {
    //taking one line/record at a time and parsing them into key value pairs
	 String line = value.toString();
     String splitarray[] = line.split("::");
     userid = splitarray[0];
     restVal = splitarray[1]+"::"+splitarray[2]+"::"+splitarray[3];
    
   //sending the key value pair out of mapper
     output.collect(new Text(userid), new Text(fileTag+restVal));
  }
}
