-- ============================================================
-- we_commerce database schema
-- ============================================================

-- Users
CREATE TABLE IF NOT EXISTS user_tb (
    id               SERIAL PRIMARY KEY,
    name             VARCHAR(255),
    email            VARCHAR(255),
    password         VARCHAR(255),
    phone_number     VARCHAR(255),
    profile_photo    VARCHAR(255),
    status           BOOLEAN DEFAULT FALSE,
    google_link      VARCHAR(255),
    map_link         VARCHAR(255),
    create_date      TIMESTAMP DEFAULT NOW(),
    address          VARCHAR(255),
    role             VARCHAR(50),
    created_by       VARCHAR(255),
    modified_by      VARCHAR(255),
    created_at       TIMESTAMP,
    modified_at      TIMESTAMP
);

-- JWT Tokens
CREATE TABLE IF NOT EXISTS token (
    id           SERIAL PRIMARY KEY,
    token        VARCHAR(512) UNIQUE,
    token_type   VARCHAR(50) DEFAULT 'BEARER',
    revoked      BOOLEAN NOT NULL DEFAULT FALSE,
    expired      BOOLEAN NOT NULL DEFAULT FALSE,
    user_id      INTEGER REFERENCES user_tb(id) ON DELETE CASCADE
);

-- OTP / PIN Code
CREATE TABLE IF NOT EXISTS code_tb (
    id           BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR(255),
    pin_code     VARCHAR(255),
    created_date TIMESTAMP DEFAULT NOW(),
    expire_date  TIMESTAMP NOT NULL,
    user_id      INTEGER REFERENCES user_tb(id) ON DELETE CASCADE
);

-- Credentials (OAuth)
CREATE TABLE IF NOT EXISTS credential_tb (
    id            BIGSERIAL PRIMARY KEY,
    client_id     VARCHAR(255),
    client_secret VARCHAR(255),
    user_id       INTEGER REFERENCES user_tb(id) ON DELETE CASCADE
);

-- Device Tokens (Push Notifications)
CREATE TABLE IF NOT EXISTS device_token_tb (
    id           BIGSERIAL PRIMARY KEY,
    device_token VARCHAR(512),
    user_id      INTEGER REFERENCES user_tb(id) ON DELETE CASCADE
);

-- Password
CREATE TABLE IF NOT EXISTS password_tb (
    id            BIGSERIAL PRIMARY KEY NOT NULL,
    original_pass VARCHAR(255),
    created_date  TIMESTAMP,
    user_id       INTEGER REFERENCES user_tb(id) ON DELETE CASCADE
);

-- Categories
CREATE TABLE IF NOT EXISTS category_tb (
    id            BIGSERIAL PRIMARY KEY NOT NULL,
    category_name VARCHAR(255) NOT NULL
);

-- Products
CREATE TABLE IF NOT EXISTS product_tb (
    id               BIGSERIAL PRIMARY KEY,
    is_hide          BOOLEAN DEFAULT FALSE,
    title            VARCHAR(255) NOT NULL,
    description      VARCHAR(550),
    price            DOUBLE PRECISION NOT NULL,
    total_amount     DOUBLE PRECISION,
    status           VARCHAR(255) NOT NULL,
    discount_values  DOUBLE PRECISION,
    discount_type    BOOLEAN,
    created_date     TIMESTAMP DEFAULT NOW(),
    condition        VARCHAR(255),
    brand            VARCHAR(255),
    model            VARCHAR(255),
    color            VARCHAR(255),
    year             VARCHAR(50),
    size             VARCHAR(50),
    type             VARCHAR(50),
    user_id          INTEGER REFERENCES user_tb(id) ON DELETE SET NULL,
    category_id      BIGINT REFERENCES category_tb(id) ON DELETE SET NULL,
    created_by       VARCHAR(255),
    modified_by      VARCHAR(255),
    created_at       TIMESTAMP,
    modified_at      TIMESTAMP
);

