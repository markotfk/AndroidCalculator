package com.laskin;

import com.laskin.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.*;
import android.widget.*;

public class LaskinActivity extends Activity implements OnClickListener {
	
	Laskin calculator;
	private TextView output;
	private final static int CLEAR_OUTPUT = 1 << 0;
	private final static int BLOCK_DOT = 1 << 1;
	private int flags = CLEAR_OUTPUT;
	
	private boolean numberAdded = false;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		output = (TextView)findViewById(R.id.output);
		calculator = new Laskin();
		addButtonListeners();
	}
	
	private void addButtonListeners() {
		Button b = (Button)findViewById(R.id.but_one);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_two);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_three);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_four);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_five);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_six);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_seven);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_eight);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_nine);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_zero);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_equals);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_dot);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_div);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_mul);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_plus);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_minus);
		b.setOnClickListener(this);
		b = (Button)findViewById(R.id.but_clear);
		b.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		Button b = (Button)v;
		
		switch (b.getId()) {
			case R.id.but_plus:
			case R.id.but_minus:
			case R.id.but_mul:
			case R.id.but_div: {
				handleOp(b.getText().toString());
				break;
			}
			case R.id.but_equals: {
				String num = output.getText().toString();
				if (num.length() > 0) {
					calculator.addNumber(output.getText().toString());
				}
				String result = String.valueOf(calculator.getResult());
				if (result.endsWith(".0")) {
					result = result.substring(0, result.length()-2);
				}
				output.setText(result);
				flags = flags | CLEAR_OUTPUT;
				flags = flags & ~BLOCK_DOT;
				
				numberAdded = false;
				break;
			}
			case R.id.but_clear: {
				calculator.reset();
				flags = flags | CLEAR_OUTPUT;
				flags = flags & ~BLOCK_DOT;
				output.setText(R.string.zero_output);
				break;
			}
			case R.id.but_dot: {
				checkClearFlag(R.string.zero_output);
				
				numberAdded = false;
				if ((flags & BLOCK_DOT) == 0) {
					output.append(b.getText());
					flags = flags | BLOCK_DOT;
				}
				break;
			}
			default: {
				checkClearFlag(0);
				
				numberAdded = false;
				output.append(b.getText());
				break;
			}
		}
	}
	
	private void handleOp(String buttonStr) {
		final String num = output.getText().toString();
		if (num.length() > 0) {
			if (!numberAdded) {
				calculator.addNumber(num);
				numberAdded = true;
			}
		}
		calculator.addNumber(buttonStr);
		flags = flags | CLEAR_OUTPUT;
		flags = flags & ~BLOCK_DOT;
	}
	
	private void checkClearFlag(int resId) {
		if ((flags & CLEAR_OUTPUT) != 0) {
			if (resId != 0) {
				output.setText(resId);
			} else {
				output.setText("");
			}
			
			flags = flags & ~CLEAR_OUTPUT;
		}
	}
}