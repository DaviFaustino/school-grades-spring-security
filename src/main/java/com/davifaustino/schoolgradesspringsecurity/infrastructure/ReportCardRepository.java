package com.davifaustino.schoolgradesspringsecurity.infrastructure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.davifaustino.schoolgradesspringsecurity.domain.ReportCard;

@Repository
public interface ReportCardRepository extends CrudRepository<ReportCard, UUID> {

    @Query("SELECT id FROM tb_report_cards WHERE " +
            "teacher_username = :teacherUsername AND school_subject = :schoolSubject AND " +
            "student_username = :studentUsername AND school_year = :schoolYear")
    Optional<UUID> findIdByCompositeKey(@Param("teacherUsername") String teacherUsername, @Param("schoolSubject") String schoolSubject,
                                        @Param("studentUsername") String studentUsername, @Param("schoolYear") String schoolYear);

    List<ReportCard> findReportCardsByTeacherUsername(String teacherUsername);

    List<ReportCard> findReportCardsByStudentUsername(String studentUsername);
}
