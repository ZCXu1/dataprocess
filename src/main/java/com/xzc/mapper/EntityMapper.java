package com.xzc.mapper;

import com.xzc.entity.Entity;

import java.util.List;

public interface EntityMapper {
    List<Entity> getEntity();

    int addEntity(Entity entity);
}
