// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   KMeans.java

package com.iflytek.itm.mining;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

// Referenced classes of package com.iflytek.itm.mining:
//			Cluster, HotUtil

public class KMeans
{

	private double patterns[][];
	public Cluster clusters[];
	int initAlg;
	int numPatterns;
	int sizeVector;
	int numClusters;

	public KMeans(int pattern_num, int dim_num, int cluster_num)
	{
		patterns = (double[][])null;
		clusters = null;
		numPatterns = pattern_num;
		sizeVector = dim_num;
		numClusters = cluster_num;
		patterns = new double[numPatterns][sizeVector];
		clusters = new Cluster[cluster_num];
		for (int i = 0; i < clusters.length; i++)
			clusters[i] = new Cluster(pattern_num, dim_num);

		initAlg = 2;
	}

	public void clear()
	{
		for (int row = 0; row < patterns.length; row++)
		{
			for (int col = 0; col < patterns[row].length; col++)
				patterns[row][col] = 0.0D;

		}

		for (int i = 0; i < clusters.length; i++)
			clusters[i].clear();

	}

	public void SetInitAlg(int Init)
	{
		initAlg = Init;
	}

	public void SetPattern(double data[], int pos)
	{
		HotUtil.arrayCopy(patterns[pos], data);
	}

	public void InitClusters()
	{
		for (int i = 0; i < clusters.length; i++)
			clusters[i].clear();

		if (initAlg == 1)
			System.out.println("Sorry, 暂时没有集成.");
		else
		if (initAlg == 2)
		{
			int centers[] = new int[numClusters];
			int curCenter = 0;
			List neighbours = new ArrayList();
			for (int i = 0; i < numPatterns; i++)
			{
				List tmplist = new ArrayList();
				for (int j = 0; j < numPatterns; j++)
				{
					double dis = cosine(patterns[j], patterns[i]);
					if (dis < 0.5D)
						tmplist.add(Integer.valueOf(j));
				}

				neighbours.add(tmplist);
			}

			int maxnb = 0;
			int min = 20;
			do
			{
				if (maxnb < min || curCenter >= numClusters)
					if (curCenter <= 1)
					{
						min -= 2;
					} else
					{
						numClusters = curCenter;
						break;
					}
				maxnb = 0;
				for (int ilist = 0; ilist < neighbours.size(); ilist++)
				{
					List values = (List)neighbours.get(ilist);
					if (values.size() > maxnb)
					{
						maxnb = values.size();
						centers[curCenter] = ilist;
					}
				}

				List nei = (List)neighbours.get(centers[curCenter]);
				int size = nei.size();
				int tmp1[] = new int[size];
				for (int inei = 0; inei < size; inei++)
					tmp1[inei] = ((Integer)nei.get(inei)).intValue();

				for (int i = 0; i < size; i++)
					((List)neighbours.get(tmp1[i])).clear();

				curCenter++;
			} while (true);
			for (int i = 0; i < numClusters; i++)
			{
				clusters[i].numMember = 0;
				for (int j = 0; j < sizeVector; j++)
					clusters[i].center[j] = patterns[centers[i]][j];

			}

		}
	}

	public void runClust()
	{
		int pass = 1;
		for (int converged = HotUtil.FALSE; converged == HotUtil.FALSE; converged = calcNewClustCenters())
			distributeSamples();

		double mse = 0.0D;
		for (int c = 0; c < numClusters; c++)
		{
			for (int m = 0; m < clusters[c].numMember; m++)
			{
				for (int p = 0; p < sizeVector; p++)
					mse += Math.pow(patterns[clusters[c].members[m]][p] - clusters[c].center[p], 2D);

			}

		}

	}

	private double cosine(int p, int c)
	{
		double dist = 0.0D;
		double dot = 0.0D;
		double mo1 = 0.0D;
		double mo2 = 0.0D;
		for (int i = 0; i < sizeVector; i++)
		{
			dot += clusters[c].center[i] * patterns[p][i];
			mo1 += clusters[c].center[i] * clusters[c].center[i];
			mo2 += patterns[p][i] * patterns[p][i];
		}

		if (mo1 == 0.0D && mo2 == 0.0D)
			dist = 0.0D;
		else
		if (mo1 * mo2 == 0.0D)
			dist = 1.0D;
		else
			dist = 1.0D - dot / (Math.sqrt(mo1) * Math.sqrt(mo2));
		return dist;
	}

	private double cosine(double point[], double center[])
	{
		double dist = 0.0D;
		double dot = 0.0D;
		double mo1 = 0.0D;
		double mo2 = 0.0D;
		for (int i = 0; i < sizeVector; i++)
		{
			dot += center[i] * point[i];
			mo1 += center[i] * center[i];
			mo2 += point[i] * point[i];
		}

		if (mo1 == 0.0D && mo2 == 0.0D)
			dist = 0.0D;
		else
		if (mo1 * mo2 == 0.0D)
			dist = 1.0D;
		else
			dist = 1.0D - dot / (Math.sqrt(mo1) * Math.sqrt(mo2));
		return dist;
	}

	private int findClosestCluster(int pat)
	{
		double MinDist = 9.9000000000000007E+099D;
		int ClustID = -1;
		for (int i = 0; i < numClusters; i++)
		{
			double d = cosine(pat, i);
			if (d < MinDist)
			{
				MinDist = d;
				ClustID = i;
			}
		}

		if (ClustID < 0)
		{
			System.out.println("Aaargh");
			ClustID = 4;
		}
		return ClustID;
	}

	private void distributeSamples()
	{
		for (int i = 0; i < numClusters; i++)
			clusters[i].numMember = 0;

		for (int pat = 0; pat < numPatterns; pat++)
		{
			int Clustid = findClosestCluster(pat);
			int MemberIndex = clusters[Clustid].numMember;
			clusters[Clustid].members[MemberIndex] = pat;
			clusters[Clustid].numMember++;
		}

	}

	private int calcNewClustCenters()
	{
		double tmp[] = new double[sizeVector];
		int ConvFlag = HotUtil.TRUE;
		double dot = 0.0D;
		double mo1 = 0.0D;
		double mo2 = 0.0D;
		double dist = 0.0D;
		for (int i = 0; i < numClusters; i++)
		{
			for (int j = 0; j < sizeVector; j++)
				tmp[j] = 0.0D;

			for (int j = 0; j < clusters[i].numMember; j++)
			{
				int VectID = clusters[i].members[j];
				for (int k = 0; k < sizeVector; k++)
					tmp[k] += patterns[VectID][k];

			}

			for (int k = 0; k < sizeVector; k++)
			{
				tmp[k] = tmp[k] / (double)clusters[i].numMember;
				dot += tmp[k] * clusters[i].center[k];
				mo1 += tmp[k] * tmp[k];
				mo2 += clusters[i].center[k] * clusters[i].center[k];
			}

			HotUtil.arrayCopy(clusters[i].center, tmp);
			dist += 1.0D - dot / (Math.sqrt(mo1) * Math.sqrt(mo2));
		}

		if (dist > 0.20000000000000001D)
			ConvFlag = HotUtil.FALSE;
		return ConvFlag;
	}
}
