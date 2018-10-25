// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMFactory.java

package com.iflytek.itm.api;

import com.iflytek.itm.impl.ITMImpl;
import com.iflytek.itm.util.ITMUtils;
import com.iflytek.itm.util.JarTool;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import org.apache.log4j.PropertyConfigurator;
import org.wltea.analyzer.cfg.Configuration;
import org.wltea.analyzer.dic.Dictionary;

// Referenced classes of package com.iflytek.itm.api:
//			ITM

public class ITMFactory
{
	static class ITMIKAnalyzerConfig
		implements Configuration
	{

		public boolean useSmart()
		{
			return false;
		}

		public void setUseSmart(boolean flag)
		{
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
			List userdic = new ArrayList();
			userdic.add("resource/user.dic");
			return userdic;
		}

		public List getExtStopWordDictionarys()
		{
			List stopdic = new ArrayList();
			stopdic.add("resource/stopword.dic");
			return stopdic;
		}

		ITMIKAnalyzerConfig()
		{
		}
	}


	private static Properties _properties = null;
	private static ITMFactory inst = new ITMFactory();

	private ITMFactory()
	{
		if (_properties == null)
		{
			_properties = new Properties();
			try
			{
				_properties.load(ITMUtils.getResourceFile("itm.properties"));
				String jarDir = JarTool.getJarDir();
				String logFile = (new StringBuilder()).append(jarDir).append("/").append(_properties.getProperty("log4j.appender.R.File")).toString();
				_properties.setProperty("log4j.appender.R.File", logFile);
				initIKAnalyzer();
			}
			catch (IOException e)
			{
				System.out.println("ITMFactory | load itm log4j fail!");
				e.printStackTrace();
			}
			PropertyConfigurator.configure(_properties);
		}
	}

	public static ITMFactory inst()
	{
		return inst;
	}

	public static ITM create()
	{
		return create("default");
	}

	public static ITM create(String params)
	{
		ITM inst = null;
		if (params == null || params.equals("default"))
			inst = new ITMImpl();
		return inst;
	}

	public static void initIKAnalyzer()
		throws IOException
	{
		ITMIKAnalyzerConfig cfg = new ITMIKAnalyzerConfig();
		Dictionary.initial(cfg);
	}

}
