// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LogFactor5InputDialog.java

package org.apache.log4j.lf5.viewer;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

// Referenced classes of package org.apache.log4j.lf5.viewer:
//			LogFactor5Dialog

public class LogFactor5InputDialog extends LogFactor5Dialog
{

	public static final int SIZE = 30;
	private JTextField _textField;

	public LogFactor5InputDialog(JFrame jframe, String title, String label)
	{
		this(jframe, title, label, 30);
	}

	public LogFactor5InputDialog(JFrame jframe, String title, String label, int size)
	{
		super(jframe, title, true);
		JPanel bottom = new JPanel();
		bottom.setLayout(new FlowLayout());
		JPanel main = new JPanel();
		main.setLayout(new FlowLayout());
		main.add(new JLabel(label));
		_textField = new JTextField(size);
		main.add(_textField);
		addKeyListener(new KeyAdapter() {

			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == 10)
					hide();
			}

			
			{
				super();
			}
		});
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
		JButton cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e)
			{
				hide();
				_textField.setText("");
			}

			
			{
				super();
			}
		});
		bottom.add(ok);
		bottom.add(cancel);
		getContentPane().add(main, "Center");
		getContentPane().add(bottom, "South");
		pack();
		centerWindow(this);
		show();
	}

	public String getText()
	{
		String s = _textField.getText();
		if (s != null && s.trim().length() == 0)
			return null;
		else
			return s;
	}

}
