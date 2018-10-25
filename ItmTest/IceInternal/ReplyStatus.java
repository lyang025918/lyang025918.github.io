// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReplyStatus.java

package IceInternal;


interface ReplyStatus
{

	public static final byte replyOK = 0;
	public static final byte replyUserException = 1;
	public static final byte replyObjectNotExist = 2;
	public static final byte replyFacetNotExist = 3;
	public static final byte replyOperationNotExist = 4;
	public static final byte replyUnknownLocalException = 5;
	public static final byte replyUnknownUserException = 6;
	public static final byte replyUnknownException = 7;
}
