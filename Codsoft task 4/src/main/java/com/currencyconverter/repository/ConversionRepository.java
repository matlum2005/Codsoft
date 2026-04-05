package com.currencyconverter.repository;

import com.currencyconverter.entity.ConversionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversionRepository extends JpaRepository<ConversionHistory, Long> {
}

