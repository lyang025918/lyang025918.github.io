// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   TraceUtil.java

package IceInternal;

import Ice.*;
import IceUtilInternal.StringUtil;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

// Referenced classes of package IceInternal:
//			TraceLevels, BasicStream, Instance

public final class TraceUtil
{

	private static Set slicingIds = new HashSet();
	static final boolean $assertionsDisabled = !IceInternal/TraceUtil.desiredAssertionStatus();

	public TraceUtil()
	{
	}

	public static void traceSend(BasicStream str, Logger logger, TraceLevels tl)
	{
		if (tl.protocol >= 1)
		{
			int p = str.pos();
			str.pos(0);
			StringWriter s = new StringWriter();
			byte type = printMessage(s, str);
			logger.trace(tl.protocolCat, (new StringBuilder()).append("sending ").append(getMessageTypeAsString(type)).append(" ").append(s.toString()).toString());
			str.pos(p);
		}
	}

	public static void traceRecv(BasicStream str, Logger logger, TraceLevels tl)
	{
		if (tl.protocol >= 1)
		{
			int p = str.pos();
			str.pos(0);
			StringWriter s = new StringWriter();
			byte type = printMessage(s, str);
			logger.trace(tl.protocolCat, (new StringBuilder()).append("received ").append(getMessageTypeAsString(type)).append(" ").append(s.toString()).toString());
			str.pos(p);
		}
	}

	public static void trace(String heading, BasicStream str, Logger logger, TraceLevels tl)
	{
		if (tl.protocol >= 1)
		{
			int p = str.pos();
			str.pos(0);
			StringWriter s = new StringWriter();
			s.write(heading);
			printMessage(s, str);
			logger.trace(tl.protocolCat, s.toString());
			str.pos(p);
		}
	}

	static synchronized void traceSlicing(String kind, String typeId, String slicingCat, Logger logger)
	{
		if (slicingIds.add(typeId))
		{
			StringWriter s = new StringWriter();
			s.write((new StringBuilder()).append("unknown ").append(kind).append(" type `").append(typeId).append("'").toString());
			logger.trace(slicingCat, s.toString());
		}
	}

	public static void dumpStream(BasicStream stream)
	{
		int pos = stream.pos();
		stream.pos(0);
		byte data[] = stream.readBlob(stream.size());
		dumpOctets(data);
		stream.pos(pos);
	}

	public static void dumpOctets(byte data[])
	{
		int inc = 8;
		for (int i = 0; i < data.length; i += 8)
		{
			for (int j = i; j - i < 8; j++)
				if (j < data.length)
				{
					int n = data[j];
					if (n < 0)
						n += 256;
					String s;
					if (n < 10)
						s = (new StringBuilder()).append("  ").append(n).toString();
					else
					if (n < 100)
						s = (new StringBuilder()).append(" ").append(n).toString();
					else
						s = (new StringBuilder()).append("").append(n).toString();
					System.out.print((new StringBuilder()).append(s).append(" ").toString());
				} else
				{
					System.out.print("    ");
				}

			System.out.print('"');
			for (int j = i; j < data.length && j - i < 8; j++)
				if (data[j] >= 32 && data[j] < 127)
					System.out.print((char)data[j]);
				else
					System.out.print('.');

			System.out.println('"');
		}

	}

