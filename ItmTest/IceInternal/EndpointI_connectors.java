// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointI_connectors.java

package IceInternal;

import Ice.LocalException;
import java.util.List;

public interface EndpointI_connectors
{

	public abstract void connectors(List list);

	public abstract void exception(LocalException localexception);
}
