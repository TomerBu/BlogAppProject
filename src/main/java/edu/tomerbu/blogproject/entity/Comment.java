package edu.tomerbu.blogproject.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString //relationships
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String email;

    private String comment;

    //relationship: אחד לרבים
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}

//BREAK EARLY: