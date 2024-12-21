package com.davifaustino.schoolgradesspringsecurity.api.dtos;

import com.davifaustino.schoolgradesspringsecurity.domain.UserRole;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(@Size(max = 30) @NotNull String username,
                            @NotNull String password,
                            @NotNull UserRole role) {
}
