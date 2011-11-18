/**
 * 
 */
package com.freeiz.ucftxts.client;

import com.freeiz.ucftxts.client.Retailer;
import java.util.*;

/**
 * @author Michael Scherer
 *	Class for holding information about a book, including author,
 *	ISBN, title, abstract, and prices
 */
public class Book
{
	public Book()
	{
		this.mRetailers = new Vector<Retailer>();
		this.mTags = new Vector<String>();
	}
	
	public Book(long isbn, String author, String title, String _abstract)
	{
		this.mISBN = isbn;
		this.mAuthor = author;
		this.mTitle = title;
		this.mAbstract = _abstract;
		
		this.mRetailers = new Vector<Retailer>();
		this.mTags = new Vector<String>();
	}
	
	public boolean AddRetailer(Retailer retailer)
	{
		return mRetailers.add(retailer);
	}
	
	public boolean AddTag(String tag)
	{
		return mTags.add(tag);
	}
	
	public String GetAuthor()
	{
		return mAuthor;
	}
	
	public void SetAuthor(String author)
	{
		mAuthor = author;
	}
	
	public String GetTitle()
	{
		return mTitle;
	}
	
	public void SetTitle(String title)
	{
		mTitle = title;
	}
	
	public String GetAbstract()
	{
		return mAbstract;
	}
	
	public void SetAbstract(String _abstract)
	{
		mAbstract = _abstract;
	}
	
	public long GetISBN()
	{
		return mISBN;
	}
	
	public void SetISBN(long isbn)
	{
		mISBN = isbn;
	}
	
	private String mAuthor;
	private String mTitle;
	private String mAbstract;
	private long mISBN;
	
	private Vector<String> mTags;
	private Vector<Retailer> mRetailers;
}
