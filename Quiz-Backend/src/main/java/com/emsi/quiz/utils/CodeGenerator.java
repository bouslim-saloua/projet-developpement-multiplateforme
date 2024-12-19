package com.emsi.quiz.utils;


public class CodeGenerator {
	public static String generateAccessCode() {
		return String.format("%08d", (int) (Math.random() * 100000000));	}
}
