package tw.gov.bli.ba.util;

import java.text.DecimalFormat;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class Pbm0001 {
    private static Log log = LogFactory.getLog(Pbm0001.class);
    private DecimalFormat dfNum07 = new DecimalFormat("0000000");
    private final static int weight[] = { 1, 3, 5, 7, 9, 7, 5, 3, 1, 3, 5, 7, 9, 7, 9, 8, 7, 6, 5, 4, 3, 2, 1, 1, 5, 3, 1, 3, 5, 7, 9, 7, 5, 3, 1, 3, 5, 7, 9, 9 };
    private final static String wtbl = " 0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "abcdefghijklmnopqrsutvwxyz";
    private final static int ntbl[] = { 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 2, 3, 4, 5, 6, 7, 8, 9 };
    private final static String sp20 = "                     ";
    private final static int lapno = 14;
    private final static int lgvidno = 10;
    private final static int lstdte = 7;
    private final static int lamt = 7;
    private final static int lin = 38;
    private final static int lchk = 39;
    private final static int pitemc[] = { 14, 24, 31, 38 };
    private StringBuffer rcode = new StringBuffer(pitemc.length);

    public String getRcode() {
        return rcode.toString();
    }

    /*
     * apno 受理編號 gvidno 受款人身分證號 stdte 核付日期 amt 核定金額
     */
    public String getPrintString(String apno, String gvidno, String stdte, int amt) {
        StringBuffer bf = new StringBuffer(40);
        int i1, i, j, sum1, sum2;
        String s;
        try {
            bf.setLength(0);
            bf.append((apno + sp20).substring(0, lapno));
            bf.append((gvidno + sp20).substring(0, lgvidno));
            if (!stdte.trim().equals(""))
                bf.append(dfNum07.format((Integer.parseInt(stdte) - 19110000)));
            else
                bf.append(sp20.substring(0, lstdte));
            bf.append(dfNum07.format(amt));
            s = bf.toString();
            bf.replace (lapno, lapno + lgvidno, sp20.substring(lgvidno));
            rcode.setLength(0);
            rcode.append(sp20.substring(0, pitemc.length));
            i1 = i = j = sum1 = sum2 = 0;
            for (i1 = 0; i1 < pitemc.length; i1++) {
                while (i < pitemc[i1]) {
                    if ((j = wtbl.indexOf(s.substring(i, i + 1))) >= 0) {
                        sum1 += ntbl[j];
                        sum2 += ntbl[j] * weight[i];
                    }
                    else
                        rcode.replace(i1, i1 + 1, "*");
                    i++;
                }
            }
            bf.append(sum1 % 10);
            sum2 += (sum1 % 10) * weight[lchk - 1];
            bf.append(Integer.toHexString(sum2 % 13).toUpperCase());
            if (rcode.toString().equals(sp20.substring(0, pitemc.length)))
                return bf.toString();            	
            else{
            	log.error("檢核不過 APPNO=" + apno + "IDNO=" + gvidno + "APLPAYDATE=" + stdte + "ISSUEAMT=" + amt);
            	return null;
            }
        }
        catch (Exception e) {
            log.error("Pbm0001 error");
            ExceptionUtility.getStackTrace(e);
            return null;
        }
    }


}
