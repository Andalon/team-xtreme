package com.freeiz.ucftxts;

import java.util.ArrayList;
import java.util.Vector;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;

import com.freeiz.ucftxts.client.Book;
import com.freeiz.ucftxts.client.XtremeResponseHandler;


public class ResultsActivity extends ListActivity implements OnClickListener, XtremeResponseHandler
{
	ArrayList<String> list;
	ArrayAdapter<Book> a;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        //setContentView(R.layout.results);
        
		//Vector<Book> books = (Vector<Book>) savedInstanceState.getSerializable("books");
        
	}
	
	@Override
	public void XtremeResponse(Vector<Book> books) 
	{
		// TODO Auto-generated method stub
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<9; i++)
			sb.append("asdadadasdadasdasd\n");
		for(Book b: books)
		{
			sb.append(b.toString());
			sb.append("\r\n");
		}
	
		
		a = new ArrayAdapter<Book>(this, android.R.layout.simple_list_item_1, books);
        setListAdapter(a);
		
	}

	@Override
	public void onClick(View arg0) 
	{
		// TODO Auto-generated method stub
		
	}
	
}