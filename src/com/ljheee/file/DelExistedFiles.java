package com.ljheee.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DelExistedFiles {

	// 定义一个List，用来存放不重复的文件的MD5值
	private List<String> md5List = new ArrayList<String>();
	
	//<md5 , filepath>存储文件名包含“副本”的文件路径，有重复时优先删除
	private HashMap<String, String> map = new HashMap<>();
	
	static int num = 0;

	/**
	 * 删除指定目录下的所有重复文件
	 * 
	 * @param path
	 *            要删除的文件路径
	 */
	public void delFile(String path) {
		File f = new File(path);
		// 如果给的是一个文件，则继续
		if (f.isFile()) {
			doDelFile(f);
			
		} else if (f.isDirectory()) {
			// 拿到它的子文件列表
			File[] flist = f.listFiles();
			for (File file : flist) {
				if (file.isFile()) {// 如果列表项为File，则继续，同上
					doDelFile(file);
				} else if (file.isDirectory()) {// 如果列表项为文件夹，递归调用自身
					delFile(file.getPath());
				}
			}
		}
	}

	/**
	 * 删除单个文件
	 * @param f
	 */
	public void doDelFile(File f) {
		
		// 取得该文件的MD5值
		String md5 = getMd5(f).toString();
		
		if (f.getName().contains("副本")) {
			map.put(md5, f.getAbsolutePath());
		}

		// 如果存放md5值的数组不为空
		if (md5List.size() > 0) {
			if (md5List.contains(md5)) {
				
				if (f.getName().contains("副本")) {
					f.delete();
					num++;
					System.out.println("文\t" + f.getName() + "\t和已有文件重复，已被删除");
				}else{
					File tp = new File(map.get(md5));//删除文件名包含“副本”的
					tp.delete();
					num++;
					System.out.println("文\t" + tp.getName() + "\t和已有文件重复，已被删除");
				}
				
			}

			// 判断该文件是否存在，如果该文件没有被删除，表明没有和该文件相同的文件，则把其md5值存入数组之中
			if (f.exists()) {
				md5List.add(md5);
			}
		} else {
			// 数组为空，表示还没有相同的项,将把其md5值存入数组之中
			md5List.add(md5);
		}

	}

	/**
	 * 取得文件的Md5值
	 * 
	 * @param file
	 *            要计算的文件
	 * @return MD5值的字符序列
	 */
	public static StringBuilder getMd5(File file) {
		StringBuilder sb = new StringBuilder();
		StringBuilder fileNotFound = new StringBuilder("系统找不到指定的路径,请重新输入");
		StringBuilder noAlgorith = new StringBuilder("无法进行Md5加密算法，可能是因为的java虚拟机版本太低");
		StringBuilder IOError = new StringBuilder("IO错误");
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5"); // 生产MD5类的实例
			FileInputStream in = new FileInputStream(file);
			BufferedInputStream bs = new BufferedInputStream(in);
			byte[] b = new byte[bs.available()]; // 定义数组b为文件不受阻塞的可读取字节数

			// 将文件以字节方式读取到数组b中
			while ((bs.read(b, 0, b.length)) != -1) {
			}
			md5.update(b); // 执行md5算法
			for (byte by : md5.digest()) {
				sb.append(String.format("%02X", by)); // 将生成的字节转化成16进制的字符串
			}
			bs.close();
		} catch (NoSuchAlgorithmException e) {
			return noAlgorith;
		} catch (FileNotFoundException e) {
			return fileNotFound;
		} catch (IOException e) {
			return IOError;
		}
		return sb;
	}

	public static void main(String[] args) {
		DelExistedFiles df = new DelExistedFiles();
		df.delFile("E:\\oppo2");
		System.out.println("操作结束，共删除了\t" + num + "\t个重复文件");
	}

}
