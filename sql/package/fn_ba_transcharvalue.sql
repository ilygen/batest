create or replace function ba.fn_BA_transCharValue
/******************************************************************************
    PROJECT:         BLI-BaWeb 勞保年金給付系統
    NAME:            fn_BA_transCharValue
    PURPOSE:         字串轉換

    PARAMETER(IN):  *v_Str             (varChar2)       --欲轉換之串
                    *v_transType       (varChar2)       --轉換字串格式類別

    PARAMETER(OUT):  v_return          (varChar2)       --已轉換之字串

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver        Date        Author           Description
    ---------  ----------  ---------------  -------------------------------------
    1.0        2009/04/10  Angela Wu        Created this function.

    NOTES:
    1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

********************************************************************************/
(
    v_Str          in     varChar2,
    v_transType    in     varChar2
) return varchar2

as
    v_i            Number;
    v_LenStr       Number;
    v_return       varChar2(300);
    v_tmpStr       varChar2(3);

begin
    v_i := 1;
    v_LenStr := 0;
    v_tmpStr := '';
    v_return := '';

    if nvl(trim(v_Str),' ')<>' ' then
         v_LenStr := length(v_Str);
    end if;

    if v_transType='1' then
        loop
            v_tmpStr := '';

            if ascii(substr(v_Str,v_i,1))>=33 and ascii(substr(v_Str,v_i,1))<=126 then
                case ascii(substr(v_Str,v_i,1))
                    when 33 then v_tmpStr := '！';
                    when 34 then v_tmpStr := '”';
                    when 35 then v_tmpStr := '＃';
                    when 36 then v_tmpStr := '＄';
                    when 37 then v_tmpStr := '％';
                    when 38 then v_tmpStr := '＆';
                    when 39 then v_tmpStr := '’';
                    when 40 then v_tmpStr := '（';
                    when 41 then v_tmpStr := '）';
                    when 42 then v_tmpStr := '＊';
                    when 43 then v_tmpStr := '＋';
                    when 44 then v_tmpStr := '，';
                    when 45 then v_tmpStr := '－';
                    when 46 then v_tmpStr := '．';
                    when 47 then v_tmpStr := '／';
                    when 48 then v_tmpStr := '０';
                    when 49 then v_tmpStr := '１';
                    when 50 then v_tmpStr := '２';
                    when 51 then v_tmpStr := '３';
                    when 52 then v_tmpStr := '４';
                    when 53 then v_tmpStr := '５';
                    when 54 then v_tmpStr := '６';
                    when 55 then v_tmpStr := '７';
                    when 56 then v_tmpStr := '８';
                    when 57 then v_tmpStr := '９';
                    when 58 then v_tmpStr := '：';
                    when 59 then v_tmpStr := '；';
                    when 60 then v_tmpStr := '＜';
                    when 61 then v_tmpStr := '＝';
                    when 62 then v_tmpStr := '＞';
                    when 63 then v_tmpStr := '？';
                    when 64 then v_tmpStr := '＠';
                    when 65 then v_tmpStr := 'Ａ';
                    when 66 then v_tmpStr := 'Ｂ';
                    when 67 then v_tmpStr := 'Ｃ';
                    when 68 then v_tmpStr := 'Ｄ';
                    when 69 then v_tmpStr := 'Ｅ';
                    when 70 then v_tmpStr := 'Ｆ';
                    when 71 then v_tmpStr := 'Ｇ';
                    when 72 then v_tmpStr := 'Ｈ';
                    when 73 then v_tmpStr := 'Ｉ';
                    when 74 then v_tmpStr := 'Ｊ';
                    when 75 then v_tmpStr := 'Ｋ';
                    when 76 then v_tmpStr := 'Ｌ';
                    when 77 then v_tmpStr := 'Ｍ';
                    when 78 then v_tmpStr := 'Ｎ';
                    when 79 then v_tmpStr := 'Ｏ';
                    when 80 then v_tmpStr := 'Ｐ';
                    when 81 then v_tmpStr := 'Ｑ';
                    when 82 then v_tmpStr := 'Ｒ';
                    when 83 then v_tmpStr := 'Ｓ';
                    when 84 then v_tmpStr := 'Ｔ';
                    when 85 then v_tmpStr := 'Ｕ';
                    when 86 then v_tmpStr := 'Ｖ';
                    when 87 then v_tmpStr := 'Ｗ';
                    when 88 then v_tmpStr := 'Ｘ';
                    when 89 then v_tmpStr := 'Ｙ';
                    when 90 then v_tmpStr := 'Ｚ';
                    when 91 then v_tmpStr := '〔';
                    when 92 then v_tmpStr := '＼';
                    when 93 then v_tmpStr := '〕';
                    when 94 then v_tmpStr := '︿';
                    when 95 then v_tmpStr := '＿';
                    when 96 then v_tmpStr := '‘';
                    when 97 then v_tmpStr := 'ａ';
                    when 98 then v_tmpStr := 'ｂ';
                    when 99 then v_tmpStr := 'ｃ';
                    when 100 then v_tmpStr := 'ｄ';
                    when 101 then v_tmpStr := 'ｅ';
                    when 102 then v_tmpStr := 'ｆ';
                    when 103 then v_tmpStr := 'ｇ';
                    when 104 then v_tmpStr := 'ｈ';
                    when 105 then v_tmpStr := 'ｉ';
                    when 106 then v_tmpStr := 'ｊ';
                    when 107 then v_tmpStr := 'ｋ';
                    when 108 then v_tmpStr := 'ｌ';
                    when 109 then v_tmpStr := 'ｍ';
                    when 110 then v_tmpStr := 'ｎ';
                    when 111 then v_tmpStr := 'ｏ';
                    when 112 then v_tmpStr := 'ｐ';
                    when 113 then v_tmpStr := 'ｑ';
                    when 114 then v_tmpStr := 'ｒ';
                    when 115 then v_tmpStr := 'ｓ';
                    when 116 then v_tmpStr := 'ｔ';
                    when 117 then v_tmpStr := 'ｕ';
                    when 118 then v_tmpStr := 'ｖ';
                    when 119 then v_tmpStr := 'ｗ';
                    when 120 then v_tmpStr := 'ｘ';
                    when 121 then v_tmpStr := 'ｙ';
                    when 122 then v_tmpStr := 'ｚ';
                    when 123 then v_tmpStr := '｛';
                    when 124 then v_tmpStr := '｜';
                    when 125 then v_tmpStr := '｝';
                    when 126 then v_tmpStr := '～';
                end case;
            else
                v_tmpStr := substr(v_Str,v_i,1);
            end if;

            v_return := v_return || v_tmpStr;
            v_i := v_i + 1;
            exit when v_i>v_LenStr;
        end loop;
    else
        v_return := v_Str;
    end if;

    return v_return;
end fn_BA_transCharValue;
/

