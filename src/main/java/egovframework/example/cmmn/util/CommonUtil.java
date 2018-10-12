package egovframework.example.cmmn.util;

import egovframework.nlip.common.NameConstants;
import egovframework.nlip.common.tag.PropertiesMgr;
import egovframework.nlip.common.web.NlipController;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**  
 * @Class Name : CommonUtil.java
 * @Description : 플랫폼 전체 공통에서 사용하는 기능을 모아 놓은 클래스 
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
public class CommonUtil implements NameConstants{

	/**
	 * 화면에서 사용할 메시지 코드와 인자값을 map에 넣음
	 * @param nlipController NlipController
	 * @param modelMap map
	 * @param msgCd 메시지 코드
	 * @param 메시지 사용 인자값
	 */
	public static void setMsgCD(NlipController nlipController, ModelMap modelMap, String msgCd, String... paramArr){
		setMsgCD(nlipController, modelMap, msgCd);
		if(paramArr != null && paramArr.length > 0){
			modelMap.addAttribute(NM_RNT_MSG_PARAM_LENGTH, paramArr.length);
			for (int i=0, objectSize=paramArr.length; i<objectSize; i++) {
				modelMap.addAttribute(NM_RNT_MSG_CD+i, paramArr[i]);
			}
		}
	}

	/**
	 * 화면에서 사용할 메시지 코드를 map에 넣음
	 * @param nlipController NlipController
	 * @param modelMap map
	 * @param msgCd 메시지 코드
	 */
	public static void setMsgCD(NlipController nlipController, ModelMap modelMap, String msgCd){
		modelMap.addAttribute(NM_NLIP_CONTROLLER, nlipController);
		modelMap.addAttribute(NM_RNT_MSG_CD, msgCd);
	}
	
	/**
	 * 기준 코드로 기준 코드의 상세 값 리스트를 불러온다.
	 * @param nlipController NlipController
	 * @param codeId 조회하려는 기준 코드
	 * @return 조회된 기준 코드값 (key, value)
	 */
	public static List<Map<String, String>> getMasterCdList(NlipController nlipController, String codeId){
		HashMap<String, String> parameterObject = new HashMap<String, String>();
		parameterObject.put("codeId", codeId);
		return nlipController.getNlipService().select("oracle.master.selectCode", parameterObject); 
	}
	
    /**
     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
     * 
     * @param
     * @return Timestamp 값
     * @exception MyException
     * @see
     */
    public static String getTimeStamp() {

		String rtnStr = null;
	
		// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";
	
		try {
		    SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
		    Timestamp ts = new Timestamp(System.currentTimeMillis());
	
		    rtnStr = sdfCurrent.format(ts.getTime());
		} catch (Exception e) {
			LogFactory.getLog(CommonUtil.class).error(e);
		}
	
		return rtnStr;
	}
    
    /**
     * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
     * @param object 원본 객체
     * @return resultVal 문자열
     */
    public static String isNullToString(Object object) {
        String string = "";
        
        if (object != null) {
            string = object.toString().trim();
        }
        
        return string;
    }   	

	public static int GET_DIR_TYPE_CLASS = 1; // class 폴더
	public static int GET_DIR_TYPE_NLIP = 2; // nlip 폴더 
    public static final String RELATIVE_PATH_PREFIX_CLASS = PropertiesMgr.class.getResource("").getPath().substring(0, PropertiesMgr.class.getResource("").getPath().lastIndexOf("classes"));
	public static final String RELATIVE_PATH_PREFIX_NLIP  = PropertiesMgr.class.getResource("").getPath().substring(0, PropertiesMgr.class.getResource("").getPath().lastIndexOf("nlip"));
    
    /**
     * 서버의 물리 경로를 찾아 문자열 리턴 
     * @param getDirType 물리 경로 타입 
     * @return 찾아낸경로
     */
	public static String getDir(int getDirType){
    	if(getDirType == GET_DIR_TYPE_CLASS){
    		return RELATIVE_PATH_PREFIX_CLASS.replaceAll("%20", " ");
    	}else if(getDirType == GET_DIR_TYPE_NLIP){
    		return RELATIVE_PATH_PREFIX_NLIP.replaceAll("%20", " ");
    	}
    	return null;
    }
}
