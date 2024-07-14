package com.rjgc.cpy.investtrackpro.repository;

import com.rjgc.cpy.investtrackpro.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Asset, Integer> {
    List<Asset> findByUserId(int userId);

    boolean existsByUserIdAndInvestmentId(int userId, int investmentId);

    Asset findByUserIdAndInvestmentId(int userId, int investmentId);

    BigDecimal findAmountByUserIdAndInvestmentId(int userId, int investmentId);
}