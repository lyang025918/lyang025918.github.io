// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Blobject.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Incoming;

// Referenced classes of package Ice:
//			ObjectImpl, ByteSeqHolder, DispatchStatus, Current

public abstract class Blobject extends ObjectImpl
{

	public Blobject()
	{
	}

	public abstract boolean ice_invoke(byte abyte0[], ByteSeqHolder byteseqholder, Current current);

	public DispatchStatus __dispatch(Incoming in, Current current)
	{
		ByteSeqHolder outParams = new ByteSeqHolder();
		BasicStream is = in.is();
		is.startReadEncaps();
		int sz = is.getReadEncapsSize();
		byte inParams[] = is.readBlob(sz);
		is.endReadEncaps();
		boolean ok = ice_invoke(inParams, outParams, current);
		if (outParams.value != null)
			in.os().writeBlob(outParams.value);
		if (ok)
			return DispatchStatus.DispatchOK;
		else
			return DispatchStatus.DispatchUserException;
	}
}
