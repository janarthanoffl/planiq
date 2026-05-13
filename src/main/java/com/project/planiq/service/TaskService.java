package com.project.planiq.service;

import com.project.planiq.entity.Task;
import com.project.planiq.entity.User;
import com.project.planiq.exception.ResourceNotFoundException;
import com.project.planiq.repository.TaskRepository;
import com.project.planiq.repository.UserRepository;
import jakarta.annotation.Priority;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // Get all tasks for a user
    public List<Task> getAllTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }
    public List<Task> getTodayTasks(Long userId) {
        return taskRepository.findTodayPendingTasks(userId, LocalDate.now());
    }
    // Get single task
    public Task getTaskById(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));

        if (!task.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Task not found for this user");
        }
        return task;
    }

    // Create new task
    public Task createTask(Task task, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        task.setUser(user);
        return taskRepository.save(task);
    }

    // Update task
    public Task updateTask(Long taskId, Task updatedTask, Long userId) {
        Task existing = getTaskById(taskId, userId);
        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setPriority(updatedTask.getPriority());
        existing.setDueDate(updatedTask.getDueDate());
        return taskRepository.save(existing);
    }

    // Mark task complete or incomplete
    public Task toggleComplete(Long taskId, Long userId) {
        Task task = getTaskById(taskId, userId);
        task.setCompleted(!task.getCompleted());
        return taskRepository.save(task);
    }

    // Delete task
    public void deleteTask(Long taskId, Long userId) {
        Task task = getTaskById(taskId, userId);
        taskRepository.delete(task);
    }

    // Get tasks by priority
    public List<Task> getTasksByPriority(Long userId, Priority priority) {
        return taskRepository.findByUserIdAndPriority(userId, priority);
    }

    // Get pending tasks only
    public List<Task> getPendingTasks(Long userId) {
        return taskRepository.findByUserIdAndCompleted(userId, false);
    }
}