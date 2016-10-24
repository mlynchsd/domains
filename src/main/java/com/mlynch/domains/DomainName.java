package com.mlynch.domains;

import java.net.URL;

import com.google.common.net.InternetDomainName;

import fj.data.Either;

/**
 * Data type to represent valid domain name
 */
public class DomainName {

	public final String value;

	private DomainName(String value) {
		this.value = value;
	}

	public static Either<String, DomainName> domainName(URL url) {
		if (url == null) {
			return Either.left("url must be provided");
		}
		try {
			return Either.right(
				new DomainName(InternetDomainName.from(url.getHost()).topPrivateDomain().name()));
		} catch (IllegalArgumentException e) {
			return Either.left("Invalid domain name");
		}
	}

}
