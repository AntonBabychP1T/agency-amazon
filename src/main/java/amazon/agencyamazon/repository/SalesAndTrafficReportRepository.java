package amazon.agencyamazon.repository;

import amazon.agencyamazon.model.report.SalesAndTrafficWrapper;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SalesAndTrafficReportRepository
        extends MongoRepository<SalesAndTrafficWrapper, String> {
    Optional<SalesAndTrafficWrapper> findByCompositeKey(String compositeKey);
}
