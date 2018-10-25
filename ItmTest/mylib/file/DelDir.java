// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DelDir.java

package mylib.file;

import java.io.File;
import java.io.PrintStream;

public class DelDir
{

	public DelDir()
	{
	}

	public static boolean doDel(String path)
	{
		if (null == path || path.trim().equals(""))
			return false;
		File dir = new File(path);
		if (!dir.exists())
			return false;
		if (dir.isFile())
			return dir.delete();
		if (dir.isDirectory())
		{
			String children[] = dir.list();
			for (int i = 0; i < children.length; i++)
			{
				boolean success = doDel((new StringBuilder()).append(dir).append("/").append(children[i]).toString());
				if (!success)
					return false;
			}

			return dir.delete();
		} else
		{
			System.out.printf("DelDir.delDir | Failed, neither file nor dir, path=[%s]\n", new Object[] {
				path
			});
			return false;
		}
	}

	public static void main(String args[])
	{
		boolean beret = false;
		String path1 = "e:\\download\\test_del.txt";
		beret = doDel(path1);
		System.out.println(beret);
		String path2 = "e:\\download\\empty\\";
		beret = doDel(path2);
		System.out.println(beret);
		String path3 = "e:\\download\\file\\";
		beret = doDel(path3);
		System.out.println(beret);
	}
}
