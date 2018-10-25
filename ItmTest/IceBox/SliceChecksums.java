// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SliceChecksums.java

package IceBox;

import java.util.*;

public class SliceChecksums
{

	public static final Map checksums;

	public SliceChecksums()
	{
	}

	static 
	{
		Map map = new HashMap();
		map.put("::IceBox::AlreadyStartedException", "d5b097af3221b37482d5f175502abf62");
		map.put("::IceBox::AlreadyStoppedException", "281d493a84d674b3a4335d6afc2c16");
		map.put("::IceBox::NoSuchServiceException", "5957f1c582d9aebad557cbdb7820d4");
		map.put("::IceBox::ServiceManager", "df3a42670c3ce4ef67d6125a5d04d4c");
		map.put("::IceBox::ServiceObserver", "f657781cda7438532a6c33e95988479c");
		checksums = Collections.unmodifiableMap(map);
	}
}
