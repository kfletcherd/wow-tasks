-- -----------------------------------------------------
-- Table "task"
-- -----------------------------------------------------
CREATE TABLE "task" (
  "id" UUID NOT NULL DEFAULT uuid_generate_v4(),
  "label" VARCHAR NOT NULL,
  "description" VARCHAR NULL,
  "image" VARCHAR NULL,
  "external_link" VARCHAR NULL,

  PRIMARY KEY ("id")
);

