package com.example.report;

import java.util.Scanner;

public class AsyncReportGeneratorApp {
    private static final ReportManager reportManager = new ReportManager(4);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Asynchronous Report Generator");
        while (true) {
            System.out.println("\n1. Create Report\n2. Check Report Status\n3. Exit");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    handleCreateReport();
                    break;
                case "2":
                    handleCheckStatus(scanner);
                    break;
                case "3":
                    reportManager.shutdown();
                    System.out.println("Exiting.");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void handleCreateReport() {
        String trackingId = reportManager.submitReportGeneration();
        System.out.println("Report request submitted. Tracking ID: " + trackingId);
    }

    private static void handleCheckStatus(Scanner scanner) {
        System.out.print("Enter Tracking ID: ");
        String trackingId = scanner.nextLine();
        ReportInfo info = reportManager.getReportInfo(trackingId);
        if (info == null) {
            System.out.println("No report found with that Tracking ID.");
            return;
        }
        System.out.println("Status: " + info.getStatus());
        if (info.getStatus() == ReportStatus.COMPLETED) {
            System.out.println("Result: " + info.getReportResult().getResult());
        } else if (info.getStatus() == ReportStatus.FAILED) {
            Exception e = info.getReportResult().getException();
            System.out.println("Report generation failed: " + (e != null ? e.getMessage() : "Unknown error"));
        } else {
            System.out.println("Report still in progress. Check back later.");
        }
    }
}
