// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RegistryObserverPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			RegistryInfo, Callback_RegistryObserver_registryInit, AMI_RegistryObserver_registryInit, Callback_RegistryObserver_registryUp, 
//			Callback_RegistryObserver_registryDown

public interface RegistryObserverPrx
	extends ObjectPrx
{

	public abstract void registryInit(RegistryInfo aregistryinfo[]);

	public abstract void registryInit(RegistryInfo aregistryinfo[], Map map);

	public abstract AsyncResult begin_registryInit(RegistryInfo aregistryinfo[]);

	public abstract AsyncResult begin_registryInit(RegistryInfo aregistryinfo[], Map map);

	public abstract AsyncResult begin_registryInit(RegistryInfo aregistryinfo[], Callback callback);

	public abstract AsyncResult begin_registryInit(RegistryInfo aregistryinfo[], Map map, Callback callback);

	public abstract AsyncResult begin_registryInit(RegistryInfo aregistryinfo[], Callback_RegistryObserver_registryInit callback_registryobserver_registryinit);

	public abstract AsyncResult begin_registryInit(RegistryInfo aregistryinfo[], Map map, Callback_RegistryObserver_registryInit callback_registryobserver_registryinit);

	public abstract void end_registryInit(AsyncResult asyncresult);

	public abstract boolean registryInit_async(AMI_RegistryObserver_registryInit ami_registryobserver_registryinit, RegistryInfo aregistryinfo[]);

	public abstract boolean registryInit_async(AMI_RegistryObserver_registryInit ami_registryobserver_registryinit, RegistryInfo aregistryinfo[], Map map);

	public abstract void registryUp(RegistryInfo registryinfo);

	public abstract void registryUp(RegistryInfo registryinfo, Map map);

	public abstract AsyncResult begin_registryUp(RegistryInfo registryinfo);

	public abstract AsyncResult begin_registryUp(RegistryInfo registryinfo, Map map);

	public abstract AsyncResult begin_registryUp(RegistryInfo registryinfo, Callback callback);

	public abstract AsyncResult begin_registryUp(RegistryInfo registryinfo, Map map, Callback callback);

	public abstract AsyncResult begin_registryUp(RegistryInfo registryinfo, Callback_RegistryObserver_registryUp callback_registryobserver_registryup);

	public abstract AsyncResult begin_registryUp(RegistryInfo registryinfo, Map map, Callback_RegistryObserver_registryUp callback_registryobserver_registryup);

	public abstract void end_registryUp(AsyncResult asyncresult);

	public abstract void registryDown(String s);

	public abstract void registryDown(String s, Map map);

	public abstract AsyncResult begin_registryDown(String s);

	public abstract AsyncResult begin_registryDown(String s, Map map);

	public abstract AsyncResult begin_registryDown(String s, Callback callback);

	public abstract AsyncResult begin_registryDown(String s, Map map, Callback callback);

	public abstract AsyncResult begin_registryDown(String s, Callback_RegistryObserver_registryDown callback_registryobserver_registrydown);

	public abstract AsyncResult begin_registryDown(String s, Map map, Callback_RegistryObserver_registryDown callback_registryobserver_registrydown);

	public abstract void end_registryDown(AsyncResult asyncresult);
}
