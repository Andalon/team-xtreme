package com.freeiz.ucftxts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.freeiz.ucftxts.client.Book;
import com.freeiz.ucftxts.client.XtremeClient;
import com.freeiz.ucftxts.client.XtremeResponseHandler;


public class LoadingActivity extends Activity implements XtremeResponseHandler
{
	private XtremeClient XClient;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		Bundle b = getIntent().getExtras();
		
		XClient = new XtremeClient(this.getString(R.string.webURL), this);
		
		startQuery(b);
	}
	
	private void startQuery(Bundle b)
	{
		long mIsbn = b.getLong("isbn");
		String mAuthor = b.getString("author");
		String mTitle = b.getString("title");
		String mTopic = b.getString("topic");
		
		try {
			if(mIsbn==0)
				XClient.Query(mAuthor, mTitle, mTopic);
			else
				XClient.Query(mIsbn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void XtremeResponse(ArrayList<Book> books) 
	{
		// TODO Auto-generated method stub
		
		Intent i = new Intent(this, ResultsActivity.class);
		
		Bundle b = new Bundle();
		
		//b.put("books", books);
		
		b.putSerializable("books", books);
		
		i.putExtras(b);
		
		startActivity(i);
	}
}