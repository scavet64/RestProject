package rest;

/**
 * Created by Dr. Baliga on 4/6/18.
 */
public interface CalculatorService {
    
    double add(double a, double b);
    double sub(double a, double b);
    double mult(double a, double b);
    double power(double base, double power);
    double div(double a, double b);

}
