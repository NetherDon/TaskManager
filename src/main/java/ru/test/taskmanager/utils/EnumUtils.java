package ru.test.taskmanager.utils;

public class EnumUtils 
{
    public static <T extends Enum<T>> T fromOrdinal(int ordinal, Class<T> clazz)
    {
        T[] values = clazz.getEnumConstants();
        if (ordinal < 0 || ordinal >= values.length) 
        {
            throw new IllegalArgumentException("Invalid ordinal for " + clazz.getSimpleName() + ": " + ordinal);
        }
        return values[ordinal];
    }    
}
