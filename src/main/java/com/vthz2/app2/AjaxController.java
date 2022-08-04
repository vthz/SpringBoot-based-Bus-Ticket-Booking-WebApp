package com.vthz2.app2;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vthz2.app2.Response.JourneyResponse;
import com.vthz2.app2.dao.JourneyRepository;
import com.vthz2.app2.dao.TripsRepository;
import com.vthz2.app2.dao.UserRepository;
import com.vthz2.app2.entities.Journey;
import com.vthz2.app2.entities.Trips;
import com.vthz2.app2.entities.User;
import com.vthz2.app2.service.CuserDetailsService;

@RestController
public class AjaxController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CuserDetailsService userService;
	
	@Autowired
	private TripsRepository tripsRepository;
	
	@Autowired
	private JourneyRepository journeyRepository;
	
	

	@CrossOrigin
	@GetMapping("/")
	public ModelAndView publicHome(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("username");
		
		String password=(String) session.getAttribute("pwd");
		System.out.println("USERNAME "+ username + "    =====  " + password);
		User user_details=userRepository.check_credentials(username, password);
		System.out.println(user_details);
		if (username!=null) {
			if (user_details.getRole().equals("ROLE_USER")){
				ModelAndView m1= new ModelAndView();
				m1.setViewName("A_user.html");
				return m1;
			}
			else {
				ModelAndView m1= new ModelAndView();
				m1.setViewName("A_admin.html");
				return m1;
			}
		}
		ModelAndView m1= new ModelAndView();
		m1.setViewName("A_publicHome.html");
		return m1;
	}
	
	@CrossOrigin
	@RequestMapping(value="/dashboard")
	public ModelAndView login1(@RequestParam("usernamelogin") String username, @RequestParam("passwordlogin") String userpassword,@RequestParam("loginType") String loginType, Model model,HttpServletRequest request) {
		System.out.println(username);
		if (loginType.equals("googleSignin")) {
			System.out.println("Its a google signin! Please handle it");
			userpassword=loginType;
			User user_details=userRepository.check_credentials(username, userpassword);
			if (user_details==null) {
				User user_class=new User();
				user_class.setUsername(username);
				user_class.setPassword(loginType);
				user_class.setAuth_provider(loginType);
				user_class.setRole("ROLE_USER");
				user_class.setValid_till(LocalDate.now());
				this.userRepository.save(user_class);
			}else {
				HttpSession session=request.getSession();
				session.setAttribute("username",username);
				session.setAttribute("userid",user_details.getUser_id());
				session.setAttribute("pwd",loginType);
				session.setAttribute("name", user_details.getName());
				session.setAttribute("imageUrl", user_details.getImageUrl());
			}
			ModelAndView m1= new ModelAndView();
			m1.setViewName("A_user.html");
			return m1;
		}
		User user_details=userRepository.check_credentials(username, userpassword);
		if (user_details==null) {
			return new ModelAndView("redirect:"+"/");
		}
		if (user_details.getRole().equals("ROLE_USER") && !user_details.getAuth_provider().equals("googleSignin")) {
			HttpSession session=request.getSession();
			
			session.setAttribute("username",username);
			session.setAttribute("userid",user_details.getUser_id());
			session.setAttribute("pwd", userpassword);
			session.setAttribute("name", user_details.getName());
			session.setAttribute("imageUrl", user_details.getImageUrl());
			ModelAndView m1= new ModelAndView();
			m1.setViewName("A_user.html");
			return m1;
		}
		else if(user_details.getRole().equals("ROLE_ADMIN") && !user_details.getAuth_provider().equals("googleSignin")){
			HttpSession session=request.getSession();
			session.setAttribute("username",username);
			session.setAttribute("userid",user_details.getUser_id());
			session.setAttribute("pwd", userpassword);
			session.setAttribute("name", user_details.getName());
			session.setAttribute("imageUrl", user_details.getImageUrl());
			
			ModelAndView m1= new ModelAndView();
			m1.setViewName("A_admin.html");
			return m1;
		}else {
			return new ModelAndView("redirect:"+"/");
		}
		
	}
	
	@CrossOrigin
	@RequestMapping("/logout_custom")
	public ModelAndView logoutFunction(HttpServletRequest request) {
		HttpSession session=request.getSession();
		session.invalidate();
		return new ModelAndView("redirect:"+"/");
	}
	
	@CrossOrigin
	@RequestMapping(value="/initialSignup",method=RequestMethod.POST)
	ResponseEntity<String> initialSignup(@RequestBody User user) {
		user.setValid_till(LocalDate.now());
		this.userRepository.save(user);
		//return "Ok";
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/basicDetails",method=RequestMethod.POST)
	ResponseEntity<String> basicDetails(@RequestBody User user,HttpServletRequest request) {
		//User result=this.userRepository.save(user);
		String name=user.getName();
		LocalDate dob=user.getDob();
		String profession=user.getProfession();
		String address=user.getAddress();
		HttpSession session=request.getSession();
		int uid=(int) session.getAttribute("userid");
		session.getAttribute("username");
		userRepository.updateBasicDetails(uid,name,dob,address,profession);
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value="/getBasicDetails", method=RequestMethod.GET)
	public User returnBasicDetails(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("username");
		String password=(String) session.getAttribute("pwd");
		User user_details=userRepository.check_credentials(username, password);
		return user_details;
	}
	
	@CrossOrigin
	  @PostMapping("/upload") 
	  public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file , HttpServletRequest request) {
		System.out.println("Image upload function has been called");
		HttpSession session=request.getSession();
		int user_id=(int) session.getAttribute("userid");
	    String fileName = String.valueOf(user_id)+file.getOriginalFilename();
	    System.out.println(fileName);
	    userRepository.updateImage(user_id,fileName);
	    try {
	      file.transferTo( new File("E:\\VS Code Scripts\\Zoho Internship\\Spring Applications\\app2\\app2\\src\\main\\resources\\profilePicturesDB\\" + fileName));
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    } 
	    return ResponseEntity.ok("File uploaded successfully.");
	  }
	
	@CrossOrigin
	@RequestMapping("/image/getProfileimage")
	@ResponseBody
	public User getImageUrl(HttpServletRequest request) {
		HttpSession session=request.getSession();
		int userid=(int) session.getAttribute("userid");
		String imageurl=userRepository.getImagePath(userid);
		if (imageurl==null) {
			imageurl="profileIMGdefault.jpg";
		}
		System.out.println(imageurl+"   ===+++");
		User user_class=new User();
		user_class.setImageUrl(imageurl);
		
		return user_class;
		
	}
	
	

	
	@RequestMapping("/A_main_js.js")
	public ModelAndView returnJS() {
		
		ModelAndView m1=new ModelAndView();
		m1.setViewName("A_main_js.js");
		return m1;
	}
	
	@RequestMapping("/A_main_js_public.js")
	public ModelAndView returnJSpublic() {
		
		ModelAndView m1=new ModelAndView();
		m1.setViewName("A_main_js_public.js");
		return m1;
	}
	
	@RequestMapping("/pay/A_payment.js")
	public ModelAndView returnJSpayment() {
		
		ModelAndView m1=new ModelAndView();
		m1.setViewName("A_payment.js");
		return m1;
	}
	@RequestMapping("/pay/A_main_js.js")
	public ModelAndView returnJSpaymentMainJS() {
		
		ModelAndView m1=new ModelAndView();
		m1.setViewName("A_main_js.js");
		return m1;
	}
	
	@RequestMapping("/A_main_css.css")
	public ModelAndView returnCss() {
		
		ModelAndView m1=new ModelAndView();
		m1.setViewName("A_main_css.css");
		return m1;
	}
	
	@CrossOrigin
	@RequestMapping("/listout1")
	@ResponseBody
	public List<User> listout(Model model,HttpServletRequest request) {
		List<User> userlist=userService.getAllUserS();
		model.addAttribute("userlist",userlist);
		request.getSession();
		ModelAndView m1=new ModelAndView();
		m1.setViewName("admin_listout.html");
		return userlist;
	}
	
	@CrossOrigin
	@RequestMapping("/alltrips")
	@ResponseBody
	public List<Trips> history(Model model) {
		List<Trips> tripslist=tripsRepository.getAllTrips();
		return tripslist;
	}
	
	@CrossOrigin
	@RequestMapping(value="/do_addtrip",method=RequestMethod.POST)
	ResponseEntity<String> registerTrip(@RequestBody Trips trip,HttpServletRequest request) {
		HttpSession session=request.getSession();
		int userid=(int) session.getAttribute("userid");
		trip.setAdmin_fk(userid);
		this.tripsRepository.save(trip);
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	} 
	
	@CrossOrigin
	@RequestMapping(value="/getuser")
	@ResponseBody
	public String getUser(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("username");
		return username;
	}
	
	@CrossOrigin
	@RequestMapping("/renewalstats")
	@ResponseBody
	public String getRenewalStats(HttpServletRequest request) {
		HttpSession session=request.getSession();
		String username=(String) session.getAttribute("username");
		return userRepository.getRenewalStats(username);
	}
	
	@CrossOrigin
	@RequestMapping("/index1")
	@ResponseBody
	public List<JourneyResponse> dashboard(HttpServletRequest request) {
		HttpSession session=request.getSession();
		int user_id=(int) session.getAttribute("userid");
		List<JourneyResponse> journeyList=journeyRepository.getJourneyHistory(user_id);
		return journeyList;
	}
	
	@CrossOrigin
	@RequestMapping("/bookit")
	public String checkrole(@RequestBody Trips trips,HttpServletRequest request) {
		System.out.println(trips);
		String start=trips.getOrigin();
		String end=trips.getDestination();
		try {
			int trip_id=tripsRepository.getTripId(start,end);
			HttpSession session=request.getSession();
			int userid=(int) session.getAttribute("userid");
			Journey journeyVar=new Journey();
			journeyVar.setUser_idfk(userid);
			journeyVar.setTrips_idfk(trip_id);
			journeyVar.setJourneyDate(LocalDate.now());
			journeyRepository.save(journeyVar);
		}catch(Exception err) {
			System.out.println(err);
		}
		
		System.out.println("Ticket has been BOOKED!!!");
		return "Ok";
	
	}
	
	@CrossOrigin
	@RequestMapping("/payment")
	public String paymentFunction() {
		System.out.println("Successful payment!");
		return "Ok";
	}
	
	@CrossOrigin
	@RequestMapping("/renewpass")
	public String renewPass(@RequestBody String months,HttpServletRequest request) {
		HttpSession session=request.getSession();
		int userid=(int) session.getAttribute("userid");
		if(months.equals("{\"months\":\"6\"}")) {
			System.out.println("Renewal for 6 months");
			int monthsInt=180;
			userRepository.renewPass(monthsInt,userid);
		}else {
			System.out.println("Renewal for 12 months");
			int monthsInt=360;
			userRepository.renewPass(monthsInt,userid);
		}
		System.out.println("Pass has been RENEWED!!!");
		return "Ok";
		
	}
	

	
	
}
