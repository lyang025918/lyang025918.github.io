// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileMove.java

package mylib.file;

import java.io.*;

// Referenced classes of package mylib.file:
//			FileInfo

public class FileMove
{

	public FileMove()
	{
	}

	public static boolean move(String srcPath, String destPath)
	{
		File file = new File(srcPath);
		File dir = new File(destPath);
		boolean success = file.renameTo(new File(dir, file.getName()));
		return success;
	}

	public static void copy(String oldPath, String newPath)
	{
		try
		{
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists())
			{
				InputStream inStream = new FileInputStream(oldPath);
				FileOutputStream fs = new FileOutputStream(newPath);
				byte buffer[] = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) 
				{
					bytesum += byteread;
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		}
		catch (Exception e)
		{
			System.out.println("error  ");
			e.printStackTrace();
		}
	}

	public static void main(String args[])
		throws Exception
	{
		File dirDir = new File("e:\\study\\source\\java\\lucene\\data\\500-10w\\");
		File dirFiles[] = dirDir.listFiles();
		int dstFileCnt = 0x186a0;
		int dirFilesCnt = dirFiles.length;
		int gap = dstFileCnt / dirFilesCnt;
		for (int i = 1; i <= dirFilesCnt; i++)
		{
			System.out.println((new StringBuilder()).append("copying ").append(i).toString());
			String srcPath = dirFiles[i - 1].getPath();
			String name = FileInfo.getNameNoExt(srcPath);
			String ext = FileInfo.getExt(srcPath);
			int counter = Integer.parseInt(name);
			String srcDir = dirFiles[i - 1].getParent();
			for (int j = 1; j < gap; j++)
			{
				int newIntName = i + j * dirFilesCnt;
				String newName = (new StringBuilder()).append(String.valueOf(newIntName)).append(".kw").toString();
				copy(srcPath, (new StringBuilder()).append(srcDir).append("/").append(newName).toString());
			}

		}

	}
}
