package tw.gov.bli.ba.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapapaykindDao;
import tw.gov.bli.ba.domain.Bapapaykind;

public class BapapaykindDaoImpl extends SqlMapClientDaoSupport implements BapapaykindDao{
    /**
     * 依傳入條件取得 給付種類對應科別參數檔(<code>BAPAPAYKIND</code>) 資料 
     * 
     * @param reType 收支區分
     * @return
     */
    public List<Bapapaykind> selectDataBy(String payKind){
        return (List<Bapapaykind>) getSqlMapClientTemplate().queryForList("BAPAPAYKIND.selectDataBy",payKind);
    }
}
