insert into KRNS_PARM_T (appl_nmspc_cd,NMSPC_CD,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,PARM_DESC_TXT,CONS_CD,OBJ_ID)  values ('KC','KC-PD','Document','STIPEND_COST_ELEMENTS','CONFG','400315','STIPEND_COST_ELEMENTS used in Phs fellowship Supplemental s2s form','A',sys_guid()); 
insert into KRNS_PARM_T (appl_nmspc_cd,NMSPC_CD,PARM_DTL_TYP_CD,PARM_NM,PARM_TYP_CD,TXT,PARM_DESC_TXT,CONS_CD,OBJ_ID)  values ('KC','KC-PD','Document','TUITION_COST_ELEMENTS','CONFG','422311','TUITION_COST_ELEMENTS used in Phs fellowship Supplemental s2s form','A',sys_guid()); 
update KRNS_PARM_T set PARM_NM = 'IRB_DISPLAY_REVIEWER_NAME_TO_OTHER_PROTOCOL_PERSONNEL' where PARM_NM = 'IRB_DISPLAY_REVIEWER_NAME_TO_OTHERS';

commit;