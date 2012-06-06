/*
 * Copyright 2005-2010 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.kra.iacuc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kuali.kra.common.permissions.Permissionable;
import org.kuali.kra.iacuc.actions.IacucProtocolStatus;
import org.kuali.kra.iacuc.actions.submit.IacucProtocolSubmission;
import org.kuali.kra.iacuc.actions.submit.IacucProtocolSubmissionStatus;
import org.kuali.kra.iacuc.actions.submit.IacucProtocolSubmissionType;
import org.kuali.kra.iacuc.customdata.IacucProtocolCustomData;
import org.kuali.kra.iacuc.noteattachment.IacucProtocolAttachmentFilter;
import org.kuali.kra.iacuc.personnel.IacucProtocolPersonnelService;
import org.kuali.kra.iacuc.protocol.IacucProtocolProjectType;
import org.kuali.kra.iacuc.protocol.research.IacucProtocolResearchArea;
import org.kuali.kra.iacuc.questionnaire.IacucProtocolModuleQuestionnaireBean;
import org.kuali.kra.iacuc.species.IacucProtocolSpecies;
import org.kuali.kra.iacuc.species.exception.IacucProtocolException;
import org.kuali.kra.iacuc.summary.IacucProtocolSummary;
import org.kuali.kra.iacuc.threers.IacucAlternateSearch;
import org.kuali.kra.iacuc.threers.IacucPrinciples;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.infrastructure.KraServiceLocator;
import org.kuali.kra.infrastructure.RoleConstants;
import org.kuali.kra.protocol.Protocol;
import org.kuali.kra.protocol.actions.ProtocolStatus;
import org.kuali.kra.protocol.actions.submit.ProtocolSubmission;
import org.kuali.kra.protocol.actions.submit.ProtocolSubmissionStatus;
import org.kuali.kra.protocol.actions.submit.ProtocolSubmissionType;
import org.kuali.kra.protocol.noteattachment.ProtocolAttachmentFilter;
import org.kuali.kra.protocol.protocol.research.ProtocolResearchArea;
import org.kuali.kra.protocol.summary.ProtocolSummary;
import org.kuali.kra.questionnaire.answer.AnswerHeader;
import org.kuali.kra.questionnaire.answer.ModuleQuestionnaireBean;
import org.kuali.rice.krad.util.GlobalVariables;

/**
 * 
 * This class is Protocol Business Object.
 */
public class IacucProtocol extends Protocol {
    
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 7380281405644745576L;
    
//    private Date applicationDate;
    private boolean isBillable;
    private String layStatement1; 
    private String layStatement2;
    
    private String protocolProjectTypeCode; 
    
    private String overviewTimeline; 
    private String speciesStudyGroupIndicator; 
    private String alternativeSearchIndicator; 
    private String scientificJustifIndicator; 

    private Timestamp createTimestamp;
    private String createUser;
    
    private IacucProtocolProjectType protocolProjectType;
    
    private List<IacucProtocolCustomData> iacucProtocolCustomDataList;

    private List<IacucPrinciples> iacucPrinciples;
    private List<IacucAlternateSearch> iacucAlternateSearches;
      
    private List<IacucProtocolSpecies> iacucProtocolSpeciesList;
    private List<IacucProtocolException> iacucProtocolExceptions;

    // lookup field
    private Integer speciesCode; 
    private Integer exceptionCategoryCode; 

    public IacucProtocol() {         
        setCreateTimestamp(new Timestamp(new java.util.Date().getTime()));
        setCreateUser(GlobalVariables.getUserSession().getPrincipalId());
        setScientificJustifIndicator("no");
        setSpecialReviewIndicator("no");
        setSpeciesStudyGroupIndicator("no");
        setKeyStudyPersonIndicator("no");
        setFundingSourceIndicator("no");
        setCorrespondentIndicator("no");
        setReferenceIndicator("no");
        setAlternativeSearchIndicator("no");
        setIacucProtocolSpeciesList(new ArrayList<IacucProtocolSpecies>());
        setIacucAlternateSearches(new ArrayList<IacucAlternateSearch>());
        setIacucProtocolCustomDataList(new ArrayList<IacucProtocolCustomData>());
        setIacucProtocolExceptions(new ArrayList<IacucProtocolException>());
        
        initIacucPrinciples();
    } 
    
    @SuppressWarnings("unchecked")
    @Override
    public List buildListOfDeletionAwareLists() {
        List managedLists = super.buildListOfDeletionAwareLists();
        managedLists.add(getIacucProtocolSpeciesList());
        managedLists.add(getIacucProtocolCustomDataList());
        managedLists.add(getIacucAlternateSearches());
        managedLists.add(getIacucProtocolExceptions());
        return managedLists;
    }

