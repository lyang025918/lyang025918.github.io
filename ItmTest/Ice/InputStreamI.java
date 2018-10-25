// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputStreamI.java

package Ice;

import IceInternal.BasicStream;
import IceInternal.Buffer;
import IceInternal.Util;
import java.io.Serializable;
import java.nio.ByteBuffer;

// Referenced classes of package Ice:
//			InputStream, UserException, Communicator, ObjectPrx, 
//			ReadObjectCallback, Object

public class InputStreamI
	implements InputStream
{
	private static class Patcher
		implements IceInternal.Patcher
	{

		ReadObjectCallback _cb;

		public void patch(Object v)
		{
			_cb.invoke(v);
		}

		public String type()
		{
			return "unknown";
		}

		Patcher(ReadObjectCallback cb)
		{
			_cb = cb;
		}
	}


	private Communicator _communicator;
	private BasicStream _is;

	public InputStreamI(Communicator communicator, byte data[])
	{
		_communicator = communicator;
		_is = new BasicStream(Util.getInstance(communicator), false, false);
		_is.closure(this);
		_is.resize(data.length, true);
		Buffer buf = _is.getBuffer();
		buf.b.position(0);
		buf.b.put(data);
		buf.b.position(0);
	}

	public Communicator communicator()
	{
		return _communicator;
	}

	public void sliceObjects(boolean slice)
	{
		_is.sliceObjects(slice);
	}

	public boolean readBool()
	{
		return _is.readBool();
	}

	public boolean[] readBoolSeq()
	{
		return _is.readBoolSeq();
	}

	public byte readByte()
	{
		return _is.readByte();
	}

	public byte[] readByteSeq()
	{
		return _is.readByteSeq();
	}

	public Serializable readSerializable()
	{
		return _is.readSerializable();
	}

	public short readShort()
	{
		return _is.readShort();
	}

	public short[] readShortSeq()
	{
		return _is.readShortSeq();
	}

	public int readInt()
	{
		return _is.readInt();
	}

	public int[] readIntSeq()
	{
		return _is.readIntSeq();
	}

	public long readLong()
	{
		return _is.readLong();
	}

	public long[] readLongSeq()
	{
		return _is.readLongSeq();
	}

	public float readFloat()
	{
		return _is.readFloat();
	}

	public float[] readFloatSeq()
	{
		return _is.readFloatSeq();
	}

	public double readDouble()
	{
		return _is.readDouble();
	}

	public double[] readDoubleSeq()
	{
		return _is.readDoubleSeq();
	}

	public String readString()
	{
		return _is.readString();
	}

	public String[] readStringSeq()
	{
		return _is.readStringSeq();
	}

	public int readSize()
	{
		return _is.readSize();
	}

	public int readAndCheckSeqSize(int minWire)
	{
		return _is.readAndCheckSeqSize(minWire);
	}

	public ObjectPrx readProxy()
	{
		return _is.readProxy();
	}

	public void readObject(ReadObjectCallback cb)
	{
		_is.readObject(new Patcher(cb));
	}

	public String readTypeId()
	{
		return _is.readTypeId();
	}

	public void throwException()
		throws UserException
	{
		_is.throwException();
	}

	public void startSlice()
	{
		_is.startReadSlice();
	}

	public void endSlice()
	{
		_is.endReadSlice();
	}

	public void skipSlice()
	{
		_is.skipSlice();
	}

	public void startEncapsulation()
	{
		_is.startReadEncaps();
	}

	public void skipEncapsulation()
	{
		_is.skipEncaps();
	}

	public void endEncapsulation()
	{
		_is.endReadEncapsChecked();
	}

	public void readPendingObjects()
	{
		_is.readPendingObjects();
	}

	public void rewind()
	{
		_is.clear();
		_is.getBuffer().b.position(0);
	}

	public void destroy()
	{
		if (_is != null)
			_is = null;
	}
}
