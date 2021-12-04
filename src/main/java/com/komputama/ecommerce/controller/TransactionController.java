package com.komputama.ecommerce.controller;

import com.komputama.ecommerce.dto.request.RequestChartDto;
import com.komputama.ecommerce.dto.request.item.AddItemChartDto;
import com.komputama.ecommerce.dto.response.ResponseChartCalculation;
import com.komputama.ecommerce.dto.response.ResponseItemChart;
import com.komputama.ecommerce.dto.response.ResponseRequestChart;
import com.komputama.ecommerce.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TransactionController {

    private final ChartService chartService;

    @PostMapping(value = "/chart/item/request")
    public ResponseRequestChart requestChart(@RequestBody RequestChartDto requestChartDto){
        return chartService.requestChart(requestChartDto);
    }

    @PostMapping(value = "/chart/item/add")
    public List<ResponseItemChart> addChart(@RequestBody AddItemChartDto addItemChartDto){
        return chartService.addChart(addItemChartDto);
    }

    @GetMapping(value = "/chart/item/remove/{idItem}")
    public String removeChart(@PathVariable(value = "idItem") String idItem){
        return chartService.removeChartByid(idItem);
    }

    @PostMapping(value = "/chart/item/calculate/{transactionId}")
    public ResponseChartCalculation calculatePrice(@PathVariable(value = "transactionId")
                                                               String transactionId){
        return chartService.calculationPrice(transactionId);
    }

}
