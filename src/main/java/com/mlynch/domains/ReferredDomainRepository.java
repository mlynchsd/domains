package com.mlynch.domains;

import fj.data.Either;

/**
 * Persistence for referred domains
 */
public interface ReferredDomainRepository {

	Either<String, ReferredDomain> save(ReferredDomain rd);

}
