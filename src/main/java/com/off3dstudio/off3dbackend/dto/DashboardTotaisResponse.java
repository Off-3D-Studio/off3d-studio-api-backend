package com.off3dstudio.off3dbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class DashboardTotaisResponse {

    private final BigDecimal totalReceber;
    private final BigDecimal totalPagar;
    private final BigDecimal saldo;

}
