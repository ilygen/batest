package tw.gov.bli.ba.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import tw.gov.bli.ba.dao.BapasignDao;

public class BapasignDaoImpl extends SqlMapClientDaoSupport implements BapasignDao{

    /**
     * 查詢 核定通知書署名參數檔 (<code>BAPASIGN</code>) 目前任職的總經理
     * 
     * @return
     */
    public String selectManager(String aplpayDate) {
        HashMap<String,String> map = new HashMap<String,String>();
        //map.put("manager", "總經理");
        //map.put("proxyManager", "代理總經理");
        map.put("aplpayDate", aplpayDate);
        return (String) getSqlMapClientTemplate().queryForObject("BAPASIGN.selectManager",map);
    }
}
