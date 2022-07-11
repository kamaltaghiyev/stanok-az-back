package az.stanok.stanokazback.config.validation;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import lombok.AllArgsConstructor;
import org.hibernate.validator.spi.nodenameprovider.JavaBeanProperty;
import org.hibernate.validator.spi.nodenameprovider.Property;
import org.hibernate.validator.spi.nodenameprovider.PropertyNodeNameProvider;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JacksonPropertyNodeNameProvider implements PropertyNodeNameProvider {
    private final ObjectMapper objectMapper;

    @Override
    public String getName(Property property) {
        if ( property instanceof JavaBeanProperty ) {
            return getJavaBeanPropertyName( (JavaBeanProperty) property );
        }

        return getDefaultName( property );
    }

    private String getJavaBeanPropertyName(JavaBeanProperty property) {
        JavaType type = objectMapper.constructType( property.getDeclaringClass() );
        BeanDescription desc = objectMapper.getSerializationConfig().introspect( type );

        return desc.findProperties()
                .stream()
                .filter( prop -> prop.getInternalName().equals( property.getName() ) )
                .map( BeanPropertyDefinition::getName )
                .findFirst()
                .orElse( property.getName() );
    }

    private String getDefaultName(Property property) {
        return property.getName();
    }
}
