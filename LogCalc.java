package com.meevasoft.logcalc;

import com.meevasoft.logcalc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class LogCalc extends Activity {
	SharedPreferences mPrefs;
	final String welcomeScreenShownPref = "welcomeScreenShown";
	private static final String DEV_NAME = "Meeva Software";
	private AlertDialog helpMenu;
	private TextView hi;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // second argument is the default to use if the preference can't be found
        Boolean welcomeScreenShown = mPrefs.getBoolean(welcomeScreenShownPref, false);

        if (!welcomeScreenShown) {
            new AlertDialog.Builder(this)
            	.setTitle("Welcome to LogCalc!")
            	.setMessage("Please check out the help menu before using LogCalc." + 
            			"\n\nHit the \'back\' button to remove the keyboard from the screen." +
            			"\n\nThis app really isn't under active development anymore, at least for a while. Please continue to submit bug reports though; we'll gladly fix those.")
            	 .setNeutralButton("Okay", null)
                 .show();
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(welcomeScreenShownPref, true);
            editor.commit(); // Very important to save the preference
        }
       
        sayHi();
    }
    
    private void sayHi() {
    	hi = (TextView)findViewById(R.id.intro_hi);
    	hi.setText("\n\nWelcome to LogCalc! Select one of the buttons to get started.");
    	hi.append("\n\n(c) 2012 " + DEV_NAME + ". All rights reserved.");
    	hi.append("\n\nQuestions? Comments? Just want to say hi? Check us out online at ");
    	hi.append(Html.fromHtml("<a href=\"http://www.facebook.com/pages/Meeva-Software/169303346512583\">our website</a>."));
    	hi.setMovementMethod(LinkMovementMethod.getInstance());;
    }
    
    /*
     * Methods for menu options
     */
    
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.help:
    			showHelpMenu();
    			break;
    	}
		return true;
    }
    
    private void showHelpMenu() {
        	helpMenu = new AlertDialog.Builder(LogCalc.this)
    		.setTitle("Help")
    		.setCancelable(true)
    		.setNeutralButton("Okay", null)
    		.create();
        	helpMenu.setMessage("log x - use this to solve a log with a variable base" +
        			"\nln x - use this to solve a natural logarithm (log base e)" +
        			"\nlg x - use this to solve a lg (log base 2)" +
        			"\n\nFor numbers like \'e\' and \'pi\', use a decimal approximation." +
        			"\n\nTruncation works in the range of 0-19" + 
        			"\n\nWARNING: Be careful with inputting very large values for \'base\' and " +
        			"\'value\'. The larger the number, the higher the chance that the result will " +
        			"be inaccurate. We've done the best we could to make this as accurate as possible, " +
        			"but we're still bound by the limitations of the math libraries we use.");
        	helpMenu.show();   
    }
    
    /*
     * Methods for top buttons
     */
    
    public void switchToLog(View v) {
    	switchToLog();
    }
    
    private void switchToLog() {
    	startActivity(new Intent(getApplicationContext(), LogCalc_Log.class).putExtras(new Bundle()));
    	finish();
    }
    
    public void switchToLn(View v) {
    	switchToLn();
    }
    
    private void switchToLn() {
    	startActivity(new Intent(getApplicationContext(), LogCalc_Ln.class).putExtras(new Bundle()));
    	finish();
    }
    
    public void switchToLg(View v) {
    	switchToLg();
    }
    
    private void switchToLg() {
    	startActivity(new Intent(getApplicationContext(), LogCalc_Lg.class).putExtras(new Bundle()));
    	finish();
    }
}
