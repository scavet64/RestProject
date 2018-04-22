package rest;

import java.util.*;

public class ShuntingYard {

//    private enum Operator
//    {
//        ADD(1), SUBTRACT(2), MULTIPLY(3), DIVIDE(4);
//        final int precedence;
//        Operator(int p) { precedence = p; }
//    }
//
//    private static Map<String, Operator> ops = new HashMap<String, Operator>() {{
//        put("+", Operator.ADD);
//        put("-", Operator.SUBTRACT);
//        put("*", Operator.MULTIPLY);
//        put("/", Operator.DIVIDE);
//    }};
//
//    private static boolean isHigerPrec(String op, String sub)
//    {
//        return (ops.containsKey(sub) && ops.get(sub).precedence >= ops.get(op).precedence);
//    }
//
//    public static String postfix(String infix)
//    {
//        StringBuilder output = new StringBuilder();
//        Deque<String> stack  = new LinkedList<>();
//
//        for (String token : infix.split("\\s")) {
//            // operator
//            if (ops.containsKey(token)) {
//                while ( ! stack.isEmpty() && isHigerPrec(token, stack.peek()))
//                    output.append(stack.pop()).append(' ');
//                stack.push(token);
//
//                // left parenthesis
//            } else if (token.equals("(")) {
//                stack.push(token);
//
//                // right parenthesis
//            } else if (token.equals(")")) {
//                while ( ! stack.peek().equals("("))
//                    output.append(stack.pop()).append(' ');
//                stack.pop();
//
//                // digit
//            } else {
//                output.append(token).append(' ');
//            }
//        }
//
//        while ( ! stack.isEmpty())
//            output.append(stack.pop()).append(' ');
//
//        return output.toString();
//    }

//    // A utility function to return precedence of a given operator
//    // Higher returned value means higher precedence
//    static int Prec(char ch)
//    {
//        switch (ch)
//        {
//            case '+':
//            case '-':
//                return 1;
//
//            case '*':
//            case '/':
//                return 2;
//
//            case '^':
//                return 3;
//        }
//        return -1;
//    }

//    // The main method that converts given infix expression
//    // to postfix expression.
//    public static String infixToPostfix(String exp)
//    {
//        // initializing empty String for result
//        String result = new String("");
//
//        // initializing empty stack
//        Stack<Character> stack = new Stack<>();
//
//        for (int i = 0; i<exp.length(); ++i)
//        {
//            char c = exp.charAt(i);
//
//            // If the scanned character is an operand, add it to output.
//            if (Character.isLetterOrDigit(c))
//                result += c;
//
//                // If the scanned character is an '(', push it to the stack.
//            else if (c == '(')
//                stack.push(c);
//
//                //  If the scanned character is an ')', pop and output from the stack
//                // until an '(' is encountered.
//            else if (c == ')')
//            {
//                while (!stack.isEmpty() && stack.peek() != '(')
//                    result += stack.pop();
//
//                if (!stack.isEmpty() && stack.peek() != '(')
//                    return "Invalid Expression"; // invalid expression
//                else
//                    stack.pop();
//            }
//            else // an operator is encountered
//            {
//                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek()))
//                    result += stack.pop() + " ";
//                stack.push(c);
//            }
//
//        }
//
//        // pop all the operators from the stack
//        while (!stack.isEmpty())
//            result += stack.pop() + " ";
//
//        return result;
//    }

    //checks whether c is operator
    private static boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/' ||
                c == '^' || c == '(' || c == ')') {
            return true;
        }
        else return false;
    }

    //compares precedence of topmost operator in stack with operator at current position in infix-string
    private static boolean isLowerPrecedence(char operatorOldChar, char operatorNewChar) {
        boolean check = true; //true = new operator has higher precedence than old operator; false = contrary
        int operatorOld = 0, operatorNew = 0; //will compare precedence of operators; higher number = higher precedence

        //assign precedence to old operator (topmost in stack)
        if (operatorOldChar == '+' || operatorOldChar == '-') operatorOld = 2;
        else if (operatorOldChar == '*' || operatorOldChar == '/') operatorOld = 3;
        else if (operatorOldChar == '^') operatorOld = 4;

        //assign precedence to new operator (current character in infix)
        if (operatorNewChar == '+' || operatorNewChar == '-') operatorNew = 2;
        else if (operatorNewChar == '*' || operatorNewChar == '/') operatorNew = 3;
        else if (operatorNewChar == '^') operatorNew = 4;

        if (operatorNewChar == '(') {
            check = false;
        }
        else if (operatorNewChar == ')') {
            check = true;
        }
        else if (operatorOld < operatorNew) {
            check = false;
        }
        else if (operatorOld >= operatorNew) {
            check = true;
        }

        return check;
    }

    public static String convertToPostfix(String infix) {
        Stack<Character> stack = new Stack<>();
        StringBuilder postfix = new StringBuilder();
        boolean isStillOneNumber = true; //will differentiate between outputs like 11 or 1 1

        for (int i = 0; i < infix.length(); i++) {

            if (!isOperator(infix.charAt(i)) & infix.charAt(i) != ' ') { //handles numbers and constants
                if (isStillOneNumber) {
                    postfix.append(infix.charAt(i));
                }
                else {
                    postfix.append(" ");
                    postfix.append(infix.charAt(i));
                }
                isStillOneNumber = true;
            }

            else if (isOperator(infix.charAt(i))){ //handles operators
                if (infix.charAt(i) == ')') {
                    while (!stack.isEmpty() & stack.peek() != '(') {
                        postfix.append(" ");
                        postfix.append(stack.pop());
                    }
                    if (!stack.isEmpty()) {
                        stack.pop();
                    }
                }
                else {
                    if (!stack.isEmpty()) {
                        if (!isLowerPrecedence(stack.peek(), infix.charAt(i))) {
                            stack.push(infix.charAt(i));
                        }
                        else {
                            while (!stack.isEmpty()) {
                                postfix.append(" ");
                                postfix.append(stack.pop());
                            }
                            stack.push(infix.charAt(i));
                        }
                    }
                    else if (stack.isEmpty()) {
                        stack.push(infix.charAt(i));
                    }
                }
                isStillOneNumber = false;
            }

            else { //handles possible spaces in infix
                isStillOneNumber = false;
            }

        }
        while (!stack.isEmpty()) {
            postfix.append(" ");
            postfix.append(stack.pop());
        }
        return postfix.toString();
    }

}
