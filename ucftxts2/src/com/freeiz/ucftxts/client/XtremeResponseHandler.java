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
	public abstract void XtremeResponse(Vector<Book> books);
}
