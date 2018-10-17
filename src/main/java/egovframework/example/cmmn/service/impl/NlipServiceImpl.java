/**
 * @Class Name : NlipServiceImpl.java
 * @Description : Nlsp 프로세스에서 사용할 공통 클래스로 지정한 DB에 연결 할 수 있게 해주는 Service
 * @Modification Information
 * @
 * @ 수정일 수정자 수정내용
 * @ --------- --------- -------------------------------
 * @ 2015.09.17 고금석 최초생성
 *
 * @author 개발팀
 * @since 2015. 09.17
 * @version 1.0
 * @see
 *
 */
package egovframework.example.cmmn.service.impl;

import egovframework.example.cmmn.dao.BasicCommonDao;
import egovframework.example.cmmn.dao.NlipAbstractDbOneDAO;
import egovframework.example.cmmn.service.NlipService;
import egovframework.example.cmmn.util.NameConstants;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("nlipService")
public class NlipServiceImpl extends EgovAbstractServiceImpl implements NlipService, NameConstants {

	protected Log log = LogFactory.getLog(this.getClass());

	/**
	 * global.properties에 지정해 놓은 정보를 가져오기 위한 변수
	 */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	/**
	 * 지정된 DB에 접속하기 위한 변수
	 */
	@Resource(name = "nlipAbstractDbOneDAO")
	private NlipAbstractDbOneDAO nlipAbstractDbOneDAO;

//	/**
//	 * 지정된 DB에 접속하기 위한 변수
//	 */
//	@Resource(name = "nlipAbstractDbTwoDAO")
//	private NlipAbstractDbTwoDAO nlipAbstractDbTwoDAO;
//
//	/**
//	 * 지정된 DB에 접속하기 위한 변수
//	 */
//	@Resource(name = "nlipAbstractDbThreeDAO")
//	private NlipAbstractDbThreeDAO nlipAbstractDbThreeDAO;
//
//	/**
//	 * 지정된 DB에 접속하기 위한 변수
//	 */
//	@Resource(name = "nlipAbstractDbFourDAO")
//	private NlipAbstractDbFourDAO nlipAbstractDbFourDAO;
//
//	/**
//	 * 지정된 DB에 접속하기 위한 변수
//	 */
//	@Resource(name = "nlipAbstractDbFiveDAO")
//	private NlipAbstractDbFiveDAO nlipAbstractDbFiveDAO;

	/**
	 * queryId의 문자열로 접속할 DB를 찾아 queryId와 parameterObject로 조회되는 데이터의 맨앞 한건의 데이터를
	 * 가져온다.
	 *
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 조회된 맨앞 한건의 데이터
	 */
	public <T> T selectOne(String queryId, Object parameterObject) {
		return getDaoQueryId(queryId).selectOne(queryId, parameterObject);
	}
	
	public <T> T selectOne(String queryId) {
		return getDaoQueryId(queryId).selectOne(queryId);
	}	

	/**
	 * queryId의 문자열로 접속할 DB를 찾아 queryId와 parameterObject로 데이터를 조회한다.
	 *
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 조회된 데이터
	 */
	public <E extends Object> List<E> select(String queryId, Object parameterObject) {
		return getDaoQueryId(queryId).selectList(queryId, parameterObject);
	}

	/**
	 * queryId의 문자열로 접속할 DB를 찾아 queryId와 parameterObject로 데이터를 DB에 넣는다.
	 *
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 상태값
	 */
	public int insert(String queryId, Object parameterObject) {
		return getDaoQueryId(queryId).update(queryId, parameterObject);
	}

	/**
	 * queryId의 문자열로 접속할 DB를 찾아 queryId와 parameterObject로 데이터를 DB에 넣고 sql return
	 * 값을 리턴한다.
	 *
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 상태값
	 */
	public Object insertSelect(String queryId, Object parameterObject) {
		return getDaoQueryId(queryId).insert(queryId, parameterObject);
	}

	/**
	 * queryId의 문자열로 접속할 DB를 찾아 queryId와 parameterObject로 DB의 데이터를 수정한다.
	 *
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 상태값
	 */
	public int update(String queryId, Object parameterObject) {
		return getDaoQueryId(queryId).update(queryId, parameterObject);
	}

