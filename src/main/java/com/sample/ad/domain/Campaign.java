package com.sample.ad.domain;

import java.util.Date;

public class Campaign {
	private String partner_id;
	private int duration;
	private String ad_content;
	private Date campaignCreationTime;
	public String getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(String partner_id) {
		this.partner_id = partner_id;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getAd_content() {
		return ad_content;
	}
	public void setAd_content(String ad_content) {
		this.ad_content = ad_content;
	}
	public Date getCampaignCreationTime() {
		return campaignCreationTime;
	}
	public void setCampaignCreationTime(Date campaignCreationTime) {
		this.campaignCreationTime = campaignCreationTime;
	}
	@Override
	public boolean equals(Object obj) {
		Campaign camp = (Campaign) obj;
		return this.partner_id.equals(camp.getPartner_id())&& this.getAd_content().equals(camp.getAd_content());
	}

}
