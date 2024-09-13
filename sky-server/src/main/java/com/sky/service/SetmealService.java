package com.sky.service;

import com.sky.dto.SetmealDTO;

public interface SetmealService {
    /**
     * add new seatmeal and the relationship between setmeal and dishes
     * @param setmealDTO
     */

    void saveWithDish(SetmealDTO setmealDTO);
}
