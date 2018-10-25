// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   _FileParserDelD.java

package IceGrid;

import Ice.*;
import IceInternal.Direct;
import IceInternal.LocalExceptionWrapper;
import java.util.Map;

// Referenced classes of package IceGrid:
//			ApplicationDescriptorHolder, ParseException, _FileParserDel, AdminPrx, 
//			ApplicationDescriptor, FileParser

public final class _FileParserDelD extends _ObjectDelD
	implements _FileParserDel
{

	static final boolean $assertionsDisabled = !IceGrid/_FileParserDelD.desiredAssertionStatus();

	public _FileParserDelD()
	{
	}

	public ApplicationDescriptor parse(final String xmlFile, AdminPrx adminProxy, Map __ctx)
		throws LocalExceptionWrapper, ParseException
	{
		final Current __current;
		final ApplicationDescriptorHolder __result;
		Direct __direct;
		__current = new Current();
		__initCurrent(__current, "parse", OperationMode.Idempotent, __ctx);
		__result = new ApplicationDescriptorHolder();
		__direct = null;
		__direct = new Direct(adminProxy) {

			final Current val$__current;
			final ApplicationDescriptorHolder val$__result;
			final String val$xmlFile;
			final AdminPrx val$adminProxy;
			final _FileParserDelD this$0;

			public DispatchStatus run(Ice.Object __obj)
			{
				FileParser __servant;
				UserException __ex;
				__servant = null;
				try
				{
					__servant = (FileParser)__obj;
				}
				// Misplaced declaration of an exception variable
				catch (UserException __ex)
				{
					throw new OperationNotExistException(__current.id, __current.facet, __current.operation);
				}
				__result.value = __servant.parse(xmlFile, adminProxy, __current);
				return DispatchStatus.DispatchOK;
				__ex;
				setUserException(__ex);
				return DispatchStatus.DispatchUserException;
			}

			
				throws UserException
			{
				this$0 = _FileParserDelD.this;
				__current = current;
				__result = applicationdescriptorholder;
				xmlFile = s;
				adminProxy = adminprx;
				super(x0);
			}
		};
		ApplicationDescriptor applicationdescriptor;
		DispatchStatus __status = __direct.servant().__collocDispatch(__direct);
		if (__status == DispatchStatus.DispatchUserException)
			__direct.throwUserException();
		if (!$assertionsDisabled && __status != DispatchStatus.DispatchOK)
			throw new AssertionError();
		applicationdescriptor = __result.value;
		__direct.destroy();
		return applicationdescriptor;
		Exception exception;
		exception;
		__direct.destroy();
		throw exception;
		ParseException __ex;
		__ex;
		throw __ex;
		__ex;
		throw __ex;
		__ex;
		LocalExceptionWrapper.throwWrapper(__ex);
		return __result.value;
	}

}
