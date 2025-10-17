package com.example.report;

public class ReportResult {
    private final String result;
    private final Exception exception;

    public ReportResult(String result, Exception exception) {
        this.result = result;
        this.exception = exception;
    }

    public String getResult() {
        return result;
    }

    public Exception getException() {
        return exception;
    }
}
