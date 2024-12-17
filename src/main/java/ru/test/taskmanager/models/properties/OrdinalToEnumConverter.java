package ru.test.taskmanager.models.properties;

import org.springframework.core.convert.converter.Converter;

import ru.test.taskmanager.utils.EnumUtils;

public class OrdinalToEnumConverter<T extends Enum<T>> implements Converter<String, T>
{
    private final Class<T> clazz;

    public OrdinalToEnumConverter(Class<T> clazz)
    {
        this.clazz = clazz;
    }

    @Override
    public T convert(String source) 
    {
        return EnumUtils.fromOrdinal(Integer.parseInt(source), clazz);
    }
    
}
