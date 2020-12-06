# Migrating Console Database

Data related to <a href="https://metamug.com/docs/console" target="_blank">Metamug Console</a> i.e. user account, backend related meta-data, resource meta-data is stored in a database called mtg_console. This database is created using <a href="http://hsqldb.org/" target="_blank">HSQLDB</a>. HSQLDB works best if the meta-data is smaller but as the number of users increases causing increase the metadata as well, we encourage the developer to make the transition to production level database from the very beginning since we don't provide capabilities to migrate your data HSQLDB. The following sections will describe the process of migrating the console database from HSQLDB to your own SQL database.

### Permissions and Grants

Create a user in your database grants to create a user and all CRUD operations. Also make sure that user has rights to create and execute procedures and triggers.

### Configuration

Open the db.properties file located at ```METAMUG_CONSOLE/conf/```

<img src="https://lh3.googleusercontent.com/-h8qsIpapjS0/XDYPoFQi4BI/AAAAAAAAEhY/h23neVQ3WWYw5UqPupv6r1KYLVMX8EfGQCL0BGAYYCw/h341/2019-01-09.png"></img>

The file contains a pre-configured connection option for HSQLDB. It also has a partially pre-configured connection option for MySQL and Postgres as well. Comment out the HSQLDB connection option block and uncomment the block of MySQL or Postgres whichever you prefer to connect the console with.

<img src="https://lh3.googleusercontent.com/-KyRV0iRfrcU/XDYPrdbM37I/AAAAAAAAEhY/YfBB-IU5gDwo6-KWNKupJzVwgJ1AC-3lgCL0BGAYYCw/h620/2019-01-09.png"></img>
Make sure you enter the username and password field of the user you've created in Pre-requisite section.

### Supported Databases

Currently, we only support **MySQL** and **PostgreSQL**. You're free to try databases like Oracle or MS-SQL but we do not guarantee optimal behaviour. Feel free to <a href="mailto:support@metamug.com?subject=Need database for Metamug Console">email</a> us if you need to connect the Console to some specific database.

Based on your choice run the table generation script provided below.

### Transitioning from HSQL to MySQL

