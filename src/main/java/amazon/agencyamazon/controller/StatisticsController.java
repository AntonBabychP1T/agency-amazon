package amazon.agencyamazon.controller;

import amazon.agencyamazon.dto.statistics.DateRequestDto;
import amazon.agencyamazon.model.report.SalesAndTrafficByDate;
import amazon.agencyamazon.service.SalesAndTrafficService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/stat")
public class StatisticsController {
    private final SalesAndTrafficService salesAndTrafficService;

    @PostMapping()
    public List<SalesAndTrafficByDate> getStatByDate(@RequestBody DateRequestDto requestDto) {
        return salesAndTrafficService.fetchStatisticsByDateRange(requestDto.startDate(), requestDto.endDate());
    }
}
