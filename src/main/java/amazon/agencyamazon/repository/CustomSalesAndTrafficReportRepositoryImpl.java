package amazon.agencyamazon.repository;

import amazon.agencyamazon.model.report.SalesAndTrafficByAsin;
import amazon.agencyamazon.model.report.SalesAndTrafficByDate;
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
    public List<SalesAndTrafficByAsin> findByAsins(List<String> asins) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("salesAndTrafficByAsin.parentAsin").in(asins)),
                Aggregation.unwind("salesAndTrafficByAsin"),
                Aggregation.group("salesAndTrafficByAsin.parentAsin")
                        .first("salesAndTrafficByAsin.salesByAsin").as("salesByAsin")
                        .first("salesAndTrafficByAsin.trafficByAsin").as("trafficByAsin"),
                Aggregation.project("parentAsin", "salesByAsin", "trafficByAsin")
        );
        AggregationResults<SalesAndTrafficByAsin> results = mongoTemplate.aggregate(
                aggregation, "salesAndTrafficWrapper", SalesAndTrafficByAsin.class
        );
        return results.getMappedResults();
    }


    @Cacheable("salesAndTrafficByDate")
    @Override
    public List<SalesAndTrafficByDate> getAllByDates() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("salesAndTrafficByDate"),
                Aggregation.group("salesAndTrafficByDate.date")
                        .first("salesAndTrafficByDate.salesByDate").as("salesByDate")
                        .first("salesAndTrafficByDate.trafficByDate").as("trafficByDate"),
                Aggregation.project().and("salesByDate").as("salesByDate")
                        .and("trafficByDate").as("trafficByDate")
                        .and("_id").as("date")
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
                        .first("salesAndTrafficByAsin.salesByAsin").as("salesByAsin")
                        .first("salesAndTrafficByAsin.trafficByAsin").as("trafficByAsin"),
                Aggregation.project().and("trafficByAsin").as("trafficByAsin")
                        .and("salesByAsin").as("salesByAsin")
                        .and("_id").as("parentAsin")
        );
        AggregationResults<SalesAndTrafficByAsin> results = mongoTemplate.aggregate(
                aggregation, "salesAndTrafficWrapper", SalesAndTrafficByAsin.class
        );
        return results.getMappedResults();
    }
}