	/**
	 * queryId의 문자열로 접속할 DB를 찾아 queryId와 parameterObject로 DB의 데이터를 삭제한다.
	 *
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 상태값
	 */
	public int delete(String queryId, Object parameterObject) {
		return getDaoQueryId(queryId).update(queryId, parameterObject);
	}

	/**
	 * dbType의 값으로 접속할 DB를 찾아 queryId와 parameterObject로 조회되는 데이터의 맨앞 한건의 데이터를
	 * 가져온다.
	 *
	 * @param dbType 접속DB 구분자
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 조회된 맨앞 한건의 데이터
	 */
	public <T> T selectOne(String dbType, String queryId, Object parameterObject) {
		return getDaoDbType(dbType).selectOne(queryId, parameterObject);
	}

	/**
	 * dbType의 값으로 접속할 DB를 찾아 queryId와 parameterObject로 조회되는 데이터를 가져온다.
	 *
	 * @param dbType 접속DB 구분자
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 조회 데이터
	 */
	public List<?> select(String dbType, String queryId, Object parameterObject) {
		return getDaoDbType(dbType).list(queryId, parameterObject);
	}

	/**
	 * dbType의 값으로 접속할 DB를 찾아 queryId와 parameterObject로 조회되는 데이터를 DB에 넣는다.
	 *
	 * @param dbType 접속DB 구분자
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 상태값
	 */
	public int insert(String dbType, String queryId, Object parameterObject) {
		return getDaoDbType(dbType).update(queryId, parameterObject);
	}

	/**
	 * dbType의 값으로 접속할 DB를 찾아 queryId와 parameterObject로 조회되는 데이터를 DB를 수정한다.
	 *
	 * @param dbType 접속DB 구분자
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 상태값
	 */
	public int update(String dbType, String queryId, Object parameterObject) {
		return getDaoDbType(dbType).update(queryId, parameterObject);
	}

	/**
	 * dbType의 값으로 접속할 DB를 찾아 queryId와 parameterObject로 조회되는 데이터를 DB를 삭제한다.
	 *
	 * @param dbType 접속DB 구분자
	 * @param queryId 쿼리ID
	 * @param parameterObject 파라미터
	 * @return 상태값
	 */
	public int delete(String dbType, String queryId, Object parameterObject) {
		return getDaoDbType(dbType).update(queryId, parameterObject);
	}

	/**
	 * queryId의 값을 분석해 접속을 원하는 DB의 DAO를 리턴한다.
	 *
	 * @param queryId 쿼리ID
	 * @return 구분된 DAO
	 */
	private BasicCommonDao getDaoQueryId(String queryId) {
		String parseQueryId = queryId.toUpperCase();
		String dbStr = parseQueryId.substring(0, parseQueryId.indexOf("."));
		for (int i = 0, objectSize = DB_NAME_ARR.length; i < objectSize; i++) {
			if (dbStr.equals(DB_NAME_ARR[i])) {
				return getDaoDbType(DB_NAME_ARR[i]);
			}
		}
		return null;
	}

	/**
	 * dbType의 값으로 접속을 원하는 DB의 DAO를 리턴한다.
	 *
	 * @param queryId 쿼리ID
	 * @return 구분된 DAO
	 */
	private BasicCommonDao getDaoDbType(String dbType) {
		if (DB_TYPE_ORACLE.equals(dbType)) {
			return nlipAbstractDbOneDAO;
	}
	//else if (DB_TYPE_POSTGRE.equals(dbType)) {
//			return nlipAbstractDbTwoDAO;
//		} else if (DB_TYPE_MSSQL.equals(dbType)) {
//			return nlipAbstractDbThreeDAO;
//		} else if (DB_TYPE_POSTGRE_TWO.equals(dbType)) {
//			return nlipAbstractDbFourDAO;
//		} else if (DB_TYPE_MSSQL_TWO.equals(dbType)) {
//			return nlipAbstractDbFiveDAO;
//		}
		return null;
	}

	/**
	 * 환경설정 properties 파일의 key로 지정되어 있는 value값을 리턴한다.
	 *
	 * @param key properties 파일에서 값을 찾을 key 값
	 * @return properties 의 조회된 value 값
	 */
	public String getProperty(String key) {
		return propertiesService.getString(key);
	}
}
