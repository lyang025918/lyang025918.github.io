// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileDownloader.java

package mylib.file;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileDownloader
{

	private static int BUFFER_SIZE = 8096;

	public FileDownloader()
	{
	}

	public static void doDownload(String destUrl, String fileName)
		throws IOException
	{
		URL url = new URL(destUrl);
		HttpURLConnection httpUrl = (HttpURLConnection)url.openConnection();
		httpUrl.connect();
		BufferedInputStream bis = new BufferedInputStream(httpUrl.getInputStream());
		FileOutputStream fos = new FileOutputStream(fileName);
		byte buf[] = new byte[BUFFER_SIZE];
		for (int size = 0; (size = bis.read(buf)) != -1;)
			fos.write(buf, 0, size);

		fos.close();
		bis.close();
		httpUrl.disconnect();
	}

	public static void main(String argv[])
	{
		FileDownloader inst = new FileDownloader();
		try
		{
			FileDownloader  = inst;
			doDownload("http://www.ebook.com/java/ÍøÂç±à³Ì001.zip", "./down.zip");
		}
		catch (Exception err)
		{
			System.out.println(err.getMessage());
		}
	}

}
