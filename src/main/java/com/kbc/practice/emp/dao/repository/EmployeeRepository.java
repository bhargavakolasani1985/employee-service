package com.kbc.practice.emp.dao.repository;

import com.kbc.practice.emp.dao.domain.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
}
