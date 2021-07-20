<%@ page language="java" pageEncoding="UTF-8"%> 
<%@ include file="/includes/include.jsp"%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN"> 
<html:html lang="true"> 
<head> 
    <acl:setProgId progId="BALP0D010L" /> 
    <title><bean:message bundle="<%=Global.BA_MSG%>" key="title.system.name" /></title> 
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/bastyle.css'/>" /> 
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/menu.css'/>" /> 
    <script type='text/javascript' src='<c:url value="/js/prototype.js"/>'></script> 
    <script type='text/javascript' src='<c:url value="/js/ba_functions.js"/>'></script> 
    <script type='text/javascript' src='<c:url value="/js/ba_onload.js"/>'></script> 
    <html:javascript formName="OldAgeReviewRpt01Form" page="1" /> 
    <script language="javascript" type="text/javascript"> 
    <!-- 
    // 如果受理編號只打了起值, 則自動將迄值帶入起值 
    function copyValue(oring, dest) { 
        dest.value = asc(oring.value); 
    } 
    <%-- begin 檢查受理編號區間 --%> 
	function checkFields() { 
		// 如果選輸入受理編號起訖，判斷input裡的值是否都有填入符合規則的值 
		if("single" === $("action").value){ 
 
			var msg = ""; 
 
			var apNoAllBeginNum = parseInt($("apNo2Begin").value+""+$("apNo3Begin").value+""+$("apNo4Begin").value,10); 
			var apNoAllEndNum = parseInt($("apNo2End").value+""+$("apNo3End").value+""+$("apNo4End").value,10); 
			var apNo1BeginVal = Trim($("apNo1Begin").value); 
			var apNo2BeginVal = Trim($("apNo2Begin").value); 
			var apNo3BeginVal = Trim($("apNo3Begin").value); 
			var apNo4BeginVal = Trim($("apNo4Begin").value); 
			var apNo1EndVal = Trim($("apNo1End").value); 
			var apNo2EndVal = Trim($("apNo2End").value); 
			var apNo3EndVal = Trim($("apNo3End").value); 
			var apNo4EndVal = Trim($("apNo4End").value); 
			 
			var regNum = /[0-9]/; 
			var regEng = /[SKL]/; 
			 
			if(apNo1BeginVal === "") { 
				msg += "「受理編號 (起) 第一欄」為必填欄位。\r\n"; 
			} 
			 
			if(apNo2BeginVal === "") { 
				msg += "「受理編號 (起) 第二欄」為必填欄位。\r\n"; 
			} 
			 
			if(apNo3BeginVal === "") { 
				msg += "「受理編號 (起) 第三欄」為必填欄位。\r\n"; 
			} 
			 
			if(apNo4BeginVal === "") { 
				msg += "「受理編號 (起) 第四欄」為必填欄位。\r\n"; 
			} 
			 
			if(apNo1EndVal === "") { 
				msg += "「受理編號 (訖) 第一欄」為必填欄位。\r\n"; 
			} 
			 
			if(apNo2EndVal === "") { 
				msg += "「受理編號 (迄) 第二欄」為必填欄位。\r\n"; 
			} 
			 
			if(apNo3EndVal === "") { 
				msg += "「受理編號 (迄) 第三欄」為必填欄位。\r\n"; 
			} 
			 
			if(apNo4EndVal === "") { 
				msg += "「受理編號 (迄) 第四欄」為必填欄位。\r\n"; 
			} 
			 
			if(!regNum.test(apNo2BeginVal) && apNo2BeginVal !== "") { 
				msg += "「受理編號 (起) 第二欄」限制僅能輸入數字。\r\n"; 
			} 
			 
			if(!regNum.test(apNo3BeginVal) && apNo3BeginVal !== "") { 
				msg += "「受理編號 (起) 第三欄」限制僅能輸入數字。\r\n"; 
			} 
			 
			if(!regNum.test(apNo4BeginVal) && apNo4BeginVal !== "") { 
				msg += "「受理編號 (起) 第四欄」限制僅能輸入數字。\r\n"; 
			} 
			 
			if(!regNum.test(apNo2EndVal) && apNo2EndVal !== "") { 
				msg += "「受理編號 (迄) 第二欄」限制僅能輸入數字。\r\n"; 
			} 
			 
			if(!regNum.test(apNo3EndVal) && apNo3EndVal !== "") { 
				msg += "「受理編號 (迄) 第三欄」限制僅能輸入數字。\r\n"; 
			} 
			 
			if(!regNum.test(apNo4EndVal) && apNo4EndVal !== "") { 
				msg += "「受理編號 (迄) 第四欄」限制僅能輸入數字。\r\n"; 
			} 
			 
			if(apNo3BeginVal.length < 5 && apNo3BeginVal !== "") { 
				msg += "「受理編號 (起) 第三欄」不可小於 5 個字元。\r\n"; 
			} 
			 
			if(apNo4BeginVal.length < 5 && apNo4BeginVal !== "") { 
				msg += "「受理編號 (起) 第四欄」不可小於 5 個字元。\r\n"; 
			} 
			 
			if(apNo3EndVal.length < 5 && apNo3EndVal !== "") { 
				msg += "「受理編號 (迄) 第三欄」不可小於 5 個字元。\r\n"; 
			} 
			 
			if(apNo4EndVal.length < 5 && apNo4EndVal !== "") { 
				msg += "「受理編號 (迄) 第四欄」不可小於 5 個字元。\r\n"; 
			} 
			 
			if(!regEng.test(apNo1BeginVal) && apNo1BeginVal !== "") { 
				msg += "「受理編號 (起) 第一欄」限制僅能輸入「L、K、S」。\r\n"; 
			} 
			 
			if(!regEng.test(apNo1EndVal) && (apNo1EndVal !== "")) { 
				msg += "「受理編號 (迄) 第一欄」限制僅能輸入「L、K、S」。\r\n"; 
			} 
			 
			if(apNoAllEndNum - apNoAllBeginNum > 500 || apNoAllEndNum - apNoAllBeginNum < 0) 
				msg += '受理編號起迄區間需小於等於500筆。\r\n';        		  	            	 
	            		 
			if (msg != "") { 
				alert(msg); 
				return false; 
			} else { 
				return true; 
			}			 
		} 
		// 如果選輸入多筆受理編號，確認是否至少有填一個 
		if("multiple" === $("action").value) { 
			 
			var apNo1Val = Trim($("apNo1").value); 
			var apNo2Val = Trim($("apNo2").value); 
			var apNo3Val = Trim($("apNo3").value); 
			var apNo4Val = Trim($("apNo4").value); 
			var apNo5Val = Trim($("apNo5").value); 
			var apNo6Val = Trim($("apNo6").value); 
			var apNo7Val = Trim($("apNo7").value); 
			var apNo8Val = Trim($("apNo8").value); 
			var apNo9Val = Trim($("apNo9").value); 
			var apNo10Val = Trim($("apNo10").value); 
			var apNo11Val = Trim($("apNo11").value); 
			var apNo12Val = Trim($("apNo12").value); 
			var apNo13Val = Trim($("apNo13").value); 
			var apNo14Val = Trim($("apNo14").value); 
			var apNo15Val = Trim($("apNo15").value); 
			var apNo16Val = Trim($("apNo16").value); 
			var apNo17Val = Trim($("apNo17").value); 
			var apNo18Val = Trim($("apNo18").value); 
			var apNo19Val = Trim($("apNo19").value); 
			var apNo20Val = Trim($("apNo20").value); 
			var apNo21Val = Trim($("apNo21").value); 
			var apNo22Val = Trim($("apNo22").value); 
			var apNo23Val = Trim($("apNo23").value); 
			var apNo24Val = Trim($("apNo24").value); 
			var apNo25Val = Trim($("apNo25").value); 
			var apNo26Val = Trim($("apNo26").value); 
			var apNo27Val = Trim($("apNo27").value); 
			var apNo28Val = Trim($("apNo28").value); 
			var apNo29Val = Trim($("apNo29").value); 
			var apNo30Val = Trim($("apNo30").value); 
			var apNo31Val = Trim($("apNo31").value); 
			var apNo32Val = Trim($("apNo32").value); 
			var apNo33Val = Trim($("apNo33").value); 
			var apNo34Val = Trim($("apNo34").value); 
			var apNo35Val = Trim($("apNo35").value); 
			var apNo36Val = Trim($("apNo36").value); 
			var apNo37Val = Trim($("apNo37").value); 
			var apNo38Val = Trim($("apNo38").value); 
			var apNo39Val = Trim($("apNo39").value); 
			var apNo40Val = Trim($("apNo40").value); 
			var apNo41Val = Trim($("apNo41").value); 
			var apNo42Val = Trim($("apNo42").value); 
			var apNo43Val = Trim($("apNo43").value); 
			var apNo44Val = Trim($("apNo44").value); 
			var apNo45Val = Trim($("apNo45").value); 
			var apNo46Val = Trim($("apNo46").value); 
			var apNo47Val = Trim($("apNo47").value); 
			var apNo48Val = Trim($("apNo48").value); 
			var apNo49Val = Trim($("apNo49").value); 
			var apNo50Val = Trim($("apNo50").value); 
			var apNo51Val = Trim($("apNo51").value); 
			var apNo52Val = Trim($("apNo52").value); 
			var apNo53Val = Trim($("apNo53").value); 
			var apNo54Val = Trim($("apNo54").value); 
			var apNo55Val = Trim($("apNo55").value); 
			var apNo56Val = Trim($("apNo56").value); 
			var apNo57Val = Trim($("apNo57").value); 
			var apNo58Val = Trim($("apNo58").value); 
			var apNo59Val = Trim($("apNo59").value); 
			var apNo60Val = Trim($("apNo60").value); 
			var apNo61Val = Trim($("apNo61").value); 
			var apNo62Val = Trim($("apNo62").value); 
			var apNo63Val = Trim($("apNo63").value); 
			var apNo64Val = Trim($("apNo64").value); 
			var apNo65Val = Trim($("apNo65").value); 
			var apNo66Val = Trim($("apNo66").value); 
			var apNo67Val = Trim($("apNo67").value); 
			var apNo68Val = Trim($("apNo68").value); 
			var apNo69Val = Trim($("apNo69").value); 
			var apNo70Val = Trim($("apNo70").value); 
			var apNo71Val = Trim($("apNo71").value); 
			var apNo72Val = Trim($("apNo72").value); 
			var apNo73Val = Trim($("apNo73").value); 
			var apNo74Val = Trim($("apNo74").value); 
			var apNo75Val = Trim($("apNo75").value); 
			var apNo76Val = Trim($("apNo76").value); 
			var apNo77Val = Trim($("apNo77").value); 
			var apNo78Val = Trim($("apNo78").value); 
			var apNo79Val = Trim($("apNo79").value); 
			var apNo80Val = Trim($("apNo80").value); 
			var msg = ""; 
			var regNum = /[0-9]/; 
			var regEng = /[SKL]/; 
			var cnt = 0;
			
			if(apNo1Val !== "" || apNo2Val !== "" || apNo3Val !== "" || apNo4Val !== "") { 
				if(apNo1Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo2Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo3Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo4Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo1Val) && apNo1Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo2Val) && apNo2Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo3Val) && apNo3Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo4Val) && apNo4Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo3Val.length < 5 && apNo3Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo4Val.length < 5 && apNo4Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo5Val !== "" || apNo6Val !== "" || apNo7Val !== "" || apNo8Val !== "") { 
				if(apNo5Val === "") {
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo6Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo7Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo8Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo5Val) && apNo5Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo6Val) && apNo6Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo7Val) && apNo7Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo8Val) && apNo8Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo7Val.length < 5 && apNo7Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo8Val.length < 5 && apNo8Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo9Val !== "" || apNo10Val !== "" || apNo11Val !== "" || apNo12Val !== "") { 
				if(apNo9Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo10Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo11Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo12Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo9Val) && apNo9Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo10Val) && apNo10Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo11Val) && apNo11Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo12Val) && apNo12Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo11Val.length < 5 && apNo11Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo12Val.length < 5 && apNo12Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo13Val !== "" || apNo14Val !== "" || apNo15Val !== "" || apNo16Val !== "") { 
				if(apNo13Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo14Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo15Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo16Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo13Val) && apNo13Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo14Val) && apNo14Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo15Val) && apNo15Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo16Val) && apNo16Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo15Val.length < 5 && apNo15Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo16Val.length < 5 && apNo16Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo17Val !== "" || apNo18Val !== "" || apNo19Val !== "" || apNo20Val !== "") { 
				if(apNo17Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo18Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo19Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo20Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo17Val) && apNo17Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo18Val) && apNo18Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo19Val) && apNo19Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo20Val) && apNo20Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo19Val.length < 5 && apNo19Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo20Val.length < 5 && apNo20Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo21Val !== "" || apNo22Val !== "" || apNo23Val !== "" || apNo24Val !== "") { 
				if(apNo21Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo22Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo23Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo24Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo21Val) && apNo21Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo22Val) && apNo22Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo23Val) && apNo23Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo24Val) && apNo24Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo23Val.length < 5 && apNo23Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo24Val.length < 5 && apNo24Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo25Val !== "" || apNo26Val !== "" || apNo27Val !== "" || apNo28Val !== "") { 
				if(apNo25Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo26Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo27Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo28Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo25Val) && apNo25Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo26Val) && apNo26Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo27Val) && apNo27Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo28Val) && apNo28Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo27Val.length < 5 && apNo27Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo28Val.length < 5 && apNo28Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo29Val !== "" || apNo30Val !== "" || apNo31Val !== "" || apNo32Val !== "") { 
				if(apNo29Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo30Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo31Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo32Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo29Val) && apNo29Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo30Val) && apNo30Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo31Val) && apNo31Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo32Val) && apNo32Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo31Val.length < 5 && apNo31Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo32Val.length < 5 && apNo32Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo33Val !== "" || apNo34Val !== "" || apNo35Val !== "" || apNo36Val !== "") { 
				if(apNo33Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo34Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo35Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo36Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo33Val) && apNo33Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo34Val) && apNo34Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo35Val) && apNo35Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo36Val) && apNo36Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo35Val.length < 5 && apNo35Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo36Val.length < 5 && apNo36Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo37Val !== "" || apNo38Val !== "" || apNo39Val !== "" || apNo40Val !== "") { 
				if(apNo37Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo38Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo39Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo40Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo37Val) && apNo37Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo38Val) && apNo38Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo39Val) && apNo39Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo40Val) && apNo40Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo39Val.length < 5 && apNo39Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo40Val.length < 5 && apNo40Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo41Val !== "" || apNo42Val !== "" || apNo43Val !== "" || apNo44Val !== "") { 
				if(apNo41Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo42Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo43Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo44Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo41Val) && apNo41Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo42Val) && apNo42Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo43Val) && apNo43Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo44Val) && apNo44Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo43Val.length < 5 && apNo43Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo44Val.length < 5 && apNo44Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo45Val !== "" || apNo46Val !== "" || apNo47Val !== "" || apNo48Val !== "") { 
				if(apNo45Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo46Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo47Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo48Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo45Val) && apNo45Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo46Val) && apNo46Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo47Val) && apNo47Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo48Val) && apNo48Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo47Val.length < 5 && apNo47Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo48Val.length < 5 && apNo48Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo49Val !== "" || apNo50Val !== "" || apNo51Val !== "" || apNo52Val !== "") { 
				if(apNo49Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo50Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo51Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo52Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo49Val) && apNo49Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo50Val) && apNo50Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo51Val) && apNo51Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo52Val) && apNo52Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo51Val.length < 5 && apNo51Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo52Val.length < 5 && apNo52Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo53Val !== "" || apNo54Val !== "" || apNo55Val !== "" || apNo56Val !== "") { 
				if(apNo53Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo54Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo55Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo56Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo53Val) && apNo53Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo54Val) && apNo54Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo55Val) && apNo55Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo56Val) && apNo56Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo55Val.length < 5 && apNo55Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo56Val.length < 5 && apNo56Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo57Val !== "" || apNo58Val !== "" || apNo59Val !== "" || apNo60Val !== "") { 
				if(apNo57Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo58Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo59Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo60Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo57Val) && apNo57Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo58Val) && apNo58Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo59Val) && apNo59Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo60Val) && apNo60Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo59Val.length < 5 && apNo59Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo60Val.length < 5 && apNo60Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo61Val !== "" || apNo62Val !== "" || apNo63Val !== "" || apNo64Val !== "") { 
				if(apNo61Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo62Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo63Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo64Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo61Val) && apNo61Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo62Val) && apNo62Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo63Val) && apNo63Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo64Val) && apNo64Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo63Val.length < 5 && apNo63Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo64Val.length < 5 && apNo64Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo65Val !== "" || apNo66Val !== "" || apNo67Val !== "" || apNo68Val !== "") { 
				if(apNo65Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo66Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo67Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo68Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo65Val) && apNo65Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo66Val) && apNo66Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo67Val) && apNo67Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo68Val) && apNo68Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo67Val.length < 5 && apNo67Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo68Val.length < 5 && apNo68Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo69Val !== "" || apNo70Val !== "" || apNo71Val !== "" || apNo72Val !== "") { 
				if(apNo69Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo70Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo71Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo72Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo69Val) && apNo69Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo70Val) && apNo70Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo71Val) && apNo71Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo72Val) && apNo72Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo71Val.length < 5 && apNo71Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo72Val.length < 5 && apNo72Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo73Val !== "" || apNo74Val !== "" || apNo75Val !== "" || apNo76Val !== "") { 
				if(apNo73Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo74Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo75Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo76Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo73Val) && apNo73Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo74Val) && apNo74Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo75Val) && apNo75Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo76Val) && apNo76Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo75Val.length < 5 && apNo75Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo76Val.length < 5 && apNo76Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(apNo77Val !== "" || apNo78Val !== "" || apNo79Val !== "" || apNo80Val !== "") { 
				if(apNo77Val === "") { 
					msg += "「受理編號第一欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo78Val === "") { 
					msg += "「受理編號第二欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo79Val === "") { 
					msg += "「受理編號第三欄」為必填欄位。\r\n"; 
				} 
				 
				if(apNo80Val === "") { 
					msg += "「受理編號第四欄」為必填欄位。\r\n"; 
				} 
				 
				if(!regEng.test(apNo77Val) && apNo77Val !== "") { 
					msg += "「受理編號第一欄」限制僅能輸入「L、K、S」。\r\n"; 
				} 
				 
				if(!regNum.test(apNo78Val) && apNo78Val !== "") { 
					msg += "「受理編號第二欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo79Val) && apNo79Val !== "") { 
					msg += "「受理編號第三欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(!regNum.test(apNo80Val) && apNo80Val !== "") { 
					msg += "「受理編號第四欄」限制僅能輸入數字。\r\n"; 
				} 
				 
				if(apNo79Val.length < 5 && apNo79Val !== "") { 
					msg += "「受理編號第三欄」不可小於 5 個字元。\r\n"; 
				} 
				 
				if(apNo80Val.length < 5 && apNo80Val !== "") { 
					msg += "「受理編號第四欄」不可小於 5 個字元。\r\n"; 
				} 
			}else {
				cnt++;
			}
			
			if(cnt === "20") { 
                msg += "「受理編號第一欄」為必填欄位。\r\n"; 
                msg += "「受理編號第二欄」為必填欄位。\r\n"; 
                msg += "「受理編號第三欄」為必填欄位。\r\n"; 
                msg += "「受理編號第四欄」為必填欄位。\r\n"; 
            } 
            
			var array = [apNo1Val, apNo5Val, apNo9Val, apNo13Val, apNo17Val, apNo21Val, apNo25Val, apNo29Val, apNo33Val, 
						 apNo37Val, apNo41Val, apNo45Val, apNo49Val, apNo53Val, apNo57Val, apNo61Val, apNo65Val, apNo69Val, 
						 apNo73Val, apNo77Val];
			for(var i = 0 ; i < array.length-1 ; i++) {
				for(var j = 1 ; j < array.length ; j++) {
					if((array[i] !== array[j]) && (array[i] !== "") && (array[j] !== "")) {
						msg+= "「受理編號第一欄」限制僅能統一輸入同一給付別，不允許跨給付別產製報表。";
						if(msg != "") {
							alert(msg);
							return false;
						}
					}
				}
			}
			
			if (msg != "") { 
				alert(msg); 
				return false; 
			} else { 
				return true; 
			}
			
		} 
			 
	} 
    // 點選輸入受理編號起訖，下方20個受理編號為不可輸入的狀態 
	function disableMultiple() { 
		$("action").value = "single"; 
		$("apNo1Begin").disabled = false; 
		$("apNo2Begin").disabled = false; 
		$("apNo3Begin").disabled = false; 
		$("apNo4Begin").disabled = false; 
		$("apNo1End").disabled = false; 
		$("apNo2End").disabled = false; 
		$("apNo3End").disabled = false; 
		$("apNo4End").disabled = false; 
		$("apNo1").value = ""; 
		$("apNo2").value = ""; 
		$("apNo3").value = ""; 
		$("apNo4").value = ""; 
		$("apNo5").value = ""; 
		$("apNo6").value = ""; 
		$("apNo7").value = ""; 
		$("apNo8").value = ""; 
		$("apNo9").value = ""; 
		$("apNo10").value = ""; 
		$("apNo11").value = ""; 
		$("apNo12").value = ""; 
		$("apNo13").value = ""; 
		$("apNo14").value = ""; 
		$("apNo15").value = ""; 
		$("apNo16").value = ""; 
		$("apNo17").value = ""; 
		$("apNo18").value = ""; 
		$("apNo19").value = ""; 
		$("apNo20").value = ""; 
		$("apNo21").value = ""; 
		$("apNo22").value = ""; 
		$("apNo23").value = ""; 
		$("apNo24").value = ""; 
		$("apNo25").value = ""; 
		$("apNo26").value = ""; 
		$("apNo27").value = ""; 
		$("apNo28").value = ""; 
		$("apNo29").value = ""; 
		$("apNo30").value = ""; 
		$("apNo31").value = ""; 
		$("apNo32").value = ""; 
		$("apNo33").value = ""; 
		$("apNo34").value = ""; 
		$("apNo35").value = ""; 
		$("apNo36").value = ""; 
		$("apNo37").value = ""; 
		$("apNo38").value = ""; 
		$("apNo39").value = ""; 
		$("apNo40").value = ""; 
		$("apNo41").value = ""; 
		$("apNo42").value = ""; 
		$("apNo43").value = ""; 
		$("apNo44").value = ""; 
		$("apNo45").value = ""; 
		$("apNo46").value = ""; 
		$("apNo47").value = ""; 
		$("apNo48").value = ""; 
		$("apNo49").value = ""; 
		$("apNo50").value = ""; 
		$("apNo51").value = ""; 
		$("apNo52").value = ""; 
		$("apNo53").value = ""; 
		$("apNo54").value = ""; 
		$("apNo55").value = ""; 
		$("apNo56").value = ""; 
		$("apNo57").value = ""; 
		$("apNo58").value = ""; 
		$("apNo59").value = ""; 
		$("apNo60").value = ""; 
		$("apNo61").value = ""; 
		$("apNo62").value = ""; 
		$("apNo63").value = ""; 
		$("apNo64").value = ""; 
		$("apNo65").value = ""; 
		$("apNo66").value = ""; 
		$("apNo67").value = ""; 
		$("apNo68").value = ""; 
		$("apNo69").value = ""; 
		$("apNo70").value = ""; 
		$("apNo71").value = ""; 
		$("apNo72").value = ""; 
		$("apNo73").value = ""; 
		$("apNo74").value = ""; 
		$("apNo75").value = ""; 
		$("apNo76").value = ""; 
		$("apNo77").value = ""; 
		$("apNo78").value = ""; 
		$("apNo79").value = ""; 
		$("apNo80").value = ""; 
		$("apNo1").disabled = true; 
		$("apNo2").disabled = true; 
		$("apNo3").disabled = true; 
		$("apNo4").disabled = true; 
		$("apNo5").disabled = true; 
		$("apNo6").disabled = true; 
		$("apNo7").disabled = true; 
		$("apNo8").disabled = true; 
		$("apNo9").disabled = true; 
		$("apNo10").disabled = true; 
		$("apNo11").disabled = true; 
		$("apNo12").disabled = true; 
		$("apNo13").disabled = true; 
		$("apNo14").disabled = true; 
		$("apNo15").disabled = true; 
		$("apNo16").disabled = true; 
		$("apNo17").disabled = true; 
		$("apNo18").disabled = true; 
		$("apNo19").disabled = true; 
		$("apNo20").disabled = true; 
		$("apNo21").disabled = true; 
		$("apNo22").disabled = true; 
		$("apNo23").disabled = true; 
		$("apNo24").disabled = true; 
		$("apNo25").disabled = true; 
		$("apNo26").disabled = true; 
		$("apNo27").disabled = true; 
		$("apNo28").disabled = true; 
		$("apNo29").disabled = true; 
		$("apNo30").disabled = true; 
		$("apNo31").disabled = true; 
		$("apNo32").disabled = true; 
		$("apNo33").disabled = true; 
		$("apNo34").disabled = true; 
		$("apNo35").disabled = true; 
		$("apNo36").disabled = true; 
		$("apNo37").disabled = true; 
		$("apNo38").disabled = true; 
		$("apNo39").disabled = true; 
		$("apNo40").disabled = true; 
		$("apNo41").disabled = true; 
		$("apNo42").disabled = true; 
		$("apNo43").disabled = true; 
		$("apNo44").disabled = true; 
		$("apNo45").disabled = true; 
		$("apNo46").disabled = true; 
		$("apNo47").disabled = true; 
		$("apNo48").disabled = true; 
		$("apNo49").disabled = true; 
		$("apNo50").disabled = true; 
		$("apNo51").disabled = true; 
		$("apNo52").disabled = true; 
		$("apNo53").disabled = true; 
		$("apNo54").disabled = true; 
		$("apNo55").disabled = true; 
		$("apNo56").disabled = true; 
		$("apNo57").disabled = true; 
		$("apNo58").disabled = true; 
		$("apNo59").disabled = true; 
		$("apNo60").disabled = true; 
		$("apNo61").disabled = true; 
		$("apNo62").disabled = true; 
		$("apNo63").disabled = true; 
		$("apNo64").disabled = true; 
		$("apNo65").disabled = true; 
		$("apNo66").disabled = true; 
		$("apNo67").disabled = true; 
		$("apNo68").disabled = true; 
		$("apNo69").disabled = true; 
		$("apNo70").disabled = true; 
		$("apNo71").disabled = true; 
		$("apNo72").disabled = true; 
		$("apNo73").disabled = true; 
		$("apNo74").disabled = true; 
		$("apNo75").disabled = true; 
		$("apNo76").disabled = true; 
		$("apNo77").disabled = true; 
		$("apNo78").disabled = true; 
		$("apNo79").disabled = true; 
		$("apNo80").disabled = true; 
	} 
     
    //點選輸入多筆受理編號，上方的受理編號起訖為不可輸入狀態 
	function disableSingle() { 
    	$("action").value = "multiple"; 
		$("apNo1Begin").value = ""; 
		$("apNo2Begin").value = ""; 
		$("apNo3Begin").value = ""; 
		$("apNo4Begin").value = ""; 
		$("apNo1End").value = ""; 
		$("apNo2End").value = ""; 
		$("apNo3End").value = ""; 
		$("apNo4End").value = ""; 
		$("apNo1Begin").disabled = true; 
		$("apNo2Begin").disabled = true; 
		$("apNo3Begin").disabled = true; 
		$("apNo4Begin").disabled = true; 
		$("apNo1End").disabled = true; 
		$("apNo2End").disabled = true; 
		$("apNo3End").disabled = true; 
		$("apNo4End").disabled = true; 
		$("apNo1").disabled = false; 
		$("apNo2").disabled = false; 
		$("apNo3").disabled = false; 
		$("apNo4").disabled = false; 
		$("apNo5").disabled = false; 
		$("apNo6").disabled = false; 
		$("apNo7").disabled = false; 
		$("apNo8").disabled = false; 
		$("apNo9").disabled = false; 
		$("apNo10").disabled = false; 
		$("apNo11").disabled = false; 
		$("apNo12").disabled = false; 
		$("apNo13").disabled = false; 
		$("apNo14").disabled = false; 
		$("apNo15").disabled = false; 
		$("apNo16").disabled = false; 
		$("apNo17").disabled = false; 
		$("apNo18").disabled = false; 
		$("apNo19").disabled = false; 
		$("apNo20").disabled = false; 
		$("apNo21").disabled = false; 
		$("apNo22").disabled = false; 
		$("apNo23").disabled = false; 
		$("apNo24").disabled = false; 
		$("apNo25").disabled = false; 
		$("apNo26").disabled = false; 
		$("apNo27").disabled = false; 
		$("apNo28").disabled = false; 
		$("apNo29").disabled = false; 
		$("apNo30").disabled = false; 
		$("apNo31").disabled = false; 
		$("apNo32").disabled = false; 
		$("apNo33").disabled = false; 
		$("apNo34").disabled = false; 
		$("apNo35").disabled = false; 
		$("apNo36").disabled = false; 
		$("apNo37").disabled = false; 
		$("apNo38").disabled = false; 
		$("apNo39").disabled = false; 
		$("apNo40").disabled = false; 
		$("apNo41").disabled = false; 
		$("apNo42").disabled = false; 
		$("apNo43").disabled = false; 
		$("apNo44").disabled = false; 
		$("apNo45").disabled = false; 
		$("apNo46").disabled = false; 
		$("apNo47").disabled = false; 
		$("apNo48").disabled = false; 
		$("apNo49").disabled = false; 
		$("apNo50").disabled = false; 
		$("apNo51").disabled = false; 
		$("apNo52").disabled = false; 
		$("apNo53").disabled = false; 
		$("apNo54").disabled = false; 
		$("apNo55").disabled = false; 
		$("apNo56").disabled = false; 
		$("apNo57").disabled = false; 
		$("apNo58").disabled = false; 
		$("apNo59").disabled = false; 
		$("apNo60").disabled = false; 
		$("apNo61").disabled = false; 
		$("apNo62").disabled = false; 
		$("apNo63").disabled = false; 
		$("apNo64").disabled = false; 
		$("apNo65").disabled = false; 
		$("apNo66").disabled = false; 
		$("apNo67").disabled = false; 
		$("apNo68").disabled = false; 
		$("apNo69").disabled = false; 
		$("apNo70").disabled = false; 
		$("apNo71").disabled = false; 
		$("apNo72").disabled = false; 
		$("apNo73").disabled = false; 
		$("apNo74").disabled = false; 
		$("apNo75").disabled = false; 
		$("apNo76").disabled = false; 
		$("apNo77").disabled = false; 
		$("apNo78").disabled = false; 
		$("apNo79").disabled = false; 
		$("apNo80").disabled = false; 
	} 
    Event.stopObserving(window, 'beforeunload', beforeUnload); 
    --> 
    </script> 
