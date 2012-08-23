import java.io.IOException;
import java.util.*;

class classify {
	
	public String classifier (String tweet){
		String getTweet=tweet.replace(".", " ");
		boolean negCameBefore=false;
		boolean negetionFlag=false;
		boolean specialNegation=false;
		boolean specialAscertiveness=false;
		int len=0;
		int dummy=0;
		int dummy2=0;
		String category ;
        int timesNeg=0;
        int timesPos=0;
        int timesNute=0;
		String[] temp;
		List<String> NEGATIVE=Arrays.asList("didn't","didnt","not","ass","sucks","bad","hate",":(","poor","wrong","hell","sad","angry","stupid","dont","don't","expensive");
		List<String> positive=Arrays.asList("good","awesome","superb",":)","like","love","rocks","superb","caring","smart","excellent","shopping","keep");
		
		temp=getTweet.split(" ");
		for (int counter=0;counter<temp.length;counter++){
			
			if (counter==0|| counter>dummy2||counter>dummy||temp[counter].equalsIgnoreCase("not")||temp[counter].equalsIgnoreCase("never")){
				negetionFlag=true;
			}
			
			if (NEGATIVE.contains(temp[counter])){
				if (counter==0 ||counter>dummy ){
					negCameBefore=true;
				}

				if (getTweet.contains(" not "+temp[counter]+" ")&& temp[counter]!=" "){
					
					specialNegation=true;
				}
				timesNeg++;
				dummy2=counter;
			}
			
			else if (positive.contains(temp[counter]) && temp[counter]!=" "){
if (getTweet.contains("not "+temp[counter])){
					
					specialAscertiveness=true;
				}
				 dummy=counter;
				timesPos++;
				
			}
			
			else {
				timesNute++;
			}
			
		}
		
		if (timesPos>timesNeg  && negCameBefore==false  ){
			if (Arrays.asList(temp).contains("but"))
			category=" :-NEGATIVE";
			else category=" :-POSITIVE";
			
		}
		
		else if (timesNeg==timesPos && negCameBefore==true){
			 //System.out.println("Hey Dude the Compiler is here :P");
			if (Arrays.asList(temp).contains("but") )
			{	System.out.println(" Hey Dude the Compiler is here in negation :P \n");
				category=" :-POSITIVE";
			}
			else {
				//System.out.println("Hey Dude the Compiler is here in eva :P \n");
				category=" :-NEGATIVE";
			}
			len = Arrays.asList(temp).size();
			if (Arrays.asList(temp).get(len-1).equalsIgnoreCase("not")||Arrays.asList(temp).get(len-1).equalsIgnoreCase("dont")||Arrays.asList(temp).get(len-1).equalsIgnoreCase("don't")){
				category=" : NEGATIVE";
			}
			
		}
		
		
		else if (timesNeg>timesPos || negCameBefore==true ){
			if (Arrays.asList(temp).contains("but"))
			{category=" :-POSITIVE";
						}
			else category=" :-NEGATIVE";
			
					}
		else {
			category = " NEUTRAL/INEFFICIENT MODEL";
		}
		
		 if (specialAscertiveness==true){
			category=" :-NEGATIVE";
		}
		 if (specialNegation==true){
			category=" :-POSITIVE";
		}
		
		
		specialAscertiveness=false;
		specialNegation=false;
		return category;
		
		
	}
}