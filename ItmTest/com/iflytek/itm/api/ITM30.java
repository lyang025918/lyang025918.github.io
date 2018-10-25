// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITM30.java

package com.iflytek.itm.api;

import com.iflytek.itm.util.ITMUtils;
import java.io.*;
import java.util.Map;
import java.util.TreeMap;
import org.apache.log4j.Logger;

// Referenced classes of package com.iflytek.itm.api:
//			ITM, ITMFactory, ITMBuilder

public class ITM30
{
	static class ITM30ParamsInst
	{

		String rule;
		String id;
		String group_id;
		String association_word;
		String association_level;
		String max_top_num;
		String max_sec_top_num;
		String hv_sent_sample_cnt;
		String kt_max_top_num;

		ITM30ParamsInst()
		{
			rule = "";
			id = "";
			group_id = "";
			association_word = "";
			association_level = "1";
			max_top_num = "20";
			max_sec_top_num = "5";
			hv_sent_sample_cnt = "500";
			kt_max_top_num = "50";
		}
	}


	static String idx_root_dir = "c:/itm_idx";
	static long _ref_cnt = 0L;
	static Map session_map = new TreeMap();
	private static final Logger logger = Logger.getLogger(com/iflytek/itm/api/ITM30);

	public ITM30()
	{
	}

	public static int ITMInit(String cfg)
	{
		return readItmCfg(cfg);
	}

	public static int ITMFini()
	{
		return 0;
	}

	public static synchronized String ITMSessionBegin(String param, int ret[])
	{
		ITM30ParamsInst inst = new ITM30ParamsInst();
		String tmp = String.valueOf(_ref_cnt);
		session_map.put(tmp, inst);
		_ref_cnt++;
		ret[0] = 0;
		return tmp;
	}

	public static synchronized int ITMSessionEnd(String sid)
	{
		session_map.remove(sid);
		return 0;
	}

	public static int ITMIndexBuild(String sid, String group_id, String id, String text_content)
	{
		ITM inst = ITMFactory.create();
		String params = (new StringBuilder()).append("sub_index_dir=").append(group_id).toString();
		ITMBuilder.ITMDocument document = new ITMBuilder.ITMDocument();
		ITMBuilder.ITMField idField = new ITMBuilder.ITMField("id", "int", id, null, true);
		document.add(idField);
		ITMBuilder.ITMField contentField = new ITMBuilder.ITMField("content", "string", text_content, "org.wltea.analyzer.lucene.IKAnalyzer", false);
		document.add(contentField);
		String timestamp = ITMUtils.calcTimestamp();
		ITMBuilder.ITMField timeField = new ITMBuilder.ITMField("timestamp", "string", timestamp, null, false);
		document.add(timeField);
		return inst.build(idx_root_dir, params, document);
	}

	public static int ITMGroupMaintain(String sid, String group_id, String action, String action_param)
	{
		ITM inst = ITMFactory.create();
		return inst.maintain(idx_root_dir, action, (new StringBuilder()).append("sub_index_dir=").append(group_id).toString());
	}

	public static int ITMAffixData(String sid, String type, String value)
	{
		return SetParam(sid, type, value);
	}

	public static String ITMMining(String sid, int svc_type, int status[], int ret[])
	{
		ITM30ParamsInst paramsInst = (ITM30ParamsInst)session_map.get(sid);
		if (paramsInst == null)
		{
			logger.error((new StringBuilder()).append("ITMMining | no session named as ").append(sid).toString());
			return "";
		}
		ITM inst = ITMFactory.create();
		StringBuffer buffer = new StringBuffer();
		String params = parse(paramsInst);
		if (svc_type == 1)
		{
			ret[0] = inst.mining(idx_root_dir, "word_association", params, buffer);
			return buffer.toString();
		}
		if (svc_type == 2)
		{
			ret[0] = inst.mining(idx_root_dir, "hot_view", params, buffer);
			return buffer.toString();
		}
		if (svc_type == 4)
		{
			ret[0] = inst.mining(idx_root_dir, "rule", params, buffer);
			return buffer.toString();
		}
		if (svc_type == 8)
		{
			ret[0] = inst.mining(idx_root_dir, "trade", params, buffer);
			return buffer.toString();
		} else
		{
			logger.error((new StringBuilder()).append("ITMMining | not support svc_type, svc_type=").append(svc_type).toString());
			return "";
		}
	}

	public static synchronized int ITMSetParam(String sid, String type, String value)
	{
		return SetParam(sid, type, value);
	}

	public static synchronized String ITMGetParam(String sid, String type)
	{
		return "not implemented";
	}

