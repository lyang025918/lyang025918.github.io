// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectInputStream.java

package IceInternal;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamClass;

// Referenced classes of package IceInternal:
//			Instance

public class ObjectInputStream extends java.io.ObjectInputStream
{

	private Instance _instance;

	public ObjectInputStream(Instance instance, InputStream in)
		throws IOException
	{
		super(in);
		_instance = instance;
	}

	protected Class resolveClass(ObjectStreamClass cls)
		throws IOException, ClassNotFoundException
	{
		Class c = _instance.findClass(cls.getName());
		if (c != null)
			return c;
		try
		{
			throw new ClassNotFoundException((new StringBuilder()).append("unable to resolve class").append(cls.getName()).toString());
		}
		catch (Exception ex)
		{
			throw new ClassNotFoundException((new StringBuilder()).append("unable to resolve class ").append(cls.getName()).toString(), ex);
		}
	}
}
