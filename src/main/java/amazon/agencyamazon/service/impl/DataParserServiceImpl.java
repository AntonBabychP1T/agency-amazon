package amazon.agencyamazon.service.impl;

import amazon.agencyamazon.model.report.SalesAndTrafficWrapper;
import amazon.agencyamazon.repository.SalesAndTrafficReportRepository;
import amazon.agencyamazon.service.DataParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DataParserServiceImpl implements DataParserService {
    private final SalesAndTrafficReportRepository reportRepository;
    private final ObjectMapper objectMapper;
    @Value("${path.to.json.file}")
    private String jsonFilePath;

    @PostConstruct
    public void init() {
        SalesAndTrafficWrapper reportWrapper = readDataFromJson();
        generateCompositeKey(reportWrapper);
        saveDataToDb(reportWrapper);
    }

    @Scheduled(fixedRate = 600000)
    @Override
    public void parseDataFromJsonToDatabase() {
        SalesAndTrafficWrapper reportWrapper = readDataFromJson();
        generateCompositeKey(reportWrapper);
        Optional<SalesAndTrafficWrapper> existingReport =
                reportRepository.findByCompositeKey(reportWrapper.getCompositeKey());
        if (existingReport.isPresent()) {
            SalesAndTrafficWrapper existingWrapper = existingReport.get();
            existingWrapper.setSalesAndTrafficByDate(reportWrapper.getSalesAndTrafficByDate());
            existingWrapper.setSalesAndTrafficByAsin(reportWrapper.getSalesAndTrafficByAsin());
            reportRepository.save(existingWrapper);
        } else {
            generateCompositeKey(reportWrapper);
            reportRepository.save(reportWrapper);
        }
    }

    private SalesAndTrafficWrapper readDataFromJson() {
        try {
            ClassPathResource resource = new ClassPathResource(jsonFilePath);
            File file = resource.getFile();
            return objectMapper
                    .readValue(file, SalesAndTrafficWrapper.class);
        } catch (IOException e) {
            throw new RuntimeException("Error reading from JSON file", e);
        }
    }

    private void generateCompositeKey(SalesAndTrafficWrapper wrapper) {
       wrapper.generateCompositeKey();
    }

    private void saveDataToDb(SalesAndTrafficWrapper reportWrapper) {
        reportRepository.save(reportWrapper);
    }
}
