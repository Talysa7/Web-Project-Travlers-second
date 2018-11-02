package db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import bean.SqlMapClient;

public class BoardDBBean {
	private SqlSession session=SqlMapClient.getSession();
	
	//get count of all trip
	public int getCount() {
		return session.selectOne("db.getCount");
	}
	public void addCount(int board_no) {
		session.update("db.addCount", board_no);
	}
	
	//get one trip post by tb_no, including location and tag list
	public BoardDataBean getBoard(int board_no) {
		//original trip data
		BoardDataBean boardDto=session.selectOne("db.getTrip", board_no);
		//set Nickname instead of id
		String user_id=boardDto.getUser_id();
		String user_name;
		//if that user left
		if(user_id==null||user_id.equals("")) {
			user_name="Ex-User";
		} else {
			user_name=(String) session.selectOne("db.getUserName", user_id);
		}
		boardDto.setUser_id(user_name);
		
		//trip detail	TODO:관련부분 JSP수정필요. 원래 ID만 줬지만 지금은 전체를 통째로 던져줌.
		List<TripDataBean> tripLists=session.selectList("db.getTripList", board_no);
		boardDto.setTripLists(tripLists);
		
		return boardDto;
	}
	
	public int insertBoard_no(BoardDataBean boardDto) {
	      return session.insert("db.insertBoard_no",boardDto);
	}
	
	public int insertTrip(BoardDataBean boardDto) {
		return session.insert("db.insertTrip",boardDto);
	}
	
	public String getUserId(String user_name) {
		return session.selectOne("db.getUserId", user_name);
	}
	public int updateBoard(BoardDataBean boardDto) {
		return session.update("db.updateBoard", boardDto);
	}
	
	//trip board list
	public List<BoardDataBean> getTrips(Map<String,Integer> map){
		return session.selectList("db.getTrips",map);
	}
	
	public int deleteTrip(int board_no) {
		return session.delete("db.deleteTrip", board_no);
	}
	
	public boolean isMember(BoardDataBean boardDto) {
		int count=session.selectOne("db.isMember",boardDto);
		if(count>0)return true;
		else return false;
	}
	
	public List<BoardDataBean> findTripByKeyword(String keyword) {	//	수정필요.
		return null;
	}
	
	public List<BoardDataBean> findTripByUser(String keyword) {	// 수정필요.
		return null;
	}
	
	public List<Map<String, String>> getMemInfoList(int board_no) {	//	 얘도 수정필요.
		List<Map<String, String>> memNumList=new ArrayList<Map<String, String>>();
		List<TripDataBean> trips=session.selectList("db.getTripList", board_no);
		
		if(trips.size()>0) {
			for(TripDataBean tripDto:trips) {
				/*Map<String, String> currentTrip=new HashMap<String, String>();	사람수 가져오는건데 각 세부일정당으로 바뀌어서 보여줄수가 없음.
				int memNum=session.selectOne("db.getMemberCount", trip_id);
				String td_trip_id_string=""+trip_id;
				String memNum_string=""+memNum;
				
				currentTrip.put("td_trip_id", td_trip_id_string);
				currentTrip.put("memNum", memNum_string);
				memNumList.add(currentTrip);*/
			}
		}
		
		return memNumList;
	}
	
	//by talysa7
	public List<BoardDataBean> getTripList(int rowNumber , int articlePerPage) {	//이거 처음 10개만 불러와요? 그 이후엔 안쓰나요?
		Map<String, Integer> tripReq=new HashMap<String, Integer>();
		tripReq.put("startRowNumber", rowNumber);
		tripReq.put("endRowNumber", rowNumber*articlePerPage);
		List<BoardDataBean> BoardList=session.selectList("board.getTripList", tripReq);
		//user_name null exception
		if(BoardList.size()>0) {
			for(BoardDataBean boardDto:BoardList) {
				if(boardDto.getUser_id().equals("")||boardDto.getUser_id()==null) {
					boardDto.setUser_id("Ex-User");
				}
			}
		}
		return BoardList;
	}
}
