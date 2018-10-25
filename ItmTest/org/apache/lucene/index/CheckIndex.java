// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   CheckIndex.java

package org.apache.lucene.index;

import java.io.*;
import java.text.NumberFormat;
import java.util.*;
import org.apache.lucene.codecs.BlockTreeTermsReader;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.lucene3x.Lucene3xSegmentInfoFormat;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.*;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			SegmentInfos, SegmentInfoPerCommit, SegmentReader, FieldInfo, 
//			FieldInfos, Fields, DocsEnum, DocsAndPositionsEnum, 
//			Terms, TermsEnum, DocValues, SegmentInfo

public class CheckIndex
{
	public static class Status
	{

		public boolean clean;
		public boolean missingSegments;
		public boolean cantOpenSegments;
		public boolean missingSegmentVersion;
		public String segmentsFileName;
		public int numSegments;
		public List segmentsChecked;
		public boolean toolOutOfDate;
		public List segmentInfos;
		public Directory dir;
		SegmentInfos newSegments;
		public int totLoseDocCount;
		public int numBadSegments;
		public boolean partial;
		public int maxSegmentName;
		public boolean validCounter;
		public Map userData;

		Status()
		{
			segmentsChecked = new ArrayList();
			segmentInfos = new ArrayList();
		}
	}


	private PrintStream infoStream;
	private Directory dir;
	private boolean crossCheckTermVectors;
	private boolean verbose;
	private static boolean assertsOn;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/CheckIndex.desiredAssertionStatus();

	public CheckIndex(Directory dir)
	{
		this.dir = dir;
		infoStream = null;
	}

	public void setCrossCheckTermVectors(boolean v)
	{
		crossCheckTermVectors = v;
	}

	public boolean getCrossCheckTermVectors()
	{
		return crossCheckTermVectors;
	}

	public void setInfoStream(PrintStream out, boolean verbose)
	{
		infoStream = out;
		this.verbose = verbose;
	}

	public void setInfoStream(PrintStream out)
	{
		setInfoStream(out, false);
	}

	private void msg(String msg)
	{
		if (infoStream != null)
			infoStream.println(msg);
	}

	public Status checkIndex()
		throws IOException
	{
		return checkIndex(null);
	}

