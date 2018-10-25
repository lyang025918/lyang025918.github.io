// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IndexUpgrader.java

package org.apache.lucene.index;

import java.io.*;
import java.util.Collection;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			IndexWriterConfig, IndexNotFoundException, UpgradeIndexMergePolicy, KeepOnlyLastCommitDeletionPolicy, 
//			IndexWriter, DirectoryReader

public final class IndexUpgrader
{

	private final Directory dir;
	private final IndexWriterConfig iwc;
	private final boolean deletePriorCommits;

	private static void printUsage()
	{
		System.err.println("Upgrades an index so all segments created with a previous Lucene version are rewritten.");
		System.err.println("Usage:");
		System.err.println((new StringBuilder()).append("  java ").append(org/apache/lucene/index/IndexUpgrader.getName()).append(" [-delete-prior-commits] [-verbose] [-dir-impl X] indexDir").toString());
		System.err.println("This tool keeps only the last commit in an index; for this");
		System.err.println("reason, if the incoming index has more than one commit, the tool");
		System.err.println("refuses to run by default. Specify -delete-prior-commits to override");
		System.err.println("this, allowing the tool to delete all but the last commit.");
		System.err.println((new StringBuilder()).append("Specify a ").append(org/apache/lucene/store/FSDirectory.getSimpleName()).append(" implementation through the -dir-impl option to force its use. If no package is specified the ").append(org/apache/lucene/store/FSDirectory.getPackage().getName()).append(" package will be used.").toString());
		System.err.println("WARNING: This tool may reorder document IDs!");
		System.exit(1);
	}

	public static void main(String args[])
		throws IOException
	{
		String path = null;
		boolean deletePriorCommits = false;
		PrintStream out = null;
		String dirImpl = null;
		for (int i = 0; i < args.length; i++)
		{
			String arg = args[i];
			if ("-delete-prior-commits".equals(arg))
			{
				deletePriorCommits = true;
				continue;
			}
			if ("-verbose".equals(arg))
			{
				out = System.out;
				continue;
			}
			if (path == null)
			{
				path = arg;
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
			} else
			{
				printUsage();
			}
		}

		if (path == null)
			printUsage();
		Directory dir = null;
		if (dirImpl == null)
			dir = FSDirectory.open(new File(path));
		else
			dir = CommandLineUtil.newFSDirectory(dirImpl, new File(path));
		(new IndexUpgrader(dir, Version.LUCENE_CURRENT, out, deletePriorCommits)).upgrade();
	}

	public IndexUpgrader(Directory dir, Version matchVersion)
	{
		this(dir, new IndexWriterConfig(matchVersion, null), false);
	}

	public IndexUpgrader(Directory dir, Version matchVersion, PrintStream infoStream, boolean deletePriorCommits)
	{
		this(dir, (new IndexWriterConfig(matchVersion, null)).setInfoStream(infoStream), deletePriorCommits);
	}

	public IndexUpgrader(Directory dir, IndexWriterConfig iwc, boolean deletePriorCommits)
	{
		this.dir = dir;
		this.iwc = iwc;
		this.deletePriorCommits = deletePriorCommits;
	}

	public void upgrade()
		throws IOException
	{
		IndexWriterConfig c;
		IndexWriter w;
		if (!DirectoryReader.indexExists(dir))
			throw new IndexNotFoundException(dir.toString());
		if (!deletePriorCommits)
		{
			Collection commits = DirectoryReader.listCommits(dir);
			if (commits.size() > 1)
				throw new IllegalArgumentException((new StringBuilder()).append("This tool was invoked to not delete prior commit points, but the following commits were found: ").append(commits).toString());
		}
		c = iwc.clone();
		c.setMergePolicy(new UpgradeIndexMergePolicy(c.getMergePolicy()));
		c.setIndexDeletionPolicy(new KeepOnlyLastCommitDeletionPolicy());
		w = new IndexWriter(dir, c);
		InfoStream infoStream = c.getInfoStream();
		if (infoStream.isEnabled("IndexUpgrader"))
			infoStream.message("IndexUpgrader", (new StringBuilder()).append("Upgrading all pre-").append(Constants.LUCENE_MAIN_VERSION).append(" segments of index directory '").append(dir).append("' to version ").append(Constants.LUCENE_MAIN_VERSION).append("...").toString());
		w.forceMerge(1);
		if (infoStream.isEnabled("IndexUpgrader"))
			infoStream.message("IndexUpgrader", (new StringBuilder()).append("All segments upgraded to version ").append(Constants.LUCENE_MAIN_VERSION).toString());
		w.close();
		break MISSING_BLOCK_LABEL_252;
		Exception exception;
		exception;
		w.close();
		throw exception;
	}
}
