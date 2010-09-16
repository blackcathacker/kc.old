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
package org.kuali.kra.irb.actions.responseapproval;

import org.kuali.kra.irb.ProtocolDocument;
import org.kuali.kra.irb.actions.approve.ProtocolApproveBean;
import org.kuali.kra.rule.BusinessRuleInterface;
import org.kuali.kra.rule.event.KraDocumentEventBaseExtension;

/**
 * Encapsulates the response approval event.
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class ProtocolResponseApprovalEvent<T extends BusinessRuleInterface> extends KraDocumentEventBaseExtension {

    private ProtocolApproveBean actionBean;
    
    /**
     * Constructs a ProtocolResponseApprovalEvent.
     * @param document
     * @param actionBean
     */
    public ProtocolResponseApprovalEvent(ProtocolDocument document, ProtocolApproveBean actionBean) {
        super("Approving document " + getDocumentId(document), "", document);
        this.actionBean = actionBean;
    }
    
    public ProtocolApproveBean getProtocolApproveBean() {
        return actionBean;
    }

    @Override
    public BusinessRuleInterface getRule() {
        return new ProtocolResponseApprovalRule();
    }
    
}