package com.freeiz.ucftxts;

import java.util.Vector;

import com.freeiz.ucftxts.client.Book;
import com.freeiz.ucftxts.client.XtremeClient;
import com.freeiz.ucftxts.client.XtremeResponseHandler;

import android.app.Activity;
import android.os.Bundle;


public class LoadingActivity extends Activity implements XtremeResponseHandler
{
	private XtremeClient XClient;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		XClient = new XtremeClient("", this);
		
		startQuery();
		
		
	}
	
	private void startQuery()
	{
		String mAuthor="";
		String mTitle="";
		
		
		
		try {
			XClient.Query(mAuthor, mTitle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
		}
	}

	@Override
	public void XtremeResponse(Vector<Book> books) {
		// TODO Auto-generated method stub
		
	}
}