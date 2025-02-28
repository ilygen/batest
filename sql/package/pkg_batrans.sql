CREATE OR REPLACE PACKAGE ba.pkg_batrans AUTHID DEFINER IS

  -- PURPOSE : 案件受理轉出, 提供BB、BC、BE,讀取BA失能及死亡受理案件資料

  TYPE typ_k_evt_rec IS RECORD(
    apno        baappbase.apno%TYPE, --受理編號
    apubno      baappbase.apubno%TYPE, --申請單位保險證號
    appdate     baappbase.appdate%TYPE, --申請日期
    evtsex      baappbase.evtsex%TYPE, --性別
    evtname     baappbase.evtname%TYPE, --事故者姓名
    evtidnno    baappbase.evtidnno%TYPE, --事故者身分證號
    evtbrdate   baappbase.evtbrdate%TYPE, --事故者出生日期
    tel1        baappbase.tel1%TYPE, --電話1
    tel2        baappbase.tel2%TYPE, --電話2
    commaddr    baappbase.commaddr%TYPE, --地址
    grdname     baappbase.grdname%TYPE, --法定代理人姓名
    grdidnno    baappbase.grdidnno%TYPE, --法定代理人身分證號
    grdbrdate   baappbase.grdbrdate%TYPE, --法定代理人出生日期
    evapptyp    baappexpand.evapptyp%TYPE, --申請傷病分類
    evtyp       baappexpand.evtyp%TYPE, --核定傷病分類
    evtjobdate  baappbase.evtjobdate%TYPE, --診斷失能日期
    criinjdp1   baappexpand.criinjdp1%TYPE, --失能項目1
    criinjdp2   baappexpand.criinjdp2%TYPE, --失能項目2
    criinjdp3   baappexpand.criinjdp3%TYPE, --失能項目3
    criinjdp4   baappexpand.criinjdp4%TYPE, --失能項目4
    criinjdp5   baappexpand.criinjdp5%TYPE, --失能項目5
    criinjdp6   baappexpand.criinjdp6%TYPE, --失能項目6
    criinjdp7   baappexpand.criinjdp7%TYPE, --失能項目7
    criinjdp8   baappexpand.criinjdp8%TYPE, --失能項目8
    criinjdp9   baappexpand.criinjdp9%TYPE, --失能項目9
    criinjdp10  baappexpand.criinjdp10%TYPE, --失能項目10
    hosid       baappexpand.hosid%TYPE, --醫療院所代碼
    doctorname1 baappexpand.doctorname1%TYPE, --醫師姓名1
    doctorname2 baappexpand.doctorname2%TYPE, --醫師姓名2
    criinjnme1  baappexpand.criinjnme1%TYPE, --國際疾病代碼1
    criinjnme2  baappexpand.criinjnme2%TYPE, --國際疾病代碼2
    criinjnme3  baappexpand.criinjnme3%TYPE, --國際疾病代碼3
    criinjnme4  baappexpand.criinjnme4%TYPE, --國際疾病代碼4
    sickdate    baappexpand.injdte%TYPE, --傷病發生日期 (重構才加)
    diagdate    baappexpand.fvisitdte%TYPE, --初診日期 (重構才加)
    surgerday   baappexpand.lsurgerydte%TYPE, --最後手術日期 (重構才加)
    treatdate   baappexpand.lsradiationdte%TYPE, --最後放射(化學)治療日期 (重構才加)
    paytyp      baappbase.paytyp%TYPE, --給付方式
    payeeacc    baappbase.payeeacc%TYPE, --帳號
    evtdiedate  baappbase.evtdiedate%TYPE, --事故者死亡日期
    lsubno      baappbase.lsubno%TYPE --最後單位保險證號
    );

  TYPE typ_k_evt_tab IS TABLE OF typ_k_evt_rec;

  --讀取BA失能受理案件資料
  FUNCTION fn_get_kevt(p_apno VARCHAR2) RETURN typ_k_evt_tab
    PIPELINED;

  TYPE typ_s_evt_rec IS RECORD(
    apno             baappbase.apno%TYPE, --受理編號
    evtnationtpe     baappbase.evtnationtpe%TYPE, --國籍別
    evtnationcode    baappbase.evtnationcode%TYPE, --國籍
    evtsex           baappbase.evtsex%TYPE, --性別
    evtidnno         baappbase.evtidnno%TYPE, --事故者身分證號
    evtname          baappbase.evtname%TYPE, --事故者姓名
    evtbrdate        baappbase.evtbrdate%TYPE, --事故者出生日期
    evtdiedate       baappbase.evtdiedate%TYPE, --死亡日期
    judgedate        baappexpand.judgedate%TYPE, --判決日期
    appdate          baappbase.appdate%TYPE, --申請日期
    apubno           baappbase.apubno%TYPE, --申請單位保險證號
    apitem           baappbase.apitem%TYPE, --申請項目
    evapptyp         baappexpand.evapptyp%TYPE, --申請傷病分類
    evtyp            baappexpand.evtyp%TYPE, --核定傷病分類
    monnotifyingmk   baappexpand.monnotifyingmk%TYPE, --寄發月通知表
    evtdate          baappbase.evtdate%TYPE, --退保日期
    evtjobdate       baappbase.evtjobdate%TYPE, --事故日期
    evtmissingdate   baappbase.evtmissingdate%TYPE, --事故者失蹤日期
    evtage           baappbase.evtage%TYPE, --事故者申請時年齡
    crtuser          baappbase.crtuser%TYPE, --新增者代號
    evcode           baappexpand.evcode%TYPE, --傷病原因
    criinjnme1       baappexpand.criinjnme1%TYPE, --國際疾病代碼1
    criinjnme2       baappexpand.criinjnme2%TYPE, --國際疾病代碼2
    criinjnme3       baappexpand.criinjnme3%TYPE, --國際疾病代碼3
    criinjnme4       baappexpand.criinjnme4%TYPE, --國際疾病代碼4
    criinpart1       baappexpand.criinpart1%TYPE, --受傷部位1
    criinpart2       baappexpand.criinpart2%TYPE, --受傷部位2
    criinpart3       baappexpand.criinpart3%TYPE, --受傷部位3
    crimedium        baappexpand.crimedium%TYPE, --媒介物
    ocacchosid       baappexpand.ocacchosid%TYPE, --職病醫療院所代碼
    hosid            baappexpand.hosid%TYPE, --醫療院所代碼
    ocaccdoctorname1 baappexpand.ocaccdoctorname1%TYPE, --職病醫師姓名1
    ocaccdoctorname2 baappexpand.ocaccdoctorname2%TYPE, --職病醫師姓名2
    doctorname1      baappexpand.doctorname1%TYPE, --醫師姓名1
    doctorname2      baappexpand.doctorname2%TYPE --醫師姓名2
    );

  TYPE typ_s_evt_tab IS TABLE OF typ_s_evt_rec;

  --讀取BA死亡受理案件-事故者資料
  FUNCTION fn_get_sevt(p_apno IN VARCHAR2) RETURN typ_s_evt_tab
    PIPELINED;

  TYPE typ_s_ben_rec IS RECORD(
    apno           baappbase.apno%TYPE, --受理編號
    benevtrel      baappbase.benevtrel%TYPE, --關係
    benname        baappbase.benname%TYPE, --遺屬姓名
    benidnno       baappbase.benidnno%TYPE, --遺屬身分證號
    benbrdate      baappbase.benbrdate%TYPE, --遺屬出生日期
    appdate        baappbase.appdate%TYPE, --遺屬申請日期
    bennationtyp   baappbase.bennationtyp%TYPE, --國籍別
    marrydate      baappexpand.marrydate%TYPE, --結婚日期
    monincomemk    baappexpand.monincomemk%TYPE, --每月工作收入註記
    monincome      baappexpand.monincome%TYPE, --每月工作收入
    studmk         baappexpand.studmk%TYPE, --在學註記
    schoolcode     baappexpand.schoolcode%TYPE, --學校代碼
    adoptdate      baappexpand.adoptdate%TYPE, --收養日期
    raiseevtmk     baappexpand.raiseevtmk%TYPE, --被保險人扶養
    paytyp         baappbase.paytyp%TYPE, --給付方式
    specialacc     baappbase.specialacc%TYPE, --專戶註記
    payeeacc       baappbase.payeeacc%TYPE, --帳號
    bankname       baappbase.bankname%TYPE, --金融機構名稱
    accname        baappbase.accname%TYPE, --戶名
    tel1           baappbase.tel1%TYPE, --電話1
    tel2           baappbase.tel2%TYPE, --電話2
    commaddr       baappbase.commaddr%TYPE, --地址
    benmarrmk      baappbase.benmarrmk%TYPE, --婚姻狀況
    savingmk       baappexpand.savingmk%TYPE, --計息存儲
    grdname        baappbase.grdname%TYPE, --法定代理人姓名
    grdidnno       baappbase.grdidnno%TYPE, --法定代理人身分證號
    grdbrdate      baappbase.grdbrdate%TYPE, --法定代理人出生日期
    assignname     baappbase.assignname%TYPE, --代辦人姓名
    assignidnno    baappbase.assignidnno%TYPE, --代辦人身分證號
    assignbrdate   baappbase.assignbrdate%TYPE, --代辦人出生日期
    commtyp        baappbase.commtyp%TYPE, --通訊地址別
    commzip        baappbase.commzip%TYPE, --通訊郵遞區號
    paybankid      baappbase.paybankid%TYPE, --金融機構總代號
    branchid       baappbase.branchid%TYPE, --分支代號
    bankaddr       baappbase.bankaddr%TYPE, --金融機構地址
    banknationcode baappbase.banknationcode%TYPE, --金融機構國籍
    accseqno       baappbase.accseqno%TYPE, --被共同具領之受款人員序號
    bensex         baappbase.bensex%TYPE, --受益人性別
    mobilephone    baappbase.mobilephone%TYPE, --手機複驗
    bendiedate     baappbase.bendiedate%TYPE, --受益人死亡日期
    seqno          baappbase.seqno%TYPE, --序號
    accrel         baappbase.accrel%TYPE --戶名與受益人關係
    );

  TYPE typ_s_ben_tab IS TABLE OF typ_s_ben_rec;

  --讀取BA死亡受理案件-遺屬資料
  FUNCTION fn_get_sben(p_apno IN VARCHAR2) RETURN typ_s_ben_tab
    PIPELINED;

