package ru.amaslakova.soundrecognition.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.amaslakova.soundrecognition.domain.model.AlbumCover;

/**
 * Repo for AlbumCover entity.
 */
@Repository
public interface AlbumCoverRepository extends JpaRepository<AlbumCover, Long> {
}
