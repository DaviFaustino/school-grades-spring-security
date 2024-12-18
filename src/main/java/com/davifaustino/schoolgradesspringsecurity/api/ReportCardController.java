package com.davifaustino.schoolgradesspringsecurity.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.davifaustino.schoolgradesspringsecurity.api.dtos.ReportCardRequest;
import com.davifaustino.schoolgradesspringsecurity.api.dtos.ReportCardResponse;
import com.davifaustino.schoolgradesspringsecurity.domain.ReportCard;
import com.davifaustino.schoolgradesspringsecurity.domain.ReportCardService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("report-card")
public class ReportCardController {
    
    @Autowired
    private ReportCardService reportCardService;
    
    @PostMapping
    public ResponseEntity<Void> saveReportCard(@RequestBody @Valid ReportCardRequest request) {
        reportCardService.saveReportCard(new ReportCard(request));
        
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/teacher")
    public ResponseEntity<List<ReportCardResponse>> getReportCardsByTeacherUsername(@RequestParam String username) {
        List<ReportCardResponse> reportCardResponses = reportCardService.getReportCardsByTeacherUsername(username).stream().map(ReportCardResponse::new).toList();

        return ResponseEntity.ok().body(reportCardResponses);
    }
    
    @GetMapping("/student")
    public ResponseEntity<List<ReportCardResponse>> getReportCardsByStudentUsername(@RequestParam String username) {
        List<ReportCardResponse> reportCardResponses = reportCardService.getReportCardsByStudentUsername(username).stream().map(ReportCardResponse::new).toList();

        return ResponseEntity.ok().body(reportCardResponses);
    }
}
