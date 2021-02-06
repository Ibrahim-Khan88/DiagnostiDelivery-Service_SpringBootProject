package com.diagnostic.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.diagnostic.model.AllInvestigationRequestEntity;
import com.diagnostic.model.AllInvestigationRequestEntityContainer;
import com.diagnostic.model.Department;
import com.diagnostic.model.Investigation;
import com.diagnostic.model.InvestigationRequest;
import com.diagnostic.model.InvestivationParameterResult;
import com.diagnostic.model.LabDoctor;
import com.diagnostic.model.ParamGroup;
import com.diagnostic.model.Parameter;
import com.diagnostic.model.Patient;
import com.diagnostic.model.Provider;
import com.diagnostic.model.ReportResponse;
import com.diagnostic.model.ReportResponseEntity;
import com.diagnostic.repository.DepartmentRepository;
import com.diagnostic.repository.InvestigationRepository;
import com.diagnostic.repository.InvestigationRequestRepository;
import com.diagnostic.repository.InvestivationParameterResultRepository;
import com.diagnostic.repository.LabDoctorRepository;
import com.diagnostic.repository.ParameterRepository;
import com.diagnostic.repository.PatientRepository;
import com.diagnostic.repository.ProviderRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

@Service
public class InvestigationRequestService {

	@Autowired
	private InvestigationRequestRepository investigationRequestRepository;

	@Autowired
	private ProviderRepository providerRepository;

	@Autowired
	private InvestigationRepository investigationRepository;

	@Autowired
	private InvestivationParameterResultRepository investivationParameterResultRepository;

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private ParameterRepository parameterRepository;

	@Autowired
	private LabDoctorRepository labDoctorRepository;

	public List<InvestigationRequest> createInvestigationRequest(int providerId, String[] selectedItem,
			Patient patient) {

		Optional<Provider> providerInvestigation = providerRepository.findById(providerId);
		List<InvestigationRequest> resultInvestigationRequest = new ArrayList<>();

		if (providerInvestigation.isPresent()) {

			Patient patientResult = patientRepository.save(patient);

			for (int i = 0; i < selectedItem.length; i++) {

				Optional<Investigation> investigation = investigationRepository
						.findById(Integer.parseInt(selectedItem[i]));
				InvestigationRequest investigationRequest = new InvestigationRequest();

				if (investigation.isPresent()) {
					investigationRequest.setInvestigation(investigation.get());
					investigationRequest.setPatient(patientResult);
					resultInvestigationRequest.add(investigationRequestRepository.save(investigationRequest));
				}

			}

			return resultInvestigationRequest;
		}

		return null;

	}

	public List<InvestigationRequest> fetchInvestigationRequestByProviderId(int providerId) {

		String image_encode;

		Optional<Provider> providerInvestigation = providerRepository.findById(providerId);

		if (providerInvestigation.isPresent()) {

			List<InvestigationRequest> investigationRequestList = investigationRequestRepository.findAll();

			for (int i = 0; i < investigationRequestList.size(); i++) {

				if (investigationRequestList.get(i).getImageFileName() != null) {

					image_encode = convertToBase64(investigationRequestList.get(i).getImageFileName(),
							investigationRequestList.get(i).getReportImage());
					// investigationRequestList.get(i).setImage_encode(image_encode);
				}
			}

			return investigationRequestList;

		}

		return null;

	}

	public InvestigationRequest updateInvestigationRequest(int investigationRequestId,
			InvestigationRequest investigationRequest) {

		Optional<InvestigationRequest> optianalInvestigationRequest = investigationRequestRepository
				.findById(investigationRequestId);

		if (optianalInvestigationRequest.isPresent()) {
			optianalInvestigationRequest.get().setImageFileName(investigationRequest.getImageFileName());
			optianalInvestigationRequest.get().setStatus(investigationRequest.getStatus());
			optianalInvestigationRequest.get().setReportImage(investigationRequest.getReportImage());
			optianalInvestigationRequest.get().setReportText(investigationRequest.getReportText());

			return investigationRequestRepository.save(optianalInvestigationRequest.get());
		}

		return null;
	}

