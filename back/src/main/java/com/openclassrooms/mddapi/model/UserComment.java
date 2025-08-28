package com.openclassrooms.mddapi.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_comments")
public class UserComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "username_id")
    private User username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

}
