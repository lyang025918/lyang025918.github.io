// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   StringSetPrx.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			Callback_StringSet_add, Callback_StringSet_remove, Callback_StringSet_get

public interface StringSetPrx
	extends ObjectPrx
{

	public abstract void add(String as[]);

	public abstract void add(String as[], Map map);

	public abstract AsyncResult begin_add(String as[]);

	public abstract AsyncResult begin_add(String as[], Map map);

	public abstract AsyncResult begin_add(String as[], Callback callback);

	public abstract AsyncResult begin_add(String as[], Map map, Callback callback);

	public abstract AsyncResult begin_add(String as[], Callback_StringSet_add callback_stringset_add);

	public abstract AsyncResult begin_add(String as[], Map map, Callback_StringSet_add callback_stringset_add);

	public abstract void end_add(AsyncResult asyncresult);

	public abstract void remove(String as[]);

	public abstract void remove(String as[], Map map);

	public abstract AsyncResult begin_remove(String as[]);

	public abstract AsyncResult begin_remove(String as[], Map map);

	public abstract AsyncResult begin_remove(String as[], Callback callback);

	public abstract AsyncResult begin_remove(String as[], Map map, Callback callback);

	public abstract AsyncResult begin_remove(String as[], Callback_StringSet_remove callback_stringset_remove);

	public abstract AsyncResult begin_remove(String as[], Map map, Callback_StringSet_remove callback_stringset_remove);

	public abstract void end_remove(AsyncResult asyncresult);

	public abstract String[] get();

	public abstract String[] get(Map map);

	public abstract AsyncResult begin_get();

	public abstract AsyncResult begin_get(Map map);

	public abstract AsyncResult begin_get(Callback callback);

	public abstract AsyncResult begin_get(Map map, Callback callback);

	public abstract AsyncResult begin_get(Callback_StringSet_get callback_stringset_get);

	public abstract AsyncResult begin_get(Map map, Callback_StringSet_get callback_stringset_get);

	public abstract String[] end_get(AsyncResult asyncresult);
}
