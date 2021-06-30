package tw.gov.bli.ba.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import tw.gov.bli.ba.domain.Baappexpand;
import tw.gov.bli.ba.domain.Bastudnotify;
import tw.gov.bli.ba.domain.Bastudterm;
import tw.gov.bli.common.annotation.DaoFieldList;

/**
 * DAOImpl for 遺屬在學期間檔 (<code>BASTUDNOTIFY</code>)
 * 
 * @author Noctis
 */

public interface BastudnotifyDao {

	 /**
     * 新增遺屬在學期間資料 (<code>BASTUDNOTIFY</code>) for 遺屬年金受款人資料更正
     *
     * @param apNo 受理編號
     * @param seqNo 序號
     * @return 內含<code>Bastudterm</code> 物件的List
     */
    public BigDecimal insertBastudnotify(Bastudnotify bastudnotify);

}
