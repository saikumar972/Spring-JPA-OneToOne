package com.onetoone.dao;

import com.onetoone.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {
    @Query(value = "select s from Student s where s.name like concat(:name,'%')")
    public List<Student> studentRecordsStartWith(@Param("name") String name, Pageable pageable);

    @Query(value = "select s from Student s where s.name=:name")
    public List<Student> findStudentWithNameAndSort(@Param("name") String name, Pageable pageable);

    @Query(value = "select s from Student s where s.name=:name")
     Page<Student> findStudentsWithNameAndSort(@Param("name") String name, Pageable pageable);

}
