package amazon.agencyamazon.service;

import java.io.File;
import java.io.IOException;

public interface FileService {
    File getFile(String path) throws IOException;
}
