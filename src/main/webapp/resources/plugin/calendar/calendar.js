/* Kurien / Kurien's Blog / http://blog.kurien.co.kr */
/* 주석만 제거하지 않는다면, 어떤 용도로 사용하셔도 좋습니다. */

function kCalendar(id, date) {
	var kCalendar = document.getElementById(id);
	
	if( typeof( date ) !== 'undefined' ) {
		date = date.split('-');
		date[1] = date[1] - 1;
		date = new Date(date[0], date[1], date[2]);
	} else {
		var date = new Date();
	}
	var currentYear = date.getFullYear();
	//년도를 구함
	
	var currentMonth = date.getMonth() + 1;
	//연을 구함. 월은 0부터 시작하므로 +1, 12월은 11을 출력
	
	var currentDate = date.getDate();
	//오늘 일자.
	
	date.setDate(1);
	var currentDay = date.getDay();
	//이번달 1일의 요일은 출력. 0은 일요일 6은 토요일
	
	var dateString = new Array('sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat');
	var lastDate = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	if( (currentYear % 4 === 0 && currentYear % 100 !== 0) || currentYear % 400 === 0 )
		lastDate[1] = 29;
	//각 달의 마지막 일을 계산, 윤년의 경우 년도가 4의 배수이고 100의 배수가 아닐 때 혹은 400의 배수일 때 2월달이 29일 임.
	
	var currentLastDate = lastDate[currentMonth-1];
	var week = Math.ceil( ( currentDay + currentLastDate ) / 7 );
	//총 몇 주인지 구함.
	
	if(currentMonth != 1)
		var prevDate = currentYear + '-' + ( currentMonth - 1 ) + '-' + currentDate;
	else
		var prevDate = ( currentYear - 1 ) + '-' + 12 + '-' + currentDate;
	//만약 이번달이 1월이라면 1년 전 12월로 출력.
	
	if(currentMonth != 12) 
		var nextDate = currentYear + '-' + ( currentMonth + 1 ) + '-' + currentDate;
	else
		var nextDate = ( currentYear + 1 ) + '-' + 1 + '-' + currentDate;
	//만약 이번달이 12월이라면 1년 후 1월로 출력.

	
	if( currentMonth < 10 )
		var currentMonth = '0' + currentMonth;
	//10월 이하라면 앞에 0을 붙여준다.
	
	var calendar = '';
	
	calendar += '<div id="header">';
	calendar += '			<span><a href="javascript:kCalendar(\'' +  id + '\', \'' + prevDate + '\')" class="button left"><i class="fas fa-angle-left"></i></a></span>';
	calendar += '			<span id="date">' + currentYear + '년 ' + currentMonth + '월</span>';
	calendar += '			<span><a href="javascript:kCalendar(\'' +  id + '\', \'' + nextDate + '\')" class="button right"><i class="fas fa-angle-right"></i></a></span>';
	calendar += '		</div>';
	calendar += '		<table border="0" cellspacing="0" cellpadding="0">';
	calendar += '			<caption data-date="' + currentYear + '-' + currentMonth + '">' + currentYear + '년 ' + currentMonth + '월 달력</caption>';
	calendar += '			<thead>';
	calendar += '				<tr>';
	calendar += '				  <th class="sun" scope="row">일</th>';
	calendar += '				  <th class="mon" scope="row">월</th>';
	calendar += '				  <th class="tue" scope="row">화</th>';
	calendar += '				  <th class="wed" scope="row">수</th>';
	calendar += '				  <th class="thu" scope="row">목</th>';
	calendar += '				  <th class="fri" scope="row">금</th>';
	calendar += '				  <th class="sat" scope="row">토</th>';
	calendar += '				</tr>';
	calendar += '			</thead>';
	calendar += '			<tbody>';
	
	var dateNum = 1 - currentDay;
	
	var today = new Date();
	var todayYear = today.getFullYear();
	var todayMonth = today.getMonth() + 1;
	todayMonth = (todayMonth + '').length == 1 ? '0' + todayMonth : todayMonth;
	for(var i = 0; i < week; i++) {
		calendar += '			<tr>';
		for(var j = 0; j < 7; j++, dateNum++) {
			if( dateNum < 1 || dateNum > currentLastDate ) {
				calendar += '				<td class="' + dateString[j] + '"> </td>';
				continue;
			}
			if(currentYear == todayYear && currentMonth == todayMonth && currentDate == dateNum)
				calendar += '				<td class="' + dateString[j] + ' on">' + dateNum + '</td>';
			else
				calendar += '				<td class="' + dateString[j] + '">' + dateNum + '</td>';
		}
		calendar += '			</tr>';
	}
	
	calendar += '			</tbody>';
	calendar += '		</table>';
	
	kCalendar.innerHTML = calendar;
	clickEvent();
}

