package com.example.report;

public class ReportInfo {
    private volatile ReportStatus status;
    private volatile ReportResult reportResult;

    public ReportInfo(ReportStatus status) {
        this.status = status;
    }

    public synchronized void setStatus(ReportStatus status) {
        this.status = status;
    }

    public ReportStatus getStatus() {
        return status;
    }

    public synchronized void setReportResult(ReportResult result) {
        this.reportResult = result;
    }

    public ReportResult getReportResult() {
        return reportResult;
    }
}
