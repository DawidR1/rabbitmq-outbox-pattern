\connect ipfronplus


CREATE SCHEMA projectdb;

CREATE TABLE messages (
    id bigint primary key,
    exchange text not null,
    routingKey text,
    headers text,
    payload text not null
);

create sequence messages_s INCREMENT 1  START 1;
