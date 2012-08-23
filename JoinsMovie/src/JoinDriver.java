/**
 * 
 */

/**
 * @author Siddharth
 *
 * @DateCreated: Aug 21, 2012
 * @TimeStamp: 2:00:24 AM
 */
 import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.TextInputFormat;

import org.apache.hadoop.mapred.lib.MultipleInputs;

import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


public class JoinDriver extends Configured implements Tool
{
       public int run(String[] args) throws Exception {

              //get the configuration parameters and assigns a job name
              JobConf conf = new JobConf(getConf(), JoinDriver.class);
              conf.setJobName("Movielens Reduce Side Join Job");
              conf.set("*mapred.textoutputformat.separator*", "");
              //setting key value types for mapper and reducer outputs
              conf.setOutputKeyClass(Text.class);
              conf.setOutputValueClass(Text.class);

              //specifying the custom reducer class
              conf.setReducerClass(MoviesJoinerReducerSide.class);
              
              //Specifying the input directories(@ runtime) and Mappers independently for inputs from multiple sources
              MultipleInputs.addInputPath(conf, new Path(args[0]), TextInputFormat.class, UserMapper.class);
              MultipleInputs.addInputPath(conf, new Path(args[1]), TextInputFormat.class, RatingMapper.class);
             
              //Specifying the output directory @ runtime
              FileOutputFormat.setOutputPath(conf, new Path(args[2]));

              JobClient.runJob(conf);
              return 0;
       }

       public static void main(String[] args) throws Exception {
              int res = ToolRunner.run(new Configuration(), new JoinDriver(),
                           args);
              System.exit(res);
       }
}