    public IacucProtocolSubmission getIacucProtocolSubmission() {
        return (IacucProtocolSubmission)getProtocolSubmission();
    }
    
//    public Date getApplicationDate() {
//        return applicationDate;
//    }
//
//    public void setApplicationDate(Date applicationDate) {
//        this.applicationDate = applicationDate;
//    }

    public boolean getIsBillable() {
        return isBillable;
    }

    public void setIsBillable(boolean isBillable) {
        this.isBillable = isBillable;
    }
    
    public String getLayStatement1() {
        return layStatement1;
    }

    public void setLayStatement1(String layStatement1) {
        this.layStatement1 = layStatement1;
    }

    public String getLayStatement2() {
        return layStatement2;
    }

    public void setLayStatement2(String layStatement2) {
        this.layStatement2 = layStatement2;
    }

    public String getOverviewTimeline() {
        return overviewTimeline;
    }

    public void setOverviewTimeline(String overviewTimeline) {
        this.overviewTimeline = overviewTimeline;
    }

    public String getSpeciesStudyGroupIndicator() {
        return speciesStudyGroupIndicator;
    }

    public void setSpeciesStudyGroupIndicator(String speciesStudyGroupIndicator) {
        this.speciesStudyGroupIndicator = speciesStudyGroupIndicator;
    }

    public String getAlternativeSearchIndicator() {
        return alternativeSearchIndicator;
    }

    public void setAlternativeSearchIndicator(String alternativeSearchIndicator) {
        this.alternativeSearchIndicator = alternativeSearchIndicator;
    }

    public String getScientificJustifIndicator() {
        return scientificJustifIndicator;
    }

    public void setScientificJustifIndicator(String scientificJustifIndicator) {
        this.scientificJustifIndicator = scientificJustifIndicator;
    }
    
    

    public void setIacucProtocolDocument(IacucProtocolDocument iacucProtocolDocument) {
        this.setProtocolDocument(iacucProtocolDocument);
    }

    public IacucProtocolDocument getIacucProtocolDocument() {
        return (IacucProtocolDocument) this.getProtocolDocument();
    }

    public void setBillable(boolean isBillable) {
        this.isBillable = isBillable;
    }

