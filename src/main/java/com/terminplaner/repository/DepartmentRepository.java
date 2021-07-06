package com.terminplaner.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.terminplaner.entity.Department;

public interface DepartmentRepository extends PagingAndSortingRepository<Department, Integer> {

}
