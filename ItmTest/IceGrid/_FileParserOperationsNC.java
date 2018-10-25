// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _FileParserOperationsNC.java

package IceGrid;


// Referenced classes of package IceGrid:
//			ParseException, AdminPrx, ApplicationDescriptor

public interface _FileParserOperationsNC
{

	public abstract ApplicationDescriptor parse(String s, AdminPrx adminprx)
		throws ParseException;
}
