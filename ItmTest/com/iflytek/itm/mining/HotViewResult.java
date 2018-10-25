// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   HotViewResult.java

package com.iflytek.itm.mining;

import java.io.*;
import java.util.*;

// Referenced classes of package com.iflytek.itm.mining:
//			WordInfo, HotUtil

public class HotViewResult
{
	static class ClusterInfoComparator
		implements Comparator
	{

		public int compare(Object arg0, Object arg1)
		{
			ClusterInfo relWord1 = (ClusterInfo)arg0;
			ClusterInfo relWord2 = (ClusterInfo)arg1;
			int flag = 0;
			if (relWord1.docNum > relWord2.docNum)
				flag = -1;
			else
			if (relWord1.docNum == relWord2.docNum)
				flag = 0;
			else
				flag = 1;
			return flag;
		}

		ClusterInfoComparator()
		{
		}
	}

	static class ClusterInfo
	{

		int clusterId;
		int docNum;

		ClusterInfo()
		{
		}
	}

	class ListComparator
		implements Comparator
	{

		final HotViewResult this$0;

		public int compare(Object arg0, Object arg1)
		{
			RelWord relWord1 = (RelWord)arg0;
			RelWord relWord2 = (RelWord)arg1;
			int flag = 0;
			if (relWord1.info.score > relWord2.info.score)
				flag = -1;
			else
			if (relWord1.info.score == relWord2.info.score)
				flag = 0;
			else
				flag = 1;
			return flag;
		}

		ListComparator()
		{
			this.this$0 = HotViewResult.this;
			super();
		}
	}

	class RelWord
	{

		String word;
		RelWordInfo info;
		final HotViewResult this$0;

		RelWord()
		{
			this.this$0 = HotViewResult.this;
			super();
			info = new RelWordInfo();
		}
	}

	class RelWordInfo
	{

		int cluster;
		int score;
		final HotViewResult this$0;

		RelWordInfo()
		{
			this.this$0 = HotViewResult.this;
			super();
		}
	}


	private Map m_RelWordsInfoMap;
	private List m_RelWordsList;
	private Map m_ClassRW;

	public HotViewResult()
	{
		m_RelWordsInfoMap = new HashMap();
		m_RelWordsList = new ArrayList();
		m_ClassRW = new HashMap();
	}

	public void fetchResult(int cluster_num, int word_top_n, Map classDocNum, StringBuffer buffer)
	{
		toRelWordsList(cluster_num);
		sort();
		getEveryClassWords(cluster_num);
		showResult(cluster_num, word_top_n, classDocNum, buffer);
	}

	public static void main(String args[])
		throws IOException
	{
		HotViewResult result = new HotViewResult();
		int numClass = 5;
		for (int i = 1; i <= numClass; i++)
		{
			String path = (new StringBuilder()).append("Class").append(i).append("RelWords.txt").toString();
			result.loadRelWordInfo(path, i - 1);
		}

		result.toRelWordsList(numClass);
		result.sort();
		result.getEveryClassWords(numClass);
		StringBuffer buffer = new StringBuffer(1024);
		result.showResult(numClass, 5, null, buffer);
		System.out.println((new StringBuilder()).append("result: \n").append(buffer.toString()).toString());
	}

	public void addRelWordInfo(String word, int score, int c)
	{
		RelWordInfo oneWord = new RelWordInfo();
		oneWord.cluster = c;
		oneWord.score = score;
		if (!m_RelWordsInfoMap.containsKey(word))
			m_RelWordsInfoMap.put(word, oneWord);
		else
		if ((double)((RelWordInfo)m_RelWordsInfoMap.get(word)).score < (double)score)
		{
			((RelWordInfo)m_RelWordsInfoMap.get(word)).cluster = c;
			((RelWordInfo)m_RelWordsInfoMap.get(word)).score = score;
		}
	}

