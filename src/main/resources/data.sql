
-- /*country_base*/

INSERT INTO `db`.`country_base`(`name`)
VALUES ('INTERNATIONAL');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Spain');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Germany');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('England');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Italy');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Slovenia');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Argentina');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Brazil');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Croatia');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Austria');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Slovakia');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Ghana');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Greece');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Portugal');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Senegal');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Mexico');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('France');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Colombia');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Turkey');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Bosnia-Herzegovina');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Chile');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Wales');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Albania');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Algeria');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Netherlands');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Belgium');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Poland');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Uruguay');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Macedonia');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Serbia');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Costa Rica');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Cameroon');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Scotland');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Guinea');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Switzerland');
INSERT INTO `db`.`country_base` (`name`)
VALUES ('Egypt');

-- -- /*league_base*/
--
INSERT INTO `db`.`league_base` (`name`, `league_type`, `country_name`)
VALUES ('CUSTOM LEAGUE', 'CUSTOM', 'INTERNATIONAL');

INSERT INTO `db`.`team_base` (`name`, `country_name`, `league_name`,`background_url`,`logo_url`,`shirt_colors`) VALUES ('Inter Milan', 'Italy', 'CUSTOM LEAGUE', 'https://i.imgur.com/p9hiHBU.jpg','https://i.imgur.com/68zX0JA.png','black-blue');
INSERT INTO `db`.`team_base` (`name`, `country_name`, `league_name`,`background_url`,`logo_url`,`shirt_colors`) VALUES ('Juventus Turin', 'Italy', 'CUSTOM LEAGUE', 'https://i.imgur.com/4i8zuIz.jpg','https://i.imgur.com/e2SUem3.png','white-black');
INSERT INTO `db`.`team_base` (`name`, `country_name`, `league_name`,`background_url`,`logo_url`,`shirt_colors`)  VALUES ('Real Madrid', 'Spain', 'CUSTOM LEAGUE','https://i.imgur.com/tGyKK5z.jpg','https://i.imgur.com/D8QI0CW.png','white-white');
INSERT INTO `db`.`team_base` (`name`, `country_name`, `league_name`,`background_url`,`logo_url`,`shirt_colors`)  VALUES ('FC Barcelona', 'Spain', 'CUSTOM LEAGUE','https://i.imgur.com/JwG2jlQ.jpg','https://i.imgur.com/DfaFlpf.png','blue-red');
INSERT INTO `db`.`team_base` (`name`, `country_name`, `league_name`,`background_url`,`logo_url`,`shirt_colors`)  VALUES ('PSG', 'France', 'CUSTOM LEAGUE','https://i.imgur.com/vcgfqz2.png','https://i.imgur.com/rmTRfOY.png','purple-red');
INSERT INTO `db`.`team_base` (`name`, `country_name`, `league_name`,`background_url`,`logo_url`,`shirt_colors`)  VALUES ('FC Liverpool', 'England', 'CUSTOM LEAGUE','https://i.imgur.com/Zpwog2I.jpg','https://i.imgur.com/IvykaLV.png','red-red');


INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('1', 'Lautaro', 'Martinez', 'Argentina', 'Inter Milan', 'https://i.imgur.com/Korp2pY.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('2', 'Romelu', 'Lukaku', 'Belgium', 'Inter Milan', 'https://i.imgur.com/H9fDzQn.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('3', 'Alexis', 'Sanchez', 'Chile', 'Inter Milan', 'https://i.imgur.com/euAPXAF.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('4', 'Sebastiano ', 'Esposito', 'Italy', 'Inter Milan', 'https://i.imgur.com/ueHxCEO.jpg');

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('5', 'Stefano', 'Sensi', 'Italy', 'Inter Milan', 'https://i.imgur.com/fOtnRHI.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('6', 'Nicolo', 'Barella', 'Italy', 'Inter Milan', 'https://i.imgur.com/ZwMpRyZ.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('7', 'Roberto', 'Gagliardini', 'Italy', 'Inter Milan', 'https://i.imgur.com/7c4zUKl.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('8', 'Matias', 'Vecino', 'Uruguay', 'Inter Milan', 'https://i.imgur.com/JBhhoHO.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('9', 'Borja', 'Valero', 'Spain', 'Inter Milan', 'https://i.imgur.com/Sn6sKDA.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('10', 'Marcelo', 'Brozovic', 'Croatia', 'Inter Milan', 'https://i.imgur.com/0lmUzAL.jpg');

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('11', 'Antonio', 'Candreva', 'Italy', 'Inter Milan', 'https://i.imgur.com/RqgY34H.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('12', 'Danilo', 'Dambrosio', 'Italy', 'Inter Milan', 'https://i.imgur.com/kpzXaMU.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('13', 'Valentino', 'Lazaro', 'Austria', 'Inter Milan', 'https://i.imgur.com/ipU23nR.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('14', 'Kwadwo', 'Asamoah', 'Ghana', 'Inter Milan', 'https://i.imgur.com/QiUETTV.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('15', 'Cristiano', 'Biraghi', 'Italy', 'Inter Milan', 'https://i.imgur.com/vrntD7n.jpg');

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('16', 'Andrea', 'Ranocchia', 'Italy', 'Inter Milan', 'https://i.imgur.com/rKUcPae.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('17', 'Stefan', 'de Vrij', 'Netherlands', 'Inter Milan', 'https://i.imgur.com/uRjDBks.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('18', 'Diego', 'Godin', 'Uruguay', 'Inter Milan', 'https://i.imgur.com/D2KcChz.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('19', 'Milan', 'Skriniar', 'Slovakia', 'Inter Milan', 'https://i.imgur.com/DdepIAO.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('20', 'Alessandro', 'Bastoni', 'Italy', 'Inter Milan', 'https://i.imgur.com/zMG9Iog.jpg');

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('21', 'Samir', 'Handanovic', 'Slovenia', 'Inter Milan', 'https://i.imgur.com/TUuJngC.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('22', 'Daniele', 'Padelli', 'Italy', 'Inter Milan','https://i.imgur.com/3yLZx1y.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('23', 'Tommaso', 'Berni', 'Italy', 'Inter Milan', 'https://i.imgur.com/xUqIVlg.jpg');


INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('90', '40', '70', '87', '82', '80', '95', '1', '88', '78', '0', '0', '75', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('92', '30', '82', '78', '71', '73', '88', '2', '80', '69', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '35', '62', '90', '80', '82', '75', '3', '82', '40', '0', '0', '65', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '35', '70', '65', '62', '70', '75', '4', '81', '50', '0', '0', '50', '0');

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('90', '70', '50', '93', '95', '90', '88', '5', '78', '55', '0', '0', '65', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '75', '60', '80', '85', '80', '90', '6', '85', '70', '0', '0', '50', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '75', '90', '60', '69', '65', '90', '7', '75', '85', '0', '0', '55', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '70', '92', '75', '78', '70', '70', '8', '80', '60', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('72', '68', '58', '74', '83', '85', '90', '9', '65', '50', '0', '0', '65', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '90', '68', '78', '90', '83', '92', '10', '80', '70', '0', '0', '75', '0');

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('81', '88', '72', '84', '82', '80', '90', '11', '82', '80', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '90', '80', '70', '80', '75', '92', '12', '80', '82', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('78', '60', '73', '82', '85', '83', '90', '13', '90', '78', '0', '0', '59', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('75', '85', '68', '82', '80', '82', '88', '14', '82', '70', '0', '0', '60', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '82', '85', '75', '78', '75', '90', '15', '85', '80', '0', '0', '62', '0');

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('50', '85', '90', '50', '60', '62', '85', '16', '80', '75', '0', '0', '60', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('65', '92', '88', '70', '75', '72', '90', '17', '88', '70', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('60', '93', '90', '65', '70', '65', '88', '18', '78', '74', '0', '0', '65', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('68', '96', '92', '70', '74', '75', '90', '19', '85', '80', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('60', '79', '90', '70', '67', '70', '88', '20', '80', '78', '0', '0', '72', '0');

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('20', '80', '85', '15', '70', '65', '92', '21', '60', '80', '90', '95', '30', '92');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('25', '70', '75', '12', '50', '40', '90', '22', '70', '82', '80', '72', '35', '86');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('20', '55', '50', '10', '55', '45', '88', '23', '55', '50', '70', '74', '20', '75');

/*positions*/

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('1', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('1', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('1', 'CF');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('2', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('2', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('2', 'CF');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('3', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('3', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('3', 'CF');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('4', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('4', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('4', 'CF');



INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('5', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('5', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('5', 'CM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('6', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('6', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('6', 'CM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('7', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('7', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('7', 'CM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('8', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('8', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('8', 'CM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('9', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('9', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('9', 'CM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('10', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('10', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('10', 'CM');


INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('11', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('12', 'LM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('11', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('11', 'LM');




INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('12', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('12', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('12', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('12', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('12', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('12', 'LCB');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('13', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('13', 'LM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('14', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('14', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('14', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('14', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('14', 'RCM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('15', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('15', 'LB');


INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('16', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('16', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('16', 'RCB');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('17', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('17', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('17', 'CB');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('18', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('18', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('18', 'CB');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('19', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('19', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('19', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('19', 'RB');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('20', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('20', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('20', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('20', 'LB');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('21', 'GK');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('22', 'GK');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('23', 'GK');

/*barcelona*/

/*players*/

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('24', 'Marc-Andre', 'ter Stegen', 'Germany', 'Fc Barcelona','https://i.imgur.com/1ajtyR5.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('25', 'Norberto', 'Neto', 'Brazil', 'Fc Barcelona','https://i.imgur.com/yYraBzo.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('26', 'Clement', 'Lenglet', 'France', 'Fc Barcelona','https://i.imgur.com/ps5HwLP.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('27', 'Samuel', 'Umtiti', 'France', 'Fc Barcelona','https://i.imgur.com/IlTPCsh.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('28', 'Gerard', 'Pique', 'Spain', 'Fc Barcelona','https://i.imgur.com/7bpliCq.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('29', 'Jordi', 'Alba', 'Spain', 'Fc Barcelona','https://i.imgur.com/mrnOtJz.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('30', 'Nelson', 'Semedo', 'Portugal', 'Fc Barcelona','https://i.imgur.com/JtwXL0j.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('31', 'Sergio', 'Busquets', 'Spain', 'Fc Barcelona','https://i.imgur.com/RdRAfNB.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('32', 'Frenkie', 'de Jong', 'Netherlands', 'Fc Barcelona','https://i.imgur.com/7zce2Hp.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('33', 'Melo', 'Arthur', 'Brazil', 'Fc Barcelona','https://i.imgur.com/ASCyReq.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('34', 'Sergi', 'Roberto', 'Spain', 'Fc Barcelona','https://i.imgur.com/VAzmWcI.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('35', 'Ivan', 'Rakitic', 'Croatia', 'Fc Barcelona','https://i.imgur.com/p9lq6jw.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('36', 'Arturo', 'Vidal', 'Chile', 'Fc Barcelona','https://i.imgur.com/gYtZG8m.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('37', 'Ousmane', 'Dembele', 'France', 'Fc Barcelona','https://i.imgur.com/lj0qHrc.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('38', 'Ansu', 'Fati', 'Spain', 'Fc Barcelona','https://i.imgur.com/pbBmmuy.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('39', 'Lionel', 'Messi', 'Argentina', 'Fc Barcelona','https://i.imgur.com/xcnIhbm.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('40', 'Antoine', 'Griezmann', 'France', 'Fc Barcelona','https://i.imgur.com/AQuOkGk.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('41', 'Luis', 'Suarez', 'Uruguay', 'Fc Barcelona','https://i.imgur.com/04i3E9d.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('42', 'Carles', 'Perez', 'Spain', 'Fc Barcelona','https://i.imgur.com/tMpQypI.jpg');

/*positions*/

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('24', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('25', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('26', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('26', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('26', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('27', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('27', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('27', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('28', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('28', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('28', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('29', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('29', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('30', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('30', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('31', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('31', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('31', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('32', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('32', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('32', 'RCM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('33', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('33', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('33', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('34', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('34', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('34', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('34', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('34', 'RM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('35', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('35', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('35', 'RCM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('35', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('35', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('35', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('36', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('36', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('36', 'RCM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('35', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('35', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('35', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('36', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('36', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('36', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('37', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('37', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('38', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('38', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('38', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('38', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('38', 'RM');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('39', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('39', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('39', 'RCF');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('40', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('40', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('40', 'RCF');


INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('41', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('41', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('41', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('42', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('42', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('42', 'RCF');

/*player attributes*/

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('25', '82', '70', '20', '50', '60', '80', '24', '70', '83', '92', '88', '15', '90');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('20', '74', '72', '15', '40', '55', '77', '25', '68', '84', '88', '80', '18', '84');

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('50', '90', '88', '40', '66', '70', '80', '26', '88', '90', '0', '0', '40', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('55', '88', '90', '45', '70', '75', '85', '27', '90', '92', '0', '0', '55', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('50', '93', '95', '80', '75', '80', '90', '28', '82', '80', '0', '0', '60', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '83', '55', '76', '78', '83', '89', '29', '84', '83', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '85', '60', '72', '70', '80', '80', '30', '80', '76', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('78', '82', '80', '83', '78', '84', '90', '31', '81', '72', '0', '0', '75', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('88', '79', '60', '88', '95', '90', '92', '32', '84', '77', '0', '0', '78', '0');

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '81', '64', '85', '92', '93', '95', '33', '80', '70', '0', '0', '73', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '78', '70', '75', '88', '78', '88', '34', '82', '74', '0', '0', '71', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '80', '72', '70', '80', '74', '85', '35', '80', '77', '0', '0', '74', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('78', '82', '77', '75', '82', '78', '90', '36', '82', '90', '0', '0', '78', '0');

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '80', '70', '83', '82', '86', '91', '37', '90', '67', '0', '0', '76', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('75', '58', '70', '78', '80', '80', '79', '38', '82', '65', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('95', '60', '72', '96', '90', '92', '80', '39', '81', '70', '0', '0', '90', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '90', '65', '80', '85', '82', '90', '40', '75', '65', '0', '0', '88', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('90', '65', '84', '86', '83', '80', '79', '41', '84', '92', '0', '0', '87', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('75', '50', '60', '65', '78', '82', '85', '42', '87', '80', '0', '0', '70', '0');


/*Real Madrid*/

/*players*/

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('43', 'Thibaut', 'Courtois', 'Belgium', 'Real Madrid','https://i.imgur.com/IAkZKeD.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('44', 'Alphonse', 'Areola', 'France', 'Real Madrid','https://i.imgur.com/sCfEV9b.jpg');

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('45', 'Raphael', 'Varane', 'France', 'Real Madrid','https://i.imgur.com/WaGKj1O.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('46', 'Eder', 'Militao', 'Brazil', 'Real Madrid','https://i.imgur.com/daAPz1j.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('47', 'Sergio', 'Ramos', 'Spain', 'Real Madrid','https://i.imgur.com/YXUXacT.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('48', 'Jose', 'Nacho', 'Spain', 'Real Madrid','https://i.imgur.com/9tnKLXd.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('49', 'Ferland', 'Mendy', 'France', 'Real Madrid','https://i.imgur.com/FVrNtHn.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('50', 'Vieira', 'Marcelo', 'Brazil', 'Real Madrid','https://i.imgur.com/QfEi3C0.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('51', 'Daniel', 'Carvajal', 'Spain', 'Real Madrid','https://i.imgur.com/Bb5YPkv.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('52', 'Alvaro', 'Odriozola', 'Spain', 'Real Madrid','https://i.imgur.com/qb2L4Q5.jpg');

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('53', 'Carlos', 'Casemiro', 'Brazil', 'Real Madrid','https://i.imgur.com/s2g7zyt.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('54', 'Toni', 'Kroos', 'Germany', 'Real Madrid','https://i.imgur.com/9P0M2XN.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('55', 'Federico', 'Valverde', 'Spain', 'Real Madrid','https://i.imgur.com/5LeJaJO.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('56', 'Luka', 'Modric', 'Croatia', 'Real Madrid','https://i.imgur.com/7hBxzt1.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('57', 'Francisco', 'Isco', 'Spain', 'Real Madrid','https://i.imgur.com/bNtwtBO.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('58', 'James', 'Rodriguez', 'Colombia', 'Real Madrid','https://i.imgur.com/eJ6KUi4.jpg');


INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('59', 'Eden', 'Hazard', 'Belgium', 'Real Madrid','https://i.imgur.com/QkqKd2k.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('60', 'Vincius', 'Junior', 'Brazil', 'Real Madrid','https://i.imgur.com/Qr54ZtZ.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('61', 'Marco', 'Asensio', 'Spain', 'Real Madrid','https://i.imgur.com/eKHvQ6H.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('62', '', 'Rodrygo', 'Brazil', 'Real Madrid','https://i.imgur.com/dVwNvXL.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('63', 'Gareth', 'Bale', 'Wales', 'Real Madrid','https://i.imgur.com/3AWffgS.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('64', 'Lucas', 'Vazguez', 'Spain', 'Real Madrid','https://i.imgur.com/sSGBXWT.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('65', 'Karim', 'Benzema', 'France', 'Real Madrid','https://i.imgur.com/SBjJV9A.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('66', 'Luka', 'Jovic', 'Serbia', 'Real Madrid','https://i.imgur.com/mC2PKIg.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('67', 'Mariano', 'Diaz', 'Spain', 'Real Madrid','https://i.imgur.com/5ETqwY3.jpg');

/*player attr*/

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('40', '70', '68', '7', '70', '67', '97', '43', '80', '87', '94', '93', '34', '95');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('35', '67', '60', '4', '73', '48', '90', '44', '76', '74', '87', '80', '20', '86');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '92', '90', '66', '74', '55', '87', '45', '82', '80', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('55', '88', '87', '58', '70', '67', '90', '46', '85', '80', '0', '0', '67', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '87', '80', '76', '77', '79', '79', '47', '87', '56', '0', '0', '78', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('65', '84', '85', '52', '67', '81', '78', '48', '82', '87', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('78', '85', '80', '77', '80', '82', '90', '49', '86', '88', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('88', '82', '70', '88', '85', '90', '90', '50', '82', '70', '0', '0', '80', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '86', '72', '80', '80', '79', '72', '51', '78', '92', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '77', '80', '72', '82', '79', '92', '52', '91', '78', '0', '0', '87', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '92', '82', '88', '92', '90', '91', '53', '84', '80', '0', '0', '90', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('83', '87', '86', '81', '90', '93', '93', '54', '80', '82', '0', '0', '91', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '82', '84', '83', '89', '91', '97', '55', '90', '71', '0', '0', '87', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('91', '81', '66', '82', '92', '93', '94', '56', '70', '67', '0', '0', '80', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('87', '84', '80', '80', '86', '82', '93', '57', '80', '90', '0', '0', '88', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('90', '70', '78', '92', '87', '92', '87', '58', '88', '78', '0', '0', '92', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('95', '65', '67', '96', '82', '89', '78', '59', '85', '70', '0', '0', '88', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '80', '78', '90', '78', '86', '79', '60', '90', '67', '0', '0', '85', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('90', '78', '80', '87', '86', '90', '78', '61', '87', '68', '0', '0', '84', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '67', '78', '84', '85', '87', '90', '62', '88', '70', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('92', '82', '89', '85', '82', '91', '85', '63', '90', '40', '0', '0', '87', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('88', '72', '70', '82', '85', '87', '92', '64', '87', '72', '0', '0', '84', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('92', '65', '92', '87', '89', '87', '92', '65', '90', '86', '0', '0', '92', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '70', '87', '82', '81', '87', '95', '66', '92', '88', '0', '0', '78', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '72', '89', '87', '80', '82', '87', '67', '88', '78', '0', '0', '72', '0');


/*positions*/

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('43', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('44', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('45', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('45', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('45', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('46', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('46', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('46', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('46', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('47', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('47', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('47', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('47', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('48', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('48', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('48', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('48', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('48', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('49', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('50', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('50', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('51', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('52', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('53', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('53', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('53', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('53', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('53', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('53', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('54', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('54', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('54', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('55', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('55', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('55', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('55', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('56', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('56', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('56', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('57', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('57', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('57', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('57', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('58', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('58', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('58', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('58', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('58', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('59', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('59', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('59', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('59', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('59', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('60', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('60', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('61', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('61', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('61', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('61', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('61', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('62', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('62', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('62', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('62', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('62', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('63', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('63', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('63', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('63', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('63', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('64', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('65', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('65', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('65', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('66', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('66', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('66', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('67', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('67', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('67', 'LCF');

/*PSG*/

/*players*/

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('68', 'Keylor', 'Navas', 'Costa Rica', 'PSG','https://i.imgur.com/dZ4xcWb.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('69', 'Sergio', 'Rico', 'Spain', 'PSG','https://i.imgur.com/GDei3fv.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('70', 'Marcin', 'Bulka', 'Poland', 'PSG','https://i.imgur.com/DqoWnGb.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('71', '', 'Marquinhos', 'Brazil', 'PSG','https://i.imgur.com/MufqNd8.jpg');

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('72', 'Presnel', 'Kimpembe', 'France', 'PSG','https://i.imgur.com/PwcJt69.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('73', 'Thilo', 'Kehrer', 'Germany', 'PSG','https://i.imgur.com/cP4LElS.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('74', 'Abdou', 'Diallo', 'France', 'PSG','https://i.imgur.com/UPMugoB.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('75', 'Thiago', 'Silva', 'Brazil', 'PSG','https://i.imgur.com/6fXUHVR.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('76', 'Juan', 'Bernat', 'Spain', 'PSG','https://i.imgur.com/diDoDAT.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('77', 'Layvin', 'Kurzawa', 'France', 'PSG','https://i.imgur.com/EQCJNVL.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('78', 'Thomas', 'Meunier', 'Belgium', 'PSG','https://i.imgur.com/Kj2OoKv.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('79', 'Collin', 'Dagba', 'France', 'PSG','https://i.imgur.com/WFzHWJz.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('80', 'Idrissa', 'Gueye', 'Senegal', 'PSG','https://i.imgur.com/WXVywGC.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('81', 'Leandro', 'Paredes', 'Argentina', 'PSG','https://i.imgur.com/6bLwx4K.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('82', 'Marco', 'Veratti', 'Italy', 'PSG','https://i.imgur.com/kUxjZfH.jpg');


INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('83', 'Julian', 'Draxler', 'Germany', 'PSG','https://i.imgur.com/4piVpSN.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('84', 'Ander', 'Herrera', 'Spain', 'PSG','https://i.imgur.com/N5lkYpm.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('85', '', 'Neymar', 'Brazil', 'PSG','https://i.imgur.com/RJ980MF.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('86', 'Angel', 'Di Maria', 'Argentina', 'PSG','https://i.imgur.com/kLF9hjq.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('87', 'Pablo', 'Sarabia', 'Spain', 'PSG','https://i.imgur.com/2DtfVnq.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('88', 'Kylian', 'Mbappe', 'France', 'PSG','https://i.imgur.com/iJpgJIY.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('89', 'Mauro', 'Icardi', 'Argentina', 'PSG','https://i.imgur.com/tzXWQ9F.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('90', 'Edinson', 'Cavani', 'Uruguay', 'PSG','https://i.imgur.com/CqYREBm.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('91', 'Choupo', 'Moting', 'Cameroon', 'PSG','https://i.imgur.com/gwjRuEM.jpg');


/*player attr*/
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('30', '80', '66', '50', '70', '69', '90', '68', '78', '80', '90', '92', '30', '91');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('40', '78', '73', '38', '70', '72', '88', '69', '73', '67', '85', '78', '45', '88');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('50', '66', '39', '40', '55', '67', '92', '70', '77', '72', '80', '82', '30', '75');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('66', '90', '88', '77', '72', '70', '88', '71', '80', '77', '0', '0', '77', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('60', '82', '84', '67', '74', '70', '90', '72', '88', '91', '0', '0', '68', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('55', '86', '88', '62', '60', '72', '82', '73', '82', '86', '0', '0', '60', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('50', '88', '90', '63', '78', '69', '80', '74', '84', '92', '0', '0', '67', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '90', '94', '70', '82', '79', '92', '75', '70', '76', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '86', '69', '80', '78', '84', '93', '76', '86', '72', '0', '0', '75', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '82', '75', '82', '81', '82', '90', '77', '90', '63', '0', '0', '82', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('81', '78', '86', '84', '82', '78', '95', '78', '84', '65', '0', '0', '80', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('78', '72', '92', '79', '86', '82', '91', '79', '90', '56', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '80', '87', '72', '90', '76', '94', '80', '80', '77', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('87', '84', '80', '67', '87', '70', '90', '81', '87', '62', '0', '0', '78', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('92', '90', '66', '80', '88', '85', '84', '82', '82', '78', '0', '0', '83', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('93', '70', '78', '86', '82', '94', '86', '83', '92', '45', '0', '0', '92', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('75', '85', '80', '77', '69', '92', '98', '84', '92', '80', '0', '0', '87', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('92', '67', '72', '94', '90', '95', '79', '85', '88', '67', '0', '0', '92', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('90', '81', '82', '90', '92', '91', '88', '86', '80', '78', '0', '0', '88', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '68', '86', '88', '90', '78', '91', '87', '90', '89', '0', '0', '78', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('90', '68', '82', '94', '86', '84', '93', '88', '94', '77', '0', '0', '87', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('94', '70', '90', '88', '78', '80', '89', '89', '83', '56', '0', '0', '92', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('88', '73', '91', '89', '80', '82', '70', '90', '75', '70', '0', '0', '88', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '67', '80', '87', '78', '79', '92', '91', '80', '88', '0', '0', '76', '0');


/*positions*/

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('68', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('69', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('70', 'GK');

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('71', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('71', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('71', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('72', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('72', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('72', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('72', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('73', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('73', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('73', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('74', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('74', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('74', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('74', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('75', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('75', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('75', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('76', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('76', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('77', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('77', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('78', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('78', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('79', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('79', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('80', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('80', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('80', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('81', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('81', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('81', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('82', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('82', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('82', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('83', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('83', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('83', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('83', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('84', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('84', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('84', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('85', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('85', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('85', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('85', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('86', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('86', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('86', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('86', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('86', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('87', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('87', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('87', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('87', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('87', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('88', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('88', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('88', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('88', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('88', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('89', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('89', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('89', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('90', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('90', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('90', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('91', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('91', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('91', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('91', 'RCF');

/*Juventus*/

/*players*/

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('92', 'Wojciech', 'Szczesny', 'Poland', 'Juventus Turin','https://i.imgur.com/ZMKJf35.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('93', 'Carlo', 'Pinsoglio', 'Italy', 'Juventus Turin','https://i.imgur.com/0JogmDl.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('94', 'Gianluigi', 'Buffon', 'Italy', 'Juventus Turin','https://i.imgur.com/554Gdpo.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('95', 'Matthijs', 'de Ligt', 'Netherlands', 'Juventus Turin','https://i.imgur.com/IaM5jqZ.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('96', 'Leonardo', 'Bonucci', 'Italy', 'Juventus Turin','https://i.imgur.com/MVjgITl.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('97', 'Merih', 'Demiral', 'Turkey', 'Juventus Turin','https://i.imgur.com/ZZKiGVG.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('98', 'Daniele', 'Rugani', 'Italy', 'Juventus Turin','https://i.imgur.com/2FYy8XV.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('99', 'Giorgio', 'Chiellini', 'Italy', 'Juventus Turin','https://i.imgur.com/ct6RPZw.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('100', 'Alex', 'Sandro', 'Brazil', 'Juventus Turin','https://i.imgur.com/mlxxCBQ.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('101', '', 'Danilo', 'Brazil', 'Juventus Turin','https://i.imgur.com/UMwbV5i.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('102', 'Mattia', 'de Sciglio', 'Italy', 'Juventus Turin','https://i.imgur.com/9PvK2DL.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('103', 'Emre', 'Can', 'Turkey', 'Juventus Turin','https://i.imgur.com/U4KAhqK.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('104', 'Miralem', 'Pjanic', 'Bosnia-Herzegovina', 'Juventus Turin','https://i.imgur.com/18vwto7.png');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('105', 'Aaron', 'Ramsey', 'Wales', 'Juventus Turin','https://i.imgur.com/GWNHOrZ.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('106', 'Rodrigo', 'Bentancur', 'Uruguay', 'Juventus Turin','https://i.imgur.com/OkgbvTL.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('107', 'Adrien', 'Rabiot', 'France', 'Juventus Turin','https://i.imgur.com/venN711.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('108', 'Blaise', 'Matuidi', 'France', 'Juventus Turin','https://i.imgur.com/cZtGzqf.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('109', 'Sami', 'Khedira', 'Germany', 'Juventus Turin','https://i.imgur.com/SPPZfNr.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('110', 'Cristiano', 'Ronaldo', 'Portugal', 'Juventus Turin','https://i.imgur.com/fX0sWZI.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('111', 'Marko', 'Pjaca', 'Croatia', 'Juventus Turin','https://i.imgur.com/UCaYIgR.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('112', 'Federico', 'Bernardeschi', 'Italy', 'Juventus Turin','https://i.imgur.com/eqPhw9N.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('113', 'Douglas', 'Costa', 'Brazil', 'Juventus Turin','https://i.imgur.com/RSi6tcV.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('114', 'Juan', 'Cuadrado', 'Colombia', 'Juventus Turin','https://i.imgur.com/7E6ndii.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('115', 'Paulo', 'Dybala', 'Italy', 'Juventus Turin','https://i.imgur.com/hJ8gebe.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('116', 'Gonzalo', 'Higuain', 'Argentina', 'Juventus Turin','https://i.imgur.com/j9zRVmu.jpg');

/*player attr*/

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('55', '78', '80', '44', '77', '60', '90', '92', '78', '70', '90', '89', '42', '88');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('50', '76', '76', '53', '80', '62', '91', '93', '82', '72', '88', '91', '55', '89');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('53', '77', '74', '48', '83', '67', '97', '94', '67', '88', '87', '95', '44', '83');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '90', '88', '66', '86', '74', '90', '95', '84', '86', '0', '0', '68', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('72', '87', '91', '72', '88', '75', '87', '96', '85', '88', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('67', '80', '87', '62', '80', '72', '84', '97', '91', '82', '0', '0', '65', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('71', '85', '91', '60', '82', '71', '83', '98', '85', '74', '0', '0', '58', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('72', '90', '91', '67', '84', '81', '93', '99', '75', '78', '0', '0', '78', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('88', '85', '80', '83', '80', '78', '92', '100', '82', '66', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '80', '84', '78', '75', '79', '91', '101', '82', '86', '0', '0', '71', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('78', '85', '87', '76', '70', '81', '77', '102', '80', '85', '0', '0', '67', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('83', '86', '88', '78', '75', '83', '79', '103', '84', '84', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '82', '80', '73', '91', '93', '94', '104', '80', '78', '0', '0', '709', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '87', '84', '80', '87', '83', '86', '105', '83', '80', '0', '0', '73', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '85', '85', '82', '86', '81', '92', '106', '80', '84', '0', '0', '77', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('83', '84', '80', '85', '87', '82', '91', '107', '84', '81', '0', '0', '78', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '86', '78', '76', '74', '79', '93', '108', '85', '89', '0', '0', '75', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('87', '85', '89', '81', '82', '78', '91', '109', '80', '94', '0', '0', '73', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('96', '72', '90', '94', '90', '95', '78', '110', '90', '81', '0', '0', '90', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('81', '67', '82', '81', '78', '76', '79', '111', '91', '83', '0', '0', '78', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('83', '62', '92', '85', '84', '79', '91', '112', '89', '73', '0', '0', '86', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('86', '72', '87', '83', '80', '88', '89', '113', '92', '77', '0', '0', '87', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('90', '87', '78', '88', '84', '79', '85', '114', '94', '82', '0', '0', '80', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('92', '69', '70', '88', '91', '92', '94', '115', '88', '58', '0', '0', '90', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('90', '70', '88', '82', '81', '80', '86', '116', '82', '88', '0', '0', '86', '0');



/*positions*/

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('92', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('93', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('94', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('95', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('95', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('95', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('96', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('96', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('96', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('97', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('97', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('97', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('97', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('98', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('98', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('98', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('99', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('99', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('99', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('99', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('100', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('100', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('101', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('101', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('102', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('102', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('102', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('103', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('103', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('103', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('103', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('103', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('103', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('104', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('104', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('104', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('105', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('105', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('105', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('105', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('106', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('106', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('106', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('106', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('107', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('107', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('107', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('108', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('108', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('108', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('108', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('109', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('109', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('109', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('110', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('110', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('110', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('110', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('110', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('111', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('111', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('111', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('111', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('111', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('112', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('112', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('112', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('112', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('112', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('113', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('113', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('113', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('113', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('113', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('114', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('114', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('115', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('115', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('115', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('116', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('116', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('116', 'CF');



/*players*/
/*FC Liverpool*/

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('117', '', 'Alisson', 'Brazil', 'FC Liverpool','https://i.imgur.com/yOnjq6a.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('118', '', 'Adrian', 'Spain', 'FC Liverpool','https://i.imgur.com/8G8YzHJ.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('119', 'Virgil', 'van Dijk', 'Netherlands', 'FC Liverpool','https://i.imgur.com/w1v0b6f.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('120', 'Joel', 'Matip', 'Cameroon', 'FC Liverpool','https://i.imgur.com/krl104n.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('121', 'Joe', 'Gomez', 'England', 'FC Liverpool','https://i.imgur.com/xBHWiM3.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('122', 'Dejan', 'Lovren', 'Croatia', 'FC Liverpool','https://i.imgur.com/LI9gWCa.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('123', 'Andrew', 'Robertson', 'Scotland', 'FC Liverpool','https://i.imgur.com/aH1A9ki.jpg');

INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('124', 'Alexander', 'Arnold', 'England', 'FC Liverpool','https://i.imgur.com/TlITLAH.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('125', 'Nathaniel', 'Clyne', 'England', 'FC Liverpool','https://i.imgur.com/iStersT.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('126', '', 'Fabinho', 'Brazil', 'FC Liverpool','https://i.imgur.com/GD1jIAm.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('127', 'Georginio', 'Wijnaldum', 'Netherlands', 'FC Liverpool','');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('128', 'Naby', 'Keita', 'Guinea', 'FC Liverpool','https://i.imgur.com/WQbc5ct.jpg');


INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('129', 'Jordan', 'Henderson', 'England', 'FC Liverpool','https://i.imgur.com/1dsLcev.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('130', 'Alex', 'Chamberlain', 'England', 'FC Liverpool','https://i.imgur.com/ivyfXVK.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('131', 'James', 'Milner', 'England', 'FC Liverpool','https://i.imgur.com/ffc3DS8.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('132', 'Adam', 'Lallana', 'England', 'FC Liverpool','https://i.imgur.com/X5D0eyD.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('133', 'Sadio', 'Mane', 'Cameroon', 'FC Liverpool','https://i.imgur.com/9fzQ2p1.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('134', 'Mohamed', 'Salah', 'Egypt', 'FC Liverpool','https://i.imgur.com/QR2VnOA.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('135', 'Xherdan', 'Shaqiri', 'Switzerland', 'FC Liverpool','https://i.imgur.com/SlCrfKj.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('136', 'Roberto', 'Firmino', 'Brazil', 'FC Liverpool','https://i.imgur.com/HEPCb97.jpg');
INSERT INTO `db`.`players_base` (`id`, `first_name`, `last_name`, `country_name`, `team_name`, `image_url`) VALUES ('137', 'Divock', 'Origi', 'Belgium', 'FC Liverpool','https://i.imgur.com/80p4v7s.jpg');

/*player attr*/

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('67', '89', '80', '66', '78', '80', '94', '117', '83', '83', '93', '96', '34', '94');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('60', '77', '78', '55', '79', '81', '93', '118', '83', '84', '89', '83', '43', '87');

INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '94', '95', '74', '76', '78', '92', '119', '87', '86', '0', '0', '77', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('78', '90', '89', '70', '70', '72', '89', '120', '88', '90', '0', '0', '75', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('66', '87', '92', '67', '65', '69', '78', '121', '91', '82', '0', '0', '70', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('60', '88', '93', '62', '68', '67', '92', '122', '79', '85', '0', '0', '72', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '89', '83', '87', '81', '83', '93', '123', '87', '88', '0', '0', '77', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '90', '81', '89', '84', '80', '90', '124', '87', '84', '0', '0', '73', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('70', '80', '77', '78', '80', '83', '94', '125', '90', '92', '0', '0', '66', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('80', '92', '90', '87', '84', '92', '94', '126', '89', '93', '0', '0', '88', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '86', '87', '80', '81', '78', '72', '127', '78', '95', '0', '0', '80', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('78', '94', '87', '77', '72', '70', '89', '128', '83', '94', '0', '0', '68', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('86', '88', '86', '78', '84', '80', '82', '129', '78', '92', '0', '0', '79', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('88', '78', '72', '80', '82', '83', '86', '130', '86', '88', '0', '0', '80', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('85', '90', '88', '78', '80', '79', '92', '131', '81', '89', '0', '0', '82', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('88', '70', '78', '82', '84', '85', '80', '132', '85', '52', '0', '0', '88', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('94', '68', '70', '91', '89', '90', '93', '133', '90', '56', '0', '0', '92', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('93', '72', '71', '93', '85', '87', '88', '134', '82', '82', '0', '0', '91', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('82', '73', '61', '88', '80', '81', '80', '135', '84', '85', '0', '0', '81', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('92', '66', '82', '90', '87', '85', '87', '136', '88', '78', '0', '0', '88', '0');
INSERT INTO `db`.`players_attributes_base` (`attacking`, `defending`, `heading`, `dribbling`, `passing`, `technique`, `team_work`, `player_id`, `speed`, `aggression`, `one_on_ones`, `penalty_saving`, `penalty_scoring`, `reflexes`) VALUES ('83', '70', '80', '81', '78', '79', '92', '137', '87', '70', '0', '0', '90', '0');


/*positions*/

INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('117', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('118', 'GK');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('119', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('119', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('119', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('120', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('120', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('120', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('121', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('121', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('121', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('121', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('121', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('122', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('122', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('122', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('123', 'LB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('123', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('124', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('125', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('126', 'LCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('126', 'CB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('126', 'RCB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('126', 'RB');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('126', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('126', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('126', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('127', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('127', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('127', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('128', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('128', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('128', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('129', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('129', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('129', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('129', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('130', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('130', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('130', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('130', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('130', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('131', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('131', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('132', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('132', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('132', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('133', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('133', 'LCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('133', 'CM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('133', 'RCM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('133', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('134', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('134', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('134', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('134', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('134', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('135', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('135', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('135', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('135', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('135', 'RM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('136', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('136', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('136', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('137', 'RCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('137', 'CF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('137', 'LCF');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('137', 'LM');
INSERT INTO `db`.`positions_base` (`player_id`, `position`) VALUES ('137', 'RM');
