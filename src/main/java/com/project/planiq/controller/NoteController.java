package com.project.planiq.controller;



import com.project.planiq.common.ApiResponse;
import com.project.planiq.entity.Note;
import com.project.planiq.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {


    private final NoteService noteService;

    // GET /api/notes?userId=1
    @GetMapping
    public ResponseEntity<ApiResponse<List<Note>>> getAllNotes(
            @RequestParam Long userId) {
        List<Note> notes = noteService.getAllNotes(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Notes fetched successfully", notes));
    }

    // GET /api/notes/1?userId=1
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Note>> getNoteById(
            @PathVariable Long id,
            @RequestParam Long userId) {
        Note note = noteService.getNoteById(id, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Note fetched successfully", note));
    }

    // POST /api/notes?userId=1
    @PostMapping
    public ResponseEntity<ApiResponse<Note>> createNote(
            @RequestBody Note note,
            @RequestParam Long userId) {
        Note created = noteService.createNote(note, userId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Note created successfully", created));
    }

    // PUT /api/notes/1?userId=1
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Note>> updateNote(
            @PathVariable Long id,
            @RequestBody Note note,
            @RequestParam Long userId) {
        Note updated = noteService.updateNote(id, note, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Note updated successfully", updated));
    }

    // PATCH /api/notes/1/pin?userId=1
    @PatchMapping("/{id}/pin")
    public ResponseEntity<ApiResponse<Note>> togglePin(
            @PathVariable Long id,
            @RequestParam Long userId) {
        Note note = noteService.togglePin(id, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Note pin toggled", note));
    }



    // DELETE /api/notes/1?userId=1
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(
            @PathVariable Long id,
            @RequestParam Long userId) {
        noteService.deleteNote(id, userId);
        return ResponseEntity.ok(
                ApiResponse.success("Note deleted successfully"));
    }

    // GET /api/notes/search?userId=1&q=spring
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Note>>> searchNotes(
            @RequestParam Long userId,
            @RequestParam String q) {
        List<Note> notes = noteService.searchNotes(userId, q);
        return ResponseEntity.ok(
                ApiResponse.success("Search results fetched", notes));
    }

    // GET /api/notes/pinned?userId=1
    @GetMapping("/pinned")
    public ResponseEntity<ApiResponse<List<Note>>> getPinnedNotes(
            @RequestParam Long userId) {
        List<Note> notes = noteService.getPinnedNotes(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Pinned notes fetched", notes));
    }

    // GET /api/notes/recent?userId=1
    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<Note>>> getRecentNotes(
            @RequestParam Long userId) {
        List<Note> notes = noteService.getRecentNotes(userId);
        return ResponseEntity.ok(
                ApiResponse.success("Recent notes fetched", notes));
    }
}