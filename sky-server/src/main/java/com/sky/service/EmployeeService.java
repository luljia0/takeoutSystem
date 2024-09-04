package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * add new employee
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * employee paging query
     * @param employeePageQueryDTO
     * @return
     */
    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    /**
     * update employee status
     * @param status
     * @param id
     */
    void startOrStop(Integer status, long id);

    /**
     * employee query according to id
     * @param id
     * @return
     */
    Employee getEmpById(long id);

    /**
     * edit employee info
     * @param employeeDTO
     */
    void editEmp(EmployeeDTO employeeDTO);
}
