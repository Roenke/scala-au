# --- !Ups

CREATE INDEX note_user_ix on "Note"("user");

# --- !Downs

DROP INDEX note_user_ix;
