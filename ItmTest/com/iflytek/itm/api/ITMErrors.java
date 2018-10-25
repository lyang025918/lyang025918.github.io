// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   ITMErrors.java

package com.iflytek.itm.api;


public final class ITMErrors extends Enum
{

	public static final ITMErrors ITM_SUCCESS;
	public static final ITMErrors ITM_FAIL;
	public static final ITMErrors ITM_ERROR_INVALID_PARAM;
	public static final ITMErrors ITM_ERROR_INVALID_PARAM_VALUE;
	public static final ITMErrors ITM_ERROR_FIELD_NOT_SAME;
	public static final ITMErrors ITM_ERROR_FIELD_TYPE_NOT_SUPPORT;
	public static final ITMErrors ITM_ERROR_DOCUMENT_ALREADY_EXISTED;
	public static final ITMErrors ITM_ERROR_INDEX_IO_EXCEPTION;
	public static final ITMErrors ITM_ERROR_NO_SUB_INDEX_DIR;
	public static final ITMErrors ITM_ERROR_INVALID_MAINTAIN_ACTION;
	public static final ITMErrors ITM_ERROR_PRIMARY_KEY_NOT_UNIQUE;
	public static final ITMErrors ITM_ERROR_ANALYZER_NOT_FOUND;
	public static final ITMErrors ITM_ERROR_ANALYZER_NOT_ACCESSIBLE;
	public static final ITMErrors ITM_ERROR_ANALYZER_NOT_INSTANTIABLE;
	public static final ITMErrors ITM_ERROR_INVALID_QUERY_SYNTAX;
	public static final ITMErrors ITM_ERROR_MINING_TYPE_NOT_SUPPORT;
	public static final ITMErrors ITM_ERROR_DIRECTORY_CAN_NOT_READ;
	public static final ITMErrors ITM_ERROR_DIRECTORY_CAN_NOT_WRITE;
	public static final ITMErrors ITM_ERROR_INVALID_DIRECTORY;
	public static final ITMErrors ITM_ERROR_INDEX_NOT_EXISTED;
	public static final ITMErrors ITM_ERROR_INVALID_RULE_SYNTAX;
	public static final ITMErrors ITM_ERROR_UPDATE_DOCUMENT_NOT_EXISTED;
	public static final ITMErrors ITM_ERROR_INVALID_RULE_REPEATED;
	public static final ITMErrors ITM_ERROR_DELETE_DOCUMENT_NOT_EXISTED;
	public static final ITMErrors ITM_ERROR_GROUP_FIELD_NOT_SUPPORTED;
	public static final ITMErrors ITM_ERROR_MAINTAIN_DELETE_FAIL;
	public static final ITMErrors ITM_ERROR_SORT_FIELD_NOT_EXISTED;
	public static final ITMErrors ITM_ERROR_FIELD_REPEAT;
	public static final ITMErrors ITM_ERROR_FIELD_NAME_EMPTY;
	public static final ITMErrors ITM_ERROR_FIELD_ILLEGAL;
	public static final ITMErrors ITM_ERROR_MINING_FIELD_NOT_EXISTED;
	public static final ITMErrors ITM_ERROR_ID_FIELD_NOT_EXISTED;
	public static final ITMErrors ITM_ERROR_PARAM_NOT_EXISTED;
	public static final ITMErrors ITM_ERROR_PARAM_EMPTY;
	public static final ITMErrors ITM_ERROR_PARAM_VALUE_EMPTY;
	public static final ITMErrors ITM_ERROR_NUMBER_FORMAT;
	public static final ITMErrors ITM_ERROR_NUMBER_RANGE;
	public static final ITMErrors ITM_ERROR_BOOL_VALUE;
	public static final ITMErrors ITM_ERROR_INDEX_IS_LOCKED;
	public static final ITMErrors ITM_ERROR_NETWORK_ERROR;
	public static final ITMErrors ITM_UNKOWN_ERROR;
	private final int code;
	private final String msg;
	private static final ITMErrors $VALUES[];

	public static ITMErrors[] values()
	{
		return (ITMErrors[])$VALUES.clone();
	}

	public static ITMErrors valueOf(String name)
	{
		return (ITMErrors)Enum.valueOf(com/iflytek/itm/api/ITMErrors, name);
	}

	private ITMErrors(String s, int i, int code, String msg)
	{
		super(s, i);
		this.code = code;
		this.msg = msg;
	}

	public String msg()
	{
		return msg;
	}

	public static String msg(int errcode)
	{
		ITMErrors aitmerrors[] = values();
		int i = aitmerrors.length;
		for (int j = 0; j < i; j++)
		{
			ITMErrors error = aitmerrors[j];
			if (error.code() == errcode)
				return error.msg();
		}

		return "";
	}

	public int code()
	{
		return code;
	}

	public String toString()
	{
		return (new StringBuilder()).append(code).append(": ").append(msg).toString();
	}

