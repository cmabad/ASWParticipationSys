package es.uniovi.asw.citizensLoader.reportWriter;

import java.util.logging.Logger;

public class WriteReportImpl implements WriteReport{
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Override
	public void writeReport(String... errors) {
		
		for (String error : errors)
			LOGGER.info(error);
		
	}

}
