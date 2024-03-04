package amazon.agencyamazon.model.statistic;

import amazon.agencyamazon.model.report.SalesAndTrafficByAsin;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TotalSalesAndTrafficStatisticsByAsin extends TotalSalesAndTrafficStatistics {
    private int totalUnitsOrdered;
    private int totalUnitsOrderedB2B;
    private double totalOrderedProductSalesAmount;
    private double totalOrderedProductSalesAmountB2B;
    private int totalOrderItems;
    private int totalOrderItemsB2B;

    private int totalBrowserSessions;
    private int totalBrowserSessionsB2B;
    private int totalMobileAppSessions;
    private int totalMobileAppSessionsB2B;
    private int totalSessions;
    private int totalSessionsB2B;

    private double totalBrowserSessionPercentage;
    private double totalBrowserSessionPercentageB2B;
    private double totalMobileAppSessionPercentage;
    private double totalMobileAppSessionPercentageB2B;
    private double totalSessionPercentage;
    private double totalSessionPercentageB2B;

    private int totalBrowserPageViews;
    private int totalBrowserPageViewsB2B;
    private int totalMobileAppPageViews;
    private int totalMobileAppPageViewsB2B;
    private int totalPageViews;
    private int totalPageViewsB2B;

    private double totalBrowserPageViewsPercentage;
    private double totalBrowserPageViewsPercentageB2B;
    private double totalMobileAppPageViewsPercentage;
    private double totalMobileAppPageViewsPercentageB2B;
    private double totalPageViewsPercentage;
    private double totalPageViewsPercentageB2B;

    private double totalBuyBoxPercentage;
    private double totalBuyBoxPercentageB2B;

    private double totalUnitSessionPercentage;
    private double totalUnitSessionPercentageB2B;

    public void calculateTotalStatistics(List<SalesAndTrafficByAsin> data) {
        this.totalUnitsOrdered = data.stream()
                .mapToInt(entry -> entry.getSalesByAsin().getUnitsOrdered())
                .sum();

        this.totalUnitsOrderedB2B = data.stream()
                .mapToInt(entry -> entry.getSalesByAsin().getUnitsOrderedB2B())
                .sum();

        this.totalOrderedProductSalesAmount = data.stream()
                .mapToDouble(entry -> entry.getSalesByAsin().getOrderedProductSales().getAmount())
                .sum();

        this.totalOrderedProductSalesAmountB2B = data.stream()
                .mapToDouble(entry -> entry.getSalesByAsin().getOrderedProductSalesB2B().getAmount())
                .sum();

        this.totalOrderItems = data.stream()
                .mapToInt(entry -> entry.getSalesByAsin().getTotalOrderItems())
                .sum();

        this.totalOrderItemsB2B = data.stream()
                .mapToInt(entry -> entry.getSalesByAsin().getTotalOrderItemsB2B())
                .sum();

        this.totalBrowserSessionsB2B = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getBrowserSessionsB2B())
                .sum();

        this.totalMobileAppSessions = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getMobileAppSessions())
                .sum();

        this.totalMobileAppSessionsB2B = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getMobileAppSessionsB2B())
                .sum();

        this.totalSessions = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getSessions())
                .sum();

        this.totalSessionsB2B = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getSessionsB2B())
                .sum();

        this.totalBrowserSessionPercentage = calculateAverage(data, "browserSessionPercentage");
        this.totalBrowserSessionPercentageB2B = calculateAverage(data, "browserSessionPercentageB2B");
        this.totalMobileAppSessionPercentage = calculateAverage(data, "mobileAppSessionPercentage");
        this.totalMobileAppSessionPercentageB2B = calculateAverage(data, "mobileAppSessionPercentageB2B");
        this.totalSessionPercentage = calculateAverage(data, "sessionPercentage");
        this.totalSessionPercentageB2B = calculateAverage(data, "sessionPercentageB2B");

        this.totalBrowserPageViews = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getBrowserPageViews())
                .sum();

        this.totalBrowserPageViewsB2B = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getBrowserPageViewsB2B())
                .sum();

        this.totalMobileAppPageViews = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getMobileAppPageViews())
                .sum();

        this.totalMobileAppPageViewsB2B = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getMobileAppPageViewsB2B())
                .sum();

        this.totalPageViews = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getPageViews())
                .sum();

        this.totalPageViewsB2B = data.stream()
                .mapToInt(entry -> entry.getTrafficByAsin().getPageViewsB2B())
                .sum();

        this.totalBrowserPageViewsPercentage = calculateAverage(data, "browserPageViewsPercentage");
        this.totalBrowserPageViewsPercentageB2B = calculateAverage(data, "browserPageViewsPercentageB2B");
        this.totalMobileAppPageViewsPercentage = calculateAverage(data, "mobileAppPageViewsPercentage");
        this.totalMobileAppPageViewsPercentageB2B = calculateAverage(data, "mobileAppPageViewsPercentageB2B");
        this.totalPageViewsPercentage = calculateAverage(data, "pageViewsPercentage");
        this.totalPageViewsPercentageB2B = calculateAverage(data, "pageViewsPercentageB2B");

        this.totalBuyBoxPercentage = calculateAverage(data, "buyBoxPercentage");
        this.totalBuyBoxPercentageB2B = calculateAverage(data, "buyBoxPercentageB2B");

        this.totalUnitSessionPercentage = calculateAverage(data, "unitSessionPercentage");
        this.totalUnitSessionPercentageB2B = calculateAverage(data, "unitSessionPercentageB2B");
    }

    private double calculateAverage(List<SalesAndTrafficByAsin> data, String fieldName) {
        return data.stream()
                .mapToDouble(entry -> getDoubleValueFromFieldName(entry, fieldName))
                .average()
                .orElse(0);
    }

    private double getDoubleValueFromFieldName(SalesAndTrafficByAsin entry, String fieldName) {
        return switch (fieldName) {
            case "browserSessionPercentage" -> entry.getTrafficByAsin().getBrowserSessionPercentage();
            case "browserSessionPercentageB2B" -> entry.getTrafficByAsin().getBrowserSessionPercentageB2B();
            case "mobileAppSessionPercentage" -> entry.getTrafficByAsin().getMobileAppSessionPercentage();
            case "mobileAppSessionPercentageB2B" -> entry.getTrafficByAsin().getMobileAppSessionPercentageB2B();
            case "sessionPercentage" -> entry.getTrafficByAsin().getSessionPercentage();
            case "sessionPercentageB2B" -> entry.getTrafficByAsin().getSessionPercentageB2B();
            case "browserPageViewsPercentage" -> entry.getTrafficByAsin().getBrowserPageViewsPercentage();
            case "browserPageViewsPercentageB2B" -> entry.getTrafficByAsin().getBrowserPageViewsPercentageB2B();
            case "mobileAppPageViewsPercentage" -> entry.getTrafficByAsin().getMobileAppPageViewsPercentage();
            case "mobileAppPageViewsPercentageB2B" -> entry.getTrafficByAsin().getMobileAppPageViewsPercentageB2B();
            case "pageViewsPercentage" -> entry.getTrafficByAsin().getPageViewsPercentage();
            case "pageViewsPercentageB2B" -> entry.getTrafficByAsin().getPageViewsPercentageB2B();
            case "buyBoxPercentage" -> entry.getTrafficByAsin().getBuyBoxPercentage();
            case "buyBoxPercentageB2B" -> entry.getTrafficByAsin().getBuyBoxPercentageB2B();
            case "unitSessionPercentage" -> entry.getTrafficByAsin().getUnitSessionPercentage();
            case "unitSessionPercentageB2B" -> entry.getTrafficByAsin().getUnitSessionPercentageB2B();
            default -> 0;
        };
    }
}
