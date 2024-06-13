package com.willian.financial_organizer.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.willian.financial_organizer.model.Releases;
import com.willian.financial_organizer.model.User;
import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.model.enums.ReleasesTypes;

import java.math.BigDecimal;
import java.time.LocalDate;

@JsonPropertyOrder({
        "id", "description", "value", "status", "type", "userId",
        "registration_date", "year", "month",
})
public class ReleasesDTO {
    private Long id;

    private String description;

    private Integer month;

    private Integer year;

    @JsonProperty("user_id")
    private Long userId;

    private BigDecimal value;

    @JsonProperty("registration_date")
    private LocalDate registrationDate;

    private ReleasesTypes type;

    private ReleasesStatus status;

    public ReleasesDTO() {
    }

    public ReleasesDTO(Releases releases) {
        this.id = releases.getId();
        this.description = releases.getDescription();
        this.month = releases.getMonth();
        this.year = releases.getYear();
        this.userId = releases.getUserId().getId();
        this.value = releases.getValue();
        this.registrationDate = releases.getRegistrationDate();
        this.type = releases.getType();
        this.status = releases.getStatus();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public ReleasesTypes getType() {
        return type;
    }

    public void setType(ReleasesTypes type) {
        this.type = type;
    }

    public ReleasesStatus getStatus() {
        return status;
    }

    public void setStatus(ReleasesStatus status) {
        this.status = status;
    }
}
