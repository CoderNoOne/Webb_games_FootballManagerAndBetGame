/*country_base*/
CREATE TABLE `country_base`
(
    `name` varchar(255) NOT NULL,
    PRIMARY KEY (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

/*league_base*/

CREATE TABLE `league_base`
(
    `name`         varchar(255) NOT NULL,
    `league_type`  varchar(255) DEFAULT NULL,
    `country_name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`name`),
    KEY `FKpn58m5rsjdgkv4ro6v3d4ogkq` (`country_name`),
    CONSTRAINT `FKpn58m5rsjdgkv4ro6v3d4ogkq` FOREIGN KEY (`country_name`) REFERENCES `country_base` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

/*team_base*/

CREATE TABLE `team_base`
(
    `name`           varchar(255) NOT NULL,
    `country_name`   varchar(255) DEFAULT NULL,
    `league_name`    varchar(255) DEFAULT NULL,
    `background_url` varchar(255) DEFAULT NULL,
    `logo_url`       varchar(255) DEFAULT NULL,
    `shirt_colors`   varchar(255) DEFAULT NULL,
    PRIMARY KEY (`name`),
    KEY `FK7uqokku5435st6vec3hwbg3r` (`country_name`),
    KEY `FKj93byf9bpabmeamxd85ijprty` (`league_name`),
    CONSTRAINT `FK7uqokku5435st6vec3hwbg3r` FOREIGN KEY (`country_name`) REFERENCES `country_base` (`name`),
    CONSTRAINT `FKj93byf9bpabmeamxd85ijprty` FOREIGN KEY (`league_name`) REFERENCES `league_base` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;


/*player_base*/

CREATE TABLE `players_base`
(
    `id`         int(11) NOT NULL AUTO_INCREMENT,
    first_name   varchar(30) DEFAULT NULL,
    last_name    varchar(50) DEFAULT NULL,
    country_name varchar(30) DEFAULT NULL,
    team_name    varchar(30) DEFAULT NULL,
    image_url  varchar(50) DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `country_foreign_key` (`country_name`),
    KEY `team_foreign_key` (`team_name`),
    CONSTRAINT `country_foreign_key` FOREIGN KEY (`country_name`) REFERENCES `country_base` (`name`),
    CONSTRAINT `team_foreign_key` FOREIGN KEY (`team_name`) REFERENCES `team_base` (`name`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

/*positions_base*/

CREATE TABLE `positions_base`
(
    `player_id` int(11) NOT NULL,
    `position`  varchar(255) DEFAULT NULL,
    KEY `FKog9k5jfa074aqmkekvoseus6y` (`player_id`),
    CONSTRAINT `FKog9k5jfa074aqmkekvoseus6y` FOREIGN KEY (`player_id`) REFERENCES `players_base` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

/*player_attributes_base*/


CREATE TABLE `players_attributes_base`
(
    `attacking`       int(11) DEFAULT NULL,
    `defending`       int(11) DEFAULT NULL,
    `heading`         int(11) DEFAULT NULL,

    `dribbling`       int(11) DEFAULT NULL,
    `passing`         int(11) DEFAULT NULL,
    `technique`       int(11) DEFAULT NULL,
    `team_work`       int(11) DEFAULT NULL,
    `player_id`       int(11) NOT NULL,

    `speed`           int(11) DEFAULT NULL,
    `aggression`      int(11) DEFAULT NULL,

    `one_on_ones`     int(11) DEFAULT NULL,
    `penalty_saving`  int(11) DEFAULT NULL,
    `penalty_scoring` int(11) DEFAULT NULL,
    `reflexes`        int(11) DEFAULT NULL,
    PRIMARY KEY (`player_id`),
    CONSTRAINT `FKkbvs3tnkyl5aqlwbwdp8path9` FOREIGN KEY (`player_id`) REFERENCES `players_base` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
