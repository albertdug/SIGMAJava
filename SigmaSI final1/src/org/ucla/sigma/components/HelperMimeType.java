package org.ucla.sigma.components;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public abstract class HelperMimeType {

	private static final Map<String, String> fileExtensionMap = new HashMap<String, String>();

	static {
		// imagenes
		fileExtensionMap.put("png", "image/png");
		fileExtensionMap.put("jpg", "image/jpeg");
		fileExtensionMap.put("jpeg", "image/jpeg");
		fileExtensionMap.put("gif", "image/gif");
		fileExtensionMap.put("ico", "image/x-icon");
		// pdf
		fileExtensionMap.put("pdf", "application/pdf");
		// xml
		fileExtensionMap.put("rss", "application/rss+xml");
		fileExtensionMap.put("xml", "application/xml");
		// comprimido
		fileExtensionMap.put("zip", "application/zip");
		fileExtensionMap.put("rar", "application/x-rar-compressed");
		// office
		fileExtensionMap.put("doc", "application/msword");
		fileExtensionMap.put("dot", "application/msword");
		fileExtensionMap
				.put("docx",
						"application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		fileExtensionMap
				.put("dotx",
						"application/vnd.openxmlformats-officedocument.wordprocessingml.template");
		fileExtensionMap.put("docm",
				"application/vnd.ms-word.document.macroEnabled.12");
		fileExtensionMap.put("dotm",
				"application/vnd.ms-word.template.macroEnabled.12");
		fileExtensionMap.put("xls", "application/vnd.ms-excel");
		fileExtensionMap.put("xlt", "application/vnd.ms-excel");
		fileExtensionMap.put("xla", "application/vnd.ms-excel");
		fileExtensionMap
				.put("xlsx",
						"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		fileExtensionMap
				.put("xltx",
						"application/vnd.openxmlformats-officedocument.spreadsheetml.template");
		fileExtensionMap.put("xlsm",
				"application/vnd.ms-excel.sheet.macroEnabled.12");
		fileExtensionMap.put("xltm",
				"application/vnd.ms-excel.template.macroEnabled.12");
		fileExtensionMap.put("xlam",
				"application/vnd.ms-excel.addin.macroEnabled.12");
		fileExtensionMap.put("xlsb",
				"application/vnd.ms-excel.sheet.binary.macroEnabled.12");
		fileExtensionMap.put("ppt", "application/vnd.ms-powerpoint");
		fileExtensionMap.put("pot", "application/vnd.ms-powerpoint");
		fileExtensionMap.put("pps", "application/vnd.ms-powerpoint");
		fileExtensionMap.put("ppa", "application/vnd.ms-powerpoint");
		fileExtensionMap
				.put("pptx",
						"application/vnd.openxmlformats-officedocument.presentationml.presentation");
		fileExtensionMap
				.put("potx",
						"application/vnd.openxmlformats-officedocument.presentationml.template");
		fileExtensionMap
				.put("ppsx",
						"application/vnd.openxmlformats-officedocument.presentationml.slideshow");
		fileExtensionMap.put("ppam",
				"application/vnd.ms-powerpoint.addin.macroEnabled.12");
		fileExtensionMap.put("pptm",
				"application/vnd.ms-powerpoint.presentation.macroEnabled.12");
		fileExtensionMap.put("potm",
				"application/vnd.ms-powerpoint.presentation.macroEnabled.12");
		fileExtensionMap.put("ppsm",
				"application/vnd.ms-powerpoint.slideshow.macroEnabled.12");
		// open office
		fileExtensionMap.put("odt", "application/vnd.oasis.opendocument.text");
		fileExtensionMap.put("ott",
				"application/vnd.oasis.opendocument.text-template");
		fileExtensionMap.put("oth",
				"application/vnd.oasis.opendocument.text-web");
		fileExtensionMap.put("odm",
				"application/vnd.oasis.opendocument.text-master");
		fileExtensionMap.put("odg",
				"application/vnd.oasis.opendocument.graphics");
		fileExtensionMap.put("otg",
				"application/vnd.oasis.opendocument.graphics-template");
		fileExtensionMap.put("odp",
				"application/vnd.oasis.opendocument.presentation");
		fileExtensionMap.put("otp",
				"application/vnd.oasis.opendocument.presentation-template");
		fileExtensionMap.put("ods",
				"application/vnd.oasis.opendocument.spreadsheet");
		fileExtensionMap.put("ots",
				"application/vnd.oasis.opendocument.spreadsheet-template");
		fileExtensionMap.put("odc", "application/vnd.oasis.opendocument.chart");
		fileExtensionMap.put("odf",
				"application/vnd.oasis.opendocument.formula");
		fileExtensionMap.put("odb",
				"application/vnd.oasis.opendocument.database");
		fileExtensionMap.put("odi", "application/vnd.oasis.opendocument.image");
		fileExtensionMap.put("oxt", "application/vnd.openofficeorg.extension");
	}

	public static String getMimeType(File file) {
		String fileName = file.getName();
		return fileExtensionMap.get(fileName.substring(
				fileName.lastIndexOf('.') + 1, fileName.length()));
	}

	public static String getMimeType(String extension) {
		return fileExtensionMap.get(extension.toLowerCase());
	}
}
