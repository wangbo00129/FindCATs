package p;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FindHotSpot {
	//static final String AAs="ARDCQEHIGNLKMFPSTWYV";
	
	// '-' means gap
	static final String[] AAs= {"A","R","D","C","Q","E","H","I","G","N","L","K","M","F","P","S","T","W","Y","V"};
	static final String str_file_fasta="E:\\My Documents\\Lab_FJ\\Unique.txt";
	static String proteins[];
	private static boolean ADD_FrequencyOfGap=false;
	public static void main2(String[] args) {
		//double biggest_entropy=-mlog(0.1)*0.1*10;
		//System.out.println(biggest_entropy);
		//if(true)return;
		proteins=BioMethods.fastaToSeqs(new File(str_file_fasta));
				
		int MAXIMUM_LENGTH=proteins[0].length()+10;
		for (int i = 0; i < MAXIMUM_LENGTH; i++) {
			System.out.println(i+"\t");
			analyzeDiversityAt(i);
		}
		
	}
	
	public static void main(String[] args) {
		
		proteins=BioMethods.fastaToSeqs(new File(str_file_fasta));
		// Get the sequences that conform to the following conditions
		// position 96==A or T; position 107==G; position 148==A or T; position 172==S; position 178==T
		int[] specific_position= {96,107,148,172,178};
		char[][] candidates= {{'A','T'},{'G','R'},{'A','T'},{'S','G','N'},{'M','T'}};
		
		for (int i = 0; i < proteins.length; i++) {
			// conform is the array of issameAA at different position 
			int conform=0;
			String unique_bases="";
			for (int j = 0; j < specific_position.length; j++) {
				
				for (int k = 0; k < candidates[j].length; k++) {
					if(proteins[i].charAt(specific_position[j])==candidates[j][k]){
						//System.out.print("j&k "+j+" "+k+" ");
						unique_bases+=(candidates[j][k]+"\t");
						conform+=(k+1)*(Math.pow(10, j)); 
					}else {
						
						//break;
					}
				}
			}
			if(!(conform+"").contains("0")) {
				//System.out.println(">protein "+i+", unique_value\t"+conform);
				System.out.println(proteins[i]+"\t"+conform+"\t"+unique_bases);
			}
		}
	}
	
	/**
	 * Analyze the diversity at the i th position of the protein sequence. 
	 * @param position the position of this DNA/protein sequence. When i > the sequence length, "-" was designated
	 */
	private static void analyzeDiversityAt(int position) {
		// TODO Auto-generated method stub
		String charsAtThisPosition="";
		for (int i = 0; i < proteins.length; i++) {
			if(position<proteins[i].length()) {
				charsAtThisPosition+=proteins[i].charAt(position);
			}else {
				charsAtThisPosition+='-';
			}
			
		}
		
		// gap replaced 
		String AAsAtThisPosition=charsAtThisPosition.replaceAll("-", "");
		double entropy=0;
		for(int j=0, AAs_length=AAs.length;j<AAs_length;j++) { 
			
			float other_percentage=(float)AAsAtThisPosition.replaceAll(AAs[j],"").length()/AAsAtThisPosition.length();
			float this_percentage=1-other_percentage;
			List<Diversity> list_diversity=new ArrayList<Diversity>();
			Diversity d=new Diversity();
			d.clas=AAs[j];
			d.percentage=this_percentage;
			list_diversity.add(d);
			
			if(d.percentage>0) {
				
				entropy=entropy-(d.percentage)*mlog(d.percentage);
				//System.out.println("interval: "+entropy);
				if(d.percentage<1) {
					System.out.println(d.clas+":\t"+d.percentage);
				}
			}

		}
		if(ADD_FrequencyOfGap)entropy=entropy+(1-AAsAtThisPosition.length()/charsAtThisPosition.length());
		//System.out.println("entropy:\t"+entropy);
	}
	/**
	 * Log using 20 as base. 
	 * @return
	 */
	public static double mlog(double a) {
		return Math.log10(a)/Math.log10(20);
	}
}
