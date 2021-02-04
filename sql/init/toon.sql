-- -----------------------------------------------------
-- Table "toon"
-- -----------------------------------------------------
CREATE TABLE "toon" (
  "id" UUID NOT NULL DEFAULT uuid_generate_v4(),
  "account_id" UUID NOT NULL REFERENCES "account" ("id"),
  "name" VARCHAR NOT NULL,
  "class" VARCHAR NOT NULL,
  "image" VARCHAR NULL,

  PRIMARY KEY ("id")
);

