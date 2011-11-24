package com.freeiz.ucftxts;

import java.util.Vector;

import com.freeiz.ucftxts.client.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Ucftxts2Activity extends Activity implements OnClickListener, XtremeResponseHandler
{
	private XtremeClient XClient;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //XClient = new XtremeClient(getString(R.string.webURL), this);
        XClient = new XtremeClient("", this);
        
        
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
    		long mIsbn = 0;
    		if(!(isbn.getText().toString()).equals("")) //make sure we're not trying to parse an empty string
    			mIsbn = Long.parseLong(isbn.getText().toString());
    		
    		String mAuthor = author.getText().toString();
    		String mTitle = title.getText().toString();
    		String mTopic = topic.getText().toString();
    		
    		author.setText("searching...");
    		
    		Intent i = new Intent(this, LoadingActivity.class);
    		Bundle b = new Bundle();
    		
    		b.putLong("isbn", mIsbn);
    		b.putString("author", mAuthor);
    		b.putString("title", mTitle);
    		b.putString("topic", mTopic);
    		
    		
    		i.putExtras(b);   		
    		startActivity(i);
    		
    		
    		
    		/*try {
    			setContentView(R.layout.loading);
				XClient.Query(mAuthor, mTitle);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//setContentView(R.layout.main);
				isbn.setText("ERROR");
				e.printStackTrace();
			}*/
    		
    	}
    	else if(v.equals((Button)findViewById(R.id.scanButton)))
    	{
    		///////////////
    		//Scan Barcode
    		//Fire Intent to Barcode Scanner here
    		///////////////
    	}
    	isbn.setText("done waiting");
    	
    	
    }
    
    public void waitForQuery()
    {

    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
    	
    }
    

	@Override
	public void XtremeResponse(Vector<Book> books) 
	{
		//setContentView(R.layout.main); //TESTING PURPOSES ONLY
		EditText topic = (EditText)findViewById(R.id.editText4);
		topic.setText("Response Fired!");
		//setContentView(R.layout.results)
	}
}