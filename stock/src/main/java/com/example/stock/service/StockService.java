package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }

    @Transactional
    public void decreaseWithPessimisticLock(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithPessimisticLock(id);
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }

    @Transactional
    public void decreaseWithOptimisticLock(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithOptimisticLock(id);
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
