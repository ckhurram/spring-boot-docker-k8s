package io.pioneerslab.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class SpringBootDockerK8sApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDockerK8sApplication.class, args);
	}

	@RequestMapping(value="/VERSION", method= RequestMethod.GET)
	public String version() throws UnknownHostException {
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		String hostName = InetAddress.getLocalHost().getHostName();
		return "VERSION from hostService: ".concat(hostName).concat("@ IP Address: ").concat(hostAddress);
	}
}
