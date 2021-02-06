package com.diagnostic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.diagnostic.model.AllInvestigationRequestEntityContainer;
import com.diagnostic.model.InvestigationRequest;
import com.diagnostic.model.Patient;
import com.diagnostic.model.ReportResponse;
import com.diagnostic.model.ReportResponseEntity;
import com.diagnostic.model.UserInvestigationReport;
import com.diagnostic.repository.InvestigationRepository;
import com.diagnostic.repository.InvestigationRequestRepository;
import com.diagnostic.repository.PatientRepository;
import com.diagnostic.service.InvestigationRequestService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;

@CrossOrigin("*")
@RestController
public class InvestigationRequestController {

	@Autowired
	private InvestigationRequestService investigationRequestService;

	@Autowired
	private InvestigationRequestRepository investigationRequestRepository;

	@Autowired
	private InvestigationRepository investigationRepository;

	@Autowired
	private PatientRepository patientRepository;

//	@Bean
//	public WebMvcConfigurer configure() {
//		return new WebMvcConfigurer() {
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/*").allowedOrigins("http://localhost:9090");
//			}
//			
//		};
//	}

	// http://localhost:8080/categoryinvestigation/1
	@PostMapping("/investigationrequest/{providerId}")
	ResponseEntity<?> createInvestigationRequest(@PathVariable(value = "providerId") int providerId,
			@RequestParam("item") String selectedItemString, @RequestParam("patient") String patientObject)
			throws JsonParseException, JsonMappingException, IOException {

		Patient patient = new ObjectMapper().readValue(patientObject, Patient.class);

		String[] selectedItem = selectedItemString.split(",");

		List<InvestigationRequest> resultInvestigationRequest = investigationRequestService
				.createInvestigationRequest(providerId, selectedItem, patient);

		if (resultInvestigationRequest != null) {
			return ResponseEntity.ok().body("successfully created");
		} else {
			return ResponseEntity.badRequest().body("Fail to create");
		}

	}

	// http://localhost:8080/investigationrequestadmin/1
	@PostMapping("/investigationrequestadmin/{providerId}")
	ResponseEntity<?> createInvestigationRequestByAdmin(@PathVariable(value = "providerId") int providerId,
			@RequestParam("selectedInvestigation") String selectedInvestigationString,
			@RequestParam("patientObject") String patientObject)
			throws JsonParseException, JsonMappingException, IOException {

		Patient patient = new ObjectMapper().readValue(patientObject, Patient.class);
		String[] selectedInvestigation = selectedInvestigationString.split(",");

		System.out.println("patient=" + patient.toString() + " \n\n");
		System.out.println(" " + selectedInvestigation[0]);

//		if (reportImage != null) {
//			object.setImageFileName(reportImage.getOriginalFilename());
//			object.setReportImage(reportImage.getBytes());
//		{"billNumber":"b22","name":" test","mobile":"01521453788","age":"22","gender":"male","referenceDoctor":"null","paymentAmount":"2222", "paymentMode":""}
//		}

		// List<InvestigationRequest> resultInvestigationRequest = null;
		String resultInvestigationRequest = investigationRequestService
				.createInvestigationRequestByAdmin(selectedInvestigation, patient, providerId);

		if (resultInvestigationRequest.equals("0")) {
			return ResponseEntity.badRequest().body("An error is occured");
		} else if (resultInvestigationRequest.equals("2")) {
			return ResponseEntity.badRequest().body("Select another billnumber");
		} else {
			return ResponseEntity.ok().body(resultInvestigationRequest);
		}

//		return ResponseEntity.badRequest().body("Select another billnumber");

//		{"billNumber":"b234567","name":"name","mobile":"mobile","age":"age","gender":"gender","referenceDoctor":"referenceDoctor","paymentAmount":"paymentAmount", "paymentMode":"paymentMode"}
//      1,2

//		createInvestigationRequestByAdmin(@PathVariable(value = "providerId") int providerId,
//				@RequestParam("object") String Object, @RequestParam(required = false) MultipartFile reportImage)
	}

	// http://localhost:8080/setreport/1
	@PostMapping("/setreport/{providerId}")
	ResponseEntity<?> setReportByAdmin(@PathVariable(value = "providerId") int providerId,
			@RequestParam("reportResult") String reportResult,
			@RequestParam("investigationResuestId") String investigationResuestId,
			@RequestParam("labDoctorName") String labDoctorTitle)
			throws JsonParseException, JsonMappingException, IOException {

		String[] reportResultList = reportResult.split(",");
		String[] investigationResuestIdList = investigationResuestId.split(",");

		System.out.println(labDoctorTitle + " \n\n\n\n\n" + investigationResuestIdList.length);
//		for (int i = 0; i < investigationResuestIdList.length; i++) {
//			System.out.println(reportResultList[i] + ' ' + investigationResuestIdList[i]);
//		}

//		boolean result = true;

		boolean result = investigationRequestService.setReportByAdmin(reportResultList, investigationResuestIdList,
				providerId, labDoctorTitle);

		if (result) {
			return ResponseEntity.ok().body("successfully updated");
		} else {
			return ResponseEntity.badRequest().body("Fail to update");
		}

	}

	// http://localhost:8080/sendmail/1/159
	@PostMapping("/sendmail/{investigationRequestId}/{providerId}")
	ResponseEntity<?> sendMail(@PathVariable(value = "investigationRequestId") int investigationRequestId,
			@PathVariable(value = "providerId") int providerId, @RequestBody String gmail)
			throws JsonParseException, JsonMappingException, IOException, MailException, MessagingException {

		int result = investigationRequestService.sendMailToPatient(gmail, providerId, investigationRequestId);

		if (result != 0) {
			return ResponseEntity.ok().body("successfully sent");
		} else {
			return ResponseEntity.badRequest().body("Fail to sent");
		}
//		ebrahimkhanobak@gmail.com

	}

	// http://localhost:8080/investigationrequestbyadmin/b000008/1
	@GetMapping("/investigationrequestbyadmin/{billno}/{providerId}")
	public ResponseEntity<List<ReportResponse>> fetchUserInvestigationRequestByAdmin(
			@PathVariable(value = "billno") String billNo, @PathVariable(value = "providerId") int providerId) {

		List<ReportResponse> userInvestigationReports = investigationRequestService
				.UserInvestigationReportByAdmin(billNo, providerId);

		return ResponseEntity.ok().body(userInvestigationReports);
	}

	// http://localhost:8080/reportResponceList
	@GetMapping("/reportResponceList")
	public ResponseEntity<List<ReportResponseEntity>> reportResponceList() {

		List<ReportResponseEntity> userInvestigationReports = investigationRequestService.param();

		return ResponseEntity.ok().body(userInvestigationReports);
	}

	// http://localhost:8080/investigationrequestbyadmin/1
	@GetMapping("/investigationrequestbyadmin/{providerId}")
	public ResponseEntity<List<AllInvestigationRequestEntityContainer>> fetch(
			@PathVariable(value = "providerId") int providerId) {

		List<AllInvestigationRequestEntityContainer> userInvestigationReports = investigationRequestService
				.allInvestigationRequestforAdmin(providerId);

		return ResponseEntity.ok().body(userInvestigationReports);
	}

	// http://localhost:8080/investigationrequestbyuser/b456789/2345678/1
	@GetMapping("/investigationrequestbyuser/{billno}/{mobile}/{providerId}")
	public ResponseEntity<List<ReportResponse>> fetchUserInvestigationRequestByUser(
			@PathVariable(value = "billno") String billNo, @PathVariable(value = "providerId") int providerId,
			@PathVariable(value = "mobile") String mobile) {

		List<ReportResponse> userInvestigationReports = investigationRequestService.UserInvestigationReport(billNo,
				mobile, providerId);

		return ResponseEntity.ok().body(userInvestigationReports);

		// return ResponseEntity.ok().body(null);
	}

	// http://localhost:8080/investigationuserreport/1/01521453788
	@GetMapping("/investigationuserreport/{providerId}/{mobile}")
	public ResponseEntity<List<UserInvestigationReport>> fetchUserInvestigationReport(
			@PathVariable(value = "providerId") int providerId, @PathVariable(value = "mobile") String mobile) {

//		List<UserInvestigationReport> userInvestigationReports = investigationRequestService
//				.UserInvestigationReport(providerId, mobile);

		List<UserInvestigationReport> userInvestigationReports = null;

		return ResponseEntity.ok().body(userInvestigationReports);
	}

	// http://localhost:8080/pdf
	@GetMapping(value = "/pdf")
	public ResponseEntity<InputStreamResource> Pdf() throws IOException, DocumentException {

		byte[] data = investigationRequestService.Pdf();
		ByteArrayInputStream bis = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=Report.pdf");

		if (data != null) {
			bis = new ByteArrayInputStream(data);
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new InputStreamResource(bis));
		} else {
			return ResponseEntity.badRequest().body(null);
		}

		// produces = MediaType.APPLICATION_PDF_VALUE
	}

	@GetMapping("/file")
	public String savebook() throws IOException {

//		File resource = new ClassPathResource("/Diagnostic-Delivery-Service/src/main/resources/static/css/custom.css")
//				.getFile();
//		String employees = new String(Files.readAllBytes(resource.toPath()));
//
//		System.out.println("Joe Employee,Jan Employee,James T. Employee" + employees);

		String data = "";
		ClassPathResource cpr = new ClassPathResource("static/css/custom.css");
		try {
			byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
			data = new String(bdata, StandardCharsets.UTF_8);
			System.out.println("data" + data);
		} catch (IOException e) {
			System.out.println("error");
		}

		return "index";
	}

	// http://localhost:8080/pdfreport/b000008/4/BIOCHEMISTRY/1
	// http://localhost:8080/pdfreport/b000008/3/X-RAY/1
	@GetMapping(value = "/pdfreport/{billNumber}/{investigationResuestId}/{departmentName}/{providerId}")
	public ResponseEntity<InputStreamResource> reportPdf(@PathVariable(value = "billNumber") String billNumber,
			@PathVariable(value = "investigationResuestId") int investigationResuestId,
			@PathVariable(value = "departmentName") String departmentName,
			@PathVariable(value = "providerId") int providerId) throws IOException, DocumentException {

		byte[] data = investigationRequestService.userReportPdf(billNumber, investigationResuestId, departmentName,
				providerId);
		ByteArrayInputStream bis = null;
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=Report.pdf");

		if (data != null) {
			bis = new ByteArrayInputStream(data);
			return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(new InputStreamResource(bis));
		} else {
			return ResponseEntity.badRequest().body(null);
		}

		// produces = MediaType.APPLICATION_PDF_VALUE
	}

	// http://localhost:8080/deletereport/b000008/3/X-RAY/1
	@DeleteMapping(value = "/deletereport/{billNumber}/{investigationResuestId}/{departmentName}/{providerId}")
	public ResponseEntity<String> deleteReportResult(@PathVariable(value = "billNumber") String billNumber,
			@PathVariable(value = "investigationResuestId") int investigationResuestId,
			@PathVariable(value = "departmentName") String departmentName,
			@PathVariable(value = "providerId") int providerId) throws IOException, DocumentException {

		boolean result = investigationRequestService.deleteReportResult(billNumber, investigationResuestId,
				departmentName, providerId);

		if (result) {
			return ResponseEntity.ok().body("Deleted Successfully");
		} else {
			return ResponseEntity.badRequest().body("Fail to delete");
		}

		// produces = MediaType.APPLICATION_PDF_VALUE
	}

//	http://localhost:8080/imagereportdownload/216
	@GetMapping(value = "/imagereportdownload/{investigationRequestId}")
	public ResponseEntity<InputStreamResource> imageReport(
			@PathVariable(value = "investigationRequestId") int investigationRequestId) {

		Optional<InvestigationRequest> in = investigationRequestRepository.findById(investigationRequestId);

		byte[] value = in.get().getReportImage();
		ByteArrayInputStream imagebyte = new ByteArrayInputStream(value);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=ReportImage.jpg");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(new InputStreamResource(imagebyte));

		// ,
		// produces = MediaType.APPLICATION_PDF_VALUE, , produces =
		// MediaType.IMAGE_JPEG_VALUE
	}

	// http://localhost:8080/investigationrequest/1
	@PostMapping("/investigationrequestn/{investigationrequestid}")
	ResponseEntity<?> updateInvestigation(@PathVariable(value = "investigationrequestid") int investigationRequestId,
			@RequestParam(required = false) MultipartFile reportImage,
			@RequestParam("investigationrequest") String investigationRequestObject)
			throws JsonParseException, JsonMappingException, IOException {

		InvestigationRequest investigationRequest = new ObjectMapper().readValue(investigationRequestObject,
				InvestigationRequest.class);

		investigationRequest.setImageFileName(reportImage.getOriginalFilename());
		investigationRequest.setReportImage(reportImage.getBytes());

		InvestigationRequest resultInvestigationRequest = investigationRequestService
				.updateInvestigationRequest(investigationRequestId, investigationRequest);

		if (resultInvestigationRequest != null) {
			return ResponseEntity.ok().body("successfully updated");
		} else {
			return ResponseEntity.badRequest().body("Fail to update");
		}
	}

}