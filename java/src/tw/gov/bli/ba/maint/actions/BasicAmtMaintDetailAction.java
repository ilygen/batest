package tw.gov.bli.ba.maint.actions;

import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.ConstantKey;
import tw.gov.bli.ba.framework.domain.UserBean;
import tw.gov.bli.ba.framework.helper.CustomMessageHelper;
import tw.gov.bli.ba.framework.helper.DatabaseMessageHelper;
import tw.gov.bli.ba.framework.helper.SystemMessageHelper;
import tw.gov.bli.ba.framework.struts.actions.BaseDispatchAction;
import tw.gov.bli.ba.domain.Babasicamt;
import tw.gov.bli.ba.domain.Bacpidtl;
import tw.gov.bli.ba.domain.Bacpirec;
import tw.gov.bli.ba.maint.cases.BasicAmtMaintCase;
import tw.gov.bli.ba.maint.cases.CpiDtlMaintCase;
import tw.gov.bli.ba.maint.cases.CpiRecMaintCase;
import tw.gov.bli.ba.maint.forms.BasicAmtMaintDetailForm;
import tw.gov.bli.ba.maint.forms.BasicAmtMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CpiDtlMaintQueryForm;
import tw.gov.bli.ba.maint.forms.CpiRecMaintDetailForm;
import tw.gov.bli.ba.maint.forms.CpiRecMaintQueryForm;
import tw.gov.bli.ba.maint.helper.CaseSessionHelper;
import tw.gov.bli.ba.maint.helper.FormSessionHelper;
import tw.gov.bli.ba.services.MaintService;
import tw.gov.bli.ba.util.BeanUtility;
import tw.gov.bli.ba.util.DateUtility;
import tw.gov.bli.ba.util.ExceptionUtility;
import tw.gov.bli.common.helper.UserSessionHelper;

/**
 * 維護作業 - 老年年金加計金額調整作業 - 新增頁面 (bapa0x082a.jsp)
 *
 * @author frank
 */
public class BasicAmtMaintDetailAction extends BaseDispatchAction {
    private static Log log = LogFactory.getLog(BasicAmtMaintDetailAction.class);

    private MaintService maintService;

    /**
     * 維護作業 - 老年年金加計金額新增作業 - 新增頁面 (bapa0x082a.jsp) <br>
     * 按下「確認」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doSave(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行 維護作業 - 老年年金加計金額新增作業 - 新增頁面 BasicAmtMaintDetailAction.doSave() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);

        BasicAmtMaintDetailForm detailForm = (BasicAmtMaintDetailForm) form;
        
        BasicAmtMaintDetailForm detailForm1 = new BasicAmtMaintDetailForm();

        try {
            
            // 檢查是否有重複資料
            //List<BasicAmtMaintCase> checkList = maintService.getBasicAmtMaintQueryCaseBy(detailForm.getPayYmB());
        	//設定給付類別 payCode
        	String payCode = "L";
        	//轉民國為西元 進資料庫查詢需核對之資料
        	String CpiYear1 = DateUtility.changeChineseYearType(detailForm.getCpiYear1());
        	String CpiYear2 = DateUtility.changeChineseYearType(detailForm.getCpiYear2());
        	Babasicamt  checkCpiYear = maintService.getBasicAmtMaintForCheckSaveCaseBy(payCode,CpiYear1,CpiYear2);
        	
            //if (checkList.size() > 0) {
        	
        	if (checkCpiYear != null) {
                // 設定該筆資料已存在訊息
                saveMessages(session, DatabaseMessageHelper.getNoResultToExistMessage());
                return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }
            
        	//成長率<0，不可儲存 begin
        	BigDecimal zero = new BigDecimal(0);
        	if(detailForm.getGrowThrate().compareTo(zero) == -1){
        		saveMessages(session, CustomMessageHelper.getGrowThrateError());
            	return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        	}
        	//成長率<0，不可儲存 end
        	
            //檢查迄月是否大於等於起月 begin
        	int intYmB = Integer.parseInt(detailForm.getPayYmB());
            String YmE = detailForm.getPayYmE();
            if(StringUtils.isBlank(detailForm.getPayYmE())){
        		 YmE = "99999";
        	}
            int intYmE = Integer.parseInt(YmE);
            if(intYmB >= intYmE){
            	saveMessages(session, CustomMessageHelper.getCheckPayYmE());
            	return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
            }
            //檢查迄月是否大於等於起月 end
            
            if (detailForm != null) {
                Babasicamt detailData = new Babasicamt();
                BeanUtility.copyProperties(detailData, detailForm);
                
                detailData.setPayCode("L");//設定給付別
                // 變更民國年為西元年                
                if (StringUtils.defaultString(detailForm.getCpiYear1()).trim().length() == 3) {
                    detailData.setCpiYear1(Integer.toString((Integer.parseInt(detailForm.getCpiYear1().substring(0, 3)) + 1911)));
                }
                
                if (StringUtils.defaultString(detailForm.getCpiYear2()).trim().length() == 3) {
                    detailData.setCpiYear2(Integer.toString((Integer.parseInt(detailForm.getCpiYear2().substring(0, 3)) + 1911)));
                }
                
                if (StringUtils.defaultString(detailForm.getPayYmB()).trim().length() == 5) {
                    detailData.setPayYmB(Integer.toString((Integer.parseInt(detailForm.getPayYmB().substring(0, 3)) + 1911)) + detailForm.getPayYmB().substring(3, 5));
                }      
                
                if (StringUtils.defaultString(detailForm.getPayYmE()).trim().length() == 5) {
                    detailData.setPayYmE(Integer.toString((Integer.parseInt(detailForm.getPayYmE().substring(0, 3)) + 1911)) + detailForm.getPayYmE().substring(3, 5));
                }   
 
             // 取得前一筆資料 加計金額需高於前一筆資料之加計金額
                if(detailData.getBasicAmt().compareTo(detailForm.getLastBasicAmt()) == -1){
                	
                	saveMessages(session, CustomMessageHelper.getCheckLastBasicAmt());
                	
                    return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
                }
                
                // 存檔
                maintService.saveBasicAmtMaintData(detailData, userData);
                   
                // 更新清單資料
                List<BasicAmtMaintCase> applyList = maintService.getBasicAmtMaintQueryCaseBy(null,"L");
                CaseSessionHelper.setBasicAmtMaintQueryCase(applyList, request);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getSaveSuccessMessage());
            }

            log.debug("執行 維護作業 - 老年年金加計金額調整作業 - 新增頁面  BasicAmtMaintDetailAction.doSave() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_SAVE_SUCCESS);


        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error("BasicAmtMaintDetailAction.doSave() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_SAVE_FAIL);
        }
    }

    /**
     * 維護作業 - 老年年金加計金額明細新增作業 - 修改頁面 (bapa0x063c.jsp) <br>
     * 按下「確認」的動作
     *
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     */
    public ActionForward doConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
        log.debug("執行維護作業 - 老年年金加計金額調整新增作業 - 修改頁面 BasicAmtMaintDetailAction.doConfirm() 開始 ... ");

