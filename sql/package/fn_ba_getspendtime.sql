CREATE OR REPLACE FUNCTION BA.FN_BA_GETSPENDTIME(p_bgntime IN VARCHAR2, -- 開始時間
                                          p_endtime IN VARCHAR2) -- 結束時間
RETURN VARCHAR2
IS
-- ============================================================================
-- 程式目的：依據傳入時間傳回時間差
-- 撰寫人員：Danny Yen
-- 執行方式：供其他程式呼叫使用
-- 傳入參數：p_bgntime  開始時間       varchar2(14)  [Ex: 20081001121212]
--           p_endtime  結束時間       varchar2(14)  [Ex: 20081001121212]
-- 傳出參數：時間差   VARCHAR2 (XXXX時XX分XX秒)
-- 相關表格：
-- 修改紀錄：
-- 維護人員        日期    說明
-- -----------   --------  ----------------------------------------------------
-- Danny         20090521  v1.0
-- ============================================================================
BEGIN
-- ============================================================================
  DECLARE
-- ----------------------------------------------------------------------------
   v_spdnum      NUMBER(10) := 0;     -- 花費秒數
   v_spdtime     VARCHAR2(20) := '';  -- 花費時間
-- ============================================================================
-- MAIN PROCESS
-------------------------------------------------------------------------------
  BEGIN
    v_spdnum := 86400 *
                (TO_DATE(p_endtime,'YYYYMMDDHH24MISS') -
                 TO_DATE(p_bgntime,'YYYYMMDDHH24MISS'));
    IF TRUNC(v_spdnum / 3600) > 0 THEN
       v_spdtime := v_spdtime || TRUNC(v_spdnum / 3600) || '時';
       v_spdnum  := v_spdnum - (TRUNC(v_spdnum / 3600) * 3600);
    END IF;
    --IF TRUNC(v_spdnum / 60) > 0 THEN
       v_spdtime := v_spdtime || LPAD(TRUNC(v_spdnum / 60),2,'0') || '分';
       v_spdnum  := v_spdnum - (TRUNC(v_spdnum / 60) * 60);
    --END IF;
    v_spdtime := v_spdtime || LPAD(v_spdnum,2,'0') || '秒';
    RETURN v_spdtime;
  END;
END FN_BA_GETSPENDTIME;
/

