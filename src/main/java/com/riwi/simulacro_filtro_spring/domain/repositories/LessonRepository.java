package com.riwi.simulacro_filtro_spring.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.simulacro_filtro_spring.domain.entities.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long>{
    List<Lesson> findByCourseId(Long id);
}
