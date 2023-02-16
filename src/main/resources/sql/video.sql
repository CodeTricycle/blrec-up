CREATE TABLE IF NOT EXISTS "video" (
  "id" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  "recorde_id" INTEGER,
  "room_id" INTEGER,
  "path" TEXT,
  "p_index" integer,
  "file_open_time" DATE,
  "file_close_time" DATE,
  "url" TEXT,
  "filename" TEXT,
  "complete" TEXT,
  "success" integer
);
