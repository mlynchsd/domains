package com.mlynch.domains;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DomainsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DomainsApplication.class, args);
	}

	@Bean
	public ReferralsService referralsService() {
		return new SimpleReferralsService(referredDomainWriter(), referralCountsService());
	}

	@Bean
	public BlockingQueue<ReferredDomain> queue() {
		return new LinkedBlockingQueue();
	}

	@Bean
	public ScheduledExecutorService scheduler() {
		return 	Executors.newSingleThreadScheduledExecutor();

	}

	@Bean
	public ReferredDomainWriter referredDomainWriter() {
		return new InMemoryReferredDomainWriter(queue());
	}

	@Bean
	public ReferralCountsService referralCountsService() {
		return new InMemoryReferralCountsService(queue(), scheduler());
	}

	@Bean
	public ReferredDomainRepository referredDomainRepository() {
		return new InMemoryReferredDomainRepository();
	}

}
