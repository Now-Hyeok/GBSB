package com.gbsb.api.dto.response;

import lombok.Getter;

/**
 * Single-object API response wrapper matching the frontend's expected format:
 * { success: true, data: {...} }
 */
@Getter
public class ApiSuccessResponse<T> {
    private final boolean success;
    private final T data;
    private final String message;

    public ApiSuccessResponse(T data) {
        this.success = true;
        this.data = data;
        this.message = null;
    }

    public ApiSuccessResponse(T data, String message) {
        this.success = true;
        this.data = data;
        this.message = message;
    }
}
