package com.mlynch.domains;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import fj.data.Either;

/**
 * In-memory implementation of repo
 */
public class InMemoryReferredDomainRepository implements ReferredDomainRepository {

	private final Map<String, List<ReferredDomain>> domains = new ConcurrentHashMap<>();

	@Override
	public Either<String, ReferredDomain> save(ReferredDomain rd) {
		List<ReferredDomain> refs = domains.get(key(rd));
		if (refs == null) {
			refs = new ArrayList<>();
		}
		refs.add(rd);
		domains.put(key(rd), refs);

		return Either.right(rd);
	}

	String key(ReferredDomain rd) {
		return rd.domainName.value + "_" + rd.clientId;
	}
}
