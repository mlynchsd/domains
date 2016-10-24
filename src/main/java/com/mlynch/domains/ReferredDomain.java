package com.mlynch.domains;

import java.net.URL;
import java.time.Instant;

import fj.data.Either;

/**
 * Data type for referred domain event
 */
public class ReferredDomain {
	public final DomainName domainName;
	public final String clientId;
	public final Instant timestamp;

	private ReferredDomain(DomainName domainName, String clientId, Instant timestamp) {
		this.domainName = domainName;
		this.clientId = clientId;
		this.timestamp = timestamp;
	}

	public static Either<String, ReferredDomain> referral(String clientId, URL url) {
		return DomainName.domainName(url).right().bind(
			dn -> Either.right(new ReferredDomain(dn, clientId, Instant.now())));
	}

	public static ReferredDomain copy(ReferredDomain rd) {
		return new ReferredDomain(rd.domainName, rd.clientId, rd.timestamp);
	}
}
