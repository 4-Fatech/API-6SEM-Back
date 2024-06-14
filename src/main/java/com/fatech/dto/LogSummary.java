package com.fatech.dto;

public class LogSummary {
    private String date;
    private String redzoneName;
    private long logCount;

    public LogSummary(String date, String redzoneName, long logCount) {
        this.date = date;
        this.redzoneName = redzoneName;
        this.logCount = logCount;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRedzoneName() {
        return redzoneName;
    }

    public void setRedzoneName(String redzoneName) {
        this.redzoneName = redzoneName;
    }

    public long getLogCount() {
        return logCount;
    }

    public void setLogCount(long logCount) {
        this.logCount = logCount;
    }
}
