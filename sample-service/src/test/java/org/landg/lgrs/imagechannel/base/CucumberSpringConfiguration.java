package org.landg.lgrs.imagechannel.base;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, args = "-Djasypt.encryptor.password=gox-pop-rv")
@ActiveProfiles("dev")
public class CucumberSpringConfiguration {
}
