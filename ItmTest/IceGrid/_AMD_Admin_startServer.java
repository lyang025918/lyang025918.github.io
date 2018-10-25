// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _AMD_Admin_startServer.java

package IceGrid;

import IceInternal.*;

// Referenced classes of package IceGrid:
//			DeploymentException, NodeUnreachableException, ServerNotExistException, ServerStartException, 
//			AMD_Admin_startServer

final class _AMD_Admin_startServer extends IncomingAsync
	implements AMD_Admin_startServer
{

	public _AMD_Admin_startServer(Incoming in)
	{
		super(in);
	}

	public void ice_response()
	{
		if (__validateResponse(true))
			__response(true);
	}

	public void ice_exception(Exception ex)
	{
		try
		{
			throw ex;
		}
		catch (DeploymentException __ex)
		{
			if (__validateResponse(false))
			{
				__os().writeUserException(__ex);
				__response(false);
			}
		}
		catch (NodeUnreachableException __ex)
		{
			if (__validateResponse(false))
			{
				__os().writeUserException(__ex);
				__response(false);
			}
		}
		catch (ServerNotExistException __ex)
		{
			if (__validateResponse(false))
			{
				__os().writeUserException(__ex);
				__response(false);
			}
		}
		catch (ServerStartException __ex)
		{
			if (__validateResponse(false))
			{
				__os().writeUserException(__ex);
				__response(false);
			}
		}
		catch (Exception __ex)
		{
			super.ice_exception(__ex);
		}
	}
}
