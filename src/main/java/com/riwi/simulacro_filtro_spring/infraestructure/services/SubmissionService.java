package com.riwi.simulacro_filtro_spring.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.simulacro_filtro_spring.api.dto.request.SubmissionReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.SubmissionResp;
import com.riwi.simulacro_filtro_spring.domain.entities.Assignment;
import com.riwi.simulacro_filtro_spring.domain.entities.Submission;
import com.riwi.simulacro_filtro_spring.domain.entities.User;
import com.riwi.simulacro_filtro_spring.domain.repositories.AssignmentRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.SubmissionRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.UserRepository;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.ISubmissionService;
import com.riwi.simulacro_filtro_spring.utils.enums.SortType;
import com.riwi.simulacro_filtro_spring.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubmissionService implements ISubmissionService {

    private static final String FIELD_BY_SORT = "submissionDate";

    @Autowired
    private final SubmissionRepository submissionRepository;

    @Autowired
    private final UserRepository userRepository;
    
    @Autowired
    private final AssignmentRepository assigmentRepository;

    @Override
    public SubmissionResp create(SubmissionReq request) {
        Submission submission = this.requestToEntity(request);
        return this.entityToResp(this.submissionRepository.save(submission));
    }

    @Override
    public SubmissionResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public SubmissionResp update(SubmissionReq request, Long id) {
        Submission submission = this.find(id);

        submission = this.requestToEntity(request);
        submission.setId(id);

        return this.entityToResp(this.submissionRepository.save(submission));
    }

    @Override
    public void delete(Long id) {
        Submission submission = this.find(id);
        this.submissionRepository.delete(submission);
    }

    @Override
    public Page<SubmissionResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;
        
        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };
        
        return this.submissionRepository.findAll(pagination).map(this::entityToResp);
    }
    
    private Submission find(Long id) {
        return this.submissionRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No submission found"));
    }
    @Override
    public List<SubmissionResp> getSubmissionsByAssignmentId(Long assigmentId) {
        List<Submission> submissions = this.submissionRepository.findByAssignmentId(assigmentId);
        return submissions.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    @Override
    public List<SubmissionResp> getSubmissionsByUserId(Long userId) {
        List<Submission> submissions = this.submissionRepository.findByUserId(userId);
        return submissions.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    private SubmissionResp entityToResp(Submission entity) {
        return SubmissionResp.builder()
                .submissionId(entity.getId())
                .content(entity.getContent())
                .submissionDate(entity.getSubmissionDate())
                .grade(entity.getGrade())
                .userId(entity.getUser().getId())
                .assignmentId(entity.getAssignment().getId())
                .build();
    }

    private Submission requestToEntity(SubmissionReq submissionReq) {
        User user = this.userRepository.findById(submissionReq.getUserId()).orElseThrow(() -> new BadRequestException("No user found with the supplied ID"));

        Assignment assignment = this.assigmentRepository.findById(submissionReq.getAssignmentId()).orElseThrow(() -> new BadRequestException("No assignment found with the supplied ID"));;

        return Submission.builder()
                .content(submissionReq.getContent())
                .submissionDate(submissionReq.getSubmissionDate())
                .user(user)
                .assignment(assignment)
                .build();
    }

}
