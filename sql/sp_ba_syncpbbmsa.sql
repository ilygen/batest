CREATE OR REPLACE PROCEDURE BA.SP_BA_SYNCPBBMSA
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            SP_BA_SYNCPBBMSA
    PURPOSE:         將大同資料庫PBBMSA當日異動回寫至NEC資料庫PBBMSA

    PARAMETER(IN):
    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2011/03/08  A70348       Created this procedure.

    NOTES:
    1.
********************************************************************************/
authid definer is
    /*v_errMsg      Varchar2(2000);
    v_startTime   Date;
    v_endTime     Date;
    v_deleteCount Number;
    v_insertCount Number;*/
begin
    /*v_errMsg := ' ';
    v_startTime := sysdate;
    v_deleteCount := 0;
    v_insertCount := 0;*/

    PKG_BA_SYNCPBBMSA.ba_sync_main;


end SP_BA_SYNCPBBMSA;
/

