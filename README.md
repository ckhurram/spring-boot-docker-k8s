# spring-boot-docker-k8s

Setting up this project with aim of Deploying a simple Microservice in Kubernetes as a Docker image.  

This tutorial will be done on macOS and will be using IntelliJ IDEA to speed up few things.

## Tools Used

* Spring Boot - to build a microservice with a VERSION endpoint
* IntelliJ IDEA - as an Editor or mock up some code
* Gradle - as a build tool, same can be achieved using maven - brew install gradle
* Docker - to build docker images - brew install docker
* minikube - local Kubernetes environment - brew cask install minikube
* kubectrl - command line interface for Kubernetes - brew install kubernetes-cli

## Build Spring Boot Microservice

This spec of the service is:

    Endpoint:   \VERSION
    Method:     GET
    Response:   JSON  

### Create Service boilerplate using IntelliJ IDEA

We can easily create the service shell using IntelliJ IDEA's Spring Initializer:

Go to 
    
    File -> New -> Project -> Spring Initializer
    

    
    Group: 	io.pioneerslab.example
    Artifiact: 	spring-boot-docker-k8s
    Type: 	Gradle Project
    
Click **Next**
    
From **Dependencies** dialogue, for now, we only interested in a simple REST Endpoint, so just choose **Web**, click **Next**.
    
    Project name: spring-boot-docker-k8s
    
Click **Finish**
    
Fill in the remaining dialogues as required, I chose **gradle** as a build tool but equally **maven** can be used.

On finishing the dialogue boxes of Spring Initializer, we should have a Spring-Boot project.

Let's build the project:

    gradle build
    
We should see:

    BUILD SUCCESSFUL
    
    Total time: 19.477 secs
    
Now let's add a REST endpoint to our SpringBoot Application, we can make main class **SpringBootDockerK8sApplication** a REST Controller by adding **@RestController** annotation.  After this add a REST endpoint with some implementation as below:

```java
        @RequestMapping(value="/VERSION", method= RequestMethod.GET)
	public String version() throws UnknownHostException {
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		String hostName = InetAddress.getLocalHost().getHostName();
		return "VERSION from hostService: ".concat(hostName).concat("@ IP Address: ").concat(hostAddress);
	}
```

Now run the app, either from IntelliJ or command prompt, and call the endpoint by using curl or anyother REST client

    curl http://localhost:8080/VERSION
    
    VERSION from hostService: Khurrams-MacBook-Pro.local@ IP Address: 192.168.0.24
    
