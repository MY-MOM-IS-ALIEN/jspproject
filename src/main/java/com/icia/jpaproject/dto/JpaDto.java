package com.icia.jpaproject.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class JpaDto {
    private Long code;
    private String strData;
    private String intData;
    private Timestamp regData;
}
