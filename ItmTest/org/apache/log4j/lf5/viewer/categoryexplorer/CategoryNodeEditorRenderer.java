// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryNodeEditorRenderer.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTree;

// Referenced classes of package org.apache.log4j.lf5.viewer.categoryexplorer:
//			CategoryNodeRenderer

public class CategoryNodeEditorRenderer extends CategoryNodeRenderer
{

	private static final long serialVersionUID = 0xab6ae76e6b4cd21aL;

	public CategoryNodeEditorRenderer()
	{
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		return c;
	}

	public JCheckBox getCheckBox()
	{
		return _checkBox;
	}
}
