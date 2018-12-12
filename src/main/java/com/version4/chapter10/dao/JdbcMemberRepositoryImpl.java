package com.version4.chapter10.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.version4.chapter10.domain.Member;
import com.version4.mvc.chapter56.exception.DuplicateSpittleException;

@Repository
public class JdbcMemberRepositoryImpl implements MemberRepository {

	@Autowired
	//可以再包装一层，不和JdbcTemplate耦合
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<Member> findSpittles(int max, int count) {
		return null;
	}

	@Override
	public void save(Member spitter, String newName) throws DuplicateSpittleException {
		jdbcTemplate.update("update qz_member set userName = ? where userName = ?", newName, spitter.getUserName());
	}

	@Override
	public Member findByUserName(String userName) {
		return jdbcTemplate.queryForObject(
			"select userName, realName, telPhone from qz_member where userName = ?",
			//可以使用java8的lambda表达式
			(rs, rowNum) -> {return new Member(
				rs.getString("userName"),
				rs.getString("realName"),
				rs.getString("telPhone")
			);},
			userName);
	}

	private static final class SpitterRowMapper implements RowMapper<Member>{

		@Override
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Member(
				rs.getString("userName"),
				rs.getString("realName"),
				rs.getString("telPhone")
			);
		}

	}

}
