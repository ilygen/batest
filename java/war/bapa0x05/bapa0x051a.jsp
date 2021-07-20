<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
	<acl:setProgId progId="BAPA0X051A" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
    <script language="javascript" type="text/javascript">
    
    	function chkSelect(obj) {
     		for(i=0;i<document.forms[0].length;i++){
    			if(document.forms[0][i].name==obj.name){
    				for(j=i;j<document.forms[0].length;j++){
    					if(document.forms[0][j].name==obj.name){
    						if (document.forms[0][j].selectedIndex==obj.selectedIndex){
    							if ((document.forms[0][j]!=obj)&&(obj.value!='')){
	    							obj.value = '';
	    							alert('扣減順位不可重複');
    							}
    						}    						
    					}
					}
					break;
    			}
			}
      	}
      	
      	function chkSelectOnSubmmit() {
      		for(i=0;i<document.forms[0].length;i++){
      			for(j=i;j<document.forms[0].length;j++){
    				if(document.forms[0][j].name=='cutSeq'){
    					if(document.forms[0][j].value=='' ){
    						alert('請選擇扣減順位');
    						return false;
    					}    					
    				}	
				}
      		}
      		return true;
      	}   
      	
      	      
      
	</script>
</head>

<body scroll="no">
	<div id="mainContent">
  		<%@ include file="/includes/ba_header.jsp"%>
    	<%@ include file="/includes/ba_menu.jsp"%>
    	<div id="main" class="mainBody">
    	<html:form action="/deductItemMaintDetail" method="post" >
		    <fieldset>
		    <legend>&nbsp;扣減參數維護作業&nbsp;</legend>
		      	<div align="right" id="showtime">
		      		網頁下載時間：網頁下載時間：民國&nbsp;<func:nowDateTime /></script>
		      	</div>
		        <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
		        	
		        	<tr>
			            <td align="right" colspan="3">                        	
	                        <acl:checkButton buttonName="btnSave">
	                            <input type="button" name="btnSave" class="button" value="確  認" onclick="$('page').value='1';$('method').value='doSave';if(chkSelectOnSubmmit()){document.DeductItemMaintDetailForm.submit();}else{return false;}" />
	                        </acl:checkButton>
	                        &nbsp;
	                        <acl:checkButton buttonName="btnBack">
	                            <input type="button" name="btnBack" class="button" value="返  回" onclick="location.href='<c:url value="/enterDeductItemMaint.do?parameter=enterDeductItemMaint" />';" />
	                        </acl:checkButton>
                    	</td>
		          	</tr>
			        <tr>
			        	<td colspan="3" class="issuetitle_L">給付種類：
			        		<span class="px13">
			        			<!-- <bean:write property="payCode" name="<%=ConstantKey.DEDUCT_ITEM_MAINT_DETAIL_FORM%>" /> -->
			        			<logic:equal property="payCode" name="<%=ConstantKey.DEDUCT_ITEM_MAINT_DETAIL_FORM%>" value="L" >
										<c:out value="老人年金"/>
								</logic:equal>
								<logic:equal property="payCode" name="<%=ConstantKey.DEDUCT_ITEM_MAINT_DETAIL_FORM%>" value="K" >
										<c:out value="失能年金"/>
								</logic:equal>
								<logic:equal property="payCode" name="<%=ConstantKey.DEDUCT_ITEM_MAINT_DETAIL_FORM%>" value="S" >
										<c:out value="遺囑年金"/>
								</logic:equal>
			        		</span>
			        	</td>
			        </tr>
			        <tr>
			        	<td align="center">
			        		<bean:define id="list" name="<%=ConstantKey.DEDUCT_ITEM_MAINT_DETAIL_FORM%>" />
			        		<html:hidden styleId="page" property="page" value="1" />
				        	<html:hidden styleId="method" property="method" value="" />
				            <html:hidden styleId="cutTyp" property="cutTyp" />
							<html:hidden styleId="payCode" property="payCode" />
							<display:table name="pageScope.list.data" id="listItem" pagesize="<%=ConstantKey.DETAIL_LIST_PAGE_SIZE%>">
								<display:column title="序號" style="width:3%; text-align: right;">
									<c:out value="${listItem_rowNum}" />
								</display:column>
								<display:column property="cutTypName" title="扣減項目" style="width:20%;text-align: center;" />								
							 	<display:column title="<font color='red'>＊</font>扣減順位" style="width:20%;text-align: center;">
									<html:select property="cutSeq" onchange="chkSelect(this)" >
              							<option value="">請選擇...</option>
							        	<option value="0" <c:if test='${(listItem.cutSeq eq "0")}'><c:out value="selected" /></c:if>>順位一</option>
							            <option value="1" <c:if test='${(listItem.cutSeq eq "1")}'><c:out value="selected" /></c:if>>順位二</option>
							            <option value="2" <c:if test='${(listItem.cutSeq eq "2")}'><c:out value="selected" /></c:if>>順位三</option>
							            <option value="3" <c:if test='${(listItem.cutSeq eq "3")}'><c:out value="selected" /></c:if>>順位四</option>
							            <option value="4" <c:if test='${(listItem.cutSeq eq "4")}'><c:out value="selected" /></c:if>>順位五</option>
							            <option value="5" <c:if test='${(listItem.cutSeq eq "5")}'><c:out value="selected" /></c:if>>順位六</option>
            						</html:select>
								</display:column>
							</display:table>
			        	</td>
			        </tr>
			        <tr>
			         	<td colspan="3"><hr size="1" noshade>
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