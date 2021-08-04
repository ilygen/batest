create or replace function ba.fn_BA_transDateValue
/******************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            fn_BA_transDateValue
    PURPOSE:         西元年日期及民國年日期的轉換

    PARAMETER(IN):  *v_Date            (varChar2)       --欲轉換之日期
                    *v_transType       (varChar2)       --轉換日期格式類別

    PARAMETER(OUT):  v_return          (varChar2)       --已轉換之日期

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver        Date        Author           Description
    ---------  ----------  ---------------  -------------------------------------
    1.0        2009/02/10  Angela Wu        Created this function.

    NOTES:
    1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

********************************************************************************/
(
    v_Date         in     varChar2,
    v_transType    in     varChar2
) return varchar2

as
    v_year        varChar2(4);
    v_return      varChar2(8);

begin
    v_return := v_Date;

    --當傳入的日期年度<=1911年時,轉成民國年需再-1
    if nvl(trim(v_Date),' ')<>' ' then
        if v_transType='1' or v_transType='2' then
            v_year := to_Char(to_Number(substr(v_Date,0,4))-1911);
            if to_Number(v_year)<=0 then
                v_year := to_Number(v_year)-1;
                v_year := substr(v_year,2,length(v_year));
            end if;
        else
            if length(v_Date)=7 or length(v_Date)=5 then
                v_year := to_Char(to_Number(substr(v_Date,0,3))+1911);
            else
                v_year := to_Char(to_Number(substr(v_Date,0,2))+1911);
            end if;
        end if;
    end if;

    --v_transType='1':將西元(年月日)轉換成民國(年月日)民國年為3碼(不足3碼時前面補0),日期格式不含/
    --ex.20080801→0970801
    if v_transType='1' then
        if length(v_Date)=8 then
            v_return := (lpad(v_year,3,'0')||substr(v_Date,5,4));
        else
            v_return := ' ';
        end if;

    --v_transType='2':將西元(年月)轉換成民國(年月),民國年為3碼(不足3碼時前面補0),日期格式不含/
    --ex.200808→09708
    elsif v_transType='2' then
        if length(v_Date)=6 then
            v_return := (lpad(v_year,3,'0')||substr(v_Date,5,2));
        else
            v_return := ' ';
        end if;

    --v_transType='3':將民國(年月日)轉換成西元(年月日),日期格式不含/
    --ex.0970801→20080801
    elsif v_transType='3' then
        if length(v_Date)=7 then
            v_return := (v_year||substr(v_Date,4,4));
        elsif length(v_Date)=6 then
            v_return := (v_year||substr(v_Date,3,4));
        else
            v_return := ' ';
        end if;

    --v_transType='4':將民國(年月)轉換成西元(年月),日期格式不含/
    --ex.09708→200808
    elsif v_transType='4' then
        if length(v_Date)=5 then
            v_return := (v_year||substr(v_Date,4,2));
        elsif length(v_Date)=4 then
            v_return := (v_year||substr(v_Date,3,2));
        else
            v_return := ' ';
        end if;

    else
        v_return := ' ';
    end if;

    return v_return;

end fn_BA_transDateValue;
/

