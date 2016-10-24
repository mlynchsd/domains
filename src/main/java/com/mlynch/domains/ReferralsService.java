package com.mlynch.domains;

import java.util.List;

import fj.data.Either;

/**
 * Service for tracking referrals
 */
public interface ReferralsService {

	Either<String, ReferredDomain> record(String clientId, String referringUrl);

	Either<String, List<ReferralCount>> fetchTop(int top);
}
