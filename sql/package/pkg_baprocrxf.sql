CREATE OR REPLACE PACKAGE BA.PKG_BAPROCRXF AUTHID DEFINER IS

    -- Purpose : 另案扣減及應收未收相關處理
    TYPE typ_baunacpdtl_rec IS RECORD(
        APNO      BAUNACPDTL.APNO%TYPE,     --受理編號
        SEQNO     BAUNACPDTL.SEQNO%TYPE,    --受款人序號
        ISSUYM    BAUNACPDTL.ISSUYM%TYPE,   --核定年月
        PAYYM     BAUNACPDTL.PAYYM%TYPE,    --給付年月
        BENIDNNO  BAUNACPDTL.BENIDNNO%TYPE, --受益人身分證號
        RECAMT    BAUNACPDTL.RECAMT%TYPE,   --應收總金額
        RECREM    BAUNACPDTL.RECREM%TYPE,   --未收總金額
        STS       BAUNACPDTL.STS%TYPE,      --資料狀況
        PAYKIND   BAUNACPDTL.PAYKIND%TYPE,  --給付種類
        BENNAME   BAAPPBASE.BENNAME%TYPE,   --受款人姓名
        BENBRDATE BAAPPBASE.BENBRDATE%TYPE  --受款人出生日期
    );
    rec_baunacpdtl typ_baunacpdtl_rec;
    TYPE typ_baunacpdtl_tab IS TABLE OF typ_baunacpdtl_rec;

    --查詢應收未收
    FUNCTION QRYARF(p_idn IN VARCHAR2) 
    RETURN typ_baunacpdtl_tab PIPELINED;

    --更新同意另案扣減金額
    PROCEDURE AGREERXF(
        p_apno1   IN VARCHAR2,  -- 應收案受理編號
        p_seqno1  IN VARCHAR2,  -- 應收案受款人序
        p_apno2   IN VARCHAR2,  -- 撥付案受理編號
        p_seqno2  IN VARCHAR2,  -- 撥付案受款人序
        p_rbamt   IN NUMBER,    -- 同意扣減金額(同意時金額>0；不同意時金額=0；註銷時金額應為空值)
        p_prpno   IN VARCHAR2,  -- 處理人員員編
        p_rtncode OUT VARCHAR2, -- 處理結果(0->成功、-1->異常)
        p_rtnmsg  OUT VARCHAR2  -- 錯誤訊息
    );
    
    --更新同意另案扣減金額
    PROCEDURE AGREERXF(
        p_apno1   IN VARCHAR2,  -- 應收案受理編號
        p_seqno1  IN VARCHAR2,  -- 應收案受款人序
        p_apno2   IN VARCHAR2,  -- 撥付案受理編號
        p_seqno2  IN VARCHAR2,  -- 撥付案受款人序
        p_idnno   IN VARCHAR2,  -- 應收案收款人身分證號
        p_rbamt   IN NUMBER,    -- 同意扣減金額(同意時金額>0；不同意時金額=0；註銷時金額應為空值)
        p_prpno   IN VARCHAR2,  -- 處理人員員編
        p_rtncode OUT VARCHAR2, -- 處理結果(0->成功、-1->異常)
        p_rtnmsg  OUT VARCHAR2  -- 錯誤訊息
    );