function clickEvent() {
	$('#kCalendar td').on('click', function(){
		var date = $('#kCalendar caption').data('date') + '-';
		date += ($(this).text().length == 1) ? '0' + $(this).text() : $(this).text();
		getSalesByDate(date);
		$('#kCalendar td').removeClass('on');
		$(this).addClass('on');
	});
}

function getSalesByDate(date) {
	$.ajax({
		url: 'getSalesByDate.do',
		type: 'post',
		data: {'date': date},
		dataType: 'json',
		success: function(data) {
			var html = '';
			html = '<span>' + date + '</span>영업한 상조';
			$target = $('.step03 .bottom');
			$target.find('.tit').html(html);
			html = '';
			html += '<ul>';
			$.each(data, function(index, value) {
				var text = value.companyName + "<br/>(" + value.funeral + ", " + value.deadName + ")";
				html += '<li><a href="javascript:clickCompany(\'' + value.salesNo + '\')">' + text + '</a></li>';
			});
			html += '</ul>';
			$target.find('.list').html(html);
		},
		error: function() {
			alert('error');
		}
	});
}

function clickCompany(salesNo) {
	$.ajax({
		url:'getSales.do',
		type: 'post',
		data: {'salesNo': salesNo},
		dataType: 'json',
		success: function(data) {
			var frm = document.frm_reg;
			frm.salesNo.value = salesNo;
			$('.btn_final').text('영업 수정');
			$('.btn_mod').css('display', 'inline-block');
			$.each(data, function(key, value) {
				$('.sel_area').val(getValue(key, 'areaName')).prop('selected', true);
				$('.sel_comp').val(getValue(key, 'companyName')).prop('selected', true);
				$('#funeral').val(getValue(key, 'funeral'));
				$('#deadName').val(getValue(key, 'deadName'));
				$('#hosil').val(getValue(key, 'hosil'));
				$('#leader').val(getValue(key, 'leader'));
				
				var html = '';
				var count = 1;
				$('.item_final').html('');
				html += '<table><tbody>';
				html += '	<tr>';
				html += '		<th width="40">번호</th>';      
				html += '		<th width="*">품목</th>';       
				html += '		<th width="40">수량</th>';      
				html += '		<th width="105">수익</th>';     
				html += '		<th width="105">리베이트</th>';  
				html += '		<th width="60"></th>';        
				html += '	</tr>';
			    html += '</tbody></table>';
				$('.item_final').html(html);
				$.each(value, function(idx, val) {
					var itemName = val.itemName;
					var amount = val.amount;
					var profit = val.profit;
					var rebate = val.rebate;
					
					html = '';
					html += '<tr>'
					html += '	<td>' + count + '</td>'
					html += '	<td>' + itemName + '</td>'
					html += '	<td>' + amount + '</td>'
					html += '	<td>' + comma(profit) + ' 원</td>'
					html += '	<td>' + comma(rebate) + ' 원</td>'
					html += '	<td><a href="#" class="btn_remove">삭제</a></td>'
					html += '</tr>'
					$('.item_final table tbody').append(html);
					removeEvent();
					
					html = '';
					html += '<div id="itemHide' + count + '" class="itemHide">';
					html += '	<input type="hidden" name="itemName" value="' + itemName + '" />'
					html += '	<input type="hidden" name="amount" value="' + amount + '" />'
					html += '	<input type="hidden" name="profit" value="' + profit + '" />'
					html += '	<input type="hidden" name="rebate" value="' + rebate + '" />'
					html += '</div>';
					$('.item_final').append(html);
					count ++;
				});
			})
		},
		error: function() {
			alert('error');
		}
	});
}

function getValue(map, key) {
	var split = map.split(key)[1]
	var idx1 = split.indexOf('=');
	var idx2 = split.indexOf(',');
	return split.substr(idx1+1, idx2-1);
}

