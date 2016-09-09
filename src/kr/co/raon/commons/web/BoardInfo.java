package kr.co.raon.commons.web;

import java.io.Serializable;

public class BoardInfo implements Serializable {
	
	private static final long	 serialVersionUID	= 886265067177548036L;
	
	private String boardId;
	private String boardType;
	
	
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	public String getBoardType() {
		return boardType;
	}
	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}
	
}
