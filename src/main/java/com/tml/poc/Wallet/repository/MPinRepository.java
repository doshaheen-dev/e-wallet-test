package com.tml.poc.Wallet.repository;

import com.tml.poc.Wallet.models.account.AccountModel;
import com.tml.poc.Wallet.models.mpin.MPINModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MPinRepository extends JpaRepository<MPINModel, Long> {


	List<MPINModel> findAll();
	Optional<MPINModel> findAllById(long id);
	Optional<MPINModel> findByUserID(long userid);
	Optional<MPINModel> findByUserIDAndIsActive(long userid,boolean isActive);

}
