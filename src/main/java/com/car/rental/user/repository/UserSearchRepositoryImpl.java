package com.car.rental.user.repository;

import com.car.rental.user.User;
import com.car.rental.user.dto.UserSearchDto;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class UserSearchRepositoryImpl implements UserSearchRepository{

    public static final Logger LOGGER = Logger.getLogger(UserSearchRepositoryImpl.class.getName());
    private final EntityManager entityManager;

    public UserSearchRepositoryImpl(EntityManager entityManager) {
        LOGGER.info("UserSearchRepositoryImpl(" + entityManager + ")");
        this.entityManager = entityManager;
    }

    @Override
    public List<User> find(UserSearchDto userSearchDto) {
        LOGGER.info("find(" + userSearchDto + ")");
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);
        UserCriteriaBuilder userCriteriaBuilder = new UserCriteriaBuilder(user, cb);

        userCriteriaBuilder.addCriteria("id", userSearchDto.getId())
                .addCriteria("firstName", userSearchDto.getFirstName())
                .addCriteria("lastName", userSearchDto.getLastName())
                .addCriteria("birth", userSearchDto.getBirth())
                .addCriteria("email", userSearchDto.getEmail())
                .addCriteria("activated", userSearchDto.getActivated())
                .addCriteria("deleted", false);

        Predicate predicate = userCriteriaBuilder.getPredicate();
        List<User> userList;
        if (predicate == null) {
            LOGGER.info("Predicate is null");
            userList = entityManager.createQuery(cq.select(user).distinct(true)).getResultList();
        } else {
            LOGGER.info("Predicate created");
            userList =
                    entityManager.createQuery(cq.select(user).where(predicate).distinct(true)).getResultList();
        }

        LOGGER.info("Found " + userList.size() + " elements");
        return userList;
    }
}
