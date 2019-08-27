Create
    schema IF NOT EXISTS stores;

CREATE TABLE Stores.Store
(
    storeId BIGINT AUTO_INCREMENT,
    name    varchar(200) NOT NULL,

    PRIMARY KEY (storeId)
);

CREATE TABLE Stores.Category
(
    categoryId BIGINT AUTO_INCREMENT,
    name       varchar(200) NOT NULL,
    storeId    BIGINT       NOT NULL,
    PRIMARY KEY (categoryId),
    CONSTRAINT store_fk FOREIGN KEY (storeId)
        REFERENCES Store (storeId)
        ON UPDATE CASCADE
);


CREATE TABLE Stores.Product
(
    productId  BIGINT AUTO_INCREMENT,
    price      DECIMAL  (10,2)                                NOT NULL,
    status     ENUM ('Available', 'Absent', 'Expected') NOT NULL,
    name       varchar(200)                             NOT NULL,
    categoryId BigInt                                   NOT NULL,
    storeId    BigInt                                   NOT NULL,
    primary key (productId),
    CONSTRAINT category_fk FOREIGN KEY (categoryId)
        REFERENCES Category (categoryId)
        ON UPDATE CASCADE,

    CONSTRAINT store_fk2 FOREIGN KEY (storeId)
        REFERENCES Store (storeId)
        ON UPDATE CASCADE
);



INSERT INTO Stores.Store (name)
VALUES ('Novus');

INSERT INTO Stores.Category (name, storeId)
VALUES ('Bakery', 1);


INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('11.99', 'Available', 'Ciabatta', 1, 1);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('27.49', 'Available', 'Buckwheat bread', 1, 1);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('18.99', 'Expected', 'Baguette', 1, 1);


INSERT INTO Stores.Category (name, storeId)
VALUES ('Fruits and Vegetables', 1);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('26.99', 'Available', 'Apple', 2, 1);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('68.99', 'Available', 'Pear', 2, 1);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('78.99', 'Expected', 'Kiwi', 2, 1);


INSERT INTO Stores.Store (name)
VALUES ('Fozzy');

INSERT INTO Stores.Category (name, storeId)
VALUES ('Drinks', 2);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('16.99', 'Available', 'Juice', 3, 2);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('18.55', 'Expected', 'Kvass', 3, 2);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('20.99', 'Available', 'Tea', 3, 2);


INSERT INTO Stores.Category (name, storeId)
VALUES ('Frozen', 2);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('50.99', 'Available', 'Fish', 4, 2);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('140.99', 'Available', 'Meat', 4, 2);

INSERT INTO Stores.Product (price, status, name, categoryId, storeId)
VALUES ('72.99', 'Available', 'Pizza', 4, 2);