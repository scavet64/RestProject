package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vincent and Aaron
 */
@RestController
public class ExpressionSolverController {

    private static final String template = "The answer is %d";

    @Autowired
    ExpressionService expressionService;

    /**
     * Solve the expression provided in the body
     * @param expression Expression to solve
     * @return Calculated Expression
     */
    @RequestMapping(path = "/solve", method = RequestMethod.POST)
    public String solve(
            @RequestBody() String expression) {
        return Double.toString(expressionService.solve(expression));
    }
}
