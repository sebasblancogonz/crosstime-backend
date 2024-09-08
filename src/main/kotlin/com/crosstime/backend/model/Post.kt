package com.crosstime.backend.model

import java.util.Date
import java.util.UUID

class Post(
    var id: UUID? = null,
    var content: String? = null,
    var createdAt: Date? = null,
    val user: User? = null,
    var comments: MutableList<Comment> = mutableListOf(),
    var likes: MutableList<Like> = mutableListOf()

)