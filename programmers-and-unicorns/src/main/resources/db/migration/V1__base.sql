CREATE TABLE programmer (
  name VARCHAR(20) NOT NULL,
  PRIMARY KEY(name)
);

CREATE TABLE unicorn (
  name VARCHAR(50) NOT NULL,
  PRIMARY KEY (name),
  programmer VARCHAR(50),
  CONSTRAINT programmers_name_fk
  FOREIGN KEY (programmer)
  REFERENCES programmer(name)
)