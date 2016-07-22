package com.sample.ad.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sample.ad.domain.Campaign;
import com.sample.ad.exception.ActiveCampaignException;
@Component("campaignDao")
public class CampaignDAO {
	private Map<String,Campaign> map = new HashMap<String, Campaign>();
	private Map<String,List<Campaign>> multiCamp = new HashMap<String, List<Campaign>>();
	public boolean addCampaign(Campaign campaign) throws ActiveCampaignException{
		boolean flag = false;
		if (!map.containsKey(campaign.getPartner_id())){
			campaign.setCampaignCreationTime(new Date());
			map.put(campaign.getPartner_id(), campaign);
		}else{
			Campaign existing = map.get(campaign.getPartner_id());
			Date existingDuration = addSecondsToDate(existing.getDuration(), existing.getCampaignCreationTime());
			if (existingDuration.compareTo(new Date())>0){
				throw new ActiveCampaignException("activeCampaign");
			}
			campaign.setCampaignCreationTime(new Date());
			map.put(campaign.getPartner_id(), campaign);
		}
		return flag;
	}
	public Campaign getCampaign(String id){
		if (map.containsKey(id)){
			Campaign existing = map.get(id);
			Date existingDuration = addSecondsToDate(existing.getDuration(), existing.getCampaignCreationTime());
			if (existingDuration.compareTo(new Date())<0){
				map.remove(id);
				return null;
			}
			return existing;
		}else{
			return null;
		}
	}
	public Collection<Campaign> getAll(){
		return map.values();
	}
	
	private static Date addSecondsToDate(int seconds, Date beforeTime){
	    final long ONE_SECOND_IN_MILLIS = 1000;//millisecs
	    long curTimeInMs = beforeTime.getTime();
	    Date afterAddingSeconds = new Date(curTimeInMs + (seconds * ONE_SECOND_IN_MILLIS));
	    return afterAddingSeconds;
	}
	public boolean addMultiCampaign(Campaign campaign) throws ActiveCampaignException{
		boolean flag = false;
		if (!multiCamp.containsKey(campaign.getPartner_id())){
			campaign.setCampaignCreationTime(new Date());
			List<Campaign> list = new ArrayList<Campaign>();
			list.add(campaign);
			multiCamp.put(campaign.getPartner_id(),list);
		}else{
			List<Campaign> list = multiCamp.get(campaign.getPartner_id());
			if (!list.contains(campaign)){
				campaign.setCampaignCreationTime(new Date());
				list.add(campaign);
			}else{
				int x = getExistingCampIndex(campaign.getPartner_id(), campaign.getAd_content(), list);
				Campaign existing = list.get(x);
				Date existingDuration = addSecondsToDate(existing.getDuration(), existing.getCampaignCreationTime());
				if (existingDuration.compareTo(new Date())>0){
					throw new ActiveCampaignException("activeCampaign");
				}
				list.remove(x);
				campaign.setCampaignCreationTime(new Date());
				list.add(campaign);
			}
		}
		return flag;
	}
	private int getExistingCampIndex(String partnerId,String adContent,List<Campaign> campList){
		int i =-1;
		for (int index=0;i<campList.size();index++){
			Campaign c =campList.get(index);
			if (partnerId.equals(c.getPartner_id())&&adContent.equals(c.getAd_content())){
				i=index;
				break;
			}
		}
		return i;
	}

}
