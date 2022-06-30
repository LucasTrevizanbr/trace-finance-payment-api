INSERT INTO wallet (id, owner_name, limit_value) VALUES (UUID_TO_BIN('3eced1b2-44cd-4e9b-932e-3dc84cebec69', true) , 'Carlos', 5000.00);

INSERT INTO diary_payment (id, amount, period, payment_date_time, wallet_id) VALUES (1, 170.00, 'DAYTIME', utc_timestamp, UUID_TO_BIN('3eced1b2-44cd-4e9b-932e-3dc84cebec69', true));
INSERT INTO diary_payment (id, amount, period, payment_date_time, wallet_id) VALUES (2, 830.00, 'DAYTIME', utc_timestamp, UUID_TO_BIN('3eced1b2-44cd-4e9b-932e-3dc84cebec69', true));
INSERT INTO diary_payment (id, amount, period, payment_date_time, wallet_id) VALUES (3, 1000.00,'DAYTIME', utc_timestamp, UUID_TO_BIN('3eced1b2-44cd-4e9b-932e-3dc84cebec69', true));
INSERT INTO diary_payment (id, amount, period, payment_date_time, wallet_id) VALUES (4, 1000.00, 'DAYTIME', utc_timestamp, UUID_TO_BIN('3eced1b2-44cd-4e9b-932e-3dc84cebec69', true));
INSERT INTO diary_payment (id, amount, period, payment_date_time, wallet_id) VALUES (5, 1000.00, 'DAYTIME', utc_timestamp, UUID_TO_BIN('3eced1b2-44cd-4e9b-932e-3dc84cebec69', true));
INSERT INTO diary_payment (id, amount, period, payment_date_time, wallet_id) VALUES (6, 900.00, 'NIGHTLY', utc_timestamp, UUID_TO_BIN('3eced1b2-44cd-4e9b-932e-3dc84cebec69', true))


