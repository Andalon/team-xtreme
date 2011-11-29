package com.freeiz.ucftxts;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.freeiz.ucftxts.client.Book;


public class ResultsActivity extends ListActivity implements OnClickListener
{
	ArrayList<String> list;
	ArrayAdapter<String> a;
	PopupWindow pw;
	List<Book> mBooks;
	
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
        //setContentView(R.layout.results);
		Bundle b = getIntent().getExtras();
		
		mBooks = new List<Book>();
		
        //Vector<Book> books = new Vector<Book>(); 
        //Log.d("books", b.get("books").toString());
		mBooks.addAll((ArrayList<Book>) b.get("books"));
		////////////////////Vector<Book> books = (Vector<Book>) savedInstanceState.getSerializable("books");
        
		buildList();
        displayList();
        
        ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		
	}
	
	
	private void buildList()
	{
		list = new ArrayList<String>();
		if (!mBooks.isEmpty())
			for(Book b: mBooks )
			{
				list.add(b.GetTitle());
			}
		else
			Log.e("ResultsActivity", "YO DAWG, YOUR VECTOR IS EMPTY");
	}
	
	private void displayList()
	{
		a = new ArrayAdapter<String> (this, android.R.layout.simple_list_item_1, list);
		setListAdapter(a);
		

	}
	
	public void createPopup(int pos)
	{
		pw = new PopupWindow(this);
		pw.setContentView(findViewById(R.layout.popup));
		TextView title = (TextView) findViewById(R.id.popup_title);
		title.setText(mBooks.get(pos).GetTitle());
	}
	
	
	
	
	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		if(v.equals((ListView)findViewById(v.getId())))
		{
			int pos = ((ListView)v).getSelectedItemPosition();
			createPopup(pos);
		}

	}
	
}