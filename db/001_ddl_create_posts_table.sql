create table posts (
id SERIAL PRIMARY KEY,
name TEXT,
text TEXT,
link TEXT UNIQUE,
created TIMESTAMP
);