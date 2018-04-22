package rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Initial format created by Dr. Baliga on 4/6/18.
 *
 * Modified by Vincent and Aaron
 */
public class ExpressionServiceImpl implements ExpressionService {

    public static String BASEURL = "http://calcserver:8080/";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Search the database for the answer to the passed in expression string.
     * @param expression Expression to search for.
     * @return Pair of wasFound and answer.
     */
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

    /**
     * Insert an entry into the database
     * @param expression Expression that was solved
     * @param answer Answer to the expression
     * @return Calculated result
     */
    private double insert(String expression, double answer) {
        jdbcTemplate.execute("INSERT INTO cache (expression, answer) VALUES ('"+
                expression + "'," + answer + ")");
        return answer;
    }

    /**
     * Solve the expression. This method will reach out to the database to see if this expression has been solved before.
     * If it was, the stored answer will be returned, otherwise this method will solve it and store it for later use.
     * @param str Expression to solve
     * @return Calculated Result
     */
    public double solve(final String str) {
        Pair<Boolean,Integer> cached = getCachedValue(str);
        if (cached.fst)
            return cached.snd;
        else
            return insert(str, evaluate(ShuntingYard.convertToPostfix(str)));
    }

    /**
     * Evaluate an expression string in postfix format.
     * @param postfix The postfix string to evaluate
     * @return Result of the calculated postfix expression
     */
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
            }
        }

        // The final result should be located in the bottom of stack
        // Otherwise return 0.0
        if (!s.isEmpty())
            return s.pop();
        else
            return 0.0;
    }

    /**
     * Takes a string and returns a double. This will throw an exception if the string is not a number
     * @param param string that represents a number
     * @returns a double from the input string
     */
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

    /////////////// Rest Calls to Calculator Rest Service ///////////////

    /**
     * Make a rest call to the Addition Endpoint of the Calculator Rest Service
     * @param a first operand
     * @param b second operand
     * @return Answer from the Calculator Rest Service
     */
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

    /**
     * Make a rest call to the Subtraction Endpoint of the Calculator Rest Service
     * @param a first operand
     * @param b second operand
     * @return Answer from the Calculator Rest Service
     */
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

    /**
     * Make a rest call to the Multiplication Endpoint of the Calculator Rest Service
     * @param a first operand
     * @param b second operand
     * @return Answer from the Calculator Rest Service
     */
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

    /**
     * Make a rest call to the Division Endpoint of the Calculator Rest Service
     * @param a first operand
     * @param b second operand
     * @return Answer from the Calculator Rest Service
     */
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
