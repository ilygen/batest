<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D083C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <html:javascript formName="PayeeDataUpdateDetailForm" page="1" />
    <script language="javascript" type="text/javascript">
    var today = "<%=DateUtility.getNowChineseDate()%>";
    var Q1 = '<c:out value="${PayeeDataUpdateQueryCaseQ1}" />';
    var Q2 = '<c:out value="${PayeeDataUpdateQueryCaseQ2}" />';
    var accSeqNo = '<c:out value="${_ben_option_list[0].benName}" />';
    var accSeqNoControl = '<c:out value="${PayeeDataUpdateQueryCaseAccSeqNoControl}" />';
    var evtIdnNo = '<c:out value="${PayeeDataUpdateQueryCase[0].evtIdnNo}" />';
    var evtBrDate = '<c:out value="${PayeeDataUpdateQueryCase[0].evtBrDate}" />';
    var benDieDate = '<c:out value="${PayeeDataUpdateQueryCase[0].benDieDate}" />';
    var accRel = '<c:out value="${PayeeDataUpdateDetailForm.accRel}" />';
    var commZipTmp="";
    var commAddrTmp="";
    var bbcmf08Count="";

    //存檔時，判斷給付方式為本人領取中的「1-匯入銀行帳戶」或「2-匯入郵寄帳號」，則出現提示訊息「請確認專戶的設定是否正確」
    function chkSpecialAcc(){
        var payTyp = $("payTyp").value;

        if(payTyp=="1" || payTyp=="2"){
            if($("specialAccAfter").checked == false){

              if($("origPayBankIdBranchId").value != $("payBankId").value + $("branchId").value || $("origPayEeacc").value != $("payEeacc").value){
               if(confirm("如非專戶請按確定，如為專戶請按取消並勾選專戶")){
                  return true;
               }else{
                  return false;
               }
              }else{
                  return true;
              }
            }else{
               return true;
            }
        }else{
             return true;
        }
    }

      <%-- 1030813 payTyp=1時tab跳過0000 --%>
    function tabChange(){

       if (Trim($("payTyp").value) == "1"){
             $("branchId").tabIndex = -1;
       }else{
             $("branchId").tabIndex = 0;
       }

    }

      <%-- 1030813 payTyp=1時tab跳過0000 --%>
    function autoTabChange(){

       if (Trim($("payTyp").value) == "1"){
             autotab($('payBankId'), $('payEeacc'));
       }else{
             autotab($('payBankId'), $('branchId'));
       }

    }

    // 關係下拉選單
    function chkBenEvtRel() {
        //$("appDate").value="";
        //$("appUser").value="";
        //$("mustIssueAmt1").value="";
        //$("mustIssueAmt2").value="";
        // 因隱藏欄位要避掉焦點
        $("appDate").disabled = false;
        $("appUser").disabled = false;
        $("mustIssueAmt1").disabled = false;
        $("mustIssueAmt2").disabled = false;

        // 自然人:1(本人)
        if($("benEvtRel").value=="1"){
            // 繼承人相關欄位
            $("mustIssueAmt1").value="";
            $("mustIssueAmt2").value="";
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="inline";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 國籍別
            $("nationTypContent").style.display="inline";
            $('benNationTyp').value='1';
            document.getElementsByName("benNationTyp")[0].checked=true;
            // 具名領取
            if(Q2 > 0){
                // 若無資料可以選擇時,則「具名領取」的選項及下拉選單隱藏
                if(accSeqNo != '')
                    $("payCategoryContent2").style.display="inline";
                else
                    $("payCategoryContent2").style.display="none";
            } else {
                $("payCategoryContent2").style.display="none";
            }
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 地址
            $("commTyp1").style.display="inline";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";

            // 若「給付主檔.受款人死亡日期(BAAPPBASE.BENDIEDATE)」不為空也不為null時,
            // 則「給付方式」、「帳號」相關欄位及「戶名」等相關欄位的欄位狀態需變成為Disabled或realOnly,不得供User修改異動。
            if(benDieDate!='' && benDieDate!=null){
                $("payTyp").disabled="true";
                $("specialAccAfter").disabled="true";
                $("payBankId").disabled="true";
                $("branchId").disabled="true";
                $("payEeacc").disabled="true";
                $("payAccount").disabled="true";
                $("bankName").disabled="true";
                $("accName").disabled="true";
            }
        }

        // 自然人:2~7
        if($("benEvtRel").value=="2" || $("benEvtRel").value=="3" || $("benEvtRel").value=="4" ||
           $("benEvtRel").value=="5" || $("benEvtRel").value=="6" || $("benEvtRel").value=="7"){
            // 繼承人相關欄位
            $("mustIssueAmt1").value="";
            $("mustIssueAmt2").value="";
            $("relationContent1").style.display="inline";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="inline";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 國籍別
            $("nationTypContent").style.display="inline";
            $('benNationTyp').value='1';
            document.getElementsByName("benNationTyp")[0].checked=true;
            // 具名領取
            if(Q2 > 0){
                // 若無資料可以選擇時,則「具名領取」的選項及下拉選單隱藏
                if(accSeqNo != '')
                    $("payCategoryContent2").style.display="inline";
                else
                    $("payCategoryContent2").style.display="none";
            } else {
                $("payCategoryContent2").style.display="none";
            }
            // 婚姻狀況
            $("marryContent").style.display="inline";
            // 地址
            $("commTyp1").style.display="inline";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";
        }

        // A-Z
        if($("benEvtRel").value=="A"){
            // 繼承人相關欄位
            $("appDate").value="";
            $("appUser").value="";
            $("mustIssueAmt2").value="";
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="inline";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDate").value="";
            $("benBrDateContent").style.display="none";
            // 法定代理人
            $("grdName").value="";
            $("grdBrDate").value="";
            $("grdIdnNo").value="";
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 國籍別
            $("benNationTyp").value="";
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 地址
            $("commTyp1").style.display="none";
            $("commTyp2").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";
        }
        if($("benEvtRel").value=="C"){
            // 繼承人相關欄位
            $("appDate").value="";
            $("appUser").value="";
            $("mustIssueAmt1").value="";
            $("mustIssueAmt2").value="";
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDate").value="";
            $("benBrDateContent").style.display="none";
            // 法定代理人
            $("grdName").value="";
            $("grdBrDate").value="";
            $("grdIdnNo").value="";
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 國籍別
            $("benNationTyp").value="";
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 地址
            $("commTyp1").style.display="none";
            $("commTyp2").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";
        }
        if($("benEvtRel").value=="Z"){
            // 繼承人相關欄位
            $("appDate").value="";
            $("appUser").value="";
            $("mustIssueAmt1").value="";
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="inline";
            // 受款人出生日期
            $("benBrDate").value="";
            $("benBrDateContent").style.display="none";
            // 法定代理人
            $("grdName").value="";
            $("grdBrDate").value="";
            $("grdIdnNo").value="";
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 國籍別
            $("benNationTyp").value="";
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 地址
            $("commTyp1").style.display="none";
            $("commTyp2").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";

            // 申請代算單位
            $("relationContent4").style.display="inline";
            chkOldAplDpt();


        }
        if($("benEvtRel").value=="E"){
            // 繼承人相關欄位
            $("appDate").value="";
            $("appUser").value="";
            $("mustIssueAmt1").value="";
            $("mustIssueAmt2").value="";
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="inline";
            // 法定代理人
            $("grdName").value="";
            $("grdBrDate").value="";
            $("grdIdnNo").value="";
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 國籍別
            $("nationTypContent").style.display="inline";
            $('benNationTyp').value='1';
            document.getElementsByName("benNationTyp")[0].checked=true;
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 地址
            $("commTyp1").style.display="inline";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";
        }
        if($("benEvtRel").value=="O"){
            // 繼承人相關欄位
            $("appDate").value="";
            $("appUser").value="";
            $("mustIssueAmt1").value="";
            $("mustIssueAmt2").value="";
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDate").value="";
            $("benBrDateContent").style.display="none";
            // 法定代理人
            $("grdName").value="";
            $("grdBrDate").value="";
            $("grdIdnNo").value="";
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 國籍別
            $("benNationTyp").value="";
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 地址
            $("commTyp1").style.display="none";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";
        }
        if($("benEvtRel").value=="" || $("benEvtRel").value=="F" || $("benEvtRel").value=="N"){
            // 繼承人相關欄位
            $("appDate").value="";
            $("appUser").value="";
            $("mustIssueAmt1").value="";
            $("mustIssueAmt2").value="";
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDate").value="";
            $("benBrDateContent").style.display="none";
            // 法定代理人
            $("grdName").value="";
            $("grdBrDate").value="";
            $("grdIdnNo").value="";
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 國籍別
            $("benNationTyp").value="";
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 地址
            $("commTyp1").style.display="none";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";
        }

        if($("benEvtRel").value=="F" || $("benEvtRel").value=="N"){
            $("commZip").value = "<%=ConstantKey.LANDBAK_ZIP%>";
            $("commAddr").value = "<%=ConstantKey.LANDBAK_ADDRESS%>";
        }
        else {
            $("commZip").value = "";
            $("commAddr").value = "";
        }

        $("accountInput1").style.display="none";
        $("accountInput2").style.display="none";
        $("accountInput3").style.display="none";
        changeBenNationTpe();
    }

    // 變更 給付方式(本人領取) 時畫面異動
    function changePayType(){
        var payTyp = $("payTyp").value;
        $("payBankId").value="";
        $("branchId").value="";
        $("payAccount").value="";
        $("payEeacc").value="";
        $("bankName").value="";
        $("accName").value="";


        if(payTyp=="1"||payTyp=="2"||payTyp=="7"||payTyp=="8"){
            if(payTyp=="1"||payTyp=="2"){
                $("accName").value = $("benName").value;
                $("specialAccContent").style.display="inline";
            }else{
                $("specialAccContent").style.display="none";
            }
            $("accountInput1").style.display="inline";
            $("accountInput2").style.display="none";
            $("accountInput3").style.display="inline";
        }
        if(payTyp==""||payTyp=="3"||payTyp=="4"||payTyp=="A"){
            $("accName").value = "";
            $("accountInput1").style.display="none";
            $("accountInput2").style.display="none";
            $("accountInput3").style.display="none";
            $("specialAccContent").style.display="none";
        }
        if(payTyp=="5"||payTyp=="6"){
            $("accName").value = "";
            $("accountInput1").style.display="none";
            $("accountInput2").style.display="inline";
            $("accountInput3").style.display="inline";
            $("specialAccContent").style.display="none";
        }
        if(payTyp=="1"){
            $("payBankId").disabled = false;
            $("branchId").value = '0000';
            $("branchId").readOnly = true;
            $("branchId").addClassName('disabledN');
            $("branchId").disabled = false;
            $("payEeacc").disabled = false;
        }
        if(payTyp=="2"){
            $("payBankId").disabled = false;
            $("branchId").disabled = false;
            $("payEeacc").disabled = false;
            $("branchId").readOnly = false;
            $("branchId").removeClassName('disabledN');

            if($("branchId").value == "0000"){
              $("branchId").value = "";
            }

        }
    }

    //變更 通訊地址別 時畫面異動
    function changeCommTyp(){
        //$("commZip").value = "";
        //$("commAddr").value = "";
        addContent.style.display="inline";

        if($("commTyp").value=="1"){
            if($("commZip").value != "")
                commZipTmp = $("commZip").value;
            if($("commAddr").value != "")
                commAddrTmp = $("commAddr").value;
            DWREngine.setAsync(false);
            // 戶籍 郵遞區號地址
            getCvldtlZip();
            getCvldtlAddr();
            //$("commZip").value = '<c:out value="${PayeeDataUpdateDetailForm.commZip}" />';
            //$("commAddr").value = '<c:out value="${PayeeDataUpdateDetailForm.commAddr}" />';

            $("commZip").disabled = true;
            $("commAddr").disabled = true;

            //2010.01.07 added
            document.getElementsByName("commTyp")[0].checked = true;
            if (Trim($("commAddr").value) == "") {//如果沒抓到戶籍地址資料,則跳回現住址並提示使用者
                $("commTyp").value = "2";
                alert('戶籍地住址無資料，請選擇「現住址」並輸入住址資料。\r\n');
                changeCommTyp();
                $("commZip").value = "";
                $("commAddr").value = "";
            }

        }

        if($("commTyp").value=="2"){
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
            if(commZipTmp != "")
                $("commZip").value = commZipTmp;
            if(commAddrTmp != "")
                $("commAddr").value = commAddrTmp;

            //2010.01.07 added
            document.getElementsByName("commTyp")[1].checked = true;
        }
    }

    //變更 婚姻狀況時畫面異動
    function changeMarrMk(){
        if(document.getElementsByName("benMarrMk")[0].checked==true){ // 已婚
            $("grdName").value = "";
            $("grdIdnNo").value = "";
            $("grdBrDate").value = "";
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
        }
        if(document.getElementsByName("benMarrMk")[1].checked==true){ // 未婚
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
        }
    }

    // 變更 國籍別 時畫面異動
    function changeBenNationTpe(){
        $("benSex").value = "";
        $("benNationCode").value = "";
        var frm = document.forms['PayeeDataUpdateDetailForm'];

        if($("benNationTyp").value=="1"){
            document.getElementsByName("benSex")[0].checked=false;
            document.getElementsByName("benSex")[1].checked=false;
			$("sexContent").style.display="none";
            $("nationalityContent").style.display="none";
            frm['commTyp'][0].disabled = false;
        }
        if($("benNationTyp").value=="2"){
            $("sexContent").style.display="inline";
            $("nationalityContent").style.display="inline";
            $("benNationCode").disabled = false;

            frm['commTyp'][0].disabled = true;
            frm['commTyp'][1].checked = true;
            frm['benSex'][0].checked = false;
            frm['benSex'][1].checked = false;
            $('benNationCode').value = '';
            frm['benNationCodeOption'].selectedIndex = 0;
            $("commTyp").value = "2";
            $("commZip").disabled = false;
            $("commAddr").disabled = false;
        }
        autoForeignBenSex();
    }

    // 變更 給付方式 時畫面異動
    function changePayCategory(){
        if($("payCategory").value=="1"){
            $("accSeqNo").value="";
            $("payTyp").disabled = false;
            $("accSeqNo").disabled = true;
        }
        if($("payCategory").value=="2"){
            $("payTyp").value="";
            $("payBankId").value="";
            $("branchId").value="";
            $("payAccount").value="";
            $("payEeacc").value="";
            $("bankName").value="";
            $("accName").value="";

            $("accountInput1").style.display="none";
            $("accountInput2").style.display="none";
            $("accountInput3").style.display="none";

            $("payTyp").disabled = true;
            //$("accSeqNo").disabled = false;
            if(accSeqNoControl == "1"){
                document.getElementsByName("payCategory")[1].disabled = true;
                $("accSeqNo").disabled = true;
            } else {
                document.getElementsByName("payCategory")[1].disabled = false;
                $("accSeqNo").disabled = false;
            }
        }
    }

    // 身分查核年月
    function chkIdnChkNote() {
        if($("idnChkNote1").checked){
            $("idnChkYear").value = "";
            $("idnChkMonth").value = "";
            $("chkForeigner").style.display="none";
        }
        else if($("idnChkNote2").checked){
            $("chkForeigner").style.display="inline";
        }
    }

    // Ajax for 取得 戶籍檔郵遞區號
    function getCvldtlZip() {
        if(($("benIdnNo").value != "") && ($("benBrDate").value != "")){
            updateCommonAjaxDo.getCvldtlZip($("benIdnNo").value, $("benBrDate").value, fillCvldtlZip);
        }
    }

    function fillCvldtlZip(zip) {
        $("commZip").value = zip;
    }

    // Ajax for 取得 戶籍檔地址
    function getCvldtlAddr() {
        if(($("benIdnNo").value != "") && ($("benBrDate").value != "")){
            updateCommonAjaxDo.getCvldtlAddr($("benIdnNo").value, $("benBrDate").value, fillCvldtlAddr);
        }
    }

    function fillCvldtlAddr(addr) {
        $("commAddr").value = addr;
    }

    // Ajax for 取得 Caub 受款人姓名
    function getCaubUname() {
        if($("benIdnNo").value != ""){
            updateCommonAjaxDo.getCaubUname($("benIdnNo").value, fillCaubUname);
        }
    }

    function fillCaubUname(uname) {
        if($("benName").value == "")
            $("benName").value = uname;
    }

    // Ajax for 取得 Caub 郵遞區號
    function getCaubCzpcd() {
        if($("benIdnNo").value != ""){
            updateCommonAjaxDo.getCaubCzpcd($("benIdnNo").value, fillCaubCzpcd);
        }
    }

    function fillCaubCzpcd(czpcd) {
        if($("commZip").value == "")
            $("commZip").value = czpcd;
    }

    // Ajax for 取得 Caub 通訊地址
    function getCaubCaddr() {
        if($("benIdnNo").value != ""){
            updateCommonAjaxDo.getCaubCaddr($("benIdnNo").value, fillCaubCaddr);
        }
    }

    function fillCaubCaddr(caddr) {
        if($("commAddr").value == "")
            $("commAddr").value = caddr;
    }

    // Ajax for 取得 Caub 電話
    function getCaubTel() {
        if($("benIdnNo").value != ""){
            updateCommonAjaxDo.getCaubTel($("benIdnNo").value, fillCaubTel);
        }
    }

    function fillCaubTel(tel) {
        if($("tel1").value == "")
            $("tel1").value = tel;
    }

    function callCaubData() {
        if(($("benEvtRel").value=="A" && $("benIdnNo").value != "") || ($("benEvtRel").value=="C" && $("benIdnNo").value != "")){
            getCaubUname();
            getCaubCzpcd();
            getCaubCaddr();
            getCaubTel();
        }
    }

    // Ajax for 取得 Bbcmf08
    function getBbcmf08Data() {
        if($("benEvtRel").value == "Z"){
            updateCommonAjaxDo.getBbcmf08Data($("benIdnNo").value, fillBbcmf08Data);
        }
    }

    function fillBbcmf08Data(dataCount) {
        bbcmf08Count = dataCount;
    }

    // Ajax for  檢核銀行帳號
    function checkBankAccount() {
    	var payBankIdBranchId = document.PayeeDataUpdateDetailForm.payBankId.value + document.PayeeDataUpdateDetailForm.branchId.value;
        updateCommonAjaxDo.checkBankAccount(payBankIdBranchId, checkBankAccountData);

    }

    function checkBankAccountData(bankData) {
      	var msg = "";
        if(!bankData)
      			 msg += "<bean:message bundle="<%=Global.BA_MSG%>" key="errors.bankId.error"/>\r\n"
      	    if (msg != "") {
      	    	alert(msg);
            	return false;
        }
        else {
                  document.PayeeDataUpdateDetailForm.submit();
        }
    }

    <%-- begin 檢查必填欄位 --%>
    function checkRequireFields() {
        var nowDate = "<%=DateUtility.getNowChineseDate()%>";
        var nowDateYM = nowDate.substring(0,5);
        var brDate = '<c:out value="${PayeeDataUpdateDetailForm.benBrDate}" />';
        var benBrDate = calDay($("benBrDate").value,0);
        var sYearEighteen = '<%=DateUtility.calYear( DateUtility.getNowChineseDate().substring(0, 5).concat( String.valueOf(DateUtility.lastDay(DateUtility.getNowChineseDate())) ) ,-18)%>';
        var msg = "";


		var secondText = $("benIdnNo").value.substring(1,2);
		if($("benIdnNo").value.length==10){
		if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[0].checked==true){
 			if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
 				//msg += '個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」。\r\n';
 				//$("benSex").focus();
 				alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」。\r\n');
    		}
 		}else if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[1].checked==true){
 			if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
 				//msg += '個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」。\r\n';
 				//$("benSex").focus();
 				alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」。\r\n');
    		}
 		}
		}

        if (Trim($("benEvtRel").value) == "1" || Trim($("benEvtRel").value) == "2" || Trim($("benEvtRel").value) == "3" ||
            Trim($("benEvtRel").value) == "4" || Trim($("benEvtRel").value) == "5" ||
            Trim($("benEvtRel").value) == "6" || Trim($("benEvtRel").value) == "7"){

            if(Trim($("benBrDate").value) == ""){
                msg += '「受款人出生日期」：為必輸欄位。\r\n';
            } else {
                if(Trim($("benBrDate").value) > nowDate)
                    msg += '「受款人出生日期」：不得大於系統日期。\r\n';
            }

            if(Trim($("benIdnNo").value) == "")
                msg += '「受款人身分證號(保險證號)」：為必輸欄位。\r\n';

            if(document.getElementsByName("benNationTyp")[0].checked==false && document.getElementsByName("benNationTyp")[1].checked==false)
                msg += '「國籍別」：為必輸欄位。\r\n';

	         if (document.getElementsByName("benNationTyp")[0].checked==true){
	            if(!isValidIdNoForTest($("benIdnNo").value) || !chkPID_CHAR($("benIdnNo").value) )
	            	msg +='「受款人身分證號(保險證號)」輸入有誤，請輸入長度為10 碼且符合格式的資料。\r\n';


	        }
            if (document.getElementsByName("benNationTyp")[1].checked==true){
                if(document.getElementsByName("benSex")[0].checked==false && document.getElementsByName("benSex")[1].checked==false)
                    msg += '「性別」：為必輸欄位。\r\n';
                if(Trim($("benNationCode").value) == "")
                    msg += '「國籍」：為必輸欄位。\r\n';
                if(Trim($("benNationCode").value) == "000")
	                msg +='「國籍別」為外籍不得輸入中華民國之國籍代碼。\r\n';
	            if(Trim($("benNationCode").value) != "" && !isNationCode($("benNationCode").value))
	                msg += '輸入「國籍」錯誤，請重新確認。\r\n';
	            if(!isEngNum($("benIdnNo").value))
	            	msg +='「受款人身分證號(保險證號)」格式錯誤。\r\n';
            }

            if (Trim($("benEvtRel").value) != "1"){
                if(Trim($("appDate").value) == ""){
                    msg += '「繼承人申請日期」：為必輸欄位。\r\n';
                } else {
                    if($("appDate").value > nowDate)
                        msg += '「繼承人申請日期」：不得大於系統日期。\r\n';
                    if($("appDate").value < "0980101")
                        msg += '「繼承人申請日期」：不得小於0980101。\r\n';
                }

                if(Trim($("appUser").value) == "")
                    msg += '「繼承自受款人」：為必輸欄位。\r\n';

                // 未滿18歲以下為必填，超過18歲可不填，生日前一天及算成年
                if(benBrDate > sYearEighteen){
                    if(Trim($("benMarrMk").value) == "")
                        msg += '「婚姻狀況」：為必輸欄位。\r\n';

                    if (document.getElementsByName("benMarrMk")[1].checked==true){
                        if(Trim($("grdName").value) == "")
                            msg += '「法定代理人姓名」：為必輸欄位。\r\n';
                        if(Trim($("grdIdnNo").value) == "")
                            msg += '「法定代理人身分證號」：為必輸欄位。\r\n';
                        if(Trim($("grdBrDate").value) == "")
                           msg += '「法定代理人出生日期」：為必輸欄位。\r\n';

                        // 法定代理人與受款人比對
                        if((Trim($("grdIdnNo").value)+Trim($("grdBrDate").value)) == (Trim($("benIdnNo").value)+Trim($("benBrDate").value)))
                            msg += '「法定代理人」的身份證號與生日不得與受款人或事故者相同。\r\n';
                        // 法定代理人與事故者比對
                        if((Trim($("grdIdnNo").value)+Trim($("grdBrDate").value)) == (Trim(evtIdnNo)+changeDateType(Trim(evtBrDate))))
                            msg += '「法定代理人」的身份證號與生日不得與受款人或事故者相同。\r\n';
                    }
                }

                // 受款人與事故者比對
                if((Trim($("benIdnNo").value)+Trim($("benBrDate").value)) == (Trim(evtIdnNo)+changeDateType(Trim(evtBrDate))))
                    msg += '「受款人」的身份證號與生日不得與事故者相同。\r\n';
            }

            if (Q1 >= 1 && Trim($("benEvtRel").value) == "1"){
                var oldIdnChkYm = "";
                if(Trim($("oldIdnChkYm").value)!=""){
                    oldIdnChkYm = changeDateType($("oldIdnChkYm").value+"01").substring(0 , 5);
                }else{
                    oldIdnChkYm = today.substring(0 , 5);
                }

                if($("idnChkNote1").checked==true){
                    if(parseNumber(oldIdnChkYm) > parseNumber(today.substring(0 , 5))){
                        msg+= '「身分查核年月」大於系統年月，不得點選自動遞延13個月。\r\n';
                        initIdnChkNote();
                    }
                }
                else if($("idnChkNote2").checked==true){
                    if(Trim($("idnChkYear").value) == ""){
                        msg += '「身分查核年月年度」：為必輸欄位。\r\n';
                        initIdnChkNote();
                    }
                    if(Trim($("idnChkMonth").value) == ""){
                        msg += '「身分查核年月月份」：為必輸欄位。\r\n';
                        initIdnChkNote();
                    }
                    if(Trim($("idnChkYear").value) != "" && Trim($("idnChkMonth").value) != ""){
                        if (!(parseNumber("" + $F("idnChkYear") + $F("idnChkMonth")) >= parseNumber(today.substring(0 , 5)) && parseNumber("" + $F("idnChkYear") + $F("idnChkMonth")) <= parseNumber(calMonth(today, 13).substring(0 , 5)))) {
                            msg += "「身分查核年月」需大於等於系統年月，且小於等於系統年月+13個月。\r\n";
                            initIdnChkNote();
                        }
                    }
                }
            }
        } else if(Trim($("benEvtRel").value) == "A"){
            if(Trim($("benIdnNo").value) == "")
                msg += '「受款人身分證號(保險證號)」：為必輸欄位。\r\n';
            if(Trim($("mustIssueAmt1").value) == "")
                msg += '「投保單位墊付金額」：為必輸欄位。\r\n';
            if(isNaN($("mustIssueAmt1").value)){
                msg += '「投保單位墊付金額」：必須為數字。\r\n';
            } else {
                if(parseNumber($("mustIssueAmt1").value) <= 0)
                    msg += '「投保單位墊付金額」：必須大於0。\r\n';
            }
            if(!isEngNum($("benIdnNo").value))
	            msg +='「受款人身分證號(保險證號)」格式錯誤。\r\n';
        } else if(Trim($("benEvtRel").value) == "E"){
            if(Trim($("benIdnNo").value) == "")
                msg += '「受款人身分證號(保險證號)」：為必輸欄位。\r\n';
            if(Trim($("benBrDate").value) == ""){
                msg += '「受款人出生日期」：為必輸欄位。\r\n';
            } else {
                if(Trim($("benBrDate").value) > nowDate)
                    msg += '「受款人出生日期」：不得大於系統日期。\r\n';
            }

            if(document.getElementsByName("benNationTyp")[0].checked==false && document.getElementsByName("benNationTyp")[1].checked==false)
                msg += '「國籍別」：為必輸欄位。\r\n';

            if (document.getElementsByName("benNationTyp")[1].checked==true){
                if(document.getElementsByName("benSex")[0].checked==false && document.getElementsByName("benSex")[1].checked==false)
                    msg += '「性別」：為必輸欄位。\r\n';
                if(Trim($("benNationCode").value) == "")
                    msg += '「國籍」：為必輸欄位。\r\n';
            }
            if(!isEngNum($("benIdnNo").value))
	            msg +='「受款人身分證號(保險證號)」格式錯誤。\r\n';

        } else if(Trim($("benEvtRel").value) == "Z"){
            if(Trim($("benIdnNo").value) == "")
                msg += '「受款人身分證號(保險證號)」：為必輸欄位。\r\n';
            if($F("benIdnNo").length > 3)
                msg += '「受款人身分證號(保險證號)」：只能輸入3碼。\r\n';
            if(bbcmf08Count == "0")
                msg += '「受款人身分證號(保險證號)」：資料錯誤,請重新輸入。\r\n';
            if(Trim($("mustIssueAmt2").value) == "")
                msg += '「實際補償金額」：為必輸欄位。\r\n';
             if(Trim($("oldAplDpt").value) == "")
                msg += '「申請代算單位」：為必輸欄位。\r\n';
            if(isNaN($("mustIssueAmt2").value)){
                msg += '「實際補償金額」：必須為數字。\r\n';

            } else {
                if(parseNumber($("mustIssueAmt2").value) <= 0)
                    msg += '「實際補償金額」：必須大於0。\r\n';
            }
            if(!isEngNum($("benIdnNo").value))
	            msg +='「受款人身分證號(保險證號)」格式錯誤。\r\n';
        } else if(Trim($("benEvtRel").value) == ""){
            msg += '「關係」：為必輸欄位。\r\n';
        }else{
        	  if(!isEngNum($("benIdnNo").value))
	            msg +='「受款人身分證號(保險證號)」格式錯誤。\r\n';
        }

        // 共用的
        if(Trim($("benName").value) == "")
            msg += '「受款人姓名」：為必輸欄位。\r\n';

        if (document.getElementsByName("payCategory")[0].checked==true && Trim($("payTyp").value) == "")
            msg += '「給付方式」：為必輸欄位。\r\n';
        if (document.getElementsByName("payCategory")[1].checked==true && Trim($("accSeqNo").value) == "")
            msg += '「給付方式」：為必輸欄位。\r\n';

        if (Trim($("payTyp").value) == "1" || Trim($("payTyp").value) == "2" || Trim($("payTyp").value) == "7" || Trim($("payTyp").value) == "8"){
            var payBankIdBranchId = document.PayeeDataUpdateDetailForm.payBankId.value + document.PayeeDataUpdateDetailForm.branchId.value;

            if(Trim($("payBankId").value) == "" || Trim($("branchId").value) == "" || Trim($("payEeacc").value) == "")
                msg += '「帳號」：為必輸欄位。\r\n';
            if(Trim($("accName").value) == "")
                msg += '「戶名」：為必輸欄位。\r\n';
            if (Trim($("payTyp").value) == "1" && payBankIdBranchId.substr(0,3) == "700")
                msg += '「帳號前三碼」：不可為700。\r\n';
            if (Trim($("payTyp").value) == "2" && payBankIdBranchId != "7000010" && payBankIdBranchId != "7000021"){
                msg += '「帳號(前)」：限定只能輸入700-0010或700-0021。\r\n';
            } else {
                if(isValidPayEeacc(payBankIdBranchId,Trim($("payEeacc").value)))
                    msg += '「帳號」格式錯誤，請重新確認。\r\n';
            }

            if(payBankIdBranchId.length < 7)
                msg += '「帳號(前)」：長度為7碼。\r\n';
            if (Trim($("payTyp").value) == "2" && payBankIdBranchId == "7000010" && $F("payEeacc").length > 8)
                msg += '「帳號(後)」：長度為8碼。\r\n';
            if (Trim($("payTyp").value) == "2" && payBankIdBranchId == "7000021" && $F("payEeacc").length != 14)
                msg += '「帳號(後)」：長度為14碼。\r\n';

        } else if (Trim($("payTyp").value) == "5" || Trim($("payTyp").value) == "6"){
            if(Trim($("payAccount").value) == "")
                msg += '「帳號」：為必輸欄位。\r\n';
            if(Trim($("bankName").value) == "")
                msg += '「金融機構名稱」：為必輸欄位。\r\n';
        }

        // 例外 "關係"資料=Z時，則地址相關資料非必填欄位
        if(Trim($("benEvtRel").value) != "Z"){
            if (document.getElementsByName("commTyp")[1].checked==true && Trim($("commZip").value) == "")
                msg += '「郵遞區號」：為必輸欄位。\r\n';
            if (document.getElementsByName("commTyp")[1].checked==true && Trim($("commAddr").value) == "")
                msg += '「地址」：為必輸欄位。\r\n';
            if ($("commTyp").value == "1" && Trim($("commZip").value) == "" && Trim($("commAddr").value) == "")
                msg += '戶籍地住址無資料，請選擇「現住址」並輸入住址資料。\r\n';

        }



        if (msg != "") {
            alert(msg);
            return false;
        }else {
        	if(chkEvtBrDate()){
              if($("payCategory").value=='1' && Trim($("payTyp").value) == "1"){
        		checkBankAccount();
        	  }else{
            	return true;
              }
            }else{
              return false;
            }
        }

    }
    <%-- ] ... end --%>

    <%-- begin 初始畫面欄位 --%>
    function initField() {
        var benEvtRel = '<c:out value="${PayeeDataUpdateDetailForm.benEvtRel}" />';

        // 關係為本人時，只能選本人
        if(benEvtRel=="1") {
            dwr.util.removeAllOptions("benEvtRel");
            dwr.util.addOptions("benEvtRel", {'1':'<%=ConstantKey.BAAPPBASE_BENEVTREL_STR_1%>'});
            $("benEvtRel").value="1";

            // 當「關係」=1 時,則「受款人姓名」、「受款人身分證號」、「受款人出生日期」、「國籍別」、「性別」、「國籍」欄位Disabled不給改。
            $("benName").disabled = true;
            $("benIdnNo").disabled = true;
            $("benBrDate").disabled = true;
            document.getElementsByName("benNationTyp")[0].disabled = true;
            document.getElementsByName("benNationTyp")[1].disabled = true;
            document.getElementsByName("benSex")[0].disabled = true;
            document.getElementsByName("benSex")[1].disabled = true;
            $("benNationCode").disabled = true;
            $("benNationCodeOption").disabled = true;

            // 若「給付主檔.受款人死亡日期(BAAPPBASE.BENDIEDATE)」不為空也不為null時,
            // 則「給付方式」、「帳號」相關欄位及「戶名」等相關欄位的欄位狀態需變成為Disabled或realOnly,不得供User修改異動。
            if(benDieDate!='' && benDieDate!=null){
                $("payTyp").disabled="true";
                $("specialAccAfter").disabled="true";
                document.getElementsByName("payCategory")[0].disabled = true;
                $("payBankId").disabled="true";
                $("branchId").disabled="true";
                $("payEeacc").disabled="true";
                $("payAccount").disabled="true";
                $("bankName").disabled="true";
                $("accName").disabled="true";
                $("payCategory2").disabled = true;
                $("accSeqNo").disabled="true";
            }
        } else if(benEvtRel=="F"){
            dwr.util.removeAllOptions("benEvtRel");
            dwr.util.addOptions("benEvtRel", {'F':'<%=ConstantKey.BAAPPBASE_BENEVTREL_STR_F%>'});
            $("benEvtRel").value="F";
        } else if(benEvtRel=="N"){
            dwr.util.removeAllOptions("benEvtRel");
            dwr.util.addOptions("benEvtRel", {'N':'<%=ConstantKey.BAAPPBASE_BENEVTREL_STR_N%>'});
            $("benEvtRel").value="N";
        }

        // 關係:1(本人)
        if($("benEvtRel").value=="1"){
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="inline";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 國籍別
            $("nationTypContent").style.display="inline";
            // 地址
            $("commTyp1").style.display="inline";
            $("commTyp2").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";

            // 因隱藏欄位要避掉焦點
            $("appDate").disabled = true;
            $("appUser").disabled = true;
            $("mustIssueAmt1").disabled = true;
            $("mustIssueAmt2").disabled = true;

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";
        }

        // 關係:2~7
        if($("benEvtRel").value=="2" || $("benEvtRel").value=="3" || $("benEvtRel").value=="4" ||
           $("benEvtRel").value=="5" || $("benEvtRel").value=="6" || $("benEvtRel").value=="7"){
            $("relationContent1").style.display="inline";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="inline";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 當受理編號第一碼為L跟關係為2~7時，預設繼承自受款人為0000
            $("appUser").value='0000';
            // 國籍別
            $("nationTypContent").style.display="inline";
            // 地址
            $("commTyp1").style.display="inline";
            $("commTyp2").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="inline";
            changeMarrMk();

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";

            // 因隱藏欄位要避掉焦點
            $("mustIssueAmt1").disabled = true;
            $("mustIssueAmt2").disabled = true;
        }

        // 關係1~7控制具名領取是否顯示
        if(Q2 > 0){
            // 若無資料可以選擇時,則「具名領取」的選項及下拉選單隱藏
            if(accSeqNo != '')
                $("payCategoryContent2").style.display="inline";
            else
                $("payCategoryContent2").style.display="none";
        } else {
            $("payCategoryContent2").style.display="none";
        }

        if($("benEvtRel").value=="A"){
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="inline";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="none";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 國籍別
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 地址
            $("commTyp1").style.display="none";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";

            // 因隱藏欄位要避掉焦點
            $("appDate").disabled = true;
            $("appUser").disabled = true;
            $("mustIssueAmt2").disabled = true;
        }
        if($("benEvtRel").value=="C"){
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="inline";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 國籍別
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 地址
            $("commTyp1").style.display="none";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";

            // 因隱藏欄位要避掉焦點
            $("appDate").disabled = true;
            $("appUser").disabled = true;
            $("mustIssueAmt1").disabled = true;
            $("mustIssueAmt2").disabled = true;
        }
        if($("benEvtRel").value=="Z"){

            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="inline";
            // 受款人出生日期
            $("benBrDateContent").style.display="none";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 國籍別
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 地址
            $("commTyp1").style.display="none";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="inline";
            chkOldAplDpt();


            // 因隱藏欄位要避掉焦點
            $("appDate").disabled = true;
            $("appUser").disabled = true;
            $("mustIssueAmt1").disabled = true;
        }
        if($("benEvtRel").value=="E"){
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="inline";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 國籍別
            $("nationTypContent").style.display="inline";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 地址
            $("commTyp1").style.display="inline";
            $("commTyp2").style.display="inline";

            // 因隱藏欄位要避掉焦點
            $("appDate").disabled = true;
            $("appUser").disabled = true;
            $("mustIssueAmt1").disabled = true;
            $("mustIssueAmt2").disabled = true;

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";
        }
        if($("benEvtRel").value=="O"){
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="none";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 國籍別
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 地址
            $("commTyp1").style.display="none";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";

            // 因隱藏欄位要避掉焦點
            $("appDate").disabled = true;
            $("appUser").disabled = true;
            $("mustIssueAmt1").disabled = true;
            $("mustIssueAmt2").disabled = true;
        }
        if($("benEvtRel").value=="" || $("benEvtRel").value=="F" || $("benEvtRel").value=="N"){
            $("relationContent1").style.display="none";
            $("relationContent2").style.display="none";
            $("relationContent3").style.display="none";
            // 受款人出生日期
            $("benBrDateContent").style.display="none";
            // 法定代理人
            $("grdContent1").style.display="inline";
            $("grdContent2").style.display="inline";
            $("grdContent3").style.display="inline";
            // 婚姻狀況
            $("marryContent").style.display="none";
            // 國籍別
            $("nationTypContent").style.display="none";
            // 性別
            $("sexContent").style.display="none";
            // 國籍
            $("nationalityContent").style.display="none";
            // "關係"若為A~Z(非自然人)時，"給付方式-具名領取"選項及下拉選單不顯示
            $("payCategoryContent2").style.display="none";
            // 地址
            $("commTyp1").style.display="inline";
            $("commTyp2").style.display="inline";

            // 申請代算單位
            $("relationContent4").style.display="none";
            document.getElementsByName("oldAplDpt")[0].checked=true;
            $("uname").value="";

            // 因隱藏欄位要避掉焦點
            $("appDate").disabled = true;
            $("appUser").disabled = true;
            $("mustIssueAmt1").disabled = true;
            $("mustIssueAmt2").disabled = true;
        }

        // 給付方式
        if($("payTyp").value=="1"||$("payTyp").value=="2"||$("payTyp").value=="8"){
            if($("payTyp").value=="1"||$("payTyp").value=="2"){
              $("specialAccContent").style.display="inline";
              if($("origSpecialAcc").value == "Y"){
                 $("specialAccAfter").checked = true;
              }
            }else{
              $("specialAccContent").style.display="none";
            }
            $("accountInput1").style.display="inline";
            $("accountInput2").style.display="none";
            $("accountInput3").style.display="inline";
        }
        if($("payTyp").value==""||$("payTyp").value=="3"||$("payTyp").value=="4"||$("payTyp").value=="A"){
            $("accountInput1").style.display="none";
            $("accountInput2").style.display="none";
            $("accountInput3").style.display="none";
            $("specialAccContent").style.display="none";
        }
        if($("payTyp").value=="5"||$("payTyp").value=="6"){
            $("accountInput1").style.display="none";
            $("accountInput2").style.display="inline";
            $("accountInput3").style.display="inline";
            $("specialAccContent").style.display="none";
        }

        // 給付方式
        if(accRel == "1"){
            document.getElementsByName("payCategory")[0].checked = true;
            $("accSeqNo").disabled = true;
        }
        if(accRel == "3"){
            $("payTyp").value="";
            $("payTyp").disabled = true;
            $("specialAccAfter").disabled= true;
            document.getElementsByName("payCategory")[1].checked=true;
            $("accountInput1").style.display="none";
            $("accountInput2").style.display="none";
            $("accountInput3").style.display="none";
        }

        // 通訊地址別
        if(document.getElementsByName("commTyp")[0].checked){
            addContent.style.display="inline";
            changeCommTyp();
        }

        if(document.getElementsByName("commTyp")[1].checked)
            addContent.style.display="inline";

        // 國籍別
        if(document.getElementsByName("benNationTyp")[0].checked){
            $("benNationCode").disabled = true;
            $("sexContent").style.display="none";
            $("nationalityContent").style.display="none";
        } else if(document.getElementsByName("benNationTyp")[1].checked){
            $("sexContent").style.display="inline";
            $("nationalityContent").style.display="inline";
            $("benNationCodeOption").value=$("benNationCode").value;
        } else { // 當關係A、C、Z、O時,國籍別為null
            $("sexContent").style.display="none";
            $("nationalityContent").style.display="none";
        }

        // 給付方式
        $("payCategoryContent1").style.display="inline";

        // 如果關係=F, N, disablen所有輸入欄位以及確認button
        var benEvtRel = ($("benEvtRel").value).toUpperCase();
        if(benEvtRel=="F" || benEvtRel=="N"){
            disableSaveDetailButton(true);
            for (i = 0; i < document.forms[0].length; i++){
                obj = document.forms[0].elements[i];
                if(obj.type == "text" || obj.type == "textarea" || obj.type == "radio" || obj.type == "checkbox" || obj.type == "select-one"){
                    obj.disabled = true;
                }
            }
        }
    }

    //依傳入關係條件 取得給付方式與申請代算單位下拉選單資料
    function getOptionList(){
        var benEvtRel = ($("benEvtRel").value).toUpperCase();
        var brDate = "<c:out value="${PayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />";
        var idnNo = "<c:out value="${PayeeDataUpdateQueryCase[0].evtIdnNo}" />";
        var apNo = "<c:out value="${PayeeDataUpdateQueryCase[0].apNo}" />";
        var oldAplDpt = "<c:out value="${PayeeDataUpdateDetailForm.oldAplDpt}" />";
            updateCommonAjaxDo.getPattyOptionList(benEvtRel, setPattyOptionList);
            updateCommonAjaxDo.getOldAplDpt(apNo,brDate,idnNo,oldAplDpt,setOldAplDptOptionList);


    }

    function setPattyOptionList(data){
        DWRUtil.removeAllOptions("payTyp");
        DWRUtil.addOptions("payTyp", {'':'請選擇...'});
        DWRUtil.addOptions("payTyp", data ,'paramCode','paramName');
    }

    function setOldAplDptOptionList(data){
        DWRUtil.removeAllOptions("oldAplDpt");
        DWRUtil.addOptions("oldAplDpt", {'':'請選擇...'});
        DWRUtil.addOptions("oldAplDpt", data ,'bmOldAplDpt','bmOldAplDpt');
        DWRUtil.removeAllOptions("oldAplDpt1");
        DWRUtil.addOptions("oldAplDpt1", {'':'請選擇...'});
        DWRUtil.addOptions("oldAplDpt1", data ,'bmOldAplDpt','uname');
    }
    <%-- ] ... end --%>

    function disableSaveDetailButton(isDisabled) {
        $("btnSave").disabled = isDisabled;
    }

    function initAll(){
        checkIdnoExist();
        // 初始化畫面
        initField();
        initIdnChkNote();
        tabChange();

        var payTyp = $("payTyp").value;
        if(payTyp=="1"){
            $("payBankId").disabled = false;
            $("branchId").value = '0000';
            $("branchId").readOnly = true;
            $("branchId").addClassName('disabledN');
            $("branchId").disabled = false;
            $("payEeacc").disabled = false;
        }
        if(payTyp=="2"){
            $("payBankId").disabled = false;
            $("branchId").disabled = false;
            $("payEeacc").disabled = false;
            $("branchId").readOnly = false;
            $("branchId").removeClassName('disabledN');

            if($("branchId").value == "0000"){
              $("branchId").value = "";
            }

        }
    }

     function chkOldAplDpt(){
        var sel = document.getElementById("oldAplDpt");
        var opts = sel.options;
        var opts1 = document.getElementById("oldAplDpt1").options;
        for (var i = 0; i < opts.length; i ++) {

            if (opts[i].selected) {
            $("uname").value=  opts1[i].innerText;
            $("oldAplDpt1").value=opts[i].innerText;
            }
        }
    }

    <%-- 重置身分查核年月預設值 --%>
    function initIdnChkNote() {
        var benEvtRel = '<c:out value="${PayeeDataUpdateDetailForm.benEvtRel}" />';
        // 身份查核年月 只有本人才會出現
        if(Q1 >= 1 && benEvtRel == "1"){
            $("idnChkNoteContent").style.display="inline";

            $("chkForeigner").style.display="none";
            $("idnChkNote1").checked = false;
            $("idnChkNote2").checked = false;
            $("idnChkYear").value = "";
            $("idnChkMonth").value = "";

            var oldIdnChkYm = "";
            if(Trim($("oldIdnChkYm").value)!=""){
                oldIdnChkYm = changeDateType($("oldIdnChkYm").value+"01").substring(0 , 5);
            }else{
                oldIdnChkYm = today.substring(0 , 5);
            }
            if($("oldIdnChkNote").value=='1'){
                $("chkForeigner").style.display="none";
                if(parseNumber(oldIdnChkYm) > parseNumber(today.substring(0 , 5))){
                    $("idnChkNote1").checked = false;
                    $("idnChkNote1").disabled = true;
                }else{
                    $("idnChkNote1").checked = true;
                }
            }
            else if($("oldIdnChkNote").value=='2'){
                $("idnChkNote2").checked = true;
                $("chkForeigner").style.display="inline";
                $("idnChkYear").value = $("oldIdnChkYear").value;
                $("idnChkMonth").value = $("oldIdnChkMonth").value;
            }
            else{
                $("chkForeigner").style.display="none";
                $("idnChkNote1").checked = false;
                $("idnChkNote2").checked = false;
                $("idnChkYear").value = "";
                $("idnChkMonth").value = "";
            }

            var chkY = "<c:out value="${PayeeDataUpdateDetailForm.idnChkYear}" />";
            var chkM = "<c:out value="${PayeeDataUpdateDetailForm.idnChkMonth}" />";
            if((chkY=="" && chkM=="") || (chkY==null && chkM==null)){
                $("idnChkYmYContent").style.display="none";
                $("idnChkYmNContent").style.display="inline";
            }else{
                $("idnChkYmYContent").style.display="inline";
                $("idnChkYmNContent").style.display="none";
            }
        } else {
            $("idnChkNoteContent").style.display="none";
        }
    }

          // Ajax for 取得 出生日期錯誤參數 確認是否有此筆資料P120436303 0480229  $("benIdnNo").value,$("benBrDate").value
    		    function checkIdnoExist() {
        		    if(isNaN($("benBrDate").value) == false){
        		    updateCommonAjaxDo.checkIdnoExist($("benIdnNo").value,$("benBrDate").value,checkIdnoExistResult);
        		    }
    		    }
    		    function checkIdnoExistResult(idnoExist) {
    		       $("idnoExist").value = idnoExist;
    		    }

            //檢核事故者出生日期 20121220 邏輯修改
            function isValidEvtDateTrue() {
            var evtBrDate = $("benBrDate").value;

            if(isValidDate($("benBrDate").value) == false){

            if($("idnoExist").value == null || $("idnoExist").value == "" || $("idnoExist").value == "null"){
            alert("輸入之「受款人出生日期」錯誤，請重新輸入");
            return false;
            }else{
              return true;
            }
            }else{
              return true;
            }
        }

            function chkEvtBrDate() {
            //檢核事故者出生日期  是否為數字 及 年月格式
            if(Trim($("benEvtRel").value) == "1" || Trim($("benEvtRel").value) == "2" || Trim($("benEvtRel").value) == "3" ||
               Trim($("benEvtRel").value) == "4" || Trim($("benEvtRel").value) == "5" || Trim($("benEvtRel").value) == "6" ||
               Trim($("benEvtRel").value) == "7" || Trim($("benEvtRel").value) == "E"){

            var msg = "";
            if($("benBrDate").value.length == 0){
                        msg += '「受款人出生日期」為必輸入欄位\r\n';
                        $("benBrDate").focus();
            }
            if($("benBrDate").value.length < 7 && $("benBrDate").value.length > 0){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();
            }
            if(isNaN($("benBrDate").value)){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();

            }
            if($("benBrDate").value.length == 7){
               var chkMonth = $("benBrDate").value.substring(3,5);
               var chkDay   = $("benBrDate").value.substring(5,7);
                   if(chkMonth > 12 || chkDay > 32){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();
               }
            }else if($("benBrDate").value.length == 8){
               var chkfrist = $("benBrDate").value.substring(0,1);
               var chkMonth = $("benBrDate").value.substring(4,6);
               var chkDay   = $("benBrDate").value.substring(6,8);
               if(chkfrist != "-"){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();

               }
               if(chkMonth > 12 || chkDay > 32){
                        msg += '輸入之「受款人出生日期」錯誤，請重新輸入\r\n';
                        $("benBrDate").focus();
               }
            }


            if(msg != ""){
                alert(msg);
                return false;
            }else{
                if(isValidEvtDateTrue()){
                return true;
                }else{
                return false;
                }
            }
           }else{
           return true;
           }
        }
         // Added by JohnsonHuang 20200115 [Begin]
            //外國人身分證號碼自動帶入
    		function autoForeignBenSex(){
    			var secondText = $("benIdnNo").value.substring(1,2);
				if($("benIdnNo").value.length==10){
    			if(document.getElementsByName("benNationTyp")[1].checked &&
    				document.getElementsByName("benSex")[0].checked==false && document.getElementsByName("benSex")[1].checked==false){
    				if(secondText=="A" || secondText=="a" || secondText=="C" || secondText=="c" || secondText=="8"){
    					document.getElementsByName("benSex")[0].checked=true;
    					document.getElementsByName("benSex")[1].checked=false;
    				}else if(secondText=="B" || secondText=="b" || secondText=="D" || secondText=="d" || secondText=="9"){
    					document.getElementsByName("benSex")[0].checked=false;
    					document.getElementsByName("benSex")[1].checked=true;
    				}else{
    					document.getElementsByName("benSex")[0].checked=false;
    					document.getElementsByName("benSex")[1].checked=false;
    					alert('個人資料「不符合外來人口統一證號編碼原則，請輸入正確身分證」');
    				}
    			}else{
    				if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[0].checked==true){
    					if(secondText!="A" && secondText!="a" && secondText!="C" && secondText!="c" && secondText!="8"){
    						alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
    					}
    				}else if(document.getElementsByName("benNationTyp")[1].checked && document.getElementsByName("benSex")[1].checked==true){
    					if(secondText!="B" && secondText!="b" && secondText!="D" && secondText!="d" && secondText!="9"){
    						alert('個人資料，身份證與性別不相符，請輸入正確「性別」或「事故者身分證字號」');
    					}
    				}
    			}
				}
    		}
         	// Added by EthanChen 20200115 [End]
    Event.observe(window, 'load', initAll, false);
    </script>
