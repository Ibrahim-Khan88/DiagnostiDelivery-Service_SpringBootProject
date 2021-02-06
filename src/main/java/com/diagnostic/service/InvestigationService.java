package com.diagnostic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.diagnostic.model.Department;
import com.diagnostic.model.FormatResponce;
import com.diagnostic.model.Investigation;
import com.diagnostic.model.Provider;
import com.diagnostic.model.Specimen;
import com.diagnostic.model.SubGroup;
import com.diagnostic.repository.DepartmentRepository;
import com.diagnostic.repository.DoctorRepository;
import com.diagnostic.repository.InvestigationRepository;
import com.diagnostic.repository.InvestigationRequestRepository;
import com.diagnostic.repository.ProviderRepository;
import com.diagnostic.repository.SpecimenRepository;
import com.diagnostic.repository.SubGroupRepository;

@Service
public class InvestigationService {

	@Autowired
	private InvestigationRepository investigationRepository;

	@Autowired
	private ProviderRepository providerRepository;

	@Autowired
	private InvestigationRequestRepository investigationRequestRepository;

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private SpecimenRepository specimenRepository;

	@Autowired
	private SubGroupRepository subGroupRepository;

	public int createInvestigation(Investigation requestInvestigation, int departmentId, int providerId, int subGroupId,
			int specimenId) {

		Optional<Investigation> isExists = investigationRepository
				.findByNameAndProviderId(requestInvestigation.getName(), providerId);

		if (isExists.isPresent()) {
			return 2;
		}

		System.out.print(requestInvestigation.toString() + "\n\n\n");

		Optional<Department> department = departmentRepository.findById(departmentId);
		Optional<Provider> provider = providerRepository.findById(providerId);
		Optional<SubGroup> subGroup = subGroupRepository.findById(subGroupId);
		Optional<Specimen> specimen = specimenRepository.findById(specimenId);

		if (department.isPresent() && provider.isPresent() && subGroup.isPresent() && specimen.isPresent()) {
			requestInvestigation.setDepartment(department.get());
			requestInvestigation.setProvider(provider.get());
			requestInvestigation.setSpecimen(specimen.get());
			requestInvestigation.setSubGroup(subGroup.get());
			return investigationRepository.save(requestInvestigation) != null ? 1 : 0;
		}

		return 0;

		// return investigationRepository.save(requestInvestigation);

	}

	// @SuppressWarnings("unused")
	public Investigation createInvestigationProviderInvestigationId(int providerInvestigationId, int categoryId,
			Investigation requestInvestigation) {

		Optional<Provider> providerInvestigation = providerRepository.findById(providerInvestigationId);

//		Test t = new Test();
//		t.setId(2);

		if (providerInvestigation.isPresent()) {

			requestInvestigation.setProvider(providerInvestigation.get());
			// requestInvestigation.setTest(t);
			return investigationRepository.save(requestInvestigation);
		}

		return null; // drop table investigation
	}

	public List<Investigation> fetchAllInvestigation() {

		List<Investigation> ListInvestigation = investigationRepository.findAllByOrderBySortingIdAsc();

		for (int i = 0; i < ListInvestigation.size(); i++) {
			ListInvestigation.get(i).setInvestigationRequest(null);
			ListInvestigation.get(i).setProvider(null);
		}

		return ListInvestigation;

	}

	public Investigation updateInvestigation(int investigationId, int providerId, int categoryId,
			Investigation updateInvestigation) {

		Investigation investigation = investigationRepository.findByIdAndProviderId(investigationId, providerId);

		if (investigation != null) {

			investigation.setDescription(updateInvestigation.getDescription());
			investigation.setName(updateInvestigation.getName());
			investigation.setNote(updateInvestigation.getNote());
			investigation.setRate(updateInvestigation.getRate());
			investigation.setFormat(updateInvestigation.getFormat());
			investigation.setRefValue(updateInvestigation.getRefValue());

			return investigationRepository.save(investigation);

		}

		return null;
	}

	public int deleteInvestigation(int investigationId) {

		boolean delete = investigationRepository.existsById(investigationId);

		if (delete) {

			investigationRepository.deleteById(investigationId);
			return 1;

		}

		return 0;
	}

	public List<Investigation> fetchInvestigationByProviderId(int providerId) {

		List<Investigation> ListInvestigation = investigationRepository.findByProviderId(providerId);
		List<Investigation> returnInvestigation = new ArrayList<Investigation>();

		int length = Math.min(200, ListInvestigation.size());

		for (int i = 0; i < length; i++) {
			returnInvestigation.add(ListInvestigation.get(i));
		}

		return returnInvestigation;

	}

	public List<Investigation> fetchInvestigationBySelectedItem(String[] selectedItem) {

		List<Investigation> investigationList = new ArrayList<>();

		for (int i = 0; i < selectedItem.length; i++) {

			Optional<Investigation> investigation = investigationRepository.findById(Integer.parseInt(selectedItem[i]));
			if (investigation.isPresent()) {
				investigationList.add(investigation.get());
			}
		}

		return investigationList;
	}

	public List<String> investigationNameList(int providerId) {

		List<Investigation> list = investigationRepository.findByProviderId(providerId);
		List<String> resultInvestigationRequestNameList = new ArrayList<>();

		// List<Investigation> list1 = investigationRepository.g;

		for (int i = 0; i < list.size(); i++) {
			resultInvestigationRequestNameList.add(list.get(i).getName());
		}

		return resultInvestigationRequestNameList;
	}

	public FormatResponce fetchInvestigationFormatByProviderIdAndInvestigationName(int providerId,
			String investigationName) {
		FormatResponce formatResponce = investigationRepository.getFormat(providerId, investigationName);

		return formatResponce;
	}

}
