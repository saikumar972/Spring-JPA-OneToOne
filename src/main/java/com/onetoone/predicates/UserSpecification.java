package com.onetoone.predicates;

import com.onetoone.entity.Address;
import com.onetoone.entity.Student;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {
    public static Specification<Student> equalsName(String name){
        return (root,query,cb)->{
            return cb.equal(root.get("name"),name);
        };
    }

    public static Specification<Student> equalsState(String state){
        return (root,query,cb)->{
            return cb.equal(root.get("address").get("state"),state);
        };
    }

    public static Specification<Student> joinAddress(){
        return (root,query,cb)->{
            Join<Student,Address> address=root.join("address", JoinType.INNER);
            return null;
        };
    }
}
