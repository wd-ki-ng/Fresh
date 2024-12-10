package com.fresh.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fresh.repository.MypageRepository;

@Service
public class MypageService {
	
	@Autowired
	private MypageRepository mypageRepository;


}
