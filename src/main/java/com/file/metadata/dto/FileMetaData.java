package com.file.metadata.dto;

public class FileMetaData {

    private String fileName;
    private boolean isReadOnly;
    private String fileLocation;
    private Integer fileid;
    private String fileVersion;

    public FileMetaData(String fileName, boolean isReadOnly, String fileLocation, Integer fileid, String fileVersion) {
        this.fileName = fileName;
        this.isReadOnly = isReadOnly;
        this.fileLocation = fileLocation;
        this.fileid = fileid;
        this.fileVersion = fileVersion;
    }

    @Override
    public String toString() {
        return "FileMetaData{" +
                "fileName='" + fileName + '\'' +
                ", isReadOnly=" + isReadOnly +
                ", fileLocation='" + fileLocation + '\'' +
                ", fileid=" + fileid +
                ", fileVersion='" + fileVersion + '\'' +
                '}';
    }
}
