package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;

/**
 * Case for 失能年金編審註記程度調整 - 眷屬資料
 * 
 * @author Goston
 */
public class DisabledCheckMarkLevelAdjustFamDataCase implements Serializable {
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String famIdnNo; // 眷屬身分證號
    private String famName; // 眷屬姓名
    private String famEvtRel; // 眷屬與事故者關係
    private String famDieDate;// 眷屬死亡日期
    

    // 眷屬編審註記資料
    private List<DisabledCheckMarkLevelAdjustFamDetailDataCase> detailList;

    /**
     * 眷屬與事故者關係
     * 
     * @return
     */
    public String getFamEvtRelString() {
        if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_1))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_1;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_2))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_2;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_3))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_3;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_4))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_4;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_5))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_5;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_6))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_6;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_7))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_7;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_A))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_A;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_C))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_C;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_E))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_E;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_F))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_F;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_N))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_N;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_Z))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_Z;
        else if (StringUtils.equals(famEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_O))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_O;
        else
            return "";
    }

    /**
     * 新增 Detail 資料
     * 
     * @param detailData
     */
    public void addDetailData(DisabledCheckMarkLevelAdjustFamDetailDataCase detailData) {
        if (detailList == null)
            detailList = new ArrayList<DisabledCheckMarkLevelAdjustFamDetailDataCase>();

        detailList.add(detailData);
    }

    public DisabledCheckMarkLevelAdjustFamDataCase() {
        detailList = new ArrayList<DisabledCheckMarkLevelAdjustFamDetailDataCase>();
    }

    public String getApNo() {
        return apNo;
    }

    public void setApNo(String apNo) {
        this.apNo = apNo;
    }

    public String getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }

    public String getFamIdnNo() {
        return famIdnNo;
    }

    public void setFamIdnNo(String famIdnNo) {
        this.famIdnNo = famIdnNo;
    }

    public String getFamName() {
        return famName;
    }

    public void setFamName(String famName) {
        this.famName = famName;
    }

    public String getFamEvtRel() {
        return famEvtRel;
    }

    public void setFamEvtRel(String famEvtRel) {
        this.famEvtRel = famEvtRel;
    }

    public List<DisabledCheckMarkLevelAdjustFamDetailDataCase> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<DisabledCheckMarkLevelAdjustFamDetailDataCase> detailList) {
        this.detailList = detailList;
    }

    public String getFamDieDate() {
        return famDieDate;
    }

    public void setFamDieDate(String famDieDate) {
        this.famDieDate = famDieDate;
    }

    public String getFamDieYm() {
        return StringUtils.substring(famDieDate, 0, 6);
    }
    
    

}
