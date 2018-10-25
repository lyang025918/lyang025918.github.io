// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMUtils.java

package com.iflytek.itm.util;

import com.iflytek.itm.mining.ITMRuleInfo;
import java.io.InputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import mylib.file.NormalizePath;

// Referenced classes of package com.iflytek.itm.util:
//			ITMConstants, ITMParamParser

public class ITMUtils
{

	public ITMUtils()
	{
	}

	public static InputStream getResourceFile(String name)
	{
		InputStream file = com/iflytek/itm/util/ITMUtils.getResourceAsStream((new StringBuilder()).append("/resource/").append(name).toString());
		return file;
	}

	public static String getSubIndexFullPath(String indexRoot, String subName, boolean isCreate)
	{
		String fullIndexPath = (new StringBuilder()).append(NormalizePath.doNorm(indexRoot, ITMConstants.ITM_PATH_SPLIT, isCreate)).append(ITMConstants.ITM_USR_DIR).append(ITMConstants.ITM_PATH_SPLIT).append(subName).toString();
		fullIndexPath = NormalizePath.doNorm(fullIndexPath, ITMConstants.ITM_PATH_SPLIT, isCreate);
		return fullIndexPath;
	}

	public static String getFinalQueryString(ITMParamParser itmParamParser, boolean ignoreRule)
	{
		StringBuffer buffer = new StringBuffer(1024);
		String query1 = getIdListQuery(itmParamParser.subIndexDirList, itmParamParser.id_field, itmParamParser.idList);
		if (isValidStr(query1))
			buffer.append("+(").append(query1).append(")");
		if (!ignoreRule)
		{
			for (int i = 0; i < itmParamParser.rules.size(); i++)
				buffer.append("+(").append(ITMConstants.ITM_RULE_INNER_FIELD_PREFIX).append(itmParamParser.mining_field).append(":(").append(((ITMRuleInfo)itmParamParser.rules.get(i)).syntax).append("))");

		}
		if (isValidStr(itmParamParser.mining_query))
			buffer.append("+(").append(itmParamParser.mining_query).append(")");
		return buffer.toString();
	}

	public static String getFinalQueryString(ITMParamParser itmParamParser)
	{
		return getFinalQueryString(itmParamParser, false);
	}

	public static void deleteEndChar(StringBuffer buffer, char c)
	{
		if (buffer.length() > 0 && buffer.charAt(buffer.length() - 1) == c)
			buffer.deleteCharAt(buffer.length() - 1);
	}

	public static String getIdListQuery(String dirList[], String idFieldName, String idList[])
	{
		StringBuffer buffer = new StringBuffer(1024);
		Set dirs = new HashSet();
		if (dirList != null)
		{
			for (int i = 0; i < dirList.length; i++)
				dirs.add(dirList[i]);

		}
		if (idList != null)
		{
			for (int i = 0; i < idList.length; i++)
			{
				String strId = idList[i];
				String tmp[] = strId.split(":");
				if (!dirs.contains(tmp[0].trim()))
					buffer.append(" (").append(idFieldName).append(":").append(tmp[1].trim()).append(") ");
			}

		}
		for (Iterator it = dirs.iterator(); it.hasNext(); buffer.append(" (").append(ITMConstants.ITM_SUB_DIR_NAME_INNER_FIELD).append(":\"").append((String)it.next()).append("\") "));
		return buffer.toString();
	}

	public static void addIdListGroups(Set dst, String idList[])
	{
		if (idList == null || idList.length == 0)
			return;
		for (int i = 0; i < idList.length; i++)
		{
			String strId = idList[i];
			String tmp[] = strId.split(":");
			dst.add(tmp[0].trim());
		}

	}

	public static void addSubDirList(Set dst, String src[])
	{
		if (src == null || src.length == 0)
			return;
		for (int i = 0; i < src.length; i++)
			dst.add(src[i]);

	}

	public static String[] toArray(Set dirs)
	{
		String listDirs[] = new String[dirs.size()];
		Iterator it = dirs.iterator();
		for (int i = 0; it.hasNext(); i++)
			listDirs[i] = (String)it.next();

		return listDirs;
	}

	public static boolean isValidStr(String str)
	{
		return str != null && !str.isEmpty();
	}

	public static boolean isLegalStr(String str)
	{
		return !str.contains("=") && !str.contains("\\") && !str.contains("/");
	}

	public static boolean isValidInt(int dst, int min, int max)
	{
		return dst >= min && dst <= max;
	}

	public static boolean isValidDir(String dir)
	{
		return !dir.contains("/") && !dir.contains("\\") && !dir.contains(":") && !dir.contains("*") && !dir.contains("?") && !dir.contains("\"") && !dir.contains("<") && !dir.contains(">") && !dir.contains("|") && !dir.contains("=") && dir.length() <= 255;
	}

	public static boolean isValidWord(String word)
	{
		String regStr = "[\\u4e00-\\u9fa5a-zA-Z0-9]+";
		Pattern pattern = Pattern.compile(regStr);
		Matcher matcher = pattern.matcher(word);
		return matcher.matches();
	}

	public static String delPunctuation(String sen)
	{
		String str = sen;
		str = str.replaceAll("(?i)[^\\u4e00-\\u9fa5a-zA-Z0-9]", "");
		return str;
	}

	public static String calcTimestamp()
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	public static void main(String args[])
	{
		String content = "字母短信后可以您打开。界面您手机上。因为您手机上有";
		Pattern pattern = Pattern.compile(ITMRuleInfo.toRegexStr("短信#手机", 41, false));
		Matcher matcher = pattern.matcher(content);
		System.out.println((new StringBuilder()).append("cnt=").append(matcher.groupCount()).toString());
		for (; matcher.find(); matcher.reset())
		{
			int bos = matcher.start();
			int eos = matcher.end();
			System.out.println((new StringBuilder()).append("bos=").append(bos).append(", eos=").append(eos).append(", group=").append(matcher.group()).toString());
		}

	}
}
