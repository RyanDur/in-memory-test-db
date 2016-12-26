CREATE TABLE programmer (
  name VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE unicorn (
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (name),
  programmer_name VARCHAR(50),
  CONSTRAINT programmers_name_fk
  FOREIGN KEY (programmer_name)
  REFERENCES programmer(name)
)