</head> 
<body scroll="no"> 
 
<div id="mainContent"> 
 
    <%@ include file="/includes/ba_header.jsp"%> 
 
    <%@ include file="/includes/ba_menu.jsp"%> 
 
    <div id="main" class="mainBody"> 
        <html:form action="/printOldAgeReviewRpt01" method="post"> 
         
        <fieldset> 
            <legend>&nbsp;勞保年金給付受理編審清單&nbsp;</legend> 
 
            <div align="right" id="showtime"> 
                網頁下載時間：民國&nbsp;<func:nowDateTime /> 
            </div> 
             
            <table width="98%" border="0" align="center" cellpadding="2" cellspacing="2" class="px13"> 
                <tr> 
                    <td colspan="2" align="right"> 
                        <html:hidden styleId="page" property="page" value="1" /> 
                        <html:hidden styleId="method" property="method" value="" /> 
                        <input type="hidden" id="action" name="action" value="multiple"/> 
                        <acl:checkButton buttonName="btnPrint"> 
                            <input type="button" id="btnPrint" name="btnPrint" tabindex="81" class="button" value="列　印" onclick="$('page').value='1'; $('method').value='doReport'; if (checkFields()){document.OldAgeReviewRpt01Form.submit();}else{return false;}" /> 
                        </acl:checkButton> 
                        &nbsp; 
                        <acl:checkButton buttonName="btnReset"> 
                            <input type="reset" id="btnReset" name="btnReset" tabindex="82" class="button" value="清  除"/> 
                        </acl:checkButton> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"><input type="radio" name="option" value="single" onclick="disableMultiple()">輸入受理編號起訖</td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="needtxt">＊</span><span class="issuetitle_R_down">受理編號起迄：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo1Begin" property="apNo1Begin" tabindex="1" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();copyValue(this, $('apNo1End'));autotab(this, $('apNo2Begin'));" disabled="true"/> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo2Begin" property="apNo2Begin" tabindex="2" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="copyValue(this, $('apNo2End'));autotab(this, $('apNo3Begin'));" disabled="true" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo3Begin" property="apNo3Begin" tabindex="3" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="copyValue(this, $('apNo3End'));autotab(this, $('apNo4Begin'));" disabled="true" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo4Begin" property="apNo4Begin" tabindex="4" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="copyValue(this, $('apNo4End'));autotab(this, $('apNo1End'));" disabled="true" /> 
                        &nbsp;～&nbsp; 
                        <html:text styleId="apNo1End" property="apNo1End" tabindex="5" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();copyValue(this, $('apNo1Begin'));autotab(this, $('apNo2End'));" disabled="true" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo2End" property="apNo2End" tabindex="6" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo3End'));" disabled="true" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo3End" property="apNo3End" tabindex="7" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo4End'));" disabled="true" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo4End" property="apNo4End" tabindex="8" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('btnPrint'));" disabled="true" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"><input type="radio" name="option" value="multiple" onclick="disableSingle()" checked>輸入多筆受理編號</td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">01.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo1" property="apNo1" tabindex="1" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo2'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo2" property="apNo2" tabindex="2" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo3'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo3" property="apNo3" tabindex="3" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo4'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo4" property="apNo4" tabindex="4" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo5'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">02.受理編號：</span> 
                        <html:text styleId="apNo5" property="apNo5" tabindex="5" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo6'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo6" property="apNo6" tabindex="6" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo7'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo7" property="apNo7" tabindex="7" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo8'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo8" property="apNo8" tabindex="8" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo9'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">03.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo9" property="apNo9" tabindex="9" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo10'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo10" property="apNo10" tabindex="10" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo11'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo11" property="apNo11" tabindex="11" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo12'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo12" property="apNo12" tabindex="12" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo13'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">04.受理編號：</span> 
                        <html:text styleId="apNo13" property="apNo13" tabindex="13" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo14'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo14" property="apNo14" tabindex="14" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo15'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo15" property="apNo15" tabindex="15" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo16'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo16" property="apNo16" tabindex="16" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo17'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">05.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo17" property="apNo17" tabindex="17" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo18'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo18" property="apNo18" tabindex="18" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo19'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo19" property="apNo19" tabindex="19" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo20'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo20" property="apNo20" tabindex="20" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo21'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">06.受理編號：</span> 
                        <html:text styleId="apNo21" property="apNo21" tabindex="21" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo22'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo22" property="apNo22" tabindex="22" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo23'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo23" property="apNo23" tabindex="23" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo24'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo24" property="apNo24" tabindex="24" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo25'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">07.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo25" property="apNo25" tabindex="25" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo26'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo26" property="apNo26" tabindex="26" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo27'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo27" property="apNo27" tabindex="27" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo28'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo28" property="apNo28" tabindex="28" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo29'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">08.受理編號：</span> 
                        <html:text styleId="apNo29" property="apNo29" tabindex="29" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo30'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo30" property="apNo30" tabindex="30" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo31'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo31" property="apNo31" tabindex="31" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo32'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo32" property="apNo32" tabindex="32" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo33'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">09.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo33" property="apNo33" tabindex="33" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo34'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo34" property="apNo34" tabindex="34" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo35'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo35" property="apNo35" tabindex="35" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo36'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo36" property="apNo36" tabindex="36" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo37'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">10.受理編號：</span> 
                        <html:text styleId="apNo37" property="apNo37" tabindex="37" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo38'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo38" property="apNo38" tabindex="38" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo39'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo39" property="apNo39" tabindex="39" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo40'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo40" property="apNo40" tabindex="40" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo41'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">11.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo41" property="apNo41" tabindex="41" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo42'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo42" property="apNo42" tabindex="42" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo43'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo43" property="apNo43" tabindex="43" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo44'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo44" property="apNo44" tabindex="44" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo45'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">12.受理編號：</span> 
                        <html:text styleId="apNo45" property="apNo45" tabindex="45" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo46'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo46" property="apNo46" tabindex="46" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo47'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo47" property="apNo47" tabindex="47" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo48'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo48" property="apNo48" tabindex="48" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo49'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">13.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo49" property="apNo49" tabindex="49" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo50'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo50" property="apNo50" tabindex="50" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo51'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo51" property="apNo51" tabindex="51" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo52'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo52" property="apNo52" tabindex="52" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo53'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">14.受理編號：</span> 
                        <html:text styleId="apNo53" property="apNo53" tabindex="53" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo54'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo54" property="apNo54" tabindex="54" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo55'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo55" property="apNo55" tabindex="55" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo56'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo56" property="apNo56" tabindex="56" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo57'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">15.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo57" property="apNo57" tabindex="57" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo58'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo58" property="apNo58" tabindex="58" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo59'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo59" property="apNo59" tabindex="59" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo60'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo60" property="apNo60" tabindex="60" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo61'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">16.受理編號：</span> 
                        <html:text styleId="apNo61" property="apNo61" tabindex="61" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo62'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo62" property="apNo62" tabindex="62" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo63'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo63" property="apNo63" tabindex="63" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo64'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo64" property="apNo64" tabindex="64" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo65'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">17.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo65" property="apNo65" tabindex="65" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo66'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo66" property="apNo66" tabindex="66" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo67'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo67" property="apNo67" tabindex="67" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo68'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo68" property="apNo68" tabindex="68" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo69'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">18.受理編號：</span> 
                        <html:text styleId="apNo69" property="apNo69" tabindex="69" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo70'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo70" property="apNo70" tabindex="70" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo71'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo71" property="apNo71" tabindex="71" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo72'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo72" property="apNo72" tabindex="72" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo73'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td width="25%" align="right"> 
                        <span class="issuetitle_R_down">19.受理編號：</span> 
                    </td> 
                    <td> 
                        <html:text styleId="apNo73" property="apNo73" tabindex="73" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo74'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo74" property="apNo74" tabindex="74" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo75'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo75" property="apNo75" tabindex="75" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo76'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo76" property="apNo76" tabindex="76" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo77'));" /> 
                        &emsp;&emsp; 
                        <span class="issuetitle_R_down">20.受理編號：</span> 
                        <html:text styleId="apNo77" property="apNo77" tabindex="77" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value).toUpperCase();" onkeyup="this.value = this.value.toUpperCase();autotab(this, $('apNo78'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo78" property="apNo78" tabindex="78" size="1" maxlength="1" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo79'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo79" property="apNo79" tabindex="79" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('apNo80'));" /> 
                        &nbsp;-&nbsp; 
                        <html:text styleId="apNo80" property="apNo80" tabindex="80" size="5" maxlength="5" styleClass="textinput" onblur="this.value = asc(this.value);" onkeyup="autotab(this, $('btnPrint'));" /> 
                    </td> 
                </tr> 
                <tr> 
                    <td colspan="2">&nbsp;</td> 
                </tr> 
                <tr> 
                    <td colspan="2"> 
                        <hr size="1" noshade> 
                        <span class="titleinput">※注意事項：</span> 
                        <br>&nbsp; 
                        <span class="needtxt">＊</span>為必填欄位。 
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
