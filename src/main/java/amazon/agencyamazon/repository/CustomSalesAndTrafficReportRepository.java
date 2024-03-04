package amazon.agencyamazon.repository;

import amazon.agencyamazon.model.report.SalesAndTrafficByAsin;
import amazon.agencyamazon.model.report.SalesAndTrafficByDate;
import amazon.agencyamazon.model.report.SalesAndTrafficWrapper;
import java.util.List;

public interface CustomSalesAndTrafficReportRepository {
    List<SalesAndTrafficByDate> findByDateRange(String startDate, String endDate);

    List<SalesAndTrafficWrapper> findByAsins(List<String> asins);

    List<SalesAndTrafficByDate> getAllByDates();

    List<SalesAndTrafficByAsin> getAllByAsins();

}
