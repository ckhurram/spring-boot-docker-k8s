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
    
    
    
### Install/Run Kubernetes(minikube) locally

For local development Kubernetes has provided a tool called minikube, which we installed earlier.  Ensure you have Docker Running and [DockerToolbox](https://www.docker.com/products/docker-toolbox), we only need VirtualBox, let's tart Kubernetes locally.

Open a terminal, and type:

    minikube start
    
It will download Minikube ISO, if you running it first time, and then will start Kubernetes cluster, we shall see following output:

    Starting local Kubernetes v1.6.4 cluster...
    Starting VM...
    Moving files into cluster...
    Setting up certs...
    Starting cluster components...
    Connecting to cluster...
    Setting up kubeconfig...
    Kubectl is now configured to use the cluster.
    
Now run the **docker-env** to view the built in docker env, on the same terminal window:

    minikube docker-env
    
which should result in the following output:

    export DOCKER_TLS_VERIFY="1"
    export DOCKER_HOST="tcp://192.168.99.100:2376"
    export DOCKER_CERT_PATH="/Users/kc/.minikube/certs"
    export DOCKER_API_VERSION="1.23"
    # Run this command to configure your shell:
    # eval $(minikube docker-env)

Run the last command to configure docker shell:
    
    eval $(minikube docker-env)
    
Now we can run docker-ps command to see the docker images running in Kubernetes:

    Khurrams-MacBook-Pro:spring-boot-docker-k8s kc$ docker ps
    CONTAINER ID        IMAGE                                      COMMAND                  CREATED              STATUS              PORTS               NAMES
    8b15bd5507ee        ca8759c215c9                               "/kube-dns --domai..."   26 seconds ago       Up 19 seconds                           k8s_kubedns_kube-dns-196007617-14ljp_kube-system_1d6caac4-4ac4-11e7-b8b6-080027238d59_0
    6e392f8e92de        71dfe833ce74                               "/dashboard --inse..."   About a minute ago   Up About a minute                       k8s_kubernetes-dashboard_kubernetes-dashboard-tzmx4_kube-system_1d569e2a-4ac4-11e7-b8b6-080027238d59_0
    f84075cf27ff        gcr.io/google_containers/pause-amd64:3.0   "/pause"                 4 minutes ago        Up 4 minutes                            k8s_POD_kube-dns-196007617-14ljp_kube-system_1d6caac4-4ac4-11e7-b8b6-080027238d59_0
    132a407a334a        gcr.io/google_containers/pause-amd64:3.0   "/pause"                 4 minutes ago        Up 4 minutes                            k8s_POD_kubernetes-dashboard-tzmx4_kube-system_1d569e2a-4ac4-11e7-b8b6-080027238d59_0
    12c359979f4a        85809f318123                               "/opt/kube-addons.sh"    4 minutes ago        Up 4 minutes                            k8s_kube-addon-manager_kube-addon-manager-minikube_kube-system_8538d869917f857f9d157e66b059d05b_0
    ff03e280389c        gcr.io/google_containers/pause-amd64:3.0   "/pause"                 6 minutes ago        Up 6 minutes                            k8s_POD_kube-addon-manager-minikube_kube-system_8538d869917f857f9d157e66b059d05b_0


### Build Docker Image for the Service

In development mode, it is really useful to use minikube's built in docker, this way you dont have to a local registry and push the images on it, building on the same docker daemon as minikube will speed up local deployments.  Need to ensure you the image is tagged with a version otherwsie it may try to pull the latest from DockerHub.

We can use docker plugin to build the image from gradle/maven but this tutorial is focused on deploying an image to local Kubernetes.

Create a new folder, let's call it dock8 and copy the built Spring boot app in that folder.  In the terminal window, at a root of our project execute following:

     mkdir dock8
     cp build/libs/spring-boot-docker-k8s-0.0.1-SNAPSHOT.jar dock8/
     cd dock8
     mv spring-boot-docker-k8s-0.0.1-SNAPSHOT.jar dock8-app.jar
     
Create a **Dockerfile** in this directory with following contenets:

    FROM java:8
    ADD dock8-app.jar app.jar
    ENTRYPOINT ["java","-jar","/app.jar"]
    
Now we can build the docker image by executing foloowing command:

    Khurrams-MacBook-Pro:dock8 kc$ docker build -t dock8app:v1 .
    Sending build context to Docker daemon 14.41 MB
    Step 1 : FROM java:8
    8: Pulling from library/java
    5040bd298390: Pull complete 
    fce5728aad85: Pull complete 
    76610ec20bf5: Pull complete 
    60170fec2151: Pull complete 
    e98f73de8f0d: Pull complete 
    11f7af24ed9c: Pull complete 
    49e2d6393f32: Pull complete 
    bb9cdec9c7f3: Pull complete 
    Digest: sha256:c1ff613e8ba25833d2e1940da0940c3824f03f802c449f3d1815a66b7f8c0e9d
    Status: Downloaded newer image for java:8
     ---> d23bdf5b1b1b
    Step 2 : ADD dock8-app.jar app.jar
     ---> f7f9b44159f8
    Removing intermediate container 0573ead367e3
    Step 3 : ENTRYPOINT java -jar /app.jar
     ---> Running in 341e9e4244c5
     ---> 4ce49b50f3ec
    Removing intermediate container 341e9e4244c5
    Successfully built 4ce49b50f3ec


Now let's verify the image is there **docker images**

    Khurrams-MacBook-Pro:dock8 kc$ docker images
    REPOSITORY                                             TAG                 IMAGE ID            CREATED              SIZE
    dock8app                                               v1                  4ce49b50f3ec        About a minute ago   658 MB
    gcr.io/google_containers/kubernetes-dashboard-amd64    v1.6.1              71dfe833ce74        3 weeks ago          134 MB
    gcr.io/google_containers/k8s-dns-sidecar-amd64         1.14.2              7c4034e4ffa4        3 weeks ago          44.5 MB
    gcr.io/google_containers/k8s-dns-kube-dns-amd64        1.14.2              ca8759c215c9        3 weeks ago          52.4 MB
    gcr.io/google_containers/k8s-dns-dnsmasq-nanny-amd64   1.14.2              e5c335701995        3 weeks ago          44.8 MB
    gcr.io/google-containers/kube-addon-manager            v6.4-beta.1         85809f318123        2 months ago         127 MB
    java                                                   8                   d23bdf5b1b1b        4 months ago         643 MB
    gcr.io/google_containers/pause-amd64                   3.0                 99e59f495ffa        13 months ago        747 kB
    
 
### Start The Docker Image in Kubernetes

Execute following:

    kubectl run dock8app --image=dock8app:v1 --port=8080
    
 we should see:
 
    Khurrams-MacBook-Pro:dock8 kc$ kubectl run dock8app --image=dock8app:v1 --port=8080
    deployment "dock8app" created
    
Now lets start the **minikube dashboard**

    Khurrams-MacBook-Pro:dock8 kc$ minikube dashboard
    Opening kubernetes dashboard in default browser...

Some useful commands to get info about our Kubernetes

    Khurrams-MacBook-Pro:dock8 kc$ kubectl get pods
    NAME                        READY     STATUS    RESTARTS   AGE
    dock8app-1925058306-889g5   1/1       Running   0          15m
    
Also

    Khurrams-MacBook-Pro:dock8 kc$ kubectl get services
    NAME         CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
    kubernetes   10.0.0.1     <none>        443/TCP   4h
    
Now we can expose this service for external use, execute following command:

    kubectl expose deployment dock8app --type=NodePort
    
Result should be:    
    
    service "dock8app" exposed
    
Running **kubectl get services**

    kubectl get services

    NAME         CLUSTER-IP   EXTERNAL-IP   PORT(S)          AGE    dock8app     10.0.0.163   <nodes>       8080:30385/TCP   1m
    kubernetes   10.0.0.1     <none>        443/TCP          4h

**dock8app** is avaialble as a service

Now we can test the **/VERSION** endpoint on minikube, type
    
    minikube service dock8app
  
    
    