package com.riwi.simulacro_filtro_spring.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.simulacro_filtro_spring.api.dto.request.EnrollmentReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.EnrollmentResp;
import com.riwi.simulacro_filtro_spring.domain.entities.Course;
import com.riwi.simulacro_filtro_spring.domain.entities.Enrollment;
import com.riwi.simulacro_filtro_spring.domain.entities.User;
import com.riwi.simulacro_filtro_spring.domain.repositories.CourseRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.EnrollmentRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.UserRepository;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.IEnrollmentService;
import com.riwi.simulacro_filtro_spring.utils.enums.SortType;
import com.riwi.simulacro_filtro_spring.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnrollmentService implements IEnrollmentService {

    private static final String FIELD_BY_SORT = "enrollmentDate";

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    private final UserRepository userRepository;


    @Autowired
    private final CourseRepository courseRepository;

    @Override
    public EnrollmentResp create(EnrollmentReq request) {
        Enrollment enrollment = this.requestToEntity(request);
        return this.entityToResp(this.enrollmentRepository.save(enrollment));
    }

    @Override
    public EnrollmentResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public EnrollmentResp update(EnrollmentReq request, Long id) {
        Enrollment enrollment = this.find(id);

        enrollment = this.requestToEntity(request);
        enrollment.setId(id);

        return this.entityToResp(this.enrollmentRepository.save(enrollment));
    }

    @Override
    public void delete(Long id) {
        Enrollment enrollment = this.find(id);
        this.enrollmentRepository.delete(enrollment);
    }

    @Override
    public Page<EnrollmentResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;
        
        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };
        
        return this.enrollmentRepository.findAll(pagination).map(this::entityToResp);
    }
    
    private Enrollment find(Long id) {
        return this.enrollmentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No enrollment found"));
    }

    @Override
    public List<EnrollmentResp> getCoursesByUserId(Long userId) {
        List<Enrollment> assigments = this.enrollmentRepository.findByUserId(userId);
        return assigments.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResp> getUsersByCourseId(Long courseId) {
        List<Enrollment> assigments = this.enrollmentRepository.findByCourseId(courseId);
        return assigments.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    private EnrollmentResp entityToResp(Enrollment entity) {
        return EnrollmentResp.builder()
                .enrollmentId(entity.getId())
                .user(entity.getUser())
                .course(entity.getCourse())
                .enrollmentDate(entity.getEnrollmentDate())
                .build();
    }

    private Enrollment requestToEntity(EnrollmentReq enrollmentReq) {
        User user = this.userRepository.findById(enrollmentReq.getUserId()).orElseThrow(() -> new BadRequestException("No User found"));

        Course course = this.courseRepository.findById(enrollmentReq.getCourseId()).orElseThrow(() -> new BadRequestException("No Course found"));

        return Enrollment.builder()
                .user(user)
                .course(course)
                .build();
    }

}