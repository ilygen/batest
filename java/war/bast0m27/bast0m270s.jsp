<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
  <head>
  	  <acl:setProgId progId="BAST0M270S" />
	  <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
	  <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
      <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
      <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	  <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
	  <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
	  <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
	  <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
      <html:javascript formName="ExecStatisticsForm" page="1" />
      <script language="javascript" type="text/javascript">
          
          function checkDate() {
              var msg = "";
              var today = new Date();
              var yyyy = today.getFullYear() - 1911;
              var MM = (today.getMonth() + 1) >= 10 ? (today.getMonth() + 1) : ("0" + (today.getMonth() + 1));
              var MM2 = (MM - 2) >= 10 ? MM - 2 : "0" + (MM - 2);

              var inputDate = $("issuYm").value;

              if (inputDate > (yyyy.toString() + MM.toString())) {
                  msg = "處理年月不得大於系統年月";
              }else if (inputDate <= (yyyy.toString() + (MM2).toString())) {
                  return confirm("處理年月小於系統年月兩個月以上，請確認是否繼續執行年金統計作業。");
              }

              if (msg !== "") {
                  alert(msg);
                  return false;
              } else {
                  return true;
              }
          }
	      <!--
	      Event.stopObserving(window, 'beforeunload', beforeUnload);
	      -->

      </script>
  </head>

<body scroll="no">
<div id="mainContent">

	 <%@ include file="/includes/ba_header.jsp"%>
     <%@ include file="/includes/ba_menu.jsp"%>
 
    <div id="main" class="mainBody">
    <html:form action="/execStatistics" method="post" onsubmit="return validateExecStatisticsForm(this);">  
      <fieldset>
      <legend>&nbsp;年金統計執行作業 &nbsp;</legend>
      
         <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
          <tr>
              <td colspan="2" align="right">
                  <html:hidden styleId="page" property="page" value="1" />
                  <html:hidden styleId="method" property="method" value="" />
                  <acl:checkButton buttonName="btnExec">
                  	<input type="button" id="btnExec" name="btnExec" tabindex="2" class="button" value="執　行" onclick="$('page').value='1'; $('method').value='doExec'; if (checkDate() && document.ExecStatisticsForm.onsubmit()){document.ExecStatisticsForm.submit();}else{return false;}" />&nbsp;
                  </acl:checkButton> 
                  <acl:checkButton buttonName="btnProgress">
                  	<input type="button" id="btnProgress" name="btnPrint" tabindex="3" class="button_90" value="進度查詢" onclick="$('page').value='1'; $('method').value='doProgress'; if (document.ExecStatisticsForm.onsubmit()){document.ExecStatisticsForm.submit();}else{return false;}" />&nbsp;
                  </acl:checkButton>                       
                  <acl:checkButton buttonName="btnReset">
                      <input type="reset" name="btnReset" id="btnReset" tabindex="4" class="button" class="button" value="清　除" />
                  </acl:checkButton>                        
              </td>
          </tr> 
          
		  
          <tr>
          	
			<td width="30%" align="right"><span class="needtxt">＊</span><span class="issuetitle_R_down">處理年月：<span></td>
 			<td>
            	<html:text styleId="issuYm" property="issuYm" tabindex="1" size="5" maxlength="5" styleClass="textinput" />
            </td>
           </tr>
          
          <tr>
              <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
              <td colspan="2"><hr size="1" noshade>
              <span class="titleinput">※注意事項：</span><br>
              　			1.&nbsp;有關年月的欄位，填寫格式如民國98年1月，請輸入09801。
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