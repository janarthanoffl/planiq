package com.project.planiq.repository;

import com.project.planiq.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findByUserId(Long userId);

    List<Note> findByUserIdAndPinned(Long userId, Boolean pinned);
    List<Note> findTop3ByUserIdOrderByCreatedAtDesc(Long userId);

    List<Note> findByUserIdAndTitleContainingIgnoreCase(Long userId, String keyword);
}
