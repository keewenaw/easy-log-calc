package com.meevasoft.logcalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.meevasoft.logcalc.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LogCalc_Lg extends Activity {
	private static final int DEFAULT_VALUE = 100;
	private static final int DEFAULT_TRUNC = 5;
	private static BigDecimal value;
	private static int truncation;
	private AlertDialog helpMenu;
	private DecimalFormat df = new DecimalFormat();
	private EditText input;
	private TextView output;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lg);
    }
    
    public void calculateLog(View v) {
    	formatOutput();
    }
      
    private void formatOutput() {
    	getValue();
    	getTrunc();
    	df.setMaximumFractionDigits(truncation);
    	df.setRoundingMode(RoundingMode.DOWN);
    	df.setParseBigDecimal(true);
    	output = (TextView)findViewById(R.id.output);
    	output.setText(Html.fromHtml("log<sub>2</sub>(" + value + ") = " + df.format(Math.log(value.doubleValue()) / Math.log(2)) + "\n\n"));
    }
    
    private void getValue() {
    	input = (EditText)findViewById(R.id.choice_value);
    	if (input.getText().toString() != "") {
    		try {
    			value = new BigDecimal(input.getText().toString());
    		} catch(NumberFormatException nfe) {
    			System.out.println("Could not parse " + nfe);
    		}
    	}
    	else value = new BigDecimal(DEFAULT_VALUE);
    	if (value.signum() < 0) value = new BigDecimal(DEFAULT_VALUE);
    }
    
    private void getTrunc() {
    	input = (EditText)findViewById(R.id.choice_trunc);
    	if (!input.getText().toString().isEmpty()) {
    		try {
    			truncation = Integer.parseInt(input.getText().toString());
    		} catch(NumberFormatException nfe) {
    			System.out.println("Could not parse " + nfe);
    		}
    	}
    	else truncation = DEFAULT_TRUNC;
    	if (truncation < 0 || truncation > 20) truncation = DEFAULT_TRUNC;
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
        	helpMenu = new AlertDialog.Builder(LogCalc_Lg.this)
    		.setTitle("Help")
    		.setCancelable(true)
    		.setNeutralButton("Okay", null)
    		.create();
        	helpMenu.setMessage("lg x - use this to solve a binary log (log base 2)" +
        			"\n\nFor the numbers \'e\' and \'pi\', use a decimal approximation." + 
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