	public int loadRelWordInfo(String path, int c)
		throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "gbk"));
		String line = null;
		int iCount = 0;
		do
		{
			line = br.readLine();
			if (null != line)
			{
				int pos = line.lastIndexOf(" ");
				String word = line.substring(0, pos);
				int score = Integer.valueOf(line.substring(pos + 1).trim()).intValue();
				addRelWordInfo(word, score, c);
				iCount++;
			} else
			{
				return 0;
			}
		} while (true);
	}

	public int toRelWordsList(int cluster_num)
	{
		Set keys = m_RelWordsInfoMap.keySet();
		Iterator it = keys.iterator();
		List toDelSet = new ArrayList();
		while (it.hasNext()) 
		{
			String word = (String)it.next();
			RelWordInfo info = (RelWordInfo)m_RelWordsInfoMap.get(word);
			int pos = word.indexOf(HotUtil.NGRAM_WORD_SPLIT_CHAR);
			String first = word.substring(0, pos);
			String second = word.substring(pos + 1);
			String rword = (new StringBuilder()).append(second).append(HotUtil.NGRAM_WORD_SPLIT_CHAR).append(first).toString();
			if (m_RelWordsInfoMap.containsKey(rword))
			{
				RelWordInfo rInfo = (RelWordInfo)m_RelWordsInfoMap.get(rword);
				if (info.score > rInfo.score)
				{
					toDelSet.add(rword);
					addWordToList(word, info);
				} else
				{
					info.cluster = cluster_num + 1;
					info.score = 0;
				}
			} else
			{
				addWordToList(word, info);
			}
		}
		for (int id = 0; id < toDelSet.size(); id++)
			m_RelWordsInfoMap.remove(toDelSet.get(id));

		return 0;
	}

	private void addWordToList(String word, RelWordInfo info)
	{
		RelWord relWord = new RelWord();
		relWord.word = word;
		relWord.info.cluster = info.cluster;
		relWord.info.score = info.score;
		m_RelWordsList.add(relWord);
	}

	public int sort()
	{
		ListComparator comp = new ListComparator();
		Collections.sort(m_RelWordsList, comp);
		return 0;
	}

	public int getEveryClassWords(int cluster_num)
	{
		int classcount[] = new int[cluster_num];
		int m_ClassWordCount[] = new int[cluster_num];
		for (int i = 0; i < m_RelWordsList.size(); i++)
		{
			RelWord relWord = (RelWord)m_RelWordsList.get(i);
			int cluster = relWord.info.cluster;
			double score = relWord.info.score;
			if (score <= 0.0D || classcount[cluster] >= 12)
				continue;
			classcount[cluster]++;
			m_ClassWordCount[cluster] += (int)score;
			if (m_ClassRW.containsKey(Integer.valueOf(cluster)))
			{
				((List)m_ClassRW.get(Integer.valueOf(cluster))).add(relWord);
			} else
			{
				List tmpList = new ArrayList();
				tmpList.add(relWord);
				m_ClassRW.put(Integer.valueOf(cluster), tmpList);
			}
		}

		return 0;
	}

	public int showResult(int cluster_num, int word_top_n, Map classDocNum, StringBuffer buffer)
	{
		List finalClusters = new ArrayList();
		toClusterInfoList(classDocNum, finalClusters);
		ClusterInfoComparator comp = new ClusterInfoComparator();
		Collections.sort(finalClusters, comp);
		List clusterWordInfos = new ArrayList(word_top_n);
		for (int i = 0; i < finalClusters.size(); i++)
		{
			clusterWordInfos.clear();
			ClusterInfo info = (ClusterInfo)finalClusters.get(i);
			int cluster = info.clusterId;
			do
			{
				List rw = (List)m_ClassRW.get(Integer.valueOf(cluster));
				if (null == rw || rw.isEmpty())
					break;
				String maxword = "";
				int maxcount = 0;
				Map one2two = new HashMap();
				for (int irw = 0; irw < rw.size(); irw++)
				{
					String str = ((RelWord)rw.get(irw)).word;
					int pos = str.indexOf(HotUtil.NGRAM_WORD_SPLIT_CHAR);
					String first = str.substring(0, pos);
					String second = str.substring(pos + 1);
					insert(first, irw, one2two);
					if (((List)one2two.get(first)).size() > maxcount)
					{
						maxcount = ((List)one2two.get(first)).size();
						maxword = first;
					}
					insert(second, irw, one2two);
					if (((List)one2two.get(second)).size() > maxcount)
					{
						maxcount = ((List)one2two.get(second)).size();
						maxword = second;
					}
				}

				if (maxcount > 1)
				{
					int n_oneword = 0;
					for (int j = 0; j < ((List)one2two.get(maxword)).size(); j++)
						n_oneword += ((RelWord)rw.get(((Integer)((List)one2two.get(maxword)).get(j)).intValue())).info.score;

					WordInfo tmpinfo = new WordInfo();
					tmpinfo.word = maxword;
					tmpinfo.score = n_oneword;
					clusterWordInfos.add(tmpinfo);
					if (clusterWordInfos.size() >= word_top_n)
						break;
					int j = 0;
					do
					{
						if (j >= maxcount)
							break;
						int rwIndex = ((Integer)((List)one2two.get(maxword)).get(j)).intValue();
						WordInfo tmpinfo2 = new WordInfo();
						tmpinfo2.word = ((RelWord)rw.get(rwIndex)).word;
						tmpinfo2.score = ((RelWord)rw.get(rwIndex)).info.score;
						clusterWordInfos.add(tmpinfo2);
						if (clusterWordInfos.size() >= word_top_n)
							break;
						j++;
					} while (true);
					for (j = maxcount - 1; j >= 0; j--)
					{
						int rwIndex = ((Integer)((List)one2two.get(maxword)).get(j)).intValue();
						rw.remove(rwIndex);
					}

				} else
				{
					int j = 0;
					do
					{
						if (j >= rw.size())
							break;
						WordInfo tmpinfo2 = new WordInfo();
						tmpinfo2.word = ((RelWord)rw.get(j)).word;
						tmpinfo2.score = ((RelWord)rw.get(j)).info.score;
						clusterWordInfos.add(tmpinfo2);
						if (clusterWordInfos.size() >= word_top_n)
							break;
						j++;
					} while (true);
					rw.clear();
				}
			} while (clusterWordInfos.size() < word_top_n);
			if (clusterWordInfos.size() > 0)
				buffer.append(info.docNum).append(":");
			for (int iw = 0; iw < clusterWordInfos.size(); iw++)
			{
				buffer.append(((WordInfo)clusterWordInfos.get(iw)).word).append("(").append((int)((WordInfo)clusterWordInfos.get(iw)).score).append(")");
				if (iw < clusterWordInfos.size() - 1)
					buffer.append("|");
			}

			if (clusterWordInfos.size() > 0 && i < finalClusters.size() - 1)
				buffer.append(";");
		}

		return 0;
	}

	private void insert(String word, int irw, Map one2two)
	{
		if (one2two.containsKey(word))
		{
			((List)one2two.get(word)).add(Integer.valueOf(irw));
		} else
		{
			List tmpList = new ArrayList();
			tmpList.add(Integer.valueOf(irw));
			one2two.put(word, tmpList);
		}
	}

	void toClusterInfoList(Map classDocNum, List finalClusters)
	{
		Set keys = classDocNum.keySet();
		ClusterInfo info;
		for (Iterator it = keys.iterator(); it.hasNext(); finalClusters.add(info))
		{
			int cid = ((Integer)it.next()).intValue();
			int docNum = ((Integer)classDocNum.get(Integer.valueOf(cid))).intValue();
			info = new ClusterInfo();
			info.clusterId = cid;
			info.docNum = docNum;
		}

	}
}
