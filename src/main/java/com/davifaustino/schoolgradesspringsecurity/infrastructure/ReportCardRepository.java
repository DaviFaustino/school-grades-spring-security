package com.davifaustino.schoolgradesspringsecurity.infrastructure;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.davifaustino.schoolgradesspringsecurity.domain.ReportCard;

@Repository
public interface ReportCardRepository extends CrudRepository<ReportCard, UUID> {

    boolean existsByTeacherUsernameAndSchoolSubjectAndStudentUsernameAndSchoolYear(String teacherUsername, String schoolSubject, String studentUsername, String schoolYear);
    
    List<ReportCard> findReportCardsByTeacherUsername(String teacherUsername);
}
