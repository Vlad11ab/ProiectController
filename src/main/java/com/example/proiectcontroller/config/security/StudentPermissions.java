package com.example.proiectcontroller.config.security;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StudentPermissions {
        STUDENT_READ("student:read"),
        STUDENT_WRITE("student:write"),
        STUDENT_EDIT("student:edit"),
        STUDENT_DELETE("student:delete"),
        BOOK_READ("book:read"),
        BOOK_WRITE("book:write"),
        BOOK_EDIT("book:edit"),
        BOOK_DELETE("book:delete");

        private final String permission;

        public String getPermission(){
            return permission;
        }
}
