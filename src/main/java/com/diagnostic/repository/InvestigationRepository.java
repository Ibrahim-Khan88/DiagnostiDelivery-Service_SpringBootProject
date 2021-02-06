package com.diagnostic.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.diagnostic.model.FormatResponce;
import com.diagnostic.model.Investigation;

public interface InvestigationRepository extends JpaRepository<Investigation, Integer> {

	// List<Investigation> findAllOrderBySortingIdAsc();
	public List<Investigation> findAllByOrderBySortingIdAsc();

	List<Investigation> findByProviderId(int providerId);

	boolean existsByid(int investigationId);

	@Query("select i.name from Investigation i where  i.provider.id = ?1")
	List<String> getNameAndRateOnly(int providerId);

	@Query("select i.format from Investigation i where  i.provider.id = ?1 and i.name = ?2")
	String fetchInvestigationNameByProviderIdAndInvestigationName(int providerId, String investigationName);

	Optional<Investigation> findByNameAndProviderId(String Name, int providerId);

	@Query("SELECT new com.diagnostic.model.FormatResponce(i.format, i.refValue, i.refValue) "
			+ "FROM Investigation i  where i.provider.id = ?1 and i.name =?2")
	FormatResponce getFormat(int providerId, String investigationName);

	boolean existsByidAndProviderId(int investigationId, int providerId);

	Investigation findByIdAndProviderId(int investigationId, int providerId);

	Investigation findByName(String selectedInvestigation);

//	@Query("select i.name, i.rate from Investigation i" )
//	interface ProjectIdAndName{
//	    String getId();
//	    String getName();
//	}
//	List<ProjectIdAndName> findAll();

}
