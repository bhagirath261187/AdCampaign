package com.sample.ad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sample.ad.dao.CampaignDAO;
import com.sample.ad.domain.Ad;
import com.sample.ad.domain.Campaign;
import com.sample.ad.exception.ActiveCampaignException;

@Controller
public class AdController {
	@Autowired
	CampaignDAO campaignDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/ad", method = RequestMethod.POST)
	public ResponseEntity add(@RequestBody Ad ad) {
		Campaign campaign = new Campaign();
		campaign.setAd_content(ad.getAd_content());
		campaign.setDuration(ad.getDuration());
		campaign.setPartner_id(ad.getPartner_id());
		try {
			campaignDao.addCampaign(campaign);
			return new ResponseEntity(
					"Campaign is created successfully for the partner :"
							+ ad.getPartner_id(), HttpStatus.ACCEPTED);
		} catch (ActiveCampaignException ex) {
			ex.printStackTrace();
			return new ResponseEntity(
					"One Active Campaign is already existed for given partner :"
							+ ad.getPartner_id(), HttpStatus.ALREADY_REPORTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Some un expected error is occured",
					HttpStatus.FORBIDDEN);
		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/ad/{id}", method = RequestMethod.GET)
	public ResponseEntity getPartnerData(@PathVariable("id") String id) {
		try {
			Campaign campaign = campaignDao.getCampaign(id);
			if (campaign != null) {
				return new ResponseEntity(campaign, HttpStatus.OK);
			} else {
				return new ResponseEntity(
						"No Active Campaign is  existed for given partner :"
								+ id, HttpStatus.ALREADY_REPORTED);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Some un expected error is occured",
					HttpStatus.FORBIDDEN);
		}

	}
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity getList() {
		try {
			return new ResponseEntity(campaignDao.getAll(),HttpStatus.OK );
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Some un expected error is occured",
					HttpStatus.FORBIDDEN);
		}

	}
	
	@RequestMapping(value = "/multiple/ad", method = RequestMethod.POST)
	public ResponseEntity addMultiple(@RequestBody Ad ad) {
		Campaign campaign = new Campaign();
		campaign.setAd_content(ad.getAd_content());
		campaign.setDuration(ad.getDuration());
		campaign.setPartner_id(ad.getPartner_id());
		try {
			campaignDao.addMultiCampaign(campaign);
			return new ResponseEntity(
					"Campaign is created successfully for the partner :"
							+ ad.getPartner_id(), HttpStatus.ACCEPTED);
		} catch (ActiveCampaignException ex) {
			ex.printStackTrace();
			return new ResponseEntity(
					"One Active Campaign is already existed for given partner :"
							+ ad.getPartner_id(), HttpStatus.ALREADY_REPORTED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Some un expected error is occured",
					HttpStatus.FORBIDDEN);
		}

	}
}
