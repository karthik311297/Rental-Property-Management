create table occupancy (occupied_date timestamp not null, userid binary(255) not null, propertyid binary(255) not null, primary key (propertyid, userid));
create table property (propertyid binary(255) not null, address varchar(255) not null, city varchar(255) not null, name varchar(255) not null, rent integer not null, userid binary(255), primary key (propertyid));
create table rent_occupancykyc (userid binary(255) not null, propertyid binary(255) not null, primary key (propertyid, userid));
create table user (userid binary(255) not null, date_of_birth timestamp not null, email_id varchar(255) not null, name varchar(255) not null, phone_number varchar(13) not null, primary key (userid));
alter table occupancy add constraint UK_59i1c6nkcdlv1jktypjg2ie9k unique (userid);
alter table occupancy add constraint UK_fye3kibtu3n4q7mn0wro8kbp9 unique (propertyid);
alter table rent_occupancykyc add constraint UK_l02jnf90xoypig54i0aif6k62 unique (userid);
alter table rent_occupancykyc add constraint UK_dfdtohmi6w7qomnuwoxuttni6 unique (propertyid);
alter table occupancy add constraint FKskxiqyc0ca3y7b9apuxv6got foreign key (userid) references user;
alter table occupancy add constraint FKhplti0xls60pi88cvo2q7t229 foreign key (propertyid) references property;
alter table property add constraint FKekw0cmw5i892wd0yqxjvc393k foreign key (userid) references user;
alter table rent_occupancykyc add constraint FKgs1mlj94evdcy1xmecvgw28fr foreign key (userid) references user;
alter table rent_occupancykyc add constraint FKn41ym8psoo05wg3s9vl89gv8x foreign key (propertyid) references property;