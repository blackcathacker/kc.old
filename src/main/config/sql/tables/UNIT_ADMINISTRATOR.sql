CREATE TABLE UNIT_ADMINISTRATOR 
   (	"UNIT_NUMBER" VARCHAR2(8) constraint UNIT_ADMINISTRATORN1 NOT NULL ENABLE, 
	"PERSON_ID" VARCHAR2(10) constraint UNIT_ADMINISTRATORSN2 NOT NULL ENABLE, 
	"UNIT_ADMINISTRATOR_TYPE_CODE" VARCHAR2(3) constraint UNIT_ADMINISTRATORN3 NOT NULL ENABLE, 
	"UPDATE_TIMESTAMP" DATE constraint UNIT_ADMINISTRATORN4 NOT NULL ENABLE, 
	"UPDATE_USER" VARCHAR2(60) constraint UNIT_ADMINISTRATORN5 NOT NULL ENABLE, 
	 "VER_NBR" NUMBER(8,0) DEFAULT 1  constraint  UNIT_ADMINISTRATORN6  NOT NULL ENABLE,
	"OBJ_ID" VARCHAR2(36) DEFAULT SYS_GUID()  constraint  UNIT_ADMINISTRATORN7  NOT NULL ENABLE,
	CONSTRAINT "PK_UNIT_ADMINISTRATOR_KRA" PRIMARY KEY ("UNIT_NUMBER", "PERSON_ID", "UNIT_ADMINISTRATOR_TYPE_CODE") ENABLE
)
/

ALTER TABLE UNIT_ADMINISTRATOR ADD (CONSTRAINT "FK1_UNIT_ADMINISTRATOR_KRA" FOREIGN KEY ("UNIT_NUMBER")
	  REFERENCES "UNIT" ("UNIT_NUMBER") ) 
/ 

 ALTER TABLE UNIT_ADMINISTRATOR ADD (CONSTRAINT "FK2_UNIT_ADMINISTRATOR_KRA" FOREIGN KEY ("UNIT_ADMINISTRATOR_TYPE_CODE")
	  REFERENCES "UNIT_ADMINISTRATOR_TYPE" ("UNIT_ADMINISTRATOR_TYPE_CODE") ) 
/ 

 ALTER TABLE UNIT_ADMINISTRATOR ADD (CONSTRAINT "FK3_UNIT_ADMINISTRATOR_KRA" FOREIGN KEY ("PERSON_ID")
	  REFERENCES "PERSON" ("PERSON_ID") )
/
