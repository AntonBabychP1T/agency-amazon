package amazon.agencyamazon.model.report;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@Document
public class SalesAndTrafficWrapper {
    @Id
    private String compositeKey;
    private ReportSpecification reportSpecification;
    private List<SalesAndTrafficByDate> salesAndTrafficByDate;
    private List<SalesAndTrafficByAsin> salesAndTrafficByAsin;

    public void generateCompositeKey() {
        this.compositeKey = reportSpecification.getReportType() + "_" +
                reportSpecification.getDataStartTime() + "_" +
                reportSpecification.getDataEndTime() + "_" +
                String.join("-", reportSpecification.getMarketplaceIds());
    }
}
