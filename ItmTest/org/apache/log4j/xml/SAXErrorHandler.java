// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SAXErrorHandler.java

package org.apache.log4j.xml;

import org.apache.log4j.helpers.LogLog;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

public class SAXErrorHandler
	implements ErrorHandler
{

	public SAXErrorHandler()
	{
	}

	public void error(SAXParseException ex)
	{
		emitMessage("Continuable parsing error ", ex);
	}

	public void fatalError(SAXParseException ex)
	{
		emitMessage("Fatal parsing error ", ex);
	}

	public void warning(SAXParseException ex)
	{
		emitMessage("Parsing warning ", ex);
	}

	private static void emitMessage(String msg, SAXParseException ex)
	{
		LogLog.warn(msg + ex.getLineNumber() + " and column " + ex.getColumnNumber());
		LogLog.warn(ex.getMessage(), ex.getException());
	}
}
