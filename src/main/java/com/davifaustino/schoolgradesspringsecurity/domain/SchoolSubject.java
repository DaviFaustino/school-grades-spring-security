package com.davifaustino.schoolgradesspringsecurity.domain;

public enum SchoolSubject {
    MATHS("maths"),
    LANGUAGE("language"),
    LITERATURE("literature"),
    SCIENCE("science"),
    GEOGRAPHY("geography"),
    PHYSICAL_EDUCATION("physical education"),
    HISTORY("history"),
    ART("art"),
    MUSIC("music");

    private String subject;
    
    SchoolSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return this.subject;
    }
}
