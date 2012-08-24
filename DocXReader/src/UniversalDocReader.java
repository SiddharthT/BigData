import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;







public class UniversalDocReader {

	
	public void readDoc(File fname,String Dirname) throws IOException{
		
		
		WordExtractor extractor = null ;
		FileInputStream fis=new FileInputStream(fname.getAbsolutePath());
		HWPFDocument document=new HWPFDocument(fis);
		extractor = new WordExtractor(document);
		String [] fileData = extractor.getParagraphText();
		File  opFile= new File (Dirname);
    	if (!opFile.exists()){
    		opFile.mkdir();
    	}
    	FileWriter fstream = new FileWriter(opFile.getAbsoluteFile().toString()+"\\"+fname.getName()+".txt");
    	BufferedWriter out = new BufferedWriter(fstream);
		for(int i=0;i<fileData.length;i++){
			if(fileData[i] != null)
			
			System.out.println(fileData[i]);
			out.write(fileData[i]);
			
			}
		out.close();
	}
	
    public void readDocx (File fname,String Dirname) throws IOException {
    	XWPFWordExtractor extractor = null ;
    	FileInputStream fis=new FileInputStream(fname.getAbsolutePath());
    	XWPFDocument document = new  XWPFDocument(fis);
    	extractor = new XWPFWordExtractor(document);
    	String  fileData = extractor.getText();
    	File  opFile= new File (Dirname);
    	if (!opFile.exists()){
    		opFile.mkdir();
    	}
    	FileWriter fstream = new FileWriter(opFile.getAbsoluteFile().toString()+"\\"+fname.getName()+".txt");
    	BufferedWriter out = new BufferedWriter(fstream);
    	//System.out.println(fileData);
    	System.out.println("Writing file to file: "+opFile.getAbsoluteFile().toString()+"\\"+fname.getName()+".txt");
    	out.write(fileData.toString());
    	out.close();
    }
    
    public void readPdf (String fname,String Dirname) throws IOException {
    	 PdfReader reader = new PdfReader(fname);
    	 File temp = new File(fname);
    	 int n = reader.getNumberOfPages();
    	 //System.out.println(n);
    	  File  opFile= new File (Dirname);
	    	if (!opFile.exists()){
	    		opFile.mkdir();
	    	}
    	 FileWriter fstream2 = new FileWriter(opFile.getAbsoluteFile().toString()+"\\"+temp.getName()+".txt");
	    	BufferedWriter out = new BufferedWriter(fstream2);
    	 for (int i=1;i<=n;i++){
    	 String str=PdfTextExtractor.getTextFromPage(reader, i);
    	 //System.out.println(str);
    	 System.out.println("Writing file to file: "+opFile.getAbsoluteFile().toString()+"\\"+temp.getName()+".txt");
    	 out.write(str);
    	 
    	 }out.close();    	     }
    
    
    
    public void readTxt ( String fname,String Dirname) throws IOException {
    	try{
    		 FileInputStream fstream = new FileInputStream(fname.toString());
    		 DataInputStream in = new DataInputStream(fstream);
    		  BufferedReader br = new BufferedReader(new InputStreamReader(in));
    		  String strLine;
    		  File  opFile= new File (Dirname);
    	    	if (!opFile.exists()){
    	    		opFile.mkdir();
    	    	}
    	    	FileWriter fstream2 = new FileWriter(opFile.getAbsoluteFile().toString()+"\\"+fname.toString()+".txt");
    	    	BufferedWriter out = new BufferedWriter(fstream2);
    		  while ((strLine = br.readLine()) != null)   {
    			  System.out.println (strLine);
    			  out.write(strLine);
    		  }
    		  in.close();
    		  out.close();}
    		  catch (Exception e){//Catch exception if any
    			  System.err.println("Error: " + e.getMessage());
    			  }
    	
    }

    public void listFilesForFolder(final File folder) throws IOException {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
            	if (fileEntry.getAbsoluteFile().getName().endsWith(".pdf") || fileEntry.getAbsoluteFile().getName().endsWith(".PDF"))
                {
            		//System.out.println(fileEntry.getAbsoluteFile().getName());
            		UniversalDocReader dcr = new UniversalDocReader();
            		dcr.readPdf(fileEntry.getAbsolutePath().toString(),"Outdir");
                }
            	
            	if (fileEntry.getAbsoluteFile().getName().endsWith(".doc") || fileEntry.getAbsoluteFile().getName().endsWith(".DOC")){
            		//System.out.println(fileEntry.getAbsoluteFile().getName());
            		UniversalDocReader dcr = new UniversalDocReader();
            		dcr.readDoc(fileEntry.getAbsoluteFile(),"Outdir");
            	}
            	
            	if (fileEntry.getAbsoluteFile().getName().endsWith(".docx") || fileEntry.getAbsoluteFile().getName().endsWith(".DOCX")){
            		//System.out.println(fileEntry.getAbsoluteFile().getName());
            		UniversalDocReader dcr = new UniversalDocReader();
            		dcr.readDocx(fileEntry.getAbsoluteFile(),"Outdir");
            	}
            	
            	if (fileEntry.getAbsoluteFile().getName().endsWith(".txt") || fileEntry.getAbsoluteFile().getName().endsWith(".TXT")){
            		//System.out.println(fileEntry.getAbsoluteFile().getName());
            		UniversalDocReader dcr = new UniversalDocReader();
            		dcr.readTxt((fileEntry.getAbsoluteFile().toString()),"Outdir");
            	}
            }
        }
    }

    
    
    public static void main(String[] args) throws IOException {
    	
    	System.out.print("Enter your directory path ( format C:\\a\\b\\c ) and press Enter: ");
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	File outdir = new File("outDir");
		if (outdir.exists()){
			outdir.delete();}
    	String name = null;
    	try {
            name = br.readLine();
          } catch (IOException e) {
            System.out.println("Error!");
            System.exit(1);
          }
          System.out.println("The Directory name you entered is " + name);
          UniversalDocReader newReader = new UniversalDocReader();
          File newFolder = new File(name);
          newReader.listFilesForFolder(newFolder);

      

    }
	
		} 

