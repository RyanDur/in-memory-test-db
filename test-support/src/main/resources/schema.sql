DROP TABLE IF EXISTS unicorn;
DROP TABLE IF EXISTS programmer;

CREATE TABLE IF NOT EXISTS programmer (
  name VARCHAR(20) NOT NULL PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS unicorn (
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (name),
  programmer_name VARCHAR(50),
  CONSTRAINT programmers_name_fk
  FOREIGN KEY (programmer_name)
  REFERENCES programmer(name)
)