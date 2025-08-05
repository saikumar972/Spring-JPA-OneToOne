package com.onetoone.entity;

import com.onetoone.dto.StudentDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@NamedNativeQuery(
        name = "userDetails",
        query = "select s.name,s.department,a.state from student s join student_address a on s.address_id=a.id where s.name=:name",
        resultSetMapping = "resultSet"
)
@SqlResultSetMapping(
        name = "resultSet",
        classes = @ConstructorResult(
                targetClass = StudentDetails.class,
                columns = {
                        @ColumnResult(name="name",type=String.class),
                        @ColumnResult(name="department",type=String.class),
                        @ColumnResult(name="state",type=String.class)
                }
        )
)
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String department;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="address_id", referencedColumnName = "id")
    private Address address;
}