function yearCalendar(id, year, defaultDate, select) {
	var kCalendar = document.getElementById(id);
	
	var currentYear = year;
	//년도를 구함
	
	if( typeof( year ) === 'undefined' || year == '') {
		currentYear = new Date().getFullYear();
	} 
	if( defaultDate != '' && defaultDate != undefined ) {
		currentYear = defaultDate;
	}
	if( select == '' || select == undefined ) {
		select = new Date().getFullYear();
	}
	var firstYear = currentYear.toString().slice(0, -1) + '0';
	var lastYear = currentYear.toString().slice(0, -1) + '9';
	
	var calendar = '';
	
	calendar += '<table border="0" cellspacing="0" cellpadding="0">';
	calendar += '	<caption data-date="' + firstYear + '~' + lastYear + '">' + firstYear + '~' + lastYear + '년</caption>';
	calendar += '	<tbody>';
	
	var today = new Date();
	var dateYear = firstYear;
	for(var i = 0; i < 4; i++) {
		calendar += '	<tr>';
		for(var j = 0; j < 3; j++) {
			if(dateYear > lastYear) break;
			if(dateYear == select)
				calendar += '		<td class="on">' + dateYear + '</td>';
			else 
				calendar += '		<td>' + dateYear + '</td>';
			dateYear ++;
		}
		calendar += '	</tr>';
	}
	calendar += ' 	</tbody>';
	calendar += '</table>';
	
	kCalendar.innerHTML = calendar;

	YearClickEvent();
	$('.calendar .date').text(firstYear + ' - ' + lastYear + ' 년');
	
	$('.calendar .btn-up, .calendar .btn-down').attr('onclick', '').unbind('click');
	if(firstYear > today.getFullYear() - 50) {
		$('.calendar .btn-up').click(function(event) {
			event.stopImmediatePropagation();
			event.preventDefault();
			yearCalendar(id, parseInt(firstYear)-1, '', select);
		});
	}
	
	if(lastYear < today.getFullYear() + 50) {
		$('.calendar .btn-down').click(function(event) {
			event.stopImmediatePropagation();
			event.preventDefault();
			yearCalendar(id, parseInt(lastYear)+1, '', select);
		});
	}
}

function YearClickEvent() {
	$('#YearCalendar td').on('click', function(){
		$('#YearCalendar td').removeClass('on');
		$(this).addClass('on');
	});
}

function monthCalendar(id, year, defaultDate, select) {
	var kCalendar = document.getElementById(id);
	
	var currentYear = year;
	//년도를 구함
	
	var currentMonth;
	if( typeof( year ) === 'undefined' || year == '' ) {
		currentYear = new Date().getFullYear();
		currentMonth = new Date().getMonth + 1;
	} 
	if( defaultDate != '' && defaultDate != undefined) {
		currentYear = defaultDate.split('-')[0];
		currentMonth = defaultDate.split('-')[1];
	}
	if( select == '' || select == undefined ) {
		var year = new Date().getFullYear();
		var month = new Date().getMonth() + 1;
		month = (month+'').length == 1 ? '0' + month : month;
		select =  year + '-' + month;
	}
	var calendar = '';
	
	calendar += '<table border="0" cellspacing="0" cellpadding="0">';
	calendar += '	<caption data-date="' + currentYear + '">' + currentYear + '년 </caption>';
	calendar += '	<tbody>';
	
	var dateMonth = 1;
	var today = new Date();
	var selectYear = select.split('-')[0];
	var selectMonth = select.split('-')[1];
	for(var i = 0; i < 4; i++) {
		calendar += '	<tr>';
		for(var j = 0; j < 3; j++) {
			if(currentYear == selectYear && dateMonth == selectMonth)
				calendar += '		<td class="on">' + dateMonth + '</td>';
			else 
				calendar += '		<td>' + dateMonth + '</td>';
			dateMonth ++;
		}
		calendar += '	</tr>';
	}
	
	calendar += ' 	</tbody>';
	calendar += '</table>';
	
	kCalendar.innerHTML = calendar;

	MonthClickEvent();
	$('.calendar .date').text(currentYear + '년 ');
	
	$('.calendar .btn-up, .calendar .btn-down').attr('onclick', '').unbind('click');
	$('.calendar .btn-up').click(function(event) {
		event.stopImmediatePropagation();
		event.preventDefault();
		monthCalendar(id, parseInt(currentYear)-1, '', select);
	});
	$('.calendar .btn-down').click(function(event) {
		event.stopImmediatePropagation();
		event.preventDefault();
		monthCalendar(id, parseInt(currentYear)+1, '', select);
	});
}

function MonthClickEvent() {
	$('#MonthCalendar td').on('click', function(){
		$('#MonthCalendar td').removeClass('on');
		$(this).addClass('on');
	});
}

