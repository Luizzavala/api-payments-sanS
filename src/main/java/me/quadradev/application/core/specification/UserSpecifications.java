package me.quadradev.application.core.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import me.quadradev.application.core.model.*;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
    public static Specification<User> hasEmail(String email) {
        return (root, query, cb) -> {
            if (email == null || email.isBlank()) {
                return null;
            }
            return cb.equal(root.get(User_.email), email);
        };
    }

    public static Specification<User> hasStatus(UserStatus status) {
        return (root, query, cb) -> {
            if (status == null) {
                return null;
            }
            return cb.equal(root.get(User_.status), status);
        };
    }

    public static Specification<User> hasFirstName(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return null;
            }
            Join<User, Person> personJoin = root.join(User_.person, JoinType.INNER);
            return cb.equal(personJoin.get(Person_.firstName), name);
        };
    }
}
