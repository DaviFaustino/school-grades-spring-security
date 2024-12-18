package com.davifaustino.schoolgradesspringsecurity.api.dtos;

import java.util.List;
import java.util.UUID;

import com.davifaustino.schoolgradesspringsecurity.domain.ReportCard;
import com.davifaustino.schoolgradesspringsecurity.domain.SchoolSubject;

public record ReportCardResponse(UUID id,
                                String teacherUsername,
                                SchoolSubject schoolSubject,
                                String studentUsername,
                                String schoolYear,
                                List<String> grades) {

    public ReportCardResponse(ReportCard reportCard) {
        this(reportCard.getId(),
            reportCard.getTeacherUsername(),
            reportCard.getSchoolSubject(),
            reportCard.getStudentUsername(),
            reportCard.getSchoolYear(),
            reportCard.getGrades());
    }
}
