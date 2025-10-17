package com.example.report;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class ReportManager {
    private final ConcurrentHashMap<String, ReportInfo> reports = new ConcurrentHashMap<>();
    private final ExecutorService executorService;

    public ReportManager(int poolSize) {
        this.executorService = Executors.newFixedThreadPool(poolSize);
    }

    public String submitReportGeneration() {
        String trackingId = UUID.randomUUID().toString();
        ReportInfo info = new ReportInfo(ReportStatus.IN_PROGRESS);
        reports.put(trackingId, info);

        executorService.submit(() -> {
            try {
                // Simulate processing delay (1s - 4s)
                int delay = ThreadLocalRandom.current().nextInt(1000, 4000);
                Thread.sleep(delay);

                // Simulate possible failure (20% chance)
                if (ThreadLocalRandom.current().nextInt(1, 6) == 1) {
                    throw new RuntimeException("Report generation failed due to an internal error.");
                }

                String reportData = "Report for Tracking ID " + trackingId + " generated successfully.";
                info.setReportResult(new ReportResult(reportData, null));
                info.setStatus(ReportStatus.COMPLETED);
            } catch (Exception e) {
                info.setReportResult(new ReportResult(null, e));
                info.setStatus(ReportStatus.FAILED);
            }
        });

        return trackingId;
    }

    public ReportInfo getReportInfo(String trackingId) {
        return reports.get(trackingId);
    }

    public void shutdown() {
        executorService.shutdown();
    }
}
