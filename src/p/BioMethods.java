package p;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * @version 2017.12.13
 * @author wb
 *
 */
public class BioMethods {
	public static void main(String[] args) {
		System.out.println(getComplementaryReverseSequence("ATCTAGCAA"));
	}
	
	/**
	 * Read fasta file to sequences, throwing sequence names
	 * @param f
	 * @return
	 */
	public static String[] fastaToSeqs(File f) {
		
		
		// list_names are not outputed. Consider revising. 
		List<String> list_names=new ArrayList<String>();
		List<String> list_seqs=new ArrayList<String>();
		
		String strs[]=CommonMethods.readFileToStringArray(f);
		
		for(int i=0;i<strs.length;i++) {
			if(strs[i].contains(">")) {
				list_names.add(strs[i]);
				String seq="";
				for(int j=i+1;j<strs.length;j++) {
					if(strs[j].contains(">")) {
						break;
					}
					seq+=strs[j];
				}
				list_seqs.add(seq);
			}
		}
		return list_seqs.toArray(new String[list_seqs.size()]);
	}
	/**
	 * Read seq from 'f'. Pure sequence file and One-sequence fasta file is acceptable. 
	 * @param f
	 * @return
	 */
	public static String fileToSeq(File f){
		String s="";
		String[] ss=CommonMethods.readFileToStringArray(f);
		for (int i = 1; i < ss.length; i++) {
			s=s+ss[i];
		}
		if(!ss[0].contains(">")){
			s=ss[0]+s;
		}
		return s.toUpperCase();
		
	}
	public static String getComplementaryReverseSequence(String s){
		
		s=s.replaceAll("A","B");
		s=s.replaceAll("a","B");
		s=s.replaceAll("G","H");
		s=s.replaceAll("g","H");
		s=s.replaceAll("C","D");
		s=s.replaceAll("c","D");
		s=s.replaceAll("T","U");
		s=s.replaceAll("t","U");

		s=s.replaceAll("B","T");
		s=s.replaceAll("H","C");
		s=s.replaceAll("D","G");
		s=s.replaceAll("U","A");
		String s1="";

		for (int i=s.length()-1;i>-1;i--)
		{
			s1=s1+s.charAt(i);
		}
		return s1;
	}
}
