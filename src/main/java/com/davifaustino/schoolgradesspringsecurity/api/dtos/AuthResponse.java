package com.davifaustino.schoolgradesspringsecurity.api.dtos;

public record AuthResponse(String accessToken,
                            String refreshToken) {
}
