package egovframework.example.cmmn.service.impl;


import egovframework.example.cmmn.util.CommonUtil;
import egovframework.example.cmmn.vo.FileVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

//import java.util.HashMap;


@Component("EgovFileMngUtil")
public class EgovFileMngUtil {

    public static final int BUFF_SIZE = 66560;

    @Resource(name = "propertiesService")
    protected EgovPropertyService propertyService;

    @Resource(name = "egovIdGnrService")
    private EgovIdGnrService idgenService;


    @Resource(name="primaryTypeSequenceIds")
    private EgovIdGnrService primaryTypeSequenceIds;


    private static final Logger LOG = Logger.getLogger(EgovFileMngUtil.class.getName());

    private boolean extCheck(String ext){

    	String[] extName ={"doc","docs","xls","xlsx","ppt","pptx","hwp","jpg","gif","png","tif","bmp","pdf","zip"};
    	for(int i=0; i<extName.length; i++){
    		if(extName[i].equals(ext)){
    			return true;
    		}
    	}
    	return false;
    }

    /**
     * 첨부파일에 대한 목록 정보를 취득한다.
     *
     * @param files
     * @return
     * @throws Exception
     */
    public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath, String authorInfoId, String crtrId) throws Exception {
	int fileKey = fileKeyParam;

	String storePathString = "";
	String atchFileIdString = "";
	if ("".equals(storePath) || storePath == null) {
	    //storePathString = propertyService.getString("Globals.fileStorePath");
	} else {
	    //storePathString = propertyService.getString(storePath);
	}
	storePathString= propertyService.getString("global.file.dir.base") + propertyService.getString("global.file.dir.upload");

	if ("".equals(atchFileId) || atchFileId == null) {
	    //atchFileIdString = idgenService.getNextStringId();
		//atchFileIdString = uUIdGenerationServiceWithoutAddress.getNextStringId();
		atchFileIdString = primaryTypeSequenceIds.getNextIntegerId()+"";
	} else {
	    atchFileIdString = atchFileId;
	}

	File saveFolder = new File(storePathString);

	if (!saveFolder.exists() || saveFolder.isFile()) {
	    saveFolder.mkdirs();
	}

	Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
	MultipartFile file;
	String filePath = "";
	List<FileVO> result  = new ArrayList<FileVO>();
	FileVO fvo;

	while (itr.hasNext()) {
	    Entry<String, MultipartFile> entry = itr.next();

	    file = entry.getValue();
	    String orginFileName = file.getOriginalFilename();

	    //--------------------------------------
	    // 원 파일명이 없는 경우 처리
	    // (첨부가 되지 않은 input file type)
	    //--------------------------------------
	    if ("".equals(orginFileName)) {
		continue;
	    }
	    ////------------------------------------

	    int index = orginFileName.lastIndexOf(".");
	    //String fileName = orginFileName.substring(0, index);
	    String fileExt = orginFileName.substring(index + 1);
	    String newName = KeyStr + CommonUtil.getTimeStamp() + fileKey;
	    long _size = file.getSize();

	    fileExt = fileExt.toLowerCase();

		if(!extCheck(fileExt)) {
			throw new Exception("허용되지 않은 파일 확장자입니다");
		}

	    if (!"".equals(orginFileName)) {
		filePath = storePathString + File.separator + newName;
		file.transferTo(new File(filePath));
	    }
	    fvo = new FileVO();
	    fvo.setFileExtsnCn(fileExt);
	    fvo.setStreCoursNm(storePathString);
	    fvo.setFileMg(Long.toString(_size));
	    fvo.setOrginlFileNm(orginFileName);
	    fvo.setStreFileNm(newName);
	    fvo.setFileId(atchFileIdString);
	    fvo.setFileSn(String.valueOf(fileKey));
	    fvo.setAuthorInfoId(authorInfoId);
	    fvo.setCrtrId(crtrId);

	    //writeFile(file, newName, storePathString);
	    result.add(fvo);

	    fileKey++;
	}

	return result;
    }

    /**
     * 첨부파일을 서버에 저장한다.
     *
     * @param file
     * @param newName
     * @param stordFilePath
     * @throws Exception
     */
    protected void writeUploadedFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
	InputStream stream = null;
	OutputStream bos = null;
	String stordFilePathReal = (stordFilePath==null?"":stordFilePath).replaceAll("..","");
	try {
	    stream = file.getInputStream();
	    File cFile = new File(stordFilePathReal);

	    if (!cFile.isDirectory()) {
		boolean _flag = cFile.mkdir();
		if (!_flag) {
		    throw new IOException("Directory creation Failed ");
		}
	    }

	    bos = new FileOutputStream(stordFilePathReal + File.separator + newName);

	    int bytesRead = 0;
	    byte[] buffer = new byte[BUFF_SIZE];

	    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
		bos.write(buffer, 0, bytesRead);
	    }
	} catch (FileNotFoundException fnfe) {
		LOG.debug("fnfe:"+fnfe);//fnfe.printStackTrace();
	} catch (IOException ioe) {
		LOG.debug("ioe:"+ioe);//ioe.printStackTrace();
	} catch (Exception e) {
		LOG.debug("e:"+e);e.printStackTrace();
	} finally {
	    if (bos != null) {
		try {
		    bos.close();
		} catch (Exception ignore) {
			LOG.debug("IGNORED: " + ignore.getMessage());
		}
	    }
	    if (stream != null) {
		try {
		    stream.close();
		} catch (Exception ignore) {
			LOG.debug("IGNORED: " + ignore.getMessage());
		}
	    }
	}
    }

    /**
     * 서버의 파일을 다운로드한다.
     *
     * @param request
     * @param response
     * @throws Exception
     */
    public static void downFile(HttpServletRequest request, HttpServletResponse response) throws Exception {

    String downFileName = CommonUtil.isNullToString(request.getAttribute("downFile")).replaceAll("..","");
    String orgFileName = CommonUtil.isNullToString(request.getAttribute("orgFileName")).replaceAll("..","");

	/*if ((String)request.getAttribute("downFile") == null) {
	    downFileName = "";
	} else {
	    downFileName = EgovStringUtil.isNullToString(request.getAttribute("downFile"));
	}*/

	/*if ((String)request.getAttribute("orgFileName") == null) {
	    orgFileName = "";
	} else {
	    orgFileName = (String)request.getAttribute("orginFile");
	}*/

	File file = new File(downFileName);

	if (!file.exists()) {
	    throw new FileNotFoundException(downFileName);
	}

	if (!file.isFile()) {
	    throw new FileNotFoundException(downFileName);
	}

	byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.
    String fName = (new String(orgFileName.getBytes(), "UTF-8")).replaceAll("\r\n","");
	response.setContentType("application/x-msdownload");
	response.setHeader("Content-Disposition:", "attachment; filename=" + fName);
	response.setHeader("Content-Transfer-Encoding", "binary");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Expires", "0");

	BufferedInputStream fin = null;
	BufferedOutputStream outs = null;

	try {
		fin = new BufferedInputStream(new FileInputStream(file));
	    outs = new BufferedOutputStream(response.getOutputStream());
	    int read = 0;

		while ((read = fin.read(b)) != -1) {
		    outs.write(b, 0, read);
		}
	} finally {
	    if (outs != null) {
			try {
			    outs.close();
			} catch (Exception ignore) {
				LOG.debug("IGNORED: " + ignore.getMessage());
			}
		    }
		    if (fin != null) {
			try {
			    fin.close();
			} catch (Exception ignore) {
				LOG.debug("IGNORED: " + ignore.getMessage());
			}
		    }
		}
    }

    /**
     * 첨부로 등록된 파일을 서버에 업로드한다.
     *
     * @param file
     * @return
     * @throws Exception

    public static HashMap<String, String> uploadFile(MultipartFile file) throws Exception {

	HashMap<String, String> map = new HashMap<String, String>();
	//Write File 이후 Move File????
	String newName = "";
	String stordFilePath = EgovProperties.getProperty("Globals.fileStorePath");
	String orginFileName = file.getOriginalFilename();

	int index = orginFileName.lastIndexOf(".");
	//String fileName = orginFileName.substring(0, _index);
	String fileExt = orginFileName.substring(index + 1);
	long size = file.getSize();

	//newName 은 Naming Convention에 의해서 생성
	newName = EgovStringUtil.getTimeStamp() + "." + fileExt;
	writeFile(file, newName, stordFilePath);
	//storedFilePath는 지정
	map.put(Globals.ORIGIN_FILE_NM, orginFileName);
	map.put(Globals.UPLOAD_FILE_NM, newName);
	map.put(Globals.FILE_EXT, fileExt);
	map.put(Globals.FILE_PATH, stordFilePath);
	map.put(Globals.FILE_SIZE, String.valueOf(size));

	return map;
    }
*/
    /**
     * 파일을 실제 물리적인 경로에 생성한다.
     *
     * @param file
     * @param newName
     * @param stordFilePath
     * @throws Exception
     */
    protected static void writeFile(MultipartFile file, String newName, String stordFilePath) throws Exception {
	InputStream stream = null;
	OutputStream bos = null;
	newName = CommonUtil.isNullToString(newName).replaceAll("..", "");
	stordFilePath = CommonUtil.isNullToString(stordFilePath).replaceAll("..", "");
	try {
	    stream = file.getInputStream();
	    File cFile = new File(stordFilePath);

	    if (!cFile.isDirectory())
		cFile.mkdir();

	    bos = new FileOutputStream(stordFilePath + File.separator + newName);

	    int bytesRead = 0;
	    byte[] buffer = new byte[BUFF_SIZE];

	    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
		bos.write(buffer, 0, bytesRead);
	    }
	} catch (FileNotFoundException fnfe) {
		LOG.debug("fnfe:"+fnfe);//fnfe.printStackTrace();
	} catch (IOException ioe) {
		LOG.debug("ioe:"+ioe);//ioe.printStackTrace();
	} catch (Exception e) {
		LOG.debug("e:"+e);//e.printStackTrace();
	} finally {
	    if (bos != null) {
		try {
		    bos.close();
		} catch (Exception ignore) {
		    Logger.getLogger(EgovFileMngUtil.class).debug("IGNORED: " + ignore.getMessage());
		}
	    }
	    if (stream != null) {
		try {
		    stream.close();
		} catch (Exception ignore) {
		    Logger.getLogger(EgovFileMngUtil.class).debug("IGNORED: " + ignore.getMessage());
		}
	    }
	}
    }

    /**
     * 서버 파일에 대하여 다운로드를 처리한다.
     *
     * @param response
     * @param streFileNm
     *            : 파일저장 경로가 포함된 형태
     * @param orignFileNm
     * @throws Exception
     */
    public void downFile(HttpServletResponse response, String streFileNm, String orignFileNm) throws Exception {
    //	String downFileName = EgovStringUtil.isNullToString(request.getAttribute("downFile")).replaceAll("..","");
    //	String orgFileName = EgovStringUtil.isNullToString(request.getAttribute("orgFileName")).replaceAll("..","");
    String downFileName = CommonUtil.isNullToString(streFileNm).replaceAll("..","");
	String orgFileName = CommonUtil.isNullToString(orignFileNm).replaceAll("..","");

	File file = new File(downFileName);
	//log.debug(this.getClass().getName()+" downFile downFileName "+downFileName);
	//log.debug(this.getClass().getName()+" downFile orgFileName "+orgFileName);

	if (!file.exists()) {
	    throw new FileNotFoundException(downFileName);
	}

	if (!file.isFile()) {
	    throw new FileNotFoundException(downFileName);
	}

	//byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.
	int fSize = (int)file.length();
	if (fSize > 0) {
	    BufferedInputStream in = null;

	    try {
		in = new BufferedInputStream(new FileInputStream(file));

    	    	String mimetype = "text/html"; //"application/x-msdownload"

    	    	response.setBufferSize(fSize);
		response.setContentType(mimetype);
		response.setHeader("Content-Disposition:", "attachment; filename=" + orgFileName);
		response.setContentLength(fSize);
		//response.setHeader("Content-Transfer-Encoding","binary");
		//response.setHeader("Pragma","no-cache");
		//response.setHeader("Expires","0");
		FileCopyUtils.copy(in, response.getOutputStream());
	    } finally {
		if (in != null) {
		    try {
			in.close();
		    } catch (Exception ignore) {

			Logger.getLogger(EgovFileMngUtil.class).debug("IGNORED: " + ignore.getMessage());
		    }
		}
	    }
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	}

	/*
	String uploadPath = propertiesService.getString("fileDir");

	File uFile = new File(uploadPath, requestedFile);
	int fSize = (int) uFile.length();

	if (fSize > 0) {
	    BufferedInputStream in = new BufferedInputStream(new FileInputStream(uFile));

	    String mimetype = "text/html";

	    response.setBufferSize(fSize);
	    response.setContentType(mimetype);
	    response.setHeader("Content-Disposition", "attachment; filename=\""
					+ requestedFile + "\"");
	    response.setContentLength(fSize);

	    FileCopyUtils.copy(in, response.getOutputStream());
	    in.close();
	    response.getOutputStream().flush();
	    response.getOutputStream().close();
	} else {
	    response.setContentType("text/html");
	    PrintWriter printwriter = response.getWriter();
	    printwriter.println("<html>");
	    printwriter.println("<br><br><br><h2>Could not get file name:<br>" + requestedFile + "</h2>");
	    printwriter.println("<br><br><br><center><h3><a href='javascript: history.go(-1)'>Back</a></h3></center>");
	    printwriter.println("<br><br><br>&copy; webAccess");
	    printwriter.println("</html>");
	    printwriter.flush();
	    printwriter.close();
	}
	//*/


	/*
	response.setContentType("application/x-msdownload");
	response.setHeader("Content-Disposition:", "attachment; filename=" + new String(orgFileName.getBytes(),"UTF-8" ));
	response.setHeader("Content-Transfer-Encoding","binary");
	response.setHeader("Pragma","no-cache");
	response.setHeader("Expires","0");

	BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
	BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());
	int read = 0;

	while ((read = fin.read(b)) != -1) {
	    outs.write(b,0,read);
	}
	log.debug(this.getClass().getName()+" BufferedOutputStream Write Complete!!! ");

	outs.close();
    	fin.close();
	//*/
    }
    
    /**
     * 공지사항 팝업 첨부파일에 대한 파일을 설정함.
     * @param files
     * @return
     * @throws Exception
     */
    public List<FileVO> noticePopFileInf(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath, String authorInfoId, String crtrId) throws Exception {
    	//files, "TN_NOTICEPOPUP_BBS_", 0, "", "",authorInfoId,userId
    int fileKey = fileKeyParam;
	String storePathString = "";
	String atchFileIdString = "";
	String imgPathString = "";
	if ("".equals(storePath) || storePath == null) {
	    //storePathString = propertyService.getString("Globals.fileStorePath");
	} else {
	    //storePathString = propertyService.getString(storePath);
	}
	//파일이 실제로 저장될 경로 
	storePathString= propertyService.getString("global.file.dir.popup") + propertyService.getString("global.file.dir.popload");
	
	//현재 저장되어있는 경로
	//popup=D:/workspace/nlip/src/main/webapp
	//popload=/img/uploadFile/noticeFile
	
	//지리원에 반영해야하는 경로
	//popup=D:/uploadFile
	//popload=/noticeFile
	
	
	//popload만 구분하여  db에 경로를 저장(개발용) 실질적으로 불러오려면 popup과 popload를 같이 사용해야함.
	imgPathString = propertyService.getString("global.file.dir.popload");
	
	if ("".equals(atchFileId) || atchFileId == null) {
	    //atchFileIdString = idgenService.getNextStringId();
		//atchFileIdString = uUIdGenerationServiceWithoutAddress.getNextStringId();
		atchFileIdString = primaryTypeSequenceIds.getNextIntegerId()+"";
	} else {
	    atchFileIdString = atchFileId;
	}
	
	//폴더가 없을경우 폴더 생성
	File saveFolder = new File(storePathString);

	if (!saveFolder.exists() || saveFolder.isFile()) {
	    saveFolder.mkdirs(); 
	}

	Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
	MultipartFile file;
	String filePath = "";
	List<FileVO> result  = new ArrayList<FileVO>();
	FileVO fvo;

	while (itr.hasNext()) {
	    Entry<String, MultipartFile> entry = itr.next();

	    file = entry.getValue();
	    String orginFileName = file.getOriginalFilename();

	    //--------------------------------------
	    // 원 파일명이 없는 경우 처리
	    // (첨부가 되지 않은 input file type)
	    //--------------------------------------
	    if ("".equals(orginFileName)) {
		continue;
	    }
	    ////------------------------------------

	    
	    // 파일 확장자까지 이름으로 포함.
	    int index = orginFileName.lastIndexOf(".");
	    String fileName = orginFileName.substring(orginFileName.lastIndexOf(".")); //확장자추가
	    String fileExt = orginFileName.substring(index + 1);
	    //파일 이름 변경 : 테이블명 + 타임스템프 + 0부터시작 + 확장자
	    //files, "TN_NOTICEPOPUP_BBS_", 0, "", "",authorInfoId,userId
	    String newName = KeyStr + CommonUtil.getTimeStamp() + fileKey+ fileName;
	    long _size = file.getSize();

	    fileExt = fileExt.toLowerCase();

	    
/*		adminPopupFile.js에서 확장자 문제는 해결함.
 		if(!extCheck(fileExt)) {
			throw new Exception("허용되지 않은 파일 확장자입니다");
		}*/
	    
	    if (!"".equals(orginFileName)) {
		filePath = storePathString + File.separator + newName;
		file.transferTo(new File(filePath));	//업로드 한 파일 데이터를 지정한 파일에 저장한다.
	    }
	    fvo = new FileVO();
	    fvo.setFileExtsnCn(fileExt);			//확장자
	    //fvo.setStreCoursNm(storePathString);	//물리적주소(실제저장은 storePathString) //지리원 반영 위치 주소
	    fvo.setStreCoursNm(imgPathString);		//물리적주소(db에서 바라보는 위치는 imgPathString) //개발 위치 주소
	    fvo.setFileMg(Long.toString(_size));	//용량
	    fvo.setOrginlFileNm(orginFileName);		//본이름
	    fvo.setStreFileNm(newName);				//수정이름
	    fvo.setFileId(atchFileIdString);		//id
	    fvo.setFileSn(String.valueOf(fileKey));	//파일 순서
	    fvo.setAuthorInfoId(authorInfoId);		//권한 정보 id
	    fvo.setCrtrId(crtrId);					//생성자 id

	    //writeFile(file, newName, storePathString);
	    result.add(fvo);

	    fileKey++;
	}
	return result;
    }

    
    public List<FileVO> noticePopFileInf2(Map<String, MultipartFile> files, String KeyStr, int fileKeyParam, String atchFileId, String storePath, String authorInfoId, String crtrId) throws Exception {
    	//files, "TN_NOTICEPOPUP_BBS_", 0, "", "",authorInfoId,userId
    	int fileKey = fileKeyParam;
    	String storePathString = "";
    	String atchFileIdString = "";
    	String imgPathString = "";
    	
    	if ("".equals(storePath) || storePath == null) {
    		storePathString = propertyService.getString("Globals.fileStorePath");
    	} else {
    		storePathString = storePath;
    	}
	
		imgPathString = propertyService.getString("global.file.dir.popload");
		if ("".equals(atchFileId) || atchFileId == null) {
			atchFileIdString = primaryTypeSequenceIds.getNextIntegerId()+"";
		} else {
		    atchFileIdString = atchFileId;
		}
	
		//폴더가 없을경우 폴더 생성
		File saveFolder = new File(storePathString);
	
		if (!saveFolder.exists() || saveFolder.isFile()) {
		    saveFolder.mkdirs(); 
		}

		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		String filePath = "";
		List<FileVO> result  = new ArrayList<FileVO>();
		FileVO fvo;

		while (itr.hasNext()) {
		    Entry<String, MultipartFile> entry = itr.next();
	
		    file = entry.getValue();
		    String orginFileName = file.getOriginalFilename();
	
		    //--------------------------------------
		    // 원 파일명이 없는 경우 처리
		    // (첨부가 되지 않은 input file type)
		    //--------------------------------------
		    if ("".equals(orginFileName)) {
			continue;
		    }
		    ////------------------------------------

		    // 파일 확장자까지 이름으로 포함.
		    int index = orginFileName.lastIndexOf(".");
		    String fileName = orginFileName.substring(orginFileName.lastIndexOf(".")); //확장자추가
		    String fileExt = orginFileName.substring(index + 1);
		    //파일 이름 변경 : 테이블명 + 타임스템프 + 0부터시작 + 확장자
		    //files, "TN_NOTICEPOPUP_BBS_", 0, "", "",authorInfoId,userId
		    String newName = KeyStr + CommonUtil.getTimeStamp() + fileKey+ fileName;
		    long _size = file.getSize();
	
		    fileExt = fileExt.toLowerCase();
		    /*
			adminPopupFile.js에서 확장자 문제는 해결함.
	 		if(!extCheck(fileExt)) {
				throw new Exception("허용되지 않은 파일 확장자입니다");
			}*/
		    
		    if (!"".equals(orginFileName)) {
			filePath = storePathString + File.separator + newName;
			file.transferTo(new File(filePath));	//업로드 한 파일 데이터를 지정한 파일에 저장한다.
		    }
		    fvo = new FileVO();
		    fvo.setFileExtsnCn(fileExt);			//확장자
		    //fvo.setStreCoursNm(storePathString);	//물리적주소(실제저장은 storePathString) //지리원 반영 위치 주소
		    fvo.setStreCoursNm(imgPathString);		//물리적주소(db에서 바라보는 위치는 imgPathString) //개발 위치 주소
		    fvo.setFileMg(Long.toString(_size));	//용량
		    fvo.setOrginlFileNm(orginFileName);		//본이름
		    fvo.setStreFileNm(newName);				//수정이름
		    fvo.setFileId(atchFileIdString);		//id
		    fvo.setFileSn(String.valueOf(fileKey));	//파일 순서
		    fvo.setAuthorInfoId(authorInfoId);		//권한 정보 id
		    fvo.setCrtrId(crtrId);					//생성자 id
	
		    //writeFile(file, newName, storePathString);
		    result.add(fvo);
	
		    fileKey++;
		}
	return result;
    }
    
}
