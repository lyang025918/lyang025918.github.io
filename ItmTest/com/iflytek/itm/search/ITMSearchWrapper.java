// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMSearchWrapper.java

package com.iflytek.itm.search;

import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.build.ITMUserData;
import com.iflytek.itm.util.ITMParamParser;
import com.iflytek.itm.util.ITMUtils;
import java.io.File;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.FSDirectory;

public class ITMSearchWrapper
{

	public IndexSearcher searcher;
	public ITMUserData itmUserData;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/search/ITMSearchWrapper);

	public ITMSearchWrapper()
	{
		searcher = null;
		itmUserData = new ITMUserData();
	}

	public int open(String indexPath, ITMParamParser itmParamParser)
	{
		int ret = 0;
		java.util.Set tmpdirs = new HashSet();
		ITMUtils.addSubDirList(tmpdirs, itmParamParser.subIndexDirList);
		ITMUtils.addIdListGroups(tmpdirs, itmParamParser.idList);
		String finalDirs[] = ITMUtils.toArray(tmpdirs);
		ret = open_dirs(indexPath, finalDirs);
		if (ret != 0)
			logger.error((new StringBuilder()).append("open | Error: searchWrapper.open, errcode=").append(ret).toString());
		return ret;
	}

	private int open_dirs(String indexPath, String subIndexDirList[])
	{
		int ret = 0;
		if (subIndexDirList == null || subIndexDirList.length == 0)
		{
			ret = ITMErrors.ITM_ERROR_NO_SUB_INDEX_DIR.code();
		} else
		{
			MultiReader multiReader = null;
			List subReaders = new ArrayList();
			for (int i = 0; i < subIndexDirList.length;)
				try
				{
					String fullIndexPath = ITMUtils.getSubIndexFullPath(indexPath, subIndexDirList[i], false);
					FSDirectory directory = FSDirectory.open(new File(fullIndexPath));
					IndexReader reader = DirectoryReader.open(directory);
					subReaders.add(reader);
					ret = itmUserData.loadIndex(directory);
					if (ret == 0)
						continue;
					logger.error((new StringBuilder()).append("open | Error: openIndex, errcode=").append(ret).toString());
					break;
				}
				catch (IOException e)
				{
					if (i == subIndexDirList.length - 1 && subReaders.size() == 0)
					{
						ret = ITMErrors.ITM_ERROR_INDEX_NOT_EXISTED.code();
						logger.error((new StringBuilder()).append("open | searcher open error, errorMsg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
						break;
					}
					logger.warn((new StringBuilder()).append("open | searcher open index error, subindex=").append(subIndexDirList[i]).append(", errorMsg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
					i++;
				}

			IndexReader arrSubReaders[] = new IndexReader[subReaders.size()];
			subReaders.toArray(arrSubReaders);
			multiReader = new MultiReader(arrSubReaders);
			searcher = new IndexSearcher(multiReader);
		}
		return ret;
	}

	public int close()
	{
		int ret = 0;
		try
		{
			itmUserData.clear();
			itmUserData = null;
			searcher.getIndexReader().close();
			searcher = null;
		}
		catch (IOException e)
		{
			ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
			logger.error((new StringBuilder()).append("close | searcher close error, errorMsg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		}
		return ret;
	}

}
