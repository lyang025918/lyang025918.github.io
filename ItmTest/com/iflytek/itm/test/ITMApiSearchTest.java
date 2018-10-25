// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMApiSearchTest.java

package com.iflytek.itm.test;

import com.iflytek.itm.api.*;
import java.io.PrintStream;
import java.util.List;

public class ITMApiSearchTest
{

	public ITMApiSearchTest()
	{
	}

	public static void main(String args[])
	{
		System.out.println("≤‚ ‘π¶ƒ‹");
		ITMApiSearchTest inst = new ITMApiSearchTest();
		for (int i = 0; i < 1; i++)
			inst.doSearch();

	}

	public void doSearch()
	{
		long start = System.currentTimeMillis();
		String indexPath = "c:/ias10";
		ITM inst = ITMFactory.create();
		ITMSearchContext searchContext = new ITMSearchContext();
		int ret = 0;
		ret = inst.search(indexPath, "+silences:[0 TO *]", "sub_index_dir_list=test \n page_size=10 \n current_page=1", searchContext);
		if (ret != 0)
		{
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
			return;
		}
		int totalHits = searchContext.itmSearchResult.getTotalHits();
		System.out.printf("total hit docs=[%d]\n", new Object[] {
			Integer.valueOf(totalHits)
		});
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
		com.iflytek.itm.api.ITMBuilder.ITMDocument docs[] = searchContext.itmSearchResult.docs();
		for (int i = 0; i < docs.length; i++)
		{
			System.out.printf("i=%d, ", new Object[] {
				Integer.valueOf(i)
			});
			com.iflytek.itm.api.ITMBuilder.ITMDocument doc = docs[i];
			List fields = doc.getFields();
			for (int j = 0; j < fields.size(); j++)
			{
				com.iflytek.itm.api.ITMBuilder.ITMField field = (com.iflytek.itm.api.ITMBuilder.ITMField)fields.get(j);
				String value = doc.get(field.name).value;
				System.out.printf("%s=%s, ", new Object[] {
					field.name, value
				});
			}

			System.out.printf("\n", new Object[0]);
		}

		searchContext.itmSearchResult.close();
		long end2 = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end2 - start).append(" total milliseconds").toString());
	}

	public void doGroup()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\test_home\\index-1000w\\itm";
		ITM inst = ITMFactory.create();
		ITMSearchContext searchContext = new ITMSearchContext();
		int ret = 0;
		ret = inst.search(indexPath, "", "sub_index_dir_list=1000w \n group_field=id \n page_size=10 \n current_page=1", searchContext);
		if (ret != 0)
		{
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
			return;
		}
		int totalHits = searchContext.itmSearchResult.getGroupTotalHits();
		System.out.printf("total hit docs=[%d], total hit groups=[%d]\n", new Object[] {
			Integer.valueOf(searchContext.itmSearchResult.getTotalHits()), Integer.valueOf(totalHits)
		});
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
		com.iflytek.itm.api.ITMSearchContext.ITMGroup groups[] = searchContext.itmSearchResult.groups();
		for (int i = 0; i < groups.length; i++)
		{
			System.out.printf("i=%d, ", new Object[] {
				Integer.valueOf(i)
			});
			com.iflytek.itm.api.ITMSearchContext.ITMGroup group = groups[i];
			System.out.println((new StringBuilder()).append("group value=").append(group.value).append(", group doc cnt=").append(group.docCount).toString());
		}

		searchContext.itmSearchResult.close();
		long end2 = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end2 - start).append(" total milliseconds").toString());
	}

	public void listDocs()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\test_home\\index\\itm";
		ITM inst = ITMFactory.create();
		ITMSearchContext searchContext = new ITMSearchContext();
		int ret = 0;
		ret = inst.search(indexPath, "*:*", "sub_index_dir_list=1w", searchContext);
		if (ret != 0)
		{
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
			return;
		}
		int totalHits = searchContext.itmSearchResult.getTotalHits();
		System.out.printf("total hit docs=[%d]\n", new Object[] {
			Integer.valueOf(totalHits)
		});
		com.iflytek.itm.api.ITMBuilder.ITMDocument docs[] = searchContext.itmSearchResult.docs();
		for (int i = 0; i < docs.length; i++)
		{
			System.out.printf("i=%d, ", new Object[] {
				Integer.valueOf(i)
			});
			com.iflytek.itm.api.ITMBuilder.ITMDocument doc = docs[i];
			List fields = doc.getFields();
			for (int j = 0; j < fields.size(); j++)
			{
				com.iflytek.itm.api.ITMBuilder.ITMField field = (com.iflytek.itm.api.ITMBuilder.ITMField)fields.get(j);
				String value = doc.get(field.name).value;
				System.out.printf("%s=%s, ", new Object[] {
					field.name, value
				});
			}

			System.out.printf("\n", new Object[0]);
		}

		searchContext.itmSearchResult.close();
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}
}
