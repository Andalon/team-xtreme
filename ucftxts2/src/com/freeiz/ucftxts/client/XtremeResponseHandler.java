/**
 * 
 */
package com.freeiz.ucftxts.client;

import java.util.*;

/**
 * @author Michael Scherer
 *	Allows the caller for the XtremeClient to get a response back
 */
public interface XtremeResponseHandler
{
	/**
	 * Called when the response has been fully received and parsed by the client
	 * @param books the list of books that match the previously given query parameters
	 */
	public abstract void XtremeResponse(Vector<Book> books);
}
