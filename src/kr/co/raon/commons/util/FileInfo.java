package kr.co.raon.commons.util;

public class FileInfo extends BaseObject{
	
	private static final long serialVersionUID = 1L;
	
	/** 파일 넘버 */
	private int fileNo;
	/** 파일명 */
    private String fileName;
    /** 저장된 파일명 */
    private String realFileName;
    /** 파일 사이즈 */
    private long fileSize;
    /** 타입 */
    private String contentType;
    /** 업로드 상태 */
    private boolean uploadStatus;
    /** 업로드 실패이유 */
    private String uplaodFailReason;
	/**
	 * @return the fileNo
	 */
	public int getFileNo() {
		return fileNo;
	}
	/**
	 * @param fileNo the fileNo to set
	 */
	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the realFileName
	 */
	public String getRealFileName() {
		return realFileName;
	}
	/**
	 * @param realFileName the realFileName to set
	 */
	public void setRealFileName(String realFileName) {
		this.realFileName = realFileName;
	}
	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @return the contentType
	 */
	public String getContentType() {
		return contentType;
	}
	/**
	 * @param contentType the contentType to set
	 */
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	/**
	 * @return the uploadStatus
	 */
	public boolean isUploadStatus() {
		return uploadStatus;
	}
	/**
	 * @param uploadStatus the uploadStatus to set
	 */
	public void setUploadStatus(boolean uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	/**
	 * @return the uplaodFailReason
	 */
	public String getUplaodFailReason() {
		return uplaodFailReason;
	}
	/**
	 * @param uplaodFailReason the uplaodFailReason to set
	 */
	public void setUplaodFailReason(String uplaodFailReason) {
		this.uplaodFailReason = uplaodFailReason;
	}
	
}
