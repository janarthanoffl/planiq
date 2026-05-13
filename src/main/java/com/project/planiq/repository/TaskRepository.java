package com.project.planiq.repository;

import com.project.planiq.entity.Task;
import jakarta.annotation.Priority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    List<Task> findByUserIdAndCompleted(Long userId, Boolean completed);

    List<Task> findByUserIdAndPriority(Long userId, Priority priority);

    @Query("SELECT t FROM Task t WHERE t.user.id = :userId AND t.dueDate = :today AND t.completed = false")
    List<Task> findTodayPendingTasks(@Param("userId") Long userId, @Param("today") LocalDate today);
}
