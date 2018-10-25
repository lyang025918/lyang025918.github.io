// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SegmentInfo.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.codecs.lucene3x.Lucene3xSegmentInfoFormat;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.TrackingDirectoryWrapper;

// Referenced classes of package org.apache.lucene.index:
//			IndexFileNames

public final class SegmentInfo
{

	public static final int NO = -1;
	public static final int YES = 1;
	public final String name;
	private int docCount;
	public final Directory dir;
	private boolean isCompoundFile;
	private volatile long sizeInBytes;
	private Codec codec;
	private Map diagnostics;
	private Map attributes;
	private String version;
	private Set setFiles;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/SegmentInfo.desiredAssertionStatus();

	void setDiagnostics(Map diagnostics)
	{
		this.diagnostics = diagnostics;
	}

	public Map getDiagnostics()
	{
		return diagnostics;
	}

	public SegmentInfo(Directory dir, String version, String name, int docCount, boolean isCompoundFile, Codec codec, Map diagnostics, 
			Map attributes)
	{
		sizeInBytes = -1L;
		if (!$assertionsDisabled && (dir instanceof TrackingDirectoryWrapper))
		{
			throw new AssertionError();
		} else
		{
			this.dir = dir;
			this.version = version;
			this.name = name;
			this.docCount = docCount;
			this.isCompoundFile = isCompoundFile;
			this.codec = codec;
			this.diagnostics = diagnostics;
			this.attributes = attributes;
			return;
		}
	}

	public long sizeInBytes()
		throws IOException
	{
		if (sizeInBytes == -1L)
		{
			long sum = 0L;
			for (Iterator i$ = files().iterator(); i$.hasNext();)
			{
				String fileName = (String)i$.next();
				sum += dir.fileLength(fileName);
			}

			sizeInBytes = sum;
		}
		return sizeInBytes;
	}

	/**
	 * @deprecated Method hasSeparateNorms is deprecated
	 */

	boolean hasSeparateNorms()
	{
		return getAttribute(Lucene3xSegmentInfoFormat.NORMGEN_KEY) != null;
	}

	void setUseCompoundFile(boolean isCompoundFile)
	{
		this.isCompoundFile = isCompoundFile;
	}

	public boolean getUseCompoundFile()
	{
		return isCompoundFile;
	}

	public void setCodec(Codec codec)
	{
		if (!$assertionsDisabled && this.codec != null)
			throw new AssertionError();
		if (codec == null)
		{
			throw new IllegalArgumentException("segmentCodecs must be non-null");
		} else
		{
			this.codec = codec;
			return;
		}
	}

	public Codec getCodec()
	{
		return codec;
	}

	public int getDocCount()
	{
		if (docCount == -1)
			throw new IllegalStateException("docCount isn't set yet");
		else
			return docCount;
	}

	void setDocCount(int docCount)
	{
		if (this.docCount != -1)
		{
			throw new IllegalStateException("docCount was already set");
		} else
		{
			this.docCount = docCount;
			return;
		}
	}

	public Set files()
	{
		if (setFiles == null)
			throw new IllegalStateException("files were not computed yet");
		else
			return Collections.unmodifiableSet(setFiles);
	}

	public String toString()
	{
		return toString(dir, 0);
	}

	public String toString(Directory dir, int delCount)
	{
		StringBuilder s = new StringBuilder();
		s.append(name).append('(').append(version != null ? version : "?").append(')').append(':');
		char cfs = getUseCompoundFile() ? 'c' : 'C';
		s.append(cfs);
		if (this.dir != dir)
			s.append('x');
		s.append(docCount);
		if (delCount != 0)
			s.append('/').append(delCount);
		return s.toString();
	}

	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj instanceof SegmentInfo)
		{
			SegmentInfo other = (SegmentInfo)obj;
			return other.dir == dir && other.name.equals(name);
		} else
		{
			return false;
		}
	}

	public int hashCode()
	{
		return dir.hashCode() + name.hashCode();
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getVersion()
	{
		return version;
	}

	public void setFiles(Set files)
	{
		checkFileNames(files);
		setFiles = files;
		sizeInBytes = -1L;
	}

	public void addFiles(Collection files)
	{
		checkFileNames(files);
		setFiles.addAll(files);
		sizeInBytes = -1L;
	}

	public void addFile(String file)
	{
		checkFileNames(Collections.singleton(file));
		setFiles.add(file);
		sizeInBytes = -1L;
	}

	private void checkFileNames(Collection files)
	{
		Matcher m = IndexFileNames.CODEC_FILE_PATTERN.matcher("");
		for (Iterator i$ = files.iterator(); i$.hasNext();)
		{
			String file = (String)i$.next();
			m.reset(file);
			if (!m.matches())
				throw new IllegalArgumentException((new StringBuilder()).append("invalid codec filename '").append(file).append("', must match: ").append(IndexFileNames.CODEC_FILE_PATTERN.pattern()).toString());
		}

	}

	public String getAttribute(String key)
	{
		if (attributes == null)
			return null;
		else
			return (String)attributes.get(key);
	}

	public String putAttribute(String key, String value)
	{
		if (attributes == null)
			attributes = new HashMap();
		return (String)attributes.put(key, value);
	}

	public Map attributes()
	{
		return attributes;
	}

}
