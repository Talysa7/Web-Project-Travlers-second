package db;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

//database view(with user_name) : pao_view_trip
//database table : pao_trip
public class TripDataBean {
	private int trip_id;										//int (10), PK, not null, on delete cascade
	private int trip_member_count;					//smallint (4), not null
	private Date start_date;								//timestamp, not null
	private Date end_date;								//timestamp, not null
	//FK
	private int board_no;									//int (10), not null
	private int coord_id;									//int (8), not null
	//guest value from pao_view_members
	//We should set it once by trip_id when we need it.
	private List<String> trip_members;			//varchar (20), not null
	//guest value from pao_view_trip
	//Be careful, when we get trip list from pao_trip, then we can't get this value!
	private String coord_name;
	
	public int getTrip_id() {
		return trip_id;
	}
	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}
	public int getTrip_member_count() {
		return trip_member_count;
	}
	public void setTrip_member_count(int trip_member_count) {
		this.trip_member_count = trip_member_count;
	}
	public int getCoord_id() {
		return coord_id;
	}
	public void setCoord_id(int coord_id) {
		this.coord_id = coord_id;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}
	public int getBoard_no() {
		return board_no;
	}
	public void setBoard_no(int board_no) {
		this.board_no = board_no;
	}
	public List<String> getTrip_members() {
		return trip_members;
	}
	//for convenience
	//by talysa7
	public List<String> getTrip_members(int trip_id) {
		this.setTrip_members(trip_id);
		return trip_members;
	}
	//get a list of each trip member's user_name from pao_view_members
	//by talysa7
	public void setTrip_members(int trip_id) {
		MemberDBBean memberDao=new MemberDBBean();
		this.trip_members=memberDao.getMemberNames(trip_id);
	}
	public String getCoord_name() {
		return coord_name;
	}
	public void setCoord_name(String coord_name) {
		this.coord_name = coord_name;
	}
}
