// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryNodeEditor.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Component;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.*;
import javax.swing.tree.TreePath;

// Referenced classes of package org.apache.log4j.lf5.viewer.categoryexplorer:
//			CategoryAbstractCellEditor, CategoryNodeEditorRenderer, CategoryNode, CategoryExplorerModel

public class CategoryNodeEditor extends CategoryAbstractCellEditor
{

	protected CategoryNodeEditorRenderer _renderer;
	protected CategoryNode _lastEditedNode;
	protected JCheckBox _checkBox;
	protected CategoryExplorerModel _categoryModel;
	protected JTree _tree;

	public CategoryNodeEditor(CategoryExplorerModel model)
	{
		_renderer = new CategoryNodeEditorRenderer();
		_checkBox = _renderer.getCheckBox();
		_categoryModel = model;
		_checkBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				_categoryModel.update(_lastEditedNode, _checkBox.isSelected());
				stopCellEditing();
			}

			
			{
				super();
			}
		});
		_renderer.addMouseListener(new MouseAdapter() {

			public void mousePressed(MouseEvent e)
			{
				if ((e.getModifiers() & 4) != 0)
					showPopup(_lastEditedNode, e.getX(), e.getY());
				stopCellEditing();
			}

			
			{
				super();
			}
		});
	}

	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row)
	{
		_lastEditedNode = (CategoryNode)value;
		_tree = tree;
		return _renderer.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, true);
	}

	public Object getCellEditorValue()
	{
		return _lastEditedNode.getUserObject();
	}

	protected JMenuItem createPropertiesMenuItem(final CategoryNode node)
	{
		JMenuItem result = new JMenuItem("Properties");
		result.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				showPropertiesDialog(node);
			}

			
			{
				super();
			}
		});
		return result;
	}

	protected void showPropertiesDialog(CategoryNode node)
	{
		JOptionPane.showMessageDialog(_tree, getDisplayedProperties(node), "Category Properties: " + node.getTitle(), -1);
	}

	protected Object getDisplayedProperties(CategoryNode node)
	{
		ArrayList result = new ArrayList();
		result.add("Category: " + node.getTitle());
		if (node.hasFatalRecords())
			result.add("Contains at least one fatal LogRecord.");
		if (node.hasFatalChildren())
			result.add("Contains descendants with a fatal LogRecord.");
		result.add("LogRecords in this category alone: " + node.getNumberOfContainedRecords());
		result.add("LogRecords in descendant categories: " + node.getNumberOfRecordsFromChildren());
		result.add("LogRecords in this category including descendants: " + node.getTotalNumberOfRecords());
		return ((Object) (result.toArray()));
	}

	protected void showPopup(CategoryNode node, int x, int y)
	{
		JPopupMenu popup = new JPopupMenu();
		popup.setSize(150, 400);
		if (node.getParent() == null)
		{
			popup.add(createRemoveMenuItem());
			popup.addSeparator();
		}
		popup.add(createSelectDescendantsMenuItem(node));
		popup.add(createUnselectDescendantsMenuItem(node));
		popup.addSeparator();
		popup.add(createExpandMenuItem(node));
		popup.add(createCollapseMenuItem(node));
		popup.addSeparator();
		popup.add(createPropertiesMenuItem(node));
		popup.show(_renderer, x, y);
	}

	protected JMenuItem createSelectDescendantsMenuItem(final CategoryNode node)
	{
		JMenuItem selectDescendants = new JMenuItem("Select All Descendant Categories");
		selectDescendants.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				_categoryModel.setDescendantSelection(node, true);
			}

			
			{
				super();
			}
		});
		return selectDescendants;
	}

	protected JMenuItem createUnselectDescendantsMenuItem(final CategoryNode node)
	{
		JMenuItem unselectDescendants = new JMenuItem("Deselect All Descendant Categories");
		unselectDescendants.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				_categoryModel.setDescendantSelection(node, false);
			}

			
			{
				super();
			}
		});
		return unselectDescendants;
	}

	protected JMenuItem createExpandMenuItem(final CategoryNode node)
	{
		JMenuItem result = new JMenuItem("Expand All Descendant Categories");
		result.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				expandDescendants(node);
			}

			
			{
				super();
			}
		});
		return result;
	}

	protected JMenuItem createCollapseMenuItem(final CategoryNode node)
	{
		JMenuItem result = new JMenuItem("Collapse All Descendant Categories");
		result.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				collapseDescendants(node);
			}

			
			{
				super();
			}
		});
		return result;
	}

	protected JMenuItem createRemoveMenuItem()
	{
		JMenuItem result = new JMenuItem("Remove All Empty Categories");
		result.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				while (removeUnusedNodes() > 0) ;
			}

			
			{
				super();
			}
		});
		return result;
	}

	protected void expandDescendants(CategoryNode node)
	{
		CategoryNode current;
		for (Enumeration descendants = node.depthFirstEnumeration(); descendants.hasMoreElements(); expand(current))
			current = (CategoryNode)descendants.nextElement();

	}

	protected void collapseDescendants(CategoryNode node)
	{
		CategoryNode current;
		for (Enumeration descendants = node.depthFirstEnumeration(); descendants.hasMoreElements(); collapse(current))
			current = (CategoryNode)descendants.nextElement();

	}

	protected int removeUnusedNodes()
	{
		int count = 0;
		CategoryNode root = _categoryModel.getRootCategoryNode();
		Enumeration enumeration = root.depthFirstEnumeration();
		do
		{
			if (!enumeration.hasMoreElements())
				break;
			CategoryNode node = (CategoryNode)enumeration.nextElement();
			if (node.isLeaf() && node.getNumberOfContainedRecords() == 0 && node.getParent() != null)
			{
				_categoryModel.removeNodeFromParent(node);
				count++;
			}
		} while (true);
		return count;
	}

	protected void expand(CategoryNode node)
	{
		_tree.expandPath(getTreePath(node));
	}

	protected TreePath getTreePath(CategoryNode node)
	{
		return new TreePath(node.getPath());
	}

	protected void collapse(CategoryNode node)
	{
		_tree.collapsePath(getTreePath(node));
	}
}
