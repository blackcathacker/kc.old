<%--
 Copyright 2005-2010 The Kuali Foundation
 
 Licensed under the Educational Community License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.osedu.org/licenses/ECL-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
--%>
<%@ include file="/WEB-INF/jsp/kraTldHeader.jsp"%>

<c:set var="isOpen" value="${KualiForm.auditActivated or KualiForm.actionHelper.openForFollowup}" />

<c:forEach items="${param}" var="par">
    <c:if test="${fn:startsWith(par.key, 'lookupActionAmendRenewProtocol') and fn:startsWith(par.value, 'true')}">
        <c:set var="isOpen" value="true" />
    </c:if>
    <c:if test="${fn:startsWith(par.key, 'lookupActionNotifyIRBProtocol') and fn:startsWith(par.value, 'true')}">
        <c:set var="isOpen" value="true" />
    </c:if>
    <c:if test="${fn:startsWith(par.key, 'lookupActionRequestProtocol') and fn:startsWith(par.value, 'true')}">
        <c:set var="isOpen" value="true" />
    </c:if>
</c:forEach>


<kul:tabTop tabTitle="Request an Action" defaultOpen="${isOpen}" tabErrorKey="">
	<div class="tab-container"  align="center">
		<h3> 
			<span class="subhead-left">Available Actions</span>
			<span class="subhead-right"><kul:help businessObjectClassName="org.kuali.kra.iacuc.actions.IacucProtocolAction" altText="help"/></span>
		</h3>
	
		<c:if test="${KualiForm.document.protocol.active and showActions}">
            <kra-iacuc-action:submitAction />
            <kra-iacuc-action:assignCmtAction />  
            <kra-iacuc-action:withdrawAction />
            <kra-iacuc-action:modifySubmissionAction />            
			<kra-iacuc-action:adminActions />
			<kra-iacuc-action:assignToAgendaAction />
			<kra-iacuc-action:tableAction />
			<kra-iacuc-action:notifyIacucAction />
			
			<kra-iacuc-action:genericAction tabTitle="Remove From Agenda"
                                          bean="${KualiForm.actionHelper.iacucProtocolRemoveFromAgendaBean}"
                                          property="actionHelper.iacucProtocolRemoveFromAgendaBean"
                                          taskName="protocolRemoveFromAgenda"
                                          methodToCall="removeFromAgenda"
                                          canPerformAction="${KualiForm.actionHelper.canRemoveFromAgenda}"
                                          defaultOpen="${false}"/>
			
          	<kra-iacuc-action:approveAction 
          								  tabTitle="Approve Action"
                                          bean="${KualiForm.actionHelper.protocolFullApprovalBean}"
                                          property="actionHelper.protocolFullApprovalBean"
                                          taskName="protocolApprove"
                                          methodToCall="grantFullApproval"
                                          canPerformAction="${KualiForm.actionHelper.canApproveFull}"
                                          defaultOpen="${KualiForm.actionHelper.isApproveOpenForFollowup}" />
          
<%--
            <kra-iacuc-action:assignReviewersAction />
            
            <kra-iacuc-action:grantExemptionAction />
           
            <kra-iacuc-action:approveAction tabTitle="Expedited Approval"
                                          bean="${KualiForm.actionHelper.protocolExpeditedApprovalBean}"
                                          property="actionHelper.protocolExpeditedApprovalBean"
                                          taskName="protocolExpediteApproval"
                                          methodToCall="grantExpeditedApproval"
                                          canPerformAction="${KualiForm.actionHelper.canApproveExpedited}" />
            <kra-iacuc-action:approveAction tabTitle="Response Approval"
                                          bean="${KualiForm.actionHelper.protocolResponseApprovalBean}"
                                          property="actionHelper.protocolResponseApprovalBean"
                                          taskName="protocolResponseApproval"
                                          methodToCall="grantResponseApproval"
                                          canPerformAction="${KualiForm.actionHelper.canApproveResponse}" />
                                          
--%> 
                                         
            <kra-iacuc-action:recordCommitteeDecisionAction />
            
