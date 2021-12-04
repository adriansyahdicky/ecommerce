package com.komputama.ecommerce.service;

import com.komputama.ecommerce.dto.request.RequestChartDto;
import com.komputama.ecommerce.dto.request.item.AddItemChartDto;
import com.komputama.ecommerce.dto.response.ProductDto;
import com.komputama.ecommerce.dto.response.ResponseChartCalculation;
import com.komputama.ecommerce.dto.response.ResponseItemChart;
import com.komputama.ecommerce.dto.response.ResponseRequestChart;
import com.komputama.ecommerce.entity.Item;
import com.komputama.ecommerce.entity.Transaction;
import com.komputama.ecommerce.repository.ItemRepository;
import com.komputama.ecommerce.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChartService {

    private final TransactionRepository transactionRepository;

    private final ItemRepository itemRepository;

    private final ProductService productService;

    public ResponseRequestChart requestChart(RequestChartDto requestChartDto){
        try {
            return requestChartTransaction(requestChartDto);
        }catch (Exception ex){
            log.error("ERROR FUNCTION ADD CHART {} ", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ADD_CHART_ERROR");
        }

    }

    private ResponseRequestChart requestChartTransaction(RequestChartDto requestChartDto){
        Transaction transaction = new Transaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setDateBuy(new Date());
        transaction.setNameUser(requestChartDto.getUser());
        transactionRepository.save(transaction);

        return ResponseRequestChart.builder()
                        .transactionId(transaction.getId())
                        .dateBuy(transaction.getDateBuy())
                        .user(transaction.getNameUser()).build();
    }


    public List<ResponseItemChart> addChart(AddItemChartDto addChartDto){
        log.info("Item Charts {} ", addChartDto);
        addChartDto.getItemCharts()
                .forEach(itemChartDto -> {
                    Item item = new Item();
                    ProductDto productDto = findProductId(itemChartDto.getProductId());
                    if(!checkItemExits(addChartDto.getTransactionId(),
                            productDto.getProductId())) {
                        item.setId(UUID.randomUUID().toString());
                        item.setPrice(productDto.getPrice());
                        item.setProductId(productDto.getProductId());
                        item.setTransactionId(addChartDto.getTransactionId());
                        item.setQty(itemChartDto.getQty());
                        itemRepository.save(item);
                    }
                });
        return resultChartItem(addChartDto.getTransactionId());
    }

    private ProductDto findProductId(String productId){
        return productService.getProductById(productId);
    }

    private boolean checkItemExits(String transactionId, String productId){
        return !ObjectUtils.isEmpty(
                itemRepository.findByTransactionIdAndProductId(transactionId, productId));
    }

    private List<ResponseItemChart> resultChartItem(String transactionId){
        List<ResponseItemChart> itemCharts = new ArrayList<>();
        itemRepository.findByTransactionId(transactionId)
                .forEach(item -> {
                    ResponseItemChart responseItemChart = ResponseItemChart
                            .builder()
                            .idItemChart(item.getId())
                            .productId(item.getProductId()).build();
                    itemCharts.add(responseItemChart);
                });
        return itemCharts;
    }

    public String removeChartByid(String id){
        Item item = Optional.of(itemRepository.findById(id)).
                        get().orElseThrow(() -> new
                        ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ITEM ID CANNOT FOUND IN DATABASE "+ id));
        itemRepository.delete(item);
        return id;
    }


    public ResponseChartCalculation calculationPrice(String transactionId){
        BigDecimal total = new BigDecimal(0);
        for (Item item : itemRepository.findByTransactionId(transactionId)){
            total = item.getPrice().multiply(new BigDecimal(item.getQty())).add(total);
        }
        return ResponseChartCalculation.builder()
                .totalPrice(total)
                .transactionId(transactionId).build();
    }

}
