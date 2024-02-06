CREATE TABLE IF NOT EXISTS USERS (
    id UUID PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    password VARCHAR(255),
    username VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    phoneNumber VARCHAR(255) UNIQUE,
    date_joined DATE
);
CREATE TABLE IF NOT EXISTS ADMIN (
    id UUID PRIMARY KEY,
    user_name VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    role VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS AUTHOR (
    id UUID PRIMARY KEY,
    author_name VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    accountStatus BOOLEAN,
    username VARCHAR(255),
    password VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS ARTICLE (
    article_id UUID PRIMARY KEY,
    date_posted TIMESTAMP,
    article_title VARCHAR(255),
    article_content TEXT,
    comment_status BOOLEAN,
    active BOOLEAN,
    view_count BIGINT,
    author_id UUID,
    FOREIGN KEY (author_id) REFERENCES AUTHOR(id)
);
CREATE TABLE IF NOT EXISTS CATEGORY (
    category_id UUID PRIMARY KEY,
    category_name VARCHAR(255) UNIQUE,
    category_desc VARCHAR(255)
);
 CREATE TABLE IF NOT EXISTS COMMENT (
     id UUID PRIMARY KEY,
     comment_text VARCHAR(255),
     user_id UUID,
     article_id UUID,
     comment_status BOOLEAN,
     comment_date DATE,
     FOREIGN KEY (user_id) REFERENCES USERS(id),
     FOREIGN KEY (article_id) REFERENCES ARTICLE(article_id)
 );
CREATE TABLE IF NOT EXISTS TAG (
    tag_id UUID PRIMARY KEY,
    tag_name VARCHAR(255) UNIQUE
);
CREATE TABLE IF NOT EXISTS tag_article_map_table (
    tag_id UUID,
    article_id UUID,
    PRIMARY KEY (tag_id, article_id),
    FOREIGN KEY (tag_id) REFERENCES tag (tag_id),
    FOREIGN KEY (article_id) REFERENCES article (article_id)
);
CREATE TABLE IF NOT EXISTS category_article_map_table (
    category_id UUID,
    article_id UUID,
    PRIMARY KEY (category_id, article_id),
    FOREIGN KEY (category_id) REFERENCES category (category_id),
    FOREIGN KEY (article_id) REFERENCES article (article_id)
);