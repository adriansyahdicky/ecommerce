package com.komputama.ecommerce.dto.request.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemChartDto {
    private String transactionId;
    private List<ItemChartDto> itemCharts;
}