<%--
            <kra-iacuc-action:genericAction tabTitle="Defer Action"
			                              bean="${KualiForm.actionHelper.protocolDeferBean}"
			                              property="actionHelper.protocolDeferBean"
			                              taskName="protocolDefer"
			                              methodToCall="defer"
			                              canPerformAction="${KualiForm.actionHelper.canDefer}" />
--%>		                              


			                              
            <kra-iacuc-action:genericAction tabTitle="Disapprove"
                                          bean="${KualiForm.actionHelper.protocolDisapproveBean}"
                                          property="actionHelper.protocolDisapproveBean"
                                          taskName="protocolDisapprove"
                                          methodToCall="disapproveProtocol"
                                          canPerformAction="${KualiForm.actionHelper.canDisapprove}"
                                          defaultOpen="${KualiForm.actionHelper.isDisapproveOpenForFollowup}"/>
                                          
                                         
            <kra-iacuc-action:genericAction tabTitle="Return for Minor Revisions"
                                          bean="${KualiForm.actionHelper.protocolSMRBean}"
                                          property="actionHelper.protocolSMRBean"
                                          taskName="protocolReturnForSMR"
                                          methodToCall="returnForSMR"
                                          canPerformAction="${KualiForm.actionHelper.canReturnForSMR}"
                                          defaultOpen="${KualiForm.actionHelper.isReturnForSMROpenForFollowup}" />
                                          
            <kra-iacuc-action:genericAction tabTitle="Return for Major Revisions"
                                          bean="${KualiForm.actionHelper.protocolSRRBean}"
                                          property="actionHelper.protocolSRRBean"
                                          taskName="protocolReturnForSRR"
                                          methodToCall="returnForSRR"
                                          canPerformAction="${KualiForm.actionHelper.canReturnForSRR}"
                                          defaultOpen="${KualiForm.actionHelper.isReturnForSRROpenForFollowup}" />
            <kra-iacuc-action:genericAction tabTitle="Return To PI"
                                          bean="${KualiForm.actionHelper.protocolReturnToPIBean}"
                                          property="actionHelper.protocolReturnToPIBean"
                                          taskName="protocolReturnToPI"
                                          methodToCall="returnToPI"
                                          canPerformAction="${KualiForm.actionHelper.canReturnToPI}"/>                                          
            <kra-iacuc-action:genericAction tabTitle="Deactivate"
                                          bean="${KualiForm.actionHelper.iacucProtocolDeactivateBean}"
                                          property="actionHelper.iacucProtocolDeactivateBean"
                                          taskName="iacucProtocolDeactivate"
                                          methodToCall="iacucDeactivate"
                                          canPerformAction="${KualiForm.actionHelper.canIacucDeactivate}" />
            <kra-iacuc-action:genericAction tabTitle="Hold"
                                          bean="${KualiForm.actionHelper.iacucProtocolHoldBean}"
                                          property="actionHelper.iacucProtocolHoldBean"
                                          taskName="iacucProtocolHold"
                                          methodToCall="iacucHold"
                                          canPerformAction="${KualiForm.actionHelper.canHold}" />
            <kra-iacuc-action:genericAction tabTitle="Lift Hold"
                                          bean="${KualiForm.actionHelper.iacucProtocolLiftHoldBean}"
                                          property="actionHelper.iacucProtocolLiftHoldBean"
                                          taskName="iacucProtocolLiftHold"
                                          methodToCall="iacucLiftHold"
                                          canPerformAction="${KualiForm.actionHelper.canLiftHold}" />                                          
                                                                                    
            <kra-iacuc-action:createAmendmentAction />
            <kra-iacuc-action:modifyAmendmentSectionsAction />
            <kra-iacuc-action:makeAdminCorrectionAction />
            <kra-iacuc-action:createRenewalWithAmendmentAction />
            <kra-iacuc-action:createRenewalAction />
                                          
<%--                                           
            <kra-iacuc-action:notifyCommitteeAction />--%>
            <kra-iacuc-action:genericAction tabTitle="IACUC Acknowledgement"
                                          bean="${KualiForm.actionHelper.iacucAcknowledgeBean}"
                                          property="actionHelper.iacucAcknowledgeBean"
                                          taskName="iacucAcknowledgement"
                                          methodToCall="iacucAcknowledgement"
                                          canPerformAction="${KualiForm.actionHelper.canIacucAcknowledge}" />
