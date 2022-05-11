/**
 * 
 */
package tw.gov.bli.ba.dao;

import java.util.List;

import tw.gov.bli.ba.domain.Bbgovnpes;

/**
 * @author kn0561
 *
 */
public interface BbgovnpesDao {

	List<Bbgovnpes> getCivilServantReviewRpt01PayListBy(String evtIdnNo, String evtBrDate, String payKindG);


}
