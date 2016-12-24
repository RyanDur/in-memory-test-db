CREATE SCHEMA foo;
SET SCHEMA foo;

CREATE TABLE programmer (
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY(name)
);

CREATE TABLE unicorn (
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (name),
  programmer VARCHAR(50),
  FOREIGN KEY (programmer)
  REFERENCES foo.programmer
  ON DELETE RESTRICT
)