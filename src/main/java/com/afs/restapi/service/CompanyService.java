package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company edit(Integer id, Company updatedCompany) {
        Company company = companyRepository.findById(id);
        if (updatedCompany.getCompanyName() != null) {
            company.setCompanyName(updatedCompany.getCompanyName());
        }
        if (updatedCompany.getEmployees() != null) {
            company.setEmployees(updatedCompany.getEmployees());
        }
        return companyRepository.save(id, company);
    }

    public Company findById(Integer id) {
        return companyRepository.findById(id);
    }

    public List<Employee> findAllEmployeesByCompanyId(Integer id) {
        return companyRepository.findAllEmployeesByCompanyId(id);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
        return companyRepository.findByPage(page, pageSize);
    }

    public Company create(Company company) {
        return companyRepository.create(company);
    }

//    public Employee remove(Integer id) {
//        return employeeRepository.remove(id);
//    }
}
