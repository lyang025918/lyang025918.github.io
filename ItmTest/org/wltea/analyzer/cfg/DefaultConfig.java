// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DefaultConfig.java

package org.wltea.analyzer.cfg;

import java.io.IOException;
import java.util.*;

// Referenced classes of package org.wltea.analyzer.cfg:
//			Configuration

public class DefaultConfig
	implements Configuration
{

	private static final String PATH_DIC_MAIN = "org/wltea/analyzer/dic/main2012.dic";
	private static final String PATH_DIC_QUANTIFIER = "org/wltea/analyzer/dic/quantifier.dic";
	private static final String FILE_NAME = "IKAnalyzer.cfg.xml";
	private static final String EXT_DICT = "ext_dict";
	private static final String EXT_STOP = "ext_stopwords";
	private Properties props;
	private boolean useSmart;

	public static Configuration getInstance()
	{
		return new DefaultConfig();
	}

	private DefaultConfig()
	{
		props = new Properties();
		java.io.InputStream input = getClass().getClassLoader().getResourceAsStream("IKAnalyzer.cfg.xml");
		if (input != null)
			try
			{
				props.loadFromXML(input);
			}
			catch (InvalidPropertiesFormatException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
	}

	public boolean useSmart()
	{
		return useSmart;
	}

	public void setUseSmart(boolean useSmart)
	{
		this.useSmart = useSmart;
	}

	public String getMainDictionary()
	{
		return "org/wltea/analyzer/dic/main2012.dic";
	}

	public String getQuantifierDicionary()
	{
		return "org/wltea/analyzer/dic/quantifier.dic";
	}

	public List getExtDictionarys()
	{
		List extDictFiles = new ArrayList(2);
		String extDictCfg = props.getProperty("ext_dict");
		if (extDictCfg != null)
		{
			String filePaths[] = extDictCfg.split(";");
			if (filePaths != null)
			{
				String as[];
				int j = (as = filePaths).length;
				for (int i = 0; i < j; i++)
				{
					String filePath = as[i];
					if (filePath != null && !"".equals(filePath.trim()))
						extDictFiles.add(filePath.trim());
				}

			}
		}
		return extDictFiles;
	}

	public List getExtStopWordDictionarys()
	{
		List extStopWordDictFiles = new ArrayList(2);
		String extStopWordDictCfg = props.getProperty("ext_stopwords");
		if (extStopWordDictCfg != null)
		{
			String filePaths[] = extStopWordDictCfg.split(";");
			if (filePaths != null)
			{
				String as[];
				int j = (as = filePaths).length;
				for (int i = 0; i < j; i++)
				{
					String filePath = as[i];
					if (filePath != null && !"".equals(filePath.trim()))
						extStopWordDictFiles.add(filePath.trim());
				}

			}
		}
		return extStopWordDictFiles;
	}
}
