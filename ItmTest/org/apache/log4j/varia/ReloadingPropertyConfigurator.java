// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ReloadingPropertyConfigurator.java

package org.apache.log4j.varia;

import java.io.InputStream;
import java.net.URL;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

public class ReloadingPropertyConfigurator
	implements Configurator
{

	PropertyConfigurator delegate;

	public ReloadingPropertyConfigurator()
	{
		delegate = new PropertyConfigurator();
	}

	public void doConfigure(InputStream inputstream, LoggerRepository loggerrepository)
	{
	}

	public void doConfigure(URL url1, LoggerRepository loggerrepository)
	{
	}
}
