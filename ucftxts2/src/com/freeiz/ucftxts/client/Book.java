/**
 * 
 */
package com.freeiz.ucftxts.client;

import com.freeiz.ucftxts.client.Retailer;

import java.io.Serializable;
import java.util.*;

/**
 * @author Michael Scherer
 *	Class for holding information about a book, including author,
 *	ISBN, title, abstract, and prices
 */
public class Book implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 234550827802557253L;
	
	/**
	 * Creates an empty book instance
	 */
	public Book()
	{
		this.mRetailers = new ArrayList<Retailer>();
		this.mTags = new ArrayList<String>();
	}
	
	/**
	 * Creates a new book
	 * @param isbn the isbn
	 * @param fname first name
	 * @param lname last name
	 * @param title title
	 * @param _abstract the book's abstract
	 */
	public Book(long isbn, String fname, String lname, String title, String _abstract)
	{
		this.mISBN = isbn;
		this.mFName = fname;
		this.mLName = lname;
		this.mTitle = title;
		this.mAbstract = _abstract;
		
		this.mRetailers = new ArrayList<Retailer>();
		this.mTags = new ArrayList<String>();
	}
	
	/**
	 * Add a retailer to the book
	 * @param retailer the retailer
	 * @return true if the add was successful
	 */
	public boolean AddRetailer(Retailer retailer)
	{
		return mRetailers.add(retailer);
	}
	
	/**
	 * Gets a list of all retailers associated with the book
	 * @return the list of retailers
	 */
	public List<Retailer> GetRetailers()
	{
		return mRetailers;
	}
	
	/**
	 * Adds a tag associated with the book
	 * @param tag a subject tag
	 * @return true if the add was successful
	 */
	public boolean AddTag(String tag)
	{
		return mTags.add(tag);
	}
	
	/**
	 * Gets the first name
	 * @return the first name
	 */
	public String GetFirstName()
	{
		return mFName;
	}
	
	/**
	 * Sets the author's first name
	 * @param fname the author's first name
	 */
	public void SetFirstName(String fname)
	{
		mFName = fname;
	}
	
	/**
	 * Gets the author's last name
	 * @return the author's last name
	 */
	public String GetLastName()
	{
		return mLName;
	}
	
	/**
	 * Sets the author's last name
	 * @param lname the author's last name
	 */
	public void SetLastName(String lname)
	{
		mLName = lname;
	}
	
	/**
	 * Gets the title
	 * @return the title of the book
	 */
	public String GetTitle()
	{
		return mTitle;
	}
	
	/**
	 * Sets the title
	 * @param title the title of the book
	 */
	public void SetTitle(String title)
	{
		mTitle = title;
	}
	
	/**
	 * Gets the book's abstract
	 * @return the book's abstract
	 */
	public String GetAbstract()
	{
		return mAbstract;
	}
	
	/**
	 * Sets the book's abstract
	 * @param _abstract the book's abstract
	 */
	public void SetAbstract(String _abstract)
	{
		mAbstract = _abstract;
	}
	
	/**
	 * Gets the ISBN
	 * @return the book's ISBN
	 */
	public long GetISBN()
	{
		return mISBN;
	}
	
	/**
	 * Sets the ISBN
	 * @param isbn the isbn
	 */
	public void SetISBN(long isbn)
	{
		mISBN = isbn;
	}
	
	/**
	 * Gets the subject
	 * @return the book's subject
	 */
	public String GetSubject()
	{
		return mSubject;
	}
	
	/**
	 * Sets the subject
	 * @param subject the subject of the book
	 */
	public void SetSubject(String subject)
	{
		mSubject = subject;
	}
	
	/**
	 * Returns a string representing the information in the book
	 */
	@Override
	public String toString()
	{
		return "t=" + mTitle + " fn=" + mFName + " ln=" + mLName + " s=" + mSubject + " isbn=" + mISBN;
	}
	
	private String mFName;
	private String mLName;
	private String mTitle;
	private String mAbstract;
	private String mSubject;
	private long mISBN;
	
	private ArrayList<String> mTags;
	private ArrayList<Retailer> mRetailers;
}
