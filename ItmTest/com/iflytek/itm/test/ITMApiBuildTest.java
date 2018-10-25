// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMApiBuildTest.java

package com.iflytek.itm.test;

import com.iflytek.itm.api.*;
import java.io.File;
import java.io.PrintStream;
import java.util.Random;
import mylib.file.FileInfo;
import mylib.utils.MobilePhone;

public class ITMApiBuildTest
{
	class ITMBuilderImpl
		implements ITMBuilder
	{

		private File files[];
		private int now;
		final ITMApiBuildTest this$0;

		public void event(String id, int evt, String msg)
		{
			System.out.println((new StringBuilder()).append("Event called: id=").append(id).append(", event = ").append(evt).append(", msg=").append(msg).toString());
		}

		public com.iflytek.itm.api.ITMBuilder.ITMDocument read()
		{
			com.iflytek.itm.api.ITMBuilder.ITMDocument document = null;
			if (files == null || now == files.length)
			{
				return null;
			} else
			{
				document = new com.iflytek.itm.api.ITMBuilder.ITMDocument();
				String id = FileInfo.getNameNoExt(files[now]);
				com.iflytek.itm.api.ITMBuilder.ITMField idField = new com.iflytek.itm.api.ITMBuilder.ITMField("id", "int", id, null, true);
				document.add(idField);
				String path = files[now].getPath();
				com.iflytek.itm.api.ITMBuilder.ITMField pathField = new com.iflytek.itm.api.ITMBuilder.ITMField("path", "string", path, null, false);
				document.add(pathField);
				String time = Long.toString(0x1330684 + now);
				com.iflytek.itm.api.ITMBuilder.ITMField timeField = new com.iflytek.itm.api.ITMBuilder.ITMField("time", "long", time, null, false);
				document.add(timeField);
				String content = FileInfo.getContent(files[now], "gbk");
				com.iflytek.itm.api.ITMBuilder.ITMField contentField = new com.iflytek.itm.api.ITMBuilder.ITMField("content", "string", content, "org.wltea.analyzer.lucene.IKAnalyzer", false);
				document.add(contentField);
				String mobilePhone = MobilePhone.randomGenerate();
				com.iflytek.itm.api.ITMBuilder.ITMField phoneField = new com.iflytek.itm.api.ITMBuilder.ITMField("phone", "string", mobilePhone, null, false);
				document.add(phoneField);
				String onebest = "你好这个只是 测试不同的field 用不同的 分词器只是测试哦!";
				com.iflytek.itm.api.ITMBuilder.ITMField onebestField = new com.iflytek.itm.api.ITMBuilder.ITMField("onebest", "string", onebest, "org.apache.lucene.analysis.core.WhitespaceAnalyzer", false);
				document.add(onebestField);
				Random ran = new Random(System.currentTimeMillis());
				String randStr = Integer.toString(ran.nextInt(12));
				com.iflytek.itm.api.ITMBuilder.ITMField randField = new com.iflytek.itm.api.ITMBuilder.ITMField("rand", "string", randStr, null, false);
				document.add(randField);
				int mb = 0x100000;
				System.out.println((new StringBuilder()).append("adding doc...now=").append(now).append(", id=").append(id).append(", path=").append(path).append(", free=").append(Runtime.getRuntime().freeMemory() / (long)mb).append(", ").append("total=").append(Runtime.getRuntime().totalMemory() / (long)mb).append(", ").append("max=").append(Runtime.getRuntime().maxMemory() / (long)mb).toString());
				now++;
				return document;
			}
		}

		public ITMBuilderImpl(String docsPath)
		{
			this.this$0 = ITMApiBuildTest.this;
			super();
			files = null;
			now = 0;
			File docDir = new File(docsPath);
			if (!docDir.exists() || !docDir.canRead())
				System.out.println((new StringBuilder()).append("Document directory '").append(docDir.getAbsolutePath()).append("' does not exist or is not readable, please check the path").toString());
			else
			if (docDir.isDirectory())
				files = docDir.listFiles();
		}
	}

