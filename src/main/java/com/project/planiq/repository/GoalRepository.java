package com.project.planiq.repository;

import com.project.planiq.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {

    Long countByUserId(Long userId);

    Long countByUserIdAndCompleted(Long userId, Boolean completed);

    List<Goal> findByUserId(Long userId);

    List<Goal> findByUserIdAndCompleted(Long userId, Boolean completed);

}
