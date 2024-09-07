package com.sky.service;

import com.sky.dto.DishDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


public interface DishService {
    /**
     * save new dish with flavors
     * @param dishDTO
     */
    void saveWithFlavor(DishDTO dishDTO);
}
