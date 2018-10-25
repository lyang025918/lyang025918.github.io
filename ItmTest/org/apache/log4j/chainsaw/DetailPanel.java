// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DetailPanel.java

package org.apache.log4j.chainsaw;

import java.awt.BorderLayout;
import java.text.MessageFormat;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.apache.log4j.Logger;

// Referenced classes of package org.apache.log4j.chainsaw:
//			MyTableModel, EventDetails

class DetailPanel extends JPanel
	implements ListSelectionListener
{

	private static final Logger LOG;
	private static final MessageFormat FORMATTER = new MessageFormat("<b>Time:</b> <code>{0,time,medium}</code>&nbsp;&nbsp;<b>Priority:</b> <code>{1}</code>&nbsp;&nbsp;<b>Thread:</b> <code>{2}</code>&nbsp;&nbsp;<b>NDC:</b> <code>{3}</code><br><b>Logger:</b> <code>{4}</code><br><b>Location:</b> <code>{5}</code><br><b>Message:</b><pre>{6}</pre><b>Throwable:</b><pre>{7}</pre>");
	private final MyTableModel mModel;
	private final JEditorPane mDetails = new JEditorPane();

	DetailPanel(JTable aTable, MyTableModel aModel)
	{
		mModel = aModel;
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createTitledBorder("Details: "));
		mDetails.setEditable(false);
		mDetails.setContentType("text/html");
		add(new JScrollPane(mDetails), "Center");
		ListSelectionModel rowSM = aTable.getSelectionModel();
		rowSM.addListSelectionListener(this);
	}

	public void valueChanged(ListSelectionEvent aEvent)
	{
		if (aEvent.getValueIsAdjusting())
			return;
		ListSelectionModel lsm = (ListSelectionModel)aEvent.getSource();
		if (lsm.isSelectionEmpty())
		{
			mDetails.setText("Nothing selected");
		} else
		{
			int selectedRow = lsm.getMinSelectionIndex();
			EventDetails e = mModel.getEventDetails(selectedRow);
			Object args[] = {
				new Date(e.getTimeStamp()), e.getPriority(), escape(e.getThreadName()), escape(e.getNDC()), escape(e.getCategoryName()), escape(e.getLocationDetails()), escape(e.getMessage()), escape(getThrowableStrRep(e))
			};
			mDetails.setText(FORMATTER.format(((Object) (args))));
			mDetails.setCaretPosition(0);
		}
	}

	private static String getThrowableStrRep(EventDetails aEvent)
	{
		String strs[] = aEvent.getThrowableStrRep();
		if (strs == null)
			return null;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < strs.length; i++)
			sb.append(strs[i]).append("\n");

		return sb.toString();
	}

	private String escape(String aStr)
	{
		if (aStr == null)
			return null;
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < aStr.length(); i++)
		{
			char c = aStr.charAt(i);
			switch (c)
			{
			case 60: // '<'
				buf.append("&lt;");
				break;

			case 62: // '>'
				buf.append("&gt;");
				break;

			case 34: // '"'
				buf.append("&quot;");
				break;

			case 38: // '&'
				buf.append("&amp;");
				break;

			default:
				buf.append(c);
				break;
			}
		}

		return buf.toString();
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
		LOG = Logger.getLogger(org.apache.log4j.chainsaw.DetailPanel.class);
	}
}
