package com.crosstime.backend.model

import java.util.UUID

class Comment(
    var id: UUID? = null,
    var content: String? = null,
    var user: User? = null
)