package com.vthz2.app2.entities;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="JOURNEY")
public class Journey {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int journey_id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate journeyDate;
	
	public LocalDate getJourneyDate() {
		return journeyDate;
	}

	public void setJourneyDate(LocalDate journeyDate) {
		this.journeyDate = journeyDate;
	}

	public int getUser_idfk() {
		return user_idfk;
	}

	public void setUser_idfk(int user_idfk) {
		this.user_idfk = user_idfk;
	}

	public int getTrips_idfk() {
		return trips_idfk;
	}

	public void setTrips_idfk(int trips_idfk) {
		this.trips_idfk = trips_idfk;
	}

	private int user_idfk;
	private int trips_idfk;



	public int getJourney_id() {
		return journey_id;
	}

	public Journey() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setJourney_id(int journey_id) {
		this.journey_id = journey_id;
	}

	@Override
	public String toString() {
		return "Journey [journey_id=" + journey_id + "]";
	}
	
	
}
