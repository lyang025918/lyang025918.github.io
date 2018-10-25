// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMMiningResource.java

package com.iflytek.itm.mining;

import com.iflytek.itm.util.ITMUtils;
import java.io.*;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;

// Referenced classes of package com.iflytek.itm.mining:
//			Pattern

public class ITMMiningResource
{

	private static ITMMiningResource inst = null;
	public Set stopWords;
	public Pattern pattern;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/ITMMiningResource);

	private ITMMiningResource()
	{
		stopWords = new HashSet();
		pattern = null;
	}

	public static ITMMiningResource inst()
	{
		if (inst == null)
			syncInit();
		return inst;
	}

	private static synchronized void syncInit()
	{
		if (inst != null)
			return;
		try
		{
			inst = new ITMMiningResource();
			loadWords("stop.txt", "gbk", inst.stopWords);
			inst.loadBusinessWords();
		}
		catch (IOException e)
		{
			logger.error((new StringBuilder()).append("ITMMiningResource | syncInit() IOException error, msg=").append(e.getMessage()).toString());
		}
	}

	public static int loadWords(String filename, String codepage, Set words)
		throws IOException
	{
		InputStream stopInputStream = ITMUtils.getResourceFile(filename);
		BufferedReader br = new BufferedReader(new InputStreamReader(stopInputStream, codepage));
		String line = null;
		do
		{
			line = br.readLine();
			if (null == line)
				break;
			String tmp = line.trim();
			if (!tmp.isEmpty())
				words.add(tmp);
		} while (true);
		return 0;
	}

	private void loadBusinessWords()
		throws IOException
	{
		pattern = new Pattern(ITMUtils.getResourceFile("business.txt"));
	}

	public boolean isNoiseWord(String term)
	{
		int len = term.length();
		if (len < 2)
			return true;
		if (term.contains("��") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("ʲô") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("ѽ") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("��") || term.contains("��"))
			return true;
		else
			return stopWords != null && stopWords.contains(term);
	}

}
