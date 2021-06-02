package ru.amaslakova.soundrecognition.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * SongHash data entity.
 */
@Data
@Entity
@Table(name = "song_hash")
@AllArgsConstructor
@NoArgsConstructor
public class SongHash implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hash")
    private String hash;

    @ManyToOne
    @JoinColumn(name = "song_id")
    private Song song;

    @Override
    public String toString() {
        return "SongHash{" +
                "id=" + getId() +
                ", song='" + getSong().getId() + "'" +
                "}";
    }
}
