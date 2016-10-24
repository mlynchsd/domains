package com.mlynch.domains;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import fj.data.Either;

@RunWith(MockitoJUnitRunner.class)
public class SimpleReferralsServiceTest {

	private SimpleReferralsService cut;

	@Mock
	private ReferredDomainWriter mockRdWriter;

	@Mock
	private ReferralCountsService mockRefCounts;

	@Before
	public void setup() {
		cut = new SimpleReferralsService(mockRdWriter, mockRefCounts);
	}

	@Test
	public void recordOk() throws Exception {
		// given
		ReferredDomain rd =
			ReferredDomain.referral("127.0.0.1", new URL("http://www.google.com")).right().value();
		given(mockRdWriter.write(any(ReferredDomain.class))).willReturn(Either.right(rd));

		// when
		Either<String, ReferredDomain> recorded = cut.record("127.0.0.1", "http://www.google.com");

		// then
		assertThat(recorded.isRight()).isTrue();
		assertThat(recorded.right().value().clientId).isEqualTo("127.0.0.1");
		assertThat(recorded.right().value().domainName.value).isEqualTo("google.com");
	}

}

