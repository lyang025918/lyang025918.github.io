// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _PropertiesOperationsNC.java

package Ice;

import java.util.Map;

// Referenced classes of package Ice:
//			Properties

public interface _PropertiesOperationsNC
{

	public abstract String getProperty(String s);

	public abstract String getPropertyWithDefault(String s, String s1);

	public abstract int getPropertyAsInt(String s);

	public abstract int getPropertyAsIntWithDefault(String s, int i);

	public abstract String[] getPropertyAsList(String s);

	public abstract String[] getPropertyAsListWithDefault(String s, String as[]);

	public abstract Map getPropertiesForPrefix(String s);

	public abstract void setProperty(String s, String s1);

	public abstract String[] getCommandLineOptions();

	public abstract String[] parseCommandLineOptions(String s, String as[]);

	public abstract String[] parseIceCommandLineOptions(String as[]);

	public abstract void load(String s);

	public abstract Properties _clone();
}
