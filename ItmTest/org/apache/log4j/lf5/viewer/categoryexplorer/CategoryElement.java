// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryElement.java

package org.apache.log4j.lf5.viewer.categoryexplorer;


public class CategoryElement
{

	protected String _categoryTitle;

	public CategoryElement()
	{
	}

	public CategoryElement(String title)
	{
		_categoryTitle = title;
	}

	public String getTitle()
	{
		return _categoryTitle;
	}

	public void setTitle(String title)
	{
		_categoryTitle = title;
	}
}
