CREATE OR REPLACE PACKAGE BA.PKG_BA_UTILITY
AUTHID DEFINER IS
    /********************************************************************************
        專案名稱 : BLI-BaWeb 勞保年金給付系統
        程式名稱 : PKG_BA_UTILITY
        使用物件 :
        I/O  物件名稱
        ---  ------------------------------------------------------------------------
        版    本 :
        Ver  日期        異動者      異動描述
        ---  ----------  ----------  ------------------------------------------------
        0.1                          Created this package
        0.2  2023.12.14  NieNie      #8630 新增BC身份証加密元件-FOR 勞委會統計處元件
     ********************************************************************************/
   TYPE T_DEX_COL IS VARRAY (10) OF NUMBER (2);
   TYPE T_END_COL IS VARRAY (128) OF VARCHAR2 (1);
   V_DEX_COL    CONSTANT T_DEX_COL := T_DEX_COL (2, 7, 10, 5, 4, 9, 6, 3, 8);
   G_V_TO_CHARS CONSTANT T_END_COL := T_END_COL (UNISTR ('\0000'), UNISTR ('\0001'), UNISTR ('\0002'), UNISTR ('\0003'),
                                                 UNISTR ('\0004'), UNISTR ('\0005'), UNISTR ('\0006'), UNISTR ('\0007'),
                                                 UNISTR ('\0008'), UNISTR ('\0009'), UNISTR ('\000a'), UNISTR ('\000b'),
                                                 UNISTR ('\000c'), UNISTR ('\000d'), UNISTR ('\000e'), UNISTR ('\000f'),
                                                 UNISTR ('\0010'), UNISTR ('\0011'), UNISTR ('\0012'), UNISTR ('\0013'),
                                                 UNISTR ('\0014'), UNISTR ('\0015'), UNISTR ('\0016'), UNISTR ('\0017'),
                                                 UNISTR ('\0018'), UNISTR ('\0019'), UNISTR ('\001a'), UNISTR ('\001b'),
                                                 UNISTR ('\001c'), UNISTR ('\001d'), UNISTR ('\001e'), UNISTR ('\001f'),
                                                 UNISTR ('\0020'), UNISTR ('\0021'), UNISTR ('\0022'), UNISTR ('\0023'),
                                                 UNISTR ('\0024'), UNISTR ('\0025'), UNISTR ('\0026'), UNISTR ('\000d'),
                                                 UNISTR ('\0028'), UNISTR ('\0029'), UNISTR ('\002a'), UNISTR ('\002b'),
                                                 UNISTR ('\002c'), UNISTR ('\002d'), UNISTR ('\002e'), UNISTR ('\002f'),
                                                 UNISTR ('\0037'), UNISTR ('\0032'), UNISTR ('\0035'), UNISTR ('\0038'),
                                                 UNISTR ('\0031'), UNISTR ('\0030'), UNISTR ('\0036'), UNISTR ('\0039'),
                                                 UNISTR ('\0034'), UNISTR ('\0033'), UNISTR ('\003a'), UNISTR ('\003b'),
                                                 UNISTR ('\003c'), UNISTR ('\003d'), UNISTR ('\003e'), UNISTR ('\003f'),
                                                 UNISTR ('\0040'), UNISTR ('\004b'), UNISTR ('\0054'), UNISTR ('\0045'),
                                                 UNISTR ('\004e'), UNISTR ('\0057'), UNISTR ('\0047'), UNISTR ('\0051'),
                                                 UNISTR ('\005a'), UNISTR ('\0048'), UNISTR ('\0052'), UNISTR ('\0059'),
                                                 UNISTR ('\0042'), UNISTR ('\004f'), UNISTR ('\0056'), UNISTR ('\0044'),
                                                 UNISTR ('\004c'), UNISTR ('\0053'), UNISTR ('\0041'), UNISTR ('\004a'),
                                                 UNISTR ('\0049'), UNISTR ('\0043'), UNISTR ('\004d'), UNISTR ('\0058'),
                                                 UNISTR ('\0046'), UNISTR ('\0050'), UNISTR ('\0055'), UNISTR ('\005b'),
                                                 UNISTR ('\000d'), UNISTR ('\005d'), UNISTR ('\005e'), UNISTR ('\005f'),
                                                 UNISTR ('\0060'), UNISTR ('\0061'), UNISTR ('\0062'), UNISTR ('\0063'),
                                                 UNISTR ('\0064'), UNISTR ('\0065'), UNISTR ('\0066'), UNISTR ('\0067'),
                                                 UNISTR ('\0068'), UNISTR ('\0069'), UNISTR ('\006a'), UNISTR ('\006b'),
                                                 UNISTR ('\006c'), UNISTR ('\006d'), UNISTR ('\006e'), UNISTR ('\006f'),
                                                 UNISTR ('\0070'), UNISTR ('\0071'), UNISTR ('\0072'), UNISTR ('\0073'),
                                                 UNISTR ('\0074'), UNISTR ('\0075'), UNISTR ('\0076'), UNISTR ('\0077'),
                                                 UNISTR ('\0078'), UNISTR ('\0079'), UNISTR ('\007a'), UNISTR ('\007b'),
                                                 UNISTR ('\007c'), UNISTR ('\007d'), UNISTR ('\007e'), UNISTR ('\007f')
                                                );
 FUNCTION FN_ENCODE_IDN (P_IDN IN VARCHAR2) RETURN VARCHAR2;

