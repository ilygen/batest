<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<%@ page import="tw.gov.bli.ba.receipt.helper.CaseSessionHelper" %>
<%@ page import="tw.gov.bli.ba.util.DateUtility" %>
<%@ page import="java.util.List" %>
<%@ page import="tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptCase" %>
<%@ page import="tw.gov.bli.ba.receipt.cases.SurvivorAnnuityReceiptBenCase" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAAP0D031A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/receiptCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="SurvivorAnnuityReceiptForm" page="1" />
    <html:javascript formName="SurvivorAnnuityReceiptBenForm" page="1" />
    <script language="javascript" type="text/javascript">
    function initAll(){
        if('<c:out value="${SurvivorAnnuityReceiptBenForm.benOptionMode}" />'=="updateMode"){
            $("benOptionMode").value="updateMode";
            $("insertModeBtn").style.display="none";
            $("insertModeStr").style.display="none";
            $("updateModeBtn").style.display="inline";
            $("updateModeStr").style.display="inline";
            $("dataIndex").innerHTML = $("dataIdx").value;
        }else{
            $("benOptionMode").value="insertMode";
            $("insertModeBtn").style.display="inline";
            $("insertModeStr").style.display="inline";
            $("updateModeBtn").style.display="none";
            $("updateModeStr").style.display="none";
        }

        //事故者國籍別
        if('<c:out value="${SurvivorAnnuityReceiptForm.evtNationTpe}" />'!=""){
            if('<c:out value="${SurvivorAnnuityReceiptForm.evtNationTpe}" />'=="1"){
                $("evtSex").disabled = true;
                $("evtNationCode").disabled = true;
                $("evtNationCodeOption").disabled = true;
                $("evtSex").value = "";
                $("evtNationCode").value = "";
                $("evtNationCodeOption").value = "";
            }else{
                if('<c:out value="${SurvivorAnnuityReceiptForm.evtNationCodeOption}" />'!=""){
                    $("evtNationCodeOption").value = '<c:out value="${SurvivorAnnuityReceiptForm.evtNationCodeOption}" />';
                }else{
                    $("evtNationCodeOption").value = '<c:out value="${SurvivorAnnuityReceiptForm.evtNationCode}" />';
                }
                $("evtSex").disabled = false;
                $("evtNationCode").disabled = false;
                $("evtNationCodeOption").disabled = false;
            }
        }else{
            $("evtNationTpe").value="1";
            $("evtSex").disabled = true;
            $("evtNationCode").disabled = true;
            $("evtNationCodeOption").disabled = true;
        }

        //遺屬國籍別

        if('<c:out value="${SurvivorAnnuityReceiptBenForm.benNationTyp}" />'!=""){
            if('<c:out value="${SurvivorAnnuityReceiptBenForm.benNationTyp}" />'=="1"){
                $("benSex").disabled = true;
                $("benNationCode").disabled = true;
                $("benNationCodeOption").disabled = true;
                $("benSex").value = "";
                $("benNationCode").value = "";
                $("benNationCodeOption").value = "";
                $("commTyp").readOnly = false;
                if('<c:out value="${SurvivorAnnuityReceiptBenForm.commTyp}" />'=="1"){
                    $("commZip").disabled = true;
                    $("commAddr").disabled = true;
                    $("commZip").value = "";
                    $("commAddr").value = "";
                }else{
                    $("commZip").disabled = false;
                    $("commAddr").disabled = false;
                }
            }else{
                $("benSex").disabled = false;
                $("benNationCode").disabled = false;
                $("benNationCodeOption").disabled = false;
                $("commTyp").value="2";
                $("commTyp").readOnly = true;
                $("commZip").disabled = false;
                $("commAddr").disabled = false;
                if('<c:out value="${SurvivorAnnuityReceiptBenForm.benNationCodeOption}" />'!=""){
                    $("benNationCodeOption").value = '<c:out value="${SurvivorAnnuityReceiptBenForm.benNationCodeOption}" />';
                }else{
                    $("benNationCodeOption").value = '<c:out value="${SurvivorAnnuityReceiptBenForm.benNationCode}" />';
                }
            }
        }else{
            $("benNationTyp").value="1";
            $("commTyp").value="2";
            $("benSex").disabled = true;
            $("benNationCode").disabled = true;
            $("benNationCodeOption").disabled = true;
            $("commTyp").readOnly = false;
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }

        //給付方式
        if('<c:out value="${SurvivorAnnuityReceiptBenForm.payCategory}" />' == "1"){
            $("payCategory1").checked=true;
            $("accData").value="";
            $("accData").disabled = true;
            $("payTyp").disabled = false;
            if('<c:out value="${SurvivorAnnuityReceiptBenForm.payTyp}" />' == ""){
                $("payTyp").value="";
            }
        }else if('<c:out value="${SurvivorAnnuityReceiptBenForm.payCategory}" />' == "2"){
            $("payCategory2").checked=true;
            $("payTyp").value="";
            $("payTyp").disabled = true;
            $("payBankId").value="";
            $("branchId").value="";
            $("payEeacc").value="";
            $("payBankId").disabled=true;
            $("branchId").disabled=true;
            $("payEeacc").disabled=true;
            //帳號複驗
            $("chkPayBankId").value = "";
            $("chkBranchId").value = "";
            $("chkPayEeacc").value = "";
            $("chkPayBankId").disabled = true;
            $("chkBranchId").disabled = true;
            $("chkPayEeacc").disabled = true;

            $("accData").disabled = false;
            $("accData").value="";
        }else{
            $("payCategory1").checked=true;
            $("accData").value="";
            $("accData").disabled = true;
            $("payTyp").disabled = false;
            $("payTyp").value="";
        }
        if(<%=((List)request.getSession().getAttribute(ConstantKey.BEN_OPTION_LIST)).size()%>==0){
            $("payCategory2").disabled = true;
            $("accData").disabled = true;
            $("accData").value="";
        }

        //手機複驗
        enableMobilePhone();
        if($("mobilePhone").disabled == false){
            $("mobilePhone").value='<c:out value="${SurvivorAnnuityReceiptBenForm.mobilePhone}" />';
        }else{
            $("mobilePhone").value="";
        }

        if('<c:out value="${SurvivorAnnuityReceiptBenForm.focusLocation}" />'=="benNationTyp"){
            $("benNationTyp").focus();
        }else{
            $("apNo1").focus();
        }
        changeCommTyp();
        changePayTyp();
        chgBenEvtRel();
        chgMonIncomeMk();
        clearSchoolCodeContent();
    }

    <%-- 1030813 payTyp=1時tab跳過0000 --%>
    function tabChange(){

       if (Trim($("payTyp").value) == "1"){
              $("branchId").tabIndex = -1;
              $("chkBranchId").tabIndex = -1;
           }
       if (Trim($("payTyp").value) == "2" || Trim($("payTyp").value) == "A" || Trim($("payTyp").value) == ""){
              $("branchId").tabIndex = 385;
              $("chkBranchId").tabIndex = 405;
       }
    }

    //變更 事故者國籍別 時畫面異動
    function changeEvtNationTpe(){
        if(Trim(asc($("evtNationTpe").value))=="1"){
            $("evtSex").disabled = true;
            $("evtSex").value = "";
            $("evtNationCode").disabled = true;
            $("evtNationCode").value = "";
            $("evtNationCodeOption").disabled = true;
            $("evtNationCodeOption").value = "";
        }
        if(Trim(asc($("evtNationTpe").value))=="2"){
            $("evtSex").disabled = false;
            $("evtNationCode").disabled = false;
            $("evtNationCodeOption").disabled = false;
        }
        autoForeignEvtSex();
    }

    function chgEvtNationTpeFocus(){
        if(Trim(asc($("evtNationTpe").value))=="1" && event.keyCode==9){
            $("evtName").focus();
        }else if(Trim(asc($("evtNationTpe").value))=="2" && event.keyCode==9){
            $("evtSex").focus();
        }
    }

    //變更 遺屬國籍別 時畫面異動
    function changeBenNationTyp(){
        if(Trim(asc($("benNationTyp").value))=="1"){
            $("benSex").disabled = true;
            $("benSex").value = "";
            $("benNationCode").disabled = true;
            $("benNationCode").value = "";
            $("benNationCodeOption").disabled = true;
            $("benNationCodeOption").value = "";
            $("commTyp").value="2";
            $("commTyp").readOnly = false;
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
        else if(Trim(asc($("benNationTyp").value))=="2"){
            $("benSex").disabled = false;
            $("benNationCode").disabled = false;
            $("benNationCodeOption").disabled = false;
            $("commTyp").value="2";
            $("commTyp").readOnly = true;
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
        autoForeignBenSex();
    }

    function changeBenNationTypForBenUpdate(){
        if(Trim(asc($("benNationTyp").value))=="1"){
            $("benSex").disabled = true;
            $("benSex").value = "";
            $("benNationCode").disabled = true;
            $("benNationCode").value = "";
            $("benNationCodeOption").disabled = true;
            $("benNationCodeOption").value = "";
            //$("commTyp").value="2";
            $("commTyp").readOnly = false;
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
        else if(Trim(asc($("benNationTyp").value))=="2"){
            $("benSex").disabled = false;
            $("benNationCode").disabled = false;
            $("benNationCodeOption").disabled = false;
            //$("commTyp").value="2";
            $("commTyp").readOnly = true;
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
    }

    function chgBenNationTypFocus(){
        if(Trim(asc($("benNationTyp").value=="1")) && event.keyCode==9){
            $("benName").focus();
        }else if(Trim(asc($("benNationTyp").value=="2")) && event.keyCode==9){
            $("benSex").focus();
        }
    }

    // Ajax for 取得 戶籍檔姓名

    function initCvldtlName() {
        if(($("evtIdnNo").value != "") && !isNaN($("evtBrDate").value)){
            receiptCommonAjaxDo.getCvldtlName($("evtIdnNo").value, $("evtBrDate").value, fillCvldtlName);
        }else{
            $("cvldtlName").value = "";
        }
    }

    //檢核事故者出生日期 20121220 邏輯修改
    function isValidEvtDateTrue() {
        var evtBrDate = $("evtBrDate").value;

        if(isValidDate($("evtBrDate").value) == false){
          if(confirm("輸入之「事故者出生日期」錯誤，是否繼續進行存檔?") == true){
          return true;
          }else{
          $("evtBrDate").focus();
          return false;
          }
        }else{
          return true;
        }

    }
    //檢核事故者出生日期 20121220 邏輯修改
    function isValidBenDateTrue() {
        var evtBrDate = $("benBrDate").value;

        if(isValidDate($("benBrDate").value) == false){
          if(confirm("輸入之「遺屬出生日期」錯誤，是否繼續進行存檔?") == true){
          return true;
          }else{
          $("benBrDate").focus();
          return false;
          }
        }else{
          return true;
        }

    }

    function fillCvldtlName(name) {
        $("cvldtlName").value = name;
    }

    // Ajax for 取得欲修改之眷屬資料
    function getBenDetailData(bafamilytempId, seqNo){
        receiptCommonAjaxDo.getSurvivorBenTempDetailData(bafamilytempId, seqNo, setBenData);
    }

    function setBenData(benCase){
        $('seqNo').value = benCase.seqNo;
        $("benNationTyp").value = benCase.famNationTyp;
        if(benCase.famNationTyp == '2'){
            $("benSex").value = benCase.famSex;
            $("benNationCode").value = benCase.famNationCode;
            $("benNationCodeOption").value = benCase.famNationCode;
        }
        $("benName").value = benCase.famName;
        $("benAppDate").value = benCase.famAppDate;
        $("benIdnNo").value = benCase.famIdnNo;
        $("benBrDate").value = benCase.famBrDate;
        $("benEvtRel").value = benCase.famEvtRel;
        $("tel1").value = benCase.tel1;
        $("tel2").value = benCase.tel2;
        $("commTyp").value = benCase.commTyp;
        $("commZip").value = benCase.commZip;
        $("commAddr").value = benCase.commAddr;
        $("grdName").value = benCase.grdName;
        $("grdIdnNo").value = benCase.grdIdnNo;
        $("grdBrDate").value = benCase.grdBrDate;
        $("monIncomeMk").value = benCase.monIncomeMk;
        if(benCase.monIncome!="" && benCase.monIncome!="null"  && benCase.monIncome!=null){
            $("monIncome").value = benCase.monIncome;
        }
        $("accSeqNo").value = benCase.accSeqNo;
        $("payTyp").value = benCase.payTyp;
        $("payBankIdBranchId").value = benCase.payBankIdBranchId;
        $("payBankId").value = benCase.payBankId;
        $("branchId").value = benCase.branchId;
        $("payEeacc").value = benCase.payEeacc;
        //帳號複驗
        $("chkPayBankIdChkBranchId").value = benCase.chkPayBankIdChkBranchId;
        $("chkPayBankId").value = benCase.chkPayBankId;
        $("chkBranchId").value = benCase.chkBranchId;
        $("chkPayEeacc").value = benCase.chkPayEeacc;

        if(benCase.accRel=="3"){
            $("payCategory2").checked = true;
            $("payTyp").value = "";
            $("payBankId").value = "";
            $("branchId").value = "";
            $("payEeacc").value = "";
            //帳號複驗
            $("chkPayBankId").value = "";
            $("chkBranchId").value = "";
            $("chkPayEeacc").value = "";

            $("payTyp").disabled = true;
            $("payBankId").disabled = true;
            $("branchId").disabled = true;
            $("payEeacc").disabled = true;
            //帳號複驗
            $("chkPayBankId").disabled = true;
            $("chkBranchId").disabled = true;
            $("chkPayEeacc").disabled = true;

            $("accData").disabled = false;
        }else{
            $("payCategory1").checked = true;
            $("payTyp").disabled = false;
            $("accData").disabled = true;
            $("accData").value="";
        }

        if(benCase.accSeqNoAmt == "1"){
            $("payCategory2").disabled = true;
            $("accData").disabled = true;
            $("accData").value="";
        }else{
            $("payCategory2").disabled = false;
            getAccDataOptionList(benCase.bafamilytempId, benCase.seqNo);
        }

        if(benCase.famEvtRel == '2'){
            $("marryDate").value = benCase.marryDate;
        }
        if(benCase.famEvtRel == '4' || benCase.famEvtRel == '7'){
            $("studMk").value = benCase.studMk;
            if (Trim(asc($("studMk").value).toUpperCase() ) == "Y") {
            	$("schoolCodeContent").style.display="inline";
            	$("schoolCode").value = benCase.schoolCode;
            	$("schoolCodeOption").value = benCase.schoolCode;
            } else {
            	clearSchoolCodeContent();
            }
        }
        if(benCase.famEvtRel != '3' && benCase.famEvtRel != '5'){
            $("handIcapMk").value = benCase.handIcapMk;
            $("interDictMk").value = benCase.interDictMk;
        }

        var tel1 = benCase.tel1;
        var tel2 = benCase.tel2;
        var shortTel1 = "";
        var shortTel2 = "";
        if(tel1.length >=2){
            shortTel1 = tel1.substring(0,2);
        }
        if(tel2.length >=2){
            shortTel2 = tel2.substring(0,2);
        }

        if((shortTel1=="09" && tel1.length==10) || (shortTel2=="09" && tel2.length==10)){
            $("mobilePhone").disabled = false;
            $("mobilePhone").value = benCase.mobilePhone;
        }else{
            $("mobilePhone").disabled = true;
            $("mobilePhone").value = "";
        }

        $("insertModeBtn").style.display="none";
        $("insertModeStr").style.display="none";
        $("updateModeBtn").style.display="inline";
        $("updateModeStr").style.display="inline";
        $("benNationTyp").focus;

        if($("dataIdx").value == "1"){
            $("defaultGrdData").disabled = true;
        }else{
            $("defaultGrdData").disabled = false;
        }

        changeBenNationTypForBenUpdate();
        chgBenEvtRel();
        chgMonIncomeMk();
        changeCommTyp();
        changePayTyp();

        $("benNationTyp").focus();
    }

    //清空遺屬資料
    function cleanBenData(){
        $("seqNo").value = "";
        $("benNationTyp").value = "1";
        $("benSex").value = "";
        $("benNationCode").value = "";
        $("benName").value = "";
        $("benAppDate").value = "";
        $("benIdnNo").value = "";
        $("benBrDate").value = "";
        $("benEvtRel").value = "";
        $("tel1").value = "";
        $("tel2").value = "";
        $("commTyp").value = "2";
        $("commZip").value = "";
        $("commAddr").value = "";
        $("grdName").value = "";
        $("grdIdnNo").value = "";
        $("grdBrDate").value = "";
        $("marryDate").value = "";
        $("studMk").value = "";
        $("monIncomeMk").value = "";
        $("monIncome").value = "";
        $("handIcapMk").value = "";
        $("interDictMk").value = "";
        $("accData").value = "";
        $("accSeqNo").value = "";
        $("mobilePhone").value = "";
        $("defaultGrdData").value = "";

        $("insertModeBtn").style.display="inline";
        $("insertModeStr").style.display="inline";
        $("updateModeBtn").style.display="none";
        $("updateModeStr").style.display="none";
        $("dataIdx").value = "";
        $('dataIndex').innerHTML = "";

        $("benSex").disabled = true;
        $("benNationCode").disabled = true;
        $("benNationCodeOption").disabled = true;
        $("commTyp").readOnly = false;
        $("commZip").disabled = false;
        $("commAddr").disabled = false;
        $("mobilePhone").disabled = true;

        $("payCategory1").checked = true;
        $("payCategory2").checked = false;
        $("accData").disabled = true;
        $("accData").value="";

        //給付方式
        $("payCategory1").checked=true;
        $("payCategory2").checked = false;
        $("accData").value="";
        $("accData").disabled = true;
        $("payTyp").disabled = false;
        $("payTyp").value="";
        $("payEeacc").value = "";
        $("payBankId").value = "";
        $("branchId").value = "";
        //帳號複驗
        $("chkPayEeacc").value = "";
        $("chkPayBankId").value = "";
        $("chkBranchId").value = "";

        $("defaultGrdData").disabled = false;
        getAccDataOptionList($("benBafamilytempId").value, '');
        clearSchoolCodeContent();
    }

    // Ajax for 取得 具領人選單

    function getAccDataOptionList(bafamilytempId, seqNo) {
        receiptCommonAjaxDo.getBenOptionListForSurvivorTemp(bafamilytempId, seqNo, setAccDataOptionList);
    }

    function setAccDataOptionList(accDataOption) {
        DWRUtil.removeAllOptions("accData");
        DWRUtil.addOptions("accData", {'':'請選擇'});
        DWRUtil.addOptions("accData", accDataOption ,'accSeqNo','accName');

        if(accDataOption.length==0){
            $("payCategory2").disabled = true;
            $("accData").disabled = true;
            $("accData").value="";
        }else{
            $("payCategory2").disabled = false;
        }
        for(i=0; i<accDataOption.length; i++){
            strs = (accDataOption[i].accSeqNo).split(";");
            if($("accSeqNo").value == strs[1]){
                $("accData").value = accDataOption[i].accSeqNo;
                break;
            }
        }
    }

    //變更 通訊地址別 時畫面異動

    function changeCommTyp(){
        if(Trim(asc($("commTyp").value))=="1"){
            $("commZip").value = "";
            $("commAddr").value = "";
            $("commZip").disabled = true;
            $("commAddr").disabled = true;
        }
        if(Trim(asc($("commTyp").value))=="2"){
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
    }

    function chgCommTypFocus(){
        if(Trim(asc($("commTyp").value)) == 1 && event.keyCode==9){
            $("grdName").focus();
        }else if(Trim(asc($("commTyp").value)) == 2 && event.keyCode==9){
            $("commZip").focus();
        }
    }

    //變更 給付方式 時畫面異動

    function chgPayCategory(){
        if($("payCategory1").checked==true){
            $("accData").value="";
            $("accData").disabled = true;
            $("payTyp").disabled = false;
            $("payTyp").value="";
        }
        if($("payCategory2").checked==true){
            $("payTyp").value="";
            $("payTyp").disabled = true;
            $("payBankId").value="";
            $("branchId").value="";
            $("payEeacc").value="";
            $("payBankId").disabled=true;
            $("branchId").disabled=true;
            $("payEeacc").disabled=true;
             //帳號複驗
            $("chkPayBankId").disabled = true;
            $("chkBranchId").disabled = true;
            $("chkPayEeacc").disabled = true;
            $("chkPayBankId").value = "";
            $("chkBranchId").value = "";
            $("chkPayEeacc").value = "";

            $("accData").disabled = false;
            $("accData").value="";
        }
        if(<%=((List)request.getSession().getAttribute(ConstantKey.BEN_OPTION_LIST)).size()%>==0){
            $("payCategory2").disabled = true;
            $("accData").disabled = true;
            $("accData").value="";
        }
    }

    function chgPayCategoryFocus(){
        if($("payCategory1").checked==true && event.keyCode==9){
            $("payBankId").focus();
        }else if($("payCategory2").checked==true && event.keyCode==9){
            $("accData").focus();
        }
    }

    //變更 給付方式 時畫面異動

    function changePayTyp(){
        if($("payTyp").value=="1"){
            $("payBankId").disabled = false;
            $("branchId").value = '0000';
            $("branchId").readOnly = true;
            $("branchId").addClassName('disabledN');
            $("branchId").disabled = false;
            $("payEeacc").disabled = false;
            //帳號複驗
            $("chkPayBankId").disabled = false;
            $("chkBranchId").value = '0000';
            $("chkBranchId").readOnly = true;
            $("chkBranchId").addClassName('disabledN');
            $("chkBranchId").disabled = false;
            $("chkPayEeacc").disabled = false;

        }else if($("payTyp").value=="2"){

            $("payBankId").disabled = false;
            $("branchId").disabled = false;
            $("payEeacc").disabled = false;
            $("branchId").readOnly = false;
            $("branchId").removeClassName('disabledN');
            //帳號複驗
            $("chkPayBankId").disabled = false;
            $("chkBranchId").disabled = false;
            $("chkPayEeacc").disabled = false;
            $("chkBranchId").readOnly = false;
            $("chkBranchId").removeClassName('disabledN');

             if($("branchId").value == "0000"){
              $("branchId").value = "";
            }
            if($("chkBranchId").value == "0000"){
              $("chkBranchId").value = "";
            }
        }else if(Trim(asc($("payTyp").value))=="A"){
            $("payBankId").disabled = true;
            $("branchId").disabled = true;
            $("payEeacc").disabled = true;
            $("payBankId").value="";
            $("branchId").value="";
            $("payEeacc").value = "";
            //帳號複驗
            $("chkPayBankId").disabled = true;
            $("chkBranchId").disabled = true;
            $("chkPayEeacc").disabled = true;
            $("chkPayBankId").value = "";
            $("chkBranchId").value = "";
            $("chkPayEeacc").value = "";
        }
        else{
            $("payBankId").disabled = true;
            $("branchId").disabled = true;
            $("payEeacc").disabled = true;
            $("payBankId").value="";
            $("branchId").value="";
            $("payEeacc").value = "";
            //帳號複驗
            $("chkPayBankId").disabled = true;
            $("chkBranchId").disabled = true;
            $("chkPayEeacc").disabled = true;
            $("chkPayBankId").value = "";
            $("chkBranchId").value = "";
            $("chkPayEeacc").value = "";
        }
    }

    function chgPayTypFocus(){
        if((Trim(asc($("payTyp").value))=="1"||Trim(asc($("payTyp").value))=="2") && event.keyCode==9){
            $("payBankId").focus();
        }else if(Trim(asc($("payTyp").value=="4")) && event.keyCode==9){
            if($("insertModeBtn").style.display=="inline"){
                $("btnInsert").focus();
            }
            if($("updateModeBtn").style.display=="inline"){
                $("btnUpdate1").focus();
            }
        }else if(event.keyCode==9){
            if($("insertModeBtn").style.display=="inline"){
                $("btnInsert").focus();
            }
            if($("updateModeBtn").style.display=="inline"){
                $("btnUpdate1").focus();
            }
        }
    }

    //變更 關係 時畫面異動

    function chgBenEvtRel(){
        if(Trim(asc($("benEvtRel").value)) == "2"){
            $("marryDate").disabled = false;
            //$("marryDate").value = "";
            $("studMk").disabled = true;
            $("studMk").value = "";
            $("handIcapMk").disabled = false;
            //$("handIcapMk").value = "";
            $("interDictMk").disabled = false;
            clearSchoolCodeContent();
            //$("interDictMk").value = "";
        }else if(Trim(asc($("benEvtRel").value)) == "4" || Trim(asc($("benEvtRel").value)) == "7"){
            $("marryDate").disabled = true;
            $("marryDate").value = "";
            $("studMk").disabled = false;
            $("handIcapMk").disabled = false;
            $("interDictMk").disabled = false;
            if (Trim(asc($("studMk").value).toUpperCase() ) == "Y") {
            	$("schoolCodeContent").style.display="inline";
            } else {
                clearSchoolCodeContent();
            }
            //$("interDictMk").value = "";
        }else if(Trim(asc($("benEvtRel").value)) == "3" || Trim(asc($("benEvtRel").value)) == "5"){
            $("marryDate").disabled = true;
            $("marryDate").value = "";
            $("studMk").disabled = true;
            $("studMk").value = "";
            $("handIcapMk").disabled = true;
            $("handIcapMk").value = "";
            $("interDictMk").disabled = true;
            $("interDictMk").value = "";
            clearSchoolCodeContent();
        }else{
            $("marryDate").disabled = true;
            $("marryDate").value = "";
            $("studMk").disabled = true;
            $("studMk").value = "";
            $("handIcapMk").disabled = false;
            //$("handIcapMk").value = "";
            $("interDictMk").disabled = false;
            clearSchoolCodeContent();
            //$("interDictMk").value = "";
        }
    }

    //變更 每月工作收入 時畫面異動

    function chgMonIncomeMk(){
        //只有 每月工作收入註記=Y 時可以輸入收入金額

        if(Trim(asc($("monIncomeMk").value).toUpperCase()) == "Y"){
            $("monIncome").disabled = false;
        }else{
            $("monIncome").value = "";
            $("monIncome").disabled = true;
        }
    }

    function chgMonIncomeMkFocus(){
        if((Trim(asc($("monIncomeMk").value).toUpperCase()) == "Y") && event.keyCode==9){
            $("monIncome").focus();
        }else if((Trim(asc($("monIncomeMk").value).toUpperCase()) != "Y") && event.keyCode==9){
            $("handIcapMk").focus();
        }
    }

    <%-- 進階欄位驗證 --%>
    <%-- 注意: 此部份之驗證須在 Validation.xml 驗證之後執行 --%>
    <%-- begin ... [ --%>
    function checkFields(){
        var msg = "";

        var benDataSize = <%=((List<SurvivorAnnuityReceiptBenCase>)request.getSession().getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_DATA_LIST)).size()%>
        if(benDataSize==0){
            msg += "請先新增遺屬資料。\r\n"
        }else{
            //檢查事故者申請日期

            if(parseNumber($("appDate").value)<parseNumber("0980101")){
                msg += "「申請日期」不可小於 0980101。\r\n"
                $("appDate").focus();
            }
            //檢查事故者性別、國籍

            <%--2009.11.23修改 國籍別=2時 性別&國籍非必填
            if($("evtNationTpe").value=="2"){
                if($("evtSex").value == ""){
                    msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.survivorAnnuityReceipt.evtSex")%>' />\r\n"
                    $("evtSex").focus();
                }
                if($("evtNationCode").value == ""){
                    msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.survivorAnnuityReceipt.evtNationCode")%>' />\r\n"
                    $("evtNationCode").focus();
                }
            }
            --%>
            //檢查事故者死亡日期

            if(parseNumber($("evtDieDate").value)>parseNumber($("appDate").value)){
                msg += "「事故者死亡日期」不可大於 「事故者申請日期」。\r\n"
                $("evtDieDate").focus();
            }
        }

        //檢核事故者出生日期  是否為數字 及 年月格式
        if($("evtBrDate").value.length < 7 && $("evtBrDate").value != ""){
                    msg += '輸入之「事故者出生日期」格式錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
        }
        if(isNaN($("evtBrDate").value)){
                    msg += '輸入之「事故者出生日期」格式錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
        }
        if($("evtBrDate").value.length == 7){
           var chkMonth = $("evtBrDate").value.substring(3,5);
           var chkDay   = $("evtBrDate").value.substring(5,7);
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「事故者出生日期」格式錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
        }else if($("evtBrDate").value.length == 8){
           var chkMonth = $("evtBrDate").value.substring(4,6);
           var chkDay   = $("evtBrDate").value.substring(6,8);
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「事故者出生日期」格式錯誤，請重新輸入\r\n';
                    $("evtBrDate").focus();
           }
        }

        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }

    function checkBenFields(){
        var msg = "";

		var secondText = $("evtIdnNo").value.substring(1,2);
		if($("evtIdnNo").value.length==10){
		if($("evtNationTpe").value=="2" && $("evtSex").value == "1"){
 			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
 				msg += '身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';
 				 $("evtSex").focus();
    		}
 		}else if($("evtNationTpe").value=="2" && $("evtSex").value == "2"){
 			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
 				msg += '身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';
 				 $("evtSex").focus();
    		}
 		}
		}

		var famText = $("benIdnNo").value.substring(1,2);
		if($("benIdnNo").value.length==10){
		if($("benNationTyp").value=="2" && $("benSex").value == "1"){
 			if(famText!="A" && famText!="a" && famText!="C" && famText!="c" && famText!="8"){
 				msg += '遺屬資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';
 				 $("benSex").focus();
    		}
 		}else if($("benNationTyp").value=="2" && $("benSex").value == "2"){
 			if(famText!="B" && famText!="b" && famText!="D" && famText!="d" && famText!="9"){
 				msg += '遺屬資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」\r\n';
 				 $("benSex").focus();
    		}
 		}
        }
        //檢查遺屬身分證號與生日是否與事故者相同

        if($("benIdnNo").value == $("evtIdnNo").value && $("benBrDate").value == $("evtBrDate").value){
            msg += " 遺屬資料與事故者資料不得重覆。\r\n"
            $("benIdnNo").focus();
        }

        //檢查遺屬性別、國籍

        <%--2009.11.23修改 國籍別=2時 性別&國籍非必填

        if($("benNationTyp").value=="2"){
            if($("benSex").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.survivorAnnuityReceipt.benSex")%>' />\r\n"
                $("benSex").focus();
            }
            if($("benNationCode").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.survivorAnnuityReceipt.benNationCode")%>' />\r\n"
                $("benNationCode").focus();
            }
        }
        --%>
        //2009.11.23修改 國籍別=2時 出生日期為非必填
        if($("benNationTyp").value=="1"){
            if($("benBrDate").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.survivorAnnuityReceipt.benBrDate")%>' />\r\n"
                $("benBrDate").focus();
            }
        }
        //檢查遺屬申請日期
        if(parseNumber($("benAppDate").value)<parseNumber("0980101")){
            msg += "「遺屬申請日期」不可小於 0980101。\r\n"
            $("benAppDate").focus();
        }
        //檢查通訊地址
        if($("commTyp").value=="2"){
            if($("commZip").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.survivorAnnuityReceipt.commZip")%>' />\r\n"
                $("commZip").focus();
            }
            if($("commAddr").value == ""){
                msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.survivorAnnuityReceipt.commAddr")%>' />\r\n"
                $("commAddr").focus();
            }
        }
        // 檢查手機複驗
        if($("mobilePhone").disabled==false){
            var tel1 = $("tel1").value;
            var tel2 = $("tel2").value;
            var shortTel1 = "";
            var shortTel2 = "";
            if(tel1.length >=2){
                shortTel1 = tel1.substring(0,2);
            }
            if(tel2.length >=2){
                shortTel2 = tel2.substring(0,2);
            }

            if(Trim($("mobilePhone").value)==""){
                msg += "「手機複驗」為必填欄位。\r\n"
                $("mobilePhone").focus();
            }else if(($("mobilePhone").value).length != 10 || !((shortTel1=="09" && $("mobilePhone").value==$("tel1").value)||(shortTel2=="09" && $("mobilePhone").value==$("tel2").value))){
                msg += "「手機複驗」輸入有誤，請檢查。\r\n"
                $("mobilePhone").focus();
            }
        }
        //檢查結婚日期
        //if($("benEvtRel").value=="2"){
        //    if($("marryDate").value == ""){
        //       msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.required" arg0='<%=baResBundle.getMessage("label.receipt.survivorAnnuityReceipt.marryDate")%>' />\r\n"
        //        $("marryDate").focus();
        //    }
        //}

        //檢查帳號
        if($("payCategory1").checked==true){
            if(Trim($("payTyp").value) == ""){
                msg += '「給付方式」：為必填欄位。\r\n';
                $("payTyp").focus();
            }else{
                if (Trim($("payTyp").value) == "1" || Trim($("payTyp").value) == "2"){
                    var payBankIdBranchId = Trim($("payBankId").value) + Trim($("branchId").value);
                    var payEeacc = Trim($("payEeacc").value);

                    if(Trim($("payBankId").value) == "" || Trim($("branchId").value) == "" || Trim($("payEeacc").value) == ""){
                        msg += '「帳號」：為必填欄位。\r\n';
                        $("payBankId").focus();
                    }
                    else if(isNaN($("payBankId").value) || isNaN($("branchId").value) || isNaN($("payEeacc").value)){
                        if(isNaN($("payBankId").value) || isNaN($("branchId").value)){
                            msg += '「帳號(前)」：必須為數字。\r\n';
                            $("payBankId").focus();
                        }
                        if(isNaN($("payEeacc").value)){
                            msg += '「帳號(後)」：必須為數字。\r\n';
                            $("payEeacc").focus();
                        }
                    }
                    else{
                        if (Trim($("payTyp").value) == "1" && payBankIdBranchId.substr(0,3) == "700"){
                            msg += '「帳號前三碼」：不可為700。\r\n';
                            $("payBankId").focus();
                        }
                        if (Trim($("payTyp").value) == "2" && payBankIdBranchId != "7000010" && payBankIdBranchId != "7000021"){
                            msg += '「帳號(前)」：限定只能輸入700-0010或700-0021。\r\n';
                            $("payBankId").focus();
                        }
                    }
                }
        //帳號複驗檢核
         if (Trim($("payTyp").value) == "1" || Trim($("payTyp").value) == "2"){
            var chkPayBankIdChkBranchId = Trim($("chkPayBankId").value) + Trim($("chkBranchId").value);
            var chkPayEeacc = Trim($("chkPayEeacc").value);

            if(Trim($("chkPayBankId").value) == "" || Trim($("chkBranchId").value) == "" || Trim($("chkPayEeacc").value) == ""){
                msg += '「帳號複驗」：為必填欄位。\r\n';
                $("chkPayBankId").focus();
            }
            else if(isNaN($("chkPayBankId").value) || isNaN($("chkBranchId").value) || isNaN($("chkPayEeacc").value)){
                if(isNaN($("chkPayBankId").value) || isNaN($("chkPayBankId").value)){
                    msg += '「帳號複驗(前)」：必須為數字。\r\n';
                    $("chkPayBankId").focus();
                }
                if(isNaN($("chkPayEeacc").value)){
                    msg += '「帳號複驗(後)」：必須為數字。\r\n';
                    $("chkPayEeacc").focus();
                }
            }
            else{
                if (Trim($("payTyp").value) == "1" && chkPayBankIdChkBranchId.substr(0,3) == "700"){
                    msg += '「帳號複驗前三碼」：不可為700。\r\n';
                    $("chkPayBankId").focus();
                }
                if (Trim($("payTyp").value) == "2" && chkPayBankIdChkBranchId != "7000010" && chkPayBankIdChkBranchId != "7000021"){
                    msg += '「帳號複驗(前)」：限定只能輸入700-0010或700-0021。\r\n';
                    $("chkPayBankId").focus();
                }

            }
        }
        //帳號複驗
        if(payBankIdBranchId != chkPayBankIdChkBranchId){
                    msg += '「帳號(前)及帳號複驗(前)不一致，請重新輸入」\r\n';
                    $("payBankId").focus();
        }
        if(payEeacc != chkPayEeacc){
                    msg += '「帳號(後)及帳號複驗(後)不一致，請重新輸入」\r\n';
                    $("payBankId").focus();
        }
            }
        }
        else if($("payCategory2").checked==true){
            if($('accData').value == ""){
                msg += '「具領人下拉選單」：為必填欄位。\r\n';
                $("accData").focus();
            }
        }

        //檢核事故者出生日期  是否為數字 及 年月格式
        if($("benBrDate").value.length < 7 && $("benBrDate").value != ""){
                    msg += '輸入之「遺屬出生日期」格式錯誤，請重新輸入\r\n';
                    $("benBrDate").focus();
        }
        if(isNaN($("benBrDate").value)){
                    msg += '輸入之「遺屬出生日期」格式錯誤，請重新輸入\r\n';
                    $("benBrDate").focus();
        }
        if($("benBrDate").value.length == 7){
           var chkMonth = $("benBrDate").value.substring(3,5);
           var chkDay   = $("benBrDate").value.substring(5,7);
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「遺屬出生日期」格式錯誤，請重新輸入\r\n';
                    $("benBrDate").focus();
           }
        }else if($("benBrDate").value.length == 8){
           var chkfrist = $("benBrDate").value.substring(0,1);
           var chkMonth = $("benBrDate").value.substring(4,6);
           var chkDay   = $("benBrDate").value.substring(6,8);
           if(chkfrist != "-"){
                    msg += '輸入之「遺屬出生日期」格式錯誤，請重新輸入\r\n';
                    $("benBrDate").focus();
           }
           if(chkMonth > 12 || chkDay > 32){
                    msg += '輸入之「遺屬出生日期」格式錯誤，請重新輸入\r\n';
                    $("benBrDate").focus();
           }
        }

        if(msg != ""){
            alert(msg);
            return false;
        }else{
            return true;
        }
    }
    <%-- ] ... end --%>


    <%-- 所有畫面清空重設 --%>
    function resetAll(){
        cleanForm();
        initAll();
    }

    //新增/修改眷屬資料保留已輸入之事故者資料

    function saveTempEvtData(){
        $("tempApNo1").value = $("apNo1").value;
        $("tempApNo2").value = $("apNo2").value;
        $("tempApNo3").value = $("apNo3").value;
        $("tempApNo4").value = $("apNo4").value;
        $("tempApUbno").value = $("apUbno").value;
        $("tempAppDate").value = $("appDate").value;
        $("tempEvtNationTpe").value = $("evtNationTpe").value;
        $("tempEvtDieDate").value = $("evtDieDate").value;
        $("tempEvtSex").value = $("evtSex").value;
        $("tempEvtNationCode").value = $("evtNationCode").value;
        $("tempEvtNationCodeOption").value = $("evtNationCodeOption").value;
        $("tempEvtName").value = $("evtName").value;
        $("tempEvtIdnNo").value = $("evtIdnNo").value;
        $("tempEvtBrDate").value = $("evtBrDate").value;
        $("tempEvAppTyp").value = $("evAppTyp").value;
        $("tempEvTyp").value = $("evTyp").value;
        $("tempApItem").value = $("apItem").value;
    }

    //將第一筆遺屬資料填入為預設代理人資料

    function setDefaultGrdData(){
        var listSize = '<%=((List)request.getSession().getAttribute(ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_DATA_LIST)).size()%>';
        if(Trim(asc($("defaultGrdData").value)).toUpperCase() == "Y" && listSize>0){
            $("grdName").value = '<c:out value="${SurvivorAnnuityReceiptBenDataList[0].benName}" />';
            $("grdIdnNo").value = '<c:out value="${SurvivorAnnuityReceiptBenDataList[0].benIdnNo}" />';
            $("grdBrDate").value = '<c:out value="${SurvivorAnnuityReceiptBenDataList[0].benBrDateStr}" />';
        }
    }

    //手機複驗
    function enableMobilePhone(){
        var tel1 = $("tel1").value;
        var tel2 = $("tel2").value;
        var shortTel1 = "";
        var shortTel2 = "";
        if(tel1.length >=2){
            shortTel1 = tel1.substring(0,2);
        }
        if(tel2.length >=2){
            shortTel2 = tel2.substring(0,2);
        }

        if((shortTel1=="09" && tel1.length==10) || (shortTel2=="09" && tel2.length==10)){
            $("mobilePhone").disabled = false;
            //$("mobilePhone").value = "";
        }else{
            $("mobilePhone").disabled = true;
            $("mobilePhone").value = "";
        }
    }

	// Added by JohnsonHuang 20200115 [Begin]
    //外國人身分證號碼自動帶入(個人)
    function autoForeignEvtSex(){
    	var secondText = $("evtIdnNo").value.substring(1,2);
		if($("evtIdnNo").value.length==10){
    	if($("evtNationTpe").value=="2" && Trim($("evtSex").value)==""){
    		if(secondText=="A" || secondText=="a" || secondText=="C" || secondText=="c" || secondText=="8"){
    			$("evtSex").value = "1";
    		}else if(secondText=="B" || secondText=="b" || secondText=="D" || secondText=="d" || secondText=="9"){
    			$("evtSex").value = "2";
    		}else{
    			$("evtSex").value = "";
    			alert('「不符合外來人口統一證號編碼原則，請輸入正確身分證」');
    		}
    	}else{
    		if($("evtNationTpe").value=="2" && $("evtSex").value == "1"){
     			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
     				alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}else if($("evtNationTpe").value=="2" && $("evtSex").value == "2"){
     			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
     				alert('身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}
    	}
		}
    }

    //外國人身分證號碼自動帶入(遺屬)
    function autoForeignBenSex(){
    	var secondText = $("benIdnNo").value.substring(1,2);
		if($("benIdnNo").value.length==10){
    	if($("benNationTyp").value=="2" && Trim($("benSex").value)==""){
    		if(secondText=="A" || secondText=="a" || secondText=="C" || secondText=="c" || secondText=="8"){
    			$("benSex").value = "1";
    		}else if(secondText=="B" || secondText=="b" || secondText=="D" || secondText=="d" || secondText=="9"){
    			$("benSex").value = "2";
    		}else{
    			$("benSex").value = "";
    			alert('遺屬資料「不符合外來人口統一證號編碼原則，請輸入正確身分證」');
    		}
    	}else{
    		if($("benNationTyp").value=="2" && $("benSex").value == "1"){
     			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
     				alert('遺屬資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}else if($("benNationTyp").value=="2" && $("benSex").value == "2"){
     			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
     				alert('遺屬資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
        		}
     		}
    	}
		}
    }

	// 學校代碼下拉選單變動
    function changeSchoolCodeOption(){
    	$("schoolCode").value = $("schoolCodeOption").value;    
	}	

	// 學校代碼下拉選單變動
    function changeSchoolCode(){
    	$("schoolCodeOption").value = $("schoolCode").value;    
	}

    <%-- 學校代碼查詢 --%>
    function doQuerySchool(){
        var argsObj = new Object();
        
        argsObj.schoolCode = $F("schoolCode");

        var res = window.showModalDialog('<c:url value="/bamo0d07/bamo0d076q.jsp"/>', argsObj, 'dialogWidth:560px;dialogHeight:500px;status:no');
        
        if (res != null) {
            $("schoolCode").value = res.schoolCode;
            $("schoolCodeOption").value = res.schoolCode;		        
        }
    }        	

    // 在學變動
    function chgStud() {
        if (Trim(asc($("studMk").value).toUpperCase() ) == "Y") {
        	$("schoolCodeContent").style.display="inline";
        } else {
        	clearSchoolCodeContent();
        }
    }

    function clearSchoolCodeContent() {
        $("schoolCodeContent").style.display="none";
        $("schoolCode").value = '';
        $("schoolCodeOption").selectedIndex = 0;
    }




 	// Added by EthanChen 20200115 [End]
    Event.observe(window, 'load', initAll, false);
    Event.stopObserving(window, 'load', inputFieldFocus);
    </script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <fieldset>
            <legend>&nbsp;遺屬年金批次受理作業&nbsp;</legend>

            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <html:form action="/survivorAnnuityReceiptInsert" method="post" onsubmit="return validateSurvivorAnnuityReceiptForm(this);">
                    <html:hidden styleId="page" property="page" value="1" />
                    <html:hidden styleId="method" property="method" value="" />
                    <html:hidden styleId="bafamilytempId" property="bafamilytempId" value="<%=CaseSessionHelper.getSurvivorAnnuityReceiptBafamilytempIdStr(request) %>"/>
                    <html:hidden styleId="actionTyp" property="actionTyp" value="insertMode"/>
                    <html:hidden styleId="sysCode" property="sysCode" value="AA"/>

                    <tr>
                        <td align="right" colspan="8">
                            <acl:checkButton buttonName="btnConfirm">
                                <input tabindex="450" type="button" name="btnConfirm" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doInsert'; if(document.SurvivorAnnuityReceiptForm.onsubmit() && checkFields() && isValidEvtDateTrue()){document.SurvivorAnnuityReceiptForm.submit();}else{return false;}" />&nbsp;
                            </acl:checkButton>
                            <acl:checkButton buttonName="btnClear">
                                <input tabindex="460" type="button" name="btnClear" class="button" value="清　除" onclick="resetAll();"/>&nbsp;
                            </acl:checkButton>
                            <acl:checkButton buttonName="btnBack">
                                <input tabindex="470" type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBack'; document.SurvivorAnnuityReceiptForm.submit();"/>
                            </acl:checkButton>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8">
                            <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                                <tr>
                                    <td>
                                        <span class="issuetitle_L_down">受理編號：</span>
                                        <html:text tabindex="2" property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))"/>
                                        &nbsp;-
                                        <html:text tabindex="4" property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo2'), $('apNo3'))"/>
                                        &nbsp;-
                                        <html:text tabindex="6" property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" onkeyup="autotab($('apNo3'), $('apNo4'))"/>
                                        &nbsp;-
                                        <html:text tabindex="8" property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value);" />
                                    </td>
                                    <td>
                                        <span class="issuetitle_L_down">申請單位保險證號：</span>
                                        <html:text tabindex="10" property="apUbno" styleId="apUbno" styleClass="textinput" size="9" maxlength="9" onblur="this.value=asc(this.value).toUpperCase();"/>
                                    </td>
                                    <td>
                                        <span class="needtxt">＊</span><span class="issuetitle_R_down">申請日期：</span>
                                        <html:text tabindex="20" property="appDate" styleId="appDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value).toUpperCase();"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td align="center" class="table1" colspan="8">
                            <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                                <tr>
                                    <td colspan="2" class="issuetitle_L">
                                        <span class="systemMsg">▲</span>&nbsp;個人資料
                                    </td>
                                </tr>
                                <tr>
                                    <td width="50%" id="iss">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">國籍別：</span>
                                        <html:text tabindex="30" property="evtNationTpe" styleId="evtNationTpe" styleClass="textinput" size="1" maxlength="1" onkeyup="changeEvtNationTpe();" onkeypress="" onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">(1-本國，2-外籍)</span>
                                    </td>
                                    <td id="iss" width="50%">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">死亡日期：</span>
                                        <html:text tabindex="40" property="evtDieDate" styleId="evtDieDate" styleClass="textinput" size="7" maxlength="7" onblur="this.value=asc(this.value);"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　性　別：</span>
                                        <html:text tabindex="50" property="evtSex" styleId="evtSex" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">(外籍者請填寫，1-男，2-女)</span>
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　國　籍：</span>
                                        <html:text tabindex="60" property="evtNationCode" styleId="evtNationCode" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value);"/>
                                        <label>
                                            <html:select tabindex="70" property="evtNationCodeOption" onchange="$('evtNationCode').value=$('evtNationCodeOption').value;">
                                                <html:option value="">請選擇</html:option>
                                                <html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />
                                            </html:select>
                                        </label>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="iss">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">事故者姓名：</span>
                                        <html:text tabindex="80" property="evtName" styleId="evtName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">
                                            (&nbsp;戶籍姓名<html:text property="cvldtlName" styleId="cvldtlName" styleClass="textinput" disabled="true" size="50"/>)
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">事故者身分證號：</span>
                                        <html:text tabindex="90" property="evtIdnNo" styleId="evtIdnNo" styleClass="textinput"  size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase(); initCvldtlName();autoForeignEvtSex();"/>
                                    </td>
                                    <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">事故者出生日期：</span>
                                        <html:text tabindex="100" property="evtBrDate" styleId="evtBrDate" styleClass="textinput" size="8" maxlength="8" onblur="this.value=asc(this.value); initCvldtlName();"/>
                                    </td>
                                </tr>
                                <tr>
                                	<td id="iss">
                                    	<span class="needtxt">＊</span><span class="issuetitle_L_down">申請傷病分類：</span>
                                        <html:text tabindex="110" property="evAppTyp" styleId="evAppTyp" styleClass="textinput"  size="1" maxlength="1"
                                        	onblur="this.value=asc(this.value);"/>
                                    	<span class="formtxt">(3-普通傷害，4-普通疾病)</span>
                                    </td>
                                    <td id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">核定傷病分類：</span>
                                        <html:text tabindex="111" property="evTyp" styleId="evTyp" styleClass="textinput"  size="1" maxlength="1"
                                        	onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">(3-普通傷害，4-普通疾病)</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="iss"><span class="needtxt">＊</span><span class="issuetitle_L_down">申請項目：</span>
                                        <html:text tabindex="120" property="apItem" styleId="apItem" styleClass="textinput"  size="1" maxlength="1" onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">(4-遺屬年金加喪葬津貼，5-遺屬年金，7-領取失能年金期間死亡，8-領取老年年金期間死亡)</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </html:form>

                <html:form action="/survivorAnnuityReceiptInsertBen" method="post" onsubmit="return validateSurvivorAnnuityReceiptBenForm(this);">
                    <html:hidden styleId="benPage" property="page" value="1" />
                    <html:hidden styleId="benMethod" property="method" value="" />
                    <html:hidden styleId="benBafamilytempId" property="bafamilytempId" value="<%=CaseSessionHelper.getSurvivorAnnuityReceiptBafamilytempIdStr(request) %>"/>
                    <html:hidden styleId="accSeqNo" property="accSeqNo"/>
                    <html:hidden styleId="benActionTyp" property="actionTyp" value="insertMode"/>
                    <html:hidden styleId="benOptionMode" property="benOptionMode" value=""/>
                    <html:hidden styleId="seqNo" property="seqNo"/>
                    <html:hidden styleId="dataIdx" property="dataIdx"/>
                    <%--for 暫存以輸入之事故者資料 [--%>
                    <html:hidden styleId="tempApNo1" property="tempApNo1" value=""/>
                    <html:hidden styleId="tempApNo2" property="tempApNo2" value=""/>
                    <html:hidden styleId="tempApNo3" property="tempApNo3" value=""/>
                    <html:hidden styleId="tempApNo4" property="tempApNo4" value=""/>
                    <html:hidden styleId="tempApUbno" property="tempApUbno" value=""/>
                    <html:hidden styleId="tempAppDate" property="tempAppDate" value=""/>
                    <html:hidden styleId="tempEvtNationTpe" property="tempEvtNationTpe" value=""/>
                    <html:hidden styleId="tempEvtDieDate" property="tempEvtDieDate" value=""/>
                    <html:hidden styleId="tempEvtSex" property="tempEvtSex" value=""/>
                    <html:hidden styleId="tempEvtNationCode" property="tempEvtNationCode" value=""/>
                    <html:hidden styleId="tempEvtNationCodeOption" property="tempEvtNationCodeOption" value=""/>
                    <html:hidden styleId="tempEvtName" property="tempEvtName" value=""/>
                    <html:hidden styleId="tempEvtIdnNo" property="tempEvtIdnNo" value=""/>
                    <html:hidden styleId="tempEvtBrDate" property="tempEvtBrDate" value=""/>
                    <html:hidden styleId="tempEvAppTyp" property="tempEvAppTyp" value=""/>
                    <html:hidden styleId="tempEvTyp" property="tempEvTyp" value=""/>
                    <html:hidden styleId="tempApItem" property="tempApItem" value=""/>
                    <%-- ]for 暫存以輸入之事故者資料 --%>

                    <tr>
                        <td align="center" class="table1" colspan="8">
                            <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                                <tr>
                                    <td colspan="2" class="issuetitle_L">
                                        <table width="100%" class="px13">
                                            <tr>
                                                <td align="left" style=" font:bold 0.95em/1.5em;color: #333333;line-height: 2em;">
                                                    <span class="systemMsg">▲</span>&nbsp;遺屬資料
                                                    <font color="red">
                                                        <div id="insertModeStr" style="display:inline">(新增模式)</div>
                                                        <div id="updateModeStr" style="display:none">(修改模式　遺屬資料序號：<span id="dataIndex"></span>)</div>
                                                    </font>
                                                </td>
                                                <td align="right">
                                                    <acl:checkButton buttonName="btnInsertBen">
                                                        <div id="insertModeBtn" style="display:inline">
                                                            <input tabindex="420" type="button" name="btnInsertBen" class="button" value="新增遺屬" onclick="$('benPage').value='1'; $('benMethod').value='doInsertBenData'; if (document.SurvivorAnnuityReceiptBenForm.onsubmit() && checkBenFields() && isValidBenDateTrue()){saveTempEvtData(); document.SurvivorAnnuityReceiptBenForm.submit();}else{return false;}" />&nbsp;&nbsp;
                                                        </div>
                                                    </acl:checkButton>
                                                    <acl:checkButton buttonName="btnUpdateBen">
                                                        <div id="updateModeBtn" style="display:none">
                                                            <input tabindex="430" type="button" name="btnUpdateBen" class="button" value="修改遺屬" onclick="$('benPage').value='1'; $('benMethod').value='doUpdateBenData'; if (document.SurvivorAnnuityReceiptBenForm.onsubmit() && checkBenFields() && isValidBenDateTrue()){saveTempEvtData(); document.SurvivorAnnuityReceiptBenForm.submit();}else{return false;}" />&nbsp;&nbsp;
                                                        </div>
                                                    </acl:checkButton>
                                                    <acl:checkButton buttonName="btnClearBen">
                                                        <input tabindex="440" type="button" name="btnClearBen" class="button" value="清除遺屬" onclick="cleanBenData();$('benOptionMode').value='insertMode';" />&nbsp;&nbsp;
                                                    </acl:checkButton>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="iss">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">國籍別：</span>
                                        <html:text tabindex="130" property="benNationTyp" styleId="benNationTyp" styleClass="textinput"  size="1" maxlength="1" onkeyup="changeBenNationTyp();" onkeypress="chgBenNationTypFocus();" onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">(1-本國，2-外籍)</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss" width="50%">
                                        <span class="issuetitle_L_down">　性　別：</span>
                                        <html:text tabindex="140" property="benSex" styleId="benSex" styleClass="textinput"  size="1" maxlength="1" onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">(外籍者請填寫，1-男，2-女)</span>
                                    </td>
                                    <td id="iss" width="50%">
                                        <span class="issuetitle_L_down">　國　籍：</span>
                                        <html:text tabindex="150" property="benNationCode" styleId="benNationCode" styleClass="textinput"  size="3" maxlength="3" onblur="this.value=asc(this.value);" />
                                        <html:select tabindex="160" property="benNationCodeOption" onchange="$('benNationCode').value=$('benNationCodeOption').value;">
                                            <html:option value="">請選擇</html:option>
                                            <html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />
                                        </html:select>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">遺屬姓名：</span>
                                        <html:text tabindex="170" property="benName" styleId="benName" styleClass="textinput"  size="20" maxlength="50" onblur="this.value=asc(this.value);"/>
                                    </td>
                                    <td id="iss">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">遺屬申請日期：</span>
                                        <html:text tabindex="180" property="benAppDate" styleId="benAppDate" styleClass="textinput"  size="7" maxlength="7" onblur="this.value=asc(this.value);" onfocus="if($('benOptionMode').value=='insertMode'&& Trim(this.value)==''){this.value=$('appDate').value}"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">遺屬身分證號：</span>
                                        <html:text tabindex="190" property="benIdnNo" styleId="benIdnNo" styleClass="textinput"  size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();autoForeignBenSex();" />
                                    </td>
                                    <td id="iss">
                                        <span class="needtxt">　</span>
                                        <span class="issuetitle_L_down">遺屬出生日期：</span>
                                        <html:text tabindex="200" property="benBrDate" styleId="benBrDate" styleClass="textinput"  size="8" maxlength="8" onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">(本國者，須填寫)</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="iss">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">關　係：</span>
                                        <html:text tabindex="210" property="benEvtRel" styleId="benEvtRel" styleClass="textinput"  size="1" maxlength="1" onkeyup="chgBenEvtRel();" onblur="this.value=asc(this.value);chgBenEvtRel();"/>
                                        <span class="formtxt">(2-配偶，3-父母，4-子女，5-祖父母，6-兄弟姊妹，7-孫子女，O-其它)</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　電　話1：</span>
                                        <html:text tabindex="220" property="tel1" styleId="tel1" styleClass="textinput"  size="20" maxlength="20" onblur="this.value=asc(this.value); enableMobilePhone();"/>
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　電　話2：</span>
                                        <html:text tabindex="230" property="tel2" styleId="tel2" styleClass="textinput"  size="20" maxlength="20" onblur="this.value=asc(this.value); enableMobilePhone();"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="iss">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">地　址：</span>
                                        <html:text tabindex="240" property="commTyp" styleId="commTyp" styleClass="textinput"  size="1" maxlength="1" onkeyup="changeCommTyp();" onkeypress="chgCommTypFocus();" onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">(1-同戶籍地，2-現住址)　
                                                      現住址：<html:text tabindex="250" property="commZip" styleId="commZip" styleClass="textinput"  size="6" maxlength="6" onblur="this.value=asc(this.value);"/>(郵遞區號)&nbsp;-
                                            <html:text tabindex="260" property="commAddr" styleId="commAddr" styleClass="textinput"  size="72" maxlength="240" onblur="this.value=asc(this.value);"/>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="iss">
                                        <span class="issuetitle_L_down">　手機複驗：</span>
                                        <html:text tabindex="262" property="mobilePhone" styleId="mobilePhone" styleClass="textinput"  size="10" maxlength="10" onblur="this.value=asc(this.value);"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="iss">
                                        <span class="issuetitle_L_down">　法定代理人預設遺屬序1：</span>
                                        <logic:empty name="<%=ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_DATA_LIST%>">
                                            <html:text tabindex="265" property="defaultGrdData" styleId="defaultGrdData" styleClass="textinput"  size="1" maxlength="1" disabled="true"/>
                                        </logic:empty>
                                        <logic:notEmpty name="<%=ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_DATA_LIST%>">
                                            <html:text tabindex="265" property="defaultGrdData" styleId="defaultGrdData" styleClass="textinput"  size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase(); setDefaultGrdData();"/>
                                        </logic:notEmpty>
                                        <span class="formtxt">(Y-預設帶入遺屬序1的法定代理人相關欄位資料)</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="iss">
                                        <span class="issuetitle_L_down">　法定代理人姓名：</span>
                                        <html:text tabindex="270" property="grdName" styleId="grdName" styleClass="textinput"  size="50" maxlength="50" onblur="this.value=asc(this.value);"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　法定代理人身分證號：</span>
                                        <html:text tabindex="280" property="grdIdnNo" styleId="grdIdnNo" styleClass="textinput"  size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();"/>
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　法定代理人出生日期：</span>
                                        <html:text tabindex="290" property="grdBrDate" styleId="grdBrDate" styleClass="textinput"  size="8" maxlength="8" onblur="this.value=asc(this.value);"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　結婚日期：</span>
                                        <html:text tabindex="300" property="marryDate" styleId="marryDate" styleClass="textinput"  size="7" maxlength="7" onblur="this.value=asc(this.value);"/>
                                        <span class="formtxt">(僅配偶須填寫)</span>
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　在　學：</span>
                                        <html:text tabindex="310" property="studMk" styleId="studMk" styleClass="textinput"  size="1" maxlength="1" onkeyup="chgStud();" onblur="this.value=asc(this.value).toUpperCase();"/>
                                        <span class="formtxt">(在學者，請輸入Y)&nbsp;</span>
                                        <span id="schoolCodeContent">
                                            <span class="issuetitle_L_down">學校代碼：</span>
                                            <html:text property="schoolCode" styleId="schoolCode" styleClass="textinput" size="7" maxlength="4" onblur="this.value=asc(this.value);changeSchoolCode();" />
                                            <html:select property="schoolCodeOption" styleId="schoolCodeOption" styleClass="formtxt" tabindex="14" onchange="changeSchoolCodeOption();">
                                                <html:option value="">請選擇</html:option>
                                                <html:options collection="<%=ConstantKey.SCHOOLCODE_OPTION_LIST%>" property="codeNo" labelProperty="codeString" />
                                            </html:select>
                                            <input name="btnQuerySchool" type="button" class="button_120" value="學校名稱查詢" onclick="doQuerySchool();">
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">　每月工作收入：</span>
                                        <html:text tabindex="320" property="monIncomeMk" styleId="monIncomeMk" styleClass="textinput"  size="1" maxlength="1" onkeyup="chgMonIncomeMk();" onkeypress="chgMonIncomeMkFocus();" onblur="this.value=asc(this.value).toUpperCase();"/>
                                        <span class="formtxt">(有每月工作收入者，請輸入Y，並填入金額)</span>
                                        <html:text tabindex="330" property="monIncome" styleId="monIncome" styleClass="textinput"  size="7" maxlength="7" onblur="this.value=asc(this.value);"/>元

                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss" colspan="2">
                                        <span class="issuetitle_L_down">　領有重度以上身心障礙手冊：</span>
                                        <html:text tabindex="340" property="handIcapMk" styleId="handIcapMk" styleClass="textinput"  size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();"/>
                                        <span class="formtxt">(領有重度以上身心障礙手冊者，請輸入Y)</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" id="iss">
                                        <span class="issuetitle_L_down">　受禁治產(監護)宣告：</span>
                                        <html:text tabindex="350" property="interDictMk" styleId="interDictMk" styleClass="textinput"  size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();"/>
                                        <span class="formtxt">(受禁治產(監護)宣告者，請輸入Y)</span>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" class="issuetitle_L">
                                        <br><span class="systemMsg">▲</span>&nbsp;給付資料
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss" colspan="2">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">給付方式：</span>
                                        <span class="formtxt">
                                            <html:radio tabindex="360" styleId="payCategory1" property="payCategory" value="1" onclick="chgPayCategory();" onkeypress="chgPayCategoryFocus();" />本人領取
                                            <html:text tabindex="370" property="payTyp" styleId="payTyp" styleClass="textinput"  size="1" maxlength="1" onkeyup="changePayTyp();" onkeypress="chgPayTypFocus();" onblur="this.value=asc(this.value).toUpperCase();tabChange();"/>
                                            <span class="formtxt">(1-匯入銀行帳戶，2-匯入郵局帳號，A-扣押戶)</span>
                                            <html:radio styleId="payCategory2" property="payCategory" value="2" onclick="chgPayCategory();" onkeypress="chgPayCategoryFocus();" />具名領取
                                            <html:select property="accData" >
                                                <html:option value="">請選擇</html:option>
                                                <logic:notEmpty name="<%=ConstantKey.BEN_OPTION_LIST%>">
                                                    <logic:iterate id="benList" name="<%=ConstantKey.BEN_OPTION_LIST%>">
                                                        <html:option value="${benList.bafamilytempId};${benList.accSeqNo}" ><c:out value="${benList.accName}" /></html:option>
                                                    </logic:iterate>
                                                </logic:notEmpty>
                                            </html:select>
                                        </span>
                                    </td>
                                </tr>
                                <tr>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　帳號：</span>
                                        <html:text tabindex="380" property="payBankId" styleId="payBankId" styleClass="textinput" size="1" maxlength="3" onblur="this.value=asc(this.value);"/>&nbsp;-
                                        <html:text tabindex="385" property="branchId" styleId="branchId" styleClass="textinput" size="1" maxlength="4" onblur="this.value=asc(this.value);"/>&nbsp;-
                                        <html:text tabindex="390" property="payEeacc" styleId="payEeacc" styleClass="textinput" size="14" maxlength="14" onblur="if(Trim(asc(this.value))!=''){this.value=leftPad(asc(this.value), 14, '0')};"/>
                                        <html:hidden styleId="payBankIdBranchId" property="payBankIdBranchId"/>
                                    </td>
                                    <td id="iss">
                                        <span class="issuetitle_L_down">　帳號複驗：</span>
                                        <html:text tabindex="400" property="chkPayBankId" styleId="chkPayBankId" styleClass="textinput" size="1" maxlength="3" onblur="this.value=asc(this.value);"/>&nbsp;-
                                        <html:text tabindex="405" property="chkBranchId" styleId="chkBranchId" styleClass="textinput" size="1" maxlength="4" onblur="this.value=asc(this.value);"/>&nbsp;-
                                        <html:text tabindex="410" property="chkPayEeacc" styleId="chkPayEeacc" styleClass="textinput" size="14" maxlength="14" onblur="if(Trim(asc(this.value))!=''){this.value=leftPad(asc(this.value), 14, '0')};"/>
                                        <html:hidden styleId="chkPayBankIdChkBranchId" property="chkPayBankIdChkBranchId"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8" class="issuetitle_L">
                            <span class="issuetitle_search">&#9658;</span>&nbsp;遺屬資料：&nbsp;
                        </td>
                    </tr>
                    <tr align="center">
                        <td>
                            <bean:define id="list" name="<%=ConstantKey.SURVIVOR_ANNUITY_RECEIPT_BEN_DATA_LIST%>" />
                            <display:table name="pageScope.list" id="listItem" pagesize="<%=ConstantKey.LIST_PAGE_SIZE%>">
                                <display:column title="序　號" style="width:7%; text-align:center;">
                                    <c:out value="${listItem_rowNum}" />
                                </display:column>
                                <display:column title="關　係" style="width:10%; text-align:left;">
                                    <c:out value="${listItem.benEvtRelStr}" />&nbsp;
                                </display:column>
                                <display:column title="姓　名" style="width:22%; text-align:left;">
                                    <c:out value="${listItem.benName}" />&nbsp;
                                </display:column>
                                <display:column title="身分證號" style="width:12%; text-align:left;">
                                    <c:out value="${listItem.benIdnNo}" />&nbsp;
                                </display:column>
                                <display:column title="在　學" style="width:10%; text-align:center;">
                                    <c:out value="${listItem.studMkStr}" />&nbsp;
                                </display:column>
                                <display:column title="每月工作收入" style="width:10%; text-align:right;">
                                    <fmt:formatNumber value="${listItem.monIncome}" />&nbsp;
                                </display:column>
                                <display:column title="結婚日期" style="width:13%; text-align:center;">
                                    <c:out value="${listItem.marryDate}" />&nbsp;
                                </display:column>
                                <display:column title="操作區" style="width:16%; text-align:center;">
                                    <acl:checkButton buttonName="btnUpdate">
                                        <input type="button" name="btnUpdate" class="button" value="修　改" onclick="getBenDetailData('<c:out value="${listItem.bafamilytempId}" />','<c:out value="${listItem.seqNo}" />');$('dataIdx').value=<c:out value="${listItem_rowNum}" />;$('dataIndex').innerHTML=<c:out value="${listItem_rowNum}" />;$('benOptionMode').value='updateMode';" />&nbsp;
                                    </acl:checkButton>
                                    <acl:checkButton buttonName="btnDeleteBen">
                                        <input type="button" name="btnDeleteBen" class="button" value="刪　除" onclick="$('benPage').value='1'; $('benMethod').value='doDeleteBenData'; $('seqNo').value='<c:out value="${listItem.seqNo}" />'; if (confirm('<bean:message bundle="<%=Global.BA_MSG%>" key="msg.delete.confirm"/>')){document.SurvivorAnnuityReceiptBenForm.submit();}else{return false;}" />
                                    </acl:checkButton>
                                </display:column>
                            </display:table>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="8"><hr width="100%" size="1px" noshade></td>
                    </tr>
                    <tr>
                        <td colspan="8">
                            <hr size="1" noshade>
                            <span class="titleinput">※注意事項：</span><br>
                                　1.&nbsp;外籍人士請於身分證號欄位填寫護照號碼。<br>
                                　2.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
                                　3.&nbsp;<span class="needtxt">＊</span>為必填欄位。

                        </td>
                    </tr>
                </html:form>
            </table>
        </fieldset>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
