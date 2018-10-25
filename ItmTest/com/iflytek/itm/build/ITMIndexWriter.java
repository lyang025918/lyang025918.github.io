// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMIndexWriter.java

package com.iflytek.itm.build;

import com.iflytek.itm.api.ITMBuilder;
import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.util.*;
import java.io.IOException;
import java.util.*;
import org.apache.log4j.Logger;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.*;

// Referenced classes of package com.iflytek.itm.build:
//			ITMUserData

public class ITMIndexWriter
{

	private static final Logger logger = Logger.getLogger(com/iflytek/itm/build/ITMIndexWriter);
	private IndexWriter writer;
	private IndexReader reader;
	private Set ids;
	private static ITMIndexWriter inst = new ITMIndexWriter();

	private ITMIndexWriter()
	{
		writer = null;
		reader = null;
		ids = null;
		Thread.currentThread().setContextClassLoader(ClassLoader.getSystemClassLoader());
	}

	public static ITMIndexWriter inst()
	{
		return inst;
	}

	public synchronized int build(String indexPath, String params, ITMBuilder itmBuilder)
	{
		int ret;
		ITMUserData itmUserData;
		ret = 0;
		logger.info("build | enter.");
		itmUserData = new ITMUserData();
		ITMParamParser itmParamParser = new ITMParamParser();
		itmParamParser.clear();
		ret = itmParamParser.parse(params);
		ret = checkParams(ret, itmParamParser);
		if (ret != 0)
		{
			logger.error((new StringBuilder()).append("build | Error: buildParamParser, params=").append(params).append(", errcode=").append(ret).toString());
		} else
		{
			if (itmParamParser.isDelete || itmParamParser.isUpdate)
				ret = itmUserData.openIndex(indexPath, itmParamParser.subIndexDir, false);
			else
				ret = itmUserData.openIndex(indexPath, itmParamParser.subIndexDir, true);
			if (ret != 0)
			{
				logger.error((new StringBuilder()).append("build | Error: openIndex, params=").append(params).append(", errcode=").append(ret).toString());
			} else
			{
				open(itmParamParser, itmUserData);
				ret = buildLoop(params, itmBuilder, itmParamParser, itmUserData);
				itmParamParser = null;
			}
		}
		ret = close(ret, itmUserData);
		break MISSING_BLOCK_LABEL_299;
		Exception e;
		e;
		ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
		logger.error((new StringBuilder()).append("build | catch errorMsg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		ret = close(ret, itmUserData);
		break MISSING_BLOCK_LABEL_299;
		Exception exception;
		exception;
		ret = close(ret, itmUserData);
		throw exception;
		itmUserData = null;
		logger.info("build | leave.");
		return ret;
	}

	private int buildLoop(String params, ITMBuilder itmBuilder, ITMParamParser itmParamParser, ITMUserData itmUserData)
		throws IOException
	{
		int ret = 0;
		com.iflytek.itm.api.ITMBuilder.ITMDocument itmDocument = null;
		Document document = null;
		while ((itmDocument = itmBuilder.read()) != null) 
		{
			document = new Document();
			ret = itmDocToLuceneDoc(itmDocument, document, itmParamParser, itmUserData);
			if (ret != 0)
			{
				ret = errorReport(ret, itmBuilder, itmDocument, params);
			} else
			{
				com.iflytek.itm.api.ITMBuilder.ITMField itmKeyField = itmDocument.getPrimary();
				Term keyTerm = toTerm(itmKeyField.name, itmKeyField.value, itmKeyField.type);
				if (itmParamParser.isDelete)
				{
					if (fileExisted(itmUserData.getDirectory(), keyTerm) == 0)
						ret = errorReport(ITMErrors.ITM_ERROR_DELETE_DOCUMENT_NOT_EXISTED, itmBuilder, itmDocument, params);
					else
						writer.deleteDocuments(keyTerm);
				} else
				if (itmParamParser.isUpdate)
					writer.updateDocument(keyTerm, document, itmUserData.getAnalyzer());
				else
				if (ids.contains(itmKeyField.value) || fileExisted(itmUserData.getDirectory(), keyTerm) != 0)
				{
					ret = errorReport(ITMErrors.ITM_ERROR_DOCUMENT_ALREADY_EXISTED, itmBuilder, itmDocument, params);
				} else
				{
					writer.addDocument(document, itmUserData.getAnalyzer());
					ids.add(itmKeyField.value);
				}
			}
		}
		return ret;
	}

	private int close(int ret, ITMUserData itmUserData)
	{
		if (writer != null && (ids.size() > 0 || ret == ITMErrors.ITM_SUCCESS.code()))
			writer.commit(itmUserData.toLuceneUserData());
		Exception e;
		try
		{
			if (writer != null)
			{
				writer.close();
				writer.getDirectory().close();
				writer = null;
			}
			if (reader != null)
			{
				reader.close();
				reader = null;
			}
			if (ids != null)
			{
				ids.clear();
				ids = null;
			}
		}
		// Misplaced declaration of an exception variable
		catch (Exception e) { }
		break MISSING_BLOCK_LABEL_316;
		e;
		ret = ITMErrors.ITM_ERROR_INDEX_IO_EXCEPTION.code();
		logger.error((new StringBuilder()).append("build | close finally errorMsg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		try
		{
			if (writer != null)
			{
				writer.close();
				writer.getDirectory().close();
				writer = null;
			}
			if (reader != null)
			{
				reader.close();
				reader = null;
			}
			if (ids != null)
			{
				ids.clear();
				ids = null;
			}
		}
		// Misplaced declaration of an exception variable
		catch (Exception e) { }
		break MISSING_BLOCK_LABEL_316;
		Exception exception;
		exception;
		try
		{
			if (writer != null)
			{
				writer.close();
				writer.getDirectory().close();
				writer = null;
			}
			if (reader != null)
			{
				reader.close();
				reader = null;
			}
			if (ids != null)
			{
				ids.clear();
				ids = null;
			}
		}
		catch (Exception e) { }
		throw exception;
		return ret;
	}

	private void open(ITMParamParser itmParamParser, ITMUserData itmUserData)
		throws IOException
	{
		if (writer == null)
		{
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, itmUserData.getAnalyzer());
			iwc.setOpenMode(org.apache.lucene.index.IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			double ram = getRAMBufferSizeMB();
			iwc.setRAMBufferSizeMB(ram);
			int maxDocs = itmParamParser.maxBufferedDocs;
			iwc.setMaxBufferedDocs(maxDocs);
			TieredMergePolicy itmMergePol = new TieredMergePolicy();
			itmMergePol.setMaxMergedSegmentMB(512D);
			iwc.setMergePolicy(itmMergePol);
			if (!itmParamParser.doMerge)
			{
				iwc.setMergePolicy(NoMergePolicy.NO_COMPOUND_FILES);
				iwc.setMergeScheduler(NoMergeScheduler.INSTANCE);
			}
			if (!itmParamParser.doOptimize)
				iwc.setIndexDeletionPolicy(NoDeletionPolicy.INSTANCE);
			logger.info((new StringBuilder()).append("build | RAMBufferSizeMB=").append(ram).append(" MB, MaxBufferedDocs=").append(maxDocs).append(", doMerge=").append(itmParamParser.doMerge).append(", doOptimize=").append(itmParamParser.doOptimize).toString());
			writer = new IndexWriter(itmUserData.getDirectory(), iwc);
			try
			{
				reader = DirectoryReader.open(itmUserData.getDirectory());
			}
			catch (Exception e) { }
			ids = null;
			ids = new HashSet(1024);
		}
	}

	private int errorReport(ITMErrors error, ITMBuilder itmBuilder, com.iflytek.itm.api.ITMBuilder.ITMDocument itmDocument, String params)
	{
		int ret = error.code();
		com.iflytek.itm.api.ITMBuilder.ITMField field = itmDocument.getPrimary();
		String id = field != null ? field.value : "";
		String msg = (new StringBuilder()).append("ITMIndexWriter.buildLoop | Error: ").append(error.msg()).append(", id=").append(id).append(", params=").append(params).append(", errcode=").append(ret).toString();
		logger.error(msg);
		itmBuilder.event(id, ret, msg);
		return ret;
	}

	private int errorReport(int errcode, ITMBuilder itmBuilder, com.iflytek.itm.api.ITMBuilder.ITMDocument itmDocument, String params)
	{
		int ret = errcode;
		String msg = (new StringBuilder()).append("ITMIndexWriter.buildLoop | Error: ").append(ITMErrors.msg(errcode)).append(", params=").append(params).append(", errcode=").append(ret).toString();
		logger.error(msg);
		com.iflytek.itm.api.ITMBuilder.ITMField field = itmDocument.getPrimary();
		itmBuilder.event(field != null ? field.value : "", ret, msg);
		return ret;
	}

	private double getRAMBufferSizeMB()
	{
		int mb = 0x100000;
		double ram = Runtime.getRuntime().maxMemory() / (long)mb;
		return ram - 45D;
	}

	private int itmDocToLuceneDoc(com.iflytek.itm.api.ITMBuilder.ITMDocument itmDocument, Document document, ITMParamParser itmParamParser, ITMUserData itmUserData)
	{
		int ret = 0;
		int keyFieldCnt = 0;
		List fields = itmDocument.getFields();
		Field innerField = fields.iterator();
		do
		{
			if (!innerField.hasNext())
				break;
			com.iflytek.itm.api.ITMBuilder.ITMField itmField = (com.iflytek.itm.api.ITMBuilder.ITMField)innerField.next();
			if (itmField.isPrimaryKey)
				keyFieldCnt++;
			if (!ITMUtils.isValidStr(itmField.name))
			{
				ret = ITMErrors.ITM_ERROR_FIELD_NAME_EMPTY.code();
				logger.error((new StringBuilder()).append("itmDocToLuceneDoc | Error, errcode=").append(ret).append(", msg=").append(ITMErrors.ITM_ERROR_FIELD_NAME_EMPTY.msg()).toString());
				break;
			}
			if (!ITMUtils.isLegalStr(itmField.name))
			{
				ret = ITMErrors.ITM_ERROR_FIELD_ILLEGAL.code();
				logger.error((new StringBuilder()).append("itmDocToLuceneDoc | Error, errcode=").append(ret).append(", msg=").append(ITMErrors.ITM_ERROR_FIELD_ILLEGAL.msg()).toString());
				break;
			}
			Field field = toLuceneField(itmField, document, itmUserData);
			if (field == null)
			{
				ret = ITMErrors.ITM_ERROR_FIELD_TYPE_NOT_SUPPORT.code();
				logger.error((new StringBuilder()).append("itmDocToLuceneDoc | Error, errcode=").append(ret).append(", msg=").append(ITMErrors.ITM_ERROR_FIELD_TYPE_NOT_SUPPORT.msg()).append(", primaryKey=").append(itmDocument.getPrimary() != null ? itmDocument.getPrimary().name : "").append(", value=").append(itmDocument.getPrimary() != null ? itmDocument.getPrimary().value : "").toString());
				break;
			}
			ret = itmUserData.addField(itmField);
			if (ret != 0)
			{
				logger.error((new StringBuilder()).append("itmDocToLuceneDoc | Error, userData.addField, errcode=").append(ret).toString());
				break;
			}
			if (null != document.getField(field.name()))
			{
				ret = ITMErrors.ITM_ERROR_FIELD_REPEAT.code();
				logger.error((new StringBuilder()).append("itmDocToLuceneDoc | Error, document.add, errcode=").append(ret).append(", msg=").append(ITMErrors.ITM_ERROR_FIELD_REPEAT.msg()).toString());
				break;
			}
			document.add(field);
		} while (true);
		innerField = new StringField(ITMConstants.ITM_SUB_DIR_NAME_INNER_FIELD, itmParamParser.subIndexDir, org.apache.lucene.document.Field.Store.YES);
		document.add(innerField);
		if (keyFieldCnt != 1)
		{
			ret = ITMErrors.ITM_ERROR_PRIMARY_KEY_NOT_UNIQUE.code();
			logger.error((new StringBuilder()).append("itmDocToLuceneDoc | Error, errcode=").append(ret).append(", msg=").append(ITMErrors.ITM_ERROR_PRIMARY_KEY_NOT_UNIQUE.msg()).toString());
		}
		return ret;
	}

	private int fileExisted(Directory directory, Term keyTerm)
	{
		int ret = 0;
		try
		{
			int docFreq = reader.docFreq(keyTerm);
			if (docFreq == 1)
				ret = ITMErrors.ITM_ERROR_DOCUMENT_ALREADY_EXISTED.code();
			else
			if (docFreq > 1)
			{
				ret = ITMErrors.ITM_FAIL.code();
				logger.error((new StringBuilder()).append("fileExisted | Error: 检索主键怎么可能检索出：").append(docFreq).append(" 个! errcode=").append(ret).toString());
			}
		}
		catch (Exception e) { }
		return ret;
	}

	private Document retrieveOldDocument(Term keyTerm)
	{
		TopDocs topDocs;
		IndexSearcher searcher = new IndexSearcher(reader);
		TermQuery termQuery = new TermQuery(keyTerm);
		topDocs = searcher.search(termQuery, 1);
		if (topDocs.totalHits != 1)
			return null;
		int docInnerID = topDocs.scoreDocs[0].doc;
		return reader.document(docInnerID);
		IOException e;
		e;
		return null;
	}

	public static Term toTerm(String field, String value, String type)
	{
		Term term = null;
		type = type.trim().toLowerCase();
		if (type.equals("string"))
			term = new Term(field, value);
		else
		if (type.equals("int"))
		{
			BytesRef bytes = new BytesRef(6);
			NumericUtils.intToPrefixCoded(Integer.valueOf(value).intValue(), 0, bytes);
			term = new Term(field, bytes);
		} else
		if (type.equals("long"))
		{
			BytesRef bytes = new BytesRef(11);
			NumericUtils.longToPrefixCoded(Long.valueOf(value).longValue(), 0, bytes);
			term = new Term(field, bytes);
		} else
		if (type.equals("float"))
			logger.error((new StringBuilder()).append("toTerm | Error, not support field type, type=").append(type).toString());
		else
		if (type.equals("double"))
			logger.error((new StringBuilder()).append("toTerm | Error, not support field type, type=").append(type).toString());
		else
			logger.error((new StringBuilder()).append("toTerm | Error, not support field type, type=").append(type).toString());
		return term;
	}

	private Field toLuceneField(com.iflytek.itm.api.ITMBuilder.ITMField itmField, Document document, ITMUserData itmUserData)
	{
		Field field = null;
		String type = itmField.type.trim().toLowerCase();
		try
		{
			if (type.equals("string"))
				field = getStringField(itmField, document, itmUserData);
			else
			if (type.equals("int"))
				field = new IntField(itmField.name, Integer.valueOf(itmField.value).intValue(), org.apache.lucene.document.Field.Store.YES);
			else
			if (type.equals("long"))
				field = new LongField(itmField.name, Long.valueOf(itmField.value).longValue(), org.apache.lucene.document.Field.Store.YES);
			else
			if (type.equals("float"))
				field = new FloatField(itmField.name, Float.valueOf(itmField.value).floatValue(), org.apache.lucene.document.Field.Store.YES);
			else
			if (type.equals("double"))
				field = new DoubleField(itmField.name, Double.valueOf(itmField.value).doubleValue(), org.apache.lucene.document.Field.Store.YES);
			else
				logger.error((new StringBuilder()).append("toLuceneField | Error, not support field type, type=").append(type).toString());
		}
		catch (NumberFormatException e)
		{
			logger.error((new StringBuilder()).append("toLuceneField | Error，传入的field值有问题, msg=").append(e.getMessage()).append(", field=").append(itmField.toString()).append(", field_value=").append(itmField.value).toString());
		}
		itmField.type = type;
		return field;
	}

	private Field getStringField(com.iflytek.itm.api.ITMBuilder.ITMField itmField, Document document, ITMUserData itmUserData)
	{
		Field field = null;
		if (ITMUtils.isValidStr(itmField.analyzer) && !itmField.analyzer.equals("null"))
		{
			field = newTextField(itmField.name, itmField.value);
			if (!itmField.name.startsWith("_isa40"))
			{
				Field ruleField = newRuleField((new StringBuilder()).append(ITMConstants.ITM_RULE_INNER_FIELD_PREFIX).append(itmField.name).toString(), itmField.value);
				document.add(ruleField);
				itmUserData.putRuleAnalyzer((new StringBuilder()).append(ITMConstants.ITM_RULE_INNER_FIELD_PREFIX).append(itmField.name).toString());
			}
		} else
		{
			field = new StringField(itmField.name, itmField.value, org.apache.lucene.document.Field.Store.YES);
		}
		return field;
	}

	public static Field newTextField(String name, String value)
	{
		FieldType fieldType = new FieldType();
		org.apache.lucene.index.FieldInfo.IndexOptions indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
		fieldType.setIndexOptions(indexOptions);
		fieldType.setIndexed(true);
		fieldType.setTokenized(true);
		fieldType.setStored(true);
		fieldType.freeze();
		return new Field(name, value, fieldType);
	}

	public static Field newRuleField(String name, String value)
	{
		FieldType fieldType = new FieldType();
		org.apache.lucene.index.FieldInfo.IndexOptions indexOptions = org.apache.lucene.index.FieldInfo.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS;
		fieldType.setIndexOptions(indexOptions);
		fieldType.setIndexed(true);
		fieldType.setTokenized(true);
		fieldType.setStored(false);
		fieldType.freeze();
		return new Field(name, value, fieldType);
	}

	private int checkParams(int preRet, ITMParamParser itmParamParser)
	{
		if (preRet != 0)
		{
			logger.error((new StringBuilder()).append("build | Error: checkParams, msg=参数格式错误, errcode=").append(preRet).toString());
			return preRet;
		}
		int ret = 0;
		if (itmParamParser.subIndexDir == null || itmParamParser.subIndexDir.isEmpty())
		{
			ret = ITMErrors.ITM_ERROR_NO_SUB_INDEX_DIR.code();
			logger.error((new StringBuilder()).append("build | Error: checkParams, msg=没有指定索引子目录, errcode=").append(ret).toString());
		} else
		if (itmParamParser.isDelete && itmParamParser.isUpdate)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_PARAM.code();
			logger.error((new StringBuilder()).append("build | Error: checkParams, msg=删除和更新不能同时为true, errcode=").append(ret).toString());
		}
		return ret;
	}

}
