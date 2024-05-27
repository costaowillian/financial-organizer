package com.willian.financial_organizer.model;

import com.willian.financial_organizer.model.enums.ReleasesStatus;
import com.willian.financial_organizer.model.enums.ReleasesTypes;
import jakarta.persistence.*;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "lancamento", schema = "fianncas")
public class Releases {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false, length = 100)
    private String description;

    @Column(name = "mes")
    private Integer month;

    @Column(name = "ano")
    private Integer year;

    @ManyToOne()
    @JoinColumn(name = "id_usuario")
    private User userId;

    @Column(name = "valor", nullable = false)
    private BigDecimal value;

    @Column(name = "data_cadastro", nullable = false)
    //@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class) use in sp 2.x
    private LocalDate registrationDate;

    @Column(name = "tipo", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReleasesTypes type;

    @Column(name = "status", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReleasesStatus status;

    public Releases() {
    }

    public Releases(Long id, String description, Integer month, Integer year, User userId, BigDecimal value, LocalDate registrationDate, ReleasesTypes type, ReleasesStatus status) {
        this.id = id;
        this.description = description;
        this.month = month;
        this.year = year;
        this.userId = userId;
        this.value = value;
        this.registrationDate = registrationDate;
        this.type = type;
        this.status = status;
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

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Releases releases = (Releases) o;
        return Objects.equals(id, releases.id) && Objects.equals(description, releases.description) && Objects.equals(month, releases.month) && Objects.equals(year, releases.year) && Objects.equals(userId, releases.userId) && Objects.equals(value, releases.value) && Objects.equals(registrationDate, releases.registrationDate) && type == releases.type && status == releases.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, month, year, userId, value, registrationDate, type, status);
    }
}
