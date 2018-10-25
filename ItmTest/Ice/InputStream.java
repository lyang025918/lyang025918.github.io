// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   InputStream.java

package Ice;

import java.io.Serializable;

// Referenced classes of package Ice:
//			UserException, Communicator, ObjectPrx, ReadObjectCallback

public interface InputStream
{

	public abstract Communicator communicator();

	public abstract void sliceObjects(boolean flag);

	public abstract boolean readBool();

	public abstract boolean[] readBoolSeq();

	public abstract byte readByte();

	public abstract byte[] readByteSeq();

	public abstract Serializable readSerializable();

	public abstract short readShort();

	public abstract short[] readShortSeq();

	public abstract int readInt();

	public abstract int[] readIntSeq();

	public abstract long readLong();

	public abstract long[] readLongSeq();

	public abstract float readFloat();

	public abstract float[] readFloatSeq();

	public abstract double readDouble();

	public abstract double[] readDoubleSeq();

	public abstract String readString();

	public abstract String[] readStringSeq();

	public abstract int readSize();

	public abstract int readAndCheckSeqSize(int i);

	public abstract ObjectPrx readProxy();

	public abstract void readObject(ReadObjectCallback readobjectcallback);

	public abstract String readTypeId();

	public abstract void throwException()
		throws UserException;

	public abstract void startSlice();

	public abstract void endSlice();

	public abstract void skipSlice();

	public abstract void startEncapsulation();

	public abstract void skipEncapsulation();

	public abstract void endEncapsulation();

	public abstract void readPendingObjects();

	public abstract void rewind();

	public abstract void destroy();
}
