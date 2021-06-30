<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/includes/include.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html:html lang="true">
<head>
    <acl:setProgId progId="BAIQ0D052Q" />
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" />
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script>
</head>
<body scroll="no">

<div id="mainContent">

    <%@ include file="/includes/ba_header.jsp"%>

    <%@ include file="/includes/ba_menu.jsp"%>

    <div id="main" class="mainBody">
        
        <fieldset>
            <legend>&nbsp;行政支援查詢&nbsp;</legend>
            
            <div align="right" id="showtime">
                                網頁下載時間：民國&nbsp;<func:nowDateTime />
            </div>
            
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13">
            <tr>
                <td align="right" colspan="9">
                    <acl:checkButton buttonName="btnBack">
                        <input type="button" name="btnBack" class="button" value="返  回" onclick="history.go(-1);" />
                    </acl:checkButton>
                </td>
            </tr>
            <tr>
                <td colspan="9">
                    <table width="100%" border="0" cellpadding="2" cellspacing="2" class="px13">
                    <tr>
                        <td width="25%">
                            <span class="issuetitle_L_down">受理編號：</span>
                            <c:out value="${ExecutiveSupportQueryCase.apNoStr}" />
                        </td>
                        <td width="25%">
                            <span class="issuetitle_L_down">給付別：</span>
                            <c:out value="${ExecutiveSupportQueryCase.payKindStr}" />
                        </td>
                        <td width="25%">
                            <span class="issuetitle_L_down">審核人員：</span>
                            <c:out value="${ExecutiveSupportQueryCase.chkMan}" />
                        </td>
                        <td>
                            <span class="issuetitle_L_down">核定年月：</span>
                            <c:out value="${ExecutiveSupportQueryDataCase.issuYm}" />
                        </td>
                        
                    </tr>
                    <tr>
                        <td colspan="2">
                            <span class="issuetitle_L_down">事故者姓名：</span>
                            <c:out value="${ExecutiveSupportQueryCase.evtName}" />
                        </td>
                        <td>
                            <span class="issuetitle_L_down">事故者身分證號：</span>
                            <c:out value="${ExecutiveSupportQueryCase.evtIdnNo}" />
                        </td>
                        <td>
                            <span class="issuetitle_L_down">事故者出生日期：</span>
                            <c:out value="${ExecutiveSupportQueryCase.evtBrDate}" />
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="issuetitle_L_down">序號：</span>
                            <c:out value="${ExecutiveSupportQueryDataCase.seqNo}" />
                        </td>
                        <td colspan="3">
                        	<span class="issuetitle_L_down">來源別：</span>
                        	<c:out value="${ExecutiveSupportQueryDataCase.sourceStr}" />
                        </td>	
                    </tr>   
                    </table>
                </td>
            </tr>
            <tr>
	            <td colspan="8" align="center" class="table1">
                      <table width="98%" cellpadding="3" cellspacing="5" class="px13">
			            <tr>
			                <td id="iss"  width="50%">
			                    <span class="issuetitle_L_down">處理函別：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.paramName}" />
			                </td>
			                <td width="50%" id="iss">
			                    <span class="issuetitle_L_down">承辦日期：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.proDate}" />
			                </td>
				         </tr>
			             <tr>
			                <td colspan="2" id="iss">
			                    <span class="issuetitle_L_down">承辦人員：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.promoteUser}" />
			                </td>
			             </tr>
			             <tr>
			                <td id="iss">
			                    <span class="issuetitle_L_down">函別內容一/救濟事由：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.ndomk1}" />
			                    <c:if test="${(ExecutiveSupportQueryDataCase.ndomk1 ne '')or(ExecutiveSupportQueryDataCase.ndomkName1 ne '')}">
			                    -
			                    </c:if>
			                    <c:out value="${ExecutiveSupportQueryDataCase.ndomkName1}" />
			                </td>
			                <td  id="iss">
			                    <span class="issuetitle_L_down">函別內容二/救濟事由：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.ndomk2}" />
			                    <c:if test="${(ExecutiveSupportQueryDataCase.ndomk2 ne '')or(ExecutiveSupportQueryDataCase.ndomkName2 ne '')}">
			                    -
			                    </c:if>
			                    <c:out value="${ExecutiveSupportQueryDataCase.ndomkName2}" />
			                </td>
			             </tr>
			              <tr>
			                <td id="iss">
			                    <span class="issuetitle_L_down">救濟種類：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.reliefKind}" />
			                </td>
			                <td id="iss">
			                    <span class="issuetitle_L_down">行政救濟辦理情形：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.reliefStat}" />
			                </td>
			            </tr>
			            <tr>
			                <td colspan="2" id="iss">
			                    <span class="issuetitle_L_down">給付性質：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.reliefTyp}" />
			                </td>
			            </tr>
			             <tr>
			                <td id="iss">
			                    <span class="issuetitle_L_down">來受文號：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.slrpNoStr}" />
			                </td>
			                <td id="iss">
			                    <span class="issuetitle_L_down">來受文日期：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.slDate}" />
			                </td>
			              </tr>
					      <tr>
			                <td id="iss">
			                    <span class="issuetitle_L_down">來受文機關代號：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.unNo}" />
			                </td>
			                <td id="iss">
			                    <span class="issuetitle_L_down">來受文機關名稱：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.unDes}" />
			                </td>
			              </tr>
			              <tr>
			                <td valign="top" id="iss">
			                    <span class="issuetitle_L_down">主　　旨：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.note}" /><br>
			                </td>
			                <td id="iss">
			                    <span class="issuetitle_L_down">分類號：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.caseTyp}" />
			                </td>
			              </tr>
			              <tr>
			                <td id="iss">
			                    <span class="issuetitle_L_down">承辦註記：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.sddMk}" />
			                </td>
			                <td id="iss">
			                    <span class="issuetitle_L_down">管制註記：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.delMk}" />
			                </td>
			              </tr>
					    <tr>
			                <td id="iss">
			                    <span class="issuetitle_L_down">承辦完成日：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.finishDate}" />
			                </td>				
			                <td id="iss">
			                    <span class="issuetitle_L_down">相關文號：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.slrelateStr}" />
			                </td>				 
			            </tr>
			            <tr>
			                <td id="iss">
			                    <span class="issuetitle_L_down">銷案日期：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.closDate}" />
			                </td>
			                <td id="iss">
			                    <span class="issuetitle_L_down">銷案文號：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.closeNoStr}" />
			                </td>
			            </tr>
			            <tr>
			                <td id="iss">
			                    <span class="issuetitle_L_down">醫院代號：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.hpNo}" />
			                </td>
			                <td id="iss">
			                    <span class="issuetitle_L_down">爭議部位：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.injser}" />
			                </td>
			            </tr>
					    <tr>
			                <td id="iss" colspan="2">
			                    <span class="issuetitle_L_down">單位保險證號：</span>
			                    <c:out value="${ExecutiveSupportQueryDataCase.unitNo}" />
			                </td>
			            </tr>
			           
			            
			          </table>
		           </td>
	            </tr>
	            <tr>
	                <td colspan="2">&nbsp;</td>
	            </tr>
            </table>
        </fieldset>
    </div>
</div>

<%@ include file="/includes/ba_footer.jsp"%>

</body>
</html:html>
