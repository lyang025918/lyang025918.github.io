// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SignalPolicy.java

package Ice;


public final class SignalPolicy extends Enum
{

	public static final SignalPolicy HandleSignals;
	public static final SignalPolicy NoSignalHandling;
	private static final SignalPolicy $VALUES[];

	public static final SignalPolicy[] values()
	{
		return (SignalPolicy[])$VALUES.clone();
	}

	public static SignalPolicy valueOf(String name)
	{
		return (SignalPolicy)Enum.valueOf(Ice/SignalPolicy, name);
	}

	private SignalPolicy(String s, int i)
	{
		super(s, i);
	}

	static 
	{
		HandleSignals = new SignalPolicy("HandleSignals", 0);
		NoSignalHandling = new SignalPolicy("NoSignalHandling", 1);
		$VALUES = (new SignalPolicy[] {
			HandleSignals, NoSignalHandling
		});
	}
}
