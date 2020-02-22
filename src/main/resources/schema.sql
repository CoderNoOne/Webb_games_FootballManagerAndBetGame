-- # users
-- CREATE TABLE `users`
-- (mie
--     `username`      varchar(255) NOT NULL,
--     `birth_date`    date         DEFAULT NULL,
--     `email`         varchar(255) DEFAULT NULL,
--     `enabled`       tinyint(4)   DEFAULT NULL,
--     `favorite_club` varchar(255) DEFAULT NULL,
--     `first_name`    varchar(255) DEFAULT NULL,
--     `gender`        varchar(255) DEFAULT NULL,
--     `last_name`     varchar(255) DEFAULT NULL,
--     `password`      varchar(255) DEFAULT NULL,
--     `photo_url`     varchar(255) DEFAULT NULL,
--     PRIMARY KEY (`username`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*authorities*/
-- CREATE TABLE IF NOT EXISTS authorities
-- (
--     username  varchar(45) NOT NULL,
--     authority varchar(45) NOT NULL,
--     PRIMARY KEY (username),
--     UNIQUE KEY uni_username_role (authority, username),
--     KEY fk_username_idx (username),
--     CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*verification_tokens*/
--
-- CREATE TABLE `verification_tokens`
-- (
--     `id`                   int(11) NOT NULL AUTO_INCREMENT,
--     `expiration_date_time` datetime     DEFAULT NULL,
--     `token`                varchar(255) DEFAULT NULL,
--     `user_username`        varchar(255) DEFAULT NULL,
--     PRIMARY KEY (`id`),
--     UNIQUE KEY `UK_fivtq1xbf4m2nr6vsouyabtk3` (`user_username`),
--     CONSTRAINT `FKbcfi34c00xf3qqkcmjnbvctla` FOREIGN KEY (`user_username`) REFERENCES `users` (`username`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*game*/
-- CREATE TABLE `game`
-- (
--     `id`         int(11) NOT NULL AUTO_INCREMENT,
--     `start_date` date    DEFAULT NULL,
--     `active`     boolean DEFAULT NULL,
--     PRIMARY KEY (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*users_games*/
--
-- CREATE TABLE `users_games`
-- (
--     `user_username` varchar(255) NOT NULL,
--     `game_id`       int(11)      NOT NULL,
--     PRIMARY KEY (`user_username`, `game_id`),
--     KEY `FKo6k8awat8x90tksian1x3cjsl` (`game_id`),
--     CONSTRAINT `FKo6k8awat8x90tksian1x3cjsl` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`),
--     CONSTRAINT `FKql7ef2xnx3xx1orw0smfv7nmu` FOREIGN KEY (`user_username`) REFERENCES `users` (`username`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
--
-- /*countries*/
--
-- CREATE TABLE `countries`
-- (
--     `name` varchar(255) NOT NULL,
--     PRIMARY KEY (`name`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*teams*/
--
-- CREATE TABLE `teams`
-- (
--     `id`            int(11) NOT NULL AUTO_INCREMENT,
--     `name`          varchar(255) DEFAULT NULL,
--     `country_id`    varchar(255) DEFAULT NULL,
--     `user_username` varchar(255) DEFAULT NULL,
--     PRIMARY KEY (`id`),
--     KEY `FK7oxg99kw26u7w7swuu6tivxmr` (`country_id`),
--     KEY `FKq1acnbca77xiypjb234ftre1e` (`user_username`),
--     CONSTRAINT `FK7oxg99kw26u7w7swuu6tivxmr` FOREIGN KEY (`country_id`) REFERENCES `countries` (`name`),
--     CONSTRAINT `FKq1acnbca77xiypjb234ftre1e` FOREIGN KEY (`user_username`) REFERENCES `users` (`username`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
--
-- /*leagues*/
-- --
-- CREATE TABLE `league`
-- (
--     `id`          int(11) NOT NULL AUTO_INCREMENT,
--     `league_type` varchar(255) DEFAULT NULL,
--     `name`        varchar(255) DEFAULT NULL,
--     `start_date`  date         DEFAULT NULL,
--     `country_id`  varchar(255) DEFAULT NULL,
--     `game_id`     int(11)      DEFAULT NULL,
--     PRIMARY KEY (`id`),
--     KEY `FKdbmbo6v4y65r2f3ykvj4stxpd` (`country_id`),
--     KEY `FKibkn39894xhmex4li24c1g5rt` (`game_id`),
--     CONSTRAINT `FKdbmbo6v4y65r2f3ykvj4stxpd` FOREIGN KEY (`country_id`) REFERENCES `countries` (`name`),
--     CONSTRAINT `FKibkn39894xhmex4li24c1g5rt` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- CREATE TABLE `matches`
-- (
--     `id`          int(11) NOT NULL AUTO_INCREMENT,
--     `date`        date         DEFAULT NULL,
--     `scoreEntity` varchar(255) DEFAULT NULL,
--     `away_id`     int(11)      DEFAULT NULL,
--     `home_id`     int(11)      DEFAULT NULL,
--     `league_id`   int(11)      DEFAULT NULL,
--     PRIMARY KEY (`id`),
--     KEY `FKm513w5sj43xwgdxm94s4i7t2v` (`away_id`),
--     KEY `FK455u7ietc8xi4f0kt14bn5n7w` (`home_id`),
--     KEY `FKnijr59n5ru1t4pampfynl2rpd` (`league_id`),
--     CONSTRAINT `FK455u7ietc8xi4f0kt14bn5n7w` FOREIGN KEY (`home_id`) REFERENCES `teams` (`id`),
--     CONSTRAINT `FKm513w5sj43xwgdxm94s4i7t2v` FOREIGN KEY (`away_id`) REFERENCES `teams` (`id`),
--     CONSTRAINT `FKnijr59n5ru1t4pampfynl2rpd` FOREIGN KEY (`league_id`) REFERENCES `league` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*health card*/
-- CREATE TABLE IF NOT EXISTS `health_cards`
-- (
--     `id`          int(11) NOT NULL AUTO_INCREMENT,
--     `description` varchar(255) DEFAULT NULL,
--     PRIMARY KEY (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*injuries*/
--
-- CREATE TABLE IF NOT EXISTS `injuries`
-- (
--     `id`             int(11) NOT NULL AUTO_INCREMENT,
--     `date_from`      date         DEFAULT NULL,
--     `date_to`        date         DEFAULT NULL,
--     `type`           varchar(255) DEFAULT NULL,
--     `health_card_id` int(11)      DEFAULT NULL,
--     PRIMARY KEY (`id`),
--     KEY `FKamv0yi786p32e1pgkdfubppji` (`health_card_id`),
--     CONSTRAINT `FKamv0yi786p32e1pgkdfubppji` FOREIGN KEY (`health_card_id`) REFERENCES `health_cards` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*player_stats*/
--
-- CREATE TABLE IF NOT EXISTS `player_stats`
-- (
--     `id`                      int(11) NOT NULL AUTO_INCREMENT,
--     `assists`                 int(11) DEFAULT NULL,
--     `goals`                   int(11) DEFAULT NULL,
--     `man_of_the_match_prizes` int(11) DEFAULT NULL,
--     PRIMARY KEY (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*players*/
--
-- CREATE TABLE `player`
-- (
--     `id`              int(11) NOT NULL AUTO_INCREMENT,
--     `first_name`      varchar(255)   DEFAULT NULL,
--     `last_name`       varchar(255)   DEFAULT NULL,
--     `market_value`    decimal(19, 2) DEFAULT NULL,
--     `height`          VARCHAR(255)   DEFAULT NULL,
--     `strong_foot`     varchar(10)    DEFAULT NULL,
--     `weight`          varchar(255)   DEFAULT NULL,
--     `pseudonym`       varchar(255)   DEFAULT NULL,
--     `country_id`      varchar(255)   DEFAULT NULL,
--     `health_card_id`  int(11)        DEFAULT NULL,
--     `player_stats_id` int(11)        DEFAULT NULL,
--     `team_id`         int(11)        DEFAULT NULL,
--     PRIMARY KEY (`id`),
--     KEY `FKefilseu4nhmdb5onaqiyjvd6b` (`country_id`),
--     KEY `FKykr4ond9m9t9lpkaanjcf3hi` (`health_card_id`),
--     KEY `FKhbx7w2236doh61gpqvmrikh5p` (`player_stats_id`),
--     KEY `FKp1r2b5dstxae7hp574tgryedb` (`team_id`),
--     CONSTRAINT `FKefilseu4nhmdb5onaqiyjvd6b` FOREIGN KEY (`country_id`) REFERENCES `countries` (`name`),
--     CONSTRAINT `FKhbx7w2236doh61gpqvmrikh5p` FOREIGN KEY (`player_stats_id`) REFERENCES `player_stats` (`id`),
--     CONSTRAINT `FKp1r2b5dstxae7hp574tgryedb` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`),
--     CONSTRAINT `FKykr4ond9m9t9lpkaanjcf3hi` FOREIGN KEY (`health_card_id`) REFERENCES `health_cards` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- CREATE TABLE `transfers`
-- (
--     `id`           int(11) NOT NULL AUTO_INCREMENT,
--     `date`         date           DEFAULT NULL,
--     `price`        decimal(19, 2) DEFAULT NULL,
--     `player_id`    int(11)        DEFAULT NULL,
--     `team_from_id` int(11)        DEFAULT NULL,
--     `team_to_id`   int(11)        DEFAULT NULL,
--     PRIMARY KEY (`id`),
--     KEY `FK5p2pew7abxaac3n39s114768p` (`player_id`),
--     KEY `FKpror3c5dbth3kcsebn6v1mlim` (`team_from_id`),
--     KEY `FK9xqpcwrpu0r7lrspu35y7q856` (`team_to_id`),
--     CONSTRAINT `FK5p2pew7abxaac3n39s114768p` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
--     CONSTRAINT `FK9xqpcwrpu0r7lrspu35y7q856` FOREIGN KEY (`team_to_id`) REFERENCES `teams` (`id`),
--     CONSTRAINT `FKpror3c5dbth3kcsebn6v1mlim` FOREIGN KEY (`team_from_id`) REFERENCES `teams` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
--
-- /*player_stats_per_match*/
--
--
-- CREATE TABLE `player_stats_per_match`
-- (
--     `id`        int(11) NOT NULL AUTO_INCREMENT,
--     `assists`   int(11) DEFAULT NULL,
--     `goals`     int(11) DEFAULT NULL,
--     `min_from`  int(11) DEFAULT NULL,
--     `min_to`    int(11) DEFAULT NULL,
--     `rating`    int(11) DEFAULT NULL,
--     `match_id`  int(11) DEFAULT NULL,
--     `player_id` int(11) DEFAULT NULL,
--     `team_id`   int(11) DEFAULT NULL,
--     PRIMARY KEY (`id`),
--     KEY `FKo7h41bgeben51nibfmi6m0n8y` (`match_id`),
--     KEY `FK8rekn7yan9k61bhev7w4uvrbu` (`player_id`),
--     KEY `FKhtwr9gw0frnu6w9007ykq53qy` (`team_id`),
--     CONSTRAINT `FK8rekn7yan9k61bhev7w4uvrbu` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`),
--     CONSTRAINT `FKhtwr9gw0frnu6w9007ykq53qy` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`),
--     CONSTRAINT `FKo7h41bgeben51nibfmi6m0n8y` FOREIGN KEY (`match_id`) REFERENCES `matches` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
--
-- /*league_teams*/
--
-- CREATE TABLE `teams_leagues`
-- (
--     `team_id`   int(11) NOT NULL,
--     `league_id` int(11) NOT NULL,
--     PRIMARY KEY (`team_id`, `league_id`),
--     KEY `FK7si6x2iebe1k89mhp45p7qex3` (`league_id`),
--     CONSTRAINT `FK7si6x2iebe1k89mhp45p7qex3` FOREIGN KEY (`league_id`) REFERENCES `league` (`id`),
--     CONSTRAINT `FKngtnfj47xx3m8pma7xj4y99qc` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
--
-- /*cards*/
--
-- CREATE TABLE IF NOT EXISTS `cards`
-- (
--     `id`         bigint(20) NOT NULL AUTO_INCREMENT,
--     `card_color` varchar(255) DEFAULT NULL,
--     `date`       date         DEFAULT NULL,
--     PRIMARY KEY (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*players_cards*/
--
-- CREATE TABLE IF NOT EXISTS `players_cards`
-- (
--     `player_id` int(11)    NOT NULL,
--     `card_id`   bigint(20) NOT NULL,
--     KEY `FKrfhcmsl9l407jgepjyppjhra2` (`card_id`),
--     KEY `FK94evt4j7o5a6vydm09ex4404y` (`player_id`),
--     CONSTRAINT `FK94evt4j7o5a6vydm09ex4404y` FOREIGN KEY (`player_id`) REFERENCES `player_stats` (`id`),
--     CONSTRAINT `FKrfhcmsl9l407jgepjyppjhra2` FOREIGN KEY (`card_id`) REFERENCES `cards` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*player_attributes*/
-- CREATE TABLE `player_attributes`
-- (
--     `aggression`      int            DEFAULT NULL,
--     `bravery`         decimal(19, 2) DEFAULT NULL,
--     `dribbling`       decimal(19, 2) DEFAULT NULL,
--     `first_touch`     decimal(19, 2) DEFAULT NULL,
--     `jumping`         decimal(19, 2) DEFAULT NULL,
--     `pace`            decimal(19, 2) DEFAULT NULL,
--     `passing`         decimal(19, 2) DEFAULT NULL,
--     `stamina`         decimal(19, 2) DEFAULT NULL,
--     `strength`        decimal(19, 2) DEFAULT NULL,
--     `team_work`       decimal(19, 2) DEFAULT NULL,
--     `technique`       decimal(19, 2) DEFAULT NULL,
--     `vision`          decimal(19, 2) DEFAULT NULL,
--     `player_id`       int(11) NOT NULL,
--     `one_on_ones`     decimal(19, 2) DEFAULT NULL,
--     `penalty_saving`  decimal(19, 2) DEFAULT NULL,
--     `penalty_scoring` decimal(19, 2) DEFAULT NULL,
--     `reflexes`        decimal(19, 2) DEFAULT NULL,
--     PRIMARY KEY (`player_id`),
--     CONSTRAINT `FKp11qkbb27byoeu8jswk8i8bqn` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*positions*/
--
-- CREATE TABLE IF NOT EXISTS `positions`
-- (
--     `player_id` int(11) NOT NULL,
--     `position`  varchar(255) DEFAULT NULL,
--     KEY `FKf2wbko08kuciqtjgsofbmyrd7` (`player_id`),
--     CONSTRAINT `FKf2wbko08kuciqtjgsofbmyrd7` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;
--
-- /*users_teams*/
--
-- CREATE TABLE IF NOT EXISTS `users_teams`
-- (
--     `user_username` varchar(255) NOT NULL,
--     `team_id`       int(11)      NOT NULL,
--     KEY `FKno0mrvrh90a0lao3tib4p5ema` (`team_id`),
--     KEY `FKbsckfmibvsr9mjjf10yjrnrhp` (`user_username`),
--     CONSTRAINT `FKbsckfmibvsr9mjjf10yjrnrhp` FOREIGN KEY (`user_username`) REFERENCES `users` (`username`),
--     CONSTRAINT `FKno0mrvrh90a0lao3tib4p5ema` FOREIGN KEY (`team_id`) REFERENCES `teams` (`id`)
-- ) ENGINE = InnoDB
--   DEFAULT CHARSET = latin1;


/*-----------------------------base for admin------------------------------------------------------------*/

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
