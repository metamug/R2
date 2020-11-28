CREATE TABLE request_log (
    log_id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    ip VARCHAR(512) NOT NULL,
    app_name VARCHAR(30) NOT NULL,
    resource VARCHAR(100) NOT NULL,
    version VARCHAR(10) NOT NULL,
    user_agent VARCHAR(255) DEFAULT 'unknown' NOT NULL,
    device_type VARCHAR(10) DEFAULT 'desktop' not null,
    os varchar(10) DEFAULT 'windows' not null,
    client_app_name varchar(128) default null,
    logged_on DATETIME DEFAULT CURRENT_TIMESTAMP not null,
    status smallint NOT NULL,
    size int NOT NULL
);
CREATE INDEX app_name_resource_index ON request_log(app_name,resource);
CREATE TABLE query_log (
    log_id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    query VARCHAR(max) NOT NULL,
    app_name VARCHAR(30) NOT NULL,
    status TINYINT NOT NULL DEFAULT '1',
    executed_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX log_id_index ON query_log(log_id,app_name);

CREATE TABLE usr (
    user_id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    pass_word VARCHAR(25) NOT NULL,
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE role (
    role_id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE usr_role (
    id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    role_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_usr_role_role FOREIGN KEY (role_id) REFERENCES role(role_id)
    ON UPDATE CASCADE ON DELETE NO ACTION
);
CREATE INDEX role_id_index ON usr_role(role_id);
CREATE INDEX user_id_index ON usr_role (user_id);
CREATE TABLE error_log (
    log_id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,
    error_id VARCHAR(25) NOT NULL,
    request_method VARCHAR(6) NOT NULL,
    message TEXT NULL,
    trace TEXT NULL,
    resource VARCHAR(100) NOT NULL,
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE client_app (
    app_id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
    client_app_name varchar(128) not null,
    access_key varchar(128) not null,
    secret_key varchar(128) not null,
    created_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE mtg_query_catalog (
    query_id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
    query_name varchar(100) NOT NULL,
    query varchar(max) NOT NULL,
    type varchar(10) NOT NULL CHECK (type IN('query','update')),
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE mtg_query_test_data (
    query_id bigint NOT NULL,
    variable_name varchar(100) NOT NULL,
    variable_test_value varchar(512) NOT NULL,
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mtg_query_catalog_mtg_query_test_data FOREIGN KEY (query_id)
    REFERENCES mtg_query_catalog(query_id)
    ON UPDATE CASCADE ON DELETE NO ACTION
);
CREATE TABLE mtg_query_tag (
    tag_id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
    tag varchar(100) NOT NULL,
    resource_name varchar(100) NOT NULL,
    resource_version varchar(10) NOT NULL,
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX tag_index ON mtg_query_tag(tag);
CREATE TABLE mtg_query_reference (
    query_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    created_on DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mtg_query_catalog_mtg_query_reference FOREIGN KEY (query_id)
    REFERENCES mtg_query_catalog(query_id)
    ON UPDATE CASCADE ON DELETE NO ACTION,
    CONSTRAINT fk_mtg_query_tag_mtg_query_reference FOREIGN KEY (tag_id)
    REFERENCES mtg_query_tag(tag_id)
    ON UPDATE CASCADE ON DELETE NO ACTION
);