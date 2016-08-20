package com.javahonk.cacheloader;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import com.javahonk.pricecacheserializers.Prices;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.filter.AndFilter;
import com.tangosol.util.filter.EqualsFilter;
import com.wachovia.cib.spt.datautil.CacheRegistry;

public class CacheLoader {

	@Value("${marketdata_cache}")
	private String marketDataCacheName;
	
	@Value("${days_to_keep}")
	private int days_to_keep;
	
	@Value("${date_format}")
	private String date_format;
	
	private NamedCache marketdatacache;
	
	private static final Logger logger = LogManager.getLogger(CacheLoader.class.getName());
	
	private static int counter = 0;
	
	@PostConstruct
	public void init(){
		
		marketdatacache = (NamedCache) CacheRegistry.getNamedCache(marketDataCacheName);
	}
	
	public void LoadPrices(LocalDate date){
		
		List<Prices> list = getPrice();
		
		for(Prices prices : list){
			
			Prices cachePrice = new Prices(
					prices.getSymbol(), 
					prices.getCusip(), 
					prices.getCompositeRicCode(), 
					prices.getClosingPrice(), 
					prices.getClosingDate()
					);
			
			String cacheKey = prices.getSymbol()+"_"+prices.getClosingDate();
			
			try {
				
				logger.info("Sending Price with detials to Cache:\n" + 
					"Key:" + cacheKey + "\n" + prices.toString());
				
				marketdatacache.put(cacheKey, cachePrice);
				counter++;
			} 
			catch (Exception e) {
				
				logger.error("Failed to write following to cache:\n" + 
						cacheKey + ":" + prices.toString(), e);
				
			}
			
			logger.info("Number of Symbols Loaded into Cache:" + counter);
		}
		
		logger.info("CacheLoader Exiting.");
		
	}
	
	@SuppressWarnings("unchecked")
	public void ClearPrices(){
		
		LocalDate dateToRemove = LocalDate.now().minusDays(days_to_keep);
		
		String dateString = dateToRemove.format(DateTimeFormatter.ofPattern(date_format));
		
		Set<String> keySet = marketdatacache.keySet();
		
		for(String key: keySet){
			
			String datePart = key.split("_")[1];
			
			if(Objects.equals(datePart, dateString)){
				
				logger.info("Removing Key:" + key);
				marketdatacache.remove(key);
				
			}
			
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public void printAllCahcePrice(){
		
		Set<String> keySet = marketdatacache.keySet();
		
		logger.info("Total Cache size:-->"+keySet.size());
		
		for (String key : keySet) {

			if (key.equalsIgnoreCase("Testing9_2015-08-16")) {
				logger.info("Key found: -->{}", key);
			}
			logger.info("Key name: {}", key);
			String datePart = key.split("_")[1];
			logger.info(key + "-->date part:-->" + datePart);

		}
		
		Set<Entry<String, Prices>> entries = marketdatacache.entrySet();
		
		for (Entry<String, Prices> entry : entries) {
			Prices price = entry.getValue();
			if (price != null) {

				if (price.getSymbol().equalsIgnoreCase("Testing3")) {
					double closingPrice = price.getClosingPrice();
					logger.info("Closing price: --> {}",closingPrice);
				}				
			}
		}
		
	}
	
	@SuppressWarnings({"unchecked"})
	public void getPriceUsingFilter() {
		
		logger.info("Entered into updateClosingPriceInMarketDataCache class: {} "+ getClass().getName());

		try {

			String underlier = "Testing3";
			LocalDateTime dateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String dateString = dateTime.format(formatter);
			
			EqualsFilter ricCodeFilter = new EqualsFilter("getSymbol", underlier);
			EqualsFilter closingDateFilter = new EqualsFilter("getClosingDate", dateString);
			
			//Get by key name;
			Prices prices = (Prices)marketdatacache.get("Testing4_2015-08-16");
			System.out.println(prices.getClosingDate());
			
			Filter compositeFilter = new AndFilter(ricCodeFilter,closingDateFilter);
			Set<Entry<String, Prices>> entries = marketdatacache.entrySet(compositeFilter);			

			for (Entry<String, Prices> entry : entries) {
				Prices price = entry.getValue();
				if (price != null) {
					double closingPrice = price.getClosingPrice();
					logger.info("Closing price: -->{}", closingPrice);
				}

			}

		} catch (Exception e) {
			logger.error("Error while attempting to get market close price {}",e);
		}
		
		logger.info("Exit from updateClosingPriceInMarketDataCache class: {} ",getClass().getName());		
	}
	
	public List<Prices> getPrice(){		
		
		List<Prices> list = new ArrayList<Prices>();
		for (int i = 0; i < 10; i++) {
			Prices prices = new Prices("Testing"+String.valueOf(i), "123456789", String.valueOf(Math.random()), Math.random(), LocalDate.now().toString());
			list.add(prices);
		}
		return list;
		
	}
	
	
}
