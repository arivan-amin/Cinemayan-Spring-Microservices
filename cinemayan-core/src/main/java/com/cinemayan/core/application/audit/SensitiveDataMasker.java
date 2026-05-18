package com.cinemayan.core.application.audit;

import com.cinemayan.core.domain.audit.SensitiveData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
@Slf4j
class SensitiveDataMasker {

    private static final String MASK_VALUE = "***MASKED***";

    private final AuditSensitiveProperties sensitiveProperties;

    String maskParameterIfSensitive (String paramName, Object value) {
        if (value == null) {
            return "null";
        }
        if (isFieldNameInSensitiveProperties(paramName)) {
            return MASK_VALUE;
        }
        if (isSimpleType(value)) {
            return value.toString();
        }
        return maskObjectFields(value);
    }

    private boolean isFieldNameInSensitiveProperties (String name) {
        return sensitiveProperties.fields()
            .stream()
            .anyMatch(sensitive -> sensitive.equalsIgnoreCase(name));
    }

    private boolean isSimpleType (Object value) {
        return value instanceof String || value instanceof Number || value instanceof Boolean ||
            value instanceof Enum || value.getClass()
            .isPrimitive();
    }

    private String maskObjectFields (Object obj) {
        Map<String, String> maskedFields = new ConcurrentHashMap<>();
        collectFields(obj.getClass()).forEach(field -> {
            try {
                maskFieldIfSensitive(obj, field, maskedFields);
            }
            catch (IllegalAccessException e) {
                log.error("Could not access field '{}' on type '{}'", field.getName(),
                    obj.getClass()
                        .getSimpleName());
                maskedFields.put(field.getName(), MASK_VALUE);
            }
        });
        return maskedFields.toString();
    }

    private List<Field> collectFields (Class<?> clazz) {
        List<Field> fields = new ArrayList<>();
        Class<?> current = clazz;
        while (current != null && current != Object.class && isUserDefinedClass(current)) {
            Arrays.stream(current.getDeclaredFields())
                .filter(field -> !isSkippableField(field))
                .forEach(fields::add);
            current = current.getSuperclass();
        }
        return fields;
    }

    private void maskFieldIfSensitive (Object obj, Field field, Map<String, String> maskedFields)
        throws IllegalAccessException {
        field.setAccessible(true);
        Object fieldValue = field.get(obj);
        String stringValue = fieldValue == null ? "null" : fieldValue.toString();
        maskedFields.put(field.getName(),
            isAnnotatedWithSensitiveData(field) ? MASK_VALUE : stringValue);
    }

    private boolean isUserDefinedClass (Class<?> clazz) {
        return clazz.getPackageName()
            .startsWith("com.cinemayan.");
    }

    private boolean isSkippableField (Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers) || field.isSynthetic();
    }

    private boolean isAnnotatedWithSensitiveData (Field field) {
        return field.isAnnotationPresent(SensitiveData.class) ||
            isFieldNameInSensitiveProperties(field.getName());
    }
}
