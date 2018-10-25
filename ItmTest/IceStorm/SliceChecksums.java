// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SliceChecksums.java

package IceStorm;

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
		map.put("::IceStorm::AlreadySubscribed", "5a82e77b38f02f3118c536f9446a889e");
		map.put("::IceStorm::BadQoS", "44f2de592dd62e3f7f4ffdf043692d");
		map.put("::IceStorm::LinkExists", "e11768febd56a8813729ce69be6c4c2");
		map.put("::IceStorm::LinkInfo", "d0e073e5e0925ec95656f71d572e2e13");
		map.put("::IceStorm::LinkInfoSeq", "a8921e43838692bbe6ca63f3dcf9b6");
		map.put("::IceStorm::NoSuchLink", "fd8f652776796bffca2df1a3baf455a3");
		map.put("::IceStorm::NoSuchTopic", "7a9479a5c39cdd32335d722bbc971176");
		map.put("::IceStorm::QoS", "3e27cb32bc95cca7b013efbf5c254b35");
		map.put("::IceStorm::Topic", "6f5a475ba16151d0414ffb84d3dad3");
		map.put("::IceStorm::TopicDict", "fff078a98be068c52d9e1d7d8f6df2a");
		map.put("::IceStorm::TopicExists", "38e6913833539b8d616d114d4e7b28d");
		map.put("::IceStorm::TopicManager", "ffc1baf19222891f8b432be6551fed5");
		checksums = Collections.unmodifiableMap(map);
	}
}