<%--
            <kra-iacuc-action:requestAction bean="${KualiForm.actionHelper.protocolCloseRequestBean}"
                                          permission="${KualiForm.actionHelper.canRequestClose}"
                                          beanName="protocolCloseRequestBean"
                                          taskName="protocolRequestClose"
                                          actionTypeCode="105" 
                                          tabTitle="Request To Close"/>
--%>                                          
            <kra-iacuc-action:requestAction bean="${KualiForm.actionHelper.iacucProtocolDeactivateRequestBean}"
                                          permission="${KualiForm.actionHelper.canIacucRequestDeactivate}"
                                          taskName="iacucProtocolRequestDeactivate"
                                          beanName="iacucProtocolDeactivateRequestBean"
                                          actionTypeCode="107"
                                          tabTitle="Request to Deactivate"/>
            <kra-iacuc-action:requestAction bean="${KualiForm.actionHelper.iacucProtocolLiftHoldRequestBean}"
                                          permission="${KualiForm.actionHelper.canRequestToLiftHold}"
                                          taskName="iacucProtocolRequestLiftHold"
                                          beanName="iacucProtocolLiftHoldRequestBean"
                                          actionTypeCode="108"
                                          tabTitle="Request to Lift Hold"/>
                                      
            <kra-iacuc-action:requestAction bean="${KualiForm.actionHelper.iacucProtocolSuspendRequestBean}"
                                          permission="${KualiForm.actionHelper.canRequestSuspend}"
                                          beanName="iacucProtocolSuspendRequestBean"
                                          taskName="iacucProtocolRequestSuspension"
                                          actionTypeCode="113"
                                          tabTitle="Request for Suspension"/>
<%--             <kra-iacuc-action:requestAction bean="${KualiForm.actionHelper.protocolTerminateRequestBean}"
                                          permission="${KualiForm.actionHelper.canRequestTerminate}"
                                          beanName="protocolTerminateRequestBean"
                                          taskName="protocolRequestTerminate"
                                          actionTypeCode="104"
                                          tabTitle="Request for Termination"/>
--%>                                          
            <kra-protocol-action:deleteAction attributes="${DataDictionary.IacucProtocolDeleteBean.attributes}"
            							  action="iacucProtocolProtocolActions"/>
