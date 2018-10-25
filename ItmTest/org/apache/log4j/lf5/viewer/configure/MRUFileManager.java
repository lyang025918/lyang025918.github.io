// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MRUFileManager.java

package org.apache.log4j.lf5.viewer.configure;

import java.io.*;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;

public class MRUFileManager
{

	private static final String CONFIG_FILE_NAME = "mru_file_manager";
	private static final int DEFAULT_MAX_SIZE = 3;
	private int _maxSize;
	private LinkedList _mruFileList;

	public MRUFileManager()
	{
		_maxSize = 0;
		load();
		setMaxSize(3);
	}

	public MRUFileManager(int maxSize)
	{
		_maxSize = 0;
		load();
		setMaxSize(maxSize);
	}

	public void save()
	{
		File file = new File(getFilename());
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(_mruFileList);
			oos.flush();
			oos.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public int size()
	{
		return _mruFileList.size();
	}

	public Object getFile(int index)
	{
		if (index < size())
			return _mruFileList.get(index);
		else
			return null;
	}

	public InputStream getInputStream(int index)
		throws IOException, FileNotFoundException
	{
		if (index < size())
		{
			Object o = getFile(index);
			if (o instanceof File)
				return getInputStream((File)o);
			else
				return getInputStream((URL)o);
		} else
		{
			return null;
		}
	}

	public void set(File file)
	{
		setMRU(file);
	}

	public void set(URL url)
	{
		setMRU(url);
	}

	public String[] getMRUFileList()
	{
		if (size() == 0)
			return null;
		String ss[] = new String[size()];
		for (int i = 0; i < size(); i++)
		{
			Object o = getFile(i);
			if (o instanceof File)
				ss[i] = ((File)o).getAbsolutePath();
			else
				ss[i] = o.toString();
		}

		return ss;
	}

	public void moveToTop(int index)
	{
		_mruFileList.add(0, _mruFileList.remove(index));
	}

	public static void createConfigurationDirectory()
	{
		String home = System.getProperty("user.home");
		String sep = System.getProperty("file.separator");
		File f = new File(home + sep + "lf5");
		if (!f.exists())
			try
			{
				f.mkdir();
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
	}

	protected InputStream getInputStream(File file)
		throws IOException, FileNotFoundException
	{
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(file));
		return reader;
	}

	protected InputStream getInputStream(URL url)
		throws IOException
	{
		return url.openStream();
	}

	protected void setMRU(Object o)
	{
		int index = _mruFileList.indexOf(o);
		if (index == -1)
		{
			_mruFileList.add(0, o);
			setMaxSize(_maxSize);
		} else
		{
			moveToTop(index);
		}
	}

	protected void load()
	{
		createConfigurationDirectory();
		File file = new File(getFilename());
		if (file.exists())
			try
			{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				_mruFileList = (LinkedList)ois.readObject();
				ois.close();
				Iterator it = _mruFileList.iterator();
				do
				{
					if (!it.hasNext())
						break;
					Object o = it.next();
					if (!(o instanceof File) && !(o instanceof URL))
						it.remove();
				} while (true);
			}
			catch (Exception e)
			{
				_mruFileList = new LinkedList();
			}
		else
			_mruFileList = new LinkedList();
	}

	protected String getFilename()
	{
		String home = System.getProperty("user.home");
		String sep = System.getProperty("file.separator");
		return home + sep + "lf5" + sep + "mru_file_manager";
	}

	protected void setMaxSize(int maxSize)
	{
		if (maxSize < _mruFileList.size())
		{
			for (int i = 0; i < _mruFileList.size() - maxSize; i++)
				_mruFileList.removeLast();

		}
		_maxSize = maxSize;
	}
}
