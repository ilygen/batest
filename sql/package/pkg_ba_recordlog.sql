CREATE OR REPLACE Package BA.PKG_BA_RecordLog
/********************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            PKG_BA_RecordLog
    PURPOSE:         DB Obj 異動資料時,記錄相關的APLog

    PARAMETER(IN):

    PARAMETER(OUT):

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver   Date        Author       Description
    ----  ----------  -----------  ----------------------------------------------
    1.0   2009/07/16  Angela Wu    Created this Package.

    NOTES:
    1.各 Procedure 所需傳入資料請參考 Package Body 中各 Procedure 的註解說明。

********************************************************************************/
authid definer is
    v_g_errMsg                     varChar2(4000);

    procedure sp_BA_recMMAPLog (
        v_i_tablename      in      varChar2,
        v_i_pkfield        in      varChar2,
        v_i_chgtime        in      varChar2,
        v_i_pgmname        in      varChar2,
        v_i_pgmcode        in      varChar2,
        v_i_deptid         in      varChar2,
        v_i_modifyman      in      varChar2,
        v_i_termed         in      varChar2,
        v_i_chgcode        in      varChar2,
        v_i_field          in      varChar2,
        v_i_befimg         in      varChar2,
        v_i_aftimg         in      varChar2,
        v_i_idno           in      varChar2,
        v_i_memo           in      varChar2,
        v_i_sno            in      Number
   );

   procedure sp_BA_recBaAPPLog (
        v_i_baappbaseid    in      varChar2,
        v_i_status         in      varChar2,
        v_i_updtime        in      varChar2,
        v_i_upduser        in      varChar2,
        v_i_upcol          in      varChar2,
        v_i_bvalue         in      varChar2,
        v_i_avalue         in      varChar2
   );

End;
/

CREATE OR REPLACE Package Body BA.PKG_BA_RecordLog
is
    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayFile.sp_BA_recMMAPLog
        PURPOSE:         DB Obj 異動資料時,記錄相關的MMAPLog

        PARAMETER(IN):  *v_i_tablename     (varChar2)       --異動的TableName
                        *v_i_pkfield       (varChar2)       --異動的Table的PK欄位名稱
                        *v_i_chgtime       (varChar2)       --異動時間
                        *v_i_pgmname       (varChar2)       --異動的程式名稱(中文)
                        *v_i_pgmcode       (varChar2)       --異動的程式名稱(英文)
                        *v_i_deptid        (varChar2)       --異動人員的部門代碼
                        *v_i_modifyman     (varChar2)       --異動人員代碼
                        *v_i_termed        (varChar2)       --異動人員的IP
                        *v_i_chgcode       (varChar2)       --'U':異動
                        *v_i_field         (varChar2)       --異動的欄位
                        *v_i_befimg        (varChar2)       --資料的改前值
                        *v_i_aftimg        (varChar2)       --資料的改後值
                        *v_i_idno          (varChar2)       --ID
                        *v_i_memo          (varChar2)       --備註
                        *v_i_sno           (Number)         --序號

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_recMMAPLog (
        v_i_tablename      in      varChar2,
        v_i_pkfield        in      varChar2,
        v_i_chgtime        in      varChar2,
        v_i_pgmname        in      varChar2,
        v_i_pgmcode        in      varChar2,
        v_i_deptid         in      varChar2,
        v_i_modifyman      in      varChar2,
        v_i_termed         in      varChar2,
        v_i_chgcode        in      varChar2,
        v_i_field          in      varChar2,
        v_i_befimg         in      varChar2,
        v_i_aftimg         in      varChar2,
        v_i_idno           in      varChar2,
        v_i_memo           in      varChar2,
        v_i_sno            in      Number
    ) is
        begin
            insert into MMAPLOG(TABLENAME
                               ,PKFIELD
                               ,CHGTIME
                               ,PGMNAME
                               ,PGMCODE
                               ,DEPTID
                               ,MODIFYMAN
                               ,TERMED
                               ,CHGCODE
                               ,FIELD
                               ,BEFIMG
                               ,AFTIMG
                               ,IDNO
                               ,MEMO
                               ,SNO
                               ) values (
                                v_i_tablename
                               ,v_i_pkfield
                               ,v_i_chgtime
                               ,v_i_pgmname
                               ,v_i_pgmcode
                               ,v_i_deptid
                               ,v_i_modifyman
                               ,v_i_termed
                               ,v_i_chgcode
                               ,v_i_field
                               ,v_i_befimg
                               ,v_i_aftimg
                               ,v_i_idno
                               ,v_i_memo
                               ,v_i_sno);

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line('**Err:PKG_BA_RecordLog-sp_BA_recMMAPLog----------->>'||v_g_errMsg);
        end;
    --procedure sp_BA_recMMAPLog End

    /********************************************************************************
        PROJECT:         BLI-BaWeb 勞保年金給付系統
        NAME:            PKG_BA_genPayFile.sp_BA_recBaAPPLog
        PURPOSE:         DB Obj 異動資料時,記錄相關的BaAPPLog

        PARAMETER(IN):  *v_i_baappbaseid   (varChar2)       --給付主檔的資料列編號(BAAPPBASE.BAAPPBASEID)
                        *v_i_status        (varChar2)       --'U':異動
                        *v_i_updtime       (varChar2)       --異動時間
                        *v_i_upduser       (varChar2)       --異動人員代碼
                        *v_i_upcol         (varChar2)       --異動欄位的中文名稱
                        *v_i_bvalue        (varChar2)       --資料的改前值
                        *v_i_avalue        (varChar2)       --資料的改後值

        PARAMETER(OUT):

        USAGE:
        I/O   (Type)Object Name
        ----  -----------------------------------------------------------------------

        REVISIONS:
        Ver   Date        Author       Description
        ----  ----------  -----------  ----------------------------------------------
        1.0   2009/07/16  Angela Wu    Created this procedure.

        NOTES:
        1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

    ********************************************************************************/
    procedure sp_BA_recBaAPPLog (
        v_i_baappbaseid    in      varChar2,
        v_i_status         in      varChar2,
        v_i_updtime        in      varChar2,
        v_i_upduser        in      varChar2,
        v_i_upcol          in      varChar2,
        v_i_bvalue         in      varChar2,
        v_i_avalue         in      varChar2
    ) is
        begin
            insert into BAAPPLOG(BAAPPBASEID
                                ,STATUS
                                ,UPDTIME
                                ,UPDUSER
                                ,UPCOL
                                ,BVALUE
                                ,AVALUE
                               ) values (
                                v_i_baappbaseid
                               ,v_i_status
                               ,v_i_updtime
                               ,v_i_upduser
                               ,v_i_upcol
                               ,v_i_bvalue
                               ,v_i_avalue);

        exception
            when others
                then
                    v_g_errMsg := SQLErrm;
                    dbms_output.put_line('**Err:PKG_BA_RecordLog-sp_BA_recBaAPPLog---------->>'||v_g_errMsg);
        end;
    --procedure sp_BA_recBaAPPLog End

end;
/

