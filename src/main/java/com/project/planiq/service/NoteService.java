package com.project.planiq.service;


import com.project.planiq.entity.Note;
import com.project.planiq.entity.User;
import com.project.planiq.exception.ResourceNotFoundException;
import com.project.planiq.repository.NoteRepository;
import com.project.planiq.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    // Get all notes for a user
    public List<Note> getAllNotes(Long userId) {
        return noteRepository.findByUserId(userId);
    }

    // Get single note
    public Note getNoteById(Long noteId, Long userId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + noteId));

        if (!note.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Note not found for this user");
        }
        return note;
    }

    // Create new note
    public Note createNote(Note note, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        note.setUser(user);
        return noteRepository.save(note);
    }

    // Update note
    public Note updateNote(Long noteId, Note updatedNote, Long userId) {
        Note existing = getNoteById(noteId, userId);
        existing.setTitle(updatedNote.getTitle());
        existing.setContent(updatedNote.getContent());
        existing.setTags(updatedNote.getTags());
        return noteRepository.save(existing);
    }

    // Toggle pin
    public Note togglePin(Long noteId, Long userId) {
        Note note = getNoteById(noteId, userId);
        note.setPinned(!note.getPinned());
        return noteRepository.save(note);
    }

    // Delete note
    public void deleteNote(Long noteId, Long userId) {
        Note note = getNoteById(noteId, userId);
        noteRepository.delete(note);
    }

    // Search notes by keyword
    public List<Note> searchNotes(Long userId, String keyword) {
        return noteRepository.findByUserIdAndTitleContainingIgnoreCase(userId, keyword);
    }

    // Get pinned notes
    public List<Note> getPinnedNotes(Long userId) {
        return noteRepository.findByUserIdAndPinned(userId, true);
    }
    public List<Note> getRecentNotes(Long userId) {
        return noteRepository.findTop3ByUserIdOrderByCreatedAtDesc(userId);
    }
}
