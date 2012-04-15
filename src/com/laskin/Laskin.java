package com.laskin;
import java.util.*;

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
	private List<String> postfix;
	
	public Laskin() {
		numbers = new ArrayList<String>();
		postfix = new ArrayList<String>();
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
		convertToPostfix();
		final double result = evaluatePostfix();
		reset();
		return result;
	}
	
	public void reset() {
		numbers.clear();
		postfix.clear();
		opType = optype.op_unknown;
	}
	
	private int precedence(String op) {
	   if (op.equalsIgnoreCase("*") || op.equalsIgnoreCase("/")) {
		   return 1;
	   }
	   if (op.equalsIgnoreCase("+") || op.equalsIgnoreCase("-")) {
		   return 0;
	   }
	   return -1;
	}

	private void convertToPostfix() {
		Stack<String> opStack = new Stack<String>();

	    for (String num : numbers) {
	    	if (isOpType(num)) {
	    		while ( (!opStack.isEmpty()) 
	    				&& (!opStack.peek().equals('('))
	    				&& (precedence(num) <= precedence((String)opStack.peek()))){
	    			postfix.add(opStack.pop());
	    		}
	    		opStack.push(num);
	    	}
	    	else if (num.equalsIgnoreCase("(")) {
	    		opStack.push(num);
	    	}
	    	else if (num.equalsIgnoreCase(")") ) {
	    		while (!(opStack.peek()).equals('(')){
	    			postfix.add(opStack.pop());
	    		}
	    		opStack.pop();
	    	}
	    	else {
	    		postfix.add(num);
	    	}
	    }
	    while (!opStack.isEmpty()) {
	    	postfix.add(opStack.pop()); 
	    }
	  }

	public double evaluatePostfix() { 
	    Stack<Double> resultStack = new Stack<Double>();
	    double num1, num2;
	    double result = 0;
	    for (String ch : postfix) {
			 if (ch.equalsIgnoreCase("+")) {
				 num2 = resultStack.pop().doubleValue();
			     num1 = resultStack.pop().doubleValue();
			     result = num1 + num2;
			     resultStack.push(new Double(result));
			 }
			 else if (ch.equalsIgnoreCase("-")) {
				 num2 = resultStack.pop().doubleValue();
			     num1 = resultStack.pop().doubleValue();
			     result = num1 - num2;
			     resultStack.push(new Double(result));
			 }
			 else if (ch.equalsIgnoreCase("*")) {
				 num2 = resultStack.pop().doubleValue();
			     num1 = resultStack.pop().doubleValue();
			     result = num1 * num2;
			     resultStack.push(new Double(result));
			 }
			 else if (ch.equalsIgnoreCase("/")) {
				 num2 = resultStack.pop().doubleValue();
			     num1 = resultStack.pop().doubleValue();
			     result = num1 / num2;
			     resultStack.push(new Double(result));
			 }
			 else {
				 resultStack.push(Double.valueOf(ch));
			 }
	    }
	    if (resultStack.size() > 0) {
	    	result = resultStack.pop().doubleValue();
	    }
	    return result;
	  }
}
