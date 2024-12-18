package ru.test.taskmanager.models.entities.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

public final class CommonSpecifications 
{
    public static <T> Specification<T> equalIfValueNotNull(String attribute, @Nullable Object value)
    {
        return (root, query, builder) -> value == null ? null : builder.equal(root.get(attribute), value);
    }
}
