// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ControlPanel.java

package org.apache.log4j.chainsaw;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import org.apache.log4j.*;

// Referenced classes of package org.apache.log4j.chainsaw:
//			MyTableModel, ExitAction

class ControlPanel extends JPanel
{

	private static final Logger LOG;

	ControlPanel(final MyTableModel aModel)
	{
		setBorder(BorderFactory.createTitledBorder("Controls: "));
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		c.ipadx = 5;
		c.ipady = 5;
		c.gridx = 0;
		c.anchor = 13;
		c.gridy = 0;
		JLabel label = new JLabel("Filter Level:");
		gridbag.setConstraints(label, c);
		add(label);
		c.gridy++;
		label = new JLabel("Filter Thread:");
		gridbag.setConstraints(label, c);
		add(label);
		c.gridy++;
		label = new JLabel("Filter Logger:");
		gridbag.setConstraints(label, c);
		add(label);
		c.gridy++;
		label = new JLabel("Filter NDC:");
		gridbag.setConstraints(label, c);
		add(label);
		c.gridy++;
		label = new JLabel("Filter Message:");
		gridbag.setConstraints(label, c);
		add(label);
		c.weightx = 1.0D;
		c.gridx = 1;
		c.anchor = 17;
		c.gridy = 0;
		Level allPriorities[] = {
			Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.TRACE
		};
		final JComboBox priorities = new JComboBox(allPriorities);
		Level lowest = allPriorities[allPriorities.length - 1];
		priorities.setSelectedItem(lowest);
		aModel.setPriorityFilter(lowest);
		gridbag.setConstraints(priorities, c);
		add(priorities);
		priorities.setEditable(false);
		priorities.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent aEvent)
			{
				aModel.setPriorityFilter((Priority)priorities.getSelectedItem());
			}

			
			{
				super();
			}
		});
		c.fill = 2;
		c.gridy++;
		final JTextField threadField = new JTextField("");
		threadField.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent aEvent)
			{
				aModel.setThreadFilter(threadField.getText());
			}

			public void removeUpdate(DocumentEvent aEvente)
			{
				aModel.setThreadFilter(threadField.getText());
			}

			public void changedUpdate(DocumentEvent aEvent)
			{
				aModel.setThreadFilter(threadField.getText());
			}

			
			{
				super();
			}
		});
		gridbag.setConstraints(threadField, c);
		add(threadField);
		c.gridy++;
		final JTextField catField = new JTextField("");
		catField.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent aEvent)
			{
				aModel.setCategoryFilter(catField.getText());
			}

			public void removeUpdate(DocumentEvent aEvent)
			{
				aModel.setCategoryFilter(catField.getText());
			}

			public void changedUpdate(DocumentEvent aEvent)
			{
				aModel.setCategoryFilter(catField.getText());
			}

			
			{
				super();
			}
		});
		gridbag.setConstraints(catField, c);
		add(catField);
		c.gridy++;
		final JTextField ndcField = new JTextField("");
		ndcField.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent aEvent)
			{
				aModel.setNDCFilter(ndcField.getText());
			}

			public void removeUpdate(DocumentEvent aEvent)
			{
				aModel.setNDCFilter(ndcField.getText());
			}

			public void changedUpdate(DocumentEvent aEvent)
			{
				aModel.setNDCFilter(ndcField.getText());
			}

			
			{
				super();
			}
		});
		gridbag.setConstraints(ndcField, c);
		add(ndcField);
		c.gridy++;
		final JTextField msgField = new JTextField("");
		msgField.getDocument().addDocumentListener(new DocumentListener() {

			public void insertUpdate(DocumentEvent aEvent)
			{
				aModel.setMessageFilter(msgField.getText());
			}

			public void removeUpdate(DocumentEvent aEvent)
			{
				aModel.setMessageFilter(msgField.getText());
			}

			public void changedUpdate(DocumentEvent aEvent)
			{
				aModel.setMessageFilter(msgField.getText());
			}

			
			{
				super();
			}
		});
		gridbag.setConstraints(msgField, c);
		add(msgField);
		c.weightx = 0.0D;
		c.fill = 2;
		c.anchor = 13;
		c.gridx = 2;
		c.gridy = 0;
		JButton exitButton = new JButton("Exit");
		exitButton.setMnemonic('x');
		exitButton.addActionListener(ExitAction.INSTANCE);
		gridbag.setConstraints(exitButton, c);
		add(exitButton);
		c.gridy++;
		JButton clearButton = new JButton("Clear");
		clearButton.setMnemonic('c');
		clearButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent aEvent)
			{
				aModel.clear();
			}

			
			{
				super();
			}
		});
		gridbag.setConstraints(clearButton, c);
		add(clearButton);
		c.gridy++;
		final JButton toggleButton = new JButton("Pause");
		toggleButton.setMnemonic('p');
		toggleButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent aEvent)
			{
				aModel.toggle();
				toggleButton.setText(aModel.isPaused() ? "Resume" : "Pause");
			}

			
			{
				super();
			}
		});
		gridbag.setConstraints(toggleButton, c);
		add(toggleButton);
	}

	static Class class$(String x0)
	{
		return Class.forName(x0);
		ClassNotFoundException x1;
		x1;
		throw (new NoClassDefFoundError()).initCause(x1);
	}

	static 
	{
		LOG = Logger.getLogger(org.apache.log4j.chainsaw.ControlPanel.class);
	}
}
