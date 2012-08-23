import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

/**
 * 
 */

/**
 * @author 460615
 *
 */
public class Testing123 {
	
	static {
		URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
		}
	
	/*void readHDFS() throws MalformedURLException, IOException{
		InputStream in = null;
		try{
			in = new URL("hdfs://imbdbox1:54310/user/hadoop/users").openStream();
			IOUtils.copyBytes(in, System.out, 4096, false);
			// process in
		}
		
		finally {
			IOUtils.closeStream(in);
			}
			
		
	}*/
	
	public void mkdir(String dir) throws IOException {
        Configuration conf = new Configuration();
        conf.addResource(new Path("core-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);

        Path path = new Path(dir);
        if (fileSystem.exists(path)) {
            System.out.println("Dir " + dir + " already not exists");
            return;
        }

        fileSystem.mkdirs(path);

        fileSystem.close();
    }
	
	public void addFile(String source, String dest) throws IOException {
        Configuration conf = new Configuration();
        /*FileSystem fs =new DistributedFileSystem(); 
        fs.initialize(new URI("hdfs://imbdbox1:54310",conf);*/
        // Conf object will read the HDFS configuration parameters from these
        // XML files.
        conf.addResource(new Path("core-site.xml"));
        conf.addResource(new Path("hdfs-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);

        // Get the filename out of the file path
        String filename = source.substring(source.lastIndexOf('/') + 1,
            source.length());

        // Create the destination path including the filename.
        if (dest.charAt(dest.length() - 1) != '/') {
            dest = dest + "/" + filename;
        } else {
            dest = dest + filename;
        }

        // System.out.println("Adding file to " + destination);

        // Check if the file already exists
        Path path = new Path(dest);
        if (fileSystem.exists(path)) {
            System.out.println("File " + dest + " already exists");
            return;
        }

        // Create a new file and write data to it.
        FSDataOutputStream out = fileSystem.create(path);
        InputStream in = new BufferedInputStream(new FileInputStream(
            new File(source)));
        
        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes = in.read(b)) > 0) {
            out.write(b, 0, numBytes);
        }

        // Close all the file descripters
        in.close();
        out.close();
        fileSystem.close();
    }

	public void readFile(String file) throws IOException {
        Configuration conf = new Configuration();
        conf.addResource(new Path("core-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);

        Path path = new Path(file);
        if (!fileSystem.exists(path)) {
            System.out.println("File " + file + " does not exists");
            return;
        }

        FSDataInputStream in = fileSystem.open(path);

        String filename = file.substring(file.lastIndexOf('/') + 1,
            file.length());
       // System.out.println(filename);
      //  filename="/user/hadoop/users";
        System.out.println(in.read());
        OutputStream out = new BufferedOutputStream(new FileOutputStream(
            new File(filename)));

        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes = in.read(b)) > 0) {
            out.write(b, 0, numBytes);
           
        }
        System.out.println("Copied file from HDFS to workspace");
        in.close();
        out.close();
        fileSystem.close();
    }
	 public void deleteFile(String file) throws IOException {
	        Configuration conf = new Configuration();
	        conf.addResource(new Path("core-site.xml"));

	        FileSystem fileSystem = FileSystem.get(conf);

	        Path path = new Path(file);
	        if (!fileSystem.exists(path)) {
	            System.out.println("File " + file + " does not exists");
	            return;
	        }
	        fileSystem.delete(new Path(file), true);

	        fileSystem.close();
	    }
	 
	
	/*void writeHDFS(String LocalSrc, String destSrc) throws IOException {
		InputStream in = new BufferedInputStream(new FileInputStream(LocalSrc));
		Configuration conf = new Configuration();
		conf.set("user.name", "hadoop");
		FileSystem fs = FileSystem.get(URI.create(destSrc), conf);
		OutputStream out = fs.create(new Path(destSrc), new Progressable() {
			public void progress() {
			System.out.print(".");
			}
			});
		IOUtils.copyBytes(in, out, 4096, true);
	
	}*/
	
	public static void main (String[] args) throws MalformedURLException, IOException{
		Configuration conf = new Configuration();
		conf.addResource(new Path("core-site.xml"));
		conf.addResource(new Path("mapred-site.xml"));
		 FileSystem fileSystem = FileSystem.get(conf);
		 
		Testing123 ts = new Testing123();
		ts.readFile("/user/hadoop/users");
		//ts.writeHDFS("C:\\Documents and Settings\\460615\\Desktop\\db2diag.log", "hdfs://imbdbox1:54310/user/hadoop/sample/diag22");
		//ts.mkdir("Siddharth");
	}

}
