package com.sample.ad.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sample.ad.dao.CampaignDAO;
@Configuration
public class TestConfig {
	@Bean
	public CampaignDAO campaignDao(){
		return new com.sample.ad.dao.CampaignDAO();
	}
}