-- Product Photos
CREATE TABLE IF NOT EXISTS photo_tb (
    id         BIGSERIAL PRIMARY KEY NOT NULL,
    photo      VARCHAR(512) NOT NULL,
    product_id BIGINT REFERENCES product_tb(id) ON DELETE CASCADE
);

-- Product View Count
CREATE TABLE IF NOT EXISTS product_view_count_tb (
    id             BIGSERIAL PRIMARY KEY NOT NULL,
    count          INTEGER NOT NULL,
    last_view_date TIMESTAMP DEFAULT NOW(),
    product_id     BIGINT REFERENCES product_tb(id) ON DELETE CASCADE
);

-- Files
CREATE TABLE IF NOT EXISTS file_tb (
    id       BIGSERIAL PRIMARY KEY,
    url      VARCHAR(512),
    filename VARCHAR(255)
);

-- Collections (Bookmark folders)
CREATE TABLE IF NOT EXISTS collection_tb (
    id           BIGSERIAL PRIMARY KEY,
    name         VARCHAR(255),
    created_date TIMESTAMP DEFAULT NOW(),
    user_id      INTEGER REFERENCES user_tb(id) ON DELETE CASCADE
);

-- Bookmarks
CREATE TABLE IF NOT EXISTS bookmark_tb (
    id            BIGSERIAL PRIMARY KEY,
    status        VARCHAR(255) NOT NULL,
    crated_date   TIMESTAMP DEFAULT NOW(),
    user_id       INTEGER REFERENCES user_tb(id) ON DELETE CASCADE,
    product_id    BIGINT REFERENCES product_tb(id) ON DELETE CASCADE,
    collection_id BIGINT REFERENCES collection_tb(id) ON DELETE SET NULL
);

-- Delivery Addresses
CREATE TABLE IF NOT EXISTS address_delivery_tb (
    id        BIGSERIAL PRIMARY KEY,
    lable     VARCHAR(255),
    contact   VARCHAR(255),
    telephone VARCHAR(255),
    address   VARCHAR(255),
    detail    VARCHAR(255),
    user_id   INTEGER REFERENCES user_tb(id) ON DELETE CASCADE
);

-- Receipts
CREATE TABLE IF NOT EXISTS reciept_tb (
    id          BIGSERIAL PRIMARY KEY,
    reference   VARCHAR(255),
    paid_by     VARCHAR(255),
    paid_date   TIMESTAMP,
    purchase_id BIGINT
);

-- Purchase Details
CREATE TABLE IF NOT EXISTS purchase_detail_tb (
    id                  BIGSERIAL PRIMARY KEY,
    payment_method      BOOLEAN DEFAULT FALSE,
    created_date        TIMESTAMP DEFAULT NOW(),
    remark              VARCHAR(255),
    user_id             INTEGER REFERENCES user_tb(id) ON DELETE SET NULL,
    product_id          BIGINT REFERENCES product_tb(id) ON DELETE SET NULL,
    address_delivery_id BIGINT REFERENCES address_delivery_tb(id) ON DELETE SET NULL
);

-- Add FK from reciept_tb -> purchase_detail_tb (after both tables exist)
ALTER TABLE reciept_tb
    ADD CONSTRAINT fk_reciept_purchase
    FOREIGN KEY (purchase_id) REFERENCES purchase_detail_tb(id) ON DELETE SET NULL;

-- Notifications
CREATE TABLE IF NOT EXISTS notification_tb (
    id           BIGSERIAL PRIMARY KEY,
    description  VARCHAR(255),
    contanct     VARCHAR(255),
    is_read      BOOLEAN DEFAULT FALSE,
    created_date TIMESTAMP DEFAULT NOW(),
    sender_id    INTEGER REFERENCES user_tb(id) ON DELETE SET NULL,
    receiver_id  INTEGER REFERENCES user_tb(id) ON DELETE SET NULL,
    reciept_id   BIGINT REFERENCES reciept_tb(id) ON DELETE SET NULL
);
