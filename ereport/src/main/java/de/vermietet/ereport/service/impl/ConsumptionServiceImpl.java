package de.vermietet.ereport.service.impl;

import de.vermietet.ereport.domain.Consumption;
import de.vermietet.ereport.service.ConsumptionService;
import de.vermietet.ereport.web.rest.errors.RestTemplateResponseErrorHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@Transactional
public class ConsumptionServiceImpl implements ConsumptionService {

    private final Logger log = LoggerFactory.getLogger(ConsumptionServiceImpl.class);
    @Value("${gateway.api}")
    private String gateWayURL;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Consumption> getConsumptions(String duration) {
        RestTemplate restTemplate = restTemplateBuilder
            .errorHandler(new RestTemplateResponseErrorHandler())
            .build();
        
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromHttpUrl(gateWayURL + "/ecounter/api/consumption_report")
            .queryParam("duration", duration);

        ResponseEntity<List<Consumption>> taskResponse =
            restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Consumption>>() {
            });

        if (HttpStatus.OK == taskResponse.getStatusCode()) {
            List<Consumption> consumptions = taskResponse.getBody();
            return consumptions;
        }

        return null;
    }
}
