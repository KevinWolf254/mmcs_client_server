package co.ke.proaktiv.io.controllers;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.ke.proaktiv.io.models.DeliveryReport;
import co.ke.proaktiv.io.models.Organisation;
import co.ke.proaktiv.io.models.User;
import co.ke.proaktiv.io.pojos.Sale;
import co.ke.proaktiv.io.pojos.reports.ReportRequest;
import co.ke.proaktiv.io.services.DeliveryReportService;
import co.ke.proaktiv.io.services.PaymentService;
import co.ke.proaktiv.io.services.UserService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@RestController
public class FileController {
	@Autowired
	private DeliveryReportService reportService;
	@Autowired
	private UserService userService;
	@Autowired
	private PaymentService paymentService;
	
	@GetMapping(value = "/secure/file/{fileName:.+}")
	public void getImportSubscriberTemplate(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName){	
		
		final File file = new File(CONTACTS_TEMPLATE_URI + fileName);
		
		if(file.exists()) {
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			response.setContentType(mimeType);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
					String.format("attachment; filename=\""+file.getName()+ "\""));
			response.setContentLength((int) file.length());
			
			try {
				final InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
				FileCopyUtils.copy(inputStream, response.getOutputStream());
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@PostMapping(value = "/reportPDF/delivery")
	public void getDeliveryReport(HttpServletResponse response, 
								  @RequestBody final ReportRequest request){
		
	    try {
	    	final InputStream jasperStream = this.getClass()
	    			.getResourceAsStream("/reports/delivery_report.jrxml");
	    	final JasperDesign design = JRXmlLoader.load(jasperStream);
			final JasperReport report = JasperCompileManager.compileReport(design);			
	      
			final Map<String, Object> params = new HashMap<>();
			final Optional<User> user = userService.findByEmail(request.getEmail());
			if(user.isPresent()) {				
			    final Organisation org = user.get().getOrganisation();
			    
			    final List<DeliveryReport> deliveryReport = reportService
			    		.search(request.getFrom(), request.getTo(), org.getId());
			    
			    final JRDataSource jrdatasource = new JRBeanCollectionDataSource(deliveryReport);
	
				params.put("datasource", jrdatasource);			
	
			    final JasperPrint jasperPrint = JasperFillManager.fillReport(report, params, jrdatasource);
	
				response.setContentType("application/x-pdf");
				response.setHeader(HttpHeaders.CONTENT_DISPOSITION,String
								.format("attachment; filename=delivery_report.pdf"));			
				
				final OutputStream outputStream = response.getOutputStream();
				JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);	
			}
	    } catch (JRException e) {
	    	e.printStackTrace();
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	@PostMapping(value = "/reportPDF/purchase")
	public void getPurchaseReport(HttpServletResponse response,
								  @RequestBody final ReportRequest request){
		 try {
		    	final InputStream jasperStream = this.getClass()
		    						.getResourceAsStream("/reports/purchase_report.jrxml");
				final JasperDesign design = JRXmlLoader.load(jasperStream);
				final JasperReport report = JasperCompileManager.compileReport(design);			
		      
			    final Map<String, Object> params = new HashMap<>();
				final Optional<User> user = userService.findByEmail(request.getEmail());
				if(user.isPresent()) {				
				    final Organisation org = user.get().getOrganisation();
			    
				    //retrieve all purchase from aeon_api
				    final List<Sale> purchases = paymentService
				    		.search(request.getFrom(), request.getTo(), org.getId());
	
				    final JRDataSource jrdatasource = new JRBeanCollectionDataSource(purchases);
	
					params.put("datasource", jrdatasource);			
	
				    final JasperPrint jasperPrint = JasperFillManager
				    					.fillReport(report, params, jrdatasource);
	
					response.setContentType("application/x-pdf");
					response.setHeader(HttpHeaders.CONTENT_DISPOSITION,String
										.format("attachment; filename=purchase_report.pdf"));			
					
					final OutputStream outputStream = response.getOutputStream();
					JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);	
				}
		    } catch (JRException e) {
		    	e.printStackTrace();
		    } catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	private static final String CONTACTS_TEMPLATE_URI = "./src/main/resources/templates/";
}
