package tw.gov.bli.ba.dao;

import tw.gov.bli.ba.domain.Bbdapr;

/**
 * DAO for 給付核定檔 (<code>BBDAPR</code>)
 *
 * @author swim
 *
 */
public interface BbdaprDao {

    public Bbdapr getDisableReviewRpt01AnnuityPayDataBy(String apNo, String issuYm, String payYm);

    public Bbdapr selectSurvivorReviewRpt01DateDataBy(String apNo);
}
