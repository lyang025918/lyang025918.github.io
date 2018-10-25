// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMRuleInfo.java

package com.iflytek.itm.mining;

import com.iflytek.itm.api.ITMErrors;
import com.iflytek.itm.mining.rule.RuleParser;
import java.io.PrintStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

public class ITMRuleInfo
{

	public int id;
	public String ruleName;
	public String rule;
	public String words[];
	public String syntax;
	public int span;
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/mining/ITMRuleInfo);

	public ITMRuleInfo()
	{
		span = 10;
	}

	public int rule(int id, String ruleStr, int span)
	{
		int ret = 0;
		ruleStr = normalize(ruleStr);
		String tmp[] = ruleStr.split(":");
		if (tmp.length != 2)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_RULE_SYNTAX.code();
			logger.error((new StringBuilder()).append("ITMRuleInfo | checkRule Error: ruleStr以:分隔必须为2, ruleStr=").append(ruleStr).append(", errcode=").append(ret).toString());
			return ret;
		}
		this.id = id;
		this.span = span;
		ruleName = tmp[0].trim();
		rule = tmp[1].trim();
		ret = checkRule(rule);
		if (ret != 0)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_RULE_SYNTAX.code();
			logger.error((new StringBuilder()).append("ITMRuleInfo | rule Error: 规则有误, ruleStr=").append(ruleStr).append(", span=").append(span).append(", errcode=").append(ret).toString());
		} else
		{
			syntax = toQuerySyntax(rule, span);
			words = toWordsList(rule, span);
		}
		return ret;
	}

	private String normalize(String ruleStr)
	{
		String tmp = ruleStr.toLowerCase();
		tmp = tmp.trim();
		tmp = tmp.replaceAll(" ", "");
		return tmp;
	}

	public static void main(String args[])
	{
		String rule = "(短信#送账单)";
		rule = "( 感谢 # 来电 ) | ( 来+吧 )";
		System.out.println((new StringBuilder()).append("rule=").append(rule).toString());
		int ret = checkRule(rule);
		System.out.println((new StringBuilder()).append("checkRule ret = ").append(ret).toString());
		String syntax = "";
		syntax = toQuerySyntax(rule, 10);
		String words[] = toWordsList(rule, 10);
		String tmp = toRegexStr("找#领导", 10, false);
		String word = "上网#套餐";
		String content = "如果硬要开包月套餐的情况下CMWAP上网功能打开了那上网的话";
		String regexStr = toRegexStr(word, 10, true);
		Pattern pattern = Pattern.compile(regexStr);
		int bos;
		int eos;
		for (Matcher matcher = pattern.matcher(content); matcher.find(); System.out.println((new StringBuilder()).append("word=").append(word).append(", bos=").append(bos).append(",eos=").append(eos).toString()))
		{
			bos = matcher.start();
			eos = matcher.end();
		}

		System.out.println((new StringBuilder()).append("rule = ").append(rule).toString());
		System.out.println((new StringBuilder()).append("syntax = ").append(syntax).toString());
	}

	public static int checkRule(String rule)
	{
		int ret = 0;
		try
		{
			RuleParser.parse(rule);
		}
		catch (Exception e)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_RULE_SYNTAX.code();
			logger.error((new StringBuilder()).append("ITMRuleInfo | checkRule Exception: 规则不合法，rule=").append(rule).append(", msg=").append(e.getMessage()).append(", errcode=").append(ret).toString());
		}
		catch (Error error)
		{
			ret = ITMErrors.ITM_ERROR_INVALID_RULE_SYNTAX.code();
			logger.error((new StringBuilder()).append("ITMRuleInfo | checkRule Error: 规则不合法，rule=").append(rule).append(", msg=").append(error.getMessage()).append(", errcode=").append(ret).toString());
		}
		return ret;
	}

	public static String[] toWordsList(String str, int span)
	{
		String tmp[] = str.split("\\(|\\)|\\||!|&");
		return tmp;
	}

	public static String toRegexStr(String str, int span, boolean isReverse)
	{
		if (str == null || str.isEmpty())
			return "";
		int pound_pos = str.indexOf("#");
		int plus_pos = str.indexOf("+");
		int pos = pound_pos;
		if (-1 == pos)
			pos = plus_pos;
		if (pos > 0)
		{
			String first = str.substring(0, pos);
			String second = str.substring(pos + 1);
			if (isReverse)
				return (new StringBuilder()).append(second).append(".{0,").append(span).append("}").append(first).toString();
			else
				return (new StringBuilder()).append(first).append(".{0,").append(span).append("}").append(second).toString();
		} else
		{
			return str;
		}
	}

	public static String toQuerySyntax(String str, int span)
	{
		StringBuffer syntax = new StringBuffer(1024);
		syntax.append("(");
		for (int i = 0; i < str.length(); i++)
		{
			char a = str.charAt(i);
			if (a == '|')
			{
				syntax.append(" OR ");
				continue;
			}
			if (a == '&')
			{
				syntax.append(" AND ");
				continue;
			}
			if (a == '!')
			{
				syntax.append(" *:* NOT ");
				continue;
			}
			if ((a == '#') | (a == '+'))
				i = fillSpanSyntax(syntax, str, i, span, a);
			else
				syntax.append(a);
		}

		syntax.append(")");
		return syntax.toString();
	}

	private static int fillSpanSyntax(StringBuffer syntax, String str, int i, int span, char token)
	{
		int bos = syntax.lastIndexOf("(");
		String firstWord = syntax.substring(bos + 1);
		int eos = str.indexOf(")", i);
		String secondWord = str.substring(i + 1, eos);
		String now = syntax.substring(0, bos);
		syntax.setLength(0);
		syntax.append(now).append((new StringBuilder()).append(firstWord).append(token).append(span).append(token).append(secondWord).toString());
		i = eos;
		return i;
	}

}
