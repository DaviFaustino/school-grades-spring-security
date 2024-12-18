package com.davifaustino.schoolgradesspringsecurity.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davifaustino.schoolgradesspringsecurity.api.dtos.ReportCardRequest;
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
}
