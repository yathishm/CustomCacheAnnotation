package com.file.metadata.service;

import com.file.metadata.dto.FileMetaData;

public interface FileMetaDataService {

    FileMetaData getFileMetaDataByIdAndVersion(final Integer fileId, final String fileVersion);

}
