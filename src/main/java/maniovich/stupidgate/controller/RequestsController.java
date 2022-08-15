package maniovich.stupidgate.controller;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RequestsController {
    private final RestTemplate restTemplate;

    public String url;
    public RequestsController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void SetUrl(String url)
    {
        this.url = url;
    }

    public ResponseEntity<String> MakeRequest(String id )
    {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(this.url)
                .queryParam("id", id);
        ResponseEntity<String> responseEntity = restTemplate
                .exchange(builder.toUriString(), HttpMethod.GET, null, String.class);
        return responseEntity;
    }

}
