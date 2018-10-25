// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMUserData.java

package com.iflytek.itm.build;

import com.iflytek.itm.api.ITMBuilder;
import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.search.ExactAnalyzer;
import com.iflytek.itm.search.RuleAnalyzer;
import com.iflytek.itm.util.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.*;
import mylib.file.NormalizePath;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.SegmentInfos;
import org.apache.lucene.store.*;
import org.apache.lucene.util.Constants;
import org.apache.lucene.util.Version;

public class ITMUserData
{

	private Directory directory;
	private Map fieldMap;
	private Map fieldAnalyzers;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/build/ITMUserData);

	public ITMUserData()
	{
		directory = null;
		fieldMap = new HashMap();
		fieldAnalyzers = new HashMap();
		fieldAnalyzers.put(ITMConstants.ITM_SUB_DIR_NAME_INNER_FIELD, new ExactAnalyzer());
	}

	public void clear()
	{
		fieldMap.clear();
		fieldMap = null;
		fieldAnalyzers.clear();
		fieldAnalyzers = null;
		try
		{
			directory.close();
			directory = null;
		}
		catch (Exception e) { }
	}

	public void putRuleAnalyzer(String field)
	{
		if (!fieldAnalyzers.containsKey(field))
			fieldAnalyzers.put(field, new RuleAnalyzer());
	}

	public int openIndex(String indexPath, String subIndexDir, boolean create)
	{
		int ret = 0;
		if (ITMUtils.isValidDir(subIndexDir))
			break MISSING_BLOCK_LABEL_46;
		logger.error((new StringBuilder()).append("openIndex | Error: subIndexDir is not valid, subIndexDir=").append(subIndexDir).toString());
		ret = ITMErrors.ITM_ERROR_INVALID_DIRECTORY.code();
		return ret;
		File fullPathFile;
		String fullIndexPath = ITMUtils.getSubIndexFullPath(indexPath, subIndexDir, create);
		fullPathFile = new File(fullIndexPath);
		if (fullPathFile.exists())
			break MISSING_BLOCK_LABEL_109;
		logger.error((new StringBuilder()).append("openIndex | Error: subIndexDir is not existed, subIndexDir=").append(subIndexDir).toString());
		ret = ITMErrors.ITM_ERROR_NO_SUB_INDEX_DIR.code();
		return ret;
		directory = getDirectory(fullPathFile);
		ret = removeLock(directory);
		if (ret == 0)
			break MISSING_BLOCK_LABEL_163;
		logger.error((new StringBuilder()).append("openIndex | Error: removeLock failed, errcode=").append(ret).toString());
		return ret;
		File dir;
		String usrIndexPath = (new StringBuilder()).append(NormalizePath.doNorm(indexPath, ITMConstants.ITM_PATH_SPLIT, true)).append(ITMConstants.ITM_USR_DIR).toString();
		dir = new File(usrIndexPath);
		if (dir.canRead())
			break MISSING_BLOCK_LABEL_222;
		ret = ITMErrors.ITM_ERROR_DIRECTORY_CAN_NOT_READ.code();
		return ret;
		if (dir.canWrite())
			break MISSING_BLOCK_LABEL_241;
		ret = ITMErrors.ITM_ERROR_DIRECTORY_CAN_NOT_WRITE.code();
		return ret;
		try
		{
			if (dir.isDirectory())
			{
				File subDirs[] = dir.listFiles();
				if (subDirs != null)
				{
					int i = 0;
					do
					{
						if (i >= subDirs.length)
							break;
						ret = loadIndex(FSDirectory.open(subDirs[i]));
						if (0 != ret)
							break;
						i++;
					} while (true);
				}
			}
			ret = loadIndex(directory);
		}
		catch (IOException e) { }
		return ret;
	}

	private int removeLock(Directory dir)
	{
		int ret = 0;
		try
		{
			if (IndexWriter.isLocked(dir))
			{
				IndexWriter.unlock(dir);
				logger.info("removeLock | remove lock file success!");
			}
		}
		catch (IOException e)
		{
			ITMErrors error = ITMErrors.ITM_ERROR_INDEX_IS_LOCKED;
			logger.error((new StringBuilder()).append("removeLock | errmsg=").append(e.getMessage()).append(", msg=").append(error.msg()).append(", errcode=").append(error.code()).toString());
			ret = error.code();
		}
		return ret;
	}

	public int loadIndex(Directory dir)
	{
		int ret = 0;
		try
		{
			SegmentInfos infos = new SegmentInfos();
			infos.read(dir);
			ret = setUserData(infos.getUserData());
			Set fields = fieldMap.keySet();
			Iterator it = fields.iterator();
			do
			{
				if (!it.hasNext())
					break;
				String fieldName = (String)it.next();
				com.iflytek.itm.api.ITMBuilder.ITMField itmField = (com.iflytek.itm.api.ITMBuilder.ITMField)fieldMap.get(fieldName);
				if (itmField != null && itmField.type.equals("string") && ITMUtils.isValidStr(itmField.analyzer) && !itmField.analyzer.equals("null"))
					putRuleAnalyzer((new StringBuilder()).append(ITMConstants.ITM_RULE_INNER_FIELD_PREFIX).append(fieldName).toString());
			} while (true);
		}
		catch (IOException e) { }
		return ret;
	}

	public Directory getDirectory()
	{
		return directory;
	}

	private Directory getDirectory(File fullPathFile)
		throws IOException
	{
		Directory directory1 = null;
		if (Constants.WINDOWS)
			directory1 = new SimpleFSDirectory(fullPathFile, new SimpleFSLockFactory());
		else
		if (Constants.LINUX)
			directory1 = new NIOFSDirectory(fullPathFile, new SimpleFSLockFactory());
		else
			directory1 = FSDirectory.open(fullPathFile, new SimpleFSLockFactory());
		return directory1;
	}

	public Analyzer getAnalyzer()
	{
		return new PerFieldAnalyzerWrapper(new StandardAnalyzer(Version.LUCENE_40), fieldAnalyzers);
	}

	public int addField(com.iflytek.itm.api.ITMBuilder.ITMField field)
	{
		int ret = 0;
		if (!fieldMap.containsKey(field.name))
		{
			fieldMap.put(field.name, field);
			ret = addAnalyzer(field.name, field.analyzer);
		} else
		{
			ret = checkField(field);
		}
		return ret;
	}

	private int checkField(com.iflytek.itm.api.ITMBuilder.ITMField field)
	{
		int ret = 0;
		com.iflytek.itm.api.ITMBuilder.ITMField rawField = (com.iflytek.itm.api.ITMBuilder.ITMField)fieldMap.get(field.name);
		if (rawField != null && (!field.name.equals(rawField.name) || !field.type.equals(rawField.type) || !field.analyzer.equals(rawField.analyzer) || field.isPrimaryKey != rawField.isPrimaryKey))
		{
			ret = ITMErrors.ITM_ERROR_FIELD_NOT_SAME.code();
			logger.error((new StringBuilder()).append("checkField | Error: field info is not the same, rawField=").append(rawField.toString()).append(", nowField=").append(field.toString()).toString());
		}
		return ret;
	}

	public Map toLuceneUserData()
	{
		Map userData = new HashMap();
		StringBuffer buffer = new StringBuffer(1024);
		Set fields = fieldMap.keySet();
		String fieldName;
		for (Iterator it = fields.iterator(); it.hasNext(); userData.put(fieldName, buffer.toString()))
		{
			buffer.setLength(0);
			fieldName = (String)it.next();
			com.iflytek.itm.api.ITMBuilder.ITMField field = (com.iflytek.itm.api.ITMBuilder.ITMField)fieldMap.get(fieldName);
			buffer.append("name=").append(fieldName).append("\n").append("type=").append(field.type).append("\n").append("value=").append("null").append("\n").append("analyzer=").append(field.analyzer).append("\n").append("isPrimaryKey=").append(field.isPrimaryKey).append("\n");
		}

		return userData;
	}

	private int setUserData(Map userData)
	{
		int ret = 0;
		Set fields = userData.keySet();
		Iterator it = fields.iterator();
		Map pairs = new HashMap();
		while (it.hasNext()) 
		{
			String name = (String)it.next();
			String data = (String)userData.get(name);
			pairs.clear();
			ret = ParamParser.parse(data, pairs);
			boolean isPrimary = Boolean.valueOf((String)pairs.get("isPrimaryKey")).booleanValue();
			com.iflytek.itm.api.ITMBuilder.ITMField field = new com.iflytek.itm.api.ITMBuilder.ITMField((String)pairs.get("name"), (String)pairs.get("type"), (String)pairs.get("value"), (String)pairs.get("analyzer"), isPrimary);
			ret = addField(field);
		}
		return ret;
	}

	public com.iflytek.itm.api.ITMBuilder.ITMField getField(String name)
	{
		com.iflytek.itm.api.ITMBuilder.ITMField field = null;
		if (fieldMap.containsKey(name))
			field = (com.iflytek.itm.api.ITMBuilder.ITMField)fieldMap.get(name);
		return field;
	}

	private int addAnalyzer(String fieldName, String strAnalyzer)
	{
		int ret;
label0:
		{
			ret = 0;
			Class c = null;
			if (strAnalyzer == null || strAnalyzer.isEmpty() || strAnalyzer.equals("null"))
			{
				fieldAnalyzers.put(fieldName, new ExactAnalyzer());
				break label0;
			}
			try
			{
				c = Class.forName(strAnalyzer);
			}
			catch (ClassNotFoundException e)
			{
				logger.error((new StringBuilder()).append("add | Error: Class of analyzer is not found, fieldName=").append(fieldName).append("analyzer=").append(strAnalyzer).toString());
				ret = ITMErrors.ITM_ERROR_ANALYZER_NOT_FOUND.code();
				break label0;
			}
			try
			{
				Object o = c.newInstance();
				Analyzer analyzer = (Analyzer)o;
				fieldAnalyzers.put(fieldName, analyzer);
			}
			catch (IllegalAccessException e)
			{
				logger.error((new StringBuilder()).append("add | Error: Class of analyzer is not accessible, fieldName=").append(fieldName).append("analyzer=").append(strAnalyzer).toString());
				ret = ITMErrors.ITM_ERROR_ANALYZER_NOT_ACCESSIBLE.code();
			}
			catch (InstantiationException e)
			{
				try
				{
					Class parameterTypes[] = {
						org/apache/lucene/util/Version
					};
					Constructor constructor = c.getConstructor(parameterTypes);
					Object parameters[] = {
						Version.LUCENE_40
					};
					Object o = constructor.newInstance(parameters);
					Analyzer analyzer = (Analyzer)o;
					fieldAnalyzers.put(fieldName, analyzer);
				}
				catch (Exception e1)
				{
					logger.error((new StringBuilder()).append("add | Error: Class of analyzer is not instantiable, fieldName=").append(fieldName).append("analyzer=").append(strAnalyzer).toString());
					ret = ITMErrors.ITM_ERROR_ANALYZER_NOT_INSTANTIABLE.code();
				}
			}
		}
		return ret;
	}

}
