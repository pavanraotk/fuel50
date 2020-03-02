create SCHEMA IF not exists feeling_indicator;
create sequence IF not exists feeling_indicator.feeling_seq
    START with 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
create table IF not exists feeling_indicator.feeling (
  id                  bigint                          not null default nextval('feeling_indicator.feeling_seq'::regclass),
  feeling_value       int                             not null,
  ip_address          varchar(255)                    not null,
  feeling_date        timestamp without time zone     not null,
  message             varchar(350),
  created             timestamp without time zone,
  modified            timestamp without time zone,
  CONSTRAINT talk_pkey PRIMARY KEY (id),
  UNIQUE(ip_address, feeling_date)
);