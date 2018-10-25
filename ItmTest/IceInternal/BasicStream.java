// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BasicStream.java

package IceInternal;

import Ice.AlreadyRegisteredException;
import Ice.CompressionException;
import Ice.EncapsulationException;
import Ice.IllegalMessageSizeException;
import Ice.InitializationData;
import Ice.Logger;
import Ice.MarshalException;
import Ice.NoObjectFactoryException;
import Ice.Object;
import Ice.ObjectFactory;
import Ice.ObjectImpl;
import Ice.ObjectPrx;
import Ice.Properties;
import Ice.SyscallException;
import Ice.UnmarshalOutOfBoundsException;
import Ice.UnsupportedEncodingException;
import Ice.UserException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

// Referenced classes of package IceInternal:
//			Buffer, OutputStreamWrapper, InputStreamWrapper, ObjectInputStream, 
//			Patcher, UserExceptionFactory, Instance, Ex, 
//			ProxyFactory, ObjectFactoryManager, TraceLevels, TraceUtil, 
//			Util

public class BasicStream
{
	private static final class WriteEncaps
	{

		int start;
		int writeIndex;
		IdentityHashMap toBeMarshaledMap;
		IdentityHashMap marshaledMap;
		int typeIdIndex;
		TreeMap typeIdMap;
		WriteEncaps next;

		void reset()
		{
			if (toBeMarshaledMap != null)
			{
				writeIndex = 0;
				toBeMarshaledMap.clear();
				marshaledMap.clear();
				typeIdIndex = 0;
				typeIdMap.clear();
			}
		}

		private WriteEncaps()
		{
		}

	}

	private static final class ReadEncaps
	{

		int start;
		int sz;
		TreeMap patchMap;
		TreeMap unmarshaledMap;
		int typeIdIndex;
		TreeMap typeIdMap;
		ReadEncaps next;

		void reset()
		{
			if (patchMap != null)
			{
				patchMap.clear();
				unmarshaledMap.clear();
				typeIdIndex = 0;
				typeIdMap.clear();
			}
		}

		private ReadEncaps()
		{
		}

	}

	private static final class DynamicUserExceptionFactory
		implements UserExceptionFactory
	{

		private Class _class;

		public void createAndThrow()
			throws UserException
		{
			try
			{
				throw (UserException)_class.newInstance();
			}
			catch (UserException ex)
			{
				throw ex;
			}
			catch (Exception ex)
			{
				throw new SyscallException(ex);
			}
		}

		public void destroy()
		{
		}

		DynamicUserExceptionFactory(Class c)
		{
			_class = c;
		}
	}

	private static final class DynamicObjectFactory
		implements ObjectFactory
	{

		private Class _class;

		public Ice.Object create(String type)
		{
			return (Ice.Object)_class.newInstance();
			Exception ex;
			ex;
			throw new SyscallException(ex);
		}

		public void destroy()
		{
		}

		DynamicObjectFactory(Class c)
		{
			_class = c;
		}
	}

	private static class BufferedOutputStream extends OutputStream
	{

		private byte _data[];
		private int _pos;
		static final boolean $assertionsDisabled = !IceInternal/BasicStream.desiredAssertionStatus();

		public void close()
			throws IOException
		{
		}

		public void flush()
			throws IOException
		{
		}

		public void write(byte b[])
			throws IOException
		{
			if (!$assertionsDisabled && _data.length - _pos < b.length)
			{
				throw new AssertionError();
			} else
			{
				System.arraycopy(b, 0, _data, _pos, b.length);
				_pos += b.length;
				return;
			}
		}

		public void write(byte b[], int off, int len)
			throws IOException
		{
			if (!$assertionsDisabled && _data.length - _pos < len)
			{
				throw new AssertionError();
			} else
			{
				System.arraycopy(b, off, _data, _pos, len);
				_pos += len;
				return;
			}
		}

		public void write(int b)
			throws IOException
		{
			if (!$assertionsDisabled && _data.length - _pos < 1)
			{
				throw new AssertionError();
			} else
			{
				_data[_pos] = (byte)b;
				_pos++;
				return;
			}
		}

		int pos()
		{
			return _pos;
		}


		BufferedOutputStream(byte data[])
		{
			_data = data;
		}
	}


	private static final byte _encapsBlob[] = {
		0, 0, 0, 0, 1, 0
	};
	static final Charset _utf8 = Charset.forName("UTF8");
	private CharsetEncoder _charEncoder;
	private Instance _instance;
	private Buffer _buf;
	private Object _closure;
	private byte _stringBytes[];
	private char _stringChars[];
	private ReadEncaps _readEncapsStack;
	private WriteEncaps _writeEncapsStack;
	private ReadEncaps _readEncapsCache;
	private WriteEncaps _writeEncapsCache;
	private int _readSlice;
	private int _writeSlice;
	private int _traceSlicing;
	private String _slicingCat;
	private boolean _sliceObjects;
	private int _messageSizeMax;
	private boolean _unlimited;
	private int _startSeq;
	private int _minSeqSize;
	private ArrayList _objectList;
	private static HashMap _exceptionFactories = new HashMap();
	private static Object _factoryMutex = new Object();
	private static boolean _checkedBZip2 = false;
	private static Constructor _bzInputStreamCtor;
	private static Constructor _bzOutputStreamCtor;
	static final boolean $assertionsDisabled = !IceInternal/BasicStream.desiredAssertionStatus();

	public BasicStream(Instance instance)
	{
		this(instance, false);
	}

	public BasicStream(Instance instance, boolean unlimited)
	{
		_charEncoder = null;
		initialize(instance, unlimited, instance.cacheMessageBuffers() > 1);
	}

