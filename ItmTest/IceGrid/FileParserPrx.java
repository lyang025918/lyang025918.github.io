// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   FileParserPrx.java

package IceGrid;

import Ice.*;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ParseException, AdminPrx, ApplicationDescriptor, Callback_FileParser_parse

public interface FileParserPrx
	extends ObjectPrx
{

	public abstract ApplicationDescriptor parse(String s, AdminPrx adminprx)
		throws ParseException;

	public abstract ApplicationDescriptor parse(String s, AdminPrx adminprx, Map map)
		throws ParseException;

	public abstract AsyncResult begin_parse(String s, AdminPrx adminprx);

	public abstract AsyncResult begin_parse(String s, AdminPrx adminprx, Map map);

	public abstract AsyncResult begin_parse(String s, AdminPrx adminprx, Callback callback);

	public abstract AsyncResult begin_parse(String s, AdminPrx adminprx, Map map, Callback callback);

	public abstract AsyncResult begin_parse(String s, AdminPrx adminprx, Callback_FileParser_parse callback_fileparser_parse);

	public abstract AsyncResult begin_parse(String s, AdminPrx adminprx, Map map, Callback_FileParser_parse callback_fileparser_parse);

	public abstract ApplicationDescriptor end_parse(AsyncResult asyncresult)
		throws ParseException;
}
