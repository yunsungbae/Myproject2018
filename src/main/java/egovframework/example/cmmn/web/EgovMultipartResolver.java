package egovframework.example.cmmn.web;


import org.apache.commons.fileupload.FileItem;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletContext;
import java.io.UnsupportedEncodingException;
import java.util.*;


public class EgovMultipartResolver extends CommonsMultipartResolver {
    public EgovMultipartResolver() {
    }


    /**
     * 첨부파일 처리를 위한 multipart resolver를 생성한다.
     *
     * @param servletContext
     */
    public EgovMultipartResolver(ServletContext servletContext) {
	super(servletContext);
    }

    /**
     * multipart에 대한 parsing을 처리한다.
     */
    @SuppressWarnings("unchecked")
    @Override
    protected MultipartParsingResult parseFileItems(List fileItems, String encoding) {
    	int index = 1;
    //스프링 3.0변경으로 수정한 부분
    MultiValueMap<String, MultipartFile> multipartFiles = new LinkedMultiValueMap<String, MultipartFile>();
	Map<String, String[]> multipartParameters = new HashMap<String, String[]>();

	// Extract multipart files and multipart parameters.
	int i =0;
	for (Iterator it = fileItems.iterator(); it.hasNext();) {
	    FileItem fileItem = (FileItem)it.next();

	    if (fileItem.isFormField()) {

		String value = null;
		if (encoding != null) {
		    try {
			value = fileItem.getString(encoding);
		    } catch (UnsupportedEncodingException ex) {
			if (logger.isWarnEnabled()) {
			    logger.warn("Could not decode multipart item '" + fileItem.getFieldName() + "' with encoding '" + encoding
				    + "': using platform default");
			}
			value = fileItem.getString();
		    }
		} else {
		    value = fileItem.getString();
		}
		String[] curParam = (String[])multipartParameters.get(fileItem.getFieldName());
		if (curParam == null) {
		    // simple form field
		    multipartParameters.put(fileItem.getFieldName(), new String[] { value });
		} else {
		    // array of simple form fields
		    String[] newParam = StringUtils.addStringToArray(curParam, value);
		    multipartParameters.put(fileItem.getFieldName(), newParam);
		}
	    } else {
		if (fileItem.getSize() > 0) {
		    // multipart file field
			String[] size =  (String[])multipartParameters.get("posblAtchFileSize");
			if(size != null && size.length > 0 ) {
				//for(int i=0; i < size.length; i++ ) {
					int maxSize = Integer.parseInt(size[i]);
					logger.debug("maxSize-->" + size[i]);
					logger.debug("fileItem.getSize()" + fileItem.getSize());
					if(fileItem.getSize() > maxSize) {
						throw new MultipartException("파일 용량 초과~~~~~" );
					}
				//}
			}
		    CommonsMultipartFile file = new CommonsMultipartFile(fileItem);


		    //스프링 3.0 업그레이드 API변경으로인한 수정
		    List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		    fileList.add(file);


		    if (multipartFiles.put(fileItem.getName(), fileList) != null) { // CHANGED!!
		    	multipartFiles.put(fileItem.getName(), fileList);
			//throw new MultipartException("Multiple files for field name [" + file.getName()+ "] found - not supported by MultipartResolver");
		    }
		    if (logger.isDebugEnabled()) {
			logger.debug("Found multipart file [" + file.getName() + "] of size " + file.getSize() + " bytes with original filename ["
				+ file.getOriginalFilename() + "], stored " + file.getStorageDescription());
		    }
		    i++;
		}

	    }
	}



	return new MultipartParsingResult(multipartFiles, multipartParameters, null);
    }
}
