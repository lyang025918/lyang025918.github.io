// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   OutputStream.java

package Ice;

import java.io.Serializable;

// Referenced classes of package Ice:
//			Communicator, ObjectPrx, Object, UserException

public interface OutputStream
{

	public abstract Communicator communicator();

	public abstract void writeBool(boolean flag);

	public abstract void writeBoolSeq(boolean aflag[]);

	public abstract void writeByte(byte byte0);

	public abstract void writeByteSeq(byte abyte0[]);

	public abstract void writeSerializable(Serializable serializable);

	public abstract void writeShort(short word0);

	public abstract void writeShortSeq(short aword0[]);

	public abstract void writeInt(int i);

	public abstract void writeIntSeq(int ai[]);

	public abstract void writeLong(long l);

	public abstract void writeLongSeq(long al[]);

	public abstract void writeFloat(float f);

	public abstract void writeFloatSeq(float af[]);

	public abstract void writeDouble(double d);

	public abstract void writeDoubleSeq(double ad[]);

	public abstract void writeString(String s);

	public abstract void writeStringSeq(String as[]);

	public abstract void writeSize(int i);

	public abstract void writeProxy(ObjectPrx objectprx);

	public abstract void writeObject(Object obj);

	public abstract void writeTypeId(String s);

	public abstract void writeException(UserException userexception);

	public abstract void startSlice();

	public abstract void endSlice();

	public abstract void startEncapsulation();

	public abstract void endEncapsulation();

	public abstract void writePendingObjects();

	public abstract byte[] finished();

	public abstract void reset(boolean flag);

	public abstract void destroy();
}
