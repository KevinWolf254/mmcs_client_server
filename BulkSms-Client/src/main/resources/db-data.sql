INSERT INTO `marketme_client_db`.`country` (`name`, `code`, `currency`) VALUES ('RWANDA', '+250', 'RWF');
INSERT INTO `marketme_client_db`.`country` (`name`, `code`, `currency`) VALUES ('KENYA', '+254', 'KES');
INSERT INTO `marketme_client_db`.`country` (`name`, `code`, `currency`) VALUES ('TANZANIA', '+255', 'TZS');
INSERT INTO `marketme_client_db`.`country` (`name`, `code`, `currency`) VALUES ('UGANDA', '+256', 'UGX');
INSERT INTO `marketme_client_db`.`country` (`name`, `code`, `currency`) VALUES ('US', '+001', 'USD');
INSERT INTO `marketme_client_db`.`country` (`name`, `code`, `currency`) VALUES ('FINLAND', '+358', 'EUR');

INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('MTN_RW', '1');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('TIGO_RW', '1');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('AIRTEL_RW', '1');

INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('SAFARICOM_KE', '2');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('TELKOM_KE', '2');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('EQUITEL_KE', '2');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('AIRTEL_KE', '2');

INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('VODACOM_TZ', '3');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('TIGO_TZ', '3');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('SMILE_TZ', '3');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('HALOTEL_TZ', '3');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('AIRTEL_TZ', '3');

INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('MTN_UG', '4');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('AFRICELL_UG', '4');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('UT_UG', '4');
INSERT INTO `marketme_client_db`.`service_provider` (`name`, `country_id`) VALUES ('AIRTEL_UG', '4');

INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('61');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('62');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('65');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('66');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('67');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('68');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('69');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('70');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('71');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('72');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('73');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('74');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('75');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('76');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('77');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('78');
INSERT INTO `marketme_client_db`.`prefix` (`number`) VALUES ('79');

INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('1', '16');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('2', '10');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('3', '11');

INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('4', '8');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('4', '9');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('4', '10');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('4', '17');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('5', '15');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('6', '14');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('7', '11');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('7', '13');

INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('8', '12');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('8', '13');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('8', '14');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('9', '3');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('9', '5');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('9', '9');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('10', '4');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('11', '2');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('12', '6');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('12', '7');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('12', '16');

INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('13', '15');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('13', '16');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('14', '17');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('15', '9');
INSERT INTO `marketme_client_db`.`service_provider_prefix` (`service_provider_id`, `prefix_id`) VALUES ('16', '13');

