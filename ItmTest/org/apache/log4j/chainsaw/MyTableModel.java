// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   MyTableModel.java

package org.apache.log4j.chainsaw;

import java.text.DateFormat;
import java.util.*;
import javax.swing.table.AbstractTableModel;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

// Referenced classes of package org.apache.log4j.chainsaw:
//			EventDetails

class MyTableModel extends AbstractTableModel
{
	private class Processor
		implements Runnable
	{

		public void run()
		{
_L2:
label0:
			{
				try
				{
					Thread.sleep(1000L);
				}
				catch (InterruptedException e) { }
				synchronized (mLock)
				{
					if (!mPaused)
						break label0;
				}
				continue; /* Loop/switch isn't completed */
			}
			boolean toHead = true;
			boolean needUpdate = false;
			for (Iterator it = mPendingEvents.iterator(); it.hasNext();)
			{
				EventDetails event = (EventDetails)it.next();
				mAllEvents.add(event);
				toHead = toHead && event == mAllEvents.first();
				needUpdate = needUpdate || matchFilter(event);
			}

			mPendingEvents.clear();
			if (needUpdate)
				updateFilteredEvents(toHead);
			obj;
			JVM INSTR monitorexit ;
			if (true) goto _L2; else goto _L1
_L1:
			exception;
			throw exception;
		}

		private Processor()
		{
			super();
		}

	}


	private static final Logger LOG;
	private static final Comparator MY_COMP = new Comparator() {

		public int compare(Object aObj1, Object aObj2)
		{
			if (aObj1 == null && aObj2 == null)
				return 0;
			if (aObj1 == null)
				return -1;
			if (aObj2 == null)
				return 1;
			EventDetails le1 = (EventDetails)aObj1;
			EventDetails le2 = (EventDetails)aObj2;
			return le1.getTimeStamp() >= le2.getTimeStamp() ? -1 : 1;
		}

	};
	private static final String COL_NAMES[] = {
		"Time", "Priority", "Trace", "Category", "NDC", "Message"
	};
	private static final EventDetails EMPTY_LIST[] = new EventDetails[0];
	private static final DateFormat DATE_FORMATTER = DateFormat.getDateTimeInstance(3, 2);
	private final Object mLock = new Object();
	private final SortedSet mAllEvents;
	private EventDetails mFilteredEvents[];
	private final List mPendingEvents = new ArrayList();
	private boolean mPaused;
	private String mThreadFilter;
	private String mMessageFilter;
	private String mNDCFilter;
	private String mCategoryFilter;
	private Priority mPriorityFilter;

	MyTableModel()
	{
		mAllEvents = new TreeSet(MY_COMP);
		mFilteredEvents = EMPTY_LIST;
		mPaused = false;
		mThreadFilter = "";
		mMessageFilter = "";
		mNDCFilter = "";
		mCategoryFilter = "";
		mPriorityFilter = Priority.DEBUG;
		Thread t = new Thread(new Processor());
		t.setDaemon(true);
		t.start();
	}

	public int getRowCount()
	{
		Object obj = mLock;
		JVM INSTR monitorenter ;
		return mFilteredEvents.length;
		Exception exception;
		exception;
		throw exception;
	}

	public int getColumnCount()
	{
		return COL_NAMES.length;
	}

	public String getColumnName(int aCol)
	{
		return COL_NAMES[aCol];
	}

	public Class getColumnClass(int aCol)
	{
		return aCol != 2 ? java.lang.Object.class : java.lang.Boolean.class;
	}

	public Object getValueAt(int aRow, int aCol)
	{
		Object obj = mLock;
		JVM INSTR monitorenter ;
		EventDetails event = mFilteredEvents[aRow];
		if (aCol == 0)
			return DATE_FORMATTER.format(new Date(event.getTimeStamp()));
		if (aCol != 1) goto _L2; else goto _L1
_L1:
		event.getPriority();
		obj;
		JVM INSTR monitorexit ;
		return;
_L2:
		if (aCol != 2) goto _L4; else goto _L3
_L3:
		event.getThrowableStrRep() != null ? Boolean.TRUE : Boolean.FALSE;
		obj;
		JVM INSTR monitorexit ;
		return;
_L4:
		if (aCol != 3) goto _L6; else goto _L5
_L5:
		event.getCategoryName();
		obj;
		JVM INSTR monitorexit ;
		return;
_L6:
		if (aCol != 4) goto _L8; else goto _L7
_L7:
		event.getNDC();
		obj;
		JVM INSTR monitorexit ;
		return;
_L8:
		event.getMessage();
		obj;
		JVM INSTR monitorexit ;
		return;
		Exception exception;
		exception;
		throw exception;
	}

