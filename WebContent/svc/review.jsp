<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="header.jsp" %>
<%@include file="setting.jsp" %>
<h2>평판페이지</h2>


<form method="post" action="reviewPro.go">
	
		
		<c:forEach var="trip" items="${trip}">	
			<div>	
		<span>일정</span>	
		<span><select id="sel1" name="sel1">		
		<option value="${trip.trip_id}">${trip.coordinate.coord_name}</option>			
		</select> </span>	
		
		<span>평판대상</span>				
		<span><select id="sel2" name="sel2" onchange="change(trip)">
		<c:forEach var="member" items="${trip.trip_members}">
		<option value="${member.user_id}" data-option="${trip.trip_id}">${member.user_id}</option>
		</c:forEach>
		</select>
		</span>
		</div>
		</c:forEach>	
					 
		
		
		<div>
		<span>점수</span>
		<span> <input type="checkbox" value="1" name="grade"> ★ </span>
		<span> <input type="checkbox" value="2" name="grade"> ★★</span>
		<span> <input type="checkbox" value="3" name="grade"> ★★★</span>
		<span> <input type="checkbox" value="4" name="grade"> ★★★★</span>	
		<span> <input type="checkbox" value="5" name="grade"> ★★★★★</span>	
		</div>
		<div>내용 </div>
		<div> ※ 동행인에 대한 평판글은 수정 및 삭제가 불가하오니 이점 유의하여 작성해주세요 ※</div>
		<div> <textarea name="textarea" rows="10" cols="50"></textarea> </div>
		<div><input type="submit" value="제출">
			 <input type="reset" value="취소"></div>
</form>