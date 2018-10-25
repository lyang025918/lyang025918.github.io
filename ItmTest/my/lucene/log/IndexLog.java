// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexLog.java

package my.lucene.log;

import java.io.*;
import java.util.*;
import mylib.file.*;

class IndexLog
{
	class CodecHeader
	{

		public int codecMagic;
		public String codecName;
		public int version;
		final IndexLog this$0;

		public final String toString()
		{
			return (new StringBuilder()).append("codecHeader: codecMagic=").append(codecMagic).append(", codecName=").append(codecName).append(", version=").append(version).toString();
		}

		CodecHeader()
		{
			this.this$0 = IndexLog.this;
			super();
			codecMagic = -1;
			codecName = "";
			version = -1;
		}
	}

	class LogFile extends FSIndexOutput
	{

		final IndexLog this$0;

		public void writeTxt(String str)
			throws IOException
		{
			byte bytes[] = str.getBytes("UTF-8");
			writeBytes(bytes, 0, bytes.length);
		}

		public LogFile(String path)
			throws IOException
		{
			this.this$0 = IndexLog.this;
			super(path);
		}
	}

	class IndexFile extends FSIndexInput
	{

		final IndexLog this$0;

		CodecHeader readCodecHeader()
			throws IOException
		{
			CodecHeader codecHeader = new CodecHeader();
			codecHeader.codecMagic = readInt();
			codecHeader.codecName = readString();
			codecHeader.version = readInt();
			return codecHeader;
		}

		public IndexFile(String path)
			throws IOException
		{
			this.this$0 = IndexLog.this;
			super(path);
			long len = length();
			System.out.printf("file=[%s], length=[%d]\n", new Object[] {
				path, Long.valueOf(len)
			});
		}
	}


	private String idx_dir_;
	private static final char split_ = 47;

	IndexLog()
	{
	}

	public static void main(String args[])
		throws IOException
	{
		String root_dir = "";
		root_dir = "e:\\study\\source\\java\\lucene\\index\\10w";
		IndexLog inst = new IndexLog();
		inst.log_all(root_dir);
	}

