package tw.gov.bli.ba.rpt.cases;

import java.io.Serializable;
import java.util.List;

/**
 * Case for 勞保遺屬年金給付受理編審清單 - 核定通知書
 * 
 * @author Evelyn Hsu
 */

public class SurvivorReviewRpt01NotifyDataCase  implements Serializable {
    
    private String subject; // 主旨
    private List<String> content; // 說明
    private String commZip; // 郵遞區號
    private String commAddr; // 地址
    
    public SurvivorReviewRpt01NotifyDataCase(){
        
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

	public String getCommZip() {
		return commZip;
	}

	public void setCommZip(String commZip) {
		this.commZip = commZip;
	}

	public String getCommAddr() {
		return commAddr;
	}

	public void setCommAddr(String commAddr) {
		this.commAddr = commAddr;
	}
    
    

}
