<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="container">
	<div class="content">
		<div class="history">
			<div class="sales_top">
				<div class="daily" style="margin-right: 0;">
					<h2 class="title">날짜별 검색</h2>
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
				<form action="searchHistory.do" method="post" name="frm_search">
					<input type="hidden" name="menu_code" value="${menu_code }" />
					<input type="hidden" name="type" />
					<input type="hidden" name="date" />
				</form>
				<a href="#" class="btn_sale">보 기</a>
			</div>
			<ul>
				<c:choose>
				<c:when test="${empty historyList }">
					<li>* 작업 내역이 없습니다.</li>
				</c:when>
				<c:otherwise>
					<c:forEach var="history" items="${historyList }">
						<li>[ <fmt:formatDate value="${history.historyDate}" pattern="yyyy-MM-dd HH:mm:ss"/> ]&nbsp;&nbsp;&nbsp;${history.historyText}</li>
					</c:forEach>
				</c:otherwise>
				</c:choose>
			</ul>
		</div>
	</div>
	<script>
		$(document).ready(function(){
			var type = '${type}';
			var date = '${date}';
			
			changeCalendar(type, date, 'no');
		});
		
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
			document.frm_search.type.value = type;
			document.frm_search.date.value = date;
			document.frm_search.submit();
		});
	</script>
</div>