/*
 * Copyright 2006-2008 The Kuali Foundation
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
package org.kuali.kra.questionnaire.answer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.kra.infrastructure.Constants;
import org.kuali.kra.questionnaire.Questionnaire;
import org.kuali.kra.questionnaire.QuestionnaireQuestion;
import org.kuali.kra.questionnaire.QuestionnaireUsage;
import org.kuali.rice.kns.service.BusinessObjectService;
import org.kuali.rice.kns.util.ObjectUtils;

import edu.emory.mathcs.backport.java.util.Collections;

/**
 * 
 * This class implemented the questionnaire answer related methods.
 */
public class QuestionnaireAnswerServiceImpl implements QuestionnaireAnswerService {

    private static final String MODULE_ITEM_CODE = "moduleItemCode";
    private static final String MODULE_ITEM_KEY = "moduleItemKey";
    private static final String MODULE_SUB_ITEM_KEY = "moduleSubItemKey";
    private BusinessObjectService businessObjectService;
    private DateFormat dateFormat = new SimpleDateFormat(Constants.DEFAULT_DATE_FORMAT_PATTERN);

    /*
     * Get the questionnaire that is 'final' for the specified module.
     */
    private List<QuestionnaireUsage> getPublishedQuestionnaire(String coeusModule) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MODULE_ITEM_CODE, coeusModule);

        List<QuestionnaireUsage> usages = new ArrayList<QuestionnaireUsage>();
        List<Integer> questionnaireIds = new ArrayList<Integer>();
        List<QuestionnaireUsage> questionnaireUsages = (List<QuestionnaireUsage>) businessObjectService.findMatching(
                QuestionnaireUsage.class, fieldValues);

        // use this sort, to list the higher version before lower version
        if (CollectionUtils.isNotEmpty(questionnaireUsages)) {
            Collections.sort((List<QuestionnaireUsage>) questionnaireUsages);
          //  Collections.reverse((List<QuestionnaireUsage>) questionnaireUsages);
        }

        // the higher version will have higher questionnaireidfk
        for (QuestionnaireUsage questionnaireUsage : questionnaireUsages) {
            if (!questionnaireIds.contains(questionnaireUsage.getQuestionnaire().getQuestionnaireId())) {
                questionnaireIds.add(questionnaireUsage.getQuestionnaire().getQuestionnaireId());
                usages.add(questionnaireUsage);
            }
        }
        return usages;
    }

    /*
     * set up answer headers for the initial load of module questionnaire answers
     */
    private List<AnswerHeader> initAnswerHeaders(ModuleQuestionnaireBean moduleQuestionnaireBean,
            Map<Integer, AnswerHeader> answerHeaderMap) {
        List<AnswerHeader> answerHeaders = new ArrayList<AnswerHeader>();
        for (QuestionnaireUsage questionnaireUsage : getPublishedQuestionnaire(moduleQuestionnaireBean.getModuleItemCode())) {
            if (answerHeaderMap.containsKey(questionnaireUsage.getQuestionnaire().getQuestionnaireId())) {
                answerHeaders.add(answerHeaderMap.get(questionnaireUsage.getQuestionnaire().getQuestionnaireId()));
                if (!questionnaireUsage.getQuestionnaire().getQuestionnaireRefId().equals(
                        answerHeaderMap.get(questionnaireUsage.getQuestionnaire().getQuestionnaireId()).getQuestionnaireRefIdFk())) {
                    answerHeaderMap.get(questionnaireUsage.getQuestionnaire().getQuestionnaireId()).setNewerVersionPublished(true);
                }
            }
            else {
                answerHeaders.add(setupAnswerForQuestionnaire(questionnaireUsage.getQuestionnaire(), moduleQuestionnaireBean));
            }
        }
        return answerHeaders;
    }

    /**
     * 
     * @see org.kuali.kra.questionnaire.answer.QuestionnaireAnswerService#getNewVersionAnswerHeader(org.kuali.kra.questionnaire.answer.ModuleQuestionnaireBean,
     *      org.kuali.kra.questionnaire.Questionnaire)
     */
    public AnswerHeader getNewVersionAnswerHeader(ModuleQuestionnaireBean moduleQuestionnaireBean, Questionnaire questionnaire) {
        AnswerHeader answerHeader = new AnswerHeader();
        List<QuestionnaireUsage> usages = getPublishedQuestionnaire(moduleQuestionnaireBean.getModuleItemCode());
        // TODO : seems already sorted in getPublishedQuestionnaire
//        if (CollectionUtils.isNotEmpty(usages) && usages.size() > 1) {
//            Collections.sort((List<QuestionnaireUsage>) usages);
//            Collections.reverse((List<QuestionnaireUsage>) usages);
//        }
        for (QuestionnaireUsage questionnaireUsage : usages) {
            if (questionnaireUsage.getQuestionnaire().getQuestionnaireId().equals(questionnaire.getQuestionnaireId())
                    && questionnaireUsage.getQuestionnaire().getSequenceNumber() > questionnaire.getSequenceNumber()) {
                answerHeader = setupAnswerForQuestionnaire(questionnaireUsage.getQuestionnaire(), moduleQuestionnaireBean);
            }
        }
        return answerHeader;
    }


    /**
     * 
     * @see org.kuali.kra.questionnaire.answer.QuestionnaireAnswerService#versioningQuestionnaireAnswer(org.kuali.kra.questionnaire.answer.ModuleQuestionnaireBean)
     */
    public List<AnswerHeader> versioningQuestionnaireAnswer(ModuleQuestionnaireBean moduleQuestionnaireBean) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MODULE_ITEM_CODE, moduleQuestionnaireBean.getModuleItemCode());
        fieldValues.put(MODULE_ITEM_KEY, moduleQuestionnaireBean.getModuleItemKey());
        fieldValues.put(MODULE_SUB_ITEM_KEY, moduleQuestionnaireBean.getModuleSubItemKey());
        List<AnswerHeader> newAnswerHeaders = new ArrayList<AnswerHeader>();
        for (AnswerHeader answerHeader : (List<AnswerHeader>) businessObjectService.findMatching(AnswerHeader.class, fieldValues)) {
            AnswerHeader copiedAnswerHeader = (AnswerHeader) ObjectUtils.deepCopy(answerHeader);
            copiedAnswerHeader.setModuleSubItemKey(Integer
                    .toString(Integer.parseInt(moduleQuestionnaireBean.getModuleSubItemKey()) + 1));
            copiedAnswerHeader.setAnswerHeaderId(null);
            for (Answer answer : copiedAnswerHeader.getAnswers()) {
                answer.setId(null);
            }
            newAnswerHeaders.add(copiedAnswerHeader);
        }
        return newAnswerHeaders;
    }

    /**
     * This will be called when 'questionnaire' page is clicked.
     * 
     * @see org.kuali.kra.questionnaire.answer.QuestionnaireAnswerService#getQuestionnaireAnswer(org.kuali.kra.questionnaire.answer.ModuleQuestionnaireBean)
     */
    public List<AnswerHeader> getQuestionnaireAnswer(ModuleQuestionnaireBean moduleQuestionnaireBean) {
        Map<String, String> fieldValues = new HashMap<String, String>();
        fieldValues.put(MODULE_ITEM_CODE, moduleQuestionnaireBean.getModuleItemCode());
        fieldValues.put(MODULE_ITEM_KEY, moduleQuestionnaireBean.getModuleItemKey());
        fieldValues.put(MODULE_SUB_ITEM_KEY, moduleQuestionnaireBean.getModuleSubItemKey());
        Map<Integer, AnswerHeader> answerHeaderMap = new HashMap<Integer, AnswerHeader>();
        for (AnswerHeader answerHeader : (List<AnswerHeader>) businessObjectService.findMatching(AnswerHeader.class, fieldValues)) {
            setupChildAnswerIndicator(answerHeader.getAnswers());
            answerHeaderMap.put(answerHeader.getQuestionnaire().getQuestionnaireId(), answerHeader);
        }

        List<AnswerHeader> answerHeaders = initAnswerHeaders(moduleQuestionnaireBean, answerHeaderMap);
        for (AnswerHeader answerHeader : answerHeaders) {
            answerHeader.setCompleted(isQuestionnaireAnswerComplete(answerHeader.getAnswers()));
        }
        return answerHeaders;

    }

    /**
     * 
     * @see org.kuali.kra.questionnaire.answer.QuestionnaireAnswerService#copyAnswerToNewVersion(org.kuali.kra.questionnaire.answer.AnswerHeader,
     *      org.kuali.kra.questionnaire.answer.AnswerHeader)
     */
    public void copyAnswerToNewVersion(AnswerHeader oldAnswerHeader, AnswerHeader newAnswerHeader) {
        List<List<Answer>> oldParentAnswers = setupParentAnswers(oldAnswerHeader.getAnswers());
        List<List<Answer>> newParentAnswers = setupParentAnswers(newAnswerHeader.getAnswers());
        for (Answer oldAnswer : oldAnswerHeader.getAnswers()) {
            if (oldAnswer.getQuestionnaireQuestion().getParentQuestionNumber() == 0
                    && StringUtils.isNotBlank(oldAnswer.getAnswer())) {
                for (Answer newAnswer : newAnswerHeader.getAnswers()) {
                    if (oldAnswer.getQuestion().getQuestionId().equals(newAnswer.getQuestion().getQuestionId())
                            && newAnswer.getQuestionnaireQuestion().getParentQuestionNumber() == 0
                            && oldAnswer.getQuestion().getQuestionRefId().equals(newAnswer.getQuestion().getQuestionRefId())) {
                        newAnswer.setAnswer(oldAnswer.getAnswer());
                        newAnswer.setMatchedChild("Y");
                        break;
                    }
                }
            }
            else if (oldAnswer.getQuestionnaireQuestion().getParentQuestionNumber() > 0
                    && StringUtils.isNotBlank(oldAnswer.getAnswer())) {
                copyChildAnswer(oldAnswer, oldParentAnswers, newAnswerHeader, newParentAnswers);
            }
        }
        // set up indicator then to empty answer that should not be copied
        setupChildAnswerIndicator(newAnswerHeader.getAnswers());
        for (Answer answer : newAnswerHeader.getAnswers()) {
            if (StringUtils.isNotBlank(answer.getAnswer()) && ("N").equals(answer.getMatchedChild())) {
                answer.setAnswer("");
            }
        }
        newAnswerHeader.setCompleted(isQuestionnaireAnswerComplete(newAnswerHeader.getAnswers()));
    }

    /**
     * 
     * @see org.kuali.kra.questionnaire.answer.QuestionnaireAnswerService#preSave(java.util.List)
     */
    public void preSave(List<AnswerHeader> answerHeaders) {
        for (AnswerHeader answerHeader : answerHeaders) {
            int i = 0;
            for (Answer answer : answerHeader.getAnswers()) {
                if (answer.getQuestion().getMaxAnswers() != null && answer.getQuestion().getMaxAnswers() > 1
                        && answer.getAnswerNumber() == 1) {
                    moveAnswer(answerHeader.getAnswers(), i);
                }
                i++;
            }
            answerHeader.setCompleted(isQuestionnaireAnswerComplete(answerHeader.getAnswers()));
        }
    }

    /*
     * if maxanswer > 1. Then make sure non-blank answers are moved to top of the answer array.
     */
    private void moveAnswer(List<Answer> answers, int index) {
        int i = 0;
        while (i < answers.get(index).getQuestion().getMaxAnswers() - 1) {
            if (StringUtils.isBlank(answers.get(index + i).getAnswer())) {
                int j = i + 1;
                while (j < answers.get(index).getQuestion().getMaxAnswers()) {
                    if (StringUtils.isNotBlank(answers.get(index + j).getAnswer())) {
                        answers.get(index + i).setAnswer(answers.get(index + j).getAnswer());
                        answers.get(index + j).setAnswer("");
                        break;
                    }
                    j++;
                }
            }
            i++;
        }
    }

    /*
     * 
     */
    private void copyChildAnswer(Answer oldAnswer, List<List<Answer>> oldParentAnswers, AnswerHeader newAnswerHeader,
            List<List<Answer>> newParentAnswers) {
        for (Answer newAnswer : newAnswerHeader.getAnswers()) {
            // TODO : the condition is too complicated
            if (oldAnswer.getQuestion().getQuestionId().equals(newAnswer.getQuestion().getQuestionId())
                    && newAnswer.getQuestionnaireQuestion().getParentQuestionNumber() > 0
                    // && oldAnswer.getQuestion().getQuestionRefId().equals(newAnswer.getQuestion().getQuestionRefId())
                    // &&
                    // oldParentAnswers.get(oldAnswer.getQuestionnaireQuestion().getParentQuestionNumber()).get(0).getQuestion().getQuestionRefId()
                    // .equals(newParentAnswers.get(newAnswer.getQuestionnaireQuestion().getParentQuestionNumber()).get(0).getQuestion().getQuestionRefId())
                    && newParentAnswers.get(newAnswer.getQuestionnaireQuestion().getParentQuestionNumber()).get(0)
                            .getMatchedChild().equals("Y") && isSameLevel(oldAnswer, oldParentAnswers, newAnswer, newParentAnswers)) {
                newAnswer.setAnswer(oldAnswer.getAnswer());
                newAnswer.setMatchedChild("Y");
                break;
            }
        }

    }

    /*
     * go up the question answer hierarchy till the first level. make sure all question are matched with same version.
     */
    private boolean isSameLevel(Answer oldAnswer, List<List<Answer>> oldParentAnswers, Answer newAnswer,
            List<List<Answer>> newParentAnswers) {
        boolean questionHierarchyMatched = true;
        Answer oAnswer = oldAnswer;
        Answer nAnswer = newAnswer;
        while (questionHierarchyMatched && oAnswer.getQuestionnaireQuestion().getParentQuestionNumber() > 0
                && nAnswer.getQuestionnaireQuestion().getParentQuestionNumber() > 0) {
            if (!oAnswer.getQuestionRefIdFk().equals(nAnswer.getQuestionRefIdFk())) {
                questionHierarchyMatched = false;
            }
            oAnswer = oldParentAnswers.get(oAnswer.getQuestionnaireQuestion().getParentQuestionNumber()).get(0);
            nAnswer = newParentAnswers.get(nAnswer.getQuestionnaireQuestion().getParentQuestionNumber()).get(0);
        }
        return questionHierarchyMatched && oAnswer.getQuestionnaireQuestion().getParentQuestionNumber() == 0
                && nAnswer.getQuestionnaireQuestion().getParentQuestionNumber() == 0;
    }

    /*
     * set up the parent answer for a child question answer. Parent question may contain multiple answers, so a 'List' type is
     * needed.
     */
    private List<List<Answer>> setupParentAnswers(List<Answer> answers) {
        List<List<Answer>> parentAnswers = initParentAnswers(answers);
        for (Answer answer : answers) {
            parentAnswers.get(answer.getQuestionNumber()).add(answer);
        }
        return parentAnswers;
    }

    /*
     * init parent answers array with empty array
     */
    private List<List<Answer>> initParentAnswers(List<Answer> answers) {
        int maxQuestionNumber = 0;
        List<List<Answer>> parentAnswers = new ArrayList<List<Answer>>();
        for (Answer answer : answers) {
            if (answer.getQuestionNumber() > maxQuestionNumber) {
                while (maxQuestionNumber < answer.getQuestionNumber()) {
                    parentAnswers.add(new ArrayList<Answer>());
                    maxQuestionNumber++;
                }
            }
        }
        parentAnswers.add(new ArrayList<Answer>());
        return parentAnswers;

    }

    public void setBusinessObjectService(BusinessObjectService businessObjectService) {
        this.businessObjectService = businessObjectService;
    }

    /*
     * initialize answer fields based on question
     */
    private AnswerHeader setupAnswerForQuestionnaire(Questionnaire questionnaire, ModuleQuestionnaireBean moduleQuestionnaireBean) {
        AnswerHeader answerHeader = new AnswerHeader(moduleQuestionnaireBean, questionnaire.getQuestionnaireRefId());
        answerHeader.setQuestionnaire(questionnaire);
        List<Answer> answers = new ArrayList<Answer>();
        for (QuestionnaireQuestion question : questionnaire.getQuestionnaireQuestions()) {
            if (question.getParentQuestionNumber() == 0) {
                answers.addAll(setupAnswersForQuestion(question));
                answers.addAll(getChildQuestions(questionnaire, question));
            }
        }
        setupChildAnswerIndicator(answers);
        answerHeader.setAnswers(answers);
        return answerHeader;
    }


    /*
     * Load the descendant questions for this questionnaire question.
     */
    private List<Answer> getChildQuestions(Questionnaire questionnaire, QuestionnaireQuestion question) {
        // TODO : if this not efficient, then may want to use businessobjectservice to retrieve it.
        List<Answer> answers = new ArrayList<Answer>();
        for (QuestionnaireQuestion questionnaireQuestion : questionnaire.getQuestionnaireQuestions()) {
            if (questionnaireQuestion.getParentQuestionNumber() != 0
                    && questionnaireQuestion.getParentQuestionNumber().equals(question.getQuestionNumber())) {
                answers.addAll(setupAnswersForQuestion(questionnaireQuestion));
                answers.addAll(getChildQuestions(questionnaire, questionnaireQuestion));
            }
        }
        return answers;

    }

    /*
     * Utility method to really fill the answer fields from question.
     */
    private List<Answer> setupAnswersForQuestion(QuestionnaireQuestion questionnaireQuestion) {
        List<Answer> answers = new ArrayList<Answer>();
        int maxAnswers = 1;
        if (questionnaireQuestion.getQuestion().getMaxAnswers() != null) {
            maxAnswers = questionnaireQuestion.getQuestion().getMaxAnswers();
        }
        for (int i = 0; i < maxAnswers; i++) {
            Answer answer = new Answer();
            answer.setQuestion(questionnaireQuestion.getQuestion());
            answer.setQuestionNumber(questionnaireQuestion.getQuestionNumber());
            answer.setQuestionRefIdFk(questionnaireQuestion.getQuestion().getQuestionRefId());
            answer.setQuestionnaireQuestionsIdFk(questionnaireQuestion.getQuestionnaireQuestionsId());
            answer.setQuestionnaireQuestion(questionnaireQuestion);
            answer.setAnswerNumber(i + 1);
            answers.add(answer);
        }
        return answers;
    }

    /**
     * 
     * @see org.kuali.kra.questionnaire.answer.QuestionnaireAnswerService#setupChildAnswerIndicator(java.util.List)
     */
    public void setupChildAnswerIndicator(List<Answer> answers) {
        // TODO : what if question maxanswer > 1
        List<List<Answer>> parentAnswers = setupParentAnswers(answers);
        // for (Answer answer : answers) {
        // parentAnswers.get(answer.getQuestionNumber()).add(answer);
        // }
        for (Answer answer : answers) {
            if (answer.getQuestionnaireQuestion().getParentQuestionNumber() > 0) {
                answer.setParentAnswer(parentAnswers.get(answer.getQuestionnaireQuestion().getParentQuestionNumber()));
            }
        }
        // for (AnswerHeader answerHeader : answers) {
        Collections.sort(answers);
        // }

        for (Answer answer : answers) {
            // parentAnswers.get(answer.getQuestionNumber()).add(answer);
            if (answer.getQuestionnaireQuestion().getParentQuestionNumber() == 0) {
                answer.setMatchedChild("Y");
            }
            else {
                answer.setParentAnswer(parentAnswers.get(answer.getQuestionnaireQuestion().getParentQuestionNumber()));
                if (StringUtils.isBlank(answer.getQuestionnaireQuestion().getCondition())) {
                    answer.setMatchedChild("Y");
                }
                else if (isParentNotDisplayed(parentAnswers.get(answer.getQuestionnaireQuestion().getParentQuestionNumber()))) {
                    answer.setMatchedChild("N");
                }
                else if (isAnyAnswerMatched(answer.getQuestionnaireQuestion().getCondition(), parentAnswers.get(answer
                        .getQuestionnaireQuestion().getParentQuestionNumber()), answer.getQuestionnaireQuestion()
                        .getConditionValue())) {
                    answer.setMatchedChild("Y");
                }
                else {
                    answer.setMatchedChild("N");
                }
            }
        }

    }

    /*
     * check if all the required answers are entered.
     */
    private boolean isQuestionnaireAnswerComplete(List<Answer> answers) {

        boolean isComplete = true;
        for (Answer answer : answers) {
            if (("Y").equals(answer.getMatchedChild()) && StringUtils.isBlank(answer.getAnswer()) && answer.getAnswerNumber() == 1) {
                isComplete = false;
                break;
            }
        }
        return isComplete;
    }

    /*
     * Check if parent answer : if it is not matched to be displayed or answer(s) is entered.
     */
    private boolean isParentNotDisplayed(List<Answer> parentAnswers) {
        boolean valid = true;
        for (Answer answer : parentAnswers) {
            // parent is not displayed
            if (("N").equals(answer.getMatchedChild())) {
                valid = true;
                break;
            }
            else {
                // if one of the parents answer is entered
                valid = valid && StringUtils.isBlank(answer.getAnswer());
                if (!valid) {
                    break;
                }
            }
        }
        return valid;
    }

    /*
     * check if any answer matched conditionvalue
     */
    private boolean isAnyAnswerMatched(String condition, List<Answer> parentAnswers, String conditionValue) {
        boolean valid = false;
        for (Answer answer : parentAnswers) {
            if (StringUtils.isNotBlank(answer.getAnswer())) {
                valid = isAnswerMatched(condition, answer.getAnswer(), conditionValue);
            }
            if (valid) {
                break;
            }
        }
        return valid;
    }

    /*
     * Following are supported condition : var responseArray = [ 'select', 'Contains text value', 'Matches text', 'Less than
     * number', 'Less than or equals number', 'Equals number', 'Greater than or equals number', 'Greater than number', 'Before
     * date', 'After date' ];
     */

    private boolean isAnswerMatched(String condition, String parentAnswer, String conditionValue) {
        boolean valid = false;
        if (ConditionType.CONTAINS_TEXT.getCondition().equals(condition)) {
            valid = StringUtils.containsIgnoreCase(parentAnswer, conditionValue);
        }
        else if (ConditionType.MATCH_TEXT.getCondition().equals(condition)) {
            valid = parentAnswer.equalsIgnoreCase(conditionValue);
        }
        else if (Integer.parseInt(condition) >= 3 && Integer.parseInt(condition) <= 7) {
            valid = (ConditionType.LESS_THAN_NUMBER.getCondition().equals(condition) && (Integer.parseInt(parentAnswer) < Integer
                    .parseInt(conditionValue)))
                    || (ConditionType.LESS_THAN_OR_EQUALS_NUMBER.getCondition().equals(condition) && (Integer
                            .parseInt(parentAnswer) <= Integer.parseInt(conditionValue)))
                    || (ConditionType.EQUALS_NUMBER.getCondition().equals(condition) && (Integer.parseInt(parentAnswer) == Integer
                            .parseInt(conditionValue)))
                    || (ConditionType.GREATER_THAN_OR_EQUALS_NUMBER.getCondition().equals(condition) && (Integer
                            .parseInt(parentAnswer) >= Integer.parseInt(conditionValue)))
                    || (ConditionType.GREATER_THAN_NUMBER.getCondition().equals(condition) && (Integer.parseInt(parentAnswer) > Integer
                            .parseInt(conditionValue)));
        } else if (Integer.parseInt(condition) >= 8) {
            try {
                Date date1 = new Date(dateFormat.parse(parentAnswer).getTime());
                Date date2 = new Date(dateFormat.parse(conditionValue).getTime());
                valid = (ConditionType.BEFORE_DATE.getCondition().equals(condition) && (date1.before(date2)))
                        || (ConditionType.AFTER_DATE.getCondition().equals(condition) && (date1.after(date2)));
            }
            catch (Exception e) {

            }

        }
        return valid;
    }

    /*
     * enum for conditions.  
     */
    private enum ConditionType {
        CONTAINS_TEXT("1"), MATCH_TEXT("2"), LESS_THAN_NUMBER("3"), LESS_THAN_OR_EQUALS_NUMBER("4"), EQUALS_NUMBER("5"), GREATER_THAN_OR_EQUALS_NUMBER(
                "6"), GREATER_THAN_NUMBER("7"), BEFORE_DATE("8"), AFTER_DATE("9");

        String condition;

        ConditionType(String condition) {
            this.condition = condition;
        }

        public String getCondition() {
            return condition;
        }


    }

}
