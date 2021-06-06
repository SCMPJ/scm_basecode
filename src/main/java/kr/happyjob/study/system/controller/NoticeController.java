package kr.happyjob.study.system.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.happyjob.study.common.comnUtils.FileUtilCho;
import kr.happyjob.study.system.model.NoticeModel;
import kr.happyjob.study.system.service.NoticeService;

@Controller
@RequestMapping("/system/")
public class NoticeController {
  
  @Autowired
  NoticeService noticeService;
  
  // 파일 업로드에 사용 될 property
  // 물리경로(상위)
  @Value("${fileUpload.rootPath}")
  private String rootPath;
  
  // 물리경로(하위)-공지사항 이미지 저장용 폴더
  @Value("${fileUpload.noticePath}")
  private String noticePath;
  
  // 상대경로
  @Value("${fileUpload.noticeRelativePath}")
  private String noticeRelativePath;
  
  // logger
  private final Logger log = LogManager.getLogger(this.getClass());

  
  // 공지사항 화면
  @RequestMapping(value="notice.do", method=RequestMethod.GET)
    public String notice() throws Exception {
    
    return "system/notice";
  }
  
  /* 공지사항 목록 조회(기본,검색) */
  @RequestMapping(value="notice.do", method=RequestMethod.POST)
  public String selectNotice(@RequestParam(required = false) Map<String, Object> param, Model model, HttpSession session)throws Exception {
    
    log.info("notice.do - Param -" + param);
    // 현재 페이지 번호
    int currentPage = Integer.parseInt((String) param.get("currentPage"));
    
    // 한 페이지에 보일 로우의 개수
    int pageSize = Integer.parseInt((String)param.get("pageSize"));
    
    // 페이지 시작 로우 번호
    int pageIndex = (currentPage - 1) * pageSize;
    
    param.put("pageIndex", pageIndex);
    param.put("pageSize", pageSize);
    
    // 총 로우의 개수
    int totalCount;
    String userType = (String) session.getAttribute("userType");
    
    // 권한 설명
    // 0(전체)
    // 1(직원)  E = SCM, F = PCS, F = DLV 
    int auth;
    
    switch(userType) {
      case "E" :
      case "F" :
      case "G" :
        auth = 1;
        break;
        
       default:
        auth = 0;
        break;
    }
    
    param.put("auth", auth);
    // 검색어 유무 확인
    if(param.containsKey("option")) {
      String option = (String) param.get("option");
      String keyword = (String) param.get("keyword");
      String formerDate = (String) param.get("formerDate");
      String latterDate = (String) param.get("latterDate");
      
      param.put("option", option);
      param.put("keyword", keyword);
      param.put("formerDate", formerDate);
      param.put("latterDate", latterDate);
      
      totalCount = noticeService.countConditionList(param);
    }
    else {
      // 검색어가 없는 경우     
      totalCount = noticeService.countNoticeList(auth);
    }
    
    List<NoticeModel> noticeList = noticeService.selectNoticeList(param); 
    
    model.addAttribute("noticeList", noticeList);
    model.addAttribute("totalCount", totalCount);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("currentPage", currentPage);
    
    return "/system/noticeList";
  }
  
  
  /* 공지사항 작성 */
  @ResponseBody
  @RequestMapping(value="writeNotice.do", method=RequestMethod.POST)
  public int insertNotice(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
    
    log.info("writeNotice.do - Param -" + param);
    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
    
    int auth = Integer.parseInt((String) param.get("auth"));
    
    param.put("auth", auth);
    
    int result = 0;
    
    // 첨부파일이 있을 경우
    if(!param.containsKey("isFile")) {
      
      // file_no 조회
      int fileNo = noticeService.selectFileNo();
      
      String imgPath = noticePath + File.separator + fileNo + File.separator;
      FileUtilCho fileUtil = new FileUtilCho(multipartHttpServletRequest, rootPath, imgPath);
      Map<String, Object> fileUtilModel = fileUtil.uploadFiles();
      
      String delimiter = "/";
      String randomId = (String) fileUtilModel.get("random_id");
      String fileOfName = (String) fileUtilModel.get("file_nm");
      fileOfName = randomId + fileOfName;
      String fileLocalPath = (String) fileUtilModel.get("file_loc");
      String fileSize = (String) fileUtilModel.get("file_size");
      String fileRelativePath = noticeRelativePath + delimiter + noticePath + delimiter + fileNo + delimiter + fileOfName;
     
      log.info("uploadedFile -" + fileRelativePath);
      
      // DB에 등록할 파일 정보
      param.put("file_no", fileNo);
      param.put("file_local_path", fileLocalPath);
      param.put("file_relative_path", fileRelativePath);
      param.put("file_ofname", fileOfName);
      param.put("file_size", fileSize);
      
      // DB에 파일  등록  
      int fileResult = noticeService.insertFile(param);
      
      if(fileResult == 1) {
        result = noticeService.insertNotice(param);
      }
      else result = 0;
    }
      // 첨부파일이 없을 경우
      // 공지사항만 등록 
    else  result = noticeService.insertNotice(param);
   
    return result;
  }
  
  /* 공지사항 단건 조회 */
  @ResponseBody
  @RequestMapping(value="detailNotice.do", method=RequestMethod.POST)
  public NoticeModel selectDetailNotice(@RequestParam Map<String, Object> param) throws Exception {
    
    log.info("detailNotice.do - Param -" + param);
    int notice_id = Integer.parseInt((String) param.get("notice_id"));
    
    // 조회수 증가
    int updateViewCount = noticeService.updateViewCount(param);
    
    NoticeModel notice;
    
    if(updateViewCount == 1) {
      notice = noticeService.selectNoticeDetail(notice_id);
    } else {
      notice = null;
    }
    
    return notice;
  }
  
