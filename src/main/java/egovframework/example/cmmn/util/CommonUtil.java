package egovframework.example.cmmn.util;


import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class CommonUtil implements NameConstants{

	/**
	 * 화면에서 사용할 메시지 코드와 인자값을 map에 넣음
	 * @param nlipController NlipController
	 * @param modelMap map
	 * @param msgCd 메시지 코드
	 * @param 메시지 사용 인자값
	 */
//	public static void setMsgCD(NlipController nlipController, ModelMap modelMap, String msgCd, String... paramArr){
//		setMsgCD(nlipController, modelMap, msgCd);
//		if(paramArr != null && paramArr.length > 0){
//			modelMap.addAttribute(NM_RNT_MSG_PARAM_LENGTH, paramArr.length);
//			for (int i=0, objectSize=paramArr.length; i<objectSize; i++) {
//				modelMap.addAttribute(NM_RNT_MSG_CD+i, paramArr[i]);
//			}
//		}
//	}


	
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

}