function dailyCalendar(id, date, defaultDate, select) {
	var kCalendar = document.getElementById(id);

	if( typeof( date ) !== 'undefined' && date != '' ) {
		date = date.split('-');
		date[1] = date[1] - 1;
		date = new Date(date[0], date[1], date[2]);
	} else {
		var date = new Date();
	}
	if( defaultDate != '' && defaultDate != undefined) {
		date = defaultDate.split('-');
		date[1] = date[1] - 1;
		date = new Date(date[0], date[1], date[2]);
	}
	if( select == '' || select == undefined ) {
		var year = new Date().getFullYear();
		var month = new Date().getMonth() + 1;
		var day = new Date().getDate();
		month = (month+'').length == 1 ? '0' + month : month;
		day = (day+'').length == 1 ? '0' + day : day;
		select =  year + '-' + month + '-' + day;
	}
	var currentYear = date.getFullYear();
	//년도를 구함
	
	var currentMonth = date.getMonth() + 1;
	//연을 구함. 월은 0부터 시작하므로 +1, 12월은 11을 출력
	
	var currentDate = date.getDate();
	//오늘 일자.
	
	date.setDate(1);
	var currentDay = date.getDay();
	//이번달 1일의 요일은 출력. 0은 일요일 6은 토요일
	
	var dateString = new Array('sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat');
	var lastDate = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	if( (currentYear % 4 === 0 && currentYear % 100 !== 0) || currentYear % 400 === 0 )
		lastDate[1] = 29;
	//각 달의 마지막 일을 계산, 윤년의 경우 년도가 4의 배수이고 100의 배수가 아닐 때 혹은 400의 배수일 때 2월달이 29일 임.
	
	var currentLastDate = lastDate[currentMonth-1];
	var week = Math.ceil( ( currentDay + currentLastDate ) / 7 );
	//총 몇 주인지 구함.
	
	if(currentMonth != 1)
		var prevDate = currentYear + '-' + ( currentMonth - 1 ) + '-' + currentDate;
	else
		var prevDate = ( currentYear - 1 ) + '-' + 12 + '-' + currentDate;
	//만약 이번달이 1월이라면 1년 전 12월로 출력.
	
	if(currentMonth != 12) 
		var nextDate = currentYear + '-' + ( currentMonth + 1 ) + '-' + currentDate;
	else
		var nextDate = ( currentYear + 1 ) + '-' + 1 + '-' + currentDate;
	//만약 이번달이 12월이라면 1년 후 1월로 출력.

	
	if( currentMonth < 10 )
		var currentMonth = '0' + currentMonth;
	//10월 이하라면 앞에 0을 붙여준다.
	
	var calendar = '';
	
	calendar += '<table border="0" cellspacing="0" cellpadding="0">';
	calendar += '	<caption data-date="' + currentYear + '-' + currentMonth + '">' + currentYear + '년 ' + currentMonth + '월 달력</caption>';
	calendar += '	<thead>';
	calendar += '		<tr>';
	calendar += '		  <th class="sun" scope="row">일</th>';
	calendar += '		  <th class="mon" scope="row">월</th>';
	calendar += '		  <th class="tue" scope="row">화</th>';
	calendar += '		  <th class="wed" scope="row">수</th>';
	calendar += '		  <th class="thu" scope="row">목</th>';
	calendar += '		  <th class="fri" scope="row">금</th>';
	calendar += '		  <th class="sat" scope="row">토</th>';
	calendar += '		</tr>';
	calendar += '	</thead>';
	calendar += '	<tbody>';
	
	var dateNum = 1 - currentDay;
	var today = new Date();
	var selectYear = select.split('-')[0];
	var selectMonth = select.split('-')[1];
	var selectDay = select.split('-')[2];
	for(var i = 0; i < week; i++) {
		calendar += '	<tr>';
		for(var j = 0; j < 7; j++, dateNum++) {
			if( dateNum < 1 || dateNum > currentLastDate ) {
				calendar += '		<td class="' + dateString[j] + ' empty"> </td>';
				continue;
			}
			if(currentYear == selectYear && currentMonth == selectMonth && selectDay == dateNum) 
				calendar += '		<td class="' + dateString[j] + ' on">' + dateNum + '</td>';
			else
				calendar += '		<td class="' + dateString[j] + '">' + dateNum + '</td>';
		}
		calendar += '	</tr>';
	}
	
	calendar += ' 	</tbody>';
	calendar += '</table>';
	
	kCalendar.innerHTML = calendar;
	DailyClickEvent();
	
	$('.calendar .date').text(currentYear + '년 ' + currentMonth + '월');
	$('.calendar .btn-up, .calendar .btn-down').attr('onclick', '').unbind('click');
	$('.calendar .btn-up').click(function(event) {
		event.stopImmediatePropagation();
		event.preventDefault();
		dailyCalendar(id, prevDate, '', select);
	});
	$('.calendar .btn-down').click(function(event) {
		event.stopImmediatePropagation();
		event.preventDefault();
		dailyCalendar(id, nextDate, '', select);
	});
}

function DailyClickEvent() {
	$('#DailyCalendar td').on('click', function(){
		if($(this).hasClass('empty')) return;
		$('#DailyCalendar td').removeClass('on');
		$(this).addClass('on');
	});
}