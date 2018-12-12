package com.version4.mvc.chapter56.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import com.version4.mvc.chapter56.domain.Spitter;
import com.version4.mvc.chapter56.domain.Spittle;
import com.version4.mvc.chapter56.exception.DuplicateSpittleException;

@Service
public class SpittleRepositoryImpl implements SpittleRepository {

	private List<Spittle> spittles = new ArrayList<Spittle>();
	private Map<String, Spitter> spitters = new HashMap<String, Spitter>();

	public SpittleRepositoryImpl(){
		IntStream.range(1, 1000).forEach(i -> spittles.add(new Spittle(i, "Spittle" + i, new Date())));
	}

	@Override
	public List<Spittle> findSpittles(int max, int count) {
		return spittles.stream().skip(max).limit(count).collect(Collectors.toList());
	}

	@Override
	public Spittle findOne(int spittleId) {
		return spittles.stream().filter(spittle -> spittle.getId() == spittleId).findFirst().orElse(null);
	}

	@Override
	public void save(Spitter spitter) throws DuplicateSpittleException {
		if(spitters.get(spitter.getUserName()) != null){
			throw new DuplicateSpittleException("100", "重复定义");
		}
		spitters.put(spitter.getUserName(), spitter);
	}

	@Override
	public Spitter findByUserName(String userName) {
		return spitters.get(userName);
	}

}
