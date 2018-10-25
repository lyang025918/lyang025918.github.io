// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryExplorerLogRecordFilter.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.util.Enumeration;
import org.apache.log4j.lf5.LogRecord;
import org.apache.log4j.lf5.LogRecordFilter;

// Referenced classes of package org.apache.log4j.lf5.viewer.categoryexplorer:
//			CategoryPath, CategoryNode, CategoryExplorerModel

public class CategoryExplorerLogRecordFilter
	implements LogRecordFilter
{

	protected CategoryExplorerModel _model;

	public CategoryExplorerLogRecordFilter(CategoryExplorerModel model)
	{
		_model = model;
	}

	public boolean passes(LogRecord record)
	{
		CategoryPath path = new CategoryPath(record.getCategory());
		return _model.isCategoryPathActive(path);
	}

	public void reset()
	{
		resetAllNodes();
	}

	protected void resetAllNodes()
	{
		CategoryNode current;
		for (Enumeration nodes = _model.getRootCategoryNode().depthFirstEnumeration(); nodes.hasMoreElements(); _model.nodeChanged(current))
		{
			current = (CategoryNode)nodes.nextElement();
			current.resetNumberOfContainedRecords();
		}

	}
}
