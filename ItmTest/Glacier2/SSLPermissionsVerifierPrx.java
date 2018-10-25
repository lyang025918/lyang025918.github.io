// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   SSLPermissionsVerifierPrx.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			SSLInfo, Callback_SSLPermissionsVerifier_authorize, AMI_SSLPermissionsVerifier_authorize

public interface SSLPermissionsVerifierPrx
	extends ObjectPrx
{

	public abstract boolean authorize(SSLInfo sslinfo, StringHolder stringholder);

	public abstract boolean authorize(SSLInfo sslinfo, StringHolder stringholder, Map map);

	public abstract AsyncResult begin_authorize(SSLInfo sslinfo);

	public abstract AsyncResult begin_authorize(SSLInfo sslinfo, Map map);

	public abstract AsyncResult begin_authorize(SSLInfo sslinfo, Callback callback);

	public abstract AsyncResult begin_authorize(SSLInfo sslinfo, Map map, Callback callback);

	public abstract AsyncResult begin_authorize(SSLInfo sslinfo, Callback_SSLPermissionsVerifier_authorize callback_sslpermissionsverifier_authorize);

	public abstract AsyncResult begin_authorize(SSLInfo sslinfo, Map map, Callback_SSLPermissionsVerifier_authorize callback_sslpermissionsverifier_authorize);

	public abstract boolean end_authorize(StringHolder stringholder, AsyncResult asyncresult);

	public abstract boolean authorize_async(AMI_SSLPermissionsVerifier_authorize ami_sslpermissionsverifier_authorize, SSLInfo sslinfo);

	public abstract boolean authorize_async(AMI_SSLPermissionsVerifier_authorize ami_sslpermissionsverifier_authorize, SSLInfo sslinfo, Map map);
}
