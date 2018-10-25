// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileInfo.java

package mylib.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileInfo
{

	public FileInfo()
	{
	}

	public static String getNameNoExt(String path)
	{
		return getNameNoExt(new File(path));
	}

	public static String getNameNoExt(File fh)
	{
		String prefix = "";
		if (null != fh)
		{
			String fileName = fh.getName();
			prefix = fileName.substring(0, fileName.lastIndexOf("."));
		}
		return prefix;
	}

	public static String getExt(String path)
	{
		return getExt(new File(path));
	}

	public static String getExt(File fh)
	{
		String suffix = "";
		if (null != fh)
		{
			String fileName = fh.getName();
			suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		}
		return suffix;
	}

	public static String getContent(String path, String charSet)
	{
		return getContent(new File(path), charSet);
	}

	public static String getContent(File file, String charSet)
	{
		char buffer[] = null;
		try
		{
			int len = (int)file.length();
			buffer = new char[len];
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis, charSet));
			br.read(buffer, 0, len);
			fis.close();
		}
		catch (Exception e)
		{
			System.out.printf("mylib.file.FileInfo | Error: %s", new Object[] {
				e.getMessage()
			});
		}
		return new String(buffer);
	}

	public static int loadFileWords(String path, List words)
	{
		return loadFileWords(new File(path), "gbk", words);
	}

	public static int loadFileWords(File file, List words)
	{
		return loadFileWords(file, "gbk", words);
	}

	public static int loadFileWords(String path, String charSet, List words)
	{
		return loadFileWords(new File(path), charSet, words);
	}

	public static int loadFileWords(File file, String charSet, List words)
	{
		int ret = 0;
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSet));
			String line = null;
			do
			{
				if ((line = br.readLine()) == null)
					break;
				line = line.trim();
				if (!line.isEmpty())
					words.add(line.trim());
			} while (true);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			ret = -1;
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
			ret = -2;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			ret = -3;
		}
		return ret;
	}

	public static void main(String args[])
	{
		String path = "e:\\download\\test.txt";
		String ext = getExt(path);
		String noext = getNameNoExt(path);
		System.out.printf("path=[%s], ext=[%s], noext=[%s]\n", new Object[] {
			path, ext, noext
		});
		String content = getContent(path, "gbk");
		System.out.println(content);
		ArrayList words = new ArrayList();
		int ret = loadFileWords(path, words);
	}
}
