package com.ptit.pttk.assignment.model;

import java.util.Date;

public class Staff extends Member {
	public Staff(String username, String password, String fullname, Date dateOfBirth, Integer gender, String phone, String address) {
		super(username, password, fullname, dateOfBirth, gender, phone, address);
	}
}
