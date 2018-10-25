// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CommandLineUtil.java

package org.apache.lucene.util;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public final class CommandLineUtil
{

	private CommandLineUtil()
	{
	}

	public static FSDirectory newFSDirectory(String clazzName, File file)
	{
		Class clazz = loadFSDirectoryClass(clazzName);
		return newFSDirectory(clazz, file);
		ClassNotFoundException e;
		e;
		throw new IllegalArgumentException((new StringBuilder()).append(org/apache/lucene/store/FSDirectory.getSimpleName()).append(" implementation not found: ").append(clazzName).toString(), e);
		e;
		throw new IllegalArgumentException((new StringBuilder()).append(clazzName).append(" is not a ").append(org/apache/lucene/store/FSDirectory.getSimpleName()).append(" implementation").toString(), e);
		e;
		throw new IllegalArgumentException((new StringBuilder()).append(clazzName).append(" constructor with ").append(java/io/File.getSimpleName()).append(" as parameter not found").toString(), e);
		e;
		throw new IllegalArgumentException((new StringBuilder()).append("Error creating ").append(clazzName).append(" instance").toString(), e);
	}

	public static Class loadDirectoryClass(String clazzName)
		throws ClassNotFoundException
	{
		return Class.forName(adjustDirectoryClassName(clazzName)).asSubclass(org/apache/lucene/store/Directory);
	}

	public static Class loadFSDirectoryClass(String clazzName)
		throws ClassNotFoundException
	{
		return Class.forName(adjustDirectoryClassName(clazzName)).asSubclass(org/apache/lucene/store/FSDirectory);
	}

	private static String adjustDirectoryClassName(String clazzName)
	{
		if (clazzName == null || clazzName.trim().length() == 0)
			throw new IllegalArgumentException((new StringBuilder()).append("The ").append(org/apache/lucene/store/FSDirectory.getSimpleName()).append(" implementation cannot be null or empty").toString());
		if (clazzName.indexOf(".") == -1)
			clazzName = (new StringBuilder()).append(org/apache/lucene/store/Directory.getPackage().getName()).append(".").append(clazzName).toString();
		return clazzName;
	}

	public static FSDirectory newFSDirectory(Class clazz, File file)
		throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException
	{
		Constructor ctor = clazz.getConstructor(new Class[] {
			java/io/File
		});
		return (FSDirectory)ctor.newInstance(new Object[] {
			file
		});
	}
}
