package com.sample.ad.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.util.ReflectionTestUtils;

import com.sample.ad.config.TestConfig;
import com.sample.ad.dao.CampaignDAO;
import com.sample.ad.domain.Ad;
import com.sample.ad.domain.Campaign;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
public class AdControllerTest {
	@Autowired
	CampaignDAO campaignDao;
	AdController adController;
	Campaign campaign;
	Ad ad;
	@Before
	public void setup(){
		adController = new AdController();
		ReflectionTestUtils.setField(adController, "campaignDao", campaignDao);
		ad = new Ad();
		ad.setAd_content("test");
		ad.setDuration(10);
		ad.setPartner_id("1");
		campaign = new Campaign();
		campaign.setAd_content("test");
		campaign.setDuration(10);
		campaign.setPartner_id("1");
	}
	@Test
	public void testadd(){
		adController.add(ad);
		assertEquals(ad.getAd_content(),campaignDao.getCampaign("1").getAd_content());
	}
	@Test
	public void testgetPartnerData(){
		ResponseEntity response = adController.getPartnerData("1");
		assertNotNull(response);
	}
	@Test
	public void testgetList(){
		ResponseEntity response = adController.getList();
		assertNotNull(response);
	}
	@Test
	public void testgetaddMultiple(){
		ResponseEntity response = adController.addMultiple(ad);
		assertNotNull(response);
	}


}
