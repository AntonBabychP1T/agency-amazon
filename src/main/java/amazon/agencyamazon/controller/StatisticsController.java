package amazon.agencyamazon.controller;

import amazon.agencyamazon.model.statistic.TotalSalesAndTrafficStatistics;
import amazon.agencyamazon.model.statistic.TotalSalesAndTrafficStatisticsByAsin;
import amazon.agencyamazon.service.SalesAndTrafficService;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Statistics manager", description = "Endpoints for getting statistic by date and asin")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/stats")
public class StatisticsController {
    private final SalesAndTrafficService salesAndTrafficService;

    @Operation(summary = "Get statistic by date range",
            description = "to get single date statistic write same date into"
                    + "both field")
    @Parameter(name = "startDate", description = "String date, format: "
            + "yyyy-MM-dd", required = true, example = "2024-02-24")
    @Parameter(name = "endDate", description = "String date, format: "
            + "yyyy-MM-dd", required = true, example = "2024-02-25")
    @GetMapping("/by-date")
    public TotalSalesAndTrafficStatistics getTotalStatisticsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        return salesAndTrafficService.fetchStatisticsByDateRange(startDate, endDate);
    }

    @Operation(summary = "Get statistic by asins")
    @GetMapping("/by-asin")
    public TotalSalesAndTrafficStatisticsByAsin getTotalStatisticsByAsins(
            @RequestParam("asins") List<String> asins) {
        return salesAndTrafficService.fetchStatisticsByAsins(asins);
    }

    @Operation(summary = "Get total statistic by all dates")
    @GetMapping("/total-by-dates")
    public TotalSalesAndTrafficStatistics getTotalStatisticsForAllDates() {
        return salesAndTrafficService.fetchTotalStatisticsForAllDates();
    }

    @Operation(summary = "Get total statistic by all asins")
    @GetMapping("/total-by-asins")
    public TotalSalesAndTrafficStatisticsByAsin getTotalStatisticsForAllAsins() {
        return salesAndTrafficService.fetchTotalStatisticsForAllAsins();
    }
}
