/**
 * 
 */
package com.freeiz.ucftxts.client;

import java.util.*;
import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

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
	
	public XtremeClient()
	{
		mHandlers = new Vector<XtremeResponseHandler>();
	}
	
	public XtremeClient(String url)
	{
		mURL = url;
		mHandlers = new Vector<XtremeResponseHandler>();
	}
	
	public XtremeClient(String url, XtremeResponseHandler handler)
	{
		mURL = url;
		mHandlers = new Vector<XtremeResponseHandler>();
		mHandlers.add(handler);
	}
	
	public void SetURL(String url)
	{
		mURL = url;
	}
	
	public String GetURL()
	{
		return mURL;
	}
	
	public void AddCallback(XtremeResponseHandler handler)
	{
		mHandlers.add(handler);
	}
	
	public boolean RemoveCallback(XtremeResponseHandler handler)
	{
		return mHandlers.remove(handler);
	}
	
	public boolean Query(String Author, String Title) throws Exception
	{
		if (Author != "" && Title != "")
			return FormattedQuery(String.format("author=%s&title=%s", Author, Title));
		else if (Author != "")
			return FormattedQuery(String.format("author=%s", Author));
		else if (Title != "")
			return FormattedQuery(String.format("title=%s", Title));
		else
			return false;
	}
	
	public boolean Query(long ISBN) throws Exception
	{
		if (ISBN != 0)
			return FormattedQuery(String.format("isbn=%13d", ISBN));
		else
			return false;
	}
	
	private boolean FormattedQuery(String arguments) throws Exception
	{
		//Vector<Book> books = null;
		if (mInUse)
			return false;
		mInUse = true;
		
		BufferedReader in = null;
		
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
		finally
		{
			//in.close();
		}
		
		return true;
	}
	
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
		
		mBuilder.setLength(0);
	}
	
	@Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
    {
		super.startElement(uri, localName, name, attributes);
		
		if (localName.equalsIgnoreCase("Book"))
		{
			mCurrentBook = new Book();
			
			mCurrentBook.SetAuthor(attributes.getValue(uri, "author"));
			mCurrentBook.SetTitle(attributes.getValue(uri, "title"));
			mCurrentBook.SetISBN(Long.parseLong(attributes.getValue(uri, "isbn")));
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
