// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryPath.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.util.LinkedList;
import java.util.StringTokenizer;

// Referenced classes of package org.apache.log4j.lf5.viewer.categoryexplorer:
//			CategoryElement

public class CategoryPath
{

	protected LinkedList _categoryElements;

	public CategoryPath()
	{
		_categoryElements = new LinkedList();
	}

	public CategoryPath(String category)
	{
		_categoryElements = new LinkedList();
		String processedCategory = category;
		if (processedCategory == null)
			processedCategory = "Debug";
		processedCategory = processedCategory.replace('/', '.');
		processedCategory = processedCategory.replace('\\', '.');
		String element;
		for (StringTokenizer st = new StringTokenizer(processedCategory, "."); st.hasMoreTokens(); addCategoryElement(new CategoryElement(element)))
			element = st.nextToken();

	}

	public int size()
	{
		int count = _categoryElements.size();
		return count;
	}

	public boolean isEmpty()
	{
		boolean empty = false;
		if (_categoryElements.size() == 0)
			empty = true;
		return empty;
	}

	public void removeAllCategoryElements()
	{
		_categoryElements.clear();
	}

	public void addCategoryElement(CategoryElement categoryElement)
	{
		_categoryElements.addLast(categoryElement);
	}

	public CategoryElement categoryElementAt(int index)
	{
		return (CategoryElement)_categoryElements.get(index);
	}

	public String toString()
	{
		StringBuffer out = new StringBuffer(100);
		out.append("\n");
		out.append("===========================\n");
		out.append("CategoryPath:                   \n");
		out.append("---------------------------\n");
		out.append("\nCategoryPath:\n\t");
		if (size() > 0)
		{
			for (int i = 0; i < size(); i++)
			{
				out.append(categoryElementAt(i).toString());
				out.append("\n\t");
			}

		} else
		{
			out.append("<<NONE>>");
		}
		out.append("\n");
		out.append("===========================\n");
		return out.toString();
	}
}