	public void log_all(String idx_dir)
		throws IOException
	{
		idx_dir_ = NormalizePath.doNorm(idx_dir, '/');
		File file = new File(idx_dir_);
		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++)
		{
			String str = files[i].getName();
			if (str.endsWith(".fnm"))
			{
				fnm(files[i].getPath());
				continue;
			}
			if (str.endsWith(".tii") || str.endsWith(".tis") || str.endsWith("segments.gen") || str.indexOf("segments_") == 0 && !str.endsWith(".log"))
				continue;
			if (str.endsWith(".si"))
			{
				si(files[i].getPath());
				continue;
			}
			if (str.endsWith(".cfs") || str.endsWith(".cfe"))
				continue;
			if (str.endsWith(".fdx"))
			{
				fdx(files[i].getPath());
				continue;
			}
			if (str.endsWith(".fdt"))
			{
				fdt(files[i].getPath());
				continue;
			}
			if (!str.endsWith(".frq"))
				if (!str.endsWith(".prx"));
		}

	}

	void segments(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		int format = idx.readInt();
		long generation1 = idx.readLong();
		long generation2 = idx.readLong();
		buffer.append((new StringBuilder()).append("seg format=").append(format).append("\r\ngeneration1=").append(generation1).append("\r\ngeneration2=").append(generation2).append("\r\n").toString());
		LogFile log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		log.writeTxt(buffer.toString());
		log.close();
	}

	void segments_N(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		CodecHeader codecHeader = idx.readCodecHeader();
		long version = idx.readLong();
		int counter = idx.readInt();
		int numSegments = idx.readInt();
		buffer.append((new StringBuilder()).append(codecHeader.toString()).append("\r\n").append("version=").append(version).append(", counter=").append(counter).append("\r\n").toString());
		buffer.append((new StringBuilder()).append("numSegments=").append(numSegments).append("\r\n").toString());
		for (int seg = 0; seg < numSegments; seg++)
		{
			String segName = idx.readString();
			String codecName = idx.readString();
			long delGen = idx.readLong();
			int delCount = idx.readInt();
			Map userData = idx.readStringStringMap();
			buffer.append((new StringBuilder()).append("seg ").append(seg).append(", segName=").append(segName).append(", codecName=").append(codecName).append(", delGen=").append(delGen).append(", delCount=").append(delCount).append("\r\n").toString());
		}

		long checksumThen = idx.readLong();
		LogFile log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		log.writeTxt(buffer.toString());
		log.close();
	}

	void si(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		CodecHeader codecHeader = idx.readCodecHeader();
		String version = idx.readString();
		int docCount = idx.readInt();
		byte isCompoundFile = idx.readByte();
		Map diagnostics = idx.readStringStringMap();
		Map attributes = idx.readStringStringMap();
		Set files = idx.readStringSet();
		buffer.append((new StringBuilder()).append(codecHeader.toString()).append("\r\n").toString());
		buffer.append((new StringBuilder()).append("version=").append(version).append(", isCompoundFile=").append(isCompoundFile).append("\r\n").toString());
		buffer.append((new StringBuilder()).append("docCount=").append(docCount).append("\r\n").toString());
		Iterator iterator = files.iterator();
		for (int i = 0; iterator.hasNext(); i++)
		{
			String file = (String)iterator.next();
			buffer.append((new StringBuilder()).append("file ").append(i).append(", ").append(file).append("\r\n").toString());
		}

		LogFile log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		log.writeTxt(buffer.toString());
		log.close();
	}

	void cfs(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		CodecHeader codecHeader = idx.readCodecHeader();
		buffer.append((new StringBuilder()).append(codecHeader.toString()).append("\r\n").toString());
		LogFile log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		log.writeTxt(buffer.toString());
		log.close();
	}

	void cfe(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		CodecHeader codecHeader = idx.readCodecHeader();
		int fileCount = idx.readVInt();
		buffer.append((new StringBuilder()).append(codecHeader.toString()).append("\r\n").toString());
		buffer.append((new StringBuilder()).append("fileCount=").append(fileCount).append("\r\n").toString());
		for (int i = 0; i < fileCount; i++)
		{
			String id = idx.readString();
			long offset = idx.readLong();
			long length = idx.readLong();
			buffer.append((new StringBuilder()).append("file id=").append(id).append(", offset=").append(offset).append(", length=").append(length).append("\r\n").toString());
		}

		LogFile log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		log.writeTxt(buffer.toString());
		log.close();
	}

	void fnm(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		CodecHeader codecHeader = idx.readCodecHeader();
		int fieldCount = idx.readVInt();
		buffer.append((new StringBuilder()).append(codecHeader.toString()).append("\r\n").toString());
		buffer.append((new StringBuilder()).append("fieldCount=").append(fieldCount).append("\r\n").toString());
		for (int i = 0; i < fieldCount; i++)
		{
			String name = idx.readString();
			int fieldNumber = idx.readVInt();
			byte bits = idx.readByte();
			byte docValuesType = idx.readByte();
			Map attrs = idx.readStringStringMap();
			buffer.append((new StringBuilder()).append("field ").append(i).append(", name=").append(name).append(", fieldNumber=").append(fieldNumber).append(", bits=").append(bits).append(", docValuesType=").append(docValuesType).append("\r\n").toString());
		}

		LogFile log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		log.writeTxt(buffer.toString());
		log.close();
	}

	void fdx(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		long bos = idx.getFilePointer();
		CodecHeader codecHeader = idx.readCodecHeader();
		long eos = idx.getFilePointer();
		long docPointerLength = idx.length() - (eos - bos);
		long doc_cnt = docPointerLength / 8L;
		buffer.append((new StringBuilder()).append(codecHeader.toString()).append("\r\n").toString());
		buffer.append((new StringBuilder()).append("fdx中共有doc数目=").append(doc_cnt).append("\r\n").toString());
		for (int i = 0; (long)i < doc_cnt; i++)
		{
			long fdtPointer = idx.readLong();
			buffer.append((new StringBuilder()).append("doc ").append(i).append(", fdtPointer=").append(fdtPointer).append("\r\n").toString());
		}

		LogFile log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		log.writeTxt(buffer.toString());
		log.close();
	}

	void fdt(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		CodecHeader codecHeader = idx.readCodecHeader();
		buffer.append((new StringBuilder()).append(codecHeader.toString()).append("\r\n").toString());
		LogFile log;
		try
		{
			do
			{
				long nowPos = idx.getFilePointer();
				int fieldCount = idx.readVInt();
				buffer.append((new StringBuilder()).append("filePointer=").append(nowPos).append(", fieldCount=").append(fieldCount).append("\r\n").toString());
				int i = 0;
				while (i < fieldCount) 
				{
					buffer.append("\t");
					int fieldId = idx.readVInt();
					byte flag = idx.readByte();
					int numeric = flag & 0x38;
					if (numeric != 0)
					{
						switch (numeric)
						{
						case 8: // '\b'
							int intValue = idx.readInt();
							buffer.append((new StringBuilder()).append("field ").append(i).append(", fieldId=").append(fieldId).append(", flag=").append(flag).append(", value=").append(intValue).append("\r\n").toString());
							break;

						case 16: // '\020'
							long longValue = idx.readLong();
							buffer.append((new StringBuilder()).append("field ").append(i).append(", fieldId=").append(fieldId).append(", flag=").append(flag).append(", value=").append(longValue).append("\r\n").toString());
							break;

						case 24: // '\030'
							float floatValue = Float.intBitsToFloat(idx.readInt());
							buffer.append((new StringBuilder()).append("field ").append(i).append(", fieldId=").append(fieldId).append(", flag=").append(flag).append(", value=").append(floatValue).append("\r\n").toString());
							break;

						case 32: // ' '
							double doubleValue = Double.longBitsToDouble(idx.readLong());
							buffer.append((new StringBuilder()).append("field ").append(i).append(", fieldId=").append(fieldId).append(", flag=").append(flag).append(", value=").append(doubleValue).append("\r\n").toString());
							break;
						}
					} else
					{
						String stringValue = idx.readString();
						buffer.append((new StringBuilder()).append("field ").append(i).append(", fieldId=").append(fieldId).append(", flag=").append(flag).append(", value=").append(stringValue).append("\r\n").toString());
					}
					i++;
				}
			} while (true);
		}
		catch (EOFException e)
		{
			log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		}
		log.writeTxt(buffer.toString());
		log.close();
	}

	void tii_tis(String path, boolean isIndex)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		long term_cnt = idx.readInt();
		buffer.append((new StringBuilder()).append("tii TermCount(4byte)=").append(term_cnt).append("词项个数。\r\n").toString());
		byte lastTermBytes[] = null;
		long lastFreqPointer = 0L;
		long lastProxPointer = 0L;
		for (long i = 0L; i < term_cnt; i++)
		{
			int prefixLength = idx.readVInt();
			int deltaLength = idx.readVInt();
			byte suffixBytes[] = new byte[deltaLength];
			idx.readBytes(suffixBytes, 0, deltaLength);
			String term = null;
			if (prefixLength == 0)
			{
				term = new String(suffixBytes, "UTF-8");
				lastTermBytes = suffixBytes;
			} else
			{
				byte termBytes[] = new byte[prefixLength + deltaLength];
				System.arraycopy(lastTermBytes, 0, termBytes, 0, prefixLength);
				System.arraycopy(suffixBytes, 0, termBytes, prefixLength, deltaLength);
				term = new String(termBytes, "UTF-8");
				lastTermBytes = termBytes;
			}
			int fieldId = idx.readVInt();
			int docFreq = idx.readVInt();
			long freqPointer = idx.readVLong() + lastFreqPointer;
			long proxPointer = idx.readVLong() + lastProxPointer;
			lastFreqPointer = freqPointer;
			lastProxPointer = proxPointer;
			buffer.append((new StringBuilder()).append("term=").append(term).append(", field id=").append(fieldId).append(", doc freq=").append(docFreq).append(", freqPointer=").append(freqPointer).append(", proxPointer=").append(proxPointer).toString());
			if (isIndex)
			{
				long tisPointer = idx.readVLong();
				buffer.append((new StringBuilder()).append(", tisPointer=").append(tisPointer).toString());
			}
			buffer.append("\r\n");
		}

		LogFile log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		log.writeTxt(buffer.toString());
		log.close();
	}

	void frq(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		int lastDoc = 0;
		int freq = 0;
		LogFile log;
		try
		{
			do
			{
				long pointer = idx.getFilePointer();
				int docCode = idx.readVInt();
				int doc = lastDoc + (docCode >>> 1);
				if ((docCode & 1) != 0)
					freq = 1;
				else
					freq = idx.readVInt();
				buffer.append((new StringBuilder()).append("filePointer=").append(pointer).append(", docId=").append(doc).append(", freq=").append(freq).append("\r\n").toString());
				doc = lastDoc;
			} while (true);
		}
		catch (EOFException e)
		{
			log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		}
		log.writeTxt(buffer.toString());
		log.close();
	}

	void prx(String path)
		throws IOException
	{
		IndexFile idx = new IndexFile(path);
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append((new StringBuilder()).append("file=").append(path).append(", file_len=").append(idx.length()).append("\r\n").toString());
		idx.seek(0L);
		int lastPos = 0;
		LogFile log;
		try
		{
			do
			{
				long pointer = idx.getFilePointer();
				int pos = lastPos + idx.readVInt();
				buffer.append((new StringBuilder()).append("filePointer=").append(pointer).append(", pos=").append(pos).append("\r\n").toString());
			} while (true);
		}
		catch (EOFException e)
		{
			log = new LogFile((new StringBuilder()).append(path).append(".log").toString());
		}
		log.writeTxt(buffer.toString());
		log.close();
	}
}
