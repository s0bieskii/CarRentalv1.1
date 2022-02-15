package com.car.rental.employee.repository;

import com.car.rental.employee.Employee;
import com.car.rental.employee.dto.EmployeeSearchDto;
import com.car.rental.rental.Rental;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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

        if(employeeSearchDto.getRentalId()!=null){
            employeeList = filterByRentalId(employeeSearchDto, employeeList);
        }
        LOGGER.info("Found " + employeeList.size() + " elements");
        return employeeList;
    }

    private List<Employee> filterByRentalId(EmployeeSearchDto employeeDto, List<Employee> carListToFilter) {
        LOGGER.info("filterByRentalId(" + employeeDto + ", " + carListToFilter + ")");
        if (employeeDto.getRentalId() != null || carListToFilter.isEmpty()) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Rental> cq = cb.createQuery(Rental.class);
            Root<Rental> rental = cq.from(Rental.class);
            CriteriaQuery<Rental> cqRental = cb.createQuery(Rental.class);
            LOGGER.info("Filter employee by rental id");
            cqRental.getRoots().add(rental);
            cqRental.select(rental);
            Predicate predicate = cb.equal(rental.get("id"), employeeDto.getRentalId());

            List<Employee> employeeFromRental =
                    entityManager.createQuery(cq.select(rental).where(predicate).distinct(true)).getResultList()
                            .stream().map(Rental::getEmployees)
                            .flatMap(List::stream).collect(Collectors.toList());
            carListToFilter =
                    carListToFilter.stream().filter(e -> employeeFromRental.contains(e)).collect(Collectors.toList());
            LOGGER.info(
                    "Rental has all employees: " + employeeFromRental.size() + " and matching for searching parameter employee: " +
                            carListToFilter.size());
            return carListToFilter;
        }
        return carListToFilter;
    }
}
