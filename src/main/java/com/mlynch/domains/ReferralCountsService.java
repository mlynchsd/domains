package com.mlynch.domains;

import java.util.Map;

import fj.data.Either;

/**
 * Service to provide referral counts
 */
public interface ReferralCountsService {

	Either<String, Map<String, ReferralCount>> counts();

}
