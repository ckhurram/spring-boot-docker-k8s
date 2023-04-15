package org.landg.lgrs.imagechannel.service;

import org.landg.lgrs.imagechannel.client.GpeFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageChannelService {

    private final GpeFeignClient gpeFeignClient;

    @Autowired
    public ImageChannelService(GpeFeignClient gpeFeignClient)
    {
        this.gpeFeignClient = gpeFeignClient;
    }



}



