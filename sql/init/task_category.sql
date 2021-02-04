-- -----------------------------------------------------
-- Table "task_category"
-- -----------------------------------------------------
CREATE TABLE "task_category" (
  "task_id" UUID NOT NULL
	REFERENCES "task" ("id")
	ON DELETE CASCADE,

  "category_id" UUID NOT NULL
	REFERENCES "category" ("id")
	ON DELETE CASCADE,

  PRIMARY KEY ("task_id", "category_id")
);

CREATE INDEX ON "task_category" ("category_id");

