create table if not exists user_db
(
    id_user serial,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    UNIQUE(email),
    PRIMARY KEY (id_user)
);

create table if not exists action_type
(
    id_action serial,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_action)
);

create table if not exists model
(
    id_model serial,
    model_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_model)
);

create table if not exists location
(
    id_location serial,
    sector_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_location)
);

create table if not exists item_type
(
    id_itemtype serial,
    type_name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_itemtype)
);

create table if not exists item
(
    id_item serial,
    barcode VARCHAR(10000) NOT NULL,
    amount INT NOT NULL,
    id_model INT NOT NULL,
    id_location INT NOT NULL,
    id_itemtype INT NOT NULL,
    PRIMARY KEY (id_item),
    FOREIGN KEY (id_model) REFERENCES model(id_model),
    FOREIGN KEY (id_location) REFERENCES location(id_location),
    FOREIGN KEY (id_itemtype) REFERENCES item_type(id_itemtype)
);

create table if not exists transaction
(
    id_transaction serial,
    time TIMESTAMP NOT NULL,
    location VARCHAR(255) NOT NULL,
    amount INT NOT NULL,
    id_action INT NOT NULL,
    id_user INT NOT NULL,
    id_item INT NOT NULL,
    PRIMARY KEY (id_transaction, id_action),
    FOREIGN KEY (id_action) REFERENCES action_type(id_action),
    FOREIGN KEY (id_user) REFERENCES user_db(id_user),
    FOREIGN KEY (id_item) REFERENCES item(id_item)
);

