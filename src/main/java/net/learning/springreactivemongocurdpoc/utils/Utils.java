package net.learning.springreactivemongocurdpoc.utils;

import net.learning.springreactivemongocurdpoc.dto.Dto;
import net.learning.springreactivemongocurdpoc.entity.Entity;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

public class Utils {
    public static Dto entityToDto(Entity Entity) {
        Dto Dto = new Dto();
        BeanUtils.copyProperties(Entity, Dto);
        return Dto;
    }

    public static Entity dtoToEntity(Dto Dto) {
        Entity entity = new Entity();
        BeanUtils.copyProperties(Dto, entity);
        entity.setId(UUID.randomUUID().toString());
        return entity;
    }
}
