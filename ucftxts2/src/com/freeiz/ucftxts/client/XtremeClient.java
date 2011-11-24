/**
 * 
 */
package com.freeiz.ucftxts.client;

import java.util.*;
//import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler; 

import com.freeiz.ucftxts.client.Retailer;
import com.freeiz.ucftxts.client.Book;
import com.freeiz.ucftxts.client.XtremeResponseHandler;

/**
 * @author Michael Scherer
 *	Client for interacting with the server, searching for
 *	books.
 */
public class XtremeClient extends DefaultHandler 
{
	private String mURL;
	private Vector<XtremeResponseHandler> mHandlers;
	
	private Vector<Book> mResults;
	private Book mCurrentBook;
	private StringBuilder mBuilder;
	private boolean mInUse;
	
	/**
	 * Builds the client with no URL and no handlers
	 */
	public XtremeClient()
	{
		mURL = "";
		mHandlers = new Vector<XtremeResponseHandler>();
	}
	
	/**
	 * Builds the client with a URL and no handlers
	 * @param url the full URL for the name of the site (and page) that the query is called on
	 */
	public XtremeClient(String url)
	{
		mURL = url;
		mHandlers = new Vector<XtremeResponseHandler>();
	}
	
	/**
	 * Builds the client with a URL and a handler
	 * @param url the full URL for the name of the site (and page) that the query is called on
	 * @param handler the object handling the response
	 */
	public XtremeClient(String url, XtremeResponseHandler handler)
	{
		mURL = url;
		mHandlers = new Vector<XtremeResponseHandler>();
		mHandlers.add(handler);
	}
	
	/**
	 * Sets the URL
	 * @param url the full URL for the name of the site (and page) that the query is called on
	 */
	public void SetURL(String url)
	{
		mURL = url;
	}
	
	/**
	 * Gets the URL
	 * @return the full URL for the name of the site (and page) that the query is called on
	 */
	public String GetURL()
	{
		return mURL;
	}
	
	/**
	 * Adds a callback
	 * @param handler the object handling the request
	 */
	public void AddCallback(XtremeResponseHandler handler)
	{
		mHandlers.add(handler);
	}
	
	/**
	 * Removes the specified handler
	 * @param handler the object handling the request
	 * @return true if the handler was removed, false if it was not found in the set
	 */
	public boolean RemoveCallback(XtremeResponseHandler handler)
	{
		return mHandlers.remove(handler);
	}
	
