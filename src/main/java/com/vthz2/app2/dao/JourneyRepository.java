package com.vthz2.app2.dao;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.vthz2.app2.Response.JourneyResponse;
import com.vthz2.app2.entities.Journey;
import com.vthz2.app2.entities.Trips;

public interface JourneyRepository extends JpaRepository<Journey,Integer>{

	

	//@Query(value="Select * from Journey",nativeQuery=true)
	@Query(value="SELECT new com.vthz2.app2.Response.JourneyResponse(T.origin,T.destination, T.price,J.journeyDate) from Trips T inner join Journey J on T.trip_id=J.trips_idfk and J.user_idfk=:uid")
	public List<JourneyResponse> getJourneyHistory(@Param("uid") int user_id1);
	
	@Transactional
	@Modifying
	@Query(value="insert into Journey (user_idfk,trips_idfk,journey_date) values(:uid,:tid,curdate());",nativeQuery=true)
	public void addJourney(@Param("uid") int userid,@Param("tid") int trip_id);

}
