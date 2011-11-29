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
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.freeiz.ucftxts.client.Book;
import com.freeiz.ucftxts.client.Retailer;


public class ResultsActivity extends ListActivity
{
	//ArrayList<String> list;
	//ArrayAdapter<String> a;
	TableLayout mTableLayout;
	ArrayList<Book> mBooks;
	
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		mTableLayout = (TableLayout)findViewById(R.id.results_layout);
		
		Bundle b = getIntent().getExtras();
		
		
		
		mBooks = new ArrayList<Book>();
		
		mBooks.addAll((ArrayList<Book>) b.get("books"));
        
		buildList();
        
		
		
	}
	
	
	private void buildList()
	{
		
		if (!mBooks.isEmpty())
			for(Book b: mBooks )
			{
				addEntry(b);
			}
		else
			Log.e("ResultsActivity", "YO DAWG, YOUR VECTOR IS EMPTY");
		
	}
	
	private void addEntry(Book b)
	{
		//first row
		TextView title = new TextView(this);
		TextView author = new TextView(this);
		
		title.setText(b.GetTitle());
		author.setText(b.GetLastName() + ", " +b.GetFirstName());
		
		TableRow row1 = new TableRow(this);
		row1.addView(title);
		row1.addView(author);
		
		mTableLayout.addView(row1);
		
		//second row
		TextView isbn = new TextView(this);
		TextView subject = new TextView(this);
		
		isbn.setText(b.GetISBN() + "");
		subject.setText(b.GetSubject());
		
		TableRow row2 = new TableRow(this);
		row2.addView(isbn);
		row2.addView(subject);
		
		mTableLayout.addView(row2);
		
		for(Retailer r: b.GetRetailers())
		{
			TableRow rRow = new TableRow(this);
			TextView rName = new TextView(this);
			TextView rPrice = new TextView(this);
			
			rName.setText(r.GetName());
			rPrice.setText(r.GetPrice() + "");
			
			rRow.addView(rName);
			rRow.addView(rPrice);
			
			mTableLayout.addView(rRow);
		}
		
	}
	
}