package com.mlynch.domains;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import fj.data.Either;

/**
 * Simple implementation of referrals service
 */
public class SimpleReferralsService implements ReferralsService {

	private final ReferredDomainWriter rdWriter;
	private final ReferralCountsService refCounts;

	@Autowired
	public SimpleReferralsService(ReferredDomainWriter rdWriter, ReferralCountsService refCounts) {
		// TODO pre checks
		this.rdWriter = rdWriter;
		this.refCounts = refCounts;
	}

	@Override
	public Either<String, ReferredDomain> record(String clientId, String referringUrl) {
		// TODO precondition check
		try {
			return ReferredDomain.referral(clientId, new URL(referringUrl)).right()
				.bind(rd -> rdWriter.write(rd));
		} catch (MalformedURLException e) {
			return Either.left("Malformed url " + referringUrl);
		}
	}

	@Override
	public Either<String, List<ReferralCount>> fetchTop(int top) {
		return refCounts.counts().right()
			.bind(c -> Either.right(topCounts(c, top)));
	}

	static List<ReferralCount> topCounts(Map<String, ReferralCount> counts, int top) {
		return counts.values().stream()
			.sorted(Collections.reverseOrder())
			.limit(top)
			.collect(Collectors.toList());
	}
}
