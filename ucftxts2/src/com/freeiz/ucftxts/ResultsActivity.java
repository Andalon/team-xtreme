package com.freeiz.ucftxts;

import java.util.ArrayList;


import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.freeiz.ucftxts.client.Book;
import com.freeiz.ucftxts.client.Retailer;

/**
 * @author Donald Thomson
 *	Formats results dynamically in a readable way.
 */
public class ResultsActivity extends ListActivity
{
	TableLayout mTableLayout;
	ArrayList<Book> mBooks;
	
	/**
	 * Called when the activity is first created. Shows results and initiates building of output.
	 */
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		mTableLayout = (TableLayout)findViewById(R.id.results_layout);

		//get info and add it into the member field
		Bundle b = getIntent().getExtras();		
		mBooks = new ArrayList<Book>();
		mBooks.addAll((ArrayList<Book>) b.get("books"));
        
		//build output
		buildList();
	}
	
	/**
	 * Builds output from the results.
	 */
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
			Log.e("mBooks", "mBooks is empty");
		}
		
	}
	
	/**
	 * Formats output.
	 * @param b the book for which information needs to be displayed
	 */
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