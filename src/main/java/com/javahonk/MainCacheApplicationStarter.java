package com.javahonk;

import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.javahonk.cacheloader.CacheLoader;

public class MainCacheApplicationStarter {
	
	private static final Logger logger = LogManager.getLogger(MainCacheApplicationStarter.class.getName());
	
	public static void main(String[] args) {
		
		logger.info("Coherence cache started...");
		
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		CacheLoader cacheLoader = (CacheLoader)context.getBean(CacheLoader.class);
		cacheLoader.LoadPrices(LocalDate.now());
		cacheLoader.printAllCahcePrice();
		cacheLoader.getPriceUsingFilter();
		//cacheLoader.ClearPrices();
		
		logger.info("All cache opration is done...");
		
		//Close application context
		((AbstractApplicationContext)context).close();
		
	}
}