	public byte[] userReportPdf(String billNumber, int investigationResuestId, String departmentName, int providerId)
			throws IOException, DocumentException {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter writer = PdfWriter.getInstance(document, out);

		PdfPTable reportResultTable = new PdfPTable(new float[] { 25, 25, 25, 25 });
		PdfPTable thReportResultTable = new PdfPTable(new float[] { 25, 25, 25, 25 });
		Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.BOLD, BaseColor.BLACK);
		Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f, Font.BOLD, BaseColor.BLACK);
		Font commonFont = new Font(FontFamily.TIMES_ROMAN, 12.0f);
		Font doctorFont = new Font(FontFamily.TIMES_ROMAN, 8.0f, Font.BOLD);
		Font endReportFont = new Font(FontFamily.TIMES_ROMAN, 8.0f);

		CustomDashedLineSeparator thReportResultTableSeparator = new CustomDashedLineSeparator();
		thReportResultTableSeparator.setDash(0);
		thReportResultTableSeparator.setGap(0);
		thReportResultTableSeparator.setLineWidth(1);
		Chunk thReportResultTableSeparatorLinebreak = new Chunk(thReportResultTableSeparator);

		String patientName, reportResult = "", age, gender, reportCreateDate = null, investigationName = null,
				referanceDoctor;

		LabDoctor doctor = new LabDoctor();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mmaa");
		int count = 1, y_axis = 270;

		Department department = departmentRepository.findByName(departmentName);
		Patient patient = patientRepository.findByBillNumber(billNumber);
		InvestigationRequest investigationRequest = investigationRequestRepository
				.findByIdAndProviderId(investigationResuestId, providerId);

		if (department.getType().equals("List")) {

			List<InvestigationRequest> investigationRequestslist = investigationRequestRepository
					.fetchInvesrigationRequestBydeptAndbillIdAndProviderId(billNumber, departmentName, providerId);

			if (investigationRequestslist.get(0) != null) {
				doctor = investigationRequestslist.get(0).getLabDoctor();
			}

			reportResultTable.setWidthPercentage(100);
			reportResultTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			reportResultTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			reportResultTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			reportResultTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			reportResultTable.getDefaultCell().setFixedHeight(20);
			reportResultTable.setSpacingAfter(0);
			reportResultTable.setSpacingBefore(0);

			thReportResultTable.setWidthPercentage(100);
			thReportResultTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			thReportResultTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			thReportResultTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			thReportResultTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			thReportResultTable.getDefaultCell().setFixedHeight(20);
			thReportResultTable.setSpacingAfter(0);
			thReportResultTable.setSpacingBefore(0);

			thReportResultTable.addCell(new Phrase(new Chunk("Investigation Name", boldFont)));
			thReportResultTable.addCell(new Phrase(new Chunk("Result", boldFont)));
			thReportResultTable.addCell(new Phrase(new Chunk("Unit", boldFont)));
			thReportResultTable.addCell(new Phrase(new Chunk("Reference Range", boldFont)));

			reportCreateDate = simpleDateFormat.format(investigationRequestslist.get(0).getLastModifiedDate());

			for (int i = 0; i < investigationRequestslist.size(); i++) {

				reportResultTable.addCell(new Phrase(
						new Chunk(investigationRequestslist.get(i).getInvestigation().getName(), commonFont)));

				if (investigationRequestslist.get(i).getReportText() != null
						&& !investigationRequestslist.get(i).getReportText().equals("")) {
					reportResultTable.addCell(
							new Phrase(new Chunk(investigationRequestslist.get(i).getReportText(), commonFont)));
				} else {
					reportResultTable.addCell(new Phrase(new Chunk("Not set", commonFont)));
				}

				reportResultTable.addCell(new Phrase(
						new Chunk(investigationRequestslist.get(i).getInvestigation().getUnit(), commonFont)));
				reportResultTable.addCell(new Phrase(
						new Chunk(investigationRequestslist.get(i).getInvestigation().getRefValue(), commonFont)));

			}

		} else if (department.getType().equals("Individual")) {

			System.out.println("Individual type");

			if (investigationRequest != null) {

				System.out.println("Individual type present");

				doctor = investigationRequest.getLabDoctor();

				investigationName = investigationRequest.getInvestigation().getName();
				reportCreateDate = simpleDateFormat.format(investigationRequest.getLastModifiedDate());

				if (investigationRequest.getReportText() != null && !investigationRequest.getReportText().isEmpty()) {

					reportResult = investigationRequest.getReportText();
				} else {
					reportResult = "Report result is not set";
				}

			}

		} else if (department.getType().equals("Parameter")) {

			if (investigationRequest != null) {

				doctor = investigationRequest.getLabDoctor();

				investigationName = investigationRequest.getInvestigation().getName();
				reportCreateDate = simpleDateFormat.format(investigationRequest.getLastModifiedDate());

				if (investigationRequest.getReportText() != null && !investigationRequest.getReportText().isEmpty()) {

					reportResult = investigationRequest.getReportText();
				} else {
					reportResult = "notset";
				}

			}

		} else {
			return null;
		}
		document.open();

		try {

			CustomDashedLineSeparator separator1 = new CustomDashedLineSeparator();
			separator1.setDash(0);
			separator1.setGap(0);
			separator1.setLineWidth(1);
			Chunk linebreak1 = new Chunk(separator1);

			CustomDashedLineSeparator separator2 = new CustomDashedLineSeparator();
			separator2.setDash(0);
			separator2.setGap(0);
			separator2.setLineWidth(1);
			Chunk linebreak2 = new Chunk(separator2);

			CustomDashedLineSeparator separator3 = new CustomDashedLineSeparator();
			separator3.setDash(0);
			separator3.setGap(0);
			separator3.setLineWidth(1);
			Chunk linebreak3 = new Chunk(separator3);

			Chunk chunkHeading = new Chunk("Investigation Report", headerFont);
			Paragraph heading = new Paragraph(chunkHeading);
			heading.setAlignment(Element.ALIGN_CENTER);
			heading.setSpacingAfter(-20f);

			Paragraph departmentNamePdf = new Paragraph(departmentName, boldFont);
			departmentNamePdf.setAlignment(Element.ALIGN_CENTER);
			departmentNamePdf.setSpacingAfter(5f);
			departmentNamePdf.setSpacingBefore(0);

			Paragraph temp = new Paragraph("", boldFont);
			temp.setAlignment(Element.ALIGN_CENTER);
			temp.setSpacingAfter(0f);
			temp.setSpacingBefore(0);

			Paragraph endReportPara = new Paragraph("*** End of report ***", endReportFont);
			endReportPara.setAlignment(Element.ALIGN_CENTER);
			endReportPara.setSpacingAfter(20f);
			endReportPara.setSpacingBefore(20);

			PdfPTable doctorTable = new PdfPTable(new float[] { 40, 42, 18f });
			doctorTable.setWidthPercentage(100);
			doctorTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			doctorTable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			doctorTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			doctorTable.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			doctorTable.getDefaultCell().setFixedHeight(15);
			doctorTable.setSpacingAfter(0);
			doctorTable.setSpacingBefore(0);

			doctorTable.addCell(new Phrase(new Chunk("", doctorFont)));
			doctorTable.addCell(new Phrase(new Chunk("", boldFont)));
			doctorTable.addCell(new Phrase(new Chunk(doctor.getTitle(), doctorFont)));

			doctorTable.addCell(new Phrase(new Chunk("", doctorFont)));
			doctorTable.addCell(new Phrase(new Chunk("", boldFont)));
			doctorTable.addCell(new Phrase(new Chunk(doctor.getDegree(), doctorFont)));

			doctorTable.addCell(new Phrase(new Chunk("", doctorFont)));
			doctorTable.addCell(new Phrase(new Chunk("", boldFont)));
			doctorTable.addCell(new Phrase(new Chunk(doctor.getDescription(), doctorFont)));

			Paragraph signDoctorNamePara = new Paragraph("Dr AMAN BISWAS", doctorFont);
			signDoctorNamePara.setAlignment(Element.ALIGN_RIGHT);
			signDoctorNamePara.setSpacingAfter(0f);
			signDoctorNamePara.setSpacingBefore(0);

			Paragraph doctorDegreePara = new Paragraph("MBBS, FCPS", doctorFont);
			doctorDegreePara.setAlignment(Element.ALIGN_RIGHT);
			doctorDegreePara.setSpacingAfter(0f);
			doctorDegreePara.setSpacingBefore(0);

			Paragraph CONSULTANTPara = new Paragraph("SENIOR CONSULTANT", doctorFont);
			CONSULTANTPara.setAlignment(Element.ALIGN_RIGHT);
			CONSULTANTPara.setSpacingAfter(0f);
			CONSULTANTPara.setSpacingBefore(0);

			PdfPTable table = new PdfPTable(new float[] { 15, 2, 25, 15, 2, 25 });
			table.setWidthPercentage(100);
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
			table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
			table.getDefaultCell().setFixedHeight(20);
			table.setSpacingAfter(0);
			table.setSpacingBefore(0);

			if (patient != null) {

				if (billNumber != null && !billNumber.isEmpty()) {
//					Chunk textAsChunk = new Chunk("Bill No ", boldFont);
//					textAsChunk.setBackground(BaseColor.RED);
//
//					PdfPCell cell = new PdfPCell(new Phrase(new Chunk("Bill No ", boldFont)));
//					cell.setBackgroundColor(BaseColor.GREEN);
//
//					PdfPCell cell1 = new PdfPCell(new Phrase(new Chunk(":", boldFont)));
//					cell.setBackgroundColor(BaseColor.GREEN);
//
//					PdfPCell cell2 = new PdfPCell(new Phrase(new Chunk(billNumber, boldFont)));
//					cell.setBackgroundColor(BaseColor.GREEN);
//
//					table.addCell(cell);
//					table.addCell(cell1);
//					table.addCell(cell2);

					table.addCell(new Phrase(new Chunk("Bill No ", boldFont)));
					table.addCell(new Phrase(new Chunk(":", boldFont)));
					table.addCell(new Phrase(new Chunk(billNumber, commonFont)));

				}

				if (patient.getName() != null && !patient.getName().isEmpty()) {
					table.addCell(new Phrase(new Chunk("Patient Name ", boldFont)));
					table.addCell(new Phrase(new Chunk(":", boldFont)));
					table.addCell(new Phrase(new Chunk(patient.getName(), commonFont)));

				}

				if (patient.getGender() != null && !patient.getGender().isEmpty()) {
					table.addCell(new Phrase(new Chunk("Gender ", boldFont)));
					table.addCell(new Phrase(new Chunk(":", boldFont)));
					table.addCell(new Phrase(new Chunk(patient.getGender(), commonFont)));

				}

				if (patient.getAge() != null && !patient.getAge().isEmpty()) {
					table.addCell(new Phrase(new Chunk("Age ", boldFont)));
					table.addCell(new Phrase(new Chunk(":", boldFont)));
					table.addCell(new Phrase(new Chunk(patient.getAge(), commonFont)));

				}

				if (patient.getReferenceDoctor() != null && !patient.getReferenceDoctor().isEmpty()) {
					table.addCell(new Phrase(new Chunk("Referred By ", boldFont)));
					table.addCell(new Phrase(new Chunk(":", boldFont)));
					table.addCell(new Phrase(new Chunk(patient.getReferenceDoctor(), commonFont)));

				}

				if (reportCreateDate != null && !reportCreateDate.isEmpty()) {
					table.addCell(new Phrase(new Chunk("Report Date ", boldFont)));
					table.addCell(new Phrase(new Chunk(":", boldFont)));
					table.addCell(new Phrase(new Chunk(reportCreateDate, commonFont)));

				}
			}

			Paragraph temp1 = new Paragraph("", boldFont);
			temp1.setAlignment(Element.ALIGN_CENTER);
			temp1.setSpacingAfter(-10f);
			temp1.setSpacingBefore(-5);

			Paragraph temp2 = new Paragraph("", boldFont);
			temp2.setAlignment(Element.ALIGN_CENTER);
			temp2.setSpacingAfter(-5);
			temp2.setSpacingBefore(0);

			Paragraph temp3 = new Paragraph("", boldFont);
			temp3.setAlignment(Element.ALIGN_CENTER);
			temp3.setSpacingAfter(-5f);
			temp3.setSpacingBefore(-5);

			document.add(heading);
			document.add(linebreak2);
			document.add(temp);
			document.add(table);
			document.add(temp1);
			document.add(linebreak1);
			document.add(temp2);
			document.add(departmentNamePdf);

			if (department.getType().equals("List")) {

				Paragraph temp4 = new Paragraph("", boldFont);
				temp4.setAlignment(Element.ALIGN_CENTER);
				temp4.setSpacingAfter(-4);
				temp4.setSpacingBefore(0);

				Paragraph temp5 = new Paragraph("", boldFont);
				temp5.setAlignment(Element.ALIGN_CENTER);
				temp5.setSpacingAfter(-5);
				temp5.setSpacingBefore(-12);

				Paragraph temp6 = new Paragraph("", boldFont);
				temp6.setAlignment(Element.ALIGN_CENTER);
				temp6.setSpacingAfter(2);
				temp6.setSpacingBefore(0);

				document.add(temp3);
				document.add(linebreak2);
				document.add(temp4);
				document.add(thReportResultTable);
				document.add(temp5);
				document.add(thReportResultTableSeparatorLinebreak);
				document.add(temp6);
				document.add(reportResultTable);

			} else if (department.getType().equals("Individual")) {
				Paragraph investigationNamePdf = new Paragraph(investigationName, boldFont);
				investigationNamePdf.setFont(boldFont);
				investigationNamePdf.setAlignment(Element.ALIGN_CENTER);
				investigationNamePdf.setSpacingAfter(0f);
				document.add(investigationNamePdf);
				document.add(temp3);
				document.add(linebreak2);

				HTMLWorker htmlWorker = new HTMLWorker(document);
				htmlWorker.parse(new StringReader(reportResult));

//				Paragraph reportResultPdf = new Paragraph(reportResult, boldFont);
//				reportResultPdf.setAlignment(Element.ALIGN_LEFT);
//				document.add(reportResultPdf);
			} else if (department.getType().equals("Parameter")) {

				Font boldFontParameter = new Font(Font.FontFamily.TIMES_ROMAN, 9.0f, Font.BOLD, BaseColor.BLACK);
				Font paramGroupName = new Font(Font.FontFamily.TIMES_ROMAN, 15.0f, Font.BOLD, BaseColor.BLACK);
				Font commonFontParameter = new Font(FontFamily.TIMES_ROMAN, 12.0f);

				Font CUSTOM_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD | Font.UNDERLINE,
						BaseColor.BLACK);

				Paragraph investigationNamePdf = new Paragraph(investigationName, boldFont);
				investigationNamePdf.setFont(boldFont);
				investigationNamePdf.setAlignment(Element.ALIGN_CENTER);
				investigationNamePdf.setSpacingAfter(0f);

				document.add(investigationNamePdf);
				document.add(temp3);
				document.add(linebreak2);

				String parameterId, result = null, unit = null, refValue = null;
				boolean resultFoundOrNot;

				if (!reportResult.equals("notset")) {

					PdfPTable reportResultTableth = new PdfPTable(new float[] { 40, 15, 20, 25 });
					reportResultTableth.setWidthPercentage(100);
					reportResultTableth.setHorizontalAlignment(Element.ALIGN_LEFT);
					reportResultTableth.getDefaultCell().setBorder(Rectangle.NO_BORDER);
					reportResultTableth.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
					reportResultTableth.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
					reportResultTableth.getDefaultCell().setFixedHeight(20);
					reportResultTableth.setSpacingAfter(0);
					reportResultTableth.setSpacingBefore(0);

					reportResultTableth.addCell(new Phrase(new Chunk("Investigation Name", boldFontParameter)));
					reportResultTableth.addCell(new Phrase(new Chunk("Reference value ", boldFontParameter)));
					reportResultTableth.addCell(new Phrase(new Chunk("Unit", boldFontParameter)));
					reportResultTableth.addCell(new Phrase(new Chunk("Result", boldFontParameter)));
					document.add(reportResultTableth);

					List<InvestivationParameterResult> investivationParameterResults = investivationParameterResultRepository
							.findByInvestigationRequestId(investigationRequest.getId());

					List<Parameter> parameterList = investigationRequest.getInvestigation().getParameters();

					List<Parameter> parameterlist = parameterList;

					parameterlist.sort(new Comparator<Parameter>() {

						@Override
						public int compare(Parameter o1, Parameter o2) {
							if (o1.getShortingId() < o2.getShortingId()) {
								return -1;
							} else if (new Integer(o1.getShortingId()).equals(o2.getShortingId())) {
								return 0; // You can change this to make it then look at the
								// words alphabetical order
							} else {
								return 1;
							}
						}
					});

					List<ParamGroup> paramGroupList = investigationRequest.getInvestigation().getParamGroup();

					// for each param group
					for (int i = 0; i < paramGroupList.size(); i++) {

						PdfPTable reportResultTableParameter = new PdfPTable(new float[] { 40, 15, 20, 25 });
						reportResultTableParameter.setWidthPercentage(100);
						reportResultTableParameter.setHorizontalAlignment(Element.ALIGN_LEFT);
						reportResultTableParameter.getDefaultCell().setBorder(Rectangle.NO_BORDER);
						reportResultTableParameter.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
						reportResultTableParameter.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
						reportResultTableParameter.getDefaultCell().setFixedHeight(20);
						reportResultTableParameter.setSpacingAfter(0);
						reportResultTableParameter.setSpacingBefore(0);

						// condition

						if (paramGroupList.get(i).isHeadingShow()) {

							Paragraph paramGroupname = new Paragraph(paramGroupList.get(i).getName(), CUSTOM_FONT);
							// temp4.setAlignment(Element.ALIGN_CENTER);
							paramGroupname.setSpacingAfter(7);
							paramGroupname.setSpacingBefore(0);

							document.add(paramGroupname);
						}

//						document.add(new Phrase(
//								new Chunk(investigationRequest.getInvestigation().getParamGroup().get(i).getName(),
//										CUSTOM_FONT)));

						paramGroupList.get(i).getParameters().removeAll(paramGroupList.get(i).getParameters());

						// setting result If exists
						for (int k = 0; k < parameterList.size(); k++) {

							// System.out.println("paramGropuList " + paramGropuList.size() + " " + i);
							if (parameterList.get(k).getParamGroup().getId() == paramGroupList.get(i).getId()) {

								paramGroupList.get(i).getParameters().add(parameterList.get(k));

							}
						}

						int parametersSize = investigationRequest.getInvestigation().getParamGroup().get(i)
								.getParameters().size();

						// for each parameter
						for (int j = 0; j < parametersSize; j++) {

							Parameter parameter = investigationRequest.getInvestigation().getParamGroup().get(i)
									.getParameters().get(j);
							resultFoundOrNot = false;

							// System.out.println("parameterIdFormUser........=" + parameter.getId());

							for (int k = 0; k < investivationParameterResults.size(); k++) {

//								System.out.println("parameterIdFormresultTabel.............="
//										+ investivationParameterResults.get(k).getParameter().getId() + "\n");

								if (String.valueOf(investivationParameterResults.get(k).getParameter().getId())
										.equals(String.valueOf(parameter.getId()))
										&& investivationParameterResults.get(k).getResult() != null) {
									result = investivationParameterResults.get(k).getResult();

									if (parameter.getRefValue() != null) {
										refValue = parameter.getRefValue();
									} else {
										refValue = "";
									}

									if (parameter.getUnit() != null) {
										unit = parameter.getUnit();
									} else {
										unit = "";
									}
									// result = null;
									resultFoundOrNot = true;
								}

							}

							if (resultFoundOrNot) {
								// System.out.println("name=" + parameter.getName() + " result=" + result);
							} else {
								result = "Not Set";
								// System.out.println("name=" + parameter.getName() + " result=" + result);
							}

							reportResultTableParameter
									.addCell(new Phrase(new Chunk(parameter.getName(), commonFontParameter)));
							reportResultTableParameter.addCell(new Phrase(new Chunk(refValue, commonFontParameter)));
							reportResultTableParameter.addCell(new Phrase(new Chunk(unit, commonFontParameter)));
							reportResultTableParameter.addCell(new Phrase(new Chunk(result, commonFontParameter)));

//							System.out.println("value "
//									+ investigationRequest.getInvestigation().getParamGroup().get(i).getParameters()
//											.get(j).getId()
//									+ " " + investigationRequest.getInvestigation().getParamGroup().get(i)
//											.getParameters().get(j).getName());

						}

						document.add(reportResultTableParameter);

					}

				} else {
					reportResult = "Report result is not set";
				}

			}
