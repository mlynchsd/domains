package com.mlynch.domains;

import fj.data.Either;

/**
 * Writer for referrals
 */
public interface ReferredDomainWriter {

	Either<String, ReferredDomain> write(ReferredDomain rd);

}
