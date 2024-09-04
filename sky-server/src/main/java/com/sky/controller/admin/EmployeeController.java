package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "apis related to employees ")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("employee login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        // generate jwt taken if successfully login
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        log.info("JWT token: {}", token);
        // generate an employee VO to frontend
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * log out
     *
     * @return
     */
    @ApiOperation("employee logout")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * add employee
     * @param employeeDTO
     * @return
     */
    @ApiOperation("add new employee")
    @PostMapping
    public Result save(@RequestBody EmployeeDTO employeeDTO) {
        log.info("Add new employee");
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * employee paging query
     * @param employeePageQueryDTO
     * @return
     */
    @ApiOperation("employee paging query")
    @GetMapping("page")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO) {
        PageResult page =  employeeService.pageQuery(employeePageQueryDTO);
        log.info("Employee paging query:" + employeePageQueryDTO.getPage() +   employeePageQueryDTO.getPageSize());
        return Result.success(page);
    }

    /**
     * update employee status
     * @param status
     * @param id
     * @return
     */
    @ApiOperation("update employee status")
    @PostMapping("/status/{status}")
    public  Result status(@PathVariable Integer status, long id) {
        log.info("update employee status");
        employeeService.startOrStop(status, id);
        return Result.success();
    }

    /**
     *  employee id query
     * @param id
     * @return
     */
    @ApiOperation("employee id query")
    @GetMapping("/{id}")
    public Result<Employee> idQuery(@PathVariable long id) {
        log.info("employee id query:{}", id);
        Employee employee = employeeService.getEmpById(id);
        return Result.success(employee);
    }
    @ApiOperation("edit employee info")
    @PutMapping
    public Result editEmp(@RequestBody EmployeeDTO employeeDTO) {
        log.info("editEmp employeeDTO:{}", employeeDTO);
        employeeService.editEmp(employeeDTO);
        return Result.success();
    }


}
