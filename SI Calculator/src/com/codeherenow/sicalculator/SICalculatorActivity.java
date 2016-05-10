/*
 * Copyright (C) 2013 Code Here Now - A subsidiary of Mobs & Geeks
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file 
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the 
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language governing permissions and 
 * limitations under the License.
 */
package com.codeherenow.sicalculator;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class SICalculatorActivity extends Activity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, TextWatcher {

	private EditText principalAmount;
	private EditText interestRate;
	private TextView calculationText;
	private Button calculateButton;
	private SeekBar yearsBar;
	private TextView yearsDisplay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sicalculator);
		principalAmount = (EditText)findViewById(R.id.principalAmount);
		interestRate = (EditText) findViewById(R.id.rateText);
		calculationText = (TextView)findViewById(R.id.calculationText);
		calculateButton = (Button)findViewById(R.id.calculateButton);
		calculateButton.setOnClickListener(this);
		yearsBar = (SeekBar)findViewById(R.id.yearsSeekBar);
		//yearsBar.setBottom(0);
		yearsBar.setOnSeekBarChangeListener(this);

		yearsDisplay = (TextView)findViewById(R.id.displayYears);

		interestRate.addTextChangedListener(this);
		principalAmount.addTextChangedListener(this);
	}


	@Override
	public void onClick(View v) {
		calculationText.setText("");

		//get the filled values by the user
		String interestRateStr = this.interestRate.getText().toString();
		String principalAmountStr = principalAmount.getText().toString();
		int yearsInt = yearsBar.getProgress();
		double rate = 0.0;

		int principalAmountInt = 0;

		if(principalAmountStr!=null && !principalAmountStr.equals("")){
			try{
				principalAmountInt = convertPrincipalAmount(principalAmountStr);
			}
			catch(NumberFormatException nfe){
				calculationText.setText("Wrong format for field Principle Amount");
			}
		}
		else{
			calculationText.setText("Missing Principle Amount!");
			return;
		}

		if(interestRateStr!=null && !interestRateStr.equals("")){
			try{
				rate = convertRate(interestRateStr);
			}
			catch(NumberFormatException nfe){
				calculationText.setText("Wrong format for Interest Rate!");
				return;
			}
		}
		else{
			calculationText.setText("Missing value for rate!");
			return;
		}

		double calculation = (double) rate / 100;
		calculation = calculation * principalAmountInt * yearsInt;
		String yearsString = "";
		if(yearsInt == 1){
			yearsString = "year";
		}
		else
			yearsString = "years";
		calculationText.setText("The inerest for $" + principalAmountStr + " at a rate of " + interestRateStr + "% for " + yearsInt + " " + yearsString + " is $" + calculation);
	}



	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		calculationText.setText("");
		if(progress==1){
			this.yearsDisplay.setText(progress + " Year");
		}
		else
			this.yearsDisplay.setText(progress + " Years");

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}

	private int convertPrincipalAmount(String amount) throws NumberFormatException {
		int principalAmount = Integer.parseInt(amount);

			return principalAmount;
	}

	private double convertRate(String interestRateStr) throws NumberFormatException{
		double rate = Double.parseDouble(interestRateStr);
		String.format("%.2f", rate);

		return rate;
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		calculationText.setText("");
	}

	@Override
	public void afterTextChanged(Editable s) {

	}
}
