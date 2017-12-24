package p;

import java.io.File;
import java.io.IOException;

public class E {
	/*
	 * 1-20th sequence or 21-40th sequence of CAT or the complementary reverse sequences of the above mentioned. 
	 */
	static final int CAT_LENGTH=720;
	static final int CAT_LINE_NUM=10;
	static int temp_flag=0;
	static final String[] SEEDS= {"ATGAGTGTTTTCGACACGTG","GTTAAAAGGTCAAGTCATTA","AAGATCACATCACTAATCCC","CACGTGTCGAAAACACTCAT","TAATGACTTGACCTTTTAAC","GGGATTAGTGATGTGATCTT"};
	static final int[] SEEDS_POSITION= {0,20,40,700,680,660};
	static final boolean[] SEEDS_POSITIVE_STRAND= {true,true,true,false,false,false};
	static String folder_fastas="E:\\My Documents\\Lab_FJ\\V.p_genomes\\Vibrio_parahaemolyticus_100138.fna";
	static String[] CATs;
	
	static String str_file_output="E:\\My Documents\\Lab_FJ\\CATs_6_seeds_720_2.txt";
	static File file_output=new File(str_file_output);
	public static void main(String[] args) {
		File[] all_fastas=new File(folder_fastas).listFiles();
		CATs=new String[all_fastas.length];
		try {
			file_output.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// find in files
		for(int i=0,fasta_num=all_fastas.length;i<fasta_num;i++) {			
			printToTxt(">"+all_fastas[i].getName());
			CATs[i]=scanForCAT(all_fastas[i], SEEDS);			
			
			if(CATs[i]==null) {
				printlnToTxt("no CAT found in "+all_fastas[i].getName());
			}else {
				printlnToTxt(CATs[i]);
			}
		}
		printlnToTxt("total: "+temp_flag);
	}
	
	private static void printlnToTxt(String string) {
		// TODO Auto-generated method stub
		CommonMethods.writeStringToFile(string+"\r\n", file_output, false);
	}
	private static void printToTxt(String string) {
		// TODO Auto-generated method stub
		CommonMethods.writeStringToFile(string, file_output, false);
	}

	/**
	 * find CAT in the given fasta
	 * @param file 
	 * the fasta file
	 * @param seeds 
	 * the sequence for finding the CAT
	 * @return
	 */
	private static String scanForCAT(File file, String[] seeds) {
		// TODO Auto-generated method stub
		String lines[]=CommonMethods.readFileToStringArray(file);
		
		// finding in the i th line of the fasta file
		for (int i = 0; i < lines.length; i++) {
			// using the j th seed
			for(int j=0;j<seeds.length;j++) {
				int index_in_linei=lines[i].indexOf(seeds[j]);
				if(index_in_linei>-1) {
					// return the found CAT
					// concat the previous and next CAT_LINE_NUM lines of the current line as the next finding target
					String str_cat_area=concat(lines,i-CAT_LINE_NUM,i+CAT_LINE_NUM);
					// return the CAT
					int pos=str_cat_area.indexOf(seeds[j]);
					String CAT=str_cat_area.substring(pos-SEEDS_POSITION[j], pos-SEEDS_POSITION[j]+CAT_LENGTH);
					if(!SEEDS_POSITIVE_STRAND[j]) {
						CAT=BioMethods.getComplementaryReverseSequence(CAT);
					}
					printlnToTxt("Found by: "+seeds[j]+","+SEEDS_POSITIVE_STRAND[j]);
					temp_flag++;
					return CAT;
				}
			}
		}
		return null;
	}

	private static String concat(String[] lines, int i, int j) {
		// TODO Auto-generated method stub
		i=i>0?i:0;
		j=j<lines.length?j:(lines.length-1);
		String str_return="";
		for(int line=i;line<j+1;line++) {
			str_return+=lines[line];
		}
		return str_return;
	}
}