<%--            
            <kra-iacuc-action:genericAction tabTitle="Close Enrollment"
                                          bean="${KualiForm.actionHelper.protocolCloseEnrollmentBean}"
                                          property="actionHelper.protocolCloseEnrollmentBean"
                                          taskName="protocolCloseEnrollment"
                                          methodToCall="closeEnrollment"
                                          canPerformAction="${KualiForm.actionHelper.canCloseEnrollment}"
                                          canAddReviewComments="${KualiForm.actionHelper.canAddCloseEnrollmentReviewerComments}" />
            <kra-iacuc-action:genericAction tabTitle="Re-open Enrollment"
                                          bean="${KualiForm.actionHelper.protocolReopenEnrollmentBean}"
                                          property="actionHelper.protocolReopenEnrollmentBean"
                                          taskName="protocolReopen"
                                          methodToCall="reopenEnrollment"
                                          canPerformAction="${KualiForm.actionHelper.canReopenEnrollment}"
                                          canAddReviewComments="${KualiForm.actionHelper.canAddReopenEnrollmentReviewerComments}" />
            <kra-iacuc-action:genericAction tabTitle="Data Analysis Only"
                                          bean="${KualiForm.actionHelper.protocolPermitDataAnalysisBean}"
                                          property="actionHelper.protocolPermitDataAnalysisBean"
                                          taskName="protocolPermitDataAnalysis"
                                          methodToCall="permitDataAnalysis"
                                          canPerformAction="${KualiForm.actionHelper.canPermitDataAnalysis}"
                                          canAddReviewComments="${KualiForm.actionHelper.canAddDataAnalysisReviewerComments}" />
            <kra-iacuc-action:genericAction tabTitle="Suspend"
                                          bean="${KualiForm.actionHelper.protocolSuspendBean}"
                                          property="actionHelper.protocolSuspendBean"
                                          taskName="protocolSuspend"
                                          methodToCall="suspend"
                                          canPerformAction="${KualiForm.actionHelper.canSuspend}"
                                          canAddReviewComments="${KualiForm.actionHelper.canAddSuspendReviewerComments}" />
            <kra-iacuc-action:genericAction tabTitle="Suspend By DSMB"
                                          bean="${KualiForm.actionHelper.protocolSuspendByDsmbBean}"
                                          property="actionHelper.protocolSuspendByDsmbBean"
                                          taskName="protocolSuspendByDsmb"
                                          methodToCall="suspendByDsmb"
                                          canPerformAction="${KualiForm.actionHelper.canSuspendByDsmb}"
                                          canAddReviewComments="false" />
            <kra-iacuc-action:genericAction tabTitle="Close"
                                          bean="${KualiForm.actionHelper.protocolCloseBean}"
                                          property="actionHelper.protocolCloseBean"
                                          taskName="protocolClose"
                                          methodToCall="closeProtocol"
                                          canPerformAction="${KualiForm.actionHelper.canClose}"
                                          canAddReviewComments="${KualiForm.actionHelper.canAddCloseReviewerComments}" />
            
            --%>
            <kra-iacuc-action:genericAction tabTitle="Suspend"
                                          bean="${KualiForm.actionHelper.protocolSuspendBean}"
                                          property="actionHelper.protocolSuspendBean"
                                          taskName="protocolSuspend"
                                          methodToCall="suspend"
                                          canPerformAction="${KualiForm.actionHelper.canSuspend}"
                                          canAddReviewComments="${KualiForm.actionHelper.canAddSuspendReviewerComments}" />
            <kra-iacuc-action:genericAction tabTitle="Expire"
                                          bean="${KualiForm.actionHelper.protocolExpireBean}"
                                          property="actionHelper.protocolExpireBean"
                                          taskName="protocolExpire"
                                          methodToCall="expire"
                                          canPerformAction="${KualiForm.actionHelper.canExpire}" />
           <kra-iacuc-action:genericAction tabTitle="Terminate"
                                          bean="${KualiForm.actionHelper.protocolTerminateBean}"
                                          property="actionHelper.protocolTerminateBean"
                                          taskName="protocolTerminate"
                                          methodToCall="terminate"
                                          canPerformAction="${KualiForm.actionHelper.canTerminate}"
                                          canAddReviewComments="${KualiForm.actionHelper.canAddTerminateReviewerComments}" />
            <kra-iacuc-action:genericAction tabTitle="Abandon"
                                          bean="${KualiForm.actionHelper.protocolAbandonBean}"
                                          property="actionHelper.protocolAbandonBean"
                                          taskName="iacucProtocolAbandon"
                                          methodToCall="abandon"
                                          canPerformAction="${KualiForm.actionHelper.canAbandon}" 
                                          canAddReviewComments="false"/>
                                        
           <%-- <kra-iacuc-action:reviewNotRequiredAction />--%>
            <kra-iacuc-action:undoLastAction />
            
            <kra-iacuc-action:manageReviewComments />
<%--            
            <kra-iacuc-action:manageNotes />