  /* 공지사항 수정 */
  @ResponseBody
  @RequestMapping(value="modifyNotice.do", method=RequestMethod.POST)
  public int updateNotice(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
   
    log.info("modifyNotice.do - Param -" + param);
    MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
    int auth = Integer.parseInt((String)param.get("auth"));
    param.put("auth", auth);
    
    int result = 0;
    // 첨부파일이 없다가 새로 등록되는 경우는 신규등록과 같은 절차를 거쳐야 한다
    int fileNo;
    String fileName = (String)param.get("file_nm");
    
    // 첨부파일의 존재유무 확인
    if(param.containsKey("noFile")) { // 글만 수정되는 경우
      result = noticeService.updateNotice(param);
    }
    else if(param.containsKey("deleted")) {
      // 기존 첨부파일 삭제  + 글수정
      fileNo = Integer.parseInt((String)param.get("file_no"));
      
      String imgPath = rootPath + File.separator + noticePath + File.separator + fileNo + File.separator;
      FileUtilCho fileUtil = new FileUtilCho(multipartHttpServletRequest, rootPath, imgPath);
      
      // 글 업데이트
      int updateResult = noticeService.updateNotice(param);
      
      if(updateResult == 1) {
        // DB에서 파일 삭제
        int deleteResult = noticeService.deleteFile(fileNo);
        
        // 물리경로에서 파일 삭제
        fileUtil.deleteFiles(param);
        if(deleteResult == 1) {
          if (fileName != null && !"".equals(fileName)) {
            File file = new File(imgPath + fileName);
            File folder = new File(imgPath);
            if (file.exists()) file.delete();
            if (folder.exists()) folder.delete();
            log.info("deletedFile" + fileName);
            result = 1;
          }
        }
        else result = 0;
      }
      else result = 0;
    }
    else if(param.containsKey("modified")|| param.containsKey("added")) { // 첨부파일 수정 + 글수정
      // 첨부파일 신규등록 || 첨부파일 수정
      // 기존 파일 번호
      fileNo = Integer.parseInt((String)param.get("file_no"));
      int formerFileNo = fileNo;
      
      // 신규파일 등록을 위한 파일번호
      fileNo = noticeService.selectFileNo();
      
      String imgPath =  noticePath + File.separator + fileNo + File.separator;
      FileUtilCho fileUtil = new FileUtilCho(multipartHttpServletRequest, rootPath, imgPath);
      
      Map<String, Object> fileUtilModel = fileUtil.uploadFiles();
      
      String delimiter = "/";
      String randomId = (String) fileUtilModel.get("random_id");
      String fileOfName = (String) fileUtilModel.get("file_nm");
      fileOfName = randomId + fileOfName;
      String fileLocalPath = (String) fileUtilModel.get("file_loc");
      String fileSize = (String) fileUtilModel.get("file_size");
      String fileRelativePath = noticeRelativePath + delimiter + noticePath + delimiter + fileNo + delimiter + fileOfName;
      
      // DB에 등록할 파일 정보
      param.put("file_no", fileNo);
      param.put("file_local_path", fileLocalPath);
      param.put("file_relative_path", fileRelativePath);
      param.put("file_ofname", fileOfName);
      param.put("file_size", fileSize);
      
      // DB에 신규 파일  등록  
      int fileResult = noticeService.insertFile(param);
      
      // 파일 신규 등록에 성공한 경우 공지사항 글 업데이트
      if(fileResult == 1) {
        // 공지사항 정보 업데이트
        result = noticeService.updateNotice(param);
        
        // 기존 파일 삭제
        if(formerFileNo != 0) {
          imgPath = rootPath + File.separator + noticePath + File.separator + formerFileNo + File.separator;
          //db에서 삭제
          int deleteResult = noticeService.deleteFile(formerFileNo);
          
          // 물리경로에서 파일 삭제
          if(deleteResult == 1) {
            if (fileName != null && !"".equals(fileName)) {
              File file = new File(imgPath + fileName);
              File folder = new File(imgPath);
              if (file.exists()) file.delete();
              if (folder.exists()) folder.delete();
              log.info("deletedFile -" + fileName);
              result = 1;
            }
          }
        }// 기존 파일 삭제 끝
      }// 파일 신규등록 성공 끝
     }
    return result; 
  }
  
  /* 공지사항 삭제 */
  @ResponseBody
  @RequestMapping(value="deleteNotice", method=RequestMethod.POST)
  public int deleteNotice(@RequestParam Map<String, Object> param) throws Exception {
    
    log.info("deleteNotice.do - Param -" + param);
    int result = 0;
    int noticeResult = noticeService.deleteNotice(param);
    
    if(noticeResult == 1) {
      
      // 파일 db에서삭제
      int fileNo = Integer.parseInt((String)param.get("file_no"));
      noticeService.deleteFile(fileNo);
      // 파일 물리에서삭제
      String imgPath = rootPath + File.separator + noticePath + File.separator + fileNo + File.separator;
      String fileName = (String)param.get("file_nm");

      if (fileName != null && !"".equals(fileName)) {
          File file = new File(imgPath + fileName);
          File folder = new File(imgPath);
          if (file.exists()) file.delete();
          if (folder.exists()) folder.delete();
          log.info("deletedFile -" + fileName);
      }
      result = 1;
    }
    return result;
  }
}