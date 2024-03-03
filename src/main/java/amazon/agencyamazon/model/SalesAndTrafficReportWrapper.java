package amazon.agencyamazon.model;

import lombok.Data;

import java.util.List;

@Data
public class SalesAndTrafficReportWrapper {
    private ReportSpecification reportSpecification;
    List<SalesAndTrafficReport> salesAndTrafficReports;

    @Data
    public static class ReportSpecification {
        private String reportType;
        private SalesAndTrafficReport.ReportOptions reportOptions;
        private String dataStartTime;
        private String dataEndTime;
        private List<String> marketplaceIds;
    }
}
