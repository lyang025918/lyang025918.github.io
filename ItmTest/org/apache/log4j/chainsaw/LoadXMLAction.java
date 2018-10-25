// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LoadXMLAction.java

package org.apache.log4j.chainsaw;

import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.*;
import javax.xml.parsers.*;
import org.apache.log4j.Logger;
import org.xml.sax.*;

// Referenced classes of package org.apache.log4j.chainsaw:
//			XMLFileHandler, MyTableModel

class LoadXMLAction extends AbstractAction
{

	private static final Logger LOG;
	private final JFrame mParent;
	private final JFileChooser mChooser = new JFileChooser();
	private final XMLReader mParser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
	private final XMLFileHandler mHandler;

	LoadXMLAction(JFrame aParent, MyTableModel aModel)
		throws SAXException, ParserConfigurationException
	{
		mChooser.setMultiSelectionEnabled(false);
		mChooser.setFileSelectionMode(0);
		mParent = aParent;
		mHandler = new XMLFileHandler(aModel);
		mParser.setContentHandler(mHandler);
	}

	public void actionPerformed(ActionEvent aIgnore)
	{
		LOG.info("load file called");
		if (mChooser.showOpenDialog(mParent) == 0)
		{
			LOG.info("Need to load a file");
			File chosen = mChooser.getSelectedFile();
			LOG.info("loading the contents of " + chosen.getAbsolutePath());
			try
			{
				int num = loadFile(chosen.getAbsolutePath());
				JOptionPane.showMessageDialog(mParent, "Loaded " + num + " events.", "CHAINSAW", 1);
			}
			catch (Exception e)
			{
				LOG.warn("caught an exception loading the file", e);
				JOptionPane.showMessageDialog(mParent, "Error parsing file - " + e.getMessage(), "CHAINSAW", 0);
			}
		}
	}

	private int loadFile(String aFile)
		throws SAXException, IOException
	{
		XMLReader xmlreader = mParser;
		JVM INSTR monitorenter ;
		StringBuffer buf = new StringBuffer();
		buf.append("<?xml version=\"1.0\" standalone=\"yes\"?>\n");
		buf.append("<!DOCTYPE log4j:eventSet ");
		buf.append("[<!ENTITY data SYSTEM \"file:///");
		buf.append(aFile);
		buf.append("\">]>\n");
		buf.append("<log4j:eventSet xmlns:log4j=\"Claira\">\n");
		buf.append("&data;\n");
		buf.append("</log4j:eventSet>\n");
		InputSource is = new InputSource(new StringReader(buf.toString()));
		mParser.parse(is);
		return mHandler.getNumEvents();
		Exception exception;
		exception;
		throw exception;
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
		LOG = Logger.getLogger(org.apache.log4j.chainsaw.LoadXMLAction.class);
	}
}