	/**
	 * Queries the server for book entries that satisfy the given conditions
	 * @param Author name of the author (name or "")
	 * @param Title title of the book (title or "")
	 * @return true if the request was sent, false if it failed to send
	 * @throws Exception the result of a failure
	 */
	public boolean Query(String Author, String Title, String Subject) throws Exception
	{
		String fname="",lname="";
		if (Author != "")
		{
			String[] s = Author.split(" ");
			if (s.length > 1)
			{
				lname = s[s.length-1];
				for (int i=0; i<s.length-1; i++)
				{
					fname += s[i] + ' ';
				}
				fname.substring(0, fname.length()-2);
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		if (Author != "")
		{
			sb.append("fname=");
			sb.append(fname);
			sb.append('&');
			sb.append("lname=");
			sb.append(lname);
			sb.append('&');
		}
		else if (Title != "")
		{
			sb.append("title=");
			sb.append(Title);
			sb.append('&');
		}
		else if (Subject != "")
		{
			sb.append("subject=");
			sb.append(Subject);
			sb.append('&');
		}
		
		if (sb.length() > 0)
			return FormattedQuery(sb.toString());
		else
			return false;
	}
	
	/**
	 * Queries the server for book entries that satisfy the given conditions
	 * @param ISBN the ISBN code for the book
	 * @return true if the request was sent, false if it failed to send
	 * @throws Exception the result of a failure
	 */
	public boolean Query(long ISBN) throws Exception
	{
		if (ISBN > 0)
			return FormattedQuery(String.format("isbn=%13d", ISBN));
		else
			return false;
	}
	
	/**
	 * Takes in a pre-formatted query and actually requests a response from the server
	 * @param arguments the query
	 * @return true if the request was sent, false if it failed to send
	 * @throws Exception the result of a failure
	 */
	private boolean FormattedQuery(String arguments) throws Exception
	{
		//Vector<Book> books = null;
		if (mInUse)
			return false;
		mInUse = true;
		
		if (mURL == "")
		{
			mResults = new Vector<Book>();
			mResults.add(new Book());
			for (XtremeResponseHandler xrh : mHandlers)
			{
				xrh.XtremeResponse(mResults);
			}
			
			mInUse = false;
		}
		
		//BufferedReader in = null;
		
		try
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(mURL);
			
			HttpResponse response = client.execute(request);
			
			///////////////////////////
			// Parse XML Here...
			///////////////////////////
			 
			
			Xml.parse(response.getEntity().getContent(), Xml.Encoding.UTF_8, this);
			
			//in.close();
		}
		catch (Exception e)
		{
			Log.e("XtremeClient", "Query", e);
			/*for (XtremeResponseHandler xrh : mHandlers)
			{
				//mResults.clear();
				xrh.XtremeResponse(null);
			}*/
		}
		finally
		{
			//in.close();
		}
		
		return true;
	}
	
	/*
	 <xml>
		<Book title=”TITLE” author=”AUTHOR” isbn=”XXXXXXXXXXXXX”>
			<Tag>TAG1</Tag>
			<Tag>TAG2</Tag>
			<Tag>TAGN</Tag>
			<Abstract>BOOK ABSTRACT STRING</Abstract>
			<Quote price=”XXXX” website=”site.com/dir/page.htm” address=”1234 LOCATION STREET” />
			<Quote price=”XXXX” website=”retailer.com/blah” address=”1423 LOCATION AVENUE” />
			<Quote price=”XXXX” website=”gimmemoney.org/1234” address=”1 GIMME MONEY WAY” />
		</Book>
	</xml>
	 */
	
	@Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        mBuilder.append(ch, start, length);
    }
	
	
	@Override
	public void endElement(String uri, String localName, String name) throws SAXException
	{
		super.endElement(uri, localName, name);
		
		if (mCurrentBook != null)
		{
			if (localName.equalsIgnoreCase("abstract"))
				mCurrentBook.SetAbstract(mBuilder.toString());
			else if (localName.equalsIgnoreCase("tag"))
				mCurrentBook.AddTag(mBuilder.toString());
		}
		
		// if the xml document ends
		if (localName.equalsIgnoreCase("xml"))
		{
			for (XtremeResponseHandler xrh : mHandlers)
			{
				xrh.XtremeResponse(mResults);
			}
			
			mInUse = false;
		}
		
		mBuilder.setLength(0);
	}
	
	@Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
    {
		super.startElement(uri, localName, name, attributes);
		
		if (localName.equalsIgnoreCase("Book"))
		{
			mCurrentBook = new Book();
			
			mCurrentBook.SetFirstName(attributes.getValue(uri, "fname"));
			mCurrentBook.SetLastName(attributes.getValue(uri, "lname"));
			mCurrentBook.SetTitle(attributes.getValue(uri, "title"));
			mCurrentBook.SetISBN(Long.parseLong(attributes.getValue(uri, "isbn")));
			mCurrentBook.SetSubject(attributes.getValue(uri, "subject"));
		}
		else if (localName.equalsIgnoreCase("Quote") && mCurrentBook != null)
		{
			Retailer r = new Retailer();
			
			r.SetAddress(attributes.getValue(uri, "address"));
			r.SetURL(attributes.getValue(uri, "website"));
			r.SetPrice(Double.parseDouble(attributes.getValue(uri, "price")));
			
			mCurrentBook.AddRetailer(r);
		}
    }
	
	@Override
    public void startDocument() throws SAXException
    {
        super.startDocument();
        mResults = new Vector<Book>();
        mBuilder = new StringBuilder();
    }
	
	@Override
	public void endDocument() throws SAXException
	{
		super.endDocument();
		
		for (XtremeResponseHandler xrh : mHandlers)
		{
			xrh.XtremeResponse(mResults);
		}
		
		mInUse = false;
	}
}
