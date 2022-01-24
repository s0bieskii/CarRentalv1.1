package com.car.rental.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@NoArgsConstructor
@Getter
@Setter
@Component
public class EntityUpdater {
    public static final Logger LOGGER = Logger.getLogger(EntityUpdater.class.getName());
    private List<String> doneFields=new ArrayList<>();
    private final String carDetails = "carDetails";

    public Object updateEntity(Object objectToUpdate, Object update) throws IllegalAccessException {
        LOGGER.warning("You should use "+EntityUpdater.class+" carefully! WARNING for fields which you will not update!");
        LOGGER.info("Update : "+objectToUpdate+" with new value from: "+update);
        objectToUpdate=customUpdate(objectToUpdate, update);
        for(Field entityField : objectToUpdate.getClass().getDeclaredFields()){
            for(Field updateField : update.getClass().getDeclaredFields()){
                entityField.setAccessible(true);
                updateField.setAccessible(true);
                if(entityField.getName().equals(updateField.getName()) && entityField.getType().equals(updateField.getType()) &&
                        !doneFields.contains(updateField.getName()) && updateField.get(update)!=null){
                    LOGGER.info("Update "+entityField.getName()+" with value: "+updateField.get(update));
                    entityField.set(objectToUpdate, updateField.get(update));
                }
            }
        }
        return objectToUpdate;
    }

    private Object customUpdate(Object objectToUpdate, Object update) throws IllegalAccessException {
        for(Field entityField:objectToUpdate.getClass().getDeclaredFields()){
            for(Field updateField:update.getClass().getDeclaredFields()){
                entityField.setAccessible(true);
                updateField.setAccessible(true);
                if(entityField.getName().equals(updateField.getName()) && updateField.getName().equals(carDetails)
                    && updateField.get(update)!=null){
                    LOGGER.info("Custom update for field CarDetails in Car entity");
                    this.updateEntity(entityField.get(objectToUpdate), updateField.get(update));
                }
            }
        }
        return objectToUpdate;
    }


}
