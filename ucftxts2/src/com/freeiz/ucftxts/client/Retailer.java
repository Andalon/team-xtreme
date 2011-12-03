/**
 * 
 */
package com.freeiz.ucftxts.client;

import java.io.Serializable;

/**
 * @author Michael Scherer
 *	Class containing all the information about a given
 *	retailer, the price that they offer, their location
 */
public class Retailer implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2533494878586476310L;
	private String mName;
	private double mPrice;
	private String mAddress;
	private String mURL;
	
	/**
	 * Creates an empty Retailer instance
	 */
	public Retailer()
	{
	}
	
	/**
	 * Creates a new retailer
	 * @param name the name of the retailer
	 * @param price the price of the book at this store
	 * @param address the address of the store
	 * @param url the website of the store
	 */
	public Retailer(String name, double price, String address, String url)
	{
		this.mName = name;
		this.mPrice = price;
		this.mAddress = address;
		this.mURL = url;
	}
	
	/**
	 * Gets the name of the store
	 * @return the name of the store
	 */
	public String GetName()
	{
		return mName;
	}
	
	/**
	 * Sets the name of the store
	 * @param name the name of the store
	 */
	public void SetName(String name)
	{
		mName = name;
	}
	
	/**
	 * Gets the price of the book at the store
	 * @return price of the book at the store
	 */
	public double GetPrice()
	{
		return mPrice;
	}
	
	/**
	 * Sets the price of the book at the store
	 * @param price the price of the book at the store
	 */
	public void SetPrice(double price)
	{
		mPrice = price;
	}
	
	/**
	 * Gets the address of the store
	 * @return the street address
	 */
	public String GetAddress()
	{
		return mAddress;
	}
	
	/**
	 * Sets the address of the store
	 * @param address the street address
	 */
	public void SetAddress(String address)
	{
		mAddress = address;
	}
	
	/**
	 * Gets the website associated with the store
	 * @return the store's website
	 */
	public String GetURL()
	{
		return mURL;
	}
	
	/**
	 * Sets the website for the store
	 * @param url the store's website
	 */
	public void SetURL(String url)
	{
		mURL = url;
	}
}