```sql
--
-- Database: mtg_console
--
CREATE DATABASE IF NOT EXISTS mtg_console DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mtg_console;
-- --------------------------------------------------------

--
-- Table structure for table console_app
--

DROP TABLE IF EXISTS console_app;
CREATE TABLE IF NOT EXISTS console_app (
  app_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  app_name VARCHAR(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  app_description VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  app_version VARCHAR(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0.1',
  app_db VARCHAR(512) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'default',
  end_point VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  app_resources int(11) NOT NULL DEFAULT '0',
  user_id BIGINT(20) NOT NULL,
  access_key VARCHAR(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  secret_key VARCHAR(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL,
  KEY app_name (app_name),
  KEY user_id (user_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table console_auth_user
--

DROP TABLE IF EXISTS console_auth_user;
CREATE TABLE IF NOT EXISTS console_auth_user (
  auth_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT(20) NOT NULL,
  auth_token VARCHAR(64) COLLATE utf8mb4_unicode_ci NOT NULL,
  ip varbinary(16) NOT NULL,
  device_type VARCHAR(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL,
  KEY auth_token (auth_token)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table console_execute_class
--

DROP TABLE IF EXISTS console_execute_class;
CREATE TABLE IF NOT EXISTS console_execute_class (
  class_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  code_name VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  user_id BIGINT(20) NOT NULL,
  class_name VARCHAR(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  class_type VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  app_name VARCHAR(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL,
  KEY code_id (code_name,user_id,app_name,deleted_on)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table console_execute_code
--

DROP TABLE IF EXISTS console_execute_code;
CREATE TABLE IF NOT EXISTS console_execute_code (
  code_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  code_name VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  code_file_name VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  code_file_size VARCHAR(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  app_name VARCHAR(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  user_id BIGINT(20) NOT NULL,
  uploaded_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL,
  KEY app_name (app_name),
  KEY user_id (user_id),
  KEY code_name (code_name),
  KEY code_file_name (code_file_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table console_file
--

DROP TABLE IF EXISTS console_file;
CREATE TABLE IF NOT EXISTS console_file (
  file_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  file_hash VARCHAR(64) COLLATE utf8mb4_unicode_ci NOT NULL UNIQUE,
  file_name VARCHAR(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  file_size VARCHAR(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  file_version VARCHAR(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '1.0',
  secure TINYINT(1) NOT NULL DEFAULT '0',
  file_auth VARCHAR(50) DEFAULT NULL,
  app_name VARCHAR(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  user_id BIGINT(20) NOT NULL,
  uploaded_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL,
  KEY file_name (file_name,file_version,app_name,user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table console_role
--

DROP TABLE IF EXISTS console_role;
CREATE TABLE IF NOT EXISTS console_role (
  role_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  role_name VARCHAR(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  app_name VARCHAR(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL,
  KEY role_name (role_name),
  KEY app_name (app_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Table structure for table console_user
--

DROP TABLE IF EXISTS console_user;
CREATE TABLE IF NOT EXISTS console_user (
  user_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  password VARCHAR(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  db_password VARCHAR(191) COLLATE utf8mb4_unicode_ci NOT NULL,
  email_id VARCHAR(191) COLLATE utf8mb4_unicode_ci NOT NULL UNIQUE,
  last_visited_app VARCHAR(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  organization_name VARCHAR(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  activate_code VARCHAR(24) COLLATE utf8mb4_unicode_ci NOT NULL,
  verified TINYINT(1) DEFAULT '1',
  premium TINYINT(1) DEFAULT '1',
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL,
  KEY password (password,email_id(191))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- ---------------------------------------------------------- --------------------------------------------------------

--
-- Table structure for table console_update
--

DROP TABLE IF EXISTS console_update;
CREATE TABLE IF NOT EXISTS console_update (
  update_id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  update_data TEXT COLLATE utf8mb4_unicode_ci NOT NULL,
  update_version VARCHAR(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  update_description VARCHAR(1000) COLLATE utf8mb4_unicode_ci NOT NULL,
  update_installed_on TIMESTAMP NULL DEFAULT NULL,
  updated_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=COMPACT;

-- --------------------------------------------------------

--
-- Dumping data for table console_user
--

INSERT INTO console_user ( password, db_password, email_id, last_visited_app, organization_name, activate_code, verified, premium,  deleted_on) VALUES
( '$2a$12$bzQJzWEOx9GHGmgrq.zHQesQpfOuTgSDh2i0Cs3Etq98PWuTwcQPm', '$2a$12$bzQJzWEOx9GHGmgrq.zHQesQpfOuTgSDh2i0Cs3Etq98PWuTwcQPm', 'admin', NULL, 'Metamug Technologies', 'aGVsbG8gd29ybGQ=', 1, 1, NULL);

--
-- Database user
--
CREATE USER '1'@'%' IDENTIFIED BY '$2a$12$bzQJzWEOx9GHGmgrq.zHQesQpfOuTgSDh2i0Cs3Etq98PWuTwcQPm';
```
### Transitioning from HSQL to PostgreSQL

