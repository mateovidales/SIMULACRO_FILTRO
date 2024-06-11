package com.riwi.simulacro_filtro_spring.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.simulacro_filtro_spring.api.dto.request.LessonReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.AssignmentResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.LessonResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.SubmissionResp;
import com.riwi.simulacro_filtro_spring.domain.entities.Assignment;
import com.riwi.simulacro_filtro_spring.domain.entities.Course;
import com.riwi.simulacro_filtro_spring.domain.entities.Lesson;
import com.riwi.simulacro_filtro_spring.domain.entities.Submission;
import com.riwi.simulacro_filtro_spring.domain.repositories.AssignmentRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.CourseRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.LessonRepository;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.ILessonService;
import com.riwi.simulacro_filtro_spring.utils.enums.SortType;
import com.riwi.simulacro_filtro_spring.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LessonService implements ILessonService {

    private static final String FIELD_BY_SORT = "lessonTitle";

    @Autowired
    private final LessonRepository lessonRepository;

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final AssignmentRepository assignmentRepository;

    @Override
    public LessonResp create(LessonReq request) {
        Lesson lesson = this.requestToEntity(request);
        return this.entityToResp(this.lessonRepository.save(lesson));
    }

    @Override
    public LessonResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public LessonResp update(LessonReq request, Long id) {
        Lesson lesson = this.find(id);

        lesson = this.requestToEntity(request);
        lesson.setId(id);

        return this.entityToResp(this.lessonRepository.save(lesson));
    }

    @Override
    public void delete(Long id) {
        Lesson lesson = this.find(id);
        this.lessonRepository.delete(lesson);
    }

    @Override
    public Page<LessonResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };
        
        return this.lessonRepository.findAll(pagination).map(this::entityToResp);
    }
    
    private Lesson find(Long id) {
        return this.lessonRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No lesson found"));
    }
    @Override
    public List<LessonResp> getLessonsByCourseId(Long courseId) {
        List<Lesson> lessons = this.lessonRepository.findByCourseId(courseId);
        return lessons.stream().map(this::entityToResp).collect(Collectors.toList());
    }

    private LessonResp entityToResp(Lesson entity) {
        List<AssignmentResp> assignments = this.assignmentRepository.findByLessonId(entity.getId())
            .stream()
            .map(this::assignmentToResp)
            .collect(Collectors.toList());

        return LessonResp.builder()
                .lessonId(entity.getId())
                .lessonTitle(entity.getLessonTitle())
                .content(entity.getContent())
                .courseId(entity.getCourse().getId())
                .assignments(assignments)
                .build();
    }

    private AssignmentResp assignmentToResp(Assignment assignment) {
        return AssignmentResp.builder()
                .assignmentId(assignment.getId())
                .assignmentTitle(assignment.getAssignmentTitle())
                .description(assignment.getDescription())
                .dueDate(assignment.getDueDate())
                .lessonId(assignment.getLesson().getId())
                .submissions(assignment.getSubmissions().stream().map(this::submissionToResp).collect(Collectors.toList()))
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

    private Lesson requestToEntity(LessonReq lessonReq) {
        Course course = this.courseRepository.findById(lessonReq.getCourseId()).orElseThrow(() -> new BadRequestException("No Course found with the supplied ID"));;

        return Lesson.builder()
                .lessonTitle(lessonReq.getLessonTitle())
                .content(lessonReq.getContent())
                .course(course)
                .build();
    }

}