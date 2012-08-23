import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DistributedFileSystem;

public class HDFSClient {
    public HDFSClient() {

    }

    public void addFile(String source, String dest) throws IOException {
        Configuration conf = new Configuration();
        /*FileSystem fs =new DistributedFileSystem(); 
        fs.initialize(new URI("hdfs://imbdbox1:54310",conf);*/
        // Conf object will read the HDFS configuration parameters from these
        // XML files.
        conf.addResource(new Path("/opt/hadoop-0.20.0/conf/core-site.xml"));
        conf.addResource(new Path("/opt/hadoop-0.20.0/conf/hdfs-site.xml"));

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
        conf.addResource(new Path("/opt/hadoop-0.20.0/conf/core-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);

        Path path = new Path(file);
        if (!fileSystem.exists(path)) {
            System.out.println("File " + file + " does not exists");
            return;
        }

        FSDataInputStream in = fileSystem.open(path);

        String filename = file.substring(file.lastIndexOf('/') + 1,
            file.length());

        OutputStream out = new BufferedOutputStream(new FileOutputStream(
            new File(filename)));

        byte[] b = new byte[1024];
        int numBytes = 0;
        while ((numBytes = in.read(b)) > 0) {
            out.write(b, 0, numBytes);
        }

        in.close();
        out.close();
        fileSystem.close();
    }

    public void deleteFile(String file) throws IOException {
        Configuration conf = new Configuration();
        conf.addResource(new Path("/opt/hadoop-0.20.0/conf/core-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);

        Path path = new Path(file);
        if (!fileSystem.exists(path)) {
            System.out.println("File " + file + " does not exists");
            return;
        }

        fileSystem.delete(new Path(file), true);

        fileSystem.close();
    }

    public void mkdir(String dir) throws IOException {
        Configuration conf = new Configuration();
        conf.addResource(new Path("/opt/hadoop-0.20.0/conf/core-site.xml"));

        FileSystem fileSystem = FileSystem.get(conf);

        Path path = new Path(dir);
        if (fileSystem.exists(path)) {
            System.out.println("Dir " + dir + " already not exists");
            return;
        }

        fileSystem.mkdirs(path);

        fileSystem.close();
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Usage: hdfsclient add/read/delete/mkdir" +
                " [<local_path> <hdfs_path>]");
            System.exit(1);
        }

        HDFSClient client = new HDFSClient();
        if (args[0].equals("add")) {
            if (args.length < 3) {
                System.out.println("Usage: hdfsclient add <local_path> " + 
                "<hdfs_path>");
                System.exit(1);
            }

            client.addFile(args[1], args[2]);
        } else if (args[0].equals("read")) {
            if (args.length < 2) {
                System.out.println("Usage: hdfsclient read <hdfs_path>");
                System.exit(1);
            }

            client.readFile(args[1]);
        } else if (args[0].equals("delete")) {
            if (args.length < 2) {
                System.out.println("Usage: hdfsclient delete <hdfs_path>");
                System.exit(1);
            }

            client.deleteFile(args[1]);
        } else if (args[0].equals("mkdir")) {
            if (args.length < 2) {
                System.out.println("Usage: hdfsclient mkdir <hdfs_path>");
                System.exit(1);
            }

            client.mkdir(args[1]);
        } else {   
            System.out.println("Usage: hdfsclient add/read/delete/mkdir" +
                " [<local_path> <hdfs_path>]");
            System.exit(1);
        }

        System.out.println("Done!");
    }
}
