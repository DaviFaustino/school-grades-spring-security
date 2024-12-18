package com.davifaustino.schoolgradesspringsecurity.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("tb_report_cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportCard {
    
    @Id
    private UUID id;
    private String teacherUsername;
    private SchoolSubject schoolSubject;
    private String studentUsername;
    private String schoolYear;
    private List<String> grades;
}
