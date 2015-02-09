package com.eastbanctech.appuimatm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private static int[] balancesByAccount = new int[]{5000, 100};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner accountType = (Spinner)findViewById(R.id.accountType);
        String[] items = new String[]{"Checking account", "Savings account"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        accountType.setAdapter(adapter);

        final TextView balanceTextView = (TextView) findViewById(R.id.balance);
        final EditText amountEditText = (EditText) findViewById(R.id.amount);

        accountType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                balanceTextView.setText(Integer.toString(balancesByAccount[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        findViewById(R.id.dispenseButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(amountEditText.getWindowToken(), 0);

                int diff = Integer.valueOf(balanceTextView.getText().toString()) - Integer.valueOf(amountEditText.getText().toString());
                if(diff >= 0){
                    balancesByAccount[accountType.getSelectedItemPosition()] = diff;
                    balanceTextView.setText(Integer.toString(balancesByAccount[accountType.getSelectedItemPosition()]));
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Please take your money from the dispenser")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                }else{
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("ERROR")
                            .setMessage("Insufficient funds")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .show();
                }
            }
        });

        getSupportActionBar().setTitle("Appium ATM > Withdraw");
    }
}
