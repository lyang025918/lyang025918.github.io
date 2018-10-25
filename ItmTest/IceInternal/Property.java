// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Property.java

package IceInternal;


public class Property
{

	private String _pattern;
	private boolean _deprecated;
	private String _deprecatedBy;

	public Property(String pattern, boolean deprecated, String deprecatedBy)
	{
		_pattern = pattern;
		_deprecated = deprecated;
		_deprecatedBy = deprecatedBy;
	}

	public String pattern()
	{
		return _pattern;
	}

	public boolean deprecated()
	{
		return _deprecated;
	}

	public String deprecatedBy()
	{
		return _deprecatedBy;
	}
}
