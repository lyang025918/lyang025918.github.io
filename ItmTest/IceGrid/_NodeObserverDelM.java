// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _NodeObserverDelM.java

package IceGrid;

import Ice.*;
import IceInternal.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			_NodeObserverDel, NodeDynamicInfoSeqHelper, NodeDynamicInfo, AdapterDynamicInfo, 
//			ServerDynamicInfo

public final class _NodeObserverDelM extends _ObjectDelM
	implements _NodeObserverDel
{

	public _NodeObserverDelM()
	{
	}

	public void nodeDown(String name, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("nodeDown", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(name);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		if (!__og.is().isEmpty())
			try
			{
				if (!__ok)
					try
					{
						__og.throwUserException();
					}
					catch (UserException __ex)
					{
						throw new UnknownUserException(__ex.ice_name(), __ex);
					}
				__og.is().skipEmptyEncaps();
			}
			catch (LocalException __ex)
			{
				throw new LocalExceptionWrapper(__ex, false);
			}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void nodeInit(NodeDynamicInfo nodes[], Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("nodeInit", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			NodeDynamicInfoSeqHelper.write(__os, nodes);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		if (!__og.is().isEmpty())
			try
			{
				if (!__ok)
					try
					{
						__og.throwUserException();
					}
					catch (UserException __ex)
					{
						throw new UnknownUserException(__ex.ice_name(), __ex);
					}
				__og.is().skipEmptyEncaps();
			}
			catch (LocalException __ex)
			{
				throw new LocalExceptionWrapper(__ex, false);
			}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void nodeUp(NodeDynamicInfo node, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("nodeUp", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			node.__write(__os);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		if (!__og.is().isEmpty())
			try
			{
				if (!__ok)
					try
					{
						__og.throwUserException();
					}
					catch (UserException __ex)
					{
						throw new UnknownUserException(__ex.ice_name(), __ex);
					}
				__og.is().skipEmptyEncaps();
			}
			catch (LocalException __ex)
			{
				throw new LocalExceptionWrapper(__ex, false);
			}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_135;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void updateAdapter(String node, AdapterDynamicInfo updatedInfo, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("updateAdapter", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(node);
			updatedInfo.__write(__os);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		if (!__og.is().isEmpty())
			try
			{
				if (!__ok)
					try
					{
						__og.throwUserException();
					}
					catch (UserException __ex)
					{
						throw new UnknownUserException(__ex.ice_name(), __ex);
					}
				__og.is().skipEmptyEncaps();
			}
			catch (LocalException __ex)
			{
				throw new LocalExceptionWrapper(__ex, false);
			}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_150;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}

	public void updateServer(String node, ServerDynamicInfo updatedInfo, Map __ctx)
		throws LocalExceptionWrapper
	{
		Outgoing __og = __handler.getOutgoing("updateServer", OperationMode.Normal, __ctx);
		try
		{
			BasicStream __os = __og.os();
			__os.writeString(node);
			updatedInfo.__write(__os);
		}
		catch (LocalException __ex)
		{
			__og.abort(__ex);
		}
		boolean __ok = __og.invoke();
		if (!__og.is().isEmpty())
			try
			{
				if (!__ok)
					try
					{
						__og.throwUserException();
					}
					catch (UserException __ex)
					{
						throw new UnknownUserException(__ex.ice_name(), __ex);
					}
				__og.is().skipEmptyEncaps();
			}
			catch (LocalException __ex)
			{
				throw new LocalExceptionWrapper(__ex, false);
			}
		__handler.reclaimOutgoing(__og);
		break MISSING_BLOCK_LABEL_150;
		Exception exception;
		exception;
		__handler.reclaimOutgoing(__og);
		throw exception;
	}
}