        HttpSession session = request.getSession();
        UserBean userData = (UserBean) UserSessionHelper.getUserData(request);
        
        
        //FormSessionHelper.removeBasicAmtMaintDetailForm(request);
        BasicAmtMaintDetailForm detailForm = (BasicAmtMaintDetailForm) form;
        
        try {
        	
            //FormSessionHelper.removeBasicAmtMaintQueryForm(request);
        	
        	//成長率<0，不可儲存 begin
        	BigDecimal zero = new BigDecimal(0);
        	if(detailForm.getGrowThrate().compareTo(zero) == -1){
        		saveMessages(session, CustomMessageHelper.getGrowThrateError());
            	return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        	}
        	//成長率<0，不可儲存 end
        	
        	//檢查迄月是否大於等於起月 begin
        	int intYmB = Integer.parseInt(detailForm.getPayYmB());
            String YmE = detailForm.getPayYmE();
            if(StringUtils.isBlank(detailForm.getPayYmE())){
        		YmE = "99999";
        	}
            int intYmE = Integer.parseInt(YmE);
            if(intYmB >= intYmE){
            	saveMessages(session, CustomMessageHelper.getCheckPayYmE());
            	return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
            }
            //檢查迄月是否大於等於起月 end
            
            
            if (detailForm != null) {
                Babasicamt detailData = new Babasicamt();
                BeanUtility.copyProperties(detailData, detailForm);
                
             //選取修改資料的前一筆資料  取得前一筆加計金額使用
             //修改之加計金額需大於前一筆資料之加計金額
                if(detailData.getBasicAmt().compareTo(detailForm.getLastBasicAmt()) == -1){
                	
                	saveMessages(session, CustomMessageHelper.getCheckLastBasicAmt());
                	
                    return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
                }
                
                // 變更民國年為西元年            
                if (StringUtils.defaultString(detailData.getCpiYear1()).trim().length() == 3) {
                    detailData.setCpiYear1(Integer.toString((Integer.parseInt(detailData.getCpiYear1().substring(0, 3)) + 1911)));
                } 
                if (StringUtils.defaultString(detailData.getCpiYear2()).trim().length() == 3) {
                    detailData.setCpiYear2(Integer.toString((Integer.parseInt(detailData.getCpiYear2().substring(0, 3)) + 1911)));
                } 
                if (StringUtils.defaultString(detailForm.getPayYmB()).trim().length() == 5) {
                    detailData.setPayYmB(Integer.toString((Integer.parseInt(detailForm.getPayYmB().substring(0, 3)) + 1911)) + detailForm.getPayYmB().substring(3, 5));
                }     
                if (StringUtils.defaultString(detailForm.getPayYmE()).trim().length() == 5) {
                    detailData.setPayYmE(Integer.toString((Integer.parseInt(detailForm.getPayYmE().substring(0, 3)) + 1911)) + detailForm.getPayYmE().substring(3, 5));
                }  
                //設定給付類別
                detailData.setPayCode("L");
                // 存檔
                maintService.updateBasicAmtMaintData(detailData, userData);

                // 更新清單資料 
                List<BasicAmtMaintCase> applyList = maintService.getBasicAmtMaintQueryCaseBy(null,"L");
                CaseSessionHelper.setBasicAmtMaintQueryCase(applyList, request);

                // 設定存檔成功訊息
                saveMessages(session, DatabaseMessageHelper.getUpdateSuccessMessage());
            }

            log.debug("執行 維護作業 - 老年年金加計金額調整新增作業 - 修改頁面   BasicAmtMaintDetailAction.doConfirm() 完成 ... ");

            return mapping.findForward(ConstantKey.FORWARD_UPDATE_SUCCESS);
        }
        catch (Exception e) {
            // 設定查詢失敗訊息
            log.error(" BasicAmtMaintDetailAction.doConfirm() 發生錯誤:" + ExceptionUtility.getStackTrace(e));
            saveMessages(session, DatabaseMessageHelper.getQueryFailMessage());
            return mapping.findForward(ConstantKey.FORWARD_UPDATE_FAIL);
        }
    }

    public void setMaintService(MaintService maintService) {
        this.maintService = maintService;
    }
}
