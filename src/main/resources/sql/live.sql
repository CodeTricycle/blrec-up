CREATE TABLE IF NOT EXISTS "live" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
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
