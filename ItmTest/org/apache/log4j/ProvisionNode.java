// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ProvisionNode.java

package org.apache.log4j;

import java.util.Vector;

// Referenced classes of package org.apache.log4j:
//			Logger

class ProvisionNode extends Vector
{

	private static final long serialVersionUID = 0xc1d6f6cb4ee417bbL;

	ProvisionNode(Logger logger)
	{
		addElement(logger);
	}
}
