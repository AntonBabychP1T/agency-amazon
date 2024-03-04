package amazon.agencyamazon.dto.statistics;

public record StatisticsResponseDto(
        Double totalOrderedProductSales,
        Double totalOrderedProductSalesB2B,
        Integer totalUnitsOrdered,
        Integer totalUnitsOrderedB2B,
        Double averageUnitsPerOrderItem,
        Double averageUnitsPerOrderItemB2B,
        Double totalRefundRate,
        Integer totalBrowserPageViews,
        Integer totalBrowserPageViewsB2B,
        Integer totalMobileAppPageViews,
        Integer totalMobileAppPageViewsB2B,
        Integer totalSessions,
        Integer totalSessionsB2B,
        Double totalBuyBoxPercentage,
        Double totalBuyBoxPercentageB2B
) {
}
