package com.riwi.simulacro_filtro_spring.infraestructure.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.simulacro_filtro_spring.api.dto.request.AssignmentReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.AssignmentResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.SubmissionResp;
import com.riwi.simulacro_filtro_spring.domain.entities.Assignment;
import com.riwi.simulacro_filtro_spring.domain.entities.Lesson;
import com.riwi.simulacro_filtro_spring.domain.entities.Submission;
import com.riwi.simulacro_filtro_spring.domain.repositories.AssignmentRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.LessonRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.SubmissionRepository;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.IAssignmentService;
import com.riwi.simulacro_filtro_spring.utils.enums.SortType;
import com.riwi.simulacro_filtro_spring.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AssignmentService implements IAssignmentService {

    private static final String FIELD_BY_SORT = "dueDate";

    @Autowired
    private final AssignmentRepository assignmentRepository;

    @Autowired
    private final LessonRepository lessonRepository;

    @Autowired
    private final SubmissionRepository submissionRepository;

    @Override
    public AssignmentResp create(AssignmentReq request) {
        Assignment assignment = this.requestToEntity(request);
        return this.entityToResp(this.assignmentRepository.save(assignment));
    }

    @Override
    public AssignmentResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public AssignmentResp update(AssignmentReq request, Long id) {
        Assignment assignment = this.find(id);

        assignment = this.requestToEntity(request);
        assignment.setId(id);

        return this.entityToResp(this.assignmentRepository.save(assignment));
    }

    @Override
    public void delete(Long id) {
        Assignment assignment = this.find(id);
        this.assignmentRepository.delete(assignment);
    }
    
    @Override
    public Page<AssignmentResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;
        
        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };
        
        return this.assignmentRepository.findAll(pagination).map(this::entityToResp);
    }
    
    private Assignment find(Long id) {
        return this.assignmentRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No assignment found with the supplied ID"));
    }


    private AssignmentResp entityToResp(Assignment entity) {
        List<SubmissionResp> submissions = this.submissionRepository.findByAssignmentId(entity.getId())
            .stream()
            .map(this::submissionToResp)
            .collect(Collectors.toList());

        return AssignmentResp.builder()
                .assignmentId(entity.getId())
                .assignmentTitle(entity.getAssignmentTitle())
                .description(entity.getDescription())
                .dueDate(entity.getDueDate())
                .lessonId(entity.getLesson().getId())
                .submissions(submissions)
                .build();
    }

    private SubmissionResp submissionToResp(Submission submission) {
        return SubmissionResp.builder()
                .submissionId(submission.getId())
                .content(submission.getContent())
                .submissionDate(submission.getSubmissionDate())
                .grade(submission.getGrade())
                .userId(submission.getUser().getId())
                .assignmentId(submission.getAssignment().getId())
                .build();
    }

    @Override
    public List<AssignmentResp> getAssignmentsByLessonId(Long lessonId) {
        List<Assignment> assigments = this.assignmentRepository.findByLessonId(lessonId);
        return assigments.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    private Assignment requestToEntity(AssignmentReq assignmentReq) {

        Lesson lesson = this.lessonRepository.findById(assignmentReq.getLessonId()).orElseThrow(() -> new BadRequestException("No lesson found with the supplied ID"));;

        return Assignment.builder()
                .assignmentTitle(assignmentReq.getAssignmentTitle())
                .description(assignmentReq.getDescription())
                .dueDate(assignmentReq.getDueDate())
                .lesson(lesson)
                .build();
    }

}