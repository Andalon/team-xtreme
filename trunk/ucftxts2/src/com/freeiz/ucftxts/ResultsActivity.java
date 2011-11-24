package com.freeiz.ucftxts;

import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.freeiz.ucftxts.client.Book;
import com.freeiz.ucftxts.client.XtremeResponseHandler;


public class ResultsActivity extends Activity implements OnClickListener, XtremeResponseHandler
{

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        
        Button scanButton = (Button)findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this);
	}
	
	@Override
	public void XtremeResponse(Vector<Book> books) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	
}