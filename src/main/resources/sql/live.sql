CREATE TABLE "live" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "create_time" DATE,
  "update_time" DATE,
  "name" TEXT,
  "room_id" INTEGER,
  "title" TEXT,
  "cover" TEXT,
  "tags" TEXT,
  "title_template" TEXT,
  "copyright" integer,
  "desc" TEXT,
  "part_title_template" TEXT,
  "upload" integer,
  "user_id" text,
  "tid" INTEGER
);
