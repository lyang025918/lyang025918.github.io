// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMSearchResultImpl.java

package com.iflytek.itm.search;

import com.iflytek.itm.api.ITMBuilder;
import com.iflytek.itm.api.ITMSearchContext;
import com.iflytek.itm.build.ITMUserData;
import com.iflytek.itm.util.ITMConstants;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.*;

// Referenced classes of package com.iflytek.itm.search:
//			ITMSearchWrapper

public class ITMSearchResultImpl
	implements com.iflytek.itm.api.ITMSearchContext.ITMSearchResult
{
	public static class ITMTopGroup
	{

		public int docTotalHits;
		public int groupTotalHits;
		public com.iflytek.itm.api.ITMSearchContext.ITMGroup groups[];

		public ITMTopGroup()
		{
		}
	}


	public TopDocs topDocs;
	public ITMTopGroup topGroups;
	public ITMSearchWrapper searchWrapper;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/search/ITMSearchResultImpl);

	public ITMSearchResultImpl()
	{
		topDocs = null;
		topGroups = null;
		searchWrapper = new ITMSearchWrapper();
	}

	public int close()
	{
		int ret = searchWrapper.close();
		topDocs = null;
		topGroups = null;
		searchWrapper = null;
		return ret;
	}

	public int getTotalHits()
	{
		if (topDocs.scoreDocs != null)
			return topDocs.totalHits;
		if (topGroups.groups != null)
			return topGroups.docTotalHits;
		else
			return 0;
	}

	public com.iflytek.itm.api.ITMBuilder.ITMDocument[] docs()
	{
		com.iflytek.itm.api.ITMBuilder.ITMDocument itmDocs[] = null;
		try
		{
			ScoreDoc scoreDocs[] = topDocs.scoreDocs;
			int len = 0;
			if (scoreDocs != null)
				len = scoreDocs.length;
			itmDocs = new com.iflytek.itm.api.ITMBuilder.ITMDocument[len];
			for (int i = 0; i < itmDocs.length; i++)
			{
				int docID = scoreDocs[i].doc;
				Document doc = searchWrapper.searcher.doc(docID);
				itmDocs[i] = luceneDoc2ItmDoc(doc);
			}

		}
		catch (IOException e)
		{
			logger.error((new StringBuilder()).append("doc | Error: ").append(e.getMessage()).toString());
		}
		return itmDocs;
	}

	private com.iflytek.itm.api.ITMBuilder.ITMDocument luceneDoc2ItmDoc(Document doc)
	{
		com.iflytek.itm.api.ITMBuilder.ITMDocument itmDocument = new com.iflytek.itm.api.ITMBuilder.ITMDocument();
		List fields = doc.getFields();
		Iterator iterator = fields.iterator();
		do
		{
			if (!iterator.hasNext())
				break;
			IndexableField field_i = (IndexableField)iterator.next();
			Field field = (Field)field_i;
			String name = field.name();
			if (!name.equals(ITMConstants.ITM_SUB_DIR_NAME_INNER_FIELD))
			{
				com.iflytek.itm.api.ITMBuilder.ITMField itmField = searchWrapper.itmUserData.getField(name);
				String itmType = getFieldType(field);
				String value = field.stringValue();
				boolean isPrimary = itmField.isPrimaryKey;
				String analyzer = itmField.analyzer;
				itmDocument.add(new com.iflytek.itm.api.ITMBuilder.ITMField(name, itmType, value, analyzer, isPrimary));
			}
		} while (true);
		return itmDocument;
	}

	private String getFieldType(Field field)
	{
		String itmType = null;
		if (field.stringValue() != null)
			itmType = "string";
		Number num = field.numericValue();
		if (num instanceof Integer)
			itmType = "int";
		else
		if (num instanceof Long)
			itmType = "long";
		else
		if (num instanceof Float)
			itmType = "float";
		else
		if (num instanceof Double)
			itmType = "double";
		return itmType;
	}

	public int getGroupTotalHits()
	{
		return topGroups.groupTotalHits;
	}

	public com.iflytek.itm.api.ITMSearchContext.ITMGroup[] groups()
	{
		return topGroups.groups;
	}

}
