package ru.amaslakova.soundrecognition.domain.model;

import com.vladmihalcea.hibernate.type.interval.PostgreSQLIntervalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.TypeDef;
import ru.amaslakova.soundrecognition.domain.enumiration.Genre;

import java.io.Serializable;
import java.time.Duration;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Song data entity.
 */
@Data
@Entity
@Table(name = "song")
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(
        typeClass = PostgreSQLIntervalType.class,
        defaultForType = Duration.class
)
public class Song implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "duration",
            columnDefinition = "interval")
    private Duration duration;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @OneToMany(mappedBy = "hash_id", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<SongHash> hash;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @Override
    public String toString() {
        return "Song{" +
                "id=" + getId() +
                ", duration='" + getDuration() + "'" +
                ", artist='" + getArtist().getId() + "'" +
                ", album='" + getAlbum().getId() + "'" +
                "}";
    }
}