	public BasicStream(Instance instance, boolean unlimited, boolean direct)
	{
		_charEncoder = null;
		initialize(instance, unlimited, direct);
	}

	private void initialize(Instance instance, boolean unlimited, boolean direct)
	{
		_instance = instance;
		_buf = new Buffer(_instance.messageSizeMax(), direct);
		_closure = null;
		_unlimited = unlimited;
		_readEncapsStack = null;
		_writeEncapsStack = null;
		_readEncapsCache = null;
		_writeEncapsCache = null;
		_traceSlicing = -1;
		_sliceObjects = true;
		_messageSizeMax = _instance.messageSizeMax();
		_startSeq = -1;
		_objectList = null;
	}

	public void reset()
	{
		_buf.reset();
		clear();
	}

	public void clear()
	{
		if (_readEncapsStack != null)
		{
			if (!$assertionsDisabled && _readEncapsStack.next != null)
				throw new AssertionError();
			_readEncapsStack.next = _readEncapsCache;
			_readEncapsCache = _readEncapsStack;
			_readEncapsCache.reset();
			_readEncapsStack = null;
		}
		if (_writeEncapsStack != null)
		{
			if (!$assertionsDisabled && _writeEncapsStack.next != null)
				throw new AssertionError();
			_writeEncapsStack.next = _writeEncapsCache;
			_writeEncapsCache = _writeEncapsStack;
			_writeEncapsCache.reset();
			_writeEncapsStack = null;
		}
		_startSeq = -1;
		if (_objectList != null)
			_objectList.clear();
		_sliceObjects = true;
	}

	public Instance instance()
	{
		return _instance;
	}

	public Object closure()
	{
		return _closure;
	}

	public Object closure(Object p)
	{
		Object prev = _closure;
		_closure = p;
		return prev;
	}

	public void swap(BasicStream other)
	{
		if (!$assertionsDisabled && _instance != other._instance)
		{
			throw new AssertionError();
		} else
		{
			Object tmpClosure = other._closure;
			other._closure = _closure;
			_closure = tmpClosure;
			Buffer tmpBuf = other._buf;
			other._buf = _buf;
			_buf = tmpBuf;
			ReadEncaps tmpRead = other._readEncapsStack;
			other._readEncapsStack = _readEncapsStack;
			_readEncapsStack = tmpRead;
			tmpRead = other._readEncapsCache;
			other._readEncapsCache = _readEncapsCache;
			_readEncapsCache = tmpRead;
			WriteEncaps tmpWrite = other._writeEncapsStack;
			other._writeEncapsStack = _writeEncapsStack;
			_writeEncapsStack = tmpWrite;
			tmpWrite = other._writeEncapsCache;
			other._writeEncapsCache = _writeEncapsCache;
			_writeEncapsCache = tmpWrite;
			int tmpReadSlice = other._readSlice;
			other._readSlice = _readSlice;
			_readSlice = tmpReadSlice;
			int tmpWriteSlice = other._writeSlice;
			other._writeSlice = _writeSlice;
			_writeSlice = tmpWriteSlice;
			int tmpStartSeq = other._startSeq;
			other._startSeq = _startSeq;
			_startSeq = tmpStartSeq;
			int tmpMinSeqSize = other._minSeqSize;
			other._minSeqSize = _minSeqSize;
			_minSeqSize = tmpMinSeqSize;
			ArrayList tmpObjectList = other._objectList;
			other._objectList = _objectList;
			_objectList = tmpObjectList;
			boolean tmpUnlimited = other._unlimited;
			other._unlimited = _unlimited;
			_unlimited = tmpUnlimited;
			return;
		}
	}

	public void resize(int sz, boolean reading)
	{
		if (!_unlimited && sz > _messageSizeMax)
			Ex.throwMemoryLimitException(sz, _messageSizeMax);
		_buf.resize(sz, reading);
		_buf.b.position(sz);
	}

	public Buffer prepareWrite()
	{
		_buf.b.limit(_buf.size());
		_buf.b.position(0);
		return _buf;
	}

	public Buffer getBuffer()
	{
		return _buf;
	}

	public void startWriteEncaps()
	{
		WriteEncaps curr = _writeEncapsCache;
		if (curr != null)
		{
			curr.reset();
			_writeEncapsCache = _writeEncapsCache.next;
		} else
		{
			curr = new WriteEncaps();
		}
		curr.next = _writeEncapsStack;
		_writeEncapsStack = curr;
		_writeEncapsStack.start = _buf.size();
		writeBlob(_encapsBlob);
	}

	public void endWriteEncaps()
	{
		if (!$assertionsDisabled && _writeEncapsStack == null)
		{
			throw new AssertionError();
		} else
		{
			int start = _writeEncapsStack.start;
			int sz = _buf.size() - start;
			_buf.b.putInt(start, sz);
			WriteEncaps curr = _writeEncapsStack;
			_writeEncapsStack = curr.next;
			curr.next = _writeEncapsCache;
			_writeEncapsCache = curr;
			_writeEncapsCache.reset();
			return;
		}
	}

	public void endWriteEncapsChecked()
	{
		if (_writeEncapsStack == null)
		{
			throw new EncapsulationException("not in an encapsulation");
		} else
		{
			endWriteEncaps();
			return;
		}
	}

