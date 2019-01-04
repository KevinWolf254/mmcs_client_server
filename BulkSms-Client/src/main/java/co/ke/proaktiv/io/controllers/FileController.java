package co.ke.proaktiv.io.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
	private static final Logger log = LoggerFactory.getLogger(FileController.class);

	@GetMapping(value = "/secure/file/{fileName:.+}")
	public void getImportSubscriberTemplate(final HttpServletRequest request, final HttpServletResponse response,
			@PathVariable("fileName") final String fileName){	
		final String filePath = this.getClass().getResource("/templates/"+fileName).getFile();
		final File file = new File(filePath);

		if(file.exists()) {
			final String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			response.setContentType(mimeType);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\""+file.getName()+ "\""));
			response.setContentLength((int) file.length());
			
			try {
				final InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				
			} catch (IOException e) {
				log.error("IOException: "+e.getMessage());
			}catch (Exception e) {
				log.error("Exception: "+e.getMessage());
			}
		}
	}	
}
