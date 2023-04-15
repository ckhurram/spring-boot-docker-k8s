package org.landg.lgrs.imagechannel.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfiguration {



    @Bean(name = "encryptorBean")
    public StringEncryptor stringEncryptor() {
        String KEY = "gox-pop-rv";
        String ALGORITHM = "PBEWithMD5AndDES";
        String KEY_OBTENTION_ITERATIONS = "1000";
        String POOLSIZE = "1";
        String PROVIDER_NAME = "SunJCE";
        String SALT_GENERATOR_CLASS_NAME = "org.jasypt.salt.RandomSaltGenerator";
        String STRING_OUTPUT_TYPE = "base64";

        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(KEY);
        config.setAlgorithm(ALGORITHM);
        config.setKeyObtentionIterations(KEY_OBTENTION_ITERATIONS);
        config.setPoolSize(POOLSIZE);
        config.setProviderName(PROVIDER_NAME);
        config.setSaltGeneratorClassName(SALT_GENERATOR_CLASS_NAME);
        config.setStringOutputType(STRING_OUTPUT_TYPE);
        encryptor.setConfig(config);

        return encryptor;
    }
}
