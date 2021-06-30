package tw.gov.bli.ba.rpt.forms;

import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import tw.gov.bli.ba.framework.struts.forms.BaseValidatorForm;

/**
 * 列印作業 - 審核作業相關報表 - 勞保老年年金給付受理編審清單 - 查詢頁面 (balp0d010l.jsp)
 * 
 * @author Goston
 */
public class OldAgeReviewRpt01Form extends BaseValidatorForm {
	private String method;

	// 起
	private String apNo1Begin; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo2Begin; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo3Begin; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo4Begin; // 受理編號 - 第四欄 ( 5碼 )

	// 迄
	private String apNo1End; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo2End; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo3End; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo4End; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第1筆
	private String apNo1; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo2; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo3; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo4; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第2筆
	private String apNo5; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo6; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo7; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo8; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第3筆
	private String apNo9; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo10; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo11; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo12; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第4筆
	private String apNo13; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo14; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo15; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo16; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第5筆
	private String apNo17; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo18; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo19; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo20; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第6筆
	private String apNo21; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo22; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo23; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo24; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第7筆
	private String apNo25; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo26; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo27; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo28; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第8筆
	private String apNo29; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo30; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo31; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo32; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第9筆
	private String apNo33; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo34; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo35; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo36; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第10筆
	private String apNo37; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo38; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo39; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo40; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第11筆
	private String apNo41; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo42; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo43; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo44; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第12筆
	private String apNo45; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo46; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo47; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo48; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第13筆
	private String apNo49; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo50; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo51; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo52; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第14筆
	private String apNo53; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo54; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo55; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo56; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第15筆
	private String apNo57; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo58; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo59; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo60; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第16筆
	private String apNo61; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo62; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo63; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo64; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第17筆
	private String apNo65; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo66; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo67; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo68; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第18筆
	private String apNo69; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo70; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo71; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo72; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第19筆
	private String apNo73; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo74; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo75; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo76; // 受理編號 - 第四欄 ( 5碼 )

	// 輸入多筆第20筆
	private String apNo77; // 受理編號 - 第一欄 ( 1碼 )
	private String apNo78; // 受理編號 - 第二欄 ( 1碼 )
	private String apNo79; // 受理編號 - 第三欄 ( 5碼 )
	private String apNo80; // 受理編號 - 第四欄 ( 5碼 )

	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		ActionErrors errors = super.validate(mapping, request);

		// Validation.xml 驗證若有錯誤則先讓使用者改正所輸入之資料
		if (errors != null && errors.size() > 0)
			return errors;

