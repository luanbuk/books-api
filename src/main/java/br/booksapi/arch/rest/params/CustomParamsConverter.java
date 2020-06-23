package br.booksapi.arch.rest.params;

import br.booksapi.arch.rest.Page;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Provider
public class CustomParamsConverter implements ParamConverterProvider {

    private static final Map<Class<?>, Supplier<ParamConverter<?>>> converters = new HashMap<>();

    static {
        converters.put(Page.class, PageParamConverter::new);
    }

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {
        return converters.containsKey(rawType) ? (ParamConverter<T>) converters.get(rawType).get() : null;
    }
}
