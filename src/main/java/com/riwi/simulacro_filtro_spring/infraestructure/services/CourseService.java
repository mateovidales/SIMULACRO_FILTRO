package com.riwi.simulacro_filtro_spring.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.simulacro_filtro_spring.api.dto.request.CourseReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.AssignmentResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.CourseResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.EnrollmentResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.LessonResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.MessageResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.SubmissionResp;
import com.riwi.simulacro_filtro_spring.domain.entities.Assignment;
import com.riwi.simulacro_filtro_spring.domain.entities.Course;
import com.riwi.simulacro_filtro_spring.domain.entities.Enrollment;
import com.riwi.simulacro_filtro_spring.domain.entities.Lesson;
import com.riwi.simulacro_filtro_spring.domain.entities.Message;
import com.riwi.simulacro_filtro_spring.domain.entities.Submission;
import com.riwi.simulacro_filtro_spring.domain.entities.User;
import com.riwi.simulacro_filtro_spring.domain.repositories.CourseRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.EnrollmentRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.LessonRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.MessageRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.UserRepository;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.ICourseService;
import com.riwi.simulacro_filtro_spring.utils.enums.SortType;
import com.riwi.simulacro_filtro_spring.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class CourseService implements ICourseService {

    private static final String FIELD_BY_SORT = "courseName";

    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final UserRepository userRepository;
    
    @Autowired
    private final LessonRepository lessonRepository;

    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public CourseResp create(CourseReq request) {
        Course course = this.requestToEntity(request);
        return this.entityToResp(this.courseRepository.save(course));
    }

    @Override
    public CourseResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public CourseResp update(CourseReq request, Long id) {
        Course course = this.find(id);

        course = this.requestToEntity(request);
        course.setId(id);

        return this.entityToResp(this.courseRepository.save(course));
    }

    @Override
    public void delete(Long id) {
        Course course = this.find(id);
        this.courseRepository.delete(course);
    }

    @Override
    public Page<CourseResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;

        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };
        
        return this.courseRepository.findAll(pagination).map(this::entityToResp);
    }
    
    private Course find(Long id) {
        return this.courseRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No course found"));
    }
    private CourseResp entityToResp(Course entity) {
        List<LessonResp> lessons = this.lessonRepository.findByCourseId(entity.getId())
            .stream()
            .map(this::lessonToResp)
            .collect(Collectors.toList());

        List<MessageResp> messages = this.messageRepository.findByCourseId(entity.getId())
            .stream()
            .map(this::messageToResp)
            .collect(Collectors.toList());

        List<EnrollmentResp> enrollments = this.enrollmentRepository.findByCourseId(entity.getId())
            .stream()
            .map(this::enrollmentToResp)
            .collect(Collectors.toList());

        return CourseResp.builder()
                .courseId(entity.getId())
                .courseName(entity.getCourseName())
                .description(entity.getDescription())
                .instructorId(entity.getInstructor().getId())
                .lessons(lessons)
                .messages(messages)
                .enrollments(enrollments)
                .build();
    }

    private LessonResp lessonToResp(Lesson lesson) {
        return LessonResp.builder()
                .lessonId(lesson.getId())
                .lessonTitle(lesson.getLessonTitle())
                .content(lesson.getContent())
                .courseId(lesson.getCourse().getId())
                .assignments(lesson.getAssignments().stream().map(this::assignmentToResp).collect(Collectors.toList()))
                .build();
    }

    private MessageResp messageToResp(Message message) {
        return MessageResp.builder()
                .messageId(message.getId())
                .senderId(message.getSender().getId())
                .receiverId(message.getReceiver().getId())
                .courseId(message.getCourse().getId())
                .messageContent(message.getMessageContent())
                .sentDate(message.getSentDate())
                .build();
    }

    private EnrollmentResp enrollmentToResp(Enrollment enrollment) {
        return EnrollmentResp.builder()
                .enrollmentId(enrollment.getId())
                .user(enrollment.getUser())
                .course(enrollment.getCourse())
                .enrollmentDate(enrollment.getEnrollmentDate())
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

    private Course requestToEntity(CourseReq courseReq) {
        User instructor = this.userRepository.findById(courseReq.getInstructorId()).orElseThrow(() -> new BadRequestException("No Instructor found with the supplied ID"));;

        return Course.builder()
                .courseName(courseReq.getCourseName())
                .description(courseReq.getDescription())
                .instructor(instructor)
                .build();
    }

}

