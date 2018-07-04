<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="today" class="java.util.Date" />

<div id="container">
	<div class="content">
		<div class="sales_top">
			<div class="daily">
				<h2 class="title">날짜별 매출</h2>
				<select name="" class="sel_cal">
					<option value="year" <c:out value="${type eq 'year' ? 'selected=\"selected\"' : ''}" />>연도 별</option>
					<option value="month" <c:out value="${type eq 'month' ? 'selected=\"selected\"' : ''}" />>월 별</option>
					<option value="daily" <c:out value="${type eq 'daily' ? 'selected=\"selected\"' : ''}" />>일 별</option>
				</select>
				<div class="calendar">
					<h3 class="date"></h3>
					<p class="btn-box">
						<a href="#" class="btn-up"><i class="fas fa-angle-up"></i></a>
						<a href="#" class="btn-down"><i class="fas fa-angle-down"></i></a>
					</p>
					<div id="YearCalendar"></div>
					<div id="MonthCalendar"></div>
					<div id="DailyCalendar"></div>
				</div>
			</div>
			<div class="area">
				<h2 class="title">지역별 매출</h2>
				<div class="select scrollbar-outer">
					<c:forEach var="area" items="${areaList }">
						<label><input type="checkbox" name="area" value="${area }"/>${area }</label>
					</c:forEach>
				</div>
			</div>
			<form action="searchSale.do" method="post" name="frm_search">
				<input type="hidden" name="menu_code" value="${menu_code }" />
				<input type="hidden" name="type" />
				<input type="hidden" name="date" />
				<input type="hidden" name="areaList" />
			</form>
			<a href="#" class="btn_sale">보 기</a>
		</div>
		
		<div class="sales_list">
			<form action="search.do" method="post" name="frm_search2">
				<input type="hidden" name="menu_code" value="${menu_code }" />
				<input type="hidden" name="type" />
				<input type="hidden" name="date" />
				<input type="hidden" name="areaList" />
				<input type="hidden" name="companyList" />
				<input type="hidden" name="itemList" />
			</form>
			<table class="outer">
				<tr>
					<th width="70">
						<a href="javascript:show_comp()"><i class="far fa-caret-square-down"></i>상조</a>
						<div class="hide_box comp_box">
							<label><input type="checkbox" checked="checked" value="all"/>전체</label>
							<c:forEach var="company" items="${companyList }">
								<label><input type="checkbox" name="company" value="${company }"/>${company }</label>
							</c:forEach>
							<a href="javascript:go_searchOk('company')" class="btn-sm">확인</a>
						</div>
					</th>
					<th width="70">
						<a href="javascript:show_item()">품목<i class="far fa-caret-square-down"></i></a>
						<div class="hide_box item_box">
							<label><input type="checkbox" checked="checked" value="all"/>전체</label>
							<c:forEach var="item" items="${itemList }">
								<label><input type="checkbox" name="item" value="${item }"/>${item }</label>
							</c:forEach>
							<a href="javascript:go_searchOk('item')" class="btn-sm">확인</a>
						</div>
					</th>
					<c:choose>
					<c:when test="${empty itemCheckList or empty salesDataMap}">
						<th>품목 없음</th>
					</c:when>
					<c:otherwise>
						<c:forEach var="item" items="${itemCheckList }">
							<th>${item }</th>
						</c:forEach>
					</c:otherwise>
					</c:choose>
				</tr>
				<c:choose>
				<c:when test="${empty itemCheckList or empty salesDataMap}">
					<tr class="empty">
						<td colspan="3">조회된 데이터가 없습니다.</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="dataMap" items="${salesDataMap }" varStatus="status">
						<tr class="tr_list" data-target="${status.count }">
							<td colspan="2">${dataMap.key }<i class="fas fa-caret-down"></i></td>
							<c:forEach var="amount" items="${dataMap.value.amountList }">
								<td>${amount }</td>
							</c:forEach>
						</tr>
						<tr class="detail" id="target${status.count }">
							<td colspan="${fn:length(itemCheckList) + 2 }">
								<p class="inner_tit">* ${dataMap.key } 매출 상세정보</p>
								<table class="inner">
									<tr class="tr_info">
										<th>일자</th>
										<th>장례식장</th>
										<th>고인명</th>
										<th>호실</th>
										<th>팀장명</th>
									</tr>
									<c:forEach var="sales" items="${dataMap.value.salesList }">
										<tr class="tr_info">
											<td>${sales.salesDate }</td>
											<td>${sales.funeral }</td>
											<td>${sales.deadName }</td>
											<td>${sales.hosil }</td>
											<td>${sales.leader }</td>
										</tr>
										<tr class="tr_item">
											<th></th>
											<th>품목</th>
											<th>수량</th>
											<th>수익</th>
											<th>리베이트</th>
										</tr>
										<c:forEach var="det" items="${dataMap.value.salesDetMap[sales.salesNo] }">
											<tr class="tr_item">
												<td></td>
												<td>${det.itemName }</td>
												<td>${det.amount }</td>
												<td><fmt:formatNumber value="${det.profit }" pattern="#,##0" /></td>
												<td><fmt:formatNumber value="${det.rebate }" pattern="#,##0" /></td>
											</tr>
										</c:forEach>
									</c:forEach>
									<tr>
										<td colspan="5" class="inner_res">
											매출 <fmt:formatNumber value="${dataMap.value.profit }" pattern="#,##0" /> 원&nbsp;&nbsp;&nbsp;
											리베이트<fmt:formatNumber value="${dataMap.value.rebate }" pattern="#,##0" /> 원&nbsp;&nbsp;&nbsp;
											순수익 <fmt:formatNumber value="${dataMap.value.realProfit }" pattern="#,##0" /> 원
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</c:otherwise>
				</c:choose>
			</table>
			<c:if test="${!empty itemCheckList and !empty salesDataMap}">
				<div class="sales_res">
					<h3 class="res">
						총 매출<span class="sale_money"><fmt:formatNumber value="${totalProfit }" pattern="#,##0" /></span>&nbsp;-&nbsp;
						리베이트<span class="sale_money"><fmt:formatNumber value="${totalRebate }" pattern="#,##0" /></span>&nbsp;=&nbsp;
						순 수익<span class="sale_money"><fmt:formatNumber value="${totalRealProfit }" pattern="#,##0" /></span>
					</h3>
				</div>
			</c:if>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			var type = '${type}';
			var date = '${date}';
			var areaList = '${areaCheckList}';
			
			changeCalendar(type, date, 'no');
			var areaArr = areaList.split(', ');
			if(areaList == '') {
				$('input:checkbox[name=area]').prop('checked', true);
			} else {
				$.each(areaArr, function(index, value) {
					$('input:checkbox[name=area][value=' + value + ']').prop('checked', true);
				});
			}
			
			var companyCheckList = '${companyCheckList}';
			var companyArr = companyCheckList.split(', ');
			if(companyCheckList == '') {
				$('input:checkbox[name=company]').prop('checked', true);
			} else {
				$.each(companyArr, function(index, value) {
					$('input:checkbox[name=company][value="' + value + '"]').prop('checked', true);
				});
			}
			var itemCheckList = '${itemCheckList}';
			var itemArr = itemCheckList.replace('[', '').replace(']', '').split(', ');
			$.each(itemArr, function(index, value) {
				$('input:checkbox[name=item][value="' + value + '"]').prop('checked', true);
			});		
		});
	</script>
	<script>
		function show_comp() {
			$('.comp_box').toggle();
			$('.item_box').hide();
		}
		
		function show_item() {
			$('.comp_box').hide();
			$('.item_box').toggle();
		}
		
		$('.sales_list .outer .tr_list').click(function(){
			if($(this).hasClass('on')) {
				$(this).removeClass('on');
				$(this).find('td').first().find('.fas').addClass('fa-caret-down');
				$(this).find('td').first().find('.fas').removeClass('fa-caret-up');
				var target = $(this).data('target');
				$('#target' + target).fadeOut();
			} else {
				$(this).addClass('on');
				$(this).find('td').first().find('.fas').addClass('fa-caret-up');
				$(this).find('td').first().find('.fas').removeClass('fa-caret-down');
				var target = $(this).data('target');
				$('#target' + target).fadeIn();
			}
		});
		
		$('.btn_sale').click(function(event){
			event.preventDefault();
			var checked = false;
			var type = $('.sel_cal').find('option:selected').val();
			var date;
			if(type == 'year') {
				date = $('#YearCalendar td.on').text();
				if(date != '') checked = true;
			} else if (type == 'month') {
				date = $('#MonthCalendar caption').data('date') + '-';
				var month = $('#MonthCalendar td.on').text();
				month = month.length == 1 ? '0' + month : month;
				date += month;
				if(date.length == 7) checked = true;
			} else if (type == 'daily') {
				date = $('#DailyCalendar caption').data('date') + '-';
				var daily = $('#DailyCalendar td.on').text();
				daily = daily.length == 1 ? '0' + daily : daily;
				date += daily;
				if(date.length == 10) checked = true;
			}
			if(!checked) {
				alert('날짜를 선택해주세요.');
				return;
			}
			var area = $('input:checkbox[name=area]:checked');
			var areaList = [];
			$.each(area, function(index, value) {
				areaList.push(value.value);
			});
			if(areaList.length < 1) {
				alert('지역을 하나 이상 선택해주세요.');
				return;
			}
			document.frm_search.type.value = type;
			document.frm_search.date.value = date;
			document.frm_search.areaList.value = areaList;
			document.frm_search.submit();
		});
		
		$('.hide_box input:checkbox[value=all]').change(function(){
			var checked = $(this).prop('checked');
			if(checked) {
				$(this).parents('.hide_box').find('input:checkbox').prop('checked', true);
			} else {
				$(this).parents('.hide_box').find('input:checkbox').prop('checked', false);
			}
		});
		
		function go_searchOk(gubun) {
			var frm = document.frm_search2;
			var type = '${type}';
			var date = '${date}';
			var areaCheckList = '${areaCheckList}';
			var companyList = [];
			var itemList = [];
			if(gubun == 'company') {
				var chk_comp = $('input:checkbox[name=company]:checked');
				$.each(chk_comp, function(index, value) {
					companyList.push(value.value);
				});
				if(companyList.length < 1) {
					alert('하나 이상 선택해주세요.');
					return;
				}
				frm.companyList.value = companyList;
				var itList = '${itemCheckList}';
				itList = itList.replace('[', '').replace(']', '').split(', ');
				frm.itemList.value = itList;
			} else if (gubun == 'item') {
				var chk_item = $('input:checkbox[name=item]:checked');
				$.each(chk_item, function(index, value) {
					itemList.push(value.value);
				});
				if(itemList.length < 1) {
					alert('하나 이상 선택해주세요.');
					return;
				}
				frm.itemList.value = itemList;
				var comp = '${companyCheckList}';
				if(comp != '')
					frm.companyList.value = comp.split(', ');
			}
			frm.type.value = type;
			frm.date.value = date;
			frm.areaList.value = areaCheckList;
			frm.submit();
		}
	</script>
	
	<script>
		$('.sel_cal').change(function() {
			var val = $(this).find('option:selected').val();
			changeCalendar(val);
		});
		function changeCalendar(val, date, res) {
			var outTime = (res == 'no' ? 0 : 100);
			var inTime = (res == 'no' ? 0 : 1000);
			if(val == 'year') {
				$('#MonthCalendar, #DailyCalendar').fadeOut(outTime, function(){
					yearCalendar('YearCalendar', '', date, date);
					$('#YearCalendar').fadeIn(inTime);
				});
			} else if (val == 'month') {
				$('#YearCalendar, #DailyCalendar').fadeOut(outTime, function(){
					monthCalendar('MonthCalendar', '', date, date);
					$('#MonthCalendar').fadeIn(inTime);
				});
			} else if (val == 'daily') {
				$('#YearCalendar, #MonthCalendar').fadeOut(outTime, function(){
					dailyCalendar('DailyCalendar', '', date, date);
					$('#DailyCalendar').fadeIn(inTime);
				});
			}
		}
	</script>
</div>