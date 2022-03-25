package com.car.rental.rent;

import com.car.rental.car.Car;
import com.car.rental.report.ReturnReport;
import com.car.rental.user.User;
import com.car.rental.utils.Config;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "rents")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private Car car;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private ReturnReport report;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_TIME_FORMAT)
    private LocalDateTime start;
    @JsonFormat(pattern = Config.GLOBAL_LOCAL_DATA_TIME_FORMAT)
    private LocalDateTime end;
    private String comment;
    private BigDecimal finalPrice;
    private boolean confirmed;
    private boolean returned;
    private boolean deleted;
}
