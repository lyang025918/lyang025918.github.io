// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FaqUtils.java

package my.lucene.search;

import com.iflytek.itm.api.ITMFactory;
import java.io.File;
import java.io.PrintStream;
import java.util.*;
import mylib.file.FileInfo;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.*;

public class FaqUtils
{

	private static final Logger logger = Logger.getLogger(my/lucene/search/FaqUtils);

	public static void main(String args[])
	{
		System.out.println("学习索引。。。");
		ITMFactory.inst();
		String index = "e:\\study\\source\\java\\lucene\\index\\10w";
		if (args.length > 0)
			index = args[0];
		try
		{
			FaqUtils faqUtils = new FaqUtils();
			faqUtils.listFieldTerms(index);
		}
		catch (Exception e)
		{
			System.out.println((new StringBuilder()).append("Error: ").append(e.getMessage()).toString());
		}
	}

	public FaqUtils()
		throws Exception
	{
	}

	public void docNum(String index)
		throws Exception
	{
		FSDirectory directory = FSDirectory.open(new File(index));
		IndexReader reader = DirectoryReader.open(directory);
		System.out.println((new StringBuilder()).append("total_doc=").append(reader.maxDoc()).append(", del_doc=").append(reader.numDeletedDocs()).append(", cur_doc=").append(reader.numDocs()).toString());
		reader.close();
	}

	public void listUserData(String index)
		throws Exception
	{
		FSDirectory directory = FSDirectory.open(new File(index));
		IndexReader reader = DirectoryReader.open(directory);
		System.out.println((new StringBuilder()).append("total_doc=").append(reader.maxDoc()).append(", del_doc=").append(reader.numDeletedDocs()).append(", cur_doc=").append(reader.numDocs()).toString());
		SegmentInfos infos = new SegmentInfos();
		infos.read(directory);
		Map userData = infos.getUserData();
		Set fields = userData.keySet();
		String key;
		String value;
		for (Iterator it = fields.iterator(); it.hasNext(); System.out.println((new StringBuilder()).append("key=").append(key).append(", value=").append(value).toString()))
		{
			key = (String)it.next();
			value = (String)userData.get(key);
		}

	}

	public void listDocs(String index)
		throws Exception
	{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		int maxDoc = reader.maxDoc();
		int numDoc = reader.numDocs();
		System.out.println((new StringBuilder()).append("maxDoc=").append(maxDoc).append(", numDoc=").append(numDoc).toString());
		for (int i = 0; i < maxDoc; i++)
		{
			Document doc = reader.document(i);
			String id = doc.get("id");
			String path = doc.get("path");
			String time = doc.get("time");
			String content = doc.get("content");
			String phone = doc.get("phone");
			String onebest = doc.get("onebest");
			System.out.println((new StringBuilder()).append("doc ").append(i).append(", id=").append(id).append(", path=").append(path).append(", time=").append(time).append(", content=").append(content).append(", phone=").append(phone).append(", onebest=").append(onebest).toString());
		}

	}

	public void listFields(String index)
		throws Exception
	{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		List leaves = reader.leaves();
		System.out.println((new StringBuilder()).append("AtomicReaderContext size=").append(leaves.size()).toString());
		for (int i = 0; i < leaves.size(); i++)
		{
			AtomicReaderContext leaf = (AtomicReaderContext)leaves.get(i);
			AtomicReader ar = leaf.reader();
			Fields fields = ar.fields();
			FieldInfos fieldInfos = ar.getFieldInfos();
			System.out.println((new StringBuilder()).append("AtomicReaderContext ").append(i).append(", fields size=").append(fields.size()).toString());
			String field;
			for (Iterator iterator = fields.iterator(); iterator.hasNext(); System.out.println((new StringBuilder()).append("\tfield = ").append(field).toString()))
				field = (String)iterator.next();

		}

	}

	public void listTerms(String index)
		throws Exception
	{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		List leaves = reader.leaves();
		System.out.println((new StringBuilder()).append("AtomicReaderContext size=").append(leaves.size()).toString());
		int i = 0;
label0:
		do
		{
label1:
			{
				if (i >= leaves.size())
					break label0;
				AtomicReaderContext leaf = (AtomicReaderContext)leaves.get(i);
				AtomicReader ar = leaf.reader();
				Fields fields = ar.fields();
				System.out.println((new StringBuilder()).append("AtomicReaderContext ").append(i).append(", fields size=").append(fields.size()).toString());
				Iterator iterator = fields.iterator();
				do
				{
					String field;
					do
					{
						if (!iterator.hasNext())
							break label1;
						field = (String)iterator.next();
					} while (!field.equals("id"));
					System.out.println((new StringBuilder()).append("\tfield = ").append(field).toString());
					Terms terms = ar.terms(field);
					TermsEnum termsEnum = terms.iterator(null);
					do
					{
						BytesRef bytesRef = termsEnum.next();
						if (bytesRef == null)
							break;
						String termText = bytesRef.utf8ToString();
						System.out.println((new StringBuilder()).append("\t\t term=").append(termText).toString());
					} while (true);
				} while (true);
			}
			i++;
		} while (true);
	}

