package amazon.agencyamazon.service;


import amazon.agencyamazon.model.statistic.TotalSalesAndTrafficStatistics;
import amazon.agencyamazon.model.statistic.TotalSalesAndTrafficStatisticsByAsin;
import java.util.List;

public interface SalesAndTrafficService {
    TotalSalesAndTrafficStatistics fetchStatisticsByDateRange(String startDate, String endDate);

    TotalSalesAndTrafficStatisticsByAsin fetchStatisticsByAsins(List<String> asins);

    TotalSalesAndTrafficStatistics fetchTotalStatisticsForAllDates();

    TotalSalesAndTrafficStatisticsByAsin fetchTotalStatisticsForAllAsins();
}
