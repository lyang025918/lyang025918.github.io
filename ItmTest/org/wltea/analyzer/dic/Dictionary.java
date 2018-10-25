// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Dictionary.java

package org.wltea.analyzer.dic;

import java.io.*;
import java.util.*;
import org.wltea.analyzer.cfg.Configuration;

// Referenced classes of package org.wltea.analyzer.dic:
//			DictSegment, Hit

public class Dictionary
{

	private static Dictionary singleton;
	private DictSegment _MainDict;
	private DictSegment _StopWordDict;
	private DictSegment _QuantifierDict;
	private Configuration cfg;

	private Dictionary(Configuration cfg)
	{
		this.cfg = cfg;
		loadMainDict();
		loadStopWordDict();
		loadQuantifierDict();
	}

	public static Dictionary initial(Configuration cfg)
	{
		if (singleton != null) goto _L2; else goto _L1
_L1:
		/*<invalid signature>*/java.lang.Object local = org/wltea/analyzer/dic/Dictionary;
		JVM INSTR monitorenter ;
		if (singleton != null)
			break MISSING_BLOCK_LABEL_34;
		singleton = new Dictionary(cfg);
		return singleton;
		local;
		JVM INSTR monitorexit ;
		  goto _L2
		local;
		JVM INSTR monitorexit ;
		throw ;
_L2:
		return singleton;
	}

	public static Dictionary getSingleton()
	{
		if (singleton == null)
			throw new IllegalStateException("词典尚未初始化，请先调用initial方法");
		else
			return singleton;
	}

	public void addWords(Collection words)
	{
		if (words != null)
		{
			for (Iterator iterator = words.iterator(); iterator.hasNext();)
			{
				String word = (String)iterator.next();
				if (word != null)
					singleton._MainDict.fillSegment(word.trim().toLowerCase().toCharArray());
			}

		}
	}

	public void disableWords(Collection words)
	{
		if (words != null)
		{
			for (Iterator iterator = words.iterator(); iterator.hasNext();)
			{
				String word = (String)iterator.next();
				if (word != null)
					singleton._MainDict.disableSegment(word.trim().toLowerCase().toCharArray());
			}

		}
	}

	public Hit matchInMainDict(char charArray[])
	{
		return singleton._MainDict.match(charArray);
	}

	public Hit matchInMainDict(char charArray[], int begin, int length)
	{
		return singleton._MainDict.match(charArray, begin, length);
	}

	public Hit matchInQuantifierDict(char charArray[], int begin, int length)
	{
		return singleton._QuantifierDict.match(charArray, begin, length);
	}

	public Hit matchWithHit(char charArray[], int currentIndex, Hit matchedHit)
	{
		DictSegment ds = matchedHit.getMatchedDictSegment();
		return ds.match(charArray, currentIndex, 1, matchedHit);
	}

	public boolean isStopWord(char charArray[], int begin, int length)
	{
		return singleton._StopWordDict.match(charArray, begin, length).isMatch();
	}

	private void loadMainDict()
	{
		InputStream is;
		_MainDict = new DictSegment(Character.valueOf('\0'));
		is = getClass().getClassLoader().getResourceAsStream(cfg.getMainDictionary());
		if (is == null)
			throw new RuntimeException("Main Dictionary not found!!!");
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
			String theWord = null;
			do
			{
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim()))
					_MainDict.fillSegment(theWord.trim().toLowerCase().toCharArray());
			} while (theWord != null);
			break MISSING_BLOCK_LABEL_178;
		}
		catch (IOException ioe)
		{
			System.err.println("Main Dictionary loading exception.");
			ioe.printStackTrace();
		}
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_198;
		Exception exception;
		exception;
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		loadExtDict();
		return;
	}

	private void loadExtDict()
	{
		List extDictFiles = cfg.getExtDictionarys();
		if (extDictFiles == null) goto _L2; else goto _L1
_L1:
		InputStream is;
		Iterator iterator;
		is = null;
		iterator = extDictFiles.iterator();
		  goto _L3
_L4:
		String extDictName = (String)iterator.next();
		System.out.println((new StringBuilder("加载扩展词典：")).append(extDictName).toString());
		is = getClass().getClassLoader().getResourceAsStream(extDictName);
		if (is == null)
			continue; /* Loop/switch isn't completed */
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
			String theWord = null;
			do
			{
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim()))
					_MainDict.fillSegment(theWord.trim().toLowerCase().toCharArray());
			} while (theWord != null);
			break MISSING_BLOCK_LABEL_218;
		}
		catch (IOException ioe)
		{
			System.err.println("Extension Dictionary loading exception.");
			ioe.printStackTrace();
		}
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		continue; /* Loop/switch isn't completed */
		Exception exception;
		exception;
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
_L3:
		if (iterator.hasNext()) goto _L4; else goto _L2
_L2:
	}

	private void loadStopWordDict()
	{
		List extStopWordDictFiles;
		_StopWordDict = new DictSegment(Character.valueOf('\0'));
		extStopWordDictFiles = cfg.getExtStopWordDictionarys();
		if (extStopWordDictFiles == null) goto _L2; else goto _L1
_L1:
		InputStream is;
		Iterator iterator;
		is = null;
		iterator = extStopWordDictFiles.iterator();
		  goto _L3
_L4:
		String extStopWordDictName = (String)iterator.next();
		System.out.println((new StringBuilder("加载扩展停止词典：")).append(extStopWordDictName).toString());
		is = getClass().getClassLoader().getResourceAsStream(extStopWordDictName);
		if (is == null)
			continue; /* Loop/switch isn't completed */
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
			String theWord = null;
			do
			{
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim()))
					_StopWordDict.fillSegment(theWord.trim().toLowerCase().toCharArray());
			} while (theWord != null);
			break MISSING_BLOCK_LABEL_235;
		}
		catch (IOException ioe)
		{
			System.err.println("Extension Stop word Dictionary loading exception.");
			ioe.printStackTrace();
		}
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		continue; /* Loop/switch isn't completed */
		Exception exception;
		exception;
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
_L3:
		if (iterator.hasNext()) goto _L4; else goto _L2
_L2:
	}

	private void loadQuantifierDict()
	{
		InputStream is;
		_QuantifierDict = new DictSegment(Character.valueOf('\0'));
		is = getClass().getClassLoader().getResourceAsStream(cfg.getQuantifierDicionary());
		if (is == null)
			throw new RuntimeException("Quantifier Dictionary not found!!!");
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"), 512);
			String theWord = null;
			do
			{
				theWord = br.readLine();
				if (theWord != null && !"".equals(theWord.trim()))
					_QuantifierDict.fillSegment(theWord.trim().toLowerCase().toCharArray());
			} while (theWord != null);
			break MISSING_BLOCK_LABEL_180;
		}
		catch (IOException ioe)
		{
			System.err.println("Quantifier Dictionary loading exception.");
			ioe.printStackTrace();
		}
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		break MISSING_BLOCK_LABEL_200;
		Exception exception;
		exception;
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		throw exception;
		try
		{
			if (is != null)
			{
				is.close();
				is = null;
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
