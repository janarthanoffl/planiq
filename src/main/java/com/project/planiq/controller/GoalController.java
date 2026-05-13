package com.project.planiq.controller;


import com.project.planiq.common.ApiResponse;
import com.project.planiq.entity.Goal;
import com.project.planiq.service.GoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@RequiredArgsConstructor
public class GoalController {
    private final GoalService goalService;

    // GET /api/goals?userId=1
    @GetMapping
    public ResponseEntity<ApiResponse<List<Goal>>> getAllGoals(
            @RequestParam Long userId) {
        List<Goal> goals = goalService.getAllGoals(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Goals fetched successfully", goals));
    }

    // GET /api/goals/1?userId=1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Goal>> getGoalById(
            @PathVariable Long id,
            @RequestParam Long userId) {
        Goal goal = goalService.getGoalById(id, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Goal fetched successfully", goal));
    }

    // POST /api/goals?userId=1
    @PostMapping
    public ResponseEntity<ApiResponse<Goal>> createGoal(
            @RequestBody Goal goal,
            @RequestParam Long userId) {
        Goal created = goalService.createGoal(goal, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Goal created successfully", created));
    }

    // PUT /api/goals/1?userId=1
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Goal>> updateGoal(
            @PathVariable Long id,
            @RequestBody Goal goal,
            @RequestParam Long userId) {
        Goal updated = goalService.updateGoal(id, goal, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Goal updated successfully", updated));
    }

    // DELETE /api/goals/1?userId=1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteGoal(
            @PathVariable Long id,
            @RequestParam Long userId) {
        goalService.deleteGoal(id, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Goal deleted successfully"));
    }

    // GET /api/goals/completed?userId=1
    @GetMapping("/completed")
    public ResponseEntity<ApiResponse<List<Goal>>> getCompletedGoals(
            @RequestParam Long userId) {
        List<Goal> goals = goalService.getCompletedGoals(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Completed goals fetched", goals));
    }

    // GET /api/goals/summary?userId=1
    @GetMapping("/summary")
    public ResponseEntity<ApiResponse<?>> getGoalsSummary(
            @RequestParam Long userId) {
        Long total = goalService.getTotalGoalsCount(userId);
        Long completed = goalService.getCompletedGoalsCount(userId);
        Long inProgress = total - completed;

        var summary = new java.util.HashMap<String, Object>();
        summary.put("total", total);
        summary.put("completed", completed);
        summary.put("inProgress", inProgress);
        summary.put("completedPercent", total > 0 ? (completed * 100 / total) : 0);

        return ResponseEntity.ok(
                ApiResponse.success("Goals summary fetched", summary));
    }
}