//
//			String content = "<html>\n <head>\n    <title>Title</title>\n    \n   \n </head>\n"
//					+ "\n    \n<body>  \n  \n      \n  <table id=\"customers\"> \r\n" + "  <tr>\r\n"
//					+ "    <th>Month</th>\r\n" + "    <th>Savings</th>\r\n" + "  </tr>\r\n" + "  <tr>\r\n"
//					+ "    <td>January</td>\r\n" + "    <td>$10090</td>\r\n" + "  </tr>\r\n"
//					+ "</table>  \n\n</body>\n</html>";
//			// String cssSource = " #customers { color: red; } ";
//
//			String cssSource = "";
//			ClassPathResource cpr = new ClassPathResource("static/css/custom.css");
//			try {
//				byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
//				cssSource = new String(bdata, StandardCharsets.UTF_8);
//				System.out.println("data" + cssSource);
//			} catch (IOException e) {
//				System.out.println("error");
//			}
//
//			// CSS
//			CSSResolver cssResolver = new StyleAttrCSSResolver();
//			CssFile cssFile = XMLWorkerHelper.getCSS(new ByteArrayInputStream(cssSource.getBytes()));
//			cssResolver.addCss(cssFile);
//			// HTML
//			HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
//			htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
//			// Pipelines
//			PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
//			HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
//			CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);
//			// XML Worker
//			XMLWorker worker = new XMLWorker(css, true);
//			XMLParser p = new XMLParser(worker);
//			p.parse(new ByteArrayInputStream(content.toString().getBytes()));

