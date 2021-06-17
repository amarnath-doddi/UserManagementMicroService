package com.user.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.user.bean.UserAccountDTO;
@FeignClient(url ="http://localhost:9014/fund",value = "fundService")
public interface FundService {
	@PostMapping("/account/")
	public ResponseEntity<UserAccountDTO> createAccount(@RequestBody UserAccountDTO account);
}