	public Status checkIndex(List onlySegments)
		throws IOException
	{
		NumberFormat nf;
		SegmentInfos sis;
		Status result;
		String oldest;
		String newest;
		String oldSegs;
		boolean foundNonNullVersion;
		int numSegments;
		String segmentsFileName;
		IndexInput input;
		int format;
		nf = NumberFormat.getInstance(Locale.ROOT);
		sis = new SegmentInfos();
		result = new Status();
		result.dir = dir;
		try
		{
			sis.read(dir);
		}
		catch (Throwable t)
		{
			msg("ERROR: could not read any segments file in directory");
			result.missingSegments = true;
			if (infoStream != null)
				t.printStackTrace(infoStream);
			return result;
		}
		oldest = Integer.toString(0x7fffffff);
		newest = Integer.toString(0x80000000);
		oldSegs = null;
		foundNonNullVersion = false;
		Comparator versionComparator = StringHelper.getVersionComparator();
		Iterator i$ = sis.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			SegmentInfoPerCommit si = (SegmentInfoPerCommit)i$.next();
			String version = si.info.getVersion();
			if (version == null)
			{
				oldSegs = "pre-3.1";
			} else
			{
				foundNonNullVersion = true;
				if (versionComparator.compare(version, oldest) < 0)
					oldest = version;
				if (versionComparator.compare(version, newest) > 0)
					newest = version;
			}
		} while (true);
		numSegments = sis.size();
		segmentsFileName = sis.getSegmentsFileName();
		input = null;
		try
		{
			input = dir.openInput(segmentsFileName, IOContext.DEFAULT);
		}
		catch (Throwable t)
		{
			msg("ERROR: could not open segments file in directory");
			if (infoStream != null)
				t.printStackTrace(infoStream);
			result.cantOpenSegments = true;
			return result;
		}
		format = 0;
		format = input.readInt();
		if (input != null)
			input.close();
		break MISSING_BLOCK_LABEL_344;
		Throwable t;
		t;
		Status status;
		msg("ERROR: could not read segment file version in directory");
		if (infoStream != null)
			t.printStackTrace(infoStream);
		result.missingSegmentVersion = true;
		status = result;
		if (input != null)
			input.close();
		return status;
		Exception exception;
		exception;
		if (input != null)
			input.close();
		throw exception;
		int i;
		String sFormat = "";
		boolean skip = false;
		result.segmentsFileName = segmentsFileName;
		result.numSegments = numSegments;
		result.userData = sis.getUserData();
		String userDataString;
		if (sis.getUserData().size() > 0)
			userDataString = (new StringBuilder()).append(" userData=").append(sis.getUserData()).toString();
		else
			userDataString = "";
		String versionString = null;
		if (oldSegs != null)
		{
			if (foundNonNullVersion)
				versionString = (new StringBuilder()).append("versions=[").append(oldSegs).append(" .. ").append(newest).append("]").toString();
			else
				versionString = (new StringBuilder()).append("version=").append(oldSegs).toString();
		} else
		{
			versionString = oldest.equals(newest) ? (new StringBuilder()).append("version=").append(oldest).toString() : (new StringBuilder()).append("versions=[").append(oldest).append(" .. ").append(newest).append("]").toString();
		}
		msg((new StringBuilder()).append("Segments file=").append(segmentsFileName).append(" numSegments=").append(numSegments).append(" ").append(versionString).append(" format=").append(sFormat).append(userDataString).toString());
		if (onlySegments != null)
		{
			result.partial = true;
			if (infoStream != null)
				infoStream.print("\nChecking only these segments:");
			Iterator i$ = onlySegments.iterator();
			do
			{
				if (!i$.hasNext())
					break;
				String s = (String)i$.next();
				if (infoStream != null)
					infoStream.print((new StringBuilder()).append(" ").append(s).toString());
			} while (true);
			result.segmentsChecked.addAll(onlySegments);
			msg(":");
		}
		if (skip)
		{
			msg("\nERROR: this index appears to be created by a newer version of Lucene than this tool was compiled on; please re-compile this tool on the matching version of Lucene; exiting");
			result.toolOutOfDate = true;
			return result;
		}
		result.newSegments = sis.clone();
		result.newSegments.clear();
		result.maxSegmentName = -1;
		i = 0;
_L3:
		if (i >= numSegments) goto _L2; else goto _L1
_L1:
		SegmentInfoPerCommit info;
		Status.SegmentInfoStatus segInfoStat;
		int toLoseDocCount;
		SegmentReader reader;
		info = sis.info(i);
		int segmentName = Integer.parseInt(info.info.name.substring(1), 36);
		if (segmentName > result.maxSegmentName)
			result.maxSegmentName = segmentName;
		if (onlySegments != null && !onlySegments.contains(info.info.name))
			continue; /* Loop/switch isn't completed */
		segInfoStat = new Status.SegmentInfoStatus();
		result.segmentInfos.add(segInfoStat);
		msg((new StringBuilder()).append("  ").append(1 + i).append(" of ").append(numSegments).append(": name=").append(info.info.name).append(" docCount=").append(info.info.getDocCount()).toString());
		segInfoStat.name = info.info.name;
		segInfoStat.docCount = info.info.getDocCount();
		toLoseDocCount = info.info.getDocCount();
		reader = null;
		Codec codec = info.info.getCodec();
		msg((new StringBuilder()).append("    codec=").append(codec).toString());
		segInfoStat.codec = codec;
		msg((new StringBuilder()).append("    compound=").append(info.info.getUseCompoundFile()).toString());
		segInfoStat.compound = info.info.getUseCompoundFile();
		msg((new StringBuilder()).append("    numFiles=").append(info.files().size()).toString());
		segInfoStat.numFiles = info.files().size();
		segInfoStat.sizeMB = (double)info.sizeInBytes() / 1048576D;
		if (info.info.getAttribute(Lucene3xSegmentInfoFormat.DS_OFFSET_KEY) == null)
			msg((new StringBuilder()).append("    size (MB)=").append(nf.format(segInfoStat.sizeMB)).toString());
		Map diagnostics = info.info.getDiagnostics();
		segInfoStat.diagnostics = diagnostics;
		if (diagnostics.size() > 0)
			msg((new StringBuilder()).append("    diagnostics = ").append(diagnostics).toString());
		if (!info.hasDeletions())
		{
			msg("    no deletions");
			segInfoStat.hasDeletions = false;
		} else
		{
			msg((new StringBuilder()).append("    has deletions [delGen=").append(info.getDelGen()).append("]").toString());
			segInfoStat.hasDeletions = true;
			segInfoStat.deletionsGen = info.getDelGen();
		}
		if (infoStream != null)
			infoStream.print("    test: open reader.........");
		reader = new SegmentReader(info, 1, IOContext.DEFAULT);
		segInfoStat.openReaderPassed = true;
		int numDocs = reader.numDocs();
		toLoseDocCount = numDocs;
		if (reader.hasDeletions())
		{
			if (reader.numDocs() != info.info.getDocCount() - info.getDelCount())
				throw new RuntimeException((new StringBuilder()).append("delete count mismatch: info=").append(info.info.getDocCount() - info.getDelCount()).append(" vs reader=").append(reader.numDocs()).toString());
			if (info.info.getDocCount() - reader.numDocs() > reader.maxDoc())
				throw new RuntimeException((new StringBuilder()).append("too many deleted docs: maxDoc()=").append(reader.maxDoc()).append(" vs del count=").append(info.info.getDocCount() - reader.numDocs()).toString());
			if (info.info.getDocCount() - numDocs != info.getDelCount())
				throw new RuntimeException((new StringBuilder()).append("delete count mismatch: info=").append(info.getDelCount()).append(" vs reader=").append(info.info.getDocCount() - numDocs).toString());
			Bits liveDocs = reader.getLiveDocs();
			if (liveDocs == null)
				throw new RuntimeException("segment should have deletions, but liveDocs is null");
			int numLive = 0;
			for (int j = 0; j < liveDocs.length(); j++)
				if (liveDocs.get(j))
					numLive++;

			if (numLive != numDocs)
				throw new RuntimeException((new StringBuilder()).append("liveDocs count mismatch: info=").append(numDocs).append(", vs bits=").append(numLive).toString());
			segInfoStat.numDeleted = info.info.getDocCount() - numDocs;
			msg((new StringBuilder()).append("OK [").append(segInfoStat.numDeleted).append(" deleted docs]").toString());
		} else
		{
			if (info.getDelCount() != 0)
				throw new RuntimeException((new StringBuilder()).append("delete count mismatch: info=").append(info.getDelCount()).append(" vs reader=").append(info.info.getDocCount() - numDocs).toString());
			Bits liveDocs = reader.getLiveDocs();
			if (liveDocs != null)
			{
				for (int j = 0; j < liveDocs.length(); j++)
					if (!liveDocs.get(j))
						throw new RuntimeException((new StringBuilder()).append("liveDocs mismatch: info says no deletions but doc ").append(j).append(" is deleted.").toString());

			}
			msg("OK");
		}
		if (reader.maxDoc() != info.info.getDocCount())
			throw new RuntimeException((new StringBuilder()).append("SegmentReader.maxDoc() ").append(reader.maxDoc()).append(" != SegmentInfos.docCount ").append(info.info.getDocCount()).toString());
		if (infoStream != null)
			infoStream.print("    test: fields..............");
		FieldInfos fieldInfos = reader.getFieldInfos();
		msg((new StringBuilder()).append("OK [").append(fieldInfos.size()).append(" fields]").toString());
		segInfoStat.numFields = fieldInfos.size();
		segInfoStat.fieldNormStatus = testFieldNorms(fieldInfos, reader);
		segInfoStat.termIndexStatus = testPostings(fieldInfos, reader);
		segInfoStat.storedFieldStatus = testStoredFields(info, reader, nf);
		segInfoStat.termVectorStatus = testTermVectors(fieldInfos, info, reader, nf);
		segInfoStat.docValuesStatus = testDocValues(info, fieldInfos, reader);
		if (segInfoStat.fieldNormStatus.error != null)
			throw new RuntimeException("Field Norm test failed");
		if (segInfoStat.termIndexStatus.error != null)
			throw new RuntimeException("Term Index test failed");
		if (segInfoStat.storedFieldStatus.error != null)
			throw new RuntimeException("Stored Field test failed");
		if (segInfoStat.termVectorStatus.error != null)
			throw new RuntimeException("Term Vector test failed");
		if (segInfoStat.docValuesStatus.error != null)
			throw new RuntimeException("DocValues test failed");
		msg("");
		if (reader != null)
			reader.close();
		break MISSING_BLOCK_LABEL_2309;
		Throwable t;
		t;
		msg("FAILED");
		String comment = "fixIndex() would remove reference to this segment";
		msg((new StringBuilder()).append("    WARNING: ").append(comment).append("; full exception:").toString());
		if (infoStream != null)
			t.printStackTrace(infoStream);
		msg("");
		result.totLoseDocCount += toLoseDocCount;
		result.numBadSegments++;
		if (reader != null)
			reader.close();
		continue; /* Loop/switch isn't completed */
		Exception exception1;
		exception1;
		if (reader != null)
			reader.close();
		throw exception1;
		result.newSegments.add(info.clone());
		i++;
		  goto _L3
_L2:
		if (0 == result.numBadSegments)
			result.clean = true;
		else
			msg((new StringBuilder()).append("WARNING: ").append(result.numBadSegments).append(" broken segments (containing ").append(result.totLoseDocCount).append(" documents) detected").toString());
		if (!(result.validCounter = result.maxSegmentName < sis.counter))
		{
			result.clean = false;
			result.newSegments.counter = result.maxSegmentName + 1;
			msg((new StringBuilder()).append("ERROR: Next segment name counter ").append(sis.counter).append(" is not greater than max segment name ").append(result.maxSegmentName).toString());
		}
		if (result.clean)
			msg("No problems were detected with this index.\n");
		return result;
	}

	private Status.FieldNormStatus testFieldNorms(FieldInfos fieldInfos, SegmentReader reader)
	{
		Status.FieldNormStatus status = new Status.FieldNormStatus();
		try
		{
			if (infoStream != null)
				infoStream.print("    test: field norms.........");
			for (Iterator i$ = fieldInfos.iterator(); i$.hasNext();)
			{
				FieldInfo info = (FieldInfo)i$.next();
				if (info.hasNorms())
				{
					if (!$assertionsDisabled && !reader.hasNorms(info.name))
						throw new AssertionError();
					DocValues dv = reader.normValues(info.name);
					checkDocValues(dv, info.name, info.getNormType(), reader.maxDoc());
					status.totFields++;
				} else
				{
					if (!$assertionsDisabled && reader.hasNorms(info.name))
						throw new AssertionError();
					if (reader.normValues(info.name) != null)
						throw new RuntimeException((new StringBuilder()).append("field: ").append(info.name).append(" should omit norms but has them!").toString());
				}
			}

			msg((new StringBuilder()).append("OK [").append(status.totFields).append(" fields]").toString());
		}
		catch (Throwable e)
		{
			msg((new StringBuilder()).append("ERROR [").append(String.valueOf(e.getMessage())).append("]").toString());
			status.error = e;
			if (infoStream != null)
				e.printStackTrace(infoStream);
		}
		return status;
	}

	private Status.TermIndexStatus checkFields(Fields fields, Bits liveDocs, int maxDoc, FieldInfos fieldInfos, boolean doPrint, boolean isVectors)
		throws IOException
	{
		Status.TermIndexStatus status;
		int computedFieldCount;
		DocsEnum docs;
		DocsEnum docsAndFreqs;
		DocsAndPositionsEnum postings;
		String lastField;
		Iterator i$;
		status = new Status.TermIndexStatus();
		computedFieldCount = 0;
		if (fields == null)
		{
			msg("OK [no fields/terms]");
			return status;
		}
		docs = null;
		docsAndFreqs = null;
		postings = null;
		lastField = null;
		i$ = fields.iterator();
_L11:
		String field;
		boolean hasPositions;
		boolean hasOffsets;
		boolean hasFreqs;
		TermsEnum termsEnum;
		boolean hasOrd;
		long termCountStart;
		BytesRef lastTerm;
		Comparator termComp;
		long sumTotalTermFreq;
		long sumDocFreq;
		FixedBitSet visitedDocs;
		if (!i$.hasNext())
			break; /* Loop/switch isn't completed */
		field = (String)i$.next();
		if (lastField != null && field.compareTo(lastField) <= 0)
			throw new RuntimeException((new StringBuilder()).append("fields out of order: lastField=").append(lastField).append(" field=").append(field).toString());
		lastField = field;
		FieldInfo fieldInfo = fieldInfos.fieldInfo(field);
		if (fieldInfo == null)
			throw new RuntimeException((new StringBuilder()).append("fieldsEnum inconsistent with fieldInfos, no fieldInfos for: ").append(field).toString());
		if (!fieldInfo.isIndexed())
			throw new RuntimeException((new StringBuilder()).append("fieldsEnum inconsistent with fieldInfos, isIndexed == false for: ").append(field).toString());
		computedFieldCount++;
		Terms terms = fields.terms(field);
		if (terms == null)
			continue; /* Loop/switch isn't completed */
		hasPositions = terms.hasPositions();
		hasOffsets = terms.hasOffsets();
		hasFreqs = isVectors || fieldInfo.getIndexOptions().compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS) >= 0;
		termsEnum = terms.iterator(null);
		hasOrd = true;
		termCountStart = status.termCount;
		lastTerm = null;
		termComp = terms.getComparator();
		sumTotalTermFreq = 0L;
		sumDocFreq = 0L;
		visitedDocs = new FixedBitSet(maxDoc);
