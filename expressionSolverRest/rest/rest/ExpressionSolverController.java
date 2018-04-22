package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExpressionSolverController {

    private static final String template = "The answer is %d";

    @Autowired
    ExpressionService expressionService;

    @RequestMapping(path = "/solve", method = RequestMethod.POST)
    public String solve(
            @RequestBody() String expression) {

        //return "testing";

        return Double.toString(expressionService.solve(expression));
    }

}
