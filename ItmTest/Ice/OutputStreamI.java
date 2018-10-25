// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputStreamI.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Buffer;
import IceInternal.Util;
import java.io.Serializable;
import java.nio.ByteBuffer;

// Referenced classes of package Ice:
//			MarshalException, OutputStream, Communicator, ObjectPrx, 
//			Object, UserException

public class OutputStreamI
	implements OutputStream
{

	private Communicator _communicator;
	private BasicStream _os;

	public OutputStreamI(Communicator communicator)
	{
		this(communicator, new BasicStream(Util.getInstance(communicator), false, false));
	}

	public OutputStreamI(Communicator communicator, BasicStream os)
	{
		_communicator = communicator;
		_os = os;
		_os.closure(this);
	}

	public Communicator communicator()
	{
		return _communicator;
	}

	public void writeBool(boolean v)
	{
		_os.writeBool(v);
	}

	public void writeBoolSeq(boolean v[])
	{
		_os.writeBoolSeq(v);
	}

	public void writeByte(byte v)
	{
		_os.writeByte(v);
	}

	public void writeByteSeq(byte v[])
	{
		_os.writeByteSeq(v);
	}

	public void writeSerializable(Serializable v)
	{
		_os.writeSerializable(v);
	}

	public void writeShort(short v)
	{
		_os.writeShort(v);
	}

	public void writeShortSeq(short v[])
	{
		_os.writeShortSeq(v);
	}

	public void writeInt(int v)
	{
		_os.writeInt(v);
	}

	public void writeIntSeq(int v[])
	{
		_os.writeIntSeq(v);
	}

	public void writeLong(long v)
	{
		_os.writeLong(v);
	}

	public void writeLongSeq(long v[])
	{
		_os.writeLongSeq(v);
	}

	public void writeFloat(float v)
	{
		_os.writeFloat(v);
	}

	public void writeFloatSeq(float v[])
	{
		_os.writeFloatSeq(v);
	}

	public void writeDouble(double v)
	{
		_os.writeDouble(v);
	}

	public void writeDoubleSeq(double v[])
	{
		_os.writeDoubleSeq(v);
	}

	public void writeString(String v)
	{
		_os.writeString(v);
	}

	public void writeStringSeq(String v[])
	{
		_os.writeStringSeq(v);
	}

	public void writeSize(int sz)
	{
		if (sz < 0)
		{
			throw new MarshalException();
		} else
		{
			_os.writeSize(sz);
			return;
		}
	}

	public void writeProxy(ObjectPrx v)
	{
		_os.writeProxy(v);
	}

	public void writeObject(Object v)
	{
		_os.writeObject(v);
	}

	public void writeTypeId(String id)
	{
		_os.writeTypeId(id);
	}

	public void writeException(UserException v)
	{
		_os.writeUserException(v);
	}

	public void startSlice()
	{
		_os.startWriteSlice();
	}

	public void endSlice()
	{
		_os.endWriteSlice();
	}

	public void startEncapsulation()
	{
		_os.startWriteEncaps();
	}

	public void endEncapsulation()
	{
		_os.endWriteEncapsChecked();
	}

	public void writePendingObjects()
	{
		_os.writePendingObjects();
	}

	public byte[] finished()
	{
		Buffer buf = _os.prepareWrite();
		byte result[] = new byte[buf.b.limit()];
		buf.b.get(result);
		return result;
	}

	public void reset(boolean clearBuffer)
	{
		_os.clear();
		Buffer buf = _os.getBuffer();
		if (clearBuffer)
			buf.clear();
		else
			buf.reset();
		buf.b.position(0);
	}

	public void destroy()
	{
		if (_os != null)
			_os = null;
	}
}