	private static void printIdentityFacetOperation(Writer out, BasicStream stream)
	{
		try
		{
			Identity identity = new Identity();
			identity.__read(stream);
			out.write((new StringBuilder()).append("\nidentity = ").append(stream.instance().identityToString(identity)).toString());
			String facet[] = stream.readStringSeq();
			out.write("\nfacet = ");
			if (facet.length > 0)
				out.write(StringUtil.escapeString(facet[0], ""));
			String operation = stream.readString();
			out.write((new StringBuilder()).append("\noperation = ").append(operation).toString());
		}
		catch (IOException ex)
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
		}
	}

	private static void printRequest(StringWriter s, BasicStream str)
	{
		int requestId = str.readInt();
		s.write((new StringBuilder()).append("\nrequest id = ").append(requestId).toString());
		if (requestId == 0)
			s.write(" (oneway)");
		printRequestHeader(s, str);
	}

	private static void printBatchRequest(StringWriter s, BasicStream str)
	{
		int batchRequestNum = str.readInt();
		s.write((new StringBuilder()).append("\nnumber of requests = ").append(batchRequestNum).toString());
		for (int i = 0; i < batchRequestNum; i++)
		{
			s.write((new StringBuilder()).append("\nrequest #").append(i).append(':').toString());
			printRequestHeader(s, str);
			str.skipEncaps();
		}

	}

	private static void printReply(StringWriter s, BasicStream str)
	{
		int requestId = str.readInt();
		s.write((new StringBuilder()).append("\nrequest id = ").append(requestId).toString());
		byte replyStatus = str.readByte();
		s.write((new StringBuilder()).append("\nreply status = ").append(replyStatus).append(' ').toString());
		switch (replyStatus)
		{
		case 0: // '\0'
			s.write("(ok)");
			break;

		case 1: // '\001'
			s.write("(user exception)");
			break;

		case 2: // '\002'
		case 3: // '\003'
		case 4: // '\004'
			switch (replyStatus)
			{
			case 2: // '\002'
				s.write("(object not exist)");
				break;

			case 3: // '\003'
				s.write("(facet not exist)");
				break;

			case 4: // '\004'
				s.write("(operation not exist)");
				break;

			default:
				if (!$assertionsDisabled)
					throw new AssertionError();
				break;
			}
			printIdentityFacetOperation(s, str);
			break;

		case 5: // '\005'
		case 6: // '\006'
		case 7: // '\007'
			switch (replyStatus)
			{
			case 7: // '\007'
				s.write("(unknown exception)");
				break;

			case 5: // '\005'
				s.write("(unknown local exception)");
				break;

			case 6: // '\006'
				s.write("(unknown user exception)");
				break;

			default:
				if (!$assertionsDisabled)
					throw new AssertionError();
				break;
			}
			String unknown = str.readString();
			s.write((new StringBuilder()).append("\nunknown = ").append(unknown).toString());
			break;

		default:
			s.write("(unknown)");
			break;
		}
	}

	private static void printRequestHeader(Writer out, BasicStream stream)
	{
		printIdentityFacetOperation(out, stream);
		try
		{
			byte mode = stream.readByte();
			out.write((new StringBuilder()).append("\nmode = ").append(mode).append(' ').toString());
			static class 1
			{

				static final int $SwitchMap$Ice$OperationMode[];

				static 
				{
					$SwitchMap$Ice$OperationMode = new int[OperationMode.values().length];
					try
					{
						$SwitchMap$Ice$OperationMode[OperationMode.Normal.ordinal()] = 1;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$Ice$OperationMode[OperationMode.Nonmutating.ordinal()] = 2;
					}
					catch (NoSuchFieldError ex) { }
					try
					{
						$SwitchMap$Ice$OperationMode[OperationMode.Idempotent.ordinal()] = 3;
					}
					catch (NoSuchFieldError ex) { }
				}
			}

			switch (1..SwitchMap.Ice.OperationMode[OperationMode.values()[mode].ordinal()])
			{
			case 1: // '\001'
				out.write("(normal)");
				break;

			case 2: // '\002'
				out.write("(nonmutating)");
				break;

			case 3: // '\003'
				out.write("(idempotent)");
				break;

			default:
				out.write("(unknown)");
				break;
			}
			int sz = stream.readSize();
			out.write("\ncontext = ");
			do
			{
				if (sz-- <= 0)
					break;
				String key = stream.readString();
				String value = stream.readString();
				out.write((new StringBuilder()).append(key).append('/').append(value).toString());
				if (sz > 0)
					out.write(", ");
			} while (true);
		}
		catch (IOException ex)
		{
			if (!$assertionsDisabled)
				throw new AssertionError();
		}
	}

	private static byte printHeader(Writer out, BasicStream stream)
	{
		byte type;
		stream.readByte();
		stream.readByte();
		stream.readByte();
		stream.readByte();
		stream.readByte();
		stream.readByte();
		stream.readByte();
		stream.readByte();
		type = stream.readByte();
		out.write((new StringBuilder()).append("\nmessage type = ").append(type).append(" (").append(getMessageTypeAsString(type)).append(')').toString());
		byte compress = stream.readByte();
		out.write((new StringBuilder()).append("\ncompression status = ").append(compress).append(' ').toString());
		switch (compress)
		{
		case 0: // '\0'
			out.write("(not compressed; do not compress response, if any)");
			break;

		case 1: // '\001'
			out.write("(not compressed; compress response, if any)");
			break;

		case 2: // '\002'
			out.write("(compressed; compress response, if any)");
			break;

		default:
			out.write("(unknown)");
			break;
		}
		int size = stream.readInt();
		out.write((new StringBuilder()).append("\nmessage size = ").append(size).toString());
		return type;
		IOException ex;
		ex;
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return 0;
	}

	private static byte printMessage(StringWriter s, BasicStream str)
	{
		byte type = printHeader(s, str);
		switch (type)
		{
		case 0: // '\0'
			printRequest(s, str);
			break;

		case 1: // '\001'
			printBatchRequest(s, str);
			break;

		case 2: // '\002'
			printReply(s, str);
			break;
		}
		return type;
	}

	private static String getMessageTypeAsString(byte type)
	{
		switch (type)
		{
		case 0: // '\0'
			return "request";

		case 1: // '\001'
			return "batch request";

		case 2: // '\002'
			return "reply";

		case 4: // '\004'
			return "close connection";

		case 3: // '\003'
			return "validate connection";
		}
		return "unknown";
	}

}
