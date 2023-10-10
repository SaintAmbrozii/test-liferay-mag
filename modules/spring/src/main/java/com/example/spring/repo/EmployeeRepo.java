package com.example.spring.repo;

import com.example.spring.domain.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT empl as emloyee FROM employee empl left join purchase p ON  p.employee_id = empl.id left join electronics el ON el.id = p.electro_id WHERE el.etype = 3 AND el.price <1000000", nativeQuery = true)
    List<Employee> bestsaleforsmatrophone();

    @Query(value = "SELECT e,  sum(coalesce(el.price * el.count, 0)) as total FROM employee e inner join purchase p ON e.id = p.employee_id inner join  electronics el ON p.electro_id = el.id WHERE el.etype=3 GROUP BY e ", nativeQuery = true)
    Employee getBestEmloyees();


}
