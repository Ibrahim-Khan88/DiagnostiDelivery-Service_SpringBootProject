package com.diagnostic.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Investigation extends Auditable<String> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@NotBlank
	String name;

	String description;

	String note;

	String format;

	String code;

	String selectionCode;

	String resultType;

	boolean vatApply;

	int vatApplyAmount;

	int discountAmount;

	int discountPer;

	int sortingId;

	String unit;

	@NotNull
	int rate;

	@NotNull
	String refValue;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties("investigation")
	private Provider provider;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties("investigation")
	private Department department;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties("investigation")
	private Specimen specimen;

	@ManyToOne(optional = false)
	@JsonIgnoreProperties("investigation")
	private SubGroup subGroup;

	@OneToMany(mappedBy = "investigation", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("investigation")
	private List<InvestigationRequest> investigationRequest;

	@ManyToMany
	@JoinTable(name = "investigationParameters")
	@JsonIgnoreProperties("investigation")
	private List<Parameter> parameters;

	@ManyToMany
	@JoinTable(name = "investigationParamGroup")
	@JsonIgnoreProperties("investigation")
	private List<ParamGroup> paramGroup;

	@ManyToMany
	@JoinTable(name = "investigationAccessories")
	@JsonIgnoreProperties("investigation")
	List<Accessories> accessories;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSelectionCode() {
		return selectionCode;
	}

	public void setSelectionCode(String selectionCode) {
		this.selectionCode = selectionCode;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public boolean isVatApply() {
		return vatApply;
	}

	public void setVatApply(boolean vatApply) {
		this.vatApply = vatApply;
	}

	public int getVatApplyAmount() {
		return vatApplyAmount;
	}

	public void setVatApplyAmount(int vatApplyAmount) {
		this.vatApplyAmount = vatApplyAmount;
	}

	public int getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(int discountAmount) {
		this.discountAmount = discountAmount;
	}

	public int getDiscountPer() {
		return discountPer;
	}

	public void setDiscountPer(int discountPer) {
		this.discountPer = discountPer;
	}

	public int getSortingId() {
		return sortingId;
	}

	public void setSortingId(int sortingId) {
		this.sortingId = sortingId;
	}

	public Specimen getSpecimen() {
		return specimen;
	}

	public void setSpecimen(Specimen specimen) {
		this.specimen = specimen;
	}

	public SubGroup getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(SubGroup subGroup) {
		this.subGroup = subGroup;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}

	public List<ParamGroup> getParamGroup() {
		return paramGroup;
	}

	public void setParamGroup(List<ParamGroup> paramGroup) {
		this.paramGroup = paramGroup;
	}

	public List<Accessories> getAccessories() {
		return accessories;
	}

	public void setAccessories(List<Accessories> accessories) {
		this.accessories = accessories;
	}

	public String getUnit() {
		return unit;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public void setInvestigationRequest(List<InvestigationRequest> investigationRequest) {
		this.investigationRequest = investigationRequest;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<InvestigationRequest> getInvestigationRequest() {
		return investigationRequest;
	}

	public String getRefValue() {
		return refValue;
	}

	public void setRefValue(String refValue) {
		this.refValue = refValue;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "Investigation [id=" + id + ", name=" + name + ", description=" + description + ", note=" + note
				+ ", format=" + format + ", code=" + code + ", selectionCode=" + selectionCode + ", resultType="
				+ resultType + ", vatApply=" + vatApply + ", vatApplyAmount=" + vatApplyAmount + ", discountAmount="
				+ discountAmount + ", discountPer=" + discountPer + ", sortingId=" + sortingId + ", unit=" + unit
				+ ", rate=" + rate + ", refValue=" + refValue + "]";
	}

}
