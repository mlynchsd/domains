package com.mlynch.domains;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableMap;

/**
 * Controller for tracking referrals
 */
@RestController
public class ReferralsController {

	private static Logger logger = LoggerFactory.getLogger(ReferralsController.class);

	private final ReferralsService service;

	@Autowired
	public ReferralsController(ReferralsService service) {
		this.service = service;
	}

	@PostMapping(path = "/referrals",
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> postReferral(@RequestParam String url, HttpServletRequest request) {
		String clientId = clientId(request);
		logger.info("clientId = " + clientId + ", url = " + url);
		// TODO error handling
		return service.record(clientId, url).either(
			e -> new ResponseEntity<>(toErrorRep(e), HttpStatus.BAD_REQUEST),
			d -> new ResponseEntity<>(toDomainRep(d), HttpStatus.CREATED)
		);
	}

	@GetMapping(path = "/referrals",
		produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getReferralReferrals(@RequestParam(required = false) int
		top, HttpServletRequest request) {
		String clientId = clientId(request);
		logger.info("clientId = " + clientId + ", top = " + top);
		// TODO error handling
		return service.fetchTop(top).either(
			e -> new ResponseEntity<>(toErrorRep(e), HttpStatus.BAD_REQUEST),
			counts -> new ResponseEntity<>(toCountsRep(counts), HttpStatus.OK)
		);
	}

	// TODO separate type? make more general
	String clientId(HttpServletRequest request) {
		String clientId  = request.getHeader("X-FORWARDED-FOR");
		if(clientId == null) {
			clientId = request.getRemoteAddr();
		}
		return clientId;
	}

	Map<String, Object> toDomainRep(ReferredDomain rd) {
		return ImmutableMap.of(
			"clientId", rd.clientId,
			"domainName", rd.domainName.value,
			"timestamp", rd.timestamp.getEpochSecond());
	}

	List<ReferralCountRep> toCountsRep(List<ReferralCount> counts) {
		return counts.stream()
			.map(rc -> new ReferralCountRep(rc.domainName.value, rc.count))
			.collect(Collectors.toList());
	}

	Map<String, Object> toErrorRep(String errorMsg) {
		return ImmutableMap.of(
			"errorMsg", errorMsg);
	}

}
