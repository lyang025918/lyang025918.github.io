// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IncomingBase.java

package IceInternal;

import Ice.*;
import IceUtilInternal.OutputBase;
import IceUtilInternal.StringUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;

// Referenced classes of package IceInternal:
//			BasicStream, Instance

public class IncomingBase
{

	protected Instance _instance;
	protected Current _current;
	protected Ice.Object _servant;
	protected ServantLocator _locator;
	protected LocalObjectHolder _cookie;
	protected boolean _response;
	protected byte _compress;
	protected BasicStream _os;
	protected ConnectionI _connection;
	protected LinkedList _interceptorAsyncCallbackList;
	static final boolean $assertionsDisabled = !IceInternal/IncomingBase.desiredAssertionStatus();

	protected IncomingBase(Instance instance, ConnectionI connection, ObjectAdapter adapter, boolean response, byte compress, int requestId)
	{
		_instance = instance;
		_response = response;
		_compress = compress;
		_os = new BasicStream(instance);
		_connection = connection;
		_current = new Current();
		_current.id = new Identity();
		_current.adapter = adapter;
		_current.con = _connection;
		_current.requestId = requestId;
		_cookie = new LocalObjectHolder();
	}

	protected IncomingBase(IncomingBase in)
	{
		_current = in._current;
		if (in._interceptorAsyncCallbackList != null)
			_interceptorAsyncCallbackList = new LinkedList(in._interceptorAsyncCallbackList);
		adopt(in);
	}

	protected void adopt(IncomingBase other)
	{
		_instance = other._instance;
		_servant = other._servant;
		other._servant = null;
		_locator = other._locator;
		other._locator = null;
		_cookie = other._cookie;
		other._cookie = null;
		_response = other._response;
		other._response = false;
		_compress = other._compress;
		other._compress = 0;
		_os = other._os;
		other._os = null;
		_connection = other._connection;
		other._connection = null;
	}

	public void reset(Instance instance, ConnectionI connection, ObjectAdapter adapter, boolean response, byte compress, int requestId)
	{
		_instance = instance;
		_current = new Current();
		_current.id = new Identity();
		_current.adapter = adapter;
		_current.con = connection;
		_current.requestId = requestId;
		if (_cookie == null)
			_cookie = new LocalObjectHolder();
		_response = response;
		_compress = compress;
		if (_os == null)
			_os = new BasicStream(instance);
		_connection = connection;
		_interceptorAsyncCallbackList = null;
	}

	public void reclaim()
	{
		_servant = null;
		_locator = null;
		if (_cookie != null)
			_cookie.value = null;
		if (_os != null)
			_os.reset();
		_interceptorAsyncCallbackList = null;
	}

	protected final void __warning(Exception ex)
	{
		if (!$assertionsDisabled && _instance == null)
			throw new AssertionError();
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		OutputBase out = new OutputBase(pw);
		out.setUseTab(false);
		out.print("dispatch exception:");
		out.print((new StringBuilder()).append("\nidentity: ").append(_instance.identityToString(_current.id)).toString());
		out.print((new StringBuilder()).append("\nfacet: ").append(StringUtil.escapeString(_current.facet, "")).toString());
		out.print((new StringBuilder()).append("\noperation: ").append(_current.operation).toString());
		if (_connection != null)
		{
			Ice.ConnectionInfo connInfo = _connection.getInfo();
			if (connInfo instanceof IPConnectionInfo)
			{
				IPConnectionInfo ipConnInfo = (IPConnectionInfo)connInfo;
				out.print((new StringBuilder()).append("\nremote host: ").append(ipConnInfo.remoteAddress).append(" remote port: ").append(Integer.toString(ipConnInfo.remotePort)).toString());
			}
		}
		out.print("\n");
		ex.printStackTrace(pw);
		pw.flush();
		_instance.initializationData().logger.warning(sw.toString());
	}

	protected final boolean __servantLocatorFinished()
	{
		if (!$assertionsDisabled && (_locator == null || _servant == null))
			throw new AssertionError();
		_locator.finished(_current, _servant, _cookie.value);
		return true;
		UserException ex;
		ex;
		if (!$assertionsDisabled && _connection == null)
			throw new AssertionError();
		if (_response)
		{
			_os.endWriteEncaps();
			_os.resize(18, false);
			_os.writeByte((byte)1);
			_os.startWriteEncaps();
			_os.writeUserException(ex);
			_os.endWriteEncaps();
			_connection.sendResponse(_os, _compress);
		} else
		{
			_connection.sendNoResponse();
		}
		_connection = null;
		break MISSING_BLOCK_LABEL_169;
		ex;
		__handleException(ex);
		return false;
	}

