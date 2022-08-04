package com.vthz2.app2.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vthz2.app2.entities.Trips;

public interface TripsRepository extends JpaRepository<Trips,Integer> {


	@Query(value="Select * from Trips",nativeQuery=true)
	public List<Trips> getAllTrips();
	
	@Query("select t.trip_id from Trips t WHERE t.origin=:s and t.destination=:e")
	public int getTripId(@Param("s")String start, @Param("e")String end);
}