//			InputStream is = new ByteArrayInputStream(content.getBytes());
//			ByteArrayInputStream cis = new ByteArrayInputStream(cssSource.getBytes());
//			XMLWorkerHelper.getInstance().parseXHtml(writer, document, is, cis);

			document.add(endReportPara);
			document.add(doctorTable);

			document.close();
		} catch (

		DocumentException ex) {

			System.out.print("Error occurred: {0}" + ex);
		}

		return out.toByteArray();

//		Document documenttest = new Document();
//		ByteArrayOutputStream outTest = new ByteArrayOutputStream();
//		PdfWriter pdfWriter = PdfWriter.getInstance(documenttest, outTest);
//		documenttest.open();
//		documenttest.addTitle("Please read this");
//		documenttest.add(new Chunk("kkkkkkk"));
//
//		String html = "<p>Html code here</p>";
//		Paragraph comb = new Paragraph();
//
//		StringBuilder sb = new StringBuilder();
//		sb.append(html);
//
//		ElementList list = XMLWorkerHelper.parseToElementList(sb.toString(), null);
//		for (Element element : list) {
//			comb.add(element);
//		}
//		documenttest.add(comb);
//		String str = "<html><head></head><body>" + "<h1>Show your support</h1>" + "</body></html>";
//
//		Optional<InvestigationRequest> investigationRequest = investigationRequestRepository
//				.findById(investigationResuestId);
//
//		HTMLWorker htmlWorker = new HTMLWorker(documenttest);
//		htmlWorker.parse(new StringReader(investigationRequest.get().getReportText()));

//		XMLWorkerHelper worker = XMLWorkerHelper.getInstance();	
//		worker.parseXHtml(pdfWriter, document, new StringReader(str));
//
//		documenttest.add(new Chunk("cccc"));
//		documenttest.close();
//		pdfWriter.close();

//		if (investigationReport != null) {
//
//			try {
//
//				PdfWriter.getInstance(document, out);
//				document.open();
//
//				Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 25.0f, Font.UNDERLINE | Font.BOLD,
//						BaseColor.BLACK);
//				Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f, Font.BOLD, BaseColor.BLACK);
//				Font commonFont = new Font(FontFamily.TIMES_ROMAN, 12.0f);
//				Chunk firstPart = new Chunk("Name ", boldFont);
//				Chunk secondPart = new Chunk("", commonFont);
//
//				Paragraph heading = new Paragraph("Medical Report", headerFont);
//
//				PdfPTable table = new PdfPTable(new float[] { 25, 8, 62.5f });
//				table.setWidthPercentage(90);
//				table.setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
//				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
//				table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//				table.getDefaultCell().setFixedHeight(35);
//
//				table.addCell(new Phrase(firstPart));
//				table.addCell(new Phrase(new Chunk(":", boldFont)));
//				table.addCell(new Phrase(new Chunk(investigationReport.getPatientName(), commonFont)));
//
//				if (investigationReport.getAge() != null && !investigationReport.getAge().isEmpty()) {
//					table.addCell(new Phrase(new Chunk("Age ", boldFont)));
//					table.addCell(new Phrase(new Chunk(":", boldFont)));
//					table.addCell(new Phrase(new Chunk(investigationReport.getAge(), commonFont)));
//					count++;
//				}
//
//				if (investigationReport.getGender() != null && !investigationReport.getGender().isEmpty()) {
//					table.addCell(new Phrase(new Chunk("Sex ", boldFont)));
//					table.addCell(new Phrase(new Chunk(":", boldFont)));
//					table.addCell(new Phrase(new Chunk(investigationReport.getGender(), commonFont)));
//					count++;
//				}
//
//				if (investigationReport.getInvestigationName() != null
//						&& !investigationReport.getInvestigationName().isEmpty()) {
//					table.addCell(new Phrase(new Chunk("Test Name ", boldFont)));
//					table.addCell(new Phrase(new Chunk(":", boldFont)));
//					table.addCell(new Phrase(new Chunk(investigationReport.getInvestigationName(), commonFont)));
//
//					table.addCell(new Phrase(new Chunk("Cost                 ", boldFont)));
//					table.addCell(new Phrase(new Chunk(":", boldFont)));
//					table.addCell(new Phrase(new Chunk(Integer.toString(investigationReport.getRate()), commonFont)));
//					count += 2;
//				}
//
//				if (investigationReport.getReportDate() != null
//						&& !investigationReport.getReportDate().toString().isEmpty()) {
//
//					table.addCell(new Phrase(new Chunk("Report Create Date     ", boldFont)));
//					table.addCell(new Phrase(new Chunk(":", boldFont)));
//					table.addCell(new Phrase(new Chunk(investigationReport.getReportDate(), commonFont)));
//					count++;
//				}
//
//				if (investigationReport.isReportText()) {
//					table.addCell(new Phrase(new Chunk("Report Result", boldFont)));
//					table.addCell(new Phrase(new Chunk(":", boldFont)));
//					table.addCell(new Phrase(new Chunk(investigationReport.getReportTextValue(), commonFont)));
//					count++;
//				}
//
//				if (investigationReport.isReportImage()) {
//
//					table.addCell(new Phrase(new Chunk("Report Image ", boldFont)));
//					table.addCell(new Phrase(new Chunk(":", boldFont)));
//					table.addCell("");
//
//					ByteArrayInputStream imagebyte = new ByteArrayInputStream(
//							investigationReport.getReportImageValue());
//					Image reportImage = Image.getInstance(investigationReport.getReportImageValue());
//					reportImage.scaleAbsolute(200, 200);
//					reportImage.setAbsolutePosition(200, y_axis + (7 - count) * 30);
//					document.add(reportImage);
//				}
//
//				heading.setAlignment(Element.ALIGN_CENTER);
//				heading.setSpacingAfter(40f);
//
//				document.add(heading);
//				document.add(table);
//				document.close();
//			document.add(patienName);
//			document.add(investigationName);
//			document.add(rate);
//			document.add(createdDate);
//			document.add(reportText);

