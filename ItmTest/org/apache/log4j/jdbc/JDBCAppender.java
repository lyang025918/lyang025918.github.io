// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
// Source File Name:   JDBCAppender.java

package org.apache.log4j.jdbc;

import java.sql.*;
import java.util.*;
import org.apache.log4j.*;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

public class JDBCAppender extends AppenderSkeleton
	implements Appender
{

	protected String databaseURL;
	protected String databaseUser;
	protected String databasePassword;
	protected Connection connection;
	protected String sqlStatement;
	protected int bufferSize;
	protected ArrayList buffer;
	protected ArrayList removes;
	private boolean locationInfo;

	public JDBCAppender()
	{
		databaseURL = "jdbc:odbc:myDB";
		databaseUser = "me";
		databasePassword = "mypassword";
		connection = null;
		sqlStatement = "";
		bufferSize = 1;
		locationInfo = false;
		buffer = new ArrayList(bufferSize);
		removes = new ArrayList(bufferSize);
	}

	public boolean getLocationInfo()
	{
		return locationInfo;
	}

	public void setLocationInfo(boolean flag)
	{
		locationInfo = flag;
	}

	public void append(LoggingEvent event)
	{
		event.getNDC();
		event.getThreadName();
		event.getMDCCopy();
		if (locationInfo)
			event.getLocationInformation();
		event.getRenderedMessage();
		event.getThrowableStrRep();
		buffer.add(event);
		if (buffer.size() >= bufferSize)
			flushBuffer();
	}

	protected String getLogStatement(LoggingEvent event)
	{
		return getLayout().format(event);
	}

	protected void execute(String sql)
		throws SQLException
	{
		Connection con;
		Statement stmt;
		con = null;
		stmt = null;
		con = getConnection();
		stmt = con.createStatement();
		stmt.executeUpdate(sql);
		if (stmt != null)
			stmt.close();
		closeConnection(con);
		break MISSING_BLOCK_LABEL_62;
		Exception exception;
		exception;
		if (stmt != null)
			stmt.close();
		closeConnection(con);
		throw exception;
	}

	protected void closeConnection(Connection connection1)
	{
	}

	protected Connection getConnection()
		throws SQLException
	{
		if (!DriverManager.getDrivers().hasMoreElements())
			setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
		if (connection == null)
			connection = DriverManager.getConnection(databaseURL, databaseUser, databasePassword);
		return connection;
	}

	public void close()
	{
		flushBuffer();
		try
		{
			if (connection != null && !connection.isClosed())
				connection.close();
		}
		catch (SQLException e)
		{
			errorHandler.error("Error closing connection", e, 0);
		}
		closed = true;
	}

	public void flushBuffer()
	{
		Iterator i;
		removes.ensureCapacity(buffer.size());
		i = buffer.iterator();
_L1:
		LoggingEvent logEvent;
		if (!i.hasNext())
			break MISSING_BLOCK_LABEL_107;
		logEvent = (LoggingEvent)i.next();
		String sql = getLogStatement(logEvent);
		execute(sql);
		removes.add(logEvent);
		  goto _L1
		SQLException e;
		e;
		errorHandler.error("Failed to excute sql", e, 2);
		removes.add(logEvent);
		  goto _L1
		Exception exception;
		exception;
		removes.add(logEvent);
		throw exception;
		buffer.removeAll(removes);
		removes.clear();
		return;
	}

	public void finalize()
	{
		close();
	}

	public boolean requiresLayout()
	{
		return true;
	}

	public void setSql(String s)
	{
		sqlStatement = s;
		if (getLayout() == null)
			setLayout(new PatternLayout(s));
		else
			((PatternLayout)getLayout()).setConversionPattern(s);
	}

	public String getSql()
	{
		return sqlStatement;
	}

	public void setUser(String user)
	{
		databaseUser = user;
	}

	public void setURL(String url)
	{
		databaseURL = url;
	}

	public void setPassword(String password)
	{
		databasePassword = password;
	}

	public void setBufferSize(int newBufferSize)
	{
		bufferSize = newBufferSize;
		buffer.ensureCapacity(bufferSize);
		removes.ensureCapacity(bufferSize);
	}

	public String getUser()
	{
		return databaseUser;
	}

	public String getURL()
	{
		return databaseURL;
	}

	public String getPassword()
	{
		return databasePassword;
	}

	public int getBufferSize()
	{
		return bufferSize;
	}

	public void setDriver(String driverClass)
	{
		try
		{
			Class.forName(driverClass);
		}
		catch (Exception e)
		{
			errorHandler.error("Failed to load driver", e, 0);
		}
	}
}
