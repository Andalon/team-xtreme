/**
 * 
 */
package com.freeiz.ucftxts.client;

import java.util.*;

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
	private ArrayList<XtremeResponseHandler> mHandlers;
	
	private ArrayList<Book> mResults;
	private Book mCurrentBook;
	private StringBuilder mBuilder;
	private boolean mInUse;
	
	/**
	 * Builds the client with no URL and no handlers
	 */
	public XtremeClient()
	{
		mURL = "";
		mHandlers = new ArrayList<XtremeResponseHandler>();
	}
	
	/**
	 * Builds the client with a URL and no handlers
	 * @param url the full URL for the name of the site (and page) that the query is called on
	 */
	public XtremeClient(String url)
	{
		mURL = url;
		mHandlers = new ArrayList<XtremeResponseHandler>();
	}
	
	/**
	 * Builds the client with a URL and a handler
	 * @param url the full URL for the name of the site (and page) that the query is called on
	 * @param handler the object handling the response
	 */
	public XtremeClient(String url, XtremeResponseHandler handler)
	{
		mURL = url;
		mHandlers = new ArrayList<XtremeResponseHandler>();
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
		// if the Author string is not empty, then parse out <Firstname> <Lastname>
		//	if it's only a single word, assume it's a last name
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
			else if (s.length == 1)
			{
				lname = s[0];
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		if (Author != "")
		{
			sb.append("fname=");
			sb.append(fname.trim());
			sb.append('&');
			sb.append("lname=");
			sb.append(lname.trim());
			sb.append('&');
		}
		
		if (Title != "")
		{
			sb.append("title=");
			sb.append(Title.trim());
			sb.append('&');
		}
		
		if (Subject != "")
		{
			sb.append("subject=");
			sb.append(Subject.trim());
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
			return FormattedQuery(String.format("isbn=%013d", ISBN));
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
		//ArrayList<Book> books = null;
		arguments = arguments.replaceAll(" ", "%20");
		
		if (mInUse)
			return false;
		mInUse = true;
		
		if (mURL == "")
		{
			mResults = new ArrayList<Book>();
			mResults.add(new Book());
			for (XtremeResponseHandler xrh : mHandlers)
			{
				xrh.XtremeResponse(mResults);
			}
			
			mInUse = false;
		}
		
		try
		{
			
			// Make a connection to the server
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(mURL + "?" + arguments);
			Log.i("XtremeClient", mURL + "?" + arguments);
			
			HttpResponse response = client.execute(request);
			
			// Parse XML Here...
			Xml.parse(new XtremeStreamFilter(response.getEntity().getContent()), Xml.Encoding.UTF_8, this);
			
			
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
		
		return true;
	}
	
	/**
	 * Gets called by the XML parser whenever text occurs between an opening and closing xml tag
	 * @param ch the characters read in
	 * @param start the starting index in the ch buffer
	 * @param length the length of the data in the ch buffer
	 */
	@Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        super.characters(ch, start, length);
        mBuilder.append(ch, start, length);
    }
	
	/**
	 * Called by the XML parser when a closing tag is encountered
	 * @param uri the full path of the xml tag
	 * @param localName the name of the tag
	 * @param name the fully qualified name of the tag
	 */
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
			else if (localName.equalsIgnoreCase("book"))
			{
				mResults.add(mCurrentBook);
				Log.d("Parser", mCurrentBook.toString());
			}
		}
		
		//Log.d("Parser", localName + '@' + uri + '=' + mCurrentBook.toString());
		
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
	
	/**
	 * Called when the beginning of a tag is encountered
	 * @param uri the full path of the xml tag
	 * @param localName the name of the tag
	 * @param name the fully qualified name of the tag
	 * @param attributes the attributes associated with the tag
	 */
	@Override
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException
    {
		super.startElement(uri, localName, name, attributes);
		
		// If it's a book tag, create a new book
		if (localName.equalsIgnoreCase("Book"))
		{
			mCurrentBook = new Book();
			
			mCurrentBook.SetFirstName(attributes.getValue(uri, "fname"));
			mCurrentBook.SetLastName(attributes.getValue(uri, "lname"));
			mCurrentBook.SetTitle(attributes.getValue(uri, "title"));
			mCurrentBook.SetISBN(Long.parseLong(attributes.getValue(uri, "isbn")));
			mCurrentBook.SetSubject(attributes.getValue(uri, "subject"));
			
			//Log.d("Parser", localName + '@' + uri + '=' + mCurrentBook.toString());
		}
		// If it's a book tag, add a retailer to the list for the current book
		else if (localName.equalsIgnoreCase("Quote") && mCurrentBook != null)
		{
			Retailer r = new Retailer();
			
			r.SetName(attributes.getValue(uri, "name"));
			r.SetAddress(attributes.getValue(uri, "address"));
			r.SetURL(attributes.getValue(uri, "website"));
			r.SetPrice(Double.parseDouble(attributes.getValue(uri, "price")));
			
			mCurrentBook.AddRetailer(r);
		}
    }
	
	/**
	 * Called when the xml document starts
	 */
	@Override
    public void startDocument() throws SAXException
    {
        super.startDocument();
        mResults = new ArrayList<Book>();
        mBuilder = new StringBuilder();
    }
	
	/**
	 * Called when the xml document ends
	 */
	@Override
	public void endDocument() throws SAXException
	{
		super.endDocument();
		
		// Let all the callbacks know that a result has finished
		for (XtremeResponseHandler xrh : mHandlers)
		{
			xrh.XtremeResponse(mResults);
		}
		
		mInUse = false;
	}
	
	/**
	 * Receive notification of a recoverable parser error.
	 * @param e the error
	 */
	@Override
	public void error(SAXParseException e)
	{
		// ignore!
		Log.e("Parser:XtremeClient", "recoverable error claimed");
	}
	
	/**
	 * Report a fatal XML parsing error.
	 * @param e the error
	 */
	@Override
	public void fatalError(SAXParseException e)
	{
		// ignore!
		Log.e("Parser:XtremeClient", "fatalError claimed");
	}
	
	/**
	 * Report a warning
	 * @param e the warning
	 */
	@Override
	public void warning(SAXParseException e) throws SAXException
	{
		// TODO Auto-generated method stub
		Log.e("Parser:XtremeClient", "warning");
		super.warning(e);
	}
}
