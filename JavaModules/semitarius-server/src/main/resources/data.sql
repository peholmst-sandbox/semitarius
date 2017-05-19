INSERT INTO tenants (id, version, identifier, name, station_lat, station_lon)
VALUES (1, 1, 'pargasfbk', 'Pargas FBK', 60.310829, 22.297064);

INSERT INTO members (version, tenant_id, first_name, last_name) VALUES (1, 1, 'Joe', 'Cool');

INSERT INTO status_descriptors (version, tenant_id, name, color)
VALUES (1, 1, 'På stationen inom 10 minuter', 0x008800);

INSERT INTO status_descriptors (version, tenant_id, name, color)
VALUES (1, 1, 'På stationen inom 20 minuter', 0xffff00);

INSERT INTO status_descriptors (version, tenant_id, name, color)
VALUES (1, 1, 'Ej tillgänglig', 0xff0000);

INSERT INTO status_descriptors (version, tenant_id, name, color)
VALUES (1, 1, 'Normal beredskap', 0xffffff);

INSERT INTO status_descriptors (version, tenant_id, name, color)
VALUES (1, 1, 'Förhöjd beredskap', 0x00ffff);
