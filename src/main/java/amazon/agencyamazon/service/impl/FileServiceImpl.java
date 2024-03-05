package amazon.agencyamazon.service.impl;

import amazon.agencyamazon.service.FileService;
import java.io.File;
import java.io.IOException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public File getFile(String path) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        return resource.getFile();
    }
}

