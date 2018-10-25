// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectSerial.java

package com.iflytek.itm.mining.worldlet;

import java.io.*;

public class ObjectSerial
{

	public ObjectSerial()
	{
	}

	public static int writeObject(Object object, String path)
	{
		int ret;
		File file;
		FileOutputStream fileOutputStream;
		ObjectOutputStream objectOutputStream;
		ret = 0;
		file = new File(path);
		if (file.exists())
			file.delete();
		fileOutputStream = null;
		objectOutputStream = null;
		fileOutputStream = new FileOutputStream(file);
		objectOutputStream = new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(object);
		try
		{
			if (null != objectOutputStream)
				objectOutputStream.close();
			if (null != fileOutputStream)
				fileOutputStream.close();
		}
		catch (IOException e1)
		{
			ret = -2;
		}
		break MISSING_BLOCK_LABEL_166;
		IOException e;
		e;
		ret = -1;
		e.printStackTrace();
		try
		{
			if (null != objectOutputStream)
				objectOutputStream.close();
			if (null != fileOutputStream)
				fileOutputStream.close();
		}
		catch (IOException e1)
		{
			ret = -2;
		}
		break MISSING_BLOCK_LABEL_166;
		Exception exception;
		exception;
		try
		{
			if (null != objectOutputStream)
				objectOutputStream.close();
			if (null != fileOutputStream)
				fileOutputStream.close();
		}
		catch (IOException e1)
		{
			ret = -2;
		}
		throw exception;
		return ret;
	}

	public static Object readObject(File file)
	{
		InputStream inputStream;
		Object object;
		inputStream = null;
		ObjectInputStream objectInputStream = null;
		object = null;
		inputStream = new FileInputStream(file);
		ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
		object = objectInputStream.readObject();
		FileNotFoundException e;
		try
		{
			if (null != inputStream)
				inputStream.close();
		}
		// Misplaced declaration of an exception variable
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_162;
		e;
		e.printStackTrace();
		try
		{
			if (null != inputStream)
				inputStream.close();
		}
		// Misplaced declaration of an exception variable
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_162;
		e;
		e.printStackTrace();
		try
		{
			if (null != inputStream)
				inputStream.close();
		}
		// Misplaced declaration of an exception variable
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_162;
		e;
		e.printStackTrace();
		try
		{
			if (null != inputStream)
				inputStream.close();
		}
		// Misplaced declaration of an exception variable
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_162;
		Exception exception;
		exception;
		try
		{
			if (null != inputStream)
				inputStream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		throw exception;
		return object;
	}
}