_L2:
		BytesRef term;
		int idx;
		term = termsEnum.next();
		if (term == null)
			break MISSING_BLOCK_LABEL_2656;
		if (lastTerm == null)
		{
			lastTerm = BytesRef.deepCopyOf(term);
		} else
		{
			if (termComp.compare(lastTerm, term) >= 0)
				throw new RuntimeException((new StringBuilder()).append("terms out of order: lastTerm=").append(lastTerm).append(" term=").append(term).toString());
			lastTerm.copyBytes(term);
		}
		int docFreq = termsEnum.docFreq();
		if (docFreq <= 0)
			throw new RuntimeException((new StringBuilder()).append("docfreq: ").append(docFreq).append(" is out of bounds").toString());
		status.totFreq += docFreq;
		sumDocFreq += docFreq;
		docs = termsEnum.docs(liveDocs, docs);
		postings = termsEnum.docsAndPositions(liveDocs, postings);
		if (hasOrd)
		{
			long ord = -1L;
			try
			{
				ord = termsEnum.ord();
			}
			catch (UnsupportedOperationException uoe)
			{
				hasOrd = false;
			}
			if (hasOrd)
			{
				long ordExpected = status.termCount - termCountStart;
				if (ord != ordExpected)
					throw new RuntimeException((new StringBuilder()).append("ord mismatch: TermsEnum has ord=").append(ord).append(" vs actual=").append(ordExpected).toString());
			}
		}
		status.termCount++;
		DocsEnum docs2;
		if (postings != null)
			docs2 = postings;
		else
			docs2 = docs;
		int lastDoc = -1;
		int docCount = 0;
		long totalTermFreq = 0L;
		do
		{
			int doc = docs2.nextDoc();
			if (doc == 0x7fffffff)
				break;
			visitedDocs.set(doc);
			int freq = -1;
			if (hasFreqs)
			{
				freq = docs2.freq();
				if (freq <= 0)
					throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(": freq ").append(freq).append(" is out of bounds").toString());
				status.totPos += freq;
				totalTermFreq += freq;
			}
			docCount++;
			if (doc <= lastDoc)
				throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(" <= lastDoc ").append(lastDoc).toString());
			if (doc >= maxDoc)
				throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(" >= maxDoc ").append(maxDoc).toString());
			lastDoc = doc;
			int lastPos = -1;
			int lastOffset = 0;
			if (hasPositions)
			{
				int j = 0;
				while (j < freq) 
				{
					int pos = postings.nextPosition();
					if (pos < 0)
						throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(": pos ").append(pos).append(" is out of bounds").toString());
					if (pos < lastPos)
						throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(": pos ").append(pos).append(" < lastPos ").append(lastPos).toString());
					lastPos = pos;
					BytesRef payload = postings.getPayload();
					if (payload != null && payload.length < 1)
						throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(": pos ").append(pos).append(" payload length is out of bounds ").append(payload.length).toString());
					if (hasOffsets)
					{
						int startOffset = postings.startOffset();
						int endOffset = postings.endOffset();
						if (!isVectors)
						{
							if (startOffset < 0)
								throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(": pos ").append(pos).append(": startOffset ").append(startOffset).append(" is out of bounds").toString());
							if (startOffset < lastOffset)
								throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(": pos ").append(pos).append(": startOffset ").append(startOffset).append(" < lastStartOffset ").append(lastOffset).toString());
							if (endOffset < 0)
								throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(": pos ").append(pos).append(": endOffset ").append(endOffset).append(" is out of bounds").toString());
							if (endOffset < startOffset)
								throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(doc).append(": pos ").append(pos).append(": endOffset ").append(endOffset).append(" < startOffset ").append(startOffset).toString());
						}
						lastOffset = startOffset;
					}
					j++;
				}
			}
		} while (true);
		long totalTermFreq2 = termsEnum.totalTermFreq();
		boolean hasTotalTermFreq = hasFreqs && totalTermFreq2 != -1L;
		if (liveDocs != null)
			if (hasFreqs)
			{
				DocsEnum docsNoDel = termsEnum.docs(null, docsAndFreqs);
				docCount = 0;
				for (totalTermFreq = 0L; docsNoDel.nextDoc() != 0x7fffffff; totalTermFreq += docsNoDel.freq())
				{
					visitedDocs.set(docsNoDel.docID());
					docCount++;
				}

			} else
			{
				DocsEnum docsNoDel = termsEnum.docs(null, docs, 0);
				docCount = 0;
				totalTermFreq = -1L;
				while (docsNoDel.nextDoc() != 0x7fffffff) 
				{
					visitedDocs.set(docsNoDel.docID());
					docCount++;
				}
			}
		if (docCount != docFreq)
			throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(" docFreq=").append(docFreq).append(" != tot docs w/o deletions ").append(docCount).toString());
		if (hasTotalTermFreq)
		{
			if (totalTermFreq2 <= 0L)
				throw new RuntimeException((new StringBuilder()).append("totalTermFreq: ").append(totalTermFreq2).append(" is out of bounds").toString());
			sumTotalTermFreq += totalTermFreq;
			if (totalTermFreq != totalTermFreq2)
				throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(" totalTermFreq=").append(totalTermFreq2).append(" != recomputed totalTermFreq=").append(totalTermFreq).toString());
		}
		if (!hasPositions)
			break MISSING_BLOCK_LABEL_2448;
		idx = 0;
