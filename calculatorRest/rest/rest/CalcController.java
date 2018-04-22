package rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.Inet4Address;

@RestController
public class CalcController {

    @Autowired
    CalculatorService calcservice;

    @RequestMapping("/add")
    public String add(
            @RequestParam(value="a", defaultValue="0") String a,
            @RequestParam(value="b", defaultValue="0") String b) {
        return Double.toString(calcservice.add(getDoubleFromString(a),getDoubleFromString(b)));
    }

    @RequestMapping("/sub")
    public String sub(
            @RequestParam(value="a", defaultValue="0") String a,
            @RequestParam(value="b", defaultValue="0") String b) {
        return Double.toString(calcservice.sub(getDoubleFromString(a),getDoubleFromString(b)));
    }

    @RequestMapping("/mult")
    public String mult(
            @RequestParam(value="a", defaultValue="1") String a,
            @RequestParam(value="b", defaultValue="1") String b) {
        return Double.toString(calcservice.mult(getDoubleFromString(a),getDoubleFromString(b)));
    }

    @RequestMapping("/div")
    public String div(
            @RequestParam(value="a", defaultValue="1") String a,
            @RequestParam(value="b", defaultValue="1") String b) {
        return Double.toString(calcservice.div(getDoubleFromString(a),getDoubleFromString(b)));
    }

    @RequestMapping("/power")
    public String power(
            @RequestParam(value="a", defaultValue="1") String a,
            @RequestParam(value="b", defaultValue="1") String b) {
        return Double.toString(calcservice.power(getDoubleFromString(a),getDoubleFromString(b)));
    }

    private double getDoubleFromString(String param){
        if(param.contains(".")){
            return Double.parseDouble(param);
        } else {
            return (double) Integer.parseInt(param);
        }
    }

}
