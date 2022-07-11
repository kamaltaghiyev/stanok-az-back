package az.stanok.stanokazback.config.validation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
public class ValidationConfig {
    @Bean
    public Validator validator(ObjectMapper objectMapper) {
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .propertyNodeNameProvider(new JacksonPropertyNodeNameProvider(objectMapper))
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
