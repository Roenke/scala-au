# --- !Ups

CREATE TABLE "User" (
  "id"    INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  "name"  TEXT               NOT NULL,
  "login" TEXT               NOT NULL,
  "passw" TEXT               NOT NULL
);

CREATE TABLE "Note" (
  "id"                INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
  "user"              INT                NOT NULL REFERENCES "User" ("id"),
  "creation_date"     DATETIME           NOT NULL,
  "modification_date" DATETIME           NOT NULL,
  "header"            TEXT               NOT NULL,
  "text"              TEXT               NOT NULL,
  "isDeleted"         BOOLEAN            NOT NULL
);

# --- !Downs

DROP TABLE "Note";
DROP TABLE "User";
