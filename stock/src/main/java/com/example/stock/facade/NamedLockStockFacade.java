package com.example.stock.facade;

import com.example.stock.repository.StockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class NamedLockStockFacade {

    private final StockRepository stockRepository;

    private final StockService stockService;

    public NamedLockStockFacade(StockRepository stockRepository, StockService stockService) {
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        try {
            stockRepository.getLock(id.toString());
            stockService.decrease(id, quantity);
        } finally {
            stockRepository.releaseLock(id.toString());
        }
    }
}
