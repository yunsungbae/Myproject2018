/**
 * @Interface Name : NameConstants.java
 * @Description : 공통 이름값을 final 상수로 모아 가지고 있는 인터페이스
 * @Modification Information
 * @
 * @  수정일        수정자         수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.09.17  고금석         최초생성
 *
 * @author 개발팀
 * @since 2015. 09.17
 * @version 1.0
 * @see
 *
 */
package egovframework.example.cmmn.util;

public interface NameConstants {

	// 메시지 처리를 위한 상수
	public static final String NM_RNT_MSG_CD = "RNT_MSG_CD";
	public static final String NM_RNT_MSG_PARAM_LENGTH = "RNT_MSG_PARAM_LENGTH";
	public static final String NM_NLIP_CONTROLLER = "NLIP_CONTROLLER";

	// DB 연결시 구분 처리를 위한 상수
	public static final String DB_TYPE_ORACLE = "ORACLE";
	public static final String DB_TYPE_POSTGRE = "POSTGRE";
	public static final String DB_TYPE_MSSQL = "MSSQL";
	public static final String DB_TYPE_POSTGRE_TWO = "POSTGRETWO";
	public static final String DB_TYPE_MSSQL_TWO = "MSSQLTWO";

	public static final String[] DB_NAME_ARR = {DB_TYPE_ORACLE, DB_TYPE_POSTGRE, DB_TYPE_MSSQL, DB_TYPE_POSTGRE_TWO, DB_TYPE_MSSQL_TWO};

	// 세션에 넣어놓을 사용자 정보의 key 상수
	public static final String NM_NLIP_USER = "nlipUser";
	public static final String NM_NLIP_ADMIN = "nlipAdmin";
	
	public static final String NM_EMAP_USER = "emapUser";

	// 세션에 넣어놓을 비회원 신청 요청 key 상수
	public static final String NM_PREV_CART_URL = "nlipPrevReqUrl";
	public static final String NM_PREV_CART_DATA = "nlipPrevReqData";
	
	// 유효성 검사 타입
	public static final int REQ_VAL_LENGTH = 0; // 항목값의 크기(length)
	public static final int REQ_VAL_VALUE = 1;  // 항목값의 범위
	public static final int REQ_VAL_NOTNULL = 2;// 값이 꼭 있어야 하는 값
	public static final String REQ_VAL_EXCEPTION_STR = "VALIDATE_ERROR";  // 정합성 체크 오류 관련 메시지
	public static final String VALIDATE_ERR_PAGE = "/error/error"; // 정합성 오류 페이지
}
