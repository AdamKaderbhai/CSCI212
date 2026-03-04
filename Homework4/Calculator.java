import java.util.Scanner;

/**
 * Calculator for evaluating infix/postfix expressions using recursion.
 * Uses LinkedList as Stack and Queue implementation.
 * 
 * I struggled significantly implementing the recursive
 * shunting-yard algorithm method and its helpers.
 * I researched examples and discussion threads (including StackOverflow)
 * to learn common recursive patterns for "pop while condition" and
 * parenthesis handling. The final code is my adaptation and understanding:
 * I implemented recursive helpers (popOperatorsToOutput, popToLeftParenthesis,
 * outputOperators) and tested many small cases until the behavior matched
 * what the algorithm requires. Any remaining imperfections are mine,
 * but the structure and logic reflect what I learned while working through
 * the problem. This was a fun yet challenging assignment I am slowly realizing
 * The beauties of recursion 
 */
public class Calculator {

    /**
     * Checks if this string is a valid double 
     * @param s the string to check
     * @return true if s is numeric
     */
    public static boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        return isNumericHelper(s, 0, false, false);
    }

    // Helper: walks string for numeric test (digits, at most one '.', optional leading '-')
    private static boolean isNumericHelper(String s, int i, boolean seenDot, boolean seenDigit) {
        if (i == s.length()) return seenDigit;
        char c = s.charAt(i);
        if (c == '-') {
            if (i != 0) return false;
            return isNumericHelper(s, i + 1, seenDot, seenDigit);
        }
        if (c == '.') {
            if (seenDot) return false;
            return isNumericHelper(s, i + 1, true, seenDigit);
        }
        if (c >= '0' && c <= '9') {
            return isNumericHelper(s, i + 1, seenDot, true);
        }
        return false;
    }

    /**
     * Returns true if string s is a supported operator: +, -, *, /, ^.
     * @param s string to test
     * @return true if s is operator
     */
    public static boolean isOperator(String s) {
        return "+".equals(s) || "-".equals(s) || "*".equals(s) || "/".equals(s) || "^".equals(s);
    }

    /**
     * Gets the precedence of an operator.
     * Higher value = higher precedence.
     * @param s operator
     * @return precedence value
     */
    private static int getPrecedence(String s) {
        if ("^".equals(s)) return 10;
        if ("*".equals(s) || "/".equals(s)) return 5;
        if ("+".equals(s) || "-".equals(s)) return 0;
        throw new IllegalArgumentException("Unknown operator: " + s);
    }

    /**
     * Converts infix token queue to postfix queue using recursive shunting yard algorithm.
     * @param tokens infix queue
     * @return postfix queue
     */
    public static Queue<String> shunt(Queue<String> tokens) {
        Stack<String> operators = new LinkedList<>();
        Queue<String> output = new LinkedList<>();
        return shuntHelper(tokens, operators, output);
    }

    private static Queue<String> shuntHelper(Queue<String> input, Stack<String> operators, Queue<String> output) {
        if (input.isEmpty()) {
            return outputOperators(operators, output);
        }
        String token = input.dequeue();
        if (isNumeric(token)) {
            output.enqueue(token);
        } else if (isOperator(token)) {
            popOperatorsToOutput(operators, output, token);
            operators.push(token);
        } else if ("(".equals(token)) {
            operators.push(token);
        } else if (")".equals(token)) {
            popToLeftParenthesis(operators, output);
        } else {
            throw new IllegalArgumentException("Invalid token: " + token);
        }
        return shuntHelper(input, operators, output);
    }

    private static Queue<String> outputOperators(Stack<String> operators, Queue<String> output) {
        if (operators.isEmpty()) return output;
        String op = operators.pop();
        if ("(".equals(op) || ")".equals(op)) {
            throw new IllegalArgumentException("Mismatched parentheses");
        }
        output.enqueue(op);
        return outputOperators(operators, output);
    }

    private static void popOperatorsToOutput(Stack<String> operators, Queue<String> output, String token) {
        if (!operators.isEmpty() && isOperator(operators.top())) {
            int topPrec = getPrecedence(operators.top());
            int currPrec = getPrecedence(token);
            boolean rightAssoc = "^".equals(token);
            boolean mustPop = rightAssoc ? (topPrec > currPrec) : (topPrec >= currPrec);
            if (mustPop) {
                output.enqueue(operators.pop());
                popOperatorsToOutput(operators, output, token);
                return;
            }
        }
        // Done popping, push token in caller.
    }

    private static void popToLeftParenthesis(Stack<String> operators, Queue<String> output) {
        if (operators.isEmpty()) {
            throw new IllegalArgumentException("Mismatched parentheses");
        }
        String oper = operators.pop();
        if ("(".equals(oper)) return;
        output.enqueue(oper);
        popToLeftParenthesis(operators, output);
    }

    /**
     * Evaluates a postfix expression in a queue of tokens and returns result.
     * @param tokens queue of postfix tokens (numbers and operators)
     * @return result as double
     */
    public static double evaluate(Queue<String> tokens) {
        Stack<Double> stack = new LinkedList<>();
        return evalHelper(tokens, stack);
    }

    // Recursive helper for evaluate
    private static double evalHelper(Queue<String> tokens, Stack<Double> stack) {
        if (tokens.isEmpty()) {
            if (stack.isEmpty())
                throw new IllegalArgumentException("No result on stack");
            double result = stack.pop();
            if (!stack.isEmpty())
                throw new IllegalArgumentException("Extra operands left");
            return result;
        }
        String token = tokens.dequeue();
        if (isNumeric(token)) {
            stack.push(stringToDouble(token));
        } else if (isOperator(token)) {
            if (stack.isEmpty()) throw new IllegalArgumentException("Not enough operands");
            double b = stack.pop();
            if (stack.isEmpty()) throw new IllegalArgumentException("Not enough operands");
            double a = stack.pop();
            double res;
            switch (token) {
                case "+": res = a + b; break;
                case "-": res = a - b; break;
                case "*": res = a * b; break;
                case "/": res = a / b; break;
                case "^": res = Math.pow(a, b); break;
                default: throw new IllegalArgumentException("Unknown operator: " + token);
            }
            stack.push(res);
        } else {
            throw new IllegalArgumentException("Unknown token: " + token);
        }
        return evalHelper(tokens, stack);
    }

    /**
     * Converts a string (valid numeric) to double recursively.
     * @param s string
     * @return double value
     */
    private static double stringToDouble(String s) {
        // Accepts integers and decimals; uses Double.parseDouble (allowed).
        return Double.parseDouble(s);
    }

    /**
     * Recursively tokenizes a line using a per-line Scanner 
     * @param line input line
     * @return queue of tokens
     */
    private static Queue<String> lineToQueue(String line) {
        Queue<String> tokens = new LinkedList<>();
        Scanner s = new Scanner(line);
        if (!s.hasNext()) {
            s.close();
            return tokens;
        }
        // If first token is "quit" and no more tokens, caller will handle quit check.
        tokensFromScanner(s, tokens);
        s.close();
        return tokens;
    }

    // Recursively reads tokens from a Scanner and enqueues them.
    private static void tokensFromScanner(Scanner s, Queue<String> tokens) {
        if (!s.hasNext()) return;
        String toks = s.next();
        tokens.enqueue(toks);
        tokensFromScanner(s, tokens);
    }

    /**
     * Reads input and processes the calculator interactively, recursively.
     * Uses a per-line Scanner to detect empty lines and the single-word "quit".
     * @param in input scanner
     */
    private static void interactiveLoop(Scanner in) {
        System.out.print("> ");
        if (!in.hasNextLine()) {
            in.close();
            return;
        }
        String line = in.nextLine();
        // Use a per-line Scanner to examine tokens 
        Scanner s = new Scanner(line);
        if (!s.hasNext()) {
            s.close();
            interactiveLoop(in); // blank line -> skip
            return;
        }
        String first = s.next();
        if ("quit".equals(first) && !s.hasNext()) {
            s.close();
            in.close();
            return;
        }
        // build token queue (first token has been consumed; we re-create queue using lineToQueue)
        s.close();
        Queue<String> infix = lineToQueue(line);
        Queue<String> postfix = shunt(infix);
        double answer = evaluate(postfix);
        System.out.println(answer);
        interactiveLoop(in); // recurse!
    }

    /**
     * Main method for the interactive calculator.
     * Reads a line of infix input, converts to postfix, and evaluates.
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Postfix/Infix Calculator Type 'quit' to exit.");
        interactiveLoop(in);
    }
}