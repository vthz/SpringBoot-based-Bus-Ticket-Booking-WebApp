package com.vthz2.app2.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vthz2.app2.entities.User;

public interface UserRepository extends JpaRepository<User,Integer>{
	User findByUsername(String name);
	
	@Query(value="Select * from User",nativeQuery=true)
	public List<User> getAllUser();

	@Query(value="Select * from User where username=:uname",nativeQuery=true)
	public User getUserByUserName(@Param("uname")String username);
	
	@Query(value="Select * from User where username=:uname and password=:upassword",nativeQuery=true)
	User check_credentials(@Param("uname")String username, @Param("upassword") String userpassword);
	
	@Query(value="Select datediff(valid_till,curdate()) from User where username=:username",nativeQuery=true)
	public String getRenewalStats(@Param("username") String username);

	@Transactional
	@Modifying
	@Query(value="Update User SET valid_till =adddate(curdate(),:months) WHERE user_id =:uid",nativeQuery=true)
	void renewPass(@Param("months") int monthsInt,@Param("uid") int userid);

	@Transactional
	@Modifying
	@Query(value="Update User SET name=:uname,dob=:dob,address=:address,profession=:profession WHERE user_id =:uid",nativeQuery=true)
	void updateBasicDetails(@Param("uid") int uid,@Param("uname") String name,@Param("dob") LocalDate dob,@Param("address") String address,@Param("profession") String profession);
	
	@Transactional
	@Modifying
	@Query(value="Update User SET image_url =:ppimg WHERE user_id =:uid", nativeQuery=true)
	void updateImage(@Param("uid") int user_id, @Param("ppimg") String fileName);

	@Query(value="select image_url from User where user_id=:uid",nativeQuery=true)
	String getImagePath(@Param("uid") int userid);
}
