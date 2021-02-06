package com.diagnostic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diagnostic.model.InvestigationRequest;

public interface InvestigationRequestRepository extends JpaRepository<InvestigationRequest, Integer> {

	@Query("select IR.reportText from InvestigationRequest IR  where  IR.patient.mobile = ?1")
	List<String> getUserReport(String mobile);

	@Query("select IR from InvestigationRequest IR  where  IR.patient.billNumber = ?1 and IR.investigation.department.name = ?2 and IR.provider.id = ?3")
	List<InvestigationRequest> fetchInvesrigationRequestBydeptAndbillIdAndProviderId(String billNo,
			String departmentName, int providerID);

	List<InvestigationRequest> findByPatientBillNumberAndPatientMobileAndProviderId(String billNo, String mobile,
			int providerId);

	List<InvestigationRequest> findByPatientBillNumberAndProviderId(String billNo, int providerId);

	List<InvestigationRequest> findByProviderId(int providerId);

	InvestigationRequest findByIdAndProviderId(int investigationRequestID, int providerId);

//	@Query("SELECT new com.diagnostic.model.UserInvestigationReport(i.id, p.billNumber, p.name, p.age, p.gender, i.investigation.name, i.investigation.name, i.investigation.rate, i.update_date, i.referenceId, i.reportImage, i.reportText) "
//			+ "FROM InvestigationRequest i JOIN i.patient p where i.providerInvestigation.id = ?1 and p.mobile =?2")
//	List<UserInvestigationReport> getUserReportList(int providerId, String mobile);
//	
//	@Query("SELECT new com.diagnostic.model.UserInvestigationReport(i.id, p.billNumber, p.name, p.age, p.gender, i.investigation.name, i.investigation.name, i.investigation.rate, i.update_date, i.referenceId, i.reportImage, i.reportText) "
//			+ "FROM InvestigationRequest i JOIN i.patient p where  i.id =:investigationRequestId and i.providerInvestigation.id =:providerId")
//	UserInvestigationReport getUserReportPDf(@Param("investigationRequestId") int investigationRequestId, @Param("providerId") int providerId);

}
