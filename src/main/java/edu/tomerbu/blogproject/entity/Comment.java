package edu.tomerbu.blogproject.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String comment;

    //relationship: אחד לרבים
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
}