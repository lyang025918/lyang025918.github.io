// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryImmediateEditor.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.TreePath;

// Referenced classes of package org.apache.log4j.lf5.viewer.categoryexplorer:
//			CategoryNode, CategoryNodeRenderer, CategoryNodeEditor

public class CategoryImmediateEditor extends DefaultTreeCellEditor
{

	private CategoryNodeRenderer renderer;
	protected Icon editingIcon;

	public CategoryImmediateEditor(JTree tree, CategoryNodeRenderer renderer, CategoryNodeEditor editor)
	{
		super(tree, renderer, editor);
		editingIcon = null;
		this.renderer = renderer;
		renderer.setIcon(null);
		renderer.setLeafIcon(null);
		renderer.setOpenIcon(null);
		renderer.setClosedIcon(null);
		super.editingIcon = null;
	}

	public boolean shouldSelectCell(EventObject e)
	{
		boolean rv = false;
		if (e instanceof MouseEvent)
		{
			MouseEvent me = (MouseEvent)e;
			TreePath path = tree.getPathForLocation(me.getX(), me.getY());
			CategoryNode node = (CategoryNode)path.getLastPathComponent();
			rv = node.isLeaf();
		}
		return rv;
	}

	public boolean inCheckBoxHitRegion(MouseEvent e)
	{
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		if (path == null)
		{
			return false;
		} else
		{
			CategoryNode node = (CategoryNode)path.getLastPathComponent();
			boolean rv = false;
			Rectangle bounds = tree.getRowBounds(lastRow);
			Dimension checkBoxOffset = renderer.getCheckBoxOffset();
			bounds.translate(offset + checkBoxOffset.width, checkBoxOffset.height);
			rv = bounds.contains(e.getPoint());
			return true;
		}
	}

	protected boolean canEditImmediately(EventObject e)
	{
		boolean rv = false;
		if (e instanceof MouseEvent)
		{
			MouseEvent me = (MouseEvent)e;
			rv = inCheckBoxHitRegion(me);
		}
		return rv;
	}

	protected void determineOffset(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row)
	{
		offset = 0;
	}
}
