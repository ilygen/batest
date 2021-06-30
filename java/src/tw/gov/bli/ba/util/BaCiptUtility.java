package tw.gov.bli.ba.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.dao.CiptDao;
import tw.gov.bli.ba.domain.Cipt;
import tw.gov.bli.ba.rpt.cases.CiptUtilityCase;
import tw.gov.bli.common.helper.SpringHelper;

public class BaCiptUtility {

    /**
     * 根據輸入的身分證號 查詢CIPT並且轉成 受理審核清單_承保異動資料報表,畫面呈現 所需的資料
     * 
     * @param idn
     * @return List<CiptUtilityCase> caseList
     */
    public static List<CiptUtilityCase> getCiptUtilityCase(String apNo, String idn) {
        CiptDao ciptDao = (CiptDao) SpringHelper.getBeanById("ciptDao");
        List<CiptUtilityCase> caseList = new ArrayList<CiptUtilityCase>();
        // 取得事故者承保異動資料：
        List<Cipt> ciptListL = ciptDao.selectDataBy(apNo, "0000", "L", idn, null);
        if (ciptListL != null && ciptListL.size() > 0) {
            List<CiptUtilityCase> caseListL = new ArrayList<CiptUtilityCase>();
            CiptUtilityCase lastCiptCase = null;
            for (Cipt cipt : ciptListL) {
                if ("3".equals(cipt.getTxcd()) || "4".equals(cipt.getTxcd()) || ("1".equals(cipt.getTxcd()) && "1".equals(cipt.getDept()))) {
                    CiptUtilityCase ciptCase = new CiptUtilityCase();

                    ciptCase.setUno(cipt.getUno());// 每筆先設定UNO 最後再去掉 因為下面判斷會用到UNO
                    ciptCase.setTxcd(cipt.getTxcd());
                    ciptCase.setIntTp(cipt.getInTyp());
                    ciptCase.setTsMk(cipt.getTsMk());
                    if ("3".equals(cipt.getTxcd()) || "4".equals(cipt.getTxcd())) {
                        ciptCase.setEfDte(cipt.getEfDte());
                        ciptCase.setWage(cipt.getWage());
                        ciptCase.setDept(cipt.getDept());
                        String sidMk = StringUtils.substring(cipt.getSidMk(), 1, 2);
                        if ("E".equals(sidMk) || "O".equals(sidMk)) {
                            ciptCase.setSidMk(sidMk);
                        }
                        if ("66".equals(StringUtils.substring(cipt.getStaff(), 0, 2)) || "00000".equals(StringUtils.substring(cipt.getStaff(), 0, 5))) {
                            ciptCase.setTxcdTypeB("66");
                        }
                    }
                    else {
                        String sidMk = StringUtils.substring(cipt.getSidMk(), 1, 2);
                        if ("E".equals(sidMk)) {
                            ciptCase.setSidMk(sidMk);
                        }
                        else if ("O".equals(sidMk) && "U".equals(StringUtils.substring(cipt.getSidMk(), 2, 3))) {
                            ciptCase.setSidMk(sidMk);
                        }
                    }

                    caseListL.add(ciptCase);
                    lastCiptCase = ciptCase;
                }
                else if ("2".equals(cipt.getTxcd())) {
                    if (lastCiptCase != null && StringUtils.equals(lastCiptCase.getUno(), cipt.getUno())) {
                        lastCiptCase.setExitDte(cipt.getEfDte());
                        if ("*".equals(cipt.getDept()))
                            lastCiptCase.setTxcdTypeA(cipt.getDept());
                    }
                }
            }

            // 上TXCD TYPE2 註記
            // [
            String uno = "";
            for (int i = 0; i < caseListL.size(); i++) {
                CiptUtilityCase ciptCase = caseListL.get(i);
                if (!StringUtils.equals(uno, ciptCase.getUno())) {
                    uno = ciptCase.getUno();
                    if (i > 0 && StringUtils.isBlank(caseListL.get(i - 1).getExitDte())) {
                        caseListL.get(i - 1).setTxcdType2("*");
                    }
                }
            }
            // 判斷最後一筆是否要上TXCD TYPE2 註記
            if (caseListL.size() > 0 && StringUtils.isBlank(caseListL.get(caseListL.size() - 1).getExitDte()))
                caseListL.get(caseListL.size() - 1).setTxcdType2("*");
            // ]

            // 排序
            CiptUtilityCase[] arrayL = (CiptUtilityCase[]) caseListL.toArray(new CiptUtilityCase[0]);
            Arrays.sort(arrayL);

            // 去除相同UNO
            caseList.addAll(removeUno(Arrays.asList(arrayL)));
        }

        // 取得事故者自願職保異動資料：
        List<Cipt> ciptListV = ciptDao.selectDataBy(apNo, "0000", "V", idn, null);
        if (ciptListV != null && ciptListV.size() > 0) {
            List<CiptUtilityCase> caseListV = new ArrayList<CiptUtilityCase>();
            CiptUtilityCase lastCiptCase = null;
            for (Cipt cipt : ciptListV) {
                if ("3".equals(cipt.getTxcd()) || "4".equals(cipt.getTxcd()) || "1".equals(cipt.getTxcd())) {
                    CiptUtilityCase ciptCase = new CiptUtilityCase();
                    ciptCase.setIntTp(cipt.getInTyp());
                    ciptCase.setUno(cipt.getUno());// 每筆先設定UNO 最後再去掉 因為下面判斷會用到UNO
                    ciptCase.setEfDte(cipt.getEfDte());
                    ciptCase.setWage(cipt.getWage());
                    ciptCase.setDept(cipt.getDept());
                    ciptCase.setTxcd(cipt.getTxcd());
                    ciptCase.setTsMk(cipt.getTsMk());
                    if ("66".equals(StringUtils.substring(cipt.getStaff(), 0, 2)) || "00000".equals(StringUtils.substring(cipt.getStaff(), 0, 5))) {
                        ciptCase.setTxcdTypeB("66");
                    }
                    caseListV.add(ciptCase);
                    lastCiptCase = ciptCase;
                }
                else if ("2".equals(cipt.getTxcd())) {
                    if (lastCiptCase != null && StringUtils.equals(lastCiptCase.getUno(), cipt.getUno())) {
                        lastCiptCase.setExitDte(cipt.getEfDte());
                        if ("*".equals(cipt.getDept()))
                            lastCiptCase.setTxcdTypeA(cipt.getDept());
                    }
                }
            }

            // 上TXCD TYPE2 SIDMK 註記
            String uno = "";
            for (int i = 0; i < caseListV.size(); i++) {
                CiptUtilityCase ciptCase = caseListV.get(i);
               	ciptCase.setSidMk("C");
                if (!StringUtils.equals(uno, ciptCase.getUno())) {
                    uno = ciptCase.getUno();
                    if (i > 0 && StringUtils.isBlank(caseListV.get(i - 1).getExitDte())) {
                        caseListV.get(i - 1).setTxcdType2("*");
                    }
                }
            }
            // 判斷最後一筆是否要上TXCD TYPE2 註記
            if (caseListV.size() > 0 && StringUtils.isBlank(caseListV.get(caseListV.size() - 1).getExitDte()))
                caseListV.get(caseListV.size() - 1).setTxcdType2("*");
            // ]

            // 排序
            CiptUtilityCase[] arrayV = (CiptUtilityCase[]) caseListV.toArray(new CiptUtilityCase[0]);
            Arrays.sort(arrayV);

            // 去除相同UNO
            caseList.addAll(removeUno(Arrays.asList(arrayV)));
        }

        return caseList;
    }

    // 將傳進來的LIST 去掉相同UNO欄位 只保留第一筆
    private static List<CiptUtilityCase> removeUno(List<CiptUtilityCase> caseList) {
        if (caseList != null && caseList.size() > 0) {
            String uno = "";
            for (CiptUtilityCase ciptCase : caseList) {
                if (StringUtils.equals(uno, ciptCase.getUno())) {
                    ciptCase.setUno("");
                    if("C".equals(ciptCase.getSidMk())){
                    	ciptCase.setSidMk("");
                    }
                }
                else {
                    uno = ciptCase.getUno();
                }
            }
        }
        return caseList;
    }

    private static void printResult(List<CiptUtilityCase> caseList) {
        for (CiptUtilityCase ciptCase : caseList) {
            System.out.println(ciptCase.getDataString());
        }
    }
}
