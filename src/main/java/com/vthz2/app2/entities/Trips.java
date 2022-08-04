package com.vthz2.app2.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Trips")
public class Trips {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int trip_id;
	private String origin;
	private String destination;
	private int distance;
	private float price;
	private float admin_fk;
	@OneToMany(targetEntity=Journey.class,cascade=CascadeType.ALL)
	@JoinColumn(name="trips_idfk", referencedColumnName="trip_id")
	private List<Journey> journey;
	
	
	public float getAdmin_fk() {
		return admin_fk;
	}
	public void setAdmin_fk(float admin_fk) {
		this.admin_fk = admin_fk;
	}
	public int getTrip_id() {
		return trip_id;
	}
	public void setTrip_id(int trip_id) {
		this.trip_id = trip_id;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	public Trips() {
		super();
	}
	
	@Override
	public String toString() {
		return "Trips [trip_id=" + trip_id + ", origin=" + origin + ", destination=" + destination + ", distance="
				+ distance + ", price=" + price + "]";
	}
	
	
}
