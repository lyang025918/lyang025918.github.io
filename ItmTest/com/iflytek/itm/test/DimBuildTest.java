// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   DimBuildTest.java

package com.iflytek.itm.test;

import com.iflytek.itm.api.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DimBuildTest
{
	class DimLine
	{

		String mypath;
		String content;
		String contact_id;
		String call_phone;
		String kf_id;
		String accept_phone;
		String begin_date;
		String file_uri;
		String call_type;
		String call_time;
		String contact_time;
		String caller_reason;
		String user_brand;
		String user_level;
		String satisfaction;
		String region;
		final DimBuildTest this$0;

		private int parse()
		{
			int ret = 0;
			String arrs[] = content.split("\\|");
			contact_id = arrs[0].trim();
			call_phone = arrs[1].trim();
			kf_id = arrs[2].trim();
			accept_phone = arrs[3].trim();
			begin_date = String.valueOf(toLongDate(arrs[4].trim()));
			file_uri = arrs[5].trim();
			call_type = arrs[6].trim();
			call_time = arrs[7].trim();
			contact_time = arrs[8].trim();
			caller_reason = arrs[9].trim();
			user_brand = arrs[10].trim();
			user_level = arrs[11].trim();
			satisfaction = arrs[12].trim();
			region = arrs.length > 13 ? arrs[13].trim() : "";
			return ret;
		}

		private long toLongDate(String str)
		{
			long dt = 0L;
			try
			{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				dt = sdf.parse(str).getTime();
			}
			catch (ParseException e)
			{
				System.out.println((new StringBuilder()).append("toDate | error: parse failed, str=").append(str).toString());
				dt = 0L;
			}
			return dt;
		}

		public DimLine(File file, int i, String str)
		{
			this.this$0 = DimBuildTest.this;
			super();
			mypath = (new StringBuilder()).append(file.getPath()).append(".").append(i).toString();
			content = str;
			parse();
		}
	}

	class ITMBuilderImpl
		implements ITMBuilder
	{

		private File files[];
		private int now;
		private int iline;
		private BufferedReader bufferedReader;
		private String strLine;
		public int idoc;
		final DimBuildTest this$0;

		public void event(String id, int evt, String msg)
		{
			System.out.println((new StringBuilder()).append("Event called: id=").append(id).append(", event = ").append(evt).append(", msg=").append(msg).toString());
		}

		public com.iflytek.itm.api.ITMBuilder.ITMDocument read()
		{
			com.iflytek.itm.api.ITMBuilder.ITMDocument document = null;
			if (files == null || now == files.length)
				return null;
			DimLine dimLine = readDimLine(files[now]);
			if (dimLine == null)
			{
				return null;
			} else
			{
				System.out.println((new StringBuilder()).append("read | adding...").append(dimLine.mypath).toString());
				document = new com.iflytek.itm.api.ITMBuilder.ITMDocument();
				String id = String.valueOf(idoc);
				com.iflytek.itm.api.ITMBuilder.ITMField idField = new com.iflytek.itm.api.ITMBuilder.ITMField("id", "int", id, null, true);
				document.add(idField);
				String mypath = dimLine.mypath;
				com.iflytek.itm.api.ITMBuilder.ITMField mypathField = new com.iflytek.itm.api.ITMBuilder.ITMField("mypath", "string", mypath, null, false);
				document.add(mypathField);
				String content = dimLine.content;
				com.iflytek.itm.api.ITMBuilder.ITMField contentField = new com.iflytek.itm.api.ITMBuilder.ITMField("content", "string", content, "org.wltea.analyzer.lucene.IKAnalyzer", false);
				document.add(contentField);
				String contact_id = dimLine.contact_id;
				com.iflytek.itm.api.ITMBuilder.ITMField contact_idField = new com.iflytek.itm.api.ITMBuilder.ITMField("contact_id", "string", contact_id, null, false);
				document.add(contact_idField);
				String call_phone = dimLine.call_phone;
				com.iflytek.itm.api.ITMBuilder.ITMField call_phoneField = new com.iflytek.itm.api.ITMBuilder.ITMField("call_phone", "string", call_phone, null, false);
				document.add(call_phoneField);
				String kf_id = dimLine.kf_id;
				com.iflytek.itm.api.ITMBuilder.ITMField kf_idField = new com.iflytek.itm.api.ITMBuilder.ITMField("kf_id", "string", kf_id, null, false);
				document.add(kf_idField);
				String accept_phone = dimLine.accept_phone;
				com.iflytek.itm.api.ITMBuilder.ITMField accept_phoneField = new com.iflytek.itm.api.ITMBuilder.ITMField("accept_phone", "string", accept_phone, null, false);
				document.add(accept_phoneField);
				String begin_date = dimLine.begin_date;
				com.iflytek.itm.api.ITMBuilder.ITMField begin_dateField = new com.iflytek.itm.api.ITMBuilder.ITMField("begin_date", "long", begin_date, null, false);
				document.add(begin_dateField);
				String file_uri = dimLine.file_uri;
				com.iflytek.itm.api.ITMBuilder.ITMField file_uriField = new com.iflytek.itm.api.ITMBuilder.ITMField("file_uri", "string", file_uri, null, false);
				document.add(file_uriField);
				String call_type = dimLine.call_type;
				com.iflytek.itm.api.ITMBuilder.ITMField call_typeField = new com.iflytek.itm.api.ITMBuilder.ITMField("call_type", "string", call_type, null, false);
				document.add(call_typeField);
				String call_time = dimLine.call_time;
				com.iflytek.itm.api.ITMBuilder.ITMField call_timeField = new com.iflytek.itm.api.ITMBuilder.ITMField("call_time", "int", call_time, null, false);
				document.add(call_timeField);
				String contact_time = dimLine.contact_time;
				com.iflytek.itm.api.ITMBuilder.ITMField contact_timeField = new com.iflytek.itm.api.ITMBuilder.ITMField("contact_time", "int", contact_time, null, false);
				document.add(contact_timeField);
				String caller_reason = dimLine.caller_reason;
				com.iflytek.itm.api.ITMBuilder.ITMField caller_reasonField = new com.iflytek.itm.api.ITMBuilder.ITMField("caller_reason", "string", caller_reason, null, false);
				document.add(caller_reasonField);
				String user_brand = dimLine.user_brand;
				com.iflytek.itm.api.ITMBuilder.ITMField user_brandField = new com.iflytek.itm.api.ITMBuilder.ITMField("user_brand", "string", user_brand, null, false);
				document.add(user_brandField);
				String user_level = dimLine.user_level;
				com.iflytek.itm.api.ITMBuilder.ITMField user_levelField = new com.iflytek.itm.api.ITMBuilder.ITMField("user_level", "string", user_level, null, false);
				document.add(user_levelField);
				String satisfaction = dimLine.satisfaction;
				com.iflytek.itm.api.ITMBuilder.ITMField satisfactionField = new com.iflytek.itm.api.ITMBuilder.ITMField("satisfaction", "string", satisfaction, null, false);
				document.add(satisfactionField);
				String region = dimLine.region;
				com.iflytek.itm.api.ITMBuilder.ITMField regionField = new com.iflytek.itm.api.ITMBuilder.ITMField("region", "string", region, null, false);
				document.add(regionField);
				idoc++;
				return document;
			}
		}

		private DimLine readDimLine(File file)
		{
			DimLine dimLine = null;
			if (iline == 0)
			{
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
				bufferedReader.readLine();
				iline++;
			}
			strLine = bufferedReader.readLine();
			if (strLine != null)
				break MISSING_BLOCK_LABEL_123;
			bufferedReader.close();
			now++;
			iline = 0;
			if (now == files.length)
				return null;
			return readDimLine(files[now]);
			try
			{
				dimLine = new DimLine(files[now], iline, strLine);
				iline++;
			}
			catch (Exception e)
			{
				System.out.printf("readDimLine | Error: file=%s, line=%d, msg=%s\n", new Object[] {
					files[now].getPath(), Integer.valueOf(iline), e.getMessage()
				});
			}
			return dimLine;
		}

		public ITMBuilderImpl(String docsPath)
		{
			this.this$0 = DimBuildTest.this;
			super();
			files = null;
			now = 0;
			iline = 0;
			bufferedReader = null;
			strLine = null;
			idoc = 0;
			File docDir = new File(docsPath);
			if (!docDir.exists() || !docDir.canRead())
				System.out.println((new StringBuilder()).append("Document directory '").append(docDir.getAbsolutePath()).append("' does not exist or is not readable, please check the path").toString());
			else
			if (docDir.isDirectory())
				files = docDir.listFiles();
		}
	}


	public DimBuildTest()
	{
	}

	public static void main(String args[])
	{
		System.out.println("≤‚ ‘π¶ƒ‹");
		DimBuildTest inst = new DimBuildTest();
		inst.doBuild();
	}

	public void doBuild()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\test_home\\index\\itm";
		String docsPath = "e:\\test_home\\resource\\dim";
		String params = "sub_index_dir=dim";
		ITM inst = ITMFactory.create();
		ITMBuilderImpl builder = new ITMBuilderImpl(docsPath);
		int ret = inst.build(indexPath, params, builder);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		System.out.println((new StringBuilder()).append("read | total doc=").append(builder.idoc).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}

	public void testSelf()
	{
		String docsPath = "e:\\test_home\\resource\\dim";
		ITMBuilderImpl builder = new ITMBuilderImpl(docsPath);
		for (com.iflytek.itm.api.ITMBuilder.ITMDocument itmDocument = null; (itmDocument = builder.read()) != null;);
	}
}
