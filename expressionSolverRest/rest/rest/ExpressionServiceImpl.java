package rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Dr. Baliga on 4/6/18.
 */

public class ExpressionServiceImpl implements ExpressionService {

    public static String BASEURL = "http://calcserver:8080/";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Pair<Boolean,Integer> getCachedValue(String expression) {

        String querystr =
                String.format("SELECT answer FROM cache WHERE expression = '%s'", expression);
        try {
            Object answer = jdbcTemplate.queryForObject(querystr, new Object[]{}, Integer.class);
            return Pair.of(true, (Integer) answer);
        }
        catch (Exception e) {
            return Pair.of(false, 0);
        }

    }

    private double insert(String expression, double answer) {
        jdbcTemplate.execute("INSERT INTO cache (expression, answer) VALUES ('"+
                expression + "'," + answer + ")");
        return answer;
    }

    public double solve(final String str) {
        Pair<Boolean,Integer> cached = getCachedValue(str);
        if (cached.fst)
            return cached.snd;
        else
            return insert(str, evaluate(ShuntingYard.convertToPostfix(str)));
    }

//    /**
//     * Evaluate postfix expression
//     *
//     * @param postfix The postfix expression
//     */
//    public static double evaluate(String postfix) {
//        // Use a stack to track all the numbers and temporary results
//        Stack<Double> s = new Stack<Double>();
//
//        // Convert expression to char array
//        char[] chars = postfix.toCharArray();
//
//        // Cache the length of expression
//        int N = chars.length;
//
//        for (int i = 0; i < N; i++) {
//            char ch = chars[i];
//
//            if (isOperator(ch)) {
//                // Operator, simply pop out two numbers from stack and perfom operation
//                // Notice the order of operands
//                Double second = s.pop();
//                Double first = s.pop();
//                switch (ch) {
//                    case '+':
//                        //s.push(s.pop() + s.pop());
//                        s.push(makeAddCall(first, second));
//                        break;
//                    case '*':
//                        s.push(makeMultCall(first, second));
//                        break;
//                    case '-':
//                        s.push(makeSubCall(first, second));
//                        break;
//                    case '/':
//                        s.push(makeDivCall(first, second));
//                        break;
//                }
//            } else if(Character.isDigit(ch)) {
//                // Number, push to the stack
//                s.push((double)ch - '0');
////                int tmpLocation = i + 1;
////                while (Character.isDigit(chars[tmpLocation]) && tmpLocation < chars.length) {
////                    s.push(10.0 * s.pop() + (chars[tmpLocation] - 0));
////                    tmpLocation++;
////                }
//            }
//        }
//
//        // The final result should be located in the bottom of stack
//        // Otherwise return 0.0
//        if (!s.isEmpty())
//            return s.pop();
//        else
//            return 0.0;
//    }

    public static double evaluate(String postfix) {
        // Use a stack to track all the numbers and temporary results
        Stack<Double> s = new Stack<Double>();

        // Convert expression to char array
        String[] chars = postfix.split(" ");

        // Cache the length of expression
        int N = chars.length;

        for (int i = 0; i < N; i++) {
            String ch = chars[i];

            if (isOperator(ch)) {
                // Operator, simply pop out two numbers from stack and perfom operation
                // Notice the order of operands
                Double second = s.pop();
                Double first = s.pop();
                switch (ch) {
                    case "+":
                        //s.push(s.pop() + s.pop());
                        s.push(makeAddCall(first, second));
                        break;
                    case "*":
                        s.push(makeMultCall(first, second));
                        break;
                    case "-":
                        s.push(makeSubCall(first, second));
                        break;
                    case "/":
                        s.push(makeDivCall(first, second));
                        break;
                }
            } else if(ch.matches("-?\\d+(\\.\\d+)?")) {
                // Number, push to the stack
                s.push(getDoubleFromString(ch));
//                int tmpLocation = i + 1;
//                while (Character.isDigit(chars[tmpLocation]) && tmpLocation < chars.length) {
//                    s.push(10.0 * s.pop() + (chars[tmpLocation] - 0));
//                    tmpLocation++;
//                }
            }
        }

        // The final result should be located in the bottom of stack
        // Otherwise return 0.0
        if (!s.isEmpty())
            return s.pop();
        else
            return 0.0;
    }

    private static double getDoubleFromString(String param){
        if(param.contains(".")){
            return Double.parseDouble(param);
        } else {
            return (double) Integer.parseInt(param);
        }
    }

    /**
     * Check if the character is an operator
     */
    private static boolean isOperator(String ch) {
        return ch.equals("*") || ch.equals("/") || ch.equals("+") || ch.equals("-");
    }

    private static Double makeAddCall(Double a, Double b)
    {
        final String uri = BASEURL + "add?a={a}&b={b}";

        Map<String, Double> params = new HashMap<String, Double>();
        params.put("a", a);
        params.put("b", b);

        RestTemplate restTemplate = new RestTemplate();
        Double result = restTemplate.getForObject(uri, Double.class, params);

        return result;
    }

    private static Double makeSubCall(Double a, Double b)
    {
        final String uri = BASEURL + "sub?a={a}&b={b}";

        Map<String, Double> params = new HashMap<String, Double>();
        params.put("a", a);
        params.put("b", b);

        RestTemplate restTemplate = new RestTemplate();
        Double result = restTemplate.getForObject(uri, Double.class);

        return result;
    }

    private static Double makeMultCall(Double a, Double b)
    {
        final String uri = BASEURL + "mult?a={a}&b={b}";

        Map<String, Double> params = new HashMap<String, Double>();
        params.put("a", a);
        params.put("b", b);

        RestTemplate restTemplate = new RestTemplate();
        Double result = restTemplate.getForObject(uri, Double.class);

        return result;
    }

    private static Double makeDivCall(Double a, Double b)
    {
        final String uri = BASEURL + "div?a={a}&b={b}";

        Map<String, Double> params = new HashMap<String, Double>();
        params.put("a", a);
        params.put("b", b);

        RestTemplate restTemplate = new RestTemplate();
        Double result = restTemplate.getForObject(uri, Double.class);

        return result;
    }


}
