# Football manager and Bet web games
---
### 1. About

The application consists of two games.

The first one (Bet game) provides the activity on predicting the results of football matches in three leagues: Italian (Serie A), Spanish (La Liga) and English (Premier League). Match results arle continuously updated every 1 minute. Based on match results, the user leaderboard is driven. 

In the another game (Football Manager)  you, the player, manage a football team (one of Inter Milan, PSG, FC Liverpool, FC Barcelona, Real Madrid, Juventus Turin) through playable custom leagues created by admin.

### 2. Data model
![Alt text](https://i.imgur.com/WgXGgsj.png "EER DIAGRAM")
*Fig. 1: Entity data model*



![Alt text](https://i.imgur.com/4ECUFpR.png "EER DIAGRAM")
*Fig. 2: Data model to store information about real teams, their squad players and their attributes with country they come from*
***

### 3. Project build with

* [Maven](https://maven.apache.org/) - Dependency Management
* [Spring boot]() - starting point for building Spring-based applications with minimized upfront configuration of Spring


### 4. Main dependencies, libraries, frameworks and technologies:

* [spring boot](https://spring.io/projects/spring-boot) - it's used to build stand-alone and production ready spring applications.
* [spring data jpa](https://spring.io/projects/spring-data-jpa) - part of the larger Spring Data family, deals with enhanced support for JPA based data access layers
* [spring security](https://spring.io/projects/spring-security) - standard for securing Spring-based applications.
* [spring boot mail starter](https://spring.io/projects/spring-security) - the interfaces and classes for Java mail support in the Spring framework
* [thymeleaf](https://www.thymeleaf.org/) - server-side Java template engine for processing and creating HTML, XML, JavaScript, CSS
* [bootstrap](https://getbootstrap.com/) -  a free and open-source CSS framework directed at responsive, front-end web development. It contains CSS- and (optionally) JavaScript-based design templates for typography, forms, buttons, navigation, and other interface components
* [javaScript](https://javascript.info/) - programming language of HTML and the Web, scripts run automatically as the page loads
* [html 5](https://developer.mozilla.org/en-US/docs/Web/HTML/Reference) - the latest evolution of the standard that defines HTML, software solution stack that defines the properties and behaviors of web page content by implementing a markup based pattern to it
* [css ](https://developer.mozilla.org/en-US/docs/Web/CSS/Reference) - stylesheet language used to describe the presentation of a document written in HTML
* [lombok](https://projectlombok.org/) - minimized boilerplate code, used also to generate a logger field

### 5. Screenshots

![Alt text](https://i.imgur.com/7W3Bk2o.png "user_page")
*Fig. 3: Main user page*

![Alt text](https://i.imgur.com/XJex6DV.png "bet_home")
*Fig. 4: Bet game menu*

![Alt text](https://i.imgur.com/GwSMD7A.png "user_page")
*Fig. 5: Italian league (Serie A) main page for betting real match result*

![Alt text](https://i.imgur.com/8npRzXB.png "user_page")
*Fig. 6: Football manager game - team management page*

![Alt text](https://i.imgur.com/poeEaiV.png "user_page")
*Fig. 7: Football manager game (Match centre - Your Formation subpage)*

![Alt text](https://i.imgur.com/0N3ZpU9.png "user_page")
*Fig. 8: Football manager game (Player statistics - goal subpage)*

![Alt text](https://i.imgur.com/490LusS.png "user_page")
*Fig. 9: Football manager game (Team leaderboard)*

![Alt text](https://i.imgur.com/orI5nJj.png "user_page")
*Fig. 10: Football manager game (Live match statistics subpage)*


![Alt text](https://i.imgur.com/2If8wJz.png "admin_page")
*Fig. 11: Main admin page*

### 6. Application Docker image on dockerHub

You can find the application docker image under the link: 

[click here](https://hub.docker.com/repository/docker/codernoone/web_app) 

To download the docker image open the terminal and enter the following command:

```
docker push codernoone/web_app:v1

```

### 7. AWS Elastic Beanstalk

Application is deployed on AWS (Amazon Web Services) Elastic Beanstalk as a multi-docker container environment

Dockerrun.aws.json file is as follows:

```docker
{
  "AWSEBDockerrunVersion": 2,
  "containerDefinitions": [
    {
      "name": "mysql",
      "image": "mysql:5.7",
      "environment": [
        {
          "name": "MYSQL_ROOT_PASSWORD",
          "value": "root"
        },
        {
          "name": "MYSQL_USER",
          "value": "user"
        },
        {
          "name": "MYSQL_PASSWORD",
          "value": "pass"
        },
        {
          "name": "MYSQL_DATABASE",
          "value": "db"
        }
      ],
      "essential": true,
      "memory": 256,
      "portMappings": [
        {
          "hostPort": 3306,
          "containerPort": 3306
        }
      ],
      "mountPoints": [
        {
          "sourceVolume": "mysql_db_data_volume",
          "containerPath": "/var/lib/mysql"
        }
      ]
    },
    {
      "name": "web-application",
      "image": "codernoone/web_app:v1",
      "essential": true,
      "memory": 512,
      "portMappings": [
        {
          "hostPort": 80,
          "containerPort": 8080
        }
      ],
      "links": [
        "mysql"
      ]
    }
  ],
  "volumes": [
    {
      "name": "mysql_db_data_volume",
      "host": {
        "sourcePath": "/var/app/current/web-app"
      }
    }
  ]
}
```

Check out this link: 
[click here](http://codernoone.eu-west-1.elasticbeanstalk.com/)


