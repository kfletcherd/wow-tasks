-- -----------------------------------------------------
-- Table "task_assignment"
-- -----------------------------------------------------
CREATE TABLE "task_assignment" (
  "toon_id" UUID NOT NULL
	REFERENCES "toon" ("id")
	ON DELETE CASCADE,

  "task_id" UUID NOT NULL
	REFERENCES "task" ("id")
	ON DELETE CASCADE,

  "completed" BOOLEAN NOT NULL,

  PRIMARY KEY ("toon_id", "task_id")
);

CREATE INDEX ON "task_assignment" ("task_id");

