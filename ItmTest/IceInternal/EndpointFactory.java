// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   EndpointFactory.java

package IceInternal;


// Referenced classes of package IceInternal:
//			EndpointI, BasicStream

public interface EndpointFactory
{

	public abstract short type();

	public abstract String protocol();

	public abstract EndpointI create(String s, boolean flag);

	public abstract EndpointI read(BasicStream basicstream);

	public abstract void destroy();
}
