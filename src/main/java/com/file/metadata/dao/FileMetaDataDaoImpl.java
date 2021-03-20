package com.file.metadata.dao;

import com.file.metadata.dto.FileMetaData;
import java.util.HashMap;
import java.util.Map;

public class FileMetaDataDaoImpl {

    private final Map<String , FileMetaData> mapDatabase = new HashMap<>();

    public FileMetaDataDaoImpl(){
        mapDatabase.put("10121_1.0.0", new FileMetaData("products.sql", true, "https://amazon.web.s3.com/account1", 10121, "1.0.0"));
        mapDatabase.put("50234_1.0.1", new FileMetaData("locations.txt", true, "https://amazon.web.s3.com/account2", 50234, "1.0.1"));
        mapDatabase.put("4688_1.0.2", new FileMetaData("mytext.txt", true, "https://amazon.web.s3.com/account3", 4688, "1.0.2"));
        mapDatabase.put("3452_1.0.3", new FileMetaData("movies.mp4", true, "https://amazon.web.s3.com/account4", 3452, "1.0.3"));
        mapDatabase.put("65433_1.0.4", new FileMetaData("image.jpeg", true, "https://amazon.web.s3.com/account5", 65433, "1.0.4"));
    }

    public FileMetaData getFileMetaDataByIdAndVersion(Integer fileId, String fileVersion) {
        return mapDatabase.get(fileId + "_" + fileVersion);
    }
}
