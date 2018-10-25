// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AttributeImpl.java

package org.apache.lucene.util;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;

// Referenced classes of package org.apache.lucene.util:
//			Attribute, AttributeSource, AttributeReflector

public abstract class AttributeImpl
	implements Cloneable, Attribute
{

	public AttributeImpl()
	{
	}

	public abstract void clear();

	public final String reflectAsString(final boolean prependAttClass)
	{
		final StringBuilder buffer = new StringBuilder();
		reflectWith(new AttributeReflector() {

			final StringBuilder val$buffer;
			final boolean val$prependAttClass;
			final AttributeImpl this$0;

			public void reflect(Class attClass, String key, Object value)
			{
				if (buffer.length() > 0)
					buffer.append(',');
				if (prependAttClass)
					buffer.append(attClass.getName()).append('#');
				buffer.append(key).append('=').append(value != null ? value : "null");
			}

			
			{
				this$0 = AttributeImpl.this;
				buffer = stringbuilder;
				prependAttClass = flag;
				super();
			}
		});
		return buffer.toString();
	}

	public void reflectWith(AttributeReflector reflector)
	{
		Class clazz = getClass();
		LinkedList interfaces = AttributeSource.getAttributeInterfaces(clazz);
		if (interfaces.size() != 1)
			throw new UnsupportedOperationException((new StringBuilder()).append(clazz.getName()).append(" implements more than one Attribute interface, the default reflectWith() implementation cannot handle this.").toString());
		Class interf = (Class)((WeakReference)interfaces.getFirst()).get();
		Field fields[] = clazz.getDeclaredFields();
		try
		{
			for (int i = 0; i < fields.length; i++)
			{
				Field f = fields[i];
				if (!Modifier.isStatic(f.getModifiers()))
				{
					f.setAccessible(true);
					reflector.reflect(interf, f.getName(), f.get(this));
				}
			}

		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}
	}

	public abstract void copyTo(AttributeImpl attributeimpl);

	public AttributeImpl clone()
	{
		AttributeImpl clone = null;
		try
		{
			clone = (AttributeImpl)super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new RuntimeException(e);
		}
		return clone;
	}

	public volatile Object clone()
		throws CloneNotSupportedException
	{
		return clone();
	}
}
