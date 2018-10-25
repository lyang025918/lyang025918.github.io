// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PermissionsVerifierPrx.java

package Glacier2;

import Ice.*;
import java.util.Map;

// Referenced classes of package Glacier2:
//			Callback_PermissionsVerifier_checkPermissions, AMI_PermissionsVerifier_checkPermissions

public interface PermissionsVerifierPrx
	extends ObjectPrx
{

	public abstract boolean checkPermissions(String s, String s1, StringHolder stringholder);

	public abstract boolean checkPermissions(String s, String s1, StringHolder stringholder, Map map);

	public abstract AsyncResult begin_checkPermissions(String s, String s1);

	public abstract AsyncResult begin_checkPermissions(String s, String s1, Map map);

	public abstract AsyncResult begin_checkPermissions(String s, String s1, Callback callback);

	public abstract AsyncResult begin_checkPermissions(String s, String s1, Map map, Callback callback);

	public abstract AsyncResult begin_checkPermissions(String s, String s1, Callback_PermissionsVerifier_checkPermissions callback_permissionsverifier_checkpermissions);

	public abstract AsyncResult begin_checkPermissions(String s, String s1, Map map, Callback_PermissionsVerifier_checkPermissions callback_permissionsverifier_checkpermissions);

	public abstract boolean end_checkPermissions(StringHolder stringholder, AsyncResult asyncresult);

	public abstract boolean checkPermissions_async(AMI_PermissionsVerifier_checkPermissions ami_permissionsverifier_checkpermissions, String s, String s1);

	public abstract boolean checkPermissions_async(AMI_PermissionsVerifier_checkPermissions ami_permissionsverifier_checkpermissions, String s, String s1, Map map);
}
