// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FeatureNotSupportedException.java

package Ice;


// Referenced classes of package Ice:
//			LocalException

public class FeatureNotSupportedException extends LocalException
{

	public String unsupportedFeature;

	public FeatureNotSupportedException()
	{
	}

	public FeatureNotSupportedException(Throwable cause)
	{
		super(cause);
	}

	public FeatureNotSupportedException(String unsupportedFeature)
	{
		this.unsupportedFeature = unsupportedFeature;
	}

	public FeatureNotSupportedException(String unsupportedFeature, Throwable cause)
	{
		super(cause);
		this.unsupportedFeature = unsupportedFeature;
	}

	public String ice_name()
	{
		return "Ice::FeatureNotSupportedException";
	}
}
