DROP TABLE IF EXISTS Member;
DROP TABLE IF EXISTS url_mappings;
DROP TABLE IF EXISTS url_access_history;

create table Member
(
    id integer not null,
    name varchar(255) not null,
    primary key (id)
);

create table url_mappings
(
    id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    origin_url VARCHAR(255) NOT NULL UNIQUE,
    short_url VARCHAR(8) NOT NULL UNIQUE,
    reg_dttm TIMESTAMP NOT NULL DEFAULT now(),
    mod_dttm TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE INDEX idx_um_reg_dttm ON url_mappings (reg_dttm);
CREATE INDEX idx_um_mod_dttm ON url_mappings (mod_dttm);

CREATE TABLE url_access_history (
    id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    origin_url VARCHAR(255) NOT NULL,
    short_url VARCHAR(8) NOT NULL,
    request_dttm TIMESTAMP NOT NULL,
    reg_dttm TIMESTAMP NOT NULL DEFAULT now(),
    mod_dttm TIMESTAMP NOT NULL DEFAULT now(),
    PRIMARY KEY (id)
);

CREATE INDEX idx_uah_short_url ON url_access_history (short_url);
CREATE INDEX idx_uah_reg_dttm ON url_access_history (reg_dttm);
CREATE INDEX idx_uah_mod_dttm ON url_access_history (mod_dttm);
CREATE INDEX idx_uah_request_dttm ON url_access_history (request_dttm);