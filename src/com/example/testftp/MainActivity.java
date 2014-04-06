package com.example.testftp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.os.Environment;
import android.os.AsyncTask;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.net.Socket;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.net.SocketException;
import java.lang.Integer;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		((Button)findViewById(R.id.test)).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				(new MyTask()).execute();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	class MyTask extends AsyncTask<Void, Void, Integer> {
		@Override
		protected Integer doInBackground(Void... params) {
			try {
				//Socket s = new Socket();					
				//SocketAddress socketAddress = new InetSocketAddress("192.168.2.68", 25001);
				//s.connect(socketAddress, 10000);
				
				
				
				String path = Environment.getExternalStorageDirectory().getPath();
		        FileInputStream in = new FileInputStream(new File(path + "/cors.png"));  
		        boolean flag = uploadFile("192.168.2.68", 21, "administrator", "19801010", "/", "cors.png", in);  
		        System.out.println(flag);  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    }  
			
			return 0;
		}
	}

	public static boolean uploadFile(String url,int port,String username, String password, String path, String filename, InputStream input) {  
	    boolean success = false;  
	    FTPClient ftp = new FTPClient();  
	    try {  
	        int reply;  
	        ftp.connect(url, port);//连接FTP服务器  
	        //如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器  
	        ftp.login(username, password);//登录  
	        reply = ftp.getReplyCode();  
	        if (!FTPReply.isPositiveCompletion(reply)) {  
	            ftp.disconnect();  
	            return success;  
	        }  
	        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
	        ftp.changeWorkingDirectory(path);  
	        ftp.storeFile(filename, input);           
	          
	        input.close();  
	        ftp.logout();  
	        success = true;  
	    }
	    catch (SocketException e) {
	    	e.printStackTrace();
	    }
	    catch (IOException e) {  
	        e.printStackTrace();  
	    } finally {  
	        if (ftp.isConnected()) {  
	            try {  
	                ftp.disconnect();  
	            } catch (IOException ioe) {  
	            }  
	        }  
	    }  
	    return success;  
	}
}
