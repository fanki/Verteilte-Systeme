-- Table for Authors
CREATE TABLE Author (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    biography TEXT,
    birthdate DATE,
    email VARCHAR(255) UNIQUE
);

-- Table for Blogs
CREATE TABLE Blog (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    published_date DATE,
    author_id BIGINT NOT NULL,
    category VARCHAR(100),
    FOREIGN KEY (author_id) REFERENCES Author(id)
);

-- Table for Comments
CREATE TABLE Comment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    content TEXT NOT NULL,
    timestamp TIMESTAMP,
    blog_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    FOREIGN KEY (blog_id) REFERENCES Blog(id),
    FOREIGN KEY (author_id) REFERENCES Author(id)
);

-- Table for Tags
CREATE TABLE Tag (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

-- Table for Blog_Tags (Many-to-Many relationship between Blogs and Tags)
CREATE TABLE Blog_Tags (
    blog_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (blog_id, tag_id),
    FOREIGN KEY (blog_id) REFERENCES Blog(id),
    FOREIGN KEY (tag_id) REFERENCES Tag(id)
);
