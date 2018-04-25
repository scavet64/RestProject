package rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Initial format created by Dr. Baliga on 4/6/18.
 *
 * Modified by Vincent and Aaron.
 *
 * Calculates values and stores them into a database that lives in memory
 */

public class CalculatorServiceImpl implements CalculatorService{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    ///////// Database Methods /////////

    private Pair<Boolean,Double> getCachedValue(String op, double left, double right) {

        String querystr =
                String.format("SELECT answer FROM cache WHERE operation = '%s' and leftop = %s and rightop = %s",
                        op, left, right);
        try {
            Object answer = jdbcTemplate.queryForObject(querystr, new Object[]{}, Double.class);
            return Pair.of(true, (Double) answer);
        }
        catch (Exception e) {
            return Pair.of(false, 0.0);
        }

    }

    private double insert(String op, double left, double right, double answer) {
        jdbcTemplate.execute("INSERT INTO cache (operation, leftop, rightop, answer) VALUES ('"+
                op + "'," + left + "," + right + "," + answer + ")");
        return answer;
    }

    //////// Interface Implementations /////////

    /**
     * Adds the two parameters. Uses a database to cache previous calculation
     * @param a First Operand
     * @param b Second Operand
     * @return Calculated value
     */
    @Override
    public double add (double a, double b) {
        Pair<Boolean,Double> cached = getCachedValue("add", a, b);
        if (cached.fst)
            return cached.snd;
        else
            return insert("add", a, b, a+b);

    }

    /**
     * Subtracts the two parameters. Uses a database to cache previous calculation
     * @param a First Operand
     * @param b Second Operand
     * @return Calculated value
     */
    @Override
    public double sub(double a, double b) {
        Pair<Boolean,Double> cached = getCachedValue("sub", a, b);
        if (cached.fst)
            return cached.snd;
        else
            return insert("sub", a, b, a-b);
    }

    /**
     * Multiply the two parameters. Uses a database to cache previous calculation
     * @param a First Operand
     * @param b Second Operand
     * @return Calculated value
     */
    @Override
    public double mult(double a, double b) {
        Pair<Boolean,Double> cached = getCachedValue("mult", a, b);
        if (cached.fst)
            return cached.snd;
        else
            return insert("mult", a, b, a*b);
    }

    /**
     * Calculates the power of the two parameters. Uses a database to cache previous calculation
     * @param base base Operand
     * @param power power Operand
     * @return Calculated value
     */
    @Override
    public double power(double base, double power) {
        Pair<Boolean,Double> cached = getCachedValue("power", base, power);
        if (cached.fst)
            return cached.snd;
        else
            return insert("power", base, power, Math.pow(base,power));
    }

    /**
     * Divides the two parameters. Uses a database to cache previous calculation
     * @param a First Operand
     * @param b Second Operand
     * @return Calculated value
     */
    @Override
    public double div(double a, double b) {
        Pair<Boolean,Double> cached = getCachedValue("div", a, b);
        if (cached.fst)
            return cached.snd;
        else
            return insert("div", a, b, a/b);
    }
}