END PKG_BAPROCRXF;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_BAPROCRXF IS
    --查詢應收未收, 重構要加入繼承人相關案件資料
    FUNCTION QRYARF(p_idn IN VARCHAR2) 
    RETURN typ_baunacpdtl_tab PIPELINED IS
    BEGIN
        FOR rec_baunacpdtl IN (
            SELECT 
                b.apno,     b.seqno,   b.issuym,   b.payym,  -- 受理編號, 受款人序號, 核定年月, 給付年月
                b.benidnno, b.recamt,  b.recrem,   b.sts,    -- 受益人身分證號, 應收總金額, 未收總金額, 資料狀況
                b.paykind,  p.benname, p.benbrdate           -- 給付種類, 受款人姓名, 受款人出生日期
            FROM baunacpdtl b, baappbase p
            WHERE b.recrem > 0
            AND b.mdchkmk <> 'D'
            AND b.benidnno = p_idn
            AND b.apno = p.apno
            AND b.seqno = p.seqno
        )LOOP
          PIPE ROW(rec_baunacpdtl);
        END LOOP;
    END;

    --更新同意另案扣減金額
    PROCEDURE AGREERXF(
        p_apno1   IN VARCHAR2,  -- 應收案受理編號
        p_seqno1  IN VARCHAR2,  -- 應收案受款人序
        p_apno2   IN VARCHAR2,  -- 撥付案受理編號
        p_seqno2  IN VARCHAR2,  -- 撥付案受款人序
        p_rbamt   IN NUMBER,    -- 同意扣減金額(同意時金額>0；不同意時金額=0；註銷時金額應為空值)
        p_prpno   IN VARCHAR2,  -- 處理人員員編
        p_rtncode OUT VARCHAR2, -- 處理結果(0->成功、-1->異常)
        p_rtnmsg  OUT VARCHAR2  -- 錯誤訊息
    ) IS
        v_rec_plog plog%ROWTYPE;
        v_prst     VARCHAR2(1) := '';
    BEGIN
        p_rtncode := '0';   -- 預設成功
        IF (TRIM(p_apno1) IS NULL OR TRIM(p_apno2) IS NULL OR TRIM(p_seqno2) IS NULL) THEN
            p_rtncode := '-1';
            p_rtnmsg := '應收案/撥付案的受理編號與受款人序不可為空';
            RETURN;
        END IF;
        
        IF (NVL(TRIM(p_rbamt), 0) < 0) THEN
            p_rtncode := '-1';
            p_rtnmsg := '扣減金額不可為負值';
            RETURN;
        END IF;
        
        IF (TRIM(p_rbamt) IS NULL) THEN -- 金額為空表註銷prst上D
            v_prst := 'D';
            p_rtnmsg := '註銷另案扣減收回成功';
        ELSIF (p_rbamt > 0) THEN        -- 金額大於0表同意prst上Y
            v_prst := 'Y';
            p_rtnmsg := '同意扣減成功';
        ELSIF (p_rbamt = 0) THEN        -- 金額為0表不同意prst上N
            v_prst := 'N';
            p_rtnmsg := '不同意扣減成功';
        END IF;
        
        IF (TRIM(p_prpno) IS NULL) THEN
            p_rtncode := '-1';
            p_rtnmsg := '處理人員員編不得為空';
            RETURN;
        END IF;
        
        UPDATE BARXF
        SET RBAMT = NVL(TRIM(p_rbamt), 0),
            PRST = v_prst,
            PRDTE = TO_CHAR(SYSDATE, 'YYYYMMDD'),
            PRPNO = p_prpno
        WHERE RXFAPNO = p_apno1
          AND APNO = p_apno2
          AND SEQNO = p_seqno2;
        
        IF (SQL%ROWCOUNT > 0) THEN
            IF (SQL%ROWCOUNT > 1) THEN
                ROLLBACK;
                p_rtncode := '-1';
                p_rtnmsg := '異動筆數大於一筆，請確認';
                RETURN;
            END IF;
            v_rec_plog.userid    := p_prpno;
            v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
            v_rec_plog.starttime := SYSDATE;
            v_rec_plog.typemk    := '1';
            v_rec_plog.levelmk   := '1'; --INFO
            v_rec_plog.pseq      := '1';
            v_rec_plog.proctime  := SYSDATE;
            v_rec_plog.procname  := 'pkg_baprocrxf.AGREERXF';
            v_rec_plog.msg1      := '更新同意另案扣減金額';
            v_rec_plog.msg2      := '受理編號-應收='||p_apno1||
                                    ',年金受理編號-撥付='||p_apno2||
                                    ',年金受款人序號-撥付='||p_seqno2||
                                    ',同意扣減金額='||p_rbamt;
            pkg_plog.sp_ins_log(v_rec_plog);
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            v_rec_plog.userid    := 'BATCH';
            v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
            v_rec_plog.starttime := SYSDATE;
            v_rec_plog.typemk    := '1';
            v_rec_plog.levelmk   := '3'; --ERROR
            v_rec_plog.pseq      := '1';
            v_rec_plog.proctime  := SYSDATE;
            v_rec_plog.procname  := 'pkg_baprocrxf.AGREERXF';
            v_rec_plog.msg1      := SQLCODE || SQLERRM;
            v_rec_plog.msg2      := dbms_utility.format_error_backtrace;
            pkg_plog.sp_ins_log(v_rec_plog);
            p_rtncode := '-1';
            p_rtnmsg := SQLCODE || SQLERRM;
    END;
    
    --更新同意另案扣減金額
    PROCEDURE AGREERXF(
        p_apno1   IN VARCHAR2,  -- 應收案受理編號
        p_seqno1  IN VARCHAR2,  -- 應收案受款人序
        p_apno2   IN VARCHAR2,  -- 撥付案受理編號
        p_seqno2  IN VARCHAR2,  -- 撥付案受款人序
        p_idnno   IN VARCHAR2,  -- 應收案收款人身分證號
        p_rbamt   IN NUMBER,    -- 同意扣減金額(同意時金額>0；不同意時金額=0；註銷時金額應為空值)
        p_prpno   IN VARCHAR2,  -- 處理人員員編
        p_rtncode OUT VARCHAR2, -- 處理結果(0->成功、-1->異常)
        p_rtnmsg  OUT VARCHAR2  -- 錯誤訊息
    ) IS
        v_rec_plog plog%ROWTYPE;
        v_prst     VARCHAR2(1) := '';
    BEGIN
        p_rtncode := '0';   -- 預設成功
        IF (TRIM(p_apno1) IS NULL OR TRIM(p_apno2) IS NULL OR TRIM(p_seqno2) IS NULL) THEN
            p_rtncode := '-1';
            p_rtnmsg := '應收案/撥付案的受理編號與受款人序不可為空';
            RETURN;
        END IF;
        
        IF (NVL(TRIM(p_rbamt), 0) < 0) THEN
            p_rtncode := '-1';
            p_rtnmsg := '扣減金額不可為負值';
            RETURN;
        END IF;
        
        IF (TRIM(p_rbamt) IS NULL) THEN -- 金額為空表註銷prst上D
            v_prst := 'D';
            p_rtnmsg := '註銷另案扣減收回成功';
        ELSIF (p_rbamt > 0) THEN        -- 金額大於0表同意prst上Y
            v_prst := 'Y';
            p_rtnmsg := '同意扣減成功';
        ELSIF (p_rbamt = 0) THEN        -- 金額為0表不同意prst上N
            v_prst := 'N';
            p_rtnmsg := '不同意扣減成功';
        END IF;
        
        IF (TRIM(p_prpno) IS NULL) THEN
            p_rtncode := '-1';
            p_rtnmsg := '處理人員員編不得為空';
            RETURN;
        END IF;
        
        IF (TRIM(p_idnno) IS NULL) THEN
            p_rtncode := '-1';
            p_rtnmsg := '應收案收款人身分證號不可為空';
            RETURN;
        END IF;
        
        UPDATE BARXF
        SET RBAMT = NVL(TRIM(p_rbamt), 0),
            PRST = v_prst,
            PRDTE = TO_CHAR(SYSDATE, 'YYYYMMDD'),
            PRPNO = p_prpno
        WHERE RXFAPNO = p_apno1
          AND APNO = p_apno2
          AND SEQNO = p_seqno2
          AND RXFIDNNO = p_idnno;
        
        IF (SQL%ROWCOUNT > 0) THEN
            v_rec_plog.userid    := p_prpno;
            v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
            v_rec_plog.starttime := SYSDATE;
            v_rec_plog.typemk    := '1';
            v_rec_plog.levelmk   := '1'; --INFO
            v_rec_plog.pseq      := '1';
            v_rec_plog.proctime  := SYSDATE;
            v_rec_plog.procname  := 'pkg_baprocrxf.AGREERXF';
            v_rec_plog.msg1      := '更新同意另案扣減金額';
            v_rec_plog.msg2      := '受理編號-應收=' || p_apno1 ||
                                    ',年金受理編號-撥付=' || p_apno2 ||
                                    ',年金受款人序號-撥付=' ||p_seqno2 ||
                                    ',應收案收款人身分證號=' || p_idnno ||
                                    ',同意扣減金額=' || p_rbamt;
            pkg_plog.sp_ins_log(v_rec_plog);
        END IF;
    EXCEPTION
        WHEN OTHERS THEN
            ROLLBACK;
            v_rec_plog.userid    := 'BATCH';
            v_rec_plog.jobid     := TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISSSSS');
            v_rec_plog.starttime := SYSDATE;
            v_rec_plog.typemk    := '1';
            v_rec_plog.levelmk   := '3'; --ERROR
            v_rec_plog.pseq      := '1';
            v_rec_plog.proctime  := SYSDATE;
            v_rec_plog.procname  := 'pkg_baprocrxf.AGREERXF';
            v_rec_plog.msg1      := SQLCODE || SQLERRM;
            v_rec_plog.msg2      := dbms_utility.format_error_backtrace;
            pkg_plog.sp_ins_log(v_rec_plog);
            p_rtncode := '-1';
            p_rtnmsg := SQLCODE || SQLERRM;
    END;
END PKG_BAPROCRXF;
/
