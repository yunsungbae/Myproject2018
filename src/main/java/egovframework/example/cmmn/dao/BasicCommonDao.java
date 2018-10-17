/**
 * @Class Name : BasicCommonDao.java
 * @Description : DB 접속 DAO 공통 클래스로 자식 DAO들에 필요한 메서드를 추가하기 위한 클래스
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
package egovframework.example.cmmn.dao;

import egovframework.rte.psl.dataaccess.EgovAbstractMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

public class BasicCommonDao extends EgovAbstractMapper {

	protected Log log = LogFactory.getLog(this.getClass());

	@Override
	public <T> T selectOne(String queryId) {
		return super.selectOne(queryId); //To change body of generated methods, choose Tools | Templates.
	}

	/**
	 * DB에 접속해 한건의 데이터를 가져올 때 사용 여러건 있을시 맨앞의 한건을 가져옴
	 *
	 * @param queryId 조회쿼리ID
	 * @param parameterObject 조회 파라미터
	 * @return 조회된 한건의 데이터
	 */
	public <T> T selectOne(String queryId, Object parameterObject) {
		List<?> list = getSqlSession().selectList(queryId, parameterObject);
		if (list != null && list.size() > 0) {
			return (T) list.get(0);
		}
		return null;
	}
}
