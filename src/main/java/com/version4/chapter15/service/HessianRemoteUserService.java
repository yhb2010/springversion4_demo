package com.version4.chapter15.service;

import java.util.List;

import com.version4.chapter15.domain.RemoteUser;

public interface HessianRemoteUserService {

	public RemoteUser getUser(int userID);

	public List<RemoteUser> getAllUser();

}