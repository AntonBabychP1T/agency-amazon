package amazon.agencyamazon.service.impl;

import amazon.agencyamazon.model.report.SalesAndTrafficByAsin;
import amazon.agencyamazon.model.report.SalesAndTrafficByDate;
import amazon.agencyamazon.model.statistic.TotalSalesAndTrafficStatistics;
import amazon.agencyamazon.model.statistic.TotalSalesAndTrafficStatisticsByAsin;
import amazon.agencyamazon.repository.CustomSalesAndTrafficReportRepository;
import amazon.agencyamazon.service.SalesAndTrafficService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SalesAndTrafficServiceImpl implements SalesAndTrafficService {
    private final CustomSalesAndTrafficReportRepository reportRepository;

    @Override
    public TotalSalesAndTrafficStatistics fetchStatisticsByDateRange(String startDate, String endDate) {
        List<SalesAndTrafficByDate> byDateRange = reportRepository.findByDateRange(startDate, endDate);
        return calculateTotalStatistics(byDateRange);
    }

    @Override
    public TotalSalesAndTrafficStatisticsByAsin fetchStatisticsByAsins(List<String> asins) {
        List<SalesAndTrafficByAsin> byAsins = reportRepository.findByAsins(asins);
        TotalSalesAndTrafficStatisticsByAsin statisticsByAsin = new TotalSalesAndTrafficStatisticsByAsin();
        statisticsByAsin.calculateTotalStatistics(byAsins);
        return statisticsByAsin;
    }

    @Override
    public TotalSalesAndTrafficStatistics fetchTotalStatisticsForAllDates() {
        List<SalesAndTrafficByDate> dataByDate = reportRepository.getAllByDates();
        return calculateTotalStatistics(dataByDate);
    }

    @Override
    public TotalSalesAndTrafficStatisticsByAsin fetchTotalStatisticsForAllAsins() {
        List<SalesAndTrafficByAsin> allByAsins = reportRepository.getAllByAsins();
        TotalSalesAndTrafficStatisticsByAsin statisticsByAsin = new TotalSalesAndTrafficStatisticsByAsin();
        statisticsByAsin.calculateTotalStatistics(allByAsins);
        return statisticsByAsin;
    }


    private TotalSalesAndTrafficStatistics calculateTotalStatistics(List<SalesAndTrafficByDate> data) {
        TotalSalesAndTrafficStatistics totalStats = new TotalSalesAndTrafficStatistics();
        calculateTotalSales(data, totalStats);
        calculateTotalOrders(data, totalStats);
        calculateTotalTrafficByDate(data, totalStats);
        return totalStats;
    }

    private void calculateTotalSales(List<SalesAndTrafficByDate> data, TotalSalesAndTrafficStatistics totalStats) {
        totalStats.setTotalOrderedProductSales(calculateTotalOrderedProductSales(data));
        totalStats.setTotalOrderedProductSalesB2B(calculateTotalOrderedProductSalesB2B(data));
        totalStats.setTotalAverageSalesPerOrderItem(calculateTotalAverageSalesPerOrderItem(data));
        totalStats.setTotalAverageSalesPerOrderItemB2B(calculateTotalAverageSalesPerOrderItemB2B(data));
        totalStats.setTotalAverageSellingPrice(calculateTotalAverageSellingPrice(data));
        totalStats.setTotalAverageSellingPriceB2B(calculateTotalAverageSellingPriceB2B(data));
        totalStats.setTotalUnitsRefunded(calculateTotalUnitsRefunded(data));
        totalStats.setTotalRefundRate(calculateTotalRefundRate(data));
        totalStats.setTotalClaimsGranted(calculateTotalClaimsGranted(data));
        totalStats.setTotalClaimsAmount(calculateTotalClaimsAmount(data));
        totalStats.setTotalShippedProductSales(calculateTotalShippedProductSales(data));
        totalStats.setTotalAverageUnitsPerOrderItem(calculateTotalAverageUnitsPerOrderItem(data));
    }

    private void calculateTotalOrders(List<SalesAndTrafficByDate> data, TotalSalesAndTrafficStatistics totalStats) {
        totalStats.setTotalUnitsOrdered(calculateTotalUnitsOrdered(data));
        totalStats.setTotalUnitsOrderedB2B(calculateTotalUnitsOrderedB2B(data));
        totalStats.setTotalOrderItems(calculateTotalOrderItems(data));
        totalStats.setTotalOrderItemsB2B(calculateTotalOrderItemsB2B(data));
        totalStats.setTotalUnitsShipped(calculateTotalUnitsShipped(data));
        totalStats.setTotalOrdersShipped(calculateTotalOrdersShipped(data));
    }

    private void calculateTotalTrafficByDate(List<SalesAndTrafficByDate> data, TotalSalesAndTrafficStatistics totalStats) {
        totalStats.setTotalBrowserPageViews(calculateTotalBrowserPageViews(data));
        totalStats.setTotalBrowserPageViewsB2B(calculateTotalBrowserPageViewsB2B(data));
        totalStats.setTotalMobileAppPageViews(calculateTotalMobileAppPageViews(data));
        totalStats.setTotalMobileAppPageViewsB2B(calculateTotalMobileAppPageViewsB2B(data));
        totalStats.setTotalPageViews(calculateTotalPageViews(data));
        totalStats.setTotalPageViewsB2B(calculateTotalPageViewsB2B(data));
        totalStats.setTotalBrowserSessions(calculateTotalBrowserSessions(data));
        totalStats.setTotalBrowserSessionsB2B(calculateTotalBrowserSessionsB2B(data));
        totalStats.setTotalMobileAppSessions(calculateTotalMobileAppSessions(data));
        totalStats.setTotalMobileAppSessionsB2B(calculateTotalMobileAppSessionsB2B(data));
        totalStats.setTotalSessions(calculateTotalSessions(data));
        totalStats.setTotalSessionsB2B(calculateTotalSessionsB2B(data));
        totalStats.setTotalBuyBoxPercentage(calculateTotalBuyBoxPercentage(data));
        totalStats.setTotalBuyBoxPercentageB2B(calculateTotalBuyBoxPercentageB2B(data));
        totalStats.setTotalOrderItemSessionPercentage(calculateTotalOrderItemSessionPercentage(data));
        totalStats.setTotalOrderItemSessionPercentageB2B(calculateTotalOrderItemSessionPercentageB2B(data));
        totalStats.setTotalUnitSessionPercentage(calculateTotalUnitSessionPercentage(data));
        totalStats.setTotalUnitSessionPercentageB2B(calculateTotalUnitSessionPercentageB2B(data));
        totalStats.setTotalAverageOfferCount(calculateTotalAverageOfferCount(data));
        totalStats.setTotalAverageParentItems(calculateTotalAverageParentItems(data));
        totalStats.setTotalFeedbackReceived(calculateTotalFeedbackReceived(data));
        totalStats.setTotalNegativeFeedbackReceived(calculateTotalNegativeFeedbackReceived(data));
        totalStats.setTotalReceivedNegativeFeedbackRate(calculateTotalReceivedNegativeFeedbackRate(data));
    }

    private double calculateTotalOrderedProductSales(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getOrderedProductSales().getAmount())
                .sum();
    }

    private double calculateTotalOrderedProductSalesB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getOrderedProductSalesB2B().getAmount())
                .sum();
    }

    private int calculateTotalUnitsOrdered(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getSalesByDate().getUnitsOrdered())
                .sum();
    }

    private int calculateTotalUnitsOrderedB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getSalesByDate().getUnitsOrderedB2B())
                .sum();
    }

    private int calculateTotalOrderItems(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getSalesByDate().getTotalOrderItems())
                .sum();
    }

    private int calculateTotalOrderItemsB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getSalesByDate().getTotalOrderItemsB2B())
                .sum();
    }

    private double calculateTotalAverageSalesPerOrderItem(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getAverageSalesPerOrderItem().getAmount())
                .average()
                .orElse(0);
    }

    private double calculateTotalAverageSalesPerOrderItemB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getAverageSalesPerOrderItemB2B().getAmount())
                .average()
                .orElse(0);
    }

    private double calculateTotalAverageUnitsPerOrderItem(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getAverageUnitsPerOrderItem())
                .average()
                .orElse(0);
    }

    private double calculateTotalAverageUnitsPerOrderItemB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getAverageUnitsPerOrderItemB2B())
                .average()
                .orElse(0);
    }

    private double calculateTotalAverageSellingPrice(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getAverageSellingPrice().getAmount())
                .average()
                .orElse(0);
    }

    private double calculateTotalAverageSellingPriceB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getAverageSellingPriceB2B().getAmount())
                .average()
                .orElse(0);
    }

    private int calculateTotalUnitsRefunded(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getSalesByDate().getUnitsRefunded())
                .sum();
    }

    private double calculateTotalRefundRate(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getRefundRate())
                .average()
                .orElse(0);
    }

    private int calculateTotalClaimsGranted(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getSalesByDate().getClaimsGranted())
                .sum();
    }

    private double calculateTotalClaimsAmount(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getClaimsAmount().getAmount())
                .sum();
    }

    private double calculateTotalShippedProductSales(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getSalesByDate().getShippedProductSales().getAmount())
                .sum();
    }

    private int calculateTotalUnitsShipped(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getSalesByDate().getUnitsShipped())
                .sum();
    }

    private int calculateTotalOrdersShipped(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getSalesByDate().getOrdersShipped())
                .sum();
    }

    private int calculateTotalBrowserPageViews(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getBrowserPageViews())
                .sum();
    }

    private int calculateTotalBrowserPageViewsB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getBrowserPageViewsB2B())
                .sum();
    }

    private int calculateTotalMobileAppPageViews(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getMobileAppPageViews())
                .sum();
    }

    private int calculateTotalMobileAppPageViewsB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getMobileAppPageViewsB2B())
                .sum();
    }

    private int calculateTotalPageViews(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getPageViews())
                .sum();
    }

    private int calculateTotalPageViewsB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getPageViewsB2B())
                .sum();
    }

    private int calculateTotalBrowserSessions(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getBrowserSessions())
                .sum();
    }

    private int calculateTotalBrowserSessionsB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getBrowserSessionsB2B())
                .sum();
    }

    private int calculateTotalMobileAppSessions(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getMobileAppSessions())
                .sum();
    }

    private int calculateTotalMobileAppSessionsB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getMobileAppSessionsB2B())
                .sum();
    }

    private int calculateTotalSessions(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getSessions())
                .sum();
    }

    private int calculateTotalSessionsB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getSessionsB2B())
                .sum();
    }

    private double calculateTotalBuyBoxPercentage(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getTrafficByDate().getBuyBoxPercentage())
                .average()
                .orElse(0);
    }

    private double calculateTotalBuyBoxPercentageB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getTrafficByDate().getBuyBoxPercentageB2B())
                .average()
                .orElse(0);
    }

    private double calculateTotalOrderItemSessionPercentage(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getTrafficByDate().getOrderItemSessionPercentage())
                .average()
                .orElse(0);
    }

    private double calculateTotalOrderItemSessionPercentageB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getTrafficByDate().getOrderItemSessionPercentageB2B())
                .average()
                .orElse(0);
    }

    private double calculateTotalUnitSessionPercentage(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getTrafficByDate().getUnitSessionPercentage())
                .average()
                .orElse(0);
    }

    private double calculateTotalUnitSessionPercentageB2B(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getTrafficByDate().getUnitSessionPercentageB2B())
                .average()
                .orElse(0);
    }

    private int calculateTotalAverageOfferCount(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getAverageOfferCount())
                .sum();
    }

    private int calculateTotalAverageParentItems(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getAverageParentItems())
                .sum();
    }

    private int calculateTotalFeedbackReceived(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getFeedbackReceived())
                .sum();
    }

    private int calculateTotalNegativeFeedbackReceived(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToInt(entry -> entry.getTrafficByDate().getNegativeFeedbackReceived())
                .sum();
    }

    private double calculateTotalReceivedNegativeFeedbackRate(List<SalesAndTrafficByDate> data) {
        return data.stream()
                .mapToDouble(entry -> entry.getTrafficByDate().getReceivedNegativeFeedbackRate())
                .average()
                .orElse(0);
    }
}
