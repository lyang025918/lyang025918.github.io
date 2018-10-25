// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   RamUsageEstimator.java

package org.apache.lucene.util;

import java.lang.management.ManagementFactory;
import java.lang.reflect.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

// Referenced classes of package org.apache.lucene.util:
//			Constants

public final class RamUsageEstimator
{
	static final class IdentityHashSet
		implements Iterable
	{

		public static final float DEFAULT_LOAD_FACTOR = 0.75F;
		public static final int MIN_CAPACITY = 4;
		public Object keys[];
		public int assigned;
		public final float loadFactor;
		private int resizeThreshold;
		static final boolean $assertionsDisabled = !org/apache/lucene/util/RamUsageEstimator.desiredAssertionStatus();

		public boolean add(Object e)
		{
			if (!$assertionsDisabled && e == null)
				throw new AssertionError("Null keys not allowed.");
			if (assigned >= resizeThreshold)
				expandAndRehash();
			int mask = keys.length - 1;
			int slot;
			Object existing;
			for (slot = rehash(e) & mask; (existing = keys[slot]) != null; slot = slot + 1 & mask)
				if (e == existing)
					return false;

			assigned++;
			keys[slot] = e;
			return true;
		}

		public boolean contains(Object e)
		{
			int mask = keys.length - 1;
			Object existing;
			for (int slot = rehash(e) & mask; (existing = keys[slot]) != null; slot = slot + 1 & mask)
				if (e == existing)
					return true;

			return false;
		}

		private static int rehash(Object o)
		{
			int k = System.identityHashCode(o);
			k ^= k >>> 16;
			k *= 0x85ebca6b;
			k ^= k >>> 13;
			k *= 0xc2b2ae35;
			k ^= k >>> 16;
			return k;
		}

		private void expandAndRehash()
		{
			Object oldKeys[] = keys;
			if (!$assertionsDisabled && assigned < resizeThreshold)
				throw new AssertionError();
			allocateBuffers(nextCapacity(keys.length));
			int mask = keys.length - 1;
			for (int i = 0; i < oldKeys.length; i++)
			{
				Object key = oldKeys[i];
				if (key == null)
					continue;
				int slot;
				for (slot = rehash(key) & mask; keys[slot] != null; slot = slot + 1 & mask);
				keys[slot] = key;
			}

			Arrays.fill(oldKeys, null);
		}

		private void allocateBuffers(int capacity)
		{
			keys = new Object[capacity];
			resizeThreshold = (int)((float)capacity * 0.75F);
		}

		protected int nextCapacity(int current)
		{
			if (!$assertionsDisabled && (current <= 0 || Long.bitCount(current) != 1))
				throw new AssertionError("Capacity must be a power of two.");
			if (!$assertionsDisabled && current << 1 <= 0)
				throw new AssertionError("Maximum capacity exceeded (1073741824).");
			if (current < 2)
				current = 2;
			return current << 1;
		}

		protected int roundCapacity(int requestedCapacity)
		{
			if (requestedCapacity > 0x40000000)
				return 0x40000000;
			int capacity;
			for (capacity = 4; capacity < requestedCapacity; capacity <<= 1);
			return capacity;
		}

		public void clear()
		{
			assigned = 0;
			Arrays.fill(keys, null);
		}

		public int size()
		{
			return assigned;
		}

		public boolean isEmpty()
		{
			return size() == 0;
		}

		public Iterator iterator()
		{
			return new Iterator() {

				int pos;
				Object nextElement;
				final IdentityHashSet this$0;

				public boolean hasNext()
				{
					return nextElement != null;
				}

				public Object next()
				{
					Object r = nextElement;
					if (r == null)
					{
						throw new NoSuchElementException();
					} else
					{
						nextElement = fetchNext();
						return r;
					}
				}

				private Object fetchNext()
				{
					for (pos++; pos < keys.length && keys[pos] == null; pos++);
					return pos < keys.length ? keys[pos] : null;
				}

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				
				{
					this$0 = IdentityHashSet.this;
					super();
					pos = -1;
					nextElement = fetchNext();
				}
			};
		}


		public IdentityHashSet()
		{
			this(16, 0.75F);
		}

		public IdentityHashSet(int initialCapacity)
		{
			this(initialCapacity, 0.75F);
		}

		public IdentityHashSet(int initialCapacity, float loadFactor)
		{
			initialCapacity = Math.max(4, initialCapacity);
			if (!$assertionsDisabled && initialCapacity <= 0)
				throw new AssertionError("Initial capacity must be between (0, 2147483647].");
			if (!$assertionsDisabled && (loadFactor <= 0.0F || loadFactor >= 1.0F))
			{
				throw new AssertionError("Load factor must be between (0, 1).");
			} else
			{
				this.loadFactor = loadFactor;
				allocateBuffers(roundCapacity(initialCapacity));
				return;
			}
		}
	}

