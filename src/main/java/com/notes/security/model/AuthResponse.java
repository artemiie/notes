package com.notes.security.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class AuthResponse {
    private Long userId;
    private String access;
    private String refresh;
}
