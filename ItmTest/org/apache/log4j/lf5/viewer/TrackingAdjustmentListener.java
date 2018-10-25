// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TrackingAdjustmentListener.java

package org.apache.log4j.lf5.viewer;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

public class TrackingAdjustmentListener
	implements AdjustmentListener
{

	protected int _lastMaximum;

	public TrackingAdjustmentListener()
	{
		_lastMaximum = -1;
	}

	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		Adjustable bar = e.getAdjustable();
		int currentMaximum = bar.getMaximum();
		if (bar.getMaximum() == _lastMaximum)
			return;
		int bottom = bar.getValue() + bar.getVisibleAmount();
		if (bottom + bar.getUnitIncrement() >= _lastMaximum)
			bar.setValue(bar.getMaximum());
		_lastMaximum = currentMaximum;
	}
}
