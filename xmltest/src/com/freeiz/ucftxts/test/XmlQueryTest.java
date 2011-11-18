/**
 * 
 */
package com.freeiz.ucftxts.test;

import java.util.Vector;

import com.freeiz.ucftxts.client.*;

/**
 * @author Michael Scherer
 *
 */
public class XmlQueryTest implements XtremeResponseHandler 
{
	public static XmlQueryTest _Instance;
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		_Instance = new XmlQueryTest();
	}
	
	private XtremeClient mClient;

	public XmlQueryTest()
	{
		mClient = new XtremeClient("http://ucftxts.freeiz.com/test.php", this);
		
		try {
			mClient.Query("Dog", "Ma");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void XtremeResponse(Vector<Book> books)
	{
		for (Book b : books)
		{
			System.out.println("Book: " + b.GetTitle() + " by " + b.GetAuthor());
		}
	}

}