--%>            
		</c:if>		
		<c:if test="${!KualiForm.document.protocol.active and showActions}">		     	     
		    <table cellpadding="0" cellspacing="0" summary="">
				<tr>
					<td>	
						<div align="left">		     	
		   					<p>No actions available for a canceled protocol document. </p>		   					
						</div>
					</td>
				</tr>
			</table> 
		</c:if>    
	</div>


      
    <div class="tab-container"  align="center">
	    <c:if test="${KualiForm.document.protocol.active and showActions}">
    	    <kul:innerTab tabTitle="Unavailable Actions" parentTab="" defaultOpen="false" useCurrentTabIndexAsKey="true" overrideDivClass="innerTab-h3head">
        	    <div class="innerTab-container" align="left">
            	<c:if test="${KualiForm.document.protocol.active and showActions}">
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Submit for Review"
                    	                                     canPerformAction="${KualiForm.actionHelper.canSubmitProtocolUnavailable}" 
                        	                                 reason="Protocol status must be In Progress, Minor Revision Required,
                            	                                     Major Revision Required, Amendment In Progress, Renewal In Progress,
                                	                                 Deferred, or Withdrawn." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Withdraw Protocol"
                    	                                     canPerformAction="${KualiForm.actionHelper.canWithdrawUnavailable}" 
                        	                                 reason="Protocol can not be an amendment or renewal.
                            	                                     <p>
                                	                                 Protocol status must not be Minor Revisions Required or Major 
                                    	                             Revisions Required.
                                        	                         <p>
                                            	                     Protocol submission status must be Submitted To Committee, In Agenda, or Pending.
                                                	                 <p>
                                                    	             Protocol must be enroute in workflow." />
                                                    	             
                    <kra-iacuc-action:genericUnavailableAction tabTitle="Administratively Withdraw Protocol"
                    	                                     canPerformAction="${KualiForm.actionHelper.canAdministrativelyWithdrawUnavailable}" 
                        	                                 reason="Protocol status must be Submitted To IACUC, Tabled or Administratively Incomplete.
                                        	                         <p>
                                            	                     Protocol submission status must be Submitted To Committee, Assigned To Agenda or Tabled if committee selected, 
                                            	                     or Pending if committee not selected." />
                                            	                     
 					<kra-iacuc-action:genericUnavailableAction tabTitle="Administratively Approve Protocol"
                    	                                     canPerformAction="${KualiForm.actionHelper.canAdministrativelyApproveUnavailable}" 
                        	                                 reason="Protocol status must be Submitted To IACUC or Tabled.
                                        	                         <p>
                                        	                         Protocol review type must be Administrative Review.
                                        	                         <p>
                                            	                     Protocol submission status must be Submitted To Committee, Assigned To Agenda or Tabled if committee selected, 
                                            	                     or Pending if committee not selected." />
                                            	                     
					<kra-iacuc-action:genericUnavailableAction tabTitle="Administratively Mark Incomplete Protocol"
                    	                                     canPerformAction="${KualiForm.actionHelper.canAdministrativelyMarkIncompleteUnavailable}" 
                        	                                 reason="Protocol status must be Submitted To IACUC or Tabled.
                                        	                         <p>
                                        	                         Protocol review type must be Administrative Review.
                                        	                         <p>
                                            	                     Protocol submission status must be Submitted To Committee, Assigned To Agenda or Tabled if committee selected, 
                                            	                     or Pending if committee not selected." />                                                    	                                               	                     
                                                    	                                             	             
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Modify Submission Request"
    	                                                     canPerformAction="${KualiForm.actionHelper.canModifyProtocolSubmissionUnavailable}"
        	                                                 reason="Protocol status must be Submitted To IACUC.
																	 <p>
                                            	                     Protocol submission status must be Pending or Submitted To Committee." />
	        	    <kra-iacuc-action:genericUnavailableAction tabTitle="Assign to Committee"
                	                                         canPerformAction="${KualiForm.actionHelper.canAssignCmtUnavailable}" 
                    	                                     reason="Protocol submission status must be Pending or Submitted To Committee.
                        	                                         <p>
                            	                                     Protocol must be enroute in workflow." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Assign Reviewers"
    	                                                     canPerformAction="${KualiForm.actionHelper.canAssignReviewersUnavailable}" 
        	                                                 reason="Protocol must be assigned to a committee.
            	                                             		 <p>
                	                                         		 Protocol must be assigned to committee schedule or Protocol review type must be Expedited.
                    	                                             <p>
                        	                                         Protocol submission status must be Pending or Submitted To Committee.
                            	                                     <p>
                                	                                 Protocol must be enroute in workflow." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Assign to Agenda"
    	                                                     canPerformAction="${KualiForm.actionHelper.canAssignToAgendaUnavailable}" 
        	                                                 reason="Protocol must be assigned to a committee and committee schedule.
            	                                                     <p>
                	                                                 Protocol submission status must be Submitted To Committee or Tabled.
                	                                                 <p>
                	                                                 Protocol review type must not be Administrative Review.
                    	                                             <p>
                        	                                         Protocol must be enroute in workflow." />
	        	    <kra-iacuc-action:genericUnavailableAction tabTitle="Table"
                	                                         canPerformAction="${KualiForm.actionHelper.canTableUnavailable}" 
                    	                                     reason="Protocol submission status must be In Agenda.
                        	                                         <p>
                            	                                     Protocol status must be Submitted To IACUC.
                            	                                     <p>
                            	                                     Protocol submission's current committee schedule cannot be the last one, by date." />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Approve Action"
                    	    	                             canPerformAction="${KualiForm.actionHelper.canApproveFullUnavailable}" 
                        	    	                         reason="The last protocol action must have been Record Committee Decision with the motion to approve. " />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Response Approval"
                    	                                     canPerformAction="${KualiForm.actionHelper.canApproveResponseUnavailable}"
                        	                                 reason="Protocol review type must be Response.
                            	                                     <p>
                                	                                 Protocol submission status must be Submitted To Committee or In Agenda." />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Record Committee Decision"
				    	                                     canPerformAction="${KualiForm.actionHelper.canRecordCommitteeDecisionUnavailable}"
				        	                                 reason="Protocol submission status must be In Agenda.
				            	                                     <p>
				            	                                     Protocol review type must not be Administrative Review.
				            	                                     <p>
				                	                                 The last protocol action was not Record Committee Decision." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Disapprove"
					                                         canPerformAction="${KualiForm.actionHelper.canDisapproveUnavailable}"
        	                                                 reason="The last protocol action must have been Record Committee Decision with the motion to disapprove. " />
	        	    <kra-iacuc-action:genericUnavailableAction tabTitle="Return for Minor Revisions"
					                                         canPerformAction="${KualiForm.actionHelper.canReturnForSMRUnavailable}"
                    	                                     reason="The last protocol action must have been Record Committee Decision with the motion to request minor revisions. " />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Return for Major Revisions"
					                                         canPerformAction="${KualiForm.actionHelper.canReturnForSRRUnavailable}"
        	                                                 reason="The last protocol action must have been Record Committee Decision with the motion to request major revisions. " />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Return To PI"
					                                         canPerformAction="${KualiForm.actionHelper.canReturnToPIUnavailable}"
        	                                                 reason="Protocol status must be Submitted To IACUC.
        	                                                 		 <p>
        	                                                 		 Protocol submission status must be Submitted to Committee." />
        	                                                 
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Deactivate"
					                                         canPerformAction="${KualiForm.actionHelper.canIacucRequestDeactivateUnavailable}"
        	                                                 reason="Protocol status must be Active, Administratively Approved, or Active - Hold.
				        	                                         <p>
				            	                                     Protocol submission status must be Submitted to Committee or Pending." />
	        	    <kra-iacuc-action:genericUnavailableAction tabTitle="Notify IACUC"
					                                         canPerformAction="${KualiForm.actionHelper.canNotifyIacucUnavailable}"
				    	                                     reason="Protocol can not be an amendment or renewal.
				        	                                         <p>
				            	                                     Protocol status is In Progress, Submitted To IACUC, Minor Revisions Required, Deferred, Amendment In Progress, Administratively Approved or Renewal In Progress." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Notify Committee"
					                                         canPerformAction="${KualiForm.actionHelper.canNotifyCommitteeUnavailable}"
					                                         reason="Protocol review type must be Expedited.
					                                                 <p>
					                                                 Protocol status is Submitted To IACUC." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Create Amendment"
					                                         canPerformAction="${KualiForm.actionHelper.canCreateAmendmentUnavailable}"
					                                         reason="Protocol can not be an amendment or renewal.
					                                                 <p>
					                                                 Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Administratively Approved, Exempt, Closed Administratively for lack of response, Closed by Investigator, Suspended by Investigator, or Suspended by IACUC." />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Modify Amendment Sections"
				    	                                     canPerformAction="${KualiForm.actionHelper.canModifyAmendmentSectionsUnavailable}"
				        	                                 reason="Protocol must be be an amendment or renewal.
				            	                                     <p>
				                	                                 Protocol status must be Amendment in Progress or Renewal in Progress." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Create Renewal with Amendment"
					                                         canPerformAction="${KualiForm.actionHelper.canCreateRenewalUnavailable && KualiForm.actionHelper.canCreateAmendmentUnavailable}"
					                                         reason="Protocol can not be an amendment or renewal.
					                                                 <p>
					                                                 Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Exempt, Closed Administratively for lack of response, Closed by Investigator, Suspended by Investigator, or Suspended by IACUC." />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Create Renewal without Amendment"
				    	                                     canPerformAction="${KualiForm.actionHelper.canCreateRenewalUnavailable}"
				        	                                 reason="Protocol can not be an amendment or renewal.
				            	                                     <p>
				                	                                 Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Exempt, Closed Administratively for lack of response, Closed by Investigator, Suspended by Investigator, Suspended by IACUC, or Expired." />
