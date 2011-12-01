package com.freeiz.ucftxts;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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
		{
			Toast.makeText(this, "No items found!", Toast.LENGTH_LONG);
			Log.e("mBooks", "mBooks is empty, yo");
		}
		
	}
	
	private void addEntry(Book b)
	{		
		//first row
		TextView title = new TextView(this);
		
		TextView author = new TextView(this);
		
		title.setBackgroundColor(0xFF444444);
		title.setTextColor(0xFFCC9900);
		title.setPadding(5, 5, 5, 5);
		title.setTextSize(22);
		
	
		title.setText(b.GetTitle());
		
		TableRow row0 = new TableRow(this);
		row0.addView(title);
		
		TableRow.LayoutParams params = (TableRow.LayoutParams)title.getLayoutParams();
		params.span = 2;
		params.width = LayoutParams.WRAP_CONTENT;
		title.setLayoutParams(params);
		
		mTableLayout.addView(row0);
		
		author.setTextSize(18);
		author.setText(" Author: " + b.GetLastName() + ", " +b.GetFirstName());
		
		TableRow row1 = new TableRow(this);
		row1.addView(author);
		
		TableRow.LayoutParams params2 = (TableRow.LayoutParams)author.getLayoutParams();
		params2.span = 2;
		author.setLayoutParams(params2);
		
		mTableLayout.addView(row1);
		
		//second row
		TextView isbn = new TextView(this);
		TextView subject = new TextView(this);
		
		isbn.setText(" ISBN: " + b.GetISBN());
		subject.setText("  Subject: " + b.GetSubject());
		
		TableRow row2 = new TableRow(this);
		row2.addView(isbn);
		row2.addView(subject);
		
		mTableLayout.addView(row2);
		
		for(Retailer r: b.GetRetailers())
		{	
			TableRow rRow = new TableRow(this);
			TextView rName = new TextView(this);
			TextView rPrice = new TextView(this);
			
			rPrice.setGravity(Gravity.RIGHT);
			
			rName.setText(" " + r.GetName());
			//rRow.addView(spacer);
			rPrice.setText(String.format("  $%.2f",r.GetPrice()));
			
			rRow.addView(rName);
			rRow.addView(rPrice);
			
			mTableLayout.addView(rRow);
		}
		
		//blank row between each entry
		TableRow blank = new TableRow(this);
		TextView spacer = new TextView(this);
		
		//spacer.setText("     ");
		
		blank.addView(spacer);
		mTableLayout.addView(blank);
		
	}
	
}