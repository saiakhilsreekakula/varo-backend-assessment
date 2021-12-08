CREATE TABLE address
(
    id bigint NOT NULL,
    address_line_1 character varying(255),
    address_line_2 character varying(255),
    city character varying(255),
    state character varying(255),
    zip_code character varying(255),
    CONSTRAINT address_pkey PRIMARY KEY (id)
);

CREATE TABLE users
(
    user_id character varying(255) NOT NULL,
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone_number character varying(255),
    is_active boolean,
    address_id bigint,
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT fkditu6lr4ek16tkxtdsne0gxib FOREIGN KEY (address_id)
        REFERENCES address (id) MATCH SIMPLE
);

create table address_audit(
	id INT GENERATED ALWAYS AS IDENTITY,
	address_line_1 varchar(255),
	address_line_2 varchar(255),
	city varchar(255),
	state varchar(255),
	zip_code varchar(255),
	user_id varchar(255),
	changed_on timestamp(6)
);

CREATE TABLE user_audit
(
    id INT GENERATED ALWAYS AS IDENTITY,
    user_id character varying(255),
    email character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    phone_number character varying(255),
    is_active boolean,
    changed_on timestamp(6)
);

CREATE OR REPLACE FUNCTION audit_address_changes()
  RETURNS TRIGGER 
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	IF (NEW.address_line_1 <> OLD.address_line_1) OR (NEW.address_line_2 <> OLD.address_line_2)
	OR (NEW.state <> OLD.state) OR (NEW.city <> OLD.city) OR (NEW.zip_code <> OLD.zip_code) THEN
		 INSERT INTO address_audit(user_id,address_line_1, address_line_2, city, state, zip_code,changed_on)
		 VALUES(OLD.id,OLD.address_line_1, OLD.address_line_2, OLD.city, OLD.state, OLD.zip_code,now());
	END IF;

	RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION audit_user_changes()
  RETURNS TRIGGER 
  LANGUAGE PLPGSQL
  AS
$$
BEGIN
	IF (NEW.email <> OLD.email) OR (NEW.first_name <> OLD.first_name) OR (NEW.last_name <> OLD.last_name)
	OR (NEW.phone_number <> OLD.phone_number) OR (NEW.is_active <> OLD.is_active) THEN
		 INSERT INTO user_audit(user_id,email, first_name, last_name, phone_number, is_active,changed_on)
		 VALUES(OLD.user_id,OLD.email, OLD.first_name, OLD.last_name, OLD.phone_number, OLD.is_active,now());
	END IF;

	RETURN NEW;
END;
$$;

CREATE TRIGGER user_changes
  BEFORE UPDATE
  ON users
  FOR EACH ROW
  EXECUTE PROCEDURE audit_user_changes();
  
CREATE TRIGGER address_changes
  BEFORE UPDATE
  ON address
  FOR EACH ROW
  EXECUTE PROCEDURE audit_address_changes();

CREATE INDEX idx_users_user_id
ON users(user_id);

CREATE INDEX idx_users_email
ON users(email);