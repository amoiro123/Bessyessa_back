package com.bessy.productservice.mappers;

import com.bessy.productservice.dto.ItemDTO;
import lombok.extern.slf4j.Slf4j;


import java.lang.reflect.Field;
import java.util.UUID;

@Slf4j
public class DropdownItemMapper {
    public static <T> ItemDTO mapToItemDTO(T obj) {
        ItemDTO itemDTO = new ItemDTO();

        try {
            // Reflectively find and set the value for `label` (look for `name`, `reference`, etc.)
            Field labelField = findField(obj, "name", "reference", "label");
            if (labelField != null) {
                labelField.setAccessible(true);
                String labelValue = (String) labelField.get(obj);
                itemDTO.setLabel(labelValue);
            }

            // Reflectively find and set the value for `value` (look for `id`, `uuid`, etc.)
            Field valueField = findField(obj, "id", "uuid", "value");
            if (valueField != null) {
                valueField.setAccessible(true);
                UUID idValue = (UUID) valueField.get(obj);
                itemDTO.setValue(idValue);
            }

        } catch (IllegalAccessException e) {
            log.error("mapToItemDTO error: {}", e.getMessage());
            throw new RuntimeException("Error mapping object to ItemDTO");
        }

        return itemDTO;
    }

    // Helper method to find the first matching field name
    private static <T> Field findField(T obj, String... fieldNames) {
        Class<?> clazz = obj.getClass();
        for (String fieldName : fieldNames) {
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                log.error("findField error: {}", e.getMessage());
            }
        }
        return null; // Return null if no matching field is found
    }
}
