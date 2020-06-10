package com.xuhao.utils;

import java.io.File;

public class UrlManager {
	private static File file = new File(UrlManager.class.getClassLoader().getResource("").getPath());
	private static String path = file.getAbsolutePath();
	public static final String CHECKPHOTO_URL = path + "img/checkbossphoto/";
}
