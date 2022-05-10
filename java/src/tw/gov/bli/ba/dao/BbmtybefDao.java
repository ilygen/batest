/**
 * 
 */
package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bbmtybef;

/**
 * @author kn0561
 *
 */
public interface BbmtybefDao {

	List<Bbmtybef> getSoldierReviewRpt01PayListBy(String evtIdnNo, String evtBrDate, String payKindG);

}
