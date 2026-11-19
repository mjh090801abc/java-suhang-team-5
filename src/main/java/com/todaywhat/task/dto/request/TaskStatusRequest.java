package com.todaywhat.task.dto.request;

import jakarta.validation.constraints.NotNull;

public record TaskStatusRequest(@NotNull Boolean completed) {
}