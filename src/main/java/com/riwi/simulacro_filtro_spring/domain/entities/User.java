package com.riwi.simulacro_filtro_spring.domain.entities;



import java.util.List;

import com.riwi.simulacro_filtro_spring.utils.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11, nullable = false)
    private Long id;

    @Column(length = 50, nullable = false)
    private String userName;  
    
    @Column(length = 255, nullable = false)
    private String password; 
    
    @Column(length = 100, nullable = false)
    private String email;   

    @Column(length = 100)
    private String fullName; 
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;   

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<Submission> submissions;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<Enrollment> enrollments;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
        mappedBy = "sender",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<Message> sentMessages;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
        mappedBy = "receiver",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<Message> receivedMessages;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(
        mappedBy = "instructor",
        cascade = CascadeType.ALL,
        orphanRemoval = false
    )
    private List<Course> courses;
}
