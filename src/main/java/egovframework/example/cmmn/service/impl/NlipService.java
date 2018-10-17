package egovframework.example.cmmn.service;

import java.util.List;

/**  
 * @Interface Name : NlipService.java
 * @Description : Nlsp 프로세스에서 사용할 공통 Service Interface
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2015.09.17  고금석         최초생성
 * 
 * @author 개발팀
 * @since 2015. 09.17
 * @version 1.0
 * @see
 * 
 */
public interface NlipService {
	
	<T> T selectOne(String queryId, Object parameterObject);
	<E> List<E> select(String queryId, Object parameterObject);
	int insert(String queryId, Object parameterObject);
	int update(String queryId, Object parameterObject);
	int delete(String queryId, Object parameterObject);

	<T> T selectOne(String dbType, String queryId, Object parameterObject);
	<E> List<E> select(String dbType, String queryId, Object parameterObject);
	int insert(String dbType, String queryId, Object parameterObject);
	int update(String dbType, String queryId, Object parameterObject);
	int delete(String dbType, String queryId, Object parameterObject);
	
	String getProperty(String key);
}
