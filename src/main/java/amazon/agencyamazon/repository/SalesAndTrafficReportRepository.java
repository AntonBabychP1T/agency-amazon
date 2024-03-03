package amazon.agencyamazon.repository;

import amazon.agencyamazon.model.SalesAndTrafficReportWrapper;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalesAndTrafficReportRepository
        extends MongoRepository<SalesAndTrafficReportWrapper, String> {
}
