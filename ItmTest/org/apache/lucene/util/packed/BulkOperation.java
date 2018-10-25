// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   BulkOperation.java

package org.apache.lucene.util.packed;


// Referenced classes of package org.apache.lucene.util.packed:
//			BulkOperationPacked1, BulkOperationPacked2, BulkOperationPacked3, BulkOperationPacked4, 
//			BulkOperationPacked5, BulkOperationPacked6, BulkOperationPacked7, BulkOperationPacked8, 
//			BulkOperationPacked9, BulkOperationPacked10, BulkOperationPacked11, BulkOperationPacked12, 
//			BulkOperationPacked13, BulkOperationPacked14, BulkOperationPacked15, BulkOperationPacked16, 
//			BulkOperationPacked17, BulkOperationPacked18, BulkOperationPacked19, BulkOperationPacked20, 
//			BulkOperationPacked21, BulkOperationPacked22, BulkOperationPacked23, BulkOperationPacked24, 
//			BulkOperationPacked, BulkOperationPackedSingleBlock, PackedInts

abstract class BulkOperation
	implements PackedInts.Decoder, PackedInts.Encoder
{

	private static final BulkOperation packedBulkOps[] = {
		new BulkOperationPacked1(), new BulkOperationPacked2(), new BulkOperationPacked3(), new BulkOperationPacked4(), new BulkOperationPacked5(), new BulkOperationPacked6(), new BulkOperationPacked7(), new BulkOperationPacked8(), new BulkOperationPacked9(), new BulkOperationPacked10(), 
		new BulkOperationPacked11(), new BulkOperationPacked12(), new BulkOperationPacked13(), new BulkOperationPacked14(), new BulkOperationPacked15(), new BulkOperationPacked16(), new BulkOperationPacked17(), new BulkOperationPacked18(), new BulkOperationPacked19(), new BulkOperationPacked20(), 
		new BulkOperationPacked21(), new BulkOperationPacked22(), new BulkOperationPacked23(), new BulkOperationPacked24(), new BulkOperationPacked(25), new BulkOperationPacked(26), new BulkOperationPacked(27), new BulkOperationPacked(28), new BulkOperationPacked(29), new BulkOperationPacked(30), 
		new BulkOperationPacked(31), new BulkOperationPacked(32), new BulkOperationPacked(33), new BulkOperationPacked(34), new BulkOperationPacked(35), new BulkOperationPacked(36), new BulkOperationPacked(37), new BulkOperationPacked(38), new BulkOperationPacked(39), new BulkOperationPacked(40), 
		new BulkOperationPacked(41), new BulkOperationPacked(42), new BulkOperationPacked(43), new BulkOperationPacked(44), new BulkOperationPacked(45), new BulkOperationPacked(46), new BulkOperationPacked(47), new BulkOperationPacked(48), new BulkOperationPacked(49), new BulkOperationPacked(50), 
		new BulkOperationPacked(51), new BulkOperationPacked(52), new BulkOperationPacked(53), new BulkOperationPacked(54), new BulkOperationPacked(55), new BulkOperationPacked(56), new BulkOperationPacked(57), new BulkOperationPacked(58), new BulkOperationPacked(59), new BulkOperationPacked(60), 
		new BulkOperationPacked(61), new BulkOperationPacked(62), new BulkOperationPacked(63), new BulkOperationPacked(64)
	};
	private static final BulkOperation packedSingleBlockBulkOps[] = {
		new BulkOperationPackedSingleBlock(1), new BulkOperationPackedSingleBlock(2), new BulkOperationPackedSingleBlock(3), new BulkOperationPackedSingleBlock(4), new BulkOperationPackedSingleBlock(5), new BulkOperationPackedSingleBlock(6), new BulkOperationPackedSingleBlock(7), new BulkOperationPackedSingleBlock(8), new BulkOperationPackedSingleBlock(9), new BulkOperationPackedSingleBlock(10), 
		null, new BulkOperationPackedSingleBlock(12), null, null, null, new BulkOperationPackedSingleBlock(16), null, null, null, null, 
		new BulkOperationPackedSingleBlock(21), null, null, null, null, null, null, null, null, null, 
		null, new BulkOperationPackedSingleBlock(32)
	};
	static final boolean $assertionsDisabled = !org/apache/lucene/util/packed/BulkOperation.desiredAssertionStatus();

	BulkOperation()
	{
	}

	public static BulkOperation of(PackedInts.Format format, int bitsPerValue)
	{
		static class 1
		{

			static final int $SwitchMap$org$apache$lucene$util$packed$PackedInts$Format[];

			static 
			{
				$SwitchMap$org$apache$lucene$util$packed$PackedInts$Format = new int[PackedInts.Format.values().length];
				try
				{
					$SwitchMap$org$apache$lucene$util$packed$PackedInts$Format[PackedInts.Format.PACKED.ordinal()] = 1;
				}
				catch (NoSuchFieldError ex) { }
				try
				{
					$SwitchMap$org$apache$lucene$util$packed$PackedInts$Format[PackedInts.Format.PACKED_SINGLE_BLOCK.ordinal()] = 2;
				}
				catch (NoSuchFieldError ex) { }
			}
		}

		switch (1..SwitchMap.org.apache.lucene.util.packed.PackedInts.Format[format.ordinal()])
		{
		case 1: // '\001'
			if (!$assertionsDisabled && packedBulkOps[bitsPerValue - 1] == null)
				throw new AssertionError();
			else
				return packedBulkOps[bitsPerValue - 1];

		case 2: // '\002'
			if (!$assertionsDisabled && packedSingleBlockBulkOps[bitsPerValue - 1] == null)
				throw new AssertionError();
			else
				return packedSingleBlockBulkOps[bitsPerValue - 1];
		}
		throw new AssertionError();
	}

	protected int writeLong(long block, byte blocks[], int blocksOffset)
	{
		for (int j = 1; j <= 8; j++)
			blocks[blocksOffset++] = (byte)(int)(block >>> 64 - (j << 3));

		return blocksOffset;
	}

	public final int computeIterations(int valueCount, int ramBudget)
	{
		int iterations = (ramBudget >>> 3) / (blockCount() + valueCount());
		if (iterations == 0)
			return 1;
		if ((iterations - 1) * blockCount() >= valueCount)
			return (int)Math.ceil((double)valueCount / (double)valueCount());
		else
			return iterations;
	}

}
