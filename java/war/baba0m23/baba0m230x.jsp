<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true">
<head>
    <acl:setProgId progId="BABA0M230X" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/interface/bjCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>    
	<html:javascript formName="RunProcedureForm" page="1" />    	
    <script language="javascript" type="text/javascript">
     
    // Ajax for 取得 Procedure 的傳入參數
    function getParameters() {
    	var procedureName = $("procedureName").value;    	
        bjCommonAjaxDo.getParameters(procedureName, getParametersResult);
    }
    
    function getParametersResult(dataList){    	    	
    	
    	$("dataSpan").innerHTML = "";    	        
        var innerHTMLStr = "";
        
        if(dataList.length==0){        
        	$("dataSpan").innerHTML = "";
        
        }else{
        
            for(var i=0; i<dataList.length; i++){        
                    	
                innerHTMLStr += '<tr align="center"><td colspan="9">';
                innerHTMLStr += '<td>';
                innerHTMLStr += '<table id="tb" width="100%" cellpadding="0" cellspacing="2" class="px13">';

               	innerHTMLStr += '<tr>';
               	innerHTMLStr += '<td align="right" width="30%">';
               	innerHTMLStr += '<span class="needtxt">　</span>';
               	innerHTMLStr += '<span class="issuetitle_R_down">' + dataList[i].paramDesc + '：</span>';
               	innerHTMLStr += '</td>';
               	innerHTMLStr += '<td>';               	
               	innerHTMLStr += '<input type="text" name="params" id="params' + i + '"  size="' + dataList[i].dataLength +'" maxlength="' + dataList[i].dataLength + '" class="textinput"  />';
               	innerHTMLStr += '</td>';
               	innerHTMLStr += '</tr>';         	
               	               	               	
                innerHTMLStr += '</table>';
                innerHTMLStr += '</td></tr>';  
            }
            
        }
        
        $("dataSpan").innerHTML = innerHTMLStr;               	        
    }
    
    function checkRequireFields() {
		var msg = "";
		
		var dataArray = [];		
        element = document.getElementsByName("params");                       
        for (i = 0; i < element.length; i++) {
            dataArray[i] = element[i].value;           
        }
        $("paramsList").value=dataArray;

        
	    if(Trim($("runDateBegin").value) == "")
            msg += '「啟動時間」：為必輸欄位。\r\n';
        if(Trim($("procedureName").value) == "")
            msg += '「程式名稱」：為必輸欄位。\r\n';            	            	
            		
		if (msg != "") {
		    alert(msg);
		    return false;
		} else {
            return true;
        }  						
	}

    Date.prototype.Format = function (fmt) { //author: meizz 
        var o = {
            "M+": this.getMonth() + 1, //月份 
            "d+": this.getDate(), //日 
            "h+": this.getHours(), //小时 
            "m+": this.getMinutes(), //分 
            "s+": this.getSeconds(), //秒 
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
            "S": this.getMilliseconds() //毫秒 
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }           
    
    function initAll(){
    	
    	var now = new Date().Format("yyyyMMddhhmmss");
    	
    	$("runDateBegin").value = now;    	
    	$("procedureName").value = '';
    	
    }
    
    Event.observe(window, 'load', initAll, false);
    </script>			
</head>
		
<body scroll="no">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/runProcedure" method="post" onsubmit="return validateRunProcedureForm(this);">
 		<html:hidden styleId="paramsList" property="paramsList" />       
        <fieldset>
            <legend>&nbsp;批次程式執行作業&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
	  
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
	            <tr>
                    <td colspan="3" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnInsert">
			                <input name="btnRun" type="button" class="button" value="執  行 " onclick="$('page').value='1'; $('method').value='doRun'; if(document.RunProcedureForm.onsubmit() && checkRequireFields()){document.RunProcedureForm.submit();}else{return false;}" />&nbsp;&nbsp;
			            </acl:checkButton>

		            </td>
                </tr>
                <tr>
                    <td align="right" width="30%">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">啟動時間：</span>
                    </td>
                    <td>
                        <html:text styleId="runDateBegin" property="runDateBegin" tabindex="1" size="20" maxlength="14" styleClass="textinput" />
                    </td>
                </tr>                     	  
                <tr>
                    <td align="right" width="30%">
                        <span class="needtxt">＊</span>
                        <span class="issuetitle_R_down">程式名稱：</span>
                    </td>
                    <td>
                        <html:select styleId="procedureName" property="procedureName" tabindex="2" onchange="getParameters();">
                            <html:option value="">請選擇</html:option>
                            <html:options collection="<%=ConstantKey.PROCEDURE_NAME_LIST%>" property="procedureName" labelProperty="procDesc" />
                        </html:select>
                    </td>
                </tr>       
                <tr>
                    <td align="right" colspan="14">
                    	<span id="dataSpan"></span>                	
                    </td>                    
                </tr>                                         
	            <tr>
	                <td colspan="2" align="right" valign="bottom">&nbsp;</td>
                </tr>
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>