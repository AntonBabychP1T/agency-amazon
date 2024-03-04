package amazon.agencyamazon.service;

import amazon.agencyamazon.model.report.SalesAndTrafficByDate;
import amazon.agencyamazon.model.report.SalesAndTrafficWrapper;
import amazon.agencyamazon.model.statistic.TotalSalesAndTrafficStatistics;

import java.util.List;

public interface SalesAndTrafficService {
    List<SalesAndTrafficByDate> fetchStatisticsByDateRange(String startDate, String endDate);

    List<SalesAndTrafficWrapper> fetchStatisticsByAsins(List<String> asins);

    TotalSalesAndTrafficStatistics fetchTotalStatisticsForAllDates();

    TotalSalesAndTrafficStatistics fetchTotalStatisticsForAllAsins();
}
