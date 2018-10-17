/**
 * @Class Name : NlipAbstractDbOneDAO.java
 * @Description : 첫번째 DB 접속 DAO
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

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository("nlipAbstractDbOneDAO")
public class NlipAbstractDbOneDAO extends BasicCommonDao {

	@Resource(name = "dbClientOne_")
	@Override
	public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
		super.setSqlSessionFactory(sqlSession);
	}

	/**
	 * EgovAbstractDAO의 오버라이드 메서드로 dataSource의 dataSourceOne에 접속 할 수 있게 한다.
	 */
	@Resource(name = "dbClientOne")
	@Override
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}

}
