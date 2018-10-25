// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileIteratorPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			FileNotAvailableException, Callback_FileIterator_read, Callback_FileIterator_destroy

public interface FileIteratorPrx
	extends ObjectPrx
{

	public abstract boolean read(int i, StringSeqHolder stringseqholder)
		throws FileNotAvailableException;

	public abstract boolean read(int i, StringSeqHolder stringseqholder, Map map)
		throws FileNotAvailableException;

	public abstract AsyncResult begin_read(int i);

	public abstract AsyncResult begin_read(int i, Map map);

	public abstract AsyncResult begin_read(int i, Callback callback);

	public abstract AsyncResult begin_read(int i, Map map, Callback callback);

	public abstract AsyncResult begin_read(int i, Callback_FileIterator_read callback_fileiterator_read);

	public abstract AsyncResult begin_read(int i, Map map, Callback_FileIterator_read callback_fileiterator_read);

	public abstract boolean end_read(StringSeqHolder stringseqholder, AsyncResult asyncresult)
		throws FileNotAvailableException;

	public abstract void destroy();

	public abstract void destroy(Map map);

	public abstract AsyncResult begin_destroy();

	public abstract AsyncResult begin_destroy(Map map);

	public abstract AsyncResult begin_destroy(Callback callback);

	public abstract AsyncResult begin_destroy(Map map, Callback callback);

	public abstract AsyncResult begin_destroy(Callback_FileIterator_destroy callback_fileiterator_destroy);

	public abstract AsyncResult begin_destroy(Map map, Callback_FileIterator_destroy callback_fileiterator_destroy);

	public abstract void end_destroy(AsyncResult asyncresult);
}
