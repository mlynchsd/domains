package com.mlynch.domains;

/**
 * Referral count resource rep
 */
public class ReferralCountRep {
	public final String domainName;
	public final long count;

	public ReferralCountRep(String domainName, long count) {
		this.domainName = domainName;
		this.count = count;
	}
}