	public void listTermDocFreqs(String index)
		throws Exception
	{
		List leaves;
		int i;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		leaves = reader.leaves();
		System.out.println((new StringBuilder()).append("AtomicReaderContext size=").append(leaves.size()).toString());
		i = 0;
_L7:
		AtomicReader ar;
		Bits liveDocBits;
		Iterator iterator;
		if (i >= leaves.size())
			break; /* Loop/switch isn't completed */
		AtomicReaderContext leaf = (AtomicReaderContext)leaves.get(i);
		ar = leaf.reader();
		liveDocBits = ar.getLiveDocs();
		Fields fields = ar.fields();
		System.out.println((new StringBuilder()).append("AtomicReaderContext ").append(i).append(", fields size=").append(fields.size()).toString());
		iterator = fields.iterator();
_L1:
		TermsEnum termsEnum;
		if (!iterator.hasNext())
			break MISSING_BLOCK_LABEL_343;
		String field = (String)iterator.next();
		System.out.println((new StringBuilder()).append("\tfield = ").append(field).toString());
		Terms terms = ar.terms(field);
		termsEnum = terms.iterator(null);
_L3:
		BytesRef bytesRef = termsEnum.next();
		if (bytesRef != null) goto _L2; else goto _L1
_L2:
		DocsEnum docsEnum;
		String termText = bytesRef.utf8ToString();
		System.out.println((new StringBuilder()).append("\t\t term=").append(termText).toString());
		docsEnum = termsEnum.docs(liveDocBits, null);
_L5:
		int docState = docsEnum.nextDoc();
		if (0x7fffffff != docState) goto _L4; else goto _L3
_L4:
		int docId = docsEnum.docID();
		int freq = docsEnum.freq();
		System.out.println((new StringBuilder()).append("\t\t\t doc=").append(docId).append(", freq=").append(freq).toString());
		  goto _L5
		  goto _L3
		i++;
		if (true) goto _L7; else goto _L6
_L6:
	}

	public void listTermDocPros(String index)
		throws Exception
	{
		List leaves;
		int i;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		leaves = reader.leaves();
		System.out.println((new StringBuilder()).append("AtomicReaderContext size=").append(leaves.size()).toString());
		i = 0;
_L6:
		AtomicReader ar;
		Bits liveDocBits;
		Iterator iterator;
		if (i >= leaves.size())
			break; /* Loop/switch isn't completed */
		AtomicReaderContext leaf = (AtomicReaderContext)leaves.get(i);
		ar = leaf.reader();
		liveDocBits = ar.getLiveDocs();
		Fields fields = ar.fields();
		System.out.println((new StringBuilder()).append("AtomicReaderContext ").append(i).append(", fields size=").append(fields.size()).toString());
		iterator = fields.iterator();
_L2:
		TermsEnum termsEnum;
		if (!iterator.hasNext())
			break MISSING_BLOCK_LABEL_482;
		String field = (String)iterator.next();
		System.out.println((new StringBuilder()).append("\tfield = ").append(field).toString());
		Terms terms = ar.terms(field);
		termsEnum = terms.iterator(null);
_L3:
		DocsAndPositionsEnum dape;
		BytesRef bytesRef = termsEnum.next();
		if (bytesRef != null)
		{
label0:
			{
				String termText = bytesRef.utf8ToString();
				System.out.println((new StringBuilder()).append("\t\t term=").append(termText).toString());
				dape = termsEnum.docsAndPositions(liveDocBits, null);
				if (dape != null)
					break label0;
				System.out.println("\t\t\t no position info");
			}
		}
		if (true) goto _L2; else goto _L1
_L1:
		int docState = dape.nextDoc();
		if (0x7fffffff != docState) goto _L4; else goto _L3
_L4:
		int docId = dape.docID();
		int freq = dape.freq();
		System.out.println((new StringBuilder()).append("\t\t\t doc=").append(docId).append(", freq=").append(freq).toString());
		System.out.printf("\t\t\t\t pos=", new Object[0]);
		for (int ifreq = 0; ifreq < freq; ifreq++)
		{
			int pos = dape.nextPosition();
			int bos = dape.startOffset();
			int eos = dape.endOffset();
			BytesRef payload = dape.getPayload();
			String strPayload = new String(payload.bytes, "utf-8");
			System.out.printf("%d(%d,%d,%s), ", new Object[] {
				Integer.valueOf(pos), Integer.valueOf(bos), Integer.valueOf(eos), strPayload
			});
		}

		System.out.println("");
		  goto _L1
		  goto _L3
		i++;
		if (true) goto _L6; else goto _L5
_L5:
	}

