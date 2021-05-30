package kr.happyjob.study.system.controller;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;
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
	private String fileRelativePath;
	
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
	  // 1(고객)
	  // 2(직원) >> E = SCM, F = PCS, F = DLV 
	  int auth;
	  
	  switch(userType) {
	    case "E" :
	    case "F" :
	    case "G" :
	      auth = 2;
	      break;
	      
	    case "J" :
	      auth = 1;
	      break;
	      
	     default:
	      auth = 0;
	      break;
	  }
	  
	  param.put("auth", auth);
	  
	  // 검색어 유무 확인
	  if(!param.containsKey("option")) {
	    totalCount = noticeService.countNoticeList(auth);
	  }
	  else {
	    
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
	 
	  MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
	  
	  int auth = Integer.parseInt((String) param.get("auth"));
	  
	  param.put("auth", auth);
	  
	  System.out.println("파라미터확인" + param);
	  
	  int result = 0;
	  
	  // 첨부파일이 있을 경우
	  if(!param.containsKey("isFile")) {
	    
	    // file_no 조회
	    int file_no = noticeService.selectFileNo();
	    
	    String imgPath = noticePath + File.separator + file_no + File.separator;
	    FileUtilCho fileUtil = new FileUtilCho(multipartHttpServletRequest, rootPath, imgPath);
	    Map<String, Object> fileUtilModel = fileUtil.uploadFiles();
	    
	    String delimiter = "/";
	    String file_ofname = (String) fileUtilModel.get("file_nm");
	    String file_local_path = (String) fileUtilModel.get("file_loc");
	    String file_size = (String) fileUtilModel.get("file_size");
	    String file_relative_path = fileRelativePath + delimiter + noticePath + delimiter + file_no + delimiter + file_ofname;
	    
	    // DB에 등록할 파일 정보
	    param.put("file_no", file_no);
	    param.put("file_local_path", file_local_path);
	    param.put("file_relative_path", file_relative_path);
	    param.put("file_ofname", file_ofname);
	    param.put("file_size", file_size);
	    
	    // DB에 파일  등록  
	    int fileResult = noticeService.insertFile(param);
	    result = noticeService.insertNotice(param);
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
//	  public NoticeModel selectDetailNotice(@RequestParam Map<String, Object> notice_id) {
	  
	  int notice_id = Integer.parseInt((String) param.get("notice_id"));
	  
	  // 조회수 증가
	  int updateViewCount = noticeService.updateViewCount(param);
	  
	  NoticeModel notice;
	  
	  if(updateViewCount == 1) {
	    notice = noticeService.selectNoticeDetail(notice_id);
	  } else {
	    log.info("조회수 증가 실패");
	    return null;
	  }
	  
	  return notice;
	}
	
	/* 공지사항 수정 */
	@ResponseBody
	@RequestMapping(value="modifyNotice.do", method=RequestMethod.POST)
	public int updateNotice(@RequestParam Map<String, Object> param, HttpServletRequest request) throws Exception {
	  MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;

	  int auth = Integer.parseInt((String)param.get("auth"));
	  param.put("auth", auth);
	  
	  int result = 0;
	  // 첨부파일이 없다가 새로 등록되는 경우는 신규등록과 같은 절차를 거쳐야 한다
	  // 첨부파일이 없는 경우에는 file_no == 0
	  int file_no = Integer.parseInt((String)param.get("file_no"));
	  int notice_id = Integer.parseInt((String)param.get("notice_id"));
	  String file_nm = (String)param.get("file_nm");
	  
	  System.out.println("file_no확인" + file_no);
	  
	  String imgPath =  noticePath + File.separator + file_no + File.separator;
	  
	  FileUtilCho fileUtil = new FileUtilCho(multipartHttpServletRequest, rootPath, imgPath);
	  
	  // 첨부파일의 존재유무 확인
	  if(param.containsKey("isFile")) { // 글만 수정되는 경우
	    result = noticeService.updateNotice(param);
	  }
	  else if(param.containsKey("deleted")) {
	    // 기존 첨부파일 삭제  + 글수정
	    
	    System.out.println("삭제!");
	    
	    System.out.println("file_nm확인" + file_nm);
	    imgPath = rootPath + File.separator + imgPath;
	    // DB에서 파일 삭제
	    int deleteResult = noticeService.deleteFile(file_no);
	    
	    // 물리경로에서 파일 삭제
	    fileUtil.deleteFiles(param);
	    if(deleteResult == 1) {
  	    if (file_nm != null && !"".equals(file_nm)) {
         //File file = new File(rootFilePath + file_nm);
  	      File file = new File(imgPath + file_nm);
          if (file.exists()) file.delete();
          //
          result = 1;
          return result;
        }
	    }
	    else {
	      // 파일 정보 삭제 실패
	      return result;
	    } 
	  }
	  else if(param.containsKey("modified")|| param.containsKey("added")) { // 첨부파일 수정 + 글수정
	   
        }// 기존 파일 삭제 끝
      }// 파일 신규등록 성공 끝
      else {//파일 신규등록 실패
        //result = 0;
        return result;
      }
	   }
	  
	  return result; // 이건 또 뭐지
	}
	
	/* 공지사항 삭제 */
	@ResponseBody
	@RequestMapping(value="deleteNotice.do", method=RequestMethod.POST)
	public int deleteNotice(@RequestParam Map<String, Object> param) throws Exception {
	  
	  int result = noticeService.deleteNotice(param);
	  
	  return result;
	}
	
}
