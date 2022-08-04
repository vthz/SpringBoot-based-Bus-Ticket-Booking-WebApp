package com.vthz2.app2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.vthz2.app2.dao.UserRepository;
import com.vthz2.app2.entities.Trips;
import com.vthz2.app2.entities.User;
import com.vthz2.app2.service.CuserDetailsService;

//@Controller
public class HomeController {
	
	private User user;
	
	@Autowired
	private CuserDetailsService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	//---PUBLIC REQUEST HANDLER
	@GetMapping("/")
	public ModelAndView publicHome() {
		
		ModelAndView m1= new ModelAndView();
		m1.setViewName("index_home.html");
		return m1;
	}
	
	@RequestMapping("/signin")
	public ModelAndView login(){
		ModelAndView m1=new ModelAndView();
		m1.setViewName("login.html");
		return m1;
	}
	
	@RequestMapping("/signup")
	public ModelAndView signup(Model model) {
		model.addAttribute("user",new User());
		ModelAndView m1=new ModelAndView();
		m1.setViewName("signup.html");
		return m1;
	}
	
	@PostMapping(value="/do_register")
	public ModelAndView registerUser(@ModelAttribute("user") User user, 
			Model model, @RequestParam("rb1") String role, 
			@RequestParam("rb2") String profession, 
			@RequestParam("dob") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob,
			@RequestParam("profileImage") MultipartFile multipartFile) throws IOException {
		System.out.println("USER"+user);
		System.out.println(role+"---"+ profession + " "+ LocalDate.now());
		user.setRole(role);
		user.setProfession(profession);
		user.setDob(dob);
		user.setValid_till(LocalDate.now());
		String fileName=StringUtils.cleanPath(multipartFile.getOriginalFilename());
		user.setImageUrl(user.getUsername()+fileName);
		String imageDir="src/main/resources/userImages";
		fileName=user.getUsername()+fileName;
		Path uploadPath=Paths.get(imageDir);
		System.out.println(uploadPath);
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		
		try(InputStream inputStream=multipartFile.getInputStream()){
		Path filePath=uploadPath.resolve(fileName);
		System.out.println(filePath);
		Files.copy(inputStream,filePath,StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException e) {
			System.out.println(e);
		}
		User result=this.userRepository.save(user);
		ModelAndView m1=new ModelAndView();
		m1.setViewName("signup.html");
		return m1;
	} 
	
	// ----=======CUSTOMER CONTROLLER
	
	@RequestMapping("/user/index")
	public ModelAndView dashboard(HttpServletRequest request,Model model,Principal p) {
		String username=p.getName();
		System.out.println(username);
		User userDetails=userRepository.getUserByUserName(username);
		System.out.println(userDetails.getImageUrl());
		String src=userDetails.getImageUrl();
		model.addAttribute("src",src);
		ModelAndView m1=new ModelAndView();
		m1.setViewName("index.html");
		return m1;
	}
	
	@RequestMapping("/user/")
	public ModelAndView userDashboard() {
		return new ModelAndView("redirect:"+"/user/index");
	}
	
	@RequestMapping("/user/payment")
	public ModelAndView payment() {
		ModelAndView m1=new ModelAndView();
		m1.setViewName("payment_gateway.html");
		return m1;
	}
	
	@RequestMapping("/user/renew")
	public ModelAndView renew() {
		ModelAndView m1=new ModelAndView();
		m1.setViewName("renewable_page.html");
		return m1;
	}
	
	@RequestMapping("/user/download")
	public ModelAndView download(HttpServletRequest request) {
		String username=request.getUserPrincipal().getName();
		return new ModelAndView("redirect:"+"/user/index");
	}
	
	@RequestMapping("/user/bookticket")
	public ModelAndView bookticket(Model model) {
		ModelAndView m1=new ModelAndView();
		m1.setViewName("book_ticket.html");
		return m1;
	}
	
	@RequestMapping("/user/main_style.css")
	public ModelAndView returnCss() {
		
		ModelAndView m1=new ModelAndView();
		m1.setViewName("main_style.css");
		return m1;
	}
	
	@RequestMapping("/user/calculate")
	public ModelAndView checkrole(@RequestParam("start")String start,@RequestParam("end") String end,Model model, HttpServletRequest request) {
		String username=request.getUserPrincipal().getName();		
		ModelAndView m1=new ModelAndView();
		m1.setViewName("download_ticket.html");
		return m1;
	
	}
	
	@RequestMapping("/user/process_renew")
	public ModelAndView processrenew(@RequestParam("rb1") String month,HttpServletRequest request) {
		return new ModelAndView("redirect:"+"/user/index");
	}
	
	@RequestMapping("/user/booknow")
	public ModelAndView finalbooking() {
		boolean flag=true;
		//@Autowired
		//Journey journey;
		//flag=bookNowFunction();
			ModelAndView m1=new ModelAndView();
			m1.setViewName("unsuccessful.html");
			return m1;
		
	}
	/*
	public boolean bookNowFunction() {
		try {
			System.out.println();
			Journey journey=new Journey();
			journey.setOrigin(borigin);
			journey.setDestination(bdestination);
			journey.setUserid(buid);
			journey.setPrice(bprice);
			journeyRepository.save(journey);
			return true;
		}
		catch(Exception e) {
			System.out.println(e);
			return false;
			
		}
	}*/	

	//-----------====CUSTOMER CONTROLLER END
	
	//-----------====ADMIN CONTROLLER START

	
	@RequestMapping("/listout")
	@ResponseBody
	public List<User> listout(Model model) {
		List<User> userlist=userService.getAllUserS();
		model.addAttribute("userlist",userlist);
		System.out.println("I am called");
		ModelAndView m1=new ModelAndView();
		m1.setViewName("admin_listout.html");
		return userlist;
	}
	
	@RequestMapping("/admin/history")
	public ModelAndView history(Model model) {
		//List<Trips> triplist=tripService.getAllTrip();

		//model.addAttribute("triplist",triplist);
		ModelAndView m1=new ModelAndView();
		m1.setViewName("admin_history.html");
		return m1;
	}
	
	@RequestMapping("/admin/main_style.css")
	public ModelAndView returnCss1() {
		
		ModelAndView m1=new ModelAndView();
		m1.setViewName("main_style.css");
		return m1;
	}
	
	@RequestMapping("/admin/newtrips")
	public ModelAndView newtrip(Model model) {
		model.addAttribute("trip", new Trips());
		ModelAndView m1=new ModelAndView();
		m1.setViewName("newtrip.html");
		return m1;
	}
	
	@RequestMapping(value="/admin/do_addtrip",method=RequestMethod.POST)
	public ModelAndView registerTrip(@ModelAttribute("trip") Trips trip, Model model) {
		System.out.println("TRIP"+trip);
		//Trips result=this.tr.save(trip);
		//model.addAttribute("trip",result);
		ModelAndView m1=new ModelAndView();
		m1.setViewName("newtrip.html");
		return m1;
	} 
	
	@RequestMapping("/admin/showAlljourney")
	public ModelAndView showAlljourney(Model model,@RequestParam("rb1") String sortby, @RequestParam("placeo") String placeo,@RequestParam("placed") String placed ) {
		System.out.println(sortby+" "+placeo+" "+placed);
	
			ModelAndView m1=new ModelAndView();
			m1.setViewName("admin_showall.html");
			return m1;
	}

	//-----------====ADMIN CONTROLLER END

	
	
	
}
