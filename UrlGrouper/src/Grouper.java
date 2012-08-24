
public class Grouper {
	
	public String fetchUrlObject (String Line){
		
		String urlObject = null;
		String url = null;
		String[] profileSpecs =null;
		String[] text1 = null;
		String compositeTextCs = " "+"*";
		text1 = Line.split("\\|");
		url = text1[3];
		profileSpecs = text1[5].split(" ");
		for (int i =0; i< profileSpecs.length;i++){
			compositeTextCs=compositeTextCs+url+"+"+profileSpecs[i]+"*";
		}
	
		urlObject=compositeTextCs;
		return urlObject;
	}
	
	public static void main(String[] args){
		Grouper gp = new Grouper();
		String test = "1|N.SPEIGHT@SAINSBURYS.CO.UK|9|HTTP://UK.LINKEDIN.COM/PUB/NICK-SPEIGHT/11/A6/44|A PROVEN RETAIL AND HR PROFESSIONAL.<BR /> <BR /> 20 YEARS RETAIL EXPERIENCE. 10 YEARS HUMAN RESOURCE MANAGEMENT EXPERIENCE AT SENIOR LEVEL	 SPECIALISING LATTERLY IN HR OUTSOURCING	 HR SHARED SERVICES AND ORGANISATION DESIGN.|TEAM LEADERSHIP	 HR GENERALIST	 HR SHARED SERVICES	 HR SYSTEMS	 HR OUTSOURCING	 HR STRATEGY	 ORGANISATION DESIGN|MANCHESTER	 UNITED KINGDOM|NICK|SPEIGHT|DEPUTY MANAGER AT SPINNEYS	 BMMI|(1	)";
		System.out.println(gp.fetchUrlObject(test));
		
	}

}
