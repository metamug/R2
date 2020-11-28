CREATE TABLE IF NOT EXISTS request_log (
    log_id BIGSERIAL NOT NULL PRIMARY KEY,
    ip inet NOT NULL,
    app_name VARCHAR(30) NOT NULL,
    resource VARCHAR(100) NOT NULL,
    version VARCHAR(10) NOT NULL,
    user_agent VARCHAR(255) NOT NULL DEFAULT 'unknown',
    device_type VARCHAR(10) NOT NULL DEFAULT 'desktop',
    os varchar(10) NOT NULL DEFAULT 'windows',    
    client_app_name varchar(128) default null,
    status SMALLINT NOT NULL,
    size int NOT NULL,
    logged_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
CREATE INDEX IF NOT EXISTS app_name_index ON request_log(app_name);
CREATE TABLE IF NOT EXISTS query_log (
    log_id BIGSERIAL NOT NULL PRIMARY KEY,
    query text NOT NULL,
    app_name VARCHAR(30) NOT NULL,
    status BOOLEAN NOT NULL DEFAULT TRUE,
    executed_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);
COMMENT ON COLUMN query_log.status is '1-Query executed successfully;0- query failed';
CREATE INDEX IF NOT EXISTS log_id_app_name_index ON query_log(log_id,app_name);

CREATE TABLE IF NOT EXISTS usr (
    user_id BIGSERIAL NOT NULL PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL UNIQUE,
    pass_word VARCHAR(25) NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS role (
    role_id BIGSERIAL NOT NULL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS usr_role (
    id BIGSERIAL NOT NULL PRIMARY KEY,
    role_id VARCHAR(50) NOT NULL,
    user_id BIGINT NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (role_id) REFERENCES role(role_id)
    ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE INDEX IF NOT EXISTS role_id_index ON usr_role(role_id);
CREATE INDEX IF NOT EXISTS user_id_index ON usr_role(user_id);
CREATE TABLE IF NOT EXISTS error_log (
    log_id BIGSERIAL NOT NULL PRIMARY KEY,
    error_id VARCHAR(25) NOT NULL,
    request_method VARCHAR(6) NOT NULL,
    message TEXT NULL,
    trace TEXT NULL,
    resource VARCHAR(100) NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS client_app (
    app_id BIGSERIAL not null PRIMARY KEY,
    client_app_name varchar(128) not null,
    access_key varchar(128) not null,
    secret_key varchar(128) not null,
    created_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS mtg_query_catalog (
    query_id BIGSERIAL NOT NULL PRIMARY KEY,
    query_name varchar(100) NOT NULL,
    query text NOT NULL,
    type varchar(10) NOT NULL,
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE IF NOT EXISTS mtg_query_test_data (
    query_id BIGSERIAL NOT NULL,
    variable_name varchar(100) NOT NULL,
    variable_test_value varchar(512) NOT NULL,
    created_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (query_id) REFERENCES mtg_query_catalog(query_id)
    ON UPDATE CASCADE ON DELETE RESTRICT
);
CREATE TABLE IF NOT EXISTS mtg_query_tag (
    tag_id BIGSERIAL NOT NULL PRIMARY KEY,
    tag varchar(100) NOT NULL,
    resource_name varchar(100) NOT NULL,
    resource_version varchar(10) NOT NULL,
    created_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX IF NOT EXISTS tag_index ON mtg_query_tag(tag);
CREATE TABLE IF NOT EXISTS mtg_query_reference (
    query_id BIGSERIAL NOT NULL,
    tag_id BIGSERIAL NOT NULL,
    created_on timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (query_id) REFERENCES mtg_query_catalog(query_id)
    ON UPDATE CASCADE ON DELETE RESTRICT,
    FOREIGN KEY (tag_id) REFERENCES mtg_query_tag(tag_id)
    ON UPDATE CASCADE ON DELETE RESTRICT
);