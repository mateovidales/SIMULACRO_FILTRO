package com.riwi.simulacro_filtro_spring.infraestructure.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.riwi.simulacro_filtro_spring.api.dto.request.UserReq;
import com.riwi.simulacro_filtro_spring.api.dto.response.AssignmentResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.CourseResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.EnrollmentResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.LessonResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.MessageResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.SubmissionResp;
import com.riwi.simulacro_filtro_spring.api.dto.response.UserResp;
import com.riwi.simulacro_filtro_spring.domain.entities.Assignment;
import com.riwi.simulacro_filtro_spring.domain.entities.Course;
import com.riwi.simulacro_filtro_spring.domain.entities.Enrollment;
import com.riwi.simulacro_filtro_spring.domain.entities.Lesson;
import com.riwi.simulacro_filtro_spring.domain.entities.Message;
import com.riwi.simulacro_filtro_spring.domain.entities.Submission;
import com.riwi.simulacro_filtro_spring.domain.entities.User;
import com.riwi.simulacro_filtro_spring.domain.repositories.CourseRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.EnrollmentRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.MessageRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.SubmissionRepository;
import com.riwi.simulacro_filtro_spring.domain.repositories.UserRepository;
import com.riwi.simulacro_filtro_spring.infraestructure.abstract_service.IUserService;
import com.riwi.simulacro_filtro_spring.utils.enums.SortType;
import com.riwi.simulacro_filtro_spring.utils.exceptions.BadRequestException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private static final String FIELD_BY_SORT = "username";

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final SubmissionRepository submissionRepository;

    @Autowired
    private final EnrollmentRepository enrollmentRepository;

    @Autowired
    private final MessageRepository messageRepository;

    @Autowired
    private final CourseRepository courseRepository;
    
    @Override
    public UserResp create(UserReq request) {
        User user = this.requestToEntity(request);
        return this.entityToResp(this.userRepository.save(user));
    }

    @Override
    public UserResp get(Long id) {
        return this.entityToResp(this.find(id));
    }

    @Override
    public UserResp update(UserReq request, Long id) {
        User user = this.find(id);
        user = this.requestToEntity(request);
        user.setId(id);

        return this.entityToResp(this.userRepository.save(user));
    }

    @Override
    public void delete(Long id) {
        User user = this.find(id);
        this.userRepository.delete(user);
    }

    @Override
    public Page<UserResp> getAll(int page, int size, SortType sortType) {
        if (page < 0) page = 0;
        
        PageRequest pagination = switch (sortType) {
            case NONE -> PageRequest.of(page, size);
            case ASC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
            case DESC -> PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
        };
        
        return this.userRepository.findAll(pagination).map(this::entityToResp);
        
    }
    
    private User find(Long id) {
        return this.userRepository.findById(id).orElseThrow(()-> new BadRequestException("No user found"));
   }

    private UserResp entityToResp(User entity) {
        List<SubmissionResp> submissions = this.submissionRepository.findByUserId(entity.getId())
            .stream()
            .map(this::submissionToResp)
            .collect(Collectors.toList());

        List<EnrollmentResp> enrollments = this.enrollmentRepository.findByUserId(entity.getId())
            .stream()
            .map(this::enrollmentToResp)
            .collect(Collectors.toList());

        List<MessageResp> sentMessages = this.messageRepository.findBySenderId(entity.getId())
            .stream()
            .map(this::messageToResp)
            .collect(Collectors.toList());

        List<MessageResp> receivedMessages = this.messageRepository.findByReceiverId(entity.getId())
            .stream()
            .map(this::messageToResp)
            .collect(Collectors.toList());

        List<CourseResp> courses = this.courseRepository.findByInstructorId(entity.getId())
            .stream()
            .map(this::courseToResp)
            .collect(Collectors.toList());

        return UserResp.builder()
                .userId(entity.getId())
                .userName(entity.getUserName())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .role(entity.getRole())
                .submissions(submissions)
                .enrollments(enrollments)
                .sentMessages(sentMessages)
                .receivedMessages(receivedMessages)
                .courses(courses)
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

    private EnrollmentResp enrollmentToResp(Enrollment enrollment) {
        return EnrollmentResp.builder()
                .enrollmentId(enrollment.getId())
                .user(enrollment.getUser())
                .course(enrollment.getCourse())
                .enrollmentDate(enrollment.getEnrollmentDate())
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

    private CourseResp courseToResp(Course course) {
        return CourseResp.builder()
                .courseId(course.getId())
                .courseName(course.getCourseName())
                .description(course.getDescription())
                .instructorId(course.getInstructor().getId())
                .lessons(course.getLessons().stream().map(this::lessonToResp).collect(Collectors.toList()))
                .messages(course.getMessages().stream().map(this::messageToResp).collect(Collectors.toList()))
                .enrollments(course.getEnrollments().stream().map(this::enrollmentToResp).collect(Collectors.toList()))
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

    private User requestToEntity(UserReq userReq) {
        return User.builder()
                .userName(userReq.getUserName())
                .password(userReq.getPassword())
                .email(userReq.getEmail())
                .fullName(userReq.getFullName())
                .role(userReq.getRole())
                .build();
    }
}