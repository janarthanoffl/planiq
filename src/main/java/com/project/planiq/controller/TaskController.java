package com.project.planiq.controller;

import com.project.planiq.common.ApiResponse;
import com.project.planiq.entity.Task;
import com.project.planiq.service.TaskService;
import jakarta.annotation.Priority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // GET /api/tasks?userId=1
    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> getAllTasks(
            @RequestParam Long userId) {
        List<Task> tasks = taskService.getAllTasks(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Tasks fetched successfully", tasks));
    }

    // GET /api/tasks/1?userId=1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> getTaskById(
            @PathVariable Long id,
            @RequestParam Long userId) {
        Task task = taskService.getTaskById(id, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Task fetched successfully", task));
    }

    // POST /api/tasks?userId=1
    @PostMapping
    public ResponseEntity<ApiResponse<Task>> createTask(
            @RequestBody Task task,
            @RequestParam Long userId) {
        Task created = taskService.createTask(task, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Task created successfully", created));
    }

    // PUT /api/tasks/1?userId=1
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Task>> updateTask(
            @PathVariable Long id,
            @RequestBody Task task,
            @RequestParam Long userId) {
        Task updated = taskService.updateTask(id, task, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Task updated successfully", updated));
    }

    // PATCH /api/tasks/1/complete?userId=1
    @PatchMapping("/{id}/complete")
    public ResponseEntity<ApiResponse<Task>> toggleComplete(
            @PathVariable Long id,
            @RequestParam Long userId) {
        Task task = taskService.toggleComplete(id, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Task completion toggled", task));
    }

    // DELETE /api/tasks/1?userId=1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(
            @PathVariable Long id,
            @RequestParam Long userId) {
        taskService.deleteTask(id, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Task deleted successfully"));
    }

    // GET /api/tasks/today?userId=1
    @GetMapping("/today")
    public ResponseEntity<ApiResponse<List<Task>>> getTodayTasks(
            @RequestParam Long userId) {
        List<Task> tasks = taskService.getTodayTasks(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Today's tasks fetched", tasks));
    }

    // GET /api/tasks/pending?userId=1
    @GetMapping("/pending")
    public ResponseEntity<ApiResponse<List<Task>>> getPendingTasks(
            @RequestParam Long userId) {
        List<Task> tasks = taskService.getPendingTasks(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Pending tasks fetched", tasks));
    }

    // GET /api/tasks/priority?userId=1&priority=HIGH
    @GetMapping("/priority")
    public ResponseEntity<ApiResponse<List<Task>>> getByPriority(
            @RequestParam Long userId,
            @RequestParam Priority priority) {
        List<Task> tasks = taskService.getTasksByPriority(userId, priority);
        return ResponseEntity.ok(
                ApiResponse.success("Tasks fetched by priority", tasks));
    }
}