<%@ page language="java" pageEncoding="UTF-8"%>
<div id="footer">
    <div id="ifon">
        <span class="systemMsg">【訊息區】</span>
        &nbsp;&nbsp;
        <span id="message" class="message">
            <logic:messagesPresent message="true">
                <html:messages id="msg" message="true" bundle="<%=Global.BA_MSG%>">
                    <bean:write name="msg" bundle="<%=Global.BA_MSG%>" />
                </html:messages>
            </logic:messagesPresent>
        </span>
    </div>
    <div id="prog"><acl:getProgId /></div>
</div>
<logic:messagesPresent message="false">
    <script language="javascript" type="text/javascript">
    <!--
    showErrorMessage('<html:errors bundle="<%=Global.BA_MSG%>" />');
    -->
    </script>
</logic:messagesPresent>
