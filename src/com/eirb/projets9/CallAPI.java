package com.eirb.projets9;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.eirb.projets9.callbacks.AsyncTaskCompleteListener;

public class CallAPI extends AsyncTask<String, Process, String> {
	
	private ProgressDialog dialog = null;
	private AsyncTaskCompleteListener<String> callback;
	private int number;
	private Context c;
	
	
	public CallAPI(Context a, AsyncTaskCompleteListener<String> cb, int id, boolean displayDialog) {
		if (cb != null && displayDialog == true)
			dialog = new ProgressDialog(a);
		
		this.callback = cb;
		this.number = id;
		c=a;
	}

	protected void onPreExecute() {
		if (callback != null && dialog !=null){
			 dialog.setMessage("Loading ...");
	         dialog.show();
		}
		
	}

	protected String doInBackground(String... arg0) {
		if(isOnline()){
			StringBuilder response = new StringBuilder();
			String url = arg0[0];

			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse execute;
			InputStream content;	
			try {
				execute = client.execute(httpGet);
				content = execute.getEntity().getContent();
				BufferedReader buffer = new BufferedReader(new InputStreamReader(
						content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response.append(s);
					response.append("\n");
				}
			} catch (ClientProtocolException e) {
				System.out.println(e);
			} catch (Exception e) {
				System.out.println(e);
			}

//			System.out.println("Response: "+ response);
//			return response.toString().replace("\n", "").replace("\t", "");
			return response.toString();
		}
		else{
			return null;
		}
		
	}

	protected void onPostExecute(String content) {
		super.onPostExecute(content);
		if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
		if (callback != null && content != null)
			callback.onTaskComplete(content,number);
		if(content == null){
			System.out.println("Toast");
			Toast.makeText(c, "You are not connected to internet", Toast.LENGTH_SHORT).show();
		}
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}

