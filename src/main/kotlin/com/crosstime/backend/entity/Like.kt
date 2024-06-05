package com.crosstime.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "likes")
class Like(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    var id: UUID? = null,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User? = null,
    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post? = null,
    @ManyToOne
    @JoinColumn(name = "comment_id")
    val comment: Comment? = null
)