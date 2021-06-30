<%@ page language="java" pageEncoding="UTF-8"%>
<script type='text/javascript' src='<c:url value="/js/menu.js"/>'></script>
<script language="javascript">
var menuHierarchy = [
    <acl:menuAcl itemId="BAAAAA,BAAAAB,BAAAAC">
    ['受理作業', null,
        <acl:menuAcl itemId="BAAAAA">
        ['老年年金給付受理作業', '<c:url value="/enterOldAgeAnnuityReceipt.do?parameter=enterOldAgeAnnuityReceipt"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAAAB">
        ['失能年金給付受理作業', '<c:url value="/enterDisabledAnnuityReceipt.do?parameter=enterDisabledAnnuityReceipt"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAAAC">
        ['遺屬年金給付受理作業', '<c:url value="/enterSurvivorAnnuityReceipt.do?parameter=enterSurvivorAnnuityReceipt"/>',],
        </acl:menuAcl>
    ],
    </acl:menuAcl>

    <acl:menuAcl itemId="BAABAA,BAABAB,BAABAC">
    ['審核作業', null,
        <acl:menuAcl itemId="BAABAA">
        ['老年年金給付審核作業', '<c:url value="/enterPaymentReview.do?parameter=enterPaymentReview"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAABAB">
        ['失能年金給付審核作業', '<c:url value="/enterDisabledPaymentReview.do?parameter=enterDisabledPaymentReview"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAABAC">
        ['遺屬年金給付審核作業', '<c:url value="/enterSurvivorPaymentReview.do?parameter=enterSurvivorPaymentReview"/>',],
        </acl:menuAcl>
    ],
    </acl:menuAcl>
    
    <acl:menuAcl itemId="BAACAA,BAACAB,BAACAC">
    ['決行作業', null,
        <acl:menuAcl itemId="BAACAA">
        ['老年年金給付決行作業', '<c:url value="/enterPaymentDecision.do?parameter=enterPaymentDecision"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAACAB">
        ['失能年金給付決行作業', '<c:url value="/enterDisabledPaymentDecision.do?parameter=enterDisabledPaymentDecision"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAACAC">
        ['遺屬年金給付決行作業', '<c:url value="/enterSurvivorPaymentDecision.do?parameter=enterSurvivorPaymentDecision"/>',],
        </acl:menuAcl>
    ],
    </acl:menuAcl>

    <acl:menuAcl itemId="BAADAA,BAADAB,BAADACAA,BAADACAB,BAADACAC,BAADADAA,BAADADAB,BAADADAC,BAADAEAA,BAADAEAB,BAADAEAC,BAADAH,BAADAF,BAADAG,BAADAIAA,BAADAJAA,BAADAJAD">
    ['更正作業', null,
        <acl:menuAcl itemId="BAADAA">
        ['通訊資料更正','<c:url value="/enterCommunicationDataUpdate.do?parameter=enterCommunicationDataUpdate"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADAB">
        ['給付資料更正','<c:url value="/enterPaymentDataUpdate.do?parameter=enterPaymentDataUpdate"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADACAA,BAADACAB,BAADACAC">
        ['案件資料更正　　　　　　　&#9658;','',
            <acl:menuAcl itemId="BAADACAA">
                ['老年年金案件資料更正','<c:url value="/enterApplicationDataUpdate.do?parameter=enterApplicationDataUpdate"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAADACAB">
                ['失能年金案件資料更正','<c:url value="/enterDisabledApplicationDataUpdate.do?parameter=enterDisabledApplicationDataUpdate"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAADACAC">
                ['遺屬年金案件資料更正','<c:url value="/enterSurvivorApplicationDataUpdate.do?parameter=enterSurvivorApplicationDataUpdate"/>',],
            </acl:menuAcl>
        ],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADADAA,BAADADAB,BAADADAC">
        ['編審註記程度調整　　　　　&#9658;','',
            <acl:menuAcl itemId="BAADADAA">
                ['老年年金編審註記程度調整', '<c:url value="/enterCheckMarkLevelAdjust.do?parameter=enterCheckMarkLevelAdjust"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAADADAB">
                ['失能年金編審註記程度調整', '<c:url value="/enterDisabledCheckMarkLevelAdjust.do?parameter=enterDisabledCheckMarkLevelAdjust"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAADADAC">
                ['遺屬年金編審註記程度調整', '<c:url value="/enterSurvivorCheckMarkLevelAdjust.do?parameter=enterSurvivorCheckMarkLevelAdjust"/>',],
            </acl:menuAcl>
        ],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADAEAA,BAADAEAB,BAADAEAC">
        ['受款人資料更正　　　　　　&#9658;', '',
        	<acl:menuAcl itemId="BAADAEAA">
        	['老年年金受款人資料更正', '<c:url value="/enterPayeeDataUpdate.do?parameter=enterPayeeDataUpdate"/>',],
        	</acl:menuAcl>
        	<acl:menuAcl itemId="BAADAEAB">
        	['失能年金受款人資料更正', '<c:url value="/enterDisabledPayeeDataUpdate.do?parameter=enterDisabledPayeeDataUpdate"/>',],
        	</acl:menuAcl>
        	<acl:menuAcl itemId="BAADAEAC">
        	['遺屬年金受款人資料更正', '<c:url value="/enterSurvivorPayeeDataUpdate.do?parameter=enterSurvivorPayeeDataUpdate"/>',],
        	</acl:menuAcl>
        ],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADAH">
        ['眷屬資料更正', '<c:url value="/enterDependantDataUpdateQuery.do?parameter=enterDependantDataUpdateQuery"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADAF">
        ['止付處理', '<c:url value="/enterStopPaymentProcess.do?parameter=enterStopPaymentProcess"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADAG">
        ['債務繼承人資料登錄作業', '<c:url value="/enterInheritorRegister.do?parameter=enterInheritorRegister"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADAIAA">
        ['死亡改匯處理作業　　　　　&#9658;', '',
            <acl:menuAcl itemId="BAADAIAA">
                ['老年年金受款人死亡改匯處理','<c:url value="/enterOldAgeDeathRepay.do?parameter=enterOldAgeDeathRepay"/>',],
            </acl:menuAcl>
        ],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADAJAA,BAADAJAB,BAADAJAC">
        ['應收收回處理作業　　　　　&#9658;', '',
            <acl:menuAcl itemId="BAADAJAA">
                ['老年年金應收收回處理','<c:url value="/enterOldAgePaymentReceive.do?parameter=enterOldAgePaymentReceive"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAADAJAB">
                ['失能年金應收收回處理','<c:url value="/enterDisabledPaymentReceive.do?parameter=enterDisabledPaymentReceive"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAADAJAC">
                ['遺屬年金應收收回處理','<c:url value="/enterSurvivorPaymentReceive.do?parameter=enterSurvivorPaymentReceive"/>',],
            </acl:menuAcl>
        ],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAADAKAA,BAADAKAB,BAADAKAC">
        ['結案狀態變更作業　　　　　&#9658;', '',
            <acl:menuAcl itemId="BAADAKAA">
    	        ['老年年金結案狀態變更作業','<c:url value="/enterOldAgeCloseStatusAlteration.do?parameter=enterOldAgeCloseStatusAlteration"/>',],
    	    </acl:menuAcl>
            <acl:menuAcl itemId="BAADAKAB">
    	        ['失能年金結案狀態變更作業','<c:url value="/enterDisabledCloseStatusAlteration.do?parameter=enterDisabledCloseStatusAlteration"/>',],
    	    </acl:menuAcl>
            <acl:menuAcl itemId="BAADAKAC">
        	    ['遺屬年金結案狀態變更作業','<c:url value="/enterSurvivorCloseStatusAlteration.do?parameter=enterSurvivorCloseStatusAlteration"/>',],
        	</acl:menuAcl>
        ],
        </acl:menuAcl>
    ],
    </acl:menuAcl>
    
    <acl:menuAcl itemId="BAAEAA,BAAEAB,BAAEAC,BAAEAD,BAAEAE,BAAEAF">
    ['查詢作業', null,
        <acl:menuAcl itemId="BAAEAA">
        ['給付查詢', '<c:url value="/enterPaymentQuery.do?parameter=enterPaymentQuery"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAEAF">
        ['給付查詢(外單位專用)', '<c:url value="/enterPaymentQuerySimplify.do?parameter=enterPaymentQuery"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAEAB">
        ['應收查詢', '<c:url value="/enterReceivableQuery.do?parameter=enterReceivableQuery"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAEAC">
        ['更正日誌查詢', '<c:url value="/enterUpdateLogQuery.do?parameter=enterUpdateLogQuery"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAEAD">
        ['編審註記查詢', '<c:url value="/enterCheckMarkLevelQuery.do?parameter=enterCheckMarkLevelQuery"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAEAE">
        ['行政支援查詢', '<c:url value="/enterExecutiveSupportQuery.do?parameter=enterExecutiveSupportQuery"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAEAG">
        ['年金統計查詢', '<c:url value="/enterAnnuityStatistics.do?parameter=enterAnnuityStatistics"/>',],
        </acl:menuAcl>
    ],
    </acl:menuAcl>
    
    <acl:menuAcl itemId="BAAFAAAA,BAAFAAAB,BAAFAAAD,BAAFABAA,BAAFABAB,BAAFABAC,BAAFABAD,BAAFABAE,BAAFACAA,BAAFACAB,BAAFADAD,BAAFADAI,BAAFADAH,BAAFADAJ,BAAFADAK,BAAFADAR,BAAFADAS,BAAFADAY,BAAFADAQ,BAAFADAL,BAAFADAM,BAAFADAN,BAAFADAO,BAAFADAU,BAAFADAV,BAAFADAT,BAAFADAP,BAAFADAF,BAAFADAB,BAAFADAC,BAAFADAE,BAAFADAG,BAAFADAA,BAAFADAW,BAAFAFAA,BAAFAGAA,BAAFADBA,BAAFADBB">
    ['列印作業', null,
        <acl:menuAcl itemId="BAAFAAAA,BAAFAAAB,BAAFAAAD">
        ['審核作業相關報表　　　&#9658;', '',
            <acl:menuAcl itemId="BAAFAAAA">
            ['勞保年金給付受理編審清單', '<c:url value="/enterOldAgeReviewRpt01.do?parameter=enterOldAgeReviewRpt01"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAAAB">
            ['審核給付清單', '<c:url value="/enterOldAgeReviewRpt02.do?parameter=enterOldAgeReviewRpt02"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAAAD">
            ['另案扣減照會單', '<c:url value="/enterOldAgeReviewRpt04.do?parameter=enterOldAgeReviewRpt04"/>',],
            </acl:menuAcl>
        ],
        </acl:menuAcl>
        
        <acl:menuAcl itemId="BAAFABAA,BAAFABAB,BAAFABAC,BAAFABAD,BAAFABAE">
        ['更正作業相關報表　　　&#9658;', '',
            <acl:menuAcl itemId="BAAFABAA">
            ['紓困繳納查詢單', '<c:url value="/enterDataUpdateRpt01.do?parameter=enterDataUpdateRpt01"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFABAB">
            ['紓困貸款撥款情形查詢清單', '<c:url value="/enterDataUpdateRpt02.do?parameter=enterDataUpdateRpt02"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFABAC">
            ['紓困貸款抵銷情形照會單', '<c:url value="/enterDataUpdateRpt03.do?parameter=enterDataUpdateRpt03"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFABAD">
            ['紓困債務人照會單', '<c:url value="/enterDataUpdateRpt04.do?parameter=enterDataUpdateRpt04"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFABAE">
            ['止付單', '<c:url value="/enterDataUpdateRpt05.do?parameter=enterDataUpdateRpt05"/>',],
            </acl:menuAcl>
        ],
        </acl:menuAcl>

        <acl:menuAcl itemId="BAAFACAA,BAAFACAB,BAAFACAC">
        ['決行作業相關報表　　　&#9658;', '',
            <acl:menuAcl itemId="BAAFACAA">
            ['歸檔清單', '<c:url value="/enterDecisionRpt01.do?parameter=enterDecisionRpt01"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFACAB">
            ['歸檔清單補印', '<c:url value="/enterDecisionRpt02.do?parameter=enterDecisionRpt02"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFACAC">
            ['歸檔清單點交清冊', '<c:url value="/enterDecisionRpt03.do?parameter=enterDecisionRpt03"/>',],
            </acl:menuAcl>
        ],
        </acl:menuAcl>

        /*
        ['收回作業相關報表', '',
           ['已發現金給付應收回暫列應收款通知單', '',],
           ['收回通知單', '',],
           ['溢領給付繳款單(一般)', '',],
           ['溢領給付繳款單(分期)', '',],
         ],
        */
            
        <acl:menuAcl itemId="BAAFADAD,BAAFADAI,BAAFADAH,BAAFADAJ,BAAFADAK,BAAFADAR,BAAFADAS,BAAFADAY,BAAFADAQ,BAAFADAL,BAAFADAM,BAAFADAN,BAAFADAO,BAAFADAU,BAAFADAV,BAAFADAT,BAAFADAP,BAAFADAF,BAAFADAB,BAAFADAC,BAAFADAE,BAAFADAG,BAAFADAA,BAAFADAW,BAAFADAZ,BAAFADBA,BAAFADBB">
        ['月核定處理相關報表　　&#9658;', '',
            <acl:menuAcl itemId="BAAFADAW">
            ['月處理核定報表彙整','<c:url value="/enterMonthlyRpt22.do?parameter=enterMonthlyRpt22"/>',],
            </acl:menuAcl>
            
            <acl:menuAcl itemId="BAAFADAD,BAAFADAI,BAAFADAH,BAAFADAJ,BAAFADAK,BAAFADAR,BAAFADAS,BAAFADAY,BAAFADAZ">
            ['月核定帳務相關報表　　　　&#9658;','',
                <acl:menuAcl itemId="BAAFADAD">
                ['月核定撥付總表', '<c:url value="/enterMonthlyRpt04.do?parameter=enterMonthlyRpt04"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAI">
                ['月核定給付撥款總額表', '<c:url value="/enterMonthlyRpt07.do?parameter=enterMonthlyRpt07"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAH">
                ['月核定合格清冊', '<c:url value="/enterMonthlyRpt08.do?parameter=enterMonthlyRpt08"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAJ">
                ['給付抵銷紓困貸款明細', '<c:url value="/enterMonthlyRpt09.do?parameter=enterMonthlyRpt09"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAK">
                ['核付清單及核付明細表', '<c:url value="/enterMonthlyRpt10.do?parameter=enterMonthlyRpt10"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAR">
                ['退回現金清冊', '<c:url value="/enterMonthlyRpt17.do?parameter=enterMonthlyRpt17"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAS">
                ['退回現金轉暫收及待結轉清單', '<c:url value="/enterMonthlyRpt18.do?parameter=enterMonthlyRpt18"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAY">
                ['郵政匯票通知／入戶匯款證明', '<c:url value="/enterMonthlyRpt23.do?parameter=enterMonthlyRpt23"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAZ">
                ['代扣補償金清冊', '<c:url value="/enterMonthlyRpt24.do?parameter=enterMonthlyRpt24"/>',],
                </acl:menuAcl>                
            ],
            </acl:menuAcl>
            
            <acl:menuAcl itemId="BAAFADAQ,BAAFADAL,BAAFADAM,BAAFADAN,BAAFADAO">
            ['退╱改匯帳務相關報表　　　&#9658;','',
                <acl:menuAcl itemId="BAAFADAQ">
                ['退匯通知書', '<c:url value="/enterMonthlyRpt16.do?parameter=enterMonthlyRpt16"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAL">
                ['退匯核定清單', '<c:url value="/enterMonthlyRpt11.do?parameter=enterMonthlyRpt11"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAM">
                ['退匯清冊', '<c:url value="/enterMonthlyRpt12.do?parameter=enterMonthlyRpt12"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAN">
                ['改匯核定清單', '<c:url value="/enterMonthlyRpt13.do?parameter=enterMonthlyRpt13"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAO">
                ['改匯清冊', '<c:url value="/enterMonthlyRpt14.do?parameter=enterMonthlyRpt14"/>',],
                </acl:menuAcl>
            ],
            </acl:menuAcl>
            
            <acl:menuAcl itemId="BAAFADAU,BAAFADAV,BAAFADAT,BAAFADAP">
            ['應收╱已收帳務相關報表　　&#9658;','',
                <acl:menuAcl itemId="BAAFADAU">
                ['應收款立帳核定清單', '<c:url value="/enterMonthlyRpt20.do?parameter=enterMonthlyRpt20"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAV">
                ['應收款立帳清冊', '<c:url value="/enterMonthlyRpt21.do?parameter=enterMonthlyRpt21"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAT">
                ['應收已收核定清單', '<c:url value="/enterMonthlyRpt19.do?parameter=enterMonthlyRpt19"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAP">
                ['應收已收清冊', '<c:url value="/enterMonthlyRpt15.do?parameter=enterMonthlyRpt15"/>',],
                </acl:menuAcl> 
            ], 
            </acl:menuAcl>
            
            <acl:menuAcl itemId="BAAFADAF,BAAFADAB,BAAFADAC,BAAFADAE,BAAFADAG,BAAFADAA,BAAFADAX,BAAFADBA,BAAFADBB">
            ['月核定處理相關報表　　　　&#9658;','',
                <acl:menuAcl itemId="BAAFADAF">
                ['核定清單', '<c:url value="/enterMonthlyRpt06.do?parameter=enterMonthlyRpt06"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAB">
                ['月編審異動清單', '<c:url value="/enterMonthlyRpt02.do?parameter=enterMonthlyRpt02"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAC">
                ['月核定差異統計表', '<c:url value="/enterMonthlyRpt03.do?parameter=enterMonthlyRpt03"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAE">
                ['給付函核定通知書', '<c:url value="/enterMonthlyRpt05.do?parameter=enterMonthlyRpt05"/>',],
                </acl:menuAcl>
                
                //['不給付函核定通知書', '',],
                
                //<acl:menuAcl itemId="BAAFADAG">
                //['核定清單移交清冊', 'BALP0D170L.htm',],
                //</acl:menuAcl>
                
                <acl:menuAcl itemId="BAAFADAA">
                ['月處理核定合格清冊', '<c:url value="/enterMonthlyRpt01.do?parameter=enterMonthlyRpt01"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADAX">
                ['線上月處理異動清單', '<c:url value="/enterMonthlyBatchRpt.do?parameter=enterMonthlyBatchRpt"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADBA">
                ['保留遺屬年金計息存儲核定清單', '<c:url value="/enterMonthlyRpt32.do?parameter=enterMonthlyRpt32"/>',],
                </acl:menuAcl>
                <acl:menuAcl itemId="BAAFADBB">
                ['保留遺屬年金計息存儲清冊', '<c:url value="/enterMonthlyRpt33.do?parameter=enterMonthlyRpt33"/>',],
                </acl:menuAcl> 
            ],
            </acl:menuAcl>
        ],
        </acl:menuAcl>
        
        //<acl:menuAcl itemId="BAAFAFAA">
        //['稽催管制相關報表　　　&#9658;', '',
            //['審核3日後未複核清單', '',],
            //['決行3日後未確認清單', '',],
            //['原暫緩給付狀態解除之給付案件明細表', '',],
            //['勞保各項年金給付逾期未結案件管制表', '',],
            //['應收未收餘額清單', '',],
            //['應收款立帳初核7日後未確認清單', '',],
            //['核付後事故者重要欄位整檔核對清單', '',],
            
            //<acl:menuAcl itemId="BAAFAFAA">
            //['更正紀錄管制清單', 'BALP0D180L.htm',],
            //</acl:menuAcl>
            
            //['領取年金者失蹤屆期通知家屬依法聲請死亡宣告案件清單', '',],
            //['已領年金給付金額明細資料', '',],
            //['已領老年給付證明', '',],
        //],
        //</acl:menuAcl>

        /*
        ['統計報表', '',
           ['勞保各項年金給付受理案件統計表', '',],
           ['勞保各項年金給付作業現況統計表', '',],
           ['勞保各項年金給付核付件數及金額統計表', '',],
           ['勞保年金各項給付補件案件統計表', '',],
           ['勞保各項年金給付抵銷紓困貸款件數及金額統計表', '',],
           ['勞保各項年金給付溢領案件統計表', '',],
           ['各類別被保險人請領老年年金給付件數及金額統計表', '',],
           ['各年齡被保險人請領老年年金給付付件數及金額統計表', '',],
           ['單位被保險人請領老年年金給付代扣補償金金額統計表', '',],
         ],
        */
        <acl:menuAcl itemId="BAAFAFAA">
        ['稽催管制相關報表　　　&#9658;', '',
            <acl:menuAcl itemId="BAAFAFAA">
            ['逾期未決行案件管制表', '<c:url value="/enterAuditRpt01.do?parameter=enterAuditRpt01"/>',],
            </acl:menuAcl>
        ],
        </acl:menuAcl>

        <acl:menuAcl itemId="BAAFAGAA,BAAFAGAB,BAAFAGAC,BAAFAGAD,BAAFAGAE,BAAFAGAF,BAAFAGAG,BAAFAGAH,BAAFAGAI,BAAFAGAJ,BAAFAGAK,BAAFAGAL,BAAFAGAM">
        ['其他類報表　　　　　　&#9658;', '',
            <acl:menuAcl itemId="BAAFAGAA">
            ['調卷/還卷單', '<c:url value="/enterOtherRpt01.do?parameter=enterOtherRpt01"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAGAB">
            ['補送在學證明通知函', '<c:url value="/enterMonthlyRpt29.do?parameter=enterMonthlyRpt29"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAGAD">
            ['批次核定函', '<c:url value="/enterOtherRpt05.do?parameter=enterOtherRpt05"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAGAC">
            ['受理案件統計表', '<c:url value="/enterOtherRpt04.do?parameter=enterOtherRpt04"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAGAE">
            ['轉催收核定清單', '<c:url value="/enterOtherRpt06.do?parameter=enterOtherRpt06"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAGAF">
            ['轉催收核定清冊', '<c:url value="/enterOtherRpt07.do?parameter=enterOtherRpt07"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAGAG">
            ['轉呆帳核定清單', '<c:url value="/enterOtherRpt08.do?parameter=enterOtherRpt08"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAGAH">
            ['轉呆帳核定清冊', '<c:url value="/enterOtherRpt09.do?parameter=enterOtherRpt09"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAGAI">
            ['穿透案件統計表', '<c:url value="/enterOtherRpt10.do?parameter=enterOtherRpt10"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAGAJ">
            ['查核失能程度通知函', '<c:url value="/enterMonthlyRpt30.do?parameter=enterMonthlyRpt30"/>',],
            </acl:menuAcl>
            
            ['老年年金通知函　　　　　&#9658;', '',
            	<acl:menuAcl itemId="BAAFAGAK">
            	['老年差額金通知','<c:url value="/enterMonthlyRpt31.do?parameter=enterMonthlyRpt31"/>',],
        		</acl:menuAcl>
            	<acl:menuAcl itemId="BAAFAGAL">
            	['老年差額金通知補印作業','<c:url value="/enterOtherRpt11.do?parameter=enterOtherRpt11"/>',],
        		</acl:menuAcl>
            	<acl:menuAcl itemId="BAAFAGAM">
            	['批次老年差額金通知列印作業','<c:url value="/enterOtherRpt12.do?parameter=enterOtherRpt12"/>',],
        		</acl:menuAcl>
            ],

        ],
        </acl:menuAcl>
        
        <acl:menuAcl itemId="BAAFAHAA,BAAFAHAB,BAAFAHAC,BAAFAHAD,BAAFAHAE,BAAFAHAF,BAAFAHAG">
        ['應收收回相關報表　　　&#9658;', '',
            <acl:menuAcl itemId="BAAFAHAA">
            ['已退匯或退回現金尚未沖轉收回案件清單', '<c:url value="/enterOtherRpt02.do?parameter=enterOtherRpt02"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAHAB">
            ['已退匯止付尚未應收款立帳案件清單', '<c:url value="/enterOtherRpt03.do?parameter=enterOtherRpt03"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAHAC">
            ['退匯/現應收已收核定清單', '<c:url value="/enterMonthlyRpt25.do?parameter=enterMonthlyRpt25"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAHAD">
            ['退匯/現應收已收清冊', '<c:url value="/enterMonthlyRpt26.do?parameter=enterMonthlyRpt26"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAHAE">
            ['退匯/現應收已收註銷核定清單', '<c:url value="/enterMonthlyRpt27.do?parameter=enterMonthlyRpt27"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAHAF">
            ['退匯/現應收已收註銷清冊', '<c:url value="/enterMonthlyRpt28.do?parameter=enterMonthlyRpt28"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAFAHAG">
            ['整批收回核定清單', '<c:url value="/enterBatchPaymentReceive.do?parameter=enterBatchPaymentReceive"/>',],
            </acl:menuAcl>            
        ],
        </acl:menuAcl>
    ],
    </acl:menuAcl>
    /*
    <acl:menuAcl itemId="BAAHAD,BAAHAE,BAAHAF,BAAHAA,BAAHAB,BAAHAC">
    ['複檢費用', null,
        <acl:menuAcl itemId="BAAHAD">
        ['複檢費用受理作業', '<c:url value="/enterReviewFeeReceipt.do?parameter=enterReviewFeeReceipt"/>',],
    	</acl:menuAcl>
    	<acl:menuAcl itemId="BAAHAE">
    	['複檢費用審核作業', '<c:url value="/enterReviewFeeReview.do?parameter=enterReviewFeeReview"/>',],
    	</acl:menuAcl>
    	<acl:menuAcl itemId="BAAHAF">
        ['複檢費用決行作業', '<c:url value="/enterReviewFeeDecision.do?parameter=enterReviewFeeDecision"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAHAA,BAAHAB,BAAHAC">
        ['複檢費用列印作業　　　　&#9658;','',
             <acl:menuAcl itemId="BAAHAA">
             ['複檢費用受理審核清單', '<c:url value="/enterReviewFeeReceiptRpt01.do?parameter=enterReviewFeeReceiptRpt01"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAHAB">
             ['複檢費用核定通知書', '<c:url value="/enterReviewFeeReceiptRpt02.do?parameter=enterReviewFeeReceiptRpt02"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAHAC">
             ['複檢費用核定清單', '<c:url value="/enterReviewFeeReceiptRpt03.do?parameter=enterReviewFeeReceiptRpt03"/>',],
             </acl:menuAcl>
       	],
        </acl:menuAcl>
    ],
    </acl:menuAcl>
    */
    <acl:menuAcl itemId="BAAGAA,BAAGAB">
    ['行政支援', null,
        <acl:menuAcl itemId="BAAGAA">
        ['行政支援記錄維護', '<c:url value="/enterExecutiveSupportMaint.do?parameter=enterExecutiveSupportMaint"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAGAB">
        ['行政支援記錄銷案', '<c:url value="/enterExecutiveSupportClose.do?parameter=enterExecutiveSupportClose"/>',],
        </acl:menuAcl>
    ],
    </acl:menuAcl>
    
    <acl:menuAcl itemId="BAAIAA,BAAIAB,BAAIAC,BAAIAD,BAAIAE,BAAIAF,BAAIAG,BAAIAH,BAAIAI,BAAIAJ,BAAIAK,BAAIAL,BAAIAM,BAAIAN,BAAIAO,BAAIAP">
    ['維護作業', null,
        <acl:menuAcl itemId="BAAIAA">
        ['編審註記維護作業', '<c:url value="/enterCheckMarkMaint.do?parameter=enterCheckMarkMaint"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAIAB">
        ['核定通知書維護作業', '<c:url value="/enterAdviceMaint.do?parameter=enterAdviceMaint"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAIAC">
        ['決行層級維護作業', '<c:url value="/enterDecisionLevelMaint.do?parameter=enterDecisionLevelMaint"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAIAD">
        ['先抽對象維護作業', '<c:url value="/enterPreviewConditionMaint.do?parameter=enterPreviewConditionMaint"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAIAE">
        ['扣減參數維護作業', '<c:url value="/enterDeductItemMaint.do?parameter=enterDeductItemMaint"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAIAF,BAAIAG">
        ['物價調整指數調整維護作業　&#9658;','',
             <acl:menuAcl itemId="BAAIAF">
             ['物價指數調整明細', '<c:url value="/enterCpiDtlMaint.do?parameter=enterCpiDtlMaint"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAIAG">
             ['物價指數調整紀錄', '<c:url value="/enterCpiRecMaint.do?parameter=enterCpiRecMaint"/>',],
             </acl:menuAcl>
       	],
        </acl:menuAcl>    
         <acl:menuAcl itemId="BAAIAH,BAAIAI,BAAIAJ">
        ['基本金額調整維護作業　&#9658;','',
             <acl:menuAcl itemId="BAAIAH">
             ['老年年金加計金額調整作業', '<c:url value="/enterBasicAmtMaint.do?parameter=enterBasicAmtMaint"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAIAI">
             ['失能年金基本金額調整作業', '<c:url value="/enterDisabledBasicAmtMaint.do?parameter=enterDisabledBasicAmtMaint"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAIAJ">
             ['遺屬年金基本金額調整作業', '<c:url value="/enterSurvivorBasicAmtMaint.do?parameter=enterSurvivorBasicAmtMaint"/>',],
             </acl:menuAcl>
       	],
        </acl:menuAcl>  
        <acl:menuAcl itemId="BAAIAK">
        ['勞保年金平均薪資月數參數維護修改作業', '<c:url value="/enterAvgAmtMonMaint.do?parameter=enterAvgAmtMonMaint"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAIAL,BAAIAM,BAAIAN">
        ['所得替代率參數維護作業　&#9658;','',
             <acl:menuAcl itemId="BAAIAL">
             ['老年年金所得替代率參數維護作業', '<c:url value="/enterReplacementRatioMaint.do?parameter=enterReplacementRatioMaint"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAIAM">
             ['失能年金所得替代率參數維護作業', '<c:url value="/enterDisabledReplacementRatioMaint.do?parameter=enterDisabledReplacementRatioMaint"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAIAN">
             ['遺屬年金所得替代率參數維護作業', '<c:url value="/enterSurvivorReplacementRatioMaint.do?parameter=enterSurvivorReplacementRatioMaint"/>',],
             </acl:menuAcl>
       	],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAIAO">
        ['重設案件給付參數資料', '<c:url value="/enterResetPaymentParameter.do?parameter=enterResetPaymentParameter"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAIAP">
        ['年金受理資料轉出', '<c:url value="/enterAnnuityAcceptDataTransfer.do?parameter=enterAnnuityAcceptDataTransfer"/>',],
        </acl:menuAcl>
    ],
    </acl:menuAcl>
    
    <acl:menuAcl itemId="BAAJAA,BAAJAB,BAAJAC,BAAJAD,BAAJAFA,BAAJAFB,BAAJAFC,BAAJAGA,BAAJAGB,BAAJAGC,BAAJAHA,BAAJAHB,BAAJAHC,BAAJAIA,BAAJAIB,BAAJAIC,BAAJAEAA,BAAJAEAB,BAAJAEAC,BAAJAJA,BAAJAJB,BAAJAJC,BAAJAKA,BAAJAKB,BAAJAKC,BAAJAM">
    ['批次處理', null,
        <acl:menuAcl itemId="BAAJAA">
        ['給付媒體回押註記', '<c:url value="/enterUpdatePaidMarkBJ.do?parameter=enterUpdatePaidMarkBJ"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAJAB">
        ['收回作業', '<c:url value="/enterReturnWriteOffBJ.do?parameter=enterReturnWriteOffBJ"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAJAC">
        ['轉催收作業', '<c:url value="/enterTrans2OverdueReceivableBJ.do?parameter=enterTrans2OverdueReceivableBJ"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAJAD">
        ['已收調整作業', '<c:url value="/enterReceivableAdjustBJ.do?parameter=enterReceivableAdjustBJ"/>',],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAJAFA,BAAJAFB,BAAJAFC">
        ['批次月處理作業　&#9658;','',
             <acl:menuAcl itemId="BAAJAFA">
             ['老年年金批次月處理作業', '<c:url value="/enterMonthBatchL.do?parameter=enterMonthBatchL"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAJAFB">
             ['失能年金批次月處理作業', '<c:url value="/enterMonthBatchK.do?parameter=enterMonthBatchK"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAJAFC">
             ['遺屬年金批次月處理作業', '<c:url value="/enterMonthBatchS.do?parameter=enterMonthBatchS"/>',],
             </acl:menuAcl>
       	],
        </acl:menuAcl>   
        <acl:menuAcl itemId="BAAJAGA,BAAJAGB,BAAJAGC">
        ['線上月試核作業　&#9658;','',
             <acl:menuAcl itemId="BAAJAGA">
             ['老年年金線上月試核作業', '<c:url value="/enterMonthCheckL.do?parameter=enterMonthCheckL"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAJAGB">
             ['失能年金線上月試核作業', '<c:url value="/enterMonthCheckK.do?parameter=enterMonthCheckK"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAJAGC">
             ['遺屬年金線上月試核作業', '<c:url value="/enterMonthCheckS.do?parameter=enterMonthCheckS"/>',],
             </acl:menuAcl>
       	],
        </acl:menuAcl>   
        <acl:menuAcl itemId="BAAJAHA,BAAJAHB,BAAJAHC">
        ['線上月核定作業　&#9658;','',
             <acl:menuAcl itemId="BAAJAHA">
             ['老年年金線上月核定作業', '<c:url value="/enterMonthL.do?parameter=enterMonthL"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAJAHB">
             ['失能年金線上月核定作業', '<c:url value="/enterMonthK.do?parameter=enterMonthK"/>',],
             </acl:menuAcl>
             <acl:menuAcl itemId="BAAJAHC">
             ['遺屬年金線上月核定作業', '<c:url value="/enterMonthS.do?parameter=enterMonthS"/>',],
             </acl:menuAcl>
       	],
        </acl:menuAcl> 
        <acl:menuAcl itemId="BAAJAIA,BAAJAIB,BAAJAIC">
		['批次產製媒體檔作業　&#9658;','',        
	        <acl:menuAcl itemId="BAAJAIA">
	        ['老年年金批次產製媒體檔作業', '<c:url value="/enterOldMediaBatch.do?parameter=enterOldMediaBatch"/>',],
	        </acl:menuAcl>
	        <acl:menuAcl itemId="BAAJAIB">
	        ['失能年金批次產製媒體檔作業', '<c:url value="/enterDisabledMediaBatch.do?parameter=enterDisabledMediaBatch"/>',],
	        </acl:menuAcl>
			<acl:menuAcl itemId="BAAJAIC">
	        ['遺屬年金批次產製媒體檔作業', '<c:url value="/enterSurvivorMediaBatch.do?parameter=enterSurvivorMediaBatch"/>',],
	        </acl:menuAcl>
		],
		</acl:menuAcl>
        <acl:menuAcl itemId="BAAJAEAA,BAAJAEAB,BAAJAEAC">
		['線上產製媒體檔作業　&#9658;','',        
	        <acl:menuAcl itemId="BAAJAEAA">
	        ['老年年金線上產製媒體檔作業', '<c:url value="/enterOldMediaOnline.do?parameter=enterOldMediaOnline"/>',],
	        </acl:menuAcl>
	        <acl:menuAcl itemId="BAAJAEAB">
	        ['失能年金線上產製媒體檔作業', '<c:url value="/enterDisabledMediaOnline.do?parameter=enterDisabledMediaOnline"/>',],
	        </acl:menuAcl>
			<acl:menuAcl itemId="BAAJAEAC">
	        ['遺屬年金線上產製媒體檔作業', '<c:url value="/enterSurvivorMediaOnline.do?parameter=enterSurvivorMediaOnline"/>',],
	        </acl:menuAcl>
		],
		</acl:menuAcl>  
		<acl:menuAcl itemId="BAAJAJA,BAAJAJB,BAAJAJC">
		['檢核作業　&#9658;','',        
	        <acl:menuAcl itemId="BAAJAJA">
	        ['產生檢核案件作業', '<c:url value="/enterCheckJobRpt.do?parameter=enterCheckJobRpt"/>',],
	        </acl:menuAcl>
	        <acl:menuAcl itemId="BAAJAJB">
	        ['檢核確認作業', '<c:url value="/enterCheckJob.do?parameter=enterCheckJob"/>',],
	        </acl:menuAcl>
	        <acl:menuAcl itemId="BAAJAJC">
	        ['檢核執行作業', '<c:url value="/enterCheckExec.do?parameter=enterCheckExec"/>',],
	        </acl:menuAcl>
		],
		</acl:menuAcl>
		<acl:menuAcl itemId="BAAJAKA,BAAJAKB,BAAJAKC">
		['批次排程作業　&#9658;','',        
	        <acl:menuAcl itemId="BAAJAKA">
	        ['批次程式設定作業', '<c:url value="/enterSetProcedure.do?parameter=enterSetProcedure"/>',],
	        </acl:menuAcl>
	        <acl:menuAcl itemId="BAAJAKB">
	        ['批次程式執行作業', '<c:url value="/enterRunProcedure.do?parameter=enterRunProcedure"/>',],
	        </acl:menuAcl>
	        <acl:menuAcl itemId="BAAJAKC">
	        ['批次程式查詢作業', '<c:url value="/enterQryProcedure.do?parameter=enterQryProcedure"/>',],
	        </acl:menuAcl>	        
		],
        </acl:menuAcl>
        <acl:menuAcl itemId="BAAJALAA,BAAJALAB,BAAJALAC">
        ['補正核付資料作業　&#9658;','',
            <acl:menuAcl itemId="BAAJALAA">
            ['老年年金補正核付作業', '<c:url value="/enterOldAgeAnnuityPayment.do?parameter=enterOldAgeAnnuityPayment"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAJALAB">
            ['失能年金補正核付作業', '<c:url value="/enterDisabledAnnuityPayment.do?parameter=enterDisabledAnnuityPayment"/>',],
            </acl:menuAcl>
            <acl:menuAcl itemId="BAAJALAC">
            ['遺屬年金補正核付作業', '<c:url value="/enterSurvivorAnnuityPayment.do?parameter=enterSurvivorAnnuityPayment"/>',],
            </acl:menuAcl>
        ],
        </acl:menuAcl>
		<acl:menuAcl itemId="BAAJAM">
	        ['年金統計執行作業', '<c:url value="/enterExecStatistics.do?parameter=enterExecStatistics"/>',],
	    </acl:menuAcl>
			
    ],
    </acl:menuAcl>
    
    <acl:menuAcl itemId="BAAKAA,BAAKAB,BAAKAC">
    ['繳款單作業', null,
		<acl:menuAcl itemId="BAAKAA">
	  		['繳款單維護作業', '<c:url value="/enterPaymentProcess.do?parameter=enterPaymentProcess.do"/>',],
	 	</acl:menuAcl>
	    <acl:menuAcl itemId="BAAKAB">
	    	  ['繳款單列印作業', '<c:url value="/enterPaymentReprint.do?parameter=enterPaymentReprint.do"/>',],
	    </acl:menuAcl>
	    <acl:menuAcl itemId="BAAKAC">
	        ['繳款單查詢作業', '<c:url value="/enterPaymentProgressQuery.do?parameter=enterPaymentProgressQuery.do"/>',],
	    </acl:menuAcl>	        
	],
	</acl:menuAcl>
    ['登出系統', '<c:url value="/logout.do"/>',]
]
</script>

<div id="systemMenu">
    <script type='text/javascript' src='<c:url value="/js/menubar.js"/>'></script>
</div>
