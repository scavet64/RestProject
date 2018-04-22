package rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Dr. Baliga on 4/8/18.
 */

@Configuration
public class Config {

    @Bean
    public ExpressionService getCalculatorService() {
        return new ExpressionServiceImpl();
    }
}
