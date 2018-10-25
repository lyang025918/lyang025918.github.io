// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   AttributeSource.java

package org.apache.lucene.util;

import java.lang.ref.WeakReference;
import java.util.*;

// Referenced classes of package org.apache.lucene.util:
//			Attribute, AttributeImpl, WeakIdentityMap, AttributeReflector

public class AttributeSource
{
	public static final class State
		implements Cloneable
	{

		AttributeImpl attribute;
		State next;

		public State clone()
		{
			State clone = new State();
			clone.attribute = attribute.clone();
			if (next != null)
				clone.next = next.clone();
			return clone;
		}

		public volatile Object clone()
			throws CloneNotSupportedException
		{
			return clone();
		}

		public State()
		{
		}
	}

	public static abstract class AttributeFactory
	{
		private static final class DefaultAttributeFactory extends AttributeFactory
		{

			private static final WeakIdentityMap attClassImplMap = WeakIdentityMap.newConcurrentHashMap();

			public AttributeImpl createAttributeInstance(Class attClass)
			{
				return (AttributeImpl)getClassForInterface(attClass).newInstance();
				InstantiationException e;
				e;
				throw new IllegalArgumentException((new StringBuilder()).append("Could not instantiate implementing class for ").append(attClass.getName()).toString());
				e;
				throw new IllegalArgumentException((new StringBuilder()).append("Could not instantiate implementing class for ").append(attClass.getName()).toString());
			}

			private static Class getClassForInterface(Class attClass)
			{
				WeakReference ref = (WeakReference)attClassImplMap.get(attClass);
				Class clazz = ref != null ? (Class)ref.get() : null;
				if (clazz == null)
					try
					{
						attClassImplMap.put(attClass, new WeakReference(clazz = Class.forName((new StringBuilder()).append(attClass.getName()).append("Impl").toString(), true, attClass.getClassLoader()).asSubclass(org/apache/lucene/util/AttributeImpl)));
					}
					catch (ClassNotFoundException e)
					{
						throw new IllegalArgumentException((new StringBuilder()).append("Could not find implementing class for ").append(attClass.getName()).toString());
					}
				return clazz;
			}


			private DefaultAttributeFactory()
			{
			}

		}


		public static final AttributeFactory DEFAULT_ATTRIBUTE_FACTORY = new DefaultAttributeFactory();

		public abstract AttributeImpl createAttributeInstance(Class class1);


		public AttributeFactory()
		{
		}
	}


	private final Map attributes;
	private final Map attributeImpls;
	private final State currentState[];
	private final AttributeFactory factory;
	private static final WeakIdentityMap knownImplClasses = WeakIdentityMap.newConcurrentHashMap();
	static final boolean $assertionsDisabled = !org/apache/lucene/util/AttributeSource.desiredAssertionStatus();

	public AttributeSource()
	{
		this(AttributeFactory.DEFAULT_ATTRIBUTE_FACTORY);
	}

	public AttributeSource(AttributeSource input)
	{
		if (input == null)
		{
			throw new IllegalArgumentException("input AttributeSource must not be null");
		} else
		{
			attributes = input.attributes;
			attributeImpls = input.attributeImpls;
			currentState = input.currentState;
			factory = input.factory;
			return;
		}
	}

	public AttributeSource(AttributeFactory factory)
	{
		attributes = new LinkedHashMap();
		attributeImpls = new LinkedHashMap();
		currentState = new State[1];
		this.factory = factory;
	}

	public final AttributeFactory getAttributeFactory()
	{
		return factory;
	}

	public final Iterator getAttributeClassesIterator()
	{
		return Collections.unmodifiableSet(attributes.keySet()).iterator();
	}

	public final Iterator getAttributeImplsIterator()
	{
		final State initState = getCurrentState();
		if (initState != null)
			return new Iterator() {

				private State state;
				final State val$initState;
				final AttributeSource this$0;

				public void remove()
				{
					throw new UnsupportedOperationException();
				}

				public AttributeImpl next()
				{
					if (state == null)
					{
						throw new NoSuchElementException();
					} else
					{
						AttributeImpl att = state.attribute;
						state = state.next;
						return att;
					}
				}

				public boolean hasNext()
				{
					return state != null;
				}

				public volatile Object next()
				{
					return next();
				}

			
			{
				this$0 = AttributeSource.this;
				initState = state1;
				super();
				state = initState;
			}
			};
		else
			return Collections.emptySet().iterator();
	}

