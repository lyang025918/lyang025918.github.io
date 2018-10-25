// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Among.java

package org.tartarus.snowball;

import java.lang.reflect.Method;

// Referenced classes of package org.tartarus.snowball:
//			SnowballProgram

public class Among
{

	private static final Class EMPTY_PARAMS[] = new Class[0];
	public final int s_size;
	public final char s[];
	public final int substring_i;
	public final int result;
	public final Method method;
	public final SnowballProgram methodobject;

	public Among(String s, int substring_i, int result, String methodname, SnowballProgram methodobject)
	{
		s_size = s.length();
		this.s = s.toCharArray();
		this.substring_i = substring_i;
		this.result = result;
		this.methodobject = methodobject;
		if (methodname.length() == 0)
			method = null;
		else
			try
			{
				method = methodobject.getClass().getDeclaredMethod(methodname, EMPTY_PARAMS);
			}
			catch (NoSuchMethodException e)
			{
				throw new RuntimeException(e);
			}
	}

}
