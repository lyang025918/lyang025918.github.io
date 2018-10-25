// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FieldConfig.java

package org.apache.lucene.queryparser.flexible.core.config;


// Referenced classes of package org.apache.lucene.queryparser.flexible.core.config:
//			AbstractQueryConfig

public class FieldConfig extends AbstractQueryConfig
{

	private String fieldName;

	public FieldConfig(String fieldName)
	{
		if (fieldName == null)
		{
			throw new IllegalArgumentException("field name should not be null!");
		} else
		{
			this.fieldName = fieldName;
			return;
		}
	}

	public String getField()
	{
		return fieldName;
	}

	public String toString()
	{
		return (new StringBuilder()).append("<fieldconfig name=\"").append(fieldName).append("\" configurations=\"").append(super.toString()).append("\"/>").toString();
	}
}
