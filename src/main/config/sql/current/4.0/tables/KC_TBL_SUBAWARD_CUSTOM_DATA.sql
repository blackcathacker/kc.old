 CREATE TABLE SUBAWARD_CUSTOM_DATA 
   (	SUBAWARD_CUSTOM_DATA_ID NUMBER(8,0), 
	SUBAWARD_ID NUMBER(12,0), 
	CUSTOM_ATTRIBUTE_ID NUMBER(12,0), 
	VALUE VARCHAR2(2000 BYTE), 
	UPDATE_TIMESTAMP DATE, 
	UPDATE_USER VARCHAR2(60 BYTE), 
	VER_NBR NUMBER(8,0) DEFAULT 1, 
	OBJ_ID VARCHAR2(36 BYTE) NOT NULL
  )
/
  ALTER TABLE SUBAWARD_CUSTOM_DATA
  ADD CONSTRAINT SUBAWARD_CUSTOM_DATAP1 PRIMARY KEY (SUBAWARD_CUSTOM_DATA_ID)
/
COMMIT
/
