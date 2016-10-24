package com.mlynch.domains;

import java.util.concurrent.BlockingQueue;

import fj.data.Either;

/**
 * In-memory writer for referred domains
 */
public class InMemoryReferredDomainWriter implements ReferredDomainWriter {

	private final BlockingQueue<ReferredDomain> queue;

	public InMemoryReferredDomainWriter(BlockingQueue<ReferredDomain> queue) {
		this.queue = queue;
	}

	@Override
	public Either<String, ReferredDomain> write(ReferredDomain rd) {
		try {
			queue.put(rd);
		} catch (InterruptedException e) {
			return Either.left("Unable to write referral " + e.getMessage());
		}
		return Either.right(rd);
	}

}