// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMServer.java

package com.iflytek.itm.svc.server;

import Ice.*;
import com.iflytek.itm.api.ITMFactory;
import com.iflytek.itm.util.JarTool;
import java.io.File;
import org.apache.log4j.Logger;

// Referenced classes of package com.iflytek.itm.svc.server:
//			SItmSvcFactory

public class ITMServer extends Application
{

	private static final Logger logger = Logger.getLogger(com/iflytek/itm/svc/server/ITMServer);

	public ITMServer()
	{
	}

	public static void main(String args[])
	{
		ITMFactory.inst();
		InitializationData initData = new InitializationData();
		initData.properties = Util.createProperties();
		String jarDir = JarTool.getJarDir();
		String cfgPath = (new StringBuilder()).append(jarDir).append("/itm.cfg").toString();
		logger.info((new StringBuilder()).append("jarDir=").append(jarDir).toString());
		logger.info((new StringBuilder()).append("cfgPath=").append(cfgPath).toString());
		if (!(new File(cfgPath)).exists())
		{
			logger.error((new StringBuilder()).append("ITMServer | there is no itm.cfg, please check. Path=").append(cfgPath).toString());
			return;
		} else
		{
			initData.properties.load(cfgPath);
			initData.properties.setProperty("Ice.MessageSizeMax", "1073741");
			initData.properties.setProperty("Ice.ThreadPool.Server.Size", "16");
			initData.properties.setProperty("Ice.ThreadPool.Client.Size", "16");
			ITMServer app = new ITMServer();
			int status = app.main("ITMServer", args, initData);
			System.exit(status);
			return;
		}
	}

	public int run(String args[])
	{
		if (args.length > 0)
		{
			logger.error((new StringBuilder()).append(appName()).append(": too many arguments").toString());
			return 1;
		}
		Properties properties = communicator().getProperties();
		String ename = properties.getProperty("itm.ename");
		String ip = properties.getProperty("itm.ip");
		if (!ip.isEmpty())
			ip = (new StringBuilder()).append("-h ").append(ip).toString();
		String port = properties.getProperty("itm.port");
		String idx_root_dir = properties.getProperty("itm.index.home");
		SItmSvcFactory.idxRootDir = idx_root_dir;
		String adapterName = (new StringBuilder()).append(ename).append("Adapter").toString();
		String endPoints = (new StringBuilder()).append("default ").append(ip).append(" -p ").append(port).toString();
		logger.info((new StringBuilder()).append("endpoints=").append(endPoints).toString());
		ObjectAdapter adapter = communicator().createObjectAdapterWithEndpoints(adapterName, endPoints);
		SItmSvcFactory itmSvcFactory = new SItmSvcFactory(adapter, communicator());
		adapter.add(itmSvcFactory, communicator().stringToIdentity(ename));
		adapter.activate();
		logger.info("ITMServer started ...");
		communicator().waitForShutdown();
		return 0;
	}

}
