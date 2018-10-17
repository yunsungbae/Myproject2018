package egovframework.example.cmmn.web;

import egovframework.example.cmmn.service.NlipService;
//import egovframework.nlip.common.service.NlipService;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springmodules.validation.commons.DefaultBeanValidator;

import javax.annotation.Resource;


@Controller
public class NlipController {

	// 로그를 찍기 위한 객체
	protected Log log = LogFactory.getLog(this.getClass());

	// 공통 서비스에 접근하기 위한 객체
	@Resource(name="nlipService")
    private NlipService nlipService;
	
	/**
	 * 메시지에 접근하기 위한 객체
	 */
	@Autowired  
    private MessageSource messageSource;
	
    /*
     * 지정된 properties에 접근하기 위한 Service 
     */
	@Resource(name = "propertiesService")
    protected EgovPropertyService propertiesService;

    /*
     * 확인 해봐야함 
     */
	@Resource(name = "beanValidator")
	protected DefaultBeanValidator beanValidator;
    
	/**
	 * 지정된 properties파일에서 key 값으로 value의 값을 가져옴
	 * @param key properties의 key 값 
	 * @return properties에서 key로 가져온 value 값
	 */
	public String getProperty(String key){
		return propertiesService.getString(key);
	}
	
	/**
	 * MessageSource 객체를 넘겨줌
	 * @return MessageSource 객체
	 */
	public MessageSource getMessageSource(){
		return messageSource;
	}

	/**
	 * NlipService 객체를 넘겨줌
	 * @return NlipService 객체
	 */
	public NlipService getNlipService(){
		return nlipService;
	}
}