	public void startReadEncaps()
	{
		ReadEncaps curr = _readEncapsCache;
		if (curr != null)
		{
			curr.reset();
			_readEncapsCache = _readEncapsCache.next;
		} else
		{
			curr = new ReadEncaps();
		}
		curr.next = _readEncapsStack;
		_readEncapsStack = curr;
		_readEncapsStack.start = _buf.b.position();
		int sz = readInt();
		if (sz < 6)
			throw new UnmarshalOutOfBoundsException();
		if (sz - 4 > _buf.b.remaining())
			throw new UnmarshalOutOfBoundsException();
		_readEncapsStack.sz = sz;
		byte eMajor = readByte();
		byte eMinor = readByte();
		if (eMajor != 1 || eMinor > 0)
		{
			UnsupportedEncodingException e = new UnsupportedEncodingException();
			e.badMajor = eMajor >= 0 ? ((int) (eMajor)) : eMajor + 256;
			e.badMinor = eMinor >= 0 ? ((int) (eMinor)) : eMinor + 256;
			e.major = 1;
			e.minor = 0;
			throw e;
		} else
		{
			return;
		}
	}

	public void endReadEncaps()
	{
		if (!$assertionsDisabled && _readEncapsStack == null)
			throw new AssertionError();
		if (_buf.b.position() != _readEncapsStack.start + _readEncapsStack.sz)
		{
			if (_buf.b.position() + 1 != _readEncapsStack.start + _readEncapsStack.sz)
				throw new EncapsulationException();
			try
			{
				_buf.b.get();
			}
			catch (BufferUnderflowException ex)
			{
				throw new UnmarshalOutOfBoundsException();
			}
		}
		ReadEncaps curr = _readEncapsStack;
		_readEncapsStack = curr.next;
		curr.next = _readEncapsCache;
		_readEncapsCache = curr;
		_readEncapsCache.reset();
	}

	public void skipEmptyEncaps()
	{
		int sz = readInt();
		if (sz < 6)
			throw new UnmarshalOutOfBoundsException();
		if (sz != 6)
			throw new EncapsulationException();
		try
		{
			_buf.b.position(_buf.b.position() + 2);
		}
		catch (IllegalArgumentException ex)
		{
			throw new UnmarshalOutOfBoundsException();
		}
	}

	public void endReadEncapsChecked()
	{
		if (_readEncapsStack == null)
		{
			throw new EncapsulationException("not in an encapsulation");
		} else
		{
			endReadEncaps();
			return;
		}
	}

	public int getReadEncapsSize()
	{
		if (!$assertionsDisabled && _readEncapsStack == null)
			throw new AssertionError();
		else
			return _readEncapsStack.sz - 6;
	}

	public void skipEncaps()
	{
		int sz = readInt();
		if (sz < 6)
			throw new UnmarshalOutOfBoundsException();
		try
		{
			_buf.b.position((_buf.b.position() + sz) - 4);
		}
		catch (IllegalArgumentException ex)
		{
			throw new UnmarshalOutOfBoundsException();
		}
	}

	public void startWriteSlice()
	{
		writeInt(0);
		_writeSlice = _buf.size();
	}

	public void endWriteSlice()
	{
		int sz = (_buf.size() - _writeSlice) + 4;
		_buf.b.putInt(_writeSlice - 4, sz);
	}

	public void startReadSlice()
	{
		int sz = readInt();
		if (sz < 4)
		{
			throw new UnmarshalOutOfBoundsException();
		} else
		{
			_readSlice = _buf.b.position();
			return;
		}
	}

	public void endReadSlice()
	{
	}

	public void skipSlice()
	{
		int sz = readInt();
		if (sz < 4)
			throw new UnmarshalOutOfBoundsException();
		try
		{
			_buf.b.position((_buf.b.position() + sz) - 4);
		}
		catch (IllegalArgumentException ex)
		{
			throw new UnmarshalOutOfBoundsException();
		}
	}

	public int readAndCheckSeqSize(int minSize)
	{
		int sz = readSize();
		if (sz == 0)
			return 0;
		if (_startSeq == -1 || _buf.b.position() > _startSeq + _minSeqSize)
		{
			_startSeq = _buf.b.position();
			_minSeqSize = sz * minSize;
		} else
		{
			_minSeqSize += sz * minSize;
		}
		if (_startSeq + _minSeqSize > _buf.size())
			throw new UnmarshalOutOfBoundsException();
		else
			return sz;
	}

	public void writeSize(int v)
	{
		if (v > 254)
		{
			expand(5);
			_buf.b.put((byte)-1);
			_buf.b.putInt(v);
		} else
		{
			expand(1);
			_buf.b.put((byte)v);
		}
	}

