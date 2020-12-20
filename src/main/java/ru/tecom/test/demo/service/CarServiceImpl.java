package ru.tecom.test.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.tecom.test.demo.entity.Car;
import ru.tecom.test.demo.repository.CarRepository;
import ru.tecom.test.demo.validator.SearchFieldsValidator;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.tecom.test.demo.cosntants.CarSearchFieldKeys.*;

@Service
public class CarServiceImpl implements CarService{

    private final CarRepository repository;
    private final SearchFieldsValidator validator;

    @Autowired
    public CarServiceImpl(CarRepository repository, SearchFieldsValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    @Override
    public List<Car> findAll() {
        return repository.findAll();
    }

    @Override
    public Car findById(long id) {
        Optional<Car> result = repository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else throw new RuntimeException("Did not find car id - " + id);
    }

    @Override
    public void save(Car car) {
        repository.save(car);
    }

    @Override
    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public List<Car> findAllByParameters(Map<String, String> parameters) {
        String sortField = parameters.get(SORT_FIELD);
        String sortDirection = parameters.get(SORT_DIRECTION);
        String validatedField = validator.validateCarField(sortField) ? sortField : "id";
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(validatedField).descending() :
                Sort.by(validatedField).ascending();
        return repository.findAll(createSpecificationByParameters(parameters), sort);
    }

    Specification<Car> createSpecificationByParameters(Map<String, String> parameters){
        return (Specification<Car>) (root, criteriaQuery, criteriaBuilder) -> {
            Predicate p = criteriaBuilder.conjunction();
            if (isNotEmpty(parameters.get(REQ_BRAND))) {
                p = criteriaBuilder.and(p, criteriaBuilder.like(criteriaBuilder.upper(root.get("brand")),
                        "%" + parameters.get(REQ_BRAND).toUpperCase() + "%"));
            }
            if (isNotEmpty(parameters.get(REQ_MODEL))) {
                p = criteriaBuilder.and(p, criteriaBuilder.like(criteriaBuilder.upper(root.get("model")),
                        "%" + parameters.get(REQ_MODEL).toUpperCase() + "%"));
            }
            if (isNotEmpty(parameters.get(REQ_YEAR_EQ))) {
                p = criteriaBuilder.and(p, criteriaBuilder.equal(root.get("year"), parameters.get(REQ_YEAR_EQ)));
            }
            if (isNotEmpty(parameters.get(REQ_TURBO))) {
                if ("y".equals(parameters.get(REQ_TURBO))){
                    p = criteriaBuilder.and(p, criteriaBuilder.isTrue(root.get("turbo")));
                } else if ("n".equals(parameters.get(REQ_TURBO))){
                    p = criteriaBuilder.and(p, criteriaBuilder.isFalse(root.get("turbo")));
                }
            }
            if (isNotEmpty(parameters.get(REQ_COLOR))) {
                p = criteriaBuilder.and(p, criteriaBuilder.like(criteriaBuilder.upper(root.get("color")),
                        "%" + parameters.get(REQ_COLOR).toUpperCase() + "%"));
            }
            return p;
        };
    }


    private boolean isNotEmpty(String string){
        return (string != null && !"".equals(string));
    }
}