</head>

<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/payeeDataUpdateDetail" method="post" onsubmit="return validatePayeeDataUpdateDetailForm(this);">

        <fieldset>
            <legend>&nbsp;老年年金受款人資料更正&nbsp;</legend>

            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>

            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td align="right">
                        <bean:define id="payeeData" name="<%=ConstantKey.PAYEE_DATA_UPDATE_DETAIL_CASE%>"/>
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <html:hidden styleId="idnoExist" property="idnoExist"/>
                        <input type="hidden" id="origPayBankIdBranchId" name="origPayBankIdBranchId" value="<c:out value="${payeeData.payBankIdBranchId}" />"/>
                        <input type="hidden" id="origPayEeacc" name="origPayEeacc" value="<c:out value="${payeeData.payEeacc}" />"/>
                        <input type="hidden" id="origSpecialAcc" name="origSpecialAcc" value="<c:out value="${payeeData.specialAcc}" />"/>
                        <acl:checkButton buttonName="btnSave">
                            <input name="btnSave" type="button" class="button" value="確　認" onclick="checkIdnoExist();$('page').value='1'; $('method').value='doConfirm'; if(chkSpecialAcc()){if (document.PayeeDataUpdateDetailForm.onsubmit() && checkRequireFields()){
                                                                                                if($('payTyp').value=='4'){if(confirm('非行動不便之扣押戶，給付方式不得選擇匯票郵寄申請人，請重新選擇給付方式。按下【確定】時，不存檔並回到畫面重新選擇「給付方式」；按下【取消】時，確定「給付方式」為匯票郵寄並繼續存檔作業。')){return false;}else{document.PayeeDataUpdateDetailForm.submit();}
                                                                                                }else{document.PayeeDataUpdateDetailForm.submit();}}else{return false;}}else{return false;}" />&nbsp;
                        </acl:checkButton>
                        <acl:checkButton buttonName="btnBack">
                            <input name="btnBack" type="button" class="button" value="返　回" onclick="$('page').value='1'; $('method').value='doBackList'; document.PayeeDataUpdateDetailForm.submit();" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                            <tr>
                                <td width="25%">
                                    <span class="issuetitle_L_down">受理編號：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].apNoStrDisplay}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">給付別：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].payCode}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">申請日期：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].appDateChineseString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故日期：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].evtJobDateStr}" />
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <span class="issuetitle_L_down">事故者姓名：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].evtName}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者出生日期：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].evtBrDateChineseString}" />
                                </td>
                                <td width="25%">
                                    <span class="issuetitle_L_down">事故者身分證號：</span>
                                    <c:out value="${PayeeDataUpdateQueryCase[0].evtIdnNo}" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td align="center" class="table1">
                        <table width="98%" cellpadding="3" cellspacing="4" class="px13">
                            <tr>
                                <td colspan="3" class="issuetitle_L"><span class="systemMsg">▲</span>&nbsp;受款人資料</td>
                            </tr>
                            <tr>
                                <td id="iss" width="34%">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">關係：</span>
                                    <html:select property="benEvtRel" styleId="benEvtRel" styleClass="formtxt" onchange="chkBenEvtRel();getOptionList();">
                                        <html:option value="">請選擇</html:option>
                                        <html:options collection="<%=ConstantKey.PAYEE_RELATIION_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                    </html:select>
                                </td>
                                <td colspan="2" id="iss">
                                    <div id="relationContent1">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">繼承人申請日期：</span>
                                        <html:text property="appDate" styleId="appDate" styleClass="textinput" size="10" maxlength="8" onblur="this.value=asc(this.value);"/>
                                        &nbsp;&nbsp;&nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">繼承自受款人：</span>
                                        <html:select property="appUser" styleId="appUser" styleClass="formtxt">
                                            <html:option value="">請選擇</html:option>
                                            <html:options collection="<%=ConstantKey.BENNAME_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                        </html:select>
                                    </div>
                                    <div id="relationContent2">
                                        <span class="needtxt">＊</span><span class="issuetitle_L_down">投保單位墊付金額：</span>
                                        <html:text property="mustIssueAmt1" styleId="mustIssueAmt1" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                                    </div>
                                    <div id="relationContent3">
                                        <span class="needtxt">＊</span><span class="issuetitle_L_down">實際補償金額：</span>
                                        <html:text property="mustIssueAmt2" styleId="mustIssueAmt2" styleClass="textinput" size="10" maxlength="7" onblur="this.value=asc(this.value);"/>
                                    </div>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">受款人姓名：</span>
                                    <html:text property="benName" styleId="benName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value);"/>
                                </td>
                            </tr>

                            <tr>
                                <td id="iss"  colspan="3">
                                <div id="relationContent4">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">申請代算單位：</span>
                                    <html:select property="oldAplDpt" styleId="oldAplDpt" styleClass="formtxt" onchange="chkOldAplDpt();">
                                        <html:option value="">請選擇</html:option>
                                        <html:options collection="<%=ConstantKey.OLDAPLDPT_OPTION_LIST%>" property="bmOldAplDpt" labelProperty="bmOldAplDpt"/>
                                    </html:select>
                                    <html:text property="uname" styleId="uname" styleClass="textinput" size="50" maxlength="50" disabled="true"/>
                                    <html:select property="oldAplDpt" styleId="oldAplDpt1" styleClass="formtxt" style="display:none">
                                        <html:option value="">請選擇</html:option>
                                        <html:options collection="<%=ConstantKey.OLDAPLDPT_OPTION_LIST%>" property="bmOldAplDpt" labelProperty="uname" />
                                    </html:select>
                                   </div>
                                </td>
                            </tr>

                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">受款人身分證號(保險證號)：</span>
                                    <html:text property="benIdnNo" styleId="benIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase(); callCaubData(); getBbcmf08Data();checkIdnoExist();autoForeignBenSex();"/>
                                </td>
                                <td id="iss" colspan="2">
                                    <div id="benBrDateContent">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">受款人出生日期：</span>
                                        <html:text property="benBrDate" styleId="benBrDate" styleClass="textinput" size="10" maxlength="8" onblur="this.value=asc(this.value);checkIdnoExist();"/>
                                    </div>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <div id="nationTypContent">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">國籍別：</span>
                                        <span class="formtxt">
                                            <html:radio property="benNationTyp" value="1" onclick="$('benNationTyp').value='1'; changeBenNationTpe();" />本國
                                            <html:radio property="benNationTyp" value="2" onclick="$('benNationTyp').value='2'; changeBenNationTpe();" />外籍
                                        </span>
                                    </div>
                                    <div id="sexContent">
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">性別：</span>
                                        <span class="formtxt">
                                            <html:radio property="benSex" value="1" onclick="$('benSex').value='1';"/>男
                                        </span>
                                        <span class="formtxt">
                                            <html:radio property="benSex" value="2" onclick="$('benSex').value='2';"/>女
                                        </span>
                                    </div>
                                    <div id="nationalityContent">
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">國籍：</span>
                                        <html:text property="benNationCode" styleId="countryId" styleClass="textinput" size="2" maxlength="3"  readonly = "true"/>
                                        <label>
                                            <html:select property="benNationCodeOption" onchange="$('benNationCode').value=$('benNationCodeOption').value">
                                                <html:option value="">請選擇</html:option>
                                                <html:options collection="<%=ConstantKey.COUNTRYID_OPTION_LIST%>" property="countryId" labelProperty="cname" />
                                            </html:select>
                                        </label>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                <div id="idnChkNoteContent">
                                	  <div id="idnChkYmNContent">
                                        <span class="issuetitle_L_down">&nbsp;&nbsp;&nbsp;&nbsp;身分查核年月(&nbsp;&nbsp;&nbsp;年&nbsp;&nbsp;月)： </span>
                                      </div>
                                      <div id="idnChkYmYContent">
                                      	<span class="issuetitle_L_down">&nbsp;&nbsp;&nbsp;&nbsp;身分查核年月(<c:out value="${PayeeDataUpdateDetailForm.idnChkYear}" />年<c:out value="${PayeeDataUpdateDetailForm.idnChkMonth}" />月)：</span>
                                      </div>
                                      <span class="formtxt">
                                          <html:radio styleId="idnChkNote1" property="idnChkNote" value="1" onclick="$('idnChkNote').value='1'; chkIdnChkNote();"/>自動遞延13個月
                                          <html:radio styleId="idnChkNote2" property="idnChkNote" value="2" onclick="$('idnChkNote').value='2'; chkIdnChkNote();"/>手動調整
                                      </span>
                                      <span class="issuetitle_L_down" id="chkForeigner">
                                          <html:text property="idnChkYear" styleId="idnChkYear" styleClass="textinput" size="3" maxlength="3" onblur="this.value=asc(this.value);"/>年
                                          <html:text property="idnChkMonth" styleId="idnChkMonth" styleClass="textinput" size="2" maxlength="2" onblur="this.value=asc(this.value);"/>月
                                     </span>
                                     <html:hidden styleId="oldIdnChkYm" property="oldIdnChkYm" />
                                     <html:hidden styleId="oldIdnChkNote" property="oldIdnChkNote" />
                                     <input type="hidden" name="oldIdnChkYear" value="<c:out value="${PayeeDataUpdateDetailForm.idnChkYear}" />" >
                                     <input type="hidden" name="oldIdnChkMonth" value="<c:out value="${PayeeDataUpdateDetailForm.idnChkMonth}" />" >
                                   </div>
                                </td>
                            </tr>

                            <tr>
                                <td colspan="3" id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">給付方式：</span>
                                    <span class="formtxt">
                                        <div id="payCategoryContent1">
                                        <html:radio styleId="payCategory1" property="payCategory" value="1" onclick="$('payCategory').value='1';changePayCategory();"/>本人領取
                                        <html:select property="payTyp" styleId="payTyp" styleClass="formtxt" onchange="changePayType();" onblur="tabChange();">
                                            <html:option value="">請選擇</html:option>
                                            <html:options collection="<%=ConstantKey.PAYTYP_OPTION_LIST%>" property="paramCode" labelProperty="paramName" />
                                        </html:select>
                                        </div>
                                        <div id="payCategoryContent2">
                                        <html:radio styleId="payCategory2" property="payCategory" value="2" onclick="$('payCategory').value='2';changePayCategory();"/>具名領取
                                        <html:select property="accSeqNo" styleId="accSeqNo" styleClass="formtxt">
                                            <html:option value="">請選擇</html:option>
                                            <logic:notEmpty name="<%=ConstantKey.BEN_OPTION_LIST%>">
                                                <logic:iterate id="benList" name="<%=ConstantKey.BEN_OPTION_LIST%>">
                                                    <html:option value="${benList.seqNo}" ><c:out value="${benList.benName}" /></html:option>
                                                </logic:iterate>
                                            </logic:notEmpty>
                                            <%--<html:options collection="<%=ConstantKey.BEN_OPTION_LIST%>" property="seqNo" labelProperty="benName" />--%>
                                        </html:select>
                                        </div>
                                        <div id="specialAccContent" style="display: none;">
                    		                 <input type="checkbox" id="specialAccAfter" name="specialAccAfter" value="Y">專戶
                    	                </div>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" id="iss">
                                    <div id="accountInput1">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">帳號：</span>
                                        <html:text property="payBankId" styleId="payBankId" styleClass="textinput" size="1"  maxlength="3"  onchange="this.value=Trim($('payBankId').value)" onblur="this.value=asc(this.value);" onkeyup="autoTabChange();"/>&nbsp;-
                                        <html:text property="branchId"  styleId="branchId"  styleClass="textinput" size="1"  maxlength="4"  onchange="this.value=Trim($('branchId').value)"  onblur="this.value=asc(this.value);" onkeyup="autotab($('branchId'), $('payEeacc'))"/>&nbsp;-
                                        <html:text property="payEeacc"  styleId="payEeacc"  styleClass="textinput" size="14" maxlength="14" onchange="this.value=Trim($('payEeacc').value)"  onblur="this.value=asc(this.value);"/>
                                        <html:hidden styleId="payBankIdBranchId" property="payBankIdBranchId"/>                　
                                    </div>
                                    <div id="accountInput2">
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">帳號：</span>
                                        <html:text property="payAccount" styleId="payAccount" styleClass="textinput" size="21" maxlength="21" onblur="this.value=asc(this.value).toUpperCase();"/>
                                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">金融機構名稱：</span>
                                        <html:text property="bankName" styleId="bankName" styleClass="textinput" size="50" maxlength="120" onblur="this.value=asc(this.value).toUpperCase();"/>                                   　
                                    </div>
                                    <div id="accountInput3">
                                        <br/>
                                        <span class="needtxt">＊</span>
                                        <span class="issuetitle_L_down">戶名：</span>
                                        <html:text property="accName" styleId="accName" styleClass="textinput" size="50" maxlength="50" onchange="this.value=Trim($('accName').value)" onblur="this.value=asc(this.value).toUpperCase();"/>                                        　
                                    </div>
                                </td>
                            </tr>

                            <tr>
                                <td id="iss">
                                    <span class="issuetitle_L_down">&nbsp;&nbsp;&nbsp;&nbsp;電話1：</span>
                                    <html:text property="tel1" styleId="tel1" styleClass="textinput" size="18" maxlength="18" onblur="this.value=asc(this.value);"/>
                                </td>
                                <td id="iss" colspan="2">
                                    <span class="issuetitle_L_down">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;電話2：</span>
                                    <html:text property="tel2" styleId="tel2" styleClass="textinput" size="18" maxlength="18" onblur="this.value=asc(this.value);"/>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">地址：</span>
                                    <span class="formtxt">
                                        <div id="commTyp1">
                                            <html:radio property="commTyp" value="1" onclick="$('commTyp').value='1'; changeCommTyp();"/>同戶籍地
                                        </div>
                                        <div id="commTyp2">
                                            <html:radio property="commTyp" value="2" onclick="$('commTyp').value='2'; changeCommTyp();"/>現住址
                                        </div>
                                    </span>
                                </td>
                                <td id="iss" colspan="2">
                                    <div id="addContent">
                                        <span class="issuetitle_L_down">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;現住址：</span>
                                        <span class="formtxt">
                                            <html:text property="commZip" styleId="commZip" styleClass="textinput" size="6" maxlength="6" onblur="this.value=asc(this.value);"/>(郵遞區號)&nbsp;-
                                            <html:text property="commAddr" styleId="commAddr" styleClass="textinput" size="65" maxlength="240" onblur="this.value=asc(this.value);"/>
                                        </span>
                                    </div>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <div id="marryContent">
                                    <span class="needtxt">＊</span>
                                    <span class="issuetitle_L_down">婚姻狀況：</span>
                                    <span class="formtxt">
                                        <html:radio property="benMarrMk" value="Y" onclick="$('benMarrMk').value='Y'; changeMarrMk();"/>已婚
                                    </span>
                                    <span class="formtxt">
                                        <html:radio property="benMarrMk" value="N" onclick="$('benMarrMk').value='N'; changeMarrMk();"/>未婚
                                    </span>
                                    </div>
                                <td>
                            </tr>
                            <tr>
                                <td id="iss" colspan="3">
                                    <div id="grdContent1">
                                        <span class="needtxt">&nbsp;&nbsp;&nbsp;</span>
                                        <span class="issuetitle_L_down">法定代理人姓名：</span>
                                        <html:text property="grdName" styleId="grdName" styleClass="textinput" size="50" maxlength="50" onblur="this.value=asc(this.value);"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td id="iss">
                                    <div id="grdContent3">
                                        <span class="needtxt">&nbsp;&nbsp;&nbsp;</span>
                                        <span class="issuetitle_L_down">法定代理人身分證號：</span>
                                        <html:text property="grdIdnNo" styleId="grdIdnNo" styleClass="textinput" size="25" maxlength="20" onblur="this.value=asc(this.value).toUpperCase();"/>
                                    </div>
                                </td>
                                <td id="iss" colspan="2">
                                    <div id="grdContent2">&nbsp;
                                        <span class="needtxt">&nbsp;&nbsp;</span>
                                        <span class="issuetitle_L_down">法定代理人出生日期：</span>
                                        <html:text property="grdBrDate" styleId="grdBrDate" styleClass="textinput" size="10" maxlength="8" onblur="this.value=asc(this.value);"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3"></td>
                            </tr>
                        </table>
                   </td>
               </tr>
               <tr>
                   <td><hr size="1" noshade>
                       <span class="titleinput">※注意事項：</span><br>
          　                                               1.&nbsp;有關日期的欄位，填寫格式如民國98年1月1日，請輸入0980101。<br>
          　                                               2.&nbsp;<span class="needtxt">＊</span>為必填欄位。
                   </td>
               </tr>
           </table>
        </fieldset>

    </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
