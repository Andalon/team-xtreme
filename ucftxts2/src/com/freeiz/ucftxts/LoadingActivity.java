package com.freeiz.ucftxts;

//import java.io.Serializable;
import java.util.ArrayList;
//import java.util.Vector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.freeiz.ucftxts.client.Book;
import com.freeiz.ucftxts.client.XtremeClient;
import com.freeiz.ucftxts.client.XtremeResponseHandler;

/**
 * @author Donald Thomson
 *	Second Activity in Android App. Takes the information from the previous activity and sends it be queried.
 *	Then receives results and passes them to the next activity.
 */
public class LoadingActivity extends Activity implements XtremeResponseHandler
{
	private XtremeClient XClient;
	
	/**
	 * Called when the activity is first created. Shows loading screen.
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
		
		//get information
		Bundle b = getIntent().getExtras();
		
		XClient = new XtremeClient(this.getString(R.string.webURL), this);
		
		startQuery(b);
	}
	
	
	/**
	 * Gathers information from the Bundle. Sends it to be queried
	 */
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

	/**
	 * Handles response from XtremeClient. Starts next activity
	 */
	@Override
	public void XtremeResponse(ArrayList<Book> books) 
	{	
		Intent i = new Intent(this, ResultsActivity.class);
		Bundle b = new Bundle();

		//Bundle information
		b.putSerializable("books", books);
		
		//Pass Bundle to next activity
		i.putExtras(b);
		startActivity(i);
	}
}