	public boolean isFileExisted(String index, String id)
		throws Exception
	{
		boolean ret = false;
		int docId = -1;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		String field = "id";
		int value = 0xf42bb;
		BytesRef bytes = new BytesRef(6);
		NumericUtils.intToPrefixCoded(value, 0, bytes);
		Term term = new Term(field, bytes);
		Query query = new TermQuery(term);
		TopDocs topDocs = searcher.search(query, 10);
		int totalHits = topDocs.totalHits;
		ScoreDoc scoreDocs[] = topDocs.scoreDocs;
		if (scoreDocs.length == 1)
		{
			docId = scoreDocs[0].doc;
			ret = true;
		}
		System.out.printf("isFileExisted ? id=[%s], fid=[%d], answer=[%s]\n", new Object[] {
			id, Integer.valueOf(docId), ret ? "true" : "false"
		});
		return ret;
	}

	public void updateDoc(String index, String path)
		throws Exception
	{
		int docId = 0;
		org.apache.lucene.store.Directory dir = FSDirectory.open(new File(index));
		org.apache.lucene.analysis.Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_40);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_40, analyzer);
		iwc.setOpenMode(org.apache.lucene.index.IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(dir, iwc);
		File file = new File(path);
		Document doc = new Document();
		Field pathField = new StringField("path", file.getPath(), org.apache.lucene.document.Field.Store.YES);
		doc.add(pathField);
		String fileTitle = FileInfo.getNameNoExt(file);
		Field titleField = new StringField("title", fileTitle, org.apache.lucene.document.Field.Store.YES);
		doc.add(titleField);
		doc.add(new LongField("modified", file.lastModified(), org.apache.lucene.document.Field.Store.YES));
		String content = "我是中国人";
		Field contentField = new TextField("content", content, org.apache.lucene.document.Field.Store.YES);
		doc.add(contentField);
		writer.updateDocument(new Term("path", file.getPath()), doc);
		writer.close();
	}

	public void getSomeTermDocPos(String index, String word, int docId)
		throws Exception
	{
		word = "套餐";
		docId = 3555;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		List leaves = reader.leaves();
		System.out.println((new StringBuilder()).append("AtomicReaderContext size=").append(leaves.size()).toString());
		Document doc = reader.document(docId);
		String content = doc.get("content");
		System.out.println((new StringBuilder()).append("content=").append(content).toString());
		boolean isFound = false;
label0:
		for (int i = 0; i < leaves.size(); i++)
		{
			AtomicReaderContext leaf = (AtomicReaderContext)leaves.get(i);
			AtomicReader ar = leaf.reader();
			if (leaf.docBase > docId)
				continue;
			int segDocId = docId - leaf.docBase;
			DocsAndPositionsEnum it = ar.termPositionsEnum(new Term("content", word));
			if (it == null)
				continue;
			int dst = 0;
			do
			{
				do
					if ((dst = it.advance(segDocId)) == 0x7fffffff)
						continue label0;
				while (dst != segDocId);
				isFound = true;
				System.out.println((new StringBuilder()).append("word=").append(word).append(", pos=").append(it.nextPosition()).append(", start=").append(it.startOffset()).append(", end=").append(it.endOffset()).toString());
			} while (true);
		}

		if (!isFound)
			System.out.println("没有找到...");
	}

	public void listFieldTerms(String index)
		throws Exception
	{
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(index)));
		String fieldName = "audio_uri";
		Terms terms = MultiFields.getTerms(reader, fieldName);
		logger.info((new StringBuilder()).append("field=").append(fieldName).toString());
		TermsEnum termsEnum = terms.iterator(null);
		do
		{
			BytesRef bytesRef = termsEnum.next();
			if (bytesRef != null)
			{
				String termText = bytesRef.utf8ToString();
				logger.info((new StringBuilder()).append("\t\t term=").append(termText).toString());
			} else
			{
				return;
			}
		} while (true);
	}

	public void getJvmSize()
		throws Exception
	{
		int mb = 0x100000;
		Runtime runtime = Runtime.getRuntime();
		System.out.println("##### Heap utilization statistics [MB] #####");
		System.out.println((new StringBuilder()).append("Free Memory:").append(runtime.freeMemory() / (long)mb).append("M").toString());
		System.out.println((new StringBuilder()).append("Total Memory:").append(runtime.totalMemory() / (long)mb).append("M").toString());
		System.out.println((new StringBuilder()).append("Max Memory:").append(runtime.maxMemory() / (long)mb).append("M").toString());
	}

}
