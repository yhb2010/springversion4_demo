package com.version4.chapter15.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.version4.chapter15.domain.RemoteUser;

@WebService
public interface JaxWsUserService {

	@WebMethod
	public RemoteUser getUser(int userID);

	@WebMethod
	public List<RemoteUser> getAllUser();

}
