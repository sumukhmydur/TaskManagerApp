package com.Uttara.taskMgr.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
	
	public void log(String data)
	{
		new Thread( new Runnable()
		{
			public void run()
			{
				BufferedWriter bw = null;
				
				try {
					bw = new BufferedWriter( new FileWriter(Constants.LOGPATH, true) );
					
					Date dt = new Date();
					bw.write(dt.toString() + " : " + data);
					bw.newLine();
					
					if (Constants.LOGGER_MODE)
						System.out.println(data);
					
				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				finally
				{
					if ( bw != null )
					{
						try {
							bw.close();
						} catch (IOException e2) {
							// TODO: handle exception
							e2.printStackTrace();
						}
					}
				}
			}
			
		}).start();
	}
	
	private Logger()
	{
	}
	
	private static Logger obj = null;
	
	public static Logger getInstance()
	{
		if ( obj == null )
			obj = new Logger();
		return obj;
	}
	
}
