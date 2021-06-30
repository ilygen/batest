// 系統標題和選單的高度
var bannerHeight = 96;
// 系統選單列的高度
var menuHeight = 26;
// 訊息區的高度
var sysMsgHeight = 30;
// Offset
var contentOffset = 4;
// 游標停在輸入欄位時的背景顏色
var inputFocusBackgroundColor = "#F6F698";
// 游標離開輸入欄位時的背景顏色
var inputBlurBackgroundColor = "#FFFFFF";

function resizeContent() {
    var myWidth = 0, myHeight = 0;

    if( typeof( window.innerWidth ) == 'number' ) {
        //Non-IE
        myWidth = window.innerWidth;
        myHeight = window.innerHeight;
    } else if( document.documentElement && ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
        //IE 6+ in 'standards compliant mode'
        myWidth = document.documentElement.clientWidth;
        myHeight = document.documentElement.clientHeight;
    } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
        //IE 4 compatible
        myWidth = document.body.clientWidth;
        myHeight = document.body.clientHeight;
    }

    if (myHeight > (bannerHeight + menuHeight + sysMsgHeight + contentOffset)) {
        if ($("main")!=null) {
            $("main").style.height = myHeight - (bannerHeight + menuHeight + sysMsgHeight + contentOffset) + "px";
        }

        if ($("main2")!=null) {
            $("main2").style.height =  myHeight - (bannerHeight + menuHeight + sysMsgHeight + contentOffset) + "px";
        }

        if ($("footer")!=null) {
            $("footer").style.height = sysMsgHeight + "px";
        }
    }
}

// 進入頁面後, 游標停在第一筆輸入欄位
function inputFieldFocus() {
    var inputFieldList = document.getElementsByTagName("input");
    for (i = 0; i < inputFieldList.length; i++) {
        var inputObj = inputFieldList[i];
        var inptuType = String(inputObj.getAttribute('type'));
        if (inptuType.toLowerCase().match('text')) {
            if (inputObj.readOnly != true && inputObj.disabled != true && inputObj.style.display != "none" && inputObj.style.visibility != "hidden") {
                try {
                    inputObj.style.background = inputFocusBackgroundColor; // for Firefox
                    inputObj.focus();
                    break;
                }
                catch(e) {
                    continue;
                }
            }
        }
    }
}

// 輸入欄位相關處理
// Begin ... [
function inputFieldInit() {
    var inputFieldList = document.getElementsByTagName("input");
    for (i = 0; i < inputFieldList.length; i++) {
        var inputObj = inputFieldList[i];
        var inptuType = String(inputObj.getAttribute('type'));
        if (inptuType.toLowerCase().match('text')) {
            // 游標 focus / blur 時, 輸入欄位變色
            Event.observe(inputObj, 'focus', focusBackgroundColor.bindAsEventListener(), false);
            Event.observe(inputObj, 'blur', blurBackgroundColor.bindAsEventListener(), false);
            Event.observe(inputObj, 'keydown', disableEnterKey.bindAsEventListener(), false);
        }
    }
}

// 游標停在輸入欄位時改變背景顏色
function focusBackgroundColor(e) {
    var elementObj = Event.element(e);
    elementObj.style.background = inputFocusBackgroundColor;
    
    var inptuType = String(elementObj.getAttribute('type'));
    if (inptuType.toLowerCase().match('text')) {
        // 選取欄位的內容, 方便修改
        elementObj.select();
    }
}

// 游標離開輸入欄位時重置背景色
function blurBackgroundColor(e) {
    var elementObj = Event.element(e);
    elementObj.style.background = inputBlurBackgroundColor;
}

function disableEnterKey(e) {
    var key;
    if(window.event)
        key = window.event.keyCode; //IE
    else
        key = e.which; //firefox

    if (key == 13) {
        if(window.event)
            event.keyCode = 35;

        return false;
    }
    else {
        return true;
    }

}
// ] ... End
// 輸入欄位相關處理


function formLocker() {

    window.onBeforeUnload = beforeUnload;
}

function beforeUnload() {
    createDisableZone();
}

function createDisableZone() {
    var bodyObj = document.getElementsByTagName("body").item(0);
    
    var disabledZone = document.getElementById('disabledZone');
    
    if (!disabledZone)
    {
        disabledZone = document.createElement('div');
        disabledZone.setAttribute('id', 'disabledZone');

        disabledZone.style.position = "absolute";
        disabledZone.style.zIndex = "999999";
        disabledZone.style.left = "0px";
        disabledZone.style.top = "0px";
        disabledZone.style.width = "100%";
        disabledZone.style.height = "100%";

        var messageZone = document.createElement('div');
        messageZone.setAttribute('id', 'messageZone');
        messageZone.style.position = "absolute";
        messageZone.style.bottom = "0px";
        messageZone.style.right = "0px";
        messageZone.style.background = "red";
        messageZone.style.color = "white";
        messageZone.style.fontFamily = "新細明體,細明體,Arial,Helvetica,sans-serif";
        messageZone.style.padding = "4px";
        messageZone.style.border="1px solid #000000";
        var text = document.createTextNode("處理中, 請稍候...");
        messageZone.appendChild(text);

        disabledZone.appendChild(messageZone);
        bodyObj.appendChild(disabledZone);
        
        // disable All button
        var inputObjList = document.getElementsByTagName("input");
        for (i = 0; i < inputObjList.length; i++) {
            var inputObj = inputObjList[i];
            if (inputObj.type == "button" || inputObj.type == "submit" || inputObj.type == "reset")
                inputObj.disabled = true;
        }
    }
    else
    {
        document.getElementById('messageZone').innerHTML = "處理中, 請稍候...";
        disabledZone.style.display = 'block';
        disabledZone.style.visibility = 'visible';
    }
}

// for Layout
Event.observe(window, 'load', resizeContent, false);
Event.observe(window, 'resize', resizeContent, false);

// for Input
Event.observe(window, 'load', inputFieldFocus, false);
Event.observe(window, 'load', inputFieldInit, false);

Event.observe(window, 'beforeunload', beforeUnload, false);

