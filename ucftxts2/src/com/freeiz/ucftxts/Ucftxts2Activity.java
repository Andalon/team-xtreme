package com.freeiz.ucftxts;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Ucftxts2Activity extends Activity implements OnClickListener 
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(this);
        
        Button scanButton = (Button)findViewById(R.id.scanButton);
        scanButton.setOnClickListener(this);
    }
    
    public void onClick(View v)
    {
    	EditText isbn = (EditText)findViewById(R.id.editText1);
    	EditText title = (EditText)findViewById(R.id.editText2);
    	EditText author = (EditText)findViewById(R.id.editText3);
    	EditText topic = (EditText)findViewById(R.id.editText4);
    	if(v.equals((Button)findViewById(R.id.searchButton)))
    	{
    		waitForQuery();
    		isbn.setText("done waiting");
    	}
    	else if(v.equals((Button)findViewById(R.id.scanButton)))
    	{
    		///////////////
    		//Scan Barcode
    		//Fire Intent to Barcode Scanner here
    		///////////////
    	}
    	
    }
    
    public boolean waitForQuery()
    {
    	//onPause();
    	setContentView(R.layout.loading);
    	
    	boolean doneWaiting = false;
    	return true;
    }
}