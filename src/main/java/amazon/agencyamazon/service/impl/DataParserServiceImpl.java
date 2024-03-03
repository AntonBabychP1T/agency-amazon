package amazon.agencyamazon.service.impl;

import amazon.agencyamazon.model.SalesAndTrafficReportWrapper;
import amazon.agencyamazon.repository.SalesAndTrafficReportRepository;
import amazon.agencyamazon.service.DataParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import jakarta.annotation.PostConstruct;
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
        initializeDatabase();
    }

    @Override
    public void initializeDatabase() {
        try {
            ClassPathResource resource = new ClassPathResource(jsonFilePath);
            File file = resource.getFile();
            SalesAndTrafficReportWrapper reportWrapper = objectMapper
                    .readValue(file, SalesAndTrafficReportWrapper.class);
            reportRepository.save(reportWrapper);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRate = 3000000)
    @Override
    public void updateDataRegularly() {
        initializeDatabase();
    }
}
