package com.davifaustino.schoolgradesspringsecurity.api.dtos;

import java.util.List;

import com.davifaustino.schoolgradesspringsecurity.domain.enums.SchoolSubject;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReportCardRequest(@NotNull @Size(max = 30) String teacherUsername,
                                @NotNull SchoolSubject schoolSubject,
                                @NotNull @Size(max = 30) String studentUsername,
                                @NotNull @Size(max = 4) String schoolYear,
                                @Size(min = 4, max = 4) List<@Size(max = 5) String> grades) {
}