	class ITMUpdateBuilder
		implements ITMBuilder
	{

		private int cnt;
		final ITMApiBuildTest this$0;

		public void event(String id, int evt, String msg)
		{
			System.out.println((new StringBuilder()).append("Event called: id=").append(id).append(", event = ").append(evt).append(", msg=").append(msg).toString());
		}

		public com.iflytek.itm.api.ITMBuilder.ITMDocument read()
		{
			if (cnt == 1)
			{
				return null;
			} else
			{
				com.iflytek.itm.api.ITMBuilder.ITMDocument document = new com.iflytek.itm.api.ITMBuilder.ITMDocument();
				com.iflytek.itm.api.ITMBuilder.ITMField idField = new com.iflytek.itm.api.ITMBuilder.ITMField("id", "int", "12345678", null, true);
				document.add(idField);
				com.iflytek.itm.api.ITMBuilder.ITMField phoneField = new com.iflytek.itm.api.ITMBuilder.ITMField("phone", "string", "13810439507", null, false);
				document.add(phoneField);
				cnt++;
				return document;
			}
		}

		ITMUpdateBuilder()
		{
			this.this$0 = ITMApiBuildTest.this;
			super();
			cnt = 0;
		}
	}


	public ITMApiBuildTest()
	{
	}

	public static void main(String args[])
	{
		System.out.println("测试功能");
		ITMApiBuildTest inst = new ITMApiBuildTest();
		String indexPath = "c:/ias40";
		String docsPath = "e:\\test_home\\resource\\test";
		String params = "sub_index_dir=test \n max_buffered_docs=6000";
		inst.doBuildDoc(indexPath, params);
	}

	public void doBuildDoc(String indexPath, String params)
	{
		long start = System.currentTimeMillis();
		ITM inst = ITMFactory.create();
		com.iflytek.itm.api.ITMBuilder.ITMDocument document = new com.iflytek.itm.api.ITMBuilder.ITMDocument();
		com.iflytek.itm.api.ITMBuilder.ITMField idField = new com.iflytek.itm.api.ITMBuilder.ITMField("text", "string", "你喜欢&什么&女", "org.wltea.analyzer.lucene.IKAnalyzer", false);
		document.add(idField);
		com.iflytek.itm.api.ITMBuilder.ITMField contentField = new com.iflytek.itm.api.ITMBuilder.ITMField("silences", "string", "0000001000|0-2000 0100020000|2000-8000", "com.iflytek.itm.search.sil.SilAnalyzer", false);
		document.add(contentField);
		com.iflytek.itm.api.ITMBuilder.ITMField lsField = new com.iflytek.itm.api.ITMBuilder.ITMField("sil", "int", "5", null, false);
		document.add(lsField);
		int ret = inst.build(indexPath, params, document);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}

	public void doBuild(String indexPath, String docsPath, String params)
	{
		long start = System.currentTimeMillis();
		ITM inst = ITMFactory.create();
		ITMBuilderImpl builder = new ITMBuilderImpl(docsPath);
		int ret = inst.build(indexPath, params, builder);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}

	public void doUpdate()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\test_home\\index\\itm";
		ITM inst = ITMFactory.create();
		ITMUpdateBuilder builder = new ITMUpdateBuilder();
		int ret = inst.build(indexPath, "sub_index_dir=2013-06 \nis_update_document=true \n", builder);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}

	public void doDelete()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\study\\source\\java\\lucene\\index\\itm";
		String docsPath = "e:\\study\\source\\java\\lucene\\data\\my\\";
		ITM inst = ITMFactory.create();
		ITMBuilderImpl builder = new ITMBuilderImpl(docsPath);
		int ret = inst.build(indexPath, "sub_index_dir=20121222\nis_delete_document=true", builder);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}
}
