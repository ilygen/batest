<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<bean:define id="caseData" name="<%=ConstantKey.MONTHLY_RPT_30_FILE_LIST%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
<acl:setProgId progId="BALP0D3S0N" />
<title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
<script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
<script language="javascript" type="text/javascript">
    
    // checkbox 單選
    function chk(input){
    
      indexs = document.getElementsByName("index");
      for(var i=0;i<indexs.length;i++){
         indexs[i].checked = false;
      }
      input.checked = true;
      return true;
    }
    
	// checkbox 多選
	function chkIndex(obj) {				
        indexs = document.getElementsByName("index");
        
        if(obj.name == "chkAllIdx"){        
            for(var i=0; i<indexs.length; i++){        
                indexs[i].checked=obj.checked;
            }        
        }
        else{        
            var isAllChecked = true;
            for(var i=0; i<indexs.length; i++){        
                if(indexs[i].checked==false){        
                    isAllChecked = false;
                    break;
                }        
            }
        
            if(isAllChecked==true){        
                $("chkAllIdx").checked=true;
            }else{        
                $("chkAllIdx").checked=false; 
            }        
        }				        		
	}
    
    function isChkAnyOne(){
        
        var isChkAnyOne = false;
            indexs = document.getElementsByName("index");
            for(var i=0; i<indexs.length; i++){
                if(indexs[i].checked==true){
                    isChkAnyOne = true;
                    break;
                }
            }
            if(isChkAnyOne == false){
                return true
            }else{
                return false
            }
        }
        
        function checkFields(){
            var msg="";
            
            if(isChkAnyOne() == true){
            msg+="請勾選欲刪除之資料。\r\n";
            }
            
            if(msg!=""){
                alert(msg);
                return false;
            }else{
                return true;
            }
        }
    
    <!--
    Event.stopObserving(window, 'beforeunload', beforeUnload);
    -->
    </script>
</head>

<body scroll="no">

	<%@ include file="/includes/ba_header.jsp"%>

	<%@ include file="/includes/ba_menu.jsp"%>

	<div id="main" class="mainBody">
		<html:form action="/printMonthlyRpt30" method="post" onsubmit="">

			<fieldset>
				<legend>&nbsp;查核失能程度通知函&nbsp;</legend>

				<div align="right" id="showtime">網頁下載時間：民國&nbsp;<func:nowDateTime /></div>

				<table width="98%" border="0" align="center" cellpadding="2"
					cellspacing="2" class="px13">
					<tr>
						<td colspan="3" align="right">
							<html:hidden styleId="page" property="page" value="1" /> 
							<html:hidden styleId="method" property="method" value="download" /> 
							<html:hidden styleId="downloadFileName" property="downloadFileName" /> 
							<acl:checkButton buttonName="btnDelete">
								<input tabindex="270" type="button" name="btnDelete" class="button" value="刪　除" onclick="$('page').value='1'; $('method').value='deleteFile'; if(checkFields() && confirm('再次確認是否刪除!?')){document.MonthlyRpt30Form.submit();}else{return false;}" />&nbsp;
                        	</acl:checkButton> 
                        	<acl:checkButton buttonName="btnBack">
                        		<input type="button" name="btnBack" class="button" value="返　回" onclick="$('page').value='1'; javascript:history.back();" />&nbsp;
                        	</acl:checkButton>
                        </td>
					</tr>
					<tr>
						<td colspan="3" align="right">
							<display:table name="pageScope.caseData" id="listItem" pagesize="0" excludedParams="*">
								<display:column	title="<input type='checkbox' name='chkAllIdx' onclick='chkIndex(this)'>全&nbsp;選"	style="width:10%; text-align:center;">
									<html:checkbox styleId="${listItem}" property="index" value="${listItem}" onclick="return chkIndex(this);" />&nbsp;
                            	</display:column>
								<display:column title="檔&nbsp;名" style="width:74%; text-align:center;">
									&nbsp;<c:out value="${listItem}" />&nbsp;
								</display:column>
								<display:column title="操&nbsp;作" style="width:16%; text-align:center;">
									<input type="button" name="btnDownload" class="button" value="下　載" onclick="$('page').value='1'; $('method').value='downloadFile'; $('downloadFileName').value='<c:out value="${listItem}" />'; document.MonthlyRpt30Form.submit();" />
								</display:column>
							</display:table>
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