END;
/

CREATE OR REPLACE PACKAGE BODY BA.PKG_BA_UTILITY
AS
    /********************************************************************************
       NEC 20230816 1 SamChang 14:40 上版註記
     ********************************************************************************/


  FUNCTION FN_ENCODE_IDN (
    P_IDN IN VARCHAR2
  ) RETURN VARCHAR2

    /********************************************************************************
        專案名稱 : BLI-BaWeb 勞保年金給付系統
        程式名稱 : PKG_BA_UTILITY.FN_ENCODE_IDN
        中文名稱 : 身份証加密元件-FOR 勞委會統計處元件
        輸入參數 : P_IDN
        輸出參數 :
        程式說明 : 身份証加密元件
        備    註 :
        使用物件 :
        I/O  物件名稱
        ---  ------------------------------------------------------------------------
        版    本 :
        Ver  日期        異動者      異動描述
        ---  ----------  ----------  ------------------------------------------------
        0.1  2023.12.13  NieNie      Created this package
    ********************************************************************************/

  IS

    V_IDN     VARCHAR2 (11);
    V_IDX_VAL VARCHAR2 (1);

    BEGIN

      IF TRIM(P_IDN) IS NOT NULL THEN

        V_IDN := G_V_TO_CHARS (ASCII (SUBSTR (P_IDN, 1, 1)) + 1);

        FOR I IN 1 .. V_DEX_COL.COUNT
        LOOP
          IF (LENGTH (P_IDN) >= V_DEX_COL (I)) THEN
            V_IDX_VAL := SUBSTR (P_IDN, V_DEX_COL (I), 1);

            IF (V_DEX_COL (I) <> 2) THEN

              IF (V_IDX_VAL NOT BETWEEN '0' AND '9') THEN
                V_IDX_VAL := G_V_TO_CHARS (ASCII (V_IDX_VAL) + 1);
              ELSE
                V_IDX_VAL := G_V_TO_CHARS ( 48 + MOD (MOD (ASCII (V_IDX_VAL) + 1, 48), 10) + 1 );
              END IF;

              IF (V_IDX_VAL = ' ') THEN
                V_IDX_VAL := '*';
              END IF;

            END IF;

            V_IDN := V_IDN || V_IDX_VAL;

          END IF;
        END LOOP;

        IF (LENGTH (P_IDN) > 10) THEN
          V_IDN := V_IDN || SUBSTR (P_IDN, -1, 1);
        END IF;

      ELSE
        V_IDN := NULL;
      END IF;

      RETURN V_IDN;

    END FN_ENCODE_IDN;

END PKG_BA_UTILITY ;
/