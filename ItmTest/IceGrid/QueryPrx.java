// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   QueryPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			Callback_Query_findObjectById, AMI_Query_findObjectById, Callback_Query_findObjectByType, AMI_Query_findObjectByType, 
//			LoadSample, Callback_Query_findObjectByTypeOnLeastLoadedNode, AMI_Query_findObjectByTypeOnLeastLoadedNode, Callback_Query_findAllObjectsByType, 
//			AMI_Query_findAllObjectsByType, Callback_Query_findAllReplicas, AMI_Query_findAllReplicas

public interface QueryPrx
	extends ObjectPrx
{

	public abstract ObjectPrx findObjectById(Identity identity);

	public abstract ObjectPrx findObjectById(Identity identity, Map map);

	public abstract AsyncResult begin_findObjectById(Identity identity);

	public abstract AsyncResult begin_findObjectById(Identity identity, Map map);

	public abstract AsyncResult begin_findObjectById(Identity identity, Callback callback);

	public abstract AsyncResult begin_findObjectById(Identity identity, Map map, Callback callback);

	public abstract AsyncResult begin_findObjectById(Identity identity, Callback_Query_findObjectById callback_query_findobjectbyid);

	public abstract AsyncResult begin_findObjectById(Identity identity, Map map, Callback_Query_findObjectById callback_query_findobjectbyid);

	public abstract ObjectPrx end_findObjectById(AsyncResult asyncresult);

	public abstract boolean findObjectById_async(AMI_Query_findObjectById ami_query_findobjectbyid, Identity identity);

	public abstract boolean findObjectById_async(AMI_Query_findObjectById ami_query_findobjectbyid, Identity identity, Map map);

	public abstract ObjectPrx findObjectByType(String s);

	public abstract ObjectPrx findObjectByType(String s, Map map);

	public abstract AsyncResult begin_findObjectByType(String s);

	public abstract AsyncResult begin_findObjectByType(String s, Map map);

	public abstract AsyncResult begin_findObjectByType(String s, Callback callback);

	public abstract AsyncResult begin_findObjectByType(String s, Map map, Callback callback);

	public abstract AsyncResult begin_findObjectByType(String s, Callback_Query_findObjectByType callback_query_findobjectbytype);

	public abstract AsyncResult begin_findObjectByType(String s, Map map, Callback_Query_findObjectByType callback_query_findobjectbytype);

	public abstract ObjectPrx end_findObjectByType(AsyncResult asyncresult);

	public abstract boolean findObjectByType_async(AMI_Query_findObjectByType ami_query_findobjectbytype, String s);

	public abstract boolean findObjectByType_async(AMI_Query_findObjectByType ami_query_findobjectbytype, String s, Map map);

	public abstract ObjectPrx findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample);

	public abstract ObjectPrx findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample, Map map);

	public abstract AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample);

	public abstract AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample, Map map);

	public abstract AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample, Callback callback);

	public abstract AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample, Map map, Callback callback);

	public abstract AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample, Callback_Query_findObjectByTypeOnLeastLoadedNode callback_query_findobjectbytypeonleastloadednode);

	public abstract AsyncResult begin_findObjectByTypeOnLeastLoadedNode(String s, LoadSample loadsample, Map map, Callback_Query_findObjectByTypeOnLeastLoadedNode callback_query_findobjectbytypeonleastloadednode);

	public abstract ObjectPrx end_findObjectByTypeOnLeastLoadedNode(AsyncResult asyncresult);

	public abstract boolean findObjectByTypeOnLeastLoadedNode_async(AMI_Query_findObjectByTypeOnLeastLoadedNode ami_query_findobjectbytypeonleastloadednode, String s, LoadSample loadsample);

	public abstract boolean findObjectByTypeOnLeastLoadedNode_async(AMI_Query_findObjectByTypeOnLeastLoadedNode ami_query_findobjectbytypeonleastloadednode, String s, LoadSample loadsample, Map map);

	public abstract ObjectPrx[] findAllObjectsByType(String s);

	public abstract ObjectPrx[] findAllObjectsByType(String s, Map map);

	public abstract AsyncResult begin_findAllObjectsByType(String s);

	public abstract AsyncResult begin_findAllObjectsByType(String s, Map map);

	public abstract AsyncResult begin_findAllObjectsByType(String s, Callback callback);

	public abstract AsyncResult begin_findAllObjectsByType(String s, Map map, Callback callback);

	public abstract AsyncResult begin_findAllObjectsByType(String s, Callback_Query_findAllObjectsByType callback_query_findallobjectsbytype);

	public abstract AsyncResult begin_findAllObjectsByType(String s, Map map, Callback_Query_findAllObjectsByType callback_query_findallobjectsbytype);

	public abstract ObjectPrx[] end_findAllObjectsByType(AsyncResult asyncresult);

	public abstract boolean findAllObjectsByType_async(AMI_Query_findAllObjectsByType ami_query_findallobjectsbytype, String s);

	public abstract boolean findAllObjectsByType_async(AMI_Query_findAllObjectsByType ami_query_findallobjectsbytype, String s, Map map);

	public abstract ObjectPrx[] findAllReplicas(ObjectPrx objectprx);

	public abstract ObjectPrx[] findAllReplicas(ObjectPrx objectprx, Map map);

	public abstract AsyncResult begin_findAllReplicas(ObjectPrx objectprx);

	public abstract AsyncResult begin_findAllReplicas(ObjectPrx objectprx, Map map);

	public abstract AsyncResult begin_findAllReplicas(ObjectPrx objectprx, Callback callback);

	public abstract AsyncResult begin_findAllReplicas(ObjectPrx objectprx, Map map, Callback callback);

	public abstract AsyncResult begin_findAllReplicas(ObjectPrx objectprx, Callback_Query_findAllReplicas callback_query_findallreplicas);

	public abstract AsyncResult begin_findAllReplicas(ObjectPrx objectprx, Map map, Callback_Query_findAllReplicas callback_query_findallreplicas);

	public abstract ObjectPrx[] end_findAllReplicas(AsyncResult asyncresult);

	public abstract boolean findAllReplicas_async(AMI_Query_findAllReplicas ami_query_findallreplicas, ObjectPrx objectprx);

	public abstract boolean findAllReplicas_async(AMI_Query_findAllReplicas ami_query_findallreplicas, ObjectPrx objectprx, Map map);
}
