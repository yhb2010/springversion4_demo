package com.mysqlmybatis;

public class Test {

	public static void main(String[] args) {

		QuestionDao studentDAO = (QuestionDao) new MyInvocationHandler().getInstance(QuestionDao.class);
		Question stu = studentDAO.getById(1);
        System.out.println(stu);

    }

}
