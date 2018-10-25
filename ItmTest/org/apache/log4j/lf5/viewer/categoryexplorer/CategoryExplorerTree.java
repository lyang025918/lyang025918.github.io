// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CategoryExplorerTree.java

package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.event.MouseEvent;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.tree.TreePath;

// Referenced classes of package org.apache.log4j.lf5.viewer.categoryexplorer:
//			CategoryNode, CategoryExplorerModel, CategoryNodeRenderer, CategoryNodeEditor, 
//			CategoryImmediateEditor, TreeModelAdapter

public class CategoryExplorerTree extends JTree
{

	private static final long serialVersionUID = 0x6ff11a69406207b8L;
	protected CategoryExplorerModel _model;
	protected boolean _rootAlreadyExpanded;

	public CategoryExplorerTree(CategoryExplorerModel model)
	{
		super(model);
		_rootAlreadyExpanded = false;
		_model = model;
		init();
	}

	public CategoryExplorerTree()
	{
		_rootAlreadyExpanded = false;
		CategoryNode rootNode = new CategoryNode("Categories");
		_model = new CategoryExplorerModel(rootNode);
		setModel(_model);
		init();
	}

	public CategoryExplorerModel getExplorerModel()
	{
		return _model;
	}

	public String getToolTipText(MouseEvent e)
	{
		return super.getToolTipText(e);
		Exception ex;
		ex;
		return "";
	}

	protected void init()
	{
		putClientProperty("JTree.lineStyle", "Angled");
		CategoryNodeRenderer renderer = new CategoryNodeRenderer();
		setEditable(true);
		setCellRenderer(renderer);
		CategoryNodeEditor editor = new CategoryNodeEditor(_model);
		setCellEditor(new CategoryImmediateEditor(this, new CategoryNodeRenderer(), editor));
		setShowsRootHandles(true);
		setToolTipText("");
		ensureRootExpansion();
	}

	protected void expandRootNode()
	{
		if (_rootAlreadyExpanded)
		{
			return;
		} else
		{
			_rootAlreadyExpanded = true;
			TreePath path = new TreePath(_model.getRootCategoryNode().getPath());
			expandPath(path);
			return;
		}
	}

	protected void ensureRootExpansion()
	{
		_model.addTreeModelListener(new TreeModelAdapter() {

			public void treeNodesInserted(TreeModelEvent e)
			{
				expandRootNode();
			}

			
			{
				super();
			}
		});
	}
}
