import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.FileSplit;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapreduce.Job;
//import org.apache.hadoop.mapreduce.Mapper;
//import org.apache.hadoop.mapreduce.Reducer;
//import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
//import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
//import org.apache.hadoop.util.GenericOptionsParser;
//import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.GenericOptionsParser;



public class UrlGrouper {

	public static class LineIndexMapper extends MapReduceBase
    implements Mapper<LongWritable, Text, Text, Text> {
    private final static Text word = new Text();
    private final static Text location = new Text();
    public void map(LongWritable key, Text val,
      OutputCollector<Text, Text> output, Reporter reporter)
      throws IOException {
    	FilterStopWords stop = new FilterStopWords();
       String line = val.toString();
       Grouper grp = new Grouper();
	   String vlaue_mod = grp.fetchUrlObject(line);
       StringTokenizer itr = new StringTokenizer(vlaue_mod.toLowerCase(),"*");
       while (itr.hasMoreTokens()) {
    	  String itre = itr.nextToken();
 	      String[] itrew = itre.split("\\+");
    	  try{
  		  word.set(itrew[1]);
  		  location.set(itrew[0]);
  	         }
  	      catch (Exception e){
  		   word.set("No URL found");
  		   location.set(itrew[0]);
  	        }
   
      output.collect(word, location);
           }
        }
      }
	
	
	
	 public static class LineIndexReducer extends MapReduceBase
     implements Reducer<Text, Text, Text, Text> {
		// public String[] stopWords = 
     public void reduce(Text key, Iterator<Text> values,
       OutputCollector<Text, Text> output, Reporter reporter)
       throws IOException {
    	 FilterStopWords stop = new FilterStopWords();
     boolean first = true;
     StringBuilder toReturn = new StringBuilder();
     while (values.hasNext()){
       if (!first)
         toReturn.append(", ");
       first=false;
       toReturn.append(values.next().toString());
     }
     if ( !stop.checker(key.toString())&& key.toString().length()>4  ) 
     output.collect(key, new Text(toReturn.toString()));
     }
 }


	 
	 public static void main(String[] args) throws Exception
	 {
	JobClient client = new JobClient();
    JobConf conf = new JobConf(UrlGrouper.class);
  String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
  if (otherArgs.length != 2) {
    System.err.println("Usage: UrlGrouper <in> <out>");
    System.exit(2);
  }
   conf.setJobName("URL Grouper");
   conf.setOutputKeyClass(Text.class);
   conf.setOutputValueClass(Text.class);
   FileInputFormat.addInputPath(conf, new Path(args[0]));
   FileOutputFormat.setOutputPath(conf, new Path(args[1]));
   conf.setMapperClass(LineIndexMapper.class);
   conf.setReducerClass(LineIndexReducer.class);
   client.setConf(conf);
  try {
    JobClient.runJob(conf);
  } catch (Exception e) {
    e.printStackTrace();
  }
 }
}

	

