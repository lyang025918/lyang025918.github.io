// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NumericConfig.java

package org.apache.lucene.queryparser.flexible.standard.config;

import java.text.NumberFormat;
import org.apache.lucene.document.FieldType;

public class NumericConfig
{

	private int precisionStep;
	private NumberFormat format;
	private org.apache.lucene.document.FieldType.NumericType type;

	public NumericConfig(int precisionStep, NumberFormat format, org.apache.lucene.document.FieldType.NumericType type)
	{
		setPrecisionStep(precisionStep);
		setNumberFormat(format);
		setType(type);
	}

	public int getPrecisionStep()
	{
		return precisionStep;
	}

	public void setPrecisionStep(int precisionStep)
	{
		this.precisionStep = precisionStep;
	}

	public NumberFormat getNumberFormat()
	{
		return format;
	}

	public org.apache.lucene.document.FieldType.NumericType getType()
	{
		return type;
	}

	public void setType(org.apache.lucene.document.FieldType.NumericType type)
	{
		if (type == null)
		{
			throw new IllegalArgumentException("type cannot be null!");
		} else
		{
			this.type = type;
			return;
		}
	}

	public void setNumberFormat(NumberFormat format)
	{
		if (format == null)
		{
			throw new IllegalArgumentException("format cannot be null!");
		} else
		{
			this.format = format;
			return;
		}
	}

	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj instanceof NumericConfig)
		{
			NumericConfig other = (NumericConfig)obj;
			if (precisionStep == other.precisionStep && type == other.type && (format == other.format || format.equals(other.format)))
				return true;
		}
		return false;
	}
}
