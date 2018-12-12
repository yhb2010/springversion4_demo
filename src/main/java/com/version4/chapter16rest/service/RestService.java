package com.version4.chapter16rest.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.stereotype.Service;
import com.version4.mvc.chapter56.domain.Spittle;

@Service
public class RestService {

	private List<Spittle> spittles = new ArrayList<Spittle>();

	public RestService(){
		IntStream.range(1, 1000).forEach(i -> spittles.add(new Spittle(i, "Spittle" + i, new Date())));
	}

	public List<Spittle> spittles(int count){
		return spittles.stream().limit(count).collect(Collectors.toList());
	}

	public Spittle save(Spittle spittle){
		spittles.add(spittle);
		return spittle;
	}

	public Spittle spittleById(int id){
		return spittles.stream().filter(spittle -> spittle.getId() == id).findFirst().orElse(null);
	}

	public void delete(int id){
		spittles = spittles.stream().filter(spittle -> spittle.getId() != id).collect(Collectors.toList());
	}

	public void updateSpittleById(Spittle spittle){
		delete(spittle.getId());
		save(spittle);
	}

}
