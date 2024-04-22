package com.crosstime.backend.entity

enum class Permission(
    val permission: String
) {
    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),
    ADMIN_CREATE("admin:create"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete")
}