_L5:
		if (idx >= 7) goto _L2; else goto _L1
_L1:
		int skipDocID;
		int docID;
		skipDocID = (int)(((long)(idx + 1) * (long)maxDoc) / 8L);
		postings = termsEnum.docsAndPositions(liveDocs, postings);
		docID = postings.advance(skipDocID);
		if (docID != 0x7fffffff) goto _L3; else goto _L2
_L3:
		int nextDocID;
		if (docID < skipDocID)
			throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": advance(docID=").append(skipDocID).append(") returned docID=").append(docID).toString());
		int freq = postings.freq();
		if (freq <= 0)
			throw new RuntimeException((new StringBuilder()).append("termFreq ").append(freq).append(" is out of bounds").toString());
		int lastPosition = -1;
		int lastOffset = 0;
		for (int posUpto = 0; posUpto < freq; posUpto++)
		{
			int pos = postings.nextPosition();
			if (pos < 0)
				throw new RuntimeException((new StringBuilder()).append("position ").append(pos).append(" is out of bounds").toString());
			if (pos < lastPosition)
				throw new RuntimeException((new StringBuilder()).append("position ").append(pos).append(" is < lastPosition ").append(lastPosition).toString());
			lastPosition = pos;
			if (!hasOffsets)
				continue;
			int startOffset = postings.startOffset();
			int endOffset = postings.endOffset();
			if (!isVectors)
			{
				if (startOffset < 0)
					throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(docID).append(": pos ").append(pos).append(": startOffset ").append(startOffset).append(" is out of bounds").toString());
				if (startOffset < lastOffset)
					throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(docID).append(": pos ").append(pos).append(": startOffset ").append(startOffset).append(" < lastStartOffset ").append(lastOffset).toString());
				if (endOffset < 0)
					throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(docID).append(": pos ").append(pos).append(": endOffset ").append(endOffset).append(" is out of bounds").toString());
				if (endOffset < startOffset)
					throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": doc ").append(docID).append(": pos ").append(pos).append(": endOffset ").append(endOffset).append(" < startOffset ").append(startOffset).toString());
			}
			lastOffset = startOffset;
		}

		nextDocID = postings.nextDoc();
		if (nextDocID != 0x7fffffff) goto _L4; else goto _L2
_L4:
		if (nextDocID <= docID)
			throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": advance(docID=").append(skipDocID).append("), then .next() returned docID=").append(nextDocID).append(" vs prev docID=").append(docID).toString());
		idx++;
		  goto _L5
		idx = 0;
_L9:
		if (idx >= 7) goto _L2; else goto _L6
_L6:
		skipDocID = (int)(((long)(idx + 1) * (long)maxDoc) / 8L);
		docs = termsEnum.docs(liveDocs, docs, 0);
		docID = docs.advance(skipDocID);
		if (docID != 0x7fffffff) goto _L7; else goto _L2
_L7:
		int nextDocID;
		if (docID < skipDocID)
			throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": advance(docID=").append(skipDocID).append(") returned docID=").append(docID).toString());
		nextDocID = docs.nextDoc();
		if (nextDocID != 0x7fffffff) goto _L8; else goto _L2