END pkg_batrans;
/

CREATE OR REPLACE PACKAGE BODY ba.pkg_batrans IS

  --讀取ba失能受理案件資料
  FUNCTION fn_get_kevt(p_apno IN VARCHAR2) RETURN typ_k_evt_tab
    PIPELINED IS
  BEGIN
    FOR rec_k IN (SELECT b.apno,
                         b.apubno, --申請單位保險證號
                         b.appdate, --申請日期
                         b.evtsex, --性別
                         b.evtname, --事故者姓名
                         b.evtidnno, --事故者身分證號
                         b.evtbrdate, --事故者出生日期
                         b.tel1, --電話1
                         b.tel2, --電話2
                         b.commaddr, --地址
                         b.grdname, --法定代理人姓名
                         b.grdidnno, --法定代理人身分證號
                         b.grdbrdate, --法定代理人出生日期
                         e.evapptyp, --申請傷病分類
                         e.evtyp, --核定傷病分類
                         b.evtjobdate, --診斷失能日期
                         e.criinjdp1, --失能項目1
                         e.criinjdp2, --失能項目2
                         e.criinjdp3, --失能項目3
                         e.criinjdp4, --失能項目4
                         e.criinjdp5, --失能項目5
                         e.criinjdp6, --失能項目6
                         e.criinjdp7, --失能項目7
                         e.criinjdp8, --失能項目8
                         e.criinjdp9, --失能項目9
                         e.criinjdp10, --失能項目10
                         e.hosid, --醫療院所代碼
                         e.doctorname1, --醫師姓名1
                         e.doctorname2, --醫師姓名2
                         e.criinjnme1, --國際疾病代碼1
                         e.criinjnme2, --國際疾病代碼2
                         e.criinjnme3, --國際疾病代碼3
                         e.criinjnme4, --國際疾病代碼4
                         e.injdte AS sickdate, --傷病發生日期 (重構才加)
                         e.fvisitdte AS diagdate, --初診日期 (重構才加)
                         e.lsurgerydte AS surgerday, --最後手術日期 (重構才加)
                         e.lsradiationdte AS treatdate, --最後放射(化學)治療日期 (重構才加)
                         b.paytyp, --給付方式
                         b.payeeacc, --帳號
                         b.evtdiedate, --事故者死亡日期
                         b.lsubno --最後單位保險證號
                    FROM baappbase b, baappexpand e
                   WHERE b.baappbaseid = e.baappbaseid(+)
                     AND b.apno = p_apno
                     AND b.seqno = '0000'
                     AND p_apno LIKE 'K%')
    LOOP
      PIPE ROW(rec_k);
    END LOOP;
  END;

  --讀取ba死亡受理案件-事故者資料
  FUNCTION fn_get_sevt(p_apno IN VARCHAR2) RETURN typ_s_evt_tab
    PIPELINED IS
  BEGIN
    FOR rec_s IN (SELECT b.apno, --受理編號
                         b.evtnationtpe, --國籍別
                         b.evtnationcode, --國籍
                         b.evtsex, --性別
                         b.evtidnno, --事故者身分證號
                         b.evtname, --事故者姓名
                         b.evtbrdate, --事故者出生日期
                         b.evtdiedate, --死亡日期
                         e.judgedate, --判決日期
                         b.appdate, --申請日期
                         b.apubno, --申請單位保險證號
                         b.apitem, --申請項目
                         e.evapptyp, --申請傷病分類
                         e.evtyp, --核定傷病分類
                         e.monnotifyingmk, --寄發月通知表
                         b.evtdate, --退保日期
                         b.evtjobdate, --事故日期
                         b.evtmissingdate, --事故者失蹤日期
                         b.evtage, --事故者申請時年齡
                         b.crtuser, --新增者代號
                         e.evcode, --傷病原因
                         e.criinjnme1, --國際疾病代碼1
                         e.criinjnme2, --國際疾病代碼2
                         e.criinjnme3, --國際疾病代碼3
                         e.criinjnme4, --國際疾病代碼4
                         e.criinpart1, --受傷部位1
                         e.criinpart2, --受傷部位2
                         e.criinpart3, --受傷部位3
                         e.crimedium, --媒介物
                         e.ocacchosid, --職病醫療院所代碼
                         e.hosid, --醫療院所代碼
                         e.ocaccdoctorname1, --職病醫師姓名1
                         e.ocaccdoctorname2, --職病醫師姓名2
                         e.doctorname1, --醫師姓名1
                         e.doctorname2  --醫師姓名2
                    FROM baappbase b, baappexpand e
                   WHERE b.baappbaseid = e.baappbaseid(+)
                     AND b.apno = p_apno
                     AND b.seqno = '0000'
                     AND p_apno LIKE 'S%')
    LOOP
      PIPE ROW(rec_s);
    END LOOP;
  END;

  --讀取ba死亡受理案件-遺屬資料
  FUNCTION fn_get_sben(p_apno IN VARCHAR2) RETURN typ_s_ben_tab
    PIPELINED IS
  BEGIN
    FOR rec_s IN (SELECT b.apno,
                         b.benevtrel, --關係
                         b.benname, --遺屬姓名
                         b.benidnno, --遺屬身分證號
                         b.benbrdate, --遺屬出生日期
                         b.appdate, --遺屬申請日期
                         b.bennationtyp, --國籍別
                         e.marrydate, --結婚日期
                         e.monincomemk, --每月工作收入註記
                         e.monincome, --每月工作收入
                         e.studmk, --在學註記
                         e.schoolcode, --學校代碼
                         e.adoptdate, --收養日期
                         e.raiseevtmk, --被保險人扶養
                         b.paytyp, --給付方式
                         b.specialacc, --專戶註記
                         b.payeeacc, --帳號
                         b.bankname, --金融機構名稱
                         b.accname, --戶名
                         b.tel1, --電話1
                         b.tel2, --電話2
                         b.commaddr, --地址
                         b.benmarrmk, --婚姻狀況
                         e.savingmk, --計息存儲
                         b.grdname, --法定代理人姓名
                         b.grdidnno, --法定代理人身分證號
                         b.grdbrdate, --法定代理人出生日期
                         e.assignname, --代辦人姓名
                         e.assignidnno, --代辦人身分證號
                         e.assignbrdate, --代辦人出生日期
                         b.commtyp, --通訊地址別
                         b.commzip, --通訊郵遞區號
                         b.paybankid, --金融機構總代號
                         b.branchid, --分支代號
                         b.bankaddr, --金融機構地址
                         b.banknationcode, --金融機構國籍
                         b.accseqno, --被共同具領之受款人員序號
                         b.bensex, --受益人性別
                         b.mobilephone, --手機複驗
                         b.bendiedate, --受益人死亡日期
                         b.seqno, --序號
                         b.accrel --戶名與受益人關係
                    FROM baappbase b, baappexpand e
                   WHERE b.baappbaseid = e.baappbaseid(+)
                     AND b.apno = p_apno
                     AND b.seqno <> '0000'
                     AND p_apno LIKE 'S%')
    LOOP
      PIPE ROW(rec_s);
    END LOOP;
  END;

END pkg_batrans;
/
