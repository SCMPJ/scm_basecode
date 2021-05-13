package kr.happyjob.study.pcs.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.happyjob.study.pcs.model.RefundModel;
import kr.happyjob.study.pcs.service.RefundService;

@Controller
@RequestMapping("/pcs/refund")
public class RefundConller {
  
  @Autowired
  RefundService refundService;
  
  // logger
  private final Logger logger = LogManager.getLogger(this.getClass());  

  // 반품 화면 
  @RequestMapping(value="/list.do", method=RequestMethod.GET)
  public String getList() {
    return "/pcs/refundList";
  }
  
  // 반품 목록 조회
  @RequestMapping(value="/list.do", method=RequestMethod.POST)
  public String getList(Model model, @RequestParam Map<String, Object> paramMap) throws Exception{
    
    logger.info("파라미터 확인:" + paramMap);
    
    // 현재 페이지 번호
    int currentPage = Integer.parseInt((String)paramMap.get("currentPage"));
    // 한 페이지에 보이 로우의 개수
    int pageSize = Integer.parseInt((String) paramMap.get("pageSize")); 
    // 페이지 시작 로우 번호
    //    int pageIndex = (currentPage - 1) * pageSize; 
    // 총 페이지 개수
    int totalCount = refundService.countRefundList();
    
    List<RefundModel> refundList = refundService.selectRefundList(paramMap);
    model.addAttribute("refundList", refundList);
    
    model.addAttribute("totalCount", totalCount);
    model.addAttribute("pageSize", pageSize);
    model.addAttribute("currentPage", currentPage);
    
    logger.info("DB에서 넘어온 데이터 확인:" + refundList);
    
    return "/pcs/refundListData";
  }

  // 반품서 단건 조회
  @ResponseBody
  @RequestMapping(value="/one.do", method=RequestMethod.POST)
  public Map<String,Object> getOneRefund(@RequestParam Map<String,Object> paramMap, Model model) throws Exception {
    logger.info("단건 파라미터 확인:" +  paramMap.get("refund_list_no"));
    
    String refund_list_no = (String) paramMap.get("refund_list_no");
    Map<String, Object> refund = refundService.selectOneRefund(refund_list_no);
    logger.info("단건 조회내역 확인" + refund);
    
    Map<String, Object> result = new HashMap<String,Object>();
    result.put("refund", refund);
    
    // Map으로 보낸 데이터가 클라이언트에게 객체로 전달됨(@ResponseBody때문인 듯)
    return result;
  }
  








}
