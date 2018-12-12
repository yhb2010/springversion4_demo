package com;

import org.springframework.util.SerializationUtils;

public class Serialization {


	public static void main(String[] args) {
		byte[] b = SerializationUtils.serialize("yyy");
		System.out.println(SerializationUtils.deserialize(b));
	}
}
