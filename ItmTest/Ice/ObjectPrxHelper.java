// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ObjectPrxHelper.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			ObjectPrxHelperBase, FacetNotExistException, ObjectPrx

public class ObjectPrxHelper extends ObjectPrxHelperBase
{

	static final boolean $assertionsDisabled = !Ice/ObjectPrxHelper.desiredAssertionStatus();

	public ObjectPrxHelper()
	{
	}

	public static ObjectPrx checkedCast(ObjectPrx b)
	{
		return b;
	}

	public static ObjectPrx checkedCast(ObjectPrx b, Map ctx)
	{
		return b;
	}

	public static ObjectPrx checkedCast(ObjectPrx b, String f)
	{
		ObjectPrx d = null;
		if (b != null)
		{
			ObjectPrx bb = b.ice_facet(f);
			try
			{
				boolean ok = bb.ice_isA("::Ice::Object");
				if (!$assertionsDisabled && !ok)
					throw new AssertionError();
				ObjectPrxHelper h = new ObjectPrxHelper();
				h.__copyFrom(bb);
				d = h;
			}
			catch (FacetNotExistException ex) { }
		}
		return d;
	}

	public static ObjectPrx checkedCast(ObjectPrx b, String f, Map ctx)
	{
		ObjectPrx d = null;
		if (b != null)
		{
			ObjectPrx bb = b.ice_facet(f);
			try
			{
				boolean ok = bb.ice_isA("::Ice::Object", ctx);
				if (!$assertionsDisabled && !ok)
					throw new AssertionError();
				ObjectPrxHelper h = new ObjectPrxHelper();
				h.__copyFrom(bb);
				d = h;
			}
			catch (FacetNotExistException ex) { }
		}
		return d;
	}

	public static ObjectPrx uncheckedCast(ObjectPrx b)
	{
		return b;
	}

	public static ObjectPrx uncheckedCast(ObjectPrx b, String f)
	{
		ObjectPrx d = null;
		if (b != null)
		{
			ObjectPrx bb = b.ice_facet(f);
			ObjectPrxHelper h = new ObjectPrxHelper();
			h.__copyFrom(bb);
			d = h;
		}
		return d;
	}

}
