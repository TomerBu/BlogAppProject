package edu.tomerbu.blogproject.entity;


import edu.tomerbu.blogproject.validators.UniqueTitle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Post {
    @Id
    @GeneratedValue()
    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String content;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Comment> comments = new HashSet<>();
}
