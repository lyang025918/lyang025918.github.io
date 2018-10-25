// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryNodeRenderer.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.*;
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;

// Referenced classes of package org.apache.log4j.lf5.viewer.categoryexplorer:
//			CategoryNode

public class CategoryNodeRenderer extends DefaultTreeCellRenderer
{

	private static final long serialVersionUID = 0xac15cbf498d31018L;
	public static final Color FATAL_CHILDREN = new Color(189, 113, 0);
	protected JCheckBox _checkBox;
	protected JPanel _panel;
	protected static ImageIcon _sat = null;

	public CategoryNodeRenderer()
	{
		_checkBox = new JCheckBox();
		_panel = new JPanel();
		_panel.setBackground(UIManager.getColor("Tree.textBackground"));
		if (_sat == null)
		{
			String resource = "/org/apache/log4j/lf5/viewer/images/channelexplorer_satellite.gif";
			java.net.URL satURL = getClass().getResource(resource);
			_sat = new ImageIcon(satURL);
		}
		setOpaque(false);
		_checkBox.setOpaque(false);
		_panel.setOpaque(false);
		_panel.setLayout(new FlowLayout(0, 0, 0));
		_panel.add(_checkBox);
		_panel.add(this);
		setOpenIcon(_sat);
		setClosedIcon(_sat);
		setLeafIcon(_sat);
	}

	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
	{
		CategoryNode node = (CategoryNode)value;
		super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		if (row == 0)
		{
			_checkBox.setVisible(false);
		} else
		{
			_checkBox.setVisible(true);
			_checkBox.setSelected(node.isSelected());
		}
		String toolTip = buildToolTip(node);
		_panel.setToolTipText(toolTip);
		if (node.hasFatalChildren())
			setForeground(FATAL_CHILDREN);
		if (node.hasFatalRecords())
			setForeground(Color.red);
		return _panel;
	}

	public Dimension getCheckBoxOffset()
	{
		return new Dimension(0, 0);
	}

	protected String buildToolTip(CategoryNode node)
	{
		StringBuffer result = new StringBuffer();
		result.append(node.getTitle()).append(" contains a total of ");
		result.append(node.getTotalNumberOfRecords());
		result.append(" LogRecords.");
		result.append(" Right-click for more info.");
		return result.toString();
	}

}
