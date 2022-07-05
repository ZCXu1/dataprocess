package com.xzc.entity;

/**
 * @Author: ZCXu1
 * @Date: 2022/7/5 20:20
 * @Version: 1.0.0
 * @Description:
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vector {
    private int entityId;
    // feature vector of the entity
    private String vector;
}
