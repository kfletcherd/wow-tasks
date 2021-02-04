-- -----------------------------------------------------
-- Table "category"
-- -----------------------------------------------------
CREATE TABLE "category" (
  "id" UUID NOT NULL DEFAULT uuid_generate_v4(),
  "label" VARCHAR NOT NULL,

  PRIMARY KEY ("id")
);

CREATE UNIQUE INDEX ON "category" ("label");

