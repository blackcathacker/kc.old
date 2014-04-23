/*
 * Copyright 2005-2014 The Kuali Foundation
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
package org.kuali.kra.s2s.service;

import java.util.List;

import org.kuali.coeus.propdev.impl.core.DevelopmentProposal;
import org.kuali.coeus.propdev.impl.s2s.S2sUserAttachedForm;

public interface S2SUserAttachedFormService {
    public List<S2sUserAttachedForm> extractNSaveUserAttachedForms(DevelopmentProposal developmentProposal, S2sUserAttachedForm s2sUserAttachedForm) throws Exception;

    public void resetFormAvailability(DevelopmentProposal developmentProposal, String namespace);
}