_L8:
		if (nextDocID <= docID)
			throw new RuntimeException((new StringBuilder()).append("term ").append(term).append(": advance(docID=").append(skipDocID).append("), then .next() returned docID=").append(nextDocID).append(" vs prev docID=").append(docID).toString());
		idx++;
		  goto _L9
		Terms fieldTerms = fields.terms(field);
		if (fieldTerms != null)
		{
			if (fieldTerms instanceof org.apache.lucene.codecs.BlockTreeTermsReader.FieldReader)
			{
				org.apache.lucene.codecs.BlockTreeTermsReader.Stats stats = ((org.apache.lucene.codecs.BlockTreeTermsReader.FieldReader)fieldTerms).computeStats();
				if (!$assertionsDisabled && stats == null)
					throw new AssertionError();
				if (status.blockTreeStats == null)
					status.blockTreeStats = new HashMap();
				status.blockTreeStats.put(field, stats);
			}
			if (sumTotalTermFreq != 0L)
			{
				long v = fields.terms(field).getSumTotalTermFreq();
				if (v != -1L && sumTotalTermFreq != v)
					throw new RuntimeException((new StringBuilder()).append("sumTotalTermFreq for field ").append(field).append("=").append(v).append(" != recomputed sumTotalTermFreq=").append(sumTotalTermFreq).toString());
			}
			if (sumDocFreq != 0L)
			{
				long v = fields.terms(field).getSumDocFreq();
				if (v != -1L && sumDocFreq != v)
					throw new RuntimeException((new StringBuilder()).append("sumDocFreq for field ").append(field).append("=").append(v).append(" != recomputed sumDocFreq=").append(sumDocFreq).toString());
			}
			if (fieldTerms != null)
			{
				int v = fieldTerms.getDocCount();
				if (v != -1 && visitedDocs.cardinality() != v)
					throw new RuntimeException((new StringBuilder()).append("docCount for field ").append(field).append("=").append(v).append(" != recomputed docCount=").append(visitedDocs.cardinality()).toString());
			}
			if (lastTerm != null)
			{
				if (termsEnum.seekCeil(lastTerm) != TermsEnum.SeekStatus.FOUND)
					throw new RuntimeException((new StringBuilder()).append("seek to last term ").append(lastTerm).append(" failed").toString());
				int expectedDocFreq = termsEnum.docFreq();
				DocsEnum d = termsEnum.docs(null, null, 0);
				int docFreq;
				for (docFreq = 0; d.nextDoc() != 0x7fffffff; docFreq++);
				if (docFreq != expectedDocFreq)
					throw new RuntimeException((new StringBuilder()).append("docFreq for last term ").append(lastTerm).append("=").append(expectedDocFreq).append(" != recomputed docFreq=").append(docFreq).toString());
			}
			long termCount = -1L;
			if (status.termCount - termCountStart > 0L)
			{
				termCount = fields.terms(field).size();
				if (termCount != -1L && termCount != status.termCount - termCountStart)
					throw new RuntimeException((new StringBuilder()).append("termCount mismatch ").append(termCount).append(" vs ").append(status.termCount - termCountStart).toString());
			}
			if (hasOrd && status.termCount - termCountStart > 0L)
			{
				int seekCount = (int)Math.min(10000L, termCount);
				if (seekCount > 0)
				{
					BytesRef seekTerms[] = new BytesRef[seekCount];
					for (int i = seekCount - 1; i >= 0; i--)
					{
						long ord = (long)i * (termCount / (long)seekCount);
						termsEnum.seekExact(ord);
						seekTerms[i] = BytesRef.deepCopyOf(termsEnum.term());
					}

					long totDocCount = 0L;
					for (int i = seekCount - 1; i >= 0; i--)
					{
						if (termsEnum.seekCeil(seekTerms[i]) != TermsEnum.SeekStatus.FOUND)
							throw new RuntimeException((new StringBuilder()).append("seek to existing term ").append(seekTerms[i]).append(" failed").toString());
						docs = termsEnum.docs(liveDocs, docs, 0);
						if (docs == null)
							throw new RuntimeException((new StringBuilder()).append("null DocsEnum from to existing term ").append(seekTerms[i]).toString());
						while (docs.nextDoc() != 0x7fffffff) 
							totDocCount++;
					}

					long totDocCountNoDeletes = 0L;
					long totDocFreq = 0L;
					for (int i = 0; i < seekCount; i++)
					{
						if (!termsEnum.seekExact(seekTerms[i], true))
							throw new RuntimeException((new StringBuilder()).append("seek to existing term ").append(seekTerms[i]).append(" failed").toString());
						totDocFreq += termsEnum.docFreq();
						docs = termsEnum.docs(null, docs, 0);
						if (docs == null)
							throw new RuntimeException((new StringBuilder()).append("null DocsEnum from to existing term ").append(seekTerms[i]).toString());
						while (docs.nextDoc() != 0x7fffffff) 
							totDocCountNoDeletes++;
					}

					if (totDocCount > totDocCountNoDeletes)
						throw new RuntimeException((new StringBuilder()).append("more postings with deletes=").append(totDocCount).append(" than without=").append(totDocCountNoDeletes).toString());
					if (totDocCountNoDeletes != totDocFreq)
						throw new RuntimeException((new StringBuilder()).append("docfreqs=").append(totDocFreq).append(" != recomputed docfreqs=").append(totDocCountNoDeletes).toString());
				}
			}
		}
		if (true) goto _L11; else goto _L10
