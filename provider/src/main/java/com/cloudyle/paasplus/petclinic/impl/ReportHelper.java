package com.cloudyle.paasplus.petclinic.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudyle.paasplus.services.report.IReportService;
import com.cloudyle.paasplus.services.report.data.IReportExportItem;
import com.cloudyle.paasplus.services.report.enums.ReportProperties;
import com.cloudyle.paasplus.services.report.exceptions.CreateReportException;
import com.cloudyle.paasplus.services.report.util.Column;
import com.cloudyle.paasplus.services.report.util.ExportTypes;

public class ReportHelper {

	private static final Logger logger = LoggerFactory
			.getLogger(ReportHelper.class);

	private String dataDir = System.getProperty("karaf.data");

	private IReportService reportService;

	public List<Column> createColumns() {
		final List<Column> columnList = new ArrayList<Column>();

		columnList.add(new Column("Name", "name", String.class.getName(), 30));

		columnList.add(new Column("Type", "type", String.class.getName(), 50));
		columnList.add(new Column("Date of Birth", "bithDate", Date.class
				.getName(), 30));
		;
		columnList.add(new Column("Owner Last Name", "owner.lastName",
				String.class.getName(), 50));
		columnList.add(new Column("Owner First Name", "owner.firstName",
				String.class.getName(), 50));
		;
		return columnList;
	}

	public byte[] createReport(String template, String reportName,
			Collection<?> data) {
		try {
			IReportExportItem reportExportItem = this.reportService
					.createDynamicReport(template, ExportTypes.PDF,
							createColumns(), data, createReportProperties());

			if (reportExportItem != null) {
				exportStreamToFile(reportExportItem.getInputStream(),
						reportExportItem.getExportType(), getReportDirectory(),
						reportName, true);
			}
		} catch (CreateReportException | IOException e) {
			logger.error("[init] Could not create or export report.", e);
		}
		return null;
	}

	public Map<String, Object> createReportProperties() {

		final Map<String, Object> propertyMap = new HashMap<String, Object>();

		propertyMap.put(ReportProperties.TITLE.getValue(), new String(
				"Cloudyle dynamic report"));
		propertyMap.put(ReportProperties.SUB_TITLE.getValue(), new String(
				"created " + new Date()));
		propertyMap.put(ReportProperties.GROUPS.getValue(), new Integer(2));
		propertyMap.put(
				ReportProperties.PRINT_BACKGROUND_ON_ODD_ROWS.getValue(),
				new Boolean(true));
		propertyMap.put(ReportProperties.USE_FULL_PAGE_WIDTH.getValue(),
				new Boolean(true));
		propertyMap.put(ReportProperties.IMAGE_BANNER_WIDTH.getValue(),
				new Integer(90));
		propertyMap.put(ReportProperties.IMAGE_BANNER_HEIGTH.getValue(),
				new Integer(30));
		propertyMap.put(ReportProperties.IMAGE_BANNER_ALIGNMENT.getValue(),
				"CENTER");
		propertyMap.put(ReportProperties.PAGE_SIZE_AND_ORIENTATION.getValue(),
				"PORTRAIT");
		propertyMap.put(ReportProperties.IMAGE_BANNER_NAME.getValue(),
				new String(getImageDirectory() + "com.cloudyle.jpg"));

		return propertyMap;
	}

	public void exportStreamToFile(final InputStream inputStream,
			final ExportTypes exportType, final String reportDirectory,
			final String fileName, final Boolean addTimeStamp)
			throws FileNotFoundException, IOException {

		final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss_SSS");
		df.format(new Date());
		File outFile;

		if (inputStream != null) {
			if (addTimeStamp) {
				outFile = new File(reportDirectory + fileName + "_"
						+ df.format(new Date()) + "."
						+ exportType.toString().toLowerCase());
			} else {
				outFile = new File(reportDirectory + fileName + "."
						+ exportType.toString().toLowerCase());
			}

			logger.debug("[exportStreamToFile] Writing the report to File-System...");
			final File parentFile = outFile.getParentFile();
			if (parentFile != null) {
				parentFile.mkdirs();
			}
			final OutputStream out = new FileOutputStream(outFile);
			final byte buf[] = new byte[300000];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			inputStream.close();
		} else {
			logger.error("[exportStreamToFile] Could not export report, the according inputStream is null...");
		}

	}

	public String getDataDir() {
		return dataDir;
	}

	protected String getImageDirectory() {
		return getDataDir() + "/reporting/resources/images/";
	}

	protected String getReportDirectory() {
		return getDataDir() + "/reporting/resources/reports/";
	}

	public IReportService getReportService() {
		return reportService;
	}

	protected String getTemplateDirectory() {
		return getDataDir() + "/reporting/resources/templates/";
	}

	public void setDataDir(String dataDir) {
		this.dataDir = dataDir;
	}

	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

}
