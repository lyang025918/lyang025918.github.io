// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   LocatorTable.java

package IceInternal;

import Ice.BooleanHolder;
import Ice.Identity;
import java.util.HashMap;
import java.util.Map;

// Referenced classes of package IceInternal:
//			Time, EndpointI, Reference

final class LocatorTable
{
	private static final class ReferenceTableEntry
	{

		public final long time;
		public final Reference reference;

		public ReferenceTableEntry(long time, Reference reference)
		{
			this.time = time;
			this.reference = reference;
		}
	}

	private static final class EndpointTableEntry
	{

		public final long time;
		public final EndpointI endpoints[];

		public EndpointTableEntry(long time, EndpointI endpoints[])
		{
			this.time = time;
			this.endpoints = endpoints;
		}
	}


	private Map _adapterEndpointsTable;
	private Map _objectTable;
	static final boolean $assertionsDisabled = !IceInternal/LocatorTable.desiredAssertionStatus();

	LocatorTable()
	{
		_adapterEndpointsTable = new HashMap();
		_objectTable = new HashMap();
	}

	synchronized void clear()
	{
		_adapterEndpointsTable.clear();
		_objectTable.clear();
	}

	synchronized EndpointI[] getAdapterEndpoints(String adapter, int ttl, BooleanHolder cached)
	{
		if (ttl == 0)
		{
			cached.value = false;
			return null;
		}
		EndpointTableEntry entry = (EndpointTableEntry)_adapterEndpointsTable.get(adapter);
		if (entry != null)
		{
			cached.value = checkTTL(entry.time, ttl);
			return entry.endpoints;
		} else
		{
			cached.value = false;
			return null;
		}
	}

	synchronized void addAdapterEndpoints(String adapter, EndpointI endpoints[])
	{
		_adapterEndpointsTable.put(adapter, new EndpointTableEntry(Time.currentMonotonicTimeMillis(), endpoints));
	}

	synchronized EndpointI[] removeAdapterEndpoints(String adapter)
	{
		EndpointTableEntry entry = (EndpointTableEntry)_adapterEndpointsTable.remove(adapter);
		return entry == null ? null : entry.endpoints;
	}

	synchronized Reference getObjectReference(Identity id, int ttl, BooleanHolder cached)
	{
		if (ttl == 0)
		{
			cached.value = false;
			return null;
		}
		ReferenceTableEntry entry = (ReferenceTableEntry)_objectTable.get(id);
		if (entry != null)
		{
			cached.value = checkTTL(entry.time, ttl);
			return entry.reference;
		} else
		{
			cached.value = false;
			return null;
		}
	}

	synchronized void addObjectReference(Identity id, Reference ref)
	{
		_objectTable.put(id, new ReferenceTableEntry(Time.currentMonotonicTimeMillis(), ref));
	}

	synchronized Reference removeObjectReference(Identity id)
	{
		ReferenceTableEntry entry = (ReferenceTableEntry)_objectTable.remove(id);
		return entry == null ? null : entry.reference;
	}

	private boolean checkTTL(long time, int ttl)
	{
		if (!$assertionsDisabled && ttl == 0)
			throw new AssertionError();
		if (ttl < 0)
			return true;
		else
			return Time.currentMonotonicTimeMillis() - time <= (long)ttl * 1000L;
	}

}
