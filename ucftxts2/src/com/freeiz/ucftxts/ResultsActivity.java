package com.freeiz.ucftxts;

import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.freeiz.ucftxts.client.Book;
import com.freeiz.ucftxts.client.XtremeResponseHandler;


public class ResultsActivity extends Activity implements OnClickListener, XtremeResponseHandler
{

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.results);
        
        
	}
	
	@Override
	public void XtremeResponse(Vector<Book> books) 
	{
		// TODO Auto-generated method stub
		
		StringBuilder sb = new StringBuilder();
		for(Book b: books)
		{
			sb.append(b.toString());
			sb.append("\r\n");
		}
		
		TextView mTV = new TextView(this);
		
        mTV.setText(sb.toString());
	}

	@Override
	public void onClick(View arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	
}