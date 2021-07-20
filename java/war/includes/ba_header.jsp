<%@ page language="java" pageEncoding="UTF-8"%>
<div id="banner">

<%
    UserBean user = (UserBean) UserSessionHelper.getUserData(request);
    if (user == null) {
%>
    <div id="sys_name" style="background-image:url(<c:url value="/images/title.gif"/>)">
<%
    }
    else {
%>        
        <c:set var="fileName" value="/images/${userData.headerFileName}"/>
        <div id="sys_name" style="background-image:url(<c:url value="${fileName}"/>)">
<%
    }
%>
    
    
    
        <table width="100%" class="sys_info">
            <tr>
                <td>
                    <p>員工編號：<c:out value="${userData.empNo}" /></p>
                    <p>部門代號：<c:out value="${userData.deptId}" /></p>
                    <p>登入日期：<c:out value="${userData.loginDateString}" /></p>
                    <p>登入時間：<c:out value="${userData.loginTimeString}" /></p>
                </td>
            </tr>
        </table>
    </div>
</div>
