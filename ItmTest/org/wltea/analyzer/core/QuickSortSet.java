// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QuickSortSet.java

package org.wltea.analyzer.core;


// Referenced classes of package org.wltea.analyzer.core:
//			Lexeme

class QuickSortSet
{
	class Cell
		implements Comparable
	{

		private Cell prev;
		private Cell next;
		private Lexeme lexeme;
		final QuickSortSet this$0;

		public int compareTo(Cell o)
		{
			return lexeme.compareTo(o.lexeme);
		}

		public Cell getPrev()
		{
			return prev;
		}

		public Cell getNext()
		{
			return next;
		}

		public Lexeme getLexeme()
		{
			return lexeme;
		}

		public volatile int compareTo(Object obj)
		{
			return compareTo((Cell)obj);
		}






		Cell(Lexeme lexeme)
		{
			this$0 = QuickSortSet.this;
			super();
			if (lexeme == null)
			{
				throw new IllegalArgumentException("lexeme must not be null");
			} else
			{
				this.lexeme = lexeme;
				return;
			}
		}
	}


	private Cell head;
	private Cell tail;
	private int size;

	QuickSortSet()
	{
		size = 0;
	}

	boolean addLexeme(Lexeme lexeme)
	{
		Cell newCell = new Cell(lexeme);
		if (size == 0)
		{
			head = newCell;
			tail = newCell;
			size++;
			return true;
		}
		if (tail.compareTo(newCell) == 0)
			return false;
		if (tail.compareTo(newCell) < 0)
		{
			tail.next = newCell;
			newCell.prev = tail;
			tail = newCell;
			size++;
			return true;
		}
		if (head.compareTo(newCell) > 0)
		{
			head.prev = newCell;
			newCell.next = head;
			head = newCell;
			size++;
			return true;
		}
		Cell index;
		for (index = tail; index != null && index.compareTo(newCell) > 0; index = index.prev);
		if (index.compareTo(newCell) == 0)
			return false;
		if (index.compareTo(newCell) < 0)
		{
			newCell.prev = index;
			newCell.next = index.next;
			index.next.prev = newCell;
			index.next = newCell;
			size++;
			return true;
		} else
		{
			return false;
		}
	}

	Lexeme peekFirst()
	{
		if (head != null)
			return head.lexeme;
		else
			return null;
	}

	Lexeme pollFirst()
	{
		if (size == 1)
		{
			Lexeme first = head.lexeme;
			head = null;
			tail = null;
			size--;
			return first;
		}
		if (size > 1)
		{
			Lexeme first = head.lexeme;
			head = head.next;
			size--;
			return first;
		} else
		{
			return null;
		}
	}

	Lexeme peekLast()
	{
		if (tail != null)
			return tail.lexeme;
		else
			return null;
	}

	Lexeme pollLast()
	{
		if (size == 1)
		{
			Lexeme last = head.lexeme;
			head = null;
			tail = null;
			size--;
			return last;
		}
		if (size > 1)
		{
			Lexeme last = tail.lexeme;
			tail = tail.prev;
			size--;
			return last;
		} else
		{
			return null;
		}
	}

	int size()
	{
		return size;
	}

	boolean isEmpty()
	{
		return size == 0;
	}

	Cell getHead()
	{
		return head;
	}
}
