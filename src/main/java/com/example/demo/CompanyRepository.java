package com.example.demo;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {
    private List<Company> companies = new ArrayList<>();

    public CompanyRepository() {
        companies.add(new Company(1, "OOCL", Arrays.asList(
                new Employee(1, "Julia", 18, "F", 100000),
                new Employee(2, "Jason", 18, "M", 100000),
                new Employee(3, "Klaus", 18, "M", 100000)
        )));
        companies.add(new Company(2, "DHL", Arrays.asList(
                new Employee(4, "Joanne", 18, "F", 100000),
                new Employee(5, "John", 18, "M", 100000),
                new Employee(6, "Johnson", 18, "M", 100000),
                new Employee(7, "Nicole", 18, "F", 100000)
        )));
        companies.add(new Company(3, "SF Express", Arrays.asList(
                new Employee(8, "Gloria", 18, "F", 100000)
        )));
    }


    public List<Company> findAll() {
        return companies;
    }

    public Company findById(Integer id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> findAllEmployeesByCompanyId(Integer id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .map(Company::getEmployees)
                .orElseThrow(NoCompanyFoundException::new);
    }
}
