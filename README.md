# Rabbit MQ Implementation

The RabbitMQ Application is a [Spring Boot](https://spring.io/projects/spring-boot) based messaging application that uses [RabbitMQ](https://www.rabbitmq.com/) as a message broker for communication. It demonstrates basic message producer and consumer functionality.

## Prerequisites

Before you begin, ensure you have RabbitMQ server installed and running. You can install RabbitMQ using [Homebrew](https://brew.sh/) (macOS/Linux) or follow the official [RabbitMQ installation guide](https://www.rabbitmq.com/download.html).

### RabbitMQ Setup via Homebrew

1. Install RabbitMQ
```
brew install rabbitmq
```

2. Start the RabbitMQ Server
```
brew services start rabbitmq
```

3. Verify the Installation
```
brew services list
```

RabbitMQ comes with a web-based management console that you can access to monitor and manage your RabbitMQ server. To access the management console, open a web browser and go to:

http://localhost:15672/

You can log in using the default credentials:

Username: `guest`
Password: `guest`

**Notes:**
To have the RabbitMQ server operational, we must first terminate the ActiveMQ service (if any) since they both share port 1883.


