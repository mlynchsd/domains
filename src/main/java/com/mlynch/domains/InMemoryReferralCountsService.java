package com.mlynch.domains;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableMap;

import fj.data.Either;

/**
 * In-memory referral counts service
 */
public class InMemoryReferralCountsService implements ReferralCountsService {

	private final BlockingQueue<ReferredDomain> queue;
	private final ScheduledExecutorService scheduler;
	private final Map<String, ReferralCount> referralCounts;

	@Autowired
	public InMemoryReferralCountsService(BlockingQueue<ReferredDomain> queue,
		ScheduledExecutorService scheduler) {
		this.queue = queue;
		this.scheduler = scheduler;
		this.referralCounts = new HashMap<>();

		scheduler.scheduleAtFixedRate(update, 0, 100, TimeUnit.MILLISECONDS);
	}

	@Override
	public Either<String, Map<String, ReferralCount>> counts() {
		return Either.right(ImmutableMap.copyOf(referralCounts));
	}

	Map<String, ReferralCount> updateCounts() {
		ReferredDomain rd = queue.poll();
		if (rd == null) {
			return referralCounts;
		}
		ReferralCount c = referralCounts.get(rd.domainName.value);
		if (c == null) {
			referralCounts.put(rd.domainName.value,
							   ReferralCount.count(rd.domainName, 1L).right().value());
		} else {
			referralCounts.put(c.domainName.value,
							   ReferralCount.count(rd.domainName, c.count + 1).right().value());
		}
		return referralCounts;
	}

	Runnable update = new Runnable() {
		@Override
		public void run() {
			updateCounts();
		}
	};
}
