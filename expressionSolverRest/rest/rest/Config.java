package rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Initial format created by Dr. Baliga on 4/6/18.
 *
 * Modified by Vincent and Aaron
 */

@Configuration
public class Config {

    @Bean
    public ExpressionService getCalculatorService() {
        return new ExpressionServiceImpl();
    }
}
