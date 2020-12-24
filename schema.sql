create table _5_step
(
    id          serial                    not null
        constraint _5_step_pkey
        primary key,
    steps_count integer                   not null,
    device_id   varchar(255)              not null,
    "createdAt" bigint default 1608848811 not null,
    "updatedAt" bigint default 1608848811 not null
);

alter table _5_step owner to monitoring_user;

create table _5_damping_angle
(
    id          serial                    not null
        constraint _5_damping_angle_pkey
        primary key,
    angle       integer                   not null,
    device_id   varchar(255)              not null,
    "createdAt" bigint default 1608848811 not null,
    "updatedAt" bigint default 1608848811 not null
);

alter table _5_damping_angle owner to monitoring_user;

create table _5_word_analyze
(
    id             serial                    not null
        constraint _5_word_analyze_pkey
        primary key,
    required_world varchar(255)              not null,
    received_world varchar(255)              not null,
    is_exact       boolean                   not null,
    has_space      boolean                   not null,
    device_id      varchar(255)              not null,
    "createdAt"    bigint default 1608848811 not null,
    "updatedAt"    bigint default 1608848811 not null
);

alter table _5_word_analyze owner to monitoring_user;

