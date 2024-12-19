package com.davifaustino.schoolgradesspringsecurity.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davifaustino.schoolgradesspringsecurity.domain.exceptions.NonExistingRecordException;
import com.davifaustino.schoolgradesspringsecurity.domain.exceptions.RecordConflictException;
import com.davifaustino.schoolgradesspringsecurity.infrastructure.ReportCardRepository;

@Service
public class ReportCardService {
    
    @Autowired
    private ReportCardRepository reportCardRepository;

    public void saveReportCard(ReportCard reportCard) {
        Optional<UUID> id = reportCardRepository.findIdByCompositeKey(reportCard.getTeacherUsername(),
                                                                    reportCard.getSchoolSubject().toString(),
                                                                    reportCard.getStudentUsername(),
                                                                    reportCard.getSchoolYear());

        if (id.isPresent()) throw new RecordConflictException("Report Card already exists in the table");

        reportCardRepository.save(reportCard);
    }

    public List<ReportCard> getReportCardsByTeacherUsername(String teacherUsername) {
        return reportCardRepository.findReportCardsByTeacherUsername(teacherUsername);
    }

    public List<ReportCard> getReportCardsByStudentUsername(String studentUsername) {
        return reportCardRepository.findReportCardsByStudentUsername(studentUsername);
    }

    public void updateReportCard(ReportCard reportCard, String id) {
        UUID uuid = UUID.fromString(id);
        if (reportCardRepository.existsById(uuid)) {
            reportCard.setId(UUID.fromString(id));
        } else {
            throw new NonExistingRecordException("Report card with the provided id does not exist");
        }

        Optional<UUID> existingId = reportCardRepository.findIdByCompositeKey(reportCard.getTeacherUsername(),
                                                                            reportCard.getSchoolSubject().toString(),
                                                                            reportCard.getStudentUsername(),
                                                                            reportCard.getSchoolYear());

        if (existingId.isPresent() && !uuid.equals(existingId.get()))
            throw new RecordConflictException("Report Card already exists in the table");
        reportCardRepository.save(reportCard);
    }
}
