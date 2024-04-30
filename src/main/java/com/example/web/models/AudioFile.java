package com.example.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "AudioFiles")
public class AudioFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String name;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private String picUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "vibe_id", referencedColumnName = "id")
    private Vibe vibe;

    @ManyToOne
    @JoinColumn(name = "playlist_id", referencedColumnName = "id")
    private Playlist playlist;
}
