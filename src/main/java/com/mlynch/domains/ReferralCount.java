package com.mlynch.domains;

import fj.data.Either;

/**
 * Data type for referred domain event
 */
public class ReferralCount implements Comparable<ReferralCount> {

	public final DomainName domainName;
	public final Long count;

	private ReferralCount(DomainName domainName, Long count) {
		this.domainName = domainName;
		this.count = count;
	}

	public static Either<String, ReferralCount> count(DomainName dn, Long count) {
		if (dn == null) {
			return Either.left("domanin name must be provided");
		}
		if (count == null || count < 0) {
			return Either.left("nonnegative count must be provided");
		}
		return Either.right(new ReferralCount(dn, count));
	}

	@Override
	public int compareTo(ReferralCount rc) {
		return count.compareTo(rc.count);
	}

}
