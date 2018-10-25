// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   NodeObserverPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			NodeDynamicInfo, Callback_NodeObserver_nodeInit, AMI_NodeObserver_nodeInit, Callback_NodeObserver_nodeUp, 
//			AMI_NodeObserver_nodeUp, Callback_NodeObserver_nodeDown, AMI_NodeObserver_nodeDown, ServerDynamicInfo, 
//			Callback_NodeObserver_updateServer, AMI_NodeObserver_updateServer, AdapterDynamicInfo, Callback_NodeObserver_updateAdapter, 
//			AMI_NodeObserver_updateAdapter

public interface NodeObserverPrx
	extends ObjectPrx
{

	public abstract void nodeInit(NodeDynamicInfo anodedynamicinfo[]);

	public abstract void nodeInit(NodeDynamicInfo anodedynamicinfo[], Map map);

	public abstract AsyncResult begin_nodeInit(NodeDynamicInfo anodedynamicinfo[]);

	public abstract AsyncResult begin_nodeInit(NodeDynamicInfo anodedynamicinfo[], Map map);

	public abstract AsyncResult begin_nodeInit(NodeDynamicInfo anodedynamicinfo[], Callback callback);

	public abstract AsyncResult begin_nodeInit(NodeDynamicInfo anodedynamicinfo[], Map map, Callback callback);

	public abstract AsyncResult begin_nodeInit(NodeDynamicInfo anodedynamicinfo[], Callback_NodeObserver_nodeInit callback_nodeobserver_nodeinit);

	public abstract AsyncResult begin_nodeInit(NodeDynamicInfo anodedynamicinfo[], Map map, Callback_NodeObserver_nodeInit callback_nodeobserver_nodeinit);

	public abstract void end_nodeInit(AsyncResult asyncresult);

	public abstract boolean nodeInit_async(AMI_NodeObserver_nodeInit ami_nodeobserver_nodeinit, NodeDynamicInfo anodedynamicinfo[]);

	public abstract boolean nodeInit_async(AMI_NodeObserver_nodeInit ami_nodeobserver_nodeinit, NodeDynamicInfo anodedynamicinfo[], Map map);

	public abstract void nodeUp(NodeDynamicInfo nodedynamicinfo);

	public abstract void nodeUp(NodeDynamicInfo nodedynamicinfo, Map map);

	public abstract AsyncResult begin_nodeUp(NodeDynamicInfo nodedynamicinfo);

	public abstract AsyncResult begin_nodeUp(NodeDynamicInfo nodedynamicinfo, Map map);

	public abstract AsyncResult begin_nodeUp(NodeDynamicInfo nodedynamicinfo, Callback callback);

	public abstract AsyncResult begin_nodeUp(NodeDynamicInfo nodedynamicinfo, Map map, Callback callback);

	public abstract AsyncResult begin_nodeUp(NodeDynamicInfo nodedynamicinfo, Callback_NodeObserver_nodeUp callback_nodeobserver_nodeup);

	public abstract AsyncResult begin_nodeUp(NodeDynamicInfo nodedynamicinfo, Map map, Callback_NodeObserver_nodeUp callback_nodeobserver_nodeup);

	public abstract void end_nodeUp(AsyncResult asyncresult);

	public abstract boolean nodeUp_async(AMI_NodeObserver_nodeUp ami_nodeobserver_nodeup, NodeDynamicInfo nodedynamicinfo);

	public abstract boolean nodeUp_async(AMI_NodeObserver_nodeUp ami_nodeobserver_nodeup, NodeDynamicInfo nodedynamicinfo, Map map);

	public abstract void nodeDown(String s);

	public abstract void nodeDown(String s, Map map);

	public abstract AsyncResult begin_nodeDown(String s);

	public abstract AsyncResult begin_nodeDown(String s, Map map);

	public abstract AsyncResult begin_nodeDown(String s, Callback callback);

	public abstract AsyncResult begin_nodeDown(String s, Map map, Callback callback);

	public abstract AsyncResult begin_nodeDown(String s, Callback_NodeObserver_nodeDown callback_nodeobserver_nodedown);

	public abstract AsyncResult begin_nodeDown(String s, Map map, Callback_NodeObserver_nodeDown callback_nodeobserver_nodedown);

	public abstract void end_nodeDown(AsyncResult asyncresult);

	public abstract boolean nodeDown_async(AMI_NodeObserver_nodeDown ami_nodeobserver_nodedown, String s);

	public abstract boolean nodeDown_async(AMI_NodeObserver_nodeDown ami_nodeobserver_nodedown, String s, Map map);

	public abstract void updateServer(String s, ServerDynamicInfo serverdynamicinfo);

	public abstract void updateServer(String s, ServerDynamicInfo serverdynamicinfo, Map map);

	public abstract AsyncResult begin_updateServer(String s, ServerDynamicInfo serverdynamicinfo);

	public abstract AsyncResult begin_updateServer(String s, ServerDynamicInfo serverdynamicinfo, Map map);

	public abstract AsyncResult begin_updateServer(String s, ServerDynamicInfo serverdynamicinfo, Callback callback);

	public abstract AsyncResult begin_updateServer(String s, ServerDynamicInfo serverdynamicinfo, Map map, Callback callback);

	public abstract AsyncResult begin_updateServer(String s, ServerDynamicInfo serverdynamicinfo, Callback_NodeObserver_updateServer callback_nodeobserver_updateserver);

	public abstract AsyncResult begin_updateServer(String s, ServerDynamicInfo serverdynamicinfo, Map map, Callback_NodeObserver_updateServer callback_nodeobserver_updateserver);

	public abstract void end_updateServer(AsyncResult asyncresult);

	public abstract boolean updateServer_async(AMI_NodeObserver_updateServer ami_nodeobserver_updateserver, String s, ServerDynamicInfo serverdynamicinfo);

	public abstract boolean updateServer_async(AMI_NodeObserver_updateServer ami_nodeobserver_updateserver, String s, ServerDynamicInfo serverdynamicinfo, Map map);

	public abstract void updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo);

	public abstract void updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo, Map map);

	public abstract AsyncResult begin_updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo);

	public abstract AsyncResult begin_updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo, Map map);

	public abstract AsyncResult begin_updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo, Callback callback);

	public abstract AsyncResult begin_updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo, Map map, Callback callback);

	public abstract AsyncResult begin_updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo, Callback_NodeObserver_updateAdapter callback_nodeobserver_updateadapter);

	public abstract AsyncResult begin_updateAdapter(String s, AdapterDynamicInfo adapterdynamicinfo, Map map, Callback_NodeObserver_updateAdapter callback_nodeobserver_updateadapter);

	public abstract void end_updateAdapter(AsyncResult asyncresult);

	public abstract boolean updateAdapter_async(AMI_NodeObserver_updateAdapter ami_nodeobserver_updateadapter, String s, AdapterDynamicInfo adapterdynamicinfo);

	public abstract boolean updateAdapter_async(AMI_NodeObserver_updateAdapter ami_nodeobserver_updateadapter, String s, AdapterDynamicInfo adapterdynamicinfo, Map map);
}
