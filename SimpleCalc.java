import java.util.List;		// used by expression evaluator

/**
 *	Using stacks to emulate an infix arithmetic calculator.
 *
 *	@author	Ani Kumar
 *	@since	February 26, 2025
 */
public class SimpleCalc {
	private ExprUtils utils;                	// expression utilities
	private ArrayStack<Double> valueStack;		// value stack
	private ArrayStack<String> operatorStack;	// operator stack

	/* Constructor that initializes fields */
	public SimpleCalc() {
		utils = new ExprUtils();
		valueStack = new ArrayStack<>();
		operatorStack = new ArrayStack<>();
	}

	/* Main that executes program */
	public static void main(String[] args) {
		SimpleCalc sc = new SimpleCalc();
		sc.run();
	}

	/* Runner method that prints messages and makes intial call */
	public void run() {
		System.out.println("\nWelcome to SimpleCalc!!!\n");
		runCalc();
		System.out.println("\nThanks for using SimpleCalc! Goodbye.\n");
	}

	/* Print help */
	public void printHelp() {
		System.out.println("Help:");
		System.out.println("  h - this message\n  q - quit\n");
		System.out.println("Expressions can contain:");
		System.out.println("  integers or decimal numbers");
		System.out.println("  arithmetic operators +, -, *, /, %, ^");
		System.out.println("  parentheses '(' and ')'\n");
	}

	/**
	 * Prompt the user for expressions, run the expression evaluator,
	 * and display the answer.
	 */
	public void runCalc() {
		String input = "";
		while (!input.equals("q")) {
			input = Prompt.getString("");
			if (input.equals("h"))
				printHelp();
			else if (input.equals("q"))
				System.exit(0);
			List<String> tokens = utils.tokenizeExpression(input);
			double answer = evaluateExpression(tokens);
			System.out.println(answer);
		}
	}

	/**
	 * Evaluate expression and return the value
	 *
	 * @param tokens	a List of String tokens making up an arithmetic expression
	 * @return 			a double value of the evaluated expression
	 */
	public double evaluateExpression(List<String> tokens) {
		for (String token : tokens) {
			if (isNumeric(token)) {
				valueStack.push(Double.parseDouble(token));
			} else if (token.equals("(")) {
				operatorStack.push(token);
			} else if (token.equals(")")) {
				while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
					processOperator();
				}
				operatorStack.pop();
			} else {
				while (!operatorStack.isEmpty() && hasPrecedence(operatorStack.peek(), token)) {
					processOperator();
				}
				operatorStack.push(token);
			}
		}
		while (!operatorStack.isEmpty()) {
			processOperator();
		}
		return valueStack.pop();
	}

	private boolean isNumeric(String token) {
		try {
			Double.parseDouble(token);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private void processOperator() {
		String operator = operatorStack.pop();
		double b = valueStack.pop();
		double a = valueStack.pop();
		switch (operator) {
			case "+": valueStack.push(a + b); break;
			case "-": valueStack.push(a - b); break;
			case "*": valueStack.push(a * b); break;
			case "/": valueStack.push(a / b); break;
			case "%": valueStack.push(a % b); break;
			case "^": valueStack.push(Math.pow(a, b)); break;
		}
	}

	/**
	 * Precedence of operators
	 *
	 * @param op1 operator 1
	 * @param op2 operator 2
	 * @return true if op2 has higher or same precedence as op1; false otherwise
	 * Algorithm:
	 * if op1 is exponent, then false
	 * if op2 is either left or right parenthesis, then false
	 * if op1 is multiplication or division or modulus and
	 * op2 is addition or subtraction, then false
	 * otherwise true
	 */
	private boolean hasPrecedence(String op1, String op2) {
		if (op1.equals("^")) return false;
		if (op2.equals("(") || op2.equals(")")) return false;
		if ((op1.equals("*") || op1.equals("/") || op1.equals("%"))
				&& (op2.equals("+") || op2.equals("-")))
			return false;
		return true;
	}
}