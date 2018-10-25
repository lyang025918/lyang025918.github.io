// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMApiMiningTest.java

package com.iflytek.itm.test;

import com.iflytek.itm.api.ITM;
import com.iflytek.itm.api.ITMFactory;
import java.io.PrintStream;

public class ITMApiMiningTest
{

	public ITMApiMiningTest()
	{
	}

	public static void main(String args[])
	{
		System.out.println("测试mining接口");
		ITMApiMiningTest inst = new ITMApiMiningTest();
		for (int i = 0; i < 1; i++)
			inst.doMining();

	}

	public void doMining()
	{
		doAssist();
	}

	public void doWordAssociation()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\test_home\\index\\itm";
		String subDir = "10w";
		ITM inst = ITMFactory.create();
		StringBuffer buffer = new StringBuffer();
		StringBuffer tmpIdList = new StringBuffer(1024);
		for (int i = 0; i < 100; i++)
			tmpIdList.append((new StringBuilder()).append(subDir).append(":").append(i).append(";").toString());

		int ret = inst.mining(indexPath, "word_association", "sub_index_dir_list=2013-06 \nword_association_word=伍球 \nsample_rate=500 \nword_association_level_top_n=2: 20;3 \nmining_field=content", buffer);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		System.out.println((new StringBuilder()).append("mining result=").append(buffer.toString()).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}

	public void doRule()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\test_home\\index\\itm";
		String subDir = "1w";
		ITM inst = ITMFactory.create();
		StringBuffer buffer = new StringBuffer();
		StringBuffer tmpIdList = new StringBuffer(1024);
		for (int i = 0; i < 100; i++)
			tmpIdList.append((new StringBuilder()).append(subDir).append(":").append(i).append(";").toString());

		int ret = inst.mining(indexPath, "rule", "sub_index_dir_list=test \nid_field=id \nrule=1:(感谢#来电)&(来电+(sil>6)) \nmining_field=content ", buffer);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		System.out.println((new StringBuilder()).append("mining result=").append(buffer.toString()).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}

	public void doHotView()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\test_home\\index-10w\\itm";
		String subDir = "10w";
		ITM inst = ITMFactory.create();
		StringBuffer buffer = new StringBuffer();
		StringBuffer tmpIdList = new StringBuffer(1024);
		for (int i = 0xf4241; i < 0xf4435; i++)
			tmpIdList.append((new StringBuilder()).append(subDir).append(":").append(i).append(";").toString());

		int ret = inst.mining(indexPath, "hot_view", "sub_index_dir_list=10w \nid_list=10w:1;10w:2;10w:100;10w:34 \nid_field=ids \nsample_rate=500 \nhot_view_mode=1 \nhot_view_feature_words_top_n=50 \nhot_view_word_top_n=15 \nmining_field=content", buffer);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		System.out.println((new StringBuilder()).append("mining result=").append(buffer.toString()).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}

	public void doTrade()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\test_home\\index\\itm";
		ITM inst = ITMFactory.create();
		StringBuffer buffer = new StringBuffer();
		String params = "sub_index_dir_list=2.01.01_1 \ntrade_top_n=10 \nsample_rate=500 \ntrade_result_type=tf-idf \nmining_field=content";
		int ret = inst.mining(indexPath, "trade", params, buffer);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		System.out.println((new StringBuilder()).append("mining result=").append(buffer.toString()).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}

	public void doAssist()
	{
		long start = System.currentTimeMillis();
		String indexPath = "e:\\test_home\\index\\itm";
		ITM inst = ITMFactory.create();
		StringBuffer buffer = new StringBuffer();
		String params = "sub_index_dir_list=assist \nassist_top_n=10 \nassist_text=好像有一个叫商旅一百多五十八的套餐吧 \n";
		int ret = inst.mining(indexPath, "assist", params, buffer);
		if (ret != 0)
			System.out.println((new StringBuilder()).append("Error: errcode=").append(ret).toString());
		System.out.println((new StringBuilder()).append("mining result=").append(buffer.toString()).toString());
		long end = System.currentTimeMillis();
		System.out.println((new StringBuilder()).append(end - start).append(" total milliseconds").toString());
	}
}
