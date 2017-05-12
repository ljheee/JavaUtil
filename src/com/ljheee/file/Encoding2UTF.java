package com.ljheee.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * 文件编码encoding转化工具
 * @author ljheee
 *
 */
public class Encoding2UTF {
	
	/**
	 * 将file文件[后缀endstaf]encoding转成UTF-8
	 * @param file
	 * @param endstaf
	 */
	public static void toUTF8(File file,String endstaf) {
		
		if(file==null||!file.exists()){
			return;
		}
		
		if(file.isFile()&&file.getName().endsWith(endstaf)){
			fileEncoding2UTF8(file);
		}else if(file.isDirectory()){
			dirEncoding2UTF8(file, endstaf);
		}
	}
	
	/**
	 * 将单个file文件encoding转成UTF-8
	 * @param f
	 */
	public static void fileEncoding2UTF8(File f){
		File temp = new File("temp.txt");
		if(!temp.exists()){
			try {
				temp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		setEmptyFile(temp);
		
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new FileReader(f));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(temp),"UTF-8"));
			String line;
			String encoding;
			for (line = br.readLine(),encoding = getEncoding(line); null != line; line = br.readLine()) {
				System.out.println("encoding="+encoding);
				bw.write(new String(line.getBytes(encoding),"UTF-8"));
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(br != null) br.close();
				if(bw != null) bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		setEmptyFile(f);
		try {
			br = new BufferedReader(new FileReader(temp));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f),"UTF-8"));
			String line;
			while(null != (line = br.readLine())){
				bw.write(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
				try {
					if(br != null) br.close();
					if(bw != null) bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		setEmptyFile(temp);
		System.out.println("fileEncoding2UTF8="+f.getName());
	}
	
	
	
	/**
	 * 清空file内容
	 * @param file
	 */
	private static void setEmptyFile(File file) {
		try {
			FileWriter fileWriter = new FileWriter(file,false);//第二个参数：不追加，清空内容
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将dir目录下所有文件encoding转成UTF-8
	 * @param f
	 * @param endstaf
	 */
	public static void dirEncoding2UTF8(File f,String endstaf){
		File[] fs = f.listFiles();
		if(fs != null&&fs.length > 0){
			for (File file : fs) {
				if(file.isFile()&&file.getName().endsWith(endstaf)){
					fileEncoding2UTF8(file);
				}else{
					dirEncoding2UTF8(file, endstaf);
				}
			}
		}
	}
	
	
	/**
	 * 获取str字符串的encoding
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getEncoding(String str) throws UnsupportedEncodingException {
		String result = null;
		if (str.equals(new String(str.getBytes("GB2312"), "GB2312"))) {  
			result = "GB2312";
		}else if(str.equals(new String(str.getBytes("ISO-8859-1"), "ISO-8859-1"))){
			result = "ISO-8859-1";
		}else if(str.equals(new String(str.getBytes("UTF-8"), "UTF-8"))){
			result = "UTF-8";
		}else if(str.equals(new String(str.getBytes("GBK"), "GBK"))){
			result = "GBK";
		}else{
			result = "";
		}
		return result;  
	}
	
	
	
	public static void main(String[] args) {
		
		toUTF8(new File("C:/2"), ".txt");
	}


}
