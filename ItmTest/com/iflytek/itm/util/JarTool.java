// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JarTool.java

package com.iflytek.itm.util;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.security.CodeSource;
import java.security.ProtectionDomain;

public class JarTool
{

	public JarTool()
	{
	}

	private static File getJarFile()
	{
		String path = com/iflytek/itm/util/JarTool.getProtectionDomain().getCodeSource().getLocation().getPath();
		try
		{
			path = URLDecoder.decode(path, "utf-8");
		}
		catch (Exception e) { }
		return new File(path);
	}

	public static String getJarPath()
	{
		File file = getJarFile();
		return file.getAbsolutePath();
	}

	public static String getJarDir()
	{
		File file = getJarFile();
		if (file == null)
			return null;
		else
			return file.getParent();
	}

	public static String getJarName()
	{
		File file = getJarFile();
		if (file == null)
			return null;
		else
			return file.getName();
	}
}
