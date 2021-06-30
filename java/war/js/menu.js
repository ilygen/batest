// Product title: Gurt JavaScript Menu
// Product version details: 1.1, 01-25-2006 (mm-dd-yyyy)
// Product URL: http://gurtom.com/products/menus/js
// Contact info: gurt-feedback@gurtom.com (specify product title in subject line)
// Notes: This script is free. Feel free to copy, use and change this script as 
// long as this head part remains unchanged. Visit official site for details.
// Copyright: (c) 2006 by Gurtom.Com

var menus = [],
aMenu = window.external ? [' style="width:100%;height:100%"', 'div onclick="', 'div'] : ['', 'a href="javascript:', 'a'],
gMenuGetElem = document.getElementById
	? function(sId) { return document.getElementById(sId) }
	: function(sId) { return document.all[sId] };

function menu(aFields, oCfg) {
	var _ = this, i;
	_.aFields = aFields;
	_.oCfg = oCfg;
	_.sPath = '';
	_.id = menus.length;
	_.aAll = [];
	_.aKids = [];
	_.aOffset = [0, 0];
	
	_.oCfg[-1] = {
		'firstX' : 20,
		'firstY' : 20,
		'nextX' : 15,
		'nextY' : 15,
		'width' : 100,
		'height' : 22,
		'hideAfter' : 200,
		'target' : '_self',
		'trace' : 0,
		'css' : ''
	};
	
	for (i = 0; i < _.aFields.length; i++)
		if (_.aFields[i])
			new gMenuItem(0, _, _, _.aFields[i]);
	for (i = 0; i < _.aKids.length; i++)
		gItemShow(_.aKids[i], 1);
	menus[_.id] = _;
}

function gMenuHide (mId) {
	var i, a = menus[mId].aAll;
	for (i = 0; i < a.length; i++) {
		gItemShow(a[i], 0);
		gItemTrig(a[i],'norm');
	}
}

function gMenuOnclick (mId, iId) {
	var m = menus[mId], _ = m.aAll[iId].aFields[1];
	gItemTrig(m.aAll[iId], 'clck');
	if (_) {
        if(_.indexOf('newWindow=true') == -1)
            open(_.replace(/newWindow=true/i, ''), gItemCfg(m.aAll[iId].depth, m.oCfg, 'target'));
        else
            //open(_.replace(/newWindow=true/i, ''), '_'+(new Date()).getSeconds());
            window.open(_.replace(/newWindow=true/i, ''), '_'+(new Date()).getSeconds(),'toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=no,resizable=yes,width=1024,height=768');
    }
}

function gMenuOnmouseout (mId, iId) {
	var m = menus[mId];
	m.nHideTimer = setTimeout('gMenuHide('+ mId +')', gItemCfg(m.oActiveItem.depth, m.oCfg, 'hideAfter'));
	if (m.oActiveItem.id == iId)
		m.oActiveItem = null;
}

function gMenuOnmouseover (mId, iId) {
	var m = menus[mId], _, bVisib, i;
	m.oActiveItem = _ = m.aAll[iId];
	if (m.nHideTimer) clearTimeout(m.nHideTimer);
	
	for (i = 0; i < m.aAll.length; i++) {
		_ = m.aAll[i];
		bVisib = !m.oActiveItem.sPath.indexOf(_.sPathShort);
		if (bVisib)
			gItemTrig(_, _ == m.oActiveItem ? 'over' : 'norm');
		gItemShow(_, bVisib);
	}
	if (m.oActiveItem.bTrace)
		for (i = m.oActiveItem; i && i.aRefs; i = i.oParent)
			gItemTrig(i, 'over');
}

// --- menu item Class ---
function gMenuItem (l, p, m, aFields) {
	var _ = this, i, c = p.aKids.length, oCfg = m.oCfg;
	_.aFields = aFields;
	_.oParent = p;
	_.sPathShort = p.sPath;
	_.sPath = p.sPath + c + ':';
	_.depth = l;
	
	_.id = m.aAll.length;
	m.aAll[_.id] = _;
	p.aKids[c] = _;

	var id = m.id + ',' + _.id, nX, nY;
	_.bTrace = gItemCfg(l, oCfg, 'trace');
	
	for (i = l; i >= -1; i --) {
		if (oCfg[i] && oCfg[i]['nextX'] != null)
			nX = oCfg[i]['nextX'];
		if (oCfg[i] && oCfg[i]['nextY'] != null)
			nY = oCfg[i]['nextY'];
		if (nX != null || nY != null)
			break;
	}

	_.aOffset = [
		p.aOffset[0] + gItemCfg(l, oCfg, 'firstX') + (nX != null ? nX * c + gItemCfg(l, oCfg, 'width') * c : 0),
		p.aOffset[1] + gItemCfg(l, oCfg, 'firstY') + (nY != null ? nY * c + gItemCfg(l, oCfg, 'height') * c : 0)
	];
	
	document.write('<', aMenu[1], 'gMenuOnclick(', id, ')" id="me', id, '" style="position:absolute;top:', _.aOffset[1], 'px;left:', 
	               _.aOffset[0], 'px;width:', gItemCfg(l, oCfg, 'width'), 'px;height:', gItemCfg(l, oCfg, 'height'), 
	               'px;visibility:hidden;z-index:', l, ';text-decoration:none" onmouseout="gMenuOnmouseout(', id, 
	               ')" onmouseover="gMenuOnmouseover(', id, ')"><div', aMenu[0], ' id="mi', id, 
	               '" class="', gItemCfg(l, oCfg, 'css'), 'norm">', aFields[0], '</div></', aMenu[2], '>');
	_.aRefs = [gMenuGetElem('me' + m.id + ',' + _.id), gMenuGetElem('mi' + m.id + ',' + _.id), gItemCfg(l, oCfg, 'css')];

	if (aFields.length > 2) {
		_.aKids = [];
		for (i = 2; i < aFields.length; i++)
			if (aFields[i])
				new gMenuItem(l + 1, _, m, aFields[i]);
	}
}

function gItemShow(_, is_visible) {
	if (_.oVisible == is_visible) return;
	_.oVisible = is_visible;
	if (is_visible)
		_.aRefs[0].style.visibility = 'visible';
	else if (_.depth)
		_.aRefs[0].style.visibility = 'hidden';
}

function gItemTrig(_, nState) {
	if (_.aRefs[3] == nState) return;
	_.aRefs[3] = nState;
	_.aRefs[1].className = _.aRefs[2] + nState
}

function gItemCfg(l, oCfg, k) {
	for (var i = l; i >= -1; i --)
		if (oCfg[i] && oCfg[i][k] != null)
			return oCfg[i][k];
}