    public Timestamp getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Timestamp createTimestamp) {
        this.createTimestamp = createTimestamp;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    

    @Override
    // implementation of hook method
    protected IacucProtocolPersonnelService getProtocolPersonnelService() {
        return (IacucProtocolPersonnelService)KraServiceLocator.getService("iacucProtocolPersonnelService");
    }


    @Override
    public String getNamespace() {
        return Constants.MODULE_NAMESPACE_IACUC;
    }


    @Override
    protected String getDefaultProtocolStatusCodeHook() {
        return IacucProtocolStatus.IN_PROGRESS;
    }


    @Override
    protected String getDefaultProtocolTypeCodeHook() {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    protected ProtocolSubmissionStatus getProtocolSubmissionStatusNewInstanceHook() {
        return new IacucProtocolSubmissionStatus();
    }


    @Override
    protected ProtocolSubmissionType getProtocolSubmissionTypeNewInstanceHook() {
        return new IacucProtocolSubmissionType();
    }


    @Override
    protected ProtocolSubmission getProtocolSubmissionNewInstanceHook() {
        return new IacucProtocolSubmission();
    }


    @Override
    protected ProtocolStatus getProtocolStatusNewInstanceHook() {
        return new IacucProtocolStatus();
    }


    @Override
    public String getDocumentRoleTypeCode() {
        return RoleConstants.IACUC_ROLE_TYPE;
    }    

    @Override
    public List<String> getRoleNames() {
      List<String> roleNames = new ArrayList<String>();

      roleNames.add(RoleConstants.IACUC_PROTOCOL_AGGREGATOR);
      roleNames.add(RoleConstants.IACUC_PROTOCOL_VIEWER);

      return roleNames;        
    }


    public List<IacucPrinciples> getIacucPrinciples() {
        return iacucPrinciples;
    }


    public void setIacucPrinciples(List<IacucPrinciples> iacucPrinciples) {
        this.iacucPrinciples = iacucPrinciples;
    }
    
    public List<IacucProtocolSpecies> getIacucProtocolSpeciesList() {
        return iacucProtocolSpeciesList;
    }


    public void setIacucProtocolSpeciesList(List<IacucProtocolSpecies> iacucProtocolSpeciesList) {
        this.iacucProtocolSpeciesList = iacucProtocolSpeciesList;
    }


    @Override
    protected ProtocolResearchArea getNewProtocolResearchAreaInstance() {
        return new IacucProtocolResearchArea();
    }


    public List<IacucAlternateSearch> getIacucAlternateSearches() {
        return iacucAlternateSearches;
    }

    public void setIacucAlternateSearches(List<IacucAlternateSearch> iacucAlternateSearches) {
        this.iacucAlternateSearches = iacucAlternateSearches;
    }

    public void setProtocolProjectTypeCode(String protocolProjectTypeCode) {
        this.protocolProjectTypeCode = protocolProjectTypeCode;
    }

    public String getProtocolProjectTypeCode() {
        return protocolProjectTypeCode;
    }

    public void setProtocolProjectType(IacucProtocolProjectType protocolProjectType) {
        this.protocolProjectType = protocolProjectType;
    }

    public IacucProtocolProjectType getProtocolProjectType() {
        return protocolProjectType;
    }

    public List<IacucProtocolCustomData> getIacucProtocolCustomDataList() {
        return iacucProtocolCustomDataList;
    }

    public void setIacucProtocolCustomDataList(List<IacucProtocolCustomData> iacucProtocolCustomDataList) {
        this.iacucProtocolCustomDataList = iacucProtocolCustomDataList;
    }

    public List<IacucProtocolException> getIacucProtocolExceptions() {
        return iacucProtocolExceptions;
    }

    public void setIacucProtocolExceptions(List<IacucProtocolException> iacucProtocolExceptions) {
        this.iacucProtocolExceptions = iacucProtocolExceptions;
    }    
    
    private void initIacucPrinciples() {
        List<IacucPrinciples> newPrinciples = new ArrayList<IacucPrinciples>();
        IacucPrinciples iPrinciples = new IacucPrinciples();
        newPrinciples.add(iPrinciples);
        setIacucPrinciples(newPrinciples);

    }

    public Integer getSpeciesCode() {
        return speciesCode;
    }

    public void setSpeciesCode(Integer speciesCode) {
        this.speciesCode = speciesCode;
    }

    public Integer getExceptionCategoryCode() {
        return exceptionCategoryCode;
    }

    public void setExceptionCategoryCode(Integer exceptionCategoryCode) {
        this.exceptionCategoryCode = exceptionCategoryCode;
    }

    /*
     * get submit for review questionnaire answerheaders
     */
    protected List <AnswerHeader> getAnswerHeaderForProtocol(Protocol protocol) {
        ModuleQuestionnaireBean moduleQuestionnaireBean = new IacucProtocolModuleQuestionnaireBean(protocol);
        moduleQuestionnaireBean.setModuleSubItemCode("0");
        List <AnswerHeader> answerHeaders = new ArrayList<AnswerHeader>();
        answerHeaders = getQuestionnaireAnswerService().getQuestionnaireAnswer(moduleQuestionnaireBean);
        return answerHeaders;
    }

    @Override
    public void initializeProtocolAttachmentFilter() {
        ProtocolAttachmentFilter protocolAttachmentFilter = new IacucProtocolAttachmentFilter();
  
        //Lets see if there is a default set for the attachment sort
        try {
            String defaultSortBy = getParameterService().getParameterValueAsString(IacucProtocolDocument.class, Constants.PARAMETER_IACUC_PROTOCOL_ATTACHMENT_DEFAULT_SORT);
            if (StringUtils.isNotBlank(defaultSortBy)) {
                protocolAttachmentFilter.setSortBy(defaultSortBy);
            }
        } catch (Exception e) {
            //No default found, do nothing.
        }
        
        setProtocolAttachmentFilter(protocolAttachmentFilter);
    } 
    
    
    public String getDocumentKey() {
        // TODO need to change this to IACUC PROTOCOL KEY!!!!
        return Permissionable.PROTOCOL_KEY;
    }

    public ProtocolSummary getProtocolSummary() {
        ProtocolSummary protocolSummary = createProtocolSummary();
        addPersonnelSummaries(protocolSummary);
        addResearchAreaSummaries(protocolSummary);
        addAttachmentSummaries(protocolSummary);
        addFundingSourceSummaries(protocolSummary);
        addParticipantSummaries(protocolSummary);
        addOrganizationSummaries(protocolSummary);
        addSpecialReviewSummaries(protocolSummary);
        addAdditionalInfoSummary(protocolSummary);
        return protocolSummary;
    }
    
    @Override
    protected ProtocolSummary createProtocolSummary() {
        IacucProtocolSummary summary = new IacucProtocolSummary();
        summary.setLastProtocolAction(getLastProtocolAction());
        summary.setProtocolNumber(getProtocolNumber().toString());
        summary.setPiName(getPrincipalInvestigator().getPersonName());
        summary.setPiProtocolPersonId(getPrincipalInvestigator().getProtocolPersonId());
        summary.setInitialSubmissionDate(getInitialSubmissionDate());
        summary.setApprovalDate(getApprovalDate());
        summary.setLastApprovalDate(getLastApprovalDate());
        summary.setExpirationDate(getExpirationDate());
        if (getProtocolType() == null) {
            refreshReferenceObject("protocolType");
        }
        summary.setType(getProtocolType().getDescription());
        if (getProtocolStatus() == null) {
            refreshReferenceObject("protocolStatus");
        }
        summary.setStatus(getProtocolStatus().getDescription());
        summary.setTitle(getTitle());
        return summary;
    }

}
