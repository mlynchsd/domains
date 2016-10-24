package com.mlynch.domains;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import fj.data.Either;

@RunWith(MockitoJUnitRunner.class)
public class InMemoryReferredDomainRepositoryTest {

	private ReferredDomainRepository cut;

	@Before
	public void setup() {
		cut = new InMemoryReferredDomainRepository();
	}

	@Test
	public void saveOk() throws Exception {
		// given
		ReferredDomain rd =
			ReferredDomain.referral("127.0.0.1", new URL("http://www.google.com")).right().value();

		// when
		Either<String, ReferredDomain> saved = cut.save(rd);

		// then
		assertThat(saved.isRight()).isTrue();
		assertThat(saved.right().value().clientId).isEqualTo("127.0.0.1");
		assertThat(saved.right().value().domainName.value).isEqualTo("google.com");
	}

}

