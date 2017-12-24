package p;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
/**
 * @since long long ago
 * @version 2017.6.9
 * @author wb
 *
 */
public class CommonMethods {
	/**
	 * 
	 * @param format, like this: "yyyyMMdd_HHmmss"
	 * @return
	 */
	public static String getStringForNowInFormat(String format){
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		String DATE=sdf.format(new Date());
		return DATE;
	}
	
	public static String getPathFromKBD(){
        Scanner s = new Scanner(System.in);
        String string =s.nextLine().toString();
        string=string.replaceAll("//", "\\"); 
        string=string.replaceAll("\"", ""); 
        s.close();
        return string;
	}
	public static void main(String args[]) {
		// copyFilesInFolderToFile(new File("D://My Documents//514//鐢熸棩"),new
		// File("z://ALL.txt"));
	}
	public static String getFileNameWithoutExtention(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(0, dot);
			}
		}
		return filename;
	}
	public static String getFileNameExtention(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length()))) {
				return filename.substring(dot+1, filename.length());
			}
		}
		return filename;
	}
	public static String getUserDir(){
		return System.getProperty("user.dir");
	}
	/**
	 * 
	 * @param file is the original file
	 * @param file2 is copy destination
	 */
	public static void copy(File file,File file2){
		InputStream inStream;
		FileOutputStream fs;
		// TODO Auto-generated method stub
		try {

			file2.createNewFile();

			while ((file2.length() != file.length()) || file2.length() == 0) {
				@SuppressWarnings("unused")
				int bytesum = 0;
				int byteread = 0;
				inStream = new FileInputStream(file.getAbsolutePath()); // 读入原文件
				fs = new FileOutputStream(file2.getAbsolutePath());
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Copy all files in 'folder' to 'destiny_file'
	 * 
	 * @param folder
	 *            is the folder that contains txt files
	 * @param destiny_file
	 */
	public static void copyFilesInFolderToFile(File folder, File destiny_file) {
		// TODO Auto-generated method stub
		List<String> list_str = new ArrayList<String>();
		if (!destiny_file.exists()) {
			try {
				destiny_file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File txts[] = folder.listFiles();
		for (File file : txts) {
			String[] temp = readFileToStringArray(file);
			for (String str : temp) {
				list_str.add(str);
			}
		}
		for (String str : list_str) {
			writeStringToFile(str + "\r\n", destiny_file, false);
		}
	}

	public static void pln(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}

	/**
	 * 
	 * @param string
	 *            is the content to write into the file
	 * @param file
	 *            is the destiny file
	 * @param override
	 *            : when true, override the existed file
	 */
	public static void writeStringToFile(String string, File file, boolean override) {
		if (!file.exists()) {
			try {
				file.getAbsoluteFile().getParentFile().mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file, !override);
			bw = new BufferedWriter(fw);
			bw.write(string);
			bw.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			bw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param string
	 *            is the string need to be splited up
	 * @param split_symbol
	 *            is the symbol between those
	 * @param ignoreDuplicateSymbol
	 *            : when true, several consecutive split symbol (e.g. ',,') will
	 *            be seen as one, which means the empty elements will be removed
	 *            from the string array
	 * @return the splited string array
	 */
	public static String[] splitStringToStringArrayList(String string, String split_symbol, boolean ignoreDuplicateSymbol) {
		String[] stra = string.split(split_symbol,-2);
		if (ignoreDuplicateSymbol) {
			List<String> list_str = new ArrayList<String>();
			for (String str : stra) {
				if (!(str.trim().toString().equals("") || str == null)) {
					list_str.add(str);
				}
			}
			stra = (String[]) list_str.toArray(new String[list_str.size()]);
		}
		return stra;
	}

	/**
	 * 
	 * @param file
	 *            is the source file, returns an array of the lines in txt.
	 * @return
	 */
	public static String[] readFileToStringArray(File file) {
		InputStreamReader read = null;
		String lineTXT = null;
		List<String> list_str = new ArrayList<String>();
		try {
			read = new InputStreamReader(new FileInputStream(file), getTxtType(file));
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader bufferedReader = new BufferedReader(read);
		try {
			while ((lineTXT = bufferedReader.readLine()) != null) {
				list_str.add(lineTXT);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] str = list_str.toArray(new String[list_str.size()]);
		return str;
	}

	public static String getTxtType(File file) throws IOException {
		// TODO Auto-generated method stub
		InputStream inputStream = new FileInputStream(file);
		byte[] head = new byte[3];
		inputStream.read(head);
		String code = "";
		code = "gb2312";
		if (head[0] == -1 && head[2] == -2) {
			code = "UTF-16";
		}
		if (head[0] == -2 && head[2] == -1) {
			code = "Unicode";
		}
		if (head[0] == -17 && head[2] == -69) {
			code = "UTF-8";
		}
		inputStream.close();
		return code;
	}

	/**
	 * 
	 * @param keyword
	 *            is the keyword need to search
	 * @return
	 */
	public static FileFilter createFileFilter(final String keyword) {
		FileFilter folders_Filter = new FileFilter() {
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if (pathname.isDirectory()) {
					if (keyword == null) {
						return true;
					} else {
						if (pathname.getName().indexOf(keyword) >= 0) {
							return true;
						} else {
							return false;
						}
					}
				}

				return false;
			}
		};
		return folders_Filter;
	}

	/**
	 * 
	 * @param str_format
	 *            should be like '.png'
	 * @return
	 */
	public static FileFilter createFileFilterForFormat(final String str_format) {
		FileFilter folders_Filter = new FileFilter() {
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if (!pathname.isDirectory()) {
					if (str_format == null) {
						return true;
					} else {
						if (pathname.getName().toLowerCase().endsWith(str_format)) {
							return true;
						} else {
							return false;
						}
					}
				}

				return false;
			}
		};
		return folders_Filter;
	}

}
