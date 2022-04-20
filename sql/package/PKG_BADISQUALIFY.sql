CREATE OR REPLACE PACKAGE BA.PKG_BADISQUALIFY IS
-- ============================================================================
-- 程式目的：BA系統承保取消資格案件查詢
-- 撰寫人員：EthanChen
-- History:
-- 20220314; EthanChen; Start.
-- ============================================================================
-- ============================================================================
-- DECLARE BEGIN
-- ============================================================================
-- 全域變數
    TYPE R_RTN IS RECORD(
        RESGTYPE BCRESG.RESGTYPE%TYPE,
        UBNO     BCPMA.UBNO%TYPE,
        GVNAME   BCBEN.GVNAME%TYPE,
        GVBRTH   BCBEN.GVBRTH%TYPE,
        GVIDNO   BCBEN.GVIDNO%TYPE,
        EVTDTE   BCPMA.EVTDTE%TYPE,
        CHKDTE   BCPMA.CHKDTE%TYPE,
        APNO     BCPMA.APNO%TYPE,
        RCKDTE   BCPMA.RCKDTE%TYPE,
        NDOCMK   BCPMA.NDOCMK%TYPE,
        BIGPG    BCPMA.BIGPG%TYPE,
        RESGDTE  BCRESG.RESGDTE%TYPE,
        SEQNO    BCRESG.SEQNO%TYPE
    );
    TYPE BC_RESG_TAB IS TABLE OF R_RTN;
-- ============================================================================
-- DECLARE END
-- ============================================================================
-- ----------------------------------------------------------------------------
-- BC用取消資格取得
-- ----------------------------------------------------------------------------
    FUNCTION DISQUALIFY_BA(
        p_resgmode VARCHAR2,      -- 取消資格對象(1：個人、2：單位)
        p_resgidno VARCHAR2,      -- 身分證號/保險證號
        p_resgbrth VARCHAR2,      -- 出生日期
        p_resgdte  VARCHAR2       -- 取消日期(退保日)
    ) RETURN BC_RESG_TAB PIPELINED;

END PKG_BADISQUALIFY;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_BADISQUALIFY IS

    CURSOR BA_RESG_MOD1(
        p_idn     IN VARCHAR2,
        p_brth    IN VARCHAR2,
        p_resgdte IN VARCHAR2
    ) RETURN R_RTN IS
        SELECT * 
        FROM (
            SELECT 
                   '' RESGTYPE,
                   DECODE(SUBSTR(APNO, 1, 1), 'L', BAAPPBASE.LSUBNO, BAAPPBASE.APUBNO) UBNO,
                   BAAPPBASE.EVTNAME,
                   BAAPPBASE.EVTBRDATE,
                   BAAPPBASE.EVTIDNNO,
                   BAAPPBASE.EVTJOBDATE,
                   BAAPPBASE.CHKDATE,
                   BAAPPBASE.APNO,
                   '' RCKDTE,
                   '' NDOCMK,
                   TO_NUMBER(BAAPPBASE.ARCPG),
                   '' RESGDTE,
                   '' SEQNO
              FROM BAAPPBASE
             WHERE BAAPPBASE.CASEMK IS NULL
               AND BAAPPBASE.EVTIDNNO = p_idn
               AND BAAPPBASE.EVTJOBDATE >= p_resgdte
        );
    
    CURSOR BA_RESG_MOD2(
        p_idn     IN VARCHAR2,
        p_brth    IN VARCHAR2,
        p_resgdte IN VARCHAR2
    ) RETURN R_RTN IS
        SELECT * 
        FROM (
            SELECT 
                   BCRESG.RESGTYPE,
                   DECODE(SUBSTR(APNO, 1, 1), 'L', BAAPPBASE.LSUBNO, BAAPPBASE.APUBNO) UBNO,
                   BAAPPBASE.EVTNAME,
                   BAAPPBASE.EVTBRDATE,
                   BAAPPBASE.EVTIDNNO,
                   BAAPPBASE.EVTJOBDATE,
                   BAAPPBASE.CHKDATE,
                   BAAPPBASE.APNO,
                   '' RCKDTE,
                   '' NDOCMK,
                   TO_NUMBER(BAAPPBASE.ARCPG),
                   BCRESG.RESGDTE,
                   BCRESG.SEQNO
              FROM BAAPPBASE
             INNER JOIN BCRESG
                ON ((BCRESG.RESGTYPE = '1' AND BCRESG.RESGIDNO = BAAPPBASE.EVTIDNNO) OR
                   (BCRESG.RESGTYPE = '2' AND BCRESG.RESGIDNO = BAAPPBASE.LSUBNO AND
                   SUBSTR(APNO, 1, 1) = 'L') OR
                   (BCRESG.RESGTYPE = '2' AND BCRESG.RESGIDNO = BAAPPBASE.APUBNO AND
                   SUBSTR(APNO, 1, 1) != 'L'))
             WHERE BAAPPBASE.CASEMK IS NULL
               AND BCRESG.CREATDTE = p_resgdte
               AND BAAPPBASE.EVTJOBDATE >= BCRESG.RESGDTE
        );
    
    /*
    -- BC用取消資格取得
    */
    FUNCTION DISQUALIFY_BA(
        p_resgmode VARCHAR2,      -- 取消資格對象(1：個人、2：單位)
        p_resgidno VARCHAR2,      -- 身分證號/保險證號
        p_resgbrth VARCHAR2,      -- 出生日期
        p_resgdte  VARCHAR2       -- 取消日期(退保日)
    ) RETURN BC_RESG_TAB PIPELINED IS
    BEGIN
        IF (p_resgmode = '1') THEN
            FOR l_row IN BA_RESG_MOD1(p_resgidno, p_resgbrth, p_resgdte)
            LOOP
                PIPE ROW(l_row);
            END LOOP;
        ELSIF (p_resgmode = '2') THEN
            FOR l_row IN BA_RESG_MOD2(p_resgidno, p_resgbrth, p_resgdte)
            LOOP
                PIPE ROW(l_row);
            END LOOP;
        END IF;
    END DISQUALIFY_BA;

END PKG_BADISQUALIFY;
/