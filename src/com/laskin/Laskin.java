package com.laskin;
import java.util.List;
import java.util.ArrayList;

public class Laskin {

	private enum optype {
		op_unknown,
		op_mul,
		op_div,
		op_plus,
		op_minus
	}
	
	private optype opType = optype.op_unknown;
	private List<String> numbers;
	
	public Laskin() {
		numbers = new ArrayList<String>();
	}
	
	public boolean isOpType(String value) {
		if (value == null) {
			throw new IllegalArgumentException("isOpType: null values not allowed");
		}
		if (value.equalsIgnoreCase("*")) {
			opType = optype.op_mul;
		} else if (value.equalsIgnoreCase("/")) {
			opType = optype.op_div;
		} else if (value.equalsIgnoreCase("+")) {
			opType = optype.op_plus;
		} else if (value.equalsIgnoreCase("-")) {
			opType = optype.op_minus;
		} else {
			opType = optype.op_unknown;
		}
		return opType != optype.op_unknown;
	}

	public void addNumber(String number) {
		if (number == null) {
			throw new IllegalArgumentException("addNumber: null values not allowed");
		}
		if (!numbers.isEmpty()) {
			String last = numbers.get(numbers.size()-1);
			if (isOpType(number) && isOpType(last) && number.equalsIgnoreCase(last) ) {
				return; // no two operators in row allowed (e.g. ++, --)
			}
		} else {
			if (isOpType(number)) {
				return;
			}
		}
		if (number.equalsIgnoreCase(".")) {
			return;
		}
		numbers.add(number);
	}
	
	public double getResult() {
		
		double result = 0;
		double lastNum = 0;
		optype prevOp = optype.op_unknown;
		try {
			for (String next : numbers) {
				prevOp = opType;
				if (!isOpType(next)) {
					if (lastNum != 0) {
						opType = prevOp;
						result = calculate(lastNum, Double.parseDouble(next));
						lastNum = result;
					} else {
						lastNum = Double.parseDouble(next);
					}
					
				} 
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			result = 0;
		}
		numbers.clear();
		opType = optype.op_unknown;
		return result;
	}
	
	/**
	 * Calculate two numbers based on current operation type
	 * @param num1 First number
	 * @param num2 Second number
	 * @return Calculation result
	 */
	private double calculate(double num1, double num2) {
		double result = 0;
		switch (opType) {
			case op_mul:
				result = num1 * num2;
				break;
			case op_div:
				result = num1 / num2;
				break;
			case op_plus:
				result = num1 + num2;
				break;
			case op_minus:
				result = num1 - num2;
				break;
			default:
				break;
		}
		return result;
	}
}