//			Style normal = new Style();
//			PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
//			normal.setFont(font).setFontSize(14);
//			
//			Style code = new Style();
//			PdfFont monospace = PdfFontFactory.createFont(FontConstants.COURIER);
//			code.setFont(monospace).setFontColor(Color.RED)
//			    .setBackgroundColor(Color.LIGHT_GRAY);

//			} catch (DocumentException ex) {
//
//				System.out.print("Error occurred: {0}" + ex);
//			}
//
//			return out.toByteArray();
//		} else {
//			System.out.println("byte null ");
//		}

		// return new ByteArrayInputStream(out.toByteArray());

	}

	class CustomDashedLineSeparator extends DottedLineSeparator {
		protected float dash = 0;
		protected float phase = 0f;

		public float getDash() {
			return dash;
		}

		public float getPhase() {
			return phase;
		}

		public void setDash(float dash) {
			this.dash = dash;
		}

		public void setPhase(float phase) {
			this.phase = phase;
		}

		@Override
		public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y) {
			canvas.saveState();
			canvas.setLineWidth(lineWidth);
			canvas.setLineDash(dash, gap, phase);
			drawLine(canvas, llx, urx, y);
			canvas.restoreState();
		}

	}

	public String convertToBase64(String fileName, byte[] logo) {

		String extension = FilenameUtils.getExtension(fileName);
		String encodeBase64 = Base64.getEncoder().encodeToString(logo);

		return "data:image/" + extension + ";base64," + encodeBase64;

	}

	public String createInvestigationRequestByAdmin(String[] selectedInvestigation, Patient patient, int providerId) {

		List<Investigation> investigationList = new ArrayList<>();
		Optional<Provider> provider = providerRepository.findById(providerId);

		// If bill number is empty
		if (patient.getBillNumber() == null || patient.getBillNumber().isEmpty()) {

			System.out.print("last " + patientRepository.LastId());
			String billNumber = String.valueOf((patientRepository.LastId() + 1));

			String temp = "b";
			for (int i = 0; i < 6 - billNumber.length(); i++) {

				temp += "0";
			}

			temp += billNumber;
			patient.setBillNumber(temp);

		}

		Patient existPatient = patientRepository.findByBillNumber(patient.getBillNumber());

		if (existPatient != null) {
			return "2";
		}
		if (!provider.isPresent()) {
			return "0";
		}

		for (int i = 0; i < selectedInvestigation.length; i++) {

			Investigation investigation = investigationRepository.findByName(selectedInvestigation[i]);

			if (investigation != null) {
				investigationList.add(investigation);
			} else {
				return "0";
			}
		}

		Patient patientResult = patientRepository.save(patient);
		LabDoctor labDoctor = new LabDoctor();

		for (int i = 0; i < selectedInvestigation.length; i++) {

			InvestigationRequest investigationRequest = new InvestigationRequest();

			investigationRequest.setPatient(patientResult);
			investigationRequest.setInvestigation(investigationList.get(i));
			investigationRequest.setProvider(provider.get());
			investigationRequest.setLabDoctor(null);
			investigationRequestRepository.save(investigationRequest);

		}

		return patientResult.getBillNumber();

		// return "some";

	}

	public List<ReportResponse> UserInvestigationReport(String billNo, String mobile, int providerId) {

		List<InvestigationRequest> resultInvestigationRequests = investigationRequestRepository
				.findByPatientBillNumberAndPatientMobileAndProviderId(billNo, mobile, providerId);

		System.out.println("size " + resultInvestigationRequests.size());

		List<InvestigationRequest> InvestigationRequestsListType = new ArrayList<>();
		List<InvestigationRequest> resultInvestigationRequestsIndividual = new ArrayList<>();
		List<ReportResponse> resultReportResponse = new ArrayList<ReportResponse>();

		// getting only list type
		for (int i = 0; i < resultInvestigationRequests.size(); i++) {

			if (resultInvestigationRequests.get(i).getInvestigation().getDepartment().getType().equals("List")) {
				InvestigationRequestsListType.add(resultInvestigationRequests.get(i));
			}

		}

		// Finding unique department
		Set<Pair> listTypeDepartment = new HashSet<Pair>();

		for (int i = 0; i < InvestigationRequestsListType.size(); i++) {

			String dept = InvestigationRequestsListType.get(i).getInvestigation().getDepartment().getName();
			String type = InvestigationRequestsListType.get(i).getInvestigation().getDepartment().getType();

			System.out.println("dept " + dept + "  " + InvestigationRequestsListType.get(i).getInvestigation());

			Pair pair = new Pair(dept, type);
			listTypeDepartment.add(pair);
		}

		for (Pair pair : listTypeDepartment) {

			// System.out.println("dept " + pair.dept + ' ' + pair.type);

			ReportResponse reportResponse = new ReportResponse();
			List<ReportResponseEntity> reportResponseEntityList = new ArrayList<ReportResponseEntity>();

			reportResponse.setDepartment(pair.dept);
			reportResponse.setType(pair.type);

			for (int i = 0; i < InvestigationRequestsListType.size(); i++) {

				if (InvestigationRequestsListType.get(i).getInvestigation() != null && InvestigationRequestsListType
						.get(i).getInvestigation().getDepartment().getName().equals(pair.dept)) {

					ReportResponseEntity reportResponseEntity = new ReportResponseEntity();

					reportResponseEntity
							.setInvestigationName(InvestigationRequestsListType.get(i).getInvestigation().getName());
					reportResponseEntity.setInvestigationRequestId(InvestigationRequestsListType.get(i).getId());

					reportResponseEntityList.add(reportResponseEntity);

				}

			}

			reportResponse.setRequestList(reportResponseEntityList);
			resultReportResponse.add(reportResponse);

		}

		// other type
		for (int i = 0; i < resultInvestigationRequests.size(); i++) {

			if (!resultInvestigationRequests.get(i).getInvestigation().getDepartment().getType().equals("List")) {

				ReportResponse reportResponse = new ReportResponse();
				ReportResponseEntity reportResponseEntity = new ReportResponseEntity();
				List<ReportResponseEntity> reportResponseEntityList = new ArrayList<ReportResponseEntity>();

				reportResponse
						.setDepartment(resultInvestigationRequests.get(i).getInvestigation().getDepartment().getName());
				reportResponse.setType(resultInvestigationRequests.get(i).getInvestigation().getDepartment().getType());

				reportResponseEntity
						.setInvestigationName(resultInvestigationRequests.get(i).getInvestigation().getName());
				reportResponseEntity.setInvestigationRequestId(resultInvestigationRequests.get(i).getId());

				reportResponseEntityList.add(reportResponseEntity);
				reportResponse.setRequestList(reportResponseEntityList);
				resultReportResponse.add(reportResponse);

			}

		}

		return resultReportResponse;

	}

	private JavaMailSender javaMailSender;

	@Autowired
	public void MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public int sendMailToPatient(String gmail, int providerId, int investigationRequestId)
			throws MailException, MessagingException, IOException {

		Optional<Provider> providerInvestigation = providerRepository.findById(investigationRequestId);
		// byte[] data = userReportPdf(providerId, investigationRequestId);
		byte[] data = null;

		if (providerInvestigation.isPresent() && data != null) {

			// System.out.println("providerInvestigation naem " +
			// providerInvestigation.get().getMedicalName());

			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

			helper.setFrom("gmailAddress");
			helper.setTo(gmail);
			helper.setSubject("Medical Report From " + providerInvestigation.get().getMedicalName());
			helper.setText("Please download the below file.");

//		ClassPathResource classPathResource = new ClassPathResource("Attachment.pdf");
//		ByteArrayInputStream byteArrayFileObj = userReportPdf(1,1);

			final InputStreamSource fileStreamSource = new ByteArrayResource(data);
			helper.addAttachment("Report.pdf", fileStreamSource);

			javaMailSender.send(mimeMessage);
			return 1;
		}

//		SimpleMailMessage mail = new SimpleMailMessage();
//		mail.setTo(gmail);
//		mail.setSubject("Testing Mail API");
//		mail.setText("Hurray ! You have done that dude...");
//		javaMailSender.send(mail);

		return 0;
	}

	public boolean setReportByAdmin(String reportResultList[], String investigationResuestIdList[], int providerId,
			String labDoctorTitle) {

		LabDoctor labDoctor = labDoctorRepository.findByTitle(labDoctorTitle);

		for (int i = 0; i < investigationResuestIdList.length; i++) {

			InvestigationRequest investigationRequest = investigationRequestRepository
					.findByIdAndProviderId(Integer.parseInt(investigationResuestIdList[i]), providerId);

			if (investigationRequest != null) {
				if (!reportResultList[i].equals("null")) {
					investigationRequest.setReportText(reportResultList[i]);
				} else {
					investigationRequest.setReportText(null);
				}

				if (labDoctor != null) {
					investigationRequest.setLabDoctor(labDoctor);
				}

				investigationRequestRepository.save(investigationRequest);
			} else
				return false;
		}

		return true;

	}

	public List<ReportResponse> UserInvestigationReportByAdmin(String billNo, int providerId) {

		List<InvestigationRequest> resultInvestigationRequests = investigationRequestRepository
				.findByPatientBillNumberAndProviderId(billNo, providerId);

		System.out.println("resultInvestigationRequests size=" + resultInvestigationRequests.size() + "\n\n");
		List<ReportResponse> resultReportResponse = new ArrayList<ReportResponse>();

		// Finding unique department
		Set<Pair> department = new HashSet<Pair>();

		for (int i = 0; i < resultInvestigationRequests.size(); i++) {

			// System.out.println("resultInvestigationRequests investigation name="
			// + resultInvestigationRequests.get(i).getInvestigation().getName() + "\n\n");
			String dept = resultInvestigationRequests.get(i).getInvestigation().getDepartment().getName();
			String type = resultInvestigationRequests.get(i).getInvestigation().getDepartment().getType();

			Pair pair = new Pair(dept, type);
			department.add(pair);

		}

		System.out.println("department=" + department.size() + "\n");

		for (Pair pair : department) {

			ReportResponse reportResponse = new ReportResponse();
			List<ReportResponseEntity> reportResponseEntityList = new ArrayList<ReportResponseEntity>();

			reportResponse.setDepartment(pair.dept);
			reportResponse.setType(pair.type);

			for (int i = 0; i < resultInvestigationRequests.size(); i++) {

//				System.out.println("inside department=" + pair.dept + " "
//						+ resultInvestigationRequests.get(i).getInvestigation().getName() + " "
//						+ resultInvestigationRequests.size() + "  i=" + i + "\n");

				if (resultInvestigationRequests.get(i).getInvestigation() != null && resultInvestigationRequests.get(i)
						.getInvestigation().getDepartment().getName().equals(pair.dept)) {

//					System.out.println("select department =" + pair.dept + " "
//							+ resultInvestigationRequests.get(i).getInvestigation().getName() + "\n");

					ReportResponseEntity reportResponseEntity = new ReportResponseEntity();
					reportResponseEntity.setFormat(resultInvestigationRequests.get(i).getInvestigation().getFormat());
					reportResponseEntity
							.setInvestigationName(resultInvestigationRequests.get(i).getInvestigation().getName());
					reportResponseEntity.setInvestigationRequestId(resultInvestigationRequests.get(i).getId());
					reportResponseEntity
							.setRefValue(resultInvestigationRequests.get(i).getInvestigation().getRefValue());
					reportResponseEntity.setReportResult(resultInvestigationRequests.get(i).getReportText());
					reportResponseEntity.setUnit(resultInvestigationRequests.get(i).getInvestigation().getUnit());

//					if the type is Parameter then we add paramGroup.
					if (resultInvestigationRequests.get(i).getInvestigation().getDepartment().getType()
							.equals("Parameter")) {

						List<ParamGroup> paramGroupList = assignParamGroup(
								resultInvestigationRequests.get(i).getInvestigation().getId(),
								resultInvestigationRequests.get(i).getId());

//						List<ParamGroup> paramGroupList = paramGroup(
//								resultInvestigationRequests.get(i).getInvestigation().getId());

//						List<ParamGroup> paramGroupList = resultInvestigationRequests.get(i).getInvestigation()
//								.getParamGroup();
//
//						List<Parameter> parameterList = resultInvestigationRequests.get(i).getInvestigation()
//								.getParameters();
//
//						List<InvestivationParameterResult> investivationParameterResultList = investivationParameterResultRepository
//								.findByInvestigationRequestId(resultInvestigationRequests.get(i).getId());
//
//						System.out.println("paramGropuList " + paramGroupList.size() + " " + parameterList.size() + "  "
//								+ investivationParameterResultList.size() + "  "
//								+ resultInvestigationRequests.get(i).getInvestigation().getName() + " \n\n\n");

						// set null to all parameter list inside paramGropuList
//						for (int k = 0; k < paramGroupList.size(); k++) {
//							paramGroupList.get(k).getParameters().removeAll(paramGroupList.get(k).getParameters());
//							paramGroupList.get(k).setParameters(
//									getParameterList(resultInvestigationRequests.get(i).getInvestigation().getId()));
//						}
//
//						for (int k = 0; k < paramGroupList.size(); k++) {
//							System.out.println("size before =" + paramGroupList.get(k).getParameters().size());
//						}

//						for (int j = 0; j < parameterList.size(); j++) {
//
//							// setting result If exists
//							for (int k = 0; k < investivationParameterResultList.size(); k++) {
//
//								// System.out.println("paramGropuList " + paramGropuList.size() + " " + i);
//								if (investivationParameterResultList.get(k).getParameter().getId() == parameterList
//										.get(j).getId()) {
//									parameterList.get(j)
//											.setReportResult(investivationParameterResultList.get(k).getResult());
//								}
//							}
//
//							for (int k = 0; k < paramGroupList.size(); k++) {
//
//								if (parameterList.get(j).getParamGroup().getId() == paramGroupList.get(k).getId()) {
//									paramGroupList.get(k).getParameters().add(parameterList.get(j));
//								}
//
//							}
//
//						}

//						for (int k = 0; k < paramGroupList.size(); k++) {
//							System.out.println("paramGroup Name=" + paramGroupList.get(k).getName() + " " + k);
////							for (int l = 0; l < paramGroupList.get(k).getParameters().size(); l++) {
////								System.out.println("Pamager Name="
////										+ paramGroupList.get(k).getParameters().get(l).getName() + " " + l);
////							}
//							System.out.println();
//						}

//						int investigationId = resultInvestigationRequests.get(i).getInvestigation().getId();
//						int paramGroupId = resultInvestigationRequests.get(i).getInvestigation().getParamGroup().get(0)
//								.getId();

						reportResponseEntity.setParamGroupList(paramGroupList);
						// parameter If
					}

					reportResponseEntityList.add(reportResponseEntity);

//					System.out.println("\n\n checking start ======================");
//					System.out.println("reportResponseEntityList.size()==" + reportResponseEntityList.size());
//					for (int p = 0; p < reportResponseEntityList.size(); p++) {
//
//						System.out.println("resultReportResponse insideLoop==="
//								+ reportResponseEntityList.get(p).getInvestigationName() + " "
//								+ reportResponseEntityList.get(p).getInvestigationRequestId());
//
//						System.out.println(
//								"paramGroup size===" + reportResponseEntityList.get(p).getParamGroupList().size());
//
//						for (int m = 0; m < reportResponseEntityList.get(p).getParamGroupList().size(); m++) {
//							System.out.println("Parameters size===" + reportResponseEntityList.get(p)
//									.getParamGroupList().get(m).getParameters().size());
//
//						}
//
//					}
//					System.out.println("ending start ======================\n\n");

				}

			}

			reportResponse.setRequestList(reportResponseEntityList);
			resultReportResponse.add(reportResponse);

		}

		return resultReportResponse;
	}

	public List<ParamGroup> assignParamGroup(int investigationID, int investigationRequestID) {

		int lenght;

		List<ParamGroup> paramGroupsList = new ArrayList<ParamGroup>();
		List<Parameter> parameterList = new ArrayList<Parameter>();
		List<Parameter> emptyParameterList = new ArrayList<Parameter>();

		// System.out.println("emptyParameterList emptyParameterList === " +
		// emptyParameterList.size());

		List<InvestivationParameterResult> investivationParameterResultList = investivationParameterResultRepository
				.findByInvestigationRequestId(investigationRequestID);

		Optional<Investigation> investigation = investigationRepository.findById(investigationID);

		if (investigation.isPresent()) {

			lenght = investigation.get().getParamGroup().size();
			parameterList = investigation.get().getParameters();

			List<Parameter> parameterlist = parameterList;

			parameterlist.sort(new Comparator<Parameter>() {

				@Override
				public int compare(Parameter o1, Parameter o2) {
					if (o1.getShortingId() < o2.getShortingId()) {
						return -1;
					} else if (new Integer(o1.getShortingId()).equals(o2.getShortingId())) {
						return 0; // You can change this to make it then look at the
						// words alphabetical order
					} else {
						return 1;
					}
				}
			});

			for (int i = 0; i < lenght; i++) {

				ParamGroup paramGroup = new ParamGroup();
				paramGroup.setId(investigation.get().getParamGroup().get(i).getId());
				paramGroup.setName(investigation.get().getParamGroup().get(i).getName());
				paramGroup.setHeadingShow(investigation.get().getParamGroup().get(i).isHeadingShow());
				// paramGroup.setParameters(emptyParameterList);

				paramGroupsList.add(paramGroup);

			}

		}

		for (int j = 0; j < parameterList.size(); j++) {

			Parameter parameter = new Parameter(parameterList.get(j).getId(), parameterList.get(j).getName(),
					parameterList.get(j).getRefValue(), parameterList.get(j).getResultType(),
					parameterList.get(j).getReportResult());

			// setting result If exists
			for (int k = 0; k < investivationParameterResultList.size(); k++) {

				// System.out.println("setReportResult " + paramGropuList.size() + " " + i);
				if (investivationParameterResultList.get(k).getParameter().getId() == parameterList.get(j).getId()) {
					parameterList.get(j).setReportResult(investivationParameterResultList.get(k).getResult());
				}
			}

		}

		for (int k = 0; k < paramGroupsList.size(); k++) {

			int id = paramGroupsList.get(k).getId();

			List<Parameter> temp = new ArrayList<>();

			for (int j = 0; j < parameterList.size(); j++) {

				if (parameterList.get(j).getParamGroup().getId() == id) {

					Parameter parameter = new Parameter(parameterList.get(j).getId(), parameterList.get(j).getName(),
							parameterList.get(j).getRefValue(), parameterList.get(j).getResultType(),
							parameterList.get(j).getReportResult());
					parameter.setShortingId(parameterList.get(j).getShortingId());
					parameter.setDropDownInput(parameterList.get(j).getDropDownInput());

					temp.add(parameter);
				}

			}

//			for (int j = 0; j < temp.size(); j++) {
//				System.out.println("to string ==" + temp.get(j).toString());
//			}

			paramGroupsList.get(k).setParameters(temp);

		}

		for (int j = 0; j < parameterList.size(); j++) {
			parameterList.get(j).setReportResult(null);
		}

		return paramGroupsList;

	}

	public List<ParamGroup> paramGroup(int i) {

		List<ParamGroup> paramGroupsList1 = new ArrayList<ParamGroup>();
		List<ParamGroup> paramGroupsList2 = new ArrayList<ParamGroup>();

		ParamGroup paramGroup = new ParamGroup();
		paramGroup.setId(47);
		paramGroup.setName("paramGroup");
		paramGroup.setParameters(getParameterList(47));

		ParamGroup paramGroup1 = new ParamGroup();
		paramGroup1.setId(47);
		paramGroup1.setName("paramGroup1");
		paramGroup1.setParameters(getParameterList(47));

		ParamGroup paramGroup2 = new ParamGroup();
		paramGroup2.setId(45);
		paramGroup2.setName("paramGroup2");
		paramGroup2.setParameters(getParameterList(45));

		ParamGroup paramGroup3 = new ParamGroup();
		paramGroup3.setId(45);
		paramGroup3.setName("paramGroup3");
		paramGroup3.setParameters(getParameterList(45));

		ParamGroup paramGroup4 = new ParamGroup();
		paramGroup4.setId(45);
		paramGroup4.setName("paramGroup4");
		paramGroup4.setParameters(getParameterList(45));

		paramGroupsList1.add(paramGroup);
		paramGroupsList1.add(paramGroup1);

		paramGroupsList2.add(paramGroup2);
		paramGroupsList2.add(paramGroup3);
		paramGroupsList2.add(paramGroup4);

		if (i == 45) {
			return paramGroupsList2;
		} else if (i == 47) {
			return paramGroupsList1;
		}

		return null;

	}

	public List<ReportResponseEntity> param() {

		List<ReportResponseEntity> reportResponseEntityList = new ArrayList<ReportResponseEntity>();

		ReportResponseEntity reportResponseEntity1 = new ReportResponseEntity();
		ReportResponseEntity reportResponseEntity2 = new ReportResponseEntity();

		List<ParamGroup> paramGroupsList1 = new ArrayList<ParamGroup>();
		List<ParamGroup> paramGroupsList2 = new ArrayList<ParamGroup>();

		ParamGroup paramGroup = new ParamGroup();
		paramGroup.setId(1);
		paramGroup.setName("paramGroup");
		paramGroup.setParameters(getParameterList(47));

		ParamGroup paramGroup1 = new ParamGroup();
		paramGroup1.setId(2);
		paramGroup1.setName("paramGroup1");
		paramGroup1.setParameters(getParameterList(47));

		ParamGroup paramGroup2 = new ParamGroup();
		paramGroup2.setId(1);
		paramGroup2.setName("paramGroup2");
		paramGroup2.setParameters(getParameterList(45));

		ParamGroup paramGroup3 = new ParamGroup();
		paramGroup3.setId(2);
		paramGroup3.setName("paramGroup3");
		paramGroup3.setParameters(getParameterList(45));

		ParamGroup paramGroup4 = new ParamGroup();
		paramGroup4.setId(3);
		paramGroup4.setName("paramGroup4");
		paramGroup4.setParameters(getParameterList(45));

		paramGroupsList1.add(paramGroup);
		paramGroupsList1.add(paramGroup1);

		paramGroupsList2.add(paramGroup2);
		paramGroupsList2.add(paramGroup3);
		paramGroupsList2.add(paramGroup4);

		reportResponseEntity1.setInvestigationName("Test Investigation");
		reportResponseEntity1.setParamGroupList(paramGroupsList1);

		reportResponseEntity2.setInvestigationName("parameter 1");
		reportResponseEntity2.setParamGroupList(paramGroupsList2);

		reportResponseEntityList.add(reportResponseEntity1);
		reportResponseEntityList.add(reportResponseEntity2);

		return reportResponseEntityList;

//		for (int m = 0; m < reportResponseEntityList.size(); m++) {
//
//		}

	}

	public List<Parameter> getParameterList(int i) {

		List<Parameter> list = new ArrayList<>();
		Parameter p1 = new Parameter(1, "name 1", "ref 1", "result 1", "input 1");
		Parameter p2 = new Parameter(2, "name 2", "ref 2", "result 2", "input ");
		Parameter p3 = new Parameter(3, "name 3", "ref 3", "result 3", "input 3");

		list.add(p1);
		// list.add(p2);

		// System.out.println("IDDDDDDDDDDDDDDDD==" + i);
		if (i == 45) {
			list.add(p3);
		}

		return list;

	}

	public class Pair {
		String dept;
		String type;

		Pair(String dept, String type) {
			this.dept = dept;
			this.type = type;
		}

		@Override
		public boolean equals(Object o) {
			if (o instanceof Pair) {
				Pair p = (Pair) o;
				return p.dept == dept && p.type == type;
			}
			return false;
		}

		@Override
		public int hashCode() {
			// use hash codes of the underlying objects
			return 31 * dept.hashCode() + type.hashCode();
		}
	}

	public byte[] Pdf() throws IOException, DocumentException {

		Document document = new Document();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, out);

		Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.BOLD, BaseColor.BLACK);
		Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f, Font.BOLD, BaseColor.BLACK);
		Font commonFont = new Font(FontFamily.TIMES_ROMAN, 12.0f);

		String investigationName = "i am the show off",
				reportResult = "<b>yes i can do every thing</b> <br> i am the show off";
		document.open();

		try {

			Paragraph investigationNamePdf = new Paragraph(investigationName, commonFont);
			investigationNamePdf.setFont(boldFont);
			investigationNamePdf.setAlignment(Element.ALIGN_CENTER);
			investigationNamePdf.setSpacingAfter(0f);
			document.add(investigationNamePdf);

			HTMLWorker htmlWorker = new HTMLWorker(document);
			htmlWorker.parse(new StringReader(reportResult));

			document.close();
		} catch (DocumentException ex) {

			System.out.print("Error occurred: {0}" + ex);
		}

		return out.toByteArray();

	}

	public List<AllInvestigationRequestEntityContainer> allInvestigationRequestforAdmin(int providerId) {

		List<InvestigationRequest> resultInvestigationRequests = investigationRequestRepository
				.findByProviderId(providerId);
		List<AllInvestigationRequestEntityContainer> allInvestigationRequestEntityContainerList = new ArrayList<AllInvestigationRequestEntityContainer>();

		// Finding unique department
		Set<String> billNumber = new HashSet<String>();

		for (int i = 0; i < resultInvestigationRequests.size(); i++) {
			billNumber.add(resultInvestigationRequests.get(i).getPatient().getBillNumber());
		}

		for (String value : billNumber) {

			AllInvestigationRequestEntityContainer allInvestigationRequestEntityContainer = new AllInvestigationRequestEntityContainer();
			List<AllInvestigationRequestEntity> allInvestigationRequestEntityList = new ArrayList<AllInvestigationRequestEntity>();

			allInvestigationRequestEntityContainer.setBillNumber(value);

			for (int i = 0; i < resultInvestigationRequests.size(); i++) {

				AllInvestigationRequestEntity allInvestigationRequestEntity = new AllInvestigationRequestEntity();

				if (resultInvestigationRequests.get(i).getPatient().getBillNumber().equals(value)) {

					allInvestigationRequestEntityContainer
							.setMobile(resultInvestigationRequests.get(i).getPatient().getMobile());
					allInvestigationRequestEntityContainer
							.setName(resultInvestigationRequests.get(i).getPatient().getName());

					allInvestigationRequestEntity.setDepartmentName(
							resultInvestigationRequests.get(i).getInvestigation().getDepartment().getName());
					allInvestigationRequestEntity
							.setRate(resultInvestigationRequests.get(i).getInvestigation().getRate());
					allInvestigationRequestEntity
							.setInvestigationName(resultInvestigationRequests.get(i).getInvestigation().getName());
					allInvestigationRequestEntity
							.setInvestigationRequestId(resultInvestigationRequests.get(i).getInvestigation().getId());

					allInvestigationRequestEntityList.add(allInvestigationRequestEntity);
				}
			}

			allInvestigationRequestEntityContainer.setRequestList(allInvestigationRequestEntityList);
			allInvestigationRequestEntityContainerList.add(allInvestigationRequestEntityContainer);
		}

		return allInvestigationRequestEntityContainerList;
	}

	public boolean deleteReportResult(String billNumber, int investigationRequestId, String departmentName,
			int providerId) {

		Department department = departmentRepository.findByName(departmentName);
		Patient patient = patientRepository.findByBillNumber(billNumber);
		InvestigationRequest investigationRequest = investigationRequestRepository
				.findByIdAndProviderId(investigationRequestId, providerId);

		if (department.getType().equals("List")) {

			List<InvestigationRequest> investigationRequestslist = investigationRequestRepository
					.fetchInvesrigationRequestBydeptAndbillIdAndProviderId(billNumber, departmentName, providerId);

			for (int i = 0; i < investigationRequestslist.size(); i++) {
				investigationRequestslist.get(i).setReportText(null);
				investigationRequestRepository.save(investigationRequestslist.get(i));
			}

			return true;
		} else if (department.getType().equals("Individual")) {

			Optional<InvestigationRequest> investigationRequest2 = investigationRequestRepository
					.findById(investigationRequestId);

			if (investigationRequest2.isPresent()) {
				investigationRequest2.get().setReportText(null);
				investigationRequestRepository.save(investigationRequest2.get());
				return true;
			}
		}

		else if (department.getType().equals("Parameter")) {

			Optional<InvestigationRequest> investigationRequest2 = investigationRequestRepository
					.findById(investigationRequestId);

			if (investigationRequest2.isPresent()) {
				investigationRequest2.get().setReportText(null);
				investigationRequestRepository.save(investigationRequest2.get());

				List<InvestivationParameterResult> investivationParameterResult = investivationParameterResultRepository
						.findByInvestigationRequestId(investigationRequestId);

				for (int i = 0; i < investivationParameterResult.size(); i++) {

					investivationParameterResultRepository.deleteById(investivationParameterResult.get(i).getId());
				}

				return true;
			} else {
				System.out.println("not Found=========");
			}

			return true;
		}

		return false;
	}

}

