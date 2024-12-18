package com.davifaustino.schoolgradesspringsecurity.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davifaustino.schoolgradesspringsecurity.infrastructure.ReportCardRepository;

@Service
public class ReportCardService {
    
    @Autowired
    private ReportCardRepository reportCardRepository;

    public void existsByCompositeKey(String teacherUsername, String schoolSubject, String studentUsername, String schoolYear) {
        if (reportCardRepository.existsByTeacherUsernameAndSchoolSubjectAndStudentUsernameAndSchoolYear(teacherUsername, schoolSubject, studentUsername, schoolYear)) {
            throw new RecordConflictException("Report Card already exists in the table");
        }
    }

    public void saveReportCard(ReportCard reportCard) {
        existsByCompositeKey(reportCard.getTeacherUsername(), reportCard.getSchoolSubject().toString(), reportCard.getStudentUsername(), reportCard.getSchoolYear());

        reportCardRepository.save(reportCard);
    }

    public List<ReportCard> getReportCardsByTeacherUsername(String teacherUsername) {
        return reportCardRepository.findReportCardsByTeacherUsername(teacherUsername);
    }

    public List<ReportCard> getReportCardsByStudentUsername(String studentUsername) {
        return reportCardRepository.findReportCardsByStudentUsername(studentUsername);
    }
}
