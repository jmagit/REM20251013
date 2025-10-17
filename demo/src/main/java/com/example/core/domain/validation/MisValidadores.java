package com.example.core.domain.validation;

public class MisValidadores {

	public boolean isNif(String value) {
		if(value == null) return true;
		value = value.toUpperCase();
		if (!value.matches("^\\d{1,8}[A-Z]$") || Integer.parseInt(value.substring(0, value.length() - 1)) == 0)
			return false;
		return "TRWAGMYFPDXBNJZSQVHLCKE".charAt(Integer.parseInt(value.substring(0, value.length() - 1)) % 23) == value
				.charAt(value.length() - 1);
	}
	
	public boolean isNotNif(String value) {
		return !isNif(value);
	}
}
