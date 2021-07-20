create or replace function ba.fn_ReplacePosition
/******************************************************************************
    PROJECT:         
    NAME:            fn_ReplacePosition
    PURPOSE:         IDN變造-->IDN位置變換
 
    PARAMETER(IN):  *v_idno          (varChar2)     --欲變換之IDN
                    *v_length        (number)       --IDN長度
                    *v_index1        (number)       --欲變換IDN位置1
                    *v_index2        (number)       --欲變換IDN位置2

    PARAMETER(OUT):  v_return        (varChar2)       --已變換之IDN

    USAGE:
    I/O   (Type)Object Name
    ----  -----------------------------------------------------------------------

    REVISIONS:
    Ver        Date        Author           Description
    ---------  ----------  ---------------  -------------------------------------
    1.0        2010/06/28  Justin Yu        Created this function.

    NOTES:
    1.於上方的PARAMETER(IN)中,打"*"者為必傳入之參數值。

********************************************************************************/

(
    v_idno   in varchar2,
    v_length in number,
    v_index1 in number ,
    v_index2 in number
) return varchar2
  as
    v_tmp           number;
    v_tmpStr1       varChar2(1);
    v_tmpStr2       varChar2(1);
    v_return        varChar2(10);
    
begin
  v_tmp := 0;
  v_tmpStr1 := '';
  v_tmpStr2 := '';
  v_return  := v_idno;
  
  if length(v_return)< v_length then
     v_return := lpad(v_return,v_length,' ');
  end if; 
  
    
  v_tmpStr1 := substr(v_return,v_index1,1);
  v_tmpStr2 := substr(v_return,v_index2,1);  
  
  v_return := substr(v_return,1,(v_index1-1))||v_tmpStr2||
              substr(v_return,(v_index1+1),(v_index2-v_index1-1))||  
              v_tmpStr1||substr(v_return,(v_index2+1));
  
  return v_return;
end fn_ReplacePosition;
/