_L10:
		int fieldCount = fields.size();
		if (fieldCount != -1)
		{
			if (fieldCount < 0)
				throw new RuntimeException((new StringBuilder()).append("invalid fieldCount: ").append(fieldCount).toString());
			if (fieldCount != computedFieldCount)
				throw new RuntimeException((new StringBuilder()).append("fieldCount mismatch ").append(fieldCount).append(" vs recomputed field count ").append(computedFieldCount).toString());
		}
		long uniqueTermCountAllFields = fields.getUniqueTermCount();
		if (uniqueTermCountAllFields == -1L)
			throw new RuntimeException("invalid termCount: -1");
		if (status.termCount != uniqueTermCountAllFields)
			throw new RuntimeException((new StringBuilder()).append("termCount mismatch ").append(uniqueTermCountAllFields).append(" vs ").append(status.termCount).toString());
		if (doPrint)
			msg((new StringBuilder()).append("OK [").append(status.termCount).append(" terms; ").append(status.totFreq).append(" terms/docs pairs; ").append(status.totPos).append(" tokens]").toString());
		if (verbose && status.blockTreeStats != null && infoStream != null && status.termCount > 0L)
		{
			java.util.Map.Entry ent;
			for (Iterator i$ = status.blockTreeStats.entrySet().iterator(); i$.hasNext(); infoStream.println((new StringBuilder()).append("      ").append(((org.apache.lucene.codecs.BlockTreeTermsReader.Stats)ent.getValue()).toString().replace("\n", "\n      ")).toString()))
			{
				ent = (java.util.Map.Entry)i$.next();
				infoStream.println((new StringBuilder()).append("      field \"").append((String)ent.getKey()).append("\":").toString());
			}

		}
		return status;
	}

	private Status.TermIndexStatus testPostings(FieldInfos fieldInfos, SegmentReader reader)
	{
		int maxDoc = reader.maxDoc();
		Bits liveDocs = reader.getLiveDocs();
		Status.TermIndexStatus status;
		try
		{
			if (infoStream != null)
				infoStream.print("    test: terms, freq, prox...");
			Fields fields = reader.fields();
			status = checkFields(fields, liveDocs, maxDoc, fieldInfos, true, false);
			if (liveDocs != null)
			{
				if (infoStream != null)
					infoStream.print("    test (ignoring deletes): terms, freq, prox...");
				checkFields(fields, null, maxDoc, fieldInfos, true, false);
			}
		}
		catch (Throwable e)
		{
			msg((new StringBuilder()).append("ERROR: ").append(e).toString());
			status = new Status.TermIndexStatus();
			status.error = e;
			if (infoStream != null)
				e.printStackTrace(infoStream);
		}
		return status;
	}

	private Status.StoredFieldStatus testStoredFields(SegmentInfoPerCommit info, SegmentReader reader, NumberFormat format)
	{
		Status.StoredFieldStatus status = new Status.StoredFieldStatus();
		try
		{
			if (infoStream != null)
				infoStream.print("    test: stored fields.......");
			Bits liveDocs = reader.getLiveDocs();
			for (int j = 0; j < info.info.getDocCount(); j++)
			{
				Document doc = reader.document(j);
				if (liveDocs == null || liveDocs.get(j))
				{
					status.docCount++;
					status.totFields += doc.getFields().size();
				}
			}

			if (status.docCount != reader.numDocs())
				throw new RuntimeException((new StringBuilder()).append("docCount=").append(status.docCount).append(" but saw ").append(status.docCount).append(" undeleted docs").toString());
			msg((new StringBuilder()).append("OK [").append(status.totFields).append(" total field count; avg ").append(format.format((float)status.totFields / (float)status.docCount)).append(" fields per doc]").toString());
		}
		catch (Throwable e)
		{
			msg((new StringBuilder()).append("ERROR [").append(String.valueOf(e.getMessage())).append("]").toString());
			status.error = e;
			if (infoStream != null)
				e.printStackTrace(infoStream);
		}
		return status;
	}

	private void checkDocValues(DocValues docValues, String fieldName, DocValues.Type expectedType, int expectedDocs)
		throws IOException
	{
		if (docValues == null)
			throw new RuntimeException((new StringBuilder()).append("field: ").append(fieldName).append(" omits docvalues but should have them!").toString());
		DocValues.Type type = docValues.getType();
		if (type != expectedType)
			throw new RuntimeException((new StringBuilder()).append("field: ").append(fieldName).append(" has type: ").append(type).append(" but fieldInfos says:").append(expectedType).toString());
		DocValues.Source values = docValues.getDirectSource();
		int size = docValues.getValueSize();
		for (int i = 0; i < expectedDocs; i++)
		{
			static class 1
			{

				static final int $SwitchMap$org$apache$lucene$index$DocValues$Type[];

				static 
				{
					$SwitchMap$org$apache$lucene$index$DocValues$Type = new int[DocValues.Type.values().length];
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_SORTED.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_VAR_SORTED.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_DEREF.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_FIXED_STRAIGHT.ordinal()] = 4;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_VAR_DEREF.ordinal()] = 5;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.BYTES_VAR_STRAIGHT.ordinal()] = 6;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FLOAT_32.ordinal()] = 7;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FLOAT_64.ordinal()] = 8;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.VAR_INTS.ordinal()] = 9;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_16.ordinal()] = 10;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_32.ordinal()] = 11;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_64.ordinal()] = 12;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$DocValues$Type[DocValues.Type.FIXED_INTS_8.ordinal()] = 13;
					}
					catch (NoSuchFieldError ex) { }
				}
			}

			switch (1..SwitchMap.org.apache.lucene.index.DocValues.Type[type.ordinal()])
			{
			case 1: // '\001'
			case 2: // '\002'
			case 3: // '\003'
			case 4: // '\004'
			case 5: // '\005'
			case 6: // '\006'
				BytesRef bytes = new BytesRef();
				values.getBytes(i, bytes);
				if (size != -1 && size != bytes.length)
					throw new RuntimeException((new StringBuilder()).append("field: ").append(fieldName).append(" returned wrongly sized bytes, was: ").append(bytes.length).append(" should be: ").append(size).toString());
				break;

			case 7: // '\007'
				if (!$assertionsDisabled && size != 4)
					throw new AssertionError();
				values.getFloat(i);
				break;

			case 8: // '\b'
				if (!$assertionsDisabled && size != 8)
					throw new AssertionError();
				values.getFloat(i);
				break;

			case 9: // '\t'
				if (!$assertionsDisabled && size != -1)
					throw new AssertionError();
				values.getInt(i);
				break;

			case 10: // '\n'
				if (!$assertionsDisabled && size != 2)
					throw new AssertionError();
				values.getInt(i);
				break;

			case 11: // '\013'
				if (!$assertionsDisabled && size != 4)
					throw new AssertionError();
				values.getInt(i);
				break;

			case 12: // '\f'
				if (!$assertionsDisabled && size != 8)
					throw new AssertionError();
				values.getInt(i);
				break;

			case 13: // '\r'
				if (!$assertionsDisabled && size != 1)
					throw new AssertionError();
				values.getInt(i);
				break;

			default:
				throw new IllegalArgumentException((new StringBuilder()).append("Field: ").append(fieldName).append(" - no such DocValues type: ").append(type).toString());
			}
		}

		if (type == DocValues.Type.BYTES_FIXED_SORTED || type == DocValues.Type.BYTES_VAR_SORTED)
		{
			DocValues.SortedSource sortedValues = values.asSortedSource();
			Comparator comparator = sortedValues.getComparator();
			int lastOrd = -1;
			BytesRef lastBytes = new BytesRef();
			for (int i = 0; i < expectedDocs; i++)
			{
				int ord = sortedValues.ord(i);
				if (ord < 0 || ord > expectedDocs)
					throw new RuntimeException((new StringBuilder()).append("field: ").append(fieldName).append(" ord is out of bounds: ").append(ord).toString());
				BytesRef bytes = new BytesRef();
				sortedValues.getByOrd(ord, bytes);
				if (lastOrd != -1)
				{
					int ordComp = Integer.signum((new Integer(ord)).compareTo(new Integer(lastOrd)));
					int bytesComp = Integer.signum(comparator.compare(bytes, lastBytes));
					if (ordComp != bytesComp)
						throw new RuntimeException((new StringBuilder()).append("field: ").append(fieldName).append(" ord comparison is wrong: ").append(ordComp).append(" comparator claims: ").append(bytesComp).toString());
				}
				lastOrd = ord;
				lastBytes = bytes;
			}

		}
	}

	private Status.DocValuesStatus testDocValues(SegmentInfoPerCommit info, FieldInfos fieldInfos, SegmentReader reader)
	{
		Status.DocValuesStatus status;
label0:
		{
			status = new Status.DocValuesStatus();
			try
			{
label1:
				{
					if (infoStream != null)
						infoStream.print("    test: DocValues........");
					FieldInfo fieldInfo;
label2:
					do
					{
						DocValues docValues;
						for (Iterator i$ = fieldInfos.iterator(); i$.hasNext(); checkDocValues(docValues, fieldInfo.name, fieldInfo.getDocValuesType(), reader.maxDoc()))
						{
							fieldInfo = (FieldInfo)i$.next();
							if (!fieldInfo.hasDocValues())
								continue label2;
							status.totalValueFields++;
							docValues = reader.docValues(fieldInfo.name);
						}

						break label1;
					} while (reader.docValues(fieldInfo.name) == null);
					throw new RuntimeException((new StringBuilder()).append("field: ").append(fieldInfo.name).append(" has docvalues but should omit them!").toString());
				}
				msg((new StringBuilder()).append("OK [").append(status.docCount).append(" total doc Count; Num DocValues Fields ").append(status.totalValueFields).toString());
				break label0;
			}
			catch (Throwable e)
			{
				msg((new StringBuilder()).append("ERROR [").append(String.valueOf(e.getMessage())).append("]").toString());
				status.error = e;
				if (infoStream != null)
					e.printStackTrace(infoStream);
			}
		}
		return status;
	}

	private Status.TermVectorStatus testTermVectors(FieldInfos fieldInfos, SegmentInfoPerCommit info, SegmentReader reader, NumberFormat format)
	{
		Status.TermVectorStatus status = new Status.TermVectorStatus();
		Bits onlyDocIsDeleted = new FixedBitSet(1);
		try
		{
			if (infoStream != null)
				infoStream.print("    test: term vectors........");
			DocsEnum docs = null;
			DocsAndPositionsEnum postings = null;
			DocsEnum postingsDocs = null;
			DocsAndPositionsEnum postingsPostings = null;
			Bits liveDocs = reader.getLiveDocs();
			Fields postingsFields;
			if (crossCheckTermVectors)
				postingsFields = reader.fields();
			else
				postingsFields = null;
			TermsEnum termsEnum = null;
			TermsEnum postingsTermsEnum = null;
label0:
			for (int j = 0; j < info.info.getDocCount(); j++)
			{
				Fields tfv = reader.getTermVectors(j);
				if (tfv == null)
					continue;
				checkFields(tfv, null, 1, fieldInfos, false, true);
				checkFields(tfv, onlyDocIsDeleted, 1, fieldInfos, false, true);
				boolean doStats = liveDocs == null || liveDocs.get(j);
				if (doStats)
					status.docCount++;
				Iterator i$ = tfv.iterator();
				do
				{
					String field;
					FieldInfo fieldInfo;
					do
					{
						if (!i$.hasNext())
							continue label0;
						field = (String)i$.next();
						if (doStats)
							status.totVectors++;
						fieldInfo = fieldInfos.fieldInfo(field);
						if (!fieldInfo.hasVectors())
							throw new RuntimeException((new StringBuilder()).append("docID=").append(j).append(" has term vectors for field=").append(field).append(" but FieldInfo has storeTermVector=false").toString());
					} while (!crossCheckTermVectors);
					Terms terms = tfv.terms(field);
					termsEnum = terms.iterator(termsEnum);
					boolean postingsHasFreq = fieldInfo.getIndexOptions().compareTo(FieldInfo.IndexOptions.DOCS_AND_FREQS) >= 0;
					boolean postingsHasPayload = fieldInfo.hasPayloads();
					boolean vectorsHasPayload = terms.hasPayloads();
					Terms postingsTerms = postingsFields.terms(field);
					if (postingsTerms == null)
						throw new RuntimeException((new StringBuilder()).append("vector field=").append(field).append(" does not exist in postings; doc=").append(j).toString());
					postingsTermsEnum = postingsTerms.iterator(postingsTermsEnum);
					boolean hasProx = terms.hasOffsets() || terms.hasPositions();
					BytesRef term = null;
					while ((term = termsEnum.next()) != null) 
					{
						if (hasProx)
						{
							postings = termsEnum.docsAndPositions(null, postings);
							if (!$assertionsDisabled && postings == null)
								throw new AssertionError();
							docs = null;
						} else
						{
							docs = termsEnum.docs(null, docs);
							if (!$assertionsDisabled && docs == null)
								throw new AssertionError();
							postings = null;
						}
						DocsEnum docs2;
						if (hasProx)
						{
							if (!$assertionsDisabled && postings == null)
								throw new AssertionError();
							docs2 = postings;
						} else
						{
							if (!$assertionsDisabled && docs == null)
								throw new AssertionError();
							docs2 = docs;
						}
						if (!postingsTermsEnum.seekExact(term, true))
							throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(" does not exist in postings; doc=").append(j).toString());
						postingsPostings = postingsTermsEnum.docsAndPositions(null, postingsPostings);
						if (postingsPostings == null)
						{
							postingsDocs = postingsTermsEnum.docs(null, postingsDocs);
							if (postingsDocs == null)
								throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(" does not exist in postings; doc=").append(j).toString());
						}
						DocsEnum postingsDocs2;
						if (postingsPostings != null)
							postingsDocs2 = postingsPostings;
						else
							postingsDocs2 = postingsDocs;
						int advanceDoc = postingsDocs2.advance(j);
						if (advanceDoc != j)
							throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(": doc=").append(j).append(" was not found in postings (got: ").append(advanceDoc).append(")").toString());
						int doc = docs2.nextDoc();
						if (doc != 0)
							throw new RuntimeException((new StringBuilder()).append("vector for doc ").append(j).append(" didn't return docID=0: got docID=").append(doc).toString());
						if (postingsHasFreq)
						{
							int tf = docs2.freq();
							if (postingsHasFreq && postingsDocs2.freq() != tf)
								throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(" doc=").append(j).append(": freq=").append(tf).append(" differs from postings freq=").append(postingsDocs2.freq()).toString());
							if (hasProx)
							{
								int i = 0;
								while (i < tf) 
								{
									int pos = postings.nextPosition();
									if (postingsPostings != null)
									{
										int postingsPos = postingsPostings.nextPosition();
										if (terms.hasPositions() && pos != postingsPos)
											throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(" doc=").append(j).append(": pos=").append(pos).append(" differs from postings pos=").append(postingsPos).toString());
									}
									int startOffset = postings.startOffset();
									int endOffset = postings.endOffset();
									if (postingsPostings != null)
									{
										int postingsStartOffset = postingsPostings.startOffset();
										int postingsEndOffset = postingsPostings.endOffset();
										if (startOffset != -1 && postingsStartOffset != -1 && startOffset != postingsStartOffset)
											throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(" doc=").append(j).append(": startOffset=").append(startOffset).append(" differs from postings startOffset=").append(postingsStartOffset).toString());
										if (endOffset != -1 && postingsEndOffset != -1 && endOffset != postingsEndOffset)
											throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(" doc=").append(j).append(": endOffset=").append(endOffset).append(" differs from postings endOffset=").append(postingsEndOffset).toString());
									}
									BytesRef payload = postings.getPayload();
									if (payload != null && !$assertionsDisabled && !vectorsHasPayload)
										throw new AssertionError();
									if (postingsHasPayload && vectorsHasPayload)
									{
										if (!$assertionsDisabled && postingsPostings == null)
											throw new AssertionError();
										if (payload == null)
										{
											if (postingsPostings.getPayload() != null)
												throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(" doc=").append(j).append(" has no payload but postings does: ").append(postingsPostings.getPayload()).toString());
										} else
										{
											if (postingsPostings.getPayload() == null)
												throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(" doc=").append(j).append(" has payload=").append(payload).append(" but postings does not.").toString());
											BytesRef postingsPayload = postingsPostings.getPayload();
											if (!payload.equals(postingsPayload))
												throw new RuntimeException((new StringBuilder()).append("vector term=").append(term).append(" field=").append(field).append(" doc=").append(j).append(" has payload=").append(payload).append(" but differs from postings payload=").append(postingsPayload).toString());
										}
									}
									i++;
								}
							}
						}
					}
				} while (true);
			}

			float vectorAvg = status.docCount != 0 ? (float)status.totVectors / (float)status.docCount : 0.0F;
			msg((new StringBuilder()).append("OK [").append(status.totVectors).append(" total vector count; avg ").append(format.format(vectorAvg)).append(" term/freq vector fields per doc]").toString());
		}
		catch (Throwable e)
		{
			msg((new StringBuilder()).append("ERROR [").append(String.valueOf(e.getMessage())).append("]").toString());
			status.error = e;
			if (infoStream != null)
				e.printStackTrace(infoStream);
		}
		return status;
	}

	public void fixIndex(Status result, Codec codec)
		throws IOException
	{
		if (result.partial)
		{
			throw new IllegalArgumentException("can only fix an index that was fully checked (this status checked a subset of segments)");
		} else
		{
			result.newSegments.changed();
			result.newSegments.commit(result.dir);
			return;
		}
	}

	private static boolean testAsserts()
	{
		assertsOn = true;
		return true;
	}

	private static boolean assertsOn()
	{
		if (!$assertionsDisabled && !testAsserts())
			throw new AssertionError();
		else
			return assertsOn;
	}

	public static void main(String args[])
		throws IOException, InterruptedException
	{
		boolean doFix = false;
		boolean doCrossCheckTermVectors = false;
		Codec codec = Codec.getDefault();
		boolean verbose = false;
		List onlySegments = new ArrayList();
		String indexPath = null;
		String dirImpl = null;
		for (int i = 0; i < args.length; i++)
		{
			String arg = args[i];
			if ("-fix".equals(arg))
			{
				doFix = true;
				continue;
			}
			if ("-crossCheckTermVectors".equals(arg))
			{
				doCrossCheckTermVectors = true;
				continue;
			}
			if ("-codec".equals(arg))
			{
				if (i == args.length - 1)
				{
					System.out.println("ERROR: missing name for -codec option");
					System.exit(1);
				}
				i++;
				codec = Codec.forName(args[i]);
				continue;
			}
			if (arg.equals("-verbose"))
			{
				verbose = true;
				continue;
			}
			if (arg.equals("-segment"))
			{
				if (i == args.length - 1)
				{
					System.out.println("ERROR: missing name for -segment option");
					System.exit(1);
				}
				i++;
				onlySegments.add(args[i]);
				continue;
			}
			if ("-dir-impl".equals(arg))
			{
				if (i == args.length - 1)
				{
					System.out.println("ERROR: missing value for -dir-impl option");
					System.exit(1);
				}
				i++;
				dirImpl = args[i];
				continue;
			}
			if (indexPath != null)
			{
				System.out.println((new StringBuilder()).append("ERROR: unexpected extra argument '").append(args[i]).append("'").toString());
				System.exit(1);
			}
			indexPath = args[i];
		}

		if (indexPath == null)
		{
			System.out.println("\nERROR: index path not specified");
			System.out.println((new StringBuilder()).append("\nUsage: java org.apache.lucene.index.CheckIndex pathToIndex [-fix] [-crossCheckTermVectors] [-segment X] [-segment Y] [-dir-impl X]\n\n  -fix: actually write a new segments_N file, removing any problematic segments\n  -crossCheckTermVectors: verifies that term vectors match postings; THIS IS VERY SLOW!\n  -codec X: when fixing, codec to write the new segments_N file with\n  -verbose: print additional details\n  -segment X: only check the specified segments.  This can be specified multiple\n              times, to check more than one segment, eg '-segment _2 -segment _a'.\n              You can't use this with the -fix option\n  -dir-impl X: use a specific ").append(org/apache/lucene/store/FSDirectory.getSimpleName()).append(" implementation. ").append("If no package is specified the ").append(org/apache/lucene/store/FSDirectory.getPackage().getName()).append(" package will be used.\n").append("\n").append("**WARNING**: -fix should only be used on an emergency basis as it will cause\n").append("documents (perhaps many) to be permanently removed from the index.  Always make\n").append("a backup copy of your index before running this!  Do not run this tool on an index\n").append("that is actively being written to.  You have been warned!\n").append("\n").append("Run without -fix, this tool will open the index, report version information\n").append("and report any exceptions it hits and what action it would take if -fix were\n").append("specified.  With -fix, this tool will remove any segments that have issues and\n").append("write a new segments_N file.  This means all documents contained in the affected\n").append("segments will be removed.\n").append("\n").append("This tool exits with exit code 1 if the index cannot be opened or has any\n").append("corruption, else 0.\n").toString());
			System.exit(1);
		}
		if (!assertsOn())
			System.out.println("\nNOTE: testing will be more thorough if you run java with '-ea:org.apache.lucene...', so assertions are enabled");
		if (onlySegments.size() == 0)
			onlySegments = null;
		else
		if (doFix)
		{
			System.out.println("ERROR: cannot specify both -fix and -segment");
			System.exit(1);
		}
		System.out.println((new StringBuilder()).append("\nOpening index @ ").append(indexPath).append("\n").toString());
		Directory dir = null;
		try
		{
			if (dirImpl == null)
				dir = FSDirectory.open(new File(indexPath));
			else
				dir = CommandLineUtil.newFSDirectory(dirImpl, new File(indexPath));
		}
		catch (Throwable t)
		{
			System.out.println((new StringBuilder()).append("ERROR: could not open directory \"").append(indexPath).append("\"; exiting").toString());
			t.printStackTrace(System.out);
			System.exit(1);
		}
		CheckIndex checker = new CheckIndex(dir);
		checker.setCrossCheckTermVectors(doCrossCheckTermVectors);
		checker.setInfoStream(System.out, verbose);
		Status result = checker.checkIndex(onlySegments);
		if (result.missingSegments)
			System.exit(1);
		if (!result.clean)
			if (!doFix)
			{
				System.out.println((new StringBuilder()).append("WARNING: would write new segments file, and ").append(result.totLoseDocCount).append(" documents would be lost, if -fix were specified\n").toString());
			} else
			{
				System.out.println((new StringBuilder()).append("WARNING: ").append(result.totLoseDocCount).append(" documents will be lost\n").toString());
				System.out.println((new StringBuilder()).append("NOTE: will write new segments file in 5 seconds; this will remove ").append(result.totLoseDocCount).append(" docs from the index. THIS IS YOUR LAST CHANCE TO CTRL+C!").toString());
				for (int s = 0; s < 5; s++)
				{
					Thread.sleep(1000L);
					System.out.println((new StringBuilder()).append("  ").append(5 - s).append("...").toString());
				}

				System.out.println("Writing...");
				checker.fixIndex(result, codec);
				System.out.println("OK");
				System.out.println((new StringBuilder()).append("Wrote new segments file \"").append(result.newSegments.getSegmentsFileName()).append("\"").toString());
			}
		System.out.println("");
		int exitCode;
		if (result.clean)
			exitCode = 0;
		else
			exitCode = 1;
		System.exit(exitCode);
	}

}
