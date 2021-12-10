package com.afs.restapi.service;

import com.afs.restapi.entity.Company;
import com.afs.restapi.entity.Employee;
import com.afs.restapi.exception.NoCompanyFoundException;
import com.afs.restapi.repository.CompanyRepository;
import com.afs.restapi.repository.CompanyRepositoryInMongo;
import com.afs.restapi.repository.EmployeeRepositoryInMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyRepositoryInMongo companyRepositoryInMongo;
    private final EmployeeRepositoryInMongo employeeRepositoryInMongo;

    public CompanyService(CompanyRepository companyRepository, CompanyRepositoryInMongo companyRepositoryInMongo, EmployeeRepositoryInMongo employeeRepositoryInMongo) {
        this.companyRepository = companyRepository;
        this.companyRepositoryInMongo = companyRepositoryInMongo;
        this.employeeRepositoryInMongo = employeeRepositoryInMongo;
    }

    public List<Company> findAll() {
        List<Company> companies = companyRepositoryInMongo.findAll();
        for (Company company : companies) {
            if (findAllEmployeesByCompanyId(company.getId()).size() != 0) {
                company.setEmployees(findAllEmployeesByCompanyId(company.getId()));
            }
        }
        return companies;
    }

    public Company edit(String id, Company updatedCompany) {
        Company company = companyRepositoryInMongo.findById(id)
                .orElseThrow(NoCompanyFoundException::new);
        if (updatedCompany.getCompanyName() != null) {
            company.setCompanyName(updatedCompany.getCompanyName());
        }
//        company.setEmployees(findAllEmployeesByCompanyId(company.getId()));
        return companyRepositoryInMongo.save(company);
    }

    public Company findById(String id) {
//        company.setEmployees(findAllEmployeesByCompanyId(company.getId()));
        return companyRepositoryInMongo.findById(id)
                .orElseThrow(NoCompanyFoundException::new);
    }

    public List<Employee> findAllEmployeesByCompanyId(String id) {
        return employeeRepositoryInMongo.getEmployeesByCompanyId(id);
    }

    public List<Company> findByPage(Integer page, Integer pageSize) {
//        companies.forEach(company -> company.setEmployees(findAllEmployeesByCompanyId(company.getId())));
        return companyRepositoryInMongo.findAll(PageRequest.of(page, pageSize))
                .stream()
                .collect(Collectors.toList());
    }

    public Company create(Company company) {
        return companyRepositoryInMongo.insert(company);
    }

    public void remove(String id) {
        companyRepositoryInMongo.deleteById(id);
    }
}
