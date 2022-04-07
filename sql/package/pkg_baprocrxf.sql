CREATE OR REPLACE PACKAGE ba.pkg_baprocrxf AUTHID DEFINER IS

  -- Purpose : 另案扣減及應收未收相關處理

  TYPE typ_baunacpdtl_rec IS RECORD(
    apno      baunacpdtl.apno%TYPE, --受理編號
    seqno     baunacpdtl.seqno%TYPE, --受款人序號
    issuym    baunacpdtl.issuym%TYPE, --核定年月
    payym     baunacpdtl.payym%TYPE, --給付年月
    benidnno  baunacpdtl.benidnno%TYPE, --受益人身分證號
    recamt    baunacpdtl.recamt%TYPE, --應收總金額
    recrem    baunacpdtl.recrem%TYPE, --未收總金額
    sts       baunacpdtl.sts%TYPE, --資料狀況
    paykind   baunacpdtl.paykind%TYPE, --給付種類
    benname   baappbase.benname%TYPE, --受款人姓名
    benbrdate baappbase.benbrdate%TYPE --受款人出生日期
    );

  rec_baunacpdtl typ_baunacpdtl_rec;

  TYPE typ_baunacpdtl_tab IS TABLE OF typ_baunacpdtl_rec;

  --查詢應收未收
  FUNCTION QRYARF(p_idn IN VARCHAR2) RETURN typ_baunacpdtl_tab
    PIPELINED;


  --更新同意另案扣減金額
  PROCEDURE AGREERXF(
                         p_apno1     IN VARCHAR2, --應收案受理編號
                         p_seqno1     IN VARCHAR2, --應收案受款人序
                         p_apno2     IN VARCHAR2, --撥付案受理編號
                         p_seqno2   IN VARCHAR2, --撥付案受款人序
                         p_rbamt     IN NUMBER, --同意扣減金額
                         p_prpno   IN VARCHAR2, --處理人員員編
                         p_rtncode  OUT VARCHAR2, --S:更新成功 N:無更新資料 E:error
                         p_rtnmsg   OUT VARCHAR2 -- 錯誤訊息
                         );

END pkg_baprocrxf;
/

CREATE OR REPLACE PACKAGE BODY ba.pkg_baprocrxf IS

  --查詢應收未收, 重構要加入繼承人相關案件資料
  FUNCTION QRYARF(p_idn IN VARCHAR2) RETURN typ_baunacpdtl_tab
    PIPELINED IS
  BEGIN
    FOR rec_baunacpdtl IN (SELECT b.apno, --受理編號
                                  b.seqno, --受款人序號
                                  b.issuym, --核定年月
                                  b.payym, --給付年月
                                  b.benidnno, --受益人身分證號
                                  b.recamt, --應收總金額
                                  b.recrem, --未收總金額
                                  b.sts, --資料狀況
                                  b.paykind, --給付種類
                                  p.benname, --受款人姓名
                                  p.benbrdate --受款人出生日期
                             FROM baunacpdtl b, baappbase p
                            WHERE b.recrem > 0
                              AND b.mdchkmk <> 'D'
                              AND b.benids = p_idn
                              --AND b.apno = p.apno
                              --AND b.seqno = p.seqno
                              )
    LOOP
      PIPE ROW(rec_baunacpdtl);
    END LOOP;
  END;

  --更新同意另案扣減金額
  PROCEDURE AGREERXF(
                         p_apno1    IN VARCHAR2, --應收案受理編號
                         p_seqno1  IN VARCHAR2, --應收案受款人序
                         p_apno2    IN VARCHAR2, --撥付案受理編號
                         p_seqno2  IN VARCHAR2, --撥付案受款人序
                         p_rbamt    IN NUMBER, --同意扣減金額
                         p_prpno    IN VARCHAR2,  --處理人員員編
                         p_rtncode     OUT VARCHAR2, --0:成功/-1:失敗
                         p_rtnmsg     OUT VARCHAR2
                         )
 IS
    v_rec_plog plog%ROWTYPE;
  BEGIN
    UPDATE barxf
       SET rbamt = p_rbamt,
           prst = (CASE
                    WHEN p_rbamt > 0 THEN
                     'Y'
                    ELSE
                     'N'
                  END),
           prdte = to_char(SYSDATE, 'yyyymmdd'),
           prpno = p_prpno
     WHERE rxfapno = p_apno1 --p_rxfapno
       AND apno = p_apno2
       AND seqno = p_seqno2;
    IF SQL%ROWCOUNT = 0 THEN
      p_rtnmsg := '0';
    ELSE
      p_rtnmsg := '-1';
      v_rec_plog.userid    := p_prpno;
      v_rec_plog.jobid     := to_char(SYSDATE, 'YYYYMMDDHH24MISSSSS');
      v_rec_plog.starttime := SYSDATE;
      v_rec_plog.levelmk   := '1'; --INFO
      v_rec_plog.pseq      := '1';
      v_rec_plog.procname  := 'pkg_baprocrxf.AGREERXF';
      v_rec_plog.msg1      := '更新同意另案扣減金額';
      v_rec_plog.msg2      := '受理編號-應收='||p_apno1||
                              ',年金受理編號-撥付='||p_apno2||
                              ',年金受款人序號-撥付='||p_seqno2||
                              ',同意扣減金額='||p_rbamt;
      pkg_plog.sp_ins_log(v_rec_plog);
    END IF;
    COMMIT;

  EXCEPTION
    WHEN OTHERS THEN
      v_rec_plog.userid    := 'BATCH';
      v_rec_plog.jobid     := to_char(SYSDATE, 'YYYYMMDDHH24MISSSSS');
      v_rec_plog.starttime := SYSDATE;
      v_rec_plog.levelmk   := '3'; --ERROR
      v_rec_plog.pseq      := '1';
      v_rec_plog.procname  := 'pkg_baprocrxf.sp_set_rbamt';
      v_rec_plog.msg1      := SQLCODE || SQLERRM;
      v_rec_plog.msg2      := dbms_utility.format_error_backtrace;
      pkg_plog.sp_ins_log(v_rec_plog);
      p_rtnmsg := 'E';
  END;

END pkg_baprocrxf;
/
