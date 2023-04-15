package org.landg.lgrs.imagechannel.client;

import org.landg.lgrs.imagechannel.dto.GraphqlRequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "gpeFeignClient", url = "${client.graphql.baseUrl}")
public interface GpeFeignClient {
    @PostMapping
    ResponseEntity<String> queryGpeForPrice(@RequestBody GraphqlRequestBody requestDTO);
}