	static 
	{
		ITM_SUCCESS = new ITMErrors("ITM_SUCCESS", 0, 0, "success");
		ITM_FAIL = new ITMErrors("ITM_FAIL", 1, -1, "fail");
		ITM_ERROR_INVALID_PARAM = new ITMErrors("ITM_ERROR_INVALID_PARAM", 2, 1001, "Invalid param");
		ITM_ERROR_INVALID_PARAM_VALUE = new ITMErrors("ITM_ERROR_INVALID_PARAM_VALUE", 3, 1002, "Invalid param value");
		ITM_ERROR_FIELD_NOT_SAME = new ITMErrors("ITM_ERROR_FIELD_NOT_SAME", 4, 1003, "Field info not the same while build twice");
		ITM_ERROR_FIELD_TYPE_NOT_SUPPORT = new ITMErrors("ITM_ERROR_FIELD_TYPE_NOT_SUPPORT", 5, 1004, "Field type not support");
		ITM_ERROR_DOCUMENT_ALREADY_EXISTED = new ITMErrors("ITM_ERROR_DOCUMENT_ALREADY_EXISTED", 6, 1005, "Document already existed");
		ITM_ERROR_INDEX_IO_EXCEPTION = new ITMErrors("ITM_ERROR_INDEX_IO_EXCEPTION", 7, 1006, "Index io exception");
		ITM_ERROR_NO_SUB_INDEX_DIR = new ITMErrors("ITM_ERROR_NO_SUB_INDEX_DIR", 8, 1007, "No sub index dir");
		ITM_ERROR_INVALID_MAINTAIN_ACTION = new ITMErrors("ITM_ERROR_INVALID_MAINTAIN_ACTION", 9, 1008, "Invalid maintain action");
		ITM_ERROR_PRIMARY_KEY_NOT_UNIQUE = new ITMErrors("ITM_ERROR_PRIMARY_KEY_NOT_UNIQUE", 10, 1009, "Primary key not unique");
		ITM_ERROR_ANALYZER_NOT_FOUND = new ITMErrors("ITM_ERROR_ANALYZER_NOT_FOUND", 11, 1010, "Analyzer not found");
		ITM_ERROR_ANALYZER_NOT_ACCESSIBLE = new ITMErrors("ITM_ERROR_ANALYZER_NOT_ACCESSIBLE", 12, 1011, "Analyzer not accessible");
		ITM_ERROR_ANALYZER_NOT_INSTANTIABLE = new ITMErrors("ITM_ERROR_ANALYZER_NOT_INSTANTIABLE", 13, 1012, "Analyzer not instantiable");
		ITM_ERROR_INVALID_QUERY_SYNTAX = new ITMErrors("ITM_ERROR_INVALID_QUERY_SYNTAX", 14, 1013, "Invalid query syntax");
		ITM_ERROR_MINING_TYPE_NOT_SUPPORT = new ITMErrors("ITM_ERROR_MINING_TYPE_NOT_SUPPORT", 15, 1014, "Mining type not support");
		ITM_ERROR_DIRECTORY_CAN_NOT_READ = new ITMErrors("ITM_ERROR_DIRECTORY_CAN_NOT_READ", 16, 1015, "Directory can not read");
		ITM_ERROR_DIRECTORY_CAN_NOT_WRITE = new ITMErrors("ITM_ERROR_DIRECTORY_CAN_NOT_WRITE", 17, 1016, "Directory can not write");
		ITM_ERROR_INVALID_DIRECTORY = new ITMErrors("ITM_ERROR_INVALID_DIRECTORY", 18, 1017, "Directory is not valid");
		ITM_ERROR_INDEX_NOT_EXISTED = new ITMErrors("ITM_ERROR_INDEX_NOT_EXISTED", 19, 1018, "Index not existed");
		ITM_ERROR_INVALID_RULE_SYNTAX = new ITMErrors("ITM_ERROR_INVALID_RULE_SYNTAX", 20, 1019, "Invalid rule syntax");
		ITM_ERROR_UPDATE_DOCUMENT_NOT_EXISTED = new ITMErrors("ITM_ERROR_UPDATE_DOCUMENT_NOT_EXISTED", 21, 1020, "Update document is not existed");
		ITM_ERROR_INVALID_RULE_REPEATED = new ITMErrors("ITM_ERROR_INVALID_RULE_REPEATED", 22, 1021, "Rule name repeated");
		ITM_ERROR_DELETE_DOCUMENT_NOT_EXISTED = new ITMErrors("ITM_ERROR_DELETE_DOCUMENT_NOT_EXISTED", 23, 1022, "To delete document is not existed");
		ITM_ERROR_GROUP_FIELD_NOT_SUPPORTED = new ITMErrors("ITM_ERROR_GROUP_FIELD_NOT_SUPPORTED", 24, 1023, "To group field is not supported");
		ITM_ERROR_MAINTAIN_DELETE_FAIL = new ITMErrors("ITM_ERROR_MAINTAIN_DELETE_FAIL", 25, 1024, "Maintain delete failed");
		ITM_ERROR_SORT_FIELD_NOT_EXISTED = new ITMErrors("ITM_ERROR_SORT_FIELD_NOT_EXISTED", 26, 1025, "Sort field is not existed");
		ITM_ERROR_FIELD_REPEAT = new ITMErrors("ITM_ERROR_FIELD_REPEAT", 27, 1026, "Field name repeated");
		ITM_ERROR_FIELD_NAME_EMPTY = new ITMErrors("ITM_ERROR_FIELD_NAME_EMPTY", 28, 1027, "Field name is empty");
		ITM_ERROR_FIELD_ILLEGAL = new ITMErrors("ITM_ERROR_FIELD_ILLEGAL", 29, 1028, "Field name is illegal, field name can't contain \\ = /");
		ITM_ERROR_MINING_FIELD_NOT_EXISTED = new ITMErrors("ITM_ERROR_MINING_FIELD_NOT_EXISTED", 30, 1029, "Mining field is not existed");
		ITM_ERROR_ID_FIELD_NOT_EXISTED = new ITMErrors("ITM_ERROR_ID_FIELD_NOT_EXISTED", 31, 1030, "Id field is not existed");
		ITM_ERROR_PARAM_NOT_EXISTED = new ITMErrors("ITM_ERROR_PARAM_NOT_EXISTED", 32, 1200, "Invalid param, param not exist");
		ITM_ERROR_PARAM_EMPTY = new ITMErrors("ITM_ERROR_PARAM_EMPTY", 33, 1201, "Invalid param, param is empty");
		ITM_ERROR_PARAM_VALUE_EMPTY = new ITMErrors("ITM_ERROR_PARAM_VALUE_EMPTY", 34, 1220, "Invalid param value, param value is empty");
		ITM_ERROR_NUMBER_FORMAT = new ITMErrors("ITM_ERROR_NUMBER_FORMAT", 35, 1221, "Invalid param value, number format error");
		ITM_ERROR_NUMBER_RANGE = new ITMErrors("ITM_ERROR_NUMBER_RANGE", 36, 1222, "Invalid param value£¬ number out of range");
		ITM_ERROR_BOOL_VALUE = new ITMErrors("ITM_ERROR_BOOL_VALUE", 37, 1223, "Invalid param value£¬ bool value error");
		ITM_ERROR_INDEX_IS_LOCKED = new ITMErrors("ITM_ERROR_INDEX_IS_LOCKED", 38, 4001, "Index is locked!");
		ITM_ERROR_NETWORK_ERROR = new ITMErrors("ITM_ERROR_NETWORK_ERROR", 39, 5001, "Network has something error.");
		ITM_UNKOWN_ERROR = new ITMErrors("ITM_UNKOWN_ERROR", 40, 9999, "Unkown error");
		$VALUES = (new ITMErrors[] {
			ITM_SUCCESS, ITM_FAIL, ITM_ERROR_INVALID_PARAM, ITM_ERROR_INVALID_PARAM_VALUE, ITM_ERROR_FIELD_NOT_SAME, ITM_ERROR_FIELD_TYPE_NOT_SUPPORT, ITM_ERROR_DOCUMENT_ALREADY_EXISTED, ITM_ERROR_INDEX_IO_EXCEPTION, ITM_ERROR_NO_SUB_INDEX_DIR, ITM_ERROR_INVALID_MAINTAIN_ACTION, 
			ITM_ERROR_PRIMARY_KEY_NOT_UNIQUE, ITM_ERROR_ANALYZER_NOT_FOUND, ITM_ERROR_ANALYZER_NOT_ACCESSIBLE, ITM_ERROR_ANALYZER_NOT_INSTANTIABLE, ITM_ERROR_INVALID_QUERY_SYNTAX, ITM_ERROR_MINING_TYPE_NOT_SUPPORT, ITM_ERROR_DIRECTORY_CAN_NOT_READ, ITM_ERROR_DIRECTORY_CAN_NOT_WRITE, ITM_ERROR_INVALID_DIRECTORY, ITM_ERROR_INDEX_NOT_EXISTED, 
			ITM_ERROR_INVALID_RULE_SYNTAX, ITM_ERROR_UPDATE_DOCUMENT_NOT_EXISTED, ITM_ERROR_INVALID_RULE_REPEATED, ITM_ERROR_DELETE_DOCUMENT_NOT_EXISTED, ITM_ERROR_GROUP_FIELD_NOT_SUPPORTED, ITM_ERROR_MAINTAIN_DELETE_FAIL, ITM_ERROR_SORT_FIELD_NOT_EXISTED, ITM_ERROR_FIELD_REPEAT, ITM_ERROR_FIELD_NAME_EMPTY, ITM_ERROR_FIELD_ILLEGAL, 
			ITM_ERROR_MINING_FIELD_NOT_EXISTED, ITM_ERROR_ID_FIELD_NOT_EXISTED, ITM_ERROR_PARAM_NOT_EXISTED, ITM_ERROR_PARAM_EMPTY, ITM_ERROR_PARAM_VALUE_EMPTY, ITM_ERROR_NUMBER_FORMAT, ITM_ERROR_NUMBER_RANGE, ITM_ERROR_BOOL_VALUE, ITM_ERROR_INDEX_IS_LOCKED, ITM_ERROR_NETWORK_ERROR, 
			ITM_UNKOWN_ERROR
		});
	}
}
