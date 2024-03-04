package amazon.agencyamazon.repository;

import amazon.agencyamazon.model.report.SalesAndTrafficByAsin;
import amazon.agencyamazon.model.report.SalesAndTrafficByDate;
import java.util.List;

public interface CustomSalesAndTrafficReportRepository {
    List<SalesAndTrafficByDate> findByDateRange(String startDate, String endDate);

    List<SalesAndTrafficByAsin> findByAsins(List<String> asins);

    List<SalesAndTrafficByDate> getAllByDates();

    List<SalesAndTrafficByAsin> getAllByAsins();

}
