package handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import db.AlbumDBBean;
import db.AlbumDataBean;
import db.CmtDBBean;
import db.CmtDataBean;
import db.TagDBBean;
import db.TagDataBean;
import db.BoardDBBean;
import db.BoardDataBean;
import db.TripDBBean;
import db.UserDBBean;
import db.UserDataBean;

@Controller
public class AdmListHandler {
	private static final int pageSize=10;
	private static final int pageBlock = 5;
	
	@Resource
	private TripDBBean tripDao;
	@Resource
	private BoardDBBean boardDao;
	@Resource
	private AlbumDBBean albumDao;
	@Resource
	private CmtDBBean cmtDao;
	@Resource
	private UserDBBean userDao;
	@Resource
	private TagDBBean tagDao;
	
	// page 이름
	private static final String tripP="trip";
	private static final String commentP="comment";
	private static final String userP="user";
	private static final String tagP="tag";
	private static final String albumP="album";
	
	// 게시판 연산 로직
	private int start;
	private int end;
	public void setBoardLogic(HttpServletRequest request, String pageNum, int count, int start, int end){
		int currentPage = Integer.parseInt(pageNum);
		int pageCount = count / pageSize + (count % pageSize>0 ? 1:0 );
		if( currentPage > pageCount ) currentPage = pageCount;
		start = ( currentPage - 1 )*pageSize + 1;					
		end = start + pageSize - 1;	
		
		if(end > count) end = count;
		
		int number = count - (currentPage - 1) * pageSize;				
			
		int startPage = (currentPage / pageBlock) * pageBlock+1;  		
		if(currentPage % pageBlock == 0) startPage -= pageBlock;
		int endPage = startPage + pageBlock - 1;							
		if(endPage > pageCount ) endPage = pageCount;
	
		request.setAttribute( "count", count );
		request.setAttribute( "pageNum", pageNum );
		request.setAttribute( "currentPage", currentPage );
		request.setAttribute( "number", number );
		request.setAttribute( "startPage", startPage );
		request.setAttribute( "endPage", endPage );
		request.setAttribute( "pageCount", pageCount );
		request.setAttribute( "pageBlock", pageBlock );
	}
	
	
	@RequestMapping("/admin/*")
	public ModelAndView admDefaultProcess(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		return new ModelAndView("admin/default");
	}

	@RequestMapping("adminTrip")
	public ModelAndView adminTripHandler(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		request.setAttribute("page", tripP);
		int count = boardDao.getPostsCount();//list row num
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}
		
		setBoardLogic(request, pageNum, count, start, end);
		
		if(count > 0) {
			Map<String, Integer> map=new HashMap<String,Integer>();
			map.put("start", start);
			map.put("end", end);
			
			List<BoardDataBean>trips = boardDao.getTrips(map);
			request.setAttribute("trips", trips);
		}
//		FIXME : 다음 하단 주석에 대한 변 (준호)
//		1. 일단 누가 여기로 이 값을 던져주는지 알아야함 (null에 대한 우려)
//		2. notice 메서드가 setBoardLevel 로 바뀌었는데,
//		notice, noticeX 의 기능을 정확히 알 수 없음
//		String board_no = request.getParameter("board_no");
//		String notice = request.getParameter("yn");
//		if(board_no != null && notice != null) {
//			if(notice.equals("yes")) {
//				tripDao.notice(Integer.parseInt(board_no));// FIXME: 두개다 필요함
//			} else {
//				tripDao.noticeX(Integer.parseInt(board_no));
//			}
//		}
		return new ModelAndView("admin/trip");
	}
	@RequestMapping("adminComment")
	public ModelAndView adminContentHandler(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		request.setAttribute("page", commentP);
		int count = cmtDao.getCmtCount();//list row num
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}
		setBoardLogic(request, pageNum, count, start, end);
		
		if(count>0) {
			Map<String, Integer> map = new HashMap<String,Integer>();
			map.put("start", start);
			map.put("end", end);
			
			List<CmtDataBean>comments = cmtDao.getComments(map);
			request.setAttribute("comments", comments);
		}
		return new ModelAndView("admin/comment");
	}
	@RequestMapping("/adminUser")
	public ModelAndView adminUserHandler(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		request.setAttribute("page", userP);
		int count = userDao.getAllUserCount();//list row num
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum == null || pageNum.equals("")){
			pageNum = "1";
		}

		setBoardLogic(request, pageNum, count, start, end);
		
		if(count>0) {
			Map<String, Integer> map = new HashMap<String,Integer>();
			map.put("start", start);
			map.put("end", end);
			
			List<UserDataBean>users = userDao.getUsers(map);
			request.setAttribute("users", users);
		}

		return new ModelAndView("admin/user");
	}
	@RequestMapping("adminTag")
	public ModelAndView adminTagHandler(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		request.setAttribute("page", tagP);
		int count=tagDao.getCount();//list row num
		
		String pageNum=request.getParameter("pageNum");
		if(pageNum==null || pageNum.equals("")){
			pageNum = "1";
		}
		setBoardLogic(request, pageNum, count, start, end);
		
		if(count>0) {
			Map<String, Integer> map = new HashMap<String,Integer>();
			map.put("start", start);
			map.put("end", end);
			
			List<TagDataBean>tags = tagDao.getTags(map);
			request.setAttribute("tags", tags);
		}
		return new ModelAndView("admin/tag");
	}
	
	@RequestMapping("adminAlbum")
	public ModelAndView adminPhotoHandler(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		request.setAttribute("page", albumP);
		int count=albumDao.getCount();//list row num
		if(count>0) {
			List<AlbumDataBean> album = albumDao.getAlbum();
			request.setAttribute("album", album);
		}
		return new ModelAndView("admin/album");
	}
	@RequestMapping("adminLogout")
	public ModelAndView adminLogoutHandler(HttpServletRequest request, HttpServletResponse response) throws HandlerException {
		request.getSession().removeAttribute( "user_id" );
		request.getSession().removeAttribute( "user_level" );
		return new ModelAndView("redirect:login.go");
	}
	
	
}
