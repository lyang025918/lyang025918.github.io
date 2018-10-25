// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogFactor5LoadingDialog.java

package org.apache.log4j.lf5.viewer;

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

// Referenced classes of package org.apache.log4j.lf5.viewer:
//			LogFactor5Dialog

public class LogFactor5LoadingDialog extends LogFactor5Dialog
{

	public LogFactor5LoadingDialog(JFrame jframe, String message)
	{
		super(jframe, "LogFactor5", false);
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		wrapStringOnPanel(message, main);
		getContentPane().add(main, "Center");
		getContentPane().add(bottom, "South");
		show();
	}
}