```sql
--
-- Database: mtg_console
--
CREATE DATABASE mtg_console;
-- ----------------------------------------------------------
--
--
-- Table structure for table `console_app`
--

DROP TABLE IF EXISTS console_app;
CREATE TABLE console_app (
  app_id BIGSERIAL PRIMARY KEY,
  app_name VARCHAR(30)  NOT NULL,
  app_description VARCHAR(255)  NOT NULL,
  app_version VARCHAR(10)  NOT NULL DEFAULT '0.1',
  app_db VARCHAR(512)  NOT NULL DEFAULT 'default',
  end_point VARCHAR(255)  NOT NULL,
  app_resources int NOT NULL DEFAULT '0',
  user_id BIGINT NOT NULL,
  access_key VARCHAR(64)  NOT NULL,
  secret_key VARCHAR(64)  NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL
);
CREATE INDEX app_name_index ON console_app(app_name);
CREATE INDEX user_id_index ON console_app(user_id);


-- --------------------------------------------------------

--
-- Table structure for table `console_auth_user`
--
DROP TABLE IF EXISTS console_auth_user;
CREATE TABLE console_auth_user (
  auth_id BIGSERIAL NOT NULL PRIMARY KEY,
  user_id BIGINT NOT NULL,
  auth_token VARCHAR(64)  NOT NULL,
  ip inet NOT NULL,
  device_type VARCHAR(10)  NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL
);
CREATE INDEX auth_token_index ON console_auth_user(auth_token);

-----------------------------------------------------------

--
-- Table structure for table `console_execute_class`
--

DROP TABLE IF EXISTS console_execute_class;
CREATE TABLE console_execute_class (
  class_id BIGSERIAL NOT NULL PRIMARY KEY,
  code_name VARCHAR(50)  NOT NULL,
  user_id int NOT NULL,
  class_name VARCHAR(255)  NOT NULL,
  class_type VARCHAR(50)  NOT NULL,
  app_name VARCHAR(50)  NOT NULL,
  added_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL
);
CREATE INDEX execute_index ON console_execute_class(code_name,user_id,app_name,deleted_on);



-- --------------------------------------------------------

--
-- Table structure for table `console_execute_code`
--

DROP TABLE IF EXISTS console_execute_code;
CREATE TABLE console_execute_code (
  code_id BIGSERIAL NOT NULL PRIMARY KEY,
  code_name VARCHAR(50)  NOT NULL,
  code_file_name VARCHAR(50)  NOT NULL,
  code_file_size VARCHAR(10)  NOT NULL,
  app_name VARCHAR(30)  NOT NULL,
  user_id BIGINT NOT NULL,
  uploaded_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL
);
  CREATE INDEX execute_app_name_index ON console_execute_code (app_name);
  CREATE INDEX execute_user_id_index ON console_execute_code (user_id);
  CREATE INDEX execute_code_name_index ON console_execute_code (code_name);
  CREATE INDEX execute_code_file_name_index ON console_execute_code (code_file_name);

-- --------------------------------------------------------

--
-- Table structure for table `console_file`
--

DROP TABLE IF EXISTS console_file;
CREATE TABLE console_file (
  file_id BIGSERIAL NOT NULL PRIMARY KEY,
  file_hash VARCHAR(64)  NOT NULL UNIQUE,
  file_name VARCHAR(191)  NOT NULL,
  file_size VARCHAR(10)  NOT NULL,
  file_version VARCHAR(10)  NOT NULL DEFAULT '1.0',
  secure boolean NOT NULL DEFAULT 'false',
  file_auth VARCHAR(50) DEFAULT NULL,
  app_name VARCHAR(30)  NOT NULL,
  user_id BIGINT NOT NULL,
  uploaded_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL
 );
  CREATE INDEX console_file_index ON console_file(file_name,file_version,app_name,user_id);

-- --------------------------------------------------------

--
-- Table structure for table `console_role`
--

DROP TABLE IF EXISTS console_role;
CREATE TABLE console_role (
  role_id BIGSERIAL NOT NULL PRIMARY KEY,
  role_name VARCHAR(50) NOT NULL,
  app_name VARCHAR(30) NOT NULL,
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL
);
CREATE INDEX role_name_index ON console_role(role_name);
CREATE INDEX role_app_name_index ON console_role(app_name);

-- --------------------------------------------------------

--
-- Table structure for table `console_user`
--


DROP TABLE IF EXISTS console_user;
CREATE TABLE console_user (
  user_id BIGSERIAL NOT NULL PRIMARY KEY,
  password VARCHAR(191) NOT NULL,
  db_password VARCHAR(191) NOT NULL,
  email_id VARCHAR(255) NOT NULL UNIQUE,
  last_visited_app VARCHAR(30) DEFAULT NULL,
  organization_name VARCHAR(100) NOT NULL,
  activate_code VARCHAR(64) NOT NULL,
  verified boolean NOT NULL DEFAULT 'false',
  premium boolean NOT NULL DEFAULT 'false',
  created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_on TIMESTAMP NULL DEFAULT NULL
);
CREATE INDEX password_email_id_index ON console_user(password,email_id);

-- --------------------------------------------------------

--
-- Table structure for table `console_update`
--
DROP TABLE IF EXISTS console_update`;
CREATE TABLE console_update` (
  update_id BIGSERIAL NOT NULL PRIMARY KEY,
  update_data TEXT NOT NULL,
  update_version VARCHAR(10) NOT NULL,
  update_description VARCHAR(1000) NOT NULL,
  update_installed_on TIMESTAMP DEFAULT NULL,
  updated_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  );

-- --------------------------------------------------------
--
-- Dumping data for table console_user
--
	INSERT INTO console_user (password,db_password,email_id,organization_name,activate_code,verified,premium,deleted_on) values('$2a$12$bzQJzWEOx9GHGmgrq.zHQesQpfOuTgSDh2i0Cs3Etq98PWuTwcQPm','$2a$12$bzQJzWEOx9GHGmgrq.zHQesQpfOuTgSDh2i0Cs3Etq98PWuTwcQPm','admin','Metamug Technologies','LoremIpsumDolor',true,true,null);
	--
-- Database user
--
CREATE USER "1" WITH PASSWORD '$2a$12$bzQJzWEOx9GHGmgrq.zHQesQpfOuTgSDh2i0Cs3Etq98PWuTwcQPm';
```
