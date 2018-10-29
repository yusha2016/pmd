/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.properties.newframework;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author Clément Fournier
 * @since 6.7.0
 */
public abstract class AbstractPropertyDescriptor<T> implements PropertyDescriptor<T> {

    private final String name;
    private final String description;
    private final float uiOrder;
    private final Set<PropertyValidator<T>> validators;
    private final Class<?> type;


    protected AbstractPropertyDescriptor(String name,
                                         String description,
                                         float uiOrder,
                                         Set<PropertyValidator<T>> validators, Class<?> type) {
        this.name = name;
        this.description = description;
        this.uiOrder = uiOrder;
        this.validators = validators;
        this.type = type;
    }


    @Override
    public final String getName() {
        return name;
    }


    @Override
    public final String getDescription() {
        return description;
    }


    @Override
    public final Class<?> getType() {
        return type;
    }


    @Override
    public List<String> getErrorMessagesFor(T value) {
        return validators.stream()
                         .map(validator -> validator.validate(value))
                         .filter(Optional::isPresent)
                         .map(Optional::get)
                         .collect(Collectors.toList());
    }


    @Override
    public final float getUiOrder() {
        return uiOrder;
    }


    @Override
    public final int compareTo(PropertyDescriptor<?> o) {
        return Float.compare(getUiOrder(), o.getUiOrder());
    }
}
