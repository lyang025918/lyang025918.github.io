// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   IOUtils.java

package org.apache.lucene.util;

import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.*;
import java.util.Iterator;
import org.apache.lucene.store.Directory;

public final class IOUtils
{

	public static final String UTF_8 = "UTF-8";
	public static final Charset CHARSET_UTF_8 = Charset.forName("UTF-8");
	private static final Method SUPPRESS_METHOD;

	private IOUtils()
	{
	}

	public static transient void closeWhileHandlingException(Exception priorException, Closeable objects[])
		throws Exception, IOException
	{
		Throwable th = null;
		Closeable arr$[] = objects;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$;)
		{
			Closeable object = arr$[i$];
			try
			{
				if (object != null)
					object.close();
				continue;
			}
			catch (Throwable t)
			{
				addSuppressed(((Throwable) (priorException != null ? ((Throwable) (priorException)) : th)), t);
				if (th == null)
					th = t;
				i$++;
			}
		}

		if (priorException != null)
			throw priorException;
		if (th != null)
		{
			if (th instanceof IOException)
				throw (IOException)th;
			if (th instanceof RuntimeException)
				throw (RuntimeException)th;
			if (th instanceof Error)
				throw (Error)th;
			else
				throw new RuntimeException(th);
		} else
		{
			return;
		}
	}

	public static void closeWhileHandlingException(Exception priorException, Iterable objects)
		throws Exception, IOException
	{
		Throwable th = null;
		Iterator i$ = objects.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Closeable object = (Closeable)i$.next();
			try
			{
				if (object != null)
					object.close();
			}
			catch (Throwable t)
			{
				addSuppressed(((Throwable) (priorException != null ? ((Throwable) (priorException)) : th)), t);
				if (th == null)
					th = t;
			}
		} while (true);
		if (priorException != null)
			throw priorException;
		if (th != null)
		{
			if (th instanceof IOException)
				throw (IOException)th;
			if (th instanceof RuntimeException)
				throw (RuntimeException)th;
			if (th instanceof Error)
				throw (Error)th;
			else
				throw new RuntimeException(th);
		} else
		{
			return;
		}
	}

	public static transient void close(Closeable objects[])
		throws IOException
	{
		Throwable th = null;
		Closeable arr$[] = objects;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$;)
		{
			Closeable object = arr$[i$];
			try
			{
				if (object != null)
					object.close();
				continue;
			}
			catch (Throwable t)
			{
				addSuppressed(th, t);
				if (th == null)
					th = t;
				i$++;
			}
		}

		if (th != null)
		{
			if (th instanceof IOException)
				throw (IOException)th;
			if (th instanceof RuntimeException)
				throw (RuntimeException)th;
			if (th instanceof Error)
				throw (Error)th;
			else
				throw new RuntimeException(th);
		} else
		{
			return;
		}
	}

	public static void close(Iterable objects)
		throws IOException
	{
		Throwable th = null;
		Iterator i$ = objects.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Closeable object = (Closeable)i$.next();
			try
			{
				if (object != null)
					object.close();
			}
			catch (Throwable t)
			{
				addSuppressed(th, t);
				if (th == null)
					th = t;
			}
		} while (true);
		if (th != null)
		{
			if (th instanceof IOException)
				throw (IOException)th;
			if (th instanceof RuntimeException)
				throw (RuntimeException)th;
			if (th instanceof Error)
				throw (Error)th;
			else
				throw new RuntimeException(th);
		} else
		{
			return;
		}
	}

	public static transient void closeWhileHandlingException(Closeable objects[])
	{
		Closeable arr$[] = objects;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			Closeable object = arr$[i$];
			try
			{
				if (object != null)
					object.close();
			}
			catch (Throwable t) { }
		}

	}

	public static void closeWhileHandlingException(Iterable objects)
	{
		Iterator i$ = objects.iterator();
		do
		{
			if (!i$.hasNext())
				break;
			Closeable object = (Closeable)i$.next();
			try
			{
				if (object != null)
					object.close();
			}
			catch (Throwable t) { }
		} while (true);
	}

	private static final void addSuppressed(Throwable exception, Throwable suppressed)
	{
		if (SUPPRESS_METHOD != null && exception != null && suppressed != null)
			try
			{
				SUPPRESS_METHOD.invoke(exception, new Object[] {
					suppressed
				});
			}
			catch (Exception e) { }
	}

	public static Reader getDecodingReader(InputStream stream, Charset charSet)
	{
		CharsetDecoder charSetDecoder = charSet.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
		return new BufferedReader(new InputStreamReader(stream, charSetDecoder));
	}

	public static Reader getDecodingReader(File file, Charset charSet)
		throws IOException
	{
		FileInputStream stream;
		boolean success;
		stream = null;
		success = false;
		Reader reader1;
		stream = new FileInputStream(file);
		Reader reader = getDecodingReader(((InputStream) (stream)), charSet);
		success = true;
		reader1 = reader;
		if (!success)
			close(new Closeable[] {
				stream
			});
		return reader1;
		Exception exception;
		exception;
		if (!success)
			close(new Closeable[] {
				stream
			});
		throw exception;
	}

	public static Reader getDecodingReader(Class clazz, String resource, Charset charSet)
		throws IOException
	{
		InputStream stream;
		boolean success;
		stream = null;
		success = false;
		Reader reader1;
		stream = clazz.getResourceAsStream(resource);
		Reader reader = getDecodingReader(stream, charSet);
		success = true;
		reader1 = reader;
		if (!success)
			close(new Closeable[] {
				stream
			});
		return reader1;
		Exception exception;
		exception;
		if (!success)
			close(new Closeable[] {
				stream
			});
		throw exception;
	}

	public static transient void deleteFilesIgnoringExceptions(Directory dir, String files[])
	{
		String arr$[] = files;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++)
		{
			String name = arr$[i$];
			try
			{
				dir.deleteFile(name);
			}
			catch (Throwable ignored) { }
		}

	}

	public static void copy(File source, File target)
		throws IOException
	{
		FileInputStream fis;
		FileOutputStream fos;
		fis = null;
		fos = null;
		fis = new FileInputStream(source);
		fos = new FileOutputStream(target);
		byte buffer[] = new byte[8192];
		int len;
		while ((len = fis.read(buffer)) > 0) 
			fos.write(buffer, 0, len);
		close(new Closeable[] {
			fis, fos
		});
		break MISSING_BLOCK_LABEL_91;
		Exception exception;
		exception;
		close(new Closeable[] {
			fis, fos
		});
		throw exception;
	}

	static 
	{
		Method m;
		try
		{
			m = java/lang/Throwable.getMethod("addSuppressed", new Class[] {
				java/lang/Throwable
			});
		}
		catch (Exception e)
		{
			m = null;
		}
		SUPPRESS_METHOD = m;
	}
}
