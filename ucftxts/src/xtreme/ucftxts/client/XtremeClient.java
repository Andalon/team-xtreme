/**
 * 
 */
package xtreme.ucftxts.client;

import java.util.*;
import java.io.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.*;

/**
 * @author Michael Scherer
 *	Client for interacting with the server, searching for
 *	books.
 */
public class XtremeClient
{
	public XtremeClient(String url)
	{
		mURL = url;
	}
	
	public void SetURL(String url)
	{
		mURL = url;
	}
	
	public Vector<Book> Query(String Author, String Title) throws Exception
	{
		if (Author != "" && Title != "")
			return FormattedQuery(String.format("author=%s&title=%s", Author, Title));
		else if (Author != "")
			return FormattedQuery(String.format("author=%s", Author));
		else if (Title != "")
			return FormattedQuery(String.format("title=%s", Title));
		else
			return new Vector<Book>();
	}
	
	public Vector<Book> Query(long ISBN) throws Exception
	{
		if (ISBN != 0)
			return FormattedQuery(String.format("isbn=%13d", ISBN));
		else
			return new Vector<Book>();
	}
	
	private Vector<Book> FormattedQuery(String arguments) throws Exception
	{
		Vector<Book> books = null;
		
		BufferedReader in = null;
		
		try
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(mURL);
			
			HttpResponse response = client.execute(request);
			
			in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			///////////////////////////
			// Parse XML Here...
			///////////////////////////
			
			in.close();
		}
		finally
		{
			in.close();
		}
		
		return books;
	}
	
	private String mURL;
}