	private static final class DummyTwoLongObject
	{

		public long dummy1;
		public long dummy2;

		private DummyTwoLongObject()
		{
		}
	}

	private static final class DummyOneFieldObject
	{

		public byte base;

		private DummyOneFieldObject()
		{
		}
	}

	private static final class ClassCache
	{

		public final long alignedShallowInstanceSize;
		public final Field referenceFields[];

		public ClassCache(long alignedShallowInstanceSize, Field referenceFields[])
		{
			this.alignedShallowInstanceSize = alignedShallowInstanceSize;
			this.referenceFields = referenceFields;
		}
	}

	public static final class JvmFeature extends Enum
	{

		public static final JvmFeature OBJECT_REFERENCE_SIZE;
		public static final JvmFeature ARRAY_HEADER_SIZE;
		public static final JvmFeature FIELD_OFFSETS;
		public static final JvmFeature OBJECT_ALIGNMENT;
		public final String description;
		private static final JvmFeature $VALUES[];

		public static JvmFeature[] values()
		{
			return (JvmFeature[])$VALUES.clone();
		}

		public static JvmFeature valueOf(String name)
		{
			return (JvmFeature)Enum.valueOf(org/apache/lucene/util/RamUsageEstimator$JvmFeature, name);
		}

		public String toString()
		{
			return (new StringBuilder()).append(super.name()).append(" (").append(description).append(")").toString();
		}

		static 
		{
			OBJECT_REFERENCE_SIZE = new JvmFeature("OBJECT_REFERENCE_SIZE", 0, "Object reference size estimated using array index scale");
			ARRAY_HEADER_SIZE = new JvmFeature("ARRAY_HEADER_SIZE", 1, "Array header size estimated using array based offset");
			FIELD_OFFSETS = new JvmFeature("FIELD_OFFSETS", 2, "Shallow instance size based on field offsets");
			OBJECT_ALIGNMENT = new JvmFeature("OBJECT_ALIGNMENT", 3, "Object alignment retrieved from HotSpotDiagnostic MX bean");
			$VALUES = (new JvmFeature[] {
				OBJECT_REFERENCE_SIZE, ARRAY_HEADER_SIZE, FIELD_OFFSETS, OBJECT_ALIGNMENT
			});
		}

		private JvmFeature(String s, int i, String description)
		{
			super(s, i);
			this.description = description;
		}
	}


	public static final String JVM_INFO_STRING;
	public static final long ONE_KB = 1024L;
	public static final long ONE_MB = 0x100000L;
	public static final long ONE_GB = 0x40000000L;
	public static final int NUM_BYTES_BOOLEAN = 1;
	public static final int NUM_BYTES_BYTE = 1;
	public static final int NUM_BYTES_CHAR = 2;
	public static final int NUM_BYTES_SHORT = 2;
	public static final int NUM_BYTES_INT = 4;
	public static final int NUM_BYTES_FLOAT = 4;
	public static final int NUM_BYTES_LONG = 8;
	public static final int NUM_BYTES_DOUBLE = 8;
	public static final int NUM_BYTES_OBJECT_REF;
	public static final int NUM_BYTES_OBJECT_HEADER;
	public static final int NUM_BYTES_ARRAY_HEADER;
	public static final int NUM_BYTES_OBJECT_ALIGNMENT;
	private static final Map primitiveSizes;
	private static final Object theUnsafe;
	private static final Method objectFieldOffsetMethod;
	private static final EnumSet supportedFeatures;

	private RamUsageEstimator()
	{
	}

	public static boolean isSupportedJVM()
	{
		return supportedFeatures.size() == JvmFeature.values().length;
	}

	public static long alignObjectSize(long size)
	{
		size += (long)NUM_BYTES_OBJECT_ALIGNMENT - 1L;
		return size - size % (long)NUM_BYTES_OBJECT_ALIGNMENT;
	}

