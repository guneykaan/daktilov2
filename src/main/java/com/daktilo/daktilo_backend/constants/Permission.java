package com.daktilo.daktilo_backend.constants;

public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_WRITE("admin:write"),

    AUTHOR_READ("author:read"),
    AUTHOR_WRITE("author:write"),

    READER_READ("reader:read");

    private String permission;

    Permission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
