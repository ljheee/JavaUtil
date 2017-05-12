package com.ljheee.file;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
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
		}else{
			dirEncoding2UTF8(file, endstaf);
		}
	}
	
	/**
	 * 将单个file文件encoding转成UTF-8
	 * @param f
	 */
	public static void fileEncoding2UTF8(File f){
		File temp = new File("temp.txt");
		if(temp.exists()){
			try {
				temp.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		ByteArrayInputStream bis = new ByteArrayInputStream("".getBytes());
		
	}
	
	
	
	/**
	 * 将dir目录下所有文件encoding转成UTF-8
	 * @param f
	 * @param endstaf
	 */
	public static void dirEncoding2UTF8(File f,String endstaf){
		File[] fs = f.listFiles();
		for (File file : fs) {
			if(file.isFile()&&file.getName().endsWith(endstaf)){
				fileEncoding2UTF8(file);
			}else{
				dirEncoding2UTF8(file, endstaf);
			}
		}
	}
	
	
	
	
	public static void main(String[] args) {
		
		
	}


}
