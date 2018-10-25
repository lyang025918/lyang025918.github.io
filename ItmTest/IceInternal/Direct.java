// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   Direct.java

package IceInternal;

import Ice.*;

// Referenced classes of package IceInternal:
//			ServantManager

public abstract class Direct
	implements Request
{

	private final Current _current;
	private Ice.Object _servant;
	private UserException _userException;
	private ServantLocator _locator;
	private LocalObjectHolder _cookie;
	static final boolean $assertionsDisabled = !IceInternal/Direct.desiredAssertionStatus();

	public abstract DispatchStatus run(Ice.Object obj);

	public final boolean isCollocated()
	{
		return true;
	}

	public final Current getCurrent()
	{
		return _current;
	}

	public Direct(Current current)
		throws UserException
	{
		_current = current;
		ObjectAdapterI adapter = (ObjectAdapterI)_current.adapter;
		if (!$assertionsDisabled && adapter == null)
			throw new AssertionError();
		adapter.incDirectCount();
		ServantManager servantManager = adapter.getServantManager();
		if (!$assertionsDisabled && servantManager == null)
			throw new AssertionError();
		_servant = servantManager.findServant(_current.id, _current.facet);
		if (_servant == null)
		{
			_locator = servantManager.findServantLocator(_current.id.category);
			if (_locator == null && _current.id.category.length() > 0)
				_locator = servantManager.findServantLocator("");
			if (_locator != null)
			{
				_cookie = new LocalObjectHolder();
				try
				{
					_servant = _locator.locate(_current, _cookie);
				}
				catch (UserException ex)
				{
					adapter.decDirectCount();
					throw ex;
				}
				catch (RuntimeException ex)
				{
					adapter.decDirectCount();
					throw ex;
				}
			}
		}
		if (_servant == null)
		{
			adapter.decDirectCount();
			if (servantManager != null && servantManager.hasServant(_current.id))
			{
				FacetNotExistException ex = new FacetNotExistException();
				ex.id = _current.id;
				ex.facet = _current.facet;
				ex.operation = _current.operation;
				throw ex;
			} else
			{
				ObjectNotExistException ex = new ObjectNotExistException();
				ex.id = _current.id;
				ex.facet = _current.facet;
				ex.operation = _current.operation;
				throw ex;
			}
		} else
		{
			return;
		}
	}

	public void destroy()
		throws UserException
	{
		ObjectAdapterI adapter;
		adapter = (ObjectAdapterI)_current.adapter;
		if (!$assertionsDisabled && adapter == null)
			throw new AssertionError();
		if (_locator != null && _servant != null)
			_locator.finished(_current, _servant, _cookie.value);
		adapter.decDirectCount();
		break MISSING_BLOCK_LABEL_81;
		Exception exception;
		exception;
		adapter.decDirectCount();
		throw exception;
	}

	public Ice.Object servant()
	{
		return _servant;
	}

	public void throwUserException()
		throws UserException
	{
		if (!$assertionsDisabled && _userException == null)
			throw new AssertionError();
		else
			throw _userException;
	}

	public void setUserException(UserException ex)
	{
		_userException = ex;
	}

}
