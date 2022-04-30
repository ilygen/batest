/**
 * 
 */
package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bbgovnbef;

/**
 * @author kn0561
 *
 */
public interface BbgovnbefDao {

	List<Bbgovnbef> getCivilServantReviewRpt01PayListBy(String evtIdnNo, String evtBrDate, String payKindG);

}
