package com.onetoone.dao;

import com.onetoone.dto.StudentDetails;
import com.onetoone.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> , JpaSpecificationExecutor<Student> {
    //pagination and sorting
    @Query(value = "select s from Student s where s.name like concat(:name,'%')")
    List<Student> studentRecordsStartWith(@Param("name") String name, Pageable pageable);

    @Query(value = "select s from Student s where s.name=:name")
    List<Student> findStudentWithNameAndSort(@Param("name") String name, Pageable pageable);

    @Query(value = "select s from Student s where s.name=:name")
     Page<Student> findStudentsWithNameAndSort(@Param("name") String name, Pageable pageable);

    //joins
    @Query(value = "select s from Student s join s.address where s.name=:name")
    Student getStudentDetailsByName(@Param("name") String name);

    @Query(value = "select s.name,s.department,a.state from Student s join s.address a where s.name=:name")
    List<Object[]> getBasicStudentDetails(@Param("name") String name);

    @Query(value = "select new com.onetoone.dto.StudentDetails(s.name,s.department,a.state) from Student s join s.address a where s.name=:name")
    List<StudentDetails> getBasicDetailsV2(String name);

    @Query(value = "select new com.onetoone.dto.StudentDetails(s.name,s.department,a.state) from Student s join s.address a where s.name like concat(:name,'%')")
    List<StudentDetails> getStudentBasicDetailsNameStartsWith(@Param("name") String name);


    //N+1
    @Query(value = "select s from Student s join s.address a where s.name=:name")
    List<Student> getStudentDetailsN(@Param("name") String name);

    @Query(value = "select s from Student s join fetch s.address a where s.name=:name")
    List<Student> getStudentDetailsJoinFetch(@Param("name") String name);

    @EntityGraph(attributePaths = "address")
    List<Student> findStudentByName(@Param("name") String name);

    //update
    @Transactional
    @Modifying
    @Query(value = "update Student s set  s.name=:name where s.id=:id ")
    int updateStudentById(@Param("id") int id,@Param("name") String name);

    //delete
    @Transactional
    @Modifying(flushAutomatically = true,clearAutomatically = true)
    @Query(value = "delete from Student s where s.id=:id")
    void deleteStudentById(@Param("id") int id);

    //native query
    @Query(value = "select s.name,s.department,a.state from student s join student_address a on s.address_id=a.id where s.name=:name",nativeQuery = true)
    List<Object[]> studentDetailsNative(@Param("name")String name);

    @Query(name = "userDetails",nativeQuery = true)
    List<StudentDetails> studentDetailsNativeV2(@Param("name")String name);
}
