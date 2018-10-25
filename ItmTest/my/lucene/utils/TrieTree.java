// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TrieTree.java

package my.lucene.utils;


public class TrieTree
{
	class Node
	{

		boolean isLeaf;
		String value;
		int childCnt;
		Node childNodes[];
		final TrieTree this$0;

		Node child(char c)
		{
			Node node = null;
			if (childNodes != null)
			{
				int i = 0;
				do
				{
					if (i >= childNodes.length)
						break;
					if (childNodes[i].value.equals((new StringBuilder()).append(value).append(c).toString()))
					{
						node = childNodes[i];
						break;
					}
					i++;
				} while (true);
			}
			return node;
		}

		Node()
		{
			this.this$0 = TrieTree.this;
			super();
			isLeaf = false;
			value = "";
			childCnt = 0;
			childNodes = null;
		}
	}


	private Node root;

	public TrieTree()
	{
		root = new Node();
	}

	public void insert(String str)
	{
		if (find(str))
			return;
		Node nowNode = root;
		for (int i = 0; i < str.length(); i++)
		{
			Node child = nowNode.child(str.charAt(i));
			if (child != null)
			{
				nowNode = child;
				continue;
			}
			if (nowNode.childNodes == null)
			{
				Node newNodes[] = new Node[1];
				nowNode.childNodes = newNodes;
			}
			if (nowNode.childNodes.length == nowNode.childCnt)
			{
				Node newNodes[] = new Node[nowNode.childCnt * 2];
				for (int ic = 0; ic < nowNode.childCnt; ic++)
					newNodes[ic] = nowNode.childNodes[ic];

				nowNode.childNodes = newNodes;
			}
			Node newNode = new Node();
			newNode.isLeaf = true;
			newNode.value = (new StringBuilder()).append(nowNode.value).append(str.charAt(i)).toString();
			nowNode.isLeaf = false;
			nowNode.childNodes[nowNode.childCnt] = newNode;
			nowNode.childCnt++;
		}

	}

	public boolean find(String str)
	{
		boolean isFound = false;
		Node nowNode = root;
		int i = 0;
		i = 0;
		do
		{
			if (i >= str.length())
				break;
			Node child = nowNode.child(str.charAt(i));
			if (child != null)
			{
				nowNode = child;
			} else
			{
				isFound = false;
				break;
			}
			i++;
		} while (true);
		if (i == str.length() && nowNode.value.equals(str))
			isFound = true;
		return isFound;
	}

	public static void main(String args[])
	{
		TrieTree trieTree = new TrieTree();
		trieTree.insert("bool");
		trieTree.insert("abc");
		trieTree.insert("def");
		trieTree.insert("abcdef");
		trieTree.insert("band");
		boolean isFound = trieTree.find("funk");
	}
}
