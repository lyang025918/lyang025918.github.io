// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   PropertiesI.java

package Ice;

import IceInternal.Property;
import IceInternal.PropertyNames;
import IceInternal.Util;
import IceUtilInternal.StringUtil;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package Ice:
//			InitializationException, LocalException, FileException, SyscallException, 
//			Properties, Util, Logger, StringSeqHolder

public final class PropertiesI
	implements Properties
{
	static class PropertyValue
	{

		public String value;
		public boolean used;

		public PropertyValue(PropertyValue v)
		{
			value = v.value;
			used = v.used;
		}

		public PropertyValue(String v, boolean u)
		{
			value = v;
			used = u;
		}
	}


	private static final int ParseStateKey = 0;
	private static final int ParseStateValue = 1;
	private HashMap _properties;
	static final boolean $assertionsDisabled = !Ice/PropertiesI.desiredAssertionStatus();

	public synchronized String getProperty(String key)
	{
		PropertyValue pv = (PropertyValue)_properties.get(key);
		if (pv != null)
		{
			pv.used = true;
			return pv.value;
		} else
		{
			return "";
		}
	}

	public synchronized String getPropertyWithDefault(String key, String value)
	{
		PropertyValue pv = (PropertyValue)_properties.get(key);
		if (pv != null)
		{
			pv.used = true;
			return pv.value;
		} else
		{
			return value;
		}
	}

	public int getPropertyAsInt(String key)
	{
		return getPropertyAsIntWithDefault(key, 0);
	}

	public synchronized int getPropertyAsIntWithDefault(String key, int value)
	{
		PropertyValue pv;
		pv = (PropertyValue)_properties.get(key);
		if (pv == null)
			break MISSING_BLOCK_LABEL_67;
		pv.used = true;
		return Integer.parseInt(pv.value);
		NumberFormatException ex;
		ex;
		Util.getProcessLogger().warning((new StringBuilder()).append("numeric property ").append(key).append(" set to non-numeric value, defaulting to ").append(value).toString());
		return value;
	}

	public String[] getPropertyAsList(String key)
	{
		return getPropertyAsListWithDefault(key, null);
	}

	public synchronized String[] getPropertyAsListWithDefault(String key, String value[])
	{
		if (value == null)
			value = new String[0];
		PropertyValue pv = (PropertyValue)_properties.get(key);
		if (pv != null)
		{
			pv.used = true;
			String result[] = StringUtil.splitString(pv.value, ", \t\r\n");
			if (result == null)
			{
				Util.getProcessLogger().warning((new StringBuilder()).append("mismatched quotes in property ").append(key).append("'s value, returning default value").toString());
				return value;
			}
			if (result.length == 0)
				result = value;
			return result;
		} else
		{
			return value;
		}
	}

	public synchronized Map getPropertiesForPrefix(String prefix)
	{
		HashMap result = new HashMap();
		Iterator i$ = _properties.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
			String key = (String)p.getKey();
			if (prefix.length() == 0 || key.startsWith(prefix))
			{
				PropertyValue pv = (PropertyValue)p.getValue();
				pv.used = true;
				result.put(key, pv.value);
			}
		} while (true);
		return result;
	}

	public void setProperty(String key, String value)
	{
		if (key != null)
			key = key.trim();
		Logger logger = Util.getProcessLogger();
		if (key == null || key.length() == 0)
			throw new InitializationException("Attempt to set property with empty key");
		int dotPos = key.indexOf('.');
		if (dotPos != -1)
		{
			String prefix = key.substring(0, dotPos);
			for (int i = 0; PropertyNames.validProps[i] != null; i++)
			{
				String pattern = PropertyNames.validProps[i][0].pattern();
				dotPos = pattern.indexOf('.');
				if (!$assertionsDisabled && dotPos == -1)
					throw new AssertionError();
				String propPrefix = pattern.substring(0, dotPos - 1);
				if (!propPrefix.equals(prefix))
					continue;
				boolean found = false;
				for (int j = 0; PropertyNames.validProps[i][j] != null && !found; j++)
				{
					pattern = PropertyNames.validProps[i][j].pattern();
					Pattern pComp = Pattern.compile(pattern);
					Matcher m = pComp.matcher(key);
					found = m.matches();
					if (!found || !PropertyNames.validProps[i][j].deprecated())
						continue;
					logger.warning((new StringBuilder()).append("deprecated property: ").append(key).toString());
					if (PropertyNames.validProps[i][j].deprecatedBy() != null)
						key = PropertyNames.validProps[i][j].deprecatedBy();
				}

				if (!found)
					logger.warning((new StringBuilder()).append("unknown property: ").append(key).toString());
			}

		}
		synchronized (this)
		{
			if (value != null && value.length() > 0)
			{
				PropertyValue pv = (PropertyValue)_properties.get(key);
				if (pv != null)
					pv.value = value;
				else
					pv = new PropertyValue(value, false);
				_properties.put(key, pv);
			} else
			{
				_properties.remove(key);
			}
		}
	}

	public synchronized String[] getCommandLineOptions()
	{
		String result[] = new String[_properties.size()];
		int i = 0;
		for (Iterator i$ = _properties.entrySet().iterator(); i$.hasNext();)
		{
			java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
			result[i++] = (new StringBuilder()).append("--").append((String)p.getKey()).append("=").append(((PropertyValue)p.getValue()).value).toString();
		}

		if (!$assertionsDisabled && i != result.length)
			throw new AssertionError();
		else
			return result;
	}

	public String[] parseCommandLineOptions(String pfx, String options[])
	{
		if (pfx.length() > 0 && pfx.charAt(pfx.length() - 1) != '.')
			pfx = (new StringBuilder()).append(pfx).append('.').toString();
		pfx = (new StringBuilder()).append("--").append(pfx).toString();
		ArrayList result = new ArrayList();
		String arr$[] = options;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String opt = arr$[i$];
			if (opt.startsWith(pfx))
			{
				if (opt.indexOf('=') == -1)
					opt = (new StringBuilder()).append(opt).append("=1").toString();
				parseLine(opt.substring(2));
			} else
			{
				result.add(opt);
			}
		}

		return (String[])result.toArray(new String[0]);
	}

	public String[] parseIceCommandLineOptions(String options[])
	{
		String args[] = options;
		for (int i = 0; PropertyNames.clPropNames[i] != null; i++)
			args = parseCommandLineOptions(PropertyNames.clPropNames[i], args);

		return args;
	}

	public void load(String file)
	{
		PushbackInputStream is;
		Exception exception;
		if (System.getProperty("os.name").startsWith("Windows") && file.startsWith("HKLM\\"))
		{
			String regQuery = (new StringBuilder()).append("reg query ").append(file).toString();
			try
			{
				Process process = Runtime.getRuntime().exec(regQuery);
				process.waitFor();
				if (process.exitValue() != 0)
				{
					InitializationException ie = new InitializationException();
					ie.reason = (new StringBuilder()).append("Could not read Windows registry key `").append(file).append("'").toString();
					throw ie;
				}
				InputStream is = process.getInputStream();
				StringWriter sw = new StringWriter();
				int c;
				while ((c = is.read()) != -1) 
					sw.write(c);
				String result[] = sw.toString().split("\n");
				String arr$[] = result;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++)
				{
					String line = arr$[i$];
					int pos = line.indexOf("REG_SZ");
					if (pos != -1)
					{
						setProperty(line.substring(0, pos).trim(), line.substring(pos + 6, line.length()).trim());
					} else
					{
						pos = line.indexOf("REG_EXPAND_SZ");
						if (pos != -1)
						{
							String name = line.substring(0, pos).trim();
							line = line.substring(pos + 13, line.length()).trim();
							do
							{
								int start = line.indexOf("%", 0);
								int end = line.indexOf("%", start + 1);
								if (start == -1 || end == -1)
									break;
								String envKey = line.substring(start + 1, end);
								String envValue = System.getenv(envKey);
								if (envValue == null)
									envValue = "";
								envKey = (new StringBuilder()).append("%").append(envKey).append("%").toString();
								do
									line = line.replace(envKey, envValue);
								while (line.indexOf(envKey) != -1);
							} while (true);
							setProperty(name, line);
						}
					}
				}

			}
			catch (LocalException ex)
			{
				throw ex;
			}
			catch (Exception ex)
			{
				throw new InitializationException((new StringBuilder()).append("Could not read Windows registry key `").append(file).append("'").toString(), ex);
			}
		} else
		{
			is = null;
			try
			{
				InputStream f = Util.openResource(getClass().getClassLoader(), file);
				if (f == null)
				{
					FileException fe = new FileException();
					fe.path = file;
					throw fe;
				}
				byte bom[] = new byte[3];
				is = new PushbackInputStream(f, bom.length);
				int read = is.read(bom, 0, bom.length);
				if ((read < 3 || bom[0] != -17 || bom[1] != -69 || bom[2] != -65) && read > 0)
					is.unread(bom, 0, read);
				InputStreamReader isr = new InputStreamReader(is, "UTF-8");
				BufferedReader br = new BufferedReader(isr);
				parse(br);
			}
			catch (IOException ex)
			{
				throw new FileException(0, file, ex);
			}
			finally
			{
				if (is == null) goto _L0; else goto _L0
			}
			if (is != null)
				try
				{
					is.close();
				}
				catch (Throwable ex) { }
		}
		break MISSING_BLOCK_LABEL_642;
		try
		{
			is.close();
		}
		catch (Throwable ex) { }
		throw exception;
	}

	public synchronized Properties _clone()
	{
		return new PropertiesI(this);
	}

	public synchronized List getUnusedProperties()
	{
		List unused = new ArrayList();
		Iterator i$ = _properties.entrySet().iterator();
		do
		{
			if (!i$.hasNext())
				break;
			java.util.Map.Entry p = (java.util.Map.Entry)i$.next();
			PropertyValue pv = (PropertyValue)p.getValue();
			if (!pv.used)
				unused.add(p.getKey());
		} while (true);
		return unused;
	}

	PropertiesI(PropertiesI props)
	{
		_properties = new HashMap();
		java.util.Map.Entry p;
		for (Iterator i$ = props._properties.entrySet().iterator(); i$.hasNext(); _properties.put(p.getKey(), new PropertyValue((PropertyValue)p.getValue())))
			p = (java.util.Map.Entry)i$.next();

	}

	PropertiesI()
	{
		_properties = new HashMap();
	}

	PropertiesI(StringSeqHolder args, Properties defaults)
	{
		_properties = new HashMap();
		if (defaults != null)
			_properties = new HashMap(((PropertiesI)defaults)._properties);
		boolean loadConfigFiles = false;
		for (int i = 0; i < args.value.length; i++)
		{
			if (!args.value[i].startsWith("--Ice.Config"))
				continue;
			String line = args.value[i];
			if (line.indexOf('=') == -1)
				line = (new StringBuilder()).append(line).append("=1").toString();
			parseLine(line.substring(2));
			loadConfigFiles = true;
			String arr[] = new String[args.value.length - 1];
			System.arraycopy(args.value, 0, arr, 0, i);
			if (i < args.value.length - 1)
				System.arraycopy(args.value, i + 1, arr, i, args.value.length - i - 1);
			args.value = arr;
		}

		if (!loadConfigFiles)
			loadConfigFiles = !_properties.containsKey("Ice.Config");
		if (loadConfigFiles)
			loadConfig();
		args.value = parseIceCommandLineOptions(args.value);
	}

	private void parse(BufferedReader in)
	{
		String line;
		try
		{
			while ((line = in.readLine()) != null) 
				parseLine(line);
		}
		catch (IOException ex)
		{
			throw new SyscallException(ex);
		}
	}

	private void parseLine(String line)
	{
		String key = "";
		String value = "";
		int state = 0;
		String whitespace = "";
		String escapedspace = "";
		boolean finished = false;
		int i = 0;
		do
		{
			if (i >= line.length())
				break;
			char c = line.charAt(i);
			switch (state)
			{
			case 0: // '\0'
				switch (c)
				{
				case 92: // '\\'
					if (i < line.length() - 1)
					{
						c = line.charAt(++i);
						switch (c)
						{
						case 35: // '#'
						case 61: // '='
						case 92: // '\\'
							key = (new StringBuilder()).append(key).append(whitespace).toString();
							whitespace = "";
							key = (new StringBuilder()).append(key).append(c).toString();
							break;

						case 32: // ' '
							if (key.length() != 0)
								whitespace = (new StringBuilder()).append(whitespace).append(c).toString();
							break;

						default:
							key = (new StringBuilder()).append(key).append(whitespace).toString();
							whitespace = "";
							key = (new StringBuilder()).append(key).append('\\').toString();
							key = (new StringBuilder()).append(key).append(c).toString();
							break;
						}
					} else
					{
						key = (new StringBuilder()).append(key).append(whitespace).toString();
						key = (new StringBuilder()).append(key).append(c).toString();
					}
					break;

				case 9: // '\t'
				case 10: // '\n'
				case 13: // '\r'
				case 32: // ' '
					if (key.length() != 0)
						whitespace = (new StringBuilder()).append(whitespace).append(c).toString();
					break;

				case 61: // '='
					whitespace = "";
					state = 1;
					break;

				case 35: // '#'
					finished = true;
					break;

				default:
					key = (new StringBuilder()).append(key).append(whitespace).toString();
					whitespace = "";
					key = (new StringBuilder()).append(key).append(c).toString();
					break;
				}
				break;

			case 1: // '\001'
				switch (c)
				{
				case 92: // '\\'
					if (i < line.length() - 1)
					{
						c = line.charAt(++i);
						switch (c)
						{
						case 35: // '#'
						case 61: // '='
						case 92: // '\\'
							value = (new StringBuilder()).append(value).append(value.length() != 0 ? whitespace : escapedspace).toString();
							whitespace = "";
							escapedspace = "";
							value = (new StringBuilder()).append(value).append(c).toString();
							break;

						case 32: // ' '
							whitespace = (new StringBuilder()).append(whitespace).append(c).toString();
							escapedspace = (new StringBuilder()).append(escapedspace).append(c).toString();
							break;

						default:
							value = (new StringBuilder()).append(value).append(value.length() != 0 ? whitespace : escapedspace).toString();
							whitespace = "";
							escapedspace = "";
							value = (new StringBuilder()).append(value).append('\\').toString();
							value = (new StringBuilder()).append(value).append(c).toString();
							break;
						}
					} else
					{
						value = (new StringBuilder()).append(value).append(value.length() != 0 ? whitespace : escapedspace).toString();
						value = (new StringBuilder()).append(value).append(c).toString();
					}
					break;

				case 9: // '\t'
				case 10: // '\n'
				case 13: // '\r'
				case 32: // ' '
					if (value.length() != 0)
						whitespace = (new StringBuilder()).append(whitespace).append(c).toString();
					break;

				case 35: // '#'
					value = (new StringBuilder()).append(value).append(escapedspace).toString();
					finished = true;
					break;

				default:
					value = (new StringBuilder()).append(value).append(value.length() != 0 ? whitespace : escapedspace).toString();
					whitespace = "";
					escapedspace = "";
					value = (new StringBuilder()).append(value).append(c).toString();
					break;
				}
				break;
			}
			if (finished)
				break;
			i++;
		} while (true);
		value = (new StringBuilder()).append(value).append(escapedspace).toString();
		if (state == 0 && key.length() != 0 || state == 1 && key.length() == 0)
		{
			Util.getProcessLogger().warning((new StringBuilder()).append("invalid config file entry: \"").append(line).append("\"").toString());
			return;
		}
		if (key.length() == 0)
		{
			return;
		} else
		{
			setProperty(key, value);
			return;
		}
	}

	private void loadConfig()
	{
		String value = getProperty("Ice.Config");
		if (value.length() == 0 || value.equals("1"))
			try
			{
				value = System.getenv("ICE_CONFIG");
				if (value == null)
					value = "";
			}
			catch (SecurityException ex)
			{
				value = "";
			}
		if (value.length() > 0)
		{
			String arr$[] = value.split(",");
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++)
			{
				String file = arr$[i$];
				load(file);
			}

		}
		_properties.put("Ice.Config", new PropertyValue(value, true));
	}

}
