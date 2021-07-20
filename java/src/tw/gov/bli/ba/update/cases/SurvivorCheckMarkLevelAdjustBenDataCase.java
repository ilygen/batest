package tw.gov.bli.ba.update.cases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import tw.gov.bli.ba.ConstantKey;

/**
 * Case for 遺屬年金編審註記程度調整 - 受款人資料
 * 
 * @author Goston
 */
public class SurvivorCheckMarkLevelAdjustBenDataCase implements Serializable {
    private String apNo; // 受理編號
    private String seqNo; // 序號
    private String benIdnNo; // 受款人身分證號
    private String benName; // 受款人姓名
    private String benEvtRel; // 受益人與事故者關係
    private String benDieDate;//受款人死亡日期
    private String appDate;//申請日期

    // 受款人編審註記資料
    private List<SurvivorCheckMarkLevelAdjustBenDetailDataCase> detailList;

    /**
     * 受益人與事故者關係
     * 
     * @return
     */
    public String getBenEvtRelString() {
        if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_1))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_1;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_2))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_2;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_3))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_3;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_4))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_4;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_5))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_5;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_6))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_6;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_7))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_7;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_A))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_A;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_C))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_C;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_E))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_E;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_F))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_F;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_N))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_N;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_Z))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_Z;
        else if (StringUtils.equals(benEvtRel, ConstantKey.BAAPPBASE_BENEVTREL_O))
            return ConstantKey.BAAPPBASE_BENEVTREL_STR_O;
        else
            return "";
    }

    /**
     * 新增 Detail 資料
     * 
     * @param detailData
     */
    public void addDetailData(SurvivorCheckMarkLevelAdjustBenDetailDataCase detailData) {
        if (detailList == null)
            detailList = new ArrayList<SurvivorCheckMarkLevelAdjustBenDetailDataCase>();

        detailList.add(detailData);
    }

    /**
     * 受款人編審註記資料 筆數
     * 
     * @return
     */
    public int getDetailListSize() {
        if (detailList != null)
            return detailList.size();
        else
            return 0;
    }

    public SurvivorCheckMarkLevelAdjustBenDataCase() {
        detailList = new ArrayList<SurvivorCheckMarkLevelAdjustBenDetailDataCase>();
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

    public String getBenIdnNo() {
        return benIdnNo;
    }

    public void setBenIdnNo(String benIdnNo) {
        this.benIdnNo = benIdnNo;
    }

    public String getBenName() {
        return benName;
    }

    public void setBenName(String benName) {
        this.benName = benName;
    }

    public String getBenEvtRel() {
        return benEvtRel;
    }

    public void setBenEvtRel(String benEvtRel) {
        this.benEvtRel = benEvtRel;
    }

    public List<SurvivorCheckMarkLevelAdjustBenDetailDataCase> getDetailList() {
        return detailList;
    }
    
    public void setDetailList(List<SurvivorCheckMarkLevelAdjustBenDetailDataCase> detailList) {
        this.detailList = detailList;
    }

    public String getBenDieDate() {
        return benDieDate;
    }

    public void setBenDieDate(String benDieDate) {
        this.benDieDate = benDieDate;
    }
    
    public String getBenDieYm(){
        return StringUtils.substring(benDieDate, 0,6);
    }
    
    public SurvivorCheckMarkLevelAdjustBenDetailDataCase[] getDetailListArray(){
        return detailList.toArray(new SurvivorCheckMarkLevelAdjustBenDetailDataCase[detailList.size()]);
    }

    public String getAppDate() {
        return appDate;
    }

    public void setAppDate(String appDate) {
        this.appDate = appDate;
    }
}
