package com.car.rental.employee.repository;

import com.car.rental.employee.Employee;
import com.car.rental.employee.dto.EmployeeSearchDto;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class EmployeeRepositoryImpl implements EmployeeSearchRepository {

    public static final Logger LOGGER = Logger.getLogger(EmployeeSearchRepository.class.getName());
    private final EntityManager entityManager;

    public EmployeeRepositoryImpl(EntityManager entityManager) {
        LOGGER.info("EmployeeSearchImpl(" + entityManager + ")");
        this.entityManager = entityManager;
    }


    @Override
    public List<Employee> find(EmployeeSearchDto employeeSearchDto) {
        LOGGER.info("find(" + employeeSearchDto + ")");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
        Root<Employee> employee = cq.from(Employee.class);
        EmployeeCriteriaBuilder employeeCriteriaBuilder = new EmployeeCriteriaBuilder(employee, cb);

        employeeCriteriaBuilder.addCriteria("id", employeeSearchDto.getId())
                .addCriteria("firstName", employeeSearchDto.getFirstName())
                .addCriteria("lastName", employeeSearchDto.getLastName())
                .addCriteria("employmentPosition", employeeSearchDto.getEmploymentPosition())
                .addCriteria("deleted", false);

        Predicate predicate = employeeCriteriaBuilder.getPredicate();
        List<Employee> employeeList;
        if (predicate == null) {
            LOGGER.info("Predicate is null");
            employeeList = entityManager.createQuery(cq.select(employee).distinct(true)).getResultList();
        } else {
            LOGGER.info("Predicate created");
            employeeList =
                    entityManager.createQuery(cq.select(employee).where(predicate).distinct(true)).getResultList();
        }
        LOGGER.info("Found " + employeeList.size() + " elements");
        return employeeList;
    }
}
