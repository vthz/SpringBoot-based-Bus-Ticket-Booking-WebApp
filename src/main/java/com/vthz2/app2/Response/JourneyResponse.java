package com.vthz2.app2.Response;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JourneyResponse {
	private String origin;
	private String destination;
	private float price;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate journeyDate;

	public JourneyResponse(String origin, String destination, float price, LocalDate journeyDate) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.price = price;
		this.journeyDate = journeyDate;
	}
	public LocalDate getJourneyDate() {
		return journeyDate;
	}
	public void setJourneyDate(LocalDate journeyDate) {
		this.journeyDate = journeyDate;
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
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	

		
	
}
