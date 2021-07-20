<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
  <head>
  	  <acl:setProgId progId="BAMO0D070C" />
	  <meta http-equiv="Content-Type" content="text/html; charset=big5" />
	  <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
	  <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
      <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
      <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
      <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
	  <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
	  <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
	  <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
	  <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
      <html:javascript formName="DependantDataUpdateQueryForm" page="1" />
      <script language="javascript" type="text/javascript">
	  
		function getProcStat(){
	        $("message").innerHTML = "";
	        var apNo = $('apNo1').value + $('apNo2').value + $('apNo3').value + $('apNo4').value;
	        updateCommonAjaxDo.getBaappbaseProcStat(apNo, chkProcStat);
	    }
	    
	    function chkProcStat(procStat){
	        if(procStat==''){
	            $("message").innerHTML = "<font color='red'>W0040 無查詢資料</font>"
	            return false;
	        }else if(procStat=='0' || procStat=='1' || procStat=='9'){
	            document.DependantDataUpdateQueryForm.submit();
	        }else if(procStat=='2'){
	            if(confirm('案件經審核核定，是否繼續更正？')){
	                document.DependantDataUpdateQueryForm.submit();
	            }else{
	                return false;
	            }
	        }else if(procStat=='3' || procStat=='4' || procStat=='5'){
	            $("message").innerHTML = "<font color='red'>W1004 本案已決行！</font>"
	            return false;
	        }
	        
	    }

      </script>
  </head>

<body scroll="no">
<div id="mainContent">

	 <%@ include file="/includes/ba_header.jsp"%>
     <%@ include file="/includes/ba_menu.jsp"%>
 
    <div id="main" class="mainBody">
    <html:form action="/dependantDataUpdateQuery" method="post" onsubmit="return validateDependantDataUpdateQueryForm(this);">  
      <fieldset>
      <legend>&nbsp;眷屬資料更正 &nbsp;</legend>
      
         <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
          <tr>
              <td colspan="2" align="right">
                  <html:hidden styleId="page" property="page" value="1" />
                  <html:hidden styleId="method" property="method" value="" />
                  <acl:checkButton buttonName="btnQuery">
                            <input type="button" id="btnQuery" name="btnQuery" tabindex="3" class="button" value="確　認" onclick="$('page').value='1'; $('method').value='doQuery'; if (document.DependantDataUpdateQueryForm.onsubmit()){getProcStat();}" />&nbsp;
                  </acl:checkButton>                        
                  <acl:checkButton buttonName="btnClear">
                      <input type="reset" name="btnReset" id="btnReset" tabindex="6" class="button" class="button" value="清　除" />
                  </acl:checkButton>                        
              </td>
          </tr> 
          <tr>
            <td width="25%" align="right"><span class="needtxt">＊</span><span class="issuetitle_R_down">受理編號：</span></td>
            <td>
                <html:text tabindex="10" property="apNo1" styleId="apNo1" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value).toUpperCase();" onkeyup="autotab($('apNo1'), $('apNo2'))" />
                &nbsp;-
                <html:text tabindex="20" property="apNo2" styleId="apNo2" styleClass="textinput" size="1" maxlength="1" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo2'), $('apNo3'))"/>
                &nbsp;-
                <html:text tabindex="30" property="apNo3" styleId="apNo3" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo3'), $('apNo4'))" />
                &nbsp;-
                <html:text tabindex="40" property="apNo4" styleId="apNo4" styleClass="textinput" size="5" maxlength="5" onblur="this.value=asc(this.value)" onkeyup="autotab($('apNo4'), $('btnQuery'))"/>
            </td>        
          </tr>
		  
          <tr>
              <td colspan="2">&nbsp;</td>
          </tr>
          <tr>
              <td colspan="2"><hr size="1" noshade>
              <span class="titleinput">※注意事項：</span><br>
                 　&nbsp;<span class="needtxt">＊</span>為必填欄位。
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