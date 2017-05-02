package com.ljheee.file;

import java.io.*;

/**
 * 文件操作
 * @author ljheee
 *
 */
public class FileOperate {
	
	static File temp;
	
	/**
	 * 在路径下文件名转小写
	 * @param f
	 */
	public static void renameFile(File f){
		
		String dir = f.getAbsolutePath();
		String upStr = f.getName();
		
		String path = dir.substring(0,dir.lastIndexOf("\\")+1);  
        
		System.out.println("path="+path+"-----upFileName="+upStr);
		temp= new File(path+upStr.toLowerCase());
		f.renameTo(temp);
	}
	
	/**
	 * 目录下-所有文件名转小写
	 * @param f
	 */
	public static void renameDir(File f){
		File[] fs = f.listFiles();
		for (File file : fs) {
			if(file.isFile()){
				renameFile(file);
			}else{
				renameDir(file);
			}
		}
		//Todo文件夹名，未改
	}
	
	
	
	
	

	public static void main(String[] args) {

		renameDir(new File("C:\\Users\\Administrator\\Downloads\\30天学通JavaWeb项目案例开发源码\\30天学通Java Web项目案例开发源码\\CHAP05"));
	
	}

}
