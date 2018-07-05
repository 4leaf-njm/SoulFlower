<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="container">
	<div class="content">
		<div style="font-size: 0;">
			<form method="post" name="frm_reg">
				<input type="hidden" name="salesNo" value="0" />
				<div class="section step01">
					<h3><span class="txt_step">STEP 01</span>지역 선택</h3>
					<div class="scrollbar-outer" style="width: 150px; height: 250px; margin: 0 auto;">
						<select id="areaName" class="sel_area" name="areaName" size="6" onchange="">
							<option value="" selected>-----------</option>
							<c:forEach var="area" items="${areaList }">
								<option value="${area.areaName}">${area.areaName}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="section step02">
					<h3><span class="txt_step">STEP 02</span>날짜 선택</h3>
					<input type="hidden" name="salesDate" />
					<div id="kCalendar"></div>
				</div>
				<div class="section step03">
					<h3><span class="txt_step">STEP 03</span>상조회사 선택</h3>
					<label class="lbl_search"><i class="fas fa-search"></i><input type="text" class="txt_search" /></label>
					<div class="search_list"></div>
					<select id="companyName" class="sel_comp" name="companyName" onchange="">
						<option value="" selected>----</option>
						<c:forEach var="company" items="${companyList }">
							<option value="${company.companyName}">${company.companyName}</option>
						</c:forEach>
					</select>
					<div class="bottom">
						<p class="tit"></p>
						<div class="list scrollbar-outer"></div>
					</div>
				</div>
				<div class="section step04">
					<h3><span class="txt_step">STEP 04</span>영업정보 입력</h3>
					<div class="input_box">
						<div class="row">
							<div class="col-lg">
								<label for="funeral">장례식장</label>
								<input type="text" name="funeral" id="funeral" />
							</div>
						</div>
						<div class="row">
							<div class="col-md">
								<label for="deadName">고인명</label>
								<input type="text" name="deadName" id="deadName" />
							</div>
							<div class="col-md">
								<label for="hosil">&nbsp;호&nbsp;&nbsp;실</label>
								<input type="text" name="hosil" id="hosil" />
							</div>
						</div>
						<div class="row">
							<div class="col-lg">
								<label for="leader">팀장명</label>
								<input type="text" name="leader" id="leader" />
							</div>
						</div>
					</div>
				</div>
				<div class="section step05">
					<h3><span class="txt_step">STEP 05</span>품목 등록</h3>
					<div class="item_wrap scrollbar-outer">
						<table>
							<tr>
								<th>품목</th>
								<th>가격</th>
								<th>수량</th>
								<th>리베이트 설정</th>
								<th width="65px">수익</th>
								<th width="65px">리베이트</th>
								<th></th>
								<th></th>
							</tr>
							<c:forEach begin="0" end="2" varStatus="status">
								<tr class="item${status.count }">
									<td>
										<select class="sel_item">
											<option value="">--</option>
											<c:forEach var="item" items="${itemList }">
												<option value="${item.itemName}" data-no="${item.itemNo}">${item.itemName}</option>
											</c:forEach>
										</select>
									</td>
									<td>
										<input type="text" name="price" class="txt_price"/>
									</td>
									<td>
										<input type="number" name="amount" min="1" value="1" class="txt_amount"/>
									</td>
									<td style="padding-right: 22px; border-right: 2px solid #adadad;">
										<div style="display: inline-block;">
											<label class="lbl_rebate"><input type="radio" name="rd_rebate${status.count }" class="rd_rebate" value="per" checked="checked"/>비율</label>
											<label class="lbl_rebate"><input type="radio" name="rd_rebate${status.count }" class="rd_rebate" value="self"/>직접입력</label>
											<p style="margin-top: 5px;">
												<input type="text" id="rebate" name="rebate" class="txt_rebate"/>
												<label for="rebate" class="lbl_rebate_input">%</label>
											</p>
										</div>
										<button type="button" class="btn_rebate">적용</button>
									</td>
									<td class="result01">0 원</td>
									<td class="result02">0 원</td>
									<td><button type="button" class="btn_reg">등록</button></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<div style="text-align: right;">
						<a href="#" class="btn_addItem">품목 추가</a>
					</div>
				</div>
				<div class="section step06">
					<h3><span class="txt_step">STEP 06</span>품목 확인</h3>
					<div class="item_final scrollbar-outer">
						<table>
							<tr>
								<th width="40">번호</th>
								<th width="*">품목</th>
								<th width="40">수량</th>
								<th width="105">수익</th>
								<th width="105">리베이트</th>
								<th width="60"></th>
							</tr>
						</table>
					</div>
					<div class="btn_box">
						<a href="#" class="btn_final">영업 등록</a>
						<a href="#" class="btn_mod">수정 취소</a>
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
<script>
	if('${msg}' != '') {
		alert('${msg}');	
	}

	window.onload = function() {
		kCalendar('kCalendar');
		
		var today = new Date();
		var year = today.getFullYear();
		var month = today.getMonth() + 1;
		var day = today.getDate();
		
		month = (month+'').length == 1 ? '0' + month : month;
		day = (day+'').length == 1 ? '0' + day : day;
		getSalesByDate(year + '-' + month + '-' + day);
	}
	itemEvent();
	
	function itemEvent() {
		$('.sel_item').change(function(){
			var $parent = $(this).parents('tr');
			var val = $(this).find('option:selected').data('no');
			if(val != '') {
				$.ajax({
					url: 'ajaxGetItem.do',
					type: 'post',
					dataType: 'json',
					data: {'no': val},
					success: function(data) {
						$parent.find('.txt_price').val(data.itemPrice);
					}, 
					error: function() {
						alert('error');
					}
				});
			} else {
				$parent.find('.txt_price').val('');
			}
		});
		
		$('.rd_rebate').change(function(){
			var $parent = $(this).parents('tr');
			var val = $(this).val();
			if(val == 'per') {
				$parent.find('.lbl_rebate_input').text('%');
				$parent.find('.txt_rebate').val('');
				$parent.find('.txt_rebate').focus();
			} else if (val == 'self') {
				$parent.find('.lbl_rebate_input').text('원');
				$parent.find('.txt_rebate').val('');
				$parent.find('.txt_rebate').focus();
			}
		});
		
		$('.btn_rebate').click(function(event) {
			event.stopImmediatePropagation(); // 이벤트 여러번 호출되는거 방지
			var $parent = $(this).parents('tr');
			if($parent.find('.sel_item').val() == '') {
				alert('품목을 선택해주세요.');
				$parent.find('.sel_item').focus();
				return;
			} else if ($parent.find('.txt_price').val() == '') {
				alert('가격을 입력해주세요.');
				$parent.find('.txt_price').focus();
				return;
			} else if (isNaN($parent.find('.txt_price').val()) == true) {
				alert('가격은 숫자만 입력 가능합니다.')
				$parent.find('.txt_price').focus();
				return;
			} else if ($parent.find('.txt_amount').val() == '') {
				alert('수량을 입력해주세요.');
				$parent.find('.txt_amount').focus();
				return;
			} else if ($parent.find('.txt_amount').val() < 1) {
				alert('수량을 1 이상 입력해주세요.');
				$parent.find('.txt_amount').focus();
				return;
			} else if ($parent.find('.txt_rebate').val() == '') {
				alert('리베이트 설정가를 입력해주세요.');
				$parent.find('.txt_rebate').focus();
				return;
			} else if ($parent.find('.rd_rebate:checked').val() == 'per' && 
					  (parseInt($parent.find('.txt_rebate').val()) < 0 || 
					   parseInt($parent.find('.txt_rebate').val()) > 100)) {
				alert('0% ~ 100% 까지만 입력 가능합니다.');
				$parent.find('.txt_rebate').focus();
				return;
			} else if ($parent.find('.rd_rebate:checked').val() == 'self' && 
					   parseInt($parent.find('.txt_rebate').val()) < 0) {
				alert('0 이상으로 설정해주세요.');
				$parent.find('.txt_rebate').focus();
				return;
			} else if ($parent.find('.rd_rebate:checked').val() == 'self' && 
				        parseInt($parent.find('.txt_rebate').val()) >
					    parseInt($parent.find('.txt_price').val()) *  
					    parseInt($parent.find('.txt_amount').val())) {
				alert('가격을 초과했습니다.');
				$parent.find('.txt_rebate').focus();
				return;
			}
			var amount = $parent.find('.txt_amount').val();
			var price = $parent.find('.txt_price').val() * amount;
			var rebate = $parent.find('.txt_rebate').val();
			var result01;
			var result02;
			if($parent.find('.rd_rebate:checked').val() == 'per') {
				result01 = price - (price * rebate / 100);
				result02 = price * rebate / 100;
			} else if ($parent.find('.rd_rebate:checked').val() == 'self') {
				result01 = price - rebate;
				result02 = rebate;
			}
			$parent.find('.result01').text(comma(result01) + ' 원');
			$parent.find('.result02').text(comma(result02) + ' 원');
			$parent.find('.btn_reg').addClass('on');
		});
		
		$('.btn_removeItem').click(function(event){
			event.preventDefault();
			$(this).parents('tr').fadeOut(200, function(){
				$(this).parents('tr').remove();
			});
		});
		
		var count = 1;
		$('.btn_reg').click(function(event) {
			event.stopImmediatePropagation();
			event.preventDefault();
			if(!$(this).hasClass('on')) {
				alert('적용 버튼을 눌러주세요.');
				return;
			}
			if($('.itemHide:last').attr('id') != undefined) {
				var no = parseInt($('.itemHide:last').attr('id').replace('itemHide', '')) + 1;
				count = no;
			}
				
			var $parent = $(this).parents('tr');
			var itemName = $parent.find('.sel_item').val();
			var amount = $parent.find('.txt_amount').val();
			var profit = $parent.find('.result01').text().replace(' 원', '').replace(',', '');
			var rebate = $parent.find('.result02').text().replace(' 원', '').replace(',', '');
			
			var html = '';
			html += '<tr>'
			html += '	<td>' + count + '</td>'
			html += '	<td>' + itemName + '</td>'
			html += '	<td>' + amount + '</td>'
			html += '	<td>' + comma(profit) + ' 원</td>'
			html += '	<td>' + comma(rebate) + ' 원</td>'
			html += '	<td><a href="#" class="btn_remove">삭제</a></td>'
			html += '</tr>'
			$('.item_final table').append(html);
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
	}
	$('.btn_addItem').click(function(event){
		event.preventDefault();
		var $table = $('.item_wrap table');
		var lastItemNo = $table.find("tr:last").attr("class").replace("item", "");
        var newitem = $table.find("tr:eq(1)").clone();
        newitem.find('input:radio[class=rd_rebate]').attr('name', 'rd_rebate' + (parseInt(lastItemNo)+1));
        newitem.find('td:last').html('<a href="#" class="btn_removeItem"><i class="fas fa-minus"></i></a>');
        var html = '<tr class=item' + (parseInt(lastItemNo)+1) + '>' + newitem.html() + '</tr>';
        $table.append(html);
        itemEvent();
	});
	
	$('.txt_search').bind('keydown', function(e){
		var $this = $(this);
		setTimeout(function(){
			var check = /([^ㄱ-ㅣ가-힣\x20])/i;
			var val = $this.val();
	        if(!check.test(val)){
	           $.ajax({
	        	   url: 'searchCompany.do',
	        	   type: 'post',
	        	   data: {'word': val},
	        	   dataType: 'json',
	        	   success: function(data) {
	        		    if($('.txt_search').val() == '') {
	        		    	$('.search_list').hide();
	        		    } else if(data.length > 0) {
							var html = '';
							html += '<ul>';
	        				$.each(data, function(index, value){
								html += '	<li><a href="javascript:searchCompany(\'' + value.companyName + '\')">' + value.companyName + '</a></li>';
							});
	        				html += '</ul>';
	        				$('.search_list').html(html);
	        				$('.search_list').show();
	        			} else {
	        				$('.search_list').hide();
	        			}
	        				
	        	   }, 
	        	   error: function() {
	        		   alert('error');
	        	   }
	           });
	        }
		}, 100);
	});
	
	$('.btn_final').click(function(event){
		event.preventDefault();
		
		if('${auth}' == 'N') {
			alert('해당 권한이 없습니다.');
			return;
		}
		var frm = document.frm_reg;
		var date = $('#kCalendar caption').data('date');
		var day = $('#kCalendar td.on').text();
		day = day.length == 1 ? '0' + day : day;
		date += '-' + day;
		
		if(frm.areaName.value == '') {
			alert('지역을 선택해주세요.');
			frm.areaName.focus();
			return;
		} else if (date.length != 10) {
			alert('날짜를 선택해주세요.');
			return;
		} else if (frm.companyName.value == '') {
			alert('상조회사를 선택해주세요.');
			frm.companyName.focus();
			return;
		} else if (frm.funeral.value == '') {
			alert('장례식장명을 입력해주세요.');
			frm.funeral.focus();
			return;
		} else if (frm.deadName.value == '') {
			alert('고인명을 입력해주세요.');
			frm.deadName.focus();
			return;
		} else if (frm.hosil.value == '') {
			alert('호실을 입력해주세요.');
			frm.hosil.focus();
			return;
		} else if (frm.leader.value == '') {
			alert('팀장명을 입력해주세요.');
			frm.leader.focus();
			return;
		} else if ($('.itemHide').length == 0) {
			alert('품목을 등록해주세요.');
			return;
		}
		var result;
		var text = $('.btn_final').text();
		if(text == '영업 수정') {
			frm.action = 'modifySales.do';
			result = confirm('영업정보를 변경 하시겠습니까 ?');
		} else {
			result = confirm('영업을 등록하시겠습니까 ?');
		}
		if(!result) return;
		frm.salesDate.value = date;
		renameForItem();
		frm.submit();
	});
	
	$('.btn_mod').click(function(event){
		event.preventDefault();
		
		$(this).css('display', 'none');
		$('.btn_final').text('영업 등록');
		$('.sel_area').val('').prop('selected', true);
		$('.sel_comp').val('').prop('selected', true);
		$('#funeral').val('');
		$('#deadName').val('');
		$('#hosil').val('');
		$('#leader').val('');
		
		var html = '';
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
	});
	
	function searchCompany(companyName) {
		$('.sel_comp').val(companyName).prop('selected', true);
		$('.txt_search').val('');
		$('.search_list').hide();
	}
	
	function removeEvent() {
		$('.item_final .btn_remove').click(function(event) {
			event.stopImmediatePropagation();
			event.preventDefault();
			var no = $(this).parents('tr').find('td:first').text();
			$(this).parents('tr').remove();
			$('#itemHide' + no).remove();
		});
	}
	
	function renameForItem() {
	    $(".itemHide").each( function (index) {
	        $(this).find("input[name=itemName]").attr("name", "salesList[" + index + "].itemName");
	        $(this).find("input[name=amount]").attr("name", "salesList[" + index + "].amount");
	        $(this).find("input[name=profit]").attr("name", "salesList[" + index + "].profit");
	        $(this).find("input[name=rebate]").attr("name", "salesList[" + index + "].rebate");
	    });
	}
</script>