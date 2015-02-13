--
-- Kuali Coeus, a comprehensive research administration system for higher education.
-- 
-- Copyright 2005-2015 Kuali, Inc.
-- 
-- This program is free software: you can redistribute it and/or modify
-- it under the terms of the GNU Affero General Public License as
-- published by the Free Software Foundation, either version 3 of the
-- License, or (at your option) any later version.
-- 
-- This program is distributed in the hope that it will be useful,
-- but WITHOUT ANY WARRANTY; without even the implied warranty of
-- MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
-- GNU Affero General Public License for more details.
-- 
-- You should have received a copy of the GNU Affero General Public License
-- along with this program.  If not, see <http://www.gnu.org/licenses/>.
--

DELIMITER /
ALTER TABLE IACUC_PROTO_STUDY_GROUP_LOCS 
ADD CONSTRAINT FK_IACUC_PROTO_STUDY_LOC_TYPE 
FOREIGN KEY (LOCATION_TYPE_CODE) 
REFERENCES IACUC_LOCATION_TYPE (LOCATION_TYPE_CODE)
/

ALTER TABLE IACUC_PROTO_STUDY_GROUP_LOCS 
ADD CONSTRAINT FK_IACUC_PROTO_STUDY_GROUP_LOC 
FOREIGN KEY (LOCATION_ID) 
REFERENCES IACUC_LOCATION_NAME (LOCATION_ID)
/

ALTER TABLE IACUC_PROTO_STUDY_GROUP_LOCS 
ADD CONSTRAINT FK_IACUC_PROTOCOL_STUDY_GROUP 
FOREIGN KEY (PROTOCOL_ID) 
REFERENCES IACUC_PROTOCOL (PROTOCOL_ID)
/


DELIMITER ;
