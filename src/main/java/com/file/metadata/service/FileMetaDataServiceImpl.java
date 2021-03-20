package com.file.metadata.service;

import com.custom.cache.annotation.CustomCacheable;
import com.file.metadata.dto.FileMetaData;
import com.file.metadata.dao.FileMetaDataDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileMetaDataServiceImpl implements FileMetaDataService {

    private FileMetaDataDaoImpl fileMetaDataDaoImpl = new FileMetaDataDaoImpl();

    private static Logger logger = LoggerFactory.getLogger(FileMetaDataServiceImpl.class);

    @CustomCacheable(cacheName = "fileMetaData")
    public FileMetaData getFileMetaDataByIdAndVersion(Integer fileId, String fileVersion) {
        logger.info("Key [ FileId= {} , FileVersion= {} ] is NOT present in the Cache, fetching it from the Database ", fileId, fileVersion);
        return fileMetaDataDaoImpl.getFileMetaDataByIdAndVersion(fileId, fileVersion);
    }
}
