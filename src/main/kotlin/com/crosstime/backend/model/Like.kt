package com.crosstime.backend.model

import java.util.UUID

class Like(
    var id: UUID? = null,
    var user: User? = null,
    var post: Post? = null,
    var comment: Comment? = null
)