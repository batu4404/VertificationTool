package test;

import java.io.File;

import core.utils.LauncherSpoon;

/**
 * Test các module của chương trình
 * test phần sinh ast từ spoon
 */
public class Test {
	public static void main(String[] args) {
		LauncherSpoon launcher = new LauncherSpoon();
		launcher.addInputResource("TestSpoon.java");
		launcher.buildModel();
	}
}
