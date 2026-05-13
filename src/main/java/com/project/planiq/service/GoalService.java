package com.project.planiq.service;

import com.project.planiq.entity.Goal;
import com.project.planiq.entity.User;
import com.project.planiq.exception.ResourceNotFoundException;
import com.project.planiq.repository.GoalRepository;
import com.project.planiq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class GoalService {

    private final GoalRepository goalRepository;
    private final UserRepository userRepository;

    // Get all goals for a user
    public List<Goal> getAllGoals(Long userId) {
        return goalRepository.findByUserId(userId);
    }

    // Get single goal by id
    public Goal getGoalById(Long goalId, Long userId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new ResourceNotFoundException("Goal not found with id: " + goalId));

        if (!goal.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Goal not found for this user");
        }
        return goal;
    }

    // Create new goal
    public Goal createGoal(Goal goal, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        goal.setUser(user);
        return goalRepository.save(goal);
    }

    // Update goal
    public Goal updateGoal(Long goalId, Goal updatedGoal, Long userId) {
        Goal existing = getGoalById(goalId, userId);
        existing.setTitle(updatedGoal.getTitle());
        existing.setDescription(updatedGoal.getDescription());
        existing.setProgress(updatedGoal.getProgress());
        existing.setDeadline(updatedGoal.getDeadline());
        existing.setCompleted(updatedGoal.getProgress() == 100);
        return goalRepository.save(existing);
    }

    // Delete goal
    public void deleteGoal(Long goalId, Long userId) {
        Goal goal = getGoalById(goalId, userId);
        goalRepository.delete(goal);
    }

    // Get completed goals
    public List<Goal> getCompletedGoals(Long userId) {
        return goalRepository.findByUserIdAndCompleted(userId, true);
    }

    // For dashboard summary widget
    public Long getTotalGoalsCount(Long userId) {
        return goalRepository.countByUserId(userId);
    }

    public Long getCompletedGoalsCount(Long userId) {
        return goalRepository.countByUserIdAndCompleted(userId, true);
    }
}