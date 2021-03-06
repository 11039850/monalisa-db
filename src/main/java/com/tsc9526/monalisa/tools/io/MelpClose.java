/*******************************************************************************************
 *	Copyright (c) 2016, zzg.zhou(11039850@qq.com)
 * 
 *  Monalisa is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU Lesser General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.

 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU Lesser General Public License for more details.

 *	You should have received a copy of the GNU Lesser General Public License
 *	along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************************/
package com.tsc9526.monalisa.tools.io;

import java.io.Closeable;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 
 * @author zzg.zhou(11039850@qq.com)
 */
public class MelpClose {
	private MelpClose(){}
	
	public static void delayClose(final Object x, int delay){
		final Timer timer=new Timer(true);
		
		timer.schedule(new TimerTask() {
			public void run() {
				try{
					close(x);
				}finally{
					timer.cancel();
				}
			}
		}, delay*1000L);
	}
	
	public static void close(Object ... xs){
		for(Object x:xs){
			if(x instanceof Throwable){
				//do nothing
			}else if(x instanceof Closeable){
				close((Closeable)x);
			}else if(x instanceof AutoCloseable){
				close((AutoCloseable)x);
			}else if(x instanceof HttpURLConnection){
				close((HttpURLConnection)x);
			}else if(x!=null){		
				close(x);
			}
		}
	}
	 
	public static void close(Object objectWithCloseMethod){
		close(objectWithCloseMethod,"close");
	}
	 
	public static void close(Object object,String closeMethodName){
		if(object==null){
			return;
		}
		
		try{
			Method m=object.getClass().getMethod(closeMethodName);
			m.setAccessible(true);
			m.invoke(object);
		}catch(NoSuchMethodException e){
			//do nothing
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Eat a throwable message(Exception and Error)
	 * 
	 * @param t throwable object
	 */
	public static void close(Throwable t){
		//do nothing
	}
	
	public static void close(HttpURLConnection c){
		try{
			if(c!=null){
				c.disconnect();
			}
		}catch(Exception e){close(e);}
	}	
	 
	
	public static void close(Closeable c){
		try{
			if(c!=null){
				c.close();
			}
		}catch(Exception e){close(e);}
	}	

	public static void close(AutoCloseable c) {
		try{
			if(c!=null){
				c.close();
			}
		}catch(Exception e){close(e);}		
	}
}

