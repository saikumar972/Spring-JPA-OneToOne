package com.onetoone.service;

import com.onetoone.dao.StudentRepo;
import com.onetoone.dto.StudentData;
import com.onetoone.dto.StudentDetails;
import com.onetoone.dto.StudentRecord;
import com.onetoone.entity.Address;
import com.onetoone.entity.Student;
import com.onetoone.predicates.UserSpecification;
import com.onetoone.utility.StudentResponseMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceV5 {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    StudentResponseMapper studentResponseMapper;
    @Autowired
    StudentRepo studentRepo;

    public List<StudentData> dynamicNativeQuery(String name,String state){
        StringBuilder queryBuilder=new StringBuilder("select s.name,s.department,a.street,a.district,a.state ");
        queryBuilder.append("from student s join student_address a ");
        queryBuilder.append("on s.address_id=a.id ");
        queryBuilder.append("where 1=1 ");
        //parameter list
        List<Object> parameters=new ArrayList<>();
        //append name
        if(name!=null && !name.isBlank()){
            queryBuilder.append("and s.name=? ");
            parameters.add(name);
        }
        //append state
        if(state!=null&&!state.isBlank()){
            queryBuilder.append("and a.state=? ");
            parameters.add(state);
        }
        //adding order
        queryBuilder.append("order by a.state desc ");
        //adding pagination
        int size=3;
        int page=0;
        queryBuilder.append("limit ? offset ? ");
        parameters.add(size);
        parameters.add(page);
        Query query= entityManager.createNativeQuery(queryBuilder.toString());
        for(int i=0;i< parameters.size();i++){
            query.setParameter(i+1,parameters.get(i));
        }

        List<Object[]> resultList=query.getResultList();

        return resultList.stream()
                .map(result->{
                    return new StudentData((String) result[0],(String) result[1],(String) result[2],(String) result[3],(String) result[4]);
                }).toList();
    }

    //criteria API
    public List<StudentRecord> getStudentDetailsByCriteriaBuilder(String name, String state){
        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        //each row is the student
        //the output form
        CriteriaQuery<Student> criteriaQuery=cb.createQuery(Student.class);
        //from which source like entity
        Root<Student> studentRoot=criteriaQuery.from(Student.class);
        //select * from student
        //selecting all fields from the entity
        criteriaQuery.select(studentRoot);
        //where
        Predicate predicate1=null;
        if(name!=null && !name.isBlank()){
            predicate1=cb.equal(studentRoot.get("name"),name);
        }
        Predicate predicate2=null;
        if(state!=null && !state.isBlank()){
            predicate2=cb.equal(studentRoot.get("address").get("state"),state);
        }
        Predicate combinedPredicate=cb.and(predicate1,predicate2);
        criteriaQuery.where(combinedPredicate);
        //query to execute
        TypedQuery<Student> query = entityManager.createQuery(criteriaQuery);
        //map to result
        List<Student> students=query.getResultList();
        return students.stream()
                .map(studentResponseMapper).toList();
    }
    //criteria API predicates
    public List<StudentRecord> getStudentDetailsByCriteriaBuilderV2(String name, String state){
        CriteriaBuilder cb= entityManager.getCriteriaBuilder();
        //each row is the student
        //the output form
        CriteriaQuery<Student> criteriaQuery=cb.createQuery(Student.class);
        //from which source like entity
        Root<Student> studentRoot=criteriaQuery.from(Student.class);
        //select * from student
        //selecting all fields from the entity
        criteriaQuery.select(studentRoot);
        //where
        List<Predicate> predicates=new ArrayList<>();
        if(name!=null && !name.isBlank()){
            predicates.add(cb.equal(studentRoot.get("name"),name));
        }
        if(state!=null && !state.isBlank()){
            predicates.add(cb.equal(studentRoot.get("address").get("state"),state));
        }
        if(!predicates.isEmpty()){
            criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0])));
        }
        //query to execute
        TypedQuery<Student> query = entityManager.createQuery(criteriaQuery);
        //map to result
        List<Student> students=query.getResultList();
        return students.stream()
                .map(studentResponseMapper).toList();
    }

    //specific fields in criteria API
    public List<StudentDetails> getStudentBasicDetailsByCriteriaAPI(String nameStartsWith,String state){
        //criteria Builder
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        //result fields
        CriteriaQuery<Object[]> criteriaQuery=cb.createQuery(Object[].class);
        //root
        Root<Student> student=criteriaQuery.from(Student.class);
        //selecting fields
        criteriaQuery.multiselect(student.get("name"),student.get("department"),student.get("address").get("state"));
        //where
        Predicate predicate1=null;
        if(nameStartsWith!=null&&!nameStartsWith.isBlank()){
            predicate1=cb.like(student.get("name"),nameStartsWith+"%");
        }
        Predicate predicate2=null;
        if(state!=null&&!state.isBlank()){
            predicate2=cb.equal(student.get("address").get("state"),state);
        }
        Predicate combinedPredicate=cb.and(predicate1,predicate2);
        criteriaQuery.where(combinedPredicate);
        //query
        TypedQuery<Object[]> query=entityManager.createQuery(criteriaQuery);
        return query.getResultList()
                .stream().map(result->new StudentDetails((String)result[0],(String)result[1],(String)result[2])).toList();
    }
    //joins and order and pagination
    public List<StudentDetails> getStudentBasicDetailsByCriteriaAPIJoins(String nameStartsWith,String state){
        //create criteriaBuilder
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        //create criteriaQuery
        CriteriaQuery<Object[]> criteriaQuery=cb.createQuery(Object[].class);
        //source
        Root<Student> student=criteriaQuery.from(Student.class);
        //join
        Join<Student,Address> address=student.join("address",JoinType.INNER);
        //grabbing fields
        criteriaQuery.multiselect(student.get("name"), student.get("department"), address.get("state"));
        //where
        Predicate predicate1=null;
        if(nameStartsWith!=null&&!nameStartsWith.isBlank()){
            predicate1=cb.like(student.get("name"),nameStartsWith+"%");
        }
        Predicate predicate2=null;
        if(state!=null&&!state.isBlank()){
            predicate2=cb.equal(address.get("state"),state);
        }
        Predicate combinedPredicate=cb.and(predicate1,predicate2);
        criteriaQuery.where(combinedPredicate);
        //ordering
        criteriaQuery.orderBy(cb.desc(cb.lower(student.get("department"))));

        //query
        TypedQuery<Object[]> query=entityManager.createQuery(criteriaQuery);
        //page and records
        query.setFirstResult(0);
        query.setMaxResults(2);
        return query.getResultList()
                .stream().map(result->new StudentDetails((String)result[0],(String)result[1],(String)result[2])).toList();
    }

    //specification API
    public List<StudentDetails> getStudentDetailsUsingSpecificationAPI(String name,String state){
        //create criteriaBuilder
        CriteriaBuilder cb=entityManager.getCriteriaBuilder();
        //create criteriaQuery
        CriteriaQuery<Object[]> criteriaQuery=cb.createQuery(Object[].class);
        //source
        Root<Student> student=criteriaQuery.from(Student.class);
        //join
        Join<Student,Address> address=student.join("address",JoinType.INNER);
        //grabbing fields
        criteriaQuery.multiselect(student.get("name"), student.get("department"), address.get("state"));
        //where
       Specification<Student> specification1=UserSpecification.equalsName(name);
        Specification<Student> specification2=UserSpecification.equalsState(state);
        Specification<Student> combinedSpecification=specification1.and(specification2);
        Predicate predicate = combinedSpecification.toPredicate(student, criteriaQuery, cb);
        criteriaQuery.where(predicate);
        //ordering
        criteriaQuery.orderBy(cb.desc(cb.lower(student.get("department"))));

        //query
        TypedQuery<Object[]> query=entityManager.createQuery(criteriaQuery);
        //page and records
        query.setFirstResult(0);
        query.setMaxResults(2);
        return query.getResultList()
                .stream().map(result->new StudentDetails((String)result[0],(String)result[1],(String)result[2])).toList();
    }

    public List<StudentRecord> getStudentDetailsUsingSpecificationAPIV2(String name, String state){
       Specification<Student> studentSpecification=Specification
               .where(UserSpecification.joinAddress())
               .and(UserSpecification.equalsName(name))
               .and(UserSpecification.equalsState(state));

        return studentRepo.findAll(studentSpecification)
                .stream()
                .map(studentResponseMapper)
                .toList();
    }

}
