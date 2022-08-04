package com.vthz2.app2.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import org.springframework.format.annotation.DateTimeFormat;

import com.vthz2.app2.AuthenticationProvider;

@Entity
@Table(name="USER")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int user_id;
	private String name;
	private String username;
	private String email;
	private String password;
	private String role;
	private String address;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dob;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate valid_till;
	private String profession;
	
	@OneToMany(targetEntity=Journey.class,cascade=CascadeType.ALL)
	@JoinColumn(name="user_idfk", referencedColumnName="user_id")
	private List<Journey> journey;
	
	@OneToMany(targetEntity=Trips.class,cascade=CascadeType.ALL)
	@JoinColumn(name="admin_fk", referencedColumnName="user_id")
	private List<Trips> trips;
	
	
	private String auth_provider;
	public List<Journey> getJourney() {
		return journey;
	}
	public void setJourney(List<Journey> journey) {
		this.journey = journey;
	}

	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	private String imageUrl;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public LocalDate getDob() {
		return dob;
	}
	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	public LocalDate getValid_till() {
		return valid_till;
	}
	public void setValid_till(LocalDate valid_till) {
		this.valid_till = valid_till;
	}
	public User() {
		super();
	}
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", name=" + name + ", username=" + username + ", email=" + email
				+ ", password=" + password + ", role=" + role + ", address=" + address + ", dob=" + dob
				+ ", valid_till=" + valid_till + ", profession=" + profession + ", imageUrl=" + imageUrl + "]";
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	/*
	@Transient
	public String getProfileImagePath() {
		if(imageUrl==null || user_id< 0) return null;
		return "/user-photos/"+user_id+"/"+imageUrl;
	}
	*/
	public String getAuth_provider() {
		return auth_provider;
	}
	public void setAuth_provider(String auth_provider) {
		this.auth_provider = auth_provider;
	}
}
