import com.custom.cache.proxy.ProxyBeanFactory;
import com.file.metadata.dto.FileMetaData;
import com.file.metadata.service.FileMetaDataService;
import com.file.metadata.service.FileMetaDataServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCacheAnnotationApp {

    private static Logger logger = LoggerFactory.getLogger(CustomCacheAnnotationApp.class);

    public static void main(String[] args) {
        ProxyBeanFactory proxyBeanFactory = new ProxyBeanFactory("com.file.metadata.service");
        FileMetaDataService fileMetaDataServiceImpl = (FileMetaDataService) proxyBeanFactory.getBean(FileMetaDataServiceImpl.class);
        Integer fileId = 10121;
        String fileVersion = "1.0.0";
        logger.info("====== Get FileMetaData for File FileId = {} and FileVersion = {} =======", fileId, fileVersion);
        FileMetaData fileMetaData = fileMetaDataServiceImpl.getFileMetaDataByIdAndVersion(fileId, fileVersion);
        logger.info("====== Get FileMetaData for File FileId = {} and FileVersion = {} =======", fileId, fileVersion);
        FileMetaData cacheMetaData = fileMetaDataServiceImpl.getFileMetaDataByIdAndVersion(fileId, fileVersion);
    }

}
