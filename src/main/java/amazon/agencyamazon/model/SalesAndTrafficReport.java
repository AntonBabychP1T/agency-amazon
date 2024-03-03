package amazon.agencyamazon.model;

import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class SalesAndTrafficReport {
    @Id
    private String id;
    private List<SalesAndTrafficData> salesAndTrafficByDate;

    @Data
    public static class ReportOptions {
        private String dateGranularity;
        private String asinGranularity;
    }

    @Data
    public static class SalesAndTrafficData {
        private String date;
        private SalesData salesByDate;
        private TrafficData trafficByDate;
    }

    @Data
    public static class SalesData {
        private MoneyValue orderedProductSales;
        private MoneyValue orderedProductSalesB2B;
        private Integer unitsOrdered;
        private Integer unitsOrderedB2B;
        private Integer totalOrderItems;
        private Integer totalOrderItemsB2B;
        private MoneyValue averageSalesPerOrderItem;
        private MoneyValue averageSalesPerOrderItemB2B;
        private Double averageUnitsPerOrderItem;
        private Double averageUnitsPerOrderItemB2B;
        private MoneyValue averageSellingPrice;
        private MoneyValue averageSellingPriceB2B;
        private Integer unitsRefunded;
        private Double refundRate;
        private Integer claimsGranted;
        private MoneyValue claimsAmount;
        private MoneyValue shippedProductSales;
        private Integer unitsShipped;
        private Integer ordersShipped;
    }

    @Data
    public static class TrafficData {
        private Integer browserPageViews;
        private Integer browserPageViewsB2B;
        private Integer mobileAppPageViews;
        private Integer mobileAppPageViewsB2B;
        private Integer pageViews;
        private Integer pageViewsB2B;
        private Integer browserSessions;
        private Integer browserSessionsB2B;
        private Integer mobileAppSessions;
        private Integer mobileAppSessionsB2B;
        private Integer sessions;
        private Integer sessionsB2B;
        private Double buyBoxPercentage;
        private Double buyBoxPercentageB2B;
        private Double orderItemSessionPercentage;
        private Double orderItemSessionPercentageB2B;
        private Double unitSessionPercentage;
        private Double unitSessionPercentageB2B;
        private Integer averageOfferCount;
        private Integer averageParentItems;
        private Integer feedbackReceived;
        private Integer negativeFeedbackReceived;
        private Double receivedNegativeFeedbackRate;
    }

    @Data
    public static class MoneyValue {
        private Double amount;
        private String currencyCode;
    }
}