		return errors;
	}

	public void reset(ActionMapping mapping, HttpServletRequest request) {
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getApNo1Begin() {
		return apNo1Begin;
	}

	public void setApNo1Begin(String apNo1Begin) {
		this.apNo1Begin = apNo1Begin;
	}

	public String getApNo2Begin() {
		return apNo2Begin;
	}

	public void setApNo2Begin(String apNo2Begin) {
		this.apNo2Begin = apNo2Begin;
	}

	public String getApNo3Begin() {
		return apNo3Begin;
	}

	public void setApNo3Begin(String apNo3Begin) {
		this.apNo3Begin = apNo3Begin;
	}

	public String getApNo4Begin() {
		return apNo4Begin;
	}

	public void setApNo4Begin(String apNo4Begin) {
		this.apNo4Begin = apNo4Begin;
	}

	public String getApNo1End() {
		return apNo1End;
	}

	public void setApNo1End(String apNo1End) {
		this.apNo1End = apNo1End;
	}

	public String getApNo2End() {
		return apNo2End;
	}

	public void setApNo2End(String apNo2End) {
		this.apNo2End = apNo2End;
	}

	public String getApNo3End() {
		return apNo3End;
	}

	public void setApNo3End(String apNo3End) {
		this.apNo3End = apNo3End;
	}

	public String getApNo4End() {
		return apNo4End;
	}

	public void setApNo4End(String apNo4End) {
		this.apNo4End = apNo4End;
	}

	public String getApNo1() {
		return apNo1;
	}

	public void setApNo1(String apNo1) {
		this.apNo1 = apNo1;
	}

	public String getApNo2() {
		return apNo2;
	}

	public void setApNo2(String apNo2) {
		this.apNo2 = apNo2;
	}

	public String getApNo3() {
		return apNo3;
	}

	public void setApNo3(String apNo3) {
		this.apNo3 = apNo3;
	}

	public String getApNo4() {
		return apNo4;
	}

	public void setApNo4(String apNo4) {
		this.apNo4 = apNo4;
	}

	public String getApNo5() {
		return apNo5;
	}

	public void setApNo5(String apNo5) {
		this.apNo5 = apNo5;
	}

	public String getApNo6() {
		return apNo6;
	}

	public void setApNo6(String apNo6) {
		this.apNo6 = apNo6;
	}

	public String getApNo7() {
		return apNo7;
	}

	public void setApNo7(String apNo7) {
		this.apNo7 = apNo7;
	}

	public String getApNo8() {
		return apNo8;
	}

	public void setApNo8(String apNo8) {
		this.apNo8 = apNo8;
	}

	public String getApNo9() {
		return apNo9;
	}

	public void setApNo9(String apNo9) {
		this.apNo9 = apNo9;
	}

	public String getApNo10() {
		return apNo10;
	}

	public void setApNo10(String apNo10) {
		this.apNo10 = apNo10;
	}

	public String getApNo11() {
		return apNo11;
	}

	public void setApNo11(String apNo11) {
		this.apNo11 = apNo11;
	}

	public String getApNo12() {
		return apNo12;
	}

	public void setApNo12(String apNo12) {
		this.apNo12 = apNo12;
	}

	public String getApNo13() {
		return apNo13;
	}

	public void setApNo13(String apNo13) {
		this.apNo13 = apNo13;
	}

	public String getApNo14() {
		return apNo14;
	}

	public void setApNo14(String apNo14) {
		this.apNo14 = apNo14;
	}

	public String getApNo15() {
		return apNo15;
	}

	public void setApNo15(String apNo15) {
		this.apNo15 = apNo15;
	}

	public String getApNo16() {
		return apNo16;
	}

	public void setApNo16(String apNo16) {
		this.apNo16 = apNo16;
	}

	public String getApNo17() {
		return apNo17;
	}

	public void setApNo17(String apNo17) {
		this.apNo17 = apNo17;
	}

	public String getApNo18() {
		return apNo18;
	}

	public void setApNo18(String apNo18) {
		this.apNo18 = apNo18;
	}

	public String getApNo19() {
		return apNo19;
	}

	public void setApNo19(String apNo19) {
		this.apNo19 = apNo19;
	}

	public String getApNo20() {
		return apNo20;
	}

	public void setApNo20(String apNo20) {
		this.apNo20 = apNo20;
	}

	public String getApNo21() {
		return apNo21;
	}

	public void setApNo21(String apNo21) {
		this.apNo21 = apNo21;
	}

	public String getApNo22() {
		return apNo22;
	}

	public void setApNo22(String apNo22) {
		this.apNo22 = apNo22;
	}

	public String getApNo23() {
		return apNo23;
	}

	public void setApNo23(String apNo23) {
		this.apNo23 = apNo23;
	}

	public String getApNo24() {
		return apNo24;
	}

	public void setApNo24(String apNo24) {
		this.apNo24 = apNo24;
	}

	public String getApNo25() {
		return apNo25;
	}

	public void setApNo25(String apNo25) {
		this.apNo25 = apNo25;
	}

	public String getApNo26() {
		return apNo26;
	}

	public void setApNo26(String apNo26) {
		this.apNo26 = apNo26;
	}

	public String getApNo27() {
		return apNo27;
	}

	public void setApNo27(String apNo27) {
		this.apNo27 = apNo27;
	}

	public String getApNo28() {
		return apNo28;
	}

	public void setApNo28(String apNo28) {
		this.apNo28 = apNo28;
	}

	public String getApNo29() {
		return apNo29;
	}

	public void setApNo29(String apNo29) {
		this.apNo29 = apNo29;
	}

	public String getApNo30() {
		return apNo30;
	}

	public void setApNo30(String apNo30) {
		this.apNo30 = apNo30;
	}

	public String getApNo31() {
		return apNo31;
	}

	public void setApNo31(String apNo31) {
		this.apNo31 = apNo31;
	}

	public String getApNo32() {
		return apNo32;
	}

	public void setApNo32(String apNo32) {
		this.apNo32 = apNo32;
	}

	public String getApNo33() {
		return apNo33;
	}

	public void setApNo33(String apNo33) {
		this.apNo33 = apNo33;
	}

	public String getApNo34() {
		return apNo34;
	}

	public void setApNo34(String apNo34) {
		this.apNo34 = apNo34;
	}

	public String getApNo35() {
		return apNo35;
	}

	public void setApNo35(String apNo35) {
		this.apNo35 = apNo35;
	}

	public String getApNo36() {
		return apNo36;
	}

	public void setApNo36(String apNo36) {
		this.apNo36 = apNo36;
	}

	public String getApNo37() {
		return apNo37;
	}

	public void setApNo37(String apNo37) {
		this.apNo37 = apNo37;
	}

	public String getApNo38() {
		return apNo38;
	}

	public void setApNo38(String apNo38) {
		this.apNo38 = apNo38;
	}

	public String getApNo39() {
		return apNo39;
	}

	public void setApNo39(String apNo39) {
		this.apNo39 = apNo39;
	}

	public String getApNo40() {
		return apNo40;
	}

	public void setApNo40(String apNo40) {
		this.apNo40 = apNo40;
	}

	public String getApNo41() {
		return apNo41;
	}

	public void setApNo41(String apNo41) {
		this.apNo41 = apNo41;
	}

	public String getApNo42() {
		return apNo42;
	}

	public void setApNo42(String apNo42) {
		this.apNo42 = apNo42;
	}

	public String getApNo43() {
		return apNo43;
	}

	public void setApNo43(String apNo43) {
		this.apNo43 = apNo43;
	}

	public String getApNo44() {
		return apNo44;
	}

	public void setApNo44(String apNo44) {
		this.apNo44 = apNo44;
	}

	public String getApNo45() {
		return apNo45;
	}

	public void setApNo45(String apNo45) {
		this.apNo45 = apNo45;
	}

	public String getApNo46() {
		return apNo46;
	}

	public void setApNo46(String apNo46) {
		this.apNo46 = apNo46;
	}

	public String getApNo47() {
		return apNo47;
	}

	public void setApNo47(String apNo47) {
		this.apNo47 = apNo47;
	}

	public String getApNo48() {
		return apNo48;
	}

	public void setApNo48(String apNo48) {
		this.apNo48 = apNo48;
	}

	public String getApNo49() {
		return apNo49;
	}

	public void setApNo49(String apNo49) {
		this.apNo49 = apNo49;
	}

	public String getApNo50() {
		return apNo50;
	}

	public void setApNo50(String apNo50) {
		this.apNo50 = apNo50;
	}

	public String getApNo51() {
		return apNo51;
	}

	public void setApNo51(String apNo51) {
		this.apNo51 = apNo51;
	}

	public String getApNo52() {
		return apNo52;
	}

	public void setApNo52(String apNo52) {
		this.apNo52 = apNo52;
	}

	public String getApNo53() {
		return apNo53;
	}

	public void setApNo53(String apNo53) {
		this.apNo53 = apNo53;
	}

	public String getApNo54() {
		return apNo54;
	}

	public void setApNo54(String apNo54) {
		this.apNo54 = apNo54;
	}

	public String getApNo55() {
		return apNo55;
	}

	public void setApNo55(String apNo55) {
		this.apNo55 = apNo55;
	}

	public String getApNo56() {
		return apNo56;
	}

	public void setApNo56(String apNo56) {
		this.apNo56 = apNo56;
	}

	public String getApNo57() {
		return apNo57;
	}

	public void setApNo57(String apNo57) {
		this.apNo57 = apNo57;
	}

	public String getApNo58() {
		return apNo58;
	}

	public void setApNo58(String apNo58) {
		this.apNo58 = apNo58;
	}

	public String getApNo59() {
		return apNo59;
	}

	public void setApNo59(String apNo59) {
		this.apNo59 = apNo59;
	}

	public String getApNo60() {
		return apNo60;
	}

	public void setApNo60(String apNo60) {
		this.apNo60 = apNo60;
	}

	public String getApNo61() {
		return apNo61;
	}

	public void setApNo61(String apNo61) {
		this.apNo61 = apNo61;
	}

	public String getApNo62() {
		return apNo62;
	}

	public void setApNo62(String apNo62) {
		this.apNo62 = apNo62;
	}

	public String getApNo63() {
		return apNo63;
	}

	public void setApNo63(String apNo63) {
		this.apNo63 = apNo63;
	}

	public String getApNo64() {
		return apNo64;
	}

	public void setApNo64(String apNo64) {
		this.apNo64 = apNo64;
	}

	public String getApNo65() {
		return apNo65;
	}

	public void setApNo65(String apNo65) {
		this.apNo65 = apNo65;
	}

	public String getApNo66() {
		return apNo66;
	}

	public void setApNo66(String apNo66) {
		this.apNo66 = apNo66;
	}

	public String getApNo67() {
		return apNo67;
	}

	public void setApNo67(String apNo67) {
		this.apNo67 = apNo67;
	}

	public String getApNo68() {
		return apNo68;
	}

	public void setApNo68(String apNo68) {
		this.apNo68 = apNo68;
	}

	public String getApNo69() {
		return apNo69;
	}

	public void setApNo69(String apNo69) {
		this.apNo69 = apNo69;
	}

	public String getApNo70() {
		return apNo70;
	}

	public void setApNo70(String apNo70) {
		this.apNo70 = apNo70;
	}

	public String getApNo71() {
		return apNo71;
	}

	public void setApNo71(String apNo71) {
		this.apNo71 = apNo71;
	}

	public String getApNo72() {
		return apNo72;
	}

	public void setApNo72(String apNo72) {
		this.apNo72 = apNo72;
	}

	public String getApNo73() {
		return apNo73;
	}

	public void setApNo73(String apNo73) {
		this.apNo73 = apNo73;
	}

	public String getApNo74() {
		return apNo74;
	}

	public void setApNo74(String apNo74) {
		this.apNo74 = apNo74;
	}

	public String getApNo75() {
		return apNo75;
	}

	public void setApNo75(String apNo75) {
		this.apNo75 = apNo75;
	}

	public String getApNo76() {
		return apNo76;
	}

	public void setApNo76(String apNo76) {
		this.apNo76 = apNo76;
	}

	public String getApNo77() {
		return apNo77;
	}

	public void setApNo77(String apNo77) {
		this.apNo77 = apNo77;
	}

	public String getApNo78() {
		return apNo78;
	}

	public void setApNo78(String apNo78) {
		this.apNo78 = apNo78;
	}

	public String getApNo79() {
		return apNo79;
	}

	public void setApNo79(String apNo79) {
		this.apNo79 = apNo79;
	}

	public String getApNo80() {
		return apNo80;
	}

	public void setApNo80(String apNo80) {
		this.apNo80 = apNo80;
	}

}
