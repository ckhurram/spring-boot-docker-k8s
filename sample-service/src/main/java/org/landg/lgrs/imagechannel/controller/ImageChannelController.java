package org.landg.lgrs.imagechannel.controller;

import com.volvo.gox.pop.price.creation.dto.*;
import org.landg.lgrs.imagechannel.dto.ImageChannelResponseDTO;
import org.landg.lgrs.imagechannel.service.ImageChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(value = "Image Channel Adapter")
@Slf4j
public class ImageChannelController {

    private static final String SUCCESS = "SUCCESS";
    private final ImageChannelService imageChannelService;

    @Autowired
    public ImageChannelController(ImageChannelService imageChannelService) {
        this.imageChannelService = imageChannelService;
    }

    @PostMapping(value = "/trigger", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Triggers Case Creation", response = ImageChannelResponseDTO.class, responseContainer = "List")
    public ResponseEntity<ImageChannelResponseDTO> trigger(
            @ApiParam(value = "Request Body containing list of PNO12s, market, modelYear, duration, mileage", required = true)
            @RequestBody List<Object> priceTriggerRequestDTOList
    ) {

        this.imageChannelService.trigger(priceTriggerRequestDTOList);


        return ResponseEntity
                .status(HttpStatus.OK)
                .body(null);
    }

}
