CREATE TABLE ht_property
  (
     id       BIGINT NOT NULL auto_increment,
     name     VARCHAR(255) NOT NULL,
     revision BIGINT NOT NULL,
     value    VARCHAR(255) NOT NULL,
     PRIMARY KEY (id)
  )
engine = innodb;