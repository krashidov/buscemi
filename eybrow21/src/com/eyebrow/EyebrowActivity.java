package com.eyebrow;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EyebrowActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
      
        //Button to take a picture
        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
			
        	//Anonymous function that listens to when the button is pressed
			public void onClick(View v) {
				
				//When the button is pressed, we run CameraTakeActivity
				Intent mIntent = new Intent(EyebrowActivity.this, CameraTakeActivity.class);
				EyebrowActivity.this.startActivity(mIntent);
				// TODO Auto-generated method stub
				
			}
		});
    }
    

}