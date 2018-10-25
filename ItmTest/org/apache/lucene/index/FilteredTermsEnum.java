// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FilteredTermsEnum.java

package org.apache.lucene.index;

import java.io.IOException;
import java.util.Comparator;
import org.apache.lucene.util.*;

// Referenced classes of package org.apache.lucene.index:
//			TermsEnum, DocsEnum, DocsAndPositionsEnum, TermState

public abstract class FilteredTermsEnum extends TermsEnum
{
	protected static final class AcceptStatus extends Enum
	{

		public static final AcceptStatus YES;
		public static final AcceptStatus YES_AND_SEEK;
		public static final AcceptStatus NO;
		public static final AcceptStatus NO_AND_SEEK;
		public static final AcceptStatus END;
		private static final AcceptStatus $VALUES[];

		public static AcceptStatus[] values()
		{
			return (AcceptStatus[])$VALUES.clone();
		}

		public static AcceptStatus valueOf(String name)
		{
			return (AcceptStatus)Enum.valueOf(org/apache/lucene/index/FilteredTermsEnum$AcceptStatus, name);
		}

		static 
		{
			YES = new AcceptStatus("YES", 0);
			YES_AND_SEEK = new AcceptStatus("YES_AND_SEEK", 1);
			NO = new AcceptStatus("NO", 2);
			NO_AND_SEEK = new AcceptStatus("NO_AND_SEEK", 3);
			END = new AcceptStatus("END", 4);
			$VALUES = (new AcceptStatus[] {
				YES, YES_AND_SEEK, NO, NO_AND_SEEK, END
			});
		}

		private AcceptStatus(String s, int i)
		{
			super(s, i);
		}
	}


	private BytesRef initialSeekTerm;
	private boolean doSeek;
	private BytesRef actualTerm;
	private final TermsEnum tenum;
	static final boolean $assertionsDisabled = !org/apache/lucene/index/FilteredTermsEnum.desiredAssertionStatus();

	protected abstract AcceptStatus accept(BytesRef bytesref)
		throws IOException;

	public FilteredTermsEnum(TermsEnum tenum)
	{
		this(tenum, true);
	}

	public FilteredTermsEnum(TermsEnum tenum, boolean startWithSeek)
	{
		initialSeekTerm = null;
		actualTerm = null;
		if (!$assertionsDisabled && tenum == null)
		{
			throw new AssertionError();
		} else
		{
			this.tenum = tenum;
			doSeek = startWithSeek;
			return;
		}
	}

	protected final void setInitialSeekTerm(BytesRef term)
	{
		initialSeekTerm = term;
	}

	protected BytesRef nextSeekTerm(BytesRef currentTerm)
		throws IOException
	{
		BytesRef t = initialSeekTerm;
		initialSeekTerm = null;
		return t;
	}

	public AttributeSource attributes()
	{
		return tenum.attributes();
	}

	public BytesRef term()
		throws IOException
	{
		return tenum.term();
	}

	public Comparator getComparator()
	{
		return tenum.getComparator();
	}

	public int docFreq()
		throws IOException
	{
		return tenum.docFreq();
	}

	public long totalTermFreq()
		throws IOException
	{
		return tenum.totalTermFreq();
	}

	public boolean seekExact(BytesRef term, boolean useCache)
		throws IOException
	{
		throw new UnsupportedOperationException((new StringBuilder()).append(getClass().getName()).append(" does not support seeking").toString());
	}

	public TermsEnum.SeekStatus seekCeil(BytesRef term, boolean useCache)
		throws IOException
	{
		throw new UnsupportedOperationException((new StringBuilder()).append(getClass().getName()).append(" does not support seeking").toString());
	}

	public void seekExact(long ord)
		throws IOException
	{
		throw new UnsupportedOperationException((new StringBuilder()).append(getClass().getName()).append(" does not support seeking").toString());
	}

	public long ord()
		throws IOException
	{
		return tenum.ord();
	}

	public DocsEnum docs(Bits bits, DocsEnum reuse, int flags)
		throws IOException
	{
		return tenum.docs(bits, reuse, flags);
	}

	public DocsAndPositionsEnum docsAndPositions(Bits bits, DocsAndPositionsEnum reuse, int flags)
		throws IOException
	{
		return tenum.docsAndPositions(bits, reuse, flags);
	}

	public void seekExact(BytesRef term, TermState state)
		throws IOException
	{
		throw new UnsupportedOperationException((new StringBuilder()).append(getClass().getName()).append(" does not support seeking").toString());
	}

	public TermState termState()
		throws IOException
	{
		if (!$assertionsDisabled && tenum == null)
			throw new AssertionError();
		else
			return tenum.termState();
	}

	public BytesRef next()
		throws IOException
	{
		do
		{
			if (doSeek)
			{
				doSeek = false;
				BytesRef t = nextSeekTerm(actualTerm);
				if (!$assertionsDisabled && actualTerm != null && t != null && getComparator().compare(t, actualTerm) <= 0)
					throw new AssertionError((new StringBuilder()).append("curTerm=").append(actualTerm).append(" seekTerm=").append(t).toString());
				if (t == null || tenum.seekCeil(t, false) == TermsEnum.SeekStatus.END)
					return null;
				actualTerm = tenum.term();
			} else
			{
				actualTerm = tenum.next();
				if (actualTerm == null)
					return null;
			}
			static class 1
			{

				static final int $SwitchMap$org$apache$lucene$index$FilteredTermsEnum$AcceptStatus[];

				static 
				{
					$SwitchMap$org$apache$lucene$index$FilteredTermsEnum$AcceptStatus = new int[AcceptStatus.values().length];
					try
					{
						$SwitchMap$org$apache$lucene$index$FilteredTermsEnum$AcceptStatus[AcceptStatus.YES_AND_SEEK.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$FilteredTermsEnum$AcceptStatus[AcceptStatus.YES.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$FilteredTermsEnum$AcceptStatus[AcceptStatus.NO_AND_SEEK.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$org$apache$lucene$index$FilteredTermsEnum$AcceptStatus[AcceptStatus.END.ordinal()] = 4;
					}
					catch (NoSuchFieldError ex) { }
				}
			}

			switch (1..SwitchMap.org.apache.lucene.index.FilteredTermsEnum.AcceptStatus[accept(actualTerm).ordinal()])
			{
			case 1: // '\001'
				doSeek = true;
				// fall through

			case 2: // '\002'
				return actualTerm;

			case 3: // '\003'
				doSeek = true;
				break;

			case 4: // '\004'
				return null;
			}
		} while (true);
	}

}