	public void setPriorityFilter(Priority aPriority)
	{
		synchronized (mLock)
		{
			mPriorityFilter = aPriority;
			updateFilteredEvents(false);
		}
	}

	public void setThreadFilter(String aStr)
	{
		synchronized (mLock)
		{
			mThreadFilter = aStr.trim();
			updateFilteredEvents(false);
		}
	}

	public void setMessageFilter(String aStr)
	{
		synchronized (mLock)
		{
			mMessageFilter = aStr.trim();
			updateFilteredEvents(false);
		}
	}

	public void setNDCFilter(String aStr)
	{
		synchronized (mLock)
		{
			mNDCFilter = aStr.trim();
			updateFilteredEvents(false);
		}
	}

	public void setCategoryFilter(String aStr)
	{
		synchronized (mLock)
		{
			mCategoryFilter = aStr.trim();
			updateFilteredEvents(false);
		}
	}

	public void addEvent(EventDetails aEvent)
	{
		synchronized (mLock)
		{
			mPendingEvents.add(aEvent);
		}
	}

	public void clear()
	{
		synchronized (mLock)
		{
			mAllEvents.clear();
			mFilteredEvents = new EventDetails[0];
			mPendingEvents.clear();
			fireTableDataChanged();
		}
	}

	public void toggle()
	{
		synchronized (mLock)
		{
			mPaused = !mPaused;
		}
	}

	public boolean isPaused()
	{
		Object obj = mLock;
		JVM INSTR monitorenter ;
		return mPaused;
		Exception exception;
		exception;
		throw exception;
	}

	public EventDetails getEventDetails(int aRow)
	{
		Object obj = mLock;
		JVM INSTR monitorenter ;
		return mFilteredEvents[aRow];
		Exception exception;
		exception;
		throw exception;
	}

	private void updateFilteredEvents(boolean aInsertedToFront)
	{
		long start = System.currentTimeMillis();
		List filtered = new ArrayList();
		int size = mAllEvents.size();
		Iterator it = mAllEvents.iterator();
		do
		{
			if (!it.hasNext())
				break;
			EventDetails event = (EventDetails)it.next();
			if (matchFilter(event))
				filtered.add(event);
		} while (true);
		EventDetails lastFirst = mFilteredEvents.length != 0 ? mFilteredEvents[0] : null;
		mFilteredEvents = (EventDetails[])(EventDetails[])filtered.toArray(EMPTY_LIST);
		if (aInsertedToFront && lastFirst != null)
		{
			int index = filtered.indexOf(lastFirst);
			if (index < 1)
			{
				LOG.warn("In strange state");
				fireTableDataChanged();
			} else
			{
				fireTableRowsInserted(0, index - 1);
			}
		} else
		{
			fireTableDataChanged();
		}
		long end = System.currentTimeMillis();
		LOG.debug("Total time [ms]: " + (end - start) + " in update, size: " + size);
	}

	private boolean matchFilter(EventDetails aEvent)
	{
		if (aEvent.getPriority().isGreaterOrEqual(mPriorityFilter) && aEvent.getThreadName().indexOf(mThreadFilter) >= 0 && aEvent.getCategoryName().indexOf(mCategoryFilter) >= 0 && (mNDCFilter.length() == 0 || aEvent.getNDC() != null && aEvent.getNDC().indexOf(mNDCFilter) >= 0))
		{
			String rm = aEvent.getMessage();
			if (rm == null)
				return mMessageFilter.length() == 0;
			else
				return rm.indexOf(mMessageFilter) >= 0;
		} else
		{
			return false;
		}
	}

	static 
	{
		LOG = Logger.getLogger(org.apache.log4j.chainsaw.MyTableModel.class);
	}






}
