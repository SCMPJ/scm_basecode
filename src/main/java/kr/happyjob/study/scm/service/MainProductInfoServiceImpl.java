package kr.happyjob.study.scm.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.happyjob.study.common.comnUtils.FileUtilCho;
import kr.happyjob.study.scm.dao.MainProductInfoDao;
import kr.happyjob.study.scm.model.GetWarehouseModel;
import kr.happyjob.study.scm.model.MainProductInfoModel;
import kr.happyjob.study.scm.model.MainProductModalModel;

@Service
public class MainProductInfoServiceImpl implements MainProductInfoService {
  
  @Autowired
  MainProductInfoDao mainProductInfoDao;
  
  //Root path for file upload 
  @Value("${fileUpload.rootPath}")
  private String rootPath;
 
  @Value("${fileUpload.productPath}")
  private String productPath;
  
  // comment path for file upload
  @Value("${fileUpload.productRelativePath}")
  private String productRelativePath;
  
  // 제품 목록 조회
  @Override
  public List<MainProductInfoModel> listMainProduct(Map<String, Object> paramMap) throws Exception {
    List<MainProductInfoModel> listMainProduct = mainProductInfoDao.listMainProduct(paramMap);
    return listMainProduct;
  }
  
  // 제품 카운트
  @Override
  public int totalCntMainProduct(Map<String, Object> paramMap) throws Exception {
    int totalCntMainProduct = mainProductInfoDao.totalCntMainProduct(paramMap);
    return totalCntMainProduct;
  }
  
  // 제품정보 관리조회
  @Override
  public MainProductInfoModel selectMainProduct(Map<String, Object> paramMap) throws Exception {
    MainProductInfoModel selectMainProduct = mainProductInfoDao.selectMainProduct(paramMap);
    return selectMainProduct;
  }
  
  /* 제품 상세정보 조회*/
  @Override
  public MainProductModalModel mainProductModal(Map<String, Object> paramMap) throws Exception {
    MainProductModalModel mainProductModal = mainProductInfoDao.mainProductModal(paramMap);
    return mainProductModal;
  }
  
  // 창고정보 조회
  @Override
  public  GetWarehouseModel getWarehouseInfo(Map<String, Object> paramMap) throws Exception {
    GetWarehouseModel getWarehouseInfo = mainProductInfoDao.getWarehouseInfo(paramMap);
    return getWarehouseInfo;
  };
  
  // 제품정보 저장
  @Override
  public int insertMainProduct(Map<String, Object> paramMap,HttpServletRequest request) throws Exception {
    MultipartHttpServletRequest mpRequest = (MultipartHttpServletRequest)request;
    
    // 파일 저장  std_id = 작성자 id , hwk_id = 과제 id  
    String itemFilePath = productPath + File.separator;
    FileUtilCho fileUtilCho = new FileUtilCho(mpRequest, rootPath, itemFilePath);
    Map<String, Object> listFileUtilModel = fileUtilCho.uploadFiles();
    
    String delimiter = "/";
    String logicalpath = delimiter + productPath + delimiter + listFileUtilModel.get("file_nm");
    listFileUtilModel.put("logi", logicalpath);
    
    paramMap.put("file", listFileUtilModel);
    
    int ret = mainProductInfoDao.insertMainProduct(paramMap);
    
    return ret;
  };
  
  // 제품정보 수정
  @Override
  public int updateMainProduct(Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
    MultipartHttpServletRequest umpRequest = (MultipartHttpServletRequest)request;
    
    // 파일 저장  std_id = 작성자 id , hwk_id = 과제 id  
    String itemFilePath = productPath + File.separator;
    FileUtilCho fileUtilCho = new FileUtilCho(umpRequest, rootPath, itemFilePath);
    Map<String, Object> listFileUtilModel = fileUtilCho.uploadFiles();
    
    String delimiter = "/";
    String logicalpath = delimiter + productPath + delimiter + listFileUtilModel.get("file_nm");
    listFileUtilModel.put("logi", logicalpath);
    
    paramMap.put("file", listFileUtilModel);
    
    int ret = mainProductInfoDao.updateMainProduct(paramMap);
    return ret;
  };
  
  // 제품정보 삭제
  @Override
  public int deleteMainProduct(Map<String, Object> paramMap) throws Exception {
    int ret = mainProductInfoDao.deleteMainProduct(paramMap);
    return ret;
  };
}