	static LinkedList getAttributeInterfaces(Class clazz)
	{
		LinkedList foundInterfaces = (LinkedList)knownImplClasses.get(clazz);
		if (foundInterfaces == null)
		{
			foundInterfaces = new LinkedList();
			Class actClazz = clazz;
			do
			{
				Class arr$[] = actClazz.getInterfaces();
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					Class curInterface = arr$[i$];
					if (curInterface != org/apache/lucene/util/Attribute && org/apache/lucene/util/Attribute.isAssignableFrom(curInterface))
						foundInterfaces.add(new WeakReference(curInterface.asSubclass(org/apache/lucene/util/Attribute)));
				}

				actClazz = actClazz.getSuperclass();
			} while (actClazz != null);
			knownImplClasses.put(clazz, foundInterfaces);
		}
		return foundInterfaces;
	}

	public final void addAttributeImpl(AttributeImpl att)
	{
		Class clazz = att.getClass();
		if (attributeImpls.containsKey(clazz))
			return;
		LinkedList foundInterfaces = getAttributeInterfaces(clazz);
		Iterator i$ = foundInterfaces.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			WeakReference curInterfaceRef = (WeakReference)i$.next();
			Class curInterface = (Class)curInterfaceRef.get();
			if (!$assertionsDisabled && curInterface == null)
				throw new AssertionError("We have a strong reference on the class holding the interfaces, so they should never get evicted");
			if (!attributes.containsKey(curInterface))
			{
				currentState[0] = null;
				attributes.put(curInterface, att);
				attributeImpls.put(clazz, att);
			}
		} while (true);
	}

	public final Attribute addAttribute(Class attClass)
	{
		AttributeImpl attImpl = (AttributeImpl)attributes.get(attClass);
		if (attImpl == null)
		{
			if (!attClass.isInterface() || !org/apache/lucene/util/Attribute.isAssignableFrom(attClass))
				throw new IllegalArgumentException((new StringBuilder()).append("addAttribute() only accepts an interface that extends Attribute, but ").append(attClass.getName()).append(" does not fulfil this contract.").toString());
			addAttributeImpl(attImpl = factory.createAttributeInstance(attClass));
		}
		return (Attribute)attClass.cast(attImpl);
	}

	public final boolean hasAttributes()
	{
		return !attributes.isEmpty();
	}

	public final boolean hasAttribute(Class attClass)
	{
		return attributes.containsKey(attClass);
	}

	public final Attribute getAttribute(Class attClass)
	{
		AttributeImpl attImpl = (AttributeImpl)attributes.get(attClass);
		if (attImpl == null)
			throw new IllegalArgumentException((new StringBuilder()).append("This AttributeSource does not have the attribute '").append(attClass.getName()).append("'.").toString());
		else
			return (Attribute)attClass.cast(attImpl);
	}

	private State getCurrentState()
	{
		State s = currentState[0];
		if (s != null || !hasAttributes())
			return s;
		State c = s = currentState[0] = new State();
		Iterator it = attributeImpls.values().iterator();
		for (c.attribute = (AttributeImpl)it.next(); it.hasNext(); c.attribute = (AttributeImpl)it.next())
		{
			c.next = new State();
			c = c.next;
		}

		return s;
	}

	public final void clearAttributes()
	{
		for (State state = getCurrentState(); state != null; state = state.next)
			state.attribute.clear();

	}

	public final State captureState()
	{
		State state = getCurrentState();
		return state != null ? state.clone() : null;
	}

	public final void restoreState(State state)
	{
		if (state == null)
			return;
		do
		{
			AttributeImpl targetImpl = (AttributeImpl)attributeImpls.get(state.attribute.getClass());
			if (targetImpl == null)
				throw new IllegalArgumentException((new StringBuilder()).append("State contains AttributeImpl of type ").append(state.attribute.getClass().getName()).append(" that is not in in this AttributeSource").toString());
			state.attribute.copyTo(targetImpl);
			state = state.next;
		} while (state != null);
	}

	public int hashCode()
	{
		int code = 0;
		for (State state = getCurrentState(); state != null; state = state.next)
			code = code * 31 + state.attribute.hashCode();

		return code;
	}

	public boolean equals(Object obj)
	{
		if (obj == this)
			return true;
		if (obj instanceof AttributeSource)
		{
			AttributeSource other = (AttributeSource)obj;
			if (hasAttributes())
			{
				if (!other.hasAttributes())
					return false;
				if (attributeImpls.size() != other.attributeImpls.size())
					return false;
				State thisState = getCurrentState();
				for (State otherState = other.getCurrentState(); thisState != null && otherState != null; otherState = otherState.next)
				{
					if (otherState.attribute.getClass() != thisState.attribute.getClass() || !otherState.attribute.equals(thisState.attribute))
						return false;
					thisState = thisState.next;
				}

				return true;
			} else
			{
				return !other.hasAttributes();
			}
		} else
		{
			return false;
		}
	}

	public final String reflectAsString(final boolean prependAttClass)
	{
		final StringBuilder buffer = new StringBuilder();
		reflectWith(new AttributeReflector() {

			final StringBuilder val$buffer;
			final boolean val$prependAttClass;
			final AttributeSource this$0;

			public void reflect(Class attClass, String key, Object value)
			{
				if (buffer.length() > 0)
					buffer.append(',');
				if (prependAttClass)
					buffer.append(attClass.getName()).append('#');
				buffer.append(key).append('=').append(value != null ? value : "null");
			}

			
			{
				this$0 = AttributeSource.this;
				buffer = stringbuilder;
				prependAttClass = flag;
				super();
			}
		});
		return buffer.toString();
	}

	public final void reflectWith(AttributeReflector reflector)
	{
		for (State state = getCurrentState(); state != null; state = state.next)
			state.attribute.reflectWith(reflector);

	}

	public final AttributeSource cloneAttributes()
	{
		AttributeSource clone = new AttributeSource(factory);
		if (hasAttributes())
		{
			for (State state = getCurrentState(); state != null; state = state.next)
				clone.attributeImpls.put(state.attribute.getClass(), state.attribute.clone());

			java.util.Map.Entry entry;
			for (Iterator i$ = attributes.entrySet().iterator(); i$.hasNext(); clone.attributes.put(entry.getKey(), clone.attributeImpls.get(((AttributeImpl)entry.getValue()).getClass())))
				entry = (java.util.Map.Entry)i$.next();

		}
		return clone;
	}

	public final void copyTo(AttributeSource target)
	{
		for (State state = getCurrentState(); state != null; state = state.next)
		{
			AttributeImpl targetImpl = (AttributeImpl)target.attributeImpls.get(state.attribute.getClass());
			if (targetImpl == null)
				throw new IllegalArgumentException((new StringBuilder()).append("This AttributeSource contains AttributeImpl of type ").append(state.attribute.getClass().getName()).append(" that is not in the target").toString());
			state.attribute.copyTo(targetImpl);
		}

	}

}
