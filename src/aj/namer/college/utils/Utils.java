package aj.namer.college.utils;

import org.apache.commons.io.FilenameUtils;

public class Utils {
	
	public static final String NEW_LINE = "\r\n";
	
	public static String getComment(String fileName) {
		String extension = FilenameUtils.getExtension(fileName);
		if (extension.contains("c")) {
			return "//";
		} else if (extension.equals("py")) {
			return "#";
		} else if (extension.equals("java")) {
			return "//";
		}
		return "";
	}

}
