package com.tinicube.tinicubebase.function;


public class C {
	/** JSON 관련 변수 **/
	public static final String JSON_STATUS = "json_status";
	public static final boolean JSON_SUCCESS = true;
	public static final boolean JSON_FAILED = false;
	
	/** Base URL **/
	public static final String URL_BASE = "http://192.168.56.1:8000/";
	public static final String API_BASE = URL_BASE + "api/";
	
	/** Chapter **/
	private static final String API_CHAPTER = API_BASE + "chapter/";
	public static final String API_CHAPTER_LIST= API_CHAPTER + "list/";
	public static final String API_CHAPTER_VIEW= API_CHAPTER + "view/";
	
	/** Chapter - Comment **/
	private static final String API_CHAPTER_COMMENT = API_CHAPTER + "comment/";
	public static final String API_CHAPTER_COMMENT_LIST = API_CHAPTER_COMMENT + "list/";
	public static final String API_CHAPTER_COMMENT_ADD = API_CHAPTER_COMMENT + "add/";
}
