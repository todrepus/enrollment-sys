package com.todrepus.enrollmentsys.domain.department;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name="departments")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="department_id")
    Long id;

    @Column(name="name")
    String name;
}
