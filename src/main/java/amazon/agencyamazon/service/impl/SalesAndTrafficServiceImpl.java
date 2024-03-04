package amazon.agencyamazon.service.impl;

import amazon.agencyamazon.model.report.SalesAndTrafficByAsin;
import amazon.agencyamazon.model.report.SalesAndTrafficByDate;
import amazon.agencyamazon.model.report.SalesAndTrafficWrapper;
import amazon.agencyamazon.model.statistic.TotalSalesAndTrafficStatistics;
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
    public List<SalesAndTrafficByDate> fetchStatisticsByDateRange(String startDate, String endDate) {
        return reportRepository.findByDateRange(startDate, endDate);

    }

    @Override
    public List<SalesAndTrafficWrapper> fetchStatisticsByAsins(List<String> asins) {
        return reportRepository.findByAsins(asins);
    }

    @Override
    public TotalSalesAndTrafficStatistics fetchTotalStatisticsForAllDates() {
        List<SalesAndTrafficByDate> dataByDate = reportRepository.getAllByDates();
        return calculateTotalStatistics(dataByDate);
    }

    @Override
    public TotalSalesAndTrafficStatistics fetchTotalStatisticsForAllAsins() {
        List<SalesAndTrafficByAsin> allByAsins = reportRepository.getAllByAsins();
        return null;
    }

    private TotalSalesAndTrafficStatistics calculateTotalStatistics(List<SalesAndTrafficByDate> data) {
        TotalSalesAndTrafficStatistics totalStats = new TotalSalesAndTrafficStatistics();
        totalStats.setTotalOrderedProductSales(
                data.stream()
                        .mapToDouble(entry -> entry.getSalesByDate().getOrderedProductSales().getAmount())
                        .sum()
        );
        totalStats.setTotalOrderedProductSalesB2B(
                data.stream()
                        .mapToDouble(entry -> entry.getSalesByDate().getOrderedProductSalesB2B().getAmount())
                        .sum()
        );
        totalStats.setTotalUnitsOrdered(
                data.stream()
                        .mapToInt(entry -> entry.getSalesByDate().getUnitsOrdered())
                        .sum()
        );
        totalStats.setTotalUnitsOrderedB2B(
                data.stream()
                        .mapToInt(entry -> entry.getSalesByDate().getUnitsOrderedB2B())
                        .sum()
        );
        totalStats.setTotalOrderItems(
                data.stream()
                        .mapToInt(entry -> entry.getSalesByDate().getTotalOrderItems())
                        .sum()
        );
        totalStats.setTotalOrderItemsB2B(
                data.stream()
                        .mapToInt(entry -> entry.getSalesByDate().getTotalOrderItemsB2B())
                        .sum()
        );

        return totalStats;
    }


}