<%--
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Request To Close"
		                                                     canPerformAction="${KualiForm.actionHelper.canRequestCloseUnavailable}"
					                                         reason="Protocol can not be an amendment or renewal.
					                                                 <p>
					                                                 Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Exempt, Suspended by Investigator, or Suspended by IACUC.
				    	                                             <p>
				        	                                         Submission Type is Request to Close, Request for Suspension, Request to Close Enrollment, Request for Termination, Request for Data Analysis Only, or Request for Re-open Enrollment." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Request To Deactivate"
		                                                     canPerformAction="${KualiForm.actionHelper.canIacucRequestDeactivateUnavailable}"
					                                         reason="Not sure yet what goes here...
					                                                 <p>
					                                                 Or here." />
--%>
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Request for Termination"
	                	                                     canPerformAction="${KualiForm.actionHelper.canRequestTerminateUnavailable}"
				        	                                 reason="Protocol can not be an amendment or renewal.
				            	                                     <p>
				                	                                 Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Exempt, Closed Administratively for lack of response, Closed by Investigator, Suspended by Investigator, or Suspended by IACUC.
				                    	                             <p>
				                        	                         Submission Type is Request to Close, Request for Suspension, Request to Close Enrollment, Request for Termination, Request for Data Analysis Only, or Request for Re-open Enrollment." />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Delete Protocol, Amendment, or Renewal"
	                	                                     canPerformAction="${KualiForm.actionHelper.canDeleteProtocolAmendRenewUnavailable}"
	                    	                                 reason="Protocol must be editable.
	                        	                                     <p>
	                            	                                 Protocol status must be Pending/In Progress, Amendment in Progress, or Renewal in Progress." />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Make Administrative Correction"
	                	                                     canPerformAction="${KualiForm.actionHelper.canMakeAdminCorrectionUnavailable}"
	                    	                                 reason="Protocol status must be Pending/In Progress, Withdrawn, or Disapproved." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Suspend"
		                                                     canPerformAction="${KualiForm.actionHelper.canSuspendUnavailable}"
					                                         reason="Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Administratively Approved, or Exempt.
					                                                 <p>
					                                                 Submission type is Request for Suspension.
				    	                                             <p>
				        	                                         Submission status can not be Withdrawn.
				            	                                     <p>
				                	                                 - or -
				                    	                             Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, or Exempt.
				                        	                         <p>
					                                                 Protocol must have a pending submission." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Close"
		                                                     canPerformAction="${KualiForm.actionHelper.canCloseUnavailable}"
					                                         reason="Protocol status must be Pending/In Progress, Minor Revisions Required, Major Revisions Required, Amendment in Progress, Renewal in Progress, Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Exempt, Suspended by Investigator, Suspended by IACUC, or Withdrawn.
					                                                 <p>
					                                                 Protocol must have a pending submission.
				    	                                             <p>
				        	                                         - or - 
				            	                                     <p>
				                	                                 Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Exempt, Suspended by Investigator,Suspended by IACUC, Minor Revisions Required, Major Revisions Required, or Withdrawn.
				                    	                             <p>
				                        	                         Submission type must be Request to Close.
				                            	                     <p>
				                                	                 Submission status can not be Withdrawn." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Expire"
		                                                     canPerformAction="${KualiForm.actionHelper.canExpireUnavailable}"
					                                         reason="Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Administratively Approved, Exempt, Closed Administratively for lack of response, Closed by Investigator, Suspended by Investigator, Withdrawn, Suspended by IACUC, or IRB review not required.
					                                                 <p>
					                                                 Protocol must have a pending submission.
				    	                                             <p>
				        	                                         Protocol must have pending amendments or renewals." />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Terminate"
	                	                                     canPerformAction="${KualiForm.actionHelper.canTerminateUnavailable}"
				        	                                 reason="Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Administratively Approved, Exempt, Closed Administratively for lack of response, Closed by Investigator, Suspended by Investigator, Expired, or Suspended by IACUC.
				            	                                     <p>
				                	                                 Protocol must have a pending submission.
				                    	                             <p>
				                        	                         - or - 
				                            	                     <p>
				                                	                 Protocol status must be Active - Open to Enrollment, Active - Closed to Enrollment, Active - Data Analysis Only, Exempt, Closed Administratively for lack of response, Closed by Investigator, Suspended by Investigator, Expired, or Suspended by IACUC.
				                                    	             <p>
				                                        	         Protocol submission type must be Request for Termination.
				                                            	     <p>
				                                                	 Protocol submission status can not be Withdrawn. " />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Review Not Required"
		                                                     canPerformAction="${KualiForm.actionHelper.canReviewNotRequiredUnavailable}"
	    	                                                 reason="Protocol Status is Submitted to IACUC.
	        	                                                     <p>
	            	                                                 Protocol review type is IRB Review not required" />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Undo Last Action"
		                                                     canPerformAction="${KualiForm.actionHelper.canUndoLastActionUnavailable}"
	    	                                                 reason="No action to undo." />
	        	    <kra-iacuc-action:genericUnavailableAction tabTitle="Manage Review Comments"
	            	                                         canPerformAction="${KualiForm.actionHelper.canManageReviewCommentsUnavailable}"
	                	                                     reason="Protocol Status is Submitted to IACUC, Minor Revisions Required, Major Revisions Required, Tabled.
	                    	                                         <p>
	                        	                                     Protocol must be enroute in workflow." />
		            <kra-iacuc-action:genericUnavailableAction tabTitle="Manage Notes"
		                                                     canPerformAction="${KualiForm.actionHelper.canManageNotesUnavailable}"
	    	                                                 reason="" />
	        	    <kra-iacuc-action:genericUnavailableAction tabTitle="Abandon"
	            	                                         canPerformAction="${!KualiForm.actionHelper.canAbandon}"
				    	                                     reason="Protocol status must be SMR or SRR.
				        	                                         <p>
				            	                                     Protocol must be the initial Protocol.
				                	                                 <p>
				                    	                             Only PI can perform this action." />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="IACUC Acknowledgement"
				                                         canPerformAction="${KualiForm.actionHelper.canIacucAcknowledgeUnavailable}"
				                                         reason="Submission status must be Submitted To Committee or In Agenda.
				                                         		 <p>
				                                         		 Last Submission must be Notify IACUC or administrative approval." />
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Hold"
				                                         canPerformAction="${KualiForm.actionHelper.canHoldUnavailable}"
				                                         reason="Protocol status must be Active."/>
	            	<kra-iacuc-action:genericUnavailableAction tabTitle="Lift Hold"
				                                         canPerformAction="${KualiForm.actionHelper.canLiftHoldUnavailable}"
				                                         reason="Protocol status must be Active - On Hold."/>
				                                         
				                                         		 				                    	                             
		    	</c:if>		   	
		   	</div>
        </kul:innerTab>
    </c:if>       
    </div>
    <c:if test="${!KualiForm.document.protocol.active and showActions}">
    	<div class="tab-container"  align="center">
			<h3> 
				<span class="subhead-left">Unavailable Actions</span>					
			</h3>  	     
			<table cellpadding="0" cellspacing="0" summary="">
				<tr>
					<td>	
						<div align="left">   			     	
			   				<p>No actions available for a canceled protocol document. </p>			   					
						</div>
					</td>
				</tr>
			</table> 
		</div>
	</c:if>
</kul:tabTop>