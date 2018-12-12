package com.version4.chapter17jms.service;

import com.version4.chapter10.domain.Member;

public interface AlertService {

	/**在spittle创建时，发送异步提醒
	 * @param member
	 */
	void sendSpittleAlert(Member member);

}
