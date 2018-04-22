package rest;

import java.util.*;

/**
 * Created by Vincent and Aaron
 */
public class ShuntingYard {

    /**
     * Checks whether or not the character is an operator
     * @param c Character to test
     * @return true if the character is an operator
     */
    private static boolean isOperator(char c) {
        if (c == '+' || c == '-' || c == '*' || c == '/' ||
                c == '^' || c == '(' || c == ')') {
            return true;
        }
        else return false;
    }

    /**
     * compares precedence of topmost operator in stack with operator at current position in infix-string
     *
     * @param operatorOldChar Old operator character
     * @param operatorNewChar new operator character
     * @return True if it is lower precedence
     */
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

    /**
     * Converts the infix expression into a postfix expression. Postfix expressions are in a format that a computer
     * can solve easily
     * @param infix The infix expression that should be converted
     * @return Converted Postfix Expression
     */
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
