package com.pCloudy.testNG;

import java.lang.reflect.Field;
import java.util.Map;

public class Env {
	public static void main(String[] args) throws ReflectiveOperationException {
		//updateEnv("NAME_Ahmad", "Ahmad Syaeed");
		print();
	}

	@SuppressWarnings({ "unchecked" })
	public static void updateEnv(String name, String val) throws ReflectiveOperationException {
		Map<String, String> env = System.getenv();
		Field field = env.getClass().getDeclaredField("m");
		field.setAccessible(true);
		((Map<String, String>) field.get(env)).put(name, val);
	}

	private static void print() {
		System.out.println();
		Map<String, String> env = System.getenv();
		for (String envName : env.keySet()) {
			System.out.format("%s=%s%n", envName, env.get(envName));
		}
		System.out.println();
	}
}
