<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAMO0D060C" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/dwr/interface/updateCommonAjaxDo.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/engine.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/dwr/util.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
</head>
<script language="javascript" type="text/javascript">
    
    <!--
    //頁面欄位值初始化       
    function initAll(){    
      $("orderBy").checked = true;
      $("orderBy").defaultChecked = true;
    }    
    
    function changeRadio(origObj, nextObj){      
        if(event.keyCode==9){
            if (typeof origObj == 'string'){
                origObj = document.getElementById(origObj);
            }
            if (typeof nextObj == 'string'){
                nextObj = document.getElementById(nextObj);
            }
            nextObj.checked=true; 
            
            objs = document.getElementsByName(nextObj.name);
            for(i = 0; i < objs.length; i++){
                obj = objs[i];
                obj.blur();
                if(obj.checked==true){                
                    obj.focus();
                    break;
                }    
            }  
       }       
    }
    
    function test2(){
        objs = document.getElementsByName("orderBy");
        for(i = 0; i < objs.length; i++){
            obj = objs[i];
            obj.blur();
            if(obj.checked==true){                
                obj.focus();
                break;
            }    
        }    
    }
    
    function checkFields(){
        if(Trim($F("apNo1")).length == 0||Trim($F("apNo2")).length == 0||Trim($F("apNo3")).length == 0||Trim($F("apNo4")).length == 0){
            alert("請輸入受理編號");
            return false;        
        }
        
        if($('apNo1').value != "L"){
            alert("「受理編號第一欄位」限制僅能輸入「L」");
            return false;        
        }
        
        var msg = "";
        if(Trim($F("apNo3")).length <5 ){
            msg = "受理編號第三欄  不可小於五個字元";
        }
        
        if(Trim($F("apNo4")).length <5 ){
           if(Trim(msg).length != 0){
              msg = msg +"\n" +"受理編號第四欄  不可小於五個字元";
             }else{
              msg = "受理編號第四欄  不可小於五個字元";
             }
        }
        
        if(Trim(msg).length != 0){
            alert(msg);
            return false;  
        }
        
        return true;
    }
    
    // 案件處理狀態的檢核 
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
            document.CheckMarkLevelAdjustForm.submit();
        }else if(procStat=='2'){
            if(confirm('案件經審核核定，是否繼續更正？')){
                document.CheckMarkLevelAdjustForm.submit();
            }else{
                return false;
            }
        }else if(procStat=='3' || procStat=='4' || procStat=='5'){
            $("message").innerHTML = "<font color='red'>W1004 本案已決行！</font>"
            return false;
        }
        
    }
    Event.observe(window, 'load', initAll, false);
    </script>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        <html:form action="/checkMarkLevelAdjust" method="post" onsubmit="">
        
        <fieldset>
            <legend>&nbsp;老年年金編審註記程度調整&nbsp;</legend>
            
            <div align="right" id="showtime">
                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
                <tr>
                    <td colspan="2" align="right">
                        <html:hidden styleId="page" property="page" value="1" />
                        <html:hidden styleId="method" property="method" value="" />
                        <acl:checkButton buttonName="btnQuery">
                            <input tabindex="80" type="button" name="btnQuery" class="button" value="確  認" onclick="$('page').value='1'; $('method').value='doQuery'; if (checkFields()){getProcStat();}else{return false;}" />
                        </acl:checkButton>
                        &nbsp;
                        <acl:checkButton buttonName="btnReset">
                            <input tabindex="90" type="reset" name="btnReset" class="button" value="清  除" />
                        </acl:checkButton>
                    </td>
                </tr>
                <tr>
                    <td width="30%" align="right">
                         <span class="needtxt">＊</span>
                         <span class="issuetitle_R_down">受理編號：</span></td>
                         <td>
                         <span class="formtxt">
                            <html:text tabindex="10" styleId="apNo1" property="apNo1" size="1" maxlength="1" styleClass="textinput" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo2'));" onkeypress="" />
                            &nbsp;-&nbsp;
                            <html:text tabindex="20" styleId="apNo2" property="apNo2" size="1" maxlength="1" styleClass="textinput" onkeyup="autotab(this, $('apNo3'));" />
                            &nbsp;-&nbsp;
                            <html:text tabindex="30" styleId="apNo3" property="apNo3" size="5" maxlength="5" styleClass="textinput" onkeyup="autotab(this, $('apNo4'));" />
                            &nbsp;-&nbsp;
                            <html:text tabindex="40" styleId="apNo4" property="apNo4" size="5" maxlength="5" styleClass="textinput" />                          
                          </span>
                    </td>
                </tr>
                <tr>
                    <td align="right"><span class="needtxt">＊</span><span class="issuetitle_R_down">排序方式：</span></td>
                    <td class="formtxt">
                        <html:radio tabindex="50" styleId="orderBy1" property="orderBy" value="1" />給付年月&nbsp;
                        <html:radio tabindex="60" styleId="orderBy2" property="orderBy" value="2" />受款人&nbsp;
                        <html:radio tabindex="70" styleId="orderBy3" property="orderBy" value="3" />編審註記
                    </td>        
               </tr>
                <tr>
                    <td colspan="2">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="2"><hr size="1" noshade="noshade">
                        <span class="titleinput">※注意事項：</span><br>
            　                                                  1.&nbsp;<span class="needtxt">＊</span>為必填欄位。</td>
                </tr>
            </table>
        </fieldset>
        
        </html:form>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
