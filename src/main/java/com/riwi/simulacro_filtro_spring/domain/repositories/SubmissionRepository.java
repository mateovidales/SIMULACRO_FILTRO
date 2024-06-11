package com.riwi.simulacro_filtro_spring.domain.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.riwi.simulacro_filtro_spring.domain.entities.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission,Long> {
    List<Submission> findByUserId(Long id);
    List<Submission> findByAssignmentId(Long id);
}
