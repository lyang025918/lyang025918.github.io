// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MethodUnion.java

package org.apache.log4j.jmx;

import java.lang.reflect.Method;

class MethodUnion
{

	Method readMethod;
	Method writeMethod;

	MethodUnion(Method readMethod, Method writeMethod)
	{
		this.readMethod = readMethod;
		this.writeMethod = writeMethod;
	}
}
