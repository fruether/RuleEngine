CREATE TABLE "PERSON" (
	"PERSON_ID" INTEGER NOT NULL ,
	"DEFAULT_GROUP_ID" INTEGER,
	"GIVENNAME" VARCHAR2 (50),
	"SURNAME" VARCHAR2 (50) NOT NULL ,
	"DATE_OF_BIRTH" DATE,
	"GENDER" CHAR (1) NOT NULL  CONSTRAINT "FILLED_GENDER" CHECK (GENDER = 'M' OR GENDER='F' ) ,
	"EMAIL" VARCHAR2 (50) CONSTRAINT "UNIQUE_EMAIL" UNIQUE,
	"PHONE_NUMBER" VARCHAR2 (20),
	"NOTE" VARCHAR2 (255),
	"USERNAME" VARCHAR2 (50) CONSTRAINT "UNIQUE_USERNAME" UNIQUE ,
	"PASSWORD" VARCHAR2 (50),
	"AUTHORITY" VARCHAR2 (50),
	"CONFIRMED" INTEGER DEFAULT 0,
	"AUTHENTICATION" VARCHAR2 (50),
	"REGISTRATION_DATE" DATE,
PRIMARY KEY ("PERSON_ID")
)
;
