package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;

/**
 * Initial format created by Dr. Baliga on 4/6/18.
 *
 * Modified by Vincent and Aaron.
 *
 * Main Spring Controller for the Calculator microservice
 */
@RestController
public class CalcController {

    @Autowired
    CalculatorService calcservice;

    /**
     * Add the two parameters together
     * @param a First Operand
     * @param b Second Operand
     * @return calculated result
     */
    @RequestMapping("/add")
    public String add(
            @RequestParam(value="a", defaultValue="0") String a,
            @RequestParam(value="b", defaultValue="0") String b) {
        return Double.toString(calcservice.add(getDoubleFromString(a),getDoubleFromString(b)));
    }

    /**
     * Subtracts the two parameters
     * @param a First Operand
     * @param b Second Operand
     * @return calculated result
     */
    @RequestMapping("/sub")
    public String sub(
            @RequestParam(value="a", defaultValue="0") String a,
            @RequestParam(value="b", defaultValue="0") String b) {
        return Double.toString(calcservice.sub(getDoubleFromString(a),getDoubleFromString(b)));
    }

    /**
     * multiplys the two parameters
     * @param a First Operand
     * @param b Second Operand
     * @return calculated result
     */
    @RequestMapping("/mult")
    public String mult(
            @RequestParam(value="a", defaultValue="1") String a,
            @RequestParam(value="b", defaultValue="1") String b) {
        return Double.toString(calcservice.mult(getDoubleFromString(a),getDoubleFromString(b)));
    }

    /**
     * Divides the two parameters
     * @param a First Operand
     * @param b Second Operand
     * @return calculated result
     */
    @RequestMapping("/div")
    public String div(
            @RequestParam(value="a", defaultValue="1") String a,
            @RequestParam(value="b", defaultValue="1") String b) {
        return Double.toString(calcservice.div(getDoubleFromString(a),getDoubleFromString(b)));
    }

    /**
     * Calculates the power of the two parameters. A is the base B is the power
     * @param a First Operand
     * @param b Second Operand
     * @return calculated result
     */
    @RequestMapping("/power")
    public String power(
            @RequestParam(value="a", defaultValue="1") String a,
            @RequestParam(value="b", defaultValue="1") String b) {
        return Double.toString(calcservice.power(getDoubleFromString(a),getDoubleFromString(b)));
    }

    /**
     * Returns a double from a string parameter.
     * Correctly uses Integer or Double parse
     * @param param string to parse
     * @return double value
     */
    private double getDoubleFromString(String param){
        if(param.contains(".")){
            return Double.parseDouble(param);
        } else {
            return (double) Integer.parseInt(param);
        }
    }

}