	static String parse(ITM30ParamsInst inst)
	{
		StringBuffer buffer = new StringBuffer(1024);
		buffer.append("mining_field=content \n").append("id_field=id \n").append("sample_rate=500 \n");
		if (ITMUtils.isValidStr(inst.group_id))
			buffer.append("sub_index_dir_list=").append(inst.group_id).append("\n");
		if (ITMUtils.isValidStr(inst.rule))
			buffer.append("rule=").append(inst.rule).append("\n");
		if (ITMUtils.isValidStr(inst.association_word))
			buffer.append("word_association_word=").append(inst.association_word).append("\n");
		if (ITMUtils.isValidStr(inst.hv_sent_sample_cnt))
			buffer.append("sample_rate=").append(inst.hv_sent_sample_cnt).append("\n");
		if (ITMUtils.isLegalStr(inst.kt_max_top_num))
			buffer.append("trade_top_n=").append(inst.kt_max_top_num).append("\n");
		if (ITMUtils.isValidStr(inst.association_level))
		{
			int level = Integer.valueOf(inst.association_level).intValue();
			if (level == 1)
				buffer.append("word_association_level_top_n=1:").append(inst.max_top_num).append("\n");
			else
				buffer.append("word_association_level_top_n=2:").append(inst.max_top_num).append(";").append(inst.max_sec_top_num).append("\n");
		}
		if (ITMUtils.isValidStr(inst.id))
		{
			buffer.append("id_list=");
			String vec_gid_ids[] = inst.id.split(";");
			for (int i = 0; i < vec_gid_ids.length; i++)
			{
				String gid_ids = vec_gid_ids[i];
				String vec_tmp[] = gid_ids.split(":");
				if (vec_tmp.length < 2)
				{
					logger.error((new StringBuilder()).append("parse | error id, id=").append(inst.id).toString());
					continue;
				}
				String str_group = vec_tmp[0];
				String str_ids = vec_tmp[1];
				String vec_ids[] = str_ids.split(",");
				for (int j = 0; j < vec_ids.length; j++)
					buffer.append(str_group).append(":").append(vec_ids[j]).append(";");

			}

			buffer.append("\n");
		}
		return buffer.toString();
	}

	static int SetParam(String sid, String type, String value)
	{
		ITM30ParamsInst inst;
		inst = (ITM30ParamsInst)session_map.get(sid);
		if (inst == null)
		{
			logger.error((new StringBuilder()).append("SetParam | no session named as ").append(sid).toString());
			return -1;
		}
		if (!type.equals("rule"))
			break MISSING_BLOCK_LABEL_86;
		new StringBuilder();
		inst;
		JVM INSTR dup_x1 ;
		rule;
		append();
		value;
		append();
		";";
		append();
		toString();
		rule;
		break MISSING_BLOCK_LABEL_297;
		if (!type.equals("id"))
			break MISSING_BLOCK_LABEL_128;
		new StringBuilder();
		inst;
		JVM INSTR dup_x1 ;
		id;
		append();
		value;
		append();
		";";
		append();
		toString();
		id;
		break MISSING_BLOCK_LABEL_297;
		if (!type.equals("group_id"))
			break MISSING_BLOCK_LABEL_170;
		new StringBuilder();
		inst;
		JVM INSTR dup_x1 ;
		group_id;
		append();
		value;
		append();
		";";
		append();
		toString();
		group_id;
		break MISSING_BLOCK_LABEL_297;
		if (type.equals("association_word"))
			inst.association_word = value;
		else
		if (type.equals("association_level"))
			inst.association_level = value;
		else
		if (type.equals("max_top_num"))
			inst.max_top_num = value;
		else
		if (type.equals("max_sec_top_num"))
			inst.max_sec_top_num = value;
		else
		if (type.equals("hv_sent_sample_cnt"))
			inst.hv_sent_sample_cnt = value;
		else
		if (type.equals("kt_max_top_num"))
			inst.kt_max_top_num = value;
		else
			logger.error((new StringBuilder()).append("ITMSetParam | not support type=").append(type).toString());
		return 0;
	}

	private static int readItmCfg(String cfg)
	{
		String line = "";
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("itm.cfg"), "gbk"));
			do
			{
				line = br.readLine();
				if (null == line)
					break;
				line = line.trim();
			} while (line.isEmpty() || !line.contains("idx_root"));
		}
		catch (Exception e)
		{
			logger.error((new StringBuilder()).append("ITMInit | exception, errmsg=").append(e.getMessage()).toString());
		}
		String kv[] = line.split("=");
		if (kv.length == 2)
		{
			String key = kv[0].trim();
			String value = kv[1].trim();
			if (key.equals("idx_root") && ITMUtils.isValidStr(value))
				idx_root_dir = value;
		}
		return 0;
	}

}
