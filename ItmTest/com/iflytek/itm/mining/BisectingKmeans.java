// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BisectingKmeans.java

package com.iflytek.itm.mining;

import java.io.*;

// Referenced classes of package com.iflytek.itm.mining:
//			Cluster, KMeans, HotUtil

public class BisectingKmeans
{

	private KMeans kmeans;
	private int nCluster;
	private double patterns[][];
	public Cluster clusters[];
	int numPatterns;
	int sizeVector;
	int numClusters;

	public BisectingKmeans(int pattern_num, int dim_num, int cluster_num)
	{
		kmeans = null;
		patterns = (double[][])null;
		clusters = null;
		numPatterns = pattern_num;
		sizeVector = dim_num;
		numClusters = cluster_num;
		patterns = new double[numPatterns][sizeVector];
		clusters = new Cluster[cluster_num];
		for (int i = 0; i < clusters.length; i++)
			clusters[i] = new Cluster(pattern_num, dim_num);

		kmeans = null;
	}

	public void SetPattern(double data[], int pos)
	{
		HotUtil.arrayCopy(patterns[pos], data);
	}

	public void initClusters()
	{
		nCluster = 1;
		clusters[0].numMember = numPatterns;
		for (int i = 0; i < numPatterns; i++)
		{
			clusters[0].members[i] = i;
			for (int j = 0; j < sizeVector; j++)
				clusters[0].center[j] += patterns[i][j];

		}

		for (int i = 0; i < sizeVector; i++)
			clusters[0].center[i] /= numPatterns;

	}

	public void runClust()
	{
		do
		{
			if (nCluster >= numClusters)
				break;
			int spl = Find2Bisect();
			kmeans = new KMeans(clusters[spl].numMember, sizeVector, 2);
			int oldmb[] = new int[clusters[spl].numMember];
			for (int i = 0; i < clusters[spl].numMember; i++)
			{
				kmeans.SetPattern(patterns[clusters[spl].members[i]], i);
				oldmb[i] = clusters[spl].members[i];
			}

			kmeans.InitClusters();
			kmeans.runClust();
			if (((double)kmeans.clusters[0].numMember < (double)clusters[spl].numMember * 0.10000000000000001D || (double)kmeans.clusters[1].numMember < (double)clusters[spl].numMember * 0.10000000000000001D) && nCluster > 2)
			{
				numClusters = nCluster;
				break;
			}
			HotUtil.arrayCopy(clusters[spl].center, kmeans.clusters[0].center);
			clusters[spl].numMember = kmeans.clusters[0].numMember;
			for (int j = 0; j < clusters[spl].numMember; j++)
				clusters[spl].members[j] = oldmb[kmeans.clusters[0].members[j]];

			HotUtil.arrayCopy(clusters[nCluster].center, kmeans.clusters[1].center);
			clusters[nCluster].numMember = kmeans.clusters[1].numMember;
			for (int j = 0; j < clusters[nCluster].numMember; j++)
				clusters[nCluster].members[j] = oldmb[kmeans.clusters[1].members[j]];

			nCluster++;
			if (nCluster >= 3)
			{
				kmeans = null;
				kmeans = new KMeans(numPatterns, sizeVector, nCluster);
				for (int i = 0; i < numPatterns; i++)
					kmeans.SetPattern(patterns[i], i);

				HotUtil.clusterArrayCopy(kmeans.clusters, clusters, nCluster);
				kmeans.runClust();
				HotUtil.clusterArrayCopy(clusters, kmeans.clusters, kmeans.numClusters);
			}
		} while (true);
		int i;
		for (i = 0; i < clusters.length && clusters[i].numMember != 0; i++);
		kmeans = null;
		nCluster = i;
		numClusters = i;
	}

	private int Find2Bisect()
	{
		double maxnum = 0.0D;
		int Bisect = 0;
		for (int i = 0; i < nCluster; i++)
			if ((double)clusters[i].numMember > maxnum)
			{
				maxnum = clusters[i].numMember;
				Bisect = i;
			}

		return Bisect;
	}

	public void log()
	{
		System.out.printf("cluster end num = %d\n", new Object[] {
			Integer.valueOf(nCluster)
		});
		for (int i = 0; i < numClusters; i++)
		{
			int numDot = clusters[i].numMember;
			System.out.printf("cluster=%d, numMembers=%d: ", new Object[] {
				Integer.valueOf(i), Integer.valueOf(numDot)
			});
			for (int idot = 0; idot < numDot; idot++)
				System.out.printf("%d,", new Object[] {
					Integer.valueOf(clusters[i].members[idot])
				});

			System.out.printf("\n\n", new Object[0]);
		}

	}

	public static void main(String args[])
		throws Exception
	{
		BisectingKmeans biCluster = new BisectingKmeans(500, 587, 10);
		biCluster.loadPatterns("r:\\work\\itm\\热词分析资源\\Cluster\\debug\\Pattern.dat");
		biCluster.initClusters();
		biCluster.runClust();
		biCluster.log();
	}

	private int loadPatterns(String path)
		throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)), "gbk"));
		String line = null;
		int iCount = 0;
		do
		{
			line = br.readLine();
			if (null != line)
			{
				if (iCount == 0)
					numPatterns = Integer.valueOf(line).intValue();
				else
				if (iCount == 1)
					sizeVector = Integer.valueOf(line).intValue();
				else
				if (iCount == 2)
				{
					numClusters = Integer.valueOf(line).intValue();
				} else
				{
					String words[] = line.split(" ");
					double oneLine[] = new double[sizeVector];
					for (int i = 0; i < words.length; i++)
					{
						String tmp = words[i].trim();
						if (!tmp.isEmpty())
							oneLine[i] = Double.valueOf(tmp).doubleValue();
					}

					patterns[iCount - 3] = oneLine;
				}
				iCount++;
			} else
			{
				return 0;
			}
		} while (true);
	}
}
