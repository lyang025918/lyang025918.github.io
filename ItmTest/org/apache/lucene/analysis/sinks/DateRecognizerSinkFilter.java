// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DateRecognizerSinkFilter.java

package org.apache.lucene.analysis.sinks;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeSource;

// Referenced classes of package org.apache.lucene.analysis.sinks:
//			TeeSinkTokenFilter

public class DateRecognizerSinkFilter extends TeeSinkTokenFilter.SinkFilter
{

	public static final String DATE_TYPE = "date";
	protected DateFormat dateFormat;
	protected CharTermAttribute termAtt;

	public DateRecognizerSinkFilter()
	{
		this(DateFormat.getDateInstance(2, Locale.ROOT));
	}

	public DateRecognizerSinkFilter(DateFormat dateFormat)
	{
		this.dateFormat = dateFormat;
	}

	public boolean accept(AttributeSource source)
	{
		if (termAtt == null)
			termAtt = (CharTermAttribute)source.addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		java.util.Date date = dateFormat.parse(termAtt.toString());
		if (date != null)
			return true;
		break MISSING_BLOCK_LABEL_46;
		ParseException e;
		e;
		return false;
	}
}
