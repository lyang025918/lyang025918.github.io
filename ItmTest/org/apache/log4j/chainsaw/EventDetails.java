// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EventDetails.java

package org.apache.log4j.chainsaw;

import org.apache.log4j.Priority;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

class EventDetails
{

	private final long mTimeStamp;
	private final Priority mPriority;
	private final String mCategoryName;
	private final String mNDC;
	private final String mThreadName;
	private final String mMessage;
	private final String mThrowableStrRep[];
	private final String mLocationDetails;

	EventDetails(long aTimeStamp, Priority aPriority, String aCategoryName, String aNDC, String aThreadName, String aMessage, 
			String aThrowableStrRep[], String aLocationDetails)
	{
		mTimeStamp = aTimeStamp;
		mPriority = aPriority;
		mCategoryName = aCategoryName;
		mNDC = aNDC;
		mThreadName = aThreadName;
		mMessage = aMessage;
		mThrowableStrRep = aThrowableStrRep;
		mLocationDetails = aLocationDetails;
	}

	EventDetails(LoggingEvent aEvent)
	{
		this(aEvent.timeStamp, ((Priority) (aEvent.getLevel())), aEvent.getLoggerName(), aEvent.getNDC(), aEvent.getThreadName(), aEvent.getRenderedMessage(), aEvent.getThrowableStrRep(), aEvent.getLocationInformation() != null ? aEvent.getLocationInformation().fullInfo : null);
	}

	long getTimeStamp()
	{
		return mTimeStamp;
	}

	Priority getPriority()
	{
		return mPriority;
	}

	String getCategoryName()
	{
		return mCategoryName;
	}

	String getNDC()
	{
		return mNDC;
	}

	String getThreadName()
	{
		return mThreadName;
	}

	String getMessage()
	{
		return mMessage;
	}

	String getLocationDetails()
	{
		return mLocationDetails;
	}

	String[] getThrowableStrRep()
	{
		return mThrowableStrRep;
	}
}
