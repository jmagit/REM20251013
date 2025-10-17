package com.example.core.domain.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

public class MisValidadoresTest {
	
	@ParameterizedTest
	@ValueSource(strings = { "12345678Z", "12345678z", "4g" })
	@NullSource
	void testNullOK(String caso) {
		// Arrange
		MisValidadores validador = new MisValidadores();
		
		// Act
		boolean actual = validador.isNif(caso);
		
		// Assert
		assertTrue(actual);
	}
	
	@ParameterizedTest
	@ValueSource(strings = { "12345678", "5g","z111" })
	@EmptySource
	void testNullKO(String caso) {
		// Arrange
		MisValidadores validador = new MisValidadores();
		
		// Act
		boolean actual = validador.isNif("");
		
		// Assert
		assertFalse(actual);
	}
	
	@Test
	void testIsNotNifOK() {
		assertTrue((new MisValidadores()).isNotNif(""));
	}
	@Test
	void testIsNotNifKO() {
		assertFalse((new MisValidadores()).isNotNif("4g"));
	}
	
}