	protected final void __handleException(Exception exc)
	{
		if (!$assertionsDisabled && _connection == null)
			throw new AssertionError();
		try
		{
			throw exc;
		}
		catch (RequestFailedException ex)
		{
			if (ex.id == null)
				ex.id = _current.id;
			if (ex.facet == null)
				ex.facet = _current.facet;
			if (ex.operation == null || ex.operation.length() == 0)
				ex.operation = _current.operation;
			if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 1)
				__warning(ex);
			if (_response)
			{
				_os.endWriteEncaps();
				_os.resize(18, false);
				if (ex instanceof ObjectNotExistException)
					_os.writeByte((byte)2);
				else
				if (ex instanceof FacetNotExistException)
					_os.writeByte((byte)3);
				else
				if (ex instanceof OperationNotExistException)
					_os.writeByte((byte)4);
				else
				if (!$assertionsDisabled)
					throw new AssertionError();
				ex.id.__write(_os);
				if (ex.facet == null || ex.facet.length() == 0)
				{
					_os.writeStringSeq(null);
				} else
				{
					String facetPath2[] = {
						ex.facet
					};
					_os.writeStringSeq(facetPath2);
				}
				_os.writeString(ex.operation);
				_connection.sendResponse(_os, _compress);
			} else
			{
				_connection.sendNoResponse();
			}
		}
		catch (UnknownLocalException ex)
		{
			if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 0)
				__warning(ex);
			if (_response)
			{
				_os.endWriteEncaps();
				_os.resize(18, false);
				_os.writeByte((byte)5);
				_os.writeString(ex.unknown);
				_connection.sendResponse(_os, _compress);
			} else
			{
				_connection.sendNoResponse();
			}
		}
		catch (UnknownUserException ex)
		{
			if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 0)
				__warning(ex);
			if (_response)
			{
				_os.endWriteEncaps();
				_os.resize(18, false);
				_os.writeByte((byte)6);
				_os.writeString(ex.unknown);
				_connection.sendResponse(_os, _compress);
			} else
			{
				_connection.sendNoResponse();
			}
		}
		catch (UnknownException ex)
		{
			if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 0)
				__warning(ex);
			if (_response)
			{
				_os.endWriteEncaps();
				_os.resize(18, false);
				_os.writeByte((byte)7);
				_os.writeString(ex.unknown);
				_connection.sendResponse(_os, _compress);
			} else
			{
				_connection.sendNoResponse();
			}
		}
		catch (LocalException ex)
		{
			if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 0)
				__warning(ex);
			if (_response)
			{
				_os.endWriteEncaps();
				_os.resize(18, false);
				_os.writeByte((byte)5);
				StringWriter sw = new StringWriter();
				sw.write((new StringBuilder()).append(ex.ice_name()).append("\n").toString());
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				pw.flush();
				_os.writeString(sw.toString());
				_connection.sendResponse(_os, _compress);
			} else
			{
				_connection.sendNoResponse();
			}
		}
		catch (UserException ex)
		{
			if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 0)
				__warning(ex);
			if (_response)
			{
				_os.endWriteEncaps();
				_os.resize(18, false);
				_os.writeByte((byte)6);
				StringWriter sw = new StringWriter();
				sw.write((new StringBuilder()).append(ex.ice_name()).append("\n").toString());
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				pw.flush();
				_os.writeString(sw.toString());
				_connection.sendResponse(_os, _compress);
			} else
			{
				_connection.sendNoResponse();
			}
		}
		catch (Exception ex)
		{
			if (_instance.initializationData().properties.getPropertyAsIntWithDefault("Ice.Warn.Dispatch", 1) > 0)
				__warning(ex);
			if (_response)
			{
				_os.endWriteEncaps();
				_os.resize(18, false);
				_os.writeByte((byte)7);
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				pw.flush();
				_os.writeString(sw.toString());
				_connection.sendResponse(_os, _compress);
			} else
			{
				_connection.sendNoResponse();
			}
		}
		_connection = null;
	}

}