	public int readSize()
	{
		byte b;
		int v;
		b = _buf.b.get();
		if (b != -1)
			break MISSING_BLOCK_LABEL_41;
		v = _buf.b.getInt();
		if (v < 0)
			throw new UnmarshalOutOfBoundsException();
		return v;
		return b >= 0 ? b : b + 256;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public void writeTypeId(String id)
	{
		if (_writeEncapsStack == null || _writeEncapsStack.typeIdMap == null)
			throw new MarshalException("type ids require an encapsulation");
		Integer index = (Integer)_writeEncapsStack.typeIdMap.get(id);
		if (index != null)
		{
			writeBool(true);
			writeSize(index.intValue());
		} else
		{
			index = Integer.valueOf(++_writeEncapsStack.typeIdIndex);
			_writeEncapsStack.typeIdMap.put(id, index);
			writeBool(false);
			writeString(id);
		}
	}

	public String readTypeId()
	{
		if (_readEncapsStack == null || _readEncapsStack.typeIdMap == null)
			throw new MarshalException("type ids require an encapsulation");
		boolean isIndex = readBool();
		String id;
		if (isIndex)
		{
			Integer index = Integer.valueOf(readSize());
			id = (String)_readEncapsStack.typeIdMap.get(index);
			if (id == null)
				throw new UnmarshalOutOfBoundsException();
		} else
		{
			id = readString();
			Integer index = Integer.valueOf(++_readEncapsStack.typeIdIndex);
			_readEncapsStack.typeIdMap.put(index, id);
		}
		return id;
	}

	public void writeBlob(byte v[])
	{
		if (v == null)
		{
			return;
		} else
		{
			expand(v.length);
			_buf.b.put(v);
			return;
		}
	}

	public void writeBlob(byte v[], int off, int len)
	{
		if (v == null)
		{
			return;
		} else
		{
			expand(len);
			_buf.b.put(v, off, len);
			return;
		}
	}

	public byte[] readBlob(int sz)
	{
		byte v[];
		if (_buf.b.remaining() < sz)
			throw new UnmarshalOutOfBoundsException();
		v = new byte[sz];
		_buf.b.get(v);
		return v;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public void writeByte(byte v)
	{
		expand(1);
		_buf.b.put(v);
	}

	public void writeByte(byte v, int end)
	{
		if (v < 0 || v >= end)
		{
			throw new MarshalException("enumerator out of range");
		} else
		{
			writeByte(v);
			return;
		}
	}

	public void writeByteSeq(byte v[])
	{
		if (v == null)
		{
			writeSize(0);
		} else
		{
			writeSize(v.length);
			expand(v.length);
			_buf.b.put(v);
		}
	}

	public void writeSerializable(Serializable o)
	{
		if (o == null)
		{
			writeSize(0);
			return;
		}
		try
		{
			OutputStreamWrapper w = new OutputStreamWrapper(this);
			ObjectOutputStream out = new ObjectOutputStream(w);
			out.writeObject(o);
			out.close();
			w.close();
		}
		catch (Exception ex)
		{
			throw new MarshalException((new StringBuilder()).append("cannot serialize object: ").append(ex).toString());
		}
	}

	public byte readByte()
	{
		return _buf.b.get();
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public byte readByte(int end)
	{
		byte v = readByte();
		if (v < 0 || v >= end)
			throw new MarshalException("enumerator out of range");
		else
			return v;
	}

	public byte[] readByteSeq()
	{
		byte v[];
		int sz = readAndCheckSeqSize(1);
		v = new byte[sz];
		_buf.b.get(v);
		return v;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public Serializable readSerializable()
	{
		int sz;
		sz = readAndCheckSeqSize(1);
		if (sz == 0)
			return null;
		ObjectInputStream in;
		InputStreamWrapper w = new InputStreamWrapper(sz, this);
		in = new ObjectInputStream(_instance, w);
		return (Serializable)in.readObject();
		Exception ex;
		ex;
		throw new MarshalException("cannot deserialize object", ex);
	}

	public void writeBool(boolean v)
	{
		expand(1);
		_buf.b.put(((byte)(v ? 1 : 0)));
	}

	public void writeBoolSeq(boolean v[])
	{
		if (v == null)
		{
			writeSize(0);
		} else
		{
			writeSize(v.length);
			expand(v.length);
			boolean arr$[] = v;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				boolean b = arr$[i$];
				_buf.b.put(((byte)(b ? 1 : 0)));
			}

		}
	}

	public boolean readBool()
	{
		return _buf.b.get() == 1;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public boolean[] readBoolSeq()
	{
		boolean v[];
		int sz = readAndCheckSeqSize(1);
		v = new boolean[sz];
		for (int i = 0; i < sz; i++)
			v[i] = _buf.b.get() == 1;

		return v;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public void writeShort(short v)
	{
		expand(2);
		_buf.b.putShort(v);
	}

	public void writeShort(short v, int end)
	{
		if (v < 0 || v >= end)
		{
			throw new MarshalException("enumerator out of range");
		} else
		{
			writeShort(v);
			return;
		}
	}

	public void writeShortSeq(short v[])
	{
		if (v == null)
		{
			writeSize(0);
		} else
		{
			writeSize(v.length);
			expand(v.length * 2);
			ShortBuffer shortBuf = _buf.b.asShortBuffer();
			shortBuf.put(v);
			_buf.b.position(_buf.b.position() + v.length * 2);
		}
	}

	public short readShort()
	{
		return _buf.b.getShort();
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public short readShort(int end)
	{
		short v = readShort();
		if (v < 0 || v >= end)
			throw new MarshalException("enumerator out of range");
		else
			return v;
	}

	public short[] readShortSeq()
	{
		short v[];
		int sz = readAndCheckSeqSize(2);
		v = new short[sz];
		ShortBuffer shortBuf = _buf.b.asShortBuffer();
		shortBuf.get(v);
		_buf.b.position(_buf.b.position() + sz * 2);
		return v;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public void writeInt(int v)
	{
		expand(4);
		_buf.b.putInt(v);
	}

	public void writeInt(int v, int end)
	{
		if (v < 0 || v >= end)
		{
			throw new MarshalException("enumerator out of range");
		} else
		{
			writeInt(v);
			return;
		}
	}

	public void writeIntSeq(int v[])
	{
		if (v == null)
		{
			writeSize(0);
		} else
		{
			writeSize(v.length);
			expand(v.length * 4);
			IntBuffer intBuf = _buf.b.asIntBuffer();
			intBuf.put(v);
			_buf.b.position(_buf.b.position() + v.length * 4);
		}
	}

	public int readInt()
	{
		return _buf.b.getInt();
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public int readInt(int end)
	{
		int v = readInt();
		if (v < 0 || v >= end)
			throw new MarshalException("enumerator out of range");
		else
			return v;
	}

	public int[] readIntSeq()
	{
		int v[];
		int sz = readAndCheckSeqSize(4);
		v = new int[sz];
		IntBuffer intBuf = _buf.b.asIntBuffer();
		intBuf.get(v);
		_buf.b.position(_buf.b.position() + sz * 4);
		return v;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public void writeLong(long v)
	{
		expand(8);
		_buf.b.putLong(v);
	}

	public void writeLongSeq(long v[])
	{
		if (v == null)
		{
			writeSize(0);
		} else
		{
			writeSize(v.length);
			expand(v.length * 8);
			LongBuffer longBuf = _buf.b.asLongBuffer();
			longBuf.put(v);
			_buf.b.position(_buf.b.position() + v.length * 8);
		}
	}

	public long readLong()
	{
		return _buf.b.getLong();
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public long[] readLongSeq()
	{
		long v[];
		int sz = readAndCheckSeqSize(8);
		v = new long[sz];
		LongBuffer longBuf = _buf.b.asLongBuffer();
		longBuf.get(v);
		_buf.b.position(_buf.b.position() + sz * 8);
		return v;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public void writeFloat(float v)
	{
		expand(4);
		_buf.b.putFloat(v);
	}

	public void writeFloatSeq(float v[])
	{
		if (v == null)
		{
			writeSize(0);
		} else
		{
			writeSize(v.length);
			expand(v.length * 4);
			FloatBuffer floatBuf = _buf.b.asFloatBuffer();
			floatBuf.put(v);
			_buf.b.position(_buf.b.position() + v.length * 4);
		}
	}

	public float readFloat()
	{
		return _buf.b.getFloat();
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public float[] readFloatSeq()
	{
		float v[];
		int sz = readAndCheckSeqSize(4);
		v = new float[sz];
		FloatBuffer floatBuf = _buf.b.asFloatBuffer();
		floatBuf.get(v);
		_buf.b.position(_buf.b.position() + sz * 4);
		return v;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public void writeDouble(double v)
	{
		expand(8);
		_buf.b.putDouble(v);
	}

	public void writeDoubleSeq(double v[])
	{
		if (v == null)
		{
			writeSize(0);
		} else
		{
			writeSize(v.length);
			expand(v.length * 8);
			DoubleBuffer doubleBuf = _buf.b.asDoubleBuffer();
			doubleBuf.put(v);
			_buf.b.position(_buf.b.position() + v.length * 8);
		}
	}

	public double readDouble()
	{
		return _buf.b.getDouble();
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public double[] readDoubleSeq()
	{
		double v[];
		int sz = readAndCheckSeqSize(8);
		v = new double[sz];
		DoubleBuffer doubleBuf = _buf.b.asDoubleBuffer();
		doubleBuf.get(v);
		_buf.b.position(_buf.b.position() + sz * 8);
		return v;
		BufferUnderflowException ex;
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public void writeString(String v)
	{
		if (v == null)
		{
			writeSize(0);
		} else
		{
			int len = v.length();
			if (len > 0)
			{
				if (_stringBytes == null || len > _stringBytes.length)
					_stringBytes = new byte[len];
				if (_stringChars == null || len > _stringChars.length)
					_stringChars = new char[len];
				v.getChars(0, len, _stringChars, 0);
				for (int i = 0; i < len; i++)
				{
					if (_stringChars[i] > '\177')
					{
						if (_charEncoder == null)
							_charEncoder = _utf8.newEncoder();
						ByteBuffer b = null;
						try
						{
							b = _charEncoder.encode(CharBuffer.wrap(_stringChars, 0, len));
						}
						catch (CharacterCodingException ex)
						{
							throw new MarshalException(ex);
						}
						writeSize(b.limit());
						expand(b.limit());
						_buf.b.put(b);
						return;
					}
					_stringBytes[i] = (byte)_stringChars[i];
				}

				writeSize(len);
				expand(len);
				_buf.b.put(_stringBytes, 0, len);
			} else
			{
				writeSize(0);
			}
		}
	}

	public void writeStringSeq(String v[])
	{
		if (v == null)
		{
			writeSize(0);
		} else
		{
			writeSize(v.length);
			String arr$[] = v;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String e = arr$[i$];
				writeString(e);
			}

		}
	}

	public String readString()
	{
		int len;
		len = readSize();
		if (len == 0)
			return "";
		if (_buf.b.remaining() < len)
			throw new UnmarshalOutOfBoundsException();
		int i;
		if (_stringBytes == null || len > _stringBytes.length)
			_stringBytes = new byte[len];
		if (_stringChars == null || len > _stringChars.length)
			_stringChars = new char[len];
		_buf.b.get(_stringBytes, 0, len);
		i = 0;
_L1:
		if (i >= len)
			break MISSING_BLOCK_LABEL_148;
		if (_stringBytes[i] < 0)
			return new String(_stringBytes, 0, len, "UTF8");
		_stringChars[i] = (char)_stringBytes[i];
		i++;
		  goto _L1
		return new String(_stringChars, 0, len);
		java.io.UnsupportedEncodingException ex;
		ex;
		if (!$assertionsDisabled)
			throw new AssertionError();
		else
			return "";
		ex;
		throw new UnmarshalOutOfBoundsException();
	}

	public String[] readStringSeq()
	{
		int sz = readAndCheckSeqSize(1);
		String v[] = new String[sz];
		for (int i = 0; i < sz; i++)
			v[i] = readString();

		return v;
	}

	public void writeProxy(ObjectPrx v)
	{
		_instance.proxyFactory().proxyToStream(v, this);
	}

	public ObjectPrx readProxy()
	{
		return _instance.proxyFactory().streamToProxy(this);
	}

	public void writeObject(Ice.Object v)
	{
		if (_writeEncapsStack == null)
		{
			_writeEncapsStack = _writeEncapsCache;
			if (_writeEncapsStack != null)
				_writeEncapsCache = _writeEncapsCache.next;
			else
				_writeEncapsStack = new WriteEncaps();
		}
		if (_writeEncapsStack.toBeMarshaledMap == null)
		{
			_writeEncapsStack.toBeMarshaledMap = new IdentityHashMap();
			_writeEncapsStack.marshaledMap = new IdentityHashMap();
			_writeEncapsStack.typeIdMap = new TreeMap();
		}
		if (v != null)
		{
			Integer p = (Integer)_writeEncapsStack.toBeMarshaledMap.get(v);
			if (p == null)
			{
				Integer q = (Integer)_writeEncapsStack.marshaledMap.get(v);
				if (q == null)
				{
					q = Integer.valueOf(++_writeEncapsStack.writeIndex);
					_writeEncapsStack.toBeMarshaledMap.put(v, q);
				}
				p = q;
			}
			writeInt(-p.intValue());
		} else
		{
			writeInt(0);
		}
	}

	public void readObject(Patcher patcher)
	{
		Ice.Object v = null;
		if (_readEncapsStack == null)
		{
			_readEncapsStack = _readEncapsCache;
			if (_readEncapsStack != null)
				_readEncapsCache = _readEncapsCache.next;
			else
				_readEncapsStack = new ReadEncaps();
		}
		if (_readEncapsStack.patchMap == null)
		{
			_readEncapsStack.patchMap = new TreeMap();
			_readEncapsStack.unmarshaledMap = new TreeMap();
			_readEncapsStack.typeIdMap = new TreeMap();
		}
		int index = readInt();
		if (patcher != null)
		{
			if (index == 0)
			{
				patcher.patch(null);
				return;
			}
			if (index < 0)
			{
				Integer i = Integer.valueOf(-index);
				LinkedList patchlist = (LinkedList)_readEncapsStack.patchMap.get(i);
				if (patchlist == null)
				{
					patchlist = new LinkedList();
					_readEncapsStack.patchMap.put(i, patchlist);
				}
				patchlist.add(patcher);
				patchReferences(null, i);
				return;
			}
		}
		if (index < 0)
			throw new MarshalException("Invalid class instance index");
		String mostDerivedId = readTypeId();
		String id = mostDerivedId;
		do
		{
			if (id.equals(ObjectImpl.ice_staticId()))
				throw new NoObjectFactoryException("", mostDerivedId);
			ObjectFactory userFactory = _instance.servantFactoryManager().find(id);
			if (userFactory != null)
				v = userFactory.create(id);
			if (v == null)
			{
				userFactory = _instance.servantFactoryManager().find("");
				if (userFactory != null)
					v = userFactory.create(id);
			}
			if (v == null)
			{
				userFactory = loadObjectFactory(id);
				if (userFactory != null)
					v = userFactory.create(id);
			}
			if (v != null)
				break;
			if (_sliceObjects)
			{
				if (_traceSlicing == -1)
				{
					_traceSlicing = _instance.traceLevels().slicing;
					_slicingCat = _instance.traceLevels().slicingCat;
				}
				if (_traceSlicing > 0)
					TraceUtil.traceSlicing("class", id, _slicingCat, _instance.initializationData().logger);
				skipSlice();
				id = readTypeId();
			} else
			{
				NoObjectFactoryException ex = new NoObjectFactoryException();
				ex.type = id;
				throw ex;
			}
		} while (true);
		Integer i = Integer.valueOf(index);
		_readEncapsStack.unmarshaledMap.put(i, v);
		if (_objectList == null)
			_objectList = new ArrayList();
		_objectList.add(v);
		v.__read(this, false);
		patchReferences(i, null);
	}

	public void writeUserException(UserException v)
	{
		writeBool(v.__usesClasses());
		v.__write(this);
		if (v.__usesClasses())
			writePendingObjects();
	}

	public void throwException()
		throws UserException
	{
		boolean usesClasses = readBool();
		String id = readString();
		String origId = id;
		do
		{
			UserExceptionFactory factory = getUserExceptionFactory(id);
			if (factory != null)
			{
				try
				{
					factory.createAndThrow();
				}
				catch (UserException ex)
				{
					ex.__read(this, false);
					if (usesClasses)
						readPendingObjects();
					throw ex;
				}
			} else
			{
				if (_traceSlicing == -1)
				{
					_traceSlicing = _instance.traceLevels().slicing;
					_slicingCat = _instance.traceLevels().slicingCat;
				}
				if (_traceSlicing > 0)
					TraceUtil.traceSlicing("exception", id, _slicingCat, _instance.initializationData().logger);
				skipSlice();
				try
				{
					id = readString();
				}
				catch (UnmarshalOutOfBoundsException ex)
				{
					throw new UnmarshalOutOfBoundsException((new StringBuilder()).append("unknown exception type `").append(origId).append("'").toString(), ex);
				}
			}
		} while (true);
	}

	public void writePendingObjects()
	{
		if (_writeEncapsStack != null && _writeEncapsStack.toBeMarshaledMap != null)
			while (_writeEncapsStack.toBeMarshaledMap.size() > 0) 
			{
				IdentityHashMap savedMap = new IdentityHashMap(_writeEncapsStack.toBeMarshaledMap);
				writeSize(savedMap.size());
				Iterator i$;
				java.util.Map.Entry p;
				for (i$ = savedMap.entrySet().iterator(); i$.hasNext(); writeInstance((Ice.Object)p.getKey(), (Integer)p.getValue()))
				{
					p = (java.util.Map.Entry)i$.next();
					_writeEncapsStack.marshaledMap.put(p.getKey(), p.getValue());
				}

				i$ = savedMap.keySet().iterator();
				while (i$.hasNext()) 
				{
					Ice.Object p = (Ice.Object)i$.next();
					_writeEncapsStack.toBeMarshaledMap.remove(p);
				}
			}
		writeSize(0);
	}

	public void readPendingObjects()
	{
		int num;
		do
		{
			num = readSize();
			for (int k = num; k > 0; k--)
				readObject(null);

		} while (num > 0);
		if (_readEncapsStack != null && _readEncapsStack.patchMap != null && _readEncapsStack.patchMap.size() != 0)
			throw new MarshalException("Index for class received, but no instance");
		if (_objectList != null)
		{
			for (Iterator i$ = _objectList.iterator(); i$.hasNext();)
			{
				Ice.Object obj = (Ice.Object)i$.next();
				try
				{
					obj.ice_postUnmarshal();
				}
				catch (Exception ex)
				{
					String s = (new StringBuilder()).append("exception raised by ice_postUnmarshal:\n").append(Ex.toString(ex)).toString();
					_instance.initializationData().logger.warning("exception raised by ice_postUnmarshal:\n");
				}
			}

		}
	}

	public void sliceObjects(boolean b)
	{
		_sliceObjects = b;
	}

	void writeInstance(Ice.Object v, Integer index)
	{
		writeInt(index.intValue());
		try
		{
			v.ice_preMarshal();
		}
		catch (Exception ex)
		{
			String s = (new StringBuilder()).append("exception raised by ice_preUnmarshal:\n").append(Ex.toString(ex)).toString();
			_instance.initializationData().logger.warning("exception raised by ice_preUnmarshal:\n");
		}
		v.__write(this);
	}

	void patchReferences(Integer instanceIndex, Integer patchIndex)
	{
		if (!$assertionsDisabled && (instanceIndex == null || patchIndex != null) && (instanceIndex != null || patchIndex == null))
			throw new AssertionError();
		LinkedList patchlist;
		Ice.Object v;
		if (instanceIndex != null)
		{
			patchlist = (LinkedList)_readEncapsStack.patchMap.get(instanceIndex);
			if (patchlist == null)
				return;
			v = (Ice.Object)_readEncapsStack.unmarshaledMap.get(instanceIndex);
			patchIndex = instanceIndex;
		} else
		{
			v = (Ice.Object)_readEncapsStack.unmarshaledMap.get(patchIndex);
			if (v == null)
				return;
			patchlist = (LinkedList)_readEncapsStack.patchMap.get(patchIndex);
		}
		if (!$assertionsDisabled && (patchlist == null || patchlist.size() <= 0))
			throw new AssertionError();
		if (!$assertionsDisabled && v == null)
			throw new AssertionError();
		for (Iterator i$ = patchlist.iterator(); i$.hasNext();)
		{
			Patcher p = (Patcher)i$.next();
			try
			{
				p.patch(v);
			}
			catch (ClassCastException ex)
			{
				throw new NoObjectFactoryException("no object factory", p.type(), ex);
			}
		}

		_readEncapsStack.patchMap.remove(patchIndex);
	}

	public int pos()
	{
		return _buf.b.position();
	}

	public void pos(int n)
	{
		_buf.b.position(n);
	}

	public int size()
	{
		return _buf.size();
	}

	public boolean isEmpty()
	{
		return _buf.empty();
	}

	public BasicStream compress(int headerSize, int compressionLevel)
	{
		if (!$assertionsDisabled && !compressible())
			throw new AssertionError();
		int uncompressedLen = size() - headerSize;
		int compressedLen = (int)((double)uncompressedLen * 1.01D + 600D);
		byte compressed[] = new byte[compressedLen];
		byte data[] = null;
		int offset = 0;
		try
		{
			data = _buf.b.array();
			offset = _buf.b.arrayOffset();
		}
		catch (Exception ex)
		{
			data = new byte[size()];
			pos(0);
			_buf.b.get(data);
		}
		try
		{
			BufferedOutputStream bos = new BufferedOutputStream(compressed);
			bos.write(66);
			bos.write(90);
			Object args[] = {
				bos, Integer.valueOf(compressionLevel)
			};
			OutputStream os = (OutputStream)_bzOutputStreamCtor.newInstance(args);
			os.write(data, offset + headerSize, uncompressedLen);
			os.close();
			compressedLen = bos.pos();
		}
		catch (Exception ex)
		{
			throw new CompressionException("bzip2 compression failure", ex);
		}
		if (compressedLen >= uncompressedLen)
		{
			return null;
		} else
		{
			BasicStream cstream = new BasicStream(_instance);
			cstream.resize(headerSize + 4 + compressedLen, false);
			cstream.pos(0);
			cstream._buf.b.put(data, offset, headerSize);
			cstream.writeInt(size());
			cstream._buf.b.put(compressed, 0, compressedLen);
			return cstream;
		}
	}

	public BasicStream uncompress(int headerSize)
	{
		if (!$assertionsDisabled && !compressible())
			throw new AssertionError();
		pos(headerSize);
		int uncompressedSize = readInt();
		if (uncompressedSize <= headerSize)
			throw new IllegalMessageSizeException();
		int compressedLen = size() - headerSize - 4;
		byte compressed[] = null;
		int offset = 0;
		try
		{
			compressed = _buf.b.array();
			offset = _buf.b.arrayOffset();
		}
		catch (Exception ex)
		{
			compressed = new byte[size()];
			pos(0);
			_buf.b.get(compressed);
		}
		BasicStream ucStream = new BasicStream(_instance);
		ucStream.resize(uncompressedSize, false);
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(compressed, offset + headerSize + 4, compressedLen);
			byte magicB = (byte)bais.read();
			byte magicZ = (byte)bais.read();
			if (magicB != 66 || magicZ != 90)
			{
				CompressionException e = new CompressionException();
				e.reason = "bzip2 uncompression failure: invalid magic bytes";
				throw e;
			}
			Object args[] = {
				bais
			};
			InputStream is = (InputStream)_bzInputStreamCtor.newInstance(args);
			ucStream.pos(headerSize);
			byte arr[] = new byte[8192];
			int n;
			while ((n = is.read(arr)) != -1) 
				ucStream.writeBlob(arr, 0, n);
			is.close();
		}
		catch (Exception ex)
		{
			throw new CompressionException("bzip2 uncompression failure", ex);
		}
		ucStream.pos(0);
		ucStream._buf.b.put(compressed, offset, headerSize);
		return ucStream;
	}

	public void expand(int n)
	{
		if (!_unlimited && _buf.b != null && _buf.b.position() + n > _messageSizeMax)
			Ex.throwMemoryLimitException(_buf.b.position() + n, _messageSizeMax);
		_buf.expand(n);
	}

	private ObjectFactory loadObjectFactory(String id)
	{
		ObjectFactory factory = null;
		try
		{
			Class c = findClass(id);
			if (c != null)
			{
				ObjectFactory dynamicFactory = new DynamicObjectFactory(c);
				while (factory == null) 
					try
					{
						_instance.servantFactoryManager().add(dynamicFactory, id);
						factory = dynamicFactory;
					}
					catch (AlreadyRegisteredException ex)
					{
						factory = _instance.servantFactoryManager().find(id);
					}
			}
		}
		catch (LinkageError ex)
		{
			throw new NoObjectFactoryException("no object factory", id, ex);
		}
		return factory;
	}

	private UserExceptionFactory getUserExceptionFactory(String id)
	{
		UserExceptionFactory factory = null;
		synchronized (_factoryMutex)
		{
			factory = (UserExceptionFactory)_exceptionFactories.get(id);
		}
		if (factory == null)
		{
			try
			{
				Class c = findClass(id);
				if (c != null)
					factory = new DynamicUserExceptionFactory(c);
			}
			catch (LinkageError ex)
			{
				throw new MarshalException(ex);
			}
			if (factory != null)
				synchronized (_factoryMutex)
				{
					_exceptionFactories.put(id, factory);
				}
		}
		return factory;
	}

	private Class findClass(String id)
		throws LinkageError
	{
		Class c = null;
		String className = typeToClass(id);
		c = getConcreteClass(className);
		if (c == null)
		{
			int pos = id.indexOf(':', 2);
			if (pos != -1)
			{
				String topLevelModule = id.substring(2, pos);
				String pkg = _instance.initializationData().properties.getProperty((new StringBuilder()).append("Ice.Package.").append(topLevelModule).toString());
				if (pkg.length() > 0)
					c = getConcreteClass((new StringBuilder()).append(pkg).append(".").append(className).toString());
			}
		}
		if (c == null)
		{
			String pkg = _instance.initializationData().properties.getProperty("Ice.Default.Package");
			if (pkg.length() > 0)
				c = getConcreteClass((new StringBuilder()).append(pkg).append(".").append(className).toString());
		}
		return c;
	}

	private Class getConcreteClass(String className)
		throws LinkageError
	{
		Class c = _instance.findClass(className);
		if (c != null)
		{
			int modifiers = c.getModifiers();
			if ((modifiers & 0x200) == 0 && (modifiers & 0x400) == 0)
				return c;
		}
		return null;
	}

	private static String fixKwd(String name)
	{
		String keywordList[] = {
			"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "checkedCast", "class", 
			"clone", "const", "continue", "default", "do", "double", "else", "enum", "equals", "extends", 
			"false", "final", "finalize", "finally", "float", "for", "getClass", "goto", "hashCode", "if", 
			"implements", "import", "instanceof", "int", "interface", "long", "native", "new", "notify", "notifyAll", 
			"null", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", 
			"switch", "synchronized", "this", "throw", "throws", "toString", "transient", "true", "try", "uncheckedCast", 
			"void", "volatile", "wait", "while"
		};
		boolean found = Arrays.binarySearch(keywordList, name) >= 0;
		return found ? (new StringBuilder()).append("_").append(name).toString() : name;
	}

	private String typeToClass(String id)
	{
		if (!id.startsWith("::"))
			throw new MarshalException((new StringBuilder()).append("expected type id but received `").append(id).append("'").toString());
		StringBuilder buf = new StringBuilder(id.length());
		int start = 2;
		boolean done = false;
		while (!done) 
		{
			int end = id.indexOf(':', start);
			String s;
			if (end != -1)
			{
				s = id.substring(start, end);
				start = end + 2;
			} else
			{
				s = id.substring(start);
				done = true;
			}
			if (buf.length() > 0)
				buf.append('.');
			buf.append(fixKwd(s));
		}
		return buf.toString();
	}

	public static synchronized boolean compressible()
	{
		if (!_checkedBZip2)
		{
			_checkedBZip2 = true;
			try
			{
				Class types[] = new Class[1];
				Class cls = Util.findClass("org.apache.tools.bzip2.CBZip2InputStream", null);
				if (cls != null)
				{
					types[0] = java/io/InputStream;
					_bzInputStreamCtor = cls.getDeclaredConstructor(types);
				}
				cls = Util.findClass("org.apache.tools.bzip2.CBZip2OutputStream", null);
				if (cls != null)
				{
					types = new Class[2];
					types[0] = java/io/OutputStream;
					types[1] = Integer.TYPE;
					_bzOutputStreamCtor = cls.getDeclaredConstructor(types);
				}
			}
			catch (Exception ex) { }
		}
		return _bzInputStreamCtor != null && _bzOutputStreamCtor != null;
	}

}
