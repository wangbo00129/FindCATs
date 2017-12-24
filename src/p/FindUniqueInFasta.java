package p;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FindUniqueInFasta {
	
	static String str_file_fasta="E:\\My Documents\\Lab_FJ\\CAT\\translatedÉ¾µô¶ÌµÄ.txt";
	static List<String> list_names=new ArrayList<String>();
	static List<String> list_seqs=new ArrayList<String>();
	static HashSet<String> set_seqs=new HashSet<String>();
	public static void main(String[] args) {		
		// read fasta to list_names and list_seqs
		File file_fasta=new File(str_file_fasta);
		String strs[]=CommonMethods.readFileToStringArray(file_fasta);
		
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
		System.out.println(list_names.size());
		System.out.println(list_seqs.size());
		
		// get set of list_seqs
		for (int i = 0; i < list_seqs.size(); i++) {
			set_seqs.add(list_seqs.get(i));
		}
		System.out.println("diversity: "+set_seqs.size());
		String[] arr_set_seqs = null;
		arr_set_seqs=set_seqs.toArray(new String[set_seqs.size()]);
		
		
		// print every set
		for (int i = 0; i < arr_set_seqs.length; i++) {
			
			int current_num=0;
			for (int j = 0; j < list_seqs.size(); j++) {
				if(arr_set_seqs[i].equals(list_seqs.get(j))) {
					System.out.println(list_names.get(j));
					current_num++;
				}
			}
			System.out.println(arr_set_seqs[i]+"\t"+current_num);
		}
		
	}
}
