package com.freeiz.ucftxts;

//import java.util.Vector;

//import com.freeiz.ucftxts.client.*;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.TextView;


/**
 * @author Donald Thomson
 *	Android App main activity. Handles all information gathering and passing that information to the next activity.
 */
public class Ucftxts2Activity extends Activity implements OnClickListener
{
//	private XtremeClient XClient;
    /** 
     * Called when the activity is first created. Sets display
     */
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        
        //Make buttons clickable
        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        
        Button scanButton = (Button)findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this);
    }
    
    
    /**
     * Handles button clicks
     */
    public void onClick(View v)
    {	
    	EditText isbn = (EditText)findViewById(R.id.editText1);
    	EditText title = (EditText)findViewById(R.id.editText2);
    	EditText author = (EditText)findViewById(R.id.editText3);
    	EditText topic = (EditText)findViewById(R.id.editText4);
    	
    	if(v.equals((Button)findViewById(R.id.searchButton)))
    	{
    		//////////
    		//Get all the information from the text fields
    		/////////
    		
    		long mIsbn = 0;
    		if(!(isbn.getText().toString()).equals("")) //make sure we're not trying to parse an empty string
    			mIsbn = Long.parseLong(isbn.getText().toString());
    		
    		String mAuthor = author.getText().toString();
    		String mTitle = title.getText().toString();
    		String mTopic = topic.getText().toString();
    		
    		
    		Intent i = new Intent(this, LoadingActivity.class);
    		Bundle b = new Bundle();
    		
    		
    		//Package info in a Bundle
    		b.putLong("isbn", mIsbn);
    		b.putString("author", mAuthor);
    		b.putString("title", mTitle);
    		b.putString("topic", mTopic);
    		
    		//Pass the Bundle to the next activity
    		i.putExtras(b);   		
    		startActivity(i);
    		
    	}
    	else if(v.equals((Button)findViewById(R.id.scanButton)))
    	{
    		///////////////
    		//Scan Barcode
    		//Fire Intent to Barcode Scanner
    		///////////////
    		IntentIntegrator integrator = new IntentIntegrator(this);
    		integrator.initiateScan();
    		
    	}
    	
    	
    }
    
    /**
     * Handles the result of the barcode scan
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
    	  
    	  //Make sure there is a result
    	  if (scanResult != null) {
    	    
    		//Parse result and package in a Bundle  
    		Long mISBN = Long.parseLong(scanResult.getContents());
    	    Bundle b = new Bundle();
    	    b.putLong("isbn", mISBN);
    	    
    	    //Pass the Bundle to the next activity
    	    Intent i = new Intent(this, LoadingActivity.class);
    	    i.putExtras(b);
    	    startActivity(i);
    	  }
    	  
    	}
    
}