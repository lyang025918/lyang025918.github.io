// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SliceChecksums.java

package Glacier2;

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
		map.put("::Glacier2::Admin", "a2df2d4165d639f36f3adadca59f154b");
		map.put("::Glacier2::CannotCreateSessionException", "f3cf2057ea305ed04671164dfaeb6d95");
		map.put("::Glacier2::IdentitySet", "622e43adfd1f535abaee1b089583847");
		map.put("::Glacier2::PermissionDeniedException", "27def8d4569ab203b629b9162d530ba");
		map.put("::Glacier2::PermissionsVerifier", "224cf229a378614459a5959f346c50");
		map.put("::Glacier2::Router", "5ed4e7041715e7ed256c31ab1d01da7");
		map.put("::Glacier2::SSLInfo", "ca63bc6d361a48471c4d16ea29818e5");
		map.put("::Glacier2::SSLPermissionsVerifier", "b796e7d91f35d3acbb5be98291aa9be4");
		map.put("::Glacier2::SSLSessionManager", "4eb77cf437452f5296bf24dda4967d");
		map.put("::Glacier2::Session", "8e47590dc18dd2a2e2e7749c941fc7");
		map.put("::Glacier2::SessionControl", "83a11c547492ddc72db70659938222");
		map.put("::Glacier2::SessionManager", "f3c67f2f29415754c0f1ccc1ab5558e");
		map.put("::Glacier2::SessionNotExistException", "9b3392dc48a63f86d96c13662972328");
		map.put("::Glacier2::StringSet", "1b46953cdce5ef8b6fe92056adf3fda0");
		checksums = Collections.unmodifiableMap(map);
	}
}