	public static long sizeOf(byte arr[])
	{
		return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + (long)arr.length);
	}

	public static long sizeOf(boolean arr[])
	{
		return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + (long)arr.length);
	}

	public static long sizeOf(char arr[])
	{
		return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 2L * (long)arr.length);
	}

	public static long sizeOf(short arr[])
	{
		return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 2L * (long)arr.length);
	}

	public static long sizeOf(int arr[])
	{
		return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 4L * (long)arr.length);
	}

	public static long sizeOf(float arr[])
	{
		return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 4L * (long)arr.length);
	}

	public static long sizeOf(long arr[])
	{
		return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 8L * (long)arr.length);
	}

	public static long sizeOf(double arr[])
	{
		return alignObjectSize((long)NUM_BYTES_ARRAY_HEADER + 8L * (long)arr.length);
	}

	public static long sizeOf(Object obj)
	{
		return measureObjectSize(obj);
	}

	public static long shallowSizeOf(Object obj)
	{
		if (obj == null)
			return 0L;
		Class clz = obj.getClass();
		if (clz.isArray())
			return shallowSizeOfArray(obj);
		else
			return shallowSizeOfInstance(clz);
	}

	public static long shallowSizeOfInstance(Class clazz)
	{
		if (clazz.isArray())
			throw new IllegalArgumentException("This method does not work with array classes.");
		if (clazz.isPrimitive())
			return (long)((Integer)primitiveSizes.get(clazz)).intValue();
		long size = NUM_BYTES_OBJECT_HEADER;
		for (; clazz != null; clazz = clazz.getSuperclass())
		{
			Field fields[] = clazz.getDeclaredFields();
			Field arr$[] = fields;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Field f = arr$[i$];
				if (!Modifier.isStatic(f.getModifiers()))
					size = adjustForField(size, f);
			}

		}

		return alignObjectSize(size);
	}

	private static long shallowSizeOfArray(Object array)
	{
		long size = NUM_BYTES_ARRAY_HEADER;
		int len = Array.getLength(array);
		if (len > 0)
		{
			Class arrayElementClazz = array.getClass().getComponentType();
			if (arrayElementClazz.isPrimitive())
				size += (long)len * (long)((Integer)primitiveSizes.get(arrayElementClazz)).intValue();
			else
				size += (long)NUM_BYTES_OBJECT_REF * (long)len;
		}
		return alignObjectSize(size);
	}

	private static long measureObjectSize(Object root)
	{
		IdentityHashSet seen = new IdentityHashSet();
		IdentityHashMap classCache = new IdentityHashMap();
		ArrayList stack = new ArrayList();
		stack.add(root);
		long totalSize = 0L;
		do
		{
			if (stack.isEmpty())
				break;
			Object ob = stack.remove(stack.size() - 1);
			if (ob != null && !seen.contains(ob))
			{
				seen.add(ob);
				Class obClazz = ob.getClass();
				if (obClazz.isArray())
				{
					long size = NUM_BYTES_ARRAY_HEADER;
					int len = Array.getLength(ob);
					if (len > 0)
					{
						Class componentClazz = obClazz.getComponentType();
						if (componentClazz.isPrimitive())
						{
							size += (long)len * (long)((Integer)primitiveSizes.get(componentClazz)).intValue();
						} else
						{
							size += (long)NUM_BYTES_OBJECT_REF * (long)len;
							int i = len;
							do
							{
								if (--i < 0)
									break;
								Object o = Array.get(ob, i);
								if (o != null && !seen.contains(o))
									stack.add(o);
							} while (true);
						}
					}
					totalSize += alignObjectSize(size);
				} else
				{
					try
					{
						ClassCache cachedInfo = (ClassCache)classCache.get(obClazz);
						if (cachedInfo == null)
							classCache.put(obClazz, cachedInfo = createCacheEntry(obClazz));
						Field arr$[] = cachedInfo.referenceFields;
						int len$ = arr$.length;
						for (int i$ = 0; i$ < len$; i$++)
						{
							Field f = arr$[i$];
							Object o = f.get(ob);
							if (o != null && !seen.contains(o))
								stack.add(o);
						}

						totalSize += cachedInfo.alignedShallowInstanceSize;
					}
					catch (IllegalAccessException e)
					{
						throw new RuntimeException("Reflective field access failed?", e);
					}
				}
			}
		} while (true);
		seen.clear();
		stack.clear();
		classCache.clear();
		return totalSize;
	}

	private static ClassCache createCacheEntry(Class clazz)
	{
		long shallowInstanceSize = NUM_BYTES_OBJECT_HEADER;
		ArrayList referenceFields = new ArrayList(32);
		for (Class c = clazz; c != null; c = c.getSuperclass())
		{
			Field fields[] = c.getDeclaredFields();
			Field arr$[] = fields;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				Field f = arr$[i$];
				if (Modifier.isStatic(f.getModifiers()))
					continue;
				shallowInstanceSize = adjustForField(shallowInstanceSize, f);
				if (!f.getType().isPrimitive())
				{
					f.setAccessible(true);
					referenceFields.add(f);
				}
			}

		}

		ClassCache cachedInfo = new ClassCache(alignObjectSize(shallowInstanceSize), (Field[])referenceFields.toArray(new Field[referenceFields.size()]));
		return cachedInfo;
	}

	private static long adjustForField(long sizeSoFar, Field f)
	{
		int fsize;
		Class type = f.getType();
		fsize = type.isPrimitive() ? ((Integer)primitiveSizes.get(type)).intValue() : NUM_BYTES_OBJECT_REF;
		if (objectFieldOffsetMethod == null)
			break MISSING_BLOCK_LABEL_175;
		long offsetPlusSize = ((Number)objectFieldOffsetMethod.invoke(theUnsafe, new Object[] {
			f
		})).longValue() + (long)fsize;
		return Math.max(sizeSoFar, offsetPlusSize);
		IllegalAccessException ex;
		ex;
		throw new RuntimeException("Access problem with sun.misc.Unsafe", ex);
		InvocationTargetException ite;
		ite;
		Throwable cause = ite.getCause();
		if (cause instanceof RuntimeException)
			throw (RuntimeException)cause;
		if (cause instanceof Error)
			throw (Error)cause;
		else
			throw new RuntimeException((new StringBuilder()).append("Call to Unsafe's objectFieldOffset() throwed checked Exception when accessing field ").append(f.getDeclaringClass().getName()).append("#").append(f.getName()).toString(), cause);
		return sizeSoFar + (long)fsize;
	}

	public static EnumSet getUnsupportedFeatures()
	{
		EnumSet unsupported = EnumSet.allOf(org/apache/lucene/util/RamUsageEstimator$JvmFeature);
		unsupported.removeAll(supportedFeatures);
		return unsupported;
	}

	public static EnumSet getSupportedFeatures()
	{
		return EnumSet.copyOf(supportedFeatures);
	}

	public static String humanReadableUnits(long bytes)
	{
		return humanReadableUnits(bytes, new DecimalFormat("0.#", DecimalFormatSymbols.getInstance(Locale.ROOT)));
	}

	public static String humanReadableUnits(long bytes, DecimalFormat df)
	{
		if (bytes / 0x40000000L > 0L)
			return (new StringBuilder()).append(df.format((float)bytes / 1.073742E+009F)).append(" GB").toString();
		if (bytes / 0x100000L > 0L)
			return (new StringBuilder()).append(df.format((float)bytes / 1048576F)).append(" MB").toString();
		if (bytes / 1024L > 0L)
			return (new StringBuilder()).append(df.format((float)bytes / 1024F)).append(" KB").toString();
		else
			return (new StringBuilder()).append(bytes).append(" bytes").toString();
	}

	public static String humanSizeOf(Object object)
	{
		return humanReadableUnits(sizeOf(object));
	}

	static 
	{
		primitiveSizes = new IdentityHashMap();
		primitiveSizes.put(Boolean.TYPE, Integer.valueOf(1));
		primitiveSizes.put(Byte.TYPE, Integer.valueOf(1));
		primitiveSizes.put(Character.TYPE, Integer.valueOf(2));
		primitiveSizes.put(Short.TYPE, Integer.valueOf(2));
		primitiveSizes.put(Integer.TYPE, Integer.valueOf(4));
		primitiveSizes.put(Float.TYPE, Integer.valueOf(4));
		primitiveSizes.put(Double.TYPE, Integer.valueOf(8));
		primitiveSizes.put(Long.TYPE, Integer.valueOf(8));
		int referenceSize = Constants.JRE_IS_64BIT ? 8 : 4;
		int objectHeader = Constants.JRE_IS_64BIT ? 16 : 8;
		int arrayHeader = Constants.JRE_IS_64BIT ? 24 : 12;
		supportedFeatures = EnumSet.noneOf(org/apache/lucene/util/RamUsageEstimator$JvmFeature);
		Class unsafeClass = null;
		Object tempTheUnsafe = null;
		try
		{
			unsafeClass = Class.forName("sun.misc.Unsafe");
			Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
			unsafeField.setAccessible(true);
			tempTheUnsafe = unsafeField.get(null);
		}
		catch (Exception e) { }
		theUnsafe = tempTheUnsafe;
		try
		{
			Method arrayIndexScaleM = unsafeClass.getMethod("arrayIndexScale", new Class[] {
				java/lang/Class
			});
			referenceSize = ((Number)arrayIndexScaleM.invoke(theUnsafe, new Object[] {
				[Ljava/lang/Object;
			})).intValue();
			supportedFeatures.add(JvmFeature.OBJECT_REFERENCE_SIZE);
		}
		catch (Exception e) { }
		objectHeader = Constants.JRE_IS_64BIT ? 8 + referenceSize : 8;
		arrayHeader = Constants.JRE_IS_64BIT ? 8 + 2 * referenceSize : 12;
		Method tempObjectFieldOffsetMethod = null;
		try
		{
			Method objectFieldOffsetM = unsafeClass.getMethod("objectFieldOffset", new Class[] {
				java/lang/reflect/Field
			});
			Field dummy1Field = org/apache/lucene/util/RamUsageEstimator$DummyTwoLongObject.getDeclaredField("dummy1");
			int ofs1 = ((Number)objectFieldOffsetM.invoke(theUnsafe, new Object[] {
				dummy1Field
			})).intValue();
			Field dummy2Field = org/apache/lucene/util/RamUsageEstimator$DummyTwoLongObject.getDeclaredField("dummy2");
			int ofs2 = ((Number)objectFieldOffsetM.invoke(theUnsafe, new Object[] {
				dummy2Field
			})).intValue();
			if (Math.abs(ofs2 - ofs1) == 8)
			{
				Field baseField = org/apache/lucene/util/RamUsageEstimator$DummyOneFieldObject.getDeclaredField("base");
				objectHeader = ((Number)objectFieldOffsetM.invoke(theUnsafe, new Object[] {
					baseField
				})).intValue();
				supportedFeatures.add(JvmFeature.FIELD_OFFSETS);
				tempObjectFieldOffsetMethod = objectFieldOffsetM;
			}
		}
		catch (Exception e) { }
		objectFieldOffsetMethod = tempObjectFieldOffsetMethod;
		try
		{
			Method arrayBaseOffsetM = unsafeClass.getMethod("arrayBaseOffset", new Class[] {
				java/lang/Class
			});
			arrayHeader = ((Number)arrayBaseOffsetM.invoke(theUnsafe, new Object[] {
				[B
			})).intValue();
			supportedFeatures.add(JvmFeature.ARRAY_HEADER_SIZE);
		}
		catch (Exception e) { }
		NUM_BYTES_OBJECT_REF = referenceSize;
		NUM_BYTES_OBJECT_HEADER = objectHeader;
		NUM_BYTES_ARRAY_HEADER = arrayHeader;
		int objectAlignment = 8;
		try
		{
			Class beanClazz = Class.forName("com.sun.management.HotSpotDiagnosticMXBean");
			Object hotSpotBean = ManagementFactory.newPlatformMXBeanProxy(ManagementFactory.getPlatformMBeanServer(), "com.sun.management:type=HotSpotDiagnostic", beanClazz);
			Method getVMOptionMethod = beanClazz.getMethod("getVMOption", new Class[] {
				java/lang/String
			});
			Object vmOption = getVMOptionMethod.invoke(hotSpotBean, new Object[] {
				"ObjectAlignmentInBytes"
			});
			objectAlignment = Integer.parseInt(vmOption.getClass().getMethod("getValue", new Class[0]).invoke(vmOption, new Object[0]).toString());
			supportedFeatures.add(JvmFeature.OBJECT_ALIGNMENT);
		}
		catch (Exception e) { }
		NUM_BYTES_OBJECT_ALIGNMENT = objectAlignment;
		JVM_INFO_STRING = (new StringBuilder()).append("[JVM: ").append(Constants.JVM_NAME).append(", ").append(Constants.JVM_VERSION).append(", ").append(Constants.JVM_VENDOR).append(", ").append(Constants.JAVA_VENDOR).append(", ").append(Constants.JAVA_VERSION).append("]").toString();
	}
}
