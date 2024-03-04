package amazon.agencyamazon.repository;

import amazon.agencyamazon.model.report.SalesAndTrafficByAsin;
import amazon.agencyamazon.model.report.SalesAndTrafficByDate;
import amazon.agencyamazon.model.report.SalesAndTrafficWrapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomSalesAndTrafficReportRepositoryImpl implements CustomSalesAndTrafficReportRepository {
    private final MongoTemplate mongoTemplate;

    @Override
    public List<SalesAndTrafficByDate> findByDateRange(String startDate, String endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("salesAndTrafficByDate.date").gte(startDate).lte(endDate)),
                Aggregation.unwind("salesAndTrafficByDate"),
                Aggregation.match(Criteria.where("salesAndTrafficByDate.date").gte(startDate).lte(endDate)),
                Aggregation.replaceRoot("salesAndTrafficByDate")
        );
        AggregationResults<SalesAndTrafficByDate> results = mongoTemplate.aggregate(
                aggregation, "salesAndTrafficWrapper", SalesAndTrafficByDate.class
        );
        return results.getMappedResults();
    }

    @Override
    public List<SalesAndTrafficWrapper> findByAsins(List<String> asins) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("salesAndTrafficByAsin.parentAsin").in(asins)),
                Aggregation.group("parentAsin")
                        .sum("salesAndTrafficByAsin.salesByAsin.orderedProductSales.amount").as("totalSales")
                        .sum("salesAndTrafficByAsin.trafficByAsin.pageViews").as("totalPageViews"),
                Aggregation.project("totalSales", "totalPageViews").and("parentAsin").previousOperation()
        );
        AggregationResults<SalesAndTrafficWrapper> results = mongoTemplate.aggregate(
                aggregation, "salesAndTrafficWrapper", SalesAndTrafficWrapper.class
        );
        return results.getMappedResults();
    }

    @Cacheable("salesAndTrafficByDate")
    @Override
    public List<SalesAndTrafficByDate> getAllByDates() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("salesAndTrafficByDate"),
                Aggregation.group("salesAndTrafficByDate.date")
                        .first("salesAndTrafficByDate").as("salesAndTrafficByDate"),
                Aggregation.project("salesAndTrafficByDate")
        );

        AggregationResults<SalesAndTrafficByDate> results = mongoTemplate.aggregate(
                aggregation, "salesAndTrafficWrapper", SalesAndTrafficByDate.class
        );
        return results.getMappedResults();
    }

    @Cacheable("salesAndTrafficByAsin")
    @Override
    public List<SalesAndTrafficByAsin> getAllByAsins() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("salesAndTrafficByAsin"),
                Aggregation.group("salesAndTrafficByAsin.parentAsin")
                        .first("salesAndTrafficByAsin").as("salesAndTrafficByAsin"),
                Aggregation.project("salesAndTrafficByAsin")
        );
        AggregationResults<SalesAndTrafficByAsin> results = mongoTemplate.aggregate(
                aggregation, "salesAndTrafficWrapper", SalesAndTrafficByAsin.class
        );
        return results.getMappedResults();
    }
}
