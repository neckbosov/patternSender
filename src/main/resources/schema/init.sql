create table TEMPLATES
(
    id              int8 primary key auto_increment,
    template_id     varchar(256) unique,
    template_string varchar(1024)
);
create table RECIPIENTS
(
    id  int8 primary key auto_increment,
    url varchar(1024)
);
create table TEMPLATE_RECIPIENT
(
    id           int8 primary key auto_increment,
    template_id  int8 references templates (id),
    recipient_id int8 references recipients (id)
);