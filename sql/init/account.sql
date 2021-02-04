-- -----------------------------------------------------
-- Table "account"
-- -----------------------------------------------------
CREATE TABLE "account" (
  "id" UUID NOT NULL DEFAULT uuid_generate_v4(),
  "username" VARCHAR NOT NULL,
  "password" VARCHAR NOT NULL,
  "salt" VARCHAR NOT NULL,
  "token" VARCHAR NULL,
  "expires" DATE NULL,

  PRIMARY KEY ("id")
);

CREATE UNIQUE INDEX ON "account" ( lower("username") );

