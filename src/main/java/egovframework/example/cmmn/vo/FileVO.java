package egovframework.example.cmmn.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * @Class Name : FileVO.java
 * @Description : 파일정보 처리를 위한 VO 클래스
 * @Modification Information
 *
 *    수정일       수정자         수정내용
 *    -------        -------     -------------------
 *    2015. 10. 02.     이세진
 *
 * @author 이세진
 * @since 2015. 10. 02.
 * @version
 * @see
 *
 */
@SuppressWarnings("serial")
public class FileVO implements Serializable {

    /**
     * 첨부파일 아이디
     */
    public String fileId = "";
    /**
     * 저장 파일 명
     */
    public String streFileNm = "";
    /**
     * 저장 경로 명
     */
    public String streCoursNm = "";
    /**
     *원본 파일 명
     */
    public String orginlFileNm = "";
    /**
     * 파일크기
     */
    public String fileMg = "";

    /**
     * 다운로드수
     */
    public String dwldCo = "";
    /**
     * 파일순번
     */
    public String fileSn = "";
    /**
     * 파일 확장자 내용
     */
    public String fileExtsnCn = "";

    public String authorInfoId = "";
    public String crtrId = "";
    public String creatDt = "";

    public String inputFileTypeName;



    public String getFileId() {
        return fileId;
    }



    public void setFileId(String fileId) {
        this.fileId = fileId;
    }



    public String getStreFileNm() {
        return streFileNm;
    }



    public void setStreFileNm(String streFileNm) {
        this.streFileNm = streFileNm;
    }



    public String getStreCoursNm() {
        return streCoursNm;
    }



    public void setStreCoursNm(String streCoursNm) {
        this.streCoursNm = streCoursNm;
    }



    public String getOrginlFileNm() {
        return orginlFileNm;
    }



    public void setOrginlFileNm(String orginlFileNm) {
        this.orginlFileNm = orginlFileNm;
    }



    public String getFileMg() {
        return fileMg;
    }



    public void setFileMg(String fileMg) {
        this.fileMg = fileMg;
    }



    public String getDwldCo() {
        return dwldCo;
    }



    public void setDwldCo(String dwldCo) {
        this.dwldCo = dwldCo;
    }



    public String getFileSn() {
        return fileSn;
    }



    public void setFileSn(String fileSn) {
        this.fileSn = fileSn;
    }



    public String getFileExtsnCn() {
        return fileExtsnCn;
    }



    public void setFileExtsnCn(String fileExtsnCn) {
        this.fileExtsnCn = fileExtsnCn;
    }




    public String getAuthorInfoId() {
        return authorInfoId;
    }



    public void setAuthorInfoId(String authorInfoId) {
        this.authorInfoId = authorInfoId;
    }



    public String getCrtrId() {
        return crtrId;
    }



    public void setCrtrId(String crtrId) {
        this.crtrId = crtrId;
    }



    public String getCreatDt() {
        return creatDt;
    }



    public void setCreatDt(String creatDt) {
        this.creatDt = creatDt;
    }


    public String getInputFileTypeName() {
        return inputFileTypeName;
    }



    public void setInputFileTypeName(String inputFileTypeName) {
        this.inputFileTypeName = inputFileTypeName;
    }



    /**
     * toString 메소드를 대치한다.
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
