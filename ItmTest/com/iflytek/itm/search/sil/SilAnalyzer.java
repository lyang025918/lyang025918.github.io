// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SilAnalyzer.java

package com.iflytek.itm.search.sil;

import java.io.*;
import java.util.ArrayList;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.*;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PayloadAttribute;
import org.apache.lucene.util.BytesRef;

// Referenced classes of package com.iflytek.itm.search.sil:
//			ItemBufferedOutput, ItemBufferedInput

public class SilAnalyzer extends Analyzer
{
	static class SilTokenizer extends Tokenizer
	{

		private CharTermAttribute termAttr;
		PayloadAttribute payloadAttr;
		private ArrayList tokens;
		private int nowToken;

		private ArrayList parse(String fieldName, Reader input)
		{
			StringBuffer buffer = new StringBuffer(1024);
			ArrayList silItems = new ArrayList();
			try
			{
				for (int a = -1; -1 != (a = input.read());)
					buffer.append((char)a);

				String strItems[] = buffer.toString().split(" ");
				for (int i = 0; i < strItems.length; i++)
				{
					SilItem item = new SilItem();
					int bos = strItems[i].indexOf("|");
					item.sil = strItems[i].substring(0, bos);
					int bos2 = strItems[i].indexOf("-");
					item.begin = Integer.valueOf(strItems[i].substring(bos + 1, bos2)).intValue();
					item.end = Integer.valueOf(strItems[i].substring(bos2 + 1)).intValue();
					silItems.add(item);
				}

			}
			catch (Exception e)
			{
				SilAnalyzer.logger.error((new StringBuilder()).append("SilTokenizer | parse error, errmsg=").append(e.getMessage()).append(", field=").append(fieldName).append(", value=").append(buffer.toString()).toString());
			}
			buffer = null;
			return silItems;
		}

		public final boolean incrementToken()
			throws IOException
		{
			boolean beret = true;
			if (nowToken >= tokens.size())
			{
				beret = false;
			} else
			{
				SilItem token = (SilItem)tokens.get(nowToken);
				clearAttributes();
				termAttr.setEmpty().append(token.sil);
				payloadAttr.setPayload(new BytesRef(token.encode()));
				nowToken++;
			}
			return beret;
		}

		public SilTokenizer(String fieldName, Reader input)
		{
			super(input);
			tokens = null;
			nowToken = 0;
			termAttr = (CharTermAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
			payloadAttr = (PayloadAttribute)addAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);
			tokens = parse(fieldName, input);
		}
	}

	static class SilItem
	{

		private String sil;
		private int begin;
		private int end;
		private byte chan;

		public byte[] encode()
		{
			ItemBufferedOutput buffer = new ItemBufferedOutput();
			try
			{
				buffer.writeVInt(begin);
				buffer.writeVInt(end - begin);
			}
			catch (Exception e)
			{
				SilAnalyzer.logger.error((new StringBuilder()).append("SilItem.encode | Error=").append(e.getMessage()).toString());
			}
			return buffer.data();
		}

		public void decode(String sil, byte bytes[])
		{
			this.sil = sil;
			ItemBufferedInput input = new ItemBufferedInput(bytes);
			try
			{
				begin = input.readVInt();
				end = input.readVInt() + begin;
			}
			catch (Exception e)
			{
				SilAnalyzer.logger.error((new StringBuilder()).append("SilItem.decode | Error=").append(e.getMessage()).toString());
			}
		}







		SilItem()
		{
		}
	}


	private static final Logger logger = Logger.getLogger(com/iflytek/itm/search/sil/SilAnalyzer);

	public SilAnalyzer()
	{
	}

	protected org.apache.lucene.analysis.Analyzer.TokenStreamComponents createComponents(String fieldName, Reader reader)
	{
		return new org.apache.lucene.analysis.Analyzer.TokenStreamComponents(new SilTokenizer(fieldName, reader));
	}

	public static void main(String args[])
		throws IOException
	{
		String field = "test";
		String fieldValue = "0000001000|0-2000 0100020000|2000-8000";
		Analyzer analyzer = new SilAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream(field, new StringReader(fieldValue));
		CharTermAttribute termTextAttr = (CharTermAttribute)tokenStream.getAttribute(org/apache/lucene/analysis/tokenattributes/CharTermAttribute);
		PayloadAttribute payloadAttr = (PayloadAttribute)tokenStream.getAttribute(org/apache/lucene/analysis/tokenattributes/PayloadAttribute);
		SilItem item;
		for (; tokenStream.incrementToken(); System.out.println((new StringBuilder()).append("sil=").append(item.sil).append(", begin=").append(item.begin).append(", end=").append(item.end).toString()))
		{
			String str = termTextAttr.toString();
			BytesRef bytes = payloadAttr.getPayload();
			item = new SilItem();
			item.decode(str, bytes.bytes);
		}

	}


}