//table.addCell(new Phrase(new Chunk("cell 7")));
//table.addCell("Text 7 ");
//table.addCell(new Phrase(new Chunk("cell")));

// ImageData data =
// ImageDataFactory.create(investigationReport.getReportImageValue());
//
//patienName.setSpacingAfter(8f);
//investigationName.setSpacingAfter(8f);
//rate.setSpacingAfter(8f);
//createdDate.setSpacingAfter(8f);
//reportText.setSpacingAfter(8f);
//Paragraph patienName = new Paragraph();
//secondPart = new Chunk(investigationReport.getPatientName(), commonFont);
//patienName.add(firstPart);
//patienName.add(secondPart);
//
//Paragraph investigationName = new Paragraph();
//firstPart = new Chunk("Service Name : ", boldFont);
//secondPart = new Chunk(investigationReport.getInvestigationName(), commonFont);
//investigationName.add(firstPart);
//investigationName.add(secondPart);
//
//Paragraph rate = new Paragraph();
//firstPart = new Chunk("Cost: ", boldFont);
//secondPart = new Chunk(Integer.toString(investigationReport.getRate()), commonFont);
//rate.add(firstPart);
//rate.add(secondPart);
//
//Paragraph createdDate = new Paragraph();
//firstPart = new Chunk("Report Create Date : ", boldFont);
//secondPart = new Chunk(investigationReport.getCreated_date(), commonFont);
//createdDate.add(firstPart);
//createdDate.add(secondPart);
//
//Paragraph reportText = new Paragraph();
//firstPart = new Chunk("Report : ", boldFont);
//secondPart = new Chunk(investigationReport.getReportTextValue(), commonFont);
//reportText.add(firstPart);
//reportText.add(secondPart);
//

//Date date = new Date();
//SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm");
//
//System.out.println("timeFormat " + timeFormat.format(date).toString());
//
//investigationRequest.setDate(dateFormat.format(date).toString());
//investigationRequest.setHour(timeFormat.format(date).toString().substring(0, 2));
//investigationRequest.setMinute(timeFormat.format(date).toString().substring(3));
//investigationRequest.setStatus("Pandding");
//investigationRequest.setReportUrl("Not Implement Yet");
//investigationRequest.setDownloadStatus("Not Implement Yet");

//{"patientName":"Md Ibrahim Khan","mobile":"01521453788","investigationName":"Calcium blood test","reportText":"Corona nagative","referenceId":"2","billStatus":"true","gender":"Male","age":"24"}
