// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogFactor5ErrorDialog.java

package org.apache.log4j.lf5.viewer;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

// Referenced classes of package org.apache.log4j.lf5.viewer:
//			LogFactor5Dialog

public class LogFactor5ErrorDialog extends LogFactor5Dialog
{

	public LogFactor5ErrorDialog(JFrame jframe, String message)
	{
		super(jframe, "Error", true);
		JButton ok = new JButton("Ok");
		ok.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				hide();
			}

			
			{
				super();
			}
		});
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		bottom.add(ok);
		JPanel main = new JPanel();
		main.setLayout(new GridBagLayout());
		wrapStringOnPanel(message, main);
		getContentPane().add(main, "Center");
		getContentPane().add(bottom, "South");
		show();
	}
}
