package com.crosstime.backend.model

import java.util.UUID

class Like(
    var id: UUID? = null,
    val user: User? = null,
    val post: Post? = null,
    val comment: Comment